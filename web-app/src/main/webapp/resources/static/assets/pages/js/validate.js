jQuery.validator.addMethod("dateFormat", function(value, element) {
        if(value=="") return true;
        return value.match(/^\d\d\d\d-\d\d-\d\d$/);
    },
    "Please enter a date in the format yyyy-MM-dd."
);

jQuery.validator.addMethod("isValidDate", function (value, element) {
    if (value=="") return true;
    var date = Date.parse(value);
    return !isNaN(date);
},'is not valid date.');

jQuery.validator.addMethod("dateFromFuture", function (value, element) {
    if (value=="") return true;
    var date = Date.parse(value),
        todayDate = new Date();
    return todayDate>=date;
},'should not be from future.');

jQuery.validator.addMethod("greaterThan", function (value, element, params) {
    if ((value=="") || ($(params).val()=="")) return true;
    var dateFrom = Date.parse($(params).val());
    var dateTo = Date.parse(value);
    return dateTo>=dateFrom;
},'Must be greater than date from.');

jQuery.validator.addMethod("isValidRating", function (value, element) {
    var b = (value>=0),
        b1 = (value<=10);
    return b&&b1;
},'should be between 0 to 10.');

jQuery.validator.addMethod("isValidNumber", function (value, element) {
    return (value.match(/^\d\.\d{1,2}$/))||(value.match(/^\d$/));
},'is not valid rating');


$(function(){

        $("#directorForm").validate({
            rules:{
                firstName:{
                    required: true,
                    maxlength: 30
                },
                lastName:{
                    required: true,
                    maxlength: 30

                }
            },
            messages: {
                // firstName: {
                //     required: "Поле Имя обязательное для заполнения"
                // }
            }
        });

    $("#filter-form").validate({
        rules:{
            fromDate:{
                dateFormat:true,
                isValidDate: true,
                dateFromFuture: true
            },
            toDate:{
                dateFormat:true,
                isValidDate: true,
                greaterThan: "#fromDate"
            }
        }
    });

    $("#movieForm").validate({
        rules:{
            movieTitle:{
                required: true,
                maxlength: 60
            },
            releaseDate:{
                required: true,
                dateFormat:true,
                dateFromFuture: true,
                isValidDate: true
            },
            rating:{
                required: true,
                isValidRating: true,
                isValidNumber:true
            }
        }
    })

});
