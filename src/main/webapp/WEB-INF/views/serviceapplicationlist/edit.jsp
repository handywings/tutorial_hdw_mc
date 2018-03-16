<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="serviceapplicationlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>แก้ไขสมัครใช้บริการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style>
.no-current-address{
	display: none;
}
.detail-install-no-current-address{
	display: none;
}
.shipping-address-no-current-address{
	display: none;
}
.invoice-address-no-current-address{
	display: none;
}
.div-detail-package{
	display: none;
}
.div-detail-package-ismonthlyservice{
	display: none;
}

.custom-file-control:after{content: "Choose file..." !important;}

.choose-custom-file-control:after{content: "" !important;}

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
					<h4>แก้ไขสมัครใช้บริการ</h4>
					<ol class="breadcrumb no-bg mb-1">
						<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/serviceapplicationlist">รายการใบสมัครใช้บริการ</a></li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id}">รายละเอียดใบสมัครขอรับบริการ</a></li>
						<li class="breadcrumb-item active">แก้ไขสมัครใช้บริการ</li>
					</ol>


					<div class="card mt15">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0"><span class="ti-file"></span>&nbsp;เลขที่ใบสมัคร #${serviceApplicationBean.serviceApplicationNo }</h4>
							<div class="float-xs-right"><span class="ti-calendar"></span>&nbsp;วันที่สมัคร ${serviceApplicationBean.createDateTh }</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
							
								<div class="row" style="display:none">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-user"></span> ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											
											<jsp:include page="newcustomers.jsp"></jsp:include>
											
										</fieldset>
									</div>
								</div>
							
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-user"></span> ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div id="oldcustomers">
												<div class="row ">
													<div class="col-md-6">
														<label for=""><b>บริษัท<span class="text-red"> *</span></b></label> <select
															class="form-control" id="edit-company" name="edit_company">
															<c:forEach var="company" items="${companys}" varStatus="i">
																<option value="${company.id}">${company.companyName}</option>
															</c:forEach>
														</select>
											
														<div class="messages"></div>
													</div>
												</div>
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
														<div class="col-md-6">
															<b>ที่อยู่ตามทะเบียนบ้าน</b><br>
																${addressBean.collectAddressDetail }
														</div>
														<div class="col-md-6">
															<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
														</div>
														</c:if>
													</c:forEach>
												</div>
												<div class="row mt05">
													<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
														<c:if test="${addressBean.addressType eq '2' }">
														<div class="col-md-6">
															<b>ที่อยู่ปัจจุบัน</b><br>
																${addressBean.collectAddressDetail }
														</div>
														<div class="col-md-6">
															<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
														</div>
														</c:if>
													</c:forEach>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<br/>
								<fieldset>
									<legend>&nbsp;&nbsp;&nbsp;<span class="ti-id-badge"></span> เอกสาประกอบการสมัครสมาชิก&nbsp;&nbsp;&nbsp;</legend>
									<div class="row">
										<div class="col-md-4">
											
											<label class="custom-control custom-checkbox">
											
											<c:if test="${!serviceApplicationBean.houseRegistrationDocuments}">
												<input id="checkbox_house_registration_documents" type="checkbox" class="custom-control-input"> 
											</c:if>
											<c:if test="${serviceApplicationBean.houseRegistrationDocuments}">
												<input id="checkbox_house_registration_documents" type="checkbox" class="custom-control-input" checked="checked"> 
											</c:if>
											
											<span class="custom-control-indicator"></span> 
											<span class="custom-control-description"></span>
											</label> <label for="exampleInputEmail1"><b>สำเนาทะเบียนบ้าน</b></label>
<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="house_registration_documents" class="custom-file-input"> -->
<!-- 												<span id="house_regis_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
										</div>
										<div class="col-md-4">											
											<label class="custom-control custom-checkbox">
											
											<c:if test="${!serviceApplicationBean.identityCardDocuments}">
												<input id="checkbox_identity_card_documents" type="checkbox" class="custom-control-input">  
											</c:if>
											<c:if test="${serviceApplicationBean.identityCardDocuments}">
												<input id="checkbox_identity_card_documents" type="checkbox" class="custom-control-input" checked="checked">  
											</c:if>
											
											<span class="custom-control-indicator"></span> 
											<span class="custom-control-description"></span>
											</label>
											<label for="exampleInputEmail1"><b>สำเนาบัตรประจำตัวประชาชน</b></label>
<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="identity_card_documents" class="custom-file-input"> -->
<!-- 												<span id="iden_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
										</div>
										<div class="col-md-4">											
											<label class="custom-control custom-checkbox">
											
											<c:if test="${!serviceApplicationBean.otherDocuments}">
												<input id="checkbox_other_documents" type="checkbox" class="custom-control-input">  
											</c:if>
											<c:if test="${serviceApplicationBean.otherDocuments}">
												<input id="checkbox_other_documents" type="checkbox" class="custom-control-input" checked="checked"> 
											</c:if>
											
											<span class="custom-control-indicator"></span> 
											<span class="custom-control-description"></span>
											</label>
											<label for="exampleInputEmail1"><b>เอกสารอื่นๆ</b></label>
<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="other_documents" class="custom-file-input"> -->
<!-- 												<span id="other_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
										</div>
									</div>		
									<div class="row">
										<div class="col-md-12">
											<textarea class="form-control" <c:if test="${!serviceApplicationBean.otherDocuments}">style="display:none"</c:if> id="remark_other_documents" rows="3">${serviceApplicationBean.remarkOtherDocuments}</textarea>
										</div>
									</div>
								</fieldset>
								
<%-- 								<jsp:include page="block_package.jsp"></jsp:include> --%>
								
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
																		<th style="vertical-align: middle;"><center>รอบบิลแรกลดให้</center></th>
																		<th><center>
																				ค่าบริการ / ${serviceApplicationBean.perMonth } เดือน
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
<%-- 																				<center>${serviceApplicationBean.firstBillFree } เดือน</center> --%>
<!-- 																			</div></td> -->
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.firstBillFreeDisCount}" /> บาท</center>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${serviceApplicationBean.monthlyServiceFee}" /> บาท</center>
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
								
								
								<jsp:include page="block_detail_install.jsp"></jsp:include>
								
								<c:if test="${serviceApplicationBean.status.stringValue eq 'D'}">
								<jsp:include page="edit/block_equipment_install.jsp"></jsp:include> 
								</c:if>
								
								<jsp:include page="block_shipping_address.jsp"></jsp:include>
								
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-write"></span> หมายเหตุ /
												ข้อมูลเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
											<textarea class="form-control" id="remark" rows="3">${serviceApplicationBean.remark}</textarea>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center>
											<button type="button" onclick="submitData('${serviceApplicationBean.status.stringValue}');" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
												<span class="btn-label"><span class="ti-check"></span></span>
												<b>บันทึกการปรับปรุงข้อมูล</b>
											</button>
										</center>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<jsp:include page="modal_productItem.jsp"></jsp:include>

			<!-- modal search product -->
			<jsp:include page="modal_product.jsp"></jsp:include>
			<!-- end modal search product -->
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
	
<script type="text/javascript">
var dataAddress1 = [];
var dataAddress2 = [];
var dataAddress3 = [];
var dataAddress4 = [];
var dataAddress5 = [];
var file_map,file_house,file_identity_card,file_other;
var monthlyService = false;
$( document ).ready(function() {
	
// 	document.getElementById('install_map').addEventListener('change', handleFileSelect, false);
// 	document.getElementById('house_registration_documents').addEventListener('change', handleFileSelectHouse, false);
// 	document.getElementById('identity_card_documents').addEventListener('change', handleFileSelectIden, false);
// 	document.getElementById('other_documents').addEventListener('change', handleFileSelectOther, false);
	
	
	$('#career').val("${serviceApplicationBean.customer.careerBean.id}");
	
	$('#customerType').val("${serviceApplicationBean.customer.custTypeReal}");
	
	$('#customerFeatures').val("${serviceApplicationBean.customer.customerFeatureBean.id}");
	
	$('#service_application_type').val("${serviceApplicationBean.serviceApplicationTypeBean.id}");
	
	$('#edit-company').val("${serviceApplicationBean.companyBean.id}");
	
	<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '1' }">
	$('#province1').val("${addressBean.provinceBean.id}");
	changeProvince1("${addressBean.provinceBean.id}","${addressBean.amphurBean.id}","${addressBean.districtBean.id}");
	</c:if>
	</c:forEach>
	
	<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '2' }">
	<c:if test="${empty addressBean.overrideAddressId}">
		$('#checkbox_no_current_address').click();
		$('.no-current-address').fadeIn();
	</c:if>
	$('#province2').val("${addressBean.provinceBean.id}");
	changeProvince2("${addressBean.provinceBean.id}","${addressBean.amphurBean.id}","${addressBean.districtBean.id}");
	</c:if>
	</c:forEach>
	
	$('#select_service_type').val("${serviceApplicationBean.servicepackage.serviceType.id}");
	selectServiceTypeChange("${serviceApplicationBean.servicepackage.serviceType.id}","${serviceApplicationBean.servicepackage.id}");
	selectProductitemList("${serviceApplicationBean.id}");
	
	<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '3' }">
	<c:if test="${empty addressBean.overrideAddressId}">
	$('.detail-install-no-current-address').fadeIn();
	</c:if>
	$('#select_address1').val("${addressBean.overrideAddressId}");
	
	$('#province3').val("${addressBean.provinceBean.id}");
	changeProvince3("${addressBean.provinceBean.id}","${addressBean.amphurBean.id}","${addressBean.districtBean.id}");
	
	$('#select_zone').val("${addressBean.zoneBean.id}");
	</c:if>
	</c:forEach>
	
	<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '4' }">
	<c:if test="${empty addressBean.overrideAddressId}">
	$('.shipping-address-no-current-address').fadeIn();
	</c:if>
	$('#select_address2').val("${addressBean.overrideAddressId}");
	
	$('#province4').val("${addressBean.provinceBean.id}");
	changeProvince4("${addressBean.provinceBean.id}","${addressBean.amphurBean.id}","${addressBean.districtBean.id}");

	</c:if>
	</c:forEach>
	
	<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '5' }">
	<c:if test="${empty addressBean.overrideAddressId}">
	$('.invoice-address-no-current-address').fadeIn();
	</c:if>
	$('#select_address3').val("${addressBean.overrideAddressId}");
	
	$('#province5').val("${addressBean.provinceBean.id}");
	changeProvince5("${addressBean.provinceBean.id}","${addressBean.amphurBean.id}","${addressBean.districtBean.id}");

	</c:if>
	</c:forEach>
	
