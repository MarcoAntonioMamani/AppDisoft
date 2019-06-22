package com.luisbar.waterdelivery.domain.interactor;

import com.luisbar.waterdelivery.data.repository.OrderRepository;
import com.luisbar.waterdelivery.domain.Mapper;
import com.luisbar.waterdelivery.domain.model.InvoiceModel;

public class UpdateState implements UseCase {

    private OrderRepository mOrderRepository;
    private Mapper mMapper;

    public UpdateState() {
        this.mOrderRepository = new OrderRepository();
        this.mMapper = new Mapper();
    }

    @Override
    public void execute(Object obj1, Object obj2) {
        InvoiceModel invoice = mMapper.invoiceModelPToInvoiceModelD((com.luisbar.waterdelivery.presentation.model.InvoiceModel) obj1);
        mOrderRepository.updateState(invoice.getId(), invoice.getAmount(), invoice.getIdrepa(), obj2);
    }
}
