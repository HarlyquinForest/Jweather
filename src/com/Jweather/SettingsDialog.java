package com.Jweather;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Control;
import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @FXML
    private MenuItem show_menuitem , default_menuitem , delete_menuitem;

    private ArrayList<City> list ;
    private JsonParser parser ;
    private Object obj ;
    private JsonObject jsonObject ;
    private findState find = findState.find;
    private City c = new City(0,"n","w");
    enum findState
    {
        find , add , clear ;
    }

    @FXML
    public void initialize()
    {
        System.out.println("settings init");
        list = Settings.cities;
        if(Settings.unit == Unit.Celsius)
            celsius_radio.setSelected(true);
        else if(Settings.unit == Unit.Fahrenheit)
            fahrenheit_radio.setSelected(true);

        parser = new JsonParser();
        new Thread(() -> {

            try {
                obj = parser.parse(new FileReader(Settings.CONFIG_PATH + "/city.json"));
                jsonObject = new Gson().fromJson(String.valueOf(obj), JsonObject.class);
            } catch (
                    FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Config file is missing");
            }
        }).start();
        bindListView();
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
        String id = ((Control) event.getTarget()).getId();
        if (id.equals(fahrenheit_radio.getId())) {
            celsius_radio.setSelected(false);
            fahrenheit_radio.setSelected(true);
            Settings.unit = Unit.Fahrenheit;
        } else if (id.equals(celsius_radio.getId())) {
            fahrenheit_radio.setSelected(false);
            celsius_radio.setSelected(true);
            Settings.unit = Unit.Celsius;
        }
        String output = Settings.unit.getName() +"\n";
        for(City i : list)
            output += i.toString()+"\n";
        writeToFile(output);
    }
    private boolean isNumric(String str)
    {
        try
        {
           Integer.parseInt(str);
        }catch (NumberFormatException | NullPointerException e)
        {
            return false ;
        }
        return true;
    }
    public void find_btn(ActionEvent event)
    {
        switch (find)
        {
            case find:
                find_btn.setDisable(true);
                search_textbox.setDisable(true);
                if(!findAction(search_textbox.getText()))
                {
                    find = findState.clear;
                }
                else
                    find_btn.setDisable(false);
                clear_btn.setVisible(true);

                break;
            case add:
                addAction();
                break;
            case clear:
                clearAction();
                break;
        }

    }
    public void listview_handler(MouseEvent event)
    {

    }
    public void show_menuitem_handler(ActionEvent event)
    {
        System.out.println("show");
        Settings.setSelectedCity(list.get(cities_listview.getSelectionModel().getSelectedIndex()));
    }
    public void default_menuitem_handler(ActionEvent event)
    {
        System.out.println("default");
        int def_index = cities_listview.getSelectionModel().getSelectedIndex();
        Settings.defaultCity = list.get(def_index);
        String output = Settings.unit.getName() +"\n";
        for(City l : list)
        {
            if(l.getId() == list.get(def_index).getId())
                output += l.toString()+"*\n";
            else
                output += l.toString()+"\n";
        }
        writeToFile(output);
        bindListView();

    }
    public void delete_menuitem_handler(ActionEvent event)
    {
        System.out.println("delete");
        int del_index = cities_listview.getSelectionModel().getSelectedIndex();
        Settings.cities.remove(del_index);
        String output = Settings.unit.getName() +"\n" ;
        for(int i = 0 ; i < list.size() ; i++)
        {
            if(i == del_index) {
                list.remove(i);
            }
            else
                output += list.get(i)+"\n";
        }
        writeToFile(output);
        bindListView();
    }
    public void clear(ActionEvent event)
    {
        clearAction();
    }
    public void trouble_btn(MouseEvent event)
    {
        try {
            Runtime rt = Runtime.getRuntime();
            String url = "https://openweathermap.org/find?q=";
            String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape","opera","links","lynx"};

            StringBuffer cmd = new StringBuffer();
            for (int i=0; i<browsers.length; i++)
                cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

            rt.exec(new String[] { "sh", "-c", cmd.toString() });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void bindListView()
    {
        cities_listview.getItems().clear();
        list = Settings.cities;
        Collection<String> collection = new ArrayList<>();
        for(City c : list)
            if(c.getId() == Settings.defaultCity.getId())
                collection.add(c.toString()+"*");
            else
                collection.add(c.toString());
        ObservableList<String> items = FXCollections.observableArrayList(collection);
        cities_listview.setItems(items);
    }
    private boolean findAction(String key )
    {
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
                list = Settings.cities;
                find = findState.add;
                search_textbox.setText(c.toString());
                find_btn.setText("Add");
                return true;
            }
        }
        search_textbox.setText(key+" not found");
        return false;
    }
    private void addAction()
    {
        try{
            boolean add = true ;
            for(City t : Settings.cities)
            {
                if(t.getId() == c.getId())
                {
                    add = false ;
                    break;
                }
            }
            if(add)
            {
                FileUtils.writeStringToFile(Settings.CONFIG_FILE , c.toString()+"\n" ,"utf-8" , true);
                Settings.cities.add(c);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        clearAction();
        bindListView();
    }
    private void clearAction()
    {
        search_textbox.setText("");
        find_btn.setText("Find");
        find_btn.setDisable(false);
        clear_btn.setVisible(false);
        search_textbox.setDisable(false);
        find = findState.find;
    }
    private void writeToFile(String o)
    {
        try {
            FileUtils.writeStringToFile(Settings.CONFIG_FILE , o , "utf-8" , false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
