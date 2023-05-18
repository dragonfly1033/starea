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
    createHourly(time, temp, cloudcover, precipitationProb, icon) {
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
        hourBox.appendChild(document.createElement('br'));

        let svg = document.createElement('img');
        svg.setAttribute('src', this.getIconPath(icon));
        svg.setAttribute('width', '50');
        svg.classList.add('svgWhite');
        hourBox.appendChild(svg);
        hourBox.appendChild(document.createElement('br'));

        if (this.temp_unit !== 'C') {
            temp = this.celsiusToFarenheit(temp);
        }

        let table = document.createElement('table');
        table.classList.add("hourlyTable");

        table.innerHTML = `
            <tr>
                <td class="hourlyTableLabels">Cloud:</td>
                <td class="hourlyTableNums">${cloudcover}%</td>
            </tr>
            <tr>
                <td class="hourlyTableLabels">Rain:</td>
                <td class="hourlyTableNums">${precipitationProb}%</td>
            </tr>
            <tr>
                <td class="hourlyTableLabels">Temp:</td>
                <td class="hourlyTableNums">${temp}°${this.temp_unit}</td>
            </tr>`
        hourBox.appendChild(table);
    }

    /**
     * Get path of icon.
     * @param {string} icon name of icon
     * @returns path of icon
     */
    getIconPath(icon) {
        return 'icons/'+icon+'.svg';
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
}