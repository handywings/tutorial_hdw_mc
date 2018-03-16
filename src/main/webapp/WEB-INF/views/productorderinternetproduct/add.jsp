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
								<li class="breadcrumb-item active"><a href="${pageContext.request.contextPath}/productorderinternetproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item active">รับสินค้าเข้า
									/เพิ่มบริการ</li>
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
											<option disabled="disabled" value="">--
												เลือกประเภทสินค้าและบริการ --</option>
											<c:forEach items="${epcBeans}" var="epcBean" varStatus="i">
												<c:if
													test="${epcBean.equipmentProductCategoryCode eq '00001' || epcBean.equipmentProductCategoryCode eq '00002' || epcBean.equipmentProductCategoryCode eq '00003'}">
													<option value="${epcBean.equipmentProductCategoryCode}"
														<c:if test="${epcBean.equipmentProductCategoryCode eq '00002'}">selected="selected"</c:if>>
														${epcBean.equipmentProductCategoryName}</option>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row mt05">
									<div class="typeEquipmentRadio">
										<div class="col-md-3">
											<label for="exampleSelect1"><b>สินค้าและบริการที่รับเข้าสต๊อก
											</b></label>

										</div>
										<div class="col-md-7">
											<label class="custom-control custom-radio"> <input
												id="radio2" name="memberType" type="radio" checked="checked"
												value="new" class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">&nbsp;สินค้าและบริการใหม่</span>
											</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
												id="radio1" name="memberType" type="radio" value="old"
												class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">&nbsp;สินค้าและบริการที่มีอยู่ในสต็อก</span>
											</label>
										</div>
									</div>
									<div id="buttonShowModal" style="display: none;">
										<div class="col-md-2">
											<button type="button" onclick="openModalSearchInternet()"
												class="btn btn-info label-left float-xs-right col-xs-12">
												<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
											</button>
										</div>
									</div>
								</div>
								</div>
							</div>
						</div>
						<!-- modal new internet user -->
						<jsp:include page="block_header_detail_internet.jsp"></jsp:include>
						<!-- End modal new internet user -->

						<!-- modal new internet user -->
						<jsp:include page="block_header_detail_internet_old.jsp"></jsp:include>
						<!-- End modal new internet user -->

						<!-- modal new internet user -->
						<jsp:include page="block_master_add_internet.jsp"></jsp:include>
						<!-- End modal new internet user -->

						<!-- modal new internet user -->
						<jsp:include page="block_child_list_internet.jsp"></jsp:include>
						<!-- End modal new internet user -->

						<!-- modal search internet user -->
						<jsp:include page="modal_search_internet.jsp"></jsp:include>
						<!-- End modal search internet user -->

						<div class="row mb30 mt15" align="center">
							<a id="aBtn" style="display:none">
								<button type="button" onclick="saveInternetProduct()" 
									class="btn btn-success label-left mr-0-5">
									<span class="btn-label"><i class="ti-check"></i></span>บันทึก
								</button>
							</a>
							<a id="aBtnSubmit">
								<button type="submit" class="btn btn-info label-left mr-0-5">
									<span class="btn-label"><i class="ti-check"></i></span>บันทึก
								</button>
							</a>
							<button type="button" onclick="reloadAddProduct()"
								class="btn btn-danger label-left mr-0-5">
								<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
							</button>
						</div>
					</div>
					</form>
				</div>
			</div>


			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

