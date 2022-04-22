package com.bluerizon.transport.requeste;

import com.bluerizon.transport.entity.Pays;
import com.bluerizon.transport.entity.Roles;

import javax.validation.constraints.*;
import java.util.Date;

public class SignupRequest {

    @Pattern(regexp="^(?=.*[a-zA-Z].*)(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$",message="regex controle")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "password cannot be null")
    @Size(min = 8, max = 200, message
            = "Password must be between 10 and 200 characters")
    private String password;
    private Pays pays;

    public SignupRequest() {
    }

    public SignupRequest(String username, String email, String password, Pays pays) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.pays = pays;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
}
