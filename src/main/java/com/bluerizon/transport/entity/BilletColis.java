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
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Domique
 */

@Entity
@Table(name = "billetColis")
@XmlRootElement
@PrimaryKeyJoinColumn(name = "idBillet")
public class BilletColis extends Billets implements Serializable {

    @Column(name = "nomComplet")
    private String nomComplet;
    @Column(name = "contact")
    private String contact;
    @Column(name = "poidsColis")
    private double poidsColis;

    public BilletColis() {
    }

    public BilletColis(String nomComplet, String contact, double poidsColis) {
        this.nomComplet = nomComplet;
        this.contact = contact;
        this.poidsColis = poidsColis;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(double poidsColis) {
        this.poidsColis = poidsColis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BilletColis that = (BilletColis) o;
        return Double.compare(that.poidsColis, poidsColis) == 0 && Objects.equals(nomComplet, that.nomComplet) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomComplet, contact, poidsColis);
    }

    @Override
    public String toString() {
        return "BilletColis{" +
                "nomComplet='" + nomComplet + '\'' +
                ", contact='" + contact + '\'' +
                ", poidsColis=" + poidsColis +
                '}';
    }
}
