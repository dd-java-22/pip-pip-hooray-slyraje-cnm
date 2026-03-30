package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentSettingsBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.SignInViewModel;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

  private FragmentSettingsBinding binding;
  private SignInViewModel signInViewModel;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    signInViewModel = new ViewModelProvider(requireActivity())
        .get(SignInViewModel.class);

    binding.signOutButton.setOnClickListener((v) -> {
      binding.signOutButton.setEnabled(false);
      signInViewModel.signOut();
    });

    signInViewModel
        .getCredential()
        .observe(getViewLifecycleOwner(), (credential) -> {
          if (credential == null) {
            NavOptions options = new NavOptions.Builder()
                .setPopUpTo(R.id.sign_in_fragment, true)
                .build();
            Navigation.findNavController(binding.getRoot())
                .navigate(R.id.sign_in_fragment, null, options);
          } else {
            binding.signOutButton.setEnabled(true);
          }
        });

    signInViewModel
        .getThrowable()
        .observe(getViewLifecycleOwner(), (throwable) -> {
          if (throwable != null) {
            binding.signOutButton.setEnabled(true);
            // TODO show a Snackbar or error text.
          }
        });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}