
$(document).ready(function() {

    var add_folder_function = function() {
        var newFolderName = prompt("New folder:");
        if (newFolderName == null) {
            return;
        } else if (newFolderName == "") {
            alert('Folder name cannot be empty string');
            return;
        }

        $.ajax({
            url: document.URL,
            type: 'PUT',
            data: 'type=folder&name=' + newFolderName,
            success: function() {
                location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('Error. Folder wasn\'t created');
            }
        });

    };

    $("#add_folder_img").on("click", add_folder_function);

    var old_file_value;
    var add_file_function = function() {
        var fileInput = $("#file_input");
        old_file_value = fileInput.files;
        fileInput.click();
        while (old_file_value == fileInput.files) {} // crutch for wait until user select file
        var file = fileInput.files[0];

        $.ajax({
            url: document.URL,
            type: 'PUT',
            data: 'type=file&name=' + file.name + '&file=' + file,
            cache: false,
            dataType: 'json',
            processData: false,
            contentType: false,
            success: function(data, textStatus, jqXHR)
            {
                location.reload();
            },
            error: function(jqXHR, textStatus, errorThrown)
            {
                alert('Error. File has not uploaded');
            }
        });

    };

    $("#add_file_img").on("click", add_file_function);

});
