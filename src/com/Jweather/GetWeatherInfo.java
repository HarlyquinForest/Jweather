package com.Jweather;

import org.apache.commons.io.FileUtils;
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

public class GetWeatherInfo
{

    void fetchWeatherInfo(City c , String api)
    {
        File cityDir = new File("/tmp/Jweather/"+c.getId());
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
            setWeatherData(c , current , daily , hourly);
        }
        else
        {
        try {
            fetchAPI(api, current, daily, hourly);
            setWeatherData(c , current , daily , hourly);
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

    private void setWeatherData(City city , File c , File d , File h)
    {
        city.setCurrentWeather(readCurrent(c));
        city.setDaysForcast(readDaily(d));
    }

    private Weather readCurrent(File file )
    {
        Weather c = new Weather();
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
            c.setDegree(Math.round(degree));

            sy = doc.getElementsByTagName("humidity");
            node = sy.item(0);
            el = (Element) node;
            c.setHumidity( Integer.parseInt(el.getAttribute("value")));

            sy = doc.getElementsByTagName("speed");
            node = sy.item(0);
            el = (Element) node;
            c.setSpeed(Integer.parseInt(el.getAttribute("value")));

            sy = doc.getElementsByTagName("clouds");
            node = sy.item(0);
            el = (Element) node;
            c.setWeather(WordUtils.capitalize(el.getAttribute("name")));

            sy = doc.getElementsByTagName("weather");
            node = sy.item(0);
            el = (Element) node;
            c.setIcon(el.getAttribute("icon"));


            /*sy = doc.getElementsByTagName("city");
            node = sy.item(0);
            el = (Element) node;
            WordUtils.capitalize(el.getAttribute("name"));

            sy = doc.getElementsByTagName("country");
            node = sy.item(0);
            el = (Element) node;
            output += " , " + el.getTextContent() + "-";

            sy = doc.getElementsByTagName("lastupdate");
            node = sy.item(0);
            el = (Element) node;
            String t[] = el.getAttribute("value").split("T");
            output += t[1];*/

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  c ;
    }
    private Weather[] readDaily(File file)
    {
        Weather[] daily = new Weather[6];
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element;
            Document doc = dBuilder.parse(file);
            NodeList nodeList = doc.getElementsByTagName("symbol");
            NodeList details;
            Node node;
            for (int i = 1; i < 6; i++) {
                node = nodeList.item(i);
                element = (Element) node;
                //output += element.getAttribute("name") + "-";
                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element) node;
                //output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("day")))) + "°-";

                details = doc.getElementsByTagName("symbol");
                node = details.item(i);
                element = (Element) node;
                //output += element.getAttribute("var") + "-";

            }
            nodeList = doc.getElementsByTagName("temperature");
            node = nodeList.item(0);
            element = (Element) node;
            //output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("min")))) + "-";
            //output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("max"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daily;
    }

}
