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
        //update();

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
    }

    public boolean isNumric(String str)
    {
        return true; 
    }
    public void find_btn(ActionEvent event)
    {

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
