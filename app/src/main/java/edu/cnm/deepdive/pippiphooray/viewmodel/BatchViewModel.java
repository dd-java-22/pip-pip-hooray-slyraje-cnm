package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchCardSummary;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchCardSummaryFormatter;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchEggAggregate;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchViabilitySummary;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import edu.cnm.deepdive.pippiphooray.service.repository.BatchRepository;
import edu.cnm.deepdive.pippiphooray.service.repository.EggRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * ViewModel for managing batch data, sorting, and egg viability operations.
 */
@HiltViewModel
public class BatchViewModel extends ViewModel {

  /**
   * Enumeration defining available sort orders for batches.
   */
  public enum SortOrder {
    /** Sort by next milestone date */
    NEXT_MILESTONE,
    /** Sort by expected hatch date */
    EXPECTED_HATCH_DATE,
    /** Sort by batch number */
    BATCH_NUMBER,
    /** Sort by incubator name */
    INCUBATOR
  }

  private static final String STATUS_VIABLE = "VIABLE";
  private static final String STATUS_NOT_VIABLE = "NOT_VIABLE";

  private final BatchRepository batchRepository;
  private final EggRepository eggRepository;

  private final LiveData<List<BatchWithIncubator>> allWithIncubator;
  private final LiveData<List<BatchWithEggGroups>> allWithGroups;
  private final LiveData<List<BatchEggAggregate>> viabilityPerBatch;
  private final MutableLiveData<SortOrder> sortOrder = new MutableLiveData<>(
      SortOrder.NEXT_MILESTONE);
  private final MediatorLiveData<List<BatchWithIncubator>> sortedBatches = new MediatorLiveData<>();
  private final MutableLiveData<Long> selectedBatchId = new MutableLiveData<>();
  private final MediatorLiveData<BatchWithEggGroups> selectedBatchWithGroups =
      new MediatorLiveData<>();
  private final MediatorLiveData<BatchViabilitySummary> selectedBatchViability =
      new MediatorLiveData<>();
  private final MediatorLiveData<List<BatchCardSummary>> batchCardSummaries =
      new MediatorLiveData<>();
  private final Map<Long, LiveData<List<Egg>>> viabilitySources = new HashMap<>();
  private final Map<Long, List<Egg>> eggsByGroup = new HashMap<>();
  private LiveData<BatchWithEggGroups> selectedBatchSource;

