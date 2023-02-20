// Create an XMLHttpRequest object
const xhttp = new XMLHttpRequest();

// Initialise variable for filter elements
let sortDropdown = document.getElementById('sortOrder');
let ratioDropdown = document.getElementById('ratioChoice');
let dirButton = document.getElementById('dirTrigger');

//This variable will be updated based on which data is loaded into tabulator,
//it can then be checked when ratio values (eg. percentage) is changed
var currentTable = 'lga';
var currentArea = 'total';
var currentOrder = 'age';
var currentDir = 'desc';

var tabledata = [];
//default data array
xhttp.open(
  'GET',
  '/querylga/' + sortDropdown.value + '/' + dirButton.value,
  true
);
xhttp.send();

//Update the columns to include a percentage when ratio values are chosen
paramLookup = function () {
  if (ratioDropdown.value == 'total') {
    //do some processing and return the param object
    return { symbol: '', precision: false };
  } else {
    //do some processing and return the param object
    return { symbol: '% ', precision: 7 };
  }
};

//custom max min header filter
var minMaxFilterEditor = function (
  cell,
  onRendered,
  success,
  cancel,
  editorParams
) {
  var end;

  var container = document.createElement('span');

  //create and style inputs
  var start = document.createElement('input');
  start.setAttribute('type', 'number');
  start.setAttribute('placeholder', 'Min');
  start.setAttribute('min', 0);
  start.setAttribute('max', 100);
  start.style.padding = '4px';
  start.style.width = '50%';
  start.style.boxSizing = 'border-box';

  start.value = cell.getValue();

  function buildValues() {
    success({
      start: start.value,
      end: end.value,
    });
  }

  function keypress(e) {
    const key = e.keyCode;
    if ((key > 47 && key < 58) || key == 8) {
      buildValues();
    }

    if (e.keyCode == 27) {
      cancel();
    }
  }

  end = start.cloneNode();
  end.setAttribute('placeholder', 'Max');

  start.addEventListener('change', buildValues);
  start.addEventListener('blur', buildValues);
  start.addEventListener('keyup', keypress);

  end.addEventListener('change', buildValues);
  end.addEventListener('blur', buildValues);
  end.addEventListener('keyup', keypress);

  container.appendChild(start);
  container.appendChild(end);

  return container;
};

//max min filter function
function minMaxFilterFunction(headerValue, rowValue, rowData, filterParams) {
  //headerValue - the value of the header filter element
  //rowValue - the value of the column in this row
  //rowData - the data for the row being filtered
  //filterParams - params object passed to the headerFilterFuncParams property

  if (rowValue) {
    if (headerValue.start != '') {
      if (headerValue.end != '') {
        return rowValue >= headerValue.start && rowValue <= headerValue.end;
      } else {
        return rowValue >= headerValue.start;
      }
    } else {
      if (headerValue.end != '') {
        return rowValue <= headerValue.end;
      }
    }
  }

  return true; //must return a boolean, true if it passes the filter.
}

