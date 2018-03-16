<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="serviceapplicationlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดใบสมัครขอรับบริการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>

<style type="text/css">
/* ======================= GOOGLE MAP =========================== */

#route table    {width: 100%}

#route table td    {font-family: thaisanslite !important; font-weight: bold; padding: 5px; padding-left: 10px;}

.adp-summary {
	font-family: thaisanslite !important; font-weight: bold; padding: 5px; color: green; font-size: x-large;background-color: rgba(76, 175, 80, 0.06);
}

.adp-details, .adp-legal {
	font-family: thaisanslite !important;font-size: xx-small;font-weight: bold;color: white;
}

/* ======================= END GOOGLE MAP =========================== */
</style>

</head>
<body class="fixed-header material-design fixed-sidebar">
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
						<div class="col-md-6">
							<h3>รายละเอียดใบสมัครขอรับบริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/serviceapplicationlist">รายการใบสมัครใช้บริการ</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบสมัครขอรับบริการ</li>
							</ol>
						</div>
						<div class="col-md-6" align="right">
						<c:if test="${serviceApplicationBean.status.stringValue eq 'A' }">
						<a href="${pageContext.request.contextPath}/financialreport/contactReceipt/exportPdf/workSheetId/${serviceApplicationBean.id}" target="_blank"><button type="button"
							class="btn btn-info label-left float-xs-right mb05">
								<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์หนังสือสัญญา
						</button></a>
						</c:if>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0"><span class="ti-file"></span>&nbsp;เลขที่ใบสมัคร #${serviceApplicationBean.serviceApplicationNo }&nbsp;/&nbsp;${serviceApplicationBean.companyBean.companyName }</h4>
							<c:if test="${not empty serviceApplicationBean.referenceServiceApplicationBean.serviceApplicationNo}">								
								<br><a href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.referenceServiceApplicationBean.id}" target="_blank" class="btn btn-outline-info w-min-sm mb-0-25 waves-effect waves-light"><b><span class="ti-reload"></span>&nbsp;ใบสมัครนี้ได้เปลี่ยนบริการมาจากใบสมัครเดิมคือ</span>&nbsp;#${serviceApplicationBean.referenceServiceApplicationBean.serviceApplicationNo}</b></a>
							</c:if>
							<div class="float-xs-right"><span class="ti-calendar"></span>&nbsp;วันที่สมัคร ${serviceApplicationBean.createDateTh }</div>							
						</div>
						<div class="card-block">
							<div class="">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-user"></span> ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div id="oldcustomers">
												<div class="row ">
													<div class="col-md-3">
														<b>รหัสลูกค้า</b><br><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank"><b>${serviceApplicationBean.customer.custCode }</b></a>
													</div>
													<div class="col-md-3">
														<b>ชื่อลูกค้า</b><br>${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;${serviceApplicationBean.customer.lastName}
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
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-shopping-cart"></span> Package
												ที่สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-3">
													<label for="exampleSelect1"><b>ประเภทบริการ</b></label><br>
													${serviceApplicationBean.servicepackage.serviceType.packageTypeName }
												</div>
												<div class="col-md-7">
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
																				ค่าติดตั้ง
																			</center></th>
																		<th><center>
																				ค่ามัดจำอุปกรณ์
																			</center></th>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกใช้ฟรี</center></th> --%>
																		<th style="vertical-align: middle;"><center>ส่วนลดรอบบิลแรก</center></th>
																		<th><center>
																				ค่าบริการ / ${serviceApplicationBean.perMonth } เดือน
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
<!-- 																			</div></td> -->
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.firstBillFreeDisCount}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.monthlyServiceFee}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center>${serviceApplicationBean.serviceDate}</center> <!-- วันที่เริ่มใช้บริการ -->
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center>${serviceApplicationBean.currentCreateInvoice}</center> <!-- วันที่ออกใบแจ้งหนี้ล่าสุด -->
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
																				ค่าติดตั้ง <br>(ไม่รวม Vat)
																			</center></th>
																		<th><center>
																				ค่ามัดจำอุปกรณ์ <br>(ไม่รวม Vat)
																			</center></th>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกใช้ฟรี</center></th> --%>
