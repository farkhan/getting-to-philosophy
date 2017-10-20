$(function() {
    $(document).ready(function(){
        $('span.glyphicon-refresh').hide();
        $("#submit").on('click', function(){
            var urlInput = $("#url").val();
            if (isFormValid()) {
                $('.glyphicon').removeClass('glyphicon-search');
                $('.glyphicon').addClass('spinner');
                $.ajax({
                    url: 'url',
                    type : "POST",
                    data: JSON.stringify({url: urlInput}),
                    contentType: "application/json; charset=utf-8",
                    success : function(result) {
                        $("#pathList").html(
                            $("#pathTemplate").render(result, true)
                        );
                        enableSearchButton()
                        console.log(result);
                    },
                    error: function(xhr, resp, text) {
                        enableSearchButton()
                        console.log(xhr, resp, text);
                    }
                });
            }
        });
        $('#url').keypress(function(e) {
            if(e.which === 13) {
                e.preventDefault();
                $(this).blur();
                $('#submit').focus().click();
            }
        });
        function isFormValid() {
            var success = true;
            var msg = "";
            if ($("#url").val() == "") {
                msg = "Please enter a URL";
                success = false;
            }
            $("#errorMsg").html(msg)
            return success;
        }

        function enableSearchButton() {
            $('.glyphicon').removeClass('spinner');
            $('.glyphicon').addClass('glyphicon-search');
        }
    });
});