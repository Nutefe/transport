package com.bluerizon.transport.response;

import java.io.Serializable;

public class Countries implements Serializable {

    private String name;
    private String dial_code;
    private String code;

    public Countries() {
    }

    public Countries(String name, String dial_code, String code) {
        this.name = name;
        this.dial_code = dial_code;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "name='" + name + '\'' +
                ", dial_code='" + dial_code + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
