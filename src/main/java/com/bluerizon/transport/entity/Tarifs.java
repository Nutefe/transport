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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Domique
 */

@Entity
@Table(name = "tarifs")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Tarifs implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TarifPK tarifPK;
    @Column(name = "prixPassager", nullable = false)
    private Double prixPassager = 0.0;
    @Column(name = "prixKilo", nullable = false)
    private Double prixKilo = 0.0;
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

    public Tarifs() {
    }

    public Tarifs(TarifPK tarifPK) {
        this.tarifPK = tarifPK;
    }

    public TarifPK getTarifPK() {
        return tarifPK;
    }

    public void setTarifPK(TarifPK tarifPK) {
        this.tarifPK = tarifPK;
    }

    public Double getPrixPassager() {
        return prixPassager;
    }

    public void setPrixPassager(Double prixPassager) {
        this.prixPassager = prixPassager;
    }

    public Double getPrixKilo() {
        return prixKilo;
    }

    public void setPrixKilo(Double prixKilo) {
        this.prixKilo = prixKilo;
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
        Tarifs tarifs = (Tarifs) o;
        return deleted == tarifs.deleted && Objects.equals(tarifPK, tarifs.tarifPK) && Objects.equals(prixPassager, tarifs.prixPassager) && Objects.equals(prixKilo, tarifs.prixKilo) && Objects.equals(version, tarifs.version) && Objects.equals(createdAt, tarifs.createdAt) && Objects.equals(updatedAt, tarifs.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tarifPK, prixPassager, prixKilo, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Tarifs{" +
                "tarifPK=" + tarifPK +
                ", prixPassager=" + prixPassager +
                ", prixKilo=" + prixKilo +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
