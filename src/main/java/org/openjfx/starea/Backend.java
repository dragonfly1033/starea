package org.openjfx.starea;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import javafx.util.Pair;
import org.json.JSONObject;

import java.lang.Math;


public class Backend {
    public static class WeatherRequest {
        boolean celsius = true;
    }

    public static class WeatherResponse {
        boolean celsius;

        public class HourData {
            double temperature2m; // C or F
            int weatherCode;
            int visibility; // m
            int precipitationProbability; // %
            boolean isDay; // bool
            double apparentTemperature; // C or F
            int cloudCover; // %
        }

        public class DayData {
            double apparentTemperatureMin; // C or F
            double apparentTemperatureMax; // C or F
            int weatherCode;
        }

        public HourData hourlyData[];
        public DayData dailyData[];

        private DayData parseDailyData(JSONObject daily, int day) {
            JSONArray apparentTemperatureMins = daily.getJSONArray("apparent_temperature_min");
            JSONArray apparentTemperatureMaxes = daily.getJSONArray("apparent_temperature_max");
            JSONArray weatherCodes = daily.getJSONArray("weathercode");

            DayData data = new DayData();

            data.apparentTemperatureMin = apparentTemperatureMins.getDouble(day);
            data.apparentTemperatureMax = apparentTemperatureMaxes.getDouble(day);
            data.weatherCode = weatherCodes.getInt(day);

            return data;
        }

        private HourData parseHourlyData(JSONObject hourly, int index) {
            JSONArray temperature2ms = hourly.getJSONArray("temperature_2m");
            JSONArray weatherCodes = hourly.getJSONArray("weathercode");
            JSONArray visibilities = hourly.getJSONArray("visibility");
            JSONArray precipitationProbabilities = hourly.getJSONArray("precipitation_probability");
            JSONArray isDays = hourly.getJSONArray("is_day");
            JSONArray apparentTemperatures = hourly.getJSONArray("apparent_temperature");
            JSONArray cloudCovers = hourly.getJSONArray("cloudcover");

            HourData data = new HourData();

            data.temperature2m = temperature2ms.getDouble(index);
            data.weatherCode = weatherCodes.getInt(index);
            data.visibility = visibilities.getInt(index);
            data.precipitationProbability = precipitationProbabilities.getInt(index);
            data.isDay = isDays.getInt(index) == 1;
            data.apparentTemperature = apparentTemperatures.getDouble(index);
            data.cloudCover = cloudCovers.getInt(index);

            return data;
        }

        WeatherResponse(JSONObject weatherJSON, boolean isCelsius)
        {
            celsius = isCelsius;

            JSONObject daily = weatherJSON.getJSONObject("daily");
            JSONObject hourly = weatherJSON.getJSONObject("hourly");

            dailyData = new DayData[7];
            hourlyData = new HourData[168];

            for (int i = 0; i < 7; i++)
            {
                dailyData[i] = parseDailyData(daily, i);
            }

            for (int i = 0; i < 168; i++)
            {
                hourlyData[i] = parseHourlyData(hourly, i);
            }
        }
    }

    private WeatherResponse response = null;

    private static int deg = 10;
    private static double whole_map_bottom = -59.995775;
    private static double whole_map_top = 85.054167;
    private static double whole_map_right = 179.999856;
    private static double whole_map_left = -180.0;
    private static double latitude;
    private static double longitude;

