document.addEventListener("DOMContentLoaded", function () {
	fetchProfileInfo();
	getRatings();
});

function fetchProfileInfo() {
	var xhr = new XMLHttpRequest();
	var arg1 = sessionStorage.getItem('found_id');
	xhr.open("GET", "ProfileStudentServlet?arg1=" + arg1, true);
	xhr.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				try {
					var profileInfo = JSON.parse(this.responseText);
					displayProfileInfo(profileInfo);
				} catch (error) {
					console.error('Błąd parsowania JSON:', error);
				}
			}
		} else {
			console.error('Błąd HTTP:', this.status);
		}
	};

	xhr.send();

	getStudentCalendarHtml(arg1, "calendar");
}

function displayProfileInfo(profileInfo) {
	document.getElementById('login').innerText = "Your profile, " + profileInfo[0].login;
	document.getElementById('name').innerText = profileInfo[0].name;
	document.getElementById('surname').innerText = profileInfo[0].surname;
	document.getElementById('email').innerText = profileInfo[0].email;
	document.getElementById('city').innerText = profileInfo[0].city;
	document.getElementById('title').innerText = '"' + profileInfo[0].title + '"';
	document.getElementById('description').innerText = profileInfo[0].description;
	document.getElementById('picture').src = 'data:image/jpeg;base64,' + profileInfo[0].picture;
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

	var arg1 = sessionStorage.getItem('found_id');
	xhttp.open("GET", "GetStudentRatingsServlet?arg1=" + arg1, true);
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