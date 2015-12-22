
$(document).ready(function() {
    if ($("#error_message_text").text() != "") {
        $("#error_message_window").css("display", "block");
    }

    $("#close_error_message_button").on("click", function() {
        $("#error_message_window").fadeOut();
    });
});
