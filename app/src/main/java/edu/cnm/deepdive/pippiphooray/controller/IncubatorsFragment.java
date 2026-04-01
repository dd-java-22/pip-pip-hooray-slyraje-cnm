package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Toast;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentIncubatorsBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.IncubatorViewModel;

@AndroidEntryPoint
public class IncubatorsFragment extends Fragment {

  private FragmentIncubatorsBinding binding;
  private IncubatorViewModel incubatorViewModel;
  private IncubatorAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentIncubatorsBinding.inflate(inflater, container, false);
    adapter = new IncubatorAdapter((incubator) ->
        Toast.makeText(requireContext(), "Edit: " + incubator.getName(), Toast.LENGTH_SHORT).show()
    );
    binding.incubatorList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.incubatorList.setAdapter(adapter);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    incubatorViewModel = new ViewModelProvider(requireActivity())
        .get(IncubatorViewModel.class);

    incubatorViewModel.getActiveIncubators()
        .observe(getViewLifecycleOwner(), adapter::submitList);

    binding.addIncubator.setOnClickListener((v) -> {
      new AddIncubatorDialogFragment().show(getChildFragmentManager(), null);
    });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}
