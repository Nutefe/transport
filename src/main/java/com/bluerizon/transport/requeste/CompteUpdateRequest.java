package com.bluerizon.transport.requeste;

import javax.validation.constraints.*;

public class CompteUpdateRequest {

    @Pattern(regexp="^(?=.*[a-zA-Z].*)(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message="regex controle")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    @NotNull(message = "nom cannot be null")
    @NotBlank(message = "nom cannot be null")
    @NotEmpty(message = "nom cannot be null")
    private String nom;
    @NotNull(message = "prenom cannot be null")
    @NotBlank(message = "prenom cannot be null")
    @NotEmpty(message = "prenom cannot be null")
    private String prenom;
    @Size(min = 8, max = 8, message
            = "telephone is 8")
    private String telephone;
    private String adresse;
    private Double longitude ;
    private Double latitude ;
    @NotNull(message = "numero enregistrement cannot be null")
    @NotBlank(message = "numero enregistrement cannot be null")
    @NotEmpty(message = "numero enregistrement cannot be null")
    private String numeroEnr;
    private String facebook;
    private String whatsapp;
    private String tweeter;

    public CompteUpdateRequest() {
    }

    public CompteUpdateRequest(String username, String email, String password, String nom, String prenom, String telephone, String adresse, Double longitude, Double latitude, String numeroEnr, String facebook, String whatsapp, String tweeter) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
        this.numeroEnr = numeroEnr;
        this.facebook = facebook;
        this.whatsapp = whatsapp;
        this.tweeter = tweeter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroEnr() {
        return numeroEnr;
    }

    public void setNumeroEnr(String numeroEnr) {
        this.numeroEnr = numeroEnr;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getTweeter() {
        return tweeter;
    }

    public void setTweeter(String tweeter) {
        this.tweeter = tweeter;
    }
}
