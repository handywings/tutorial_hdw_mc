<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job6" class="job" style="display: none;">


	<div class="row">
		<div class="col-md-4">
			<b>รายละเอียดการย้ายจุด</b>
		</div>
	</div>
	<div class="row mb05">
		<div class="col-md-12 mb05">
			<div class="table-responsive">
				<table class="table table-bordered mb-0 table-hover">
					<thead class="thead-bg">
						<tr>
							<th width="20%"><center></center></th>
							<th width="20%"><center>จุดทั้งหมด</center></th>
							<th width="20%"><center>Digital</center></th>
							<th width="20%"><center>Analog</center></th>


						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="td-middle"><center>
									<b>จุดติดตั้ง</b>
								</center></td>
							<td><center><span id="c_mp_original_point_all" ></span> จุด</center></td>
							<td><center><span id="c_mp_original_point_degital" ></span> จุด</center></td>
							<td><center><span id="c_mp_original_point_analog" ></span> จุด</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>
									<b>จุดย้าย</b>
								</center></td>
							<td><center><span id="c_mp_new_point_all" >0</span> จุด</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_mp_new_point_degital" onblur="mp_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_mp_new_point_analog" onblur="mp_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>
	<div class="row">
		<div class="col-md-4">
			<b>ค่าบริการย้ายจุด</b>
		</div>
	</div>
	<div class="row mb05">
		<div class="col-md-12 mb05">
			<div class="table-responsive">
				<table class="table table-bordered mb-0 table-hover">
					<thead class="thead-bg">
						<tr>
							<th style="width: 80px;"><center>ลำดับ</center></th>
							<th><center>รายการ</center></th>
							<th style="width: 150px;"><center>จำนวนเงิน</center></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="td-middle"><center>1</center></td>
							<td>ค่าย้ายจุด</td>
							<td><center><div class="input-group">
										<input type="text" class="form-control" value="0" style="text-align: center;"
											id="c_mp_point_value" onblur="mp_calPointValue()" >
										<div class="input-group-addon">บาท</div>
									</div>
								</center>
							</td>
						</tr>

						<tr>
							<td class="td-middle" colspan="2" align="right"><b>รวมเป็นเงิน</b></td>
							<td><center>
									<b><span id="c_mp_total" >0</span> บาท</b>
								</center></td>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="row mt05">
		<div class="col-md-12">
			<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
			<textarea class="form-control" id="c_mp_remark" rows="3"></textarea>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var mp_price_move_point = 0;
var result_digitalPoint = 0;
var result_analogPoint = 0;
function saveMovePoint(){
	$('.preloader').modal('show');
	var movePointWorksheetBean = {};
	movePointWorksheetBean.digitalPoint = $('#c_mp_new_point_degital').val();
	movePointWorksheetBean.analogPoint = $('#c_mp_new_point_analog').val();
	movePointWorksheetBean.movePointPrice = $('#c_mp_point_value').val();
	movePointWorksheetBean.remark = $('#c_mp_remark').val();
	
	movePointWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	movePointWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	console.log(movePointWorksheetBean);
	
	//send save worksheet tune
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveMovePoint",
		dataType : 'json',
		async : false,
		data : JSON.stringify(movePointWorksheetBean),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				window.location.reload();
			}else{
				
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
function loadMovePoint(){
	var serviceApplicationBean = {};
	serviceApplicationBean.id = $('#idServiceApplication').val();
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/loadPointAll",
		dataType : 'json',
		async : false,
		data : JSON.stringify(serviceApplicationBean),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
					console.log(data);
				$('#c_mp_original_point_all').text(data["result"]["digitalPoint"]+data["result"]["analogPoint"]);
				$('#c_mp_original_point_degital').text(data["result"]["digitalPoint"]);
				$('#c_mp_original_point_analog').text(data["result"]["analogPoint"]);
				
				result_digitalPoint = data["result"]["digitalPoint"];
				result_analogPoint = data["result"]["analogPoint"];
				
				$(data["result"]["serviceProductBean"]).each(function(i,value) {
					if("00006"==value.productCode){
						mp_price_move_point = value.price;
					}
				});
				
			}else{
				
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

function mp_calPoint(){
	var c_mp_new_point_degital = $('#c_mp_new_point_degital').val();
	if(isNaN(parseInt(c_mp_new_point_degital))){
		$('#c_mp_new_point_degital').val('0');
		c_mp_new_point_degital = 0;
	}
	else if(c_mp_new_point_degital > result_digitalPoint){
		$('#c_mp_new_point_degital').val(result_digitalPoint);
		c_mp_new_point_degital = result_digitalPoint;
	}else{
		$('#c_mp_new_point_degital').val(parseInt(c_mp_new_point_degital));
	}
	
	var c_mp_new_point_analog = $('#c_mp_new_point_analog').val();
	if(isNaN(parseInt(c_mp_new_point_analog))){
		$('#c_mp_new_point_analog').val('0');
		c_mp_new_point_analog = 0;
	}
	else if(c_mp_new_point_analog > result_analogPoint){
		$('#c_mp_new_point_analog').val(result_analogPoint);
		c_mp_new_point_analog = result_analogPoint;
	}else{
		$('#c_mp_new_point_analog').val(parseInt(c_mp_new_point_analog));
	}
	
	$('#c_mp_new_point_all').text(parseInt(c_mp_new_point_degital)+parseInt(c_mp_new_point_analog));

	var c_mp_point_value = $('#c_mp_point_value'); // ค่าย้ายจุด
	
	
	var amount_digital = parseInt(c_mp_new_point_degital)*mp_price_move_point;
	var amount_analog = parseInt(c_mp_new_point_analog)*mp_price_move_point;

	c_mp_point_value.val(amount_digital+amount_analog);
	
	$('#c_mp_total').text(parseInt(c_mp_point_value.val()));
}

function mp_calPointValue(){
	
	var c_mp_new_point_degital = $('#c_mp_new_point_degital').val();
	var c_mp_new_point_analog = $('#c_mp_new_point_analog').val();
	var amount_digital = parseInt(c_mp_new_point_degital)*mp_price_move_point;
	var amount_analog = parseInt(c_mp_new_point_analog)*mp_price_move_point;
	
	var c_mp_point_value = $('#c_mp_point_value').val(); // ค่าย้ายจุด
	if(isNaN(parseInt(c_mp_point_value))){
		$('#c_mp_point_value').val(amount_digital+amount_analog);
		c_mp_point_value = amount_digital+amount_analog;
	}else{
		$('#c_mp_point_value').val(parseInt(c_mp_point_value));
	}
	
	$('#c_mp_total').text(parseInt(c_mp_point_value));
}

</script>