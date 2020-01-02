package com.Jweather;

public class City
{
    private String lastUpdate;
    private String name ;
    private String country;
    private Weather currentWeather ;
    private Weather[] daysForecast;
    private int ID = 0 ;

    City(int i , String n , String c)
    {
        ID = i ;
        name = n ;
        country = c;
        daysForecast = new Weather[5];
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

    public void setDaysForecast(Weather[] daysForecast) {
        this.daysForecast = daysForecast;
    }

    public Weather[] getDaysForecast() {
        return daysForecast;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String toString()
    {
        return ID + "," + name + ","+country;
    }
}
