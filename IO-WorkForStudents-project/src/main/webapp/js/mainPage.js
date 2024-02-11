window.addEventListener('DOMContentLoaded', function () {
	window.scrollTo(0, 0);
	loadOffers(0);
	reveal();
});

let searched = false;
let filtered = false;
function nextPage(pageNumber, sideBar, min, max) {
	window.scrollTo(0, 0);

	if (!searched && !filtered)
		loadOffers(pageNumber);
	else if (filtered)
		filterAndSortOffers(min, max, sideBar, pageNumber);
	else if (searched)
		searchForOffers(sideBar, pageNumber);
}

function loadOffers(pageNumber) {
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

	xhttp.open("GET", "offersdisplay?arg1=" + pageNumber, true);
	xhttp.send();
}

function searchForOffers(title, pageNumber) {
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
		loadOffers(0);
	else {
		xhttp.open("GET", "searchoff?arg1=" + arg1 + "&arg2=" + pageNumber, true);
		xhttp.send();
	}
}

function filterAndSortOffers(min, max, search, pageNumber) {
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

		xhttp.open("GET", "sortAndFilterOff?arg1=" + arg1 + "&arg2=" + arg2 + "&arg3=" + arg3 + "&arg4=" + arg4 + "&arg5=" + pageNumber, true);
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
		offerDiv.id = `offer_${offer.id_offer}`;

		var titleElement = document.createElement("h2");
		titleElement.innerText = offer.title;
		titleElement.className = "offerTitle";

		var contentElement = document.createElement("p");
		contentElement.innerText = offer.content;
		contentElement.className = "offerContent";

		var showMoreElement = document.createElement("button");
        showMoreElement.innerText = "Show more";
        showMoreElement.value = "Show more";
        showMoreElement.className = "showMore";
		
		var applyElement = document.createElement("button");
        applyElement.innerText = "Apply";
        applyElement.value = "Apply";
        applyElement.className = "apply";
		applyElement.style.marginLeft = '10px';
		
		applyElement.addEventListener('click', applyForOffer(offer.id_person));
		
		var hideElement = document.createElement("button");
        hideElement.innerText = "Hide";
        hideElement.value = "Hide";
        hideElement.className = "hide";
		hideElement.style.marginLeft = '10px';

		hideElement.addEventListener('click', hide(offer.id_offer));

		var calendarDiv = document.createElement("div");
		calendarDiv.id = "calendar" + offer.id_offer;

		offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);
		offerDiv.appendChild(calendarDiv);
        offerDiv.appendChild(showMoreElement);
		offerDiv.appendChild(applyElement);
		offerDiv.appendChild(hideElement);

		containersContainer.appendChild(offerDiv);
		getOfferCalendarHtml(offer.id_offer);
	}
	reveal();
}

function applyForOffer(id_person) {
	return function () {
		
	};
}

function hide(selected_offer) {
	return function () {
		var containersContainer = document.getElementById("containersContainerID");
		var offerDiv = document.getElementById(`offer_${selected_offer}`);
		if (offerDiv) {
			containersContainer.removeChild(offerDiv);
		} else {
			console.error(`Could not remove offer. Element not found for offer ID: ${selected_offer}`);
		}
		reveal();
	};
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