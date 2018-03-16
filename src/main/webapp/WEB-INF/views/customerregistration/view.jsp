<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="customerregistration" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดข้อมูลลูกค้า</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="material-design fixed-sidebar fixed-header">
	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row" >
						<div class="col-md-8">
							<h3>ข้อมูลลูกค้า คุณ${customerBean.firstName } ${customerBean.lastName }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/customerregistration">ทะเบียนลูกค้า</a></li>
								<li class="breadcrumb-item active">ข้อมูลลูกค้า</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<perm:permission object="M24.edit" >
								<a href="${pageContext.request.contextPath}/customerregistration/edit/${customerBean.id}"><button
										type="button"
										class="btn btn-warning label-left float-xs-right mr-0-5">
										<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูลส่วนตัว
									</button></a>
							</perm:permission>
							<a href="${pageContext.request.contextPath}/customerregistration/exportPdf" target="_blank"><button
										type="button"
										class="btn btn-info label-left float-xs-right mr-0-5">
										<span class="btn-label"><i class="fa fa-file-pdf-o"></i></span>Export Pdf
									</button></a>
						</div>
					</div>
					<div class="row mt05 mb30">
						<div class="col-sm-12 col-md-12">
							<div class="card mb-0">
								<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
									<li class="nav-item"><a class="nav-link active"
										data-toggle="tab" href="#stream" role="tab"
										aria-expanded="true"><b>ข้อมูลลูกค้า</b></a></li>
									<li class="nav-item"><a class="nav-link" data-toggle="tab"
										href="#photos" role="tab" aria-expanded="false"><b>Package
												ที่สมัครใช้บริการ</b></a></li>
									<li class="nav-item"><a class="nav-link" data-toggle="tab"
										href="#friends" role="tab" aria-expanded="false"><b>รายการอุปกรณ์</b></a>
									</li>
									<li class="nav-item"><a class="nav-link" data-toggle="tab"
										href="#tasks" role="tab" aria-expanded="false"><b>ประวัติการใช้บริการ</b></a>
									</li>
									<li class="nav-item"><a class="nav-link" data-toggle="tab"
										href="#pay" role="tab" aria-expanded="false"><b>ประวัติการชำระเงิน</b></a>
									</li>
									<li class="nav-item"><a class="nav-link" data-toggle="tab"
										href="#list-internet" role="tab" aria-expanded="false"><b>รายการ Internet</b></a>
									</li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="stream" role="tabpanel"
										aria-expanded="true">
										<div class="box-block">
											<div class="row">
												<div class="col-md-12">
													<fieldset>
														<legend>&nbsp;&nbsp;&nbsp;ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
														<div class="row">
															<div class="col-md-3">
																<b>รหัสลูกค้า</b><br>${customerBean.custCode }
															</div>
															<div class="col-md-3">
																<b>ชื่อลูกค้า</b><br>${customerBean.firstName } &nbsp; ${customerBean.lastName }
															</div>
															<div class="col-md-3">
																<b>เลขที่บัตรประชาชน</b><br>${customerBean.identityNumber }
															</div>
															<div class="col-md-3">
																<b>ประเภทลูกค้า</b><br>${customerBean.customerType.value }
															</div>
														</div>
														<div class="row mt05">
															<div class="col-md-3">
																<b>ลักษณะลูกค้า</b><br>${customerBean.customerFeatureBean.customerFeatureName }
															</div>
															<div class="col-md-3">
																<b>อาชีพ</b><br>${customerBean.careerBean.careerName }
															</div>
															<div class="col-md-3">
																<b>เบอร์โทรติดต่อ</b><br>${customerBean.contact.mobile }
															</div>
															<div class="col-md-3">
																<b>Fax</b><br>${customerBean.contact.fax }
															</div>
														</div>
														<div class="row mt05">
															<div class="col-md-3">
																<b>E-mail</b><br>${customerBean.contact.email }
															</div>
														</div>
													</fieldset>
												</div>
											</div>
											<c:forEach var="addressBean" items="${customerBean.addressList}" varStatus="i">
											<c:if test="${addressBean.addressType eq '1' }">
											<div class="row mt15">
												<div class="col-md-12">
													<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ตามทะเบียนบ้าน&nbsp;&nbsp;&nbsp;</legend>
														<div class="row">
															<div class="col-md-6">
																<b>ที่อยู่</b><br>${addressBean.collectAddressDetail }
															</div>
															<div class="col-md-6">
																<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
															</div>
														</div>
													</fieldset>
												</div>
											</div>
											</c:if>
											</c:forEach>
											
											<c:forEach var="addressBean" items="${customerBean.addressList}" varStatus="i">
											<c:if test="${addressBean.addressType eq '2' }">
											<div class="row mt15">
												<div class="col-md-12">
													<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ในปัจจุบัน&nbsp;&nbsp;&nbsp;</legend>
														<div class="row">
															<div class="col-md-6">
																<b>ที่อยู่</b><br>${addressBean.collectAddressDetail }
															</div>
															<div class="col-md-6">
																<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
															</div>
														</div>
													</fieldset>
												</div>
											</div>
											</c:if>
											</c:forEach> 
										</div>
									</div>
									<div class="tab-pane card-block" id="photos" role="tabpanel"
										aria-expanded="false">
										<form action="">
											<div class="row mb15">
												<div class="col-md-11 mb05">
													<div class="form-group">
														<input type="text" class="form-control"
															placeholder="ระบุชื่อ Package หรือรหัส Package ที่ต้องการค้นหา" id="inputTableApplication">
													</div>
												</div>
												<div class="col-md-1 mb05" align="center">
													<button type="button" id="btnClearSearchApplication" 
														class="btn btn-defalut">ล้าง</button>
												</div>
											</div>
										</form>
										<div class="row mb05">
											<div class="col-md-12">
												<span class="pull-right"> 
													<i class="fa fa-pencil-square-o text-brown"></i>&nbsp;<font style="color: gray;">แบบร่าง</font>
													&nbsp;|&nbsp;<i class="fa fa-hourglass-2 text-orange"></i>&nbsp;<font style="color: gray;">รอมอบหมายงาน</font>
													&nbsp;|&nbsp;<i class="fa fa-wrench text-blue"></i>&nbsp;<font style="color: gray;">ระหว่างการติดตั้ง</font>
													&nbsp;|&nbsp;<i class="fa fa-check-circle-o text-green"></i>&nbsp;<font style="color: gray;">ใช้งานปกติ</font>
				<!-- 									<i class="ion-record text-red"></i> ระงับชั่วคราว |  -->
													&nbsp;|&nbsp;<i class="fa fa-times-circle-o text-red"></i>&nbsp;<font style="color: gray;">ยกเลิกการใช้บริการ</font>
												</span>
											</div>
											<div class="col-md-12 mb05">
												<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover" id="tableApplication">
														<thead class="thead-bg">
															<tr>
																<th width="15%"><center>เลขที่ใบสมัคร</center></th>
																<th width="25%"><center>ชื่อ Package ที่สมัคร</center></th>
																<th width="20%"><center>พื้นที่ติดตั้ง</center></th>
																<th width="10%"><center>สถานะ</center></th>
																<th width="15%"><center>เริ่มใช้บริการ</center></th>
																<th width="15%"><center>ใบแจ้งหนี้ล่าสุด</center></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="serviceApplication" items="${customerBean.serviceApplicationList}" varStatus="i">
															<tr>
																<td class="td-middle"><center><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" class="cursor-p" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplication.id}" target="_blank"><b>${serviceApplication.serviceApplicationNo }</b></a></center></td>
																<td class="td-middle" style="color: gray;">${serviceApplication.servicepackage.packageName }<br> <small><b>Package ID :</b>
																		${serviceApplication.servicepackage.packageCode }</small>
																</td>
																<td class="td-middle">
																	<center>
																	<c:forEach var="addressBean" items="${serviceApplication.addressList}" varStatus="j">
																		<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.zoneBean.zoneDetail } <b>(${addressBean.zoneBean.zoneName })</b>
																		</c:if>
																	</c:forEach>
																	</center>
																</td>
																<td class="td-middle">
																	<center>																
																		<c:if test="${serviceApplication.status.stringValue eq 'A'}"><i class="fa fa-check-circle-o text-green" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ใช้งานปกติ"></i></c:if>
																		<c:if test="${serviceApplication.status.stringValue eq 'I'}"><i class="fa fa-times-circle-o text-red" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ยกเลิกการใช้บริการ"></i></c:if>
																		<c:if test="${serviceApplication.status.stringValue eq 'H'}"><i class="fa fa-hourglass-2 text-orange" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="รอมอบหมายงาน"></i></c:if>
																		<c:if test="${serviceApplication.status.stringValue eq 'D'}"><i class="fa fa-pencil-square-o text-brown" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="แบบร่าง"></i></c:if>
																		<c:if test="${serviceApplication.status.stringValue eq 'W'}"><i class="fa fa-wrench text-blue" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ระหว่างการติดตั้ง"></i></c:if>
	<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'T'}"><i class="ion-record text-red" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ระงับชั่วคราว"></i></c:if> --%>
																	</center>																
																</td>																
																<td class="td-middle">
																	<center>
																	<c:choose>
																		<c:when test="${serviceApplication.status.stringValue eq 'A'}">
																			${serviceApplication.startDate }
																		</c:when>
																	</c:choose>	
																	</center>
																</td>
																<td class="td-middle"><center>${serviceApplication.currentCreateInvoice }</center></td>
															</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>

											</div>
										</div> 
									</div>
									<div class="tab-pane card-block" id="friends" role="tabpanel"
										aria-expanded="false">
										<form action="">
											<div class="row mb15">
												<div class="col-md-11 mb05">
													<div class="form-group">
														<input type="text" class="form-control"
															placeholder="ระบุ รหัส หรือชื่อ ของสินค้าและอุปกณ์ ที่ต้องการค้นหา" id="inputTableProductItem">
													</div>
												</div>
												<div class="col-md-1 mb05" align="center">
													<button type="button" id="btnClearSearchProductItem" 
														class="btn btn-defalut">ล้าง</button>
												</div>
											</div>
										</form>
										<div class="row">
											<div class="col-md-12">
											<div class="table-responsive">
												<table class="table table-bordered mb-0 table-hover" id="tableProductItem">
													<thead class="thead-bg">
														<tr>
															<th><center>รหัสสินค้า</center></th>
															<th><center>Serial Numeber</center></th>
															<th>ชื่อเรียกสินค้า</th>
															<th style="width: 130px;"><center>จำนวน</center></th>
															<th style="width: 150px;"><center>สถานะ</center></th>

														</tr>
													</thead>
													<tbody>
														<c:forEach var="productItemWorksheetBean" items="${productItemWorksheetBeans}" varStatus="i">
															<tr>
																	<td class="td-middle"><center>
																			<a target="_blank"
																				href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productItemWorksheetBean.equipmentProductItemBean.equipmentProductBean.id }"
																				data-toggle="tooltip" data-placement="bottom"
																				title="" data-original-title="ดูรายละเอียด"><b>${productItemWorksheetBean.equipmentProductItemBean.equipmentProductBean.productCode }</b></a>
																		</center></td>
																	
																	<td class="td-middle"><center>
																			<a target="_blank"
																				href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${productItemWorksheetBean.equipmentProductItemBean.id }"
																				data-toggle="tooltip" data-placement="bottom"
																				title="" data-original-title="ดูรายละเอียด">${productItemWorksheetBean.equipmentProductItemBean.serialNo }</a>
																		</center></td>
																		
																	<td class="td-middle">${productItemWorksheetBean.equipmentProductItemBean.equipmentProductBean.productName }</td>
																	
																	<td class="td-middle" scope="row"><center>${productItemWorksheetBean.quantity }
																			${productItemWorksheetBean.equipmentProductItemBean.equipmentProductBean.unit.unitName }</center></td>
																			
																	<td class="td-middle"><center>
																	<c:choose>
																		<c:when test="${productItemWorksheetBean.lend }">
																			ยืมอุปกรณ์
																		</c:when>
																		<c:when test="${productItemWorksheetBean.free }">
																			ฟรี
																		</c:when>
																		<c:otherwise>
																			ขายขาด
																		</c:otherwise>
																	</c:choose>
																</center></td>
																
															</tr>
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>
										</div>
									</div>
									<div class="tab-pane card-block" id="tasks" role="tabpanel"
										aria-expanded="false">
										<form action="">
											<div class="row mb15">
												<div class="col-md-11 mb05">
													<div class="form-group">
														<input type="text" class="form-control"
															placeholder="ระบุหมายเลขใบงานที่ต้องการค้นหา" id="inputTableWorksheet">
													</div>
												</div>
												<div class="col-md-1 mb05" align="center">
													<button type="button" class="btn btn-defalut" id="btnClearSearchWorksheet">ล้าง</button>
												</div>
											</div>
										</form>
										<div class="row mb05">
											<div class="col-md-12 mb05">
												<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover" id="customerWorksheetTable">
														<thead class="thead-bg">
															<tr>
																<th><center>เลขที่ใบงาน</center></th>
																<th><center>วันที่ใช้บริการ</center></th>
																<th><center>ประเภทใบงาน</center></th>
																<th style="width:300px"><center>เขตชุมชน / โครงการที่ติดตั้ง</center></th>
																<th><center>ผู้รับผิดชอบ</center></th>
																<th><center>สถานะ</center></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="serviceApplicationBean" items="${customerBean.serviceApplicationList}" varStatus="i">
															<c:forEach var="worksheetBean" items="${serviceApplicationBean.worksheetBeanList}" varStatus="j">
															<tr>
																<td class="td-middle"><center><a target="_blank" href="${pageContext.request.contextPath}/worksheetlist/detail/${worksheetBean.idWorksheetParent}" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด"><b>${worksheetBean.workSheetCode}</b></a></center></td>
																<td class="td-middle"><center>
																${worksheetBean.currentDateAssignText }
																</center></td>
