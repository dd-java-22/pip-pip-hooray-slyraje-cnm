package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentIncubatorsBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.IncubatorViewModel;

@AndroidEntryPoint
public class IncubatorsFragment extends Fragment {

  private FragmentIncubatorsBinding binding;
  private IncubatorViewModel incubatorViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentIncubatorsBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    incubatorViewModel = new ViewModelProvider(requireActivity())
        .get(IncubatorViewModel.class);

    incubatorViewModel.getActiveIncubators()
        .observe(getViewLifecycleOwner(), (incubators) -> {
          // TODO: update RecyclerView/list, or at least stub some display.
          // e.g., show count or names in a TextView for now.
        });

    // TODO: set up click handlers to add/edit incubators, calling incubatorViewModel.save(...)
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}