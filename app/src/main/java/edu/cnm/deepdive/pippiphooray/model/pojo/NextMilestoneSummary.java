package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.Nullable;
import java.time.LocalDate;

public class NextMilestoneSummary {

  private final String label;
  private final LocalDate date;

  public NextMilestoneSummary(String label, @Nullable LocalDate date) {
    this.label = label;
    this.date = date;
  }

  public String getLabel() {
    return label;
  }

  public LocalDate getDate() {
    return date;
  }
}