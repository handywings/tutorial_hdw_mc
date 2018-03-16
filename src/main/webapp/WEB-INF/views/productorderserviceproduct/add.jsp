<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productadd" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รับสินค้าเข้า / เพิ่มบริการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="material-design fixed-sidebar fixed-header">
	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<!-- Content -->
		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row mt05 mb05" >
						<div class="col-md-12">
							<h3>รับสินค้าเข้า / เพิ่มบริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active"><a href="${pageContext.request.contextPath}/productorderserviceproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item active">รับสินค้าเข้า / เพิ่มบริการ</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<span class="ti-import"></span>&nbsp;&nbsp;นำเข้าสินค้า/บริการ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-3">
										<label for="exampleSelect1"><b>ประเภทสินค้าและบริการที่รับเข้าสต๊อก</b></label>
									</div>
									<div class="col-md-9">
										<select name="productCategory" id="productCategory"
											class="form-control">
											<option selected="selected" disabled="disabled" value="">
												-- เลือกประเภทสินค้าและบริการ --</option>
											<c:forEach items="${epcBeans}" var="epcBean" varStatus="i">
												<c:if test="${epcBean.equipmentProductCategoryCode eq '00001' || epcBean.equipmentProductCategoryCode eq '00002' || epcBean.equipmentProductCategoryCode eq '00003'}">
												<option <c:if test="${epcBean.equipmentProductCategoryCode eq '00003'}">selected="selected"</c:if>
												value="${epcBean.equipmentProductCategoryCode}">
													${epcBean.equipmentProductCategoryName}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
									</div>
									<div class="typeEquipmentRadio">
<!-- 									<div class="col-md-2"> -->
<!-- 										<label for="exampleSelect1"><b>สินค้าและบริการที่รับเข้าสต๊อก -->
<!-- 										</b></label> -->

<!-- 									</div> -->
<!-- 									<div class="col-md-4"> -->
<!-- 										<label class="custom-control custom-radio"> <input -->
<!-- 											id="radio2" name="memberType" type="radio" checked="checked" -->
<!-- 											value="new" class="custom-control-input"> <span -->
<!-- 											class="custom-control-indicator"></span> <span -->
<!-- 											class="custom-control-description">สินค้าและบริการใหม่</span> -->
<!-- 										</label> <label class="custom-control custom-radio"> <input -->
<!-- 											id="radio1" name="memberType" type="radio" value="old" -->
<!-- 											class="custom-control-input"> <span -->
<!-- 											class="custom-control-indicator"></span> <span -->
<!-- 											class="custom-control-description">สินค้าและบริการที่มีอยู่ในสต็อก</span> -->
<!-- 										</label> -->
<!-- 									</div> -->
									</div>
								</div>
								<div id="buttonShowModal" style="display: none;">
									<div class="row mt05">
										<div class="col-md-12">
											<button type="button" onclick="openModalSearchEquipment()"
												class="btn btn-info label-left float-xs-right col-xs-12">
												<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
											</button>
										</div>
									</div>
							</div>
						</div>
					</div>

					<!-- modal new equipment -->
					<div class="card mt05" id="block_header_detail_charge">
					<div class="posts-list posts-list-1">
						<div class="pl-item">
						<form id="formProductOrderService">
							<div class="row">
								<div class="col-md-12">
									<fieldset>
										<legend>&nbsp;&nbsp;&nbsp;เพิ่มสินค้า (ค่าแรง/ค่าบริการ)&nbsp;&nbsp;&nbsp;</legend>
										<div class="row">
											<div class="col-md-4">
												<div class="form-group">
												<b>ชื่อบริการ<span class="text-red"> *</span></b><br> 
												<input type="text"
													id="service_productName" name="service_productName" class="form-control">
													<div class="messages"></div>
												</div>
											</div>
											
