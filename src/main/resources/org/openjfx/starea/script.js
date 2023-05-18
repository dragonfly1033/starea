function setDay(index) {
    console.log(index)
}

function makeHours() {
    var info;
    var icon;
    for (int i = 0; i < 24; i++) {
        info = config.weather.hourly[24*config.currentDay + i];
        icon = window.remote.getWeatherIcon(info.isDay, info.weatherCode);
        createHourly(i, info.temp, info.cloudCover, info.precipitation, icon);
    }
}

function onStart() {
  window.remote.setDay();
  config.weather = JSON.parse(window.remote.getWeather());
}

var config = {
    weather: {},
    currentDay: 0
}
