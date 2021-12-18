package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Bus;
import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Lignes;
import com.bluerizon.transport.entity.Voyages;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class PassagerRequest {

    @NotBlank
    private String nomCompletExpediteur;
    @NotBlank
    private String contactExpediteur;
    private Compagnies compagnie;
    @NotBlank
    private String nomCompletDestinataire;
    @NotBlank
    private String contactDestinataire;
    private double poidsApporte;
    private Voyages voyage;
    private Bus bus;
    private Lignes ligne;

    public PassagerRequest() {
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
