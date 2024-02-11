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

	var id = sessionStorage.getItem('showOffer_id');
        
	xhttp.open("GET", "ViewStudentOfferServlet?arg1=" + id , true);
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
		contentElement.innerText = `\nOffer's description: \n\n ${offer.content} `;
		contentElement.className = "offerContent";


		offerDiv.appendChild(titleElement);
		offerDiv.appendChild(contentElement);

		containersContainer.appendChild(offerDiv);
	}
}