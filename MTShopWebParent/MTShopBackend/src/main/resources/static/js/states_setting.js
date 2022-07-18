var buttonLoad4States;
var dropDownCountry4States;
var dropDownStates;
var buttonAddState;
var buttonUpdateState;
var buttonDeleteState;
var labelStateName;
var fieldStateName;

$(document).ready(function() {
    buttonLoad4States = $("#buttonLoadCountriesForStates");
    dropDownCountry4States = $("#dropDownCountriesForStates");
    dropDownStates = $("#dropDownStates");
    buttonAddState = $("#buttonAddState");
    buttonUpdateState = $("#buttonUpdateState");
    buttonDeleteState = $("#buttonDeleteState");
    labelStateName = $("#labelStateName");
    fieldStateName = $("#fieldStateName");

    buttonLoad4States.click(function() {
        loadCountries4States();
    });

    dropDownCountry4States.on("change", function() {
        loadStates4Country();
    });

    dropDownStates.on("change", function() {
        changeFormStateToSelectedState();
    });

    buttonAddState.click(function() {
        if (buttonAddState.val() == "Add") {
            addState();
        } else {
            changeFormStateToNew();
        }
    });

    buttonUpdateState.click(function() {
        updateState();
    });

    buttonDeleteState.click(function() {
        deleteState();
    });
});

function deleteState() {
    stateId = dropDownStates.val();

    url = contextPath + "states/delete/" + stateId;

    $.ajax({
        type: 'DELETE',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }
    }).done(function() {
        $("#dropDownStates option[value='" + stateId + "']").remove();
        changeFormStateToNew();
        showToastMessage("Tiểu bang/tỉnh thành đã xoá thành công");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function updateState() {
    if (!validateFormState()) return;

    url = contextPath + "states/save";
    stateId = dropDownStates.val();
    stateName = fieldStateName.val();

    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    countryName = selectedCountry.text();

    jsonData = {id: stateId, name: stateName, country: {id: countryId, name: countryName}};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(stateId) {
        $("#dropDownStates option:selected").text(stateName);
        showToastMessage("Tiểu bang/tỉnh thành đã được cập nhật thành công");
        changeFormStateToNew();
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function addState() {
    if (!validateFormState()) return;

    url = contextPath + "states/save";
    stateName = fieldStateName.val();

    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    countryName = selectedCountry.text();

    jsonData = {name: stateName, country: {id: countryId, name: countryName}};

    $.ajax({
        type: 'POST',
        url: url,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        },
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function(stateId) {
        selectNewlyAddedState(stateId, stateName);
        showToastMessage("Tiểu bang/tỉnh thành đã được thêm thành công");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });

}

function validateFormState() {
    formState = document.getElementById("formState");
    if (!formState.checkValidity()) {
        formState.reportValidity();
        return false;
    }

    return true;
}

function selectNewlyAddedState(stateId, stateName) {
    $("<option>").val(stateId).text(stateName).appendTo(dropDownStates);

    $("#dropDownStates option[value='" + stateId + "']").prop("selected", true);

    fieldStateName.val("").focus();
}

function changeFormStateToNew() {
    buttonAddState.val("Add");
    labelStateName.text("Tiểu bang / tỉnh thành:");

    buttonUpdateState.prop("disabled", true);
    buttonDeleteState.prop("disabled", true);

    fieldStateName.val("").focus();
}

function changeFormStateToSelectedState() {
    buttonAddState.prop("value", "New");
    buttonUpdateState.prop("disabled", false);
    buttonDeleteState.prop("disabled", false);

    labelStateName.text("Tiểu bang / tỉnh thành:");

    selectedStateName = $("#dropDownStates option:selected").text();
    fieldStateName.val(selectedStateName);

}

function loadStates4Country() {
    selectedCountry = $("#dropDownCountriesForStates option:selected");
    countryId = selectedCountry.val();
    url = contextPath + "states/list_by_country/" + countryId;

    $.get(url, function(responseJSON) {
        dropDownStates.empty();

        $.each(responseJSON, function(index, state) {
            $("<option>").val(state.id).text(state.name).appendTo(dropDownStates);
        });

    }).done(function() {
        changeFormStateToNew();
        showToastMessage("Tất cả Tiểu bang/tỉnh thành đã tải lên thành công " + selectedCountry.text());
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}

function loadCountries4States() {
    url = contextPath + "countries/list";
    $.get(url, function(responseJSON) {
        dropDownCountry4States.empty();

        $.each(responseJSON, function(index, country) {
            $("<option>").val(country.id).text(country.name).appendTo(dropDownCountry4States);
        });

    }).done(function() {
        buttonLoad4States.val("Refresh danh sách quốc gia");
        showToastMessage("Tất cả quốc gia đã tải lên thành công");
    }).fail(function() {
        showToastMessage("ERROR: Could not connect to server or server encountered an error");
    });
}