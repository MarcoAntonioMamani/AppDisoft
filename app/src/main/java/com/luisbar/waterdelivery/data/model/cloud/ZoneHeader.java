package com.luisbar.waterdelivery.data.model.cloud;

import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.JsonObjectParser;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.List;

public class ZoneHeader extends ModelRepository<ZoneHeader.ZoneHeaderNested> {

    public ZoneHeader() {
        super("ZoneHeaderNested", "Tl001s", ZoneHeaderNested.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/getZonesByZoneIds", "POST"),
                getClassName() + ".getZonesByZoneIds");

        return contract;
    }

    public void getZonesByZoneIds(List ids, final ObjectCallback<ZoneHeaderNested> callback) {
        invokeStaticMethod("getZonesByZoneIds",
                ImmutableMap.of("ids", ids),
                new JsonObjectParser(this, callback));
    }

    public static class ZoneHeaderNested extends Model {

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
