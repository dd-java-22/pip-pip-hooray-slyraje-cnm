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

public class BulkCandlingGroupAdapter
    extends ListAdapter<EggGroup, BulkCandlingGroupAdapter.ViewHolder> {

  public interface OnInputFocusListener {
    void onInputFocused(@NonNull EditText editText);
  }

  private final Map<Long, String> enteredValues = new HashMap<>();
  private final OnInputFocusListener focusListener;

  public BulkCandlingGroupAdapter(@NonNull OnInputFocusListener focusListener) {
    super(DIFF_CALLBACK);
    this.focusListener = focusListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemBulkCandlingGroupBinding binding = ItemBulkCandlingGroupBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  public boolean hasAnyEnteredValue() {
    for (EggGroup group : getCurrentList()) {
      String raw = enteredValues.get(group.getId());
      if (raw != null && !raw.isBlank()) {
        return true;
      }
    }
    return false;
  }

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