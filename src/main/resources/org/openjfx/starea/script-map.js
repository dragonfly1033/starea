
function gotoHomeScreen() {
    window.remote.gotoHomeScreen();
}

function createNewLocation(radius)  {
}

function onStart() {
    config.path = window.remote.getPath();

    var slider = document.getElementById("myRange");
    var output = document.getElementById("radius");
    output.textContent = slider.value;

    slider.oninput = function() {
        output.textContent = slider.value;
    }

    slider.onmouseup = function() {
        createNewLocation(slider.value);
    }

    let imageObj = document.createElement("img");

    imageObj.setAttribute("src", config.path + "map.png");

    document.getElementById("mapDiv").appendChild(imageObj);

    window.remote.log(config.path + "map.png");
}

function useScore(segs) {
    //Function to update star-o-meter goes here
    for (let i = 0; i < segs; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#ddaa00");
    }
    for (let i = segs; i < 5; i++) {
        document.getElementById("segment"+i).setAttribute("fill", "#444444");
    }
};

window.onload = function () {
    onStart();
}


var config = {
    path: ""
}