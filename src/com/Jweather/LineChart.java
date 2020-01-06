package com.Jweather;

import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

class LineChart
{
    private Pane layout ;
    private Line[] bars;
    private Line[] lines ;
    private Circle[] circles ;
    private Label[] temperatures;
    private Label[] times;
    private String[] x_axis_label;
    private int[] coordinates;
    private int margin = 10 ;
    private int padding = 10 ;
    private int[] x1 ;
    private int[] x2 ;
    private int[] y1 ;
    private int[] y2;
    private int x_distance ;
    private int y_distance ;
    private String[] toolTips;

    LineChart(Pane pane , int[] x , String[] labels)
    {
        layout = pane ;
        coordinates = x ;
        x_axis_label = labels;
        int len = coordinates.length;
        bars = new Line[len];
        lines = new Line[len];
        times = new Label[len];
        circles = new Circle[len];
        temperatures = new Label[len];
        x1 = new int[len];
        x2 = new int[len];
        y1 = new int[len];
        y2 = new int[len];
    }

    LineChart(Pane pane , int[] x , String[] labels , String[] tips)
    {
        layout = pane ;
        coordinates = x ;
        x_axis_label = labels;
        int len = coordinates.length;
        bars = new Line[len];
        lines = new Line[len];
        times = new Label[len];
        circles = new Circle[len];
        temperatures = new Label[len];
        toolTips = tips;
        x1 = new int[len];
        x2 = new int[len];
        y1 = new int[len];
        y2 = new int[len];
    }

    void  drawChart()
    {
        layout.getChildren().clear();
        dataBinding();
        int x = 0 ;
        int layoutHeight = (int)layout.getHeight();
        for(int i = 0; i < coordinates.length ; i++)
        {
            bars[i] =  new Line(x_distance-margin , 0- padding *2 , x_distance-margin ,-(layoutHeight - (margin + padding)) );
            bars[i].setLayoutY(layoutHeight - margin);
            bars[i].setLayoutX(x);
            bars[i].setStroke(Color.WHITE);
            bars[i].setOpacity(0.3);

            x+=x_distance;

            times[i] = new Label(x_axis_label[i]);
            times[i].setPrefWidth(50);
            times[i].setLayoutX(x - 20);
            times[i].setLayoutY(layoutHeight - (margin + padding));
            times[i].setTextFill(Color.WHITE);
            if(toolTips != null ) {
                times[i].setTooltip(new Tooltip(toolTips[i]));
            }

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

            temperatures[i] = new Label(coordinates[i]+"");
            temperatures[i].setTextFill(Color.WHITE);
            if(i == 0 )
                temperatures[i].setLayoutX(x_distance - margin);
            else
                temperatures[i].setLayoutX(x2[i - 1] - 10);
            temperatures[i].setLayoutY(y1[i] - 30);

            layout.getChildren().add(circles[i]);
            layout.getChildren().add(bars[i]);
            layout.getChildren().add(times[i]);
            layout.getChildren().add(lines[i]);
            layout.getChildren().add(temperatures[i]);
        }
    }
    private void dataBinding()
    {
        int length = coordinates.length;
        x_distance =(int) (layout.getWidth() / length) - margin ;
        y_distance =(int) layout.getHeight() / 2 ;
        // x2 = x1 + length
        System.out.println("Coordinates");
        x1[0] = x_distance - margin ;
        for(int i = 0; i < coordinates.length ; i++)
        {
            if(i+1 < coordinates.length)
                y2[i] = y_distance - coordinates[i+1];
            else
                y2[i] = y_distance - coordinates[i];
            y1[i] = y_distance - coordinates[i];
            if(i - 1 >= 0 )
            {
                x1[i] = x1[i - 1] + x_distance;
            }
            x2[i] = x1[i] + x_distance;
        }

    }
}