<!-- 																30 มกราคม 2560 (10:00 น.) -->
																<td class="td-middle">
																<center>
																	${worksheetBean.workSheetTypeText }
																</center></td>
																<td class="td-middle">
																	<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																		<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.collectAddressDetail } <b>(${addressBean.zoneBean.zoneName })</b>
																		</c:if>
																	</c:forEach>
																</td>
																<td class="td-middle">
																<center>
																	<c:forEach var="historyTechnicianGroupWork" items="${worksheetBean.historyTechnicianGroupWorkBeans}" varStatus="k">	
																		<c:if test="${worksheetBean.historyTechnicianGroupWorkBeanSize == k.count }">
																			${historyTechnicianGroupWork.technicainGroup.personnel.firstName}&nbsp;
																			${historyTechnicianGroupWork.technicainGroup.personnel.lastName}
																		</c:if>
																	</c:forEach>
																</center></td>
																<td class="td-middle"><center>
																		<c:choose>
																			<c:when test="${worksheetBean.status.stringValue eq 'W'}">
																				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png" style="width: 20px;"><br>
																				<small><b style="color: #f8c056;">ใบงานรอมอบหมาย</b></small>
																			</c:when>
																			<c:when test="${worksheetBean.status.stringValue eq 'R'}">
																				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png" style="width: 20px;"><br>
																				<small><b style="color: #3d8ff3;">ใบงานอยู่ระหว่างดำเนินงาน</b></small>
																			</c:when>
																			<c:when test="${worksheetBean.status.stringValue eq 'H'}">
																				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png" style="width: 20px;"><br>
																				<small><b style="color: #96723a;">ใบงานคงค้าง</b></small>
																			</c:when>
																			<c:when test="${worksheetBean.status.stringValue eq 'D'}">
																				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png" style="width: 20px;"><br>
																				<small><b style="color: #e2574c;">ใบงานยกเลิก</b></small>
																			</c:when>
																			<c:when test="${worksheetBean.status.stringValue eq 'S'}">
																				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png" style="width: 20px;"><br>
																				<small><b style="color: #6ed029;">ใบงานที่เสร็จเรียบร้อย</b></small>
																			</c:when>
																		</c:choose>	
																	</center>
																</td>
															</tr> 
															</c:forEach>
															</c:forEach>
														</tbody>
													</table>
												</div>

											</div>
										</div>
									</div>
									<div class="tab-pane card-block" id="pay" role="tabpanel"
										aria-expanded="false">
										<form action="">
											<div class="row mb15">
												<div class="col-md-11 mb05">
													<div class="form-group">
														<input type="text" class="form-control" id="inputCusInvoice"
															placeholder="ระบุ รหัสใบแจ้งหนี้  ที่ต้องการค้นหา">
													</div>
												</div>
												<div class="col-md-1 mb05" align="center">
													<button type="button" id="btnClearCusInvoice"
														class="btn btn-defalut">ล้าง</button>
												</div>
											</div>
										</form>
										
										<div class="row">
											<div class="col-md-12">
												<span class="text-grey pull-right"> 													
													<i class="fa fa-hourglass-half" style="color: gray;"></i>&nbsp;<b style="color: gray;">รอลูกค้าชำระเงิน</b>
													&nbsp;&nbsp;<i class="fa fa-exclamation-triangle" style="color: orange;"></i>&nbsp;<b style="color: orange;">เกินวันนัดชำระเงิน</b>
													&nbsp;&nbsp;<i class="fa fa-check-circle" style="color: green;"></i>&nbsp;<b style="color: green;">ชำระเงินเรียบร้อย</b>
													&nbsp;&nbsp;<i class="fa fa-times-circle" style="color: red;"></i>&nbsp;<b style="color: red;">ยกเลิกบิล</b>													
												</span>
											</div>
											<div class="col-md-12">
												<div class="table-responsive">
												<table class="table table-bordered mb-0 table-1 table-hover" id="tbCusInvoice">
													<thead class="thead-bg">
														<tr>
															<th><center>เลขที่ใบแจ้งหนี้</center></th>
															<th><center>ประเภทใบแจ้งหนี้</center></th>
															<th><center>ยอดเงิน (บาท)</center></th>
															<th><center>วันที่ออกเอกสาร</center></th>
															<th><center>วันที่นัดชำระ</center></th>
															<th><center>สถานะ</center></th>
														</tr>
													</thead>
													<tbody>
														 <c:forEach var="historyInvoice" items="${invoiceDocumentBeanList}" varStatus="invCount">
														<c:choose>
													 	<c:when test="${historyInvoice.worksheet.status.stringValue eq 'R' || historyInvoice.worksheet.status.stringValue eq 'S'}">
															<tr>
																<td class="td-middle">
																	<c:choose>
																		<c:when test="${historyInvoice.serviceApplication.status.stringValue eq 'I' && historyInvoice.status ne 'S' && historyInvoice.amount > 0}">
																			<center>
																				<a href="${pageContext.request.contextPath}/invoice/view/${historyInvoice.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${historyInvoice.invoiceCode }</b></a>	
																				<br><small style="color: red;"><b>(หนี้สูญ)</b></small>
																			</center>
																		</c:when>
																		<c:otherwise>
																			<center>
																				<a href="${pageContext.request.contextPath}/invoice/view/${historyInvoice.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${historyInvoice.invoiceCode }</b></a>		 
																				<c:if test="${historyInvoice.billing == true }">
																							<br><small style="color: orange;"><b>(วางบิลแล้ว)</b></small>
																						</c:if>
																			</center>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td class="td-middle"><center>
																	<c:choose>
																			<c:when test="${historyInvoice.invoiceType eq 'S'}">
																				ใบแจ้งหนี้ลูกค้าติดตั้งใหม่
																			</c:when>
																			<c:when test="${historyInvoice.invoiceType eq 'R'}">
																				ใบแจ้งหนี้ตามใบแจ้งซ่อม
																			</c:when>
																			<c:when test="${historyInvoice.invoiceType eq 'O'}">
																				ใบแจ้งหนี้ค่าบริการตามรอบบิล
																			</c:when>
																		</c:choose>
																</center></td>
																<td class="td-middle"><center>
																	<fmt:formatNumber type="number"
																			maxFractionDigits="2"
																			value="${historyInvoice.amount}"/>
																</center></td>
																<td class="td-middle"><center>${historyInvoice.issueDocDateTh}</center></td>
																<td class="td-middle">
																<center>
																	${historyInvoice.createDateTh}
