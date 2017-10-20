$(function() {
    $(document).ready(function(){
        // click on button submit
        $('span.glyphicon-refresh').hide();
        $("#submit").on('click', function(){
            $('.glyphicon').removeClass('glyphicon-search');
            $('.glyphicon').addClass('spinner');
            var urlInput = $("#url").val();
            $.ajax({
                url: 'url',
                type : "POST",
                data: JSON.stringify({url: urlInput}),
                contentType: "application/json; charset=utf-8",
                success : function(result) {
                    $("#pathList").html(
                        $("#pathTemplate").render(result, true)
                    );
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                },
                always: function() {
                    console.log('always');
                    $('.glyphicon').removeClass('spinner');
                    $('.glyphicon').addClass('glyphicon-search');
                }
            });
        });
        $('#url').keypress(function(e) {
            if(e.which === 13) {
                e.preventDefault();
                $(this).blur();
                $('#submit').focus().click();
            }
        });
    });
});