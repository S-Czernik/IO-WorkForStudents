//document.addEventListener('DOMContentLoaded', startPage());

function startPage(){
    let numOfOffers = 10;
    while(numOfOffers > 0){
        getOffer();
        
    }
    
    
}


function getOffer(){
    document.getElementById("containersContainer").insertAdjacentHTML(
            '<div class="container">',
                '<h2 class ="offerTitle">',
                '    Default title',
                '</h2>',
               ' <p class = "offerPercentage">',
                '    100%',
               ' </p>',
                '<p class = "offerContent">',
                '    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu nibh ac ligula feugiat semper ut ut velit. Suspendisse id lacinia ipsum, sed elementum turpis. Ut lectus tortor, gravida eu blandit in, suscipit et eros. Vestibulum et sem pellentesque, tincidunt orci id, pharetra lectus. Praesent semper justo massa, vel tincidunt magna gravida eu. Nam sit amet massa at justo finibus efficitur. Duis eros ante, auctor vel massa sollicitudin, gravida rhoncus massa. Sed feugiat mattis lectus id tempor. Aenean malesuada mauris lacus, at vehicula velit fermentum sit amet. ',
'Maecenas viverra enim vel augue varius, id mattis massa blandit. Nulla volutpat facilisis leo vitae venenatis. Nam et massa mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. ',
        'Integer odio lacus, mollis eu varius nec, lobortis molestie ipsum. Etiam pulvinar orci dignissim, suscipit tortor eu, pretium turpis. ',
     '   Nullam dapibus, elit et sodales luctus, quam tellus pretium nunc, ut luctus mauris sapien vel libero. Duis sodales sapien enim, eu posuere ',
'odio suscipit et. Maecenas at feugiat justo, a ultricies sapien. Suspendisse a pretium nibh, eget laoreet urna. Donec a nulla sit amet leo luctus porta. Proin pretium ornare placerat. ',
             '   </p>',

             '   <p class = "offerTags">',
                 '   #Tag1, #Tag2,',
         '       </p>',
                    
         '   </div>   ');
    
    
}
