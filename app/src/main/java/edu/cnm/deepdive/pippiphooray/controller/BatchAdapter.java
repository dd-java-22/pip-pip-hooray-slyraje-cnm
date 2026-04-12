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
import java.util.Objects;

public class BatchAdapter extends ListAdapter<BatchCardSummary, BatchAdapter.ViewHolder> {

  private final OnBatchClickListener listener;

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

  @FunctionalInterface
  public interface OnBatchClickListener {
    void onBatchClick(long batchId);
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemBatchBinding binding;

    ViewHolder(@NonNull ItemBatchBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(@NonNull BatchCardSummary batch) {
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
      int eggsSet = batch.getNumEggsSet();
      if (eggsSet <= 0) {
        return "0/0 viable";
      }
      return eggsSet + "/" + eggsSet + " viable (100.0%)";
    }

    private String formatNextMilestone(BatchCardSummary batch) {
      return "Next: Expected Hatch Day";
    }

    private String formatNextMilestoneDate(BatchCardSummary batch) {
      LocalDate date = batch.getExpectedHatchDate();
      return (date != null) ? date.toString() : "";
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
              && Objects.equals(oldItem.getExpectedHatchDate(), newItem.getExpectedHatchDate())
              && oldItem.getNumEggsSet() == newItem.getNumEggsSet();
        }
      };
}