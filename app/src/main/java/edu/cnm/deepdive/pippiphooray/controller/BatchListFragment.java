package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentBatchListBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;

@AndroidEntryPoint
public class BatchListFragment extends Fragment {

  private FragmentBatchListBinding binding;
  private BatchViewModel batchViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentBatchListBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity())
        .get(BatchViewModel.class);

    batchViewModel.getAll().observe(getViewLifecycleOwner(), (batches) -> {
      // TODO: submit to RecyclerView adapter, update empty state, etc.
    });

    binding.pickDate.setOnClickListener((v) -> {
      DatePickerFragment newFragment = new DatePickerFragment();
      newFragment.show(getParentFragmentManager(), "datePicker");
    });

    binding.addBatch.setOnClickListener((v) -> {
      NavDirections action =
          BatchListFragmentDirections.navigateToBatchDetailFragment(0L);
      Navigation.findNavController(v).navigate(action);
    });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}