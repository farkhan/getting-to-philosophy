$(function() {
    $('#urlForm').validate({
        rules: {
            url: {
                required: true,
                url: true
            }
        },
        messages: {
            url: {
                required: "Url is required",
                minlength: "your name must consist of at least 2 characters",
                url: "Please enter a valid URL"
            }
        },
        submitHandler: function(form) {
            $(form).ajaxSubmit({
                type:"POST",
                data: $(form).serialize(),
                url:"process.php",
                success: function() {
                    $('#urlForm :input').attr('disabled', 'disabled');
                    $('#urlForm').fadeTo( "slow", 0.15, function() {
                        $(this).find(':input').attr('disabled', 'disabled');
                        $(this).find('label').css('cursor','default');
                        $('#success').fadeIn();
                    });
                },
                error: function() {
                    $('#contact').fadeTo( "slow", 0.15, function() {
                        $('#error').fadeIn();
                    });
                }
            });
        }
    });
});