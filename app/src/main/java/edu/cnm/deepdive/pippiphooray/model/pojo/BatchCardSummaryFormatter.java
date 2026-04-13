package edu.cnm.deepdive.pippiphooray.model.pojo;

import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility class for formatting breed summaries used on batch cards.
 */
public final class BatchCardSummaryFormatter {

  private static final String BREED_PENDING = "Breed pending";
  private static final int MAX_VISIBLE_BREEDS = 2;

  private BatchCardSummaryFormatter() {
  }

  /**
   * Builds a short, human-readable summary of the breeds in the given egg groups.
   *
   * <p>Distinct, non-blank breed names are collected in insertion order and then
   * formatted as:
   * <ul>
   *   <li>{@code "Breed pending"} if no valid breeds are found,</li>
   *   <li>{@code "Breed"} for a single breed,</li>
   *   <li>{@code "Breed A, Breed B"} for up to two breeds, or</li>
   *   <li>{@code "Breed A, Breed B +N"} when more than two breeds exist.</li>
   * </ul>
   *
   * @param groups egg groups associated with a batch; may be {@code null} or empty.
   * @return formatted breed summary string.
   */
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