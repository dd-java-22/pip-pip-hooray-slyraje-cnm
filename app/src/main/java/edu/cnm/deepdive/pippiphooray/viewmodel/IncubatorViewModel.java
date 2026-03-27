package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import edu.cnm.deepdive.pippiphooray.service.repository.IncubatorRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

@HiltViewModel
public class IncubatorViewModel extends ViewModel {

  private final IncubatorRepository incubatorRepository;
  private final LiveData<List<Incubator>> activeIncubators;

  @Inject
  IncubatorViewModel(IncubatorRepository incubatorRepository) {
    this.incubatorRepository = incubatorRepository;
    this.activeIncubators = incubatorRepository.getActiveIncubators();
  }

  public LiveData<List<Incubator>> getActiveIncubators() {
    return activeIncubators;
  }

  public CompletableFuture<Long> save(Incubator incubator) {
    return incubatorRepository.save(incubator);
  }

  public CompletableFuture<Void> deactivate(Incubator incubator) {
    return incubatorRepository.deactivate(incubator);
  }

}