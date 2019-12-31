package com.Jweather;

public class City
{
    private String name ;
    private String country;
    private Weather currentWeather ;
    private Weather[] daysForcast;
    private int ID = 0 ;

    protected City(int i , String n , String c)
    {
        ID = i ;
        name = n ;
        country = c;
        daysForcast = new Weather[6];
    }

    int getId() {
        return ID;
    }

    String getCountry() {
        return country;
    }

    String getName() {
        return name;
    }

    void setCountry(String country) {
        this.country = country;
    }

    void setId(int id) {
        this.ID = id;
    }

    void setName(String name) {
        this.name = name;
    }

    public void setDaysForcast(Weather[] daysForcast) {
        this.daysForcast = daysForcast;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    @Override
    public String toString()
    {
        return ID + "," + name + ","+country;
    }
}