    public Backend() throws Exception {
        //String ip = getIP();
        //JSONObject weatherJSON = getWeatherJSON(ip);

        // Test value to avoid excess API requests
//        JSONObject weatherJSON = new JSONObject("{\"elevation\":12,\"hourly_units\":{\"temperature_2m\":\"°C\",\"weathercode\":\"wmo code\",\"visibility\":\"m\",\"precipitation_probability\":\"%\",\"is_day\":\"\",\"apparent_temperature\":\"°C\",\"time\":\"iso8601\",\"cloudcover\":\"%\"},\"generationtime_ms\":2.822995185852051,\"timezone_abbreviation\":\"BST\",\"daily_units\":{\"apparent_temperature_min\":\"°C\",\"weathercode\":\"wmo code\",\"apparent_temperature_max\":\"°C\",\"time\":\"iso8601\"},\"timezone\":\"Europe/London\",\"latitude\":52.2,\"daily\":{\"apparent_temperature_min\":[3.6,8.8,9.3,10.5,9.5,8.5,7.3],\"weathercode\":[95,80,80,80,80,61,61],\"apparent_temperature_max\":[14.8,14.5,13.6,17.2,17.2,14.3,12.1],\"time\":[\"2023-05-04\",\"2023-05-05\",\"2023-05-06\",\"2023-05-07\",\"2023-05-08\",\"2023-05-09\",\"2023-05-10\"]},\"utc_offset_seconds\":3600,\"hourly\":{\"temperature_2m\":[6.5,6.2,6.2,6.2,6.6,6.3,6.3,7.4,9.1,10.9,13.1,14.6,15.6,17.1,17.6,17,16.5,15.7,15.1,14.7,13.8,12.5,11.9,11.9,11.8,11.4,11.3,11.5,10.9,10.7,10.2,11.2,12.6,14.2,14.4,15.1,16,16.7,17.2,16.5,16.7,16.4,16.5,15.9,14.7,13.1,12.2,11.8,11.5,11.5,11.1,11,10.7,10.5,10.6,11.3,12.2,13.6,14.7,15.6,15.3,15.3,14.4,14.7,14.4,14.3,14.3,14.1,13.7,13.5,13.3,13.2,13.1,13.1,12.9,12.6,12.5,12.4,12.5,12.6,13.1,13.6,14.4,15.1,16.5,17.8,18.5,19,19.2,19,18.6,17.7,16.7,15.6,14.2,13.5,13,12.5,12.2,12.1,12,11.9,11.8,11.9,12,12.3,12.9,13.6,14.6,15.8,16.6,17.4,18,17.6,16.9,15.8,15.2,14.5,13.7,13.1,12.5,11.8,11.4,11,10.6,10.5,10.6,10.6,11.6,12.6,13.8,14.7,15.5,16.5,17,17.3,17.4,17.2,16.8,16.1,15.2,14.2,13,12.4,11.9,11.3,10.8,10.3,9.9,9.6,9.4,9.7,10.5,11.7,13.1,13.8,14.3,14.8,15.2,15.4,15.4,15,14.4,13.5,12.8,12.1,11.3,11.1],\"weathercode\":[3,3,3,3,3,2,3,3,3,3,3,3,3,3,3,3,3,95,3,2,1,1,2,2,3,3,3,3,3,3,3,2,3,3,3,3,3,3,80,80,80,3,3,2,1,1,2,2,2,2,3,3,3,3,3,3,3,3,3,3,61,61,61,61,61,61,3,80,61,3,3,61,80,3,3,3,3,3,3,3,2,3,3,3,2,2,3,3,3,2,2,2,3,3,3,3,3,3,3,3,3,61,61,61,3,3,3,3,3,3,3,3,3,80,80,80,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,61,61,61,2,2,2,2,2,2,2,2,2,3,3,3,2,2,2,3,3,3,3,3,3,2],\"visibility\":[24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,23000,24140,22920,24140,24140,24140,24140,17060,18640,18100,8680,23660,24140,24140,24140,24140,24140,24140,15540,24140,18180,17180,24140,12480,12100,8580,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,9440,14820,21120,18940,24140,21060,24140,22400,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,8180,23200,24100,5240,24140,24140,20960,17700,24140,14600,24140,22420,24140,24140,24140,24140,24140,24140,24140,24140,14260,900,11840,14100,18920,24140,25140,24960,24140,23400,22360,20900,19620,18260,17660,19100,21540,24140,24620,24460,24140,24140,24140,24140,24580,25140,24140,20460,15260,10120,9500,10440,11960,12700,13500,14200,13880,13160,13580,16260,20140,24140,24920],\"precipitation_probability\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,9,17,26,24,21,19,22,26,29,34,40,45,34,24,13,22,30,39,58,78,97,96,95,94,93,91,90,71,51,32,21,11,0,0,0,0,1,2,3,13,22,32,42,51,61,62,64,65,56,48,39,34,28,23,27,31,35,31,27,23,20,16,13,11,8,6,13,19,26,30,35,39,40,41,42,32,23,13,10,6,3,12,20,29,37,44,52,57,63,68,69,70,71,73,75,77,77,77,77,70,62,55,39,22,6,5,4,3,7,12,16,18,21,23,40,57,74,79,85,90,88,86,84,73,63,52,41,30,19,18,17,16,16,16,16,21,27,32,45,58,71,75,80,84,84,84,84,76,69,61,61],\"is_day\":[0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0],\"apparent_temperature\":[3.7,3.7,3.8,3.6,4,3.9,3.9,4.8,6.2,8.1,9.8,11.3,12.8,14.3,14.8,13.8,13,12.8,12.6,12.7,12.5,11.5,10.7,10.8,10.6,10.2,10.1,10.1,9.4,9.1,8.8,9.9,10.3,11.4,12.4,13.4,13.5,14.2,14.5,14.3,14.5,14.5,14.2,14.2,13.8,12.3,11,10.5,10.3,10.4,9.8,9.9,9.5,9.3,9.5,10,11,12.7,12.9,13.4,13.6,13.5,12.9,13,12.8,12.9,13.2,13,12.8,12.6,12.3,12.2,12.2,12.4,12.3,12.1,11.9,11.7,11.7,10.5,11,11.4,12.1,12.9,14.1,15.8,16.3,16.5,16.7,17,17.2,16.9,16.1,14.8,13.2,12.2,11.4,10.6,10.3,10.3,10.1,9.9,9.8,9.7,9.6,9.5,10,11.1,12.6,14.5,15.5,16.5,17.2,16.9,16,14.7,13.9,13.1,12,11.2,10.4,9.5,9,8.7,8.5,8.5,8.6,8.6,8.9,9.6,10.5,11.2,12.4,13.5,13.9,13.8,14.1,14.2,14.3,14,13.3,12.2,10.9,10.5,10.4,10,9.4,8.6,7.9,7.5,7.3,7.4,8.1,9,10,10.5,10.9,11.2,11.3,11.8,12.1,11.8,11.4,10.8,10.4,10.1,9.7,9.7],\"time\":[\"2023-05-04T00:00\",\"2023-05-04T01:00\",\"2023-05-04T02:00\",\"2023-05-04T03:00\",\"2023-05-04T04:00\",\"2023-05-04T05:00\",\"2023-05-04T06:00\",\"2023-05-04T07:00\",\"2023-05-04T08:00\",\"2023-05-04T09:00\",\"2023-05-04T10:00\",\"2023-05-04T11:00\",\"2023-05-04T12:00\",\"2023-05-04T13:00\",\"2023-05-04T14:00\",\"2023-05-04T15:00\",\"2023-05-04T16:00\",\"2023-05-04T17:00\",\"2023-05-04T18:00\",\"2023-05-04T19:00\",\"2023-05-04T20:00\",\"2023-05-04T21:00\",\"2023-05-04T22:00\",\"2023-05-04T23:00\",\"2023-05-05T00:00\",\"2023-05-05T01:00\",\"2023-05-05T02:00\",\"2023-05-05T03:00\",\"2023-05-05T04:00\",\"2023-05-05T05:00\",\"2023-05-05T06:00\",\"2023-05-05T07:00\",\"2023-05-05T08:00\",\"2023-05-05T09:00\",\"2023-05-05T10:00\",\"2023-05-05T11:00\",\"2023-05-05T12:00\",\"2023-05-05T13:00\",\"2023-05-05T14:00\",\"2023-05-05T15:00\",\"2023-05-05T16:00\",\"2023-05-05T17:00\",\"2023-05-05T18:00\",\"2023-05-05T19:00\",\"2023-05-05T20:00\",\"2023-05-05T21:00\",\"2023-05-05T22:00\",\"2023-05-05T23:00\",\"2023-05-06T00:00\",\"2023-05-06T01:00\",\"2023-05-06T02:00\",\"2023-05-06T03:00\",\"2023-05-06T04:00\",\"2023-05-06T05:00\",\"2023-05-06T06:00\",\"2023-05-06T07:00\",\"2023-05-06T08:00\",\"2023-05-06T09:00\",\"2023-05-06T10:00\",\"2023-05-06T11:00\",\"2023-05-06T12:00\",\"2023-05-06T13:00\",\"2023-05-06T14:00\",\"2023-05-06T15:00\",\"2023-05-06T16:00\",\"2023-05-06T17:00\",\"2023-05-06T18:00\",\"2023-05-06T19:00\",\"2023-05-06T20:00\",\"2023-05-06T21:00\",\"2023-05-06T22:00\",\"2023-05-06T23:00\",\"2023-05-07T00:00\",\"2023-05-07T01:00\",\"2023-05-07T02:00\",\"2023-05-07T03:00\",\"2023-05-07T04:00\",\"2023-05-07T05:00\",\"2023-05-07T06:00\",\"2023-05-07T07:00\",\"2023-05-07T08:00\",\"2023-05-07T09:00\",\"2023-05-07T10:00\",\"2023-05-07T11:00\",\"2023-05-07T12:00\",\"2023-05-07T13:00\",\"2023-05-07T14:00\",\"2023-05-07T15:00\",\"2023-05-07T16:00\",\"2023-05-07T17:00\",\"2023-05-07T18:00\",\"2023-05-07T19:00\",\"2023-05-07T20:00\",\"2023-05-07T21:00\",\"2023-05-07T22:00\",\"2023-05-07T23:00\",\"2023-05-08T00:00\",\"2023-05-08T01:00\",\"2023-05-08T02:00\",\"2023-05-08T03:00\",\"2023-05-08T04:00\",\"2023-05-08T05:00\",\"2023-05-08T06:00\",\"2023-05-08T07:00\",\"2023-05-08T08:00\",\"2023-05-08T09:00\",\"2023-05-08T10:00\",\"2023-05-08T11:00\",\"2023-05-08T12:00\",\"2023-05-08T13:00\",\"2023-05-08T14:00\",\"2023-05-08T15:00\",\"2023-05-08T16:00\",\"2023-05-08T17:00\",\"2023-05-08T18:00\",\"2023-05-08T19:00\",\"2023-05-08T20:00\",\"2023-05-08T21:00\",\"2023-05-08T22:00\",\"2023-05-08T23:00\",\"2023-05-09T00:00\",\"2023-05-09T01:00\",\"2023-05-09T02:00\",\"2023-05-09T03:00\",\"2023-05-09T04:00\",\"2023-05-09T05:00\",\"2023-05-09T06:00\",\"2023-05-09T07:00\",\"2023-05-09T08:00\",\"2023-05-09T09:00\",\"2023-05-09T10:00\",\"2023-05-09T11:00\",\"2023-05-09T12:00\",\"2023-05-09T13:00\",\"2023-05-09T14:00\",\"2023-05-09T15:00\",\"2023-05-09T16:00\",\"2023-05-09T17:00\",\"2023-05-09T18:00\",\"2023-05-09T19:00\",\"2023-05-09T20:00\",\"2023-05-09T21:00\",\"2023-05-09T22:00\",\"2023-05-09T23:00\",\"2023-05-10T00:00\",\"2023-05-10T01:00\",\"2023-05-10T02:00\",\"2023-05-10T03:00\",\"2023-05-10T04:00\",\"2023-05-10T05:00\",\"2023-05-10T06:00\",\"2023-05-10T07:00\",\"2023-05-10T08:00\",\"2023-05-10T09:00\",\"2023-05-10T10:00\",\"2023-05-10T11:00\",\"2023-05-10T12:00\",\"2023-05-10T13:00\",\"2023-05-10T14:00\",\"2023-05-10T15:00\",\"2023-05-10T16:00\",\"2023-05-10T17:00\",\"2023-05-10T18:00\",\"2023-05-10T19:00\",\"2023-05-10T20:00\",\"2023-05-10T21:00\",\"2023-05-10T22:00\",\"2023-05-10T23:00\"],\"cloudcover\":[100,100,100,100,92,66,95,93,100,100,95,95,96,87,95,100,100,100,87,71,8,19,69,34,100,91,100,100,95,100,75,71,84,97,100,100,100,100,100,100,100,100,94,91,32,33,58,79,83,80,100,98,89,99,99,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,96,98,93,97,95,99,89,78,84,85,85,54,57,62,68,73,77,82,86,91,95,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,85,70,55,57,60,62,59,55,52,54,55,57,51,53,54,67,81,94,96,98,100,100,100,100,100,100,100,100,100,100,90,81,71,63,56,48,47,46,45,63,82,100,88,75,63,75,88,100,100,100,100,90]},\"current_weather\":{\"weathercode\":3,\"temperature\":17,\"windspeed\":18.5,\"is_day\":1,\"time\":\"2023-05-04T15:00\",\"winddirection\":97},\"longitude\":0.119999886}\n");

//        response = new WeatherResponse(weatherJSON, true);

//        System.out.println(getCloudCover(3, 3));

        getLightPollution(55.203690, -1.973907);
    }

