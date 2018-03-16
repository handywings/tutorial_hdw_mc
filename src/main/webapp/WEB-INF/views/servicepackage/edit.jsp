<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="package" scope="request"/>
<c:set var="subMenu" value="servicepackage" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>แก้ไข  Package บริการ</title>
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
							<h3>แก้ไข  Package บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">Package บริการ</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/servicepackage">รายการ
										Package บริการ</a></li>
								<li class="breadcrumb-item active">แก้ไข Package
									บริการ</li>
							</ol>
						</div>
						<div class="col-md-5" align="right">
<%-- 							<c:if test="${servicePackageBean.active }"> --%>
								<perm:permission object="M14.check" >									
									<c:choose>
										<c:when test="${servicePackageBean.active }">
											<button type="button" onclick="openDialogCancel(${servicePackageBean.id });"
												class="btn btn-danger label-left float-xs-right mr-0-5 mb05">
												<span class="btn-label"><i class="fa fa-ban"></i></span>ยกเลิกบริการ
											</button>
										</c:when>
										<c:otherwise>
											<button type="button" onclick="openDialogEnable(${servicePackageBean.id });"
												class="btn btn-success label-left float-xs-right mr-0-5 mb05">
												<span class="btn-label"><i class="fa fa-check"></i></span>เปิดใช้งานบริการ
											</button>
										</c:otherwise>
									</c:choose>
								</perm:permission>
<%-- 							</c:if> --%>
						</div>
					</div>
					<div class="card mt05 mb30">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-card-giftcard"></i>&nbsp;&nbsp;ข้อมูล Package บริการ</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
							<form id="formUpdateServicepackage">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายละเอียด Package
												บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mt05">
											<div class="form-group">
												<div class="col-md-3">
														<label for="exampleInputEmail1"><b>รหัส
																Package บริการ<span class="text-red"> *</span></b></label> 
																<input type="text" class="form-control" name="packageCode"
															id="packageCode" aria-describedby="emailHelp" value="${servicePackageBean.packageCode}">
															<input type="hidden" value="${servicePackageBean.id}" id="packageId" />
													<div class="messages"></div>													
												</div>
												
												</div>
												<div class="form-group">
												<div class="col-md-9">
														<label for="exampleInputEmail1"><b>ชื่อ
																Package บริการ<span class="text-red"> *</span></b></label> <input type="text" class="form-control" value="${servicePackageBean.packageName}"
															id="packageName" aria-describedby="emailHelp" name="packageName">
													<div class="messages"></div>
												</div>												
												</div>
											</div>
											<div class="row">
											<div class="form-group">
												<div class="col-md-6">
													<div class="">
														<label for="exampleSelect1"><b>ประเภทบริการ<span class="text-red"> *</span></b></label>
														<select class="form-control" id="serviceType">
															<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
																<option value="${servicePackageTypeBean.id }"
																<c:if test="${servicePackageBean.serviceType.id eq  servicePackageTypeBean.id}">selected</c:if>
																>${servicePackageTypeBean.packageTypeName }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												</div>
												<div class="form-group">
												<div class="col-md-6">
													<div class="">
														<label for="exampleSelect1"><b>สังกัดบริษัท<span class="text-red"> *</span></b></label>
														<select class="form-control" id="company">
															<c:forEach items="${companyBeans}" var="companyBean"> 
																<option value="${companyBean.id }"
																<c:if test="${servicePackageBean.company.id eq  companyBean.id}">selected</c:if>
																>${companyBean.companyName }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												</div>
											</div>

											<div class="row mt05">
												<div class="form-group">
												<div class="col-md-3">
													<label><b>ค่าติดตั้ง<span class="text-red"> *</span></b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control" value="${servicePackageBean.installationFee}"
															id="installationFee" name="installationFee">
														<div class="input-group-addon">บาท</div>
													</div>
													<div class="messages"></div>
												</div>
												</div>
												<div class="form-group">
												<div class="col-md-3">
													<label><b>ค่ามัดจำอุปกรณ์<span class="text-red"> *</span></b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control" value="${servicePackageBean.deposit}"
															id="deposit" name="deposit">
														<div class="input-group-addon">บาท</div>
													</div>
													<div class="messages"></div>
												</div>
												</div>
												<div class="form-group">
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>รูปแบบการคิดค่าบริการ<span class="text-red"> *</span></b></label><br>
													<label class="custom-control custom-radio"> <input
														id="radio1" name="monthlyService" type="radio"
														checked="checked" value="month"
														class="custom-control-input"> <span
														class="custom-control-indicator"></span> <span
														class="custom-control-description">&nbsp;ค่าบริการรายเดือน</span>
													</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
														id="radio2" name="monthlyService" type="radio" value="one-service"
														class="custom-control-input"> <span
														class="custom-control-indicator"></span> <span
														class="custom-control-description">&nbsp;ค่าบริการครั้งเดียว</span>
													</label>
												</div>
												</div>
											</div>
											<div class="row mt05" id="once" style="display: none;">
												<div class="col-md-3">
													<label><b>ค่าบริการ (ไม่รวม Vat)</b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control" value="${servicePackageBean.oneServiceFee}"
															id="oneServiceFee">
														<div class="input-group-addon">บาท</div>
													</div>
												</div>
											</div>
											<div class="row mt05" id="priceMonth">
											<div class="form-group">
												<div class="col-md-3">
													<label style="width: 100%;"><b>ค่าบริการแบบรอบบิล (ไม่รวม Vat)<span class="text-red"> *</span></b></label>
													<div class="input-group" style="width: 50%; float: left;">
														<input type="number" min="0" class="form-control" value="${servicePackageBean.monthlyServiceFee}"
															id="monthlyServiceFee" name="monthlyServiceFee">
														<div class="input-group-addon">บาท</div>
													</div>
													<div style="width: 10%; float: left; margin-top: 0.3em;">
														<center>ต่อ</center>
													</div>
													<div class="input-group" style="width: 40%; float: left;">
														<select class="form-control" id="perMounth">
															<c:forEach var="i" begin="1" end="12">
																<option value="${i}" <c:if test="${servicePackageBean.perMounth == i}">selected</c:if> >${i} เดือน</option>
															</c:forEach>
														</select>
													</div>
													<div class="messages"></div>
												</div> 
												</div>
