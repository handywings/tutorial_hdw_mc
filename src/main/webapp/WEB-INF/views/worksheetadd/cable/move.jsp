<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job9" class="job" style="display: none;">
	<div class="row mt05">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>ย้ายไปยัง บ้านเลขที่</b></label> <input
				type="text" class="form-control" id="c_m_no">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>หมู่ที่</b></label> <input
				type="text" class="form-control" id="c_m_section">
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>อาคาร</b></label> <input
				type="text" class="form-control" id="c_m_building">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> <input
				type="text" class="form-control" id="c_m_room">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>ชั้นที่</b></label> <input
				type="text" class="form-control" id="c_m_floor">
		</div>
		<div class="col-md-5">
			<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> <input
				type="text" class="form-control" id="c_m_village">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> <input
				type="text" class="form-control" id="c_m_alley">
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>ถนน</b></label> <input
				type="text" class="form-control" id="c_m_road">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>จังหวัด</b></label> 
			<select class="form-control" id="c_m_province" >
				<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
				<c:forEach var="province" items="${provinces}" varStatus="i">
				<option value="${province.id}">${province.PROVINCE_NAME}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อำเภอ / เขต</b></label>
			<select class="form-control" id="c_m_amphur" >
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตำบล / แขวง</b></label>
			<select class="form-control" id="c_m_district" >
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>รหัสไปรณีย์</b></label> 
			<input class="form-control bg-form" type="text" id="c_m_postcode" >
		</div>
		<div class="col-md-8">
			<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label> 
			<input type="text" class="form-control" id="c_m_nearbyPlaces" >
		</div>
		<div class="col-md-4">
			<label for="exampleSelect1"><b>เขตชุมชน / โครงการที่ติดตั้ง</b></label><br>
			<select class="form-control" id="c_m_select_zone" data-plugin="select2" style="width: 100%">
				<option disabled="disabled" selected="selected">--- เลือก ---</option>
				<c:forEach var="zone" items="${zones}" varStatus="i">
				<option value="${zone.id}">${zone.zoneDetail} (${zone.zoneName})</option>
				</c:forEach>
			</select>
		</div>
	</div>	
	<div class="row">
		<div class="col-md-4">
			<b>รายละเอียดการย้ายสาย</b>
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
									<b>จุดติดตั้งเดิม</b>
								</center></td>
							<td><center><span id="c_m_original_point_all" ></span> จุด</center></td>
							<td><center><span id="c_m_original_point_degital" ></span> จุด</center></td>
							<td><center><span id="c_m_original_point_analog" ></span> จุด</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>
									<b>ย้ายสาย</b>
								</center></td>
							<td><center><span id="c_m_new_point_all" >0</span> จุด</center></td>
							<td><center>
									<div class="input-group">
										<input type="text" class="form-control" value="0"
											id="c_m_new_point_degital" onblur="move_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
							<td><center>
									<div class="input-group">
										<input type="text" class="form-control" value="0"
											id="c_m_new_point_analog" onblur="move_calPoint()" >
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
			<b>ค่าบริการย้ายสาย</b>
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
							<td class="td-middle">ค่าบริการย้ายสาย</td>
							<td>
								<center>
									<div class="input-group">
										<input type="text" class="form-control" value="0"
											id="c_m_point_value" onblur="move_calPointValue()" >
										<div class="input-group-addon">บาท</div>
									</div>
								</center>
							</td>
						</tr>

						<tr>
							<td class="td-middle" colspan="2" align="right"><b>รวมเป็นเงิน</b></td>
							<td><center>
									<b><span id="c_m_total" >0</span> บาท</b>
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
			<textarea class="form-control" id="c_m_remark" rows="3"></textarea>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var dataAddress = [];
var m_price_move_point = 0;
var m_result_digitalPoint = 0;
var m_result_analogPoint = 0;

//Begin Address
//Begin change Province
$('#c_m_province').change(function(){
	var id = $(this).val();
	$('.preloader').modal('show');
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
		dataType : 'json',
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				dataAddress = data;
				var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
				$.each(dataAddress.result.amphurBeans, function( index, value ) {
					option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
				});
				
				$('#c_m_amphur').html(option);
				$('#c_m_postcode').val('');
				setTimeout(function() {$('.preloader').modal('hide');}, 200);
			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
});
//End change Province

//Begin change Amphur
$('#c_m_amphur').change(function(){
	var id = $(this).val();
	$('.preloader').modal('show');
		if(dataAddress){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#c_m_postcode').val('');
				}
			});
			$('#c_m_district').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
		}
});
//End change Amphur
	//Begin change District
	$('#c_m_district').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
