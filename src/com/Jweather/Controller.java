package com.Jweather;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.commons.lang3.text.WordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

public class Controller
{
    public Controller()
    {

    }
    @FXML
    private Label h1,h2,h3,h4,h5,h6,h7,h8;
    @FXML
    private Label day_temp , night_temp , avg_temp;
    @FXML
    private Label Today_degree ,  humidity_lbl , wind_lbl , weather_name_lbl , current_location_lbl;
    @FXML
    private Label day1_lbl,day2_lbl,day3_lbl,day4_lbl, day5_lbl;
    @FXML
    private Label day1_temp_lbl, day2_temp_lbl, day3_temp_lbl,day4_temp_lbl,day5_temp_lbl;
    @FXML
    private Label weather_day1_lbl, weather_day2_lbl,weather_day3_lbl, weather_day4_lbl,weather_day5_lbl;
    @FXML
    private Label last_update_lbl;
    @FXML
    private ImageView today_weather_icon,day1_weather_icon ,day2_weather_icon ,day3_weather_icon ,day4_weather_icon ,day5_weather_icon ;
    @FXML
    private Pane chart_canvas;
    @FXML
    private Button refresh_btn;

    @FXML
    public void PrivCityMouseHandler(){}
    @FXML
    public void NextCityMouseHandler(){}

    @FXML
    public void updatebtn(){}
    @FXML
    public void Settings_btn_Clicked(){}

}
