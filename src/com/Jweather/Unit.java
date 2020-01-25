package com.Jweather;

public enum Unit
{
    Celsius("metric"), Fahrenheit("imperial");
    private final String name ;

    Unit(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
