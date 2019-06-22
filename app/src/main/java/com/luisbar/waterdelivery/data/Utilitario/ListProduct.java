package com.luisbar.waterdelivery.data.Utilitario;

import java.util.List;

/**
 * Created by usuario on 11/07/2018.
 */

public class ListProduct {
List<producto> listp;

    public ListProduct(List<producto> listp) {
        this.listp = listp;
    }

    public List<producto> getListp() {
        return listp;
    }

    public void setListp(List<producto> listp) {
        this.listp = listp;
    }
}
