package com.Jweather;

public class Weather
{
    //variables
    private int Degree;
    private int humidity ;
    private int speed ;
    private String lastUpdate;
    private String icon ;
    private String weather ;
    private String day ;

    Weather()
    {}
    Weather(int degree , String ico , String name , String d)
    {
            Degree = degree;
            icon = ico ;
            weather = name ;
            day = d ;
    }

    public int getDegree() {
        return Degree;
    }

    public String getDay() {
        return day;
    }

    public String getIcon() {
        return icon;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setDegree(int degree) {
        this.Degree = degree;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }
}

