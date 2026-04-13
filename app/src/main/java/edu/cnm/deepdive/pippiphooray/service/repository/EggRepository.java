package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchEggAggregate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EggRepository {

  LiveData<List<Egg>> getByEggGroupId(long eggGroupId);

  LiveData<Egg> get(long id);

  LiveData<List<BatchEggAggregate>> getViabilityPerBatch();

  CompletableFuture<List<Egg>> fetchByEggGroupId(long eggGroupId);

  CompletableFuture<Long> save(Egg egg);

  CompletableFuture<Void> saveAll(List<Egg> eggs);

  CompletableFuture<Void> seedEggsForGroup(long eggGroupId, int initialEggCount);

}