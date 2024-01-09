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

    xhttp.open("GET", "ProfileEmployerServlet", true);
    xhttp.send();
}

function displayProfileInfo(profileInfo) {
    console.log(profileInfo);
    document.getElementById('login').innerText ="Your profile, " + profileInfo[0].login;
    document.getElementById('company_name').innerText = profileInfo[0].company_name;
    document.getElementById('email').innerText = profileInfo[0].email;
    document.getElementById('city').innerText = profileInfo[0].city;
    document.getElementById('NIP').innerText = profileInfo[0].NIP;
    document.getElementById('description').innerText = profileInfo[0].description;
    document.getElementById('picture').src = 'data:image/jpeg;base64,' + profileInfo[0].picture;
}