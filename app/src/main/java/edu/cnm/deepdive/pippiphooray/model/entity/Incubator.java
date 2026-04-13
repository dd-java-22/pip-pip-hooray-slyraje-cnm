package edu.cnm.deepdive.pippiphooray.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "incubator",
    indices = {
        @Index(value = "name")
    }
)

/**
 * Room entity representing an egg incubator device.
 */
public class Incubator {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "incubator_id")
  private long id;

  @SuppressWarnings("NotNullFieldNotInitialized")
  @NonNull
  private String name;

  @SuppressWarnings("NotNullFieldNotInitialized")
  @NonNull
  private String model;

  @ColumnInfo(name = "target_temperature")
  private double targetTemperature;

  @ColumnInfo(name = "target_humidity")
  private double targetHumidity;

  @Nullable
  private String notes;

  @ColumnInfo(name = "active", defaultValue = "1")
  private boolean active;

  /**
   * Gets the unique identifier for this incubator.
   *
   * @return the incubator ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the unique identifier for this incubator.
   *
   * @param id the incubator ID to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets the name of this incubator.
   *
   * @return the incubator name
   */
  @NonNull
  public String getName() {
    return name;
  }

  /**
   * Sets the name of this incubator.
   *
   * @param name the incubator name to set
   */
  public void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * Gets the model of this incubator.
   *
   * @return the incubator model
   */
  @NonNull
  public String getModel() {
    return model;
  }

  /**
   * Sets the model of this incubator.
   *
   * @param model the incubator model to set
   */
  public void setModel(@NonNull String model) {
    this.model = model;
  }

  /**
   * Gets the target temperature in degrees Fahrenheit for this incubator.
   *
   * @return the target temperature
   */
  public double getTargetTemperature() {
    return targetTemperature;
  }

  /**
   * Sets the target temperature in degrees Fahrenheit for this incubator.
   *
   * @param targetTemperature the target temperature to set
   */
  public void setTargetTemperature(double targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  /**
   * Gets the target humidity percentage for this incubator.
   *
   * @return the target humidity
   */
  public double getTargetHumidity() {
    return targetHumidity;
  }

  /**
   * Sets the target humidity percentage for this incubator.
   *
   * @param targetHumidity the target humidity to set
   */
  public void setTargetHumidity(double targetHumidity) {
    this.targetHumidity = targetHumidity;
  }

  /**
   * Gets any notes associated with this incubator.
   *
   * @return the notes, or null if none recorded
   */
  @Nullable
  public String getNotes() {
    return notes;
  }

  /**
   * Sets notes associated with this incubator.
   *
   * @param notes the notes to set
   */
  public void setNotes(@Nullable String notes) {
    this.notes = notes;
  }

  /**
   * Checks whether this incubator is active.
   *
   * @return true if active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets whether this incubator is active.
   *
   * @param active true to mark as active, false otherwise
   */
  public void setActive(boolean active) {
    this.active = active;
  }
}