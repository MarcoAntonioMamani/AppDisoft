package com.luisbar.waterdelivery;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.facebook.stetho.Stetho;
import com.strongloop.android.loopback.RestAdapter;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class WaterDeliveryApplication extends Application {

    private RestAdapter mRestAdapter;
    public static Resources resources;




    @Override
    public void onCreate() {
        super.onCreate();
        resources = getResources();
        initRealm();
        initStetho();
    }

    /**
     * Initialize client for requesting data to the loopback API,
     * with it you can create your ModelRepository
     * @return RestAdapter
     */
    public RestAdapter createRestAdpater() {
        if (mRestAdapter == null)
            mRestAdapter = new RestAdapter(getApplicationContext(), getHost());

        return mRestAdapter;
    }

    private String getHost() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        return settings.getString("IP", "https://water-delivery.herokuapp.com/api");
    }

    /**
     * Initialize Realm database
     */
    private void initRealm() {
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                                                .deleteRealmIfMigrationNeeded()
                                                .build();

        Realm.setDefaultConfiguration(configuration);
    }

    /**
     * Initialize Stetho, it is using for seeing data from realm database
     */
    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .withLimit(7000)
                                .build())
                .build());
    }
}