// 	function handleFileSelect(evt) {
// 	      evt.stopPropagation();
// 	      evt.preventDefault();
// 	      file_map = evt.target.files[0];
// 	      console.log(file_map);
// 	      if(typeof file_map !== "undefined"){
// 			  var install_map_span = $('#install_map_span');
// 			  install_map_span.addClass('choose-custom-file-control');
// 	    	  if(file_map.name.length > 25){
// 	    		  var name = file_map.name.substring(0,25);
// 	    		  install_map_span.text(name+"...");
// 	    	  }else{
// 	    		  install_map_span.text(file_map.name);
// 	    	  }

// 	      }
// 	}
	
	function handleFileSelectHouse(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_house = evt.target.files[0];
	      if(typeof file_house !== "undefined"){
			  var house_regis_span = $('#house_regis_span');
			  house_regis_span.addClass('choose-custom-file-control');
	    	  if(file_house.name.length > 25){
	    		  var name = file_house.name.substring(0,25);
	    		  house_regis_span.text(name+"...");
	    	  }else{
	    		  house_regis_span.text(file_house.name);
	    	  }

	    	  $('#checkbox_house_registration_documents').prop('checked', true);
	    	  
	      }
	}
	
	function handleFileSelectIden(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_identity_card = evt.target.files[0];
	      if(typeof file_identity_card !== "undefined"){
			  var iden_span = $('#iden_span');
			  iden_span.addClass('choose-custom-file-control');
	    	  if(file_identity_card.name.length > 25){
	    		  var name = file_identity_card.name.substring(0,25);
	    		  iden_span.text(name+"...");
	    	  }else{
	    		  iden_span.text(file_identity_card.name);
	    	  }

	    	  $('#checkbox_identity_card_documents').prop('checked', true);
	    	  
	      }
	}
	
	function handleFileSelectOther(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_other = evt.target.files[0];
	      if(typeof file_other !== "undefined"){
			  var other_span = $('#other_span');
			  other_span.addClass('choose-custom-file-control');
	    	  if(file_other.name.length > 25){
	    		  var name = file_other.name.substring(0,25);
	    		  other_span.text(name+"...");
	    	  }else{
	    		  other_span.text(file_other.name);
	    	  }

	    	  $('#checkbox_other_documents').prop('checked', true);
	    	  
	      }
	}

	$('input[name="daterange"]').daterangepicker({
		timePicker: true,
		timePicker24Hour: true,
// 		timePickerIncrement: 30,
		locale: {
			format: 'DD/MM/YYYY HH:mm'
		},
		startDate: '"${serviceApplicationBean.startDate}"',
	    endDate: '"${serviceApplicationBean.endDate}"'
	});
	
	//Begin Address 1
	//Begin change Province
	$('#province1').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress1 = data;
