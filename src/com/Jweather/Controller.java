package com.Jweather;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;

import java.awt.*;

public class Controller
{
    private ShowWeather w = new ShowWeather();
    public Controller()
    {

    }

    @FXML
    private Label Today_degree;
    @FXML
    private  Label humidity_lbl;
    @FXML
    private  Label wind_lbl;
    @FXML
    private Label weather_name_lbl;
    @FXML
    private  Label current_location_lbl;
    @FXML
    private Label day1_lbl;
    @FXML
    private Label day2_lbl;
    @FXML
    private Label day3_lbl;
    @FXML
    private Label day4_lbl;
    @FXML
    private Label day5_lbl;
    @FXML
    private Label day1_temp_lbl;
    @FXML
    private Label day2_temp_lbl;
    @FXML
    private Label day3_temp_lbl;
    @FXML
    private Label day4_temp_lbl;
    @FXML
    private Label day5_temp_lbl;
    @FXML
    private Label weather_day1_lbl;
    @FXML
    private Label weather_day2_lbl;
    @FXML
    private Label weather_day3_lbl;
    @FXML
    private Label weather_day4_lbl;
    @FXML
    private Label weather_day5_lbl;


    @FXML
    public void initialize()
    {
        String[] temp = w.showCurrnet().split("-");
        Today_degree.setText(temp[0]);
        humidity_lbl.setText(temp[1]+"%");
        wind_lbl.setText(temp[2]+"m/s");
        weather_name_lbl.setText(temp[3]);
        current_location_lbl.setText(temp[4]);

        String[] days = w.Days().split("-");
        day1_lbl.setText(days[0].replaceFirst("DAY",""));
        day2_lbl.setText(days[1].replaceFirst("DAY",""));
        day3_lbl.setText(days[2].replaceFirst("DAY",""));
        day4_lbl.setText(days[3].replaceFirst("DAY",""));
        day5_lbl.setText(days[4].replaceFirst("DAY",""));

        String[] tempDays = w.showDays().split("-");
        for(int i = 0 ; i < 5 ;)
        {
            weather_day1_lbl.setText(tempDays[i]);
            day1_temp_lbl.setText(tempDays[++i]);
            weather_day2_lbl.setText(tempDays[++i]);
            day2_temp_lbl.setText(tempDays[++i]);
            weather_day3_lbl.setText(tempDays[++i]);
            day3_temp_lbl.setText(tempDays[++i]);
            weather_day4_lbl.setText(tempDays[++i]);
            day4_temp_lbl.setText(tempDays[++i]);
            weather_day5_lbl.setText(tempDays[++i]);
            day5_temp_lbl.setText(tempDays[++i]);

        }

    }


    public void NextCityMouseHandler(MouseEvent event)
    {
        //Main.scene.getStylesheets().remove(0);
        //Main.scene.getStylesheets().add(ThemeSummer);



    }
    public void PrivCityMouseHandler(MouseEvent event)
    {
        //Main.scene.getStylesheets().remove(0);
        //Main.scene.getStylesheets().add(ThemeFall);
    }

}
