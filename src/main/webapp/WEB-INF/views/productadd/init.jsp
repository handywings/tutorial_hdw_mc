<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รับสินค้าเข้า / เพิ่มบริการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>

<link rel="stylesheet" href="<c:url value="/resources/assets/plugins/typeahead/dist/typeahead-min.css" />">

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productadd" scope="request"/>
	
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
							<h3>รับสินค้าเข้า / เพิ่มบริการ </h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active"><a href="${pageContext.request.contextPath}/productorderequipmentproduct">รายการสินค้าและบริการ</a></li>
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
													<option value="${epcBean.equipmentProductCategoryCode}"  <c:if test="${epcBean.equipmentProductCategoryCode== '00001'}">selected="selected" </c:if>>
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
										<label class="custom-control custom-radio mr-0-5"> <input
											id="radio2" name="memberType" type="radio" checked="checked"
											value="new" class="custom-control-input"> <span
											class="custom-control-indicator"></span> <span
											class="custom-control-description">&nbsp;สินค้าและบริการใหม่</span>
										</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio mr-0-5"> <input
											id="radio1" name="memberType" type="radio" value="old"
											class="custom-control-input"> <span
											class="custom-control-indicator"></span> <span
											class="custom-control-description">&nbsp;สินค้าและบริการที่มีอยู่ในสต็อก</span>
										</label>
									</div>
									
									<div id="buttonShowModal" style="display: none;">
										<div class="col-md-2">
											<button type="button" onclick="openModalSearchEquipment()"
												class="btn btn-info label-left float-xs-right col-xs-12">
												<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
											</button>
										</div>
									
									</div>
								</div>
								</div>
							</div>
						</div>
					</div>
					<!-- modal new equipment -->
					<jsp:include page="block_header_detail_equipment_newitem.jsp"></jsp:include>
					<!-- end modal search equipment -->
					<!-- modal old equipment -->
					<jsp:include page="block_header_detail_equipment_olditem.jsp"></jsp:include>
					<!-- end modal search equipment -->

					<!-- modal new equipment -->
					<jsp:include page="block_header_detail_charge.jsp"></jsp:include>
					<!-- end modal search equipment -->

					<!-- modal search equipment -->
					<jsp:include page="block_master_add_equipment.jsp"></jsp:include>
					<!-- end modal search equipment -->

					<!-- modal search equipment -->
					<jsp:include page="block_child_list_equipment.jsp"></jsp:include>
					<!-- end modal search equipment -->
					
					<div class="row mt15 mb30" align="center">
					<a>
							<button type="submit" 
								class="btn btn-success label-left mr-0-5">
								<span class="btn-label"><i class="ti-check"></i></span>บันทึก
							</button>
						</a> <button type="button" onclick="reloadAddProduct()"
								class="btn btn-danger label-left mr-0-5">
								<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
					</button>
					</div>
					</form>
				</div>
			</div>
		</div>
		<!-- modal search equipment -->
		<jsp:include page="modal_search_equipment.jsp"></jsp:include>
		<!-- end modal search equipment -->

		<!-- Footer -->
		<jsp:include page="../layout/footer.jsp"></jsp:include>	

	</div>
	
	<jsp:include page="../layout/script.jsp"></jsp:include>
	
	
