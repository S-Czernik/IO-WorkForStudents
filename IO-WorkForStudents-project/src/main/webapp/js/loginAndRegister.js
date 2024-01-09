function validateRegister(arg1, arg2, arg3, arg4, result) {
    event.preventDefault();
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var correct = this.responseText;

            if (correct === "true") {
                document.getElementById("loginInput").value = "";
                document.getElementById("emailInput").value = "";
                document.getElementById("passwordInput").value = "";
                document.getElementById("passwordRepeatInput").value = "";
                resultElem.innerHTML = "";
                
                document.getElementById("registerPage").action = "mainPage.html";
                document.getElementById("registerPage").submit();
            } else {
                resultElem.innerHTML = "Register failed. Please try different data.";
            }
        }
    };

    var resultElem = document.getElementById(result);
    var userTypeRadio = document.querySelector('input[name="userType"]:checked');
    var type = userTypeRadio ? userTypeRadio.value : null;
    var login = document.getElementById(arg1).value;
    var email = document.getElementById(arg2).value;
    var passwd = document.getElementById(arg3).value;
    var passwdCheck = document.getElementById(arg4).value;
    var passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]).{10,}$/;

    if (!type || !login.trim() || !email.trim() || !passwd.trim() || !passwdCheck.trim()) {
        resultElem.innerHTML = "Register failed. Please complete all fields.";
    } else if (!passwordRegex.test(passwd)) {
        resultElem.innerHTML = "Register failed. Password is too simple - please try different password. (minimum 10 characters, capital letter, number and symbol)";
    } else if (passwd !== passwdCheck) {
        resultElem.innerHTML = "Register failed. Passwords are different.";
    } else {
        xhttp.open("POST", "registerServlet?arg1=" + type + "&arg2=" + login + "&arg3=" + passwd + "&arg4=" + email, true);
        xhttp.send();
    }
}

function validateLogin(arg1, arg2, result) {
    event.preventDefault();
    var xhttp = new XMLHttpRequest();
<<<<<<<< HEAD:IO-WorkForStudents-project/src/main/webapp/js/loginAndRegisterjs.js
    
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var parts = this.responseText.split('.');
            var found_id = parts[0];
            var found_type = parts[1];
========

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var found_id = this.responseText;
>>>>>>>> NotificationOnUserProfile:IO-WorkForStudents-project/src/main/webapp/js/loginAndRegister.js

            if (found_id !== "-1") {
                document.getElementById("arg1").value = "";
                document.getElementById("arg2").value = "";
                resultElem.innerHTML = "";
<<<<<<<< HEAD:IO-WorkForStudents-project/src/main/webapp/js/loginAndRegisterjs.js

                sessionStorage.setItem('found_id', found_id);
                sessionStorage.setItem('found_type', found_type);

========
                
                sessionStorage.setItem('found_id', found_id);
                
>>>>>>>> NotificationOnUserProfile:IO-WorkForStudents-project/src/main/webapp/js/loginAndRegister.js
                document.getElementById("loginPage").action = "mainPage.html";
                document.getElementById("loginPage").submit();
            } else {
                if (!argument1.trim() || !argument2.trim()) {
                    resultElem.innerHTML = "Login failed. Please complete all fields.";
                } else {
                    resultElem.innerHTML = "Login failed. Please try again.";
                }
            }
        }
    };

    var resultElem = document.getElementById(result);
    var argument1 = document.getElementById(arg1).value;
    var argument2 = document.getElementById(arg2).value;

    xhttp.open("POST", "loginServlet?arg1=" + argument1 + "&arg2=" + argument2, true);
    xhttp.send();
}