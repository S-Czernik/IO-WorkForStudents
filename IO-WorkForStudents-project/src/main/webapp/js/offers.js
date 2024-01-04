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

function searchForOffer(title) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            //var offers = JSON.parse(this.responseText);
            //displayOffers(offers);
        }
    };
    
    var arg1 = document.getElementById(title).value;
    
    xhttp.open("GET", "offerssearch?arg1=" + arg1, true);
    xhttp.send();
}

function displayOffers(offers) {
    var containersContainer = document.getElementById("containersContainerID");

    containersContainer.innerHTML = "";

    for (var i = 0; i < offers.length; i++) {
        var offer = offers[i];

        var offerDiv = document.createElement("div");
        offerDiv.className = "container";
        
        var titleElement = document.createElement("h2");
        titleElement.innerText = offer.title;
        titleElement.className = "offerTitle";

        var contentElement = document.createElement("p");
        contentElement.innerText = offer.content;
        contentElement.className = "offerContent";

        var showMoreElement = document.createElement("button");
        showMoreElement.innerText = "show more";
       showMoreElement.value = "show more";
        showMoreElement.className = "showMore";
      //  var showMoreElement = document.createElement("button");
        
        
        
        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);
        offerDiv.appendChild(showMoreElement);

        containersContainer.appendChild(offerDiv);
    }
}

function displayOffersMainPage(offers) {
    var containersContainer = document.getElementById("containersContainerID");

    containersContainer.innerHTML = "";

    for (var i = 0; i < offers.length; i++) {
        var offer = offers[i];

        var offerDiv = document.createElement("div");
        offerDiv.className = "container";
        
        var titleElement = document.createElement("h2");
        titleElement.innerText = offer.title;
        titleElement.className = "offerTitle";

        var contentElement = document.createElement("p");
        contentElement.innerText = offer.content;
        contentElement.className = "offerContent";
        
        var percentageElement = document.createElement("p");
        percentageElement.innerText = offer.percentage;
        percentageElement.className = "offerPercentage";
        
        var showMoreElement = document.createElement("button");
        showMoreElement.innerText = "show more";
        showMoreElement.value = "show more";
        showMoreElement.className = "showMore";

        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(percentageElement);
        offerDiv.appendChild(contentElement);
        offerDiv.appendChild(showMoreElement);

        containersContainer.appendChild(offerDiv);
    }
}
