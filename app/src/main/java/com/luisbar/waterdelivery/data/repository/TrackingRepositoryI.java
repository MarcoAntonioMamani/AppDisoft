package com.luisbar.waterdelivery.data.repository;

/**
 * Interface that define all methods and listener from TrackinRepository
 */
public interface TrackingRepositoryI {

    void saveLocation(String lat, String lng, Object object);
    void setmRestAdapter(Object object);
    int getIdFromEmployee();
}
