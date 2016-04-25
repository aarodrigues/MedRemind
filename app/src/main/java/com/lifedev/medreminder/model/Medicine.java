package com.lifedev.medreminder.model;

import java.util.Date;

/**
 * Created by alano on 4/21/16.
 */
public class Medicine {

    private int id;
    private String name;
    private String laboratory;
    private Date dateCadastre;
    private Date beginHour;
    private Date interval;

    public Medicine(){

    }

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

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public Date getDateCadastre() {
        return dateCadastre;
    }

    public void setDateCadastre(Date dateCadastre) {
        this.dateCadastre = dateCadastre;
    }

    public Date getBeginHour() {
        return beginHour;
    }

    public void setBeginHour(Date beginHour) {
        this.beginHour = beginHour;
    }

    public Date getInterval() {
        return interval;
    }

    public void setInterval(Date interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return name;
    }

}
