package com.bluerizon.transport.requeste;

public class UpdatePassRequest {
    private String nouveau;
    private String ancien;

    public UpdatePassRequest() {
    }

    public String getNouveau() {
        return nouveau;
    }

    public void setNouveau(String nouveau) {
        this.nouveau = nouveau;
    }

    public String getAncien() {
        return ancien;
    }

    public void setAncien(String ancien) {
        this.ancien = ancien;
    }
}
