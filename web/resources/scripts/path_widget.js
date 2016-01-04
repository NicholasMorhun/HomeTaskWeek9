
$(document).ready(function() {
    var submitPath = function() {
        var path = $("#path").val();
        if (path == "") {
            path = "/";
        }
        var urlPathArray = window.location.pathname.split("/");
        var newUrl = "/" + urlPathArray[1] + "/" + urlPathArray[2] + path;

        document.forms["path_form"].action = newUrl;
        document.forms["path_form"].submit();
    };

    $("#change_path_button_img").on("click", submitPath);

    $("#path").keyup(function(event){
        if(event.keyCode == 13) {
            submitPath(); // TODO verify
        }
    });
});
