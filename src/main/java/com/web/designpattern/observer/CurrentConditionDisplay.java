package com.web.designpattern.observer;

public class CurrentConditionDisplay implements Observer, DispalyElement {

    private float temperature;
    private float humidity;
    private float pressure;
    private Subject weatherData;

    public CurrentConditionDisplay(Subject weatherData) {
        //记录订阅的主题， 便于取消注册
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("temp: " + temperature + ", humidity: " + humidity + ", pressure: " +
                pressure);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
}
