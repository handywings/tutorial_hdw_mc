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
<title>ออกใบงานติดตั้ง</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
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
						<div class="col-md-7">
							<h3>ออกใบงานติดตั้ง</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/serviceapplicationlist">รายการใบสมัครใช้บริการ</a></li>
								<li class="breadcrumb-item active">ออกใบงานติดตั้ง</li>
							</ol>
						</div>
					</div>

					<div class="card mb30">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0"><span class="ti-file"></span>&nbsp;เลขที่ใบสมัคร #${serviceApplicationBean.serviceApplicationNo }&nbsp;/&nbsp;${serviceApplicationBean.companyBean.companyName }</h4>
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
													<label for="exampleInputEmail1"><b>วัน/เวลาที่สะดวกในการติดตั้ง</b></label>
													<br>${serviceApplicationBean.easyInstallationDateTime}
												</div>
<!-- 												<div class="col-md-2"> -->
<!-- 													<label for="exampleInputEmail1"><b>เวลาที่สะดวกในการติดตั้ง</b></label> -->
<!-- 													<br>09:30 - 10:00 น. -->
<!-- 												</div> -->
												<div class="col-md-4">
												<label for="exampleSelect1"><b>เขตชุมชน / โครงการที่ติดตั้ง</b>
													</label><br><c:out value="${zoneDetail}" /> <b>(<c:out value="${zoneName}" />)</b>
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
								
								<jsp:include page="worksheet/block_equipment_install.jsp"></jsp:include>
								
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-email"></span> ที่อยู่ในการจัดส่งเอกสารการเงิน&nbsp;&nbsp;&nbsp;</legend>
											<c:forEach var="addressApplicationBean" items="${serviceApplicationBean.addressList}" varStatus="i">
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
												<c:forEach var="addressApplicationBean" items="${serviceApplicationBean.addressList}" varStatus="i">
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
								
								<c:if test="${serviceApplicationBean.remark != null && serviceApplicationBean.remark != \"\"}">
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
								
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center>
											<c:if test="${serviceApplicationBean.status.stringValue eq 'D' }">
												<button type="button" onclick="submitData('H');" class="btn btn-info label-left b-a-0 waves-effect waves-light">
													<span class="btn-label"><i class="fa fa-paper-plane"></i></span>
													บันทึกและออกใบงาน
												</button>
											</c:if>
										</center>
									</div>
								</div>
								
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- Footer -->
			<jsp:include page="modal_productItem.jsp"></jsp:include>
			
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
	
	function submitData(status){
		
		if(!validateSN()){
			$('.errorInternet').hide();
			$('html, body').animate({ scrollTop: $('#div_block_equipment_install').offset().top }, 'slow');
			$('.errorSN').fadeIn();
			$('#aEquiment').click();
		}else if(!validateInternet()){
			$('.errorSN').hide();
			$('html, body').animate({ scrollTop: $('#div_block_equipment_install').offset().top }, 'slow');
			$('.errorInternet').fadeIn();
			$('#aInternetUser').click();
// 			setTimeout(function(){$('#aInternetUser').click();}, 200);
		} else {
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
			console.log(serviceApplicationBean);
			
			serviceApplicationBean = JSON.stringify(serviceApplicationBean);
		    formData.append("serviceApplicationBean", serviceApplicationBean);
		    
		    $('.preloader').modal('show');
			$.ajax({dataType : 'json',
				contentType : "application/json; charset=utf-8",
		    	url : "${pageContext.request.contextPath}/serviceapplicationlist/createworksheetsetup",
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
						openAlert('warning','มีข้อผิดพลาด');
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
	}
	
	function validateSN(){
		var validateReturn = true;
		$.each($('.serial-no'), function( index, value ) {
			if($(this).text()==""){
				validateReturn = false;
			}
		});
		return validateReturn;
	}
	
	function validateInternet(){
		var validateReturn = true;
		$.each($('.internet-username'), function( index, value ) {
			if($(this).text()==""){
				validateReturn = false;
			}
		});
		return validateReturn;
	}
	
	function generateBeanJson(statusSubmit){
		var serviceApplicationBean = {};
		
		serviceApplicationBean.id = "${serviceApplicationBean.id}";
		
		var status = {};
		status.stringValue = statusSubmit;
		serviceApplicationBean.status = status;

		var productitemList = [];
		var productItemWorksheetBeanList = [];
		$('#table_template_equiment > tbody  > tr').each(function() {
			var item = {};
			 $(this).find('input').each(function (i) {
				 //alert($(this).val());
					switch (i) {
							case 0:
								var productItemBean = {};
								productItemBean.id = $(this).val();
								item.productItemBean = productItemBean;
								break;
							case 1:
								item.type = $(this).val();
								break;
							case 2:
								item.id = $(this).val();
								break;
							case 3:
								var equipmentProductItemBean = {};
								equipmentProductItemBean.id = $(this).val();
								item.equipmentProductItemBean = equipmentProductItemBean;
								break;
							case 4:
								item.quantity = $(this).val();
								break;
							case 5:
								item.price = $(this).val();
								break;
							case 6:
								if($(this).is(":checked")){
									item.free = true;
								}else{
									item.free = false;
								} 
								break;
							case 7:
								if($(this).is(":checked")){
									item.lend = true;
								}else{
									item.lend = false;
								}  
								break;
							case 8:
								item.amount = $(this).val();
								break;
							}
					
						}); //end find textbox
						
			 			//add to arrayjson
			 			productItemWorksheetBeanList.push(item);
					}); //end find tr
					
					$('#table_template_internet > tbody  > tr').each(function() {
						var item = {};
						 $(this).find('input').each(function (i) {
							 //alert($(this).val());
								switch (i) {
										case 0:
											var productItemBean = {};
											productItemBean.id = $(this).val();
											item.productItemBean = productItemBean;
											break;
										case 1:
											item.type = $(this).val();
											break;
										case 2:
											item.id = $(this).val();
											break;
										case 3:
											var internetProductBeanItem = {};
											internetProductBeanItem.id = $(this).val();
											item.internetProductBeanItem = internetProductBeanItem;
											break;
										}
								
									}); //end find textbox
									
						 			//add to arrayjson
						 			productItemWorksheetBeanList.push(item);
								}); //end find tr
								
								$('#table_template_service > tbody  > tr').each(function() {
									var item = {};
									 $(this).find('input').each(function (i) {
										 //alert($(this).val());
											switch (i) {
													case 0:
														var productItemBean = {};
														productItemBean.id = $(this).val();
														item.productItemBean = productItemBean;
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
														item.amount = $(this).val();
														break;
													}
											
												}); //end find textbox
												
									 			//add to arrayjson
									 			productItemWorksheetBeanList.push(item);
											}); //end find tr
								
					var productitemLists = {};
					productitemLists.productItemWorksheetBeanList = productItemWorksheetBeanList;
					productitemList.push(productitemLists);
					
		serviceApplicationBean.productitemList = productitemList;
			
		return serviceApplicationBean;
	}	

function calculate(){
	
	$.each($('#table_template_equiment tbody tr'), function( index, value ) {

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
	
	$.each($('#table_template_service tbody tr'), function( index, value ) {
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

function loadProductItemEdit(id,type,spanId){
	$('.preloader').modal('show');
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/requisition/loadEquipmentProductWithId/"+id,
		dataType : 'json',
		async: false,
		//timeout : 100000,
		success : function(data) {
// 			dataEquipmentProduct = data;
			if(data["error"] == false){
				var rows = "";
				for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
					var startRow = "<tr>";
					var endRow = "</tr>";
					
					if(data["result"]["equipmentProductItemBeans"][i].repair){
						startRow = "<tr style=' background-color: rgba(255, 152, 0, 0.07); ' >";
					}
					
					var tdRadio = "<td style='vertical-align: middle;'><center>"+
					"<label class='custom-control custom-radio'> <input name='radioProduct' type='radio' " +
					"value='" + data["result"]["equipmentProductItemBeans"][i].serialNo +"|"+ data["result"]["equipmentProductItemBeans"][i].id +"|"+ spanId + "' class='custom-control-input'> " +
					"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
					"</label></center></td>";
					
					var columnProductCode = "<td style='vertical-align: middle;'><b>"+ data["result"].productCode +"</b></td>";
					
					if(data["result"]["equipmentProductItemBeans"][i].repair){
						columnProductSerial = "<td style='vertical-align: middle;'><center><a target='_blank' data-toggle='tooltip' data-placement='bottom' title='' data-original-title='ดูรายละเอียด' href='${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/" + data["result"]["equipmentProductItemBeans"][i].id + "'>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +'</a><br><span style="color: #FF9800;">(มีประวัติการซ่อม)</span></center></td>';
					}else{
						columnProductSerial = "<td style='vertical-align: middle;'><center><a target='_blank' data-toggle='tooltip' data-placement='bottom' title='' data-original-title='ดูรายละเอียด' href='${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/" + data["result"]["equipmentProductItemBeans"][i].id + "'>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +'</a></center></td>';
					}
					var columnProductRefDoc = "<td style='vertical-align: middle;'><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
					var columnProductNumberImport = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].numberImport +"</center></td>";
					
					var status = data["result"]["equipmentProductItemBeans"][i].status;
					if(status == 1) {
						status = "ใช้งานได้ปกติ";
					} else if(status == 0) {
						status = "เสีย";
					} else if(status == 6) {
						status = "อยู่ระหว่างซ่อม";
					} else if(status == 7) {
						status = "CA";
					}
					
					var columnProductStatus = "<td style='vertical-align: middle;'><center>" + status + "</center></td>";
					
					rows = rows + startRow + tdRadio;
					rows = rows + columnProductSerial + columnProductNumberImport + columnProductStatus + columnProductRefDoc;
				}
				
				$('#tableEquipmentProductChild > tbody').html(rows);
				
				setTimeout(
					function(){
						$('#addEquipmentProductItem').css('overflow-y', 'auto');
						$('#addEquipmentProductItem').modal('show');
						$('.preloader').modal('hide');
						$('#barCodeScan').val('');
					}, 200);
			}else{
// 				dataEquipmentProduct = '';
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

$("#barCodeScan").keypress(function(e,val) {
    if(e.which == 13) {
    	e.preventDefault();
    	chooseSerialNumber(this.value);
    }
});

function chooseSerialNumber(sn){
	
	$('input[name=radioProduct]:radio').each(function () {
		var data = $(this).val();
// 		console.log(data);
// 		console.log((data.toLowerCase().indexOf(sn) >= 0));
		if(data.toLowerCase().indexOf(sn) >= 0){
			$(this).attr('checked', 'checked');
			appendToListProductItem();
		}
	});
	console.log(sn);
}

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

function appendToListProductItem(){
	var radioProduct = $("input:radio[name ='radioProduct']:checked").val();
	if(typeof radioProduct != 'undefined'){
		console.log(radioProduct);

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

</script>
</body>
</html>
