<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="serviceapplication" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>สมัครใช้บริการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/dropify/css/dropify.min.css" />">
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
					<div class="row mt05 mb05" >
						<div class="col-md-12">
							<h3>สมัครใช้บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item active">สมัครใช้บริการ</li>
							</ol>
						</div>
					</div>
					<div class="card mb40">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-marker-alt"></i>&nbsp;&nbsp;กรอกข้อมูลสมาชิก
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;&nbsp;<span class="ti-user"></span> ข้อมูลผู้สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>เลือกข้อมูลลูกค้า</b></label><br>
												</div>
												<div class="col-md-6">													
													<label class="custom-control custom-radio"> <input
														checked="checked" id="radio2" name="memberType" type="radio" value=""
														class="custom-control-input"> <span
														class="custom-control-indicator"></span> <span
														class="custom-control-description">&nbsp;ลูกค้ารายใหม่</span>
													</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
														id="radio1" name="memberType" type="radio"
														value="old" class="custom-control-input">
														<span class="custom-control-indicator"></span> <span
														class="custom-control-description">&nbsp;ลูกค้ารายเก่า</span>
													</label>
<!-- 													<button type="button" onclick="smartCardReader()" -->
<!-- 														class="btn btn-warning label-left float-xs-right mr-0-5"> -->
<!-- 														<span class="btn-label"><i class="typcn typcn-upload-outline"></i></span>อ่านข้อมูลจากบัตรประชาชน -->
<!-- 													</button> -->
												</div>
												<div class="col-md-6">
													<label for=""><b>บริษัท<span class="text-red">
																*</span></b></label> <select class="form-control" id="add-company"
														name="add_company">
														<c:forEach var="companys" items="${companys}"
															varStatus="i">
															<option value="${companys.id}">${companys.companyName}</option>
														</c:forEach>
													</select>

													<div class="messages"></div>
												</div>
												<div id="oldcustomers" style="display: none;">
												<form action="listCustomer" method="post">
													<div class="col-md-2">
														<button type="button" data-toggle="modal"
															onclick="openModalSearchCustomer();"
															class="btn btn-info label-left float-xs-right col-xs-12 btn-xs">
															<span class="btn-label"><i
																class="ti-search"></i></span>ค้นหาลูกค้าเก่า
														</button>
													</div>
												</form>
											</div>
											</div>
											<hr>
											
											<jsp:include page="oldcustomers.jsp"></jsp:include>
											
											<jsp:include page="newcustomers.jsp"></jsp:include>
											
										</fieldset>
									</div>
								</div>
								
								<fieldset class="mt15">
									<legend>&nbsp;&nbsp;&nbsp;<span class="ti-id-badge"></span> เอกสาประกอบการสมัครสมาชิก&nbsp;&nbsp;&nbsp;</legend>
									<div class="row">
										<div class="col-md-2">

											<label class="custom-control custom-checkbox"> <input
												type="checkbox" id="checkbox_house_registration_documents"
												class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">สำเนาทะเบียนบ้าน</span>
											</label> 
<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="house_registration_documents" class="custom-file-input"> -->
<!-- 												<span id="house_regis_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
										</div>
										<div class="col-md-3">
											<label class="custom-control custom-checkbox"> <input
												type="checkbox" id="checkbox_identity_card_documents"
												class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">สำเนาบัตรประจำตัวประชาชน</span>
											</label> 
											<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="identity_card_documents" class="custom-file-input"> -->
<!-- 												<span id="iden_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
											
										</div>
										<div class="col-md-7">
										
											<label class="custom-control custom-checkbox"> <input
												type="checkbox" id="checkbox_other_documents"
												class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">เอกสารอื่นๆ</span>
											</label> 										
											
