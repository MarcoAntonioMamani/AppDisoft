package com.luisbar.waterdelivery.presentation.view.fragment;

import android.view.View;

/**
 * Interface that define all methods and listener from InvoiceFragment
 */
public interface InvoiceFragmentI {

    void configFragment(View view);
    void printingFinished(String msg);
}
