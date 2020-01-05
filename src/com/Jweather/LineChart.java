package com.Jweather;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.awt.*;

public class LineChart
{
    private Pane layout ;
    private Line[] lines ;
    private Circle[] circles ;
    private Label[] tempertures;
    private Label[] times;
    private String[] labels ;
    private int[] coor ;
    private int margin = 10 ;
    private int pading = 10 ;
    private int[] x1 ;
    private int[] x2 ;
    private int[] y1 ;
    private int[] y2;
    int x_distance ;
    int y_distance ;

    LineChart(Pane pane , int[] x , String[] labels)
    {
        layout = pane ;
        coor = x ;
        this.labels = labels;
        int len = coor.length;
        lines = new Line[len];
        times = new Label[len];
        x1 = new int[len];
        x2 = new int[len];
        y1 = new int[len];
        y2 = new int[len];
    }

    boolean drawChart()
    {
        dataBinding();
        int x = 0 ;
        int layoutHeight = (int)layout.getHeight();
        for(int i = 0 ; i < coor.length ; i++)
        {
            lines[i] =  new Line();
            lines[i].setEndX(x_distance-margin);
            lines[i].setEndY(-(layoutHeight - (margin + pading)));
            lines[i].setStartX(x_distance-margin);
            lines[i].setStartY(0 - pading*2);
            lines[i].setLayoutY(layoutHeight - margin);
            lines[i].setLayoutX(x);
            lines[i].setStroke(Color.WHITE);
            lines[i].setOpacity(1.0);
            layout.getChildren().add(lines[i]);

            x+=x_distance;

            times[i] = new Label(labels[i]);
            times[i].setPrefWidth(50);
            times[i].setLayoutX(x - 20);
            times[i].setLayoutY(layoutHeight - (margin + pading));
            times[i].setTextFill(Color.WHITE);
            layout.getChildren().add(times[i]);
        }
        return true;
    }
    void dataBinding()
    {
        int length = coor.length;
        x_distance =(int) (layout.getWidth() / length) - margin ;
        y_distance =(int) layout.getHeight() / 2 ;
        // x2 = x1 + length
        x1[0] = 0 ;
        for(int i = 0 ; i < coor.length ; i++)
        {
            y2[i] = y_distance - coor[i];
            y1[i] = y2[i] - y_distance;

            x2[i] = x1[i] + x_distance;
            x1[i] = x2[i] - x_distance;
        }

    }
}
