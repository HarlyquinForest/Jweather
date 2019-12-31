package com.Jweather;
import com.jfoenix.controls.*;
import com.google.gson.*;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;


import javafx.event.ActionEvent;
import javafx.scene.control.Control;

import javax.swing.text.Element;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
    public void initialize()
    {
        update();

    }
    @FXML
    public void exitWindow(ActionEvent event)
    {

    }
    public void update()
    {
        Collection<String> cities = Settings.Cities.values();
        Settings.City_List.addAll(cities);
        ObservableList<String> list = FXCollections.observableArrayList(cities);
        cities_listview.setItems(list);

        if(Settings.Celcius)
        {
            celsius_radio.setSelected(true);
        }
        else
        {
            fahrenheit_radio.setSelected(true);
        }
    }
    public void radioButton_handle(ActionEvent event )
    {
        String id = ((Control)event.getTarget()).getId() ;
        if(id == fahrenheit_radio.getId())
        {
            celsius_radio.setSelected(false);
            fahrenheit_radio.setSelected(true);
            Settings.Celcius = false ;
        }
        else if(id == celsius_radio.getId())
        {
            fahrenheit_radio.setSelected(false);
            celsius_radio.setSelected(true);
            Settings.Celcius = true;
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
        File file = new File(Settings.CONFIG_PATH+"/city.json");
        City city = Settings.city;
        if(find_btn.getText().contains("Find"))
        {

            String search_element="";
            if(search_textbox.getText() != "")
            {
                String cityName = search_textbox.getText();
                find_btn.setText("Searching");
                search_textbox.setDisable(true);
                if(isNumric(search_textbox.getText()))
                {
                    search_element = "id";
                }
                else
                {
                    search_element = "name";
                }

                String finalSearch_element = search_element;
                new Thread()
                {
                    boolean found = false ;
                    String field_name = "" , field_country ="" ;
                    int id ;
                    String element = finalSearch_element;
                    @Override
                    public void run()
                    {
                        try
                        {
                            JsonParser parser = new JsonParser();
                            Object obj = parser.parse(new FileReader(Settings.CONFIG_PATH+"/city.json"));
                            JsonObject jsonObject = new Gson().fromJson(String.valueOf(obj), JsonObject.class);
                            JsonArray array = (JsonArray) jsonObject.get("cities");
                            JsonObject jsonObj;
                            for(int i = 0 ; i < array.size() ;i++)
                            {
                                jsonObj=(JsonObject) array.get(i);
                                field_name = jsonObj.get(element).getAsString();

                                if(cityName.equalsIgnoreCase(field_name))
                                {
                                    field_country = jsonObj.get("country").getAsString();
                                    id = jsonObj.get("id").getAsInt();
                                    field_name = jsonObj.get("name").getAsString();
                                    found = true;
                                    break;
                                }
                            }
                            city.setName(field_name);
                            city.setCountry(field_country);
                            city.setId(id);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run()
                            {

                                if(found)
                                {

                                    search_textbox.setText(city.getName()+" , "+city.getCountry());
                                    find_btn.setText("Add");
                                    clear_btn.setVisible(true);
                                }
                                else
                                {
                                    String temp = search_textbox.getText();
                                    search_textbox.setText(temp +" not found");
                                    find_btn.setText("Find");
                                    clear_btn.setVisible(true);
                                }
                            }
                        });
                    }
                }.start();
            }
            else
            {
                search_textbox.setPromptText("Enter something first");
            }
        }
        else if(find_btn.getText().contains("Add"))
        {
            try {
                String data = city.toString()+"\n";
                Settings.Cities.putIfAbsent(city.getId(),city.getName());
                FileUtils.writeStringToFile(new File(Settings.CONFIG_PATH+"/config.conf"),data , true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                find_btn.setText("Find");
                update();
                search_textbox.setText("");
                find_btn.setText("Find");
                clear_btn.setVisible(false);
                search_textbox.setDisable(false);
            }
        }




    }
    public void listview_handler(MouseEvent event)
    {
        if(event.getClickCount() == 2)
        {
            String index = cities_listview.getSelectionModel().getSelectedItem().toString();
            Settings.Cities.forEach((id , name ) ->
            {
                if(name == index)
                {
                    Settings.city.setId(id);
                    Settings.city.setName(name);
                }
            });
            Settings.refresh = true;
            Settings.ready = true;


        }

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

        try {
            Runtime rt = Runtime.getRuntime();
            String url = "https://openweathermap.org/find?q=";
            String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape","opera","links","lynx"};

            // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
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


}
