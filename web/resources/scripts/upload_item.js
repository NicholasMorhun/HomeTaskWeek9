
$(document).ready(function() {

    var createFolder = function() {
        var newFolderName = prompt("New folder:");
        if (newFolderName !== undefined || newFolderName != "") {
            var url = (location.protocol + '//' + location.host + location.pathname).replace(/storage/g, "upload");
            window.location.href = url + '?name=' + newFolderName;
        }
    };
    $("#add_folder_img").on("click", createFolder);

    var selectItemForUpload = function(event) {
        var buttonId = $(event.target).attr("id");
        $("#type_hidden_input").attr("value", buttonId.split("_")[1]);
        $("#file_input").click();
    };
    $("#add_file_img").on("click", selectItemForUpload);
    $("#add_archive_img").on("click", selectItemForUpload);

    var uploadFile = function() {
        var url = (location.protocol + '//' + location.host + location.pathname).replace(/storage/g, "upload");
        document.forms["upload_file"].action = url;
        document.forms["upload_file"].submit();
    };
    $("#file_input").on("change", uploadFile);

});
