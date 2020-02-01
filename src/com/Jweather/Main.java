package com.Jweather;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,1500,750 );
        Image icon = new Image(Main.class.getResourceAsStream("/source/icon.png"));

        primaryStage.setTitle("Jweather");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1500);
        primaryStage.getIcons().add(icon);
        primaryStage.show();

        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setWallpaper(scene);
        new Thread(()->
        {
            System.out.println("Main thread started");
            System.out.println("Connected to net ="+Connection.ok);
            Settings settings = new Settings() ;
            GetWeatherInfo getWeatherInfo = new GetWeatherInfo(Settings.defaultCity , settings.getAPI());

            System.out.println("Main thread finished");
        }).start();

        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }



    public static void main(String[] args)
    {
        launch(args);
    }
}
