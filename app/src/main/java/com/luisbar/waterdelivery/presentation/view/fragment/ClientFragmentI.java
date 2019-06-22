package com.luisbar.waterdelivery.presentation.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.luisbar.waterdelivery.common.eventbus.ClientAddedFailedV;
import com.luisbar.waterdelivery.common.eventbus.ClientAddedSucceededV;

/**
 * Interface that define all methods and listener from ClientFragment
 */
public interface ClientFragmentI {

    void configFragment(View view, Bundle bundle);

    void clientAddedSucceeded(ClientAddedSucceededV clientAddedSucceededV);
    void clientAddedFailed(ClientAddedFailedV clientAddedFailedV);
}
