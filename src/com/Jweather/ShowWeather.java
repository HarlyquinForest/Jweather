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
import java.time.LocalDate;

public class ShowWeather
{
    private String ThemeSummer = getClass().getResource("/source/summer.css").toExternalForm();
    private String ThemeFall = getClass().getResource("/source/fall.css").toExternalForm();
    private String ThemeWinter = getClass().getResource("/source/winter.css").toExternalForm();
    private String ThemeSpring = getClass().getResource("/source/spring.css").toExternalForm();
    private File current = new File("/tmp/Jweather/"+Settings.City+"/current.xml");
    private File daily   = new File("/tmp/Jweather/"+ Settings.City+"/daily.xml");

    public ShowWeather()
    {
    }
    public String showCurrnet()
    {
        String output ;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(current);
            doc.getDocumentElement().normalize();

            Node node;
            NodeList sy  = doc.getElementsByTagName("temperature");
            node = sy.item(0);
            Element el = (Element)node;
            String temp = el.getAttribute("value");
            double degree = Double.parseDouble(temp);
            output = String.format("%02d",Math.round(degree));

            sy = doc.getElementsByTagName("humidity");
            node = sy.item(0);
            el = (Element)node;
            output += "-"+el.getAttribute("value");

            sy= doc.getElementsByTagName("speed");
            node= sy.item(0);
            el= (Element) node ;
            output+= "-"+el.getAttribute("value");

            sy= doc.getElementsByTagName("clouds");
            node= sy.item(0);
            el= (Element) node ;
            output+= "-"+WordUtils.capitalize(el.getAttribute("name"));

            sy = doc.getElementsByTagName("weather");
            node= sy.item(0);
            el= (Element) node ;
            output += "-"+el.getAttribute("icon");


            sy= doc.getElementsByTagName("city");
            node= sy.item(0);
            el= (Element) node ;
            output+= "-"+ WordUtils.capitalize(el.getAttribute("name"));

            sy= doc.getElementsByTagName("country");
            node= sy.item(0);
            el= (Element) node ;
            output+= " , "+el.getTextContent();



            return output;

        }catch (Exception e)
        {
            e.printStackTrace();
            return "°" ;
        }
    }

    public String Days()
    {
        String output ="";

        for(int i = 1 ; i <= 5 ; i++)
            output +=  LocalDate.now().getDayOfWeek().plus(i) +"-";

        return output;
    }

    public String showDays()
    {
        String output = "";
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element ;
            Document doc = dBuilder.parse(daily);
            NodeList nodeList = doc.getElementsByTagName("symbol");
            NodeList details ;
            Node node ;
            for(int i = 1 ; i < nodeList.getLength() ; i++)
            {
                node = nodeList.item(i);
                element = (Element)node;
                output += element.getAttribute("name")+"-";
                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element)node;
                output+=String.format("%02d",Math.round(Double.parseDouble(element.getAttribute("day"))))+"°-";

                details = doc.getElementsByTagName("symbol");
                node = details.item(i);
                element = (Element) node ;
                output += element.getAttribute("var")+"-";

            }
        }catch (Exception e){e.printStackTrace();}
        return output;
    }

    public  void setWallpaper(Scene scene)
    {
        String Month = LocalDate.now().getMonth().toString();
        switch (Month)
        {
            case "MARCH":
            case "APRIL":
            case "MAY":
                scene.getStylesheets().add(ThemeSpring);
                break;
            case "JUNE":
            case "JULY":
            case "AUGUST":
                scene.getStylesheets().add(ThemeSummer);
                break;
            case "SEPTEMBER":
            case "OCTOBER":
            case "NOVEMBER":
                scene.getStylesheets().add(ThemeFall);
                break;
            case "DECEMBER":
            case "JANUARY":
            case "FEBRUARY":
                scene.getStylesheets().add(ThemeWinter);
        }

    }
}
