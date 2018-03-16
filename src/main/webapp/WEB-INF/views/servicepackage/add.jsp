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
<title>เพิ่ม Package บริการ</title>
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
						<div class="col-md-12">
							<h3>เพิ่ม Package บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">Package บริการ</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/servicepackage">รายการ
										Package บริการ</a></li>
								<li class="breadcrumb-item active">เพิ่มรายการ Package
									บริการ</li>
							</ol>
						</div>
					</div>
					<div class="card mt05 mb30">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-card-giftcard"></i>&nbsp;&nbsp;ข้อมูล Package บริการ</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<form id="formAddServicepackage">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายละเอียด Package
												บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mt05">
												<div class="form-group">
												<div class="col-md-3">
													<div class="">
														<label for="exampleInputEmail1"><b>รหัส
																Package บริการ<span class="text-red"> *</span></b></label> 
																<input type="text" class="form-control"
															id="packageCode" aria-describedby="emailHelp" name="packageCode">
													<div class="messages"></div>
													</div>
												</div>
												</div>
												<div class="form-group">
												<div class="col-md-9">
													<div class="">
														<label for="exampleInputEmail1"><b>ชื่อ
																Package บริการ<span class="text-red"> *</span></b></label> <input type="text" class="form-control"
															id="packageName" aria-describedby="emailHelp" name="packageName">
													<div class="messages"></div>
													</div>
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
																<option value="${servicePackageTypeBean.id }">${servicePackageTypeBean.packageTypeName }</option>
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
																<option value="${companyBean.id }">${companyBean.companyName }</option>
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
														<input type="number" min="0" class="form-control" value="0.00"
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
														<input type="number" min="0" class="form-control" value="0.00"
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
														class="custom-control-description">&nbsp;ค่าบริการแบบรอบบิล</span>
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
												<div class="form-group">
												<div class="col-md-3">
													<label><b>ค่าบริการ<span class="text-red"> *</span></b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control" value="0.00"
															id="oneServiceFee">
														<div class="input-group-addon">บาท</div>
													</div>
												</div>
												</div>
											</div>
											<div class="row mt05" id="priceMonth">
												<div class="col-md-3">
												<div class="form-group">
													<label style="width: 100%;"><b>ค่าบริการแบบรอบบิล (ไม่รวม Vat)<span class="text-red"> *</span></b></label>
													<div class="input-group" style="width: 50%; float: left;">
														<input type="number" min="0" class="form-control" value="0.00"
															id="monthlyServiceFee" name="monthlyServiceFee">
														<div class="input-group-addon">บาท</div>
													</div>
													<div style="width: 10%; float: left; margin-top: 0.3em;">
														<center>ต่อ</center>
													</div>
													<div class="input-group" style="width: 40%; float: left;">
														<select class="form-control" id="perMounth">
															<c:forEach var="i" begin="1" end="12">
																<option value="${i}">${i} เดือน</option>
															</c:forEach>
														</select>
													</div>
													<div class="messages"></div>
												</div> 
												</div>
<!-- 												<div class="form-group"> -->
<!-- 												<div class="col-md-3"> -->
<!-- 													<label><b>จำนวนรอบบิลใช้ฟรี<span class="text-red"> *</span></b></label> -->
<!-- 													<div class="input-group"> -->
<!-- 														<input type="text" class="form-control" -->
<!-- 															id="firstBillFree" value="0"> -->
<!-- 														<div class="input-group-addon">รอบบิล</div> -->
<!-- 													</div> -->
<!-- 												</div> -->
<!-- 												</div> -->
												<div class="form-group">
												<div class="col-md-3">
													<label><b>ส่วนลดรอบบิลแรก<span class="text-red"> *</span></b></label>
													<div class="input-group">
														<input type="number" min="0" class="form-control"
															id="firstBillFreeDisCount" value="0.00">
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
													<font style="color: gray;">หากต้องการเพิ่มบริการหรือวัสดุอุปกรณ์ที่ติดตั้ง กรุณากดปุ่ม <b>"เลือกสินค้า"</b></font>
													<button type="button" onclick="openModalSearchProduct()"
														class="btn btn-info label-left float-xs-right mr-0-5">
														<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
													</button>
												</div>
											</div>
											<hr>
											<div class="row">
												<div class="col-md-12">
													<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover" id="tableTemplate" style="display: none;">
														<thead class="thead-bg">
															<tr>
																<th width="50px;"><center>ลำดับ</center></th>
																<th><center>รหัสสินค้า</center></th>
																<th>ข้อมูลสินค้า</th>
																<th><center>ประเภทสินค้า</center></th>
																<th style="width: 160px;"><center>จำนวน</center></th>
																<th width="50px;"><center>ฟรี</center></th>
																<th width="50px;"><center>ยืม</center></th>
																<th width="50px;"></th>
															</tr>
														</thead>
														<tbody id="tbody-child-item">
														</tbody>
													</table>
													</div>
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
					url : "${pageContext.request.contextPath}/servicepackage/saveServicePackage",
					dataType : 'json',
					async : false,
					data : JSON.stringify(servicePackageBean),
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/servicepackage";
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
	var form = document.querySelector("form#formAddServicepackage");
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
