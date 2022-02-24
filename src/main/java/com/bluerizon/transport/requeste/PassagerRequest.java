package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.Voyages;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PassagerRequest {

    @NotBlank
    private String nomComplet;
    @NotBlank
    private String contact;
    private Compagnies compagnie;
    @NotNull
    private double poidsApporte;
    private Voyages voyage;
    private Bus bus;
    private Lignes ligne;

    public PassagerRequest() {
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

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public double getPoidsApporte() {
        return poidsApporte;
    }

    public void setPoidsApporte(double poidsApporte) {
        this.poidsApporte = poidsApporte;
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

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }
}
