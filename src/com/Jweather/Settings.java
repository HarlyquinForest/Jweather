package com.Jweather;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Settings
{
    private String HOME_PATH = System.getProperty("user.home");
    private String CONFIG_PATH = HOME_PATH+"/.Jweather";
    private File CONFIG_FILE = new File(CONFIG_PATH+"/config.conf");
    private File CITY_DATA = new File(CONFIG_PATH+"/city.json");
    private File TMP = new File("/tmp/Jweather");
    private Unit unit ;
    private String API ;
    static City defaultCity;
    private ArrayList<City> cities;

    Settings()
    {
        if(!new File(CONFIG_PATH).exists())
        {
           boolean ok =  new File(CONFIG_PATH).mkdirs();
           if(!ok)
           {
               System.out.println("We got some error while creating required directories");
               System.exit(-1);
           }
        }
        if(!TMP.exists())
        {
            if(!TMP.mkdirs())
            {
                System.out.println("We got some error while creating required directories");
                System.exit(-1);
            }
        }
        if(!CONFIG_FILE.exists())
        {
            try
            {
                FileUtils.writeStringToFile(CONFIG_FILE, "C", "utf-8");
            }catch (Exception e)
            {
                System.out.println("Something went wrong");
            }
        }
        if(!CITY_DATA.exists())
        {
            if(!copyCityData()) {
                System.out.println("Couldn't copy city json to config dir");
                System.exit(-1);
            }
        }
        cities = new ArrayList<>();
        loadSettings();
    }
    private boolean copyCityData()
    {
        File citySource = new File("src/source/city.json");
        try {
            FileUtils.copyFile(citySource, CITY_DATA);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    private void loadSettings()
    {
        List<String> lines;
        try {
            lines = FileUtils.readLines(CONFIG_FILE, "utf-8");
        }catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        switch (lines.get(0))
        {
            case "C":
                unit = Unit.Celsius;
            break;
            case "F":
                unit = Unit.Fahrenheit;
            break;
            default:
                unit = Unit.Celsius;
            break;
        }
        lines.remove(0);
        for(String line : lines)
        {
            String[] temp = line.split(",");
            cities.add(new City(Integer.parseInt(temp[0]) , temp[1] , temp[2].replace("*","")));
            if(temp[2].contains("*"))
            {
                defaultCity = new City(Integer.parseInt(temp[0]) , temp[1] , temp[2].replace("*",""));
            }
        }

        if (defaultCity != null)
        {
            API = "?id="+ defaultCity.getId()+"&APPID=04ed4038994ff1be56247052ae7bc45f&units="+unit.getName()+"&mode=xml";
        }
        else
        {
            API = null ;
        }
    }

    public String getAPI() {
        return API;
    }

    public City getDefaultCity() {
        return defaultCity;
    }
}
