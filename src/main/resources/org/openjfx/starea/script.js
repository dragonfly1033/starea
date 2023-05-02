function changeColour() {
    var letters = "0123456789ABCDEF"
    var colour = "#"
    for (var i = 0; i < 6; i++) {
        colour += letters[Math.floor(Math.random() * 16)];
    }
    var box = document.getElementById("box")
    box.style.backgroundColor = colour
    window.myObject.doIt(window.location.href);
}