// 					console.log(data);
					if("00000"!=data.result.zip_CODE){
						$('#c_m_postcode').val(data.result.zip_CODE);
					}else{
						$('#c_m_postcode').val('');
					}
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	});
	//End change District
//End Address

function saveMove(){
	$('.preloader').modal('show');
	var moveWorksheetBean = {};
	moveWorksheetBean.digitalPoint = $('#c_m_new_point_degital').val();
	moveWorksheetBean.analogPoint = $('#c_m_new_point_analog').val();
	moveWorksheetBean.moveCablePrice = $('#c_m_point_value').val();
	moveWorksheetBean.remark = $('#c_m_remark').val();
	
	moveWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	moveWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	var addressList = [];
	var address = {};
	
	address.no = $('#c_m_no').val();
	address.section = $('#c_m_section').val();
	address.building = $('#c_m_building').val();
	address.room = $('#c_m_room').val();
	address.floor = $('#c_m_floor').val();
	address.village = $('#c_m_village').val();
	address.alley = $('#c_m_alley').val();
	address.road = $('#c_m_road').val();
	var provinceBean = {};
	provinceBean.id = $('#c_m_province').val();
	address.provinceBean = provinceBean;
	var amphurBean = {};
	amphurBean.id = $('#c_m_amphur').val();
	address.amphurBean = amphurBean;
	var districtBean = {};
	districtBean.id = $('#c_m_district').val();
	address.districtBean = districtBean;
	address.postcode = $('#c_m_postcode').val();
	address.nearbyPlaces = $('#c_m_nearbyPlaces').val();
	address.addressType = '6';
	
	var zone = {};
	zone.id = $('#c_m_select_zone').val();
	address.zoneBean = zone;
	
	addressList.push(address);
	
	moveWorksheetBean.addressList = addressList;
	
	console.log(moveWorksheetBean);
	
	//send save worksheet tune
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveMove",
		dataType : 'json',
		async : false,
		data : JSON.stringify(moveWorksheetBean),
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
	
function loadMove(){
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
				$('#c_m_original_point_all').text(data["result"]["digitalPoint"]+data["result"]["analogPoint"]);
				$('#c_m_original_point_degital').text(data["result"]["digitalPoint"]);
				$('#c_m_original_point_analog').text(data["result"]["analogPoint"]);
				
				m_result_digitalPoint = data["result"]["digitalPoint"];
				m_result_analogPoint = data["result"]["analogPoint"];
				
				$(data["result"]["serviceProductBean"]).each(function(i,value) {
					if("00008"==value.productCode){
						m_price_move_point = value.price;
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

function move_calPoint(){
	var c_m_new_point_degital = $('#c_m_new_point_degital').val();
	if(isNaN(parseInt(c_m_new_point_degital))){
		$('#c_m_new_point_degital').val('0');
		c_m_new_point_degital = 0;
	}
	else if(c_m_new_point_degital > m_result_digitalPoint){
		$('#c_m_new_point_degital').val(m_result_digitalPoint);
		c_m_new_point_degital = m_result_digitalPoint;
	}else{
		$('#c_m_new_point_degital').val(parseInt(c_m_new_point_degital));
	}
	
	var c_m_new_point_analog = $('#c_m_new_point_analog').val();
	if(isNaN(parseInt(c_m_new_point_analog))){
		$('#c_m_new_point_analog').val('0');
		c_m_new_point_analog = 0;
	}
	else if(c_m_new_point_analog > m_result_analogPoint){
		$('#c_m_new_point_analog').val(m_result_analogPoint);
		c_m_new_point_analog = m_result_analogPoint;
	}else{
		$('#c_m_new_point_analog').val(parseInt(c_m_new_point_analog));
	}
	
	$('#c_m_new_point_all').text(parseInt(c_m_new_point_degital)+parseInt(c_m_new_point_analog));

	var c_m_point_value = $('#c_m_point_value'); // ค่าย้ายจุด

	c_m_point_value.val(m_price_move_point);
	
	$('#c_m_total').text(parseInt(c_m_point_value.val()));
}

function move_calPointValue(){
	
	var c_m_point_value = $('#c_m_point_value').val(); // ค่าย้ายจุด
	if(isNaN(parseInt(c_m_point_value))){
		$('#c_m_point_value').val(m_price_move_point);
		c_m_point_value = m_price_move_point;
	}else{
		$('#c_m_point_value').val(parseInt(c_m_point_value));
	}
	
	$('#c_m_total').text(parseInt(c_m_point_value));
}

</script>