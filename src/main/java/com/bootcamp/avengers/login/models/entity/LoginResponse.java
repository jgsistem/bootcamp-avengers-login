package com.bootcamp.avengers.login.models.entity;

import javax.persistence.Column;

public class LoginResponse {

    private String dni;
    private String names;
    private String fatherlastname;
    private String motherlastname;
    private int status;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getFatherlastname() {
        return fatherlastname;
    }

    public void setFatherlastname(String fatherlastname) {
        this.fatherlastname = fatherlastname;
    }

    public String getMotherlastname() {
        return motherlastname;
    }

    public void setMotherlastname(String motherlastname) {
        this.motherlastname = motherlastname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
