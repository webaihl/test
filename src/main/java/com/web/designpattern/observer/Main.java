package com.web.designpattern.observer;

public class Main {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        //任意增加你的展示板(订阅者)
        CurrentConditionDisplay conditionDisplay =
                new CurrentConditionDisplay(weatherData);
        StatisticsDisplay statisticsDisplay =
                new StatisticsDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30);
        weatherData.removeObserver(statisticsDisplay);
        weatherData.setMeasurements(82, 67, 32);
        weatherData.setMeasurements(84, 69, 34);

    }
}
