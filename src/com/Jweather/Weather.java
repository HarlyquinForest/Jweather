package com.Jweather;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Set;

public class Weather
{
    //variables
    private int currentDegree ;
    private String icon ;
    private String weather ;
    private String day ;

    Weather(int degree , String ico , String name , String d)
    {
            currentDegree = degree;
            icon = ico ;
            weather = name ;
            day = d ;
    }

    public int getCurrentDegree() {
        return currentDegree;
    }

    public String getDay() {
        return day;
    }

    public String getIcon() {
        return icon;
    }

    public void setCurrentDegree(int currentDegree) {
        this.currentDegree = currentDegree;
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

