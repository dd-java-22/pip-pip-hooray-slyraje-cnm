package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.DialogBulkCandlingBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;
import java.util.ArrayList;
import java.util.List;

@AndroidEntryPoint
public class BulkCandlingDialogFragment extends DialogFragment {

  private DialogBulkCandlingBinding binding;
  private BatchViewModel batchViewModel;
  private BulkCandlingGroupAdapter adapter;
  private long batchId;

  public static BulkCandlingDialogFragment newInstance(long batchId) {
    BulkCandlingDialogFragment fragment = new BulkCandlingDialogFragment();
    Bundle args = new Bundle();
    args.putLong("batchId", batchId);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    binding = DialogBulkCandlingBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity()).get(BatchViewModel.class);
    batchId = requireArguments().getLong("batchId");

    adapter = new BulkCandlingGroupAdapter(this::focusAndShowKeyboard);
    binding.groupList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.groupList.setAdapter(adapter);

    batchViewModel.setSelectedBatchId(batchId);
    batchViewModel.getSelectedBatchWithGroups().observe(getViewLifecycleOwner(), batchWithGroups -> {
      List<EggGroup> groups = (batchWithGroups != null && batchWithGroups.getGroups() != null)
          ? batchWithGroups.getGroups()
          : new ArrayList<>();
      adapter.submitList(groups);
      binding.emptyMessage.setVisibility(groups.isEmpty() ? View.VISIBLE : View.GONE);
    });

    binding.cancelButton.setOnClickListener(v -> dismiss());

    binding.saveButton.setOnClickListener(v -> {
      if (!adapter.validateInputs()) {
        Toast.makeText(
            requireContext(),
            R.string.message_bulk_candling_invalid_input,
            Toast.LENGTH_SHORT
        ).show();
        return;
      }

      binding.progressBar.setVisibility(View.VISIBLE);
      binding.saveButton.setEnabled(false);
      binding.cancelButton.setEnabled(false);

      batchViewModel.applyBulkCandling(adapter.collectValidCounts())
          .whenComplete((result, throwable) -> requireActivity().runOnUiThread(() -> {
            binding.progressBar.setVisibility(View.GONE);
            binding.saveButton.setEnabled(true);
            binding.cancelButton.setEnabled(true);

            if (throwable == null) {
              Toast.makeText(
                  requireContext(),
                  R.string.message_bulk_candling_saved,
                  Toast.LENGTH_SHORT
              ).show();
              dismiss();
            } else {
              Toast.makeText(
                  requireContext(),
                  R.string.message_bulk_candling_save_failed,
                  Toast.LENGTH_SHORT
              ).show();
            }
          }));
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    if (getDialog() != null && getDialog().getWindow() != null) {
      getDialog().getWindow().setLayout(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
      );
    }
  }

  private void focusAndShowKeyboard(@NonNull EditText editText) {
    editText.requestFocus();
    editText.post(() -> {
      WindowInsetsControllerCompat controller =
          ViewCompat.getWindowInsetsController(editText);
      if (controller != null) {
        controller.show(WindowInsetsCompat.Type.ime());
      }
    });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}