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
@Table(name = "villes")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Villes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idVille")
    private Long idVille;
    @JoinColumn(name = "compagnie", referencedColumnName = "idCompagnie", nullable = false)
    @ManyToOne
    private Compagnies compagnie;
    @JoinColumn(name = "pays", referencedColumnName = "idPays", nullable = false)
    @ManyToOne
    private Pays pays;
    @Column(name = "nomVille")
    private String nomVille;
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

    public Villes() {
    }

    public Villes(Long idVille) {
        this.idVille = idVille;
    }

    public Long getIdVille() {
        return idVille;
    }

    public void setIdVille(Long idVille) {
        this.idVille = idVille;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public String getNomVille() {
        return nomVille;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
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
        Villes villes = (Villes) o;
        return deleted == villes.deleted && Objects.equals(idVille, villes.idVille) && Objects.equals(compagnie, villes.compagnie) && Objects.equals(pays, villes.pays) && Objects.equals(nomVille, villes.nomVille) && Objects.equals(version, villes.version) && Objects.equals(createdAt, villes.createdAt) && Objects.equals(updatedAt, villes.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVille, compagnie, pays, nomVille, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Villes{" +
                "idVille=" + idVille +
                ", compagnie=" + compagnie +
                ", pays=" + pays +
                ", nomVille='" + nomVille + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
