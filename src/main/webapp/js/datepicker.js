$(document).ready(function () {

    // ================= DOB DATEPICKER =================

    var currentYear = new Date().getFullYear();
    var maxYear = currentYear - 18;

    $("#udate").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "1970:" + maxYear,
        minDate: new Date(1970, 0, 1),
        maxDate: new Date(maxYear, 11, 31)
    });

    // ================= FUTURE DATEPICKER =================

    var today = new Date();

    var maxDate = new Date(
        today.getFullYear(),
        today.getMonth() + 3,
        today.getDate()
    );

    $("#udatee").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        minDate: today,
        maxDate: maxDate,

        beforeShowDay: function (date) {

            // Disable Sunday

            if (date.getDay() === 0) {
                return [false];
            }

            return [true];
        }
    });

});