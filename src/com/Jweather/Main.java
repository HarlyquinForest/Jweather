package com.Jweather;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Main extends Application {

    private String ThemeSummer = getClass().getResource("/source/summer.css").toExternalForm();
    private String ThemeFall = getClass().getResource("/source/fall.css").toExternalForm();
    private String ThemeWinter = getClass().getResource("/source/winter.css").toExternalForm();
    private String ThemeSpring = getClass().getResource("/source/spring.css").toExternalForm();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Weather w = new Weather();
        w.getWeather();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,1500,750);
        ShowWeather s = new ShowWeather();
        s.setWallpaper(scene);
        primaryStage.setTitle("Jweather");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public static void main(String[] args)
    {
        launch(args);
    }
}
