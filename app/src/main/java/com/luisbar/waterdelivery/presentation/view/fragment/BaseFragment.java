package com.luisbar.waterdelivery.presentation.view.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.luisbar.waterdelivery.common.config.Config;

public class BaseFragment extends Fragment {

    /**
     * It method remove the current fragment
     */
    protected void removeCurrentFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .remove(this)
                .commitAllowingStateLoss();

        fragmentManager.popBackStack();
    }

    /**
     * It method change the fragment by new one
     * @param container Id from container for the fragment
     * @param fragment New fragment
     */
    protected void changeFragmentAndSaveTransition(int container, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    /**
     * It show the progress bar
     */
    protected void showDialog(String tag, DialogFragment customDialog) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment previous = fragmentManager.findFragmentByTag(tag);

        if (previous != null) {
            DialogFragment dialogFragment = (DialogFragment) previous;
            dialogFragment.dismissAllowingStateLoss();
        }

        fragmentTransaction.addToBackStack(null);
        customDialog.show(fragmentTransaction, tag);
    }

    /**
     * It hide the progress bar
     */
    protected void hideDialog(String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (tag.equals(Config.PROGRESS_DIALOG_FRAGMENT)) fragmentManager.executePendingTransactions();
        Fragment previous = fragmentManager.findFragmentByTag(tag);

        if (previous != null) {
            DialogFragment dialogFragment = (DialogFragment) previous;
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    protected void showSnackBar(String message) {
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
