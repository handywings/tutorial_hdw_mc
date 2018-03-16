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
													<legend>&nbsp;&nbsp;&nbsp;รายละเอียดใบงานซ่อมสัญญาณ&nbsp;&nbsp;&nbsp;</legend>
													<div class="row">
														<div class="col-md-12">
															<input type="hidden" id="hiddenWorksheetRepairConnectionId"
																value="${worksheetBean.repairConnectionWorksheetBean.id}" />
															<div class="row mt05">
																<div class="col-md-12" id="c_repair_main_block">
																	<c:forEach items="${worksheetBean.repairConnectionWorksheetBean.repairMatchItemBeanList}" var="repairItem" varStatus="i">
																	<div class="row mt05">
																		<div class="col-md-12">
																			<fieldset>
																				<legend>&nbsp;&nbsp;&nbsp;อุปกรณ์ ลำดับที่
																					${i.count}&nbsp;&nbsp;&nbsp;</legend>
																				<div class="row mt05 mb05">
																					<div class="col-md-12">
																						<b>อุปกรณ์ที่ใช้งานในปัจจุบัน</b>
																						<div class="table-responsive">
																						<table class="table table-bordered mb-0 table-hover">
																							<thead class="thead-bg">
																								<tr>
																									<th><center>หมายเลข S/N</center></th>
																									<th><center>ชื่อเรียกอุปกรณ์</center></th>
																									<th><center>ประเภทอุปกรณ์</center></th>
																									<th><center>สถานะอุปกรณ์</center></th>
																									<th><center>สถานะการรับประกัน</center></th>
																								</tr>
																							</thead>
																							<tbody>
																									<tr role="row" class="odd">
																										<td align="center"><span><input
																												type="hidden"
																												id="hiddenOldItem${i.count}"
																												value="${repairItem.productItemWorksheetsId}">
																												${repairItem.equipmentProductItemOld.serialNo}
																										</span></td>
																										<td align="center"><span>${repairItem.equipmentProductItemOld.equipmentProductBean.productName}</span></td>
																										<td align="center"><span>${repairItem.equipmentProductItemOld.equipmentProductBean.equipmentTypeName}</span></td>
																										<td align="center"><span>${repairItem.equipmentProductItemOld.status}</span></td>
																										<td align="center"><span>
																												<c:choose>
																													<c:when
																														test="${repairItem.equipmentProductItemOld.expireGuarantee }">หมดประกัน</c:when>
																													<c:otherwise>ยังไม่หมดประกัน</c:otherwise>
																												</c:choose>
																										</span></td>
																									</tr>
																								</tbody>
																						</table>
																						</div>
																					</div>
																				</div>
																				<c:if test="${statusCurrentHistory eq 'R'}">
																				<div class="row mt05 pt05">
																					<div class="col-md-12">
																						<label
																							class="custom-control custom-checkbox checkbox_repair"><input
																							type="checkbox" id="checkbox_repair_${i.count}"
																								<c:if test="${repairItem.equipmentProductItemNew ne null }">
																								checked
																								</c:if>
																							onclick="checkBoxRepair(${i.count})"
																							class="custom-control-input"><span
																							class="custom-control-indicator"></span> <spanclass='custom-control-description'>แจ้งอุปกรณ์เสียต้องการแจ้งซ่อม</spanclass='custom-control-description'></label>
																						
																						<c:choose>
																							<c:when test="${repairItem.equipmentProductItemNew ne null }">
																								<button type="button"
																							onclick="openViewModalRepair(${repairItem.equipmentProductItemOld.equipmentProductBean.id},${i.count})"
																							class="btn btn-info label-left float-xs-right btn_repair"
																							id="btn_repair_${i.count}" style="display: block;">
																							<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
																						</button>
																							</c:when>
																							<c:otherwise>
																								<button type="button"
																							onclick="openViewModalRepair(${repairItem.equipmentProductItemOld.equipmentProductBean.id},${i.count})"
																							class="btn btn-info label-left float-xs-right btn_repair"
																							id="btn_repair_${i.count}" style="display: none;">
																							<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
																						</button>
																							</c:otherwise>
																						</c:choose>
																					</div>
																				</div>
																				</c:if>
																					<c:choose>
																						<c:when test="${repairItem.equipmentProductItemNew ne null }">
																							<div class="row mt05 mb05 div_repair"
																							id="div_repair_${i.count}" style="display: block;">
																							<div class="col-md-12">
																								<b>อุปกรณ์ที่ใช้งานทดแทน</b>
																								<div class="table-responsive">
																								<table class="table table-bordered mb-0 table-hover">
																									<thead class="thead-bg">
																										<tr>
																											<th><center>หมายเลข S/N</center></th>
																											<th><center>ชื่อเรียกอุปกรณ์</center></th>
																											<th><center>ประเภทอุปกรณ์</center></th>
																											<th><center>สถานะอุปกรณ์</center></th>
																											<th><center>สถานะการรับประกัน</center></th>
																										</tr>
																									</thead>
																									<tbody>
																										<tr role="row" class="odd">
																											<td align="center" class="tdSn${i.count}"><span><input
																													type="hidden"
																													class="c_repair_hidden_eItem_id" value="${repairItem.equipmentProductItemNew.id}_${i.count}">
																													${repairItem.equipmentProductItemNew.serialNo}
																													</span></td>
																											<td align="center" class="tdEn${i.count}"><span>${repairItem.equipmentProductItemNew.equipmentProductBean.productName}</span></td>
																											<td align="center" class="tdETn${i.count}"><span>${repairItem.equipmentProductItemNew.equipmentProductBean.equipmentTypeName}</span></td>
																											<td align="center" class="tdESt${i.count}"><span>${repairItem.equipmentProductItemNew.status}</span></td>
																											<td align="center" class="tdStn${i.count}"><span>
																												<c:choose>
																													<c:when test="${repairItem.equipmentProductItemNew.expireGuarantee }">หมดประกัน</c:when>
																													<c:otherwise>ยังไม่หมดประกัน</c:otherwise>
																												</c:choose>
																											</span></td>
																										</tr>
																									</tbody>
																								</table>
																							</div>
																							</div>
																						</div>
																						<div class="row reason_repair" id="reason_repair_${i.count}"
																							style="display: block;">
																							<div class="col-md-12">
																								<b>ปัญหาที่พบ</b>
																								<textarea class="form-control c_repair_textarea worksheetViewDisableDom" onchange="autoRepair()"
																									id="c_repair_remark_${i.count}" rows="3">${repairItem.comment}</textarea>
																							</div>
																						</div>
																						</c:when>
																						<c:otherwise>
																							<div class="row mt05 mb05 div_repair"
																								id="div_repair_${i.count}" style="display: none;">
																								<div class="col-md-12">
																									<b>อุปกรณ์ที่ใช้งานทดแทน</b>
																									<table class="table table-bordered mb-0 table-hover">
																										<thead class="thead-bg">
																											<tr>
																												<th><center>หมายเลข S/N</center></th>
																												<th><center>ชื่อเรียกอุปกรณ์</center></th>
																												<th><center>ประเภทอุปกรณ์</center></th>
																												<th><center>สถานะอุปกรณ์</center></th>
																												<th><center>สถานะการรับประกัน</center></th>
																											</tr>
																										</thead>
																										<tbody>
																											<tr role="row" class="odd">
																												<td align="center" class="tdSn${i.count}"><span></span></td>
																												<td align="center" class="tdEn${i.count}"><span></span></td>
																												<td align="center" class="tdETn${i.count}"><span></span></td>
																												<td align="center" class="tdESt${i.count}"><span></span></td>
																												<td align="center" class="tdStn${i.count}"><span></span></td>
																											</tr>
																										</tbody>
																									</table>
																								</div>
																							</div>
																							<div class="row reason_repair" id="reason_repair_${i.count}"
																								style="display: none;">
																								<div class="col-md-12">
																									<b>ปัญหาที่พบ</b>
																									<textarea class="form-control c_repair_textarea worksheetViewDisableDom" onchange="autoRepair()"
																										id="c_repair_remark_${i.count}" rows="3"></textarea>
																								</div>
																							</div>
																						</c:otherwise>
																					</c:choose>
																			</fieldset>
																		</div>
																	</div>
																	</c:forEach>
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
												<perm:permission object="M31.edit">
													<button type="button" onclick="openModalSuccess('C_RC');"
														class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-check"></i></span>
														บันทึกใบงานสำเร็จ
													</button>
													<button type="button"
														onclick="openModalNotSuccess('C_RC');"
														class="btn btn-warning btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><span class="ti-reload"></span></span>
														บันทึกใบงานไม่สำเร็จ
													</button>
												</perm:permission>
												<perm:permission object="M31.delete">
													<button type="button"
														onclick="openModalCloseWorksheet(${worksheetBean.idWorksheetParent });"
														class="btn btn-danger btn-rounded label-left b-a-0 waves-effect waves-light">
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
														<button type="button" onclick="openModalUpdate('C_RC');"
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
	<jsp:include page="../modal_search_equipment_repair.jsp"></jsp:include>
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

	$( document ).ready(function() {
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
	
	function checkBoxRepair(id) {
		if ($("#checkbox_repair_" + id).is(':checked')) {
			$("#btn_repair_" + id).fadeIn();
			$("#div_repair_" + id).fadeIn();
			$("#reason_repair_" + id).fadeIn();
			//autoRepair();
		} else {
			clearAppend(id);
			$("#btn_repair_" + id).hide();
			$("#div_repair_" + id).hide();
			$("#reason_repair_" + id).hide();
			autoRepair();
		}
	}
	
	function clearAppend(id){
		$(".tdSn"+id).html("");
		$(".tdEn"+id).html("");
		$(".tdETn"+id).html("");
		$(".tdStn"+id).html("");
		$(".tdESt"+id).html("");
		$("#c_repair_remark_" + id).val("");
	}
		function updateWorksheetRepairConnection(typeSave) {
			var snapHead = {};
			var worksheetUpdateSnapShotBean = setDataHeader(snapHead, typeSave);
			var repairConnectionWorksheetBean = {};
			repairConnectionWorksheetBean.id = $('#hiddenWorksheetRepairConnectionId').val();
					
			worksheetUpdateSnapShotBean.availableDateTime = $('#daterange1').val();
			
			worksheetUpdateSnapShotBean.repairConnectionWorksheetBean = repairConnectionWorksheetBean;
			worksheetUpdateSnapShotBean.typeWorksheet = "C_RC";

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
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/workSheetId/${worksheetBean.idWorksheetParent}/companyId/"+companyId,'_blank');
		}
		 
	</script>
</body>
</html>