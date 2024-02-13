function getStudentCalendarCsv() {
	var xhttp = new XMLHttpRequest();
	var id_stud = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				loadCsv(this.responseText);
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getusercsv&userid=" + id_stud);
	xhttp.send();
}
function getOfferCalendarCsv() {
	var xhttp = new XMLHttpRequest();
	var id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				loadCsv(this.responseText);
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getoffercsv&offerid=" + id_offer);
	xhttp.send();
}
function getStudentCalendarHtml(id_stud, div_name = "") {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				if (div_name.length === 0)
					document.getElementById("calendar" + id_stud).innerHTML = this.responseText;
				else
					document.getElementById(div_name).innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getuserhtml&userid=" + id_stud);
	xhttp.send();
}
function getOfferCalendarHtml(id_offer, div_name = "") {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				if (div_name.length === 0)
					document.getElementById("calendar" + id_offer).innerHTML = this.responseText;
				else
					document.getElementById(div_name).innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getofferhtml&offerid=" + id_offer);
	xhttp.send();
}

function getStudentCalendarCompatibility(id_stud) {
	var xhttp = new XMLHttpRequest();
	var id_empl = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendarmatching" + id_stud).innerHTML = this.responseText.substring(0, 4) + '%';
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=cmpstud&userid=" + id_stud + "&emplid=" + id_empl);
	xhttp.send();
}

function getOfferCalendarCompatibility(id_offer) {
	var xhttp = new XMLHttpRequest();
	var id_stud = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendarmatching" + id_offer).innerHTML = this.responseText.substring(0, 4) + '%';
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=cmpoffer&userid=" + id_stud + "&offerid=" + id_offer);
	xhttp.send();
}

function getHtmlFromCsv(csv) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendar").innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("POST", "calendar");
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "gethtml");
	xhttp.send(csv);
}

function updateStudentCalendar(csv) {
	var xhttp = new XMLHttpRequest();
	var id_stud = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//display yay notification or sth
			}
		}
	};
	xhttp.open("POST", "calendar", false);
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "setuser");
	xhttp.setRequestHeader("userid", id_stud);
	xhttp.send(csv);
}
function updateOfferCalendar(csv) {
	var xhttp = new XMLHttpRequest();
	var id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//display yay notification or sth
			}
		}
	};
	xhttp.open("POST", "calendar", false);
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "setoffer");
	xhttp.setRequestHeader("offerid", id_offer);
	xhttp.send(csv);
}