<!-- 	</div> -->

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
	$(document).ready(function() {
		$('#block_header_detail_internet_old').hide();
		$('#productCategory').change(
				function() {
					var productCategoryValue = $(this).val();
					
					if (productCategoryValue == productCategoryValueTypeInternet) { //check type internet user
						 
					} else if (productCategoryValue == productCategoryValueTypeCharge) { //check type charge
						window.location.href = "${pageContext.request.contextPath}/productorderserviceproduct/add";
					} else { //type equipment
						window.location.href = "${pageContext.request.contextPath}/productadd";
					}

				});
		
		$("input[name='memberType']").change(function() {
			//show
			var radioEquipmentType = $("input[name='memberType']:checked").val();
			if (radioEquipmentType == 'old') {
				//button show modal
				$('#block_header_detail_internet').hide();
				$('#block_header_detail_internet_old').show();
				$('#buttonShowModal').show();
				
				$('#aBtn').hide();
				$('#aBtnSubmit').hide();
				
			} else {
				window.location.reload();
			}
		});
		
		
		//added 16-05-2017 blind textbox username for check dup
		$( ".username-textbox" ).keyup(function() {
			if(checkDuplicateUsername($(this).val())){
				$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
			}else{
				$(this).next('.messages').html("");
			}
		});
		
		});
	
	function reloadAddProduct(){
		window.location.reload();
	}
		
	
	function saveInternetProduct(){
		var radioEquipmentType = $("input[name='memberType']:checked").val();
		if (radioEquipmentType == 'old') {
			saveProductTypeInternet(); //call save old product
		} else {
			saveNewProductTypeInternet() //call save new product
		}		
	}
	
	function saveNewProductTypeInternet(){
		var countDup = 0;
		var countEmptyRef = 0;
		var countDupUi = 0;
		//internet product
		var internetProductBean = {};
		internetProductBean.productName = $('#internet_packageName').val();
		internetProductBean.productCode = $('#internet_code').val();
		//stock
		StockBean = {};
		StockBean.id = $('#internet_equipmentStock').val();
		internetProductBean.stock = StockBean;
		
		//iternet item
		var internetProductBeanItems = [];
		$('#tableInternetProductChild > tbody  > tr.trNewItem').each(function() {
			var item = {};
			var tempCountDupUi = 0;
			 $(this).find('input').each(function (i) {
				 //alert($(this).val());
					switch (i) {
							case 0:
								item.reference = $(this).val();
								if(isEmpty($(this).val())){
									countEmptyRef = countEmptyRef+1;
									$(this).next('.messagesRef').html("<p class='help-block error'>กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง</p>");
								}
								break;
							case 1:
								item.userName = $(this).val();
								$("#tableInternetProductChild .username-textbox").each(function(){
								     if($(this).val() == item.userName){
								    	 tempCountDupUi = tempCountDupUi+1;
								     }         
								});
								
								if(tempCountDupUi > 1){
									$("#tableInternetProductChild .username-textbox").each(function(){
									     if($(this).val() == item.userName){
									    	 $(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในหน้าจอ กรุณาเปลี่ยนใหม่</p>");
									     }         
									});
								}
								
								if(checkDuplicateUsername($(this).val())){
									countDup = countDup+1;
									$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
								}
								break;
							case 2:
								item.password = $(this).val();
								break; 
							}
					
						}); //end find textbox
			 		//add to arrayjson
			 			internetProductBeanItems.push(item);
			 			if(tempCountDupUi > 1){
			 				countDupUi = countDupUi+1;
			 			}
			 			
					}); //end find tr
					//add to EquipmentProductBean
					internetProductBean.internetProductBeanItems = internetProductBeanItems;

					if(countDup == 0 && countEmptyRef == 0 && countDupUi == 0){
						$('.preloader').modal('show');
					//send new save
					$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/productorderinternetproduct/saveInternetProductNew",
						dataType : 'json',
						async : false,
						data : JSON.stringify(internetProductBean),
						//timeout : 100000,
						success : function(data) {
							if (data["error"] == false) {
								//window.location.reload();
								window.location.href = "${pageContext.request.contextPath}/productorderinternetproduct";
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
	}
	
	function saveProductTypeInternet(){
		var countDup = 0;
		var countEmptyRef = 0;
		var countDupUi = 0;
		//internet product
		var internetProductBean = {};
		internetProductBean.id = $('#hiddenProductInternetId').val();
		
		//iternet item
		var internetProductBeanItems = [];
		$('#tableInternetProductChild > tbody  > tr.trNewItem').each(function() {
			var item = {};
			var tempCountDupUi = 0;
			 $(this).find('input').each(function (i) {
				 //alert($(this).val());
					switch (i) {
							case 0:
								item.reference = $(this).val();
								if(isEmpty($(this).val())){
									countEmptyRef = countEmptyRef+1;
									$(this).next('.messagesRef').html("<p class='help-block error'>กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง</p>");
								}
								break;
							case 1:
								item.userName = $(this).val();
								$("#tableInternetProductChild .username-textbox").each(function(){
								     if($(this).val() == item.userName){
								    	 tempCountDupUi = tempCountDupUi+1;
								     }         
								});
								
								if(tempCountDupUi > 1){
									$("#tableInternetProductChild .username-textbox").each(function(){
									     if($(this).val() == item.userName){
									    	 $(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในหน้าจอ กรุณาเปลี่ยนใหม่</p>");
									     }         
									});
								}
								
								if(checkDuplicateUsername($(this).val())){
									countDup = countDup+1;
									$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
								}
								break;
							case 2:
								item.password = $(this).val();
								break; 
							}
					
						}); //end find textbox
			 		//add to arrayjson
			 			internetProductBeanItems.push(item);
			 			if(tempCountDupUi > 1){
			 				countDupUi = countDupUi+1;
			 			}
					}); //end find tr
					//add to EquipmentProductBean
					internetProductBean.internetProductBeanItems = internetProductBeanItems;
					
					if(countDup == 0 && countEmptyRef == 0 && countDupUi == 0){
						$('.preloader').modal('show');
					
					//send new save
					$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/productorderinternetproduct/saveInternetProductOld",
						dataType : 'json',
						async : false,
						data : JSON.stringify(internetProductBean),
						//timeout : 100000,
						success : function(data) {
							if (data["error"] == false) {
								//window.location.reload();
								window.location.href = "${pageContext.request.contextPath}/productorderinternetproduct";
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
				}
	
	
	
	function checkDuplicateUsername(username){
		//send check dup
		var checkDup = false;
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/checkDuplicateUsername",
			//dataType : 'json',
			async : false,
			data : JSON.stringify(username),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					if(data["result"] == true){
						checkDup = true;
					}else{
						checkDup = false;
					}
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
		
		return checkDup;
	}
	</script>
	
	<script>
		var constraints = {
				"internet_packageName" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
				"internet_code" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
				"internet_equipmentStock" : {
				presence : {
					message : "^กรุณาเลือกข้อมูล"
				}
			}
		};
		
		// Hook up the form so we can prevent it from being posted
		var form = document.querySelector("form#formProductOrderInternet");
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
				saveInternetProduct();
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
