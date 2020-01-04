package com.Jweather;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;
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
    public Controller() {}
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
    public void initialize()
    {
        Settings settings = new Settings();
        City current = settings.getDefaultCity();
        GetWeatherInfo getWeatherInfo = new GetWeatherInfo(current , settings.getAPI());
        new Thread(() ->
        {
            while (!getWeatherInfo.weatherReady())
            {
                System.out.println("Waiting thread is running");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ready");
        }).start();
        //getWeatherInfo.fetchWeatherInfo(settings.getAPI());

        ShowWeather showWeather = new ShowWeather();
        ShowWeather.setCity(current);
        Weather w = showWeather.getWeather();
        setCurrentWeather(w);
        setDailyWeather(ShowWeather.getCity().getDaysForecast());
    }

    void setCurrentWeather(Weather w)
    {
        Today_degree.setText(w.getDegree()+"°");
        humidity_lbl.setText(w.getHumidity()+"%");
        wind_lbl.setText(w.getSpeed()+"mps");
        weather_name_lbl.setText(w.getWeather());
        today_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+w.getIcon()+".png")));
        current_location_lbl.setText(ShowWeather.getCity().getName()+","+ShowWeather.getCity().getCountry());
    }

    void setDailyWeather(Weather[] daily)
    {
        day1_lbl.setText(daily[0].getDay());
        day1_temp_lbl.setText(daily[0].getDegree()+"°");
        weather_day1_lbl.setText(daily[0].getWeather());
        weather_day1_lbl.setTooltip(new Tooltip(weather_day1_lbl.getText()));
        day1_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+daily[0].getIcon()+".png")));

        day2_lbl.setText(daily[1].getDay());
        day2_temp_lbl.setText(daily[1].getDegree()+"°");
        weather_day2_lbl.setText(daily[1].getWeather());
        weather_day2_lbl.setTooltip(new Tooltip(weather_day2_lbl.getText()));
        day2_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+daily[1].getIcon()+".png")));

        day3_lbl.setText(daily[2].getDay());
        day3_temp_lbl.setText(daily[2].getDegree()+"°");
        weather_day3_lbl.setText(daily[2].getWeather());
        weather_day3_lbl.setTooltip(new Tooltip(weather_day3_lbl.getText()));
        day3_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+daily[2].getIcon()+".png")));

        day4_lbl.setText(daily[3].getDay());
        day4_temp_lbl.setText(daily[3].getDegree()+"°");
        weather_day4_lbl.setText(daily[3].getWeather());
        weather_day4_lbl.setTooltip(new Tooltip(weather_day4_lbl.getText()));
        day4_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+daily[3].getIcon()+".png")));

        day5_lbl.setText(daily[4].getDay());
        day5_temp_lbl.setText(daily[4].getDegree()+"°");
        weather_day5_lbl.setText(daily[4].getWeather());
        weather_day5_lbl.setTooltip(new Tooltip(weather_day5_lbl.getText()));
        day5_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+daily[4].getIcon()+".png")));

    }

    @FXML
    public void PrivCityMouseHandler(){}
    @FXML
    public void NextCityMouseHandler(){}

    @FXML
    public void updatebtn(){}
    @FXML
    public void Settings_btn_Clicked(){}

}
