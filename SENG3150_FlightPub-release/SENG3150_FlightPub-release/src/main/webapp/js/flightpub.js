function validateSpecificSearch() {
    if(document.getElementById("return").checked) {
        if(!document.getElementById("retdate").value) {
            alert("Please select your return date");
            return false;
        }
    }

    // written by Sam
    var depart = false;
    var arrival = false;
    for(var i = 0; i < getAirports().length; i++){
        var arr = getAirports();
        if(document.getElementById("depart").value == arr[i].split(" - ")[1]) {
            depart = true;
        }
        if(document.getElementById("arrival").value == arr[i].split(" - ")[1]) {
            arrival = true;
        }
    }
    if(!depart || !arrival)
    {
        if(!depart && !arrival) {
            alert("Error, these airports do not exist");
        } else if(!depart) {
            alert("Error, the departure airport "+document.getElementById("depart").value + " does not exist");
        } else {
            alert("Error, the arrival airport "+document.getElementById("arrival").value + " does not exist");
        }
        return false;
    }

    return true;
}

function validateAssistedSearch() {
    if(document.getElementById("return2").checked) {
        var returnDate = document.getElementById("returndate2").value;
        var returnDateEnd = document.getElementById("returndateend").value;
        if(!returnDate || !returnDateEnd) {
            alert("Please select your return dates");
            return false;
        }
    }

    // written by Sam
    var depart = false;
    for(var i = 0; i < getAirports().length; i++){
        var arr = getAirports();
        if(document.getElementById("depart2").value == arr[i].split(" - ")[0] ||
            document.getElementById("depart2").value == arr[i].split(" - ")[1]) {
            depart = true;
        }
    }
    if(!depart)
    {
        alert("Error, the departure airport "+document.getElementById("depart2").value + " does not exist");
        return false;
    }

    return true;
}

function minimumDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    if(dd<10) {
        dd='0'+dd
    }
    if(mm<10) {
        mm='0'+mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    document.getElementById("depdate").setAttribute("min", today);
    document.getElementById("depdate2").setAttribute("min", today);
    document.getElementById("returndate").setAttribute("min", today);
    document.getElementById("returndate2").setAttribute("min", today);
}

function hideReturnDate() {
    var x = document.getElementById("returning");
    x.style.display = "none";
}

function showReturnDate() {
    var x = document.getElementById("returning");
    x.style.display = "inline-block";
}

function hideReturnDate2() {
    var x = document.getElementById("returning2");
    x.style.display = "none";
}

function showReturnDate2() {
    var x = document.getElementById("returning2");
    x.style.display = "inline-block";
}

function popUpReview() {
    var modal = document.getElementById('popUpModal');
    modal.style.display = "block";
}

function closePopUpReview() {
    var modal = document.getElementById('popUpModal');
    modal.style.display = "none";
}

var airports = {
    "ADL": {
        "n": "Adelaide"
    },
    "AMS": {
        "n": "Amsterdam"
    },
    "ATL": {
        "n": "Atlanta"
    },
    "BKK": {
        "n": "Bangkok"
    },
    "BNE": {
        "n": "Brisbane"
    },
    "CBR": {
        "n": "Canberra"
    },
    "CDG": {
        "n": "Paris-Charles"
    },
    "CNS": {
        "n": "Cairns"
    },
    "DOH": {
        "n": "Doha"
    },
    "DRW": {
        "n": "Darwin"
    },
    "DXB": {
        "n": "Dubai"
    },
    "FCO": {
        "n": "Rome-Fiumicino"
    },
    "GIG": {
        "n": "Rio De Janeiro"
    },
    "HBA": {
        "n": "Hobart"
    },
    "HEL": {
        "n": "Helsinki"
    },
    "HKG": {
        "n": "Hong Kong"
    },
    "HNL": {
        "n": "Honolulu"
    },
    "JFK": {
        "n": "New York - JFK"
    },
    "JNB": {
        "n": "Johannesburg"
    },
    "KUL": {
        "n": "Kuala Lumpur"
    },
    "LAX": {
        "n": "Los Angeles"
    },
    "LGA": {
        "n": "New York - Laguardia"
    },
    "LGW": {
        "n": "London-Gatwick"
    },
    "LHR": {
        "n": "London-Heathrow"
    },
    "MAD": {
        "n": "Madrid"
    },
    "MEL": {
        "n": "Melbourne"
    },
    "MIA": {
        "n": "Miami"
    },
    "MUC": {
        "n": "Munich"
    },
    "NRT": {
        "n": "Tokyo - Narita"
    },
    "OOL": {
        "n": "Gold Coast"
    },
    "ORD": {
        "n": "Chicago - OHare Intl."
    },
    "ORY": {
        "n": "Paris - Orly"
    },
    "PER": {
        "n": "Perth"
    },
    "SFO": {
        "n": "San Francisco"
    },
    "SIN": {
        "n": "Singapore"
    },
    "SYD": {
        "n": "Sydney"
    },
    "VIE": {
        "n": "Vienna"
    },
    "YYZ": {
        "n": "Toronto"
    }
};

function getAirports() {
    var arr = [];
    for (airport in airports) {
        if (airports.hasOwnProperty(airport)) {
            arr.push(airport + " - " + airports[airport].n);
        }
    }
    return arr;
}

$(document).ready(function(){
    $("#depart").autocomplete({
        source: getAirports(),
        select: function (event, ui) {
            ui.item.value = ui.item.value.split(" - ")[1];
            $("#depart").on('change', function(){
                $("#depart").attr('value', ui.item.value);
            });

        }
    });
    $("#arrival").autocomplete({
        source: getAirports(),
        select: function (event, ui) {
            ui.item.value = ui.item.value.split(" - ")[1];
            $("#arrival").on('change', function(){
                $("#arrival").attr('value', ui.item.value);
            });
        }
    });
    $("#depart2").autocomplete({
        source: getAirports(),
        select: function (event, ui) {
            ui.item.value = ui.item.value.split(" - ")[1];
            $("#depart2").on('change', function(){
                $("#depart2").attr('value', ui.item.value);
            });
        }
    });
});