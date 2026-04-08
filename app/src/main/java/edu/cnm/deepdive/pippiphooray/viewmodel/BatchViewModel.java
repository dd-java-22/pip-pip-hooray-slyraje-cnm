package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import edu.cnm.deepdive.pippiphooray.service.repository.BatchRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

  private final BatchRepository batchRepository;
  private final LiveData<List<BatchWithIncubator>> allWithIncubator;
  private final MutableLiveData<SortOrder> sortOrder = new MutableLiveData<>(
      SortOrder.NEXT_MILESTONE);
  private final MediatorLiveData<List<BatchWithIncubator>> sortedBatches = new MediatorLiveData<>();

  @Inject
  BatchViewModel(BatchRepository batchRepository) {
    this.batchRepository = batchRepository;
    this.allWithIncubator = batchRepository.getAllWithIncubator();

    sortedBatches.addSource(allWithIncubator, batches ->
        recomputeSortedBatches(batches, sortOrder.getValue()));
    sortedBatches.addSource(sortOrder, order ->
        recomputeSortedBatches(allWithIncubator.getValue(), order));
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

}