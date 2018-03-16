<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="changeservice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดใบสมัครขอรับบริการ</title>
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
					<div class="row mt05 mb05" >
						<div class="col-md-7">
							<h3>รายละเอียดใบสมัครขอรับบริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/changeservice">รายการแจ้งยกเลิกใช้บริการ</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบสมัครขอแจ้งยกเลิกใช้บริการ</li>
							</ol>
						</div>
						<div class="col-md-5 mt05" align="right">
							<a href="${pageContext.request.contextPath}/worksheetadd/serviceapplication/${serviceApplicationBean.id}/jobType/cutWorksheet">
								<button type="button" class="btn btn-danger label-left mr-0-5">
									<span class="btn-label"><i class="ti-close"></i></span>แจ้งยกเลิกใช้บริการ
								</button>
							</a>
						</div>


						<div class="modal fade" id="changePackage" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">แจ้งเปลี่ยน
											Package บริการ</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<div class="col-md-12">
												<div class="mb-1">
													<label for="exampleSelect1"><b>วันที่แจ้งเปลี่ยน
															Package</b></label>
													<div class="input-group">
														<input type="text"
															class="form-control datepicker-autoclose"
															placeholder="dd/MMMM/yyyy" value="01/26/2017"> <span
															class="input-group-addon"><i
															class="fa fa-calendar-o"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="mb-1">
													<label for="exampleSelect1"><b>วันที่สิ้นสุดการใช้บริการ
															Package</b></label>
													<div class="input-group">
														<input type="text"
															class="form-control datepicker-autoclose"
															placeholder="dd/MMMM/yyyy" value="02/28/2017"> <span
															class="input-group-addon"><i
															class="fa fa-calendar-o"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="label-alert-warning">
													<center>
														<b>ต้องออกใบแจ้งหนี้อีกหนึ่งรอบบิลในวันที่ 20 มกราคม
															2560 เนื่องจากเกินรอบบิล</b>
													</center>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<label for="exampleSelect1"><b>ส่วนลดพิเศษ</b></label>
												<div class="input-group">
													<input type="text" class="form-control" value="0.00"
														id="exampleInputAmount">
													<div class="input-group-addon">บาท</div>
												</div>
											</div>
										</div>
										<div class="row mt15">
											<div class="col-md-12">
												<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;กรอกรหัสผ่านเข้าสู่ระบบขอบคุณ
														เพื่อยืนยันการยกเลิกการใช้บริการถาวร&nbsp;&nbsp;&nbsp;</legend>
													<div class="row mt05 mb05">
														<div class="col-md-12">
															<input type="password" name="key" class="form-control"
																value="" placeholder="กรอกรหัสผ่านให้ถูกต้อง...">
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-success"
											data-dismiss="modal">ยืนยัน</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">ปิด</button>
									</div>
								</div>
							</div>
						</div>

						<div class="modal fade" id="cancelPackage" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">แจ้งยกเลิกใช้บริการถาวร</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<div class="col-md-12">
												<div class="mb-1">
													<label for="exampleSelect1"><b>วันที่แจ้งยกเลิก</b></label>
													<div class="input-group">
														<input type="text"
															class="form-control datepicker-autoclose"
															placeholder="dd/MMMM/yyyy"> <span
															class="input-group-addon"><i
															class="fa fa-calendar-o"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<div class="mb-1">
													<label for="exampleSelect1"><b>วันที่สิ้นสุดการใช้บริการ
															Package</b></label>
													<div class="input-group">
														<input type="text"
															class="form-control datepicker-autoclose"
															placeholder="dd/MMMM/yyyy"> <span
															class="input-group-addon"><i
															class="fa fa-calendar-o"></i></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-md-12">
												<label for="exampleSelect1"><b>ส่วนลดพิเศษ</b></label>
												<div class="input-group">
													<input type="text" class="form-control" value="0.00"
														id="exampleInputAmount">
													<div class="input-group-addon">บาท</div>
												</div>
											</div>
										</div>
										<div class="row mt15">
											<div class="col-md-12">
												<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;กรอกรหัสผ่านเข้าสู่ระบบขอบคุณ
														เพื่อยืนยันการเปลี่ยน Package&nbsp;&nbsp;&nbsp;</legend>
													<div class="row mt05 mb05">
														<div class="col-md-12">
															<input type="password" name="key" class="form-control"
																value="" placeholder="กรอกรหัสผ่านให้ถูกต้อง...">
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-success"
											data-dismiss="modal">ยืนยัน</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">ปิด</button>
									</div>
								</div>
							</div>
						</div>

					</div>

					<div class="card mb30">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">เลขที่ใบสมัคร #${serviceApplicationBean.serviceApplicationNo }</h4>
							<div class="float-xs-right"><b>วันที่สมัคร ${serviceApplicationBean.createDateTh }</b></div>
						</div>
						<div class="card-block">
							<div class="">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div id="oldcustomers">
												<div class="row ">
													<div class="col-md-3">
														<b>รหัสลูกค้า</b><br><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด"><b>${serviceApplicationBean.customer.custCode }</b></a>
													</div>
													<div class="col-md-3">
														<b>ชื่อลูกค้า</b><br>${serviceApplicationBean.customer.firstName } ${serviceApplicationBean.customer.lastName }
													</div>
													<div class="col-md-2">
														<b>เลขที่บัตรประชาชน</b><br>${serviceApplicationBean.customer.identityNumber }
													</div>
													<div class="col-md-2">
														<b>ประเภทลูกค้า</b><br>${serviceApplicationBean.customer.customerType.value }
													</div>
													<div class="col-md-2">
														<b>ลักษณะของลูกค้า</b><br>${serviceApplicationBean.customer.customerFeatureBean.customerFeatureName }
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-3">
														<b>อาชีพ</b><br>${serviceApplicationBean.customer.careerBean.careerName }
													</div>
													<div class="col-md-3">
														<b>เบอร์โทรติดต่อ</b><br>${serviceApplicationBean.customer.contact.mobile }
													</div>
													<div class="col-md-2">
														<b>Fax</b><br>${serviceApplicationBean.customer.contact.fax }
													</div>
													<div class="col-md-4">
														<b>E-mail</b><br>${serviceApplicationBean.customer.contact.email }
													</div>
												</div>
												<div class="row mt05">
													<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
														<c:if test="${addressBean.addressType eq '1' }">
														<div class="col-md-8">
															<b>ที่อยู่ตามทะเบียนบ้าน</b><br>
																${addressBean.collectAddressDetail }
														</div>
														<div class="col-md-4">
															<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
														</div>
														</c:if>
													</c:forEach>
												</div>
												<div class="row mt05">
													<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
														<c:if test="${addressBean.addressType eq '2' }">
														<div class="col-md-8">
															<b>ที่อยู่ปัจจุบัน</b><br>
																${addressBean.collectAddressDetail }
														</div>
														<div class="col-md-4">
															<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
														</div>
														</c:if>
													</c:forEach>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;Package
												ที่สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-4">
													<label for="exampleSelect1"><b>ประเภทบริการ</b></label><br>
													${serviceApplicationBean.servicepackage.serviceType.packageTypeName }
												</div>
												<div class="col-md-6">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ชื่อ
																Package</b></label><br> [ Package ID : ${serviceApplicationBean.servicepackage.packageCode} ] ${serviceApplicationBean.servicepackage.packageName}
													</div>
												</div>
												<div class="col-md-2">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ประเภทใบสมัคร</b></label><br>${serviceApplicationBean.serviceApplicationTypeBean.serviceApplicationTypeName}
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>รายละเอียด
																Package</b></label>
														<div class="table-responsive">
														<c:choose>
															<c:when test="${serviceApplicationBean.monthlyService }">
																<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
																		<th><center>
																				ค่าติดตั้ง <br>
																			</center></th>
																		<th><center>
																				ค่ามัดจำอุปกรณ์ <br>
																			</center></th>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกใช้ฟรี</center></th> --%>
																		<th style="vertical-align: middle;"><center>ส่วนลดรอบบิลแรก</center></th>
																		<th><center>
																				ค่าบริการรายเดือน <br>
																			</center></th>
																		<th style="vertical-align: middle;"><center>วันที่เริ่มใช้บริการ</center></th>
																		<th style="vertical-align: middle;"><center>วันที่ออกใบแจ้งหนี้ล่าสุด</center></th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.installationFee}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.deposit}" /> บาท</center>
																			</div></td>
