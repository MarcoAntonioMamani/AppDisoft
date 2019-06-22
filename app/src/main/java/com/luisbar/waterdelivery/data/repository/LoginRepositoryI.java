package com.luisbar.waterdelivery.data.repository;

import java.util.List;

/**
 * Interface that define all methods and listener from LoginRepository
 */
public interface LoginRepositoryI {

    void signIn(String code, final String identityCard, Object object);
    void getDetailZone(int id, final com.luisbar.waterdelivery.data.model.local.Employee employee);
    void getCurrentUser();
    void insertUser(final com.luisbar.waterdelivery.data.model.local.Employee employeeAux, final List<Integer> zones);
    void setmRestAdapter(Object object);
}