<!-- 											<label class="custom-file"> -->
<!-- 												<input type="file" id="other_documents" class="custom-file-input"> -->
<!-- 												<span id="other_span" class="custom-file-control"></span> -->
<!-- 											</label> -->
										</div>
									</div>		
									<div class="row">
										<div class="col-md-12">
											<textarea class="form-control" style="display:none" id="remark_other_documents" rows="3"></textarea>
										</div>
									</div>
								</fieldset>
								
								<jsp:include page="block_package.jsp"></jsp:include>
								
								<jsp:include page="block_detail_install.jsp"></jsp:include>
								
								<jsp:include page="block_equipment_install.jsp"></jsp:include>
								
								<jsp:include page="block_shipping_address.jsp"></jsp:include>
								
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-write"></span> หมายเหตุ /
												ข้อมูลเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
											<textarea class="form-control" id="remark" rows="3"></textarea>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center>
											<button type="button" onclick="submitData('D');" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
												<span class="btn-label"><span class="ti-check"></span></span>
												<b>บันทึกข้อมูลใบสมัครแบบร่าง</b>
											</button>
<!-- 											<button type="button" onclick="submitData('H');" -->
<!-- 												class="btn btn-success w-min-sm mb-0-25 waves-effect waves-light">บันทึกและออกใบงาน</button> -->
										</center>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- modal search product -->
			<jsp:include page="modal_customer.jsp"></jsp:include>
			<!-- end modal search product -->
			
			<!-- modal search product -->
			<jsp:include page="modal_product.jsp"></jsp:include>
			<!-- end modal search product -->
			
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/dropify/js/dropify.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/js/forms-upload.js" />"></script>
		
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
		}
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
					
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
					$('#district1').html(option);
					
					$('#postcode1').val('');
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
	});
	//End change Province
	
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
			}
	});
	//End change Amphur
	//Begin change District
	$('#district1').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
// 					console.log(data);
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
					
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
					$('#district2').html(option);
					
					$('#postcode2').val('');
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
	});
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
			}
	});
	//End change Amphur
	//Begin change District
	$('#district2').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
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
	});
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
					
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
					$('#district3').html(option);
					
					$('#postcode3').val('');
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
	});
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
			}
	});
	//End change Amphur
	//Begin change District
	$('#district3').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
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
					
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
					$('#district4').html(option);
					
					$('#postcode4').val('');
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
	});
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
			}
	});
	//End change Amphur
	//Begin change District
	$('#district4').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
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
					
					var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
					$('#district5').html(option);
					
					$('#postcode5').val('');
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
	});
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
			}
	});
	//End change Amphur
	//Begin change District
	$('#district5').change(function(){
		var id = $(this).val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
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
					$('.div-detail-package').hide();
					$('.div-detail-package-ismonthlyservice').hide();
					
					$('#table_template tbody').html('');
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
		
	});
	
	$('#service_packages').change(function () {
		var id = $(this).val();
		
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
						$('#priceservice').html("ค่าบริการรอบบิล / " + data.result.perMounth + " เดือน");
						
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
						var checkedFree = "",checkedLend = "",salePrice_disabled = "",lend_disabled="",free_disabled="";
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
						
						if("I"==value.type){
							salePrice_disabled = "disabled";
							lend_disabled = "disabled";
							free_disabled = "disabled";
						}
						
						if("S"==value.type){
							lend_disabled = "disabled";
						}
						
						console.log(value);
						
						table_template += '<tr>\
							<td><center><span class="template-no">'+(index+1)+'</span></center>\
							<input class="productId" type="hidden" value="'+value.productBean.id+'">\
							<input class="productType" type="hidden" value="'+value.type+'"></td>\
							<td><center>'+(value.productBean.productCode||'')+'</center></td>\
							<td>'+value.productBean.productName+'</td>\
							<td scope="row" style="width: 180px;" ><div class="input-group">\
									<input type="number" style="text-align: center;" min="1" class="form-control quantity calculate" value="'+value.quantity+'" >\
									<div class="input-group-addon">'+value.productBean.unit.unitName+'</div>\
								</div>\
								<input type="checkbox" class="custom-control-input check-box-free"\
								'+checkedFree+' '+free_disabled+'>\
								<input type="checkbox" class="custom-control-input check-box-lend"\
								'+checkedLend+' '+lend_disabled+'>\
								<input class="salePrice" type="hidden" value="'+salePrice+'">\
							</td>\
								<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
								class="ti-trash remove-template" data-toggle="tooltip"\
								data-placement="bottom" title=""\
								data-original-title="ลบ"></span></a></td></tr>';
								
						/*	<td scope="row"><div class="input-group">\
									<input type="number" min="0" class="form-control salePrice calculate" style="text-align: right;" value="'+salePrice+'" '+salePrice_disabled+'>\
									<div class="input-group-addon">บาท</div>\
								</div></td>\
							<td align="center" style="vertical-align: middle;">\
									<label class="custom-control custom-checkbox">\
										<input type="checkbox" class="custom-control-input check-box-free"\
										'+checkedFree+' '+free_disabled+'> <span\
										class="custom-control-indicator"></span> <span\
										class="custom-control-description"></span>\
									</label>\
							</td>\
							<td align="center" style="vertical-align: middle;">\
									<label class="custom-control custom-checkbox">\
										<input type="checkbox" class="custom-control-input check-box-lend"\
										'+checkedLend+' '+lend_disabled+'> <span\
										class="custom-control-indicator"></span> <span\
										class="custom-control-description"></span>\
									</label>\
							</td>\
							<td scope="row"><div class="input-group">\
									<input type="number" class="form-control totalPrice" style="text-align: right;" value="'+totalPrice+'" disabled>\
									<div class="input-group-addon">บาท</div>\
								</div></td>\
							<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p"><span\
									class="ti-trash remove-template" data-toggle="tooltip"\
									data-placement="bottom" title=""\
									data-original-title="ลบ"></span></a>\
							</td>\
						</tr>'; */
						
					});
					
					$('#table_template tbody').html(table_template);
					
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
		
	});
	
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
	
	$.each($('#table_template tbody tr'), function( index, value ) {
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
			totalPrice.val(salePrice.val()*quantity.val());
		}

	});
	
}

