/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluerizon.transport.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Domique
 */

@Embeddable
public class TarifPK implements Serializable {

    @JoinColumn(name = "bus", referencedColumnName = "idBus", nullable = false)
    @ManyToOne
    private Bus bus;
    @JoinColumn(name = "ligne", referencedColumnName = "idLigne", nullable = false)
    @ManyToOne(optional = false)
    private Lignes ligne;

    public TarifPK() {
    }

    public TarifPK(Bus bus, Lignes ligne) {
        this.bus = bus;
        this.ligne = ligne;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarifPK tarifPK = (TarifPK) o;
        return Objects.equals(bus, tarifPK.bus) && Objects.equals(ligne, tarifPK.ligne);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bus, ligne);
    }

    @Override
    public String toString() {
        return "TarifPK{" +
                "bus=" + bus +
                ", ligne=" + ligne +
                '}';
    }
}
