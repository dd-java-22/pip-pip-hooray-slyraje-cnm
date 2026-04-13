package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentBatchDetailBinding;
import edu.cnm.deepdive.pippiphooray.model.entity.Batch;
import edu.cnm.deepdive.pippiphooray.model.entity.EggGroup;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchViabilitySummary;
import edu.cnm.deepdive.pippiphooray.model.pojo.BatchWithEggGroups;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AndroidEntryPoint
public class BatchDetailFragment extends Fragment {

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("MMM d, uuuu");

  private FragmentBatchDetailBinding binding;
  private BatchViewModel batchViewModel;
  private long batchId;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    binding = FragmentBatchDetailBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(
      @NonNull View view,
      @Nullable Bundle savedInstanceState
  ) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity()).get(BatchViewModel.class);
    batchId = BatchDetailFragmentArgs.fromBundle(requireArguments()).getBatchId();

    batchViewModel.setSelectedBatchId(batchId);
    batchViewModel.getSelectedBatchViability().observe(getViewLifecycleOwner(), summary -> {
      if (summary != null) {
        bindViability(summary);
      }
    });

    batchViewModel.getSelectedBatchWithGroups().observe(getViewLifecycleOwner(), batchWithGroups -> {
      if (batchWithGroups != null) {
        bindBatchWithGroups(batchWithGroups);
      }
    });

    installMenu();

    batchViewModel.get(batchId).observe(getViewLifecycleOwner(), batch -> {
      if (batch != null) {
        bindBatch(batch);
      }
    });

    binding.bulkCandlingButton.setOnClickListener(v ->
        BulkCandlingDialogFragment.newInstance(batchId)
            .show(getChildFragmentManager(), BulkCandlingDialogFragment.class.getSimpleName())
    );

//    binding.perEggCandlingButton.setOnClickListener(v ->
//        Toast.makeText(
//            requireContext(),
//            R.string.message_per_egg_candling_placeholder,
//            Toast.LENGTH_SHORT
//        ).show()
//    );
  }

  private void installMenu() {
    MenuHost menuHost = requireActivity();
    menuHost.addMenuProvider(new MenuProvider() {
      @Override
      public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_batch_detail, menu);
      }

      @Override
      public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_edit_batch) {
          Toast.makeText(
              requireContext(),
              R.string.message_edit_batch_placeholder,
              Toast.LENGTH_SHORT
          ).show();
          return true;
        }
        return false;
      }
    }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
  }

  private void bindBatch(@NonNull Batch batch) {
    if (batch.getBatchNumber() != null) {
      binding.batchTitle.setText(
          getString(R.string.format_batch_title, batch.getBatchNumber()));
    } else {
      binding.batchTitle.setText(R.string.label_batch_detail);
    }

    binding.batchNumberValue.setText(
        (batch.getBatchNumber() != null)
            ? String.valueOf(batch.getBatchNumber())
            : getString(R.string.value_unknown));

    binding.incubatorValue.setText(
        (batch.getIncubatorId() != null)
            ? getString(R.string.value_incubator_selected)
            : getString(R.string.value_unassigned));

    binding.eggsSetValue.setText(String.valueOf(batch.getNumEggsSet()));

    binding.dateSetValue.setText(formatDate(batch.getDateSet()));
    binding.lockdownDateValue.setText(formatDate(getLockdownDate(batch)));
    binding.expectedHatchDateValue.setText(formatDate(batch.getExpectedHatchDate()));

    LocalDate dateSet = batch.getDateSet();
    binding.day7Date.setText(formatDate(offsetDays(dateSet, 7)));
    binding.day14Date.setText(formatDate(offsetDays(dateSet, 14)));
    binding.day18Date.setText(formatDate(getLockdownDate(batch)));
    binding.hatchDate.setText(formatDate(batch.getExpectedHatchDate()));
  }

  private void bindBatchWithGroups(@NonNull BatchWithEggGroups batchWithGroups) {
    binding.breedsValue.setText(formatBreedSummary(batchWithGroups.getGroups()));
  }

  @Nullable
  private LocalDate getLockdownDate(@NonNull Batch batch) {
    if (batch.getLockdownDate() != null) {
      return batch.getLockdownDate();
    }
    if (batch.getExpectedHatchDate() != null) {
      return batch.getExpectedHatchDate().minusDays(3);
    }
    return offsetDays(batch.getDateSet(), 18);
  }

  @Nullable
  private LocalDate offsetDays(@Nullable LocalDate date, long days) {
    return (date != null) ? date.plusDays(days) : null;
  }

  @NonNull
  private String formatDate(@Nullable LocalDate date) {
    return (date != null)
        ? DATE_FORMATTER.format(date)
        : getString(R.string.value_unknown);
  }

  @NonNull
  private String formatBreedSummary(@Nullable List<EggGroup> groups) {
    if (groups == null || groups.isEmpty()) {
      return getString(R.string.value_unknown);
    }

    StringBuilder summary = new StringBuilder();
    for (int i = 0; i < groups.size(); i++) {
      EggGroup group = groups.get(i);
      if (group.getBreed() == null || group.getBreed().isBlank()) {
        continue;
      }
      if (summary.length() > 0) {
        summary.append(", ");
      }
      summary.append(group.getBreed());
    }

    return (summary.length() > 0)
        ? summary.toString()
        : getString(R.string.value_unknown);
  }

  private void bindViability(@NonNull BatchViabilitySummary summary) {
    int total = summary.getTotalCount();
    int viable = summary.getViableCount();

    if (total > 0) {
      binding.viableValue.setText(
          getString(R.string.value_viability_dynamic, viable, total, summary.getViableRate() * 100.0)
      );
    } else {
      binding.viableValue.setText(R.string.value_viability_zero);
    }
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}