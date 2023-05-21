package org.openjfx.starea;

import javafx.scene.web.WebEngine;
import org.json.JSONObject;

public class Remote {
    private WebEngine webEngine;
    private Backend backend;
    private String path;

    public void setDay() {
        int day = Backend.getDayOfWeekIndex();
        webEngine.executeScript("setDay("+day+")");
    }

    public int getHour() {
        return Backend.getHour();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String val) {
        path = val;
    }

    public String getWeather() {
        JSONObject weather = new JSONObject();
        weather.put("daily", backend.forecast.dailyData);
        weather.put("hourly", backend.forecast.hourlyData);
        System.out.println(weather);
        return weather.toString();
    }

    public String getWeatherIcon(boolean isDay, int weatherCode) {
        return Backend.getWeatherIcon(isDay, weatherCode);
    }

    public void log(String text) {
        System.out.println(text);
    }
    public void logJSON(JSONObject text) {
        System.out.println("lanakls");
        System.out.println(text);
        System.out.println(text.toString());
    }

    public Remote(WebEngine we, Backend be) {
        webEngine = we;
        backend = be;
    }
}
