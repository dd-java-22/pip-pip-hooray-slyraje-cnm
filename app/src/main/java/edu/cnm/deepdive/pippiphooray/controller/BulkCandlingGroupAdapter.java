package edu.cnm.deepdive.pippiphooray.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.ItemBulkCandlingGroupBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Adapter used to display egg groups and collect bulk candling input values.
 *
 * <p>Each row shows an egg group and lets the user enter the number of viable
 * eggs observed for that group.
 */
public class BulkCandlingGroupAdapter
    extends ListAdapter<EggGroup, BulkCandlingGroupAdapter.ViewHolder> {

  /**
   * Listener notified when an input field should receive focus and show the keyboard.
   */
  public interface OnInputFocusListener {

    /**
     * Requests focus handling for the provided input field.
     *
     * @param editText input field that should receive focus.
     */
    void onInputFocused(@NonNull EditText editText);
  }

  private final Map<Long, String> enteredValues = new HashMap<>();
  private final OnInputFocusListener focusListener;

  /**
   * Creates a new adapter instance.
   *
   * @param focusListener listener used when an input field gains focus.
   */
  public BulkCandlingGroupAdapter(@NonNull OnInputFocusListener focusListener) {
    super(DIFF_CALLBACK);
    this.focusListener = focusListener;
  }

  /**
   * Creates a new view holder for an egg-group row.
   *
   * @param parent parent view group.
   * @param viewType view type identifier.
   * @return new view holder instance.
   */
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemBulkCandlingGroupBinding binding = ItemBulkCandlingGroupBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  /**
   * Binds an egg-group item to the specified view holder.
   *
   * @param holder target view holder.
   * @param position adapter position.
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  /**
   * Returns whether the user has entered at least one non-blank viable-count value.
   *
   * @return {@code true} if any value has been entered; otherwise {@code false}.
   */
  public boolean hasAnyEnteredValue() {
    for (EggGroup group : getCurrentList()) {
      String raw = enteredValues.get(group.getId());
      if (raw != null && !raw.isBlank()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Collects valid entered values as viable counts keyed by egg-group ID.
   *
   * <p>Values are clamped to the range from 0 to the group's initial egg count.
   *
   * @return map of egg-group IDs to validated viable counts.
   */
  @NonNull
  public Map<Long, Integer> collectValidCounts() {
    Map<Long, Integer> counts = new HashMap<>();
    for (EggGroup group : getCurrentList()) {
      String raw = enteredValues.get(group.getId());
      if (raw == null || raw.isBlank()) {
        continue;
      }

      int value = Integer.parseInt(raw);
      int clamped = Math.max(0, Math.min(value, group.getInitialEggCount()));
      counts.put(group.getId(), clamped);
    }
    return counts;
  }

  /**
   * Validates all entered viable-count values.
   *
   * @return {@code true} if all entered values are numeric and within range;
   *     otherwise {@code false}.
   */
  public boolean validateInputs() {
    boolean valid = true;
    for (int i = 0; i < getCurrentList().size(); i++) {
      EggGroup group = getCurrentList().get(i);
      String raw = enteredValues.get(group.getId());
      if (raw == null || raw.isBlank()) {
        continue;
      }
      try {
        int value = Integer.parseInt(raw);
        if (value < 0 || value > group.getInitialEggCount()) {
          valid = false;
        }
      } catch (NumberFormatException e) {
        valid = false;
      }
    }
    return valid;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemBulkCandlingGroupBinding binding;
    private TextWatcher watcher;

    ViewHolder(@NonNull ItemBulkCandlingGroupBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(@NonNull EggGroup group) {
      binding.breedName.setText(group.getBreed());
      binding.groupEggCount.setText(
          itemView.getContext().getString(
              R.string.label_group_egg_count, group.getInitialEggCount())
      );

      if (watcher != null) {
        binding.viableCountInput.removeTextChangedListener(watcher);
      }

      String currentValue = enteredValues.get(group.getId());
      binding.viableCountInput.setText(currentValue != null ? currentValue : "");
      binding.viableCountInput.setHint(String.valueOf(group.getInitialEggCount()));
      binding.viableCountInput.setError(null);

      watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
          enteredValues.put(group.getId(), s.toString().trim());
        }
      };
      binding.viableCountInput.addTextChangedListener(watcher);

      binding.viableCountInput.setOnClickListener(
          v -> focusListener.onInputFocused(binding.viableCountInput));

      binding.viableCountInput.setOnFocusChangeListener((v, hasFocus) -> {
        if (hasFocus) {
          focusListener.onInputFocused(binding.viableCountInput);
        }
      });
    }
  }

  private static final DiffUtil.ItemCallback<EggGroup> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull EggGroup oldItem, @NonNull EggGroup newItem) {
          return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull EggGroup oldItem, @NonNull EggGroup newItem) {
          return oldItem.getBatchId() == newItem.getBatchId()
              && Objects.equals(oldItem.getBreed(), newItem.getBreed())
              && oldItem.getInitialEggCount() == newItem.getInitialEggCount()
              && Objects.equals(oldItem.getNotes(), newItem.getNotes());
        }
      };
}