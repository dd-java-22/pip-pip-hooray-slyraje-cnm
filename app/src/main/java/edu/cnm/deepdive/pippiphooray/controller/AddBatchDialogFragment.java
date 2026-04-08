package edu.cnm.deepdive.pippiphooray.controller;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.DialogAddBatchBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.entity.Incubator;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;
import edu.cnm.deepdive.pippiphooray.viewmodel.EggGroupViewModel;
import edu.cnm.deepdive.pippiphooray.viewmodel.IncubatorViewModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@AndroidEntryPoint
public class AddBatchDialogFragment extends DialogFragment {

  private DialogAddBatchBinding binding;
  private BatchViewModel batchViewModel;
  private EggGroupViewModel eggGroupViewModel;
  private IncubatorViewModel incubatorViewModel;

  private final List<Incubator> incubators = new ArrayList<>();

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    binding = DialogAddBatchBinding.inflate(LayoutInflater.from(requireContext()));

    batchViewModel = new ViewModelProvider(requireActivity()).get(BatchViewModel.class);
    eggGroupViewModel = new ViewModelProvider(requireActivity()).get(EggGroupViewModel.class);
    incubatorViewModel = new ViewModelProvider(requireActivity()).get(IncubatorViewModel.class);

    setupIncubatorDropdown();
    setupDatePickers();

    AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.title_add_batch)
        .setView(binding.getRoot())
        .setNegativeButton(android.R.string.cancel, (dialogInterface, which) -> {})
        .setPositiveButton(R.string.action_save, null)
        .create();

    dialog.setOnShowListener(d ->
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> saveBatch()));

    return dialog;
  }

  private void setupIncubatorDropdown() {
    incubatorViewModel.getActiveIncubators().observe(this, incubatorList -> {
      incubators.clear();
      if (incubatorList != null) {
        incubators.addAll(incubatorList);
      }

      List<String> labels = new ArrayList<>();
      for (Incubator incubator : incubators) {
        labels.add(incubator.getName());
      }

      ArrayAdapter<String> adapter = new ArrayAdapter<>(
          requireContext(),
          android.R.layout.simple_list_item_1,
          labels
      );
      binding.incubator.setAdapter(adapter);
    });
  }

  private void setupDatePickers() {
    binding.dateSet.setOnClickListener(v ->
        showDatePicker(selectedDate -> {
          binding.dateSet.setText(selectedDate.toString());

          if (isBlank(binding.lockdownDate)) {
            binding.lockdownDate.setText(selectedDate.plusDays(18).toString());
          }
          if (isBlank(binding.expectedHatchDate)) {
            binding.expectedHatchDate.setText(selectedDate.plusDays(21).toString());
          }
        })
    );

    binding.lockdownDate.setOnClickListener(v ->
        showDatePicker(date -> binding.lockdownDate.setText(date.toString()))
    );

    binding.expectedHatchDate.setOnClickListener(v ->
        showDatePicker(date -> binding.expectedHatchDate.setText(date.toString()))
    );
  }

  private void showDatePicker(@NonNull Consumer<LocalDate> consumer) {
    MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
        .build();

    picker.addOnPositiveButtonClickListener(selection -> {
      LocalDate date = Instant.ofEpochMilli(selection)
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
      consumer.accept(date);
    });

    picker.show(getChildFragmentManager(), "date_picker");
  }

  private void saveBatch() {
    clearErrors();

    Integer batchNumber = parseInteger(textOf(binding.batchNumber));
    String breed = textOf(binding.breed);
    String incubatorName = textOf(binding.incubator);
    LocalDate dateSet = parseDate(textOf(binding.dateSet));
    LocalDate lockdownDate = parseDate(textOf(binding.lockdownDate));
    LocalDate expectedHatchDate = parseDate(textOf(binding.expectedHatchDate));
    Integer numEggsSet = parseInteger(textOf(binding.numEggsSet));
    String notes = textOf(binding.notes);

    boolean valid = true;

    if (batchNumber == null) {
      binding.batchNumberLayout.setError(getString(R.string.error_batch_number_required));
      valid = false;
    }

    if (breed.isBlank()) {
      binding.breedLayout.setError(getString(R.string.error_breed_required));
      valid = false;
    }

    Incubator selectedIncubator = findIncubatorByName(incubatorName);
    if (selectedIncubator == null) {
      binding.incubatorLayout.setError(getString(R.string.error_incubator_required));
      valid = false;
    }

    if (dateSet == null) {
      binding.dateSetLayout.setError(getString(R.string.error_date_required));
      valid = false;
    }

    if (lockdownDate == null) {
      binding.lockdownDateLayout.setError(getString(R.string.error_date_required));
      valid = false;
    }

    if (expectedHatchDate == null) {
      binding.expectedHatchDateLayout.setError(getString(R.string.error_date_required));
      valid = false;
    }

    if (numEggsSet == null || numEggsSet <= 0) {
      binding.numEggsSetLayout.setError(getString(R.string.error_egg_count_required));
      valid = false;
    }

    if (dateSet != null && lockdownDate != null && lockdownDate.isBefore(dateSet)) {
      binding.lockdownDateLayout.setError(getString(R.string.error_lockdown_before_set));
      valid = false;
    }

    if (lockdownDate != null && expectedHatchDate != null
        && expectedHatchDate.isBefore(lockdownDate)) {
      binding.expectedHatchDateLayout.setError(getString(R.string.error_hatch_before_lockdown));
      valid = false;
    }

    if (!valid || selectedIncubator == null || batchNumber == null || dateSet == null
        || lockdownDate == null || expectedHatchDate == null || numEggsSet == null) {
      return;
    }

    Batch batch = new Batch();
    batch.setBatchNumber(batchNumber);
    batch.setIncubatorId(selectedIncubator.getId());
    batch.setDateSet(dateSet);
    batch.setLockdownDate(lockdownDate);
    batch.setExpectedHatchDate(expectedHatchDate);
    batch.setNumEggsSet(numEggsSet);
    batch.setNotes(notes.isBlank() ? null : notes);
    batch.setBatchStatus("ACTIVE");

    batchViewModel.save(batch)
        .thenCompose(batchId -> {
          EggGroup eggGroup = new EggGroup();
          eggGroup.setBatchId(batchId);
          eggGroup.setBreed(breed);
          eggGroup.setInitialEggCount(numEggsSet);
          eggGroup.setNotes(null);
          return eggGroupViewModel.save(eggGroup);
        })
        .thenAccept(id -> {
          if (isAdded()) {
            requireActivity().runOnUiThread(this::dismiss);
          }
        })
        .exceptionally(throwable -> {
          if (isAdded()) {
            requireActivity().runOnUiThread(() ->
                binding.notesLayout.setError(getString(R.string.error_save_batch)));
          }
          return null;
        });
  }

  private void clearErrors() {
    binding.batchNumberLayout.setError(null);
    binding.incubatorLayout.setError(null);
    binding.breedLayout.setError(null);
    binding.dateSetLayout.setError(null);
    binding.lockdownDateLayout.setError(null);
    binding.expectedHatchDateLayout.setError(null);
    binding.numEggsSetLayout.setError(null);
    binding.notesLayout.setError(null);
  }

  @Nullable
  private Integer parseInteger(@NonNull String value) {
    if (value.isBlank()) {
      return null;
    }
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Nullable
  private LocalDate parseDate(@NonNull String value) {
    if (value.isBlank()) {
      return null;
    }
    try {
      return LocalDate.parse(value);
    } catch (DateTimeParseException e) {
      return null;
    }
  }

  @NonNull
  private String textOf(@NonNull TextView view) {
    return view.getText() == null ? "" : view.getText().toString().trim();
  }

  private boolean isBlank(@NonNull TextView view) {
    return textOf(view).isBlank();
  }

  @Nullable
  private Incubator findIncubatorByName(@NonNull String name) {
    for (Incubator incubator : incubators) {
      if (name.equals(incubator.getName())) {
        return incubator;
      }
    }
    return null;
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}