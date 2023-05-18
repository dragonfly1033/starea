package org.openjfx.starea;

import javafx.scene.web.WebEngine;
import org.json.JSONObject;

public class Remote {
    private WebEngine webEngine;
    private Backend backend;

    public void setDay() {
        int day = Backend.getDayOfWeekIndex();
        webEngine.executeScript("setDay("+day+")");
    }

    public String getWeather() {
        JSONObject weather = new JSONObject();
        weather.put("daily", backend.forecast.dailyData);
        weather.put("hourly", backend.forecast.hourlyData);
        return weather.toString();
    }

    public void log(String text) {
        System.out.println(text);
    }

    public Remote(WebEngine we, Backend be) {
        webEngine = we;
        backend = be;
    }
}
