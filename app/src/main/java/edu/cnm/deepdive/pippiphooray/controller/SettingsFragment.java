package edu.cnm.deepdive.pippiphooray.controller;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.pippiphooray.R;
import edu.cnm.deepdive.pippiphooray.viewmodel.SignInViewModel;

/**
 * Preference screen for application settings and account actions.
 *
 * <p>This fragment manages default incubator settings, CSV export access,
 * and sign-out behavior.
 */
@AndroidEntryPoint
public class SettingsFragment extends PreferenceFragmentCompat {

  private static final String KEY_DEFAULT_TARGET_TEMP = "pref_default_target_temp";
  private static final String KEY_DEFAULT_TARGET_HUMIDITY = "pref_default_target_humidity";
  private static final String KEY_EXPORT_BATCH_CSV = "pref_export_batch_csv";
  private static final String KEY_SIGN_OUT = "pref_sign_out";

  private SignInViewModel signInViewModel;

  /**
   * Loads the preference hierarchy and configures preference listeners.
   *
   * @param savedInstanceState saved instance state, if any.
   * @param rootKey root preference key, if a subset of preferences is shown.
   */
  @Override
  public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey);

    EditTextPreference defaultTempPreference = findPreference(KEY_DEFAULT_TARGET_TEMP);
    EditTextPreference defaultHumidityPreference = findPreference(KEY_DEFAULT_TARGET_HUMIDITY);
    Preference exportPreference = findPreference(KEY_EXPORT_BATCH_CSV);
    Preference signOutPreference = findPreference(KEY_SIGN_OUT);

    if (defaultTempPreference != null) {
      defaultTempPreference.setOnPreferenceChangeListener((preference, newValue) ->
          isValidTemperature(newValue)
      );
    }

    if (defaultHumidityPreference != null) {
      defaultHumidityPreference.setOnPreferenceChangeListener((preference, newValue) ->
          isValidHumidity(newValue)
      );
    }

    if (exportPreference != null) {
      exportPreference.setOnPreferenceClickListener((preference) -> {
        Toast.makeText(requireContext(), R.string.message_export_csv_placeholder, Toast.LENGTH_SHORT)
            .show();
        return true;
      });
    }

    if (signOutPreference != null) {
      signOutPreference.setOnPreferenceClickListener((preference) -> {
        if (signInViewModel != null) {
          signInViewModel.signOut();
        }
        return true;
      });
    }
  }

  /**
   * Completes setup after the preference view has been created.
   *
   * <p>This method connects the fragment to the sign-in state so the user can
   * be redirected to the sign-in screen after signing out or after an auth error.
   *
   * @param view fragment root view.
   * @param savedInstanceState saved instance state, if any.
   */
  @Override
  public void onViewCreated(@NonNull android.view.View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    signInViewModel = new ViewModelProvider(requireActivity()).get(SignInViewModel.class);

    signInViewModel
        .getCredential()
        .observe(getViewLifecycleOwner(), (credential) -> {
          if (credential == null) {
            NavOptions options = new NavOptions.Builder()
                .setPopUpTo(R.id.sign_in_fragment, true)
                .build();
            NavHostFragment.findNavController(this)
                .navigate(R.id.sign_in_fragment, null, options);
          }
        });

    signInViewModel
        .getThrowable()
        .observe(getViewLifecycleOwner(), (throwable) -> {
          if (throwable != null) {
            Toast.makeText(requireContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private boolean isValidTemperature(Object newValue) {
    String value = (newValue != null) ? newValue.toString().trim() : "";
    if (value.isEmpty()) {
      Toast.makeText(requireContext(), R.string.error_invalid_temperature, Toast.LENGTH_SHORT)
          .show();
      return false;
    }
    try {
      Double.parseDouble(value);
      return true;
    } catch (NumberFormatException e) {
      Toast.makeText(requireContext(), R.string.error_invalid_temperature, Toast.LENGTH_SHORT)
          .show();
      return false;
    }
  }

  private boolean isValidHumidity(Object newValue) {
    String value = (newValue != null) ? newValue.toString().trim() : "";
    if (value.isEmpty()) {
      Toast.makeText(requireContext(), R.string.error_invalid_humidity, Toast.LENGTH_SHORT)
          .show();
      return false;
    }
    try {
      double humidity = Double.parseDouble(value);
      if (humidity < 0 || humidity > 100) {
        Toast.makeText(requireContext(), R.string.error_invalid_humidity, Toast.LENGTH_SHORT)
            .show();
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      Toast.makeText(requireContext(), R.string.error_invalid_humidity, Toast.LENGTH_SHORT)
          .show();
      return false;
    }
  }

}