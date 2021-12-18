package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.UserCompagnies;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class VoyageRequest {
    private Set<Bus> bus;
    private Lignes ligne;
    private Compagnies compagnie;
    private Date dateDepart;
    private Date dateArriver;

    public VoyageRequest() {
    }

    public Set<Bus> getBus() {
        return bus;
    }

    public void setBus(Set<Bus> bus) {
        this.bus = bus;
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Date getDateArriver() {
        return dateArriver;
    }

    public void setDateArriver(Date dateArriver) {
        this.dateArriver = dateArriver;
    }
}
