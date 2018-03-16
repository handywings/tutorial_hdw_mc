<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job2" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-4">
			<b>ค่าบริการ</b>
		</div>
	</div>
	<div class="row mb05">
		<div class="col-md-12 mb05">
			<div class="table-responsive">
				<table class="table table-bordered mb-0 table-hover">
					<thead class="thead-bg">
						<tr>
							<th><center>ลำดับ</center></th>
							<th><center>รายการ</center></th>
							<th width="200px;"><center>จำนวนเงิน</center></th>
						</tr>
					</thead>
					<tbody>						
						<tr>
							<td class="td-middle"><center>1</center></td>
							<td>บิลค้างชำระ (24 พฤศจิกายน 2559 - 24 ธันวาคม 2559)</td>
							<td><center>400.00 บาท</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>2</center></td>
							<td>ค่าบิลล่วงหน้า (24 ธันวาคม 2559 - 24 มกราคม 2560)</td>
							<td><center>400.00 บาท</center></td>
						</tr>
						<tr>
							<td class="td-middle"><center>3</center></td>
							<td>ค่าบริการจั้มสาย</td>
							<td>
								<center>
									<div class="input-group">
										<input type="hidden" id="c_c_serviceProductCode" /> 
										<input type="text" class="form-control" value="" id="c_c_connectPrice">
										<div class="input-group-addon">บาท</div>
									</div>
								</center>
							</td>
						</tr>
						<tr>
							<td class="td-middle" colspan="2" align="right"><b>รวมเป็นเงิน</b></td>
							<td><center>
									<b>1,150.00 บาท</b>
								</center></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
			<textarea class="form-control" id="c_connect_remark" rows="3"></textarea>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">

	function saveConnect(){
		$('.preloader').modal('show');
		var connectWorksheetBean = {};
		connectWorksheetBean.price = $('#c_c_connectPrice').val();
		connectWorksheetBean.remark = $('#c_connect_remark').val();
		
		connectWorksheetBean.availableDateTime = $('#daterange1').val();
		
		//service product
		ServiceProductBean = {};
		ServiceProductBean.productCode = $('#c_c_serviceProductCode').val();
		connectWorksheetBean.serviceProductBean = ServiceProductBean;
		
		//service application
		ServiceApplicationBean = {};
		ServiceApplicationBean.id = $('#idServiceApplication').val();
		connectWorksheetBean.serviceApplication = ServiceApplicationBean;
		
		//send save worksheet tune
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/saveConnect",
			dataType : 'json',
			async : false,
			data : JSON.stringify(connectWorksheetBean),
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
	
	function loadConnect(){
		var connectWorksheetBean = {};
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/loadConnect",
			dataType : 'json',
			async : false,
			data : JSON.stringify(connectWorksheetBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#c_c_connectPrice').val(data["result"]["price"]);
					$('#c_c_serviceProductCode').val(data["result"]["productCode"]);
					
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
</script>