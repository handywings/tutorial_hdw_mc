<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job8" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label for="exampleInputEmail1"><b>ผู้แจ้งตัดสาย</b></label> <input
					type="text" class="form-control" id="c_cu_reporter">
			</div>
		</div>
		<div class="col-md-6">
			<div class="form-group">
				<label for="exampleInputEmail1"><b>เบอร์โทรติดต่อ</b></label> <input
					type="text" class="form-control" id="c_cu_mobile">
			</div>
		</div>
	</div>
	<div class="row mt05">
		<div class="col-md-6">
			<div class="form-group">
				<label for="exampleInputEmail1"><b>วันที่แจ้งยกเลิก</b></label>
				<div class="input-group">
					<input type="text" class="datepickerCut form-control"
						id="datepickerCancel" placeholder="วัน-เดือน-ปี" >
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="form-group">
				<label for="exampleInputEmail1"><b>วันที่สิ้นสุดการใช้บริการ Package</b></label>
					<input type="text" class="datepickerCut form-control"
						id="datepickerEndPackage" placeholder="วัน-เดือน-ปี" >
			</div>
		</div>
		<div class="col-md-4" style="display:none">
			<div class="form-group">
				<label for="exampleInputEmail1"><b>ส่วนลดพิเศษ</b></label>
				<div class="input-group">
					<input type="text" class="form-control" value="" id="specialDiscount" placeholder="0.00">
					<div class="input-group-addon">บาท</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row mb05">
		<div class="col-md-12">
			<div>
				<label for="jobType"><b>สาเหตุการตัดสาย</b></label> 
				<select	class="form-control" id="c_cu_cause_cable_cut" >
					<option value="null" selected="selected">--- เลือก ---</option>
					<option value="1">ไม่มีเวลาดู</option>
					<option value="2">ค้างชำระ</option>
					<option value="3">ติดอินเตอร์เน็ต</option>
					<option value="4">ติดจานที่อื่น</option>
					<option value="5">ย้ายบ้าน (ไปในเขตที่สัญญาณไม่ครอบคลุม)</option>
					<option value="6">อื่นๆ</option>
				</select>
			</div>
		</div>
	</div>
	<div class="row mt05 pt05">
		<div class="col-md-12">
			<label class="custom-control custom-checkbox ">
				<input type="checkbox" id="checkBox_return_device" class="custom-control-input">
				<span class="custom-control-indicator"></span> <span
				class="custom-control-description">มีการคืนอุปกรณ์</span>
			</label>
		</div>
	</div>
	<script type="text/javascript">
	$('#checkBox_return_device').click(function() {
		if ($(this).is(':checked')) {
			$("#div_borrowed_equipment").fadeIn();
		} else {
			$("#div_borrowed_equipment").hide();
		}
	});
	</script>
	<div class="row mb05" id="div_borrowed_equipment" style="display: none;">
		<div class="col-md-12">
			<b>รายการอุปกรณ์ที่เช่ายืม</b>
		</div>
		<div class="col-md-12 mb05">
			<div class="table-responsive">
				<table class="table table-bordered mb-0 table-hover" id="table_borrowed_equipment">
					<thead class="thead-bg">
						<tr>
							<th class="td-middle" style=" width: 90px; "><center>คืนอุปกรณ์</center></th>
							<th><center>รายการ</center></th>
							<th style="width: 150px;"><center>จำนวนสินค้า</center></th>
							<th><center>สถานะอุปกรณ์</center></th>
							<th style="width: 150px;"><center>ค่ามัดจำ</center></th>
							<th style="width: 150px;"><center>จำนวนเงิน</center></th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-12">
					<label class="custom-control custom-checkbox ">
						<input type="checkbox" id="checkBokxDepositAll" checked class="custom-control-input">
						<span class="custom-control-indicator"></span> <span
						class="custom-control-description">คืนเงินมัดจำ จำนวน</span>
					</label>&nbsp;
					<div class="input-group">
						<input type="number" id="depositAll" name="depositAll" class="form-control" placeholder="0.00">
						<div class="input-group-addon">บาท</div>
					</div>
				</div>
			</div>
			<br />
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<label class="custom-control custom-checkbox ">
				<input type="checkbox" id="c_cu_ca" class="custom-control-input">
				<span class="custom-control-indicator"></span> <span
				class="custom-control-description">ส่งเรื่องเพื่อแจ้ง CA</span>
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>&nbsp;&nbsp;&nbsp;ค่าใช้จ่ายเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
				<div class="row">
					<div class="col-md-12">
						หากต้องการเพิ่มค่าใช้จ่ายสินค้าหรือบริการเพิ่มเติม
						กรุณาคลิกที่ปุ่ม "เลือกสินค้า / บริการ"
						<button type="button" onclick="openModalServiceWorkSheetCut()"
							class="btn btn-info label-left float-xs-right mr-0-5">
							<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
							/ บริการ
						</button>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-md-12">
					<div class="table-responsive">
						<table class="table table-bordered mb-0 table-hover" id="tableTemplate"
							style="">
							<thead class="thead-bg">
								<tr>
									<th width="50px;"><center>ลำดับ</center></th>
									<th><center>รหัสสินค้า</center></th>
									<th>ข้อมูลสินค้า / บริการ</th>
									<th><center>ประเภทสินค้า</center></th>
									<th style="width: 160px;"><center>ราคา</center></th>
									<th width="50px;"></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
	</div>
	<div class="row mt05">
		<div class="col-md-12">
			<b>บันทึกเพิ่มเติม</b>
			<textarea class="form-control" id="c_cu_remark" rows="3"></textarea>
		</div>
	</div>
