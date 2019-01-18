package com.app.springboot.datatransfer;

import java.util.Date;

public class UserData {
    private Integer id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private CityData city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public CityData getCity() {
        return city;
    }

    public void setCity(CityData city) {
        this.city = city;
    }
}
