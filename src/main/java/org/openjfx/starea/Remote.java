package org.openjfx.starea;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
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

    public String getNewBestLocation(int radius) throws Exception {
        var latlon = Backend.getLowestLightPollution(backend.forecast.latitude, backend.forecast.longitude, radius);
        int score = getScore(0, 23, latlon.getKey(), latlon.getValue());

        String imgUrl = MapGenerator.generateMapImageURL(600, 600, backend.forecast.latitude, backend.forecast.longitude, latlon.getKey(), latlon.getValue());
        String hrefUrl = MapGenerator.getMapsURL(latlon.getKey(), latlon.getValue());

        return "{ \"href\": \"" + hrefUrl + "\"," +
                "\"img\": \"" + imgUrl + "\"," +
                "\"score\": " + score +
                "}";
    }

    public void openBrowserWindow(String url) {
        Main.showDocument(url);
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
