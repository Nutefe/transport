package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Roles;

import javax.validation.constraints.*;
import java.util.Date;

public class CompteRequest {

    @Pattern(regexp="^(?=.*[a-zA-Z].*)(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message="regex controle")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 200, message
            = "Password must be between 10 and 200 characters")
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
    private Roles role;
    private Pays pays;
    private Integer nbrCompagnie = 1;
    private Date expirer;
    private boolean active = true;

    public CompteRequest() {
    }

    public CompteRequest(String username, String email, String password, String nom, String prenom, String telephone,
                         String adresse, Roles role, Pays pays, Integer nbrCompagnie, Date expirer, boolean active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.role = role;
        this.pays = pays;
        this.nbrCompagnie = nbrCompagnie;
        this.expirer = expirer;
        this.active = active;
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

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Integer getNbrCompagnie() {
        return nbrCompagnie;
    }

    public void setNbrCompagnie(Integer nbrCompagnie) {
        this.nbrCompagnie = nbrCompagnie;
    }

    public Date getExpirer() {
        return expirer;
    }

    public void setExpirer(Date expirer) {
        this.expirer = expirer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
