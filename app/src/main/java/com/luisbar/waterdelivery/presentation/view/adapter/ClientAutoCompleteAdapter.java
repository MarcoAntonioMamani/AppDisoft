package com.luisbar.waterdelivery.presentation.view.adapter;

import java.util.AbstractList;
import java.util.List;

public class ClientAutoCompleteAdapter extends AbstractList {

    private List<List> lstClients;

    public ClientAutoCompleteAdapter(List lstClients) {
        this.lstClients = lstClients;
    }

    @Override
    public Client get(int location) {
        return new Client(Integer.valueOf(lstClients.get(location).get(0).toString())
                , lstClients.get(location).get(1).toString());
    }

    @Override
    public int size() {
        return lstClients.size();
    }

    public class Client {

        private int id;
        private String name;

        public Client(int id, String name) {
            this.id = id;
            this.name = name;
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
    }
}
