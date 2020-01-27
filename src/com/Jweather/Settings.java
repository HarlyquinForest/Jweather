package com.Jweather;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings
{
    static String HOME_PATH = System.getProperty("user.home");
    static String CONFIG_PATH = HOME_PATH+"/.Jweather";
    static File CONFIG_FILE = new File(CONFIG_PATH+"/config.conf");
    private File CITY_DATA = new File(CONFIG_PATH+"/city.json");
    private File TMP = new File("/tmp/Jweather");
    private static GetWeatherInfo getWeatherInfo ;
    static Unit unit ;
    private static String API ;
    static City defaultCity;
    private static City selectedCity;
    static ArrayList<City> cities;

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
                FileUtils.writeStringToFile(CONFIG_FILE, "metric\n2643743,London,GB\n", "utf-8");
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
        getWeatherInfo = new GetWeatherInfo(selectedCity, getAPI());
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
            default:
            case "metric":
                unit = Unit.Celsius;
            break;
            case "imperial":
                unit = Unit.Fahrenheit;
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
                selectedCity = defaultCity;
            }
        }
        if(defaultCity == null ) {
            selectedCity = cities.get(0);
            defaultCity = selectedCity;
        }
        API = "?id="+ defaultCity.getId()+"&APPID=04ed4038994ff1be56247052ae7bc45f&units="+unit.getName()+"&mode=xml";
    }

    public static String getAPI() {
        return API;
    }

    public City getDefaultCity() {
        return defaultCity;
    }

    public static City getSelectedCity() {
        return selectedCity;
    }

    public static void setSelectedCity(City selectedCity)
    {
        Settings.selectedCity = selectedCity;
        API = "?id="+ selectedCity.getId()+"&APPID=04ed4038994ff1be56247052ae7bc45f&units="+unit.getName()+"&mode=xml";
        new Thread(() -> {getWeatherInfo = new GetWeatherInfo(selectedCity , API);}).start();
    }

    static void restCity()
    {
        try {
            FileUtils.deleteDirectory(new File("/tmp/Jweather/"+selectedCity.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
