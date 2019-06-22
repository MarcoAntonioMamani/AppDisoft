package com.luisbar.waterdelivery.presentation.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;

public class QuantityDialogFragment extends DialogFragment {

    /**
     * Create a new instance of ProgressDialogFragment
     */
    public static QuantityDialogFragment newInstance(String title, Fragment fragment) {
        QuantityDialogFragment quantityDialogFragment = new QuantityDialogFragment();

        Bundle args = new Bundle();
        args.putString(Config.TITLE, title);
        args.putInt(Config.FRAGMENT, fragment.getId());

        quantityDialogFragment.setArguments(args);
        quantityDialogFragment.setCancelable(false);

        return quantityDialogFragment;
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_quantity, null);
        final EditText inputQuantity = (EditText) view.findViewById(R.id.input_dialog_quantity);


        return  new AlertDialog.Builder(getActivity())
                .setTitle(args.getString(Config.TITLE))
                .setView(view)
                .setPositiveButton(R.string.txt_49, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QuantityDialogFragmentI quantityDialogFragmentI;
                        quantityDialogFragmentI = (QuantityDialogFragmentI) getActivity()
                                                    .getSupportFragmentManager()
                                                    .findFragmentById(args.getInt(Config.FRAGMENT));

                        quantityDialogFragmentI.accept(Integer.valueOf(inputQuantity.getText().toString()));
                    }
                })
                .setNegativeButton(R.string.txt_50, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    public interface QuantityDialogFragmentI {

        void accept(int quantity);
    }
}
