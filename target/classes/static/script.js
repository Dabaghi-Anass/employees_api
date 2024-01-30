console.log("Hello from script.js")
const clock = document.getElementById("clock");
let date;
function updateClock() {
    date = new Date();
    clock.innerText = date.toLocaleTimeString();
}

setInterval(updateClock, 1000);