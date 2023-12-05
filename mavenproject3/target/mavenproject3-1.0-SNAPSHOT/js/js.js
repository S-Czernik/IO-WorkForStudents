function register() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
        }
    };

    xhttp.open("GET", "register", true);
    xhttp.send();
}