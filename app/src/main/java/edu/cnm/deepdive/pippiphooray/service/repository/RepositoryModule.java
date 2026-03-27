package edu.cnm.deepdive.pippiphooray.service.repository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

  @Binds
  @Singleton
  public abstract IncubatorRepository bindIncubatorRepository(IncubatorRepositoryImpl impl);

  @Binds
  @Singleton
  public abstract BatchRepository bindBatchRepository(BatchRepositoryImpl impl);

  @Binds
  @Singleton
  public abstract EggGroupRepository bindEggGroupRepository(EggGroupRepositoryImpl impl);

  @Binds
  @Singleton
  public abstract EggRepository bindEggRepository(EggRepositoryImpl impl);

  @Binds
  @Singleton
  public abstract CandlingObservationRepository bindCandlingObservationRepository(
      CandlingObservationRepositoryImpl impl);

}