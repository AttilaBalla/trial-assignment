function getusers() {
    
    $container = $("#user_rows").empty();

    function populateUserTable(i, user) {
        var $row = $("<div/>")
                .addClass("row")
                .html(`
                <div class="col">
                ` + user["id"] + `
                </div>
                <div class="col">
                ` + user["username"] + `
                </div>
                <div class="col">
                ` + user["email"] + `
                </div>
                <div class="col">
                    <a class="text-dark" href="#" data-toggle="modal" data-target="#delete_modal">
                        <span class="fa fa-trash delete_icon" aria-hidden="true" data-userId="`+ user["id"] +`" data-toggle="tooltip" data-placement="top" title="Delete"></span>
                    </a>
                </div>
            `);
        
        $container.append($row);
    }

    function setDeleteTriggers() {
        $(".delete_icon").click(function() {
            $(".delete_user_button").attr({
                "data-userid": this.dataset.userid
            });
        });
    }

    $.ajax({
        type: 'GET',
        url: '/users',
        success: function(response) {

            if(JSON.parse(response).length > 0) {
                jQuery.each(JSON.parse(response), function(i, user) {
                    populateUserTable(i, user);
                });
                setDeleteTriggers();
            } 
            else {
                console.log("No users found at this time!");
            } 
        },
        error: function(response) {
            console.log(response);
            //TODO
        }
    });
}

getusers();