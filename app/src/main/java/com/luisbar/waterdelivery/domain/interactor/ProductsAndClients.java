package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.OrderRepository;

public class ProductsAndClients implements UseCase {

    private OrderRepository mOrderRepository;

    public ProductsAndClients() {
        this.mOrderRepository = new OrderRepository();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        mOrderRepository.getClientsAndProducts();
    }
}
