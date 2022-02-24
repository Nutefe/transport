package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ColisRequest {

    @NotBlank
    private String nomCompletExpediteur;
    @NotBlank
    private String contactExpediteur;
    private Compagnies compagnie;
    @NotBlank
    private String nomCompletDestinataire;
    @NotBlank
    private String contactDestinataire;
    @NotNull
    private double poidsColis;
    private Voyages voyage;
    private Bus bus;
    private Lignes ligne;

    public ColisRequest() {
    }

    public String getNomCompletExpediteur() {
        return nomCompletExpediteur;
    }

    public void setNomCompletExpediteur(String nomCompletExpediteur) {
        this.nomCompletExpediteur = nomCompletExpediteur;
    }

    public String getContactExpediteur() {
        return contactExpediteur;
    }

    public void setContactExpediteur(String contactExpediteur) {
        this.contactExpediteur = contactExpediteur;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public String getNomCompletDestinataire() {
        return nomCompletDestinataire;
    }

    public void setNomCompletDestinataire(String nomCompletDestinataire) {
        this.nomCompletDestinataire = nomCompletDestinataire;
    }

    public String getContactDestinataire() {
        return contactDestinataire;
    }

    public void setContactDestinataire(String contactDestinataire) {
        this.contactDestinataire = contactDestinataire;
    }

    public double getPoidsColis() {
        return poidsColis;
    }

    public void setPoidsColis(double poidsColis) {
        this.poidsColis = poidsColis;
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