<!-- 																		<td scope="row"><div class="input-group"> -->
<%-- 																				<center>${serviceApplicationBean.firstBillFree } เดือน</center> --%>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.firstBillFreeDisCount}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.monthlyServiceFee}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center>${serviceApplicationBean.serviceDate}</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center>${serviceApplicationBean.currentCreateInvoice}</center>
																			</div></td>
																	</tr>
																</tbody>
															</table>
															</c:when>
															<c:otherwise>
																<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
																		<th><center>
																				ค่าติดตั้ง
																			</center></th>
																		<th><center>
																				ค่ามัดจำอุปกรณ์
																			</center></th>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกใช้ฟรี</center></th> --%>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกลดให้</center></th> --%>
																		<th><center>
																				ค่าบริการ 
																			</center></th>
<%-- 																		<th style="vertical-align: middle;"><center>วันที่เริ่มใช้บริการ</center></th> --%>
<%-- 																		<th style="vertical-align: middle;"><center>วันที่ออกใบแจ้งหนี้ล่าสุด</center></th> --%>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.installationFee}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.deposit}" /> บาท</center>
																			</div></td>
<!-- 																		<td scope="row"><div class="input-group"> -->
<%-- 																				<center>1 เดือน</center> --%>
<!-- 																			</div></td> -->
<!-- 																		<td scope="row"><div class="input-group"> -->
<%-- 																				<center>200.00 บาท</center> --%>
<!-- 																			</div></td> -->
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.oneServiceFee}" /> บาท</center>
																			</div></td>
