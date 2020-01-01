package com.Jweather;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        /*Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,1500,750);
*/
        //Settings set = new Settings();
        System.out.println("Connected to net ="+Connection.checkConnection());

        Settings settings = new Settings();
        City current = settings.getDefaultCity();
        GetWeatherInfo getWeatherInfo = new GetWeatherInfo();
        getWeatherInfo.fetchWeatherInfo(current, settings.getAPI());
        Weather weather = current.getCurrentWeather();
        System.out.println("City :"+current.getName());
        System.out.println("Today Weather:");
        System.out.println(weather.getDegree());
        System.out.println(weather.getWeather());
        System.out.println(weather.getDay());

        System.exit(0);

        //ShowWeather s = new ShowWeather();

        /*primaryStage.setTitle("Jweather");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1500);
        primaryStage.show();
*/
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
