document.addEventListener("DOMContentLoaded", function () {
	fetchProfileInfo();
	getRatings();
});

function fetchProfileInfo() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			try {
				var profileInfo = JSON.parse(this.responseText);
				displayProfileInfo(profileInfo);
			} catch (error) {
				console.error('Błąd parsowania JSON:', error);
			}
		} else {
			console.error('Błąd HTTP:', this.status);
		}
	};

	var arg1 = sessionStorage.getItem('showProfile_id');

	xhttp.open("GET", "ProfileEmployerServlet?arg1=" + arg1, true);
	xhttp.send();
}

function displayProfileInfo(profileInfo) {
	console.log(profileInfo);
	document.getElementById('login').innerText = "Your profile, " + profileInfo[0].login;
	document.getElementById('company_name').innerText = profileInfo[0].company_name;
	document.getElementById('email').innerText = profileInfo[0].email;
	document.getElementById('city').innerText = profileInfo[0].city;
	document.getElementById('NIP').innerText = profileInfo[0].NIP;
	document.getElementById('description').innerText = profileInfo[0].description;
	document.getElementById('picture').src = 'data:image/jpeg;base64,' + profileInfo[0].picture;
}

function validateRating(content, stars) {
	event.preventDefault();
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			document.getElementById("contentInput").value = "";
			document.getElementById("starsInput").value = "";
			getRatings();	
		}
	};

	var userID = sessionStorage.getItem('showProfile_id');
	var comment = document.getElementById(content).value;
	var rating = document.getElementById(stars).value;
	if (!content.trim() || !stars.trim()) {

	} else {
		xhttp.open("GET", "AddEmployerRatingServlet?arg1=" + userID + "&arg2=" + comment + "&arg3=" + rating, true);
		xhttp.send();
	}
}

function getRatings() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4 && this.status === 200) {
			try {
				var ratings = JSON.parse(this.responseText);
				displayRatings(ratings);
			} catch (error) {
				console.error('Błąd parsowania JSON:', error);
			}
		} else {
			console.error('Błąd HTTP:', this.status);
		}
	};

	var arg1 = sessionStorage.getItem('showProfile_id');
	xhttp.open("GET", "GetEmployerRatingsServlet?arg1=" + arg1, true);
	xhttp.send();
}

function displayRatings(ratings) {
	var containersContainer = document.getElementById("ratings-container");
	containersContainer.innerHTML = "";

	for (var i = 0; i < ratings.length; i++) {
		var rating = ratings[i];

		var ratingDiv = document.createElement("div");
		ratingDiv.className = "container";
		ratingDiv.id = `offer${rating.ratingID}`;

		var contentElement = document.createElement("p");
		contentElement.innerText = rating.content;
		contentElement.className = "ratingContent";
		var starElement = document.createElement("img");
		switch (parseInt(rating.stars)) {
			case 1:
				starElement.src = "profile-elements/1star.png";
				break;
			case 2:
				starElement.src = "profile-elements/2stars.png";
				break;
			case 3:
				starElement.src = "profile-elements/3stars.png";
				break;
			case 4:
				starElement.src = "profile-elements/4stars.png";
				break;
			case 5:
				starElement.src = "profile-elements/5stars.png";
				break;
			default:
				starElement.src = "profile-elements/1star.png";
		}
		starElement.alt = "Rating Star";
		starElement.className = "ratingStar";

		ratingDiv.appendChild(contentElement);
		ratingDiv.appendChild(starElement);

		containersContainer.appendChild(ratingDiv);
	}
}