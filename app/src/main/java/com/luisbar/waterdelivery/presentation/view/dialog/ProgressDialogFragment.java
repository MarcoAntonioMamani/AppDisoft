package com.luisbar.waterdelivery.presentation.view.dialog;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.common.config.Config;

public class ProgressDialogFragment extends DialogFragment {

    /**
     * Create a new instance of ProgressDialogFragment
     */
    public static ProgressDialogFragment newInstance(String title, String message) {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();

        Bundle args = new Bundle();
        args.putString(Config.TITLE, title);
        args.putString(Config.MESSAGE, message);

        progressDialogFragment.setArguments(args);
        progressDialogFragment.setCancelable(false);

        return progressDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Bundle args = getArguments();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_progress, null);
        TextView textMessage = (TextView) view.findViewById(R.id.text_message);
        ProgressBar pb = (ProgressBar) view.findViewById(R.id.progress_dialog);

        textMessage.setText(args.getString(Config.MESSAGE));
        pb.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

        return  new AlertDialog.Builder(getActivity())
                .setTitle(args.getString(Config.TITLE))
                .setView(view)
                .create();
    }
}
