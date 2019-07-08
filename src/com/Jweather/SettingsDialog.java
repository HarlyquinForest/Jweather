package com.Jweather;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.jfoenix.controls.*;
import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXComboBox;


import javafx.event.ActionEvent;
import javafx.scene.control.Control;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;


public class SettingsDialog
{
    private String[] Cities ;

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
    private JFXComboBox cities_combo;

    @FXML
    public void initialize()
    {
        ArrayList<String> cities = Settings.City_List;
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
        }
        else if(id == celsius_radio.getId())
        {
            fahrenheit_radio.setSelected(false);
        }
    }

    public void find_btn(ActionEvent event)
    {
        File file = new File(Settings.CONFIG_PATH+"/city.json");
        if(search_textbox.getText() != "")
        {
            String cityName = search_textbox.getText();
            find_btn.setText("Searching");
            String fieldname = "" ;
            try
            {
                JsonReader reader = new JsonReader(new FileReader(Settings.CONFIG_PATH+"/city.json"));
                reader.beginObject();
                while(reader.hasNext())
                {
                    JsonToken token = reader.peek();

                    if(token.equals(JsonToken.NAME))
                    {
                        fieldname = reader.nextName();

                    }
                    if("name".equals(fieldname))
                    {
                        token = reader.peek();
                        if(reader.nextString() == cityName)
                            System.out.println("Found : "+cityName);
                    }

                }
                reader.endObject();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            search_textbox.setPromptText("Enter something first");
        }

    }

    public void save_btn(ActionEvent event)
    {

    }

}
