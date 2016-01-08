
function showError(errorMessage) {
    var errorTextSelector = $("#error_message_text");
    if (errorTextSelector.text() != "") {
        errorTextSelector.text(errorMessage);
        $("#error_message_window").css("display", "block");
    }
}

$(document).ready(function () {
    $("#close_error_message_button").on("click", function() {
        $("#error_message_window").fadeOut();
    });
});