  /**
   * Constructs a BatchViewModel with the specified repositories.
   *
   * @param batchRepository the repository for batch data access
   * @param eggRepository the repository for egg data access
   */
  @Inject
  BatchViewModel(BatchRepository batchRepository, EggRepository eggRepository) {
    this.batchRepository = batchRepository;
    this.eggRepository = eggRepository;
    this.allWithIncubator = batchRepository.getAllWithIncubator();
    this.allWithGroups = batchRepository.getAllWithGroups();
    this.viabilityPerBatch = eggRepository.getViabilityPerBatch();

    sortedBatches.addSource(allWithIncubator, batches ->
        recomputeSortedBatches(batches, sortOrder.getValue()));
    sortedBatches.addSource(sortOrder, order ->
        recomputeSortedBatches(allWithIncubator.getValue(), order));

    batchCardSummaries.addSource(allWithIncubator, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(),
            sortOrder.getValue(), viabilityPerBatch.getValue()));
    batchCardSummaries.addSource(allWithGroups, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(),
            sortOrder.getValue(), viabilityPerBatch.getValue()));
    batchCardSummaries.addSource(sortOrder, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(),
            sortOrder.getValue(), viabilityPerBatch.getValue()));
    batchCardSummaries.addSource(viabilityPerBatch, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(),
            allWithGroups.getValue(),
            sortOrder.getValue(),
            viabilityPerBatch.getValue()
        ));

    selectedBatchWithGroups.addSource(selectedBatchId, id -> {
      if (selectedBatchSource != null) {
        selectedBatchWithGroups.removeSource(selectedBatchSource);
        selectedBatchSource = null;
      }
      clearViabilitySources();

      if (id != null) {
        selectedBatchSource = batchRepository.getWithGroups(id);
        selectedBatchWithGroups.addSource(selectedBatchSource, batchWithGroups -> {
          selectedBatchWithGroups.setValue(batchWithGroups);
          attachViabilitySources(batchWithGroups);
        });
      } else {
        selectedBatchWithGroups.setValue(null);
        selectedBatchViability.setValue(new BatchViabilitySummary(0, 0));
      }
    });
  }

  /**
   * Gets all batches as LiveData.
   *
   * @return a LiveData list of all batches
   */
  public LiveData<List<Batch>> getAll() {
    return batchRepository.getAll();
  }

  /**
   * Gets a specific batch by ID as LiveData.
   *
   * @param id the batch ID
   * @return a LiveData containing the batch with the specified ID
   */
  public LiveData<Batch> get(long id) {
    return batchRepository.get(id);
  }

  /**
   * Gets all batches for a specific incubator as LiveData.
   *
   * @param incubatorId the incubator ID
   * @return a LiveData list of batches for the specified incubator
   */
  public LiveData<List<Batch>> getByIncubator(long incubatorId) {
    return batchRepository.getByIncubator(incubatorId);
  }

  /**
   * Gets a specific batch with its egg groups as LiveData.
   *
   * @param id the batch ID
   * @return a LiveData containing the batch with its egg groups
   */
  public LiveData<BatchWithEggGroups> getWithGroups(long id) {
    return batchRepository.getWithGroups(id);
  }

  /**
   * Gets all batches with incubator information as LiveData.
   *
   * @return a LiveData list of all batches with incubator details
   */
  public LiveData<List<BatchWithIncubator>> getAllWithIncubator() {
    return allWithIncubator;
  }

  /**
   * Gets all batches sorted according to the current sort order as LiveData.
   *
   * @return a LiveData list of sorted batches with incubator details
   */
  public LiveData<List<BatchWithIncubator>> getBatchSummaries() {
    return sortedBatches;
  }

  /**
   * Gets all batch card summaries for display in the UI as LiveData.
   *
   * @return a LiveData list of batch card summaries
   */
  public LiveData<List<BatchCardSummary>> getBatchCardSummaries() {
    return batchCardSummaries;
  }

  /**
   * Sets the sort order for batches.
   *
   * @param order the sort order to apply
   */
  public void setSortOrder(SortOrder order) {
    sortOrder.setValue(order);
  }

  /**
   * Saves a batch asynchronously.
   *
   * @param batch the batch to save
   * @return a CompletableFuture containing the ID of the saved batch
   */
  public CompletableFuture<Long> save(Batch batch) {
    return batchRepository.save(batch);
  }

  /**
   * Saves a batch with an initial egg group asynchronously.
   *
   * @param batch the batch to save
   * @param breed the breed for the initial egg group
   * @return a CompletableFuture containing the ID of the saved batch
   */
  public CompletableFuture<Long> saveWithInitialEggGroups(Batch batch, String breed) {
    return batchRepository.saveWithInitialEggGroups(batch, breed);
  }

  /**
   * Deletes a batch asynchronously.
   *
   * @param batch the batch to delete
   * @return a CompletableFuture that completes when the deletion finishes
   */
  public CompletableFuture<Void> delete(Batch batch) {
    return batchRepository.delete(batch);
  }

  /**
   * Sets the selected batch by ID.
   *
   * @param batchId the ID of the batch to select
   */
  public void setSelectedBatchId(long batchId) {
    selectedBatchId.setValue(batchId);
  }

  /**
   * Gets the selected batch with its egg groups as LiveData.
   *
   * @return a LiveData containing the selected batch with its egg groups
   */
  public LiveData<BatchWithEggGroups> getSelectedBatchWithGroups() {
    return selectedBatchWithGroups;
  }

  /**
   * Gets all eggs for a specific egg group as LiveData.
   *
   * @param eggGroupId the egg group ID
   * @return a LiveData list of eggs in the specified group
   */
  public LiveData<List<Egg>> getEggsByGroup(long eggGroupId) {
    return eggRepository.getByEggGroupId(eggGroupId);
  }

  /**
   * Applies bulk candling to a batch by marking eggs as viable or not viable.
   *
   * @param viableCountsByGroupId a map of egg group IDs to viable egg counts
   * @return a CompletableFuture that completes when the operation finishes
   */
  public CompletableFuture<Void> applyBulkCandling(Map<Long, Integer> viableCountsByGroupId) {
    BatchWithEggGroups batchWithGroups = selectedBatchWithGroups.getValue();
    if (batchWithGroups == null || batchWithGroups.getGroups() == null
        || viableCountsByGroupId == null || viableCountsByGroupId.isEmpty()) {
      return CompletableFuture.completedFuture(null);
    }

    List<CompletableFuture<Void>> futures = new ArrayList<>();

    for (EggGroup group : batchWithGroups.getGroups()) {
      Integer viableCount = viableCountsByGroupId.get(group.getId());
      if (viableCount == null) {
        continue;
      }

      CompletableFuture<Void> future = eggRepository.fetchByEggGroupId(group.getId())
          .thenCompose(eggs -> {
            eggs.sort(Comparator.comparingInt(Egg::getEggNumber));

            int clampedViableCount = Math.max(0, Math.min(viableCount, eggs.size()));

            for (int i = 0; i < eggs.size(); i++) {
              Egg egg = eggs.get(i);
              if (i < clampedViableCount) {
                egg.setHatchStatus(STATUS_VIABLE);
              } else {
                egg.setHatchStatus(STATUS_NOT_VIABLE);
              }
            }

            return eggRepository.saveAll(eggs);
          });

      futures.add(future);
    }

    return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
  }

  /**
   * Gets the viability summary for the selected batch as LiveData.
   *
   * @return a LiveData containing the batch viability summary
   */
  public LiveData<BatchViabilitySummary> getSelectedBatchViability() {
    return selectedBatchViability;
  }

  private void recomputeSortedBatches(List<BatchWithIncubator> batches, SortOrder order) {
    if (batches == null || order == null) {
      sortedBatches.setValue(batches);
      return;
    }

    List<BatchWithIncubator> copy = new ArrayList<>(batches);

    Comparator<BatchWithIncubator> comparator = switch (order) {
      case EXPECTED_HATCH_DATE, NEXT_MILESTONE -> Comparator.comparing(
          BatchWithIncubator::getExpectedHatchDate,
          Comparator.nullsLast(Comparator.naturalOrder())
      );

      case BATCH_NUMBER -> Comparator.comparing(
          BatchWithIncubator::getBatchNumber,
          Comparator.nullsLast(Comparator.naturalOrder())
      ).thenComparing(
          BatchWithIncubator::getDateSet,
          Comparator.nullsLast(Comparator.reverseOrder())
      );

      case INCUBATOR -> Comparator.comparing(
          BatchWithIncubator::getIncubatorName,
          Comparator.nullsLast(String::compareToIgnoreCase)
      ).thenComparing(
          BatchWithIncubator::getDateSet,
          Comparator.nullsLast(Comparator.reverseOrder())
      );
    };

    copy.sort(comparator);
    sortedBatches.setValue(copy);
  }

  private void attachViabilitySources(BatchWithEggGroups batchWithGroups) {
    clearViabilitySources();
    selectedBatchViability.setValue(new BatchViabilitySummary(0, 0));

    if (batchWithGroups == null || batchWithGroups.getGroups() == null) {
      return;
    }

    for (EggGroup group : batchWithGroups.getGroups()) {
      LiveData<List<Egg>> eggSource = eggRepository.getByEggGroupId(group.getId());
      viabilitySources.put(group.getId(), eggSource);

      selectedBatchViability.addSource(eggSource, eggs -> {
        eggsByGroup.put(group.getId(), eggs);
        recomputeViability(eggsByGroup);
      });
    }
  }

  private void recomputeViability(Map<Long, List<Egg>> eggsByGroup) {
    int total = 0;
    int viable = 0;

    for (List<Egg> eggs : eggsByGroup.values()) {
      if (eggs == null) {
        continue;
      }
      total += eggs.size();
      for (Egg egg : eggs) {
        if (isViable(egg)) {
          viable++;
        }
      }
    }

    selectedBatchViability.setValue(new BatchViabilitySummary(viable, total));
    android.util.Log.d("BatchViewModel", "viability = " + viable + "/" + total);
  }

  private boolean isViable(Egg egg) {
    String status = egg.getHatchStatus();
    return STATUS_VIABLE.equals(status);
  }

  private void recomputeBatchCardSummaries(
      List<BatchWithIncubator> incubatorBatches,
      List<BatchWithEggGroups> groupBatches,
      SortOrder order,
      List<BatchEggAggregate> viabilityAggregates
  ) {
    if (incubatorBatches == null) {
      batchCardSummaries.setValue(null);
      return;
    }

    Map<Long, BatchWithEggGroups> groupsByBatchId = new HashMap<>();
    if (groupBatches != null) {
      for (BatchWithEggGroups bwg : groupBatches) {
        if (bwg != null && bwg.getBatch() != null) {
          groupsByBatchId.put(bwg.getBatch().getId(), bwg);
        }
      }
    }

    Map<Long, BatchEggAggregate> viabilityByBatchId = new HashMap<>();
    if (viabilityAggregates != null) {
      for (BatchEggAggregate agg : viabilityAggregates) {
        viabilityByBatchId.put(agg.getBatchId(), agg);
      }
    }

    List<BatchCardSummary> summaries = new ArrayList<>();
    LocalDate today = LocalDate.now();

    for (BatchWithIncubator batch : incubatorBatches) {
      BatchWithEggGroups withGroups = groupsByBatchId.get(batch.getId());
      String breedSummary = (withGroups != null)
          ? BatchCardSummaryFormatter.buildBreedSummary(withGroups.getGroups())
          : "Breed pending";

      BatchEggAggregate agg = viabilityByBatchId.get(batch.getId());
      int totalEggCount = (agg != null) ? agg.getTotalCount() : 0;
      int viableCount   = (agg != null) ? agg.getViableCount() : 0;
      double viabilityRate = (totalEggCount > 0)
          ? ((double) viableCount / totalEggCount)
          : 0.0;

      summaries.add(new BatchCardSummary(
          batch.getId(),
          batch.getBatchNumber(),
          batch.getIncubatorName(),
          breedSummary,
          viableCount,
          totalEggCount,
          viabilityRate,
          batch.getExpectedHatchDate(),
          computeNextMilestoneLabel(batch, today),
          computeNextMilestoneDate(batch, today)
      ));
    }

    if (order != null) {
      Comparator<BatchCardSummary> comparator = switch (order) {
        case EXPECTED_HATCH_DATE -> Comparator.comparing(
            BatchCardSummary::getExpectedHatchDate,
            Comparator.nullsLast(Comparator.naturalOrder())
        );
        case NEXT_MILESTONE -> Comparator.comparing(
            BatchCardSummary::getNextMilestoneDate,
            Comparator.nullsLast(Comparator.naturalOrder())
        );
        case BATCH_NUMBER -> Comparator.comparing(
            BatchCardSummary::getBatchNumber,
            Comparator.nullsLast(Comparator.naturalOrder())
        ).thenComparing(
            BatchCardSummary::getExpectedHatchDate,
            Comparator.nullsLast(Comparator.reverseOrder())
        );
        case INCUBATOR -> Comparator.comparing(
            BatchCardSummary::getIncubatorName,
            Comparator.nullsLast(String::compareToIgnoreCase)
        ).thenComparing(
            BatchCardSummary::getExpectedHatchDate,
            Comparator.nullsLast(Comparator.reverseOrder())
        );
      };
      summaries.sort(comparator);
    }

    batchCardSummaries.setValue(summaries);
  }

  private String computeNextMilestoneLabel(BatchWithIncubator batch, LocalDate today) {
    LocalDate day7 = offsetDays(batch.getDateSet(), 7);
    if (day7 != null && !day7.isBefore(today)) {
      return "Next: Day 7 Candling Observation";
    }

    LocalDate day14 = offsetDays(batch.getDateSet(), 14);
    if (day14 != null && !day14.isBefore(today)) {
      return "Next: Day 14 Candling Observation";
    }

    LocalDate day18 = getLockdownDate(batch);
    if (day18 != null && !day18.isBefore(today)) {
      return "Next: Day 18 Candling / Lockdown";
    }

    LocalDate hatch = batch.getExpectedHatchDate();
    if (hatch != null) {
      return "Next: Expected Hatch Day";
    }

    return "Next milestone unavailable";
  }

  private LocalDate computeNextMilestoneDate(BatchWithIncubator batch, LocalDate today) {
    LocalDate day7 = offsetDays(batch.getDateSet(), 7);
    if (day7 != null && !day7.isBefore(today)) {
      return day7;
    }

    LocalDate day14 = offsetDays(batch.getDateSet(), 14);
    if (day14 != null && !day14.isBefore(today)) {
      return day14;
    }

    LocalDate day18 = getLockdownDate(batch);
    if (day18 != null && !day18.isBefore(today)) {
      return day18;
    }

    return batch.getExpectedHatchDate();
  }

  private LocalDate getLockdownDate(BatchWithIncubator batch) {
    if (batch.getLockdownDate() != null) {
      return batch.getLockdownDate();
    }
    if (batch.getExpectedHatchDate() != null) {
      return batch.getExpectedHatchDate().minusDays(3);
    }
    return offsetDays(batch.getDateSet(), 18);
  }

  private LocalDate offsetDays(LocalDate date, long days) {
    return (date != null) ? date.plusDays(days) : null;
  }

  private void clearViabilitySources() {
    for (LiveData<List<Egg>> source : viabilitySources.values()) {
      selectedBatchViability.removeSource(source);
    }
    viabilitySources.clear();
    eggsByGroup.clear();
  }

}