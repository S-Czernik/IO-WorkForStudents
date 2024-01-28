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
	xhttp.open("GET", "CalendarServlet?rqtype=getusercsv&userid=" + id_stud, true);
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
	xhttp.open("GET", "CalendarServlet?rqtype=getoffercsv&offerid=" + id_offer, true);
	xhttp.send();
}
function getStudentCalendarHtml(id_stud) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				document.getElementById("calendar").innerHTML = this.responseText;
			}
		}
	};
	xhttp.open("GET", "CalendarServlet?rqtype=getuserhtml&userid=" + id_stud, true);
	xhttp.send();
}
function getOfferCalendarHtml(id_offer) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load html into div
			}
		}
	};
	xhttp.open("GET", "CalendarServlet?rqtype=getofferhtml&offerid=" + id_offer, true);
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
	xhttp.open("GET", "CalendarServlet?rqtype=compare&userid=" + id_stud + "&offerid=" + id_offer, true);
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
	xhttp.open("POST", "CalendarServlet");
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
	xhttp.open("POST", "CalendarServlet");
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
	xhttp.open("POST", "CalendarServlet");
	xhttp.setRequestHeader("Content-Type", "application/plaintext; charset=UTF-8");
	xhttp.setRequestHeader("rqtype", "setoffer");
	xhttp.setRequestHeader("offerid", "setoffer");
	xhttp.send(getEditorCsv());
}