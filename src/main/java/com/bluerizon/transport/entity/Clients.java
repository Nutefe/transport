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
@Table(name = "clients")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idClient")
    private Long idClient;
    @JoinColumn(name = "compagnie", referencedColumnName = "idCompagnie", nullable = false)
    @ManyToOne
    private Compagnies compagnie;
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "nomComplet", nullable = false)
    private String nomComplet;
    @Column(name = "contact")
    private String contact;
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

    public Clients() {
    }

    public Clients(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
        Clients clients = (Clients) o;
        return deleted == clients.deleted && Objects.equals(idClient, clients.idClient) && Objects.equals(compagnie, clients.compagnie) && Objects.equals(code, clients.code) && Objects.equals(nomComplet, clients.nomComplet) && Objects.equals(contact, clients.contact) && Objects.equals(version, clients.version) && Objects.equals(createdAt, clients.createdAt) && Objects.equals(updatedAt, clients.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClient, compagnie, code, nomComplet, contact, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Clients{" +
                "idClient=" + idClient +
                ", compagnie=" + compagnie +
                ", code='" + code + '\'' +
                ", nomComplet='" + nomComplet + '\'' +
                ", contact='" + contact + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
