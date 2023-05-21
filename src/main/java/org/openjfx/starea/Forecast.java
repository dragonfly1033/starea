package org.openjfx.starea;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Forecast {
    public JSONObject[] hourlyData;
    public JSONObject[] dailyData;
    public double latitude;
    public double longitude;

    public Forecast() throws Exception {
//        String ip = getIP();
//        JSONObject weatherJSON = getWeatherJSON(ip);
        JSONObject weatherJSON = new JSONObject("{\"elevation\":12,\"hourly_units\":{\"temperature_2m\":\"°C\",\"weathercode\":\"wmo code\",\"visibility\":\"m\",\"precipitation_probability\":\"%\",\"is_day\":\"\",\"time\":\"iso8601\",\"cloudcover\":\"%\"},\"generationtime_ms\":1.1039972305297852,\"timezone_abbreviation\":\"BST\",\"daily_units\":{\"apparent_temperature_min\":\"°C\",\"weathercode\":\"wmo code\",\"apparent_temperature_max\":\"°C\",\"time\":\"iso8601\"},\"timezone\":\"Europe/London\",\"latitude\":52.2,\"daily\":{\"apparent_temperature_min\":[3.5,6.2,7.9,10.5,11.2,10.5,13.1],\"weathercode\":[80,3,3,3,3,3,3],\"apparent_temperature_max\":[13.8,15.8,17.9,16.3,16.4,20,20.4],\"time\":[\"2023-05-16\",\"2023-05-17\",\"2023-05-18\",\"2023-05-19\",\"2023-05-20\",\"2023-05-21\",\"2023-05-22\"]},\"utc_offset_seconds\":3600,\"hourly\":{\"temperature_2m\":[7.3,6.9,6.8,6.3,5.7,5.7,5.8,7.3,9.1,10.8,12.2,13.1,14.1,15.1,15.8,15.6,16.1,16.2,16,15.8,14.9,13.6,13.2,12.1,11.1,10.2,9.5,9.1,8.1,7.6,7.7,9,10.4,11.9,12.6,13.8,14.5,15.2,16,16,16.8,16.8,16.6,15.1,13.9,12.4,11.2,10.5,10,9.7,9.5,9.5,9.5,9.4,9.5,10.4,12.1,14,15.2,16.3,17.5,18.2,18.7,19,19.1,19.1,18.8,18.3,17.2,15.9,14.8,13.6,13.1,13.1,12.7,12.4,12.1,11.9,11.7,11.8,12.2,12.8,13.7,14.5,15.4,16.4,17.1,17.7,18.1,18.1,17.8,17.1,16.2,15.1,13.7,13,12.5,12,11.9,12,12.1,12.1,12.1,12.3,12.8,13.5,14.4,14.9,15.3,15.7,16.1,16.5,16.8,16.8,16.7,16.5,17.3,15.9,14.3,13.6,13.1,12.5,12,11.6,11.2,11,11,11.5,12.6,14.1,16,17,17.9,18.9,19.5,19.9,20.3,20.3,20.1,19.5,18.6,17.3,15.8,15.1,14.7,14.2,14,13.8,13.8,13.8,13.8,14.2,14.8,15.6,16.7,17.7,18.7,19.8,20.3,20.7,20.8,20.7,20.4,19.6,18.6,17.4,15.9,15.1],\"weathercode\":[1,3,2,1,0,0,0,0,0,1,2,2,2,2,3,3,3,3,2,2,3,3,3,80,1,2,2,3,3,2,2,3,3,3,3,3,3,3,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2,2,2,2,1,1,1,1,1,1,3,3,3,3,3,3,3],\"visibility\":[24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,18980,24140,24140,23300,22260,18500,22880,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,23220,18440,14920,17340,19840,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140,24140],\"precipitation_probability\":[0,0,0,0,0,0,0,0,0,0,0,1,2,3,8,14,19,31,43,55,46,38,29,20,12,3,2,1,0,0,0,0,0,0,0,14,28,42,47,53,58,53,47,42,29,16,3,2,1,0,1,2,3,2,1,0,0,0,0,9,17,26,36,45,55,47,40,32,23,15,6,4,2,0,0,0,0,0,0,0,2,4,6,18,30,42,50,57,65,57,50,42,30,18,6,4,2,0,0,0,0,0,0,0,1,2,3,8,14,19,20,22,23,21,18,16,11,5,0,0,0,0,2,4,6,7,9,10,10,10,10,8,5,3,8,14,19,15,10,6,4,2,0,1,2,3,3,3,3,4,5,6,6,6,6,6,6,6,8,11,13,13,13,13,10,6,3,3],\"is_day\":[0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0],\"time\":[\"2023-05-16T00:00\",\"2023-05-16T01:00\",\"2023-05-16T02:00\",\"2023-05-16T03:00\",\"2023-05-16T04:00\",\"2023-05-16T05:00\",\"2023-05-16T06:00\",\"2023-05-16T07:00\",\"2023-05-16T08:00\",\"2023-05-16T09:00\",\"2023-05-16T10:00\",\"2023-05-16T11:00\",\"2023-05-16T12:00\",\"2023-05-16T13:00\",\"2023-05-16T14:00\",\"2023-05-16T15:00\",\"2023-05-16T16:00\",\"2023-05-16T17:00\",\"2023-05-16T18:00\",\"2023-05-16T19:00\",\"2023-05-16T20:00\",\"2023-05-16T21:00\",\"2023-05-16T22:00\",\"2023-05-16T23:00\",\"2023-05-17T00:00\",\"2023-05-17T01:00\",\"2023-05-17T02:00\",\"2023-05-17T03:00\",\"2023-05-17T04:00\",\"2023-05-17T05:00\",\"2023-05-17T06:00\",\"2023-05-17T07:00\",\"2023-05-17T08:00\",\"2023-05-17T09:00\",\"2023-05-17T10:00\",\"2023-05-17T11:00\",\"2023-05-17T12:00\",\"2023-05-17T13:00\",\"2023-05-17T14:00\",\"2023-05-17T15:00\",\"2023-05-17T16:00\",\"2023-05-17T17:00\",\"2023-05-17T18:00\",\"2023-05-17T19:00\",\"2023-05-17T20:00\",\"2023-05-17T21:00\",\"2023-05-17T22:00\",\"2023-05-17T23:00\",\"2023-05-18T00:00\",\"2023-05-18T01:00\",\"2023-05-18T02:00\",\"2023-05-18T03:00\",\"2023-05-18T04:00\",\"2023-05-18T05:00\",\"2023-05-18T06:00\",\"2023-05-18T07:00\",\"2023-05-18T08:00\",\"2023-05-18T09:00\",\"2023-05-18T10:00\",\"2023-05-18T11:00\",\"2023-05-18T12:00\",\"2023-05-18T13:00\",\"2023-05-18T14:00\",\"2023-05-18T15:00\",\"2023-05-18T16:00\",\"2023-05-18T17:00\",\"2023-05-18T18:00\",\"2023-05-18T19:00\",\"2023-05-18T20:00\",\"2023-05-18T21:00\",\"2023-05-18T22:00\",\"2023-05-18T23:00\",\"2023-05-19T00:00\",\"2023-05-19T01:00\",\"2023-05-19T02:00\",\"2023-05-19T03:00\",\"2023-05-19T04:00\",\"2023-05-19T05:00\",\"2023-05-19T06:00\",\"2023-05-19T07:00\",\"2023-05-19T08:00\",\"2023-05-19T09:00\",\"2023-05-19T10:00\",\"2023-05-19T11:00\",\"2023-05-19T12:00\",\"2023-05-19T13:00\",\"2023-05-19T14:00\",\"2023-05-19T15:00\",\"2023-05-19T16:00\",\"2023-05-19T17:00\",\"2023-05-19T18:00\",\"2023-05-19T19:00\",\"2023-05-19T20:00\",\"2023-05-19T21:00\",\"2023-05-19T22:00\",\"2023-05-19T23:00\",\"2023-05-20T00:00\",\"2023-05-20T01:00\",\"2023-05-20T02:00\",\"2023-05-20T03:00\",\"2023-05-20T04:00\",\"2023-05-20T05:00\",\"2023-05-20T06:00\",\"2023-05-20T07:00\",\"2023-05-20T08:00\",\"2023-05-20T09:00\",\"2023-05-20T10:00\",\"2023-05-20T11:00\",\"2023-05-20T12:00\",\"2023-05-20T13:00\",\"2023-05-20T14:00\",\"2023-05-20T15:00\",\"2023-05-20T16:00\",\"2023-05-20T17:00\",\"2023-05-20T18:00\",\"2023-05-20T19:00\",\"2023-05-20T20:00\",\"2023-05-20T21:00\",\"2023-05-20T22:00\",\"2023-05-20T23:00\",\"2023-05-21T00:00\",\"2023-05-21T01:00\",\"2023-05-21T02:00\",\"2023-05-21T03:00\",\"2023-05-21T04:00\",\"2023-05-21T05:00\",\"2023-05-21T06:00\",\"2023-05-21T07:00\",\"2023-05-21T08:00\",\"2023-05-21T09:00\",\"2023-05-21T10:00\",\"2023-05-21T11:00\",\"2023-05-21T12:00\",\"2023-05-21T13:00\",\"2023-05-21T14:00\",\"2023-05-21T15:00\",\"2023-05-21T16:00\",\"2023-05-21T17:00\",\"2023-05-21T18:00\",\"2023-05-21T19:00\",\"2023-05-21T20:00\",\"2023-05-21T21:00\",\"2023-05-21T22:00\",\"2023-05-21T23:00\",\"2023-05-22T00:00\",\"2023-05-22T01:00\",\"2023-05-22T02:00\",\"2023-05-22T03:00\",\"2023-05-22T04:00\",\"2023-05-22T05:00\",\"2023-05-22T06:00\",\"2023-05-22T07:00\",\"2023-05-22T08:00\",\"2023-05-22T09:00\",\"2023-05-22T10:00\",\"2023-05-22T11:00\",\"2023-05-22T12:00\",\"2023-05-22T13:00\",\"2023-05-22T14:00\",\"2023-05-22T15:00\",\"2023-05-22T16:00\",\"2023-05-22T17:00\",\"2023-05-22T18:00\",\"2023-05-22T19:00\",\"2023-05-22T20:00\",\"2023-05-22T21:00\",\"2023-05-22T22:00\",\"2023-05-22T23:00\"],\"cloudcover\":[31,92,54,10,0,0,0,0,0,29,72,53,36,93,100,100,96,91,42,52,99,97,100,83,44,71,80,100,82,85,80,100,100,96,100,100,99,100,74,88,92,94,100,100,100,100,100,100,100,100,100,100,100,100,100,100,91,95,100,99,94,98,95,97,88,89,94,92,89,91,95,98,99,98,97,96,95,95,95,95,94,93,92,95,97,100,100,100,100,100,100,100,91,82,73,82,90,99,99,100,100,100,100,100,100,100,100,100,100,100,99,97,96,94,92,90,85,85,86,90,95,99,88,77,66,61,57,52,67,83,98,96,95,93,90,87,84,88,92,96,97,99,100,100,100,100,97,95,92,87,83,78,76,74,72,60,47,35,31,28,24,49,75,100,100,100,100,100]},\"current_weather\":{\"weathercode\":2,\"temperature\":6.8,\"windspeed\":2.3,\"is_day\":0,\"time\":\"2023-05-16T02:00\",\"winddirection\":231},\"longitude\":0.119999886}");

        JSONObject daily = weatherJSON.getJSONObject("daily");
        JSONObject hourly = weatherJSON.getJSONObject("hourly");
        parseDailyData(daily);
        parseHourlyData(hourly);
    }

    private String getIP() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        String ip = in.readLine();
        return ip;
    }

    private JSONObject getWeatherJSON(String ip) throws Exception {
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

        JSONObject weather = curlJSON(url);
        return weather;
    }

    private JSONObject curlJSON(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(res.body());
        return jsonObject;
    }

    private void parseDailyData(JSONObject daily) {
        JSONArray apparentTemperatureMins = daily.getJSONArray("apparent_temperature_min");
        JSONArray apparentTemperatureMaxes = daily.getJSONArray("apparent_temperature_max");
        JSONArray weatherCodes = daily.getJSONArray("weathercode");

        dailyData = new JSONObject[7];

        for (int i = 0; i < 7; i++) {
            JSONObject data = new JSONObject();
            data.put("tempMin", apparentTemperatureMins.getDouble(i));
            data.put("tempMax", apparentTemperatureMaxes.getDouble(i));
            data.put("weatherCode", weatherCodes.getInt(i));
            dailyData[i] = data;
        }
    }

    private void parseHourlyData(JSONObject hourly) {
        JSONArray temperature2ms = hourly.getJSONArray("temperature_2m");
        JSONArray weatherCodes = hourly.getJSONArray("weathercode");
        JSONArray visibilities = hourly.getJSONArray("visibility");
        JSONArray precipitationProbabilities = hourly.getJSONArray("precipitation_probability");
        JSONArray isDays = hourly.getJSONArray("is_day");
        JSONArray cloudCovers = hourly.getJSONArray("cloudcover");

        hourlyData = new JSONObject[168];

        for (int i = 0; i < 168; i++) {
            JSONObject data = new JSONObject();
            data.put("temp", temperature2ms.getDouble(i));
            data.put("weatherCode", weatherCodes.getInt(i));
            data.put("visibility", visibilities.getInt(i));
            data.put("precipitation", precipitationProbabilities.getInt(i));
            data.put("isDay", isDays.getInt(i) == 1);
            data.put("cloudCover", cloudCovers.getInt(i));
            hourlyData[i] = data;
        }
    }
}

