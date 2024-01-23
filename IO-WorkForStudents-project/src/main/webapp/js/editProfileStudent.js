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

    var arg1 = sessionStorage.getItem('found_id');

    xhttp.open("GET", "editProfileStudentServlet?arg1=" + arg1, true);
    xhttp.send();
}

function displayProfileInfo(profileInfo) {
    console.log(profileInfo);
    document.getElementById('name').value = profileInfo[0].name;
    document.getElementById('surname').value = profileInfo[0].surname;
    document.getElementById('city').value = profileInfo[0].city;
    document.getElementById('title').value = profileInfo[0].title;
    document.getElementById('description').value = profileInfo[0].description;
    document.getElementById('picture').src = 'data:image/jpeg;base64,' + profileInfo[0].picture;
}

document.addEventListener("DOMContentLoaded", function () {
    document.getElementById('fileInput').addEventListener('input', handleFileSelect);
});

function handleFileSelect(event) {
    const input = event.target;
    const file = input.files[0];

    if (file) {
        console.log('Selected file:', file);

        const picture = document.getElementById('picture');
        const reader = new FileReader();
        reader.onload = function (e) {
            picture.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

function saveProfileChanges() {
    var userID = sessionStorage.getItem('found_id');
    var name = document.getElementById('name').value;
    var surname = document.getElementById('surname').value;
    var city = document.getElementById('city').value;
    var title = document.getElementById('title').value;
    var description = document.getElementById('description').value;

    var fileInput = document.getElementById('fileInput');
    var xhr = new XMLHttpRequest();
    // Ustaw metodę HTTP i adres URL serwletu
    xhr.open("POST", "editProfileStudentServlet?"+"userID="+userID+"&name="+name+"&surname="+surname+"&city="+city+"&title="+title
            +"&description="+description, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("Zapisano zmiany pomyślnie.");
            } else {
                console.error("Błąd podczas zapisywania zmian.");
            }
        }
    };

    xhr.send();
    window.location.href = "profileStudent.html";
}