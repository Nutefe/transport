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
@Table(name = "arrets")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Arrets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idArret")
    private Long idArret;
    @JoinColumn(name = "ville", referencedColumnName = "idVille", nullable = false)
    @ManyToOne
    private Villes ville;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "gare")
    private boolean gare = false;
    @Column(name = "deleted")
    private boolean deleted = false;
    @Version
    @Basic(optional = false)
    @Column(nullable = false)
    private Long version;
    @CreatedDate
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Arrets() {
    }

    public Arrets(Long idArret) {
        this.idArret = idArret;
    }

    public Long getIdArret() {
        return idArret;
    }

    public void setIdArret(Long idArret) {
        this.idArret = idArret;
    }

    public Villes getVille() {
        return ville;
    }

    public void setVille(Villes ville) {
        this.ville = ville;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public boolean isGare() {
        return gare;
    }

    public void setGare(boolean gare) {
        this.gare = gare;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrets arrets = (Arrets) o;
        return gare == arrets.gare && deleted == arrets.deleted && Objects.equals(idArret, arrets.idArret) && Objects.equals(ville, arrets.ville) && Objects.equals(libelle, arrets.libelle) && Objects.equals(adresse, arrets.adresse) && Objects.equals(longitude, arrets.longitude) && Objects.equals(latitude, arrets.latitude) && Objects.equals(version, arrets.version) && Objects.equals(createdAt, arrets.createdAt) && Objects.equals(updatedAt, arrets.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idArret, ville, libelle, adresse, longitude, latitude, gare, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Arrets{" +
                "idArret=" + idArret +
                ", ville=" + ville +
                ", libelle='" + libelle + '\'' +
                ", adresse='" + adresse + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", gare=" + gare +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
