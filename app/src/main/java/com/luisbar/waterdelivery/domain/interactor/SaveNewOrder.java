package com.luisbar.waterdelivery.domain.interactor;

import android.util.Log;

import com.luisbar.waterdelivery.data.repository.OrderRepository;
import com.luisbar.waterdelivery.domain.Mapper;
import com.luisbar.waterdelivery.domain.model.OrderModel;

public class SaveNewOrder implements UseCase {

    private OrderRepository mOrderRepository;
    private Mapper mMapper;

    public SaveNewOrder() {
        this.mOrderRepository = new OrderRepository();
        this.mMapper = new Mapper();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        OrderModel orderModel = mMapper.orderModelPToOrderModelD((com.luisbar.waterdelivery.presentation.model.OrderModel) obj1);
        String xml = mMapper.nativeListToXml(orderModel.getProducts());
        Log.e("execute: ", xml);
        mOrderRepository.saveOrderInCloud(orderModel.getClientId(), orderModel.getDate(),
                orderModel.getObservation(), xml,
                orderModel.getTotal(), obj2);
    }
}
