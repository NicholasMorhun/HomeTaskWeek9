$(document).ready(function() {
    function loadContent() {
        $.ajax({
            url: "${urlToFile}",
            async: false,
            success: function(result){
                $("#source_code").text(result);
            }});
    }

    loadContent();
    SyntaxHighlighter.all();
});