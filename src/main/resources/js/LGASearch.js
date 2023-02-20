//Initialise value for outcomeCheckboxes (the 4 that make up the LGA score)
let outcomeCheckboxes = document.getElementById('scoreCheckboxes');
let ageScoreBox = document.getElementById('lgaScoreAge');
let lfScoreBox = document.getElementById('lgaScoreLf');
let nsScoreBox = document.getElementById('lgaScoreNs');
let scScoreBox = document.getElementById('lgaScoreSc');

// DROPDOWN MENU, this code and its associated css etc. is adapted from W3Schools

/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
function dropdown() {
  document.getElementById('myDropdown').classList.toggle('show');
}

// FIXME: Hide dropdown menu when click outside it.
// document.body.addEventListener('click', function () {
//   //Close the dropdown menu
//   var dropdownList = document.getElementById('myDropdown');
//   if (dropdownList.style.display != 'none') {
//     dropdownList.style.display = 'none';
//   }
//   console.log('document body click');
// });

function filterDropdown() {
  var input, filter, ul, li, a, i;
  input = document.getElementById('myInput');
  filter = input.value.toUpperCase();
  div = document.getElementById('myDropdown');
  a = div.getElementsByTagName('a');
  for (i = 0; i < a.length; i++) {
    txtValue = a[i].textContent || a[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      a[i].style.display = '';
    } else {
      a[i].style.display = 'none';
    }
  }
}

// Setup dynamic content block with LGA info to be replaced
let dynamicContent = document.getElementById('dynamicContent');

// Create a few XMLHttpRequest objects
const xhttpBuild = new XMLHttpRequest();
const xhttpScore = new XMLHttpRequest();
const xhttpDist = new XMLHttpRequest();
const xhttpSimilar = new XMLHttpRequest();

//Select all anchor tags in the dropdown list
let dropdownLink = document.querySelectorAll('.dropdown-content a');

//Add functionality to each dropdown link
dropdownLink.forEach((dropdownLink) => {
  // Add event listener that triggers a GET request to each link in the dropdown menu
  dropdownLink.addEventListener('click', function () {
    // Send a request
    xhttpBuild.open('GET', '/LGABuild/' + dropdownLink.id, true);
    xhttpBuild.send();
    //Close the dropdown menu
    document.getElementById('myDropdown').classList.toggle('show');
    // Send a request that updates the values of the checkboxes for each outcome
    xhttpScore.open('GET', '/querylgascore/' + dropdownLink.id, true);
    xhttpScore.send();
    // Send a request that checks for nearby LGAs and returns them
    xhttpDist.open('GET', '/querylgadistance/' + dropdownLink.id, true);
    xhttpDist.send();
    // Send a request that checks for similar LGAs and returns them
    xhttpSimilar.open('GET', '/querylgasimilar/' + dropdownLink.id, true);
    xhttpSimilar.send();
  });
});

// Initialise total score and selection score.
let selectedScore = 0.0;
let lgaScore = document.getElementById('lgaScore');
let totalScore = parseFloat(lgaScore.innerHTML);

// This function ensures that displayed score always matches current checkboxes
let scoreUpdate = function () {
  //Select all checkboxes
  let scoreCheckbox = document.querySelectorAll('#scoreCheckboxes input');
  scoreCheckbox.forEach((scoreCheckbox) => {
    let incomingScore = scoreCheckbox.value;
    if (scoreCheckbox.checked) {
      //If the ranking metric (here, incoming score) is too low to be percetible, assign it one for user feedback.
      if (parseFloat(incomingScore) == 0.0) {
        incomingScore++;
      }
      //Add to selectedScore based on value of checkboxes
      selectedScore = selectedScore + parseFloat(incomingScore);
    }
  });
  if (selectedScore == 0.0) {
    selectedScore = totalScore;
  }
  // console.log(selectedScore.toFixed(2));
  //Update shown score
  lgaScore.innerHTML = selectedScore.toFixed(2);
  // If selected score is too low, display n/a
  if (selectedScore < 1) {
    console.log(totalScore);
    lgaScore.innerHTML = 'N/A';
  }
  // Reset the selectedScore
  selectedScore = 0.0;
};
//Run the scoreUpdate function whenever the checkboxes are changed.
outcomeCheckboxes.onchange = scoreUpdate;

// When a xhttpBuild response is received, update the information html
xhttpBuild.onload = function () {
  let newContent = xhttpBuild.responseText;
  dynamicContent.innerHTML = newContent;
};

