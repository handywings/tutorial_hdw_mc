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

<%-- 			<c:if test="${statusCurrentHistory ne 'H' && statusCurrentHistory ne ''}"> --%>
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
													<legend>&nbsp;&nbsp;&nbsp;รายละเอียดใบงานตัดสาย&nbsp;&nbsp;&nbsp;</legend>
													<div class="row">
														<div class="col-md-12">
															<input type="hidden" id="hiddenWorksheetCutId"
																value="${worksheetBean.cutWorksheetBean.id}" />
															<div class="row">
																<div class="col-md-6">
																	<div class="form-group">
																		<label for="exampleInputEmail1"><b>ผู้แจ้งตัดสาย</b></label>
																		<input type="text" class="form-control" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																			id="c_cu_reporter" value="${worksheetBean.cutWorksheetBean.reporter}">
																	</div>
																</div>
																<div class="col-md-6">
																	<div class="form-group">
																		<label for="exampleInputEmail1"><b>เบอร์โทรติดต่อ</b></label>
																		<input type="text" class="form-control" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																			id="c_cu_mobile" value="${worksheetBean.cutWorksheetBean.mobile}">
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-md-4">
																	<div class="form-group">
																		<label for="exampleInputEmail1"><b>วันที่แจ้งยกเลิก</b></label>
																		<div class="input-group">
																			<input type="text" class="datepickerCut form-control" value="${worksheetBean.cutWorksheetBean.cancelDate}" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																				id="datepickerCancel" placeholder="วัน-เดือน-ปี">
																		</div>
																	</div>
																</div>
																<div class="col-md-4">
																	<div class="form-group">
																		<label for="exampleInputEmail1"><b>วันที่สิ้นสุดการใช้บริการ
																				Package</b></label> 
																			<input type="text" class="datepickerCut form-control" value="${worksheetBean.cutWorksheetBean.endPackageDate}" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																				id="datepickerEndPackage" placeholder="วัน-เดือน-ปี">
																	</div>
																</div>
																<div class="col-md-4" style="display:none">
																	<div class="form-group">
																		<label for="exampleInputEmail1"><b>ส่วนลดพิเศษ</b></label>
																		<div class="input-group">
																			<input type="text" class="form-control" value="${worksheetBean.cutWorksheetBean.specialDiscount}" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																				id="specialDiscount" placeholder="0.00">
																			<div class="input-group-addon">บาท</div>
																		</div>
																	</div>
																</div>
															</div>
															<div class="row mb05">
																<div class="col-md-12">
																	<div>
																		<label for="jobType"><b>สาเหตุการตัดสาย</b></label> <select <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if>
																			class="form-control" id="c_cu_cause_cable_cut">
																			<option value="0">---เลือก ---</option>
																			<option value="1" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 1}">selected="selected"</c:if> >ไม่มีเวลาดู</option>
																			<option value="2" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 2}">selected="selected"</c:if> >ค้างชำระ</option>
																			<option value="3" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 3}">selected="selected"</c:if> >ติดอินเตอร์เน็ต</option>
																			<option value="4" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 4}">selected="selected"</c:if> >ติดจานที่อื่น</option>
																			<option value="5" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 5}">selected="selected"</c:if> >ย้ายบ้าน
																				(ไปในเขตที่สัญญาณไม่ครอบคลุม)</option>
																			<option value="6" <c:if test="${worksheetBean.cutWorksheetBean.cutWorkType == 6}">selected="selected"</c:if> >อื่นๆ</option>
																		</select>
																	</div>
																</div>
															</div>
															<div class="row mt05 pt05">
																<div class="col-md-12">
																	<label class="custom-control custom-checkbox ">
																		<input type="checkbox" id="checkBox_return_device" class="custom-control-input" <c:if test="${worksheetBean.cutWorksheetBean.returnEquipment}"> checked </c:if>  <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if> >
																		<span class="custom-control-indicator"></span> <span
																		class="custom-control-description">มีการคืนอุปกรณ์</span>
																	</label>
																</div>
															</div>
															<div class="row mb05" id="div_borrowed_equipment" <c:if test="${!worksheetBean.cutWorksheetBean.returnEquipment}">style="display: none;" </c:if> >
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
																			<c:set var="countNoSetup" value="1" scope="page" />
																			<c:set var="deposit" value="0" scope="page" />
																			<c:set var="amount" value="0" scope="page" />
																			<c:forEach items="${worksheetBean.serviceApplication.productitemList}" var="productItem" varStatus="i">
																				<c:forEach items="${productItem.productItemWorksheetBeanList}" var="productItemWorksheet" varStatus="j">
																				<c:choose>
																						<c:when test="${productItemWorksheet.lend}">
																							<tr>
																								<td><center><label class="custom-control custom-checkbox "> <input type="checkbox" value="${productItemWorksheet.id}" class="custom-control-input checkBox_return_device" <c:if test="${productItemWorksheet.returnEquipment}">checked</c:if> <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if> > <span class="custom-control-indicator"></span> <span class="custom-control-description"></span> </label></center>
																								<td>${productItem.product.productName} (${productItem.product.productCode}) / ${productItemWorksheet.equipmentProductItemBean.serialNo}</td>
																								<td><center>${productItemWorksheet.quantity} ${productItem.product.unit.unitName}</center></td>
																								<td><select class="form-control" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if> >
																										<option value="1" <c:if test="${productItemWorksheet.lendStatus == 1}">selected="selected"</c:if> >ปกติ</option>
																										<option value="0" <c:if test="${productItemWorksheet.lendStatus == 0}">selected="selected"</c:if>>ชำรุด</option>
																										<option value="5" <c:if test="${productItemWorksheet.lendStatus == 5}">selected="selected"</c:if>>หาย</option>
																										<option value="7" <c:if test="${productItemWorksheet.lendStatus == 7}">selected="selected"</c:if>>CA</option>
																									</select>
																								<td><center>${productItemWorksheet.deposit} บาท</center></td>
																								<td><center>${productItemWorksheet.amount} บาท</center></td>
																							</tr>
																							<c:set var="countNoSetup" value="${countNoSetup + 1}" scope="page" />
																							<c:set var="deposit" value="${deposit + productItemWorksheet.deposit}" scope="page" />
																							<c:set var="amount" value="${amount + productItemWorksheet.amount}" scope="page" />
																						</c:when>
																				</c:choose>
																				</c:forEach>
																			</c:forEach>
																			<c:if test="${countNoSetup > 1}">
																				<tr>
																					<td class="td-middle" colspan="4" align="right"><b>รวมเป็นเงิน</b></td>
																					<td><center>
																							<b><c:out value="${deposit}" /> บาท</b>
																						</center>
																					</td>
																					<td><center>
																							<b><c:out value="${amount}" /> บาท</b>
																						</center>
																					</td>
																				</tr>
																			</c:if>
																			</tbody>
																		</table>
																	</div>
																</div>
																<div class="col-md-12">
																	<div class="row">
																		<div class="col-md-12">
																			<label class="custom-control custom-checkbox ">
																				<input type="checkbox" id="checkBokxDepositAll" 
																				<c:if test="${worksheetBean.serviceApplication.flagRefund == true}">
																					checked
																				</c:if>
																				class="custom-control-input worksheetViewDisableDom">
																				<span class="custom-control-indicator"></span> <span
																				class="custom-control-description">คืนเงินมัดจำ จำนวน</span>
																			</label>&nbsp;
																			<div class="input-group">
																				<input type="number" id="depositAll" name="depositAll" 
																				class="form-control worksheetViewDisableDom" placeholder="0.00" value="${worksheetBean.serviceApplication.refund }">
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
																		<input type="checkbox" id="c_cu_ca" class="custom-control-input" <c:if test="${worksheetBean.cutWorksheetBean.submitCA}"> checked </c:if> <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if> >
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
																					<c:forEach items="${worksheetBean.productItemList}" var="productItem" varStatus="i">
																						<c:choose>
																						<c:when test="${productItem.type eq 'S'}">
																						<tr>
																							<td class="td-middle"><center><span class='template-no'>${i.count}</span></center></td>
																							<td><center>${productItem.serviceProductBean.productCode}<input type="hidden" class="productId" value="${productItem.serviceProductBean.id}"><input type="hidden" class="productType" value="${productItem.serviceProductBean.type}"></center></td>
																							<td>${productItem.serviceProductBean.productName}</td>
																							<td>${productItem.productCategoryName}</td>
																							<td><center><div class="input-group"><input type="number" min="1" value="${productItem.price}" class="form-control" <c:if test="${statusCurrentHistory ne 'R'}">disabled</c:if> ><div class="input-group-addon">บาท</div></div></center></td>
																							<td align="center" style="vertical-align: middle;">
																								<c:if test="${statusCurrentHistory eq 'R'}"><a class="run-swal cursor-p">
																								<span class="ti-trash remove-template-worksheet-productservice" data-toggle="tooltip"
																								data-placement="bottom" title="" data-original-title=""></span>
																								</a>
																								</c:if>
																							</td>
																						</tr>
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
													<button type="button" onclick="openModalSuccess('C_CU');"
														class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-check"></i></span>
														บันทึกใบงานสำเร็จ
													</button>
													<button type="button"
														onclick="openModalNotSuccess('C_CU');"
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
														<button type="button" onclick="openModalUpdate('C_CU');"
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
<%-- 			</c:if> --%>
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

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
	
	$(document).ready(function() {
		
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
		
		//set date picker
		$(".datepickerCut").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE'
		});
		
		$('.checkBox_return_device').click(function() {
			var idWorksheetItem = $(this).val();
			var typeCal = 0;
			var currentDeposit = $('#depositAll').val();
			
			if ($(this).is(':checked')) {
				typeCal = 1; //บวก
			} else {
				typeCal = 0; //ลบ
			}
			
			url = "${pageContext.request.contextPath}/worksheetadd/loadDepositItem/"+idWorksheetItem;
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
						if(typeCal == 1){
							$('#depositAll').val(parseFloat(currentDeposit) + data["result"]);
						}else if(typeCal == 0){
							$('#depositAll').val(parseFloat(currentDeposit) - data["result"]);
						}
					}
				}
			});
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
	
	$('#checkBox_return_device').click(function() {
		if ($(this).is(':checked')) {
			$("#div_borrowed_equipment").fadeIn();
		} else {
			$("#div_borrowed_equipment").hide();
		}
	});
	
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
//	 		console.log($(this));
			var templateNo = $(this).find('.template-no');
			// set No
			templateNo.text(index+1);
		});
	}

	function checkItemDuplicate(id,type){
		var isDuplicate = false;
		$.each($('#tableTemplate tbody tr'), function( index, value ) {
//	 		console.log(id + " " + type);
			var productId = $(this).find('.productId');
			var productType = $(this).find('.productType');
//	 		console.log(productId.val() + " " + productType.val());
			if(productId.val()==id && productType.val()==type){
				isDuplicate =  true;
			}
		});
		return isDuplicate;
	}
	
	$(document).on('click', '.remove-template-worksheet-productservice', function(event) {
		$(this).parent().parent().parent().remove();
		setNo();
	});
	
		function updateWorksheetCut(typeSave) {
			$('.preloader').modal('show');
			var snapHead = {};
			var worksheetUpdateSnapShotBean = setDataHeader(snapHead, typeSave);
			var cutWorksheetBean = {};
			cutWorksheetBean.id = $('#hiddenWorksheetCutId').val();
			cutWorksheetBean.reporter = $('#c_cu_reporter').val();
			cutWorksheetBean.mobile = $('#c_cu_mobile').val();
			cutWorksheetBean.cutWorkType = $('#c_cu_cause_cable_cut').val();
					
			cutWorksheetBean.returnEquipment = $('#checkBox_return_device').is(":checked");
			cutWorksheetBean.submitCA = $('#c_cu_ca').is(":checked");
			
			cutWorksheetBean.cancelDate = $('#datepickerCancel').val();
			cutWorksheetBean.endPackageDate = $('#datepickerEndPackage').val();
			cutWorksheetBean.specialDiscount = $('#specialDiscount').val();
			
			worksheetUpdateSnapShotBean.availableDateTime = $('#daterange1').val();
			
			//service application
			ServiceApplicationBean = {};
			
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
			
			 
			ServiceApplicationBean.id = "${worksheetBean.serviceApplication.id}";
			cutWorksheetBean.serviceApplication = ServiceApplicationBean;
			
			worksheetUpdateSnapShotBean.cutWorksheetBean = cutWorksheetBean;
			worksheetUpdateSnapShotBean.typeWorksheet = "C_CU";

			console.log(worksheetUpdateSnapShotBean);
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
								console.log(data);
// 								window.location.reload();
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