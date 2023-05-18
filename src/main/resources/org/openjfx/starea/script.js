function setDay(index) {
    console.log(index)
}

function onStart() {
  window.remote.setDay();
  config.weather = JSON.parse(window.remote.getWeather());
  console.log(config.weather.daily[0].tempMax);
}

var config = {
    weather: {}
}
