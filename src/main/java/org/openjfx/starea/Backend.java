package org.openjfx.starea;

import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.net.URL;
import java.time.LocalDate;


public class Backend {
    private static int deg = 10;
    private static double whole_map_bottom = -59.995775;
    private static double whole_map_top = 85.054167;
    private static double whole_map_right = 179.999856;
    private static double whole_map_left = -180.0;
    public Forecast forecast;

    public Backend() throws Exception {
        forecast = new Forecast();
    }

    public static int getDayOfWeekIndex(){
        return LocalDate.now().getDayOfWeek().getValue() -1;
    }

    public static String[] getDayList(){
        int todayIndex = getDayOfWeekIndex();
        String[] days = new String[7];
        days[(7  - todayIndex)%7] = "Mon";
        days[(8  - todayIndex)%7] = "Tue";
        days[(9  - todayIndex)%7] = "Wed";
        days[(10 - todayIndex)%7] = "Thu";
        days[(11 - todayIndex)%7] = "Fri";
        days[(12 - todayIndex)%7] = "Sat";
        days[(13 - todayIndex)%7] = "Sun";
        return days;
    }

    public static String interpretWeatherCode(int weatherCode){
        if (weatherCode == 0) { return "Clear Sky"; }
        else if (weatherCode == 1) { return "Mainly Clear"; }
        else if (weatherCode == 2) { return "Partly Cloudy"; }
        else if (weatherCode == 3) { return "Overcast"; }
        else if (weatherCode == 45) { return "Fog"; }
        else if (weatherCode == 48) { return "Depositing Rime Fog"; }
        else if (weatherCode == 51) { return "Light Drizzle"; }
        else if (weatherCode == 53) { return "Moderate Drizzle"; }
        else if (weatherCode == 55) { return "Dense Drizzle"; }
        else if (weatherCode == 56) { return "Light Freezing Drizzle"; }
        else if (weatherCode == 57) { return "Dense Freezing Drizzle"; }
        else if (weatherCode == 61) { return "Slight Rain"; }
        else if (weatherCode == 63) { return "Moderate Rain"; }
        else if (weatherCode == 65) { return "Heavy Rain"; }
        else if (weatherCode == 66) { return "Light Freezing Rain"; }
        else if (weatherCode == 67) { return "Heavy Freezing Rain"; }
        else if (weatherCode == 71) { return "Slight Snow Fall"; }
        else if (weatherCode == 73) { return "Moderate Snow Fall"; }
        else if (weatherCode == 75) { return "Heavy Snow Fall"; }
        else if (weatherCode == 77) { return "Snow Grains"; }
        else if (weatherCode == 80) { return "Slight Rain Showers"; }
        else if (weatherCode == 81) { return "Moderate Rain Showers"; }
        else if (weatherCode == 82) { return "Violent Rain Showers"; }
        else if (weatherCode == 85) { return "Slight Snow Showers"; }
        else if (weatherCode == 86) { return "Heavy Snow Showers"; }
        else if (weatherCode == 95) { return "Slight Thunderstorm"; }
        else if (weatherCode == 96) { return "Thunderstorm with Slight Hail"; }
        else if (weatherCode == 99) { return "Thunderstorm with Heavy Hail"; }
        else { return "No Description Found"; }
    }

    private static Pair<String, Pair<Integer, Integer>> getImgName(double lat, double lon) {
        int corner_lat = (int)Math.floor((lat - whole_map_bottom)/deg) * deg + (int)Math.floor(whole_map_bottom) + deg + 1;
        int corner_lon = (int)Math.floor((lon - whole_map_left)/deg) * deg + (int)Math.floor(whole_map_left);
        String ans = corner_lat + "_" + corner_lon + ".png";
        return new Pair(ans, new Pair(corner_lat, corner_lon));
    }

