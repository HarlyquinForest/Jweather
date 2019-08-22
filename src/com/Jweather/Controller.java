package com.Jweather;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
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
    public Controller()
    {

    }
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

    private boolean update = Settings.ready ;
    private int sleep = 1000;
    private int[] temperture = new int[8];

    @FXML
    public void initialize() {
        new Thread() {
            public void run() {
                System.out.println("By Thread");
                while (true)
                {
                    //System.out.println(currentThread() + "" + update + " sleep " + sleep);
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
                                update = Settings.ready;
                                System.out.println("Updated");

                            }
                        });
                    }
                    if(Settings.refresh)
                    {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Weather weather = new Weather();
                                weather.GetWeather();

                                Update();
                                System.out.println("Updated refrsh");
                            }
                        });
                    }
                }

            }
        }.start();


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

        last_update_lbl.setText("Last Update "+temp[6]);

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

        int day = Integer.parseInt(tempDays[15]) , night = Integer.parseInt(tempDays[16]) , avg = (day + night)/2 ;
        day_temp.setText(tempDays[15]+"°");
        night_temp.setText(tempDays[16]+"°");
        avg_temp.setText(avg+"°");


        String[] t = w.forecast().split("-");

        h1.setText(t[0]);h2.setText(t[2]);h3.setText(t[4]);h4.setText(t[6]);h5.setText(t[8]);h6.setText(t[10]);h7.setText(t[12]);h8.setText(t[14]);

        int c = 0 ;
        for(int i = 1 ; i < 16 ; i+=2 , c++)
        {
            temperture[c] =Integer.parseInt(t[i]);
        }

        init_chart();
    }
    public void NextCityMouseHandler(MouseEvent event)
    {
        //System.out.println(Settings.Cities);
        int key = nextKey();

        default_lbl();

        newCity(key);


    }
    public void PrivCityMouseHandler(MouseEvent event)
    {
        //System.out.println(Settings.Cities);
        int key = privKey();

        default_lbl();

        newCity(key);
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
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    try {
                        Settings.save();
                        //System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e )
        {
            e.printStackTrace();
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
        day_temp.setText("--");
        night_temp.setText("--");
        avg_temp.setText("--");

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
    private void newCity(int key)
    {
        Settings.city.setId(key);
        Settings.city.setName(Settings.Cities.get(key));

        Settings.refresh = true;
        Settings.ready = true;

    }
    private int nextKey()
    {
        int next = 0 ;
        int temp = 0 ;
        int first = 0 ;
        Set<Integer> set = Settings.Cities.keySet();

        Iterator<Integer> itr = set.iterator();
        boolean n = false ;

        while(itr.hasNext())
        {
            if(first==0 && temp == 0 )
            {
                temp = itr.next();
                first = temp ;
            }
            else
                temp = itr.next();
            if(Settings.city.getId() == temp)
            {
                n  = true;
            }
            else if(n)
            {
                next = temp;
                n=false;
            }
        }
        if(next==0)
            next=first;
        System.out.println(next);
        return next;

    }
    private int privKey()
    {
        int priv = 0 ;
        int temp = 0 ;

        boolean p = false;
        Set<Integer> set = Settings.Cities.keySet();

        Iterator<Integer> itr = set.iterator();

        while(itr.hasNext())
        {
            temp = itr.next();
            if(Settings.city.getId() == temp)
            {
                p=true;
            }
            else if(!p)
            {
                priv = temp ;
            }

        }
        if(priv==0)
            priv=temp;
        System.out.println(priv);
        return priv;

    }
    private void init_chart()
    {
        chart_canvas.getChildren().clear();
        Line line[] = new Line[7];
        Label l[] = new Label[8];
        Circle circle[] = new Circle[8];

        Line bar = new Line();
        int x = 0 ;
        for(int i = 0 ; i < 8 ; i++)
        {
            bar.setEndX(72);
            bar.setEndY(-116);
            bar.setStartX(72);
            bar.setStartY(50);
            bar.setLayoutY(126);
            bar.setLayoutX(x);
            bar.setStroke(Color.WHITE);
            bar.setOpacity(0.3);
            chart_canvas.getChildren().add(bar);
            bar =  new Line();
            x+=87;
        }

        int startx , endx = -15 , starty =100 ,endy = temperture[0];
        for(int i = 0 ; i < 8 ; i++)
        {
            startx = endx;

            starty=endy;
            endy=  90 - temperture[i];

            endx = startx+87;
            endy+=10;

            if(i!=0)
            {
                line[i-1] = new Line();
                line[i-1].setStartY(starty);
                line[i-1].setEndY(endy);
                line[i-1].setStartX(startx);
                line[i-1].setEndX(endx);

                line[i-1].setStroke(Color.WHITE);
                line[i-1].setStrokeWidth(2);
                line[i-1].setOpacity(0.5);
                chart_canvas.getChildren().add(line[i-1]);
            }
            l[i] = new Label();
            circle[i] = new Circle();

            circle[i].setRadius(3);
            circle[i].setCenterY(endy);
            circle[i].setCenterX(endx);
            circle[i].setFill(Color.WHITE);

            l[i].setLayoutX(endx-12);
            if(endy>starty)
                l[i].setLayoutY(endy-30);
            else
                l[i].setLayoutY(endy+10);
            l[i].setText(temperture[i]+"°");
            l[i].setTextFill(Color.WHITE);

            chart_canvas.getChildren().add(l[i]);
            chart_canvas.getChildren().add(circle[i]);

        }

    }
}
