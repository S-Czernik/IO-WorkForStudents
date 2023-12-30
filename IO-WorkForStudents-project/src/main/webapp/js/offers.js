window.onload = startPage(0);

function startPage(pageNumber) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
    
    //let currentPage = window.location.href;
    //var page = currentPage.includes('mainPage.html') ? 1 : 0;  do późniejszego odróżniania czy mają być wyświetlane też %
    //xhttp.open("GET", "offersdisplay?arg1=" + page, true);
    
    xhttp.open("GET", "offersdisplay?arg1=" + pageNumber, true);
    xhttp.send();
}

function displayOffers(offers) {
    var containersContainer = document.getElementById("containersContainerID");

    containersContainer.innerHTML = "";

    for (var i = 0; i < offers.length; i++) {
        var offer = offers[i];

        var offerDiv = document.createElement("div");
        offerDiv.className = "offer";
        
        var titleElement = document.createElement("h2");
        titleElement.innerText = offer.title;

        var contentElement = document.createElement("p");
        contentElement.innerText = offer.content;

        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);

        containersContainer.appendChild(offerDiv);
    }
}