<%-- 																		<c:choose> --%>
<%-- 																			<c:when test="${historyInvoice.invoiceType eq 'O'}"> --%>
<%-- 																				${historyInvoice.createDateTh} --%>
<%-- 																			</c:when> --%>
<%-- 																			<c:otherwise> --%>
<%-- 																				${historyInvoice.paymentDateTh} --%>
<%-- 																			</c:otherwise> --%>
<%-- 																		</c:choose> --%>
																</center></td>
																<td class="td-middle">
																	<center>
																		<c:choose>
																			<c:when test="${historyInvoice.status eq 'C'}">
																				<i class="fa fa-times-circle" style="color: red;"></i>
																			</c:when>
																			<c:when test="${historyInvoice.status eq 'W'}">
																				<i class="fa fa-hourglass-half" style="color: gray;"></i>
																			</c:when>
																			<c:when test="${historyInvoice.status eq 'O'}">
																				<i class="fa fa-exclamation-triangle" style="color: orange;"></i>
																			</c:when>
																			<c:when test="${historyInvoice.status eq 'S'}">
																				<i class="fa fa-check-circle" style="color: green;"></i>
																			</c:when>
																		</c:choose>	
																	</center>
																</td>
															</tr>
															</c:when>
															</c:choose>
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>
										</div>
									</div>
									<div class="tab-pane card-block" id="list-internet" role="tabpanel"
										aria-expanded="false">
										<form action="">
											<div class="row mb15">
												<div class="col-md-11 mb05">
													<div class="form-group">
														<input type="text" class="form-control"
															placeholder="ระบุรหัสสินค้า ที่ต้องการค้นหา" id="inputTablelistInternet">
													</div>
												</div>
												<div class="col-md-1 mb05" align="center">
													<button type="button" class="btn btn-defalut" id="btnClearSearchlistInternet">ล้าง</button>
												</div>
											</div>
										</form>
										<div class="row mb05">
											<div class="col-md-12 mb05">
												<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover" id="listInternetTable">
														<thead class="thead-bg">
															<tr>
																<th><center>รหัสสินค้า</center></th>
																<th><center>ชื่อ package บริการ</center></th>
																<th><center>Username</center></th>
																<th><center>Password</center></th>
															</tr>
														</thead>
														<tbody>
														<c:forEach var="productItemWorksheetInternet" items="${productItemWorksheetInternetBeans}" varStatus="j">
															<tr>
																<td class="td-middle"><center><a target="_blank" href="${pageContext.request.contextPath}/productorderinternetproduct/detail/
																${productItemWorksheetInternet.internetProductBeanItem.internetProductBean.id}" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด"><b>
																${productItemWorksheetInternet.internetProductBeanItem.internetProductBean.productCode}</b></a></center></td>
																<td class="td-middle"><center>
																	${productItemWorksheetInternet.internetProductBeanItem.internetProductBean.productName}
																</center></td>
																<td class="td-middle"><center>
																	${productItemWorksheetInternet.internetProductBeanItem.userName}
																</center></td>
																<td class="td-middle">
																<center>
																	${productItemWorksheetInternet.internetProductBeanItem.password}
																</center></td>
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

				</div>
			</div>

			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
			$( document ).ready(function() {
				//table application
				var oTable = $('#tableApplication').dataTable();
				$('#tableApplication_filter').css('display','none');

				$("#inputTableApplication").keyup( function (e) {
				     oTable.fnFilter( this.value );
				} );
				
				$('#btnClearSearchApplication').on('click',function(){
					$("#inputTableApplication").val("");
					oTable.fnFilter("");
				});
				//end service application
				
				//table product item
				var oTableProductItem = $('#tableProductItem').dataTable();
				$('#tableProductItem_filter').css('display','none');

				$("#inputTableProductItem").keyup( function (e) {
					oTableProductItem.fnFilter( this.value );
				} );
				
				$('#btnClearSearchProductItem').on('click',function(){
					$("#inputTableProductItem").val("");
					oTableProductItem.fnFilter("");
				});
				//end table product item
				
				//table worksheet
				var oTableWorksheet = $('#customerWorksheetTable').dataTable();
				$('#customerWorksheetTable_filter').css('display','none');

				$("#inputTableWorksheet").keyup( function (e) {
					oTableWorksheet.fnFilter( this.value );
				} );
				
				$('#btnClearSearchWorksheet').on('click',function(){
					$("#inputTableWorksheet").val("");
					oTableWorksheet.fnFilter("");
				});
				//end table worksheet
				
				//table invoice
				var oTableInvoice = $('#tbCusInvoice').dataTable();
				$('#tbCusInvoice_filter').css('display','none');

				$("#inputCusInvoice").keyup( function (e) {
					oTableInvoice.fnFilter( this.value );
				} );
				
				$('#btnClearCusInvoice').on('click',function(){
					$("#inputCusInvoice").val("");
					oTableInvoice.fnFilter("");
				});
				//end table invoice
				
				//table list-internet
				var oTableDigitalAnalog = $('#listInternetTable').dataTable();
				$('#listInternetTable_filter').css('display','none');

				$("#inputTablelistInternet").keyup( function (e) {
					oTableDigitalAnalog.fnFilter( this.value );
				} );
				
				$('#btnClearSearchlistInternet').on('click',function(){
					$("#inputTablelistInternet").val("");
					oTableDigitalAnalog.fnFilter("");
				});
				//end table list-internet
				
			});
	</script>

</body>
</html>