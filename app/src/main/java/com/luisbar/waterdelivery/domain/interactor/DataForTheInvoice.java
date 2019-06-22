package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.OrderRepository;
import com.luisbar.waterdelivery.domain.Mapper;
import com.luisbar.waterdelivery.domain.model.InvoiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DataForTheInvoice implements UseCase {

    private OrderRepository mOrderRepository;
    private Mapper mMapper;

    public DataForTheInvoice() {
        this.mOrderRepository = new OrderRepository();
        this.mMapper = new Mapper();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        InvoiceModel invoiceModel = mMapper.invoiceModelPToInvoiceModelD((com.luisbar.waterdelivery.presentation.model.InvoiceModel) obj1);

        mOrderRepository.getDataForTheInvoice(invoiceModel.getId(), invoiceModel.getIdentityCard(),
                invoiceModel.getAmount(), invoiceModel.getClientName(), listToJsonArray(invoiceModel.getProducts()), obj2);
    }

    private JSONArray listToJsonArray(List detail) {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < detail.size(); i++) {
            List aux = (List) detail.get(i);
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("fvbnumi", 0);
                jsonObject.put("fvbcprod", aux.get(4));
                jsonObject.put("fvbdesprod", aux.get(0));
                jsonObject.put("fvbcant", aux.get(1));
                jsonObject.put("fvbprecio", aux.get(2));
                jsonObject.put("fvbnumi2", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }
}
