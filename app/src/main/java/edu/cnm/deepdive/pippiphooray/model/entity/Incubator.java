package edu.cnm.deepdive.pippiphooray.model.entity;

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
public class Incubator {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "incubator_id")
  private long incubatorId;

  @ColumnInfo(index = true)
  private String name;

  private String model;

  @ColumnInfo(name = "target_temperature")
  private double targetTemperature;

  @ColumnInfo(name = "target_humidity")
  private double targetHumidity;

  private String notes;

  public long getIncubatorId() {
    return incubatorId;
  }

  public void setIncubatorId(long incubatorId) {
    this.incubatorId = incubatorId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public double getTargetTemperature() {
    return targetTemperature;
  }

  public void setTargetTemperature(double targetTemperature) {
    this.targetTemperature = targetTemperature;
  }

  public double getTargetHumidity() {
    return targetHumidity;
  }

  public void setTargetHumidity(double targetHumidity) {
    this.targetHumidity = targetHumidity;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

}
