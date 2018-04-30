window.onload = function() {

    var registerAlert = $(".register_alert").hide();
    var mainAlert = $(".main_alert").hide();

    function convertToSimpleJson(formArray) {
        var returnArray = {};
        for (var i = 0; i < formArray.length; i++){
            returnArray[formArray[i]['name']] = formArray[i]['value'];
        }
        return returnArray;
        }

    function validateForm(data) {
        if(data["userPassword"] !== data["userPasswordAgain"]) {
            registerAlert.addClass("alert-danger").html("Passwords must match!");
            return false;
        }

        if(data["userPassword"].length < 6) {
            registerAlert.addClass("alert-danger").html("Password has to be at least 6 characters long!");
            return false;
        }

        return true;

    }

    $(".register_form").on("submit", function(event) {

        event.preventDefault();
        registerAlert.removeClass("alert-danger").empty().hide();
        mainAlert.removeClass("alert-danger alert-success").empty();

        let data = convertToSimpleJson($(this).serializeArray());
        if(!validateForm(data)) {
            registerAlert.show();
            return;
        }

        console.log(data);
        
        $.ajax({
            type: 'POST',
            contentType: 'application/JSON',
            url: "/users",
            data: JSON.stringify(data),
            
            success: function(response) {
                if(JSON.parse(response)["success"] === true){
                    getusers();
                    $("#register_modal").modal('hide');
                    $(".register_form").trigger("reset");
                    mainAlert.addClass("alert-success").append("Your account has been created successfully!");
                    mainAlert.fadeTo(5000, 5000).slideUp(500, function(){
                        mainAlert.slideUp(500);
                         });
                } else {
                    registerAlert.append(JSON.parse(response)["information"]);
                    registerAlert.addClass("alert-danger").show();
                }
            },

            error: function(response) {
                console.log(response);
                mainAlert.addClass("alert-danger").append("An error occured, please try again later!");
                mainAlert.fadeTo(5000, 5000).slideUp(500, function(){
                mainAlert.slideUp(500);
                    });
            }
        });
    });
}
