package edu.cnm.deepdive.pippiphooray.service.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import edu.cnm.deepdive.pippiphooray.model.dao.BatchDao;
import edu.cnm.deepdive.pippiphooray.model.dao.CandlingObservationDao;
import edu.cnm.deepdive.pippiphooray.model.dao.EggDao;
import edu.cnm.deepdive.pippiphooray.model.dao.EggGroupDao;
import edu.cnm.deepdive.pippiphooray.model.dao.IncubatorDao;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.CandlingObservation;
import edu.cnm.deepdive.pippiphooray.model.entity.Egg;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.time.Instant;
import java.time.LocalDate;

@TypeConverters(PipPipDatabase.Converters.class)
@Database(
    entities = {
        Egg.class,
        EggGroup.class,
        Batch.class,
        Incubator.class,
        CandlingObservation.class
    },
    views = {
        BatchWithIncubator.class
    },

    version = PipPipDatabase.VERSION
)
public abstract class PipPipDatabase extends RoomDatabase {

  static final String NAME = "pip_pip_hooray_db";
  static final int VERSION = 1;

  public abstract BatchDao getBatchDao();

  public abstract CandlingObservationDao getCandlingObservationDao();

  public abstract EggDao getEggDao();

  public abstract EggGroupDao getEggGroupDao();

  public abstract IncubatorDao getIncubatorDao();

  public static class Converters {

    @TypeConverter
    public static Long toLongFromInstant(Instant value) {
      return (value != null) ? value.toEpochMilli() : null;
    }

    @TypeConverter
    public static Instant toInstant(Long value) {
      return (value != null) ? Instant.ofEpochMilli(value) : null;
    }

    @TypeConverter
    public static Long toLong(LocalDate value) {
      return (value != null) ? value.toEpochDay() : null;
    }

    @TypeConverter
    public static LocalDate toLocalDate(Long value) {
      return (value != null) ? LocalDate.ofEpochDay(value) : null;
    }
  }

}