// 					console.log(dataAddress1);
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress1.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur1').html(option);
					$('#postcode1').val('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur1').val("");
					changeAmphur1("","");
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
	//End change Province
	function changeProvince1(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress1 = data;
// 					console.log(dataAddress1);
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress1.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur1').html(option);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur1').val(amphurId);
					changeAmphur1(amphurId,districtId);
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
	
	//Begin change Amphur
	$('#amphur1').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress1){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress1.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#postcode1').val('');
				}
			});
			$('#district1').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district1').val("");
		}
	});
	//End change Amphur
	function changeAmphur1(id,districtId) {
		if(dataAddress1){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress1.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#district1').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district1').val(districtId);
		}
	}
	//Begin change District
	$('#district1').change(function(){
		var id = $(this).val();
		if("" != id){
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
							$('#postcode1').val(data.result.zip_CODE);
						}else{
							$('#postcode1').val('');
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
		}
	});

	//End change District
	//End Address 1

	//Begin Address 2
	//Begin change Province
	$('#province2').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress2 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress2.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur2').html(option);
					$('#postcode2').val('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur2').val("");
					changeAmphur2("","");
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
	
	function changeProvince2(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress2 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress2.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur2').html(option);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur2').val(amphurId);
					changeAmphur2(amphurId,districtId);
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
	$('#amphur2').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress2){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress2.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#postcode2').val('');
				}
			});
			$('#district2').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district2').val("");
		}
	});
	
	function changeAmphur2(id,districtId) {
		if(dataAddress2){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress2.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#district2').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district2').val(districtId);
		}
	}
	//End change Amphur
	//Begin change District
	$('#district2').change(function(){
		var id = $(this).val();
		changeDistrict2(id);
	});
	
	function changeDistrict2(id) {
		if("" != id){
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
							$('#postcode2').val(data.result.zip_CODE);
						}else{
							$('#postcode2').val('');
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
		}
	}
	//End change District
	//End Address 2
	
	//Begin Address 3
	//Begin change Province
	$('#province3').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress3 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress3.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur3').html(option);
					$('#postcode3').val('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur3').val("");
					changeAmphur3("","");
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
	function changeProvince3(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress3 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress3.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur3').html(option);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur3').val(amphurId);
					changeAmphur3(amphurId,districtId);
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
	$('#amphur3').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress3){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress3.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#postcode3').val('');
				}
			});
			$('#district3').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district3').val("");
		}
	});
	function changeAmphur3(id,districtId) {
		if(dataAddress3){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress3.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#district3').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district3').val(districtId);
		}
	}
	//End change Amphur
	//Begin change District
	$('#district3').change(function(){
		var id = $(this).val();
		if("" != id){
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
							$('#postcode3').val(data.result.zip_CODE);
						}else{
							$('#postcode3').val('');
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
		}
	});
	//End change District
	//End Address 3
	
	//Begin Address 4
	//Begin change Province
	$('#province4').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress4 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress4.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur4').html(option);
					$('#postcode4').val('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur4').val("");
					changeAmphur4("","");
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
	function changeProvince4(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress4 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress4.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur4').html(option);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur4').val(amphurId);
					changeAmphur4(amphurId,districtId);
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
	$('#amphur4').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress4){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress4.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#postcode4').val('');
				}
			});
			$('#district4').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district4').val("");
		}
	});
	function changeAmphur4(id,districtId) {
		if(dataAddress4){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress4.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#district4').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district4').val(districtId);
		}
	}
	//End change Amphur
	//Begin change District
	$('#district4').change(function(){
		var id = $(this).val();
		if("" != id){
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
							$('#postcode4').val(data.result.zip_CODE);
						}else{
							$('#postcode4').val('');
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
		}
	});
	//End change District
	//End Address 4
	
	//Begin Address 5
	//Begin change Province
	$('#province5').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress5 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress5.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur5').html(option);
					$('#postcode5').val('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur5').val("");
					changeAmphur5("","");
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
	function changeProvince5(id,amphurId,districtId) {
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getAmphurByProvince/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					dataAddress5 = data;
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
					$.each(dataAddress5.result.amphurBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
					});
					
					$('#amphur5').html(option);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					// selected amphur
					$('#amphur5').val(amphurId);
					changeAmphur5(amphurId,districtId);
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
	$('#amphur5').change(function(){
		var id = $(this).val();
		$('.preloader').modal('show');
		if(dataAddress5){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress5.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
					$('#postcode5').val('');
				}
			});
			$('#district5').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district5').val("");
		}
	});
	function changeAmphur5(id,districtId) {
		if(dataAddress5){
			var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
			$.each(dataAddress5.result.amphurBeans, function( index, val ) {
				if(id==val.id){
					$.each(val.districtBeans, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
					});
				}
			});
			$('#district5').html(option);
			setTimeout(function() {$('.preloader').modal('hide');}, 200);
			$('#district5').val(districtId);
		}
	}
	//End change Amphur
	//Begin change District
	$('#district5').change(function(){
		var id = $(this).val();
		if("" != id){
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
							$('#postcode5').val(data.result.zip_CODE);
						}else{
							$('#postcode5').val('');
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
		}
	});
	//End change District
	//End Address 5
	
	$('input[name=memberType]').change(function() {
		if (this.value == 'old') {
			$('#oldcustomers').fadeIn();
			$('#newcustomers').hide();
			$('#oldcustomersdetail').hide();
		} else {
			$('#oldcustomers').hide();
			$('#newcustomers').fadeIn();
			$('#oldcustomersdetail').hide();
		}
		$('#customerId').val('');
		
		$('#no1').val('');
		$('#section1').val('');
		$('#building1').val('');
		$('#room1').val('');
		$('#floor1').val('');
		$('#village1').val('');
		$('#alley1').val('');
		$('#road1').val('');
		$('#province1').val('');
		$('#amphur1').val('');
		$('#district1').val('');
		$('#postcode1').val('');
		$('#nearbyPlaces1').val('');
		
		$('#no2').val('');
		$('#section2').val('');
		$('#building2').val('');
		$('#room2').val('');
		$('#floor2').val('');
		$('#village2').val('');
		$('#alley2').val('');
		$('#road2').val('');
		$('#province2').val('');
		$('#amphur2').val('');
		$('#district2').val('');
		$('#postcode2').val('');
		$('#nearbyPlaces2').val('');
		
	});

	$('#checkbox_no_current_address').click(function () {
		if($(this).is(":checked")){
			$('.no-current-address').fadeIn();
		}else{
			$('.no-current-address').hide();
		}
	});
	
	$('#select_address1').change(function () {
		if(!$(this).val()){
			$('.detail-install-no-current-address').fadeIn();
		}else{
			$('.detail-install-no-current-address').hide();
		}
	});
	
	$('#select_address2').change(function () {
		if(!$(this).val()){
			$('.shipping-address-no-current-address').fadeIn();
		}else{
			$('.shipping-address-no-current-address').hide();
		}
	});
	
	$('#select_address3').change(function () {
		if(!$(this).val()){
			$('.invoice-address-no-current-address').fadeIn();
		}else{
			$('.invoice-address-no-current-address').hide();
		}
	});
	
	$('#select_service_type').change(function () {
		var service_type_id = $(this).val();
		$('#table_template_equiment tbody').html("");
		selectServiceTypeChange(service_type_id,"");
	});
	
	function selectServiceTypeChange(service_type_id,service_id){
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getServicePackage/"+service_type_id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือก Package ---</option>';
					$.each(data.result, function( index, value ) {
						option += '<option value="'+value.id+'">'+value.packageName+'</option>';
					});
					
					$('#service_packages').html(option);
// 					console.log(data);
// 					console.log(data.result[0].perMounth);
					$('#priceservice').html("ค่าบริการรอบบิล / " + data.result[0].perMounth + " เดือน");
// 					$('.div-detail-package').hide();
// 					$('.div-detail-package-ismonthlyservice').fadeIn();
					
					$('#table_template tbody').html('');
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					$('#service_packages').val(service_id);
					
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
	
// 	function selectProductitemList(id){
// 		$('.preloader').modal('show');
// 		$.ajax({
// 			type : "GET",
// 			contentType : "application/json; charset=utf-8",
// 			url : "${pageContext.request.contextPath}/serviceapplicationlist/getProductitem/"+id,
// 			dataType : 'json',
// 			//timeout : 100000,
// 			success : function(data) {
// 				if(data["error"] == false){
// 					console.log(data);
					
// 					<c:if test="${serviceApplicationBean.monthlyService}">
// 					$('.div-detail-package').hide();
// 					$('.div-detail-package-ismonthlyservice').fadeIn();
// 					monthlyService = true;
// 					</c:if>
					
					
// 					<c:if test="!${serviceApplicationBean.monthlyService}">
// 					$('.div-detail-package-ismonthlyservice').hide();
// 					$('.div-detail-package').fadeIn();
// 					monthlyService = false;
// 					</c:if>
					
// 					var table_template_equiment = '';
// 					var table_template_internet = '';
// 					var table_template_service = '';
// 					var index_e = 1, index_i = 1, index_s = 1;
// 					// รายชื่อวัสดุอุปกรณ์ที่ติดตั้ง
// 					$.each(data.result, function( index, value ) {
// 						var checkedFree = "",checkedLend = "";
// 						var totalPrice = 0;
// 						var salePrice = value.price;
						
						
// 						totalPrice = value.price*value.quantity;
						
// 						// เช็ค ฟรี หรือ ยืม
// 						if(value.free==true){
// 							checkedFree = "checked=\"checked\"";
// 							totalPrice = 0;
// 						}
// 						else if(value.lend==true){
// 							checkedLend = "checked=\"checked\"";
// 							totalPrice = 0;
// 						}
// // 						console.log(value);
// 						var sizeLoop = 1;
// 						var quantity = value.quantity;
// 						if("E"==value.type){
// 							$.each(value.productItemWorksheetBeanList, function( index, val ) {
// 						table_template_equiment += '<tr>\
// 							<td><center><span class="template-no">'+(index_e++)+'</span></center>\
// 							<input class="productId" type="hidden" value="'+value.product.id+'"></td>\
// 							<input class="productType" type="hidden" value="'+value.type+'">\
// 							<input id="productItemWorksheetId_'+(index_e-1)+'" class="productItemWorksheetId" type="hidden" value="'+val.id+'">\
// 							<input id="productItemId_'+(index_e-1)+'" class="productItemId" type="hidden" value="'+(val.equipmentProductItemBean==null?"":val.equipmentProductItemBean.id)+'">\
// 							<td><center>'+(value.product.productCode||'')+'</center></td>\
// 							<td><center><span class="serial-no" id="serial_'+(index_e-1)+'">'+(val.equipmentProductItemBean==null?"":val.equipmentProductItemBean.serialNo)+'</span></center></td>\
// 							<td>'+value.product.productName+'</td>\
// 							<td scope="row"><div class="input-group">\
// 									<input type="number" class="form-control salePrice calculate" value="'+val.price+'" >\
// 									<div class="input-group-addon">บาท</div>\
// 								</div></td>\
// 							<td align="center" style="vertical-align: middle;">\
// 									<label class="custom-control custom-checkbox">\
// 										<input type="checkbox" class="custom-control-input check-box-free"\
// 										'+checkedFree+'> <span\
// 										class="custom-control-indicator"></span> <span\
// 										class="custom-control-description"></span>\
// 									</label>\
// 							</td>\
// 							<td align="center" style="vertical-align: middle;">\
// 									<label class="custom-control custom-checkbox">\
// 										<input type="checkbox" class="custom-control-input check-box-lend"\
// 										'+checkedLend+'> <span\
// 										class="custom-control-indicator"></span> <span\
// 										class="custom-control-description"></span>\
// 									</label>\
// 							</td>\
// 							<td scope="row"><div class="input-group">\
// 									<input type="number" class="form-control totalPrice" value="'+val.price+'" disabled>\
// 									<div class="input-group-addon">บาท</div>\
// 								</div></td>\
// 							<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
// 									class="ti-trash remove-template" data-toggle="tooltip"\
// 									data-placement="bottom" title=""\
// 									data-original-title="ลบ"></span></a>\
// 						<br><a class="run-swal cursor-p"\
// 						onclick="loadProductItemEdit('+value.product.id+',\''+value.type+'\',\''+(index_e-1)+'\')" ><span\
// 						class="ti-plus" data-toggle="tooltip"\
// 						data-placement="bottom" title=""\
// 						data-original-title="เพิ่มรายชิ้น"></span></a></td></tr>';
// 							});
// 						}else if("I"==value.type){
// 							$.each(value.productItemWorksheetBeanList, function( index, val ) {
// 								table_template_internet += '<tr>\
// 									<td><center><span class="template-no">'+(index_i++)+'</span></center>\
// 									<input class="productId" type="hidden" value="'+value.product.id+'"></td>\
// 									<input class="productType" type="hidden" value="'+value.type+'">\
// 									<input id="productItemWorksheetId_'+(index_i-1)+'" class="productItemWorksheetId" type="hidden" value="'+val.id+'">\
// 									<input id="internetId_'+(index_i-1)+'" class="productItemId" type="hidden" value="'+(val.internetProductBeanItem==null?"":val.internetProductBeanItem.id)+'">\
// 									<td><center>'+(value.product.productCode||'')+'</center></td>\
// 									<td>'+value.product.productName+'</td>\
// 									<td><span class="internet-username" id="span_username_'+(index_i-1)+'">'+(val.internetProductBeanItem==null?"":val.internetProductBeanItem.userName)+'</span></td>\
// 									<td><span class="internet-password" id="span_password_'+(index_i-1)+'">'+(val.internetProductBeanItem==null?"":val.internetProductBeanItem.password)+'</span></td>\
// 									<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
// 											class="ti-trash remove-template" data-toggle="tooltip"\
// 											data-placement="bottom" title=""\
// 											data-original-title="ลบ"></span></a>\
// 									<br><a class="run-swal cursor-p"\
// 									onclick="loadInternetItemEdit('+value.product.id+',\''+value.type+'\',\''+(index_i-1)+'\')" ><span\
// 									class="ti-plus" data-toggle="tooltip"\
// 									data-placement="bottom" title=""\
// 									data-original-title="เพิ่มรายชิ้น"></span></a></td></tr>';
// 									});
// 						}else if("S"==value.type){
// 							table_template_service += '<tr>\
// 								<td><center><span class="template-no">'+(index_s++)+'</span></center>\
// 								<input class="productId" type="hidden" value="'+value.product.id+'"></td>\
// 								<input class="productType" type="hidden" value="'+value.type+'">\
// 								<td>'+value.product.productName+'</td>\
// 								<td scope="row"><div class="input-group">\
// 										<input type="number" class="form-control quantity calculate" value="'+quantity+'" >\
// 										<div class="input-group-addon">'+value.product.unit.unitName+'</div>\
// 									</div></td>\
// 								<td scope="row"><div class="input-group">\
// 										<input type="number" class="form-control salePrice calculate" value="'+salePrice+'" >\
// 										<div class="input-group-addon">บาท</div>\
// 									</div></td>\
// 								<td align="center" style="vertical-align: middle;">\
// 										<label class="custom-control custom-checkbox">\
// 											<input type="checkbox" class="custom-control-input check-box-free"\
// 											'+checkedFree+'> <span\
// 											class="custom-control-indicator"></span> <span\
// 											class="custom-control-description"></span>\
// 										</label>\
// 								</td>\
// 								<td scope="row"><div class="input-group">\
// 										<input type="number" class="form-control totalPrice" value="'+totalPrice+'" disabled>\
// 										<div class="input-group-addon">บาท</div>\
// 									</div></td>\
// 								<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
// 										class="ti-trash remove-template" data-toggle="tooltip"\
// 										data-placement="bottom" title=""\
// 										data-original-title="ลบ"></span></a>\</td></tr>';
// 						}
// 					});
					
					
// 					$('#count_div_equiment').text(index_e-1);
// 					$('#count_div_internet').text(index_i-1);
// 					$('#count_div_service').text(index_s-1);
					
// 					$('#table_template_equiment tbody').html(table_template_equiment);
// 					$('#table_template_internet tbody').html(table_template_internet);
// 					$('#table_template_service tbody').html(table_template_service);
					
// 					calculate();
					
// 					setTimeout(function() {$('.preloader').modal('hide');}, 200);
// 				}
// 			},
// 			error : function(e) {
// 				console.log("ERROR: ", e);
// 			},
// 			done : function(e) {
// 				console.log("DONE");
// 			}
// 		});
// 	}
	
	$('#service_packages').change(function () {
		var id = $(this).val();
		$('#table_template_equiment tbody').html("");
		selectServiceChange(id);
	});
	
	function selectServiceChange(id){
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getServicePackageDetail/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					console.log(data);

					// รายละเอียด Package
					if(data.result.monthlyService===true){
						$('#installationFee_ismonthlyservice').val(data.result.installationFee);
						$('#deposit_ismonthlyservice').val(data.result.deposit);
						$('#firstBillFree_ismonthlyservice').val(data.result.firstBillFree);
						$('#firstBillFreeDisCount_ismonthlyservice').val(data.result.firstBillFreeDisCount);
						$('#monthlyServiceFee_ismonthlyservice').val(data.result.monthlyServiceFee);
						$('#perMonth_ismonthlyservice').val(data.result.perMounth);
						
						$('.div-detail-package').hide();
						$('.div-detail-package-ismonthlyservice').fadeIn();
						
						monthlyService = true;
					}else{
						$('#installationFee').val(data.result.installationFee);
						$('#deposit').val(data.result.deposit);
						$('#oneServiceFee').val(data.result.oneServiceFee);
						
						$('.div-detail-package-ismonthlyservice').hide();
						$('.div-detail-package').fadeIn();
						
						monthlyService = false;
					}
					
					var table_template = '';
					// รายชื่อวัสดุอุปกรณ์ที่ติดตั้ง
					$.each(data.result.template.listTemplateServiceItemBean, function( index, value ) {
						var checkedFree = "",checkedLend = "";
						var totalPrice = 0;
						var salePrice = 0;
						var equipmentProductItemBeans = value.productBean.equipmentProductItemBeans;
						var internetProductBeanItems = value.productBean.internetProductBeanItems;
						
						if(typeof equipmentProductItemBeans !== "undefined" && equipmentProductItemBeans.length > 0){
							salePrice = equipmentProductItemBeans[0].salePrice;
						}
						else if(typeof internetProductBeanItems !== "undefined" && internetProductBeanItems.length > 0){
							salePrice = 0;
						}
						else if(value.productBean.type = 'S'){
							salePrice = value.productBean.price;
						}
						
						totalPrice = salePrice*value.quantity;
						
						// เช็ค ฟรี หรือ ยืม
						if(value.free==true){
							checkedFree = "checked=\"checked\"";
							totalPrice = 0;
						}
						else if(value.lend==true){
							checkedLend = "checked=\"checked\"";
							totalPrice = 0;
						}
						
						table_template += '<tr>\
							<td><center><span class="template-no">'+(index+1)+'</span></center>\
							<input class="productId" type="hidden" value="'+value.productBean.id+'"></td>\
							<input class="productType" type="hidden" value="'+value.type+'">\
							<td><center>'+(value.productBean.productCode||'')+'</center></td>\
							<td>'+value.productBean.productName+'</td>\
							<td scope="row"><div class="input-group">\
									<input type="number" class="form-control quantity calculate" value="'+value.quantity+'" >\
									<div class="input-group-addon">'+value.productBean.unit.unitName+'</div>\
								</div></td>\
							<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
								class="ti-trash remove-template" data-toggle="tooltip"\
								data-placement="bottom" title=""\
								data-original-title="ลบ"></span></a>\
								</td>\
							</tr>';
								
// 							<td scope="row"><div class="input-group">\
// 									<input type="number" class="form-control salePrice calculate" value="'+salePrice+'" >\
// 									<div class="input-group-addon">บาท</div>\
// 								</div></td>\
// 							<td align="center" style="vertical-align: middle;">\
// 									<label class="custom-control custom-checkbox">\
// 										<input type="checkbox" class="custom-control-input check-box-free"\
// 										'+checkedFree+'> <span\
// 										class="custom-control-indicator"></span> <span\
// 										class="custom-control-description"></span>\
// 									</label>\
// 							</td>\
// 							<td align="center" style="vertical-align: middle;">\
// 									<label class="custom-control custom-checkbox">\
// 										<input type="checkbox" class="custom-control-input check-box-lend"\
// 										'+checkedLend+'> <span\
// 										class="custom-control-indicator"></span> <span\
// 										class="custom-control-description"></span>\
// 									</label>\
// 							</td>\
// 							<td scope="row"><div class="input-group">\
// 									<input type="number" class="form-control totalPrice" value="'+totalPrice+'" disabled>\
// 									<div class="input-group-addon">บาท</div>\
// 								</div></td>\
// 							<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
// 									class="ti-trash remove-template" data-toggle="tooltip"\
// 									data-placement="bottom" title=""\
// 									data-original-title="ลบ"></span></a>\
// 							</td>\
// 						</tr>';
					});
					
					$('#table_template_equiment tbody').html(table_template);
					autoInsertEquiment();
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
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
	
	$(document).on('click', '.check-box-lend', function(event) {
		if($(this).is(":checked")){
			$(this).parent().parent().parent().find('.check-box-free').prop('checked', false);
		}
		calculate();
	});
	
	$(document).on('click', '.check-box-free', function(event) {
		if($(this).is(":checked")){
			$(this).parent().parent().parent().find('.check-box-lend').prop('checked', false);
		}
		calculate();
	});
	
	$(document).on('blur', '.calculate', function(event) {
		calculate();
	});
	
	$(document).on('click', '.remove-template', function(event) {
		$(this).parent().parent().parent().remove();
		calculate();
	});
	
});