</div>

<!-- dialog -->
<div class="modal fade" id="addService_worksheet_cut" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left"
	style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;รายการค่าแรง / ค่าบริการ
				</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า</center></th>
														<th style="vertical-align: middle;"><center>ชื่อเรียกสินค้า</center></th>
														<th style="vertical-align: middle;"><center>ค่าบริการ</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${productBeans}" var="productBean" varStatus="i">
													<tr>
														<td>
															<center>
																<label class="custom-control custom-radio"> <input
																	name="radioServiceProduct" type="radio"
																	value="${productBean.id}|${productBean.type}" class="custom-control-input">
																	<span class="custom-control-indicator"></span> <span
																	class="custom-control-description">&nbsp;</span>
																</label>
															</center>
														</td>
														<td><center>${i.count }</center></td>
														<td>${productBean.productCode }</td>
														<td>${productBean.productName }</td>
														<td style="text-align: right;">${productBean.price} บาท</td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="appendToBlockChild_worksheetCut();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>
<!-- dialog -->

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script src="<c:url value="/resources/assets/plugins/datepicker-th/js/bootstrap-datepicker-custom.js" />"></script>
<script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
<script type="text/javascript">
$(document).ready(function() {
	
	$(".datepickerCut").datepicker({
		autoclose: true,
		format: 'dd-mm-yyyy',
		todayHighlight:'TRUE',
		todayBtn: true,
		language : 'th',
		thaiyear : true
	});
	
}); //end ready

function openModalServiceWorkSheetCut(){
	$('#addService_worksheet_cut').modal('show');
}

function appendToBlockChild_worksheetCut(){
	var radioServiceProduct = $("input:radio[name ='radioServiceProduct']:checked").val();
	if(typeof radioServiceProduct != 'undefined'){
		console.log(radioServiceProduct);
		loadServiceProductWithId(radioServiceProduct);
	}
}

function loadServiceProductWithId(key){
	$('.preloader').modal('show');
	var seperate = key.split("|");
	url = "${pageContext.request.contextPath}/servicepackage/loadServiceProductWithId/"+seperate[0];
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : url,
		dataType : 'json',
		async: false,
		//timeout : 100000,
		success : function(data) {
			console.log(data);
			if(data["error"] == false){
				var isDuplicate = false;
				isDuplicate = checkItemDuplicate(data["result"]["id"],data["result"]["type"]);
				if(isDuplicate){
					setTimeout(function(){$('.preloader').modal('hide');}, 200);
					$('#addService_worksheet_cut').modal('hide');
					return false;
				}
				
				var rows = "";
				var startRow = "<tr>";
				var endRow = "</tr>";
				
				var columnOrderCount = "<td><center><span class='template-no'></span></center></td>";
				var columnProductCode = "<td><center>"+ data["result"]["productCode"]+"<input type='hidden' class='productId' value='"+data["result"]["id"]+"'><input type='hidden' class='productType' value='"+data["result"]["type"]+"'></center></td>";
				var columnProductName = "<td>"+ data["result"]["productName"] +"</td>";
				var columnProductCategory = "<td>"+ data["result"]["productCategory"].equipmentProductCategoryName +"</td>";
				var columnPrice = "<td><center><div class='input-group'><input type='number' min='1' value='"+data["result"]["price"]+"' class='form-control'><div class='input-group-addon'>บาท</div></div></center></td>";
				var columnAction  = '<td align="center" style="vertical-align: middle;">\
					<a class="run-swal cursor-p"><span\
					class="ti-trash remove-template-worksheet-productservice" data-toggle="tooltip"\
					data-placement="bottom" title=""\
					data-original-title="ลบ"></span></a>\
					</td>';
				
				rows = rows + startRow + columnOrderCount;
				rows = rows + columnProductCode + columnProductName + columnProductCategory + columnPrice;
				rows = rows + columnAction + endRow;
				
				$('#tableTemplate > tbody').append(rows);
				setNo();
				
			}
			setTimeout(function(){$('.preloader').modal('hide');}, 200);
			$('#addService_worksheet_cut').modal('hide');
		}
	});
}

