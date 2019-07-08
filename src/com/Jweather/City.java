package com.Jweather;

public class City
{
    private String name ;
    private String country;
    private int id ;


    @Override
    public String toString()
    {
        return "City name :"  + name + " , Country :" + country + " , id:"+id;
    }
}
