/**
 * Class to represent horizontally scrolling collection of hourly weather 
 * information.
 */
class HourlyCarousel {

    temp_unit = 'C';
    hourly_div;

    constructor(divId) {
        this.hourly_div = document.getElementById(divId);
    }

    /**
     * Remove all boxes from this carousel.
     */
    clearHourly() {
        document.getElementById("hourlyDiv").innerHTML = '';
    }

    /**
     * Add a box with information for 1 hour to hourly carousel.
     * @param {number} time time to be displayed, number 0-23
     * @param {number} temp temperature for this time in pre-set unit, default Celsius
     * @param {number} cloudcover percentage cloudcover
     * @param {number} precipitationProb probability of precipitation
     * @param {string} icon icon name
     */
    createHourly(time, temp, cloudcover, precipitationProb, score, icon) {
        let hourBox = document.createElement('div');
        hourBox.classList.add('hourly');
        this.hourly_div.appendChild(hourBox);

        let rights = document.getElementsByClassName("hourly-rightmost");
        while (rights.length > 0) {
            rights[0].classList.remove('hourly-rightmost');
        }

        hourBox.classList.add('hourly-rightmost');

        let timeSpan = document.createElement('span');
        if (time < 10) { time= '0'+time; }
        else { time=String(time); }
        timeSpan.innerHTML = `${time}:00`;
        timeSpan.classList.add('timeSpan');
        hourBox.appendChild(timeSpan);

        let svg = document.createElement('img');
        svg.setAttribute('src', this.getIconPath(icon));
        svg.setAttribute('width', '50');
        svg.classList.add('svgColor');
        hourBox.appendChild(svg);

        if (this.temp_unit !== 'C') {
            temp = this.celsiusToFarenheit(temp);
        }

        let table = document.createElement('table');
        table.classList.add("hourlyTable");
        table.innerHTML = `
            <tr>
                <td class="hourlyTableNums">${precipitationProb}%</td>
                <td class="hourlyTableNums">${temp}°${this.temp_unit}</td>
            </tr>`
        hourBox.appendChild(table);

        let scoreSpan = document.createElement('span');
        scoreSpan.innerHTML = `${score}/5`;
        scoreSpan.classList.add('scoreSpan');
        hourBox.appendChild(scoreSpan);

        //1ms delay to let DOM update first
        setTimeout(function(){
            this.hourly_div.scrollTop = 0;
            this.hourly_div.scrollLeft = 0;
        }.bind(this), 1);
    }

    /**
     * Get path of icon.
     * @param {string} icon name of icon
     * @returns path of icon
     */
    getIconPath(icon) {
        return config.path+'icons/'+icon+'.svg';
    }

    /**
     * Convert a temperature in Celsius to Farenheit, rounding to nearest whole number.
     * @param {number} c temperature in Celsius
     * @returns temperature in Farenheit
     */
    celsiusToFarenheit(c) {
        return Math.round(1.8*c +32);
    }

    /**
     * Set unit of temperatures added from this point. Note this will not affect
     * temperatures already on the page.
     * @param {'F'|'C'} unit temperature unit to set
     */
    setTempUnit(unit) {
        this.temp_unit = unit;
    }

}

function setDay(index) {
    document.querySelector(".day.selected").classList.remove("selected")
    document.getElementById("day-group").children[index].classList.add("selected")
    config.currentDay = index;
    makePage();
}

function makeHours() {
    var info;
    var icon;
    if (config.carousel) { config.carousel.clearHourly(); }
    config.carousel = new HourlyCarousel("hourlyDiv");
    for (let i = 0; i < 24; i++) {
        info = config.weather.hourly[24*config.currentDay + i];
        icon = window.remote.getWeatherIcon(info.isDay, info.weatherCode);
        config.carousel.createHourly(i, info.temp, info.cloudCover, info.precipitation, info.score, icon);
    }
}

//Gets the percentage score and updates the star-o-meter accordingly
function useScore(segs) {
    //Function to update star-o-meter goes here
    for (let i = 0; i < segs; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#ddaa00");
    }
    for (let i = segs; i < 5; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#444444");
    }
};

