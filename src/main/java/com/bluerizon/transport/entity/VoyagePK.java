/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluerizon.transport.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Domique
 */

@Embeddable
public class VoyagePK implements Serializable {

    @JoinColumn(name = "voyage", referencedColumnName = "idVoyage", nullable = false)
    @ManyToOne
    private Voyages voyage;
    @JoinColumn(name = "bus", referencedColumnName = "idBus", nullable = false)
    @ManyToOne(optional = false)
    private Bus bus;

    public VoyagePK() {
    }

    public VoyagePK(Voyages voyage, Bus bus) {
        this.voyage = voyage;
        this.bus = bus;
    }

    public Voyages getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyages voyage) {
        this.voyage = voyage;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoyagePK voyagePK = (VoyagePK) o;
        return Objects.equals(voyage, voyagePK.voyage) && Objects.equals(bus, voyagePK.bus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voyage, bus);
    }
}
