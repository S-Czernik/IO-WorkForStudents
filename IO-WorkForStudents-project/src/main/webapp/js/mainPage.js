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
		if (arg1.charAt(0) === '#')
			arg1 = arg1.replace(/#/g, '_');

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

		if (arg4.charAt(0) === '#')
			arg4 = arg4.replace(/#/g, '_');

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
		offerDiv.appendChild(titleElement);

		if (offer.title !== "Offer not found!") {
			var calendarMatchingDiv = document.createElement("div");
			calendarMatchingDiv.id = "calendarmatching" + offer.id_offer;
			calendarMatchingDiv.style.textAlign = "right";
			calendarMatchingDiv.style.width = "fit-content"; // Dostosowuje szerokość do zawartości
			calendarMatchingDiv.style.display = "inline-block"; // Powoduje, że ramka obejmuje tylko zawartość
			calendarMatchingDiv.style.border = "2px solid orange";
			calendarMatchingDiv.style.padding = "5px 10px"; // Dostosowuje padding do preferencji (góra/dół lewo/prawo)
			calendarMatchingDiv.style.marginLeft = "350px"; // Przesuwa element do prawej strony
			offerDiv.appendChild(calendarMatchingDiv);

			var contentElement = document.createElement("p");
			contentElement.innerText = offer.content; // Dodajemy treść i odpowiednią etykietę
			contentElement.className = "offerContent";
			contentElement.innerText += "\n Salary: $" + offer.salary + "."; // Dodajemy informację o wynagrodzeniu
			offerDiv.appendChild(contentElement);

			var applyElement = document.createElement("button");
			applyElement.innerText = "Apply";
			applyElement.value = "Hire";
			applyElement.className = "apply";
			applyElement.style.marginLeft = '10px';
			applyElement.addEventListener('click', function (id_offer) {
				return function () {
					alert("Your application has been sent!");
					sessionStorage.setItem('found_id_offer', id_offer);
					createClickListener(id_offer);
				};
			}(offer.id_offer));

			var hideElement = document.createElement("button");
			hideElement.innerText = "Hide";
			hideElement.value = "Hide";
			hideElement.className = "hide";
			hideElement.style.marginLeft = '10px';
			hideElement.addEventListener('click', hide(offer.id_offer));

			var calendarDiv = document.createElement("div");
			calendarDiv.id = "calendar" + offer.id_offer;
			calendarDiv.style.display = 'none';
			offerDiv.appendChild(calendarDiv);
			
			offerDiv.appendChild(applyElement);
			var showMoreElement = document.createElement("button");
			showMoreElement.innerText = "Show calendar";
			showMoreElement.value = "Show more";
			showMoreElement.className = "showMore";
			showMoreElement.style.marginLeft = '10px';
			showMoreElement.addEventListener('click', function (id_offer, buttonSelf) {
				return function () {
					var calendarDiv = document.getElementById("calendar" + id_offer);
					if (calendarDiv.style.display === 'none') {
						calendarDiv.style.display = 'block';
						buttonSelf.innerText = "Hide calendar";
					} else {
						calendarDiv.style.display = 'none';
						buttonSelf.innerText = "Show calendar";
					}
				};
			}(offer.id_offer, showMoreElement));
			offerDiv.appendChild(showMoreElement);
			
			offerDiv.appendChild(hideElement);
		}

		containersContainer.appendChild(offerDiv);

		if (offer.title !== "Offer not found!") {
			getOfferCalendarHtml(offer.id_offer);
			getOfferCalendarCompatibility(offer.id_offer);
		}
	}
	reveal();
}

function createClickListener(id_offer) {
	selected_offer = id_offer;
	sendNotification(id_offer); // Pass id_offer to sendNotification
}

function sendNotification(offer_id) {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function () {
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

	var stud_id = sessionStorage.getItem('found_id');
	var type = 'application';
	xhttp.open("GET", "NotificationHandlerServlet?arg1=" + type + "&arg2=" + offer_id + "&arg3=" + stud_id, true);
	xhttp.send();
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

document.addEventListener("DOMContentLoaded", function () {
	document.getElementById("sideBarSearch").addEventListener("keypress", function (event) {
		if (event.key === "Enter") {
			searchForOffers('sideBarSearch', 0);
		}
	});
});

window.addEventListener("scroll", reveal);