<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="worksheet" scope="request"/>
<c:set var="subMenu" value="worksheetlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายละเอียดเลขที่ใบงาน ${worksheetBean.workSheetCode }</title>
<jsp:include page="../../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
	<div class="wrapper">
		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../../layout/menu-top.jsp"></jsp:include>
		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row mt05 mb05" >
						<div class="col-md-4">
							<h3>รายละเอียดเลขที่ใบงาน ${worksheetBean.workSheetCode }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/worksheetlist">รายการใบงานทั้งหมด</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบงาน</li>
							</ol>
						</div>
						<div class="col-md-8 mt05" align="right">
							<c:if test="${worksheetBean.status.stringValue ne 'W' }">
								<c:choose>
									<c:when
										test="${worksheetBean.status.stringValue eq 'R' || worksheetBean.status.stringValue eq 'H' || worksheetBean.status.stringValue eq 'W'}">
											<a href="javascript:void(0);" >
												<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
												 class="btn btn-info label-left float-xs-right mr-0-5">
													<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบแจ้งหนี้ / ใบรับเงินชั่วคราว
				 								</button>
				 							</a>
										<a href="${pageContext.request.contextPath}/worksheetreport/workSheet/exportPdf/workSheetId/${worksheetBean.idWorksheetParent}" target="_blank"><button type="button"
												class="btn btn-primary label-left float-xs-right mr-0-5 mb05">
												<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบงาน
											</button></a>
									</c:when>
									<c:when test="${worksheetBean.status.stringValue eq 'S' }">
										<c:if test="${worksheetBean.invoiceDocumentBean.status eq 'S' }">
											<a href="javascript:void(0);" >
												<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
												 class="btn btn-info label-left float-xs-right mr-0-5">
													<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบเสร็จรับเงิน
				 								</button>
				 							</a>
										</c:if>
										<c:if test="${worksheetBean.invoiceDocumentBean.status ne 'S' }">
											<a href="javascript:void(0);" >
												<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
												 class="btn btn-info label-left float-xs-right mr-0-5">
													<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบแจ้งหนี้ / ใบรับเงินชั่วคราว
				 								</button>
				 							</a>
										</c:if>
										<a href="${pageContext.request.contextPath}/worksheetreport/workSheet/exportPdf/workSheetId/${worksheetBean.idWorksheetParent}" target="_blank"><button type="button"
												class="btn btn-primary label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบงาน
											</button></a>
									</c:when>
									<c:when test="${worksheetBean.status.stringValue eq 'C' }">
										<a href="javascript:void(0);" >
											<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
											 class="btn btn-info label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบแจ้งหนี้ / ใบรับเงินชั่วคราว
			 								</button>
			 							</a>
									</c:when>
								</c:choose>
							</c:if>
						</div>

						<div class="modal fade" id="modalPrintInvoiceReceipt" tabindex="-1" role="dialog"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="exampleModalLabel">เลือกบริษัท</h4>
										</div>
										<div class="modal-body">
											<form>
												<div class="form-group">
													<select id="company_for_print" name="company" class="form-control" path="company">
														<c:forEach items="${companyList}" var="company">
														<option value="${company.id}" >${company.companyName}</option>
														</c:forEach>
													</select>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-info" data-dismiss="modal" onclick="printInvoiceReceipt();">ตกลง</button>
										</div>
									</div>
								</div>
							</div>

					</div>
					<div class="card mt05">

						<!-- header worksheet -->
						<jsp:include page="../header_worksheet.jsp"></jsp:include>
						<!-- End header worksheet -->
						<div class="row mt05">
							<div class="col-md-12">
								<fieldset>
									<legend>&nbsp;&nbsp;&nbsp;รอบบิล&nbsp;&nbsp;&nbsp;</legend>
									<div id="job1" class="job">
										<!-- current bill -->
										<jsp:include page="../current_bill_andvance.jsp"></jsp:include>
										<!-- End current bill -->
									</div>
								</fieldset>
							</div>
						</div>

						<!-- technician history -->
						<jsp:include page="../history_technician.jsp"></jsp:include>
						<!-- End technician history -->
					</div>
				</div>
			</div>

				<div class="card mt05">
					<div class="card-header text-uppercase">
						<div class="row">
							<div class="col-md-12">
								<h4>ผลการดำเนินงานของช่าง</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div id="job1" class="job">
								<div class="row">
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-12">
												<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;รายละเอียดใบงานย้ายสาย&nbsp;&nbsp;&nbsp;</legend>
													<div class="row">
														<div class="col-md-12">
															<input type="hidden" id="hiddenWorksheetMoveId"
																value="${worksheetBean.moveWorksheetBean.id}" />


															<div class="row mt05">
																<div class="col-md-2">
																	<label for="exampleInputEmail1"><b>ย้ายไปยัง
																			บ้านเลขที่</b></label> <input type="text" class="form-control worksheetViewDisableDom"
																		id="c_m_no" value="${worksheetBean.moveWorksheetBean.address.no}">
																</div>
																<div class="col-md-2">
																	<label for="exampleInputEmail1"><b>หมู่ที่</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom"
																		id="c_m_section" value="${worksheetBean.moveWorksheetBean.address.section}">
																</div>
																<div class="col-md-4">
																	<label for="exampleInputEmail1"><b>อาคาร</b></label> <input
																		type="text" class="form-control worksheetViewDisableDom" id="c_m_building" value="${worksheetBean.moveWorksheetBean.address.building}">
																</div>
																<div class="col-md-2">
																	<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom" id="c_m_room" value="${worksheetBean.moveWorksheetBean.address.room}">
																</div>
																<div class="col-md-2">
																	<label for="exampleInputEmail1"><b>ชั้นที่</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom" id="c_m_floor" value="${worksheetBean.moveWorksheetBean.address.floor}">
																</div>
																<div class="col-md-5">
																	<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom"
																		id="c_m_village" value="${worksheetBean.moveWorksheetBean.address.village}">
																</div>
																<div class="col-md-3">
																	<label for="exampleInputEmail1"><b>ตรอกซอย</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom" id="c_m_alley" value="${worksheetBean.moveWorksheetBean.address.alley}">
																</div>
																<div class="col-md-4">
																	<label for="exampleInputEmail1"><b>ถนน</b></label> <input
																		type="text" class="form-control worksheetViewDisableDom" id="c_m_road" value="${worksheetBean.moveWorksheetBean.address.road}">
																</div>
																<div class="col-md-3">
																	<label for="exampleInputEmail1"><b>จังหวัด</b></label>
																	<select class="form-control worksheetViewDisableDom" id="c_m_province">
																		<option disabled="disabled" selected="selected">---
																			กรุณาเลือกจังหวัด ---</option>
																		<c:forEach var="province" items="${provinces}"
																			varStatus="i">
																			<option value="${province.id}" <c:if test="${worksheetBean.moveWorksheetBean.address.provinceBean.id == province.id}">selected="selected"</c:if> >${province.PROVINCE_NAME}</option>
																		</c:forEach>
																	</select>
																</div>
																<div class="col-md-3">
																	<label for="exampleInputEmail1"><b>อำเภอ /
																			เขต</b></label> <select class="form-control worksheetViewDisableDom" id="c_m_amphur">
																		<option value="" selected="selected"
																			disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
																	</select>
																</div>
																<div class="col-md-3">
																	<label for="exampleInputEmail1"><b>ตำบล /
																			แขวง</b></label> <select class="form-control worksheetViewDisableDom" id="c_m_district">
																		<option value="" selected="selected"
																			disabled="disabled">--- กรุณาเลือกตำบล ---</option>
																	</select>
																</div>
																<div class="col-md-3">
																	<label for="exampleInputEmail1"><b>รหัสไปรณีย์</b></label>
																	<input class="form-control bg-form worksheetViewDisableDom" type="text" value="${worksheetBean.moveWorksheetBean.address.postcode}"
																		id="c_m_postcode">
																</div>
																<div class="col-md-8">
																	<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
																	<input type="text" class="form-control worksheetViewDisableDom" value="${worksheetBean.moveWorksheetBean.address.nearbyPlaces}"
																		id="c_m_nearbyPlaces">
																</div>
																<div class="col-md-4">
																	<label for="exampleSelect1"><b>เขตชุมชน /
																			โครงการที่ติดตั้ง</b></label> <select class="form-control worksheetViewDisableDom"
																		id="c_m_select_zone" data-plugin="select2">
																		<option disabled="disabled" selected="selected">---
																			เลือก ---</option>
																		<c:forEach var="zone" items="${zones}" varStatus="i">
																			<option value="${zone.id}" <c:if test="${worksheetBean.moveWorksheetBean.address.zoneBean.id == zone.id}">selected="selected"</c:if> >
																			${zone.zoneDetail}(${zone.zoneName})</option>
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
																					<td><center>
																							<span id="c_m_original_point_all"></span> จุด
																						</center></td>
																					<td><center>
																							<span id="c_m_original_point_degital"></span>
																							จุด
																						</center></td>
																					<td><center>
																							<span id="c_m_original_point_analog"></span> จุด
																						</center></td>
																				</tr>
																				<tr>
																					<td class="td-middle"><center>
																							<b>ย้ายสาย</b>
																						</center></td>
																					<td><center>
																							<span id="c_m_new_point_all">0</span> จุด
																						</center></td>
																					<td><center>
																							<div class="input-group">
																								<input type="text" class="form-control worksheetViewDisableDom"
																									value="${worksheetBean.moveWorksheetBean.digitalPoint}" id="c_m_new_point_degital"
																									onblur="move_calPoint()">
																								<div class="input-group-addon">จุด</div>
																							</div>
																						</center></td>
																					<td><center>
																							<div class="input-group">
																								<input type="text" class="form-control worksheetViewDisableDom"
																									value="${worksheetBean.moveWorksheetBean.analogPoint}" id="c_m_new_point_analog"
																									onblur="move_calPoint()">
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
																								<input type="text" class="form-control worksheetViewDisableDom"
																									value="${worksheetBean.moveWorksheetBean.moveCablePrice}" id="c_m_point_value"
																									onblur="move_calPointValue()">
																								<div class="input-group-addon">บาท</div>
																							</div>
																						</center>
																					</td>
																				</tr>

																				<tr>
																					<td class="td-middle" colspan="2" align="right"><b>รวมเป็นเงิน</b></td>
																					<td><center>
																							<b><span id="c_m_total">${worksheetBean.moveWorksheetBean.moveCablePrice}</span> บาท</b>
																						</center></td>
																				</tr>

																			</tbody>
																		</table>
																	</div>
																</div>
															</div>


														</div>
													</div>
												</fieldset>
											</div>
										</div>
										<div id="successTask">
											<jsp:include page="../table_sub_worksheet.jsp"></jsp:include>
											<c:if test="${worksheetBean.workSheetType ne 'C_S'}">
												<jsp:include page="../tableAddEquipmentProduct.jsp"></jsp:include>
											</c:if>
										</div>
										<br />
										<jsp:include page="../tableMember.jsp"></jsp:include>
										<br />
										 <div class="row" id="failedTask">
													<div class="col-md-12">
														<b>หมายเหตุ / ข้อมูลเพิ่มเติม</b>
														<textarea class="form-control worksheetViewDisableDom"
														id="remark" rows="3">${worksheetBean.remark}</textarea>
													</div>
												</div>
												<div class="row" >
													<div class="col-md-12">
														<b>หมายเหตุ / ข้อมูลเพิ่มเติม (ใบงานสำเร็จ)</b>
														<textarea class="form-control worksheetViewDisableDom"
														id="remarkSuccess" rows="3">${worksheetBean.remarkSuccess}</textarea>
													</div>
												</div>
									</div>
								</div>
								<c:if test="${statusCurrentHistory eq 'R' && worksheetBean.status.stringValue ne 'D'}">
									<div class="row mt15 mb15" id="btnSave">
										<div class="col-md-12">
											<center>
												<perm:permission object="M31.edit" >
													<button type="button" onclick="openModalSuccess('C_M');" class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-check"></i></span>
														บันทึกใบงานสำเร็จ
													</button>
													<button type="button" onclick="openModalNotSuccess('C_M');" class="btn btn-warning btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><span class="ti-reload"></span></span>
														บันทึกใบงานไม่สำเร็จ
													</button>
													</perm:permission>
													
													<perm:permission object="M31.delete" >
													<button type="button" onclick="openModalCloseWorksheet(${worksheetBean.idWorksheetParent });" class="btn btn-danger btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-close"></i></span>
														ยกเลิกใบงาน
													</button>
													</perm:permission>
												</center>											
										</div>
									</div>
								</c:if>
								<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' && worksheetBean.status.stringValue ne 'R' }">
										<div class="row mt15 mb15">
											<div class="col-md-12">
												<center>
													<perm:permission object="M31.edit">
														<button type="button" onclick="openModalUpdate('C_M');"
															class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
															<span class="btn-label"><i class="ti-check"></i></span>
															บันทึกใบงาน
														</button>
													</perm:permission>
													<perm:permission object="M31.delete" >	
															<button type="button" onclick="openModalCloseWorksheet(${worksheetBean.idWorksheetParent });" class="btn btn-danger btn-rounded label-left b-a-0 waves-effect waves-light">
																<span class="btn-label"><i class="ti-close"></i></span>
																ยกเลิกใบงาน
															</button>
													</perm:permission>
												</center>										
											</div>
										</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>

		</div>
	</div>
	</div>
	<jsp:include page="../modal_sub_worksheet.jsp"></jsp:include>
	<jsp:include page="../modal_add_personnel_team.jsp"></jsp:include>
	<jsp:include page="../modal_search_equipment.jsp"></jsp:include>
	<jsp:include page="../modal_worksheet_success.jsp"></jsp:include>
	<jsp:include page="../modal_worksheet_not_success.jsp"></jsp:include>
	<jsp:include page="../modal_worksheet_update.jsp"></jsp:include>
	<!-- Footer -->
	<jsp:include page="../../layout/footer.jsp"></jsp:include>
	</div>
	</div>
	<jsp:include page="../../layout/script.jsp"></jsp:include>

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
	var dataAddress = [];
	var m_price_move_point = 0;
	var m_result_digitalPoint = 0;
	var m_result_analogPoint = 0;
	
	$( document ).ready(function() {
		loadMove();
		$('input[name="daterange1"]').daterangepicker({
			timePicker: true,
			timePicker24Hour: true,
//	 		timePickerIncrement: 30,
			locale: {
				format: 'DD/MM/YYYY HH:mm'
			},
			startDate: '"${worksheetBean.startDate}"',
		    endDate: '"${worksheetBean.endDate}"'
		});
	});
	
	changeProvince("${worksheetBean.moveWorksheetBean.address.provinceBean.id}","${worksheetBean.moveWorksheetBean.address.amphurBean.id}","${worksheetBean.moveWorksheetBean.address.districtBean.id}");
	
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
			success : function(data) {
				if(data["error"] == false){
					dataAddress1 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress1.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#c_m_amphur').html(option);
					$('#c_m_postcode').val('');

					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#c_m_amphur').val("");
					changeAmphur("","");
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
	
	function changeProvince(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			success : function(data) {
				if(data["error"] == false){
					dataAddress1 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress1.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#c_m_amphur').html(option);

					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#c_m_amphur').val(amphurId);
					changeAmphur(amphurId,districtId);
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
	//End change Province

	//Begin change Amphur
	$('#c_m_amphur').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress1){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress1.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#c_m_postcode').val('');
				}
			});
			$('#c_m_district').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#c_m_district').val("");
		}
	});
	//End change Amphur
	function changeAmphur(id,districtId) {
		if(dataAddress1){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress1.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#c_m_district').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#c_m_district').val(districtId);
		}
	}
	
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
					console.log(data);
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
	
		function updateWorksheetMove(typeSave) {
			var snapHead = {};
			var worksheetUpdateSnapShotBean = setDataHeader(snapHead, typeSave);
			var moveWorksheetBean = {};
			moveWorksheetBean.id = $('#hiddenWorksheetMoveId').val();
			moveWorksheetBean.digitalPoint = $('#c_m_new_point_degital').val();
			moveWorksheetBean.analogPoint = $('#c_m_new_point_analog').val();
			moveWorksheetBean.moveCablePrice = $('#c_m_point_value').val();
			
			worksheetUpdateSnapShotBean.availableDateTime = $('#daterange1').val();
			
			//service application
			ServiceApplicationBean = {};
			ServiceApplicationBean.id = "${worksheetBean.serviceApplication.id}";
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
			
			worksheetUpdateSnapShotBean.moveWorksheetBean = moveWorksheetBean;
			worksheetUpdateSnapShotBean.typeWorksheet = "C_M";

			console.log(moveWorksheetBean);
			//worksheetUpdateSnapShotBean.remark = $('#remark').val();
			//send save worksheet tune
			$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/worksheetlist/updateSnapshotWorksheet",
						dataType : 'json',
						async : false,
						data : JSON.stringify(worksheetUpdateSnapShotBean),
						//timeout : 100000,
						success : function(data) {
							if (data["error"] == false) {
								if(typeSave == 'S'){
									window.location.href = "${pageContext.request.contextPath}/invoice/payment/"+data["result"];
								}else{
									window.location.reload();
								}
							} else {

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
			serviceApplicationBean.id = "${worksheetBean.serviceApplication.id}";
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

		function addJobDetails(){
			$('.preloader').modal('show');
			var worksheetUpdateSnapShotBean = {};
			worksheetUpdateSnapShotBean.idWorksheetParent =  $('#hiddenCurrentWorksheetId').val();
			worksheetUpdateSnapShotBean.jobDetails = $('#jobDetails').val();
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/worksheetlist/updateJobDetails",
				dataType : 'json',
				async : false,
				data : JSON.stringify(worksheetUpdateSnapShotBean),
				//timeout : 100000,
				success : function(data) {
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
				},
				error : function(e) {
					console.log("ERROR: ", e);
					$('.preloader').modal('hide');
				},
				done : function(e) {
					console.log("DONE");
					$('.preloader').modal('hide');
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
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/workSheetId/${worksheetBean.idWorksheetParent}/companyId/"+companyId,'_blank');
		}
		
	</script>
</body>
</html>