package edu.cnm.deepdive.pippiphooray.service.repository;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IncubatorRepository {

  LiveData<List<Incubator>> getActiveIncubators();

  LiveData<Incubator> get(long id);

  CompletableFuture<Long> save(Incubator incubator); // insert or update

  CompletableFuture<Void> deactivate(Incubator incubator); // soft delete: active = false
}