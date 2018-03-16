<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div id="job7" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-4">
			<b>รายละเอียดการลดจุด</b>
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
									<b>จุดเดิม</b>
								</center></td>
							<td><center><span id="c_rp_original_point_all" ></span> จุด</center></td>
							<td><center><span id="c_rp_original_point_degital" ></span> จุด</center></td>
							<td><center><span id="c_rp_original_point_analog" ></span> จุด</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>
									<b>ลดจุด</b>
								</center></td>
							<td><center><span id="c_rp_new_point_all" >0</span> จุด</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_rp_new_point_degital" onblur="rp_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
							<td><center>
									<div class="input-group">
										<input type="number" class="form-control" value="0" style="text-align: center;"
											id="c_rp_new_point_analog" onblur="rp_calPoint()" >
										<div class="input-group-addon">จุด</div>
									</div>
								</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>
									<b>คงเหลือ</b>
								</center></td>
							<td><center><span id="c_rp_balancel_all" ></span> จุด</center></td>
							<td><center><span id="c_rp_balance_degital" ></span> จุด</center></td>
							<td><center><span id="c_rp_balance_analog" ></span> จุด</center></td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>

	<div class="row mb05">
		<div class="col-md-12">
			<div>
				<label for="jobType"><b>ส่วนลดรายเดือน (นับจากบิลถัดไป)</b></label>
				<div class="input-group">
					<input id="c_rp_monthlyFree" type="number" min="1" value="0" class="form-control">
					<div class="input-group-addon">บาท</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row mt05">
		<div class="col-md-12">
			<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
			<textarea class="form-control" id="c_rp_remark" rows="3"></textarea>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var c_rp_original_point_all = 0;
var c_rp_original_point_degital = 0;
var c_rp_original_point_analog = 0;

function saveReducePoint(){
	$('.preloader').modal('show');
	var reducePointWorksheetBean = {};
	reducePointWorksheetBean.digitalPoint = $('#c_rp_new_point_degital').val();
	reducePointWorksheetBean.analogPoint = $('#c_rp_new_point_analog').val();
	reducePointWorksheetBean.monthlyFree = $('#c_rp_monthlyFree').val();
	reducePointWorksheetBean.remark = $('#c_rp_remark').val();
	
	reducePointWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	reducePointWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	console.log(reducePointWorksheetBean);
	
	//send save worksheet tune
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveReducePoint",
		dataType : 'json',
		async : false,
		data : JSON.stringify(reducePointWorksheetBean),
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
function loadReducePoint(){
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
				$('#c_rp_original_point_all').text(data["result"]["digitalPoint"]+data["result"]["analogPoint"]);
				$('#c_rp_original_point_degital').text(data["result"]["digitalPoint"]);
				$('#c_rp_original_point_analog').text(data["result"]["analogPoint"]);
				
				$('#c_rp_balancel_all').text(data["result"]["digitalPoint"]+data["result"]["analogPoint"]);
				$('#c_rp_balance_degital').text(data["result"]["digitalPoint"]);
				$('#c_rp_balance_analog').text(data["result"]["analogPoint"]);
				
				c_rp_original_point_all = data["result"]["digitalPoint"]+data["result"]["analogPoint"];
				c_rp_original_point_degital = data["result"]["digitalPoint"];
				c_rp_original_point_analog = data["result"]["analogPoint"];
				
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

function rp_calPoint(){
	var c_rp_new_point_degital = $('#c_rp_new_point_degital').val();
	if(isNaN(parseInt(c_rp_new_point_degital))){
		$('#c_rp_new_point_degital').val('0');
		c_rp_new_point_degital = 0;
	}
	else if(c_rp_new_point_degital > c_rp_original_point_degital){
		$('#c_rp_new_point_degital').val(c_rp_original_point_degital);
		c_rp_new_point_degital = c_rp_original_point_degital;
	}else{
		$('#c_rp_new_point_degital').val(parseInt(c_rp_new_point_degital));
	}
	
	var c_rp_new_point_analog = $('#c_rp_new_point_analog').val();
	if(isNaN(parseInt(c_rp_new_point_analog))){
		$('#c_rp_new_point_analog').val('0');
		c_rp_new_point_analog = 0;
	}
	else if(c_rp_new_point_analog > c_rp_original_point_analog){
		$('#c_rp_new_point_analog').val(c_rp_original_point_analog);
		c_rp_new_point_analog = c_rp_original_point_analog;
	}else{
		$('#c_rp_new_point_analog').val(parseInt(c_rp_new_point_analog));
	}
	
	$('#c_rp_new_point_all').text(parseInt(c_rp_new_point_degital)+parseInt(c_rp_new_point_analog));

	//คงเหลือ
	
	$('#c_rp_balancel_all').text(c_rp_original_point_all-(parseInt(c_rp_new_point_degital)+parseInt(c_rp_new_point_analog)));
	$('#c_rp_balance_degital').text(c_rp_original_point_degital-parseInt(c_rp_new_point_degital));
	$('#c_rp_balance_analog').text(c_rp_original_point_analog-parseInt(c_rp_new_point_analog));
	
}


</script>