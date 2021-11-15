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
@Table(name = "pays")
@XmlRootElement
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "deleted", "version"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idPays")
    private Integer idPays;
    @Column(name = "nomPays", unique = true)
    private String nomPays;
    @Column(name = "codePays", unique = true)
    private String codePays;
    @Column(name = "indicatifPays")
    private String indicatifPays;
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

    public Pays() {
    }

    public Pays(Integer idPays) {
        this.idPays = idPays;
    }

    public Pays(String nomPays, String codePays, String indicatifPays) {
        this.nomPays = nomPays;
        this.codePays = codePays;
        this.indicatifPays = indicatifPays;
    }

    public Integer getIdPays() {
        return idPays;
    }

    public void setIdPays(Integer idPays) {
        this.idPays = idPays;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }

    public String getCodePays() {
        return codePays;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getIndicatifPays() {
        return indicatifPays;
    }

    public void setIndicatifPays(String indicatifPays) {
        this.indicatifPays = indicatifPays;
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
        Pays pays = (Pays) o;
        return deleted == pays.deleted && Objects.equals(idPays, pays.idPays) && Objects.equals(nomPays, pays.nomPays) && Objects.equals(codePays, pays.codePays) && Objects.equals(indicatifPays, pays.indicatifPays) && Objects.equals(version, pays.version) && Objects.equals(createdAt, pays.createdAt) && Objects.equals(updatedAt, pays.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPays, nomPays, codePays, indicatifPays, deleted, version, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Pays{" +
                "idPays=" + idPays +
                ", nomPays='" + nomPays + '\'' +
                ", codePays='" + codePays + '\'' +
                ", indicatifPays='" + indicatifPays + '\'' +
                ", deleted=" + deleted +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
