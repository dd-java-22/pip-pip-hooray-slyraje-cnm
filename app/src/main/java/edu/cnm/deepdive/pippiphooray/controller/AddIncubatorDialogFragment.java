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

@AndroidEntryPoint
public class AddIncubatorDialogFragment extends DialogFragment {

  static final String INCUBATOR_ID_KEY = "incubator_id";

  public static AddIncubatorDialogFragment newInstance(long incubatorId) {
    AddIncubatorDialogFragment fragment = new AddIncubatorDialogFragment();
    Bundle args = new Bundle();
    args.putLong(INCUBATOR_ID_KEY, incubatorId);
    fragment.setArguments(args);
    return fragment;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    DialogAddIncubatorBinding binding = DialogAddIncubatorBinding.inflate(
        LayoutInflater.from(requireContext()));

    return new MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.title_add_incubator)
        .setView(binding.getRoot())
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(R.string.action_save, (dialog, which) -> {
          String name = getText(binding.incubatorName);
          if (name.isEmpty()) {
            return;
          }
          Incubator incubator = new Incubator();
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
        })
        .create();
  }

  @NonNull
  private String getText(@NonNull View view) {
    return ((android.widget.EditText) view).getText().toString().trim();
  }

}
