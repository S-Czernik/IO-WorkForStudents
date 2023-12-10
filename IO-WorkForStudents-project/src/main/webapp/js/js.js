function register() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
        }
    };

    xhttp.open("GET", "register", true);
    xhttp.send();
}

function validateLogin(arg1, arg2, result) {
    event.preventDefault();
    
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var found = this.responseText;
            var resultElem = document.getElementById(result);

            if (found === "true") {
                document.getElementById("loginPage").action = "mainPage.html";
                document.getElementById("loginPage").submit();
            } else {
                resultElem.innerHTML = "Login failed. Please try again.";
            }
        }
    };
    
    var argument1 = document.getElementById(arg1).value;
    var argument2 = document.getElementById(arg2).value;

    xhttp.open("POST", "loginServlet?arg1=" + argument1 + "&arg2=" + argument2, true);
    xhttp.send();
}