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
@Table(name = "billets")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Billets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idBillet")
    private Long idBillet;
    @JoinColumn(name = "user", referencedColumnName = "idUser", nullable = false)
    @ManyToOne
    private Users user;
    @JoinColumn(name = "voyage", referencedColumnName = "idVoyage", nullable = false)
    @ManyToOne
    private Voyages voyage;
    @JoinColumn(name = "client", referencedColumnName = "idClient", nullable = false)
    @ManyToOne
    private Clients client;
    @JoinColumns({
            @JoinColumn(name = "bus", referencedColumnName = "bus", nullable = false),
            @JoinColumn(name = "ligne", referencedColumnName = "ligne", nullable = false) })
    @ManyToOne
    private Tarifs tarif;
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

    public Billets() {
    }

    public Billets(Long idBillet) {
        this.idBillet = idBillet;
    }

    public Long getIdBillet() {
        return idBillet;
    }

    public void setIdBillet(Long idBillet) {
        this.idBillet = idBillet;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Voyages getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyages voyage) {
        this.voyage = voyage;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Tarifs getTarif() {
        return tarif;
    }

    public void setTarif(Tarifs tarif) {
        this.tarif = tarif;
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
        Billets billets = (Billets) o;
        return deleted == billets.deleted && Objects.equals(idBillet, billets.idBillet) && Objects.equals(user, billets.user) && Objects.equals(voyage, billets.voyage) && Objects.equals(client, billets.client) && Objects.equals(tarif, billets.tarif) && Objects.equals(version, billets.version) && Objects.equals(createdAt, billets.createdAt) && Objects.equals(updatedAt, billets.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBillet, user, voyage, client, tarif, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Billets{" +
                "idBillet=" + idBillet +
                ", user=" + user +
                ", voyage=" + voyage +
                ", client=" + client +
                ", tarif=" + tarif +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
