package com.luisbar.waterdelivery.presentation.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;

public class CalendarDialogFragment extends DialogFragment {

    /**
     * Create a new instance of ConfirmationDialogFragment
     */
    public static CalendarDialogFragment newInstance(String title, Fragment fragment) {
        CalendarDialogFragment calendarDialogFragment = new CalendarDialogFragment();
        Bundle args = new Bundle();

        args.putInt(Config.FRAGMENT, fragment.getId());
        args.putString(Config.TITLE, title);

        calendarDialogFragment.setArguments(args);
        calendarDialogFragment.setCancelable(false);

        return calendarDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Bundle args = getArguments();
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

        return  builder
                .setCancelable(false)
                .setView(R.layout.fragment_dialog_calendar)
                .setTitle(args.getString(Config.TITLE))
                .setPositiveButton(R.string.txt_49,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CalendarDialogFragmentI calendarDialogFragmentI;
                                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));

                                DialogFragment dialogFragment = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Config.CALENDAR_DIALOG_FRAGMENT);
                                DatePicker datePicker = (DatePicker) dialogFragment.getDialog().findViewById(R.id.dp_date);

                                calendarDialogFragmentI = (CalendarDialogFragmentI) fragment;
                                calendarDialogFragmentI.accept(datePicker.getYear() + "-" +
                                        (datePicker.getMonth()+1) + "-" +
                                        datePicker.getDayOfMonth());
                            }
                        }
                )
                .setNegativeButton(R.string.txt_50,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                CalendarDialogFragmentI calendarDialogFragmentI;
                                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));

                                calendarDialogFragmentI = (CalendarDialogFragmentI) fragment;
                                calendarDialogFragmentI.cancelCalendar();
                            }
                        }
                )
                .create();
    }

    /**
     * Interface for triggering an event when the accept or cancel button has been pressed
     */
    public interface CalendarDialogFragmentI {
        void accept(String date);
        void cancelCalendar();
    }
}
