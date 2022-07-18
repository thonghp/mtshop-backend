dropdownBrands = $("#brand");
dropdownCategories = $("#category");

$(document).ready(function () {

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    dropdownBrands.change(function () {
        dropdownCategories.empty();
        getCategories();
    });

    getCategoriesForNewForm();

});

function getCategoriesForNewForm() {
    catIdField = $("#categoryId");
    editMode = false;

    if (catIdField.length)
        editMode = true;

    if (!editMode)
        getCategories();

}

function getCategories() {
    brandId = dropdownBrands.val();
    url = brandModuleUrl + "/" + brandId + "/categories";

    $.get(url, function (responseJson) {
        $.each(responseJson, function (index, category) {
            $("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
        });
    });
}

function checkUnique(form) {
    productId = $("#id").val();
    productName = $("#name").val();

    csrfValue = $("input[name='_csrf']").val();

    params = {id: productId, name: productName, _csrf: csrfValue};

    $.post(checkUniqueUrl, params, function (response) {
        if (response == "OK") {
            form.submit();
        } else if (response == "Duplicate") {
            showWarningModal("Sản phẩm " + productName + " đã tồn tại !");
        } else {
            showErrorModal("Unknown response from server");
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server"); // this line runs when the above url is wrong
    });

    return false;
}