<%-- 																		<th style="vertical-align: middle;"><center>รอบบิลแรกลดให้</center></th> --%>
																		<th><center>
																				ค่าบริการ <br>(ไม่รวม Vat)
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
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-map-alt"></span> ข้อมูลรายละเอียดการติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
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
															<c:set var="zoneDetail" value="${addressApplicationBean.zoneBean.zoneDetail}" scope="session" />
														</c:if>
												</c:forEach>
											</div>
											<div class="row mt05">
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>วัน / เวลาที่สะดวกในการติดตั้ง</b></label>
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
																						
											<div class="row mt05">
												<div class="col-md-12">
													<ul class="nav nav-tabs nav-tabs-2" role="tablist">
														<li class="nav-item"><a class="nav-link active"
															data-toggle="tab" href="#home" role="tab"
															aria-expanded="true"><span class="ti-location-pin"></span>&nbsp;&nbsp;<b>แผนที่</b></a></li>
														<li class="nav-item"><a class="nav-link"
															data-toggle="tab" href="#profile" role="tab"
															aria-expanded="false"><span class="ti-direction-alt"></span>&nbsp;&nbsp;<b>เส้นทาง</b></a></li>
													</ul>
													<div class="tab-content mt15">
														<div class="tab-pane active" id="home" role="tabpanel"
															aria-expanded="true">
															<div class="card modal static-modal custom-modal-4">
																<div class="modal-dialog">
																	<div class="modal-content">
																		<div class="cm-img img-cover"
																			style="background-image: url(../../resources/assets/img/point_map.png);">
																			<div class="gradient gradient-primary"></div>
																			<div class="cm-content">
																				<h1 class=" text-uppercase">พื้นที่ติดตั้ง</h1>
																				<h4>
																					ระยะทางโดยประมาณ <strong id="km"></strong>
																				</h4>
																				<hr>
																				<font style="color: #e3d5ff;"><b>กรุณาระบุตำแหน่งพื้นที่ติดตั้ง
																						ด้วยการลากจุดไปยังตำแหน่งใกล้เคียง</b></font>
																			</div>
																		</div>
																		<div class="modal-body" style="padding: 0px;">
																			<div id="gmap-route" style="with: 300px; height: 400px;"></div>
																		</div>
																		<!-- 						<div id="route"></div> -->
																	</div>
																</div>
															</div>
														</div>
														<div class="tab-pane" id="profile" role="tabpanel"
															aria-expanded="false">
															<div id="route"></div>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>LATITUDE</b></label> <input
														class="form-control bg-form" type="text" id="latitude"
														name="latitude" value="${serviceApplicationBean.latitude}" disabled="disabled">
												</div>
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>LONGITUDE</b></label> <input
														type="text" class="form-control" id="longitude"
														name="longitude" value="${serviceApplicationBean.latitude}" disabled="disabled">
												</div>
											</div>
											
										</fieldset>										
									</div>
								</div>

								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-hummer"></span> รายชื่อวัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
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
																<th style="width: 150px;text-align: right;" >ราคาขาย</th>
																<th style="width: 150px;text-align: right;" >ราคารวม</th>
																<th><center>ฟรี</center></th>
																<th><center>ยืม</center></th>	
																<c:if test="${serviceApplicationBean.createBy eq 'Migrate'}">
																<th><center>วันที่ติดตั้ง Digital</center></th>	
																</c:if>															
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${serviceApplicationBean.productitemList}" var="productItem" varStatus="i">
																<c:choose>
																	<c:when test="${productItem.productItemWorksheetBeanList != null && productItem.productItemWorksheetBeanList.size() > 0 }">
																		<tr>
																			<td class="td-middle"><center>${i.count }</center></td>
																			
																			<c:choose>
																				<c:when test="${productItem.product.type == \"E\" }">
																					<td class="td-middle"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productItem.product.id }" target="_blank"><b>${productItem.product.productCode }</b></a></td>
																				</c:when>
																				<c:when test="${productItem.product.type == \"I\" }">
																					<td class="td-middle"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" href="${pageContext.request.contextPath}/productorderinternetproduct/detail/${productItem.product.id }" target="_blank"><b>${productItem.product.productCode }</b></a></td>
																				</c:when>
																				<c:otherwise>
																					<td class="td-middle"><b>${productItem.product.productCode }</b></td>
																				</c:otherwise>
																			</c:choose>
																			
																			<td class="td-middle">${productItem.product.productName }</td>
																			<td class="td-middle" scope="row"><center>${productItem.productItemWorksheetBeanList.get(0).quantity } &nbsp;${productItem.product.unit.unitName }</center></td>
																			<td style="text-align: right;"><fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.productItemWorksheetBeanList.get(0).price}" /> บาท</td>
																			<td style="text-align: right;" scope="row">
																			<fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.productItemWorksheetBeanList.get(0).amount}" /> บาท</td>
																			<td align="center" style="vertical-align: middle;"><center>
																			<c:if test="${productItem.productItemWorksheetBeanList.get(0).free }">
																					<span class="ti-check-box"></span>
																			</c:if>
																				</center></td>
																			<td align="center" style="vertical-align: middle;"><center>
																			<c:if test="${productItem.productItemWorksheetBeanList.get(0).lend }">
																					<span class="ti-check-box"></span>
																			</c:if>
																				</center></td>	
																			<c:if test="${serviceApplicationBean.createBy eq 'Migrate'}">
																			<td><center>${productItem.installDigital }</center></td>	
																			</c:if>																
																		</tr>
																	</c:when>
																	<c:otherwise>
																		<tr>
																			<td class="td-middle"><center>${i.count }</center></td>
																			<td class="td-middle"><b>${productItem.product.productCode }</b></td>
																			<td class="td-middle">${productItem.product.productName }</td>
																			<td class="td-middle" scope="row"><center>${productItem.quantity } &nbsp;${productItem.product.unit.unitName }</center></td>
																			<td style="text-align: right;"><fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.price}" /> บาท</td>
																			<td style="text-align: right;" scope="row">
																			<fmt:formatNumber type="number" maxFractionDigits="3" value="${productItem.amount}" /> บาท</td>
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
																			<c:if test="${serviceApplicationBean.createBy eq 'Migrate'}">
																			<td><center>${productItem.installDigital }</center></td>	
																			</c:if>															
																		</tr>
																	</c:otherwise>
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
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-email"></span> ที่อยู่ในการจัดส่งเอกสารการเงิน&nbsp;&nbsp;&nbsp;</legend>
											<c:forEach var="addressApplicationBean"
												items="${serviceApplicationBean.addressList}" varStatus="i">
												<c:if test="${addressApplicationBean.addressType eq '4' }">
													<div class="row mt05">
														<div class="col-md-8">
															<b>ที่อยู่สำหรับการจัดส่งใบแจ้งหนี้และใบเสร็จรับเงิน</b><br>${addressApplicationBean.collectAddressDetail }
															
															<c:if test="${addressApplicationBean.nearbyPlaces != null && addressApplicationBean.nearbyPlaces != \"\" }">
																<br><b>สถานที่ใกล้เคียง</b><br>${addressApplicationBean.nearbyPlaces }
															</c:if>
															
														</div>
														<div class="col-md-4">
															<b>เขตชุมชน</b><br>${addressApplicationBean.zoneBean.zoneDetail } (${addressApplicationBean.zoneBean.zoneName })
														</div>
													</div>
												</c:if>
											</c:forEach>
											<hr>
											<div class="row mt05">
												<c:forEach var="addressApplicationBean"
													items="${serviceApplicationBean.addressList}" varStatus="i">
													<c:if test="${addressApplicationBean.addressType eq '5' }">
														<div class="col-md-8">
															<b>ที่อยู่สำหรับการจัดส่งใบกำกับภาษี</b><br>${addressApplicationBean.collectAddressDetail }
														</div>
														<div class="col-md-4">
														
															<c:if test="${addressApplicationBean.nearbyPlaces != null && addressApplicationBean.nearbyPlaces != \"\" }">
																<b>สถานที่ใกล้เคียง</b><br>${addressApplicationBean.nearbyPlaces }
															</c:if>
														
															
														</div>
													</c:if>
												</c:forEach>
											</div>
										</fieldset>
									</div>
								</div>

								<c:if test="${serviceApplicationBean.remark != null && serviceApplicationBean.remark != \"\" }">
									<div class="row mt15">
										<div class="col-md-12">
											<fieldset>
												<legend>&nbsp;&nbsp;&nbsp;<span class="ti-write"></span> หมายเหตุ /
													ข้อมูลเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
												${serviceApplicationBean.remark }
											</fieldset>
										</div>
									</div>
								</c:if>
								
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-id-badge"></span> เอกสารประกอบการสมัคร&nbsp;&nbsp;&nbsp;</legend>
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
					
					
					<div class="card mt05 mb40">
						<div class="card mb-0">
							<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
								<li class="nav-item"><a class="nav-link active" data-toggle="tab"
									href="#tasks" role="tab" aria-expanded="false"><b>ประวัติการใช้บริการ</b></a>
								</li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#pay" role="tab" aria-expanded="false"><b>ประวัติการชำระเงิน</b></a>
								</li>
							</ul>
							<div class="tab-content">
									<div class="tab-pane active" id="tasks" role="tabpanel"
										aria-expanded="false">
										<div class="box-block">
										<div class="table-responsive">
											<table class="table table-bordered mb-0  table-1 table-hover"
											id="customerWorksheetTable">
											<thead class="thead-bg">
												<tr>
													<th><center>เลขที่ใบงาน</center></th>
													<th><center>วันที่ใช้บริการ</center></th>
													<th><center>ประเภทใบงาน</center></th>
													<th style="width: 300px"><center>เขตชุมชน /
															โครงการที่ติดตั้ง</center></th>
													<th><center>ผู้รับผิดชอบ</center></th>
													<th><center>สถานะ</center></th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="worksheetBean"
														items="${serviceApplicationBean.worksheetBeanList}"
														varStatus="j">
														<tr>
															<td class="td-middle"><center><a target="_blank" href="${pageContext.request.contextPath}/worksheetlist/detail/${worksheetBean.idWorksheetParent}" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด"><b>${worksheetBean.workSheetCode}</b></a></center></td>
															<td class="td-middle"><center>${worksheetBean.currentDateAssignText }</center></td>
															<!-- 																30 มกราคม 2560 (10:00 น.) -->
															<td class="td-middle">
																<center>${worksheetBean.workSheetTypeText }</center>
															</td>
															<td class="td-middle">
																<center>
																	<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																		<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.collectAddressDetail } <b>(${addressBean.zoneBean.zoneDetail })</b>
																		</c:if>
																	</c:forEach>
																</center>
															</td>
															<td class="td-middle">
																<center>
																	<c:forEach var="historyTechnicianGroupWork"
																		items="${worksheetBean.historyTechnicianGroupWorkBeans}"
																		varStatus="k">
																		<c:if
																			test="${worksheetBean.historyTechnicianGroupWorkBeanSize == k.count }">
																			${historyTechnicianGroupWork.technicainGroup.personnel.firstName}&nbsp;
																			${historyTechnicianGroupWork.technicainGroup.personnel.lastName}
																		</c:if>
																	</c:forEach>
																</center>
															</td>
															<td class="td-middle"><center>
																	<c:choose>
																		<c:when
																			test="${worksheetBean.status.stringValue eq 'W'}">
																			<img
																				src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png"
																				style="width: 20px;">
																			<br>
																			<small><b style="color: #f8c056;">ใบงานรอมอบหมาย</b></small>
																		</c:when>
																		<c:when
																			test="${worksheetBean.status.stringValue eq 'R'}">
																			<img
																				src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png"
																				style="width: 20px;">
																			<br>
																			<small><b style="color: #3d8ff3;">ใบงานอยู่ระหว่างดำเนินงาน</b></small>
																		</c:when>
																		<c:when
																			test="${worksheetBean.status.stringValue eq 'H'}">
																			<img
																				src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png"
																				style="width: 20px;">
																			<br>
																			<small><b style="color: #96723a;">ใบงานคงค้าง</b></small>
																		</c:when>
																		<c:when
																			test="${worksheetBean.status.stringValue eq 'D'}">
																			<img
																				src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png"
																				style="width: 20px;">
																			<br>
																			<small><b style="color: #e2574c;">ใบงานยกเลิก</b></small>
																		</c:when>
																		<c:when
																			test="${worksheetBean.status.stringValue eq 'S'}">
																			<img
																				src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png"
																				style="width: 20px;">
																			<br>
																			<small><b style="color: #6ed029;">ใบงานที่เสร็จเรียบร้อย</b></small>
																		</c:when>
																	</c:choose>
																</center></td>
														</tr>
													</c:forEach>
											</tbody>
										</table>
										</div>										
										</div>
									</div>
									
									
									<div class="tab-pane card-block" id="pay" role="tabpanel"
										aria-expanded="false">
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
																		<c:choose>
																			<c:when test="${historyInvoice.invoiceType eq 'O'}">
																				${historyInvoice.createDateTh}
																			</c:when>
																			<c:otherwise>
																				${historyInvoice.paymentDateTh}
																			</c:otherwise>
																		</c:choose>
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
	
