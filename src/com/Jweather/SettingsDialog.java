package com.Jweather;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Control;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SettingsDialog
{
    @FXML
    private JFXRadioButton fahrenheit_radio ;
    @FXML
    private JFXRadioButton celsius_radio ;
    @FXML
    private JFXTextField search_textbox ;
    @FXML
    private JFXButton find_btn ;
    @FXML
    private JFXListView cities_listview;
    @FXML
    private JFXButton clear_btn;

    private ArrayList<City> list ;
    private JsonParser parser ;
    private Object obj ;
    private JsonObject jsonObject ;
    private findState find = findState.find;
    City c = new City(0,"n","w");
    enum findState
    {
        find , add , clear ;
    }

    @FXML
    public void initialize()
    {
        list = Settings.cities;
        Collection<String> collection = new ArrayList<>();
        for(City c : list)
            collection.add(c.toString());
        ObservableList<String> items = FXCollections.observableArrayList(collection);
        cities_listview.setItems(items);

        if(Settings.unit == Unit.Celsius)
            celsius_radio.setSelected(true);
        else if(Settings.unit == Unit.Fahrenheit)
            fahrenheit_radio.setSelected(true);

        parser = new JsonParser();
        try {
            obj = parser.parse(new FileReader(Settings.CONFIG_PATH+"/city.json"));
            jsonObject = new Gson().fromJson(String.valueOf(obj), JsonObject.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Config file is missing");
        }
    }
    @FXML
    public void exitWindow(ActionEvent event)
    {

    }
   public void update()
    {
    }
    public void radioButton_handle(ActionEvent event )
    {
        String id = ((Control)event.getTarget()).getId() ;
        if(id.equals(fahrenheit_radio.getId()))
        {
            celsius_radio.setSelected(false);
            fahrenheit_radio.setSelected(true);
            Settings.unit = Unit.Fahrenheit ;
        }
        else if(id.equals(celsius_radio.getId()))
        {
            fahrenheit_radio.setSelected(false);
            celsius_radio.setSelected(true);
            Settings.unit = Unit.Celsius;
        }
    }

    public boolean isNumric(String str)
    {
        try
        {
            int num = Integer.parseInt(str);
        }catch (NumberFormatException | NullPointerException e)
        {
            return false ;
        }
        return true;
    }
    public void find_btn(ActionEvent event)
    {
        if(find == findState.find)
        {
            String key = search_textbox.getText();
            String attribute ;
            if(isNumric(key))
                attribute = "id";
            else
                attribute = "name";
            JsonArray array = (JsonArray) jsonObject.get("cities");
            JsonObject jo ;
            for(int i = 0 ; i < array.size() ; i++)
            {
                jo = (JsonObject) array.get(i);
                String temp = jo.get(attribute).getAsString();
                if(key.equalsIgnoreCase(temp))
                {
                    System.out.println("Found");
                    c = new City(jo.get("id").getAsInt() , jo.get("name").getAsString() , jo.get("country").getAsString());
                    Settings.cities.add(c);
                    list = Settings.cities;
                    find = findState.add;
                    find_btn.setText("Add");
                    break ;
                }
            }
        }
        else if(find == findState.add)
        {
            try{
                FileUtils.writeStringToFile(Settings.CONFIG_FILE , c.toString()+"\n" ,"utf-8" , true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            find = findState.find;
            find_btn.setText("Find");
        }
    }
    public void listview_handler(MouseEvent event)
    {

    }
    public void clear(ActionEvent event)
    {
        search_textbox.setText("");
        find_btn.setText("Find");
        clear_btn.setVisible(false);
        search_textbox.setDisable(false);
    }

    public void trouble_btn(MouseEvent event)
    {

    }

}
