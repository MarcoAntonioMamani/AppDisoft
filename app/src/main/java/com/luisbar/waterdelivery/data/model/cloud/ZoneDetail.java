package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.JsonObjectParser;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.List;

public class ZoneDetail extends ModelRepository<ZoneDetail.ZoneDetailNested> {

    public ZoneDetail() {
        super("ZoneDetailNested", "Tl0012s", ZoneDetailNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getZonesByEmployeeId", "POST"),
                getClassName() + ".getZonesByEmployeeId");

        return contract;
    }

    public void getZonesByEmployeeId(int id, final ObjectCallback<ZoneDetailNested> callback) {
        invokeStaticMethod("getZonesByEmployeeId",
                ImmutableMap.of("id", id),
                new JsonObjectParser(this, callback));
    }

    public static class ZoneDetailNested extends Model {

        private List<Integer> result;

        /**
         *
         * @return
         * The result
         */
        public List<Integer> getResult() {
            return result;
        }

        /**
         *
         * @param result
         * The result
         */
        public void setResult(List<Integer> result) {
            this.result = result;
        }
    }
}
