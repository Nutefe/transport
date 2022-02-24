package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Compagnies;
import com.bluerizon.transport.entity.Roles;
import com.bluerizon.transport.entity.Users;

import javax.validation.constraints.*;
import java.util.Date;

public class UserCompagniesRequest {

    private Users user;
    private Compagnies compagnie;

    public UserCompagniesRequest() {
    }

    public UserCompagniesRequest(Users user, Compagnies compagnie) {
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
}
