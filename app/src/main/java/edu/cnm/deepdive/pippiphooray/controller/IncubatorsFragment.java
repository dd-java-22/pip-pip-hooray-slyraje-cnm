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
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentIncubatorsBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.IncubatorViewModel;

/**
 * Fragment that displays the list of active incubators.
 *
 * <p>This screen allows the user to view existing incubators and open the dialog
 * for creating or editing an incubator.
 */
@AndroidEntryPoint
public class IncubatorsFragment extends Fragment {

  private FragmentIncubatorsBinding binding;
  private IncubatorViewModel incubatorViewModel;
  private IncubatorAdapter adapter;

  /**
   * Inflates the incubators layout, initializes the recycler view,
   * and configures the incubator adapter.
   *
   * @param inflater layout inflater.
   * @param container optional parent container.
   * @param savedInstanceState saved instance state, if any.
   * @return root view for this fragment.
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentIncubatorsBinding.inflate(inflater, container, false);
    adapter = new IncubatorAdapter((incubator) ->
        AddIncubatorDialogFragment.newInstance(incubator.getId())
            .show(getChildFragmentManager(), null)
    );
    binding.incubatorList.setLayoutManager(new LinearLayoutManager(requireContext()));
    binding.incubatorList.setAdapter(adapter);
    return binding.getRoot();
  }

  /**
   * Completes fragment setup by connecting the ViewModel and UI event handlers.
   *
   * @param view root view returned from {@link #onCreateView}.
   * @param savedInstanceState saved instance state, if any.
   */
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

  /**
   * Clears the view binding reference when the fragment view is destroyed.
   */
  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}