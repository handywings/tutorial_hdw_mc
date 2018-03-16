
function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
}

//open alert message
function openAlert(type,title,detail){
	swal({
		title : title,
		text : detail,
		type : type,
		confirmButtonClass : 'btn btn-primary btn-lg',
		buttonsStyling : false
	});
}

//append comma
function numberWithCommas(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function convertTodateString(strDate){
	var month = "";
	if(strDate != ""){
		var strDateSplit = strDate.split("-");
		if(strDateSplit[1] == 1){
			month = "มกราคม";
		}else if(strDateSplit[1] == 2){
			month = "กุมภาพันธ์";
		}else if(strDateSplit[1] == 3){
			month = "มีนาคม";
		}else if(strDateSplit[1] == 4){
			month = "เมษายน";
		}else if(strDateSplit[1] == 5){
			month = "พฤษภาคม";
		}else if(strDateSplit[1] == 6){
			month = "มิถุนายน";
		}else if(strDateSplit[1] == 7){
			month = "กรกฎาคม";
		}else if(strDateSplit[1] == 8){
			month = "สิงหาคม";
		}else if(strDateSplit[1] == 9){
			month = "กันยายน";
		}else if(strDateSplit[1] == 10){
			month = "ตุลาคม";
		}else if(strDateSplit[1] == 11){
			month = "พฤศจิกายน";
		}else if(strDateSplit[1] == 12){
			month = "ธันวาคม";
		}
		var daySplit = strDateSplit[1].split("");;
		var day = daySplit[0];
		if(day == 0){
			day = daySplit[1];
		}else{
			day = strDateSplit[1];
		}
		return day+" "+month+" "+(parseInt(strDateSplit[2]) + 543);
	}
	return "";
}

	function isEmpty(str) {
		return str.replace(/^\s+|\s+$/g, '').length == 0;
	}
