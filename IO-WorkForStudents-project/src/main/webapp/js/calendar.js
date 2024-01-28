function getStudentCalendarCsv() {
	var xhttp = new XMLHttpRequest();
	id_stud = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load csv into editor
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getusercsv&userid=" + id_stud, false);
	xhttp.send();
}
function getOfferCalendarCsv() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load csv into editor
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getoffercsv&offerid=" + id_offer, false);
	xhttp.send();
}
function getStudentCalendarHtml(id_stud) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendar" + id_stud).innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getuserhtml&userid=" + id_stud, false);
	xhttp.send();
}
function getOfferCalendarHtml(id_offer) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendar" + id_offer).innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=getofferhtml&offerid=" + id_offer, false);
	xhttp.send();
}
function getCalendarCompatibility() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load number into display
			}
		}
	};
	xhttp.open("GET", "calendar?rqtype=compare&userid=" + id_stud + "&offerid=" + id_offer, false);
	xhttp.send();
}

function getEditorCsv() {

}

function getHtmlFromCsv() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load html into div
			}
		}
	};
	xhttp.open("POST", "calendar");
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "gethtml");
	xhttp.send(getEditorCsv());
}

function updateStudentCalendar() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//display yay notification or sth
			}
		}
	};
	xhttp.open("POST", "calendar");
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "setuser");
	xhttp.setRequestHeader("userid", "setoffer");
	xhttp.send(getEditorCsv());
}
function updateOfferCalendar() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id_offer');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//display yay notification or sth
			}
		}
	};
	xhttp.open("POST", "calendar");
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "setoffer");
	xhttp.setRequestHeader("offerid", "setoffer");
	xhttp.send(getEditorCsv());
}