<!-- 												<div class="form-group"> -->
<!-- 												<div class="col-md-3"> -->
<!-- 													<label><b>รอบบิลแรกใช้ฟรี</b></label> -->
<!-- 													<div class="input-group"> -->
<!-- 														<input type="number" class="form-control" -->
<%-- 															id="firstBillFree" value="${servicePackageBean.firstBillFree }"> --%>
<!-- 														<div class="input-group-addon">เดือน</div> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 												</div> -->
												<div class="form-group">
												<div class="col-md-3">
													<label><b>ส่วนลดรอบบิลแรก<span class="text-red"> *</span></b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control"
															id="firstBillFreeDisCount" value="${servicePackageBean.firstBillFreeDisCount }">
														<div class="input-group-addon">บาท</div>
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
											<legend>&nbsp;&nbsp;&nbsp;รายชื่อบริการและ วัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-12">
													<button type="button" onclick="openModalSearchProduct()"
														class="btn btn-info label-left float-xs-right mr-0-5">
														<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
													</button>
												</div>
											</div>
											<hr>
											<div class="row">
												<div class="col-md-12">
													<table class="table table-bordered mb-0 table-hover" id="tableTemplate">
														<thead class="thead-bg">
															<tr>
																<th><center>ลำดับ</center></th>
																<th><center>รหัสสินค้า</center></th>
																<th>ข้อมูลสินค้า</th>
																<th><center>ประเภทสินค้า</center></th>
																<th style="width: 130px;"><center>จำนวน</center></th>
																<th><center>ฟรี</center></th>
																<th><center>ยืม</center></th>
																<th></th>
															</tr>
														</thead>
														<tbody id="tbody-child-item">
															<c:forEach items="${servicePackageBean.template.listTemplateServiceItemBean}" var="item" varStatus="i"> 
															 <tr class="trNewItem">
															 <td><center>${i.count }</center></td>
															 <td>
																 <center>${item.productBean.productCode }</center>
																 <input type="hidden" value="${item.productBean.id }">
																 <input type="hidden" value="${item.productBean.type }">
															 </td>
															 <td>
															 	${item.productBean.productName}
															 </td>
															 <td>
															 	<center>${item.productBean.productCategory.equipmentProductCategoryName }</center>
															 </td>
															 <td>
															 	<center>
															 		<div class="input-group">
																 	<input type="number" min="1" value="${item.quantity }" class="form-control">
																 	<div class="input-group-addon">
																 	${item.productBean.unit.unitName }
																 	</div>
																 	</div>
															 	</center>
															 </td>
															 <td>
															 	<center>
																 	<label class="custom-control custom-checkbox">
																 	<input type="checkbox" class="custom-control-input" <c:if test="${item.free }">checked</c:if> >
																 	<span class="custom-control-indicator"></span> 
																 	<span class="custom-control-description"></span></label>
															 	</center>
															 </td>
															 <td>
 															 <c:if test="${item.productBean.type eq 'E' &&
 															 not empty item.productBean.equipmentProductItemBeans &&
  															 item.productBean.equipmentProductItemBeans.size() > 0 &&
 															 item.productBean.equipmentProductItemBeans.get(0).serialNo ne '' }">
																 <center>
																 <label class="custom-control custom-checkbox">
																 <input type="checkbox" class="custom-control-input" <c:if test="${item.lend }">checked</c:if> >
																 <span class="custom-control-indicator"></span> 
																 <span class="custom-control-description"></span>
																 </label>
																 </center>
															</c:if>
															 </td>
															 <td>
																 <center>
																 <a class="removeRowsChildNew" style="cursor:pointer"><span class="ti-trash" data-toggle="tooltip" data-placement="bottom" title="" ></span></a>
																 </center>
															 </td>
															 </tr>
															 </c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-12 mt05" align="center">
											<button type="submit"
												class="btn btn-success label-left mr-0-5">
												<span class="btn-label"><i class="ti-check"></i></span>บันทึก
											</button>
											<a href="${pageContext.request.contextPath}/servicepackage">
											 <button type="button" class="btn btn-danger	 label-left  mr-0-5">
												<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
											</button></a>
									</div>
								</div>
