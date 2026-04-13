package edu.cnm.deepdive.pippiphooray.model.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class NextMilestoneHelper {

  private static final String LABEL_DAY_7 = "Next: Day 7 Candling Observation";
  private static final String LABEL_DAY_14 = "Next: Day 14 Candling Observation";
  private static final String LABEL_DAY_18 = "Next: Day 18 Candling / Lockdown";
  private static final String LABEL_HATCH = "Next: Expected Hatch Day";

  private NextMilestoneHelper() {
  }

  @NonNull
  public static NextMilestoneSummary computeNextMilestone(
      @Nullable LocalDate dateSet,
      @Nullable LocalDate lockdownDate,
      @Nullable LocalDate expectedHatchDate,
      @NonNull LocalDate today
  ) {
    LocalDate day7 = (dateSet != null) ? dateSet.plusDays(7) : null;
    LocalDate day14 = (dateSet != null) ? dateSet.plusDays(14) : null;
    LocalDate day18 = computeLockdownDate(dateSet, lockdownDate, expectedHatchDate);

    List<NextMilestoneSummary> candidates = new ArrayList<>();
    candidates.add(new NextMilestoneSummary(LABEL_DAY_7, day7));
    candidates.add(new NextMilestoneSummary(LABEL_DAY_14, day14));
    candidates.add(new NextMilestoneSummary(LABEL_DAY_18, day18));
    candidates.add(new NextMilestoneSummary(LABEL_HATCH, expectedHatchDate));

    NextMilestoneSummary fallback = null;

    for (NextMilestoneSummary candidate : candidates) {
      LocalDate date = candidate.getDate();
      if (date == null) {
        continue;
      }
      fallback = candidate;
      if (!date.isBefore(today)) {
        return candidate;
      }
    }

    return (fallback != null)
        ? fallback
        : new NextMilestoneSummary("Next milestone unavailable", null);
  }

  @Nullable
  private static LocalDate computeLockdownDate(
      @Nullable LocalDate dateSet,
      @Nullable LocalDate lockdownDate,
      @Nullable LocalDate expectedHatchDate
  ) {
    if (lockdownDate != null) {
      return lockdownDate;
    }
    if (expectedHatchDate != null) {
      return expectedHatchDate.minusDays(3);
    }
    return (dateSet != null) ? dateSet.plusDays(18) : null;
  }
}