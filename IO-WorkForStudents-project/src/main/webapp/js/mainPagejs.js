window.onload = loadOffers(0);

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

function searchForOffers(title) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
	
	var arg1 = document.getElementById(title).value.trim();

    if (arg1 === '')
        loadOffers(2);
    else {
        xhttp.open("GET", "searchoff?arg1=" + arg1, true);
		xhttp.send();
    }
}

function filterAndSortOffers(min, max) {
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
    var sort = document.querySelector('input[name="sort"]:checked').value;

    if (sort === '-1' && arg1 === '' && arg2 === '')
        loadOffers(2);
    else {
        if (arg1 === '' || arg2 === '')
            arg1 = arg2 = -1;
        
        xhttp.open("GET", "sortAndFilterOff?arg1=" + min + "&arg2=" + max + "&arg3=" + sort, true);
		xhttp.send();
    }
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
