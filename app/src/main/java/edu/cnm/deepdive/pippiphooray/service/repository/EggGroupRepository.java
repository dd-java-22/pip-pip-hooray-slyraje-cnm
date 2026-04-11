package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EggGroupRepository {

  LiveData<List<EggGroup>> getByBatch(long batchId);

  LiveData<EggGroup> get(long id);

  CompletableFuture<Long> save(EggGroup eggGroup);

  CompletableFuture<List<Long>> saveAll(List<EggGroup> eggGroups);

}