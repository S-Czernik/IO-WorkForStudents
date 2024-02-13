
// Declare a global variable to store the current notification
var currentNotification;    
var actionn;

document.addEventListener("DOMContentLoaded", function() {
    fetchNotifications();
    
    document.querySelector('.side-menu-trigger').addEventListener('click', function() {
        document.querySelector('.side-menu').classList.toggle('active');
});

});


function fetchNotifications(arg1) {
    var xhttp = new XMLHttpRequest();
    arg1 = sessionStorage.getItem('found_id');
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var notifications = JSON.parse(this.responseText);
            displayNotifications(notifications);
        }
    };

    xhttp.open("GET", "NotificationServlet?arg1="+arg1 ,true);
    xhttp.send();
}

function displayNotifications(notifications) {
    const sideMenu = document.querySelector('.side-menu ul');

    notifications.forEach(notification => {
        const listItem = document.createElement('li');

        // Create container for text content
        const textContainer = document.createElement('div');
        textContainer.className = 'text-container';

        // Create anchor element
        const anchor = document.createElement('a');
        anchor.href = '#'; // additional links
        anchor.setAttribute('data-id', notification.ID); // Add data-id attribute
        anchor.classList.add('notification-anchor'); // Add class for identification

        if (notification.messageType === 'newOffer') {
            anchor.innerHTML = `You got a new offer from: ${notification.userLogin}!<br>${notification.offerTitle}<br>`;
        } else if (notification.messageType === 'accepted') {
            anchor.innerHTML = `Your application was accepted by: ${notification.userLogin}!<br>Your job will be: ${notification.offerTitle}<br>Good luck!<br>`;
        } else if (notification.messageType === 'application') {
            anchor.innerHTML = `Student: ${notification.userLogin}!<br>Has applied to job: ${notification.offerTitle}!<br>`;
        } else if (notification.messageType === 'acceptation') {
            anchor.innerHTML = `Student: ${notification.userLogin}!<br>Accepted your offer: ${notification.offerTitle}!<br>`;
        } else if (notification.messageType === 'contactEmployer') {
            anchor.innerHTML = `You have accepted offer from: ${notification.userLogin}!<br> your job will be ${notification.offerTitle}<br>`;
        } else if (notification.messageType === 'contactStudent') {
            anchor.innerHTML = `You have accepted student: ${notification.userLogin}!<br> Their job will be: ${notification.offerTitle}!<br>`;
        } else {
            // handle other message types if needed
            anchor.textContent = 'Unknown message type';
        }

        // Append anchor to the text container
        textContainer.appendChild(anchor);

        // Create container for the button
        const buttonContainer = document.createElement('div');
        buttonContainer.className = 'button-container';

        // Create button elements
        const acceptButt = document.createElement("button");
        const rejectButt = document.createElement("button");
        const viewOfferButt = document.createElement("button");
        const viewProfileButt = document.createElement("button");
        const deleteButt = document.createElement("button");
        const contactEmployerButt = document.createElement("button");
        const contactStudentButt = document.createElement("button");

        //Student
        if (notification.messageType === 'newOffer') {
            acceptButt.innerText = "Accept";
            rejectButt.innerText = "Reject";
            viewOfferButt.innerText = "View Offer";

            // Add event listeners to accept and reject buttons
            acceptButt.addEventListener('click', function() {
                actionn = 'accept';
                showConfirmationModal('accept', notification);  
            });

            rejectButt.addEventListener('click', function () {
                actionn = 'reject';
                showConfirmationModal('reject',notification);
            });
            viewOfferButt.addEventListener('click', function() {
                actionn = 'viewOffer';
                sessionStorage.setItem('showOffer_id', notification.offerID);                
                window.location.href = 'viewEmployerOffer.html';
            });
			
            buttonContainer.appendChild(viewOfferButt);
            buttonContainer.appendChild(acceptButt);
            buttonContainer.appendChild(rejectButt);
            
        } else if (notification.messageType === 'accepted') {
            deleteButt.innerText = "Delete Notification";
            viewProfileButt.innerText = "View Profile";

            // Add event listener to delete button
            deleteButt.addEventListener('click', function () {
                actionn = 'delete';
                showConfirmationModal('delete',notification);
            });
            viewProfileButt.addEventListener('click', function () {
                actionn = 'viewProfile';
                sessionStorage.setItem('showProfile_id', notification.studentID);
                window.location.href = 'viewEmployerProfile.html';
            });
            
            buttonContainer.appendChild(viewProfileButt);
            buttonContainer.appendChild(deleteButt);
            
        } else if (notification.messageType === 'contactEmployer') {
            contactEmployerButt.innerText = "Conact Employer Here";

            contactEmployerButt.addEventListener('click', function () {
                actionn = 'viewProfile';
                sessionStorage.setItem('showProfile_id', notification.studentID);
                window.location.href = 'viewEmployerProfile.html';
            });
             
            buttonContainer.appendChild(contactEmployerButt);
            
            
        // Employer
        
        } else if (notification.messageType === 'application') {
            acceptButt.innerText = "Accept";
            rejectButt.innerText = "Reject";
            viewOfferButt.innerText = "View Offer";
            
            // Add event listeners to accept and reject buttons
            acceptButt.addEventListener('click', function() {
                actionn = 'accept';
                showConfirmationModal('accept', notification);
            });

            rejectButt.addEventListener('click', function () {
                actionn = 'reject';
                showConfirmationModal('reject',notification);
            });
            viewOfferButt.addEventListener('click', function() {
                actionn = 'viewOffer';
                sessionStorage.setItem('showOffer_id', notification.offerID);                
                window.location.href = 'viewStudentOffer.html';   
            });
            buttonContainer.appendChild(viewOfferButt);
            buttonContainer.appendChild(acceptButt);
            buttonContainer.appendChild(rejectButt);
        } else if (notification.messageType === 'acceptation') {
            deleteButt.innerText = "Delete Notification";
            viewProfileButt.innerText = "View Profile";

            // Add event listener to delete button
            deleteButt.addEventListener('click', function () {
                actionn = 'delete';
                showConfirmationModal('delete',notification);
            });
            viewProfileButt.addEventListener('click', function () {
                actionn = 'viewProfile';
                sessionStorage.setItem('showProfile_id', notification.studentID);
                window.location.href = 'viewStudentProfile.html';               
            });
            
            buttonContainer.appendChild(viewProfileButt);
            buttonContainer.appendChild(deleteButt);
            
        } else if (notification.messageType === 'contactStudent') {
            contactStudentButt.innerText = "Conact Student Here";

            contactStudentButt.addEventListener('click', function () {
                actionn = 'viewProfile';
                sessionStorage.setItem('showProfile_id', notification.studentID);
                window.location.href = 'viewStudentProfile.html';
            });
             
            buttonContainer.appendChild(contactStudentButt);
            
        } else {
            // handle other message types if needed
            anchor.textContent = 'Unknown message type';
        }

        // Append both text and button containers to the list item
        listItem.appendChild(textContainer);
        listItem.appendChild(buttonContainer);

        // Append the list item to the side menu
        sideMenu.appendChild(listItem);
    });
}

 // Function to handle the accept button click
