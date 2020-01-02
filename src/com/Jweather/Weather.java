package com.Jweather;

public class Weather
{
    //variables
    private int Degree;
    private int min ;
    private int max;
    private int humidity ;
    private float speed ;
    private String icon ;
    private String weather ;
    private String day ;
    private String windName;
    private String windDirection;

    Weather()
    {
    }
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

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setWindName(String windName) {
        this.windName = windName;
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

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindName() {
        return windName;
    }

    public String getWeather() {
        return weather;
    }

    public float getSpeed() {
        return speed;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getHumidity() {
        return humidity;
    }
}

