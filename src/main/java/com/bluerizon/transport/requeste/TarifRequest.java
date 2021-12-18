package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Lignes;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class TarifRequest {

    private Bus bus;
    private Lignes ligne;
    private Double prixPassager = 0.0;
    private Double prixKilo = 0.0;

    public TarifRequest() {
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public Double getPrixPassager() {
        return prixPassager;
    }

    public void setPrixPassager(Double prixPassager) {
        this.prixPassager = prixPassager;
    }

    public Double getPrixKilo() {
        return prixKilo;
    }

    public void setPrixKilo(Double prixKilo) {
        this.prixKilo = prixKilo;
    }
}