<!-- 																		<td scope="row"><div class="input-group"> -->
<%-- 																				<center>1 กันยายน 2559</center> --%>
<!-- 																			</div></td> -->
<!-- 																		<td scope="row"><div class="input-group"> -->
<%-- 																				<center>1 ธันวาคม 2560</center> --%>
<!-- 																			</div></td> -->
																	</tr>
																</tbody>
															</table>
															</c:otherwise>
														</c:choose>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลรายละเอียดการติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mt05">
												<c:forEach var="addressApplicationBean" items="${serviceApplicationBean.addressList}" varStatus="i">
														<c:if test="${addressApplicationBean.addressType eq '3' }">
															<div class="col-md-6">
																<b>ที่อยู่</b><br>${addressApplicationBean.collectAddressDetail }
															</div>
															<div class="col-md-6">
																<b>สถานที่ใกล้เคียง</b><br>${addressApplicationBean.nearbyPlaces }
															</div>
															<c:set var="zoneName" value="${addressApplicationBean.zoneBean.zoneName}" scope="session" />
														</c:if>
												</c:forEach>
											</div>
											<div class="row mt05">
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>วัน/เวลาที่สะดวกในการติดตั้ง</b></label>
													<br>${serviceApplicationBean.easyInstallationDateTime}
												</div>
<!-- 												<div class="col-md-2"> -->
<!-- 													<label for="exampleInputEmail1"><b>เวลาที่สะดวกในการติดตั้ง</b></label> -->
<!-- 													<br>09:30 - 10:00 น. -->
<!-- 												</div> -->
												<div class="col-md-4">
												<label for="exampleSelect1"><b>เขตชุมชน / โครงการที่ติดตั้ง</b>
													</label><br><c:out value="${zoneDetail} (${zoneName})" />
												</div>
												<div class="col-md-2">
												<label for="exampleSelect1"><b>Plate Number</b>
													</label><br><c:choose><c:when test="${serviceApplicationBean.plateNumber != null}">${serviceApplicationBean.plateNumber}</c:when><c:otherwise>-</c:otherwise> </c:choose>
												</div>
											</div>
											<c:forEach items="${serviceApplicationBean.documents}" var="documentBean" varStatus="i">
											<c:if test="${documentBean.fileType eq 'I'}">
											<div class="row mt05">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>แผนที่ในการติดตั้ง</b></label>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<center>
