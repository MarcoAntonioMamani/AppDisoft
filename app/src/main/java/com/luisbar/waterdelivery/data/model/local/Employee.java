package com.luisbar.waterdelivery.data.model.local;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Employee extends RealmObject {

    @PrimaryKey
    private Integer cbnumi;
    private String cbdesc;
    private boolean copied;
    private RealmList<Zone> zones;

    /**
     * @return The cbnumi
     */
    public Integer getCbnumi() {
        return cbnumi;
    }

    /**
     * @param cbnumi The cbnumi
     */
    public void setCbnumi(Integer cbnumi) {
        this.cbnumi = cbnumi;
    }

    /**
     * @return The cbdesc
     */
    public String getCbdesc() {
        return cbdesc;
    }

    /**
     * @param cbdesc The cbdesc
     */
    public void setCbdesc(String cbdesc) {
        this.cbdesc = cbdesc;
    }

    public boolean isCopied() {
        return copied;
    }

    public void setCopied(boolean copied) {
        this.copied = copied;
    }

    public RealmList<Zone> getZones() {
        return zones;
    }

    public void setZones(RealmList<Zone> zones) {
        this.zones = zones;
    }
}
