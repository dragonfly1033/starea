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

    public void gotoMapScreen() {
        webEngine.loadContent(backend.map_html, "text/html");
        webEngine.executeScript("startUp()");
    }

    public void gotoHomeScreen() {
        webEngine.loadContent(backend.index_html, "text/html");
        webEngine.executeScript("startUp()");
    }

    public int getHour() {
        return Backend.getHour();
    }

    public String getPath() {
        return path;
    }

    public int getScore(int day, int hour, double lat, double lon) throws Exception {
        System.out.print(day);
        System.out.println(hour);
        return backend.getScoreNew(day, hour, lat, lon);
    }

    public int getScoreHere(int day, int hour) throws Exception {
        System.out.print(day);
        System.out.println(hour);
        return backend.getScore(day, hour, backend.forecast.latitude, backend.forecast.longitude);
    }

    public int getScoreHereNow() throws Exception {
        return backend.getScore(Backend.getDayOfWeekIndex(), getHour(), backend.forecast.latitude, backend.forecast.longitude);
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

    public Remote(WebEngine we, Backend be) {
        webEngine = we;
        backend = be;
    }
}
