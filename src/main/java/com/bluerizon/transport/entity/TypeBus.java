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
@Table(name = "typeBus")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class TypeBus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idTypeBus")
    private Long idTypeBus;
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "user", nullable = false),
            @JoinColumn(name = "compagnie", referencedColumnName = "compagnie", nullable = false) })
    @ManyToOne
    private UserCompagnies userCompagnie;
    @Column(name = "libelle")
    private String libelle;
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

    public TypeBus() {
    }

    public TypeBus(Long idTypeBus) {
        this.idTypeBus = idTypeBus;
    }

    public Long getIdTypeBus() {
        return idTypeBus;
    }

    public void setIdTypeBus(Long idTypeBus) {
        this.idTypeBus = idTypeBus;
    }

    public UserCompagnies getUserCompagnie() {
        return userCompagnie;
    }

    public void setUserCompagnie(UserCompagnies userCompagnie) {
        this.userCompagnie = userCompagnie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
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
        TypeBus typeBus = (TypeBus) o;
        return deleted == typeBus.deleted && Objects.equals(idTypeBus, typeBus.idTypeBus) && Objects.equals(userCompagnie, typeBus.userCompagnie) && Objects.equals(libelle, typeBus.libelle) && Objects.equals(version, typeBus.version) && Objects.equals(createdAt, typeBus.createdAt) && Objects.equals(updatedAt, typeBus.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeBus, userCompagnie, libelle, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "TypeBus{" +
                "idTypeBus=" + idTypeBus +
                ", userCompagnie=" + userCompagnie +
                ", libelle='" + libelle + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
