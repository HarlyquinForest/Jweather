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

        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setWallpaper(scene);

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
