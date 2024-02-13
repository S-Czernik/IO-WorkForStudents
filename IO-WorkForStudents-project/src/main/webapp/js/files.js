document.addEventListener("DOMContentLoaded", function () {
    var currentLocation = window.location.pathname;

    if (currentLocation.includes("profileStudent.html")) {
        fetchFiles(displayFilesOnNormalProfile);
    } else if (currentLocation.includes("editProfileStudent.html")) {
        fetchFiles(displayFilesOnEditProfile);
    } else {
        console.error("Nieznana strona:", currentLocation);
    }
    var filesInput = document.getElementById('filesInput');
    filesInput.addEventListener('change', addFile);
});

function fetchFiles(callback) {
    var arg1 = sessionStorage.getItem('found_id');
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "FilesServlet?arg1=" + arg1, true);
    xhr.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                try {
                    var files = JSON.parse(this.responseText);
                    callback(files);
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

function displayFilesOnNormalProfile(files) {
    var filesList = document.getElementById("uploaded-files-list");

    filesList.innerHTML = "";

    files.forEach(function (file) {
        var fileElement = document.createElement("div");
        fileElement.className = "uploaded-file";

        var fileNameElement = document.createElement("a");
        fileNameElement.textContent = file.fileName;
        fileNameElement.onclick = function () {
            console.log("FILE CLICKED");
            var fileURL = 'data:application/pdf;base64,' + arrayBufferToBase64(file.fileData);
            var newWindow = window.open();
            newWindow.document.write('<embed width="100%" height="100%" src="' + fileURL + '" type="application/pdf" />');
        };
        fileElement.appendChild(fileNameElement);
        filesList.appendChild(fileElement);
    });
}

function displayFilesOnEditProfile(files) {
    var filesList = document.getElementById("uploaded-files-list-edit");

    filesList.innerHTML = "";

    files.forEach(function (file) {
        var fileElement = document.createElement("div");
        fileElement.className = "uploaded-file";

        var fileNameElement = document.createElement("a");
        fileNameElement.textContent = file.fileName;

        var deleteButton = document.createElement("span");
        deleteButton.textContent = "x";
        deleteButton.className = "delete-button";
        deleteButton.onclick = function () {
            var confirmation = confirm("Czy na pewno chcesz usunąć ten plik?");
            if (confirmation) {
                deleteFileFromDatabase(file.id);
                filesList.removeChild(fileElement);
            }
        };

        fileElement.appendChild(deleteButton);
        fileElement.appendChild(fileNameElement);
        filesList.appendChild(fileElement);
    });
}

function addFile() {
    var input = document.getElementById('filesInput');
    var files = input.files;

    for (var i = 0; i < files.length; i++) {
        var file = files[i];
        var fileName = file.name;
        var formData = new FormData();
        var userID = sessionStorage.getItem('found_id');
        console.log(userID);
        formData.append('operationType', "add");
        formData.append('userID', userID);
        formData.append('fileName', fileName);
        formData.append('fileData', file);


        sendFileToServer(formData);
    }
}

function sendFileToServer(formData) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'FilesServlet', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Plik został pomyślnie przesłany na serwer');
            fetchFiles(displayFilesOnEditProfile);
        } else {
            console.error('Wystąpił błąd podczas przesyłania pliku na serwer');
        }
    };
    xhr.send(formData);
}

function deleteFileFromDatabase(fileId) {
    var formData = new FormData();
    formData.append('operationType', "delete");
    formData.append('fileID', fileId);
    sendFileDeletionRequestToServer(formData);
}

function sendFileDeletionRequestToServer(formData) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'FilesServlet', true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            console.log('Plik został pomyślnie usunięty z bazy danych');
            fetchFiles(displayFilesOnEditProfile);
        } else {
            console.error('Wystąpił błąd podczas usuwania pliku z bazy danych');
        }
    };
    xhr.send(formData);
}

function arrayBufferToBase64(buffer) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
}