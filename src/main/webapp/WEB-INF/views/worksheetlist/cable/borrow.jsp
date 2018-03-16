<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
													<legend>&nbsp;&nbsp;&nbsp;รายละเอียดใบงานแจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี&nbsp;&nbsp;&nbsp;</legend>
													<c:if test="${statusCurrentHistory eq 'R'}">
													<div class="row">
														<form action="borrow_adminlistproduct" method="post">
															<div class="col-md-4">
																<button type="button" class="btn btn-primary" 
																	onclick="openModalAddBorrow();">เลือกสินค้า</button>
															</div>
														</form>
													</div>
													<br />
													</c:if>
													<div class="row">
														<div class="col-md-12">
															<input type="hidden" id="hiddenWorksheetBorrowId"
																value="${worksheetBean.borrowWorksheetBean.id}" />
															<table class="table table-bordered mb-0 table-hover"
																id="c_borrow_tableEquipmentProduct">
																<thead class="thead-bg">
																	<tr>
																		<th><center>ลำดับ</center></th>
																		<th><center>หมายเลข S/N</center></th>
																		<th><center>ชื่อเรียกอุปกรณ์</center></th>
																		<th style="width: 150px;"><center>ราคาต้นทุน</center></th>
																		<th style="width: 150px;"><center>ราคาขาย</center></th>
																		<th style="width: 150px;"><center>ราคามัดจำ</center></th>
																		<c:if test="${statusCurrentHistory eq 'R'}"><th></th></c:if>
																	</tr>
																</thead>
																<tbody>
																<c:set var="countNoSetup" value="1" scope="page" />
																	<c:forEach items="${worksheetBean.productItemList}"
																		var="productItem" varStatus="i">
																		<c:choose>
																			<c:when test="${productItem.type eq 'E'}">
																				<c:forEach var="productWorkSheetItem" items="${productItem.productItemWorksheetBeanList}"
																					varStatus="j">
																					<c:if test="${productWorkSheetItem.productTypeMatch eq 'B'}">
																						<tr class="c_borrow_trNewItem">
																							<td class="td-middle"><center>
																									<span class="c_borrow_no-item"><c:out value="${countNoSetup}" /></span>
																									<c:set var="countNoSetup" value="${countNoSetup + 1}" scope="page" />
																								</center></td>
																							<td class="td-middle"><center>${productWorkSheetItem.equipmentProductItemBean.serialNo }</center></td>
																							<td class="td-middle">${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }</td>
																							<td class="td-middle"><center>
																							<input type="hidden" value="${productWorkSheetItem.price}" />
																							<fmt:formatNumber type="number" maxFractionDigits="2" value="${productWorkSheetItem.price}" />
																									บาท
																								</center></td>
																							<td class="td-middle"><div
																									class="input-group">
																									<input type="number" min="0"
																										class="form-control worksheetViewDisableDom borrowInput" value="${productWorkSheetItem.amount}"
																										id="exampleInputAmount">
																									<div class="input-group-addon">บาท</div>
																								</div></td>
																							<td class="td-middle"><div
																									class="input-group">
																									<input type="number" min="0"
																										class="form-control worksheetViewDisableDom borrowInput" value="${productWorkSheetItem.deposit}"
																										id="exampleInputAmount">
																									<div class="input-group-addon">บาท</div>
																								</div></td>
																							<c:if test="${statusCurrentHistory eq 'R'}">
																							<td align="center" class="td-middle"><a
																								class="run-swal cursor-p c_borrow_removeProductItem"><span
																									class="ti-trash" data-toggle="tooltip"
																									data-placement="bottom" title=""
																									data-original-title="ลบ"
																									aria-describedby="tooltip128894"></span></a> <input
																								class="c_borrow_hidden_equpment_product_item_id"
																								type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.id }"> <input
																								class="c_borrow_hidden_equpment_product_id"
																								type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.id }"></td>
																						</c:if>
																						</tr>
																					</c:if>
																				</c:forEach>
																			</c:when>
																		</c:choose>
																	</c:forEach>
																</tbody>
															</table>
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
													<button type="button" onclick="openModalSuccess('C_B');" class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-check"></i></span>
														บันทึกใบงานสำเร็จ
													</button>
													<button type="button" onclick="openModalNotSuccess('C_B');" class="btn btn-warning btn-rounded label-left b-a-0 waves-effect waves-light">
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
														<button type="button" onclick="openModalUpdate('C_B');"
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
	<jsp:include page="../modal_search_equipment_borrow.jsp"></jsp:include>
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
	
		function updateWorksheetBorrow(typeSave) {
			var snapHead = {};
			var worksheetUpdateSnapShotBean = setDataHeader(snapHead, typeSave);
			var borrowWorksheetBean = {};
			borrowWorksheetBean.id = $('#hiddenWorksheetBorrowId').val();
					
			worksheetUpdateSnapShotBean.availableDateTime = $('#daterange1').val();
			
			worksheetUpdateSnapShotBean.borrowWorksheetBean = borrowWorksheetBean;
			worksheetUpdateSnapShotBean.typeWorksheet = "C_B";

			//worksheetUpdateSnapShotBean.remark = $('#remark').val();
			//send save worksheet tune
			$
					.ajax({
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