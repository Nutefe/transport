package com.bluerizon.transport.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "privileges")
@EntityListeners(AuditingEntityListener.class)
public class Privileges {
    private static final long serialVersionUID = 1L;
    @Id //@GeneratedValue(strategy= GenerationType.AUTO, generator="native") @GenericGenerator(name = "native",strategy = "native")
    @Basic(optional = false)
    @Column(name = "idPrivilege")
    private Integer idPrivilege;
    private String name;

//    @ManyToMany(mappedBy = "privileges")
//    private Collection<Roles> roles;

    public Privileges() {
    }

    public Privileges(Integer idPrivilege) {
        this.idPrivilege = idPrivilege;
    }

    public Integer getIdPrivilege() {
        return idPrivilege;
    }

    public void setIdPrivilege(Integer idPrivilege) {
        this.idPrivilege = idPrivilege;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Collection<Roles> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Roles> roles) {
//        this.roles = roles;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privileges that = (Privileges) o;
        return Objects.equals(idPrivilege, that.idPrivilege) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrivilege, name);
    }

    @Override
    public String toString() {
        return "Privileges{" +
                "idPrivilege=" + idPrivilege +
                ", name='" + name + '\'' +
                '}';
    }
}
