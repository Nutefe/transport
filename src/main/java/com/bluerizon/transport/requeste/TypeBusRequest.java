package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Users;

public class TypeBusRequest {
    private Compagnies compagnie;
    private String libelle;

    public TypeBusRequest() {
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