function calculate(){
	
	$.each($('#table_template_equiment tbody tr'), function( index, value ) {
// 		console.log($(this));
		var templateNo = $(this).find('.template-no');
		var quantity = $(this).find('.quantity');
		var salePrice = $(this).find('.salePrice');
		var checkBoxFree = $(this).find('.check-box-free');
		var checkBoxLend = $(this).find('.check-box-lend');
		var totalPrice = $(this).find('.totalPrice');
		
		// set No
		templateNo.text(index+1);
		
		if(!quantity.val() || quantity.val()==0){
			quantity.val('1');
		}
		if(!salePrice.val()){
			salePrice.val('0');
		}
		
		if(checkBoxFree.is(":checked") || checkBoxLend.is(":checked")){
			totalPrice.val('0');
		}else{
			totalPrice.val(salePrice.val());
		}

	});
	
	autoInsertEquiment();
}

function autoInsertEquiment(){
	var serviceApplicationBean = {};
	var productitemList = [];
	$('#table_template_equiment > tbody  > tr').each(function() {
		var item = {};
		 $(this).find('input').each(function (i) {
				switch (i) {
						case 0:
							item.id = $(this).val();;
							break;
						case 1:
							item.type = $(this).val();
							break;
						case 2:
							item.quantity = $(this).val();
							break;
						case 3:
							item.price = $(this).val();
							break;
						case 4:
							if($(this).is(":checked")){
								item.free = true;
							}else{
								item.free = false;
							} 
							break;
						case 5:
							if($(this).is(":checked")){
								item.lend = true;
							}else{
								item.lend = false;
							}  
							break;
						case 6:
							item.amount = $(this).val();
							break;
						}
				
					}); //end find textbox
					
		 			//add to arrayjson
		 			productitemList.push(item);
				}); //end find tr
				
	serviceApplicationBean.productitemList = productitemList;
	serviceApplicationBean.id = "${serviceApplicationBean.id}";
	console.log(serviceApplicationBean);
	
	$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/serviceapplicationlist/autoInsertEquiment/",
		dataType : 'json',
		data : JSON.stringify(serviceApplicationBean),
		//timeout : 100000,
		success : function(data) {
			if(data.result){
				selectProductitemList("${serviceApplicationBean.id}");
			}
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

function submitData(status){
	$('.preloader').modal('show');
	var file_default = new File([""], "filename");
	
	var formData = new FormData();
	if(typeof file_map === "undefined"){
		formData.append("file_map", file_default);
	}else{
		formData.append("file_map", file_map);
	}
	
	if(typeof file_house === "undefined"){
		formData.append("file_house", file_default);
	}else{
		formData.append("file_house", file_house);
	}
	
	if(typeof file_identity_card === "undefined"){
		formData.append("file_identity_card", file_default);
	}else{
		formData.append("file_identity_card", file_identity_card);
	}
	
	if(typeof file_other === "undefined"){
		formData.append("file_other", file_default);
	}else{
		formData.append("file_other", file_other);
	}

	var serviceApplicationBean = generateBeanJson(status);
	
	var companyBean = {};
	companyBean.id = $('#edit-company').val();
	serviceApplicationBean.companyBean = companyBean;
	
	console.log(serviceApplicationBean);
	
	serviceApplicationBean = JSON.stringify(serviceApplicationBean);
    formData.append("serviceApplicationBean", serviceApplicationBean);
    
	$.ajax({dataType : 'json',
		contentType : "application/json; charset=utf-8",
    	url : "${pageContext.request.contextPath}/serviceapplicationlist/update",
        data : formData,
        type : "POST",
        enctype: 'multipart/form-data',
        processData: false,
        contentType:false,
        async : false,
        cache: false,
        success : function(data) {
			if (data["error"] == false) {
				window.location.href = "${pageContext.request.contextPath}/serviceapplicationlist";
			} else {

			}
		},
		error : function(e) {
			console.log("ERROR: ", e);
			var n = e.responseText.indexOf("MaxUploadSizeExceededException");
			if(n > 0){
				$('.preloader').modal('hide');
				openAlert('warning','ไฟล์ที่อัพโหลดมีขนาดใหญ่เกินที่ระบบกำหนดไว้');
			}
		},
		done : function(e) {
			console.log("DONE");
		}
    });

}

function generateBeanJson(statusSubmit){
	var serviceApplicationBean = {};
	var multipartFiles = [];
	
	// set data customer
	var customer = {};
	customer.id = $('#customerId').val();
	customer.firstName = $('#firstName').val();
	customer.lastName = $('#lastName').val();
	customer.identityNumber = $('#identityNumber').val();
	customer.prefix = $('#prefix').val();
	customer.sex = $('input[name=sex]:checked').val();
	
	var careerBean = {};
	careerBean.id = $('#career').val();
	customer.careerBean = careerBean;
	
	var customerType = {};
	customerType.value = $('#customerType').val();
	customer.customerType = customerType;
	
	var customerFeatureBean = {};
	customerFeatureBean.id = $('#customerFeatures').val();
	customer.customerFeatureBean = customerFeatureBean;
	
	var contact = {};
	contact.mobile = $('#mobile').val();
	contact.email = $('#email').val();
	contact.fax = $('#fax').val();
	customer.contact = contact;
	
	var addressList = [];
	var address1 = {};
	var address2 = {};
	var amphur1,district1,amphur2,district2,amphur3,district3,amphur4,district4,amphur5,district5;
	
	address1.id = $('#addressId1').val();
	address1.no = $('#no1').val();
	address1.section = $('#section1').val();
	address1.building = $('#building1').val();
	address1.room = $('#room1').val();
	address1.floor = $('#floor1').val();
	address1.village = $('#village1').val();
	address1.alley = $('#alley1').val();
	address1.road = $('#road1').val();
	
	var provinceBean = {};
	provinceBean.id = $('#province1').val();
	address1.provinceBean = provinceBean;
	var amphurBean = {};
	amphurBean.id = $('#amphur1').val();
	address1.amphurBean = amphurBean;
	var districtBean = {};
	districtBean.id = $('#district1').val();
	address1.districtBean = districtBean;
	address1.postcode = $('#postcode1').val();
	address1.nearbyPlaces = $('#nearbyPlaces1').val();
	address1.addressType = '1';
	
	amphur1 = $('#amphur1').val();
	district1 = $('#district1').val();
	
	addressList.push(address1);
	
	// เช็ค ที่อยู่ปัจจุบัน เป็นที่อยู่เดียวกันกับที่อยู่ในทะเบียนบ้าน ?

	if($('#checkbox_no_current_address').is(":checked")){
		// ที่อยู่บัจจุปัน ไม่ได้เป็นที่อยู่ทะเบียนบ้าน
		address2.id = $('#addressId2').val();
		address2.no = $('#no2').val();
		address2.section = $('#section2').val();
		address2.building = $('#building2').val();
		address2.room = $('#room2').val();
		address2.floor = $('#floor2').val();
		address2.village = $('#village2').val();
		address2.alley = $('#alley2').val();
		address2.road = $('#road2').val();
		var provinceBean = {};
		provinceBean.id = $('#province2').val();
		address2.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = $('#amphur2').val();
		address2.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = $('#district2').val();
		address2.districtBean = districtBean;
		address2.postcode = $('#postcode2').val();
		address2.nearbyPlaces = $('#nearbyPlaces2').val();
		address2.addressType = '2';
		
		amphur2 = $('#amphur2').val();
		district2 = $('#district2').val();
		
		addressList.push(address2);
	}else{

		$('#no2').val($('#no1').val());
		$('#section2').val($('#section1').val());
		$('#building2').val($('#building1').val());
		$('#room2').val($('#room1').val());
		$('#floor2').val($('#floor1').val());
		$('#village2').val($('#village1').val());
		$('#alley2').val($('#alley1').val());
		$('#road2').val($('#road1').val());
		$('#province2').val($('#province1').val());
		$('#amphur2').val($('#amphur1').val());
		$('#district2').val($('#district1').val());
		$('#postcode2').val($('#postcode1').val());
		$('#nearbyPlaces2').val($('#nearbyPlaces1').val());
		
		address2.id = $('#addressId2').val();
		address2.no = $('#no1').val();
		address2.section = $('#section1').val();
		address2.building = $('#building1').val();
		address2.room = $('#room1').val();
		address2.floor = $('#floor1').val();
		address2.village = $('#village1').val();
		address2.alley = $('#alley1').val();
		address2.road = $('#road1').val();
		var provinceBean = {};
		provinceBean.id = $('#province1').val();
		address2.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = $('#amphur1').val();
		address2.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = $('#district1').val();
		address2.districtBean = districtBean;
		address2.postcode = $('#postcode1').val();
		address2.nearbyPlaces = $('#nearbyPlaces1').val();
		address2.addressType = '2';
		address2.overrideAddressId = '1';
		
		amphur2 = amphur1;
		district2 = district1;
		
		addressList.push(address2);
	}

	
	customer.addressList = addressList;
	
	serviceApplicationBean.customer = customer;
	
	var servicepackage = {};
	servicepackage.id = $('#service_packages').val();
	serviceApplicationBean.servicepackage = servicepackage;
	
	if(monthlyService == true){
		serviceApplicationBean.installationFee = $('#installationFee_ismonthlyservice').val();
		serviceApplicationBean.deposit = $('#deposit_ismonthlyservice').val();
		serviceApplicationBean.firstBillFree = $('#firstBillFree_ismonthlyservice').val();
		serviceApplicationBean.firstBillFreeDisCount = $('#firstBillFreeDisCount_ismonthlyservice').val();
		serviceApplicationBean.monthlyServiceFee = $('#monthlyServiceFee_ismonthlyservice').val();
		serviceApplicationBean.perMonth = $('#perMonth_ismonthlyservice').val();
		
		serviceApplicationBean.monthlyService = true;
	}else{
		serviceApplicationBean.installationFee = $('#installationFee').val();
		serviceApplicationBean.deposit = $('#deposit').val();
		serviceApplicationBean.oneServiceFee = $('#oneServiceFee').val();
		
		serviceApplicationBean.monthlyService = false;
	}
	
	var addressList = [];
	var address3 = {};
	var address4 = {};
	var address5 = {};
	var select_address1 = $('#select_address1').val();
	if(select_address1 == 1){
		
		$('#no3').val($('#no1').val());
		$('#section3').val($('#section1').val());
		$('#building3').val($('#building1').val());
		$('#room3').val($('#room1').val());
		$('#floor3').val($('#floor1').val());
		$('#village3').val($('#village1').val());
		$('#alley3').val($('#alley1').val());
		$('#road3').val($('#road1').val());
		$('#province3').val($('#province1').val());
		$('#amphur3').val($('#amphur1').val());
		$('#district3').val($('#district1').val());
		$('#postcode3').val($('#postcode1').val());
		$('#nearbyPlaces3').val($('#nearbyPlaces1').val());
		
		address3.no = $('#no1').val();
		address3.section = $('#section1').val();
		address3.building = $('#building1').val();
		address3.room = $('#room1').val();
		address3.floor = $('#floor1').val();
		address3.village = $('#village1').val();
		address3.alley = $('#alley1').val();
		address3.road = $('#road1').val();
		var provinceBean = {};
		provinceBean.id = $('#province1').val();
		address3.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur1;
		address3.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district1;
		address3.districtBean = districtBean;
		address3.postcode = $('#postcode1').val();
		address3.nearbyPlaces = $('#nearbyPlaces1').val();
		address3.addressType = '3';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address3.zoneBean = zone;
		
		address3.overrideAddressId = '1';
		
		amphur3 = amphur1;
		district3 = district1;
		
		addressList.push(address3);
	}else if (select_address1 == 2){
		
		$('#no3').val($('#no2').val());
		$('#section3').val($('#section2').val());
		$('#building3').val($('#building2').val());
		$('#room3').val($('#room2').val());
		$('#floor3').val($('#floor2').val());
		$('#village3').val($('#village2').val());
		$('#alley3').val($('#alley2').val());
		$('#road3').val($('#road2').val());
		$('#province3').val($('#province2').val());
		$('#amphur3').val($('#amphur2').val());
		$('#district3').val($('#district2').val());
		$('#postcode3').val($('#postcode2').val());
		$('#nearbyPlaces3').val($('#nearbyPlaces2').val());
		
		address3.no = $('#no2').val();
		address3.section = $('#section2').val();
		address3.building = $('#building2').val();
		address3.room = $('#room2').val();
		address3.floor = $('#floor2').val();
		address3.village = $('#village2').val();
		address3.alley = $('#alley2').val();
		address3.road = $('#road2').val();
		var provinceBean = {};
		provinceBean.id = $('#province2').val();
		address3.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur2;
		address3.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district2;
		address3.districtBean = districtBean;
		address3.postcode = $('#postcode2').val();
		address3.nearbyPlaces = $('#nearbyPlaces2').val();
		address3.addressType = '3';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address3.zoneBean = zone;
		
		address3.overrideAddressId = '2';
		
		amphur3 = amphur2;
		district3 = district2;
		
		addressList.push(address3);
	}else{
		address3.no = $('#no3').val();
		address3.section = $('#section3').val();
		address3.building = $('#building3').val();
		address3.room = $('#room3').val();
		address3.floor = $('#floor3').val();
		address3.village = $('#village3').val();
		address3.alley = $('#alley3').val();
		address3.road = $('#road3').val();
		var provinceBean = {};
		provinceBean.id = $('#province3').val();
		address3.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = $('#amphur3').val();
		address3.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = $('#district3').val();
		address3.districtBean = districtBean;
		address3.postcode = $('#postcode3').val();
		address3.nearbyPlaces = $('#nearbyPlaces3').val();
		address3.addressType = '3';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address3.zoneBean = zone;
		
		amphur3 = $('#amphur3').val();
		district3 = $('#district3').val();
		
		addressList.push(address3);
	}
	
	var select_address2 = $('#select_address2').val();
	if(select_address2 == 1){
		
		$('#no4').val($('#no1').val());
		$('#section4').val($('#section1').val());
		$('#building4').val($('#building1').val());
		$('#room4').val($('#room1').val());
		$('#floor4').val($('#floor1').val());
		$('#village4').val($('#village1').val());
		$('#alley4').val($('#alley1').val());
		$('#road4').val($('#road1').val());
		$('#province4').val($('#province1').val());
		$('#amphur4').val($('#amphur1').val());
		$('#district4').val($('#district1').val());
		$('#postcode4').val($('#postcode1').val());
		$('#nearbyPlaces4').val($('#nearbyPlaces1').val());
		
		address4.no = $('#no1').val();
		address4.section = $('#section1').val();
		address4.building = $('#building1').val();
		address4.room = $('#room1').val();
		address4.floor = $('#floor1').val();
		address4.village = $('#village1').val();
		address4.alley = $('#alley1').val();
		address4.road = $('#road1').val();
		var provinceBean = {};
		provinceBean.id = $('#province1').val();
		address4.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur1;
		address4.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district1;
		address4.districtBean = districtBean;
		address4.postcode = $('#postcode1').val();
		address4.nearbyPlaces = $('#nearbyPlaces1').val();
		address4.addressType = '4';
		address4.overrideAddressType = '1';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address4.zoneBean = zone;
		
		amphur4 = amphur1;
		district4 = district1;
		
		addressList.push(address4);
	}else if (select_address2 == 2){
		
		$('#no4').val($('#no2').val());
		$('#section4').val($('#section2').val());
		$('#building4').val($('#building2').val());
		$('#room4').val($('#room2').val());
		$('#floor4').val($('#floor2').val());
		$('#village4').val($('#village2').val());
		$('#alley4').val($('#alley2').val());
		$('#road4').val($('#road2').val());
		$('#province4').val($('#province2').val());
		$('#amphur4').val($('#amphur2').val());
		$('#district4').val($('#district2').val());
		$('#postcode4').val($('#postcode2').val());
		$('#nearbyPlaces4').val($('#nearbyPlaces2').val());
		
		address4.no = $('#no2').val();
		address4.section = $('#section2').val();
		address4.building = $('#building2').val();
		address4.room = $('#room2').val();
		address4.floor = $('#floor2').val();
		address4.village = $('#village2').val();
		address4.alley = $('#alley2').val();
		address4.road = $('#road2').val();
		var provinceBean = {};
		provinceBean.id = $('#province2').val();
		address4.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur2;
		address4.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district2;
		address4.districtBean = districtBean;
		address4.postcode = $('#postcode2').val();
		address4.nearbyPlaces = $('#nearbyPlaces2').val();
		address4.addressType = '4';
		address4.overrideAddressId = '2';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address4.zoneBean = zone;
		
		amphur4 = amphur2;
		district4 = district2;
		
		addressList.push(address4);
	}else if (select_address2 == 3){
		
		$('#no4').val($('#no3').val());
		$('#section4').val($('#section3').val());
		$('#building4').val($('#building3').val());
		$('#room4').val($('#room3').val());
		$('#floor4').val($('#floor3').val());
		$('#village4').val($('#village3').val());
		$('#alley4').val($('#alley3').val());
		$('#road4').val($('#road3').val());
		$('#province4').val($('#province3').val());
		$('#amphur4').val($('#amphur3').val());
		$('#district4').val($('#district3').val());
		$('#postcode4').val($('#postcode3').val());
		$('#nearbyPlaces4').val($('#nearbyPlaces3').val());
		
		address4.no = $('#no3').val();
		address4.section = $('#section3').val();
		address4.building = $('#building3').val();
		address4.room = $('#room3').val();
		address4.floor = $('#floor3').val();
		address4.village = $('#village3').val();
		address4.alley = $('#alley3').val();
		address4.road = $('#road3').val();
		var provinceBean = {};
		provinceBean.id = $('#province3').val();
		address4.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur3;
		address4.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district3;
		address4.districtBean = districtBean;
		address4.postcode = $('#postcode3').val();
		address4.nearbyPlaces = $('#nearbyPlaces3').val();
		address4.addressType = '4';
		address4.overrideAddressId = '3';
		
		var zone = {};
		zone.id = $('#select_zone').val();
		address4.zoneBean = zone;
		
		amphur4 = amphur3;
		district4 = district3;
		
		addressList.push(address4);
	}else{
		address4.no = $('#no4').val();
		address4.section = $('#section4').val();
		address4.building = $('#building4').val();
		address4.room = $('#room4').val();
		address4.floor = $('#floor4').val();
		address4.village = $('#village4').val();
		address4.alley = $('#alley4').val();
		address4.road = $('#road4').val();
		var provinceBean = {};
		provinceBean.id = $('#province4').val();
		address4.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = $('#amphur4').val();
		address4.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = $('#district4').val();
		address4.districtBean = districtBean;
		address4.postcode = $('#postcode4').val();
		address4.nearbyPlaces = $('#nearbyPlaces4').val();
		address4.addressType = '4';
		
		var zone = {};
		zone.id = $('#select_zone4').val();
		address4.zoneBean = zone;
		
		amphur4 = $('#amphur4').val();
		district4 = $('#district4').val();
		
		addressList.push(address4);
	}
	
	var select_address3 = $('#select_address3').val();
	if(select_address3 == 1){
		address5.no = $('#no1').val();
		address5.section = $('#section1').val();
		address5.building = $('#building1').val();
		address5.room = $('#room1').val();
		address5.floor = $('#floor1').val();
		address5.village = $('#village1').val();
		address5.alley = $('#alley1').val();
		address5.road = $('#road1').val();
		var provinceBean = {};
		provinceBean.id = $('#province1').val();
		address5.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur1;
		address5.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district1;
		address5.districtBean = districtBean;
		address5.postcode = $('#postcode1').val();
		address5.nearbyPlaces = $('#nearbyPlaces1').val();
		address5.addressType = '5';
		address5.overrideAddressId = '1';
		
		amphur5 = amphur1;
		district5 = district1;
		
		addressList.push(address5);
	}else if (select_address3 == 2){
		address5.no = $('#no2').val();
		address5.section = $('#section2').val();
		address5.building = $('#building2').val();
		address5.room = $('#room2').val();
		address5.floor = $('#floor2').val();
		address5.village = $('#village2').val();
		address5.alley = $('#alley2').val();
		address5.road = $('#road2').val();
		var provinceBean = {};
		provinceBean.id = $('#province2').val();
		address5.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur2;
		address5.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district2;
		address5.districtBean = districtBean;
		address5.postcode = $('#postcode2').val();
		address5.nearbyPlaces = $('#nearbyPlaces2').val();
		address5.addressType = '5';
		address5.overrideAddressId = '2';
		
		amphur5 = amphur2;
		district5 = district2;
		
		addressList.push(address5);
	}else if (select_address3 == 3){
		address5.no = $('#no3').val();
		address5.section = $('#section3').val();
		address5.building = $('#building3').val();
		address5.room = $('#room3').val();
		address5.floor = $('#floor3').val();
		address5.village = $('#village3').val();
		address5.alley = $('#alley3').val();
		address5.road = $('#road3').val();
		var provinceBean = {};
		provinceBean.id = $('#province3').val();
		address5.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur3;
		address5.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district3;
		address5.districtBean = districtBean;
		address5.postcode = $('#postcode3').val();
		address5.nearbyPlaces = $('#nearbyPlaces3').val();
		address5.addressType = '5';
		address5.overrideAddressId = '3';
		
		amphur5 = amphur3;
		district5 = district3;
		
		addressList.push(address5);
	}else if (select_address3 == 4){
		address5.no = $('#no4').val();
		address5.section = $('#section4').val();
		address5.building = $('#building4').val();
		address5.room = $('#room4').val();
		address5.floor = $('#floor4').val();
		address5.village = $('#village4').val();
		address5.alley = $('#alley4').val();
		address5.road = $('#road4').val();
		var provinceBean = {};
		provinceBean.id = $('#province4').val();
		address5.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = amphur4;
		address5.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = district4;
		address5.districtBean = districtBean;
		address5.postcode = $('#postcode4').val();
		address5.nearbyPlaces = $('#nearbyPlaces4').val();
		address5.addressType = '5';
		address5.overrideAddressId = '4';
		
		amphur5 = amphur4;
		district5 = district4;
		
		addressList.push(address5);
	}else{
		address5.no = $('#no5').val();
		address5.section = $('#section5').val();
		address5.building = $('#building5').val();
		address5.room = $('#room5').val();
		address5.floor = $('#floor5').val();
		address5.village = $('#village5').val();
		address5.alley = $('#alley5').val();
		address5.road = $('#road5').val();
		var provinceBean = {};
		provinceBean.id = $('#province5').val();
		address5.provinceBean = provinceBean;
		var amphurBean = {};
		amphurBean.id = $('#amphur5').val();
		address5.amphurBean = amphurBean;
		var districtBean = {};
		districtBean.id = $('#district5').val();
		address5.districtBean = districtBean;
		address5.postcode = $('#postcode5').val();
		address5.nearbyPlaces = $('#nearbyPlaces5').val();
		address5.addressType = '5';
		
		amphur5 = $('#amphur5').val();
		district5 = $('#district5').val();
		
		addressList.push(address5);
	}
	
	serviceApplicationBean.id = ${serviceApplicationBean.id }
	serviceApplicationBean.addressList = addressList;
	
	serviceApplicationBean.easyInstallationDateTime = $('#daterange').val();

// 	var productitemList = [];
// 	var productItemWorksheetBeanList = [];
// 	$('#table_template_equiment > tbody  > tr').each(function() {
// 		var item = {};
// 		 $(this).find('input').each(function (i) {
// 			 //alert($(this).val());
// 				switch (i) {
// 						case 0:
// 							var productItemBean = {};
// 							productItemBean.id = $(this).val();
// 							item.id = productItemBean;
// 							break;
// 						case 1:
// 							item.type = $(this).val();
// 							break;
// 						case 2:
// 							item.id = $(this).val();
// 							break;
// 						case 3:
// 							var equipmentProductItemBean = {};
// 							equipmentProductItemBean.id = $(this).val();
// 							item.equipmentProductItemBean = equipmentProductItemBean;
// 							break;
// 						case 4:
// 							item.price = $(this).val();
// 							break;
// 						case 5:
// 							if($(this).is(":checked")){
// 								item.free = true;
// 							}else{
// 								item.free = false;
// 							} 
// 							break;
// 						case 6:
// 							if($(this).is(":checked")){
// 								item.lend = true;
// 							}else{
// 								item.lend = false;
// 							}  
// 							break;
// 						case 7:
// 							item.amount = $(this).val();
// 							break;
// 						}
				
// 					}); //end find textbox
					
// 		 			//add to arrayjson
// 		 			productItemWorksheetBeanList.push(item);
// 				}); //end find tr
				
// 				$('#table_template_internet > tbody  > tr').each(function() {
// 					var item = {};
// 					 $(this).find('input').each(function (i) {
// 						 //alert($(this).val());
// 							switch (i) {
// 									case 0:
// 										var productItemBean = {};
// 										productItemBean.id = $(this).val();
// 										item.id = productItemBean;
// 										break;
// 									case 1:
// 										item.type = $(this).val();
// 										break;
// 									case 2:
// 										item.id = $(this).val();
// 										break;
// 									case 3:
// 										var internetProductBeanItem = {};
// 										internetProductBeanItem.id = $(this).val();
// 										item.internetProductBeanItem = internetProductBeanItem;
// 										break;
// 									}
							
// 								}); //end find textbox
								
// 					 			//add to arrayjson
// 					 			productItemWorksheetBeanList.push(item);
// 							}); //end find tr
							
// 				var productitemLists = {};
// 				productitemLists.productItemWorksheetBeanList = productItemWorksheetBeanList;
// 				productitemList.push(productitemLists);
				
// 	serviceApplicationBean.productitemList = productitemList;
	serviceApplicationBean.remark = $('#remark').val();

	var status = {};
	status.stringValue = statusSubmit;
	serviceApplicationBean.status = status;
	
	var serviceApplicationTypeBean = {};
	serviceApplicationTypeBean.id = $('#service_application_type').val();
	serviceApplicationBean.serviceApplicationTypeBean = serviceApplicationTypeBean;
	
	serviceApplicationBean.houseRegistrationDocuments = $('#checkbox_house_registration_documents').is(":checked");
	serviceApplicationBean.identityCardDocuments = $('#checkbox_identity_card_documents').is(":checked");
	serviceApplicationBean.otherDocuments = $('#checkbox_other_documents').is(":checked");

	serviceApplicationBean.plateNumber = $('#plate_number').val();
	serviceApplicationBean.latitude = $('#latitude').val();
	serviceApplicationBean.longitude = $('#longitude').val();
	
	if($('#checkbox_other_documents').is(":checked")){
		serviceApplicationBean.remarkOtherDocuments = $('#remark_other_documents').val();
	}
		
	return serviceApplicationBean;
}

// function loadProductItemEdit(id,type,spanId){
// 	$('.preloader').modal('show');
// 	$.ajax({
// 		type : "GET",
// 		contentType : "application/json; charset=utf-8",
// 		url : "${pageContext.request.contextPath}/requisition/loadEquipmentProductWithId/"+id,
// 		dataType : 'json',
// 		async: false,
// 		//timeout : 100000,
// 		success : function(data) {
// // 			dataEquipmentProduct = data;
// 			if(data["error"] == false){
// 				var rows = "";
// 				for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
// 					var startRow = "<tr>";
// 					var endRow = "</tr>";
					
// 					var tdRadio = "<td><center>"+
// 					"<label class='custom-control custom-radio'> <input name='radioProduct' type='radio' " +
// 					"value='" + data["result"]["equipmentProductItemBeans"][i].serialNo +"|"+ data["result"]["equipmentProductItemBeans"][i].id +"|"+ spanId + "' class='custom-control-input'> " +
// 					"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
// 					"</label></center></td>";
					
// 					var columnProductCode = "<td>"+ data["result"].productCode +"</td>";
// 					var columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
// 					var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
// 					var columnProductNumberImport = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].numberImport +"</center></td>";
					
// 					rows = rows + startRow + tdRadio;
// 					rows = rows + columnProductSerial + columnProductNumberImport + columnProductRefDoc;
// 				}
				
// 				$('#tableEquipmentProductChild > tbody').html(rows);
				
// 				setTimeout(
// 					function(){
// 					$('.preloader').modal('hide');
// 					}, 200);
// 				$('#addEquipmentProductItem').modal('show');
// 			}else{
// // 				dataEquipmentProduct = '';
// 			}
// 		},
// 		error : function(e) {
// 			console.log("ERROR: ", e);
// 		},
// 		done : function(e) {
// 			console.log("DONE");
// 		}
// 	});
// }

function loadInternetItemEdit(id,type,spanId){
	$('.preloader').modal('show');
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/requisition/loadInternetWithId/"+id,
		dataType : 'json',
		async: false,
		//timeout : 100000,
		success : function(data) {
// 			console.log(data);
			if(data["error"] == false){
				var rows = "";
				for(var i=0;i<data["result"]["internetProductBeanItems"].length;i++){
					var startRow = "<tr>";
					var endRow = "</tr>";
					
					var tdRadio = "<td><center>"+
					"<label class='custom-control custom-radio'> <input name='radioInternet' type='radio' " +
					"value='" + data["result"]["internetProductBeanItems"][i].userName +"|"+ data["result"]["internetProductBeanItems"][i].password +"|"+ data["result"]["internetProductBeanItems"][i].id +"|"+ spanId + "' class='custom-control-input'> " +
					"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
					"</label></center></td>";

					var columnUserName = "<td><center>"+ data["result"]["internetProductBeanItems"][i].userName +"</center></td>";
					var columnPassword = "<td><center>"+ data["result"]["internetProductBeanItems"][i].password +"</center></td>";

					rows = rows + startRow + tdRadio;
					rows = rows + columnUserName + columnPassword;
				}
				
				$('#tableInternetProductChild > tbody').html(rows);
				
				setTimeout(
					function(){
					$('.preloader').modal('hide');
					}, 200);
				
				$('#addInternetProductItem').modal('show');
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

function appendToListProductItem(){
	var radioProduct = $("input:radio[name ='radioProduct']:checked").val();
	if(typeof radioProduct != 'undefined'){
// 		console.log(radioProduct);

	var seperate = radioProduct.split("|");
	$.each($('#table_template_equiment tbody tr'), function( index, value ) {
		var serialNo = $(this).find('.serial-no');
		var productItemId = $(this).find('.productItemId');
		var productItemWorksheetId = $(this).find('.productItemWorksheetId');
		
// 		console.log(serialNo.text());
		if(serialNo.text()==seperate[0]){
			serialNo.text('');
			productItemId.val('');
		}
	});
		$('#productItemId_'+seperate[2]+'').val(seperate[1]);
		$('#serial_'+seperate[2]+'').text(seperate[0]);
		$('#addEquipmentProductItem').modal('hide');
	}
}

function appendToListInternetProductItem(){
	var radioInternet = $("input:radio[name ='radioInternet']:checked").val();
	if(typeof radioInternet != 'undefined'){
// 		console.log(radioInternet);

	var seperate = radioInternet.split("|");
	$.each($('#table_template_internet tbody tr'), function( index, value ) {
		var productItemId = $(this).find('.productItemId');
		var username = $(this).find('.internet-username');
		var password = $(this).find('.internet-password');
		
// 		console.log(serialNo.text());
		if(productItemId.val()==seperate[2]){
			username.text('');
			password.text('');
			productItemId.val('');
		}
	});
		$('#internetId_'+seperate[3]+'').val(seperate[2]);
		$('#span_username_'+seperate[3]+'').text(seperate[0]);
		$('#span_password_'+seperate[3]+'').text(seperate[1]);
		$('#addInternetProductItem').modal('hide');
	}
}

$( "#checkbox_other_documents" ).click(function() {
	  $( "#remark_other_documents" ).toggle( "slow", function() {
	    // Animation complete.
	  });
	});
	

function selectProductitemList(id){
	$('.preloader').modal('show');
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/serviceapplicationlist/getProductitem/"+id,
		dataType : 'json',
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				console.log(data);
				
				<c:if test="${serviceApplicationBean.monthlyService}">
				$('.div-detail-package').hide();
				$('.div-detail-package-ismonthlyservice').fadeIn();
				monthlyService = true;
				</c:if>
				
				
				<c:if test="!${serviceApplicationBean.monthlyService}">
				$('.div-detail-package-ismonthlyservice').hide();
				$('.div-detail-package').fadeIn();
				monthlyService = false;
				</c:if>
				
				var table_template = '';
				// รายชื่อวัสดุอุปกรณ์ที่ติดตั้ง
				$.each(data.result, function( index, value ) {
					var checkedFree = "",checkedLend = "";
					var totalPrice = 0;
					var salePrice = value.price;
					
					
					totalPrice = value.price*value.quantity;
					
					// เช็ค ฟรี หรือ ยืม
					if(value.free==true){
						checkedFree = "checked=\"checked\"";
						totalPrice = 0;
					}
					else if(value.lend==true){
						checkedLend = "checked=\"checked\"";
						totalPrice = 0;
					}
					
					table_template += '<tr>\
						<td><center><span class="template-no">'+(index+1)+'</span></center>\
						<input class="productId" type="hidden" value="'+value.product.id+'"></td>\
						<input class="productType" type="hidden" value="'+value.type+'">\
						<td><center>'+(value.product.productCode||'')+'</center></td>\
						<td>'+value.product.productName+'</td>\
						<td scope="row"><div class="input-group">\
								<input type="number" class="form-control quantity calculate" value="'+value.quantity+'" >\
								<div class="input-group-addon">'+value.product.unit.unitName+'</div>\
							</div></td>\
						<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
							class="ti-trash remove-template" data-toggle="tooltip"\
							data-placement="bottom" title=""\
							data-original-title="ลบ"></span></a>\
							</td>\
						</tr>';
							
//							<td scope="row"><div class="input-group">\
//									<input type="number" class="form-control salePrice calculate" value="'+salePrice+'" >\
//									<div class="input-group-addon">บาท</div>\
//								</div></td>\
//							<td align="center" style="vertical-align: middle;">\
//									<label class="custom-control custom-checkbox">\
//										<input type="checkbox" class="custom-control-input check-box-free"\
//										'+checkedFree+'> <span\
//										class="custom-control-indicator"></span> <span\
//										class="custom-control-description"></span>\
//									</label>\
//							</td>\
//							<td align="center" style="vertical-align: middle;">\
//									<label class="custom-control custom-checkbox">\
//										<input type="checkbox" class="custom-control-input check-box-lend"\
//										'+checkedLend+'> <span\
//										class="custom-control-indicator"></span> <span\
//										class="custom-control-description"></span>\
//									</label>\
//							</td>\
//							<td scope="row"><div class="input-group">\
//									<input type="number" class="form-control totalPrice" value="'+totalPrice+'" disabled>\
//									<div class="input-group-addon">บาท</div>\
//								</div></td>\
//							<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
//									class="ti-trash remove-template" data-toggle="tooltip"\
//									data-placement="bottom" title=""\
//									data-original-title="ลบ"></span></a>\
//							</td>\
//						</tr>';
				});
				
				$('#table_template_equiment tbody').html(table_template);
				
				setTimeout(function() {$('.preloader').modal('hide');}, 200);
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
	            lat: ${serviceApplicationBean.latitude},
	            lon: ${serviceApplicationBean.longitude},
	            title: 'ลากจุดนี้ B ไปยังปลายทาง',
	            html: '',
	            visible: false
	        }
	    ],
	    map_div: '#gmap-route',
	    generate_controls: false,
	    show_markers: false,
	    type: 'directions',
	    draggable: true,
	    zoom: 21,
	    directions_panel: '#route',
	    afterRoute: function(distance,status, result) {
	        $('#km').text(': '+(distance/1000)+' km');
	        $('#latitude').val(result.request.destination.lat());
	        $('#longitude').val(result.request.destination.lng());
	        console.log("A : " + result.request.origin.lat() + "," + result.request.origin.lng());
	        console.log("B : " + result.request.destination.lat() + "," + result.request.destination.lng());
	    }
	}).Load();

});
</script>

</body>
</html>
