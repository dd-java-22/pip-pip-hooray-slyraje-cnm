package edu.cnm.deepdive.pippiphooray.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.DialogAddIncubatorBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import edu.cnm.deepdive.pippiphooray.viewmodel.IncubatorViewModel;
import java.util.List;
import java.util.Locale;

/**
 * Dialog fragment used to create or edit an incubator record.
 *
 * <p>The dialog supports both adding a new incubator and editing an existing one,
 * including optional target temperature and humidity values.
 */
@AndroidEntryPoint
public class AddIncubatorDialogFragment extends DialogFragment {

  /** Argument key used to pass the incubator ID into the dialog. */
  static final String INCUBATOR_ID_KEY = "incubator_id";

  /**
   * Creates a new dialog fragment instance for editing an existing incubator.
   *
   * @param incubatorId ID of the incubator to edit.
   * @return configured dialog fragment instance.
   */
  public static AddIncubatorDialogFragment newInstance(long incubatorId) {
    AddIncubatorDialogFragment fragment = new AddIncubatorDialogFragment();
    Bundle args = new Bundle();
    args.putLong(INCUBATOR_ID_KEY, incubatorId);
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Creates and configures the dialog for adding or editing an incubator.
   *
   * @param savedInstanceState saved instance state, if any.
   * @return configured dialog instance.
   */
  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    DialogAddIncubatorBinding binding = DialogAddIncubatorBinding.inflate(
        LayoutInflater.from(requireContext()));

    long incubatorId = getArguments() != null
        ? getArguments().getLong(INCUBATOR_ID_KEY, 0) : 0;
    boolean editing = incubatorId > 0;

    if (editing) {
      Incubator existing = findIncubatorById(incubatorId);
      if (existing != null) {
        binding.incubatorName.setText(existing.getName());
        binding.incubatorModel.setText(existing.getModel());
        if (existing.getTargetTemperature() != 0) {
          binding.targetTemperature.setText(
              String.format(Locale.getDefault(), "%.1f", existing.getTargetTemperature()));
        }
        if (existing.getTargetHumidity() != 0) {
          binding.targetHumidity.setText(
              String.format(Locale.getDefault(), "%.0f", existing.getTargetHumidity()));
        }
      }
    }

    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext())
        .setTitle(editing ? R.string.title_edit_incubator : R.string.title_add_incubator)
        .setView(binding.getRoot())
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(R.string.action_save, (dialog, which) -> {
          String name = getText(binding.incubatorName);
          if (name.isEmpty()) {
            return;
          }
          Incubator incubator = new Incubator();
          if (editing) {
            incubator.setId(incubatorId);
          }
          incubator.setName(name);
          incubator.setModel(getText(binding.incubatorModel));
          incubator.setActive(true);

          String tempText = getText(binding.targetTemperature);
          if (!tempText.isEmpty()) {
            try {
              incubator.setTargetTemperature(Double.parseDouble(tempText));
            } catch (NumberFormatException ignored) {
            }
          }

          String humidityText = getText(binding.targetHumidity);
          if (!humidityText.isEmpty()) {
            try {
              incubator.setTargetHumidity(Double.parseDouble(humidityText));
            } catch (NumberFormatException ignored) {
            }
          }

          IncubatorViewModel viewModel = new ViewModelProvider(requireActivity())
              .get(IncubatorViewModel.class);
          viewModel.save(incubator);
        });

    if (editing) {
      builder.setNeutralButton(R.string.action_deactivate, (dialog, which) -> {
        Incubator existing = findIncubatorById(incubatorId);
        if (existing != null) {
          IncubatorViewModel viewModel = new ViewModelProvider(requireActivity())
              .get(IncubatorViewModel.class);
          viewModel.deactivate(existing);
        }
      });
    }

    return builder.create();
  }

  @NonNull
  private String getText(@NonNull View view) {
    return ((android.widget.EditText) view).getText().toString().trim();
  }

  @Nullable
  private Incubator findIncubatorById(long id) {
    IncubatorViewModel viewModel = new ViewModelProvider(requireActivity())
        .get(IncubatorViewModel.class);
    List<Incubator> incubators = viewModel.getActiveIncubators().getValue();
    if (incubators != null) {
      for (Incubator incubator : incubators) {
        if (incubator.getId() == id) {
          return incubator;
        }
      }
    }
    return null;
  }

}