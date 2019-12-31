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

public class ShowWeather {

    /*private File current = new File("/tmp/Jweather/" + Settings.city.getId() + "/current.xml");
    private File daily = new File("/tmp/Jweather/" + Settings.city.getId() + "/daily.xml");
    private File hourly = new File("/tmp/Jweather/" + Settings.city.getId() + "/hourly.xml");
*/
    public ShowWeather()
    {
    }

    /*public String Days() {
        String output = "";

        for (int i = 1; i <= 5; i++)
            output += LocalDate.now().getDayOfWeek().plus(i) + "-";

        return output;
    }

    public String showDays() {
        String output = "";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element;
            Document doc = dBuilder.parse(daily);
            NodeList nodeList = doc.getElementsByTagName("symbol");
            NodeList details;
            Node node;
            for (int i = 1; i < 6; i++) {
                node = nodeList.item(i);
                element = (Element) node;
                output += element.getAttribute("name") + "-";
                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element) node;
                output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("day")))) + "°-";

                details = doc.getElementsByTagName("symbol");
                node = details.item(i);
                element = (Element) node;
                output += element.getAttribute("var") + "-";

            }
            nodeList = doc.getElementsByTagName("temperature");
            node = nodeList.item(0);
            element = (Element) node;
            output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("min")))) + "-";
            output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("max"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public String forecast() {
        String output = "";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Element element;
            Document doc = dBuilder.parse(hourly);
            NodeList nodeList = doc.getElementsByTagName("time");
            NodeList details;
            Node node;
            for (int i = 0; i < 8; i++) {
                node = nodeList.item(i);
                element = (Element) node;
                String temp[] = element.getAttribute("from").split("T");
                String pattern = "HH:mm:ss";

                SimpleDateFormat reading = new SimpleDateFormat(pattern);
                pattern = "H";
                SimpleDateFormat Hour = new SimpleDateFormat(pattern);
                Date date = reading.parse(temp[1]);
                temp[1] = Hour.format(date);
                int h = Integer.parseInt(temp[1]);
                if (h >= 00 && h <= 12)
                    output += temp[1] + "am-";
                else if (h > 12 && h <= 23)
                    output += temp[1] + "pm-";

                details = doc.getElementsByTagName("temperature");
                node = details.item(i);
                element = (Element) node;
                output += String.format("%02d", Math.round(Double.parseDouble(element.getAttribute("value")))) + "-";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }



    }*/
}
