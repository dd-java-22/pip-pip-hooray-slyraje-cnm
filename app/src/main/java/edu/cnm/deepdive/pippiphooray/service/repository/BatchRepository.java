package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BatchRepository {

  LiveData<List<Batch>> getAll();

  LiveData<Batch> get(long id);

  LiveData<List<Batch>> getByIncubator(long incubatorId);

  LiveData<BatchWithEggGroups> getWithGroups(long id);

  LiveData<List<BatchWithIncubator>> getAllWithIncubator();

  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByExpectedHatch();

  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByIncubator();

  LiveData<List<BatchWithIncubator>> getAllWithIncubatorOrderByBatchNumber();

  CompletableFuture<Long> save(Batch batch);

  CompletableFuture<Void> delete(Batch batch); // if you choose to support deletion
}