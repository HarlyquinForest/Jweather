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

public class Weather extends Thread
{
    //variables
    private int City ;


    //constructor
    public Weather()
    {
        this.City = Settings.getID();
        //System.out.println(City);
        CreateDirs();
    }
    //create tmp dirs
    private void CreateDirs()
    {
        File main = new File("/tmp/Jweather");
        File CityDir = new File("/tmp/Jweather/"+City);
        if(!main.exists())
        {
            try
            {
                main.mkdir();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(!CityDir.exists())
        {
            try
            {
                CityDir.mkdir();
                System.out.println("Dir created");

            }catch (Exception e )
            {
                e.printStackTrace();
            }
        }
    }
    public static boolean saved()
    {
        boolean ok = true;
        int id = Settings.city.getId();
        if(!new File("/tmp/Jweather/"+id+"/current.xml").exists())
        {
            ok = false ;
        }
        else if(!new File("/tmp/Jweather/"+id+"/daily.xml").exists())
        {
            ok = false ;
        }
        else if(!new File("/tmp/Jweather/"+id+"/hourly.xml").exists())
        {
           ok = false ;
        }
        return ok ;
    }
    public void run()
    {
        int sleep = 1000 ;
        do
        {
            Settings.checkConnection();
            Settings.ready = saved();
            try {
                sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(sleep != 20000)
                sleep +=100 ;

        } while(Settings.offline);
        if(!saved())
        {
            getWeather();
            Settings.ready = true;
        }
        else
        {
            Settings.ready = true;
        }
    }
    public void GetWeather()
    {
        getWeather();
    }
    //get weather info form api
    private void  getWeather()
    {
        String API ;
        if(Settings.Celcius)
            API = "&APPID=04ed4038994ff1be56247052ae7bc45f&units=metric&mode=xml";
        else
            API = "&APPID=04ed4038994ff1be56247052ae7bc45f&units=imperial&mode=xml";
        File current = new File("/tmp/Jweather/"+City+"/current.xml");
        File daily = new File("/tmp/Jweather/"+City+"/daily.xml");
        File hourly = new File("/tmp/Jweather/"+City+"/hourly.xml");
        String[] API_URL = {
                  "http://api.openweathermap.org/data/2.5/forecast/daily?id=" + City + API
                , "http://api.openweathermap.org/data/2.5/weather?id=" + City + API
                , "http://api.openweathermap.org/data/2.5/forecast?id=" + City + API
                            };
        URL url ;
        URLConnection urlConnection ;
        HttpURLConnection connection ;
        BufferedReader in ;
        String write ;
        String read ;
        try
        {
            for(int i = 0 ; i < 3 ; i++)
            {
                url = new URL(API_URL[i]);
                urlConnection = url.openConnection();
                connection =  (HttpURLConnection) urlConnection ;
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                write ="";
                read = "";

                while((read = in.readLine()) != null )
                {
                    write += read;
                }
                switch (i)
                {
                    case 0 :
                        FileUtils.write(daily , write , "utf-8");
                        break;
                    case 1 :
                        FileUtils.write(current , write , "utf-8");
                        break;
                    case 2 :
                        FileUtils.write(hourly , write , "utf-8");
                        break;
                }
            }

        } catch(Exception e )
        {
            System.out.println("Some problem occured!");
        }
        finally {
            Settings.refresh = false ;
        }

    }
}