function submitData(status){
	
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
	companyBean.id = $('#add-company').val();
	serviceApplicationBean.companyBean = companyBean;
	
	console.log(serviceApplicationBean);
	
	serviceApplicationBean = JSON.stringify(serviceApplicationBean);
    formData.append("serviceApplicationBean", serviceApplicationBean);
    
 	$('.preloader').modal('show');
	$.ajax({dataType : 'json',
		contentType : "application/json; charset=utf-8",
    	url : "${pageContext.request.contextPath}/serviceapplication/save",
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
				$('.preloader').modal('hide');
				openAlert('warning',data);
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
	
	serviceApplicationBean.addressList = addressList;
	
	serviceApplicationBean.easyInstallationDateTime = $('#daterange').val();

	var productitemList = [];
	$('#table_template > tbody  > tr').each(function() {
		var item = {};
		 $(this).find('input').each(function (i) {
				switch (i) {
						case 0:
							item.id = $(this).val();
							break;
						case 1:
							item.type = $(this).val();
							break;
						case 2:
							item.quantity = $(this).val();
							break;
// 						case 3:
// 							item.price = $(this).val();
// 							break;
						case 3:
							if($(this).is(":checked")){
								item.free = true;
							}else{
								item.free = false;
							} 
							break;
						case 4:
							if($(this).is(":checked")){
								item.lend = true;
							}else{
								item.lend = false;
							}  
							break;
						case 5:
							item.price = $(this).val();
							item.amount = $(this).val();
							break;
						}
				
					}); //end find textbox
					
		 			//add to arrayjson
		 			productitemList.push(item);
				}); //end find tr
				
	serviceApplicationBean.productitemList = productitemList;
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
	
	if($('#checkbox_other_documents').is(":checked")){
		serviceApplicationBean.remarkOtherDocuments = $('#remark_other_documents').val();
	}
	
	serviceApplicationBean.plateNumber = $('#plate_number').val();
	serviceApplicationBean.latitude = $('#latitude').val();
	serviceApplicationBean.longitude = $('#longitude').val();

	return serviceApplicationBean;
}

	$( "#checkbox_other_documents" ).click(function() {
	  $( "#remark_other_documents" ).toggle( "slow", function() {
	    // Animation complete.
	  });
	});
	
	function smartCardReader(){
// 		getJSONP("http://localhost:8080/smartcard/data/", function(data){
// 		    var json = JSON.stringify(data);
// 		    console.log(json);
// 		}); 
		
		$.getJSON('http://localhost:8080/smartcard/data/', function(data) {
			console.log(data);
   			//data is the JSON string
			$('#prefix').val(data.prename);
			$('#firstName').val(data.fname);
			$('#lastName').val(data.lname);
			
			var input = '<input type="file" id="edit-input-file-now-custom-2" \
				class="dropify" data-default-file="http://localhost:8080/smartcard/picture/'+data.fname+'.jpg" \
				data-height="210" />\
			<div class="messages"></div>';
			
			$('#div-dropify').html(input);
	        
			$('.dropify').dropify();
			
		});

	}
	
	function smartCardReaderOld(){
		$('.preloader').modal('show');
		setTimeout(function() {
			var url = "${pageContext.request.contextPath}/serviceapplication/smartCardReader";
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : url,
				dataType : 'json',
				async: false,
				//timeout : 100000,
				success : function(data) {
					console.log(data);
					data = data.result;
					$('input[name=sex]').val(data.sex);
					$('#prefix').val(data.th_Prefix);
					$('#firstName').val(data.th_Firstname);
					$('#lastName').val(data.th_Lastname);
					$('#identityNumber').val(data.citizenid);
					$('#no1').val(data.addrHouseNo);
					$('#section1').val(data.addrVillageNo);
					
					var input = '<input type="file" id="edit-input-file-now-custom-2" \
						class="dropify" data-default-file="'+data.photoPart+'" \
						data-height="210" />\
					<div class="messages"></div>';
					
					$('#div-dropify').html(input);
	                
					var drEvent = $('.dropify').dropify();
					
					var sex = data.sex;
					if(sex="male"){
						$('#radio1').prop("checked",true);

					}else{
// 						$('#radio1').removeAttr('checked');
// 						$('#radio1').prop("checked", true);
// 						$('#radio1').prop("checked",false);
						$('#radio2').prop("checked",true);
					}					

// 					$('input:radio[name="sex"][value=' + sex + ']').prop('checked', true);
					
					var $radios = $('input:radio[name=sex]');
				    if($radios.is(':checked') === false) {
				        $radios.filter('[value=male]').prop('checked', true);
				    }
					
					var addrAmphurId = data.addrAmphur;
					var addrTambolId = data.addrTambol;
					var id = data.addrProvince;
					$('#province1').val(id).trigger('change');
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
								
								var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>';
								$.each(dataAddress1.result.amphurBeans, function( index, value ) {
									option += '<option value="'+value.id+'">'+value.AMPHUR_NAME+'</option>';
								});
								
								$('#amphur1').html(option);
								
								var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
								$('#district1').html(option);
								
								$('#postcode1').val('');
								setTimeout(function() {$('.preloader').modal('hide');}, 200);
								
								$('.preloader').modal('show');
									if(dataAddress1){
										var option = '<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>';
										$.each(dataAddress1.result.amphurBeans, function( index, val ) {
											if(addrAmphurId==val.id){
												$.each(val.districtBeans, function( index, value ) {
													option += '<option value="'+value.id+'">'+value.DISTRICT_NAME+'</option>';
												});
												$('#postcode1').val('');
											}
										});
										$('#district1').html(option);
										setTimeout(function() {
											$('#amphur1').val(addrAmphurId).trigger('change');
											$('#district1').val(addrTambolId).trigger('change');
											$('.preloader').modal('hide');}, 200);
										
										$.ajax({
											type : "GET",
											contentType : "application/json; charset=utf-8",
											url : "${pageContext.request.contextPath}/serviceapplication/getDistrict/"+addrTambolId,
											dataType : 'json',
											//timeout : 100000,
											success : function(data) {
												if(data["error"] == false){
//								 					console.log(data);
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
								
							}
						},
						error : function(e) {
							console.log("ERROR: ", e);
						},
						done : function(e) {
							console.log("DONE");
						}
					});
					
					
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
					
				},
				error : function(e) {
					console.log("ERROR: ", e);
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
				},
				done : function(e) {
					console.log("DONE");
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
				}
			});
		}, 200);
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
	            lat: 13.556062165266543,
	            lon: 100.27661451230313,
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
