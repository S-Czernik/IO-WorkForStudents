window.addEventListener('DOMContentLoaded', function () {
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
		if (arg1.charAt(0) === '#')
			arg1 = arg1.replace(/#/g, '_');

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

		if (arg4.charAt(0) === '#')
			arg4 = arg4.replace(/#/g, '_');

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
		offerDiv.id = `offer_${offer.id_offer}`;

		var titleElement = document.createElement("h2");
		titleElement.innerText = offer.title;
		titleElement.className = "offerTitle";
		offerDiv.appendChild(titleElement);

		if (offers.length > 1 && offer.title !== "Profile not found!") {
			var contentElement = document.createElement("p");
			contentElement.innerText = offer.content;
			contentElement.className = "offerContent";
			offerDiv.appendChild(contentElement);

			var applyElement = document.createElement("button");
			applyElement.innerText = "Apply";
			applyElement.value = "Apply";
			applyElement.className = "showMore";
			applyElement.style.marginLeft = '10px';
			applyElement.addEventListener('click', function () {
				window.location.href = 'login.html';
			});
			offerDiv.appendChild(applyElement);

			var hideElement = document.createElement("button");
			hideElement.innerText = "Hide";
			hideElement.value = "Hide";
			hideElement.className = "hide";
			hideElement.style.marginLeft = '10px';
			hideElement.addEventListener('click', hide(offer.id_offer));
			offerDiv.appendChild(hideElement);

			var calendarDiv = document.createElement("div");
			calendarDiv.id = "calendar" + offer.id_person;
			calendarDiv.style.display = 'none';
			offerDiv.appendChild(calendarDiv);

			var showMoreElement = document.createElement("button");
			showMoreElement.innerText = "Show calendar";
			showMoreElement.value = "Show more";
			showMoreElement.className = "showMore";
			showMoreElement.addEventListener('click', function (offerIdPerson, buttonSelf) {
				return function () {
					var calendarDiv = document.getElementById("calendar" + offerIdPerson);
					if (calendarDiv.style.display === 'none') {
						calendarDiv.style.display = 'block';
						buttonSelf.innerText = "Hide calendar";
					} else {
						calendarDiv.style.display = 'none';
						buttonSelf.innerText = "Show calendar";
					}
				};
			}(offer.id_person, showMoreElement));
			offerDiv.appendChild(showMoreElement);
		}

		containersContainer.appendChild(offerDiv);

		if (offers.length > 1 && offer.title !== "Profile not found!") {
			getStudentCalendarHtml(offer.id_person);
		}
	}
	reveal();
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
			searchForProfiles('sideBarSearch', 0);
		}
	});
});

window.addEventListener("scroll", reveal);