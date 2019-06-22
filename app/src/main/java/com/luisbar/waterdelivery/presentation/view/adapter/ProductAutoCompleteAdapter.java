package com.luisbar.waterdelivery.presentation.view.adapter;

import java.util.AbstractList;
import java.util.List;

public class ProductAutoCompleteAdapter extends AbstractList {

    private List<List> lstProducts;

    public ProductAutoCompleteAdapter(List lstProducts) {
        this.lstProducts = lstProducts;
    }

    @Override
    public Product get(int location) {
        return new Product(Integer.valueOf(lstProducts.get(location).get(0).toString())
                , lstProducts.get(location).get(1).toString(),
                lstProducts.get(location).get(2).toString());
    }

    @Override
    public int size() {
        return lstProducts.size();
    }

    public class Product {

        private int id;
        private String name;
        private String price;

        public Product(int id, String name, String price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
