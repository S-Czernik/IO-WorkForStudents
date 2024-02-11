var id_offer;

window.onload = function () {
	initializeData();
};

function initializeData() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				try {
					var offer = JSON.parse(this.responseText);
					console.log('Parsed JSON data:', offer);
					fillTextFieldsData(offer);
				} catch (error) {
					console.error('Error parsing JSON:', error);
					console.error('Invalid JSON response:', this.responseText);
				}
			} else if (this.status === 404) {
				console.error('Error: Offer not found');
			} else if (this.status === 500) {
				console.error('Error: Internal server error');
			} else {
				console.error('Error:', this.status);
				console.error('Empty or invalid response:', this.responseText);
			}
		}
	};
	var encodedSelectedOffer = encodeURIComponent(id_offer);
	xhttp.open("GET", "OfferGetServlet?arg1=" + encodedSelectedOffer, true);
	xhttp.send();
}



function fillTextFieldsData(offer) {
	console.log('Received JSON data:', offer);

	// Check if the offer array is not empty
	if (Array.isArray(offer) && offer.length > 0) {
		var offerEdit = offer[0];

		// Assume offerDetails is an object containing the saved offer data in your JS
		var offerDetails = {
			title: offerEdit.title,
			content: offerEdit.content,
			tags: offerEdit.tags,
			salary: offerEdit.salary
					// Add other properties as needed
		};

		// Fill the text fields with offer details
		document.getElementById('titleInput').value = offerDetails.title;
        document.getElementById('contentInput').value = offerDetails.content;
		document.getElementById('tagsInput').value = "#" + offerDetails.tags;
        document.getElementById('salaryInput').value = offerDetails.salary;
	} else {
		console.error('Error: Empty or invalid offer array');
	}
}


function editOffer(arg2, arg3, arg5, arg6, result) {
	event.preventDefault();
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			var correct = this.responseText;

			if (correct === "true") {
				document.getElementById("titleInput").value = "";
				document.getElementById("contentInput").value = "";
				document.getElementById("salaryInput").value = "";
				document.getElementById("tagsInput").value = "";
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
	var tags = document.getElementById(arg6).value;
	tags = tags.replace(/#/g, '');
	if (!title.trim() || !content.trim() || !salary.trim()) {
		resultElem.innerHTML = "Addition of an offer failed. Please complete all fields.";
	} else {
		xhttp.open("GET", "OfferEditServlet?arg1=" + id_offer + "&arg2=" + title + "&arg3=" + content + "&arg5=" + salary + "&arg6=" + tags, true);
		xhttp.send();
	}
}