    private static int timeToIndex(int day, int hour) throws Exception{
        if (day < 0 || day > 6) { throw new Exception("day 0-6 : today-in 6 days"); }
        if (hour < 0 || hour > 23) { throw new Exception("hour 0-23"); }
        return 24*day + hour;
    }

    private static int getDayOfWeekIndex(){
        return LocalDate.now().getDayOfWeek().getValue() -1;
    }

    private static String[] getDayList(){
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

    private static String interpretWeatherCode(int weatherCode){
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

    private JSONObject getWeatherJSON(String ip) throws Exception{
        JSONObject ipInfo = curlJSON("http://ip-api.com/json/"+ip);
        String s_latitude = ipInfo.get("lat").toString();
        String s_longitude = ipInfo.get("lon").toString();
        String timezone = ipInfo.get("timezone").toString();

        latitude = Double.parseDouble(s_latitude);
        longitude = Double.parseDouble(s_longitude);

        String url = "https://api.open-meteo.com/v1/forecast?" +
                "current_weather=true&" +
                "daily=apparent_temperature_max,apparent_temperature_min,weathercode&" +
                "hourly=is_day,temperature_2m,weathercode,cloudcover,visibility,precipitation_probability&" +
                "latitude=" + s_latitude + "&" +
                "longitude=" + s_longitude + "&" +
                "timezone=" + timezone;
        System.out.println(url);
        JSONObject weather = curlJSON(url);

        System.out.println(weather.toString());
        return weather;
    }

    private static String getIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        String ip = in.readLine();
        return ip;
    }

    private static JSONObject curlJSON(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(res.body());
        return jsonObject;
    }

    private static Pair<String, Pair<Integer, Integer>> getImgName(double lat, double lon) {
        int corner_lat = (int)Math.floor((lat - whole_map_bottom)/deg) * deg + (int)Math.floor(whole_map_bottom) + deg + 1;
        int corner_lon = (int)Math.floor((lon - whole_map_left)/deg) * deg + (int)Math.floor(whole_map_left);
        String ans = Integer.toString(corner_lat) + "_" + Integer.toString(corner_lon) + ".png";
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

    private int getScore(int day, int hour, double lat, double lon) throws Exception {
        if (isDay(day, hour)) { return 0; }
        double cloudCoverScore = Math.max(1-2*getCloudCover(day, hour), 0.0);
        double visibility = getVisibility(day, hour)/24140.0;
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

    private boolean isDay(int day, int hour) throws Exception {
        return response.hourlyData[timeToIndex(day, hour)].isDay;
    }
    private double getCloudCover(int day, int hour) throws Exception {
        return response.hourlyData[timeToIndex(day, hour)].cloudCover;
    }
    private double getVisibility(int day, int hour) throws Exception {
        return response.hourlyData[timeToIndex(day, hour)].visibility;
    }
}


