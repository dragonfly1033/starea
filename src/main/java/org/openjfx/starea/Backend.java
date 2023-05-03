package org.openjfx.starea;

import java.io.IOException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.time.LocalDate;

import org.json.JSONObject;


public class Backend {
    public Backend() {
//        String ip = getIP();
//        JSONObject weatherJSON = getWeatherJSON(ip);

        // Test value to avoid excess API requests
        JSONObject weatherJSON = new JSONObject("{\"latitude\":52.2,\"longitude\":0.099999905,\"generationtime_ms\":1.2140274047851562,\"utc_offset_seconds\":3600,\"timezone\":\"Europe/London\",\"timezone_abbreviation\":\"BST\",\"elevation\":12.0,\"current_weather\":{\"temperature\":9.2,\"windspeed\":17.9,\"winddirection\":128.0,\"weathercode\":61,\"is_day\":1,\"time\":\"2023-04-27T19:00\"},\"hourly_units\":{\"time\":\"iso8601\",\"apparent_temperature\":\"°C\",\"cloudcover\":\"%\",\"visibility\":\"m\",\"precipitation_probability\":\"%\"},\"hourly\":{\"time\":[\"2023-04-27T00:00\",\"2023-04-27T01:00\",\"2023-04-27T02:00\",\"2023-04-27T03:00\",\"2023-04-27T04:00\",\"2023-04-27T05:00\",\"2023-04-27T06:00\",\"2023-04-27T07:00\",\"2023-04-27T08:00\",\"2023-04-27T09:00\",\"2023-04-27T10:00\",\"2023-04-27T11:00\",\"2023-04-27T12:00\",\"2023-04-27T13:00\",\"2023-04-27T14:00\",\"2023-04-27T15:00\",\"2023-04-27T16:00\",\"2023-04-27T17:00\",\"2023-04-27T18:00\",\"2023-04-27T19:00\",\"2023-04-27T20:00\",\"2023-04-27T21:00\",\"2023-04-27T22:00\",\"2023-04-27T23:00\",\"2023-04-28T00:00\",\"2023-04-28T01:00\",\"2023-04-28T02:00\",\"2023-04-28T03:00\",\"2023-04-28T04:00\",\"2023-04-28T05:00\",\"2023-04-28T06:00\",\"2023-04-28T07:00\",\"2023-04-28T08:00\",\"2023-04-28T09:00\",\"2023-04-28T10:00\",\"2023-04-28T11:00\",\"2023-04-28T12:00\",\"2023-04-28T13:00\",\"2023-04-28T14:00\",\"2023-04-28T15:00\",\"2023-04-28T16:00\",\"2023-04-28T17:00\",\"2023-04-28T18:00\",\"2023-04-28T19:00\",\"2023-04-28T20:00\",\"2023-04-28T21:00\",\"2023-04-28T22:00\",\"2023-04-28T23:00\",\"2023-04-29T00:00\",\"2023-04-29T01:00\",\"2023-04-29T02:00\",\"2023-04-29T03:00\",\"2023-04-29T04:00\",\"2023-04-29T05:00\",\"2023-04-29T06:00\",\"2023-04-29T07:00\",\"2023-04-29T08:00\",\"2023-04-29T09:00\",\"2023-04-29T10:00\",\"2023-04-29T11:00\",\"2023-04-29T12:00\",\"2023-04-29T13:00\",\"2023-04-29T14:00\",\"2023-04-29T15:00\",\"2023-04-29T16:00\",\"2023-04-29T17:00\",\"2023-04-29T18:00\",\"2023-04-29T19:00\",\"2023-04-29T20:00\",\"2023-04-29T21:00\",\"2023-04-29T22:00\",\"2023-04-29T23:00\",\"2023-04-30T00:00\",\"2023-04-30T01:00\",\"2023-04-30T02:00\",\"2023-04-30T03:00\",\"2023-04-30T04:00\",\"2023-04-30T05:00\",\"2023-04-30T06:00\",\"2023-04-30T07:00\",\"2023-04-30T08:00\",\"2023-04-30T09:00\",\"2023-04-30T10:00\",\"2023-04-30T11:00\",\"2023-04-30T12:00\",\"2023-04-30T13:00\",\"2023-04-30T14:00\",\"2023-04-30T15:00\",\"2023-04-30T16:00\",\"2023-04-30T17:00\",\"2023-04-30T18:00\",\"2023-04-30T19:00\",\"2023-04-30T20:00\",\"2023-04-30T21:00\",\"2023-04-30T22:00\",\"2023-04-30T23:00\",\"2023-05-01T00:00\",\"2023-05-01T01:00\",\"2023-05-01T02:00\",\"2023-05-01T03:00\",\"2023-05-01T04:00\",\"2023-05-01T05:00\",\"2023-05-01T06:00\",\"2023-05-01T07:00\",\"2023-05-01T08:00\",\"2023-05-01T09:00\",\"2023-05-01T10:00\",\"2023-05-01T11:00\",\"2023-05-01T12:00\",\"2023-05-01T13:00\",\"2023-05-01T14:00\",\"2023-05-01T15:00\",\"2023-05-01T16:00\",\"2023-05-01T17:00\",\"2023-05-01T18:00\",\"2023-05-01T19:00\",\"2023-05-01T20:00\",\"2023-05-01T21:00\",\"2023-05-01T22:00\",\"2023-05-01T23:00\",\"2023-05-02T00:00\",\"2023-05-02T01:00\",\"2023-05-02T02:00\",\"2023-05-02T03:00\",\"2023-05-02T04:00\",\"2023-05-02T05:00\",\"2023-05-02T06:00\",\"2023-05-02T07:00\",\"2023-05-02T08:00\",\"2023-05-02T09:00\",\"2023-05-02T10:00\",\"2023-05-02T11:00\",\"2023-05-02T12:00\",\"2023-05-02T13:00\",\"2023-05-02T14:00\",\"2023-05-02T15:00\",\"2023-05-02T16:00\",\"2023-05-02T17:00\",\"2023-05-02T18:00\",\"2023-05-02T19:00\",\"2023-05-02T20:00\",\"2023-05-02T21:00\",\"2023-05-02T22:00\",\"2023-05-02T23:00\",\"2023-05-03T00:00\",\"2023-05-03T01:00\",\"2023-05-03T02:00\",\"2023-05-03T03:00\",\"2023-05-03T04:00\",\"2023-05-03T05:00\",\"2023-05-03T06:00\",\"2023-05-03T07:00\",\"2023-05-03T08:00\",\"2023-05-03T09:00\",\"2023-05-03T10:00\",\"2023-05-03T11:00\",\"2023-05-03T12:00\",\"2023-05-03T13:00\",\"2023-05-03T14:00\",\"2023-05-03T15:00\",\"2023-05-03T16:00\",\"2023-05-03T17:00\",\"2023-05-03T18:00\",\"2023-05-03T19:00\",\"2023-05-03T20:00\",\"2023-05-03T21:00\",\"2023-05-03T22:00\",\"2023-05-03T23:00\"],\"apparent_temperature\":[4.3,3.9,3.0,2.8,2.4,2.2,2.0,2.7,4.5,5.8,7.2,8.1,8.2,7.7,7.7,7.4,6.8,6.5,5.7,5.5,5.0,5.2,5.8,5.3,5.3,6.2,7.2,7.9,8.8,9.4,9.9,10.5,11.1,10.9,12.0,12.3,12.8,12.9,13.1,13.6,12.7,12.1,11.3,10.6,9.7,8.7,7.7,6.9,6.8,6.2,5.2,4.5,2.6,1.7,1.7,2.4,5.9,9.1,11.8,14.1,14.4,14.7,15.4,15.8,15.0,15.6,15.7,13.8,11.6,9.8,8.7,8.2,7.9,7.9,7.8,7.7,7.4,7.3,7.2,7.5,8.4,9.8,11.4,13.2,15.1,16.2,16.1,15.8,15.8,15.6,15.2,14.3,13.3,12.2,11.1,10.7,10.8,10.6,10.2,9.7,9.1,9.0,9.3,10.0,11.1,12.1,13.3,14.2,15.0,15.8,16.2,16.4,16.6,16.7,16.4,15.5,14.8,13.9,12.8,12.1,11.3,10.4,10.1,9.9,9.6,9.1,8.5,8.3,9.2,10.5,12.1,12.9,13.5,14.0,12.9,12.5,11.7,11.1,10.4,9.6,8.8,8.1,7.2,6.3,5.4,4.5,3.9,3.6,3.3,3.4,3.5,4.2,5.2,6.6,8.2,9.7,11.2,12.4,12.6,12.3,11.3,10.7,9.9,8.5,7.3,5.9,4.3,3.6],\"cloudcover\":[100,86,100,100,89,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,100,100,95,98,100,100,87,77,70,65,62,69,98,53,47,12,0,0,0,0,0,0,63,100,100,100,89,0,0,8,68,92,100,88,93,100,99,87,88,74,81,84,93,98,94,94,100,99,99,87,85,81,84,87,96,100,68,100,94,98,100,99,100,100,100,100,100,100,100,100,100,100,100,100,100,96,93,89,85,81,77,73,70,66,66,65,65,62,60,57,58,59,60,73,87,100,100,100,100,90,81,71,81,90,100,96,94,93,95,98,100,93,86,79,53,26,0,33,66,99,99,100,100,100,100,100,100,100,100,100,100,100,82,63,45,52,58,65,66],\"visibility\":[24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,23160.00,5760.00,13480.00,20500.00,23340.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,15640.00,8400.00,360.00,620.00,24140.00,24140.00,24140.00,24140.00,16860.00,22620.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,23040.00,20380.00,11760.00,20440.00,20560.00,21800.00,24140.00,18400.00,19300.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00,24140.00],\"precipitation_probability\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,7,10,39,68,97,96,95,94,82,70,58,47,37,26,26,26,26,24,21,19,30,41,52,53,54,55,47,40,32,22,13,3,2,1,0,0,0,0,0,0,0,0,0,0,9,17,26,35,43,52,54,56,58,48,39,29,21,14,6,6,6,6,5,4,3,2,1,0,17,35,52,59,67,74,74,74,74,70,65,61,49,38,26,25,24,23,17,12,6,6,6,6,17,28,39,45,52,58,54,49,45,30,15,0,0,0,0,0,0,0,0,0,0,0,0,0,3,7,10,12,14,16,13,9,6,4,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,5,4,3,3]},\"daily_units\":{\"time\":\"iso8601\",\"sunrise\":\"iso8601\",\"sunset\":\"iso8601\"},\"daily\":{\"time\":[\"2023-04-27\",\"2023-04-28\",\"2023-04-29\",\"2023-04-30\",\"2023-05-01\",\"2023-05-02\",\"2023-05-03\"],\"sunrise\":[\"2023-04-27T05:35\",\"2023-04-28T05:33\",\"2023-04-29T05:31\",\"2023-04-30T05:29\",\"2023-05-01T05:27\",\"2023-05-02T05:25\",\"2023-05-03T05:23\"],\"sunset\":[\"2023-04-27T20:19\",\"2023-04-28T20:20\",\"2023-04-29T20:22\",\"2023-04-30T20:24\",\"2023-05-01T20:26\",\"2023-05-02T20:27\",\"2023-05-03T20:29\"]}}");

        String[] dayList = getDayList();
        for (int i = 0; i < dayList.length; i++){ System.out.println(dayList[i]); }

        System.out.println(
                interpretWeatherCode(weatherJSON.getJSONObject("current_weather")
                                                .getInt("weathercode")
                ));
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

    private static JSONObject getWeatherJSON(String ip) throws Exception{
        JSONObject ipInfo = curlJSON("http://ip-api.com/json/"+ip);
        String latitude = ipInfo.get("lat").toString();
        String longitude = ipInfo.get("lon").toString();
        String timezone = ipInfo.get("timezone").toString();

        String url = "https://api.open-meteo.com/v1/forecast?" +
                "current_weather=true&" +
                "daily=sunrise,sunset,weathercode&" +
                "hourly=apparent_temperature,cloudcover,visibility,precipitation_probability&" +
                "latitude=" + latitude + "&" +
                "longitude=" + longitude + "&" +
                "timezone=" + timezone;
        System.out.println(url);
        JSONObject weather = curlJSON(url);
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
}
