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
import java.util.Map;

/**
 * Dialog fragment that applies bulk candling results to all egg groups in a batch.
 *
 * <p>The user can enter viable egg counts for each group and save the results
 * in one operation.
 */
@AndroidEntryPoint
public class BulkCandlingDialogFragment extends DialogFragment {

  private DialogBulkCandlingBinding binding;
  private BatchViewModel batchViewModel;
  private BulkCandlingGroupAdapter adapter;
  private long batchId;

  /**
   * Creates a new dialog instance for the specified batch.
   *
   * @param batchId ID of the batch to update.
   * @return configured dialog fragment instance.
   */
  public static BulkCandlingDialogFragment newInstance(long batchId) {
    BulkCandlingDialogFragment fragment = new BulkCandlingDialogFragment();
    Bundle args = new Bundle();
    args.putLong("batchId", batchId);
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * Inflates the bulk-candling dialog layout.
   *
   * @param inflater layout inflater.
   * @param container optional parent container.
   * @param savedInstanceState saved instance state, if any.
   * @return root view for the dialog.
   */
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

  /**
   * Completes initialization of the dialog UI, observers, and click handlers.
   *
   * @param view root view returned from {@link #onCreateView}.
   * @param savedInstanceState saved instance state, if any.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity()).get(BatchViewModel.class);
    batchId = requireArguments().getLong("batchId");

    adapter = new BulkCandlingGroupAdapter(this::focusAndShowKeyboard);
    binding.groupList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.groupList.setAdapter(adapter);

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

      if (!adapter.hasAnyEnteredValue()) {
        dismiss();
        return;
      }

      Map<Long, Integer> viableCountsByGroupId = adapter.collectValidCounts();

      binding.progressBar.setVisibility(View.VISIBLE);
      binding.saveButton.setEnabled(false);
      binding.cancelButton.setEnabled(false);

      batchViewModel.applyBulkCandling(viableCountsByGroupId)
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

  /**
   * Adjusts the dialog window size when the dialog becomes visible.
   */
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

  /**
   * Clears the view binding reference when the dialog view is destroyed.
   */
  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}