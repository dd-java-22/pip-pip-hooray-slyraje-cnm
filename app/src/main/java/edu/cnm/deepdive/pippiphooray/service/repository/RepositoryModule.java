package edu.cnm.deepdive.pippiphooray.service.repository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

/**
 * Dagger module that provides bindings for repository implementations.
 * All repository implementations are bound as singletons for application-wide use.
 */
@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

  /**
   * Binds the IncubatorRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound IncubatorRepository
   */
  @Binds
  @Singleton
  public abstract IncubatorRepository bindIncubatorRepository(IncubatorRepositoryImpl impl);

  /**
   * Binds the BatchRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound BatchRepository
   */
  @Binds
  @Singleton
  public abstract BatchRepository bindBatchRepository(BatchRepositoryImpl impl);

  /**
   * Binds the EggGroupRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound EggGroupRepository
   */
  @Binds
  @Singleton
  public abstract EggGroupRepository bindEggGroupRepository(EggGroupRepositoryImpl impl);

  /**
   * Binds the EggRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound EggRepository
   */
  @Binds
  @Singleton
  public abstract EggRepository bindEggRepository(EggRepositoryImpl impl);

  /**
   * Binds the CandlingObservationRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound CandlingObservationRepository
   */
  @Binds
  @Singleton
  public abstract CandlingObservationRepository bindCandlingObservationRepository(
      CandlingObservationRepositoryImpl impl);

  /**
   * Binds the GoogleAuthRepository interface to its implementation.
   *
   * @param impl the implementation to bind
   * @return the bound GoogleAuthRepository
   */
  @Binds
  @Singleton
  public abstract GoogleAuthRepository bindGoogleAuthRepository(
      GoogleAuthRepositoryImpl impl);

}