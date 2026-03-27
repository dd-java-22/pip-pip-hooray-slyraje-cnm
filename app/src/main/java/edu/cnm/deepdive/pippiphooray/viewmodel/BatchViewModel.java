package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import edu.cnm.deepdive.pippiphooray.service.repository.BatchRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

@HiltViewModel
public class BatchViewModel extends ViewModel {

  private final BatchRepository batchRepository;

  @Inject
  BatchViewModel(BatchRepository batchRepository) {
    this.batchRepository = batchRepository;
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
    return batchRepository.getAllWithIncubator();
  }

  public CompletableFuture<Long> save(Batch batch) {
    return batchRepository.save(batch);
  }

  public CompletableFuture<Void> delete(Batch batch) {
    return batchRepository.delete(batch);
  }

}