</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- modal search product -->
			<jsp:include page="modal_product.jsp"></jsp:include>
			<!-- end modal search product -->
			<jsp:include page="cancel_service.jsp"></jsp:include>
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
		
		var mounthService = ${servicePackageBean.monthlyService};
		if(mounthService){
			$('input:radio[name="monthlyService"]').filter('[value="month"]').attr('checked', true);
			$('#priceMonth').fadeIn();
			$('#once').hide();
		}else{
			$('input:radio[name="monthlyService"]').filter('[value="one-service"]').attr('checked', true);
			$('#once').fadeIn();
			$('#priceMonth').hide();
		}
		
		//click remove in child list
		$('#tableTemplate > tbody').on('click', '.removeRowsChildNew', function () {
		    $(this).closest('tr').remove();
		    calculateOrderCount();
		});
		
	});
	
	function calculateOrderCount(){
		//$('#tbody-child-item tr').find('td:not(:empty):first')
		var countInChild = 1;
		$('#tbody-child-item tr').each(function(){
			var td = $(this).children('td:first');
			td.html('<center>'+(countInChild++) + '</center>');
		});
	}
	
	
		$('input[name=monthlyService]').change(function() {
			if (this.value == "month") {
				$('#priceMonth').fadeIn();
				$('#once').hide();
			} else {
				$('#once').fadeIn();
				$('#priceMonth').hide();
			}
		});
		
		
		
		function saveServicePackage(){
			$('.preloader').modal('show');
			var servicePackageBean = {};
			servicePackageBean.id = $('#packageId').val();
			servicePackageBean.packageCode = $('#packageCode').val();
			servicePackageBean.packageName = $('#packageName').val();
			servicePackageBean.installationFee = $('#installationFee').val();
			servicePackageBean.deposit = $('#deposit').val();
			
			var radioIsSeperateMounth = $("input:radio[name ='monthlyService']:checked").val();
			if(radioIsSeperateMounth == 'month'){
				servicePackageBean.monthlyService = true;
				servicePackageBean.monthlyServiceFee = $('#monthlyServiceFee').val();
				servicePackageBean.perMounth = $('#perMounth').val();
				servicePackageBean.firstBillFree = $('#firstBillFree').val();
				servicePackageBean.firstBillFreeDisCount = $('#firstBillFreeDisCount').val();
			}else{
				servicePackageBean.monthlyService = false;
				servicePackageBean.oneServiceFee = $('#oneServiceFee').val();
			}
			
			//company
			CompanyBean = {};
			CompanyBean.id = $('#company').val();
			servicePackageBean.company = CompanyBean;
			//service type
			ServicePackageTypeBean = {};
			ServicePackageTypeBean.id = $('#serviceType').val();
			servicePackageBean.serviceType = ServicePackageTypeBean;
			//template
			TemplateServiceBean = {};
			var templateItem = [];
			$('#tableTemplate > tbody  > tr.trNewItem').each(function() {
				var item = {};
				 $(this).find('input').each(function (i) {
					 //alert($(this).val());
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
								}
						
							}); //end find textbox
				 		//add to arrayjson
				 			templateItem.push(item);
						}); //end find tr
						
				TemplateServiceBean.listTemplateServiceItemBean = templateItem;
				servicePackageBean.template = TemplateServiceBean;
				
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/servicepackage/updateServicePackage",
					dataType : 'json',
					async : false,
					data : JSON.stringify(servicePackageBean),
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/servicepackage/edit/"+$('#packageId').val();
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
		
	</script>
	
	<script>
	var constraints = {
		packageCode : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		packageName : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},		
		deposit : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			},
			numericality : {
				onlyInteger : false,
				greaterThanOrEqualTo : 0,
				message : "^กรุณากรอกเป็นตัวเลขเท่านั้น"	
			}
		},
		installationFee : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			},
			numericality : {
				onlyInteger : false,
				greaterThanOrEqualTo : 0,
				message : "^กรุณากรอกเป็นตัวเลขเท่านั้น"
			}
		},
		monthlyServiceFee : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			},
			numericality : {
				onlyInteger : false,
				greaterThanOrEqualTo : 0,
				message : "^กรุณากรอกเป็นตัวเลขเท่านั้น"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formUpdateServicepackage");
	form.addEventListener("submit", function(ev) {
		ev.preventDefault();
		handleFormSubmit(form);
	});

	function handleFormSubmit(form, input) {
		// validate the form aainst the constraints
		var errors = validate(form, constraints);
		// then we update the form to reflect the results
		showErrors(form, errors || {});
		if (!errors) {
			saveServicePackage();
		}
	}

	// Hook up the inputs to validate on the fly
	var inputs = document.querySelectorAll("input, textarea, select");
	for (var i = 0; i < inputs.length; ++i) {
		inputs.item(i).addEventListener("change", function(ev) {
			var errors = validate(form, constraints) || {};
			showErrorsForInput(this, errors[this.name])
		});
	}
</script>
	
</body>
</html>
