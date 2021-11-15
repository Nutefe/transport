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
@Table(name = "lignes")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Lignes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idLigne")
    private Long idLigne;
    @JoinColumn(name = "ligne", referencedColumnName = "idLigne", nullable = true)
    @ManyToOne
    private Lignes ligne;
    @JoinColumn(name = "depart", referencedColumnName = "idArret", nullable = false)
    @ManyToOne
    private Arrets depart;
    @JoinColumn(name = "arriver", referencedColumnName = "idArret", nullable = false)
    @ManyToOne
    private Arrets arriver;
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

    public Lignes() {
    }

    public Lignes(Long idLigne) {
        this.idLigne = idLigne;
    }

    public Long getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Long idLigne) {
        this.idLigne = idLigne;
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public Arrets getDepart() {
        return depart;
    }

    public void setDepart(Arrets depart) {
        this.depart = depart;
    }

    public Arrets getArriver() {
        return arriver;
    }

    public void setArriver(Arrets arriver) {
        this.arriver = arriver;
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
        Lignes lignes = (Lignes) o;
        return deleted == lignes.deleted && Objects.equals(idLigne, lignes.idLigne) && Objects.equals(ligne, lignes.ligne) && Objects.equals(depart, lignes.depart) && Objects.equals(arriver, lignes.arriver) && Objects.equals(version, lignes.version) && Objects.equals(createdAt, lignes.createdAt) && Objects.equals(updatedAt, lignes.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLigne, ligne, depart, arriver, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Lignes{" +
                "idLigne=" + idLigne +
                ", ligne=" + ligne +
                ", depart=" + depart +
                ", arriver=" + arriver +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
