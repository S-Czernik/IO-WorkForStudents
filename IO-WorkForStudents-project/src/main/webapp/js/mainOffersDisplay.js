window.onload = function () {
    if (!sessionStorage.getItem('firstOpen')) {
        setType();
        sessionStorage.setItem('firstOpen', 'true');
    }
};

let currentType = "student";

function setType() {
    currentType = sessionStorage.getItem('found_type').trim();

    if (currentType === "student")
        loadOffers(3);
    else
        loadProfiles(3);
}

function nextPage(pageNumber) {
    window.scrollTo(0, 0);
    var arg = pageNumber;
    if (currentType === 'student')
        loadOffers(arg);
    else
        loadProfiles(arg);
}

function loadOffers(pageNumber) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "offersdisplay?arg1=" + pageNumber, true);
    xhttp.send();
}

function loadProfiles(pageNumber) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "profilesdisplay?arg1=" + pageNumber, true);
    xhttp.send();
}

function searchType(title) {
    window.scrollTo(0, 0);
    var arg = document.getElementById(title).value;
    if (currentType === 'student')
        searchForOffers(arg);
    else
        searchForProfiles(arg);
}

function searchForOffers(title) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "searchoff?arg1=" + title, true);
    xhttp.send();
}

function searchForProfiles(title) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "searchprof?arg1=" + title, true);
    xhttp.send();
}

function filterAndSort(min, max) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
    
    var arg1 = document.getElementById(min).value.trim();
    var arg2 = document.getElementById(max).value.trim();
    var selectedSortOption = document.querySelector('input[name="sort"]:checked').value;

    xhttp.open("GET", "sortAndFilter?arg1=" + arg1 + "&arg2=" + arg2 + "&arg3=" + selectedSortOption, true);
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
