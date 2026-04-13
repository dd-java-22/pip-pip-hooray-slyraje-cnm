package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentBatchListBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;
/**
 * Manages the list of incubation batches shown on the home screen.
 */
@AndroidEntryPoint
public class BatchListFragment extends Fragment {

  private FragmentBatchListBinding binding;
  private BatchViewModel batchViewModel;
  private BatchAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    binding = FragmentBatchListBinding.inflate(inflater, container, false);

    adapter = new BatchAdapter(batchId -> {
      NavDirections action =
          BatchListFragmentDirections.navigateToBatchDetailFragment(batchId);
      Navigation.findNavController(binding.getRoot()).navigate(action);
    });

    binding.batchList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.batchList.setAdapter(adapter);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity()).get(BatchViewModel.class);

    batchViewModel.getBatchCardSummaries().observe(
        getViewLifecycleOwner(),
        summaries -> adapter.submitList(summaries)
    );

    binding.addBatch.setOnClickListener(v ->
        new AddBatchDialogFragment().show(getChildFragmentManager(), null)
    );
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}