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
@Table(name = "bus")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Bus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idBus")
    private Long idBus;
    @JoinColumn(name = "compagnie", referencedColumnName = "idCompagnie", nullable = false)
    @ManyToOne
    private Compagnies compagnie;
    @JoinColumn(name = "typeBus", referencedColumnName = "idTypeBus", nullable = false)
    @ManyToOne
    private TypeBus typeBus;
    @Column(name = "numeroMatricule", unique = true)
    private String numeroMatricule;
    @Column(name = "nombrePlace")
    private Integer nombrePlace;
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

    public Bus() {
    }

    public Bus(Long idBus) {
        this.idBus = idBus;
    }

    public Long getIdBus() {
        return idBus;
    }

    public void setIdBus(Long idBus) {
        this.idBus = idBus;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public TypeBus getTypeBus() {
        return typeBus;
    }

    public void setTypeBus(TypeBus typeBus) {
        this.typeBus = typeBus;
    }

    public String getNumeroMatricule() {
        return numeroMatricule;
    }

    public void setNumeroMatricule(String numeroMatricule) {
        this.numeroMatricule = numeroMatricule;
    }

    public Integer getNombrePlace() {
        return nombrePlace;
    }

    public void setNombrePlace(Integer nombrePlace) {
        this.nombrePlace = nombrePlace;
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
        Bus bus = (Bus) o;
        return deleted == bus.deleted && Objects.equals(idBus, bus.idBus) && Objects.equals(compagnie, bus.compagnie) && Objects.equals(typeBus, bus.typeBus) && Objects.equals(numeroMatricule, bus.numeroMatricule) && Objects.equals(nombrePlace, bus.nombrePlace) && Objects.equals(version, bus.version) && Objects.equals(createdAt, bus.createdAt) && Objects.equals(updatedAt, bus.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBus, compagnie, typeBus, numeroMatricule, nombrePlace, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "idBus=" + idBus +
                ", compagnie=" + compagnie +
                ", typeBus=" + typeBus +
                ", numeroMatricule='" + numeroMatricule + '\'' +
                ", nombrePlace=" + nombrePlace +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
