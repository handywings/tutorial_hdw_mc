<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="customerregistration" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>แก้ไขข้อมูลลูกค้า</title>
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
								<li class="breadcrumb-item active">แก้ไขข้อมูลลูกค้า</li>
							</ol>
						</div>
					</div>
					<div class="card mt15 mb40">
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div id="newcustomers">
												<div class="row">
													<div class="col-md-2">
														<label for="exampleInputEmail1"><b>รหัสลูกค้า</b></label>
														<input type="hidden" class="form-control"
															id="custId" value="${customerBean.id }">
														<input type="text" class="form-control"
															id="custCode" value="${customerBean.custCode }">
													</div>
													<div class="col-md-3">
														<b>เพศ<span class="text-red"> *</span>
														</b><br> <label style="padding-bottom: 5.5px;"
															class="custom-control custom-radio"> <input id="radio1"
															name="sex" type="radio" <c:if test="${customerBean.sex eq 'male'}">checked="checked"</c:if> value="male"
															class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;ชาย</span>
														</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
															id="radio2" name="sex" type="radio" <c:if test="${customerBean.sex eq 'female'}">checked="checked"</c:if> value="female"
															class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;หญิง</span>
														</label>
														<div class="messages"></div>
													</div>
												</div>
												<div class="row">
													<div class="col-md-2">
														<label for="exampleInputEmail1"><b>คำนำหน้า</b></label> 
														<input type="text" class="form-control" id="prefix" value="${customerBean.prefix }">
													</div>
													<div class="col-md-3">
														<label for="exampleInputEmail1"><b>ชื่อ</b></label> <input
															type="text" class="form-control" id="custfName" value="${customerBean.firstName }">
													</div>
													<div class="col-md-3">
														<label for="exampleInputEmail1"><b>นามสกุล</b></label> <input
															type="text" class="form-control" id="custlName" value="${customerBean.lastName }">
													</div>
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>ประเภทลูกค้า</b></label>
														<select class="form-control" id="custType">
															<option value="I" <c:if test="${customerBean.custTypeReal eq 'I' }">selected</c:if> >บุคคลธรรมดา</option>
															<option value="C" <c:if test="${customerBean.custTypeReal eq 'C' }">selected</c:if>>นิติบุคคล</option>
														</select>
													</div>
												</div>
												<div class="row">
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>ลักษณะลูกค้า</b></label>
														<select class="form-control" id="custFeature">
															<c:forEach var="customerFeatureBean" items="${customerFeatureBeans}" varStatus="i">
																<option value="${customerFeatureBean.id }" <c:if test="${customerBean.customerFeatureBean.id == customerFeatureBean.id }">selected</c:if> >${customerFeatureBean.customerFeatureName }</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-md-2">
														<label for="exampleInputEmail1"><b>เลขที่บัตรประชาชน</b></label>
														<input type="text" class="form-control"
															id="custIdentNumber" value="${customerBean.identityNumber }">
													</div>
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>อาชีพ</b></label> <select class="form-control" id="custCareer">
															<c:forEach var="careerBean" items="${careerBeans}" varStatus="i">
																<option value="${careerBean.id }" <c:if test="${customerBean.careerBean.id == careerBean.id }">selected</c:if> >${careerBean.careerName }</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-md-2">
														<input type="hidden" class="form-control"
															id="contactId" value="${customerBean.contact.id }">
														<label for="exampleInputEmail1"><b>เบอร์ติดต่อ</b></label>
														<input type="text" class="form-control"
															id="custMobile" value="${customerBean.contact.mobile }">
													</div>
												</div>
												<div class="row">
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>Fax</b></label> <input
															type="text" class="form-control" id="custFax" value="${customerBean.contact.fax }">
													</div>
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>E-mail</b></label> <input
															type="text" class="form-control" id="custEamil" value="${customerBean.contact.email }">
													</div>
												</div>

											</div>
										</fieldset>
									</div>
								</div>
								<!-- ที่อยู่ -->
												<c:forEach var="addressBean" items="${customerBean.addressList}" varStatus="i">
												<c:if test="${addressBean.addressType eq '1' }">
												<fieldset>
													<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ในทะเบียนบ้าน&nbsp;&nbsp;&nbsp;</legend>
													<div class="row">
														<div class="col-md-2">
															<input type="hidden" id="hiddenAddressId_type1" value="${addressBean.id }" />
															<label for="exampleInputEmail1"><b>บ้านเลขที่</b></label>
															<input type="text" class="form-control"
																id="homeNo_type1" value="${addressBean.no }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>หมู่ที่</b></label> <input
																type="text" class="form-control" id="section_type1" value="${addressBean.section }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>อาคาร</b></label> <input
																type="text" class="form-control" id="building_type1" value="${addressBean.building }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label>
															<input type="text" class="form-control"
																id="room_type1" value="${addressBean.room }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>ชั้นที่</b></label> <input
																type="text" class="form-control" id="floor_type1" value="${addressBean.floor }">
														</div>
														<div class="col-md-4">
															<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> <input
																type="text" class="form-control" id="village_type1" value="${addressBean.village }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> <input
																type="text" class="form-control" id="alley_type1"  value="${addressBean.alley }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ถนน</b></label> <input
																type="text" class="form-control" id="road_type1"  value="${addressBean.road }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>จังหวัด</b></label> <select
																class="form-control" id="province_type1" name="province" onchange="loadAmphur(1)">
																<option disabled="disabled" selected="selected">---
																	กรุณาเลือกจังหวัด ---</option>
																<c:forEach var="provinceBean" items="${provinceBeans}" varStatus="i">
																<option value="${provinceBean.id}" <c:if test="${addressBean.provinceBean.id eq provinceBean.id }">selected</c:if> >${provinceBean.PROVINCE_NAME}</option>
																</c:forEach>
															</select>
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>อำเภอ /
																	เขต</b></label> <select class="form-control" id="amphur_type1"
																name="district" onclick="loadDistrict(1);"
																onchange="">
																<option value="" selected="selected" disabled="disabled">---กรุณาเลือกอำเภอ ---</option>
																<c:forEach var="amphurBean" items="${provinceBeansType1.amphurBeans}" varStatus="i">
																	<option value="${amphurBean.id}" <c:if test="${addressBean.amphurBean.id eq amphurBean.id }">selected</c:if> >${amphurBean.AMPHUR_NAME}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="row">
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ตำบล /
																	แขวง</b></label> <select class="form-control" id="district_type1"
																name="subdistrict" onclick="loadZipCode(1);" >
																<option value="" selected="selected" disabled="disabled">---กรุณาเลือกตำบล ---</option>
																<c:forEach var="amphurBean" items="${provinceBeansType1.amphurBeans}" varStatus="i">
																<c:forEach var="districtBean" items="${amphurBean.districtBeans}" varStatus="j">
																<c:if test="${addressBean.amphurBean.id eq amphurBean.id }">
																	<option value="${districtBean.id }" <c:if test="${addressBean.districtBean.id eq districtBean.id }">selected ${addressBean.districtBean.id}</c:if> >${districtBean.DISTRICT_NAME }</option>
																</c:if>
																</c:forEach>
																</c:forEach>
															</select>
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>รหัสไปรษณีย์</b></label>
															<input class="form-control bg-form" type="text"
																id="zipCode_type1" name="postcode" value="${addressBean.postcode }">
														</div>
														<div class="col-md-6">
															<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
															<input type="text" class="form-control"
																id="nearby_type1" value="${addressBean.nearbyPlaces }">
														</div>
													</div>

													<!-- ที่อยู่ -->
												</fieldset>
												</c:if>
												</c:forEach>
												
												<c:forEach var="addressBean" items="${customerBean.addressList}" varStatus="i">
												<c:if test="${addressBean.addressType eq '2' }">
												<div class="row mt05">
													<div class="col-md-12">
														<label class="custom-control custom-checkbox"> <input
															type="checkbox" id="checkbox_no_current_address" <c:if test="${empty addressBean.overrideAddressId}">checked</c:if>
															class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">ที่อยู่ปัจจุบัน
																ไม่ได้เป็นที่อยู่เดียวกันกับที่อยู่ในทะเบียนบ้าน</span>
														</label>
													</div>
												</div>
												<!-- ที่อยู่ -->
												<fieldset class="no-current-address" <c:if test="${not empty addressBean.overrideAddressId}"> style="display:none"</c:if> >
													<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ปัจจุบัน&nbsp;&nbsp;&nbsp;</legend>
													<div class="row">
														<div class="col-md-2">
															<input type="hidden" id="hiddenAddressId_type2" value="${addressBean.id }" />
															<label for="exampleInputEmail1"><b>บ้านเลขที่</b></label>
															<input type="text" class="form-control"
																id="homeNo_type2" value="${addressBean.no }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>หมู่ที่</b></label> <input
																type="text" class="form-control" id="section_type2" value="${addressBean.section }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>อาคาร</b></label> <input
																type="text" class="form-control" id="building_type2" value="${addressBean.building }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label>
															<input type="text" class="form-control"
																id="room_type2" value="${addressBean.room }">
														</div>
														<div class="col-md-1">
															<label for="exampleInputEmail1"><b>ชั้นที่</b></label> <input
																type="text" class="form-control" id="floor_type2" value="${addressBean.floor }">
														</div>
														<div class="col-md-4">
															<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> <input
																type="text" class="form-control" id="village_type2" value="${addressBean.village }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> <input
																type="text" class="form-control" id="alley_type2"  value="${addressBean.alley }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ถนน</b></label> <input
																type="text" class="form-control" id="road_type2"  value="${addressBean.road }">
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>จังหวัด</b></label> <select
																class="form-control" id="province_type2" name="province_type2"
																onchange="loadAmphur(2)">
																<option disabled="disabled" selected="selected">---กรุณาเลือกจังหวัด ---</option>
																<c:forEach var="provinceBean" items="${provinceBeans}" varStatus="i">
																	<option value="${provinceBean.id}" <c:if test="${addressBean.provinceBean.id eq provinceBean.id }">selected</c:if> >${provinceBean.PROVINCE_NAME}</option>
																</c:forEach>
															</select>
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>อำเภอ /
																	เขต</b></label> <select class="form-control" id="amphur_type2"
																name="district" onclick="loadDistrict(2);"
																onchange="">
																<option value="" selected="selected" disabled="disabled">---กรุณาเลือกอำเภอ ---</option>
																<c:forEach var="amphurBean" items="${provinceBeansType2.amphurBeans}" varStatus="i">
																	<option value="${amphurBean.id}" <c:if test="${addressBean.amphurBean.id eq amphurBean.id }">selected</c:if> >${amphurBean.AMPHUR_NAME}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="row">
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>ตำบล /
																	แขวง</b></label> <select class="form-control" id="district_type2"
																name="subdistrict" onclick="loadZipCode(2);">
																<option value="" selected="selected" disabled="disabled">---กรุณาเลือกตำบล ---</option>
																<c:forEach var="amphurBean" items="${provinceBeansType2.amphurBeans}" varStatus="i">
																<c:forEach var="districtBean" items="${amphurBean.districtBeans}" varStatus="j">
																<c:if test="${addressBean.amphurBean.id eq amphurBean.id }">
																	<option value="${districtBean.id }" <c:if test="${addressBean.districtBean.id eq districtBean.id }">selected</c:if> >${districtBean.DISTRICT_NAME }</option>
																</c:if>
																</c:forEach>
																</c:forEach>
															</select>
														</div>
														<div class="col-md-3">
															<label for="exampleInputEmail1"><b>รหัสไปรษณีย์</b></label>
															<input class="form-control bg-form" type="text"
																id="zipCode_type2" name="postcode" value="${addressBean.postcode }">
														</div>
														<div class="col-md-6">
															<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
															<input type="text" class="form-control"
																id="nearby_type2" value="${addressBean.nearbyPlaces }">
														</div>
													</div>
													<!-- ที่อยู่ -->
												</fieldset>
												</c:if>
												</c:forEach>
											
								
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center>
											<button type="button" onclick="updateCustomer();" 
												class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light">บันทึก</button>
											<a href="${pageContext.request.contextPath}/customerregistration">
											<button type="button"
												class="btn btn-danger w-min-sm mb-0-25 waves-effect waves-light">ยกเลิก</button></a>
										</center>
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
	$('#checkbox_no_current_address').click(function () {
		if($(this).is(":checked")){
			$('.no-current-address').fadeIn();
		}else{
			$('.no-current-address').hide();
		}
	});
	
	function updateCustomer(){
		$('.preloader').modal('show');
		//customer
		var customerBean = {};
		customerBean.id = $('#custId').val();
		customerBean.custCode = $('#custCode').val();
		customerBean.sex = $('input[name=sex]:checked').val();
		customerBean.prefix = $('#prefix').val();
		customerBean.firstName = $('#custfName').val();
		customerBean.lastName = $('#custlName').val();
		customerBean.custTypeReal = $('#custType').val();
		customerBean.identityNumber = $('#custIdentNumber').val();
		//career
		CareerBean = {};
		CareerBean.id = $('#custCareer').val();
		customerBean.careerBean = CareerBean;
		//customer feature
		CustomerFeatureBean = {};
		CustomerFeatureBean.id = $('#custFeature').val();
		customerBean.customerFeatureBean = CustomerFeatureBean;
		//contact
		ContactBean = {};
		ContactBean.id = $('#contactId').val();
		ContactBean.mobile = $('#custMobile').val();
		ContactBean.email = $('#custEamil').val();
		ContactBean.fax = $('#custFax').val();
		customerBean.contact = ContactBean;
		
		var addressList = [];
		//address type1
		AddressBeanType1 = {};
		AddressBeanType1.id = $('#hiddenAddressId_type1').val();
		AddressBeanType1.no = $('#homeNo_type1').val();
		AddressBeanType1.section = $('#section_type1').val();
		AddressBeanType1.building = $('#building_type1').val();
		AddressBeanType1.room = $('#room_type1').val();
		AddressBeanType1.floor = $('#floor_type1').val();
		AddressBeanType1.village = $('#village_type1').val();
		AddressBeanType1.alley = $('#alley_type1').val();
		AddressBeanType1.road = $('#road_type1').val();
		AddressBeanType1.postcode = $('#zipCode_type1').val();
		AddressBeanType1.nearbyPlaces = $('#nearby_type1').val();
		//province
		ProvinceBeanType1 = {};
		ProvinceBeanType1.id = $('#province_type1').val();
		AddressBeanType1.provinceBean = ProvinceBeanType1;
		//amphur
		AmphurBeanType1 = {};
		AmphurBeanType1.id = $('#amphur_type1').val();
		AddressBeanType1.amphurBean = AmphurBeanType1;
		//district
		DistrictBeanType1 = {};
		DistrictBeanType1.id = $('#district_type1').val();
		AddressBeanType1.districtBean = DistrictBeanType1;
		
		addressList.push(AddressBeanType1);
		//address type2
		AddressBeanType2 = {};
		if ($("#checkbox_no_current_address").is(":checked")) {
			AddressBeanType2.overrideAddressId = null;
		}else{
// 			AddressBeanType2.overrideAddressId = $('#hiddenAddressId_type1').val();
			AddressBeanType2.overrideAddressId = '1';
		}
		AddressBeanType2.id = $('#hiddenAddressId_type2').val();
		AddressBeanType2.no = $('#homeNo_type2').val();
		AddressBeanType2.section = $('#section_type2').val();
		AddressBeanType2.building = $('#building_type2').val();
		AddressBeanType2.room = $('#room_type2').val();
		AddressBeanType2.floor = $('#floor_type2').val();
		AddressBeanType2.village = $('#village_type2').val();
		AddressBeanType2.alley = $('#alley_type2').val();
		AddressBeanType2.road = $('#road_type2').val();
		AddressBeanType2.postcode = $('#zipCode_type2').val();
		AddressBeanType2.nearbyPlaces = $('#nearby_type2').val();
		
		//province
		ProvinceBeanType2 = {};
		ProvinceBeanType2.id = $('#province_type2').val();
		AddressBeanType2.provinceBean = ProvinceBeanType2;
		//amphur
		AmphurBeanType2 = {};
		AmphurBeanType2.id = $('#amphur_type2').val();
		AddressBeanType2.amphurBean = AmphurBeanType2;
		//district
		DistrictBeanType2 = {};
		DistrictBeanType2.id = $('#district_type2').val();
		AddressBeanType2.districtBean = DistrictBeanType2;
		addressList.push(AddressBeanType2);
		
		customerBean.addressList = addressList;
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/customerregistration/updateCustomer",
			dataType : 'json',
			async : false,
			data : JSON.stringify(customerBean),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					window.location.href = "${pageContext.request.contextPath}/customerregistration/edit/"+$('#custId').val();
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
	
	function loadAmphur(type){
		var provinceId = 0;
		if(type == "1"){
			provinceId = $('#province_type1').val();
		}else if(type == "2"){
			provinceId = $('#province_type2').val();
		}
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/customerregistration/loadAmphur/"+provinceId,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					if(type == "1"){
						$('#zipCode_type1').val('');
						$('#amphur_type1')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกจังหวัด ---</option>");
						$('#district_type1')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกตำบล ---</option>");
						
						for(var i=0;i<data["result"].length;i++){
							$('#amphur_type1')
						    .append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["AMPHUR_NAME"] +"</option>");
						}
					}else if(type == "2"){
						$('#zipCode_type2').val('');
						$('#amphur_type2')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกจังหวัด ---</option>");
						$('#district_type2')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกตำบล ---</option>");
						
						for(var i=0;i<data["result"].length;i++){
							$('#amphur_type2')
							.append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["AMPHUR_NAME"] +"</option>");
						}
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
	function loadDistrict(type){
		var amphurId = 0;
		if (type == "1") {
			amphurId = $('#amphur_type1').val();
		} else if (type == "2") {
			amphurId = $('#amphur_type2').val();
			}
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/customerregistration/loadDistrict/"+amphurId,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					if(type == "1"){
						$('#district_type1')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกตำบล ---</option>");
						
						for(var i=0;i<data["result"].length;i++){
							$('#district_type1')
						    .append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["DISTRICT_NAME"] +"</option>");
						}
						$('#zipCode_type1').val('');
					}else if(type == "2"){
						$('#district_type2')
					    .empty()
					    .append("<option disabled='disabled' selected='selected'>---กรุณาเลือกตำบล ---</option>");
						
						for(var i=0;i<data["result"].length;i++){
							$('#district_type2')
							.append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["DISTRICT_NAME"] +"</option>");
						}
						$('#zipCode_type2').val('');
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
	
	function loadZipCode(type){
		var amphurId = 0;
		if (type == "1") {
			districtId = $('#district_type1').val();
		} else if (type == "2") {
			districtId = $('#district_type2').val();
			}
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+districtId,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					if(type == "1"){
						if("00000"!=data.result.zip_CODE){
							$('#zipCode_type1').val(data.result.zip_CODE);
						}else{
							$('#zipCode_type1').val('');
						}
					}else if(type == "2"){
						if("00000"!=data.result.zip_CODE){
							$('#zipCode_type2').val(data.result.zip_CODE);
						}else{
							$('#zipCode_type2').val('');
						}
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
	</script>
</body>
</html>
