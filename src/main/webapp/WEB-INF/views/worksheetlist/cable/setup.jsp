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
							<h3>รายละเอียดเลขที่ใบงาน  ${worksheetBean.workSheetCode }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/worksheetlist" >รายการใบงานทั้งหมด</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบงาน</li>
							</ol>
						</div>
						
						<c:if test="${worksheetBean.createBy ne 'Migrate'}">
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
						</c:if>
						
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
															<legend>&nbsp;&nbsp;&nbsp;วัสดุ/อุปกรณ์ ที่ใช้ในการติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
												<c:if test="${worksheetBean.status.stringValue ne 'S'}">
												<div class="row">
													<div class="col-md-10">หากต้องการเพิ่มวัสดุ/อุปกรณ์ ที่ใช้ในการติดตั้ง เพิ่มเติม คลิกที่ปุ่ม <b>"ค้นหาสินค้า"</b> เพื่อเพิ่มวัสดุ/อุปกรณ์</div>
													<div class="col-md-2" align="right">
														<button type="button" onclick="openModalSearchEquipment_setup();" class="btn-block btn btn-info label-left float-xs-right mr-0-5 btn-xs">
															<span class="btn-label"><i class="ti-plus"></i></span>ค้นหาสินค้า
														</button>
													</div>
												</div>
												</c:if>
												<div class="row mt15 mb15">
													<div class="col-md-12">
													<div class="table-responsive">
														<table id="tableEquipmentProductSetup" class="table table-bordered mb-0 table-hover">
															<thead class="thead-bg">
																<tr>
																	<th style="vertical-align: middle; width: 20px;">ลำดับ</th>
																	<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
																	<th style="vertical-align: middle;"><center>S/N</center></th>
																	<th style="vertical-align: middle; width: 150px;"><center>จำนวนที่เบิก</center>
																	</th>
																	<th style="vertical-align: middle;"><center>ฟรี</center></th>
																	<th style="vertical-align: middle;"><center>ยืม</center></th>
																	<th style="vertical-align: middle; width: 150px;"><center>ราคาขาย</center></th>
																	<th style="vertical-align: middle; width: 150px;"><center>ราคารวม</center></th>
																	<c:if test="${worksheetBean.status.stringValue ne 'S'}">
																		<th style="vertical-align: middle; width: 30px;"></th>
																	</c:if>
																</tr>
															</thead>
															<tbody>
																 <c:set var="countNoSetup" value="1" scope="page" />
																 <c:forEach var="productItemOld" items="${worksheetBean.productItemList}" varStatus="i">
																 	<c:choose>
																 		<c:when test="${productItemOld.type eq 'E'}">
																 			<c:forEach var="productWorkSheetItem" items="${productItemOld.productItemWorksheetBeanList}" varStatus="j">
																			<tr class="trNewItem">
																				<td>
																					<center>
																						<span class="no-item-setup"><c:out value="${countNoSetup}" /></span>
																						<c:set var="countNoSetup" value="${countNoSetup + 1}" scope="page" />
																						<input type="hidden" value="${productItemOld.type }" />
																					</center>
																				</td>
																				<td>
																					${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }
																					<br><font style="color: gray;"><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.id }" target="_blank">${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productCode }</a></font>
																					<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																					<br><small class="text-gray"><b><span class="ti-file"></span>&nbsp;เบิกจากใบเบิก&nbsp;<a target="_blank" href="${pageContext.request.contextPath}/requisitionlist/view/${productWorkSheetItem.requisitionItemBean.requisitionDocumentBean.id}">#${productWorkSheetItem.requisitionItemBean.requisitionDocumentBean.requisitionDocumentCode}</a><br><span class="ti-user"></span>&nbsp;${productWorkSheetItem.requisitionItemBean.personnelBean.firstName}&nbsp;${productWorkSheetItem.requisitionItemBean.personnelBean.lastName}&nbsp;(ผู้เบิก)</b></small>
																					</c:if>
																				</td>
																				<td>
																					 <center>
																						<a href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${productWorkSheetItem.equipmentProductItemBean.id }" target="_blank">${productWorkSheetItem.equipmentProductItemBean.serialNo }</a>
																					 </center>
																				</td>
																				<c:set var="quantity" value="${productWorkSheetItem.quantity }" scope="page" />
																				<c:if test="${not empty productWorkSheetItem.equipmentProductItemBean.serialNo}">
																					<c:set var="quantity" value="1" scope="page" />
																				</c:if>
																				
																				<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																					<c:set var="quantityMax" value="${productWorkSheetItem.requisitionItemBean.quantity}" scope="page" />
																				</c:if>
																				<c:if test="${empty productWorkSheetItem.requisitionItemBean}">
																					<c:set var="quantityMax" value="${productWorkSheetItem.equipmentProductItemBean.balance + productWorkSheetItem.quantity}" scope="page" />
																				</c:if>
																				
																				<td style="vertical-align: middle; width: 80px;">
																					<div class="input-group">
																					<input type="number" value="${quantity}" min="1" max="${quantityMax}" class="form-control worksheetViewDisableDom numberReqNotSN quantity" <c:if test="${not empty productWorkSheetItem.equipmentProductItemBean.serialNo}">disabled</c:if> />
																					<div class="input-group-addon">${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</div>
																					 </div>
																					 
																					<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																						<small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;${quantityMax}&nbsp;${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</b></small>
																					</c:if>
																					<c:if test="${empty productWorkSheetItem.requisitionItemBean}">
																						<small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;${quantityMax}&nbsp;${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</b></small>
																					</c:if>
																					 
																					 
																				</td>
																				<td><center>
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="${productWorkSheetItem.price }"
																						<c:if test="${productWorkSheetItem.free }">checked</c:if> >
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td><center <c:if test="${empty productWorkSheetItem.equipmentProductItemBean.serialNo}"> style="display: none;" </c:if>>
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend"
																						<c:if test="${productWorkSheetItem.lend }">checked</c:if> <c:if test="${empty productWorkSheetItem.equipmentProductItemBean.serialNo}">disabled</c:if> >
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td>
																					<input type="number" value="${productWorkSheetItem.price }" class="form-control worksheetViewDisableDom salePrice"/>
																				</td>
																				<td>
																					<div class="input-group">
																					<input type="number" value="${quantity * productWorkSheetItem.price}" disabled class="form-control worksheetViewDisableDom totalPrice"/>
																					
																					 </div>
																				</td>
																				<c:if test="${worksheetBean.status.stringValue ne 'S'}">
																					<td>
																						
																							<a class="run-swal cursor-p removeProductItemSetup">
																							<span class="ti-trash" data-toggle="tooltip" 
																							data-placement="bottom" title="" 
																							aria-describedby="tooltip128894"></span></a>
																							<input type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.id }" />
																							<input type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.id }" />
																							<input type="hidden" class="requesterId" value="${productWorkSheetItem.requisitionItemBean.id}">
																					</td>
																				</c:if>
																			</tr>
																		</c:forEach>
																 		</c:when>
																 		<c:when test="${productItemOld.type eq 'S'}">
																 			<tr class="trNewItem">
																			<td>
																				<center>
																					<span class="no-item-setup"><c:out value="${countNoSetup}" /></span>
																					<c:set var="countNoSetup" value="${countNoSetup + 1}" scope="page" />
																					<input type="hidden" value="${productItemOld.type }" />
																				</center>
																			</td>
																			<td>
																				${productItemOld.serviceProductBean.productName }
																			</td>
																			<td>
																			</td>
																			<td style="vertical-align: middle; width: 80px;">
																				<div class="input-group">
																				<input type="number" value="${productItemOld.quantity }" class="form-control worksheetViewDisableDom numberService quantity" />
																				<div class="input-group-addon">${productItemOld.serviceProductBean.unit.unitName }</div>
																				 </div>
																			</td>
																			<td>
																				<center>
																				<label class="custom-control custom-checkbox">
																					<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="${productItemOld.price }"
																					<c:if test="${productItemOld.free }">checked</c:if> >
																					<span class="custom-control-indicator"></span>
																					<span class="custom-control-description"></span>
																				</label>
																				</center>
																			</td>
																			<td>
																				<center style="display: none;">
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend" 
																							disabled>
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																				</center>
																			</td>
																			<td>
																				<input type="number" value="${productItemOld.price }" class="form-control worksheetViewDisableDom salePrice calculate" />
																			</td>
																			<td>
																					<div class="input-group">
																					<input type="number" value="${productItemOld.quantity * productItemOld.price}" disabled class="form-control worksheetViewDisableDom totalPrice"/>
																					 </div>
																				</td>
																			<c:if test="${statusCurrentHistory eq 'R'}">
																			<td>
																				 
																					<a class="run-swal cursor-p removeProductItemSetup">
																					<span class="ti-trash" data-toggle="tooltip" 
																					data-placement="bottom" title="" 
																					aria-describedby="tooltip128894"></span></a>
																					<input type="hidden" value="0" />
																					<input type="hidden" value="${productItemOld.serviceProductBean.id  }" />
																				 
																			</td>
																			</c:if>
																		</tr>
																 		</c:when>
																 		<c:when test="${productItemOld.type eq 'I'}">
																 			<c:forEach var="productWorkSheetItem" items="${productItemOld.productItemWorksheetBeanList}" varStatus="k">
																 			<tr class="trNewItem">
																				<td>
																					<center>
																						<span class="no-item-setup"><c:out value="${countNoSetup}" /></span>
																						<c:set var="countNoSetup" value="${countNoSetup + 1}" scope="page" />
																						<input type="hidden" value="${productItemOld.type }" />
																					</center>
																				</td>
																				<td>
																					${productWorkSheetItem.internetProductBeanItem.internetProductBean.productName }
																					<br><font style="color: gray;"><a href="${pageContext.request.contextPath}/productorderinternetproduct/detail/${productWorkSheetItem.internetProductBeanItem.internetProductBean.id }" target="_blank">${productWorkSheetItem.internetProductBeanItem.internetProductBean.productCode }</a></font>
																				</td>
																				<td>
																					 <center>
																						
																					 </center>
																				</td>
																				<td style="vertical-align: middle; width: 80px;">
																					<div class="input-group">
																					<input type="number" value="${productWorkSheetItem.quantity }" disabled class="form-control quantity calculate"/>
																					<div class="input-group-addon">${productWorkSheetItem.internetProductBeanItem.internetProductBean.unit.unitName }</div>
																					 </div>
																				</td>
																				<td>
																					<center style="display: none;">
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="${productWorkSheetItem.price }"
																							disabled <c:if test="${productWorkSheetItem.free }"> checked="checked" </c:if> >
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td>
																					<center style="display: none;">
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input check-box-lend"
																							disabled>
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td>
																					<input type="number" value="${productWorkSheetItem.price }" disabled class="form-control salePrice calculate" />
																				</td>
																				<td>
																					<div class="input-group">
																					<input type="number" value="${productWorkSheetItem.quantity * productWorkSheetItem.price}" disabled class="form-control worksheetViewDisableDom totalPrice"/>
																					
																					 </div>
																				</td>
																				<c:if test="${worksheetBean.status.stringValue ne 'S'}">
																				<td>
																						<a class="run-swal cursor-p removeProductItemSetup">
																						<span class="ti-trash" data-toggle="tooltip" 
																						data-placement="bottom" title="" 
																						aria-describedby="tooltip128894"></span></a>
																						<input type="hidden" value="${productWorkSheetItem.internetProductBeanItem.id }" />
																						<input type="hidden" value="${productWorkSheetItem.internetProductBeanItem.internetProductBean.id }" />
																				</td>
																				</c:if>
																			</tr>
																			</c:forEach>
																 		</c:when>
																 	</c:choose>
																 </c:forEach>
															</tbody>
														</table>
														</div>
													</div>
												</div>
											</fieldset>
													</div>
												</div>
												<div id="successTask">
													<div class="row">
													<div class="col-md-12"> 
														<input type="hidden" id="hiddenWorksheetSetupId" value="${worksheetBean.setupWorksheetBean.id}" />
													</div>
												</div> 
												<jsp:include page="../table_sub_worksheet.jsp"></jsp:include>
												<c:if test="${worksheetBean.workSheetType ne 'C_S'}">
													<jsp:include page="../tableAddEquipmentProduct.jsp"></jsp:include>
												</c:if>
												</div>
												<br />
												 <jsp:include page="../tableMember.jsp"></jsp:include>
												 <br />
												 <c:if test="${worksheetBean.serviceApplication.servicepackage.monthlyService == true && worksheetBean.status.stringValue eq 'R'}">
													<div class="row">
														<div class="col-md-1">
															<b>เริ่มรอบบิล </b>
														</div>
														<div class="col-md-3">
															<div class="input-group">
																<input type="text" class="form-control worksheetViewDisableDomNotAuto"
																	id="dateStartCalBill" placeholder="วัน-เดือน-ปี" value="${worksheetBean.dateStartNewBill }">
																<div class="input-group-addon"><i class="fa fa-calendar-o" style="margin: 0;"></i></div>
															</div>
														</div>
													</div>
													<br />
												</c:if>
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
													<button type="button" onclick="openModalSuccess('C_S');"
														class="btn btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><i class="ti-check"></i></span>
														บันทึกใบงานสำเร็จ
													</button>
													<button type="button" onclick="openModalNotSuccess('C_S');"
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
														<button type="button" onclick="openModalUpdate('C_S');"
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
			<jsp:include page="../modal_search_equipment_setup.jsp"></jsp:include>
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
	//global variable
	var monthlyService = 'false';
	var refServiceApplication = null;
	
	$( document ).ready(function() {
		calculate();
		
		$("#dateStartCalBill").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE'
		});	
		var currentDate = new Date();
		//$("#dateStartCalBill").datepicker("setDate", currentDate);
		
		monthlyService = '${worksheetBean.serviceApplication.servicepackage.monthlyService}';
		refServiceApplication = '${worksheetBean.serviceApplication.referenceServiceApplicationBean}';
	});
	
		//init 
		var statusCurrentHistory = '${statusCurrentHistory}';
		//alert(statusCurrentHistory);