//lgaColumns are up here so they can be loaded by default
var lgaColumns = [
  { title: 'LGA Code', field: 'code', headerFilter: 'input' },
  {
    title: 'Name',
    field: 'name',
    headerFilter: 'input',
    cellClick: function (e, cell) {
      // Send a request
      xhttpBuild.open('GET', '/LGABuild/' + cell.getValue(), true);
      xhttpBuild.send();
    },
  },
  {
    title: 'Area (kmsq)',
    field: 'area',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
    formatter: 'money',
    formatterParams: { precision: false },
  },
  {
    title: 'Latitude',
    field: 'latitude',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Longitude',
    field: 'longitude',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
];

//Clear filters on "Clear Filters" button click
document.getElementById('filter-clear').addEventListener('click', function () {
  table.clearFilter(true);
  table.clearHeaderFilter();
  // console.log('Click');
});

//Re-order table based on dirTrigger direction
dirButton.addEventListener('click', function () {
  // Then load the new data
  if (dirButton.value == 'desc') {
    currentDir = 'asc';
    dirButton.value = 'asc';
    dirButton.innerText = 'Ascending';
    console.log(dirButton.value);
    serverRequest();
    return;
  }
  if (dirButton.value == 'asc') {
    currentDir = 'desc';
    dirButton.value = 'desc';
    dirButton.innerText = 'Descending';
    console.log(dirButton.value);
    serverRequest();
    return;
  }
});

var table = new Tabulator('#data-table', {
  data: tabledata, //load row data from array
  layout: 'fitColumns', //fit columns to width of table
  maxHeight: '600px', //Do not let table get taller than 600px
  responsiveLayout: 'hide', //hide columns that dont fit on the table
  tooltips: true, //show tool tips on cells
  addRowPos: 'bottom', //when adding a new row, add it to the top of the table
  history: true, //allow undo and redo actions on the table
  pagination: 'local', //paginate the data
  paginationSize: 12, //allow 7 rows per page of data
  paginationCounter: 'rows', //display count of paginated rows in footer
  movableColumns: true, //allow column order to be changed
  columns: lgaColumns, //load LGA columns by default
});

// LOAD ALL LGAs FUNCTIONALITY

//trigger AJAX load on button click
document
  .getElementById('allLGAsTrigger')
  .addEventListener('click', function () {
    // Then load the new data
    currentTable = 'lga';
    xhttp.open(
      'GET',
      '/querylga/' + sortDropdown.value + '/' + dirButton.value,
      true
    );
    xhttp.send();
  });

document
  .getElementById('allLGAsTrigger')
  .addEventListener('click', function () {
    table.setColumns(lgaColumns); //overwrite existing columns with new columns definition array
  });

//LOAD ALL AGES FUNCTIONALITY

//trigger AJAX load on button click
document
  .getElementById('allAgesTrigger')
  .addEventListener('click', function () {
    // Then load the new data
    currentTable = 'age';
    xhttp.open(
      'GET',
      '/queryage/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  });

document
  .getElementById('allAgesTrigger')
  .addEventListener('click', function () {
    table.setColumns(ageColumns); //overwrite existing columns with new columns definition array
  });

//new column definition array
var ageColumns = [
  { title: 'LGA Code', field: 'lgacode', headerFilter: 'input' },
  {
    title: 'LGA Name',
    field: 'lganame',
    headerFilter: 'input',
    cellClick: function (e, cell) {
      // Send a request
      xhttpBuild.open('GET', '/LGABuild/' + cell.getValue(), true);
      xhttpBuild.send();
    },
  },
  {
    title: 'Indigenous Status',
    field: 'indiStatus',
    editor: 'list',
    editorParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
    },
    headerFilter: true,
    headerFilterParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
      clearable: true,
    },
  },
  {
    title: 'Sex',
    field: 'sex',
    editor: 'list',
    editorParams: {
      values: { Male: 'Male', Female: 'Female' },
    },
    headerFilter: true,
    headerFilterParams: {
      values: { Male: 'Male', Female: 'Female' },
      clearable: true,
    },
  },
  {
    title: '0 - 4',
    field: 'ageRange1',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '5 - 9',
    field: 'ageRange2',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '10 - 14',
    field: 'ageRange3',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '15 - 19',
    field: 'ageRange4',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '20 - 24',
    field: 'ageRange5',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '25 - 29',
    field: 'ageRange6',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '30 - 34',
    field: 'ageRange7',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '35 - 39',
    field: 'ageRange8',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '40 - 44',
    field: 'ageRange9',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '45 - 49',
    field: 'ageRange10',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '50 - 54',
    field: 'ageRange11',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '55 - 59',
    field: 'ageRange12',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '60 - 64',
    field: 'ageRange13',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: '65+',
    field: 'ageRange14',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
];

//LOAD LABOUR FORCE FUNCTIONALITY

//trigger AJAX load on button click
document
  .getElementById('labourForceTrigger')
  .addEventListener('click', function () {
    // Then load the new data
    currentTable = 'labourforce';
    xhttp.open(
      'GET',
      '/querylabourforce/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  });

//Update columns as well
document
  .getElementById('labourForceTrigger')
  .addEventListener('click', function () {
    table.setColumns(labourForceColumns); //overwrite existing columns with new columns definition array
  });

//new column definition array
var labourForceColumns = [
  { title: 'LGA Code', field: 'lgacode', headerFilter: 'input' },
  {
    title: 'LGA Name',
    field: 'lganame',
    headerFilter: 'input',
    cellClick: function (e, cell) {
      // Send a request
      xhttpBuild.open('GET', '/LGABuild/' + cell.getValue(), true);
      xhttpBuild.send();
    },
  },
  {
    title: 'Indigenous Status',
    field: 'indiStatus',
    editor: 'list',
    editorParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
    },
    headerFilter: true,
    headerFilterParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
      clearable: true,
    },
  },
  {
    title: 'Sex',
    field: 'sex',
    editor: 'list',
    editorParams: {
      values: { Male: 'Male', Female: 'Female' },
    },
    headerFilter: true,
    headerFilterParams: {
      values: { Male: 'Male', Female: 'Female' },
      clearable: true,
    },
  },
  {
    title: 'Employed',
    field: 'employed',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Unemployed',
    field: 'unemployed',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Government Industry',
    field: 'governmentIndustry',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Private Industry',
    field: 'privateIndustry',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Not In Labour Force',
    field: 'notInLabourForce',
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
];

//LOAD SCHOOL QUALIFICATIONS

//trigger AJAX load on button click
document.getElementById('schoolTrigger').addEventListener('click', function () {
  // Set current table and load the new data
  currentTable = 'school';
  // Then load the new data
  xhttp.open(
    'GET',
    '/queryschool/' +
      sortDropdown.value +
      '/' +
      ratioDropdown.value +
      '/' +
      dirButton.value,
    true
  );
  xhttp.send();
});

document.getElementById('schoolTrigger').addEventListener('click', function () {
  table.setColumns(schoolColumns); //overwrite existing columns with new columns definition array
});

//new column definition array
var schoolColumns = [
  { title: 'LGA Code', field: 'lgacode', headerFilter: 'input' },
  {
    title: 'LGA Name',
    field: 'lganame',
    headerFilter: 'input',
    cellClick: function (e, cell) {
      // Send a request
      xhttpBuild.open('GET', '/LGABuild/' + cell.getValue(), true);
      xhttpBuild.send();
    },
  },
  {
    title: 'Indigenous Status',
    field: 'indiStatus',
    editor: 'list',
    editorParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
    },
    headerFilter: true,
    headerFilterParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
      clearable: true,
    },
  },
  {
    title: 'Sex',
    field: 'sex',
    editor: 'list',
    editorParams: {
      values: { Male: 'Male', Female: 'Female' },
    },
    headerFilter: true,
    headerFilterParams: {
      values: { Male: 'Male', Female: 'Female' },
      clearable: true,
    },
  },
  {
    title: 'Did not go to school',
    field: 'noSchool',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Year 8',
    field: 'year8',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Year 10',
    field: 'year10',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Year 12',
    field: 'year12',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
];

//LOAD NON SCHOOL QUALIFICATIONS

//trigger AJAX load on button click
document
  .getElementById('nonschoolTrigger')
  .addEventListener('click', function () {
    // Then set current table and load the new data
    currentTable = 'nonschool';
    // Then load the new data
    xhttp.open(
      'GET',
      '/querynonschool/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  });

document
  .getElementById('nonschoolTrigger')
  .addEventListener('click', function () {
    table.setColumns(nonschoolColumns); //overwrite existing columns with new columns definition array
  });

//new column definition array
var nonschoolColumns = [
  { title: 'LGA Code', field: 'lgacode', headerFilter: 'input' },
  {
    title: 'LGA Name',
    field: 'lganame',
    headerFilter: 'input',
    cellClick: function (e, cell) {
      // Send a request
      xhttpBuild.open('GET', '/LGABuild/' + cell.getValue(), true);
      xhttpBuild.send();
    },
  },
  {
    title: 'Indigenous Status',
    field: 'indiStatus',
    editor: 'list',
    editorParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
    },
    headerFilter: true,
    headerFilterParams: {
      values: {
        Indigenous: 'Indigenous',
        'Not Indigenous': 'Not Indigenous',
        Unspecified: 'Unspecified',
      },
      clearable: true,
    },
  },
  {
    title: 'Sex',
    field: 'sex',
    editor: 'list',
    editorParams: {
      values: { Male: 'Male', Female: 'Female' },
    },
    headerFilter: true,
    headerFilterParams: {
      values: { Male: 'Male', Female: 'Female' },
      clearable: true,
    },
  },
  {
    title: 'Certificate II',
    field: 'certii',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Advanced Diploma',
    field: 'advdip',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Graduate Certificate/Diploma',
    field: 'gradcert',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Bachelor Degree',
    field: 'bachelor',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
  {
    title: 'Postgraduate Degree',
    field: 'postgrad',
    formatter: 'money',
    formatterParams: paramLookup,
    headerFilter: minMaxFilterEditor,
    headerFilterFunc: minMaxFilterFunction,
    headerFilterLiveFilter: false,
  },
];

// This changes the percentage ratio based on currently loaded table and dropdown form
document.getElementById('ratioChoice').onchange = function () {
  if (this.value == 'total') {
    table.setData('/query' + currentTable + ratioChoice.value);
    currentArea = 'total';
  }
  if (this.value == 'aus') {
    table.setData('/query' + currentTable + ratioChoice.value);
    currentArea = 'aus';
  }
  if (this.value == 'state') {
    table.setData('/query' + currentTable + ratioChoice.value);
    currentArea = 'state';
  }
  if (this.value == 'lga') {
    table.setData('/query' + currentTable + ratioChoice.value);
    currentArea = 'lga';
  }
};

//Select everything with queryFilter class
let filterCall = document.querySelectorAll('.queryFilter');

// Send a request based on the current table and filter selections
const serverRequest = function () {
  // TODO: replace desc with variable
  if (currentTable == 'lga') {
    xhttp.open(
      'GET',
      '/querylga/' + sortDropdown.value + '/' + dirButton.value,
      true
    );
    xhttp.send();
  }
  if (currentTable == 'age') {
    xhttp.open(
      'GET',
      '/queryage/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  }
  if (currentTable == 'labourforce') {
    xhttp.open(
      'GET',
      '/querylabourforce/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  }
  if (currentTable == 'school') {
    xhttp.open(
      'GET',
      '/queryschool/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  }
  if (currentTable == 'nonschool') {
    xhttp.open(
      'GET',
      '/querynonschool/' +
        sortDropdown.value +
        '/' +
        ratioDropdown.value +
        '/' +
        dirButton.value,
      true
    );
    xhttp.send();
  }
  // Look up current ratio value and update columns with precision and percentage accordingly
  if (ratioDropdown.value == 'total') {
    paramLookup = function () {
      //do some processing and return the param object
      return { symbol: '', precision: false };
    };
  } else {
    paramLookup = function () {
      //do some processing and return the param object
      return { symbol: '%', precision: 8 };
    };
  }
};

filterCall.forEach((filterCall) => {
  filterCall.onchange = function () {
    serverRequest();
  };
});

// When a response is received, update the table
xhttp.onload = function () {
  let newTable = xhttp.responseText;
  table.setData(newTable);
  // console.log(newTable);
};