$(document).on('click', '.remove-template-worksheet-productservice', function(event) {
	$(this).parent().parent().parent().remove();
	setNo();
});

function setNo(){
	
	$.each($('#tableTemplate tbody tr'), function( index, value ) {
// 		console.log($(this));
		var templateNo = $(this).find('.template-no');
		// set No
		templateNo.text(index+1);

	});
	
}

function checkItemDuplicate(id,type){
	var isDuplicate = false;
	$.each($('#tableTemplate tbody tr'), function( index, value ) {
// 		console.log(id + " " + type);
		var productId = $(this).find('.productId');
		var productType = $(this).find('.productType');
// 		console.log(productId.val() + " " + productType.val());
		if(productId.val()==id && productType.val()==type){
			isDuplicate =  true;
		}
	});
	return isDuplicate;
}

function saveCut(){

	var cutWorksheetBean = {};
	//service application
	ServiceApplicationBean = {};
	
	cutWorksheetBean.reporter = $('#c_cu_reporter').val();
	cutWorksheetBean.mobile = $('#c_cu_mobile').val();
	cutWorksheetBean.cutWorkType = $('#c_cu_cause_cable_cut').val();
	cutWorksheetBean.remark = $('#c_cu_remark').val();
	cutWorksheetBean.returnEquipment = $('#checkBox_return_device').is(":checked");
	cutWorksheetBean.submitCA = $('#c_cu_ca').is(":checked");

	cutWorksheetBean.cancelDate = $('#datepickerCancel').val();
	cutWorksheetBean.endPackageDate = $('#datepickerEndPackage').val();
	cutWorksheetBean.specialDiscount = $('#specialDiscount').val();
	
	cutWorksheetBean.availableDateTime = $('#daterange1').val();
	
	if($('#checkBox_return_device').is(":checked")){
		var productItemWorksheetBeanList = [];
		$('#table_borrowed_equipment > tbody  > tr').each(function() {
			var item = {};
			var isAdd = false;
			$(this).find('input').each(function (i) {
				switch (i) {
				case 0:
					if($(this).is(":checked")){
						item.returnEquipment = true;
						item.id = $(this).val();
						isAdd = true;
					}else{
						isAdd = false;
					}
					break;
				}
			});
			item.lendStatus = $(this).find('select').val();
			if(isAdd) productItemWorksheetBeanList.push(item);
		});
		cutWorksheetBean.productItemWorksheetBeanList = productItemWorksheetBeanList;
		
		if($('#checkBokxDepositAll').is(":checked")){
			ServiceApplicationBean.flagRefund = true;
			ServiceApplicationBean.refund = $('#depositAll').val();
		}else{
			ServiceApplicationBean.flagRefund = false;
			ServiceApplicationBean.refund = $('#depositAll').val();
		}
		
	}
	
	var productItemList = [];
	$('#tableTemplate > tbody  > tr').each(function() {
		var item = {};
		var isAdd = false;
		var serviceProductBean = {};
		$(this).find('input').each(function (i) {
			switch (i) {
			case 0:
				serviceProductBean.id = $(this).val();
				break;
			case 1:
				item.type = $(this).val();
				break;
			case 2:
				serviceProductBean.price = $(this).val();
				break;
			}
		});
		item.serviceProductBean = serviceProductBean;
		productItemList.push(item);
	});
	cutWorksheetBean.productItemList = productItemList;
	
	//service application
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	cutWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	console.log(cutWorksheetBean);
	
	//send save worksheet cut
	$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/saveCutcable",
		dataType : 'json',
		async : false,
		data : JSON.stringify(cutWorksheetBean),
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				window.location.href = "${pageContext.request.contextPath}/assignworksheet";
// 				window.location.reload();
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
function loadCut(){
	
}
</script>