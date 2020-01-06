package com.Jweather;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class LineChart
{
    private Pane layout ;
    private Line[] bars;
    private Line[] lines ;
    private Circle[] circles ;
    private Label[] tempertures;
    private Label[] times;
    private String[] x_axis_label;
    private int[] coor ;
    private int margin = 10 ;
    private int pading = 10 ;
    private int[] x1 ;
    private int[] x2 ;
    private int[] y1 ;
    private int[] y2;
    private int x_distance ;
    private int y_distance ;

    LineChart(Pane pane , int[] x , String[] labels)
    {
        layout = pane ;
        coor = x ;
        x_axis_label = labels;
        int len = coor.length;
        bars = new Line[len];
        lines = new Line[len];
        times = new Label[len];
        circles = new Circle[len];
        tempertures = new Label[len];
        x1 = new int[len];
        x2 = new int[len];
        y1 = new int[len];
        y2 = new int[len];
    }

    boolean drawChart()
    {
        layout.getChildren().clear();
        dataBinding();
        int x = 0 ;
        int layoutHeight = (int)layout.getHeight();
        for(int i = 0 ; i < coor.length ; i++)
        {
            bars[i] =  new Line(x_distance-margin , 0-pading*2 , x_distance-margin ,-(layoutHeight - (margin + pading)) );
            bars[i].setLayoutY(layoutHeight - margin);
            bars[i].setLayoutX(x);
            bars[i].setStroke(Color.WHITE);
            bars[i].setOpacity(0.3);

            x+=x_distance;

            times[i] = new Label(x_axis_label[i]);
            times[i].setPrefWidth(50);
            times[i].setLayoutX(x - 20);
            times[i].setLayoutY(layoutHeight - (margin + pading));
            times[i].setTextFill(Color.WHITE);

            lines[i] = new Line(x1[i],y1[i],x2[i],y2[i]);
            lines[i].setStroke(Color.WHITE);
            lines[i].setStrokeWidth(2);
            lines[i].setOpacity(0.5);

            circles[i] = new Circle();
            circles[i].setRadius(3);
            if(i == 0 )
                circles[i].setCenterX(x_distance-margin);
            else
                circles[i].setCenterX(x2[i-1]);
            circles[i].setCenterY(y1[i]);
            circles[i].setFill(Color.WHITE);

            tempertures[i] = new Label(coor[i]+"");
            tempertures[i].setTextFill(Color.WHITE);
            if(i == 0 )
                tempertures[i].setLayoutX(x_distance - margin);
            else
                tempertures[i].setLayoutX(x2[i - 1] - 10);
            tempertures[i].setLayoutY(y1[i] - 30);

            layout.getChildren().add(circles[i]);
            layout.getChildren().add(bars[i]);
            layout.getChildren().add(times[i]);
            layout.getChildren().add(lines[i]);
            layout.getChildren().add(tempertures[i]);
        }

        return true;
    }
    void dataBinding()
    {
        int length = coor.length;
        x_distance =(int) (layout.getWidth() / length) - margin ;
        y_distance =(int) layout.getHeight() / 2 ;
        // x2 = x1 + length
        System.out.println("Coordinates");
        x1[0] = x_distance - margin ;
        for(int i = 0 ; i < coor.length ; i++)
        {
            if(i+1 < coor.length)
                y2[i] = y_distance - coor[i+1];
            else
                y2[i] = y_distance - coor[i];
            y1[i] = y_distance - coor[i];
            if(i - 1 >= 0 )
            {
                x1[i] = x1[i - 1] + x_distance;
            }
            x2[i] = x1[i] + x_distance;
        }

    }
}
