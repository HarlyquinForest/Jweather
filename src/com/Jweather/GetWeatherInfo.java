package com.Jweather;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class GetWeatherInfo
{
    private  City selectedCity;
    private static boolean ready = false;
    GetWeatherInfo(City c , String api)
    {
        selectedCity = c;
        fetchWeatherInfo(api);
    }
    void fetchWeatherInfo(String api)
    {
        File cityDir = new File("/tmp/Jweather/"+selectedCity.getId());
        if(!cityDir.exists())
        {
            if(!cityDir.mkdirs())
            {
                System.out.println("Something went wrong ");
                return ;
            }
        }
        System.out.println(cityDir.getAbsolutePath());
        File current = new File(cityDir.getAbsolutePath()+"/current.xml");
        File daily   = new File(cityDir.getAbsolutePath()+"/daily.xml");
        File hourly  = new File(cityDir.getAbsolutePath()+"/hourly.xml");

        if(current.exists() && daily.exists() && hourly.exists())
        {
            setWeatherData(current , daily , hourly);
            ready = true;
        }
        else
        {
        try {
            fetchAPI(api, current, daily, hourly);
            setWeatherData(current , daily , hourly);
            ready = true;
            System.out.println("all Good");
        }catch (Exception e)
        {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        }
    }

    private void fetchAPI(String api , File c , File d , File h) throws Exception
    {
        URL[] API_URLS = {
                new URL("http://api.openweathermap.org/data/2.5/forecast/daily"+ api),
                new URL("http://api.openweathermap.org/data/2.5/weather"+ api),
                new URL("http://api.openweathermap.org/data/2.5/forecast"+ api)};
        URLConnection urlConnection ;
        HttpURLConnection connection ;
        BufferedReader in ;
        String write ;
        String read ;
        int counter = 0 ;
        for(URL url : API_URLS)
        {
            urlConnection = url.openConnection();
            connection = (HttpURLConnection)urlConnection;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            write = read = "";
            while((read = in.readLine()) != null)
                write += read;
            switch (counter)
            {
                case 0 :
                    FileUtils.writeStringToFile(d , write , "utf-8");
                    break;
                case 1 :
                    FileUtils.writeStringToFile(c , write , "utf-8");
                    break;
                case 2 :
                    FileUtils.writeStringToFile(h , write , "utf-8");
                    break;
            }
            counter++;
        }
    }

    private void setWeatherData(File c , File d , File h)
    {
        selectedCity.setCurrentWeather(readCurrent(c));
        selectedCity.setDaysForecast(readDaily(d));
        selectedCity.setDaysHourly(readHourly(h));
    }
    private Weather readCurrent(File file )
    {
        Weather w = new Weather();
        w.setDay("NOW");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            Node node;
            NodeList sy = doc.getElementsByTagName("temperature");
            node = sy.item(0);
            Element el = (Element) node;
            String temp = el.getAttribute("value");
            float degree = Float.parseFloat(temp);
            w.setDegree(Math.round(degree));

            sy = doc.getElementsByTagName("humidity");
            node = sy.item(0);
            el = (Element) node;
            w.setHumidity( Integer.parseInt(el.getAttribute("value")));

            sy = doc.getElementsByTagName("speed");
            node = sy.item(0);
            el = (Element) node;
            w.setSpeed(Float.parseFloat(el.getAttribute("value")));
            w.setWindName(el.getAttribute("name"));

            sy = doc.getElementsByTagName("direction");
            node = sy.item(0);
            el = (Element) node;
            w.setWindDirection(el.getAttribute("name"));

            sy = doc.getElementsByTagName("clouds");
            node = sy.item(0);
            el = (Element) node;
            w.setWeather(WordUtils.capitalize(el.getAttribute("name")));

            sy = doc.getElementsByTagName("weather");
            node = sy.item(0);
            el = (Element) node;
            w.setIcon(el.getAttribute("icon"));

            sy = doc.getElementsByTagName("lastupdate");
            node = sy.item(0);
            el = (Element) node;
            String t[] = el.getAttribute("value").split("T");
            selectedCity.setLastUpdate(t[0]+" "+t[1]);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  w ;
    }
    private Weather[] readDaily(File file)
    {
        Weather[] daily = new Weather[5];
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element;
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("symbol");
            NodeList details;
            Node node;
            for (int i = 1; i < 6; i++)
            {
                daily[i-1] = new Weather();
                node = nodeList.item(i);
                element = (Element) node;
                daily[i-1].setWeather(StringUtils.capitalize(element.getAttribute("name")));

                details = doc.getElementsByTagName("time");
                node= details.item(i);
                element = (Element)node ;
                daily[i-1].setDay(LocalDate.parse(element.getAttribute("day")).getDayOfWeek().toString());


                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element) node;
                daily[i-1].setDegree(Math.round(Float.parseFloat(element.getAttribute("day"))));
                daily[i-1].setMin(Math.round(Float.parseFloat(element.getAttribute("min"))));
                daily[i-1].setMax(Math.round(Float.parseFloat(element.getAttribute("max"))));

                details = doc.getElementsByTagName("symbol");
                node = details.item(i);
                element = (Element) node;
                daily[i-1].setIcon(element.getAttribute("var"));

                details = doc.getElementsByTagName("windSpeed");
                node = details.item(i);
                element = (Element) node;
                daily[i-1].setSpeed(Float.parseFloat(element.getAttribute("mps")));
                daily[i-1].setWindName(element.getAttribute("name"));

                details = doc.getElementsByTagName("windDirection");
                node = details.item(i);
                element = (Element) node;
                daily[i-1] .setWindDirection(element.getAttribute("name"));

            }
            nodeList = doc.getElementsByTagName("temperature");
            node = nodeList.item(0);
            element = (Element) node;
            selectedCity.getCurrentWeather().setMin(Math.round(Float.parseFloat(element.getAttribute("min"))));
            selectedCity.getCurrentWeather().setMax(Math.round(Float.parseFloat(element.getAttribute("max"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daily;
    }
    private Weather[] readHourly(File file)
    {
        Weather[] hourly = new Weather[8];
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element;
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("time");
            NodeList details;
            Node node;
            for (int i = 0; i < 8; i++) {
                hourly[i] = new Weather();
                node = nodeList.item(i);
                element = (Element) node;
                String[] temp = element.getAttribute("from").split("T");

                if(LocalDate.now().toString().equals(temp[0])) {
                    hourly[i].setDay("Today");
                }
                else
                    hourly[i].setDay(LocalDate.parse(temp[0]).getDayOfWeek().toString()); // gets date and return the day nem , how intelligent is this ! :-0

                String pattern = "HH:mm:ss";
                SimpleDateFormat reading = new SimpleDateFormat(pattern);
                pattern = "H";
                SimpleDateFormat Hour = new SimpleDateFormat(pattern);
                Date date = reading.parse(temp[1]);
                temp[1] = Hour.format(date);
                int h = Integer.parseInt(temp[1]);
                if (h >= 00 && h <= 12)
                    hourly[i].setTime(temp[1] + "am");
                else if (h > 12 && h <= 23)
                    hourly[i].setTime(temp[1] + "pm");

                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element) node;
                hourly[i].setDegree(Math.round(Float.parseFloat(element.getAttribute("value"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hourly;
    }

    static boolean weatherReady()
    {
        return ready;
    }

    public static void setReady(boolean ready) {
        GetWeatherInfo.ready = ready;
    }

    public void setCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

}
