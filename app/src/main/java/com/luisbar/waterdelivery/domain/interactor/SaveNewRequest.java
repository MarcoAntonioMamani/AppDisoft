package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.FcmRepository;

import org.json.JSONArray;
import org.json.JSONException;

public class SaveNewRequest implements UseCase {

    private FcmRepository mFcmRepository;

    public SaveNewRequest() {
        this.mFcmRepository = new FcmRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        JSONArray jsonArray = null;
        int id = 0;

        try {
            jsonArray = new JSONArray("[" + obj1.toString() + "]");
            id = jsonArray.getJSONObject(0).getInt("oanumi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mFcmRepository.saveNewRequest(jsonArray, id);
    }
}
