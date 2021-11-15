/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluerizon.transport.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Domique
 */

@Embeddable
public class UserPK implements Serializable {

    @JoinColumn(name = "user", referencedColumnName = "idUser", nullable = false)
    @ManyToOne
    private Users user;
    @JoinColumn(name = "compagnie", referencedColumnName = "idCompagnie", nullable = false)
    @ManyToOne(optional = false)
    private Compagnies compagnie;

    public UserPK() {
    }

    public UserPK(Users user, Compagnies compagnie) {
        this.user = user;
        this.compagnie = compagnie;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Compagnies getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(Compagnies compagnie) {
        this.compagnie = compagnie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPK userPK = (UserPK) o;
        return Objects.equals(user, userPK.user) && Objects.equals(compagnie, userPK.compagnie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, compagnie);
    }

    @Override
    public String toString() {
        return "UserPK{" +
                "user=" + user +
                ", compagnie=" + compagnie +
                '}';
    }
}
