$(document).ready(function () {
    // navigate when pressing cancel button
    $("#btnCancel").on("click", function () {
        window.location = moduleUrl;
    });

    // check image file size
    $("#fileImage").change(function () { // is executed every time the event is fired
        if (!checkFileSize(this)) {
            return;
        }

        showImageThumbnail(this);
    });
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();

    reader.onload = function (e) {
        $("#thumbnail").attr("src", e.target.result);
    };

    reader.readAsDataURL(file);
}

function checkFileSize(fileInput) {
    fileSize = fileInput.files[0].size; // returns the selected file and can get the name or size
    var mb = 1024 * 1024; // 1mb = 1024 x 1024 kb

    if (fileSize > mb) {
        fileInput.setCustomValidity("Bạn phải chọn ảnh nhỏ hơn 1MB !"); // Show message as required of input
        fileInput.reportValidity();

        return false;
    } else {
        fileInput.setCustomValidity("");

        return true;
    }
}

function showModalDialog(title, message) {
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function showErrorModal(message) {
    showModalDialog("Error", message)
}

function showWarningModal(message) {
    showModalDialog("Warning", message)
}