package com.luisbar.waterdelivery.presentation.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;

public class ConfirmationDialogFragment extends DialogFragment {

    /**
     * Create a new instance of ConfirmationDialogFragment
     */
    public static ConfirmationDialogFragment newInstance(String title, String message, Fragment fragment) {
        ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
        Bundle args = new Bundle();

        args.putInt(Config.FRAGMENT, fragment.getId());
        args.putString(Config.TITLE, title);
        args.putString(Config.MESSAGE, message);

        confirmationDialogFragment.setArguments(args);
        confirmationDialogFragment.setCancelable(false);

        return confirmationDialogFragment;
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

        return new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setTitle(args.getString(Config.TITLE))
                .setMessage(args.getString(Config.MESSAGE))
                .setPositiveButton(R.string.txt_49,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ConfirmationDialogFragmentI confirmationDialogFragmentI;
                                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));

                                confirmationDialogFragmentI = (ConfirmationDialogFragmentI) fragment;
                                confirmationDialogFragmentI.accept();
                            }
                        }
                )
                .setNegativeButton(R.string.txt_50,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ConfirmationDialogFragmentI confirmationDialogFragmentI;
                                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(args.getInt(Config.FRAGMENT));

                                confirmationDialogFragmentI = (ConfirmationDialogFragmentI) fragment;
                                confirmationDialogFragmentI.cancel();
                            }
                        }
                )
                .create();
    }

    /**
     * Interface for triggering an event when the accept or cancel button has been pressed
     */
    public interface ConfirmationDialogFragmentI {
        void accept();
        void cancel();
    }
}