<script src="https://maps.google.com/maps/api/js?sensor=false&libraries=geometry&v=3.22&key=AIzaSyC_XL3Tcv19fOT8XX0Po-CdkkHwJx5MQMY"></script>

<script type="text/javascript">
$(function() {
	marker = new Maplace({
	    locations: [
	        {
	            lat: 13.555561,
	            lon: 100.274206,
	            title: 'บริษัท มหาชัย เคเบิลทีวี จำกัด',
	            html: '<h3>บริษัท มหาชัย เคเบิลทีวี จำกัด, สมุทรสาคร</h3>'
	        },
	        {
	            lat: ${serviceApplicationBean.latitude },
	            lon: ${serviceApplicationBean.longitude },
	            title: 'ลากจุดนี้ B ไปยังปลายทาง',
	            html: '',
	            visible: false
	        }
	    ],
	    map_div: '#gmap-route',
	    generate_controls: false,
	    show_markers: false,
	    type: 'directions',
	    draggable: false,
	    zoom: 21,
	    directions_panel: '#route',
	    afterRoute: function(distance,status, result) {
	        $('#km').text(': '+(distance/1000)+' km');
	        $('#latitude').val(${serviceApplicationBean.latitude });
	        $('#longitude').val(${serviceApplicationBean.longitude });
	        console.log("A : " + result.request.origin.lat() + "," + result.request.origin.lng());
	        console.log("B : " + result.request.destination.lat() + "," + result.request.destination.lng());
	    }
	}).Load();

});
</script>
</body>
</html>
