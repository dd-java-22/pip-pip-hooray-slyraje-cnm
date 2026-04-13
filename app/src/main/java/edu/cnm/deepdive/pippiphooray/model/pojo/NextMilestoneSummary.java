package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.Nullable;
import java.time.LocalDate;

/**
 * Simple value object that describes the next incubation milestone.
 *
 * <p>Each instance contains a human-readable label and an optional date.
 */
public class NextMilestoneSummary {

  private final String label;
  private final LocalDate date;

  /**
   * Creates a new milestone summary.
   *
   * @param label human-readable milestone label.
   * @param date milestone date, or {@code null} if unavailable.
   */
  public NextMilestoneSummary(String label, @Nullable LocalDate date) {
    this.label = label;
    this.date = date;
  }

  /**
   * Returns the milestone label.
   *
   * @return milestone label.
   */
  public String getLabel() {
    return label;
  }

  /**
   * Returns the milestone date.
   *
   * @return milestone date, or {@code null} if unavailable.
   */
  public LocalDate getDate() {
    return date;
  }
}