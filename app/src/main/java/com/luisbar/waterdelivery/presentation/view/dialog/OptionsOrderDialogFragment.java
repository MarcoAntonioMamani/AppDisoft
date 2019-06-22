package com.luisbar.waterdelivery.presentation.view.dialog;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;

public class OptionsOrderDialogFragment extends DialogFragment {

    public static OptionsOrderDialogFragment newInstance(String title, Fragment fragment) {
        Bundle args = new Bundle();
        OptionsOrderDialogFragment optionsOrderDialogFragment = new OptionsOrderDialogFragment();

        args.putInt(Config.FRAGMENT, fragment.getId());
        args.putString(Config.TITLE, title);

        optionsOrderDialogFragment.setArguments(args);
        optionsOrderDialogFragment.setCancelable(false);

        return optionsOrderDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        return new AlertDialog.Builder(getActivity())
                .setTitle(args.getString(Config.TITLE))
                .setCancelable(false)
                .setItems(R.array.order_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OptionsOrderDialogFragmentI optionsOrderDialogFragmentI;
                        optionsOrderDialogFragmentI = (OptionsOrderDialogFragmentI) getActivity()
                                .getSupportFragmentManager()
                                .findFragmentByTag(Config.ORDER_FRAGMENT);

                        optionsOrderDialogFragmentI.onItemClickOptionsOrder(which);
                    }
                })
                .setNegativeButton(R.string.txt_50,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }

    public interface OptionsOrderDialogFragmentI {

        void onItemClickOptionsOrder(int position);
    }
}
