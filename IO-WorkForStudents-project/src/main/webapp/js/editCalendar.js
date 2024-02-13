document.addEventListener("DOMContentLoaded", function () {
	onLoad();
});

function editCalendar() {
	let table = document.getElementById("outputTable");
	// Check validity of all rows
	for (var i = 1; i < table.rows.length; i++) {
		if (table.rows[i].cells[0].childNodes[0].value.length === 0)
			table.rows[i].cells[0].childNodes[0].value = "--:--";
		if (table.rows[i].cells[1].childNodes[0].value.length === 0)
			table.rows[i].cells[1].childNodes[0].value = "--:--";
	}

// Check validity of last row & add new if ok
	let lastRow = table.rows[table.rows.length - 1];
	if (lastRow.cells[0].childNodes[0].value.length !== 0 && lastRow.cells[1].childNodes[0].value.length !== 0 && lastRow.cells[2].childNodes[0].value !== "none") {
		addRow();
	}

	getHtmlFromCsv(getCsv());
}

function addRow() {
	let table = document.getElementById("outputTable");

	if (table.rows.length !== 1)
		table.rows[table.rows.length - 1].cells[3].childNodes[0].textContent = "Delete";

	let newRow = table.insertRow(table.rows.length);
	newRow.insertCell(0).innerHTML = '<input type="time" step="60"onchange="editCalendar()">';
	newRow.insertCell(1).innerHTML = '<input type="time" step="60"onchange="editCalendar()">';
	newRow.insertCell(2).innerHTML = '<select id="day0" onchange="editCalendar()">\n\
<option value="none">---</option>\
<option value="mon">Monday</option>\
<option value="tue">Tuesday</option>\
<option value="wed">Wednesday</option>\
<option value="thu">Thursday</option>\
<option value="fri">Friday</option>\
<option value="sat">Saturday</option>\
<option value="sun">Sunday</option></select>';
	newRow.insertCell(3).innerHTML = '<button onclick="deleteData(this)">Reset</button>';
}

function deleteData(button) {
// Get the parent row of the clicked button 
	let row = button.parentNode.parentNode;
	let lastRow = row.parentNode.rows[row.parentNode.rows.length - 1];

	// Check if last row
	if (row != lastRow)
		// Remove the row from the table 
		row.parentNode.removeChild(row);
	else {
		// Reset last row
		row.cells[0].childNodes[0].value = "--:--";
		row.cells[1].childNodes[0].value = "--:--";
		row.cells[2].childNodes[0].value = "none";
	}

	getHtmlFromCsv(getCsv());
}

function getCsv() {
	var csv = "";

	let table = document.getElementById("outputTable");
	// Check validity of all rows
	for (var i = 1; i < table.rows.length; i++) {

		let begin = convertTimeToMinute(table.rows[i].cells[0].childNodes[0].value);
		let end = convertTimeToMinute(table.rows[i].cells[1].childNodes[0].value);
		let day = table.rows[i].cells[2].childNodes[0].value;

		if (begin !== NaN && end !== NaN && begin < end && day !== "none") {
			if (csv !== "")
				csv += ",";
			csv += begin + "," + end + "," + day;
		}
	}

	return csv;
}

function convertTimeToMinute(time) {
	let vals = time.split(':');
	return parseInt(vals[0]) * 60 + parseInt(vals[1]);
}

function convertMinuteToTime(minute) {
	let h = ("00" + Math.floor(minute / 60)).slice(-2);
	let m = ((minute % 60) + "00").substring(0, 2);
	return h + ":" + m;
}

function onLoad() {
	let user_type = sessionStorage.getItem('found_type');
	if (user_type === "student")
		getStudentCalendarCsv();
	else if (user_type === "employer")
		getOfferCalendarCsv();
}

function loadCsv(csv) {
	let table = document.getElementById("outputTable");

	let vals = csv.split(',');
	let count = Math.floor(vals.length / 3);
	for (var i = 0; i < count; i++) {
		addRow();

		table.rows[i + 1].cells[0].childNodes[0].value = convertMinuteToTime(parseInt(vals[3 * i + 0]));
		table.rows[i + 1].cells[1].childNodes[0].value = convertMinuteToTime(parseInt(vals[3 * i + 1]));
		table.rows[i + 1].cells[2].childNodes[0].value = vals[3 * i + 2];
	}

	addRow();
	getHtmlFromCsv(getCsv());
}

function save() {
	let user_type = sessionStorage.getItem('found_type');
	if (user_type === "student") {
		updateStudentCalendar(getCsv());
		window.location.href = "/IO-WorkForStudents-project/profileStudent.html";
	} else if (user_type === "employer") {
		updateOfferCalendar(getCsv());
		window.location.href = "/IO-WorkForStudents-project/profileEmployer.html";
	}

}

function returnWhere() {
	let user_type = sessionStorage.getItem('found_type');
	if (user_type === "student") {
		window.location.href = "/IO-WorkForStudents-project/profileStudent.html";
	} else if (user_type === "employer") {
		window.location.href = "/IO-WorkForStudents-project/offers.html";
	}
}