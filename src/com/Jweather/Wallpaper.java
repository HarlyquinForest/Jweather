package com.Jweather;

import javafx.scene.Scene;

import java.time.LocalDate;

public class Wallpaper
{
    private String ThemeSummer = getClass().getResource("/source/summer.css").toExternalForm();
    private String ThemeFall = getClass().getResource("/source/fall.css").toExternalForm();
    private String ThemeWinter = getClass().getResource("/source/winter.css").toExternalForm();
    private String ThemeSpring = getClass().getResource("/source/spring.css").toExternalForm();

    public void setWallpaper(Scene scene) {
        String Month = LocalDate.now().getMonth().toString();
        switch (Month) {
            case "MARCH":
            case "APRIL":
            case "MAY":
                scene.getStylesheets().add(ThemeSpring);
                break;
            case "JUNE":
            case "JULY":
            case "AUGUST":
                scene.getStylesheets().add(ThemeSummer);
                break;
            case "SEPTEMBER":
            case "OCTOBER":
            case "NOVEMBER":
                scene.getStylesheets().add(ThemeFall);
                break;
            case "DECEMBER":
            case "JANUARY":
            case "FEBRUARY":
                scene.getStylesheets().add(ThemeWinter);
        }
    }
}