// 		if(statusCurrentHistory == 'R'){
// 			$("input").attr('disabled','disabled');
// 		}
		
		$('#newcustomers').hide();

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
		
		function updateWorksheetSettup(typeSave){
			var snapHead = {};
			var worksheetUpdateSnapShotBean = setDataHeader(snapHead,typeSave);
			
			var setupWorksheetBean = {};
			setupWorksheetBean.id = $('#hiddenWorksheetSetupId').val();
			if(monthlyService == 'true'){
				setupWorksheetBean.dateStartBill = $('#dateStartCalBill').val();
			}else{
				setupWorksheetBean.dateStartBill = "";
			}
			worksheetUpdateSnapShotBean.setupWorksheetBean = setupWorksheetBean;
			worksheetUpdateSnapShotBean.typeWorksheet = "C_S";
			
			worksheetUpdateSnapShotBean.remark = $('#remark').val();
			//console.log(JSON.stringify(worksheetUpdateSnapShotBean));
			console.log(worksheetUpdateSnapShotBean);
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
					if(data["error"] == false){
						if(typeSave == 'S'){
							window.location.href = "${pageContext.request.contextPath}/invoice/payment/"+data["result"];
						}else{
							window.location.reload();
						}
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
		
		function autoInsertAndDeleteDataSetup(){
			console.log('autoInsertAndDeleteDataSetup');
			worksheetUpdateSnapShotBean = {};
			worksheetUpdateSnapShotBean.idWorksheetParent =  $('#hiddenCurrentWorksheetId').val();
			//product
			var productItemBeanList = [];
			$('#tableEquipmentProductSetup > tbody  > tr.trNewItem').each(function() {
				var productWorksheetItem = [];
				var worksheetItem = {};
				var productItem = {};
				var typeHidden = '';
				$(this).find('input').each(function (i) {
					switch (i) {
							case 0:
								typeHidden = $(this).val();
								productItem.type = typeHidden;
								break;
							case 1:
								if(typeHidden == 'S'){
									productItem.quantity = $(this).val();
								}else if(typeHidden == 'E'){
									worksheetItem.quantity = $(this).val();
								}else if(typeHidden == 'I'){
									worksheetItem.quantity = $(this).val();
								}
								break;
							case 2:
								var checkIsFree = $(this).is(':checked');
								if(typeHidden == 'S'){
									productItem.free = checkIsFree;
								}else if(typeHidden == 'E'){
									worksheetItem.free = checkIsFree;
								}else if(typeHidden == 'I'){
									worksheetItem.free = checkIsFree;
								}
								break;
							case 3:
								var checkIsLend = $(this).is(':checked');
								if(typeHidden == 'S'){
									productItem.lend = checkIsLend;
								}else if(typeHidden == 'E'){
									worksheetItem.lend = checkIsLend;
								}else if(typeHidden == 'I'){
									worksheetItem.lend = checkIsLend;
								}
								break;
							case 4:
								if(typeHidden == 'S'){
									productItem.price = $(this).val();
								}else if(typeHidden == 'E'){
									worksheetItem.price = $(this).val();
								}else if(typeHidden == 'I'){
									worksheetItem.price = $(this).val();
								}
								break;
							case 5:
								
								break;
							case 6:
								if(typeHidden == 'S'){
									
								}else if(typeHidden == 'E'){
									var EquipmentProductItemBean = {};
									EquipmentProductItemBean.id = $(this).val();
									worksheetItem.equipmentProductItemBean = EquipmentProductItemBean;
								}else if(typeHidden == 'I'){
									var InternetProductBean = {};
									InternetProductBean.id = $(this).val();
									worksheetItem.internetProductBeanItem = InternetProductBean;
								}
								break;
							case 7:
								if(typeHidden == 'S'){
									productItem.id = $(this).val();
								}else if(typeHidden == 'E'){
									productItem.id = $(this).val();
								}else if(typeHidden == 'I'){
									productItem.id = $(this).val();
								}
								break;
							case 8:
								if(typeHidden == 'S'){
									
								}else if(typeHidden == 'E'){
									var requisitionItemBean = {};
									requisitionItemBean.id = $(this).val();
									worksheetItem.requisitionItemBean = requisitionItemBean;
								}else if(typeHidden == 'I'){
									
								}
								break;
							}
					
						});
				 			productWorksheetItem.push(worksheetItem);
				 			productItem.productItemWorksheetBeanList = productWorksheetItem; //productItem ตัวนี้ให้มองว่าเป็น equimentProduct ที่เป็นตัวแม่ของ equipmentProductItem ยนน
				 			productItemBeanList.push(productItem);
						});
			
					worksheetUpdateSnapShotBean.productItemBeanList = productItemBeanList;
// 					console.log(JSON.stringify(worksheetUpdateSnapShotBean));
					console.log(worksheetUpdateSnapShotBean);
					
					$('.preloader').modal('show');
					$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/worksheetlist/insertAndUpdateEquipmentProductItemAll/",
						dataType : 'json',
						data : JSON.stringify(worksheetUpdateSnapShotBean),
						//timeout : 100000,
						success : function(data) {
							$('.preloader').modal('hide');
						},
						error : function(e) {
							console.log("ERROR: ", e);
						},
						done : function(e) {
							console.log("DONE");
						}
					});
		}
		
		//event checkbox
		$(document).on('click', '.check-box-lend', function(event) {
			if($(this).is(":checked")){
				$(this).closest('tr').find('.check-box-free').prop('checked', false);
			}
			calculate();
			autoInsertAndDeleteDataSetup();
		});
		
		$(document).on('click', '.check-box-free', function(event) {
			if($(this).is(":checked")){
				$(this).closest('tr').find('.check-box-lend').prop('checked', false);
			}
			calculate();
			autoInsertAndDeleteDataSetup();
		});
		
		function calculate(){
			
			$.each($('#tableEquipmentProductSetup tbody tr'), function( index, value ) {
// 				console.log($(this));
				//var templateNo = $(this).find('.template-no');
				var quantity = $(this).find('.quantity');
				var salePrice = $(this).find('.salePrice');
				var checkBoxFree = $(this).find('.check-box-free');
				var checkBoxLend = $(this).find('.check-box-lend');
				var totalPrice = $(this).find('.totalPrice');
				
				// set No
				//templateNo.text(index+1);
				
				if(!quantity.val() || quantity.val()==0){
					quantity.val('1');
				}
				if(!salePrice.val()){
					salePrice.val('0');
				}
				
				if(checkBoxFree.is(":checked") || checkBoxLend.is(":checked")){
					totalPrice.val('0');
				}else{
					totalPrice.val(salePrice.val()*quantity.val());
				}

			});
		}
		
		$(document).on('blur', '.salePrice', function(event) {
			calculate();
			autoInsertAndDeleteDataSetup();
		});
		
		$(document).on('blur', '.numberService', function(event) {
			calculate();
			autoInsertAndDeleteDataSetup();
		});
		
		$(document).on('blur', '.numberReqNotSN', function(event) {
			var max = $(this).attr('max');
			var val = $(this).val();
			console.log('max : '+max);
			console.log('val : '+val);
			console.log('isNaN : '+isNaN(parseInt(val)));
			if(parseInt(val) > parseInt(max)){
				$(this).val(max);
			}
			if(isNaN(parseInt(val)) || parseInt(val) < 1){
				$(this).val(1);
			}
			calculate();
			autoInsertAndDeleteDataSetup();
		});
		
		// ลบ row
		$(document).on('click', '.removeProductItemSetup', function(event) {
			$(this).parent().parent().remove();

			// เรียงลำดับ
			$('#tableEquipmentProductSetup > tbody tr').each(function (i) {
			     $(this).find('.no-item-setup').text((i + 1));
			});
			
// 			var removeItem = $(this).parent().parent().find('.hidden_item_id').val();
// 			productItemId_main_array = jQuery.grep(productItemId_main_array, function(value) {
// 				  return value != removeItem;
// 			});
			
			autoInsertAndDeleteDataSetup();
			
		});
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/workSheetId/${worksheetBean.idWorksheetParent}/companyId/"+companyId,'_blank');
		}
		
	</script>
</body>
</html>