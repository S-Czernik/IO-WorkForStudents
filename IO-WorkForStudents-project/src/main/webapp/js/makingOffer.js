window.onload = startPage(0);

function startPage(pageNumber) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var offers = JSON.parse(this.responseText);
            displayOffers(offers);
        }
    };
    
    xhttp.open("GET", "offersdisplay?arg1=" + pageNumber, true);
    xhttp.send();
}

function displayOffers(offers) {
    var containersContainer = document.getElementById("containersContainerID");

    containersContainer.innerHTML = "";

    for (var i = 0; i < offers.length; i++) {
        var offer = offers[i];

        var offerDiv = document.createElement("div");
        offerDiv.className = "container";
        
        var titleElement = document.createElement("h2");
        titleElement.innerText = offer.title;
        titleElement.className = "offerTitle";

        var contentElement = document.createElement("p");
        contentElement.innerText = offer.content;
        contentElement.className = "offerContent";

        var showMoreElement = document.createElement("button");
        showMoreElement.innerText = "show more";
       showMoreElement.value = "show more";
        showMoreElement.className = "showMore";
        
        offerDiv.appendChild(titleElement);
        offerDiv.appendChild(contentElement);
        offerDiv.appendChild(showMoreElement);

        containersContainer.appendChild(offerDiv);
    }
}

function validateOffer(arg2, arg3, arg4, arg5, result) {
    event.preventDefault();
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var correct = this.responseText;
            
            if (correct === "true") {
                document.getElementById("titleInput").value = "";
                document.getElementById("contentInput").value = "";
                document.getElementById("infoInput").value = "";
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
    var info = document.getElementById(arg4).value;
    var salary = document.getElementById(arg5).value;
    if (!title.trim() || !content.trim() || !info.trim() || !salary.trim()) {
        resultElem.innerHTML = "Addition of an offer failed. Please complete all fields.";
    } else {
        xhttp.open("GET", "offerAddingServlet?arg1=" + sessionStorage.getItem('found_id') + "&arg2=" + title + "&arg3=" + content + "&arg4=" + info + "&arg5=" + salary, true);
        xhttp.send();
    }
}