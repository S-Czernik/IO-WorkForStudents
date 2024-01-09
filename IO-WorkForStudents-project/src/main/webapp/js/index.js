window.onload = loadOffers(0);

let currentType = "student";

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
            currentType = "student";
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
            currentType = "employer";
        }
    };

    xhttp.open("GET", "profilesdisplay?arg1=" + pageNumber, true);
    xhttp.send();
}

function searchType(title) {
    window.scrollTo(0, 0);
    var arg = document.getElementById(title).value.trim();

    if (arg === '')
        loadOffers(2);
    else {
        if (currentType === 'student')
            searchForOffers(arg);
        else
            searchForProfiles(arg);
    }
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

function sortType(min, max) {
    window.scrollTo(0, 0);
    var arg1 = document.getElementById(min).value.trim();
    var arg2 = document.getElementById(max).value.trim();
    var sort = document.querySelector('input[name="sort"]:checked').value;

    if (sort === '-1' && arg1 === '' && arg2 === '')
        nextPage(2);
    else {
        if (arg1 === '' || arg2 === '')
            arg1 = arg2 = -1;
        
        if (currentType === 'student')
            filterAndSortOffers(arg1, arg2, sort);
        else
            filterAndSortProfiles(arg1, arg2, sort);
    }
}

function filterAndSortOffers(min, max, sort) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
    
    xhttp.open("GET", "sortAndFilterOff?arg1=" + min + "&arg2=" + max + "&arg3=" + sort, true);
    xhttp.send();
}

function filterAndSortProfiles(min, max, sort) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "sortAndFilterProf?arg1=" + min + "&arg2=" + max + "&arg3=" + sort, true);
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
