package edu.cnm.deepdive.pippiphooray.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.pippiphooray.databinding.ItemIncubatorBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import java.util.Locale;

/**
 * RecyclerView adapter for displaying incubator items in a list.
 */
public class IncubatorAdapter extends ListAdapter<Incubator, IncubatorAdapter.ViewHolder> {

  private final OnIncubatorClickListener listener;

  /**
   * Constructs an IncubatorAdapter with the specified click listener.
   *
   * @param listener the listener to handle incubator item clicks
   */
  public IncubatorAdapter(OnIncubatorClickListener listener) {
    super(DIFF_CALLBACK);
    this.listener = listener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemIncubatorBinding binding = ItemIncubatorBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Incubator incubator = getItem(position);
    holder.bind(incubator);
    holder.itemView.setOnClickListener((v) -> listener.onIncubatorClick(incubator));
  }

  /**
   * Functional interface for handling incubator item click events.
   */
  @FunctionalInterface
  public interface OnIncubatorClickListener {

    /**
     * Called when an incubator item is clicked.
     *
     * @param incubator the incubator that was clicked
     */
    void onIncubatorClick(Incubator incubator);
  }

  /**
   * ViewHolder for displaying an incubator item.
   */
  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemIncubatorBinding binding;

    /**
     * Constructs a ViewHolder with the specified binding.
     *
     * @param binding the view binding for the incubator item
     */
    public ViewHolder(@NonNull ItemIncubatorBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    /**
     * Binds an incubator to this ViewHolder's views.
     *
     * @param incubator the incubator to display
     */
    public void bind(@NonNull Incubator incubator) {
      binding.incubatorName.setText(incubator.getName());
      binding.incubatorModel.setText(incubator.getModel());
      binding.incubatorTemp.setText(
          String.format(Locale.getDefault(), "Temp: %.1f\u00B0F", incubator.getTargetTemperature()));
      binding.incubatorHumidity.setText(
          String.format(Locale.getDefault(), "Humidity: %.0f%%", incubator.getTargetHumidity()));
    }
  }

  private static final DiffUtil.ItemCallback<Incubator> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<>() {

        @Override
        public boolean areItemsTheSame(@NonNull Incubator oldItem, @NonNull Incubator newItem) {
          return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Incubator oldItem, @NonNull Incubator newItem) {
          return oldItem.getName().equals(newItem.getName())
              && oldItem.getModel().equals(newItem.getModel())
              && oldItem.getTargetTemperature() == newItem.getTargetTemperature()
              && oldItem.getTargetHumidity() == newItem.getTargetHumidity();
        }
      };

}