package com.Jweather;

import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang3.text.WordUtils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

class ShowWeather {

    private static City city ;
    ShowWeather(City c)
    {
        city = c ;
    }

    static City getCity() {
        return city;
    }

    static void setCity(City city) {
        ShowWeather.city = city;
    }

    Weather getWeather()
    {
        return city.getCurrentWeather();
    }
    Weather[] getForecast()
    {
        return city.getDaysForecast();
    }
    Weather[] getHourlyForecast(){return city.getDaysHourly();}
    String showCity()
    {
        return city.getName();
    }
}
