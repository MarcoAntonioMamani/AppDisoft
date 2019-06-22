package com.luisbar.waterdelivery.data.repository;


import com.luisbar.waterdelivery.R;
import com.luisbar.waterdelivery.WaterDeliveryApplication;
import com.luisbar.waterdelivery.common.eventbus.EventBusMask;
import com.luisbar.waterdelivery.common.eventbus.SignedInFailedP;
import com.luisbar.waterdelivery.common.eventbus.SignedInSucceededP;
import com.luisbar.waterdelivery.common.eventbus.UserLoggedP;
import com.luisbar.waterdelivery.data.model.cloud.Employee;
import com.luisbar.waterdelivery.data.model.cloud.ZoneDetail;
import com.luisbar.waterdelivery.data.model.local.Zone;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;

public class LoginRepository implements LoginRepositoryI {

    private RestAdapter mRestAdapter;
    private final String NOT_FOUND = "Not Found";
    private final String READ_TIMED_OUT = "Read timed out";

    /**
     * It get the Employee object through the API and send it
     * to the listener in LoginPresenter
     * @param code Employee id
     * @param identityCard Identity card from employee id
     * @param object RestAdapter
     */
    @Override
    public void signIn(String code, final String identityCard, Object object) {
        setmRestAdapter(object);
        Employee employee = mRestAdapter.createRepository(Employee.class);

        employee.findById(code, new ObjectCallback<Employee.EmployeeNested>() {
            @Override
            public void onSuccess(Employee.EmployeeNested object) {
                if (object.getCbci().equals(identityCard)) {
                    com.luisbar.waterdelivery.data.model.local.Employee employee;
                    employee = new com.luisbar.waterdelivery.data.model.local.Employee();

                    employee.setCbnumi(object.getCbnumi());
                    employee.setCbdesc(object.getCbdesc());

                    getDetailZone(object.getCbnumi(), employee);
                } else
                    EventBusMask.post(new SignedInFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_32)));
            }

            @Override
            public void onError(Throwable t) {
                switch (t.getMessage()) {
                    case NOT_FOUND:
                        EventBusMask.post(new SignedInFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_33)));
                        break;

                    case READ_TIMED_OUT:
                        EventBusMask.post(new SignedInFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_45)));
                        break;

                    default:
                        EventBusMask.post(new SignedInFailedP(t.getMessage()));
                }
            }
        });
    }

    /**
     * Method for getting the ids about zone for the user logged
     * @param id Employee id
     * @param employee Employee object
     */
    @Override
    public void getDetailZone(int id, final com.luisbar.waterdelivery.data.model.local.Employee employee) {
        ZoneDetail zoneDetail = mRestAdapter.createRepository(ZoneDetail.class);

        zoneDetail.getZonesByEmployeeId(id, new ObjectCallback<ZoneDetail.ZoneDetailNested>() {
            @Override
            public void onSuccess(ZoneDetail.ZoneDetailNested object) {
                insertUser(employee, object.getResult());
            }

            @Override
            public void onError(Throwable t) {
                EventBusMask.post(new SignedInFailedP(WaterDeliveryApplication.resources.getString(R.string.txt_36)));
            }
        });
    }

    /**
     * Get the current user, if is there?, and send it to the
     * listener in LoginPresenter
     */
    @Override
    public void getCurrentUser() {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<com.luisbar.waterdelivery.data.model.local.Employee> query;
        query = realm.where(com.luisbar.waterdelivery.data.model.local.Employee.class);

        String name = query.findFirst() == null ? null : query.findFirst().getCbdesc();
        EventBusMask.post(new UserLoggedP(name));

        realm.close();
    }

    /**
     * It insert the session in realm database
     * @param employeeAux Employee object
     * @param zones Zones ids
     */
    @Override
    public void insertUser(final com.luisbar.waterdelivery.data.model.local.Employee employeeAux, final List<Integer> zones) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                com.luisbar.waterdelivery.data.model.local.Employee employee;
                employee = realm
                        .createObject(com.luisbar.waterdelivery.data.model.local.Employee.class, employeeAux.getCbnumi());
                RealmList realmListZones = new RealmList();

                for (Integer zoneNumber : zones) {
                    Zone zone = realm.createObject(Zone.class);

                    zone.setZone(zoneNumber);
                    realmListZones.add(zone);
                }

                employee.setCbdesc(employeeAux.getCbdesc().toLowerCase());
                employee.setZones(realmListZones);
            }
        },
        new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                EventBusMask.post(new SignedInSucceededP(employeeAux.getCbdesc().toLowerCase()));
                realm.close();
            }
        },
        new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                EventBusMask.post(new SignedInFailedP(error.getMessage()));
                error.printStackTrace();
                realm.close();
            }
        });
    }

    /**
     * It set the RestAdapter
     * @param object RestAdapter
     */
    @Override
    public void setmRestAdapter(Object object) {
        if (mRestAdapter == null)
            mRestAdapter = (RestAdapter) object;
    }
}
