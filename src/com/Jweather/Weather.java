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

public class Weather
{
    //variables
    private String City ;


    //constructor
    public Weather()
    {
        City = Settings.City;
        CreateDirs();
    }
    //create tmp dirs
    private void CreateDirs()
    {
        new File("/tmp/Jweather").mkdir();
        File CityDir = new File("/tmp/Jweather/"+City);
        if(!CityDir.exists())
        {
            try
            {
                CityDir.mkdir();

            }catch (Exception e )
            {
                e.printStackTrace();
            }
        }
    }


    //get weather info form api
    public void  getWeather()
    {
        File currentW = new File("/tmp/Jweather/"+City+"/current.xml");
        File daily = new File("/tmp/Jweather/"+City+"/daily.xml");
        File hourly = new File("/tmp/Jweather/"+City+"/hourly.xml");
        String API_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + City + "&APPID=04ed4038994ff1be56247052ae7bc45f&units=metric&mode=xml";
        try
        {
            URL url = new URL(API_URL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection =  (HttpURLConnection) urlConnection ;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String urlString ="";
            String current ;

            while((current = in.readLine()) != null )
            {
                urlString += current;
            }
            FileUtils.write(daily , urlString , "utf-8");
            //current
            API_URL = "http://api.openweathermap.org/data/2.5/weather?q=" + City + "&APPID=04ed4038994ff1be56247052ae7bc45f&units=metric&mode=xml";
            url = new URL(API_URL);
            urlConnection = url.openConnection();
            connection = (HttpURLConnection) urlConnection;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            urlString = "";
            current = "";
            while((current = in.readLine()) != null )
            {
                urlString += current;
            }
            FileUtils.write(currentW , urlString , "utf-8");
            //5 day forecast 3 hours
            API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=" + City + "&APPID=04ed4038994ff1be56247052ae7bc45f&units=metric&mode=xml";
            url = new URL(API_URL);
            urlConnection = url.openConnection();
            connection = (HttpURLConnection) urlConnection;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            urlString = "";
            current = "";
            while((current = in.readLine()) != null )
            {
                urlString += current;
            }
            FileUtils.write(hourly , urlString , "utf-8");


        } catch(Exception e )
        {
            System.out.println("Some problem occured!");
        }

    }

    /*private static Scanner sc = new Scanner(System.in);


        File file ;
        System.out.println("Enter you city name : ");
        String city = sc.nextLine();
        String dirPath = "/tmp/"+city;
        file = new File(dirPath);
        if(file.exists())
            System.out.println("folder is exist");
        else
        {
            System.out.println("folder is missing but i will create");
            file.mkdir();
        }
        System.out.println("daily or hourly : ");
        char c = sc.next().charAt(0);
        if ( c == 'd')
            file = new File(dirPath+"/daily.xml");
        else if (c == 'h' )
            file = new File(dirPath+"/hourly.xml");
        else
            file = new File(dirPath+"/daily.xml");
        boolean saved = false;
        if(file.exists())
        {
            System.out.println("File is exist \n Reading form file");
            Thread.sleep(2000);
            getWeather(file , c);
        }else
        {
            saved = saveWeather(file , city ,c );
        }
        if(saved)
            getWeather(file , c);



    public static void getWeather(File file , char mode)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        switch (mode )
        {
            case 'd':
                dailyWeather(file , dbFactory);
                break;
            case 'h' :
                hourlyWeather(file , dbFactory);
                break;
        }

    }
    private static void dailyWeather(File file, DocumentBuilderFactory dbFactory)
    {
        try
        {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("location");
            Node node = nList.item(0);
            System.out.println("Current Element : "+node.getNodeName());

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element el = (Element)node;
                System.out.println("City name : "+el.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("Country name : "+el.getElementsByTagName("country").item(0).getTextContent());
                node = nList.item(1);
                el = (Element)node;
                System.out.println("altitude : "+el.getAttribute("altitude")
                        +"\nlatitude : "+el.getAttribute("latitude")
                        +"\nlongitude : "+el.getAttribute("longitude")
                        +"\ngeobase : "+el.getAttribute("geobase")
                        +"\ngeobaseid : "+el.getAttribute("geobaseid"));
                nList = doc.getElementsByTagName("time");
                NodeList sy ;
                for(int i = 0 ; i < nList.getLength() ; i++)
                {
                    node = nList.item(i);
                    el = (Element) node ;
                    System.out.println("--------------------------------------------------");
                    System.out.printf("Day %s : %n",el.getAttribute("day"));
                    sy = doc.getElementsByTagName("symbol");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("Symbol : %s %n",el.getAttribute("name"));
                    sy = doc.getElementsByTagName("windSpeed");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("WindSpeed : %s mps  Name : %s%n", el.getAttribute("mps") , el.getAttribute("name"));
                    sy = doc.getElementsByTagName("temperature");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("Avg Temperature : %s C  Min : %s C   Max : %s C%n", el.getAttribute("day") , el.getAttribute("min"),el.getAttribute("max"));
                }

            }


        }catch(Exception e){e.printStackTrace();}

    }
    private static void hourlyWeather(File file, DocumentBuilderFactory dbFactory)
    {
        try
        {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("location");
            Node node = nList.item(0);
            System.out.println("Current Element : "+node.getNodeName());

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element el = (Element)node;
                System.out.println("City name : "+el.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("Country name : "+el.getElementsByTagName("country").item(0).getTextContent());
                node = nList.item(1);
                el = (Element)node;
                System.out.println("altitude : "+el.getAttribute("altitude")
                        +"\nlatitude : "+el.getAttribute("latitude")
                        +"\nlongitude : "+el.getAttribute("longitude")
                        +"\ngeobase : "+el.getAttribute("geobase")
                        +"\ngeobaseid : "+el.getAttribute("geobaseid"));
                nList = doc.getElementsByTagName("time");
                NodeList sy ;
                for(int i = 0 ; i < nList.getLength() ; i++)
                {
                    node = nList.item(i);
                    el = (Element) node ;
                    System.out.println("--------------------------------------------------");
                    System.out.printf("from  %s to %s  %n",el.getAttribute("from") , el.getAttribute("to"));
                    sy = doc.getElementsByTagName("symbol");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("Symbol : %s %n",el.getAttribute("name"));
                    sy = doc.getElementsByTagName("windSpeed");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("WindSpeed : %s mps  Name : %s%n", el.getAttribute("mps") , el.getAttribute("name"));
                    sy = doc.getElementsByTagName("temperature");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("Temperature : %s C %n", el.getAttribute("value"));
                    sy = doc.getElementsByTagName("humidity");
                    node = sy.item(i);
                    el = (Element)node;
                    System.out.printf("Humidity : %s%s %n" , el.getAttribute("value"),el.getAttribute("unit"));
                    sy = doc.getElementsByTagName("clouds");
                    node = sy.item(i);
                    el = (Element) node ;
                    System.out.printf("Clouds : %s %n" , el.getAttribute("value"));
                }

            }


        }catch(Exception e){e.printStackTrace();}

    }*/
}
