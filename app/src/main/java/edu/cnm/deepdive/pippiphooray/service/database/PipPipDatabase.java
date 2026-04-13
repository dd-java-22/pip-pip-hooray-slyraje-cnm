package edu.cnm.deepdive.pippiphooray.service.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
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
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Main Room database for the Pip Pip Hooray application.
 *
 * <p>This database stores batches, incubators, egg groups, eggs, and candling
 * observations, and exposes DAO access methods for each entity type.
 */
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
  static final int VERSION = 3;

  /**
   * Returns the DAO used for batch persistence operations.
   *
   * @return batch DAO.
   */
  public abstract BatchDao getBatchDao();

  /**
   * Returns the DAO used for candling observation persistence operations.
   *
   * @return candling observation DAO.
   */
  public abstract CandlingObservationDao getCandlingObservationDao();

  /**
   * Returns the DAO used for egg persistence operations.
   *
   * @return egg DAO.
   */
  public abstract EggDao getEggDao();

  /**
   * Returns the DAO used for egg-group persistence operations.
   *
   * @return egg-group DAO.
   */
  public abstract EggGroupDao getEggGroupDao();

  /**
   * Returns the DAO used for incubator persistence operations.
   *
   * @return incubator DAO.
   */
  public abstract IncubatorDao getIncubatorDao();

  /**
   * Type converters used by Room for unsupported Java time types.
   */
  public static class Converters {

    /**
     * Converts an {@link Instant} to epoch milliseconds for database storage.
     *
     * @param value instant value to convert.
     * @return epoch milliseconds, or {@code null} if the input is null.
     */
    @TypeConverter
    public static Long toLongFromInstant(Instant value) {
      return (value != null) ? value.toEpochMilli() : null;
    }

    /**
     * Converts stored epoch milliseconds to an {@link Instant}.
     *
     * @param value epoch milliseconds value.
     * @return instant represented by the value, or {@code null} if the input is null.
     */
    @TypeConverter
    public static Instant toInstant(Long value) {
      return (value != null) ? Instant.ofEpochMilli(value) : null;
    }

    /**
     * Converts a {@link LocalDate} to epoch-day form for database storage.
     *
     * @param value local date to convert.
     * @return epoch day value, or {@code null} if the input is null.
     */
    @TypeConverter
    public static Long toLong(LocalDate value) {
      return (value != null) ? value.toEpochDay() : null;
    }

    /**
     * Converts an epoch-day value to a {@link LocalDate}.
     *
     * @param value epoch-day value.
     * @return local date represented by the value, or {@code null} if the input is null.
     */
    @TypeConverter
    public static LocalDate toLocalDate(Long value) {
      return (value != null) ? LocalDate.ofEpochDay(value) : null;
    }
  }

}