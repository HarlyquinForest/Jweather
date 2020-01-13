package com.Jweather;

public class City
{
    private String lastUpdate;
    private String name ;
    private String country;
    private Weather currentWeather ;
    private Weather[] daysForecast;
    private Weather[] daysHourly;
    private int ID ;

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

    void setDaysForecast(Weather[] daysForecast) {
        this.daysForecast = daysForecast;
    }

    Weather[] getDaysForecast() {
        return daysForecast;
    }

    void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    Weather getCurrentWeather() {
        return currentWeather;
    }

    void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    String getLastUpdate() {
        return lastUpdate;
    }

    void setDaysHourly(Weather[] daysHourly) {
        this.daysHourly = daysHourly;
    }

    Weather[] getDaysHourly() {
        return daysHourly;
    }

    @Override
    public String toString()
    {
        return ID + "," + name + ","+country;
    }
}
