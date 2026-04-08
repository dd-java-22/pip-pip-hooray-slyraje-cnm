package edu.cnm.deepdive.pippiphooray.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.pippiphooray.controller.BatchAdapter.ViewHolder;
import edu.cnm.deepdive.pippiphooray.databinding.ItemBatchBinding;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithIncubator;
import java.time.LocalDate;
import java.util.Objects;

public class BatchAdapter extends ListAdapter<BatchWithIncubator, ViewHolder> {

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
    BatchWithIncubator batch = getItem(position);
    holder.bind(batch);
    holder.itemView.setOnClickListener(v -> listener.onBatchClick(batch.getId()));
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

    void bind(@NonNull BatchWithIncubator batch) {
      binding.batchTitle.setText(formatTitle(batch));
      binding.batchIncubator.setText(formatIncubator(batch));
      binding.batchViability.setText(formatViability(batch));
      binding.batchNextMilestone.setText(formatNextMilestone(batch));
      binding.batchNextMilestoneDate.setText(formatNextMilestoneDate(batch));
    }

    private String formatTitle(BatchWithIncubator batch) {
      // temporary until breed summary is available
      Integer batchNumber = batch.getBatchNumber();
      return (batchNumber != null)
          ? "Batch #" + batchNumber
          : "Batch";
    }

    private String formatIncubator(BatchWithIncubator batch) {
      String name = batch.getIncubatorName();
      return (name != null && !name.isBlank())
          ? "Incubator: " + name
          : "Incubator: Unassigned";
    }

    private String formatViability(BatchWithIncubator batch) {
      int eggsSet = batch.getNumEggsSet();
      if (eggsSet <= 0) {
        return "0/0 viable";
      }
      // temporary placeholder until egg status aggregation is added
      return eggsSet + "/" + eggsSet + " viable (100.0%)";
    }

    private String formatNextMilestone(BatchWithIncubator batch) {
      // temporary approximation based on dates currently available
      return "Next: Expected Hatch Day";
    }

    private String formatNextMilestoneDate(BatchWithIncubator batch) {
      LocalDate date = batch.getExpectedHatchDate();
      return (date != null) ? date.toString() : "";
    }
  }

  private static final DiffUtil.ItemCallback<BatchWithIncubator> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull BatchWithIncubator oldItem,
            @NonNull BatchWithIncubator newItem) {
          return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull BatchWithIncubator oldItem,
            @NonNull BatchWithIncubator newItem) {
          return Objects.equals(oldItem.getBatchNumber(), newItem.getBatchNumber())
              && Objects.equals(oldItem.getIncubatorName(), newItem.getIncubatorName())
              && Objects.equals(oldItem.getExpectedHatchDate(), newItem.getExpectedHatchDate())
              && oldItem.getNumEggsSet() == newItem.getNumEggsSet();
        }
      };
}