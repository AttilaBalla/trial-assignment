$(".delete_user_button").click(function(event){

    let mainAlert = $(".main_alert").hide();
    let id = this.dataset.userid;
    
    $.ajax({
        type: 'DELETE',
        url: '/delete?userId=' + id,
        success: function(response) {
            getusers();
            mainAlert.empty();
            $("#delete_modal").modal('hide');
            mainAlert.addClass("alert-success").append("Account deleted successfully!");
            mainAlert.fadeTo(5000, 5000).slideUp(500, function(){
                mainAlert.slideUp(500);
                    });
        },
        error: function(response) {
            console.log(response);
            //TODO
        }
    });
});