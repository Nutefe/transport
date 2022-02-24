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
import java.util.*;

/**
 *
 * @author Domique
 */

@Entity
@Table(name = "voyages")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Voyages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idVoyage")
    private Long idVoyage;
    @JoinColumn(name = "ligne", referencedColumnName = "idLigne", nullable = false)
    @ManyToOne
    private Lignes ligne;
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "user", nullable = false),
            @JoinColumn(name = "compagnie", referencedColumnName = "compagnie", nullable = false) })
    @ManyToOne
    private UserCompagnies userCompagnie;
    @Column(name = "dateDepart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepart;
    @Column(name = "dateArriver")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateArriver;
    @Column(name = "terminer")
    private boolean terminer = false;
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

    public Voyages() {
    }

    public Voyages(Long idVoyage) {
        this.idVoyage = idVoyage;
    }

    public Long getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(Long idVoyage) {
        this.idVoyage = idVoyage;
    }

    public Lignes getLigne() {
        return ligne;
    }

    public void setLigne(Lignes ligne) {
        this.ligne = ligne;
    }

    public UserCompagnies getUserCompagnie() {
        return userCompagnie;
    }

    public void setUserCompagnie(UserCompagnies userCompagnie) {
        this.userCompagnie = userCompagnie;
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

    public boolean isTerminer() {
        return terminer;
    }

    public void setTerminer(boolean terminer) {
        this.terminer = terminer;
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
        Voyages voyages = (Voyages) o;
        return terminer == voyages.terminer && deleted == voyages.deleted && Objects.equals(idVoyage, voyages.idVoyage) && Objects.equals(ligne, voyages.ligne) && Objects.equals(userCompagnie, voyages.userCompagnie) && Objects.equals(dateDepart, voyages.dateDepart) && Objects.equals(dateArriver, voyages.dateArriver) && Objects.equals(version, voyages.version) && Objects.equals(createdAt, voyages.createdAt) && Objects.equals(updatedAt, voyages.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVoyage, ligne, userCompagnie, dateDepart, dateArriver, terminer, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Voyages{" +
                "idVoyage=" + idVoyage +
                ", ligne=" + ligne +
                ", userCompagnie=" + userCompagnie +
                ", dateDepart=" + dateDepart +
                ", dateArriver=" + dateArriver +
                ", terminer=" + terminer +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
