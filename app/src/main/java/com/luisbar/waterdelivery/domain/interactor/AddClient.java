package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.ClientRepository;
import com.luisbar.waterdelivery.domain.Mapper;
import com.luisbar.waterdelivery.domain.model.ClientModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AddClient implements UseCase {

    private ClientRepository mClientRepository;
    private Mapper mMapper;

    public AddClient() {
        this.mClientRepository = new ClientRepository();
        this.mMapper = new Mapper();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        ClientModel clientModel = mMapper.clientModelPToClientModelD((com.luisbar.waterdelivery.presentation.model.ClientModel) obj1);
        JSONObject data = new JSONObject();

        try {
            data.put("name", clientModel.getName());
            data.put("clientNit", clientModel.getClientNit());
            data.put("reason", clientModel.getReason());
            data.put("nit", clientModel.getNit());
            data.put("address", clientModel.getAddress());
            data.put("phone", clientModel.getPhone());
            data.put("lat", clientModel.getLocation().latitude+"");
            data.put("lng", clientModel.getLocation().longitude+"");

            mClientRepository.saveClientInCloud(data,
                    obj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
