package edu.cnm.deepdive.pippiphooray.model.pojo;

import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class BatchCardSummaryFormatter {

  private static final String BREED_PENDING = "Breed pending";
  private static final int MAX_VISIBLE_BREEDS = 2;

  private BatchCardSummaryFormatter() {
  }

  public static String buildBreedSummary(List<EggGroup> groups) {
    if (groups == null || groups.isEmpty()) {
      return BREED_PENDING;
    }

    Set<String> distinctBreeds = new LinkedHashSet<>();

    for (EggGroup group : groups) {
      if (group == null) {
        continue;
      }
      String breed = group.getBreed();
      if (breed != null) {
        String normalized = breed.trim();
        if (!normalized.isBlank()) {
          distinctBreeds.add(normalized);
        }
      }
    }

    if (distinctBreeds.isEmpty()) {
      return BREED_PENDING;
    }

    List<String> breeds = List.copyOf(distinctBreeds);

    if (breeds.size() == 1) {
      return breeds.get(0);
    }

    if (breeds.size() <= MAX_VISIBLE_BREEDS) {
      return String.join(", ", breeds);
    }

    int remaining = breeds.size() - MAX_VISIBLE_BREEDS;
    return breeds.get(0) + ", " + breeds.get(1) + " +" + remaining;
  }
}