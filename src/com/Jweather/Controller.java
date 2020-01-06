package com.Jweather;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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

    int sleep = 100 ;

    @FXML
    public void initialize()
    {
        new Thread(() ->
        {
        City current;
            while (true)
            {
                current = Settings.defaultCity;
                if(GetWeatherInfo.weatherReady() && current != null)
                {
                    System.out.println("ready");
                    GetWeatherInfo.setReady(false);
                    final City city = current;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            updateLabel(city);
                        }
                    });
                }
//                System.out.println("Waiting thread is running");
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sleep+=100;
                if(sleep>=5000) {
                    System.out.println("5 Seconds sleep");
                    // going to deep sleep until user try to see other city
                }

            }
        }).start();

    }

    private void updateLabel(City city)
    {
        ShowWeather showWeather = new ShowWeather();
        ShowWeather.setCity(city);
        Weather w = showWeather.getWeather();
        setCurrentWeather(w);
        setDailyWeather(city.getDaysForecast());
        LineChart lineChart = new LineChart(chart_canvas , getDaysHourlyTemps(city.getDaysHourly()) , getDaysHourlyTimes(city.getDaysHourly()) , getDaysToolTips(city.getDaysHourly()));
        lineChart.drawChart();
    }

    private void setCurrentWeather(Weather w)
    {
        Today_degree.setText(w.getDegree()+"°");
        humidity_lbl.setText(w.getHumidity()+"%");
        wind_lbl.setText(w.getSpeed()+"mps");
        weather_name_lbl.setText(w.getWeather());
        today_weather_icon.setImage(new Image(getClass().getResourceAsStream("/source/Weather icons/"+w.getIcon()+".png")));
        current_location_lbl.setText(ShowWeather.getCity().getName()+","+ShowWeather.getCity().getCountry());
    }

    private void setDailyWeather(Weather[] daily)
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

    private int[] getDaysHourlyTemps(Weather[] forecast)
    {
        int[] temps = new int[forecast.length];

        for(int i = 0 ; i < forecast.length ; i++)
        {
            temps[i] = forecast[i].getDegree();
        }
        return temps;
    }

    private String[] getDaysHourlyTimes(Weather[] forecast)
    {
        System.out.println("---");
        String[] times = new String[forecast.length];
        for(int i = 0 ; i < forecast.length ; i++) {
            times[i] = forecast[i].getTime();
        }
        return times;
    }

    private String[] getDaysToolTips(Weather[] forecast)
    {
        String[] tips = new String[forecast.length];
        for(int i = 0 ; i < forecast.length ; i++) {
            tips[i] = forecast[i].getDay();
        }
            return tips;
    }

    @FXML
    public void PrivCityMouseHandler()
    {
        sleep = 100 ;
    }
    @FXML
    public void NextCityMouseHandler()
    {
        sleep = 100;
    }

    @FXML
    public void updatebtn(){}
    @FXML
    public void Settings_btn_Clicked(){}

}
