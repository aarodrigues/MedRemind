package com.lifedev.medreminder.model;

import java.util.Date;

/**
 * Created by alano on 4/21/16.
 */
public class Caregiver {

    private int id;

    private String name;

    private String phone;

    private String email;

    private Date dateCadastre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public Date getDateCadastre() {
        return dateCadastre;
    }

    public void setDateCadastre(Date dateCadastre) {
        this.dateCadastre = dateCadastre;
    }

    @Override
    public String toString() {
        return name;
    }


}