<!-- 														<img -->
<!-- 															src="https://f.ptcdn.info/955/009/000/1379636282-map02JPG-o.jpg" -->
<!-- 															style="width: 800px;"> -->
													<img
															src="${documentBean.filePath}${documentBean.fileName}"
															style="width: 800px;">
													</center>
												</div>
											</div>
											</c:if>	
											</c:forEach>
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายชื่อวัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-12">
													<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover">
														<thead class="thead-bg">
															<tr>
																<th><center>ลำดับ</center></th>
																<th>รหัสสินค้า</th>
																<th>ชื่อเรียกสินค้า</th>
																<th style="width: 130px;"><center>จำนวน</center></th>
																<th style="width: 150px;"><center>ราคาขาย
																		(ไม่รวม Vat)</center></th>
																<th><center>ฟรี</center></th>
																<th><center>ยืม</center></th>
																<th style="width: 150px;"><center>ราคารวม
																		(ไม่รวม Vat)</center></th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${serviceApplicationBean.productitemList}" var="productItem" varStatus="i">
															<tr>
																<td><center>${i.count }</center></td>
																<td><b>${productItem.product.productCode }</b></td>
																<td>${productItem.product.productName }</td>
																<td scope="row"><center>${productItem.quantity } &nbsp;${productItem.product.unit.unitName }</center></td>
																<td class="td-middle"><center><fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.price}" /> บาท</center></td>
																<td align="center" style="vertical-align: middle;"><center>
																<c:if test="${productItem.free }">
																		<span class="ti-check-box"></span>
																</c:if>
																	</center></td>
																<td align="center" style="vertical-align: middle;"><center>
																<c:if test="${productItem.lend }">
																		<span class="ti-check-box"></span>
																</c:if>
																	</center></td>
																<td scope="row"><center><fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.amount}" /> บาท</center></td>
															</tr>
															</c:forEach>
														</tbody>
													</table>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ในการจัดส่งเอกสารการเงิน&nbsp;&nbsp;&nbsp;</legend>
											<c:forEach var="addressApplicationBean" items="${serviceApplicationBean.addressList}" varStatus="i">
														<c:if test="${addressApplicationBean.addressType eq '4' }">
													<div class="row mt05">
															<div class="col-md-8">
																<b>ที่อยู่สำหรับการจัดส่งใบแจ้งหนี้และใบเสร็จรับเงิน</b><br>${addressApplicationBean.collectAddressDetail }
															</div>
															<div class="col-md-4">
																<b>สถานที่ใกล้เคียง</b><br>${addressApplicationBean.nearbyPlaces }
															</div>
													</div>
											</c:if>
												</c:forEach>
											<hr>
											<div class="row mt05">
												<c:forEach var="addressApplicationBean" items="${serviceApplicationBean.addressList}" varStatus="i">
														<c:if test="${addressApplicationBean.addressType eq '5' }">
															<div class="col-md-8">
																<b>ที่อยู่สำหรับการจัดส่งใบกำกับภาษี</b><br>${addressApplicationBean.collectAddressDetail }
															</div>
															<div class="col-md-4">
																<b>สถานที่ใกล้เคียง</b><br>${addressApplicationBean.nearbyPlaces }
															</div>
														</c:if>
												</c:forEach>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;หมายเหตุ /
												ข้อมูลเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
											${serviceApplicationBean.remark }
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;เอกสารประกอบการสมัคร&nbsp;&nbsp;&nbsp;</legend>
											<c:if test="${serviceApplicationBean.houseRegistrationDocuments }">
												<span class="ti-check-box"></span>  สำเนาทะเบียนบ้าน
											<br />
											</c:if>
											<c:if test="${serviceApplicationBean.identityCardDocuments }">
												<span class="ti-check-box"></span>  สำเนาบัตรประจำตัวประชาชน
											<br />
											</c:if>
											<c:if test="${serviceApplicationBean.otherDocuments }">
												<span class="ti-check-box"></span>  เอกสารอื่นๆ
												<textarea class="form-control" id="remark_other_documents" rows="3" disabled>${serviceApplicationBean.remarkOtherDocuments}</textarea>
											<br />
											</c:if>
										</fieldset>
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
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	<script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>

	<script type="text/javascript">
$('.datepicker-autoclose').datepicker({
	autoclose: true,
	format: 'dd-mm-yyyy',
	todayHighlight:'TRUE',
	todayBtn: true,
    language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
    thaiyear: true              //Set เป็นปี พ.ศ.
});	
	</script>


</body>
</html>
