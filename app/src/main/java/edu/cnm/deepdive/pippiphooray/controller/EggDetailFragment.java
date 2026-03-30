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
import edu.cnm.deepdive.pippiphooray.databinding.FragmentEggDetailBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.BatchViewModel;

@AndroidEntryPoint
public class EggDetailFragment extends Fragment {

  private FragmentEggDetailBinding binding;
  private BatchViewModel batchViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentEggDetailBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    batchViewModel = new ViewModelProvider(requireActivity())
        .get(BatchViewModel.class);

    long eggId = EggDetailFragmentArgs
        .fromBundle(getArguments())
        .getEggId();

    if (eggId != 0L) {
      // TODO add EggRepository/EggViewModel access here:
      // - load the Egg and its candling observations by eggId,
      // - observe LiveData and bind to UI.
    } else {
      // TODO prepare UI for creating a new egg entry.
    }
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}