//Gets the current, maximum and minimum temperatures and updates the onscreen values accordingly
function setTemps(current, min, max) {
    document.getElementById("tempnum").innerHTML = current + "&deg C";
    document.getElementById("minnum").innerHTML = min + "&deg C";
    document.getElementById("maxnum").innerHTML = max + "&deg C";
};

function gotoMapScreen() {
    window.remote.gotoMapScreen();
}

function onStart() {
     config.path = window.remote.getPath();
    config.weather = JSON.parse(window.remote.getWeather());
    //config.weather = JSON.parse("{\"daily\":[{\"tempMax\":13.8,\"weatherCode\":80,\"tempMin\":3.5},{\"tempMax\":15.8,\"weatherCode\":3,\"tempMin\":6.2},{\"tempMax\":17.9,\"weatherCode\":3,\"tempMin\":7.9},{\"tempMax\":16.3,\"weatherCode\":3,\"tempMin\":10.5},{\"tempMax\":16.4,\"weatherCode\":3,\"tempMin\":11.2},{\"tempMax\":20,\"weatherCode\":3,\"tempMin\":10.5},{\"tempMax\":20.4,\"weatherCode\":3,\"tempMin\":13.1}],\"hourly\":[{\"precipitation\":0,\"score\":3,\"temp\":7.3,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":31,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":6.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":6.8,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":54,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":6.3,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":10,\"isDay\":false},{\"precipitation\":0,\"score\":5,\"temp\":5.7,\"visibility\":24140,\"weatherCode\":0,\"cloudCover\":0,\"isDay\":false},{\"precipitation\":0,\"score\":5,\"temp\":5.7,\"visibility\":24140,\"weatherCode\":0,\"cloudCover\":0,\"isDay\":false},{\"precipitation\":0,\"score\":0,\"temp\":5.8,\"visibility\":24140,\"weatherCode\":0,\"cloudCover\":0,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":7.3,\"visibility\":24140,\"weatherCode\":0,\"cloudCover\":0,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":9.1,\"visibility\":24140,\"weatherCode\":0,\"cloudCover\":0,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":10.8,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":29,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":12.2,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":72,\"isDay\":true},{\"precipitation\":1,\"score\":0,\"temp\":13.1,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":53,\"isDay\":true},{\"precipitation\":2,\"score\":0,\"temp\":14.1,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":36,\"isDay\":true},{\"precipitation\":3,\"score\":0,\"temp\":15.1,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":93,\"isDay\":true},{\"precipitation\":8,\"score\":0,\"temp\":15.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":14,\"score\":0,\"temp\":15.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":19,\"score\":0,\"temp\":16.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":true},{\"precipitation\":31,\"score\":0,\"temp\":16.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":91,\"isDay\":true},{\"precipitation\":43,\"score\":0,\"temp\":16,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":42,\"isDay\":true},{\"precipitation\":55,\"score\":0,\"temp\":15.8,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":52,\"isDay\":true},{\"precipitation\":46,\"score\":0,\"temp\":14.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":true},{\"precipitation\":38,\"score\":3,\"temp\":13.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":false},{\"precipitation\":29,\"score\":3,\"temp\":13.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":20,\"score\":3,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":80,\"cloudCover\":83,\"isDay\":false},{\"precipitation\":12,\"score\":3,\"temp\":11.1,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":44,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":10.2,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":71,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":9.5,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":80,\"isDay\":false},{\"precipitation\":1,\"score\":3,\"temp\":9.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":8.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":82,\"isDay\":false},{\"precipitation\":0,\"score\":0,\"temp\":7.6,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":85,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":7.7,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":80,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":10.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":11.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":12.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":14,\"score\":0,\"temp\":13.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":28,\"score\":0,\"temp\":14.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":true},{\"precipitation\":42,\"score\":0,\"temp\":15.2,\"visibility\":18980,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":47,\"score\":0,\"temp\":16,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":74,\"isDay\":true},{\"precipitation\":53,\"score\":0,\"temp\":16,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":88,\"isDay\":true},{\"precipitation\":58,\"score\":0,\"temp\":16.8,\"visibility\":23300,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":true},{\"precipitation\":53,\"score\":0,\"temp\":16.8,\"visibility\":22260,\"weatherCode\":3,\"cloudCover\":94,\"isDay\":true},{\"precipitation\":47,\"score\":0,\"temp\":16.6,\"visibility\":18500,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":42,\"score\":0,\"temp\":15.1,\"visibility\":22880,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":29,\"score\":0,\"temp\":13.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":16,\"score\":3,\"temp\":12.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":11.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":10.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":1,\"score\":3,\"temp\":10,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":9.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":1,\"score\":3,\"temp\":9.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":9.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":9.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":2,\"score\":0,\"temp\":9.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":1,\"score\":0,\"temp\":9.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":10.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":91,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":14,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":15.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":9,\"score\":0,\"temp\":16.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":true},{\"precipitation\":17,\"score\":0,\"temp\":17.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":94,\"isDay\":true},{\"precipitation\":26,\"score\":0,\"temp\":18.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":98,\"isDay\":true},{\"precipitation\":36,\"score\":0,\"temp\":18.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":45,\"score\":0,\"temp\":19,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":true},{\"precipitation\":55,\"score\":0,\"temp\":19.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":88,\"isDay\":true},{\"precipitation\":47,\"score\":0,\"temp\":19.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":89,\"isDay\":true},{\"precipitation\":40,\"score\":0,\"temp\":18.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":94,\"isDay\":true},{\"precipitation\":32,\"score\":0,\"temp\":18.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":true},{\"precipitation\":23,\"score\":0,\"temp\":17.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":89,\"isDay\":true},{\"precipitation\":15,\"score\":3,\"temp\":15.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":91,\"isDay\":false},{\"precipitation\":6,\"score\":3,\"temp\":14.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":false},{\"precipitation\":4,\"score\":3,\"temp\":13.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":98,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":13.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":13.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":98,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":false},{\"precipitation\":0,\"score\":0,\"temp\":11.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":11.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":11.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":2,\"score\":0,\"temp\":12.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":94,\"isDay\":true},{\"precipitation\":4,\"score\":0,\"temp\":12.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":93,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":13.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":true},{\"precipitation\":18,\"score\":0,\"temp\":14.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":30,\"score\":0,\"temp\":15.4,\"visibility\":23220,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":true},{\"precipitation\":42,\"score\":0,\"temp\":16.4,\"visibility\":18440,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":50,\"score\":0,\"temp\":17.1,\"visibility\":14920,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":57,\"score\":0,\"temp\":17.7,\"visibility\":17340,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":65,\"score\":0,\"temp\":18.1,\"visibility\":19840,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":57,\"score\":0,\"temp\":18.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":50,\"score\":0,\"temp\":17.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":42,\"score\":0,\"temp\":17.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":30,\"score\":0,\"temp\":16.2,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":91,\"isDay\":true},{\"precipitation\":18,\"score\":3,\"temp\":15.1,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":82,\"isDay\":false},{\"precipitation\":6,\"score\":3,\"temp\":13.7,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":73,\"isDay\":false},{\"precipitation\":4,\"score\":3,\"temp\":13,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":82,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":12.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":90,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":11.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":0,\"score\":0,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":12.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":0,\"score\":0,\"temp\":12.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":1,\"score\":0,\"temp\":12.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":2,\"score\":0,\"temp\":13.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":3,\"score\":0,\"temp\":14.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":8,\"score\":0,\"temp\":14.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":14,\"score\":0,\"temp\":15.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":19,\"score\":0,\"temp\":15.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":20,\"score\":0,\"temp\":16.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":true},{\"precipitation\":22,\"score\":0,\"temp\":16.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":true},{\"precipitation\":23,\"score\":0,\"temp\":16.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":true},{\"precipitation\":21,\"score\":0,\"temp\":16.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":94,\"isDay\":true},{\"precipitation\":18,\"score\":0,\"temp\":16.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":true},{\"precipitation\":16,\"score\":0,\"temp\":16.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":90,\"isDay\":true},{\"precipitation\":11,\"score\":0,\"temp\":17.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":85,\"isDay\":true},{\"precipitation\":5,\"score\":3,\"temp\":15.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":85,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":14.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":86,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":13.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":90,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":13.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":12.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":12,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":88,\"isDay\":false},{\"precipitation\":4,\"score\":3,\"temp\":11.6,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":77,\"isDay\":false},{\"precipitation\":6,\"score\":3,\"temp\":11.2,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":66,\"isDay\":false},{\"precipitation\":7,\"score\":0,\"temp\":11,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":61,\"isDay\":true},{\"precipitation\":9,\"score\":0,\"temp\":11,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":57,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":11.5,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":52,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":12.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":67,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":14.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":83,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":16,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":98,\"isDay\":true},{\"precipitation\":8,\"score\":0,\"temp\":17,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":true},{\"precipitation\":5,\"score\":0,\"temp\":17.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":true},{\"precipitation\":3,\"score\":0,\"temp\":18.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":93,\"isDay\":true},{\"precipitation\":8,\"score\":0,\"temp\":19.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":90,\"isDay\":true},{\"precipitation\":14,\"score\":0,\"temp\":19.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":87,\"isDay\":true},{\"precipitation\":19,\"score\":0,\"temp\":20.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":84,\"isDay\":true},{\"precipitation\":15,\"score\":0,\"temp\":20.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":88,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":20.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":19.5,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":96,\"isDay\":true},{\"precipitation\":4,\"score\":0,\"temp\":18.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":true},{\"precipitation\":2,\"score\":3,\"temp\":17.3,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":99,\"isDay\":false},{\"precipitation\":0,\"score\":3,\"temp\":15.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":1,\"score\":3,\"temp\":15.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":2,\"score\":3,\"temp\":14.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":14.2,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":14,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":97,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":13.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":95,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":13.8,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":92,\"isDay\":false},{\"precipitation\":4,\"score\":0,\"temp\":13.8,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":87,\"isDay\":true},{\"precipitation\":5,\"score\":0,\"temp\":13.8,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":83,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":14.2,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":78,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":14.8,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":76,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":15.6,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":74,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":16.7,\"visibility\":24140,\"weatherCode\":2,\"cloudCover\":72,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":17.7,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":60,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":18.7,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":47,\"isDay\":true},{\"precipitation\":6,\"score\":0,\"temp\":19.8,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":35,\"isDay\":true},{\"precipitation\":8,\"score\":0,\"temp\":20.3,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":31,\"isDay\":true},{\"precipitation\":11,\"score\":0,\"temp\":20.7,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":28,\"isDay\":true},{\"precipitation\":13,\"score\":0,\"temp\":20.8,\"visibility\":24140,\"weatherCode\":1,\"cloudCover\":24,\"isDay\":true},{\"precipitation\":13,\"score\":0,\"temp\":20.7,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":49,\"isDay\":true},{\"precipitation\":13,\"score\":0,\"temp\":20.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":75,\"isDay\":true},{\"precipitation\":13,\"score\":0,\"temp\":19.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":10,\"score\":0,\"temp\":18.6,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":true},{\"precipitation\":6,\"score\":3,\"temp\":17.4,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":15.9,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false},{\"precipitation\":3,\"score\":3,\"temp\":15.1,\"visibility\":24140,\"weatherCode\":3,\"cloudCover\":100,\"isDay\":false}]}");
     window.remote.setDay();
//    setDay(3);
     let hr = window.remote.getHour();
     setHour(hr);
//    setHour(13);
     let perc = window.remote.getScoreHereNow();
     useScore(perc);
//    useScore(4);
}

function makePage() {
    makeHours();
    document.getElementById("minnum").textContent = config.weather.daily[config.currentDay].tempMin+" °C";
    document.getElementById("maxnum").textContent = config.weather.daily[config.currentDay].tempMax+" °C";
     document.getElementById("weathericonimg").setAttribute("src",
       config.path + "icons/" + window.remote.getWeatherIcon(false,
           config.weather.daily[config.currentDay].weatherCode) + ".svg");
}

function setStar(segs) {
    for (let i = 0; i < segs; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#ddaa00");
    }
    for (let i = segs; i < 5; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#444444");
    }
}

function setHour(hr) {
    config.carousel.hourly_div.scrollLeft = 5 + 91 * hr
}

var config = {
    weather: {},
    currentDay: 0,
    carousel: undefined,
    path: ""
}


window.onload = function () {
    onStart();
};