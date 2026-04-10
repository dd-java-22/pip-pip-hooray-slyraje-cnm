package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
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
  private final MutableLiveData<SortOrder> sortOrder = new MutableLiveData<>(
      SortOrder.NEXT_MILESTONE);
  private final MediatorLiveData<List<BatchWithIncubator>> sortedBatches = new MediatorLiveData<>();
  private final MutableLiveData<Long> selectedBatchId = new MutableLiveData<>();
  private final MediatorLiveData<BatchWithEggGroups> selectedBatchWithGroups =
      new MediatorLiveData<>();
  private final MediatorLiveData<BatchViabilitySummary> selectedBatchViability =
      new MediatorLiveData<>();

  @Inject
  BatchViewModel(BatchRepository batchRepository, EggRepository eggRepository) {
    this.batchRepository = batchRepository;
    this.allWithIncubator = batchRepository.getAllWithIncubator();
    this.eggRepository = eggRepository;

    sortedBatches.addSource(allWithIncubator, batches ->
        recomputeSortedBatches(batches, sortOrder.getValue()));
    sortedBatches.addSource(sortOrder, order ->
        recomputeSortedBatches(allWithIncubator.getValue(), order));

    selectedBatchWithGroups.addSource(selectedBatchId, id -> {
      if (id != null) {
        LiveData<BatchWithEggGroups> source = batchRepository.getWithGroups(id);
        selectedBatchWithGroups.addSource(source, batchWithGroups -> {
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

  public void setSortOrder(SortOrder order) {
    sortOrder.setValue(order);
  }

  public CompletableFuture<Long> save(Batch batch) {
    return batchRepository.save(batch);
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
    if (batchWithGroups == null || batchWithGroups.getGroups() == null) {
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

  private void attachViabilitySources(BatchWithEggGroups batchWithGroups) {
    selectedBatchViability.setValue(new BatchViabilitySummary(0, 0));

    if (batchWithGroups == null || batchWithGroups.getGroups() == null) {
      return;
    }

    Map<Long, List<Egg>> eggsByGroup = new HashMap<>();

    for (EggGroup group : batchWithGroups.getGroups()) {
      LiveData<List<Egg>> eggSource = eggRepository.getByEggGroupId(group.getId());
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
  }

  private boolean isViable(Egg egg) {
    String status = egg.getHatchStatus();
    return status == null || status.isBlank() || "VIABLE".equals(status);
  }

  public LiveData<BatchViabilitySummary> getSelectedBatchViability() {
    return selectedBatchViability;
  }

}