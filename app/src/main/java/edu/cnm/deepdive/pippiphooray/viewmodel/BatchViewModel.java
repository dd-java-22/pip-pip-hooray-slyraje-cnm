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
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchViabilitySummary;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import edu.cnm.deepdive.pippiphooray.service.repository.BatchRepository;
import edu.cnm.deepdive.pippiphooray.service.repository.EggRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;


@HiltViewModel
public class BatchViewModel extends ViewModel {

  public enum SortOrder {
    NEXT_MILESTONE,
    EXPECTED_HATCH_DATE,
    BATCH_NUMBER,
    INCUBATOR
  }

  private static final String STATUS_VIABLE = "VIABLE";
  private static final String STATUS_NOT_VIABLE = "NOT_VIABLE";

  private final BatchRepository batchRepository;
  private final EggRepository eggRepository;

  private final LiveData<List<BatchWithIncubator>> allWithIncubator;
  private final LiveData<List<BatchWithEggGroups>> allWithGroups;
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


  @Inject
  BatchViewModel(BatchRepository batchRepository, EggRepository eggRepository) {
    this.batchRepository = batchRepository;
    this.eggRepository = eggRepository;
    this.allWithIncubator = batchRepository.getAllWithIncubator();
    this.allWithGroups = batchRepository.getAllWithGroups();

    sortedBatches.addSource(allWithIncubator, batches ->
        recomputeSortedBatches(batches, sortOrder.getValue()));
    sortedBatches.addSource(sortOrder, order ->
        recomputeSortedBatches(allWithIncubator.getValue(), order));

    batchCardSummaries.addSource(allWithIncubator, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(), sortOrder.getValue()));
    batchCardSummaries.addSource(allWithGroups, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(), sortOrder.getValue()));
    batchCardSummaries.addSource(sortOrder, ignored ->
        recomputeBatchCardSummaries(
            allWithIncubator.getValue(), allWithGroups.getValue(), sortOrder.getValue()));

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

  public LiveData<List<Batch>> getAll() {
    return batchRepository.getAll();
  }

  public LiveData<Batch> get(long id) {
    return batchRepository.get(id);
  }

  public LiveData<List<Batch>> getByIncubator(long incubatorId) {
    return batchRepository.getByIncubator(incubatorId);
  }

  public LiveData<BatchWithEggGroups> getWithGroups(long id) {
    return batchRepository.getWithGroups(id);
  }

  public LiveData<List<BatchWithIncubator>> getAllWithIncubator() {
    return allWithIncubator;
  }

  public LiveData<List<BatchWithIncubator>> getBatchSummaries() {
    return sortedBatches;
  }

  public LiveData<List<BatchCardSummary>> getBatchCardSummaries() {
    return batchCardSummaries;
  }

  public void setSortOrder(SortOrder order) {
    sortOrder.setValue(order);
  }

  public CompletableFuture<Long> save(Batch batch) {
    return batchRepository.save(batch);
  }

  public CompletableFuture<Long> saveWithInitialEggGroups(Batch batch, String breed) {
    return batchRepository.saveWithInitialEggGroups(batch, breed);
  }

  public CompletableFuture<Void> delete(Batch batch) {
    return batchRepository.delete(batch);
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

  public void setSelectedBatchId(long batchId) {
    selectedBatchId.setValue(batchId);
  }

  public LiveData<BatchWithEggGroups> getSelectedBatchWithGroups() {
    return selectedBatchWithGroups;
  }

  public LiveData<List<Egg>> getEggsByGroup(long eggGroupId) {
    return eggRepository.getByEggGroupId(eggGroupId);
  }

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

  public LiveData<BatchViabilitySummary> getSelectedBatchViability() {
    return selectedBatchViability;
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
      SortOrder order
  ) {
    if (incubatorBatches == null) {
      batchCardSummaries.setValue(null);
      return;
    }

    Map<Long, BatchWithEggGroups> groupsByBatchId = new HashMap<>();
    if (groupBatches != null) {
      for (BatchWithEggGroups batchWithGroups : groupBatches) {
        if (batchWithGroups != null && batchWithGroups.getBatch() != null) {
          groupsByBatchId.put(batchWithGroups.getBatch().getId(), batchWithGroups);
        }
      }
    }

    List<BatchCardSummary> summaries = new ArrayList<>();
    for (BatchWithIncubator batch : incubatorBatches) {
      BatchWithEggGroups withGroups = groupsByBatchId.get(batch.getId());
      String breedSummary = (withGroups != null)
          ? BatchCardSummaryFormatter.buildBreedSummary(withGroups.getGroups())
          : "Breed pending";

      summaries.add(new BatchCardSummary(
          batch.getId(),
          batch.getBatchNumber(),
          batch.getIncubatorName(),
          breedSummary,
          batch.getNumEggsSet(),
          batch.getExpectedHatchDate()
      ));
    }

    if (order != null) {
      Comparator<BatchCardSummary> comparator = switch (order) {
        case EXPECTED_HATCH_DATE, NEXT_MILESTONE -> Comparator.comparing(
            BatchCardSummary::getExpectedHatchDate,
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

  private void clearViabilitySources() {
    for (LiveData<List<Egg>> source : viabilitySources.values()) {
      selectedBatchViability.removeSource(source);
    }
    viabilitySources.clear();
    eggsByGroup.clear();
  }

}