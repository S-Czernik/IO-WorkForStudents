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
function getStudentCalendarHtml() {
	var xhttp = new XMLHttpRequest();
	id_stud = sessionStorage.getItem('found_id');
	xhttp.onreadystatechange = function () {
		if (this.readyState === 4) {
			if (this.status === 200) {
				//load html into div
			}
		}
	};
	xhttp.open("GET", "CalendarServlet?rqtype=getuserhtml&userid=" + id_stud, true);
	xhttp.send();
}
function getOfferCalendarHtml() {
	var xhttp = new XMLHttpRequest();
	id_offer = sessionStorage.getItem('found_id_offer');
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
function updateStudentCalendar(csv) {

}