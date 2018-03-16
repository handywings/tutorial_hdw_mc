<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job1" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-4">
			<b>รายละเอียดการเสริมจุด</b>
		</div>
	</div>
	<div class="row mb05">
		<div class="col-md-12 mb05">
			<div class="table-responsive">
				<table class="table table-bordered mb-0">
					<thead class="thead-bg table-hover">
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
									<b>จุดเดิม</b>
								</center></td>
							<td><center><span id="c_ap_original_point_all" ></span> จุด</center></td>
							<td><center><span id="c_ap_original_point_degital" ></span> จุด</center></td>
							<td><center><span id="c_ap_original_point_analog" ></span> จุด</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>
									<b>จุดเสริม</b>
								</center></td>
							<td><center><span id="c_ap_new_point_all" >0</span> จุด</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_ap_new_point_degital" onblur="ap_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_ap_new_point_analog" onblur="ap_calPoint()" >
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
			<b>ค่าบริการเสริมจุด</b>
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
							<td>ค่าเสริมจุด</td>
							<td><center>
									<div class="input-group">
										<input type="text" class="form-control" value="0"
											id="c_ap_point_value" onblur="ap_calPointValue()" >
										<div class="input-group-addon">บาท</div>
									</div>
								</center>
							</td>
						</tr>
						<tr>
							<td class="td-middle"><center>2</center></td>
							<td>รายบริการเพิ่มตามรอบบิล</td>
							<td>
								<center>
									<div class="input-group">
										<input type="text" class="form-control" value="0"
											id="c_ap_monthly_added" onblur="ap_calPointValue()" >
										<div class="input-group-addon">บาท</div>
									</div>
								</center>
							</td>
						</tr>


						<tr>
							<td class="td-middle" colspan="2" align="right"><b>รวมเป็นเงิน</b></td>
							<td><center>
									<b><span id="c_ap_total" >0</span> บาท</b>
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
			<textarea class="form-control" id="c_ap_remark" rows="3"></textarea>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var ap_price_digital = 0;
var ap_price_analog = 0;
	function saveAddPoint(){
		$('.preloader').modal('show');
		var addPointWorksheetBean = {};
		addPointWorksheetBean.digitalPoint = $('#c_ap_new_point_degital').val();
		addPointWorksheetBean.analogPoint = $('#c_ap_new_point_analog').val();
		addPointWorksheetBean.addPointPrice = $('#c_ap_point_value').val();
		addPointWorksheetBean.monthlyFree = $('#c_ap_monthly_added').val();
		addPointWorksheetBean.remark = $('#c_ap_remark').val();
		
		addPointWorksheetBean.availableDateTime = $('#daterange1').val();
		
		//service application
		ServiceApplicationBean = {};
		ServiceApplicationBean.id = $('#idServiceApplication').val();
		addPointWorksheetBean.serviceApplication = ServiceApplicationBean;
		
		console.log(addPointWorksheetBean);
		
		//send save worksheet tune
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/saveAddPoint",
			dataType : 'json',
			async : false,
			data : JSON.stringify(addPointWorksheetBean),
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
	function loadAddPoint(){
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
					$('#c_ap_original_point_all').text(data["result"]["digitalPoint"]+data["result"]["analogPoint"]);
					$('#c_ap_original_point_degital').text(data["result"]["digitalPoint"]);
					$('#c_ap_original_point_analog').text(data["result"]["analogPoint"]);
					
					$(data["result"]["serviceProductBean"]).each(function(i,value) {
						console.log(value);
						if("00004"==value.productCode){
							ap_price_digital = value.price;
						}
						if("00005"==value.productCode){
							ap_price_analog = value.price;
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
	
	
	
	function ap_calPoint(){
		var c_ap_new_point_degital = $('#c_ap_new_point_degital').val();
		if(isNaN(parseInt(c_ap_new_point_degital))){
			$('#c_ap_new_point_degital').val('0');
			c_ap_new_point_degital = 0;
		}else{
			$('#c_ap_new_point_degital').val(parseInt(c_ap_new_point_degital));
		}
		
		var c_ap_new_point_analog = $('#c_ap_new_point_analog').val();
		if(isNaN(parseInt(c_ap_new_point_analog))){
			$('#c_ap_new_point_analog').val('0');
			c_ap_new_point_analog = 0;
		}else{
			$('#c_ap_new_point_analog').val(parseInt(c_ap_new_point_analog));
		}
		
		$('#c_ap_new_point_all').text(parseInt(c_ap_new_point_degital)+parseInt(c_ap_new_point_analog));

		var c_ap_point_value = $('#c_ap_point_value'); // ค่าเสริมจุด
		var c_ap_monthly_added = $('#c_ap_monthly_added'); // รายเดือนเพิ่ม
		
		
		var amount_digital = parseInt(c_ap_new_point_degital)*ap_price_digital;
		var amount_analog = parseInt(c_ap_new_point_analog)*ap_price_analog;

		c_ap_point_value.val(amount_digital+amount_analog);
		
		$('#c_ap_total').text(parseInt(c_ap_point_value.val())+parseInt(c_ap_monthly_added.val()));
	}
	
	function ap_calPointValue(){
		
		var c_ap_new_point_degital = $('#c_ap_new_point_degital').val();
		var c_ap_new_point_analog = $('#c_ap_new_point_analog').val();
		var amount_digital = parseInt(c_ap_new_point_degital)*ap_price_digital;
		var amount_analog = parseInt(c_ap_new_point_analog)*ap_price_analog;
		
		var c_ap_point_value = $('#c_ap_point_value').val(); // ค่าเสริมจุด
		if(isNaN(parseInt(c_ap_point_value))){
			$('#c_ap_point_value').val(amount_digital+amount_analog);
			c_ap_point_value = amount_digital+amount_analog;
		}else{
			$('#c_ap_point_value').val(parseInt(c_ap_point_value));
		}
		
		var c_ap_monthly_added = $('#c_ap_monthly_added').val(); // รายเดือนเพิ่ม
		if(isNaN(parseInt(c_ap_monthly_added))){
			$('#c_ap_monthly_added').val('0');
			c_ap_monthly_added = 0;
		}else{
			$('#c_ap_monthly_added').val(parseInt(c_ap_monthly_added));
		}
		
		$('#c_ap_total').text(parseInt(c_ap_point_value)+parseInt(c_ap_monthly_added));
	}
	
</script>