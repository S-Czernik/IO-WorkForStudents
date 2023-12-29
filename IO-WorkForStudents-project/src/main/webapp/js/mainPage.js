document.addEventListener('DOMContentLoaded', startPage());

function startPage(){
    let numOfOffers = 12; // +1
    let i = 0;
    while(i < numOfOffers){
        getOffer(i);
        i++;
        
    }
}


function getOffer(i) {
  document.getElementById("containersContainerID").insertAdjacentHTML('beforeend',
    '<div class="container" id="container' + i + '">' +
    '<h2 class="offerTitle" id="offerTitle'+i+'">Offer Title #' + i + '</h2>' +
    '<p class="offerPercentage" id="offerPercentage' + i + '">100%</p>' +
    '<p class="offerContent" id="offerContent' + i + '">Offer Content #' + i + '</p>' +
    '<p class="offerTags"offerTags'+ i +'" ID = >#Tag1, #Tag2</p>' +
    '</div>');
}
