package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.databinding.FragmentSignInBinding;
import edu.cnm.deepdive.pippiphooray.viewmodel.SignInViewModel;

/**
 * Fragment that handles user authentication with Google Sign-In.
 *
 * <p>This screen initiates interactive sign-in, observes authentication state,
 * and attempts silent sign-in when possible.
 */
@AndroidEntryPoint
public class SignInFragment extends Fragment {

  private FragmentSignInBinding binding;
  private SignInViewModel signInViewModel;

  /**
   * Inflates the sign-in layout and configures the sign-in button.
   *
   * @param inflater layout inflater.
   * @param container optional parent container.
   * @param savedInstanceState saved instance state, if any.
   * @return root view for this fragment.
   */
  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    binding = FragmentSignInBinding.inflate(inflater, container, false);
    binding.signInButton.setOnClickListener(
        (v) -> {
          binding.signInButton.setEnabled(false);
          signInViewModel.signIn(requireActivity());
        });
    return binding.getRoot();
  }

  /**
   * Completes fragment setup by connecting the UI to the sign-in ViewModel.
   *
   * @param view root view returned from {@link #onCreateView}.
   * @param savedInstanceState saved instance state, if any.
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    signInViewModel = new ViewModelProvider(requireActivity())
        .get(SignInViewModel.class);

    signInViewModel
        .getCredential()
        .observe(getViewLifecycleOwner(), (credential) -> {
          if (credential != null) {
            Navigation.findNavController(binding.getRoot())
                .navigate(R.id.navigate_to_batch_list_fragment);
          } else {
            binding.signInButton.setEnabled(true);
          }
        });

    signInViewModel
        .getThrowable()
        .observe(getViewLifecycleOwner(), (throwable) -> {
          if (throwable != null) {
            binding.signInButton.setEnabled(true);
            binding.signInButton.setVisibility(View.VISIBLE);
            // TODO: show a Snackbar or error text here.
          }
        });

    // Attempt silent sign-in similar to capstone's signInQuickly.
    signInViewModel.signInQuickly(requireActivity());
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