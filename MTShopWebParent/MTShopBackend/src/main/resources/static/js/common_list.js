function clearFilter() {
    window.location = moduleUrl;
}

function showDeleteConfirmModal(link, entityName) {
    entityId = link.attr("entityId");

    $("#yesButton").attr("href", link.attr("href")); // set value of attribute
    $("#confirmText").text("Bạn có muốn xoá " + entityName + " có id " + entityId + " không ?");
    $("#confirmModal").modal();
}