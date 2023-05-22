
function gotoHomeScreen() {
    window.remote.gotoHomeScreen();
}

function createNewLocation(radius)  {
    return JSON.parse(window.remote.getNewBestLocation(radius));
}

function showDocument() {
    if (config.currMapURL !== "") {
        window.remote.openBrowserWindow(config.currMapURL);
    }
}

function updateLocation(radius) {
    let bestLoc = createNewLocation(radius);

    let imageElm = document.getElementById("mapImg");

    imageElm.setAttribute("src", bestLoc.img);
    config.currMapURL = bestLoc.href;
    useScore(bestLoc.score);
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
        updateLocation(slider.value);
    }

    updateLocation(slider.value);

    document.getElementById("mapLink").onclick = function () {
        showDocument();
    };
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
    path: "",
    currMapURL: ""
}