<%-- 	<script src="<c:url value="/resources/assets/plugins/datepicker-th/js/bootstrap-datepicker-custom.js" />"></script> --%>
    <script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
    <script src="<c:url value="/resources/assets/plugins/typeahead/dist/typeahead.jquery.min.js" />" charset="UTF-8"></script>

    <script>
        $(document).ready(function () {
            $('.datepicker').datepicker({
                format: 'dd/mm/yyyy',
                todayBtn: true,
                language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
                thaiyear: true              //Set เป็นปี พ.ศ.
            }).datepicker("setDate", "0");  //กำหนดเป็นวันปัจุบัน
        });
    </script>
	
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							initLoad();

							//---------------------------Begin controll page ---------------------------//
							$("input[name='memberType']").change(function() {
								//show
								var radioEquipmentType = $("input[name='memberType']:checked").val();
								if (radioEquipmentType == 'old') {
									$('#block_header_detail_equipment_olditem').show();
									$('#block_header_detail_equipment_newitem').hide();
									//button show modal
									$('#buttonShowModal').show();
								} else {
									window.location.reload();
								}
								
							});
							 
							$('#productCategory')
									.change(
											function() {
												var productCategoryValue = $(
														this).val();
												if (productCategoryValue == productCategoryValueTypeInternet) { //check type internet user
													pageTypeInternet();
												} else if (productCategoryValue == productCategoryValueTypeCharge) { //check type charge
													pageTypeCharge();
												} else { //type equipment
													pageTypeEquipment();
												}

											});
							//---------------------------End controll page ---------------------------//

							$("#masterEquipmentProductSerial").keypress(function(e) {
							    if(e.which == 13) {
							    	e.preventDefault();
							    	appendBlockMasterToChild();
							    }
							});
							
						});

		function initLoad() {
			//set disabled
			$('#new_masterEquipmentProductMinumun').attr('disabled','disabled');
			
			var pageType = '${pageType}';
			console.log("pageType : " + pageType);
			switch (pageType) {
			case 'E':
				pageTypeEquipment();
				break;
			case 'I':
				pageTypeInternet();
				break;
			case 'C':
				pageTypeCharge();
				break;
			}
		}
		//function switch page type
		function pageTypeEquipment() {
			//hide
			$('#block_header_detail_charge').hide();
			$('#block_header_detail_equipment_olditem').hide();
			$('#block_header_detail_internet').hide();
			$('#block_header_detail_internet_old').hide();
			$('.typeInternetRadio').hide();
			$('#buttonShowModalInternet').hide();
			$('#block_master_add_internet').hide();
			$('#block_child_list_internet').hide();
			//show
			var radioEquipmentType = $("input[name='memberType']:checked").val();
			if (radioEquipmentType == 'old') {
				$('#block_header_detail_equipment_olditem').show();
				$('#block_header_detail_equipment_newitem').hide();
				//button show modal
				$('#buttonShowModal').show();
			} else {
				$('#block_header_detail_equipment_olditem').hide();
				$('#block_header_detail_equipment_newitem').show();
				//button show modal
				$('#buttonShowModal').hide();
				//clear table
				//$('#tableEquipmentProductChild > tbody').html("");
			}

			$('#block_master_add_equipment').show();
			$('#block_child_list_equipment').show();
			$('.typeEquipmentRadio').show();
			$('.trOldItem').show();
			$('.trNewItem').show();

		}

		function pageTypeInternet() {
			window.location.href = "${pageContext.request.contextPath}/productorderinternetproduct/add";
		}
		function pageTypeCharge() {
			window.location.href = "${pageContext.request.contextPath}/productorderserviceproduct/add";
		}
		
		///////////////////save equipment ///////////////////////////////////
		function saveEquipmentProduct(){
			var saveType = $('#productCategory').val();
			
				if (saveType == productCategoryValueTypeInternet) { //check type internet user
					saveProductTypeInternetUser();
				} else if (saveType == productCategoryValueTypeCharge) { //check type charge
					saveProductTypeServiceCharge();
				} else { //type equipment
					var radioEquipmentType = $("input[name='memberType']:checked").val();
					if (radioEquipmentType == 'old') {
						saveProductTypeEquiment(); //call save old product
					} else {
						saveNewProductTypeEquiment() //call save new product
					}
				}//end type equipment			
		}
		
		function reloadAddProduct(){
			window.location.reload();
		}
		
		//save equipment product old
		function saveProductTypeEquiment(){
			//create data json for save
			var idEquipmentProduct = $('#hiddenProductEquipmentId').val();
			if(idEquipmentProduct != null && idEquipmentProduct != ""){
			$('.preloader').modal('show');
			
			var equipmentProductBean = {};
			equipmentProductBean.id = idEquipmentProduct;
			//product item
			var productItem = [];
			$('#tableEquipmentProductChild > tbody  > tr.trNewItem').each(function() {
				var item = {};
				 $(this).find('input').each(function (i) {
						switch (i) {
								case 0:
									item.serialNo = $(this).val();
									break;
								case 1:
									item.referenceNo = $(this).val();
									break;
								case 2:
									item.numberImport = $(this).val();
									break;
								case 3:
									item.guaranteeDate = $(this).val();
									break;
								case 4:
									item.orderDate = $(this).val();
									break;
								case 5:
								    var str = $(this).val();
								    var cost = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.cost = cost;
									break;
								case 6:
								    var str = $(this).val();
								    var priceIncTax = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.priceIncTax = priceIncTax;
									break;
								case 7:
									var str = $(this).val();
								    var salePrice = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.salePrice = salePrice;
									break;
								}
						
							}); //end find textbox
				 		//add to arrayjson
				 			productItem.push(item);
						}); //end find tr
						//add to EquipmentProductBean
						equipmentProductBean.equipmentProductItemBeans = productItem;
						
						//send save
						$.ajax({
							type : "POST",
							contentType : "application/json; charset=utf-8",
							url : "${pageContext.request.contextPath}/productadd/saveEquipmentProductOld",
							dataType : 'json',
							async : false,
							data : JSON.stringify(equipmentProductBean),
							//timeout : 100000,
							success : function(data) {
								if (data["error"] == false) {
									//window.location.reload();
									window.location.href = "${pageContext.request.contextPath}/productorderequipmentproduct";
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
		} //End save equipment product old
		
	
		//save equipment type new
		function saveNewProductTypeEquiment() {
			$('.preloader').modal('show');
			//create bean
			//equipment bean
			var equipmentProductBean = {};
			equipmentProductBean.productName = $('#new_masterEquipmentProductName').val();
			equipmentProductBean.productCode = $('#new_masterEquipmentProductCode').val();
			equipmentProductBean.supplier = $('#new_supplier').val();
			//check is minimun
			if($('#checkboxIsMinimum').is(":checked")){
				equipmentProductBean.isminimum = true;
				equipmentProductBean.minimumNumber = $('#new_masterEquipmentProductMinumun').val();
			}else{
				equipmentProductBean.isminimum = false;
			}
			
			//product category
			EquipmentProductCategoryBean = {};
			EquipmentProductCategoryBean.id = $('#new_equipmentProductType').val();
			equipmentProductBean.productCategory = EquipmentProductCategoryBean;
			//stock
			StockBean = {};
			StockBean.id = $('#new_equipmentStock').val();
			equipmentProductBean.stock = StockBean;
			//unit
			UnitBean = {};
			UnitBean.id = $('#new_equipmentUnit').val();
			equipmentProductBean.unit = UnitBean;
			
			//financial type
			equipmentProductBean.financialType = $('#financial_type').val();
			
			//product item
			var productItem = [];
			$('#tableEquipmentProductChild > tbody  > tr.trNewItem').each(function() {
				var item = {};
				 $(this).find('input').each(function (i) {
						switch (i) {
								case 0:
									item.serialNo = $(this).val();
									break;
								case 1:
									item.referenceNo = $(this).val();
									break;
								case 2:
									item.numberImport = $(this).val();
									break;
								case 3:
									item.guaranteeDate = $(this).val();
									break;
								case 4:
									item.orderDate = $(this).val();
									break;
								case 5:
								    var str = $(this).val();
								    var cost = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.cost = cost;
								case 6:
									var str = $(this).val();
								    var priceIncTax = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.priceIncTax = priceIncTax;
									break;
								case 7:
									var str = $(this).val();
								    var salePrice = parseFloat(str.replace(/\s/g, "").replace(",", ""));
									item.salePrice = salePrice;
									break;
								}
						
							}); //end find textbox
				 		//add to arrayjson
				 			productItem.push(item);
						}); //end find tr
						//add to EquipmentProductBean
						equipmentProductBean.equipmentProductItemBeans = productItem;
						
					//send new save
						$.ajax({
							type : "POST",
							contentType : "application/json; charset=utf-8",
							url : "${pageContext.request.contextPath}/productadd/saveEquipmentProductNew",
							dataType : 'json',
							async : false,
							data : JSON.stringify(equipmentProductBean),
							//timeout : 100000,
							success : function(data) {
								if (data["error"] == false) {
									//window.location.reload();
									window.location.href = "${pageContext.request.contextPath}/productorderequipmentproduct";
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
// 				"new_masterEquipmentProductCode" : {
// 				presence : {
// 					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
// 				}
// 			},
				"new_masterEquipmentProductName" : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
				"new_equipmentProductType" : {
				presence : {
					message : "^กรุณาเลือกข้อมูล"
				}
			},
				"new_equipmentStock" : {
					presence : {
						message : "^กรุณาเลือกข้อมูล"
				}
			},
				"new_equipmentUnit" : {
					presence : {
						message : "^กรุณาเลือกข้อมูล"
				}
			},
				"financial_type" : {
					presence : {
						message : "^กรุณาเลือกข้อมูล"
				}
		}
			
		};
		
		// Hook up the form so we can prevent it from being posted
		var form = document.querySelector("form#formProductOrderEquipment");
		form.addEventListener("submit", function(ev) {
				ev.preventDefault();
				handleFormSubmit(form);
			
		});
		
		function handleFormSubmit(form, input) {
			// validate the form aainst the constraints
			var errors = validate(form, constraints);
			// then we update the form to reflect the results
			showErrors(form, errors || {});
			var radioEquipmentType = $("input[name='memberType']:checked").val();
			if (radioEquipmentType == 'new') {
				if (!errors) {
					saveEquipmentProduct();
				}
			}else if(radioEquipmentType == 'old'){
				saveEquipmentProduct();
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
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			/* =================================================================
				Basic
			================================================================= */
		 	
			var substringMatcher = function(strs) {				
			  return function findMatches(q, cb) {
			    var matches, substringRegex;
	
			    // an array that will be populated with substring matches
			    matches = [];
	
			    // regex used to determine if a string contains the substring `q`
			    substrRegex = new RegExp(q, 'i');
	
			    // iterate through the pool of strings and for any string that
			    // contains the substring `q`, add it to the `matches` array
			    $.each(strs, function(i, str) {
			      if (substrRegex.test(str)) {
			        matches.push(str);
			      }
			    });
	
			    cb(matches);
			  };
			};
	
			var states = [
				<c:forEach var="supplier" items="${supplierList }">'${supplier }',</c:forEach>
			];
	
			$('#the-basics .typeahead').typeahead({
			  hint: true,
			  highlight: true,
			  minLength: 1
			},
			{
			  name: 'states',
			  source: substringMatcher(states)
			});	
		});
	</script>
</body>
</html>
