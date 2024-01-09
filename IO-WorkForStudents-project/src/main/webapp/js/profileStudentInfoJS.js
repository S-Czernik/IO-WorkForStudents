document.addEventListener("DOMContentLoaded", function () {
    fetchProfileInfo();
});

function fetchProfileInfo() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            try {
                var profileInfo = JSON.parse(this.responseText);
                displayProfileInfo(profileInfo);
            } catch (error) {
                console.error('Błąd parsowania JSON:', error);
            }
        } else {
            console.error('Błąd HTTP:', this.status);
        }
    };

    xhttp.open("GET", "ProfileStudentServlet", true);
    xhttp.send();
}

function displayProfileInfo(profileInfo) {
    console.log(profileInfo);
    document.getElementById('login').innerText ="Your profile, " + profileInfo[0].login;
    document.getElementById('name_and_surname').innerText = profileInfo[0].name +" " + profileInfo[0].surname;
    document.getElementById('email').innerText = profileInfo[0].email;
    document.getElementById('city').innerText = profileInfo[0].city;
    document.getElementById('title').innerText ='"' + profileInfo[0].title + '"';
    document.getElementById('description').innerText = profileInfo[0].description;
    document.getElementById('picture').src = 'data:image/jpeg;base64,' + profileInfo[0].picture;
}