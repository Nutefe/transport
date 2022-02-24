package com.bluerizon.transport.response;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Voyages;

import java.util.List;

public class ResponseVoyage {

    private Voyages voyage;
    private List<Bus> bus;

    public ResponseVoyage() {
    }

    public ResponseVoyage(Voyages voyage, List<Bus> bus) {
        this.voyage = voyage;
        this.bus = bus;
    }

    public Voyages getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyages voyage) {
        this.voyage = voyage;
    }

    public List<Bus> getBus() {
        return bus;
    }

    public void setBus(List<Bus> bus) {
        this.bus = bus;
    }
}