function handleAcceptButton(notification) {
    // Extract information from the notification
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("Server response:", this.responseText); // Log the server response

            // Check if the response is not empty
            if (this.responseText.trim() !== "") {
                // Handle the response from the server
                var response = JSON.parse(this.responseText);
                console.log("Response from server:", response); // Log the response

                if (response.success) {
                    // The acceptance was successful, handle UI changes
                    console.log("Notification is being accepted and removing");
                } else {
                    // Handle the case where acceptance was not successful
                    console.error("Acceptance failed:", response.message);
                }
            } else {
                console.error("Empty response from the server");
            }
        }
    };

    var id_notif = notification.ID;
    var state = 'accept';
    var loggedUser = sessionStorage.getItem('found_id');
    xhttp.open("GET", "NotificationHandlerServlet?arg1="+state +"&arg2="+id_notif +"&arg3=" + loggedUser,true);
    xhttp.send();
}



    // Function to handle the reject button click
function handleRejectButton(notification) {
    // Extract information from the notification
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            // Handle the response from the server
            var response = JSON.parse(this.responseText);
            console.log("Response from server:", response); // Log the response

            if (response.success) {
                console.log("Notification is beeing rejected removing");
            } else {
                // Handle the case where rejection was not successful
                console.error("Rejection failed:", response.message);
            }
        }
    };

    var id_notif = notification.ID;
    var state = 'reject';
    var loggedUser = sessionStorage.getItem('found_id');
    xhttp.open("GET", "NotificationHandlerServlet?arg1="+state +"&arg2="+id_notif +"&arg3=" + loggedUser,true);
    xhttp.send();
}


    // Function to handle the delete button click
    function handleDeleteButton(notification) {
        // Extract information from the notification
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
        }
    };
        var id_notif = notification.ID;
        var state = 'delete';
        var loggedUser = sessionStorage.getItem('found_id');
        xhttp.open("GET", "NotificationHandlerServlet?arg1="+state +"&arg2="+id_notif +"&arg3=" + loggedUser,true);
        xhttp.send();
}


function showConfirmationModal(action, notification) {
    var confirmationModal = document.getElementById('confirmationModal');
    confirmationModal.style.display = 'block';
    // Store the current notification in the global variable
    currentNotification = notification;
    
    document.getElementById('modalAction').innerText = action;
}

function confirmAction(isConfirmed, action) {
    var confirmationModal = document.getElementById('confirmationModal');
    confirmationModal.style.display = 'none';
    

    if (isConfirmed) {
        // Perform the action based on the provided action parameter
        switch (actionn) {
            case 'accept':
                // Handle accept action
                handleAcceptButton(currentNotification);
                break;
            case 'reject':
                // Handle reject action
                handleRejectButton(currentNotification);
                break;
            case 'delete':
                // Handle delete action
                handleDeleteButton(currentNotification);
                break;
            // Add more cases for other actions if needed
        }

        // Remove the corresponding notification from the UI
        removeNotification(currentNotification);
    }
}


function removeNotification(notification) {
    // Find the anchor element corresponding to the notification
    var anchorElement = document.querySelector('.notification-anchor[data-id="' + notification.ID + '"]');

    // Log information to help debug
    console.log("Trying to remove notification:", notification);
    console.log("Corresponding anchor element:", anchorElement);

    // Remove the anchor element if found
    if (anchorElement) {
        var listItem = findClosestParent(anchorElement, 'li');
        if (listItem) {
            listItem.parentNode.removeChild(listItem);
            console.log("Notification removed successfully!");
        } else {
            console.error("List item not found. Could not remove notification.");
        }
    } else {
        console.error("Anchor element not found. Could not remove notification.");
    }
}

function findClosestParent(element, tagName) {
    // Traverse the DOM tree to find the closest parent with the specified tagName
    while (element && element.tagName !== tagName.toUpperCase()) {
        element = element.parentNode;
    }
    
    return element;
}