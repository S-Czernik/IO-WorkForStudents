document.addEventListener("DOMContentLoaded", function () {
    fetchProfileInfo();
});

function fetchProfileInfo() {
    var xhr = new XMLHttpRequest();
    var arg1 = sessionStorage.getItem('found_id');
    xhr.open("GET", "EditProfileEmployerServlet?arg1=" + arg1, true);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                try {
                    var profileInfo = JSON.parse(this.responseText);
                    displayProfileInfo(profileInfo);
                } catch (error) {
                    console.error('Błąd parsowania JSON:', error);
                }
            } else {
                console.error('Błąd HTTP:', this.status);
            }
        }
    };

    xhr.send();
}

function displayProfileInfo(profileInfo) {
    console.log(profileInfo);
    document.getElementById('company_name').value = profileInfo[0].company_name;
    document.getElementById('NIP').value = profileInfo[0].NIP;
    document.getElementById('city').value = profileInfo[0].city;
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
    } else {
        const picture = document.getElementById('picture');
    }
}

function saveProfileChanges() {
    var userID = sessionStorage.getItem('found_id');
    var company_name = document.getElementById('company_name').value;
    var NIP = document.getElementById('NIP').value;
    var city = document.getElementById('city').value;
    var description = document.getElementById('description').value;

    var fileInput = document.getElementById('fileInput');
    var formData = new FormData();
    formData.append('userID', userID);
    formData.append('company_name', company_name);
    formData.append('NIP', NIP);
    formData.append('city', city);
    formData.append('description', description);
    formData.append('picture', fileInput.files[0]);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "EditProfileEmployerServlet", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log("Zapisano zmiany pomyślnie.");
            } else {
                console.error("Błąd podczas zapisywania zmian.");
            }
            // Przekierowanie po zapisaniu zmian
            window.location.href = "profileEmployer.html";
        }
    };
    xhr.send(formData);
}