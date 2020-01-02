package com.Jweather;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,1500,750);
        System.out.println("Connected to net ="+Connection.checkConnection());

        Settings settings = new Settings();
        City current = settings.getDefaultCity();
        GetWeatherInfo getWeatherInfo = new GetWeatherInfo(current);
        getWeatherInfo.fetchWeatherInfo(settings.getAPI());

        ShowWeather showWeather = new ShowWeather();
        ShowWeather.setCity(current);
        System.out.println(showWeather.getCity().toString());
        Weather weather = current.getCurrentWeather();
        System.out.println("City :"+current.getName());
        System.out.println("Last Update :"+current.getLastUpdate());
        System.out.println("Today Weather:");
        System.out.println("Min "+weather.getMin());
        System.out.println("Max "+weather.getMax());
        System.out.println(weather.getDegree());
        System.out.println(weather.getWeather());
        System.out.println(weather.getDay());
        System.out.println(weather.getSpeed());
        System.out.println(weather.getWindName());
        System.out.println(weather.getWindDirection());

        Weather[] days = current.getDaysForecast();
        int d = 1 ;
        for(Weather w :days)
        {
            System.out.println("------");
            System.out.println("Day"+d++);
            System.out.println(w.getDegree());
            System.out.println(w.getWeather());
            System.out.println(w.getDay());
            System.out.println(w.getSpeed());
            System.out.println(w.getWindName());
            System.out.println(w.getWindDirection());
        }


        primaryStage.setTitle("Jweather");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1500);
        primaryStage.show();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }



    public static void main(String[] args)
    {
        launch(args);
    }
}
