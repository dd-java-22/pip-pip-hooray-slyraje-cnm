package edu.cnm.deepdive.pippiphooray.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.pippiphooray.databinding.ItemBatchBinding;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchCardSummary;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * RecyclerView adapter for displaying batch card summaries in a list.
 */
public class BatchAdapter extends ListAdapter<BatchCardSummary, BatchAdapter.ViewHolder> {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("MMM d, uuuu");
  private final OnBatchClickListener listener;

  /**
   * Constructs a BatchAdapter with the specified click listener.
   *
   * @param listener the listener to handle batch item clicks
   */
  public BatchAdapter(OnBatchClickListener listener) {
    super(DIFF_CALLBACK);
    this.listener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemBatchBinding binding = ItemBatchBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    BatchCardSummary batch = getItem(position);
    holder.bind(batch);
    holder.itemView.setOnClickListener(v -> listener.onBatchClick(batch.getBatchId()));
  }

  /**
   * Functional interface for handling batch item click events.
   */
  @FunctionalInterface
  public interface OnBatchClickListener {

    /**
     * Called when a batch item is clicked.
     *
     * @param batchId the ID of the clicked batch
     */
    void onBatchClick(long batchId);
  }

  /**
   * ViewHolder for displaying a batch card summary.
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemBatchBinding binding;

    /**
     * Constructs a ViewHolder with the specified binding.
     *
     * @param binding the view binding for the batch item
     */
    public ViewHolder(@NonNull ItemBatchBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * Binds a batch card summary to this ViewHolder's views.
     *
     * @param batch the batch card summary to display
     */
    public void bind(@NonNull BatchCardSummary batch) {
      binding.batchTitle.setText(formatTitle(batch));
      binding.batchBreedSummary.setText(formatBreedSummary(batch));
      binding.batchIncubator.setText(formatIncubator(batch));
      binding.batchViability.setText(formatViability(batch));
      binding.batchNextMilestone.setText(formatNextMilestone(batch));
      binding.batchNextMilestoneDate.setText(formatNextMilestoneDate(batch));
    }

    private String formatTitle(BatchCardSummary batch) {
      Integer batchNumber = batch.getBatchNumber();
      return (batchNumber != null)
          ? "Batch #" + batchNumber
          : "Batch";
    }

    private String formatBreedSummary(BatchCardSummary batch) {
      String summary = batch.getBreedSummary();
      return (summary != null && !summary.isBlank())
          ? summary
          : "Breed pending";
    }

    private String formatIncubator(BatchCardSummary batch) {
      String name = batch.getIncubatorName();
      return (name != null && !name.isBlank())
          ? "Incubator: " + name
          : "Incubator: Unassigned";
    }

    private String formatViability(BatchCardSummary batch) {
      return String.format(
          "%d/%d viable (%.1f%%)",
          batch.getViableCount(),
          batch.getTotalEggCount(),
          batch.getViabilityRate() * 100.0
      );
    }

    private String formatNextMilestone(BatchCardSummary batch) {
      String label = batch.getNextMilestoneLabel();
      return (label != null && !label.isBlank())
          ? label
          : "Next milestone unavailable";
    }

    private String formatNextMilestoneDate(BatchCardSummary batch) {
      LocalDate date = batch.getNextMilestoneDate();
      return (date != null) ? DATE_FORMATTER.format(date) : "";
    }
  }

  private static final DiffUtil.ItemCallback<BatchCardSummary> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull BatchCardSummary oldItem,
            @NonNull BatchCardSummary newItem) {
          return oldItem.getBatchId() == newItem.getBatchId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BatchCardSummary oldItem,
            @NonNull BatchCardSummary newItem) {
          return Objects.equals(oldItem.getBatchNumber(), newItem.getBatchNumber())
              && Objects.equals(oldItem.getIncubatorName(), newItem.getIncubatorName())
              && Objects.equals(oldItem.getBreedSummary(), newItem.getBreedSummary())
              && oldItem.getViableCount() == newItem.getViableCount()
              && oldItem.getTotalEggCount() == newItem.getTotalEggCount()
              && Double.compare(oldItem.getViabilityRate(), newItem.getViabilityRate()) == 0
              && Objects.equals(oldItem.getExpectedHatchDate(), newItem.getExpectedHatchDate())
              && Objects.equals(oldItem.getNextMilestoneLabel(), newItem.getNextMilestoneLabel())
              && Objects.equals(oldItem.getNextMilestoneDate(), newItem.getNextMilestoneDate());
        }
      };
}