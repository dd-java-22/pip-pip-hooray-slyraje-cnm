package edu.cnm.deepdive.pippiphooray.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

/**
 * Dialog fragment that displays a date picker.
 */
public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

  /**
   * Creates the date picker dialog using the current date as the default selection.
   *
   * @param savedInstanceState saved instance state, if any.
   * @return configured date picker dialog.
   */
  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker.
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it.
    return new DatePickerDialog(requireContext(), this, year, month, day);
  }

  /**
   * Receives the date selected by the user.
   *
   * @param view date picker view.
   * @param year selected year.
   * @param month selected month, zero-based.
   * @param day selected day of month.
   */
  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    // Do something with the date the user picks.
  }
}