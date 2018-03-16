$(document).ready(function(){

	/* =================================================================
		Color Picker
	================================================================= */
 	
	$('.colorpicker-default').colorpicker();

    $('.colorpicker-rgba').colorpicker({
        color: '#AA3399',
        format: 'rgba'
    });

	/* =================================================================
		Clock Picker
	================================================================= */
 	
	$('.clockpicker').clockpicker();

	var input = $('#single-input').clockpicker({
		placement: 'bottom',
		align: 'left',
		autoclose: true,
		'default': 'now'
	});

	$('#check-minutes').click(function(e){
		e.stopPropagation();
		input.clockpicker('show')
			.clockpicker('toggleView', 'minutes');
	});

	/* =================================================================
		Date Picker
	================================================================= */
 	
	$('.mydatepicker, #datepicker').datepicker();

	$('#datepicker-autoclose').datepicker({
		autoclose: true,
		todayHighlight: true
	});

	$('#datepicker-multiple').datepicker({
        format: "mm/dd/yyyy",
        clearBtn: true,
        multidate: true,
        multidateSeparator: ", "
	});

	$('#date-range').datepicker({
		toggleActive: true
	});

	$('#datepicker-inline').datepicker({
		todayHighlight: true
	});

	/* =================================================================
		Date Range Picker
	================================================================= */

	$('input[name="daterange"]').daterangepicker({
		buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-success',
        cancelClass: 'btn-inverse',
	});

	$('input[name="daterange-with-time"]').daterangepicker({
		timePicker: true,
		timePickerIncrement: 30,
		locale: {
			format: 'MM/DD/YYYY h:mm A'
		},
		buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-success',
        cancelClass: 'btn-inverse',
	});

   	var start = moment().subtract(29, 'days');
    var end = moment();

    function cb(start, end) {
        $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		$('#reportrange-stuff span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
    }

    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
        autoclose: true,
        ranges: {
           'วันนี้': [moment(), moment()],
           'เมื่อวาน': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'เดือนนี้': [moment().startOf('month'), moment().endOf('month')],
		   'ปีนี้': [moment().startOf('years'), moment().endOf('years')],
           '7 วันที่ผ่านมา': [moment().subtract(6, 'days'), moment()],
           '30 วันที่ผ่านมา': [moment().subtract(29, 'days'), moment()],
           'เดือนที่ผ่านมา': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
		buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-success',
        cancelClass: 'btn-inverse',
    }, cb);

	$('#reportrange-stuff').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
           'วันนี้': [moment(), moment()],
           'เมื่อวาน': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'เดือนนี้': [moment().startOf('month'), moment().endOf('month')],
		   'ปีนี้': [moment().startOf('years'), moment().endOf('years')],
           '7 วันที่ผ่านมา': [moment().subtract(6, 'days'), moment()],
           '30 วันที่ผ่านมา': [moment().subtract(29, 'days'), moment()],
           'เดือนที่ผ่านมา': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        },
		buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-success',
        cancelClass: 'btn-inverse',
    }, cb);

    cb(start, end);

});