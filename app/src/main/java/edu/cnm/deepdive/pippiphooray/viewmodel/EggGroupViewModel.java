package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.service.repository.EggGroupRepository;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@HiltViewModel
public class EggGroupViewModel extends ViewModel {

  private final EggGroupRepository eggGroupRepository;

  @Inject
  EggGroupViewModel(EggGroupRepository eggGroupRepository) {
    this.eggGroupRepository = eggGroupRepository;
  }

  public LiveData<List<EggGroup>> getByBatch(long batchId) {
    return eggGroupRepository.getByBatch(batchId);
  }

  public LiveData<EggGroup> get(long id) {
    return eggGroupRepository.get(id);
  }

  public CompletableFuture<Long> save(EggGroup eggGroup) {
    return eggGroupRepository.save(eggGroup);
  }
}