
function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

$(document).ready(function() {
    $('#' + getURLParameter('sort')).addClass('sort_by_it');
});

$(document).ready(function() {

    var sort_by_function = function(event) {
        var sortByValue = $(event.target).attr("id");
        if (sortByValue === undefined) {
           return;
        }
        var reverseOrder;
        if (getURLParameter('sort') == sortByValue) {
            reverseOrder = (getURLParameter('reverse') != 'true');
        } else {
            reverseOrder = false;
        }

        window.location.href = location.protocol + '//' + location.host + location.pathname + "?sort=" + sortByValue + "&reverse=" + reverseOrder;
    };
    $("th").on("click", sort_by_function);

    var delete_item_function = function() {
        var itemId = $(this).attr('id').substring(7);
        var confirmation = confirm("You want to delete '" + itemId + "'. Are you sure?");
        if (confirmation) {
            $.ajax({
                url: document.URL.replace("storage", "delete"),
                type: 'post',
                data: {'name': itemId},
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