// When a xhttpScore response is received, update the checkboxes and LGA score
xhttpScore.onload = function () {
  let reply = JSON.parse(xhttpScore.responseText);
  // console.log(reply);
  //Set the checkboxes to have new values based on the incoming LGA.
  document.getElementById('lgaScoreAge').setAttribute('value', reply.ageScore);
  document.getElementById('lgaScoreLf').setAttribute('value', reply.lfScore);
  document.getElementById('lgaScoreNs').setAttribute('value', reply.nsScore);
  document.getElementById('lgaScoreSc').setAttribute('value', reply.scScore);
  //Set new total score
  totalScore = reply.totalScore;
  //Ensure new incoming score matches current checkboxes
  scoreUpdate();
};

let nearbyLgaFunc = function () {
  //Select all appropriate divs in the LGA list
  let nearbyLgaLink = document.querySelectorAll('.nearbyName');

  //Add functionality to each dropdown link
  nearbyLgaLink.forEach((nearbyLgaLink) => {
    // Add event listener that triggers a GET request to each link in the dropdown menu
    nearbyLgaLink.addEventListener('click', function () {
      // Send requests with the new info
      xhttpBuild.open('GET', '/LGABuild/' + nearbyLgaLink.innerText, true);
      xhttpBuild.send();
      xhttpScore.open('GET', '/querylgascore/' + nearbyLgaLink.innerText, true);
      xhttpScore.send();
      xhttpSimilar.open(
        'GET',
        '/querylgasimilar/' + nearbyLgaLink.innerText,
        true
      );
      xhttpSimilar.send();
      xhttpDist.open(
        'GET',
        '/querylgadistance/' + nearbyLgaLink.innerText,
        true
      );
      xhttpDist.send();
    });
  });
};

// Loop through nearbyLga nodelist and update html of divs with nearbyLga as returned from the json response
const nearbyName = Array.from(document.querySelectorAll('.nearbyName'));
const nearbyDist = Array.from(document.querySelectorAll('.nearbyDist'));
xhttpDist.onload = function () {
  let jsonData = JSON.parse(xhttpDist.responseText);
  for (var i = 1; i < 6; i++) {
    let reply = jsonData[i];
    nearbyName[i - 1].innerText = reply.name;
    nearbyDist[i - 1].innerText = reply.distance.toFixed(2) + ' km\u00B2';
  }
  // Call the function that updates the event listeners on the links and sends requests to update all the info
  nearbyLgaFunc();
};

let simLgaFunc = function () {
  //Select all anchor tags in the similar LGA list
  let similarLgaLink = document.querySelectorAll('.similarName a');

  //Add functionality to each dropdown link
  similarLgaLink.forEach((similarLgaLink) => {
    // Add event listener that triggers a GET request to each link in the dropdown menu
    similarLgaLink.addEventListener('click', function () {
      // Send requests with the new info
      xhttpBuild.open('GET', '/LGABuild/' + similarLgaLink.innerText, true);
      xhttpBuild.send();
      xhttpScore.open(
        'GET',
        '/querylgascore/' + similarLgaLink.innerText,
        true
      );
      xhttpScore.send();
      xhttpSimilar.open(
        'GET',
        '/querylgasimilar/' + similarLgaLink.innerText,
        true
      );
      xhttpSimilar.send();
      xhttpDist.open(
        'GET',
        '/querylgadistance/' + similarLgaLink.innerText,
        true
      );
      xhttpDist.send();
    });
  });
};

// Loop through similarLga nodelist and update html of divs with similarLga as returned from the json response
const similarNameDivs = Array.from(document.querySelectorAll('.similarName'));
const similarLgaList = document.getElementById('similarLgaList');
let newHTML = '';
// let similarDist = Array.from(document.querySelectorAll('.similarDist'));
xhttpSimilar.onload = function () {
  let similarLgas = JSON.parse(xhttpSimilar.responseText);
  let length = similarLgas.length;
  //Keep the for loop within constraints
  if (length > 6) {
    length = 6;
  }
  for (var i = 1; i < length; i++) {
    let similarLGA = similarLgas[i];
    console.log(similarLGA.name);
    newHTML =
      newHTML + "<div class='similarName'><a>" + similarLGA.name + '</div></a>';
  }
  // Insert the new HTML into the DOM and reset it
  similarLgaList.innerHTML = newHTML;
  newHTML = '';

  // Call the function that updates the event listeners on the links and sends requests to update all the info
  simLgaFunc();
};

// Ensure event listeners are added on page load.
window.onload = simLgaFunc();
window.onload = nearbyLgaFunc();