    private static Pair<Integer, Integer> latlon2pix(double lat, double lon, int map_width, int map_height, double map_left, double map_right, double map_top, double map_bottom) {
        int x = (int)Math.round(map_width * (lon - map_left) / (map_right - map_left));
        int y = (int)Math.round(map_height - map_height * (lat - map_bottom) / (map_top - map_bottom));
        return new Pair(x, y);
    }

    private static int getLightPollution(double lat, double lon) throws Exception {
        Pair<String, Pair<Integer, Integer>> name = getImgName(lat, lon);
        URL path = Backend.class.getResource("grid/"+name.getKey());
        BufferedImage img = ImageIO.read(path);
        int w = img.getWidth();
        int h = img.getHeight();
        DataBufferUShort buffer = (DataBufferUShort) img.getRaster().getDataBuffer();
        short[] arrayUShort = buffer.getData();
        double top = name.getValue().getKey();
        double left = name.getValue().getValue();
        Pair<Integer, Integer> coord = latlon2pix(lat, lon, w, h, left, left + 10, top, top - 10);
        int lightPollution = arrayUShort[coord.getKey() + coord.getValue() * w] & 0xffff;
        return lightPollution;
    }

    public int getScore(int day, int hour, double lat, double lon) throws Exception {
        if (forecast.isDay(day, hour)) { return 0; }
        double cloudCoverScore = Math.max(1-2*forecast.getCloudCover(day, hour), 0.0);
        double visibility = forecast.getVisibility(day, hour)/24140.0;
        double visibilityScore = (visibility < 0) ? 0 : Math.pow((visibility-0.8)/0.2, 2);
        double darkness = 1.0 - getLightPollution(lat, lon)/65535.0;
        double lightPollutionScore = 0.0;
        if (darkness <= 0.5) { lightPollutionScore = 0.0; }
        else if (darkness <= 0.8) { lightPollutionScore = darkness * 0.24167 - 0.12083; }
        else if (darkness <= 0.95) { lightPollutionScore = darkness * 1.13333 - 0.834167; }
        else if (darkness <= 0.98) { lightPollutionScore = darkness * 10.375 - 9.61375; }
        else if (darkness <= 0.999) { lightPollutionScore = darkness * 15.65789 - 14.7910; }
        else if (darkness <= 1.0) { lightPollutionScore = darkness * 148.75 - 147.75; }

        double score = (cloudCoverScore * 1.0 + visibilityScore * 1.0 + lightPollutionScore * 1.0)/3.0;
        int rating = (int)Math.round(score * 5);

        return rating;
    }

    private String getWeatherIcon(boolean isDay, int weatherCode)
    {
        switch (weatherCode) {
            case 0:
            case 1:
                return isDay ? "wi-day-sunny" : "wi-night-clear";
            case 2:
            case 3:
                return isDay ? "wi-day-sunny-overcast" : "wi-night-alt-partly-cloudy";
            case 45:
            case 48:
                return isDay ? "wi-day-fog" : "wi-night-fog";
            case 51:
            case 53:
            case 61:
            case 80:
                return isDay ? "wi-day-sprinkle" : "wi-night-alt-sprinkle";
            case 56:
            case 57:
            case 66:
            case 67:
            case 77:
                return "wi-snowflake-cold";
            case 63:
            case 65:
                return isDay ? "wi-day-rain" : "wi-night-alt-rain";
            case 71:
            case 73:
            case 75:
            case 85:
            case 86:
                return isDay ? "wi-day-snow" : "wi-night-alt-snow";
            case 81:
            case 82:
                return isDay ? "wi-day-showers" : "wi-night-alt-showers";
            case 95:
                return isDay ? "wi-day-thunderstorm" : "wi-night-alt-thunderstorm";
            case 96:
            case 99:
                return isDay ? "wi-day-snow-thunderstorm" : "wi-night-alt-snow-thunderstorm";
            default:
                return "wi-small-craft-advisory"; // just use this as placeholder, we'll know that something's gone wrong
        }
    }
}


