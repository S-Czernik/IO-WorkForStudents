/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener("DOMContentLoaded", function() {
    fetchNotifications();
});

function fetchNotifications() {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var notifications = JSON.parse(this.responseText);
            displayNotifications(notifications);
        }
    };

    xhttp.open("GET", "NotificationServlet", true);
    xhttp.send();
}

function displayNotifications(notifications) {
    const sideMenu = document.querySelector('.side-menu ul');

    notifications.forEach(notification => {
        const listItem = document.createElement('li');
        const anchor = document.createElement('a');
        anchor.href = '#'; // additional links

        if (notification.messageType === 'newOffer') {
            anchor.innerHTML = `You got a new offer from: ${notification.userLogin}!<br>${notification.offerTitle}`;
        } else if (notification.messageType === 'accepted') {
            anchor.innerHTML = `Your application was accepted by: ${notification.userLogin}!<br>Your job will be: ${notification.offerTitle}<br>Good luck!`;
        } else if (notification.messageType === 'application') {
            anchor.innerHTML = `Student: ${notification.userLogin}!<br>Has applied to job: ${notification.offerTitle}!`;
        } else if (notification.messageType === 'acceptation') {
            anchor.innerHTML = `Stduent: ${notification.userLogin}!<br>Accepted your offer: ${notification.offerTitle}<br>!`;
        } else {
            // handle other message types if needed
            anchor.textContent = 'Unknown message type';
        }

        listItem.appendChild(anchor);
        sideMenu.appendChild(listItem);
    });
}

