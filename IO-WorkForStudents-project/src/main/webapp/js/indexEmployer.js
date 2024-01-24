window.addEventListener('DOMContentLoaded', function() {
	window.scrollTo(0, 0);
    loadProfiles(0);
	reveal();
});

let searched = false;
let filtered = false;
function nextPage(pageNumber, sideBar, min, max) {
	reveal();
    window.scrollTo(0, 0);

	if (!searched && !filtered)
        loadProfiles(pageNumber);
	else if (filtered)
		filterAndSortProfiles(min, max, sideBar, pageNumber);
    else if (searched)
		searchForProfiles(sideBar, pageNumber);
	reveal();
}

function loadProfiles(pageNumber) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
			searched = false;
			filtered = false;
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

    xhttp.open("GET", "profilesdisplay?arg1=" + pageNumber, true);
    xhttp.send();
	reveal();
}

function searchForProfiles(title, pageNumber) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
			searched = true;
			filtered = false;
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
	
	var arg1 = document.getElementById(title).value.trim();
    if (arg1 === '')
        loadProfiles(0);
    else {
        xhttp.open("GET", "searchprof?arg1=" + arg1 + "&arg2=" + pageNumber, true);
		xhttp.send();
    }
	reveal();
}

function filterAndSortProfiles(min, max, search, pageNumber) {
    window.scrollTo(0, 0);
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
			filtered = true;
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };

	var arg1 = document.getElementById(min).value.trim();
    var arg2 = document.getElementById(max).value.trim();
    var arg3 = document.querySelector('input[name="sort"]:checked').value;
	var arg4 = document.getElementById(search).value.trim();

    if (arg3 === '9' && arg1 === '' && arg2 === '')
        loadOffers(0);
    else {
        if (arg1 === '' || arg2 === '' || (arg1 === '0' && arg2 === '0'))
            arg1 = arg2 = -1;
        
        xhttp.open("GET", "sortAndFilterProf?arg1=" + arg1 + "&arg2=" + arg2 + "&arg3=" + arg3 + "&arg4=" + arg4 + "&arg5=" + pageNumber, true, true);
		xhttp.send();
    }
	reveal();
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
        // var showMoreElement = document.createElement("button");



        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);
        offerDiv.appendChild(showMoreElement);

        containersContainer.appendChild(offerDiv);
    }
	reveal();
}

function reveal() {
  var reveals = document.querySelectorAll(".container");

  for (var i = 0; i < reveals.length; i++) {
    var windowHeight = window.innerHeight;
    var elementTop = reveals[i].getBoundingClientRect().top;
    var elementVisible = 50;

    if (elementTop < windowHeight - elementVisible) {
      reveals[i].classList.add("active");
    } else {
      reveals[i].classList.remove("active");
    }
  }
}

window.addEventListener("scroll", reveal);