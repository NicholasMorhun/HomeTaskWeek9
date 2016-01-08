
$(document).ready(function() {

    var delete_item_function = function() {
        var itemId = $(this).attr('id').substring(7);
        var confirmation = confirm("You want to delete '" + itemId + "'. Are you sure?");
        if (confirmation) {
            $.ajax({
                url: document.URL,
                type: 'DELETE',
                data: 'name=' + itemId,
                success: function() {
                    $('#' + itemId).fadeOut();
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert('Error. Item has not deleted');
                }
            });
        }
    };

    $(".control_button_img").on("click", delete_item_function);
});