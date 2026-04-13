package edu.cnm.deepdive.pippiphooray.service.database;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import edu.cnm.deepdive.pippiphooray.model.dao.BatchDao;
import edu.cnm.deepdive.pippiphooray.model.dao.CandlingObservationDao;
import edu.cnm.deepdive.pippiphooray.model.dao.EggDao;
import edu.cnm.deepdive.pippiphooray.model.dao.EggGroupDao;
import edu.cnm.deepdive.pippiphooray.model.dao.IncubatorDao;
import javax.inject.Singleton;

/**
 * Hilt module that provides the Room database and DAO dependencies.
 */
@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

  /**
   * Provides the application's Room database instance.
   *
   * @param context application context.
   * @return singleton database instance.
   */
  @Provides
  @Singleton
  public PipPipDatabase provideDatabase(@ApplicationContext Context context) {
    return Room.databaseBuilder(
            context,
            PipPipDatabase.class,
            PipPipDatabase.NAME
        )
        .fallbackToDestructiveMigration(true)
        .build();
  }

  /**
   * Provides the batch DAO.
   *
   * @param database application database.
   * @return batch DAO.
   */
  @Provides
  @Singleton
  public BatchDao provideBatchDao(PipPipDatabase database) {
    return database.getBatchDao();
  }

  /**
   * Provides the candling observation DAO.
   *
   * @param database application database.
   * @return candling observation DAO.
   */
  @Provides
  @Singleton
  public CandlingObservationDao provideCandlingObservationDao(PipPipDatabase database) {
    return database.getCandlingObservationDao();
  }

  /**
   * Provides the egg DAO.
   *
   * @param database application database.
   * @return egg DAO.
   */
  @Provides
  @Singleton
  public EggDao provideEggDao(PipPipDatabase database) {
    return database.getEggDao();
  }

  /**
   * Provides the egg-group DAO.
   *
   * @param database application database.
   * @return egg-group DAO.
   */
  @Provides
  @Singleton
  public EggGroupDao provideEggGroupDao(PipPipDatabase database) {
    return database.getEggGroupDao();
  }

  /**
   * Provides the incubator DAO.
   *
   * @param database application database.
   * @return incubator DAO.
   */
  @Provides
  @Singleton
  public IncubatorDao provideIncubatorDao(PipPipDatabase database) {
    return database.getIncubatorDao();
  }

}