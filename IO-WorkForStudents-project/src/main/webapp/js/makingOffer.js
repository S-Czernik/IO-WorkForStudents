window.onload = startPage(0);

var selected_offer;
function startPage(pageNumber) {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function () {
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
	reveal();
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

		var editOffer = document.createElement("button");
		editOffer.innerText = "Edit offer";
		editOffer.className = "apply";
		editOffer.style.marginLeft = '20px';
		editOffer.style.marginRight = '10px'; // Optional: Add some styling to separate the buttons

		// Use a closure to capture the current value of i
		editOffer.addEventListener('click', createEditClickListener(offer.id_offer, 1));

		var deleteOffer = document.createElement("button");
		deleteOffer.innerText = "Delete offer";
		deleteOffer.value = "Delete";
		deleteOffer.className = "apply";
		deleteOffer.style.marginBottom = '15px';
		deleteOffer.style.marginRight = '10px';

		deleteOffer.addEventListener('click', createEditClickListener(offer.id_offer, 2));

		var calendarDiv = document.createElement("div");
		calendarDiv.id = "calendar" + offer.id_offer;
		calendarDiv.style.display = 'none';
		
		var editOfferCalendar = document.createElement("button");
		editOfferCalendar.innerText = "Edit calendar";
		editOfferCalendar.value = "Edit";
		editOfferCalendar.className = "apply";
		editOfferCalendar.style.marginLeft = '10px';
		editOfferCalendar.style.display = "inline";

		var showMoreElement = document.createElement("button");
		showMoreElement.innerText = "Show calendar";
		showMoreElement.value = "Show more";
		showMoreElement.className = "apply";
		showMoreElement.style.marginLeft = '20px';
		showMoreElement.addEventListener('click', function (id_offer, buttonSelf) {
			return function () {
				var calendarDiv = document.getElementById("calendar" + id_offer);
				if (calendarDiv.style.display === 'none') {
					calendarDiv.style.display = 'block';
					editOfferCalendar.style.display = "inline";
					editOfferCalendar.style.display = "inline";
					buttonSelf.innerText = "Hide calendar";
				} else {
					calendarDiv.style.display = 'none';
					editOfferCalendar.style.display = 'none';
					editOfferCalendar.style.display = "none";
					buttonSelf.innerText = "Show calendar";
				}
			};
		}(offer.id_offer, showMoreElement));
 
		editOfferCalendar.addEventListener('click', function (offerIdPerson) {
            return function () {
                sessionStorage.setItem('found_id_offer', offerIdPerson);
                window.location.href = "editCalendar.html";
            };
        }(offer.id_offer));

		offerDiv.appendChild(titleElement);
		offerDiv.appendChild(contentElement);
		offerDiv.appendChild(editOffer);
		offerDiv.appendChild(deleteOffer);
		offerDiv.appendChild(calendarDiv);
		offerDiv.appendChild(showMoreElement);
		offerDiv.appendChild(editOfferCalendar);

		containersContainer.appendChild(offerDiv);
		getOfferCalendarHtml(offer.id_offer);
	}
	reveal();
}

// Function to create a closure for the click event listener
function createEditClickListener(id_offer, type) {
	return function () {
		selected_offer = id_offer;
		sessionStorage.setItem('found_id_offer', id_offer);
		if (type === 2)
			confirmDeletion();
		else {
			window.location.href = 'offerEditor.html';
		}
	};
}

function confirmDeletion() {
	var confirmationModal = document.getElementById('confirmationModal');
	confirmationModal.style.display = 'block';
}

function removeOffer() {
	// Implement the functionality to visually remove the offer
	var containersContainer = document.getElementById("containersContainerID");
	var offerDiv = document.getElementById(`offer_${selected_offer}`); // Assuming offer elements have an ID like "offer_123"

	if (offerDiv) {
		containersContainer.removeChild(offerDiv);
	} else {
		console.error(`Could not remove offer. Element not found for offer ID: ${selected_offer}`);
	}
}

function confirmAction(confirmation) {
	var confirmationModal = document.getElementById('confirmationModal');
	confirmationModal.style.display = 'none';

	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			console.log("Status:", this.status);
			console.log("Response:", this.responseText);

			if (this.status === 200) {
				var deleted = this.responseText;
				if (deleted) {
					// Handle success or additional actions if needed
				}
			} else {
				console.error("Error:", this.status);
			}
		}
	};

	if (confirmation) {
		removeOffer(); // Remove the offer visually
		reveal();
		var encodedSelectedOffer = sessionStorage.getItem('found_id_offer');
		xhttp.open("GET", "OfferDeletionServlet?arg1=" + encodedSelectedOffer, true);
		xhttp.send();
	}
	reveal();
}

function validateOffer(arg2, arg3, arg4, arg5, result) {
	event.preventDefault();
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var correct = this.responseText;

			if (correct === "true") {
				document.getElementById("titleInput").value = "";
				document.getElementById("contentInput").value = "";
				document.getElementById("salaryInput").value = "";
				resultElem.innerHTML = "";

				document.getElementById("offersPage").action = "offers.html";
				document.getElementById("offersPage").submit();
			} else {
				resultElem.innerHTML = "Addition of an offer failed. Please try different data.";
			}
		}
	};

	var resultElem = document.getElementById(result);
	var title = document.getElementById(arg2).value;
	var content = document.getElementById(arg3).value;
	var salary = document.getElementById(arg4).value;
	var tags = document.getElementById(arg5).value;
	tags = tags.replace(/#/g, '');
	if (!title.trim() || !content.trim() || !salary.trim()) {
		resultElem.innerHTML = "Addition of an offer failed. Please complete all fields.";
	} else {
		xhttp.open("GET", "offerAddingServlet?arg1=" + sessionStorage.getItem('found_id') + "&arg2=" + title + "&arg3=" + content + "&arg4=" + salary + "&arg5=" + tags, true);
		xhttp.send();
	}
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