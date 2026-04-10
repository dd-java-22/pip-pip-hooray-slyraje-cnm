package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EggRepository {

  LiveData<List<Egg>> getByEggGroupId(long eggGroupId);

  LiveData<Egg> get(long id);

  CompletableFuture<List<Egg>> fetchByEggGroupId(long eggGroupId);

  CompletableFuture<Long> save(Egg egg);

  CompletableFuture<Void> saveAll(List<Egg> eggs);

}
