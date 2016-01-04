
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
            error: function(xhr, textStatus, errorThrown){
                alert('Error. Folder wasn\'t created');
            }
        });

    };

    $("#add_folder_img").on("click", add_folder_function);

});
