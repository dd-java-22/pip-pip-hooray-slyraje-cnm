package edu.cnm.deepdive.pippiphooray.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.service.repository.EggGroupRepository;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ViewModel for managing egg group data and operations.
 */
@HiltViewModel
public class EggGroupViewModel extends ViewModel {

  private final EggGroupRepository eggGroupRepository;

  /**
   * Constructs an EggGroupViewModel with the specified repository.
   *
   * @param eggGroupRepository the repository for egg group data access
   */
  @Inject
  EggGroupViewModel(EggGroupRepository eggGroupRepository) {
    this.eggGroupRepository = eggGroupRepository;
  }

  /**
   * Gets all egg groups for a specific batch as LiveData.
   *
   * @param batchId the ID of the batch
   * @return a LiveData list of egg groups in the specified batch
   */
  public LiveData<List<EggGroup>> getByBatch(long batchId) {
    return eggGroupRepository.getByBatch(batchId);
  }

  /**
   * Gets a specific egg group by ID as LiveData.
   *
   * @param id the ID of the egg group to retrieve
   * @return a LiveData containing the egg group with the specified ID
   */
  public LiveData<EggGroup> get(long id) {
    return eggGroupRepository.get(id);
  }

  /**
   * Saves an egg group asynchronously.
   *
   * @param eggGroup the egg group to save
   * @return a CompletableFuture containing the ID of the saved egg group
   */
  public CompletableFuture<Long> save(EggGroup eggGroup) {
    return eggGroupRepository.save(eggGroup);
  }
}