<!-- 											<div class="col-md-3"> -->
<!-- 												<div class="form-group"> -->
<!-- 												<b>ค่าบริการ<span class="text-red"> *</span></b><br> -->
<!-- 													<input type="number" class="form-control" value="0.00" min="0" -->
<!-- 														id="service_productPrice" name="service_productPrice"> -->
<!-- 													<div class="input-group-addon">บาท</div> -->
<!-- 												<div class="messages"></div> -->
<!-- 												</div> -->
<!-- 											</div> -->
											
											<div class="form-group">
												<div class="col-md-3">
													<b>ค่าบริการ<span class="text-red"> *</span></b><br>
													<div class="input-group">
														<input type="number" class="form-control" value="0.00" min="0"
														id="service_productPrice" name="service_productPrice">
														<div class="input-group-addon">บาท</div>
													<div class="messages"></div>
													</div>
												</div>
											</div>
											
											<div class="col-md-2">
												<b>หน่วยนับ<span class="text-red"> *</span></b><br>
												<div class="form-group">
													<select class="form-control" id="serviceProduct_Unit" name="Product_Unit">
														<option value="" disabled="disabled">--- เลือกหน่วยนับ ---</option>
														<c:forEach items="${unitBeans}" var="unitBean" varStatus="i">
															<option value="${unitBean.id}">${unitBean.unitName}</option>
														</c:forEach>
													</select>
													<div class="messages"></div>
												</div>
											</div>
											<div class="col-md-3">
												<b>คลังสินค้า<span class="text-red"> *</span></b><br>
												<div class="form-group">
													<select class="form-control" id="service_productStock" name="productStock">
														<option value="" disabled="disabled">--- เลือกคลังสินค้า ---</option>
														<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
															<option value="${stockBean.id}">
																${stockBean.stockName} (${stockBean.company.companyName})</option>
														</c:forEach>
													</select>
													<div class="messages"></div>
												</div>												
											</div>
										</div>
									</fieldset>
								</div>
							</div>	
												<div class="row mt15 mb30" align="center">
						<button type="submit" class="btn btn-info label-left mr-0-5">
							<span class="btn-label"><i class="ti-check"></i></span>บันทึก
						</button>
						<button type="button" onclick="reloadAddProduct()"
							class="btn btn-danger label-left  mr-0-5">
							<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
						</button>

					</div>						
							</form>
						</div>
					</div>
				</div>
					<!-- end modal search equipment -->



				</div>
			</div>
		</div>

		<!-- Footer -->
		<jsp:include page="../layout/footer.jsp"></jsp:include>
	</div>


	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
	
	$(document).ready(function() {
		$('#productCategory').change(
				function() {
					var productCategoryValue = $(this).val();
					
					if (productCategoryValue == productCategoryValueTypeInternet) { //check type internet user
						window.location.href = "${pageContext.request.contextPath}/productorderinternetproduct/add";
					} else if (productCategoryValue == productCategoryValueTypeCharge) { //check type charge
						//window.location.href = "${pageContext.request.contextPath}/productorderserviceproduct/add";
					} else { //type equipment
						window.location.href = "${pageContext.request.contextPath}/productadd";
					}

			});
		});
	
		//save product service
		function saveProductTypeServiceCharge(){
			$('.preloader').modal('show');
			var serviceProductBean = {};
			serviceProductBean.productName = $('#service_productName').val();
			serviceProductBean.price = $('#service_productPrice').val();
			//set stock
			var StockBean = {}
			StockBean.id = $('#service_productStock').val();
			serviceProductBean.stock = StockBean;
			
			//unit
			UnitBean = {};
			UnitBean.id = $('#serviceProduct_Unit').val();
			serviceProductBean.unit = UnitBean;
			
			//send save
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/productadd/saveServiceCharge",
				dataType : 'json',
				async : false,
				data : JSON.stringify(serviceProductBean),
				//timeout : 100000,
				success : function(data) {
					if (data["error"] == false) {
						//window.location.reload();
						window.location.href = "${pageContext.request.contextPath}/productorderserviceproduct";
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
		
		function reloadAddProduct(){
			window.location.reload();
		}
	</script>
	<script>
		var constraints = {
				"service_productName" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
				"Product_Unit" : {
				presence : {
					message : "^กรุณาเลือกข้อมูล"
				}
			},
				"productStock" : {
				presence : {
					message : "^กรุณาเลือกข้อมูล"
				}
			}
		};
		
		// Hook up the form so we can prevent it from being posted
		var form = document.querySelector("form#formProductOrderService");
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
				saveProductTypeServiceCharge();
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
