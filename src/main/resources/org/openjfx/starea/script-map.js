
function gotoHomeScreen() {
    window.remote.gotoHomeScreen();
}

function onStart() {
    var slider = document.getElementById("myRange");
    var output = document.getElementById("radius");
    output.textContent = slider.value;

    slider.oninput = function() {
        output.textContent = slider.value;
    }
}

window.onload = function () {
    onStart();
}