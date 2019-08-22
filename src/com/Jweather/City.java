package com.Jweather;

public class City
{
    private String name ;
    private String country;
    private int id = 0 ;

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return id + "," + name + ","+country;
    }
}
