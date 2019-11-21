package com.example.chms;

import java.io.Serializable;
import java.util.Date;

public class Cattle implements Serializable {
    private int ucin,age;
    private String cattleType,breed,status,breedingStatus;
    private Date lastHeatDate;

    public Cattle() {

    }

    public int getUcin() {
        return ucin;
    }

    public void setUcin(int ucin) {
        this.ucin = ucin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCattleType() {
        return cattleType;
    }

    public void setCattleType(String cattleType) {
        this.cattleType = cattleType;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBreedingStatus() {
        return breedingStatus;
    }

    public void setBreedingStatus(String breedingStatus) {
        this.breedingStatus = breedingStatus;
    }

    public Date getLastHeatDate() {
        return lastHeatDate;
    }

    public void setLastHeatDate(Date lastHeatDate) {
        this.lastHeatDate = lastHeatDate;
    }
}
