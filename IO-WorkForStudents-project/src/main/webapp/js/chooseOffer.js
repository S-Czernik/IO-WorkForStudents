window.onload = function() {
    startPage(0);
};

var selected_offer;

function startPage(pageNumber) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4) {
            console.log("Status:", this.status);
            console.log("Response:", this.responseText);

            if (this.status === 200) {
                if (this.responseText.trim() !== "") {
                    var offers = JSON.parse(this.responseText);
                    console.log("Parsed JSON:", offers);
                    displayOffers(offers);
                } else {
                    console.error("Error: Empty response");
                }
            } else {
                console.error("Error:", this.status);
            }
        }
    };

    var id = sessionStorage.getItem('found_id');
    xhttp.open("GET", "offersWithId?arg1=" + id + "&arg2=" + pageNumber, true);
    xhttp.send();
}

function displayOffers(offers) {
    var containersContainer = document.getElementById("containersContainerID");

    containersContainer.innerHTML = "";

    for (var i = 0; i < offers.length; i++) {
        var offer = offers[i];

        var offerDiv = document.createElement("div");
        offerDiv.className = "container";
        offerDiv.id = `offer_${offer.id_offer}`; // Set the ID for the offer element

        var titleElement = document.createElement("h2");
        titleElement.innerText = `Offer's Title: \n ${offer.title} `;
        titleElement.className = "offerTitle";

        var contentElement = document.createElement("p");
        contentElement.innerText = `Offer's description: \n ${offer.content} `;
        contentElement.className = "offerContent";

        var sendOffer = document.createElement("button");
        sendOffer.innerText = "Send Job Offer";
        sendOffer.value = "Send Job Offer";
        sendOffer.className = "sendJobOffer"; // Changed class name to avoid spaces

        sendOffer.addEventListener('click', function(id_offer) {
            return function() {
                // Set found_id_offer in session storage
                sessionStorage.setItem('found_id_offer', id_offer);
                // Call createClickListener with id_offer
                createClickListener(id_offer);
            };
        }(offer.id_offer));

        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);
        offerDiv.appendChild(sendOffer);

        containersContainer.appendChild(offerDiv);
    }
}

function createClickListener(id_offer) {
    selected_offer = id_offer;
    sendNotification(id_offer); // Pass id_offer to sendNotification
}

function sendNotification(offer_id) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4) {
            console.log("Status:", this.status);
            console.log("Response:", this.responseText);

            if (this.status === 200) {
                if (this.responseText.trim() !== "") {
                    var response = JSON.parse(this.responseText);
                    console.log("Parsed JSON:", response);
                    // Handle response if needed
                    redirectToMainPage();
                } else {
                    console.error("Error: Empty response");
                }
            } else {
                console.error("Error:", this.status);
            }
        }
    };

    var stud_id = sessionStorage.getItem('found_stud_id');
    console.log("found_student_id:", stud_id);
    var type = 'newOffer';
    xhttp.open("GET", "NotificationHandlerServlet?arg1=" + type + "&arg2=" + offer_id + "&arg3=" + stud_id, true);
    xhttp.send();
}

function redirectToMainPage() {
    window.location.href = "mainPageEmployer.html";
}
