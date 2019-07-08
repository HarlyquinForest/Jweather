package com.Jweather;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
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

import java.awt.*;
import java.io.File;
import java.net.URL;

public class Controller
{
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
    private ImageView today_weather_icon;
    @FXML
    private ImageView day1_weather_icon ;
    @FXML
    private ImageView day2_weather_icon ;
    @FXML
    private ImageView day3_weather_icon ;
    @FXML
    private ImageView day4_weather_icon ;
    @FXML
    private ImageView day5_weather_icon ;
    @FXML
    private ComboBox interval_combo;

    private boolean update = Settings.ready ;
    private int sleep = 1000;

    @FXML
    public void initialize() {
        new Thread() {
            public void run() {
                System.out.println("By Thread");
                while (true)
                {
                    System.out.println(currentThread() + "" + update + " sleep " + sleep);
                    update = Settings.ready;
                    try {
                        sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(sleep != 10000)
                        sleep += 100;
                    if(update)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Update();
                                Settings.ready = false ;
                                System.out.println("Updated");
                            }
                        });
                    }
                }

            }
        }.start();
        /*ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Hello");
        list.add("10m");
        interval_combo.setItems(list);*/

    }
    private void Update()
    {
        ShowWeather w = new ShowWeather();
        String[] temp = w.showCurrnet().split("-");
        Today_degree.setText(temp[0]);
        humidity_lbl.setText(temp[1]+"%");
        wind_lbl.setText(temp[2]+"m/s");
        weather_name_lbl.setText(temp[3]);
        String today_icon_ = "/source/Weather icons/"+temp[4]+".png";
        Image today_icon = new Image(getClass().getResourceAsStream(today_icon_));
        today_weather_icon.setImage(today_icon);
        current_location_lbl.setText(temp[5]);

        String[] days = w.Days().split("-");
        day1_lbl.setText(days[0].replaceFirst("DAY",""));
        day2_lbl.setText(days[1].replaceFirst("DAY",""));
        day3_lbl.setText(days[2].replaceFirst("DAY",""));
        day4_lbl.setText(days[3].replaceFirst("DAY",""));
        day5_lbl.setText(days[4].replaceFirst("DAY",""));



        String[] tempDays = w.showDays().replaceAll("sky is " , "").split("-");

        for ( int i = 0 ; i < 9 ; i++)
            tempDays[i] = WordUtils.capitalize(tempDays[i]);

        weather_day1_lbl.setText(tempDays[0]);
        day1_temp_lbl.setText(tempDays[1]);
        day1_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+tempDays[2]+".png")));

        weather_day2_lbl.setText(tempDays[3]);
        day2_temp_lbl.setText(tempDays[4]);
        day2_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+tempDays[5]+".png")));

        weather_day3_lbl.setText(tempDays[6]);
        day3_temp_lbl.setText(tempDays[7]);
        day3_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+tempDays[8]+".png")));

        weather_day4_lbl.setText(tempDays[9]);
        day4_temp_lbl.setText(tempDays[10]);
        day4_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+tempDays[11]+".png")));

        weather_day5_lbl.setText(tempDays[12]);
        day5_temp_lbl.setText(tempDays[13]);
        day5_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+tempDays[14]+".png")));

    }
    public void NextCityMouseHandler(ActionEvent event)
    {
        //Main.scene.getStylesheets().remove(0);
        //Main.scene.getStylesheets().add(ThemeSummer);



    }
    public void PrivCityMouseHandler(ActionEvent event)
    {
        //Main.scene.getStylesheets().remove(0);
        //Main.scene.getStylesheets().add(ThemeFall);
    }
    public void Settings_btn_Clicked(ActionEvent event )
    {
        try{
            Parent settings = FXMLLoader.load(getClass().getResource("SettingsDialog.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            stage.setScene(new Scene(settings , 400 , 500));
            stage.setResizable(false);
            stage.showAndWait();

        }catch (Exception e )
        {
            //e.printStackTrace();
            e.getCause();
        }
    }
    public void updatebtn()
    {
        default_lbl();
        sleep = 1000 ;
        Weather weather = new Weather();
        weather.start();
    }
    public void default_lbl()
    {
        Today_degree.setText("--");
        humidity_lbl.setText("--");
        wind_lbl.setText("--");
        weather_name_lbl.setText("--");
        current_location_lbl.setText("--");

        day1_lbl.setText("--");
        day2_lbl.setText("--");
        day3_lbl.setText("--");
        day4_lbl.setText("--");
        day5_lbl.setText("--");

        weather_day1_lbl.setText("--");
        day1_temp_lbl.setText("--");

        weather_day2_lbl.setText("--");
        day2_temp_lbl.setText("--");

        weather_day3_lbl.setText("--");
        day3_temp_lbl.setText("--");

        weather_day4_lbl.setText("--");
        day4_temp_lbl.setText("--");

        weather_day5_lbl.setText("--");
        day5_temp_lbl.setText("--");
    }
}
