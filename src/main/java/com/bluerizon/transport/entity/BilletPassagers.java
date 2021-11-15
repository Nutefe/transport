/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluerizon.transport.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
@Table(name = "billetPassagers")
@XmlRootElement
@PrimaryKeyJoinColumn(name = "idBillet")
public class BilletPassagers extends Billets implements Serializable {

    @Column(name = "poidsAutorise")
    private double poidsAutorise;
    @Column(name = "poidsApporte")
    private double poidsApporte;

    public BilletPassagers() {
    }

    public double getPoidsAutorise() {
        return poidsAutorise;
    }

    public void setPoidsAutorise(double poidsAutorise) {
        this.poidsAutorise = poidsAutorise;
    }

    public double getPoidsApporte() {
        return poidsApporte;
    }

    public void setPoidsApporte(double poidsApporte) {
        this.poidsApporte = poidsApporte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BilletPassagers that = (BilletPassagers) o;
        return Double.compare(that.poidsAutorise, poidsAutorise) == 0 && Double.compare(that.poidsApporte, poidsApporte) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(poidsAutorise, poidsApporte);
    }

    @Override
    public String toString() {
        return "BilletPassagers{" +
                "poidsAutorise=" + poidsAutorise +
                ", poidsApporte=" + poidsApporte +
                '}';
    }
}
