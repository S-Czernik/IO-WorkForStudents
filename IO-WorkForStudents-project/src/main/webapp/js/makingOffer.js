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

		var editOffer = document.createElement("a");
		editOffer.innerText = "Edit";
		editOffer.className = "Edit";
		editOffer.href = 'offerEditor.html';
		editOffer.style.marginRight = '10px'; // Optional: Add some styling to separate the buttons

		// Use a closure to capture the current value of i
		editOffer.addEventListener('click', createEditClickListener(offer.id_offer, 1));

		var deleteOffer = document.createElement("button");
		deleteOffer.innerText = "Delete";
		deleteOffer.value = "Delete";
		deleteOffer.className = "Delete";

		deleteOffer.addEventListener('click', createEditClickListener(offer.id_offer, 2));

		offerDiv.appendChild(titleElement);
		offerDiv.appendChild(contentElement);
		offerDiv.appendChild(editOffer);
		offerDiv.appendChild(deleteOffer);

		containersContainer.appendChild(offerDiv);
	}
}

// Function to create a closure for the click event listener
function createEditClickListener(id_offer, type) {
	return function () {
		selected_offer = id_offer;
		sessionStorage.setItem('found_id_offer', id_offer);
		if(type === 2)
			confirmDeletion();
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

	removeOffer(); // Remove the offer visually
	if (confirmation) {
		var encodedSelectedOffer = sessionStorage.getItem('found_id_offer');
		xhttp.open("GET", "OfferDeletionServlet?arg1=" + encodedSelectedOffer, true);
		xhttp.send();
	}
}

function validateOffer(arg2, arg3, arg5, result) {
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
	var salary = document.getElementById(arg5).value;
	if (!title.trim() || !content.trim() || !salary.trim()) {
		resultElem.innerHTML = "Addition of an offer failed. Please complete all fields.";
	} else {
		xhttp.open("GET", "offerAddingServlet?arg1=" + sessionStorage.getItem('found_id') + "&arg2=" + title + "&arg3=" + content + "&arg5=" + salary, true);
		xhttp.send();
	}
}