/* ***Begin Code - Tejaswini Nallamothu *** */
"use strict";
let _customForm = document.querySelector('.custom-form'),
    _formButton = document.querySelector('.btn-submit button'),
    _countriesInsert = document.querySelector('.countries select'),
    _ageField = document.querySelector('#age'),
    _firstName = document.querySelector('#firstName'),
    _lastName = document.querySelector('#lastName'),
    _countriesList,
    _maxAge,
    _minAge;

// Fetching countries select values from json
const countriesDataFn = ()=>{
    let countriesJson = '/content/dam/anf-code-challenge/exercise-1/countries.json';
    fetch(countriesJson)
    .then((response) => response.json())
    .then((data)=>{
        countriesAppendFn(data);
    });
}

// Append countries list to selectbox
const countriesAppendFn = (countryVal)=>{    
    _countriesList = Object.keys(countryVal);
    _countriesList.map((values)=>{
        _countriesInsert.innerHTML += '<option>'+values+'</option>';
    });
}

// Getting Ager JSON for age validation
const ageValidationFn=()=>{
    let ageJson = '/bin/ageValidation';
    fetch(ageJson)
    .then((response) => response.json())
    .then((data)=>{
        _maxAge = data.maxAge;
        _minAge = data.minAge;
    });
}

// Form on Submit function
const formFn = ()=>{    
    _formButton.addEventListener('click',()=>{

        let ageFieldVal = _ageField.value;  

        if(ageFieldVal < _minAge || ageFieldVal > _maxAge ){            
            warningToasterFn('You are not eligible', _ageField);
        }else if(document.querySelector('.warning-toaster') !== null){
            document.querySelector('.warning-toaster').remove();
        }

        if(_countriesInsert[_countriesInsert.selectedIndex].text === 'Select'){            
            warningToasterFn('Select Country', document.querySelector('.countries select'))
        }else if(document.querySelector('.warning-toaster') !== null){
            document.querySelector('.warning-toaster').remove();
        }

        if((ageFieldVal > _minAge && ageFieldVal < _maxAge) && _countriesInsert[_countriesInsert.selectedIndex].text !== 'Select'){
            fetch('/bin/saveUserDetails?firstName='+_firstName.value+'&lastName='+_lastName.value+'&age='+ageFieldVal+'&country='+_countriesInsert[_countriesInsert.selectedIndex].text+' ',  {
                method: "POST"
            }).then( (response) => {                 
                response.status = 200 ? _customForm.reset() : ''
             });
             
        }
    });
}

// Common Warning Toaster
const warningToasterFn = (text,element)=>{
    if(document.querySelector('.warning-toaster') == null){
        let warningElem = document.createElement('p');
        warningElem.className = 'warning-toaster';
        warningElem.textContent = text;
        element.after(warningElem);    
    }        
}

// Execute function on DOM Ready
if(_customForm !==null){
    window.addEventListener("DOMContentLoaded", () => {
        formFn()
        countriesDataFn();
        ageValidationFn();
    });
}

// ***END Code*****