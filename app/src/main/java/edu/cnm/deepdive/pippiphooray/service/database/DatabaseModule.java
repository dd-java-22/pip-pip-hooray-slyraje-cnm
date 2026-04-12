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

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

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

  @Provides
  @Singleton
  public BatchDao provideBatchDao(PipPipDatabase database) {
    return database.getBatchDao();
  }

  @Provides
  @Singleton
  public CandlingObservationDao provideCandlingObservationDao(PipPipDatabase database) {
    return database.getCandlingObservationDao();
  }

  @Provides
  @Singleton
  public EggDao provideEggDao(PipPipDatabase database) {
    return database.getEggDao();
  }

  @Provides
  @Singleton
  public EggGroupDao provideEggGroupDao(PipPipDatabase database) {
    return database.getEggGroupDao();
  }

  @Provides
  @Singleton
  public IncubatorDao provideIncubatorDao(PipPipDatabase database) {
    return database.getIncubatorDao();
  }

}