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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>แก้ไขสินค้า</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<link rel="stylesheet" href="<c:url value="/resources/assets/plugins/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css" />">
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
					<div class="row mt05 mb05" >
						<div class="col-md-12">
							<h3>${equipmentProductBean.productName }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/productorderequipmentproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${equipmentProductBean.id}">ข้อมูลสินค้า</a></li>
								<li class="breadcrumb-item active">แก้ไขสินค้า</li>

							</ol>
						</div>
					</div>
					<div class="card mt05" id="block_header_detail_equipment_newitem">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายละเอียดสินค้า

									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-3">
													<input type="hidden" value="${equipmentProductBean.id }" id="new_masterEquipmentId" />
													<b>รหัสสินค้า</b><br> <input type="text"
														id="new_masterEquipmentProductCode" class="form-control" value="${equipmentProductBean.productCode }">
												</div>
												<div class="col-md-3">
													<b>ชื่อสินค้า</b><br> <input type="text"
														id="new_masterEquipmentProductName" class="form-control" value="${equipmentProductBean.productName }">
												</div>
												<div class="col-md-3">
													<b>หมวดหมู่วัสดุอุปกรณ์</b><br>
													<div class="">
														<select class="form-control" id="new_equipmentProductType">
															<c:forEach items="${epcSearchBeans}" var="epcSearchBean" varStatus="i">
																<c:choose>
																<c:when test="${epcSearchBean.id == equipmentProductBean.productCategory.id}">
																	<option value="${epcSearchBean.id}" selected="selected">
																		${epcSearchBean.equipmentProductCategoryName}
																	</option>
																</c:when> 
																<c:otherwise>
																	<option value="${epcSearchBean.id}">
																		${epcSearchBean.equipmentProductCategoryName}
																	</option>
																</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<b>Supplier</b><br> <input type="text"
														id="new_supplier" class="form-control" value="${equipmentProductBean.supplier }">
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<b>คลังสินค้า</b><br>
													<div class="">
														<select class="form-control" id="new_equipmentStock">
															<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
																<c:choose>
																<c:when test="${stockBean.id == equipmentProductBean.stock.id}">
																	<option value="${stockBean.id}" selected="selected">
																		${stockBean.stockName}
																		(${stockBean.company.companyName})
																	</option>
																</c:when>
																<c:otherwise>
																	<option value="${stockBean.id}">
																		${stockBean.stockName}
																		(${stockBean.company.companyName})
																	</option>
																</c:otherwise>
																</c:choose>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<b>หน่วยนับ</b><br>
													<div class="">
														<select class="form-control" id="new_equipmentUnit">
															<c:forEach items="${unitBeans}" var="unitBean" varStatus="i">
															<c:choose>
																<c:when test="${unitBean.id == equipmentProductBean.unit.id}">
																	<option value="${unitBean.id}" selected="selected">${unitBean.unitName}</option>
																</c:when>
																<c:otherwise>
																	<option value="${unitBean.id}">${unitBean.unitName}</option>
																</c:otherwise>
															</c:choose>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<b>หมวดบัญชีสินค้า</b><br>
													<div class="form-group">
														<select class="form-control" id="financial_type" name="financial_type">
															<option value="A" <c:if test="${equipmentProductBean.financialType eq 'A' }"> selected="selected"</c:if> >ทรัพย์สิน</option>
															<option value="C" <c:if test="${equipmentProductBean.financialType eq 'C' }"> selected="selected"</c:if>>ต้นทุน</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<b>จำนวนน้อยสุดในคลังสินค้า</b><br>
													<div class="controls form-inline">
														<label class="custom-control custom-checkbox"> 
														<c:choose>
															<c:when test="${equipmentProductBean.isminimum}">
																<input type="checkbox" id="checkboxIsMinimum" class="custom-control-input" value="" checked="checked"> 
															</c:when>
															<c:otherwise>
																<input type="checkbox" id="checkboxIsMinimum" class="custom-control-input" value=""> 
															</c:otherwise>
														</c:choose>
														<span class="custom-control-indicator"></span> <span
															class="custom-control-description"></span>
														</label> 
														<c:choose>
															<c:when test="${equipmentProductBean.isminimum}">
																<input type="number" id="new_masterEquipmentProductMinumun" value="${equipmentProductBean.minimumNumber }" min="1"
														 class="form-control"> 
															</c:when>
															<c:otherwise>
																<input type="number" id="new_masterEquipmentProductMinumun" value="" min="1" disabled="disabled"
														 class="form-control"> 
															</c:otherwise>
														</c:choose>
													</div>
												</div>												
											</div>
										</fieldset>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05" id="block_master_add_equipment">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ข้อมูลสินค้าในระบบ

									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<div class="row">
											<div class="col-md-12">
												<div class="table-responsive">
												<table class="table table-bordered mb-0 table-hover" id="tableEquipmentProductChild">
													<thead class="thead-bg">
														<tr>
															<th style="width: 20px;"><center>ลำดับ</center></th>
															<th><center>SN</center></th>
															<th><center>อ้างอิง</center></th>
															<th style="width: 80px;"><center>จำนวนรับเข้า</center></th>
															<th><center>สิ้นสุดประกัน</center></th>
															<th><center>วันสั่งซื้อ</center></th>
															<th style="width: 150px;"><center>ราคาต้นทุน</center></th>
															<th style="width: 150px;"><center>ราคารวมภาษี</center></th>
															<th style="width: 150px;"><center>ราคาขาย</center></th> 
														</tr>
													</thead>
													<tbody id="tbodyNewItem">
													<c:forEach items="${equipmentProductBean.equipmentProductItemBeans}" var="productItem" varStatus="i">
														<tr class="trNewItem">
															<td><center>
																	<input type="hidden" id="hiddenItemId" value="${productItem.id }">
																	<span id="spanMasterEquipmentProductOrderCount">${i.count}</span>
																</center></td>
															<td><input type="text"
																id="masterEquipmentProductSerial" value="${productItem.serialNo }"
																name="masterEquipmentProductSerial" class="form-control"></td>
															<td><input type="text" value="${productItem.referenceNo }"
																id="masterEquipmentProductReference"
																name="masterEquipmentProductReference"
																class="form-control"></td>
															<td scope="row"><div>
																	<input type="number" class="form-control" value="${productItem.numberImport }"
																		id="masterEquipmentProductCount" >

																</div></td>
															<td scope="row">
																<div class="input-group">
																	<input type="text"
																		class="datepickerEquimentProduct form-control"
																		id="datepickerGuarantee" placeholder="วัน-เดือน-ปี" value="${productItem.guaranteeDate }">
																	<!--<div class="input-group-addon"><i class="fa fa-calendar-o"></i></div>-->
																</div>
															</td>
															<td scope="row">
																<div class="input-group">
																	<input type="text"
																		class="datepickerEquimentProduct form-control"
																		id="datepickerOrderDate" placeholder="วัน-เดือน-ปี" value="${productItem.orderDate }">
																	<!-- <div class="input-group-addon"><i class="fa fa-calendar-o"></i></div> -->
																</div>
															</td>
															<td scope="row"><div class="input-group">
																	<input type="number" class="form-control productCost"  value="${productItem.cost }"
																		min="0" >
																	<div class="input-group-addon productCost">บาท</div>
																</div></td>
															<td scope="row"><div class="input-group">
																	<input type="number" class="form-control priceIncTax"  value="${productItem.priceIncTax }"
																		min="0" >
																	<div class="input-group-addon priceIncTax">บาท</div>
																</div></td>
															<td scope="row"><div class="input-group">
																	<input type="number" class="form-control productSalePrice"  value="${productItem.salePrice }"
																		min="0" >
																	<div class="input-group-addon productSalePrice">บาท</div>
															</div></td> 
														</tr>
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
					<div class="card mb30">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการการขอเบิกสินค้าเพื่อสำรอง

									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">								
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-1 table_requisitionItem table-hover">
												<thead class="thead-default">
													<tr style="background: #e8e8e8;">														
														<th><center>หมายเลขการเบิก</center></th>
														<th><center>Serial Number</center></th>
														<th width="120px;"><center>ผู้ขอเบิก</center></th>
														<th><center>วันที่เบิก</center></th>
														<th><center>จำนวนที่เบิก</center></th>
														<th><center>จำนวนที่คืน</center></th>
													</tr>
												</thead>
												<tbody>
													<c:set var="count" value="0" scope="page" />
													<c:forEach items="${equipmentProductBean.equipmentProductItemBeans}" var="productItem" varStatus="i">
														<c:forEach items="${productItem.requisitionItemBeans}" var="requisitionItem" varStatus="j">
														<c:if test="${requisitionItem.requisitionDocumentBean.withdraw eq 'R'}">
														<tr>
														    <c:set var="count" value="${count + 1}" scope="page"/>
														    <td class="td-middle">
																<center><a href="/cable/requisitionlist/view/${requisitionItem.requisitionDocumentBean.id}" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${requisitionItem.requisitionDocumentBean.requisitionDocumentCode}</b><br>
																<font class="text-gray">${requisitionItem.requisitionDocumentBean.withdrawText}</font>
																</center>
															</td>												
															<td>
															<c:choose>
																<c:when test="${productItem.serialNo != ''}"><center>${productItem.serialNo}</center></c:when>
																<c:otherwise><center>-</center></c:otherwise>
															</c:choose>
															</td>															
															<td class="td-middle"><font><center>${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.firstName}&nbsp;${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.lastName}<br>(${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.nickName})</center></font></td>
															<td class="td-middle"><center>${requisitionItem.requisitionDocumentBean.createDateTh}</center></td>
															<td class="td-middle"><center><span class="requisitionItem_quantity">${requisitionItem.quantity}</span></center></td>
															<td class="td-middle">
															<center>
															<input type="hidden" class="requisitionItem_id" value="${requisitionItem.id}">
															<input type="text" class="form-control requisitionItem_number" value="${requisitionItem.returnEquipmentProductItem}" style="text-align: center;" >
															</center></td>
														</tr>
														</c:if>
														</c:forEach>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
								
							</div>
						</div>
					</div>
						<div class="row mt15 mb30" align="center">
							<a>
									<button type="button"  onclick="autoUpdateRequisition();" 
										class="btn btn-success label-left mr-0-5">
										<span class="btn-label"><i class="ti-check"></i></span>บันทึก
									</button>
								</a>
							<a href="${pageContext.request.contextPath}/productorderequipmentproduct">
								<button type="button"  
											class="btn btn-danger label-left mr-0-5">
											<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
								</button> 
							</a>							
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
    	
    	<script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
		
		<script type="text/javascript">
		$(document).ready(function() {
			
			$('.productCost').blur(function(){
				var stockId = $('#new_equipmentStock').val();
				var vat = loadVatWithStockId(stockId);
				console.log(vat);
				
				console.log($(this).val()*(vat/100));
				var valuePrice = parseFloat($(this).val())+($(this).val()*(vat/100));
				console.log(valuePrice);
				valuePrice = valuePrice.toFixed(2);
			    $(this).closest('tr').find('.priceIncTax').val(valuePrice);
			    $(this).closest('tr').find('.productSalePrice').val(valuePrice);
			    
// 			    $(this).closest('tr').find('.priceIncTax').val(parseFloat($(this).val())+($(this).val()*(vat/100)));
// 			    $(this).closest('tr').find('.productSalePrice').val(parseFloat($(this).val())+($(this).val()*(vat/100)));
			    
// 			    var x = $(this).closest('tr').find('.productSalePrice').val();
// 			    $(this).closest('tr').find('.productSalePrice').val(formatNumber(x));
			    
// 			    var x = $(this).closest('tr').find('.priceIncTax').val();
// 			    $(this).closest('tr').find('.priceIncTax').val(formatNumber(x));
			    
// 			    $(this).closest('tr').find('.productCost').val(formatNumber($(this).val()));
			    
			});
			
			$('#checkboxIsMinimum').click(function(){
			    if($(this).is(':checked')){
			    	$('#new_masterEquipmentProductMinumun').removeAttr('disabled');
			    } else {
			    	//$('#new_masterEquipmentProductMinumun').val("");
			    	$('#new_masterEquipmentProductMinumun').attr('disabled','disabled');
			    }
			});
			
			//set date picker
			$(".datepickerEquimentProduct").datepicker({
				autoclose: true,
				format: 'dd-mm-yyyy',
				todayHighlight:'TRUE',
				todayBtn: true,
	            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
	            thaiyear: true              //Set เป็นปี พ.ศ.
			});
			
			
// 			$('.productCost').blur(function(){
// 				var stockId = $('#new_equipmentStock').val();
// 				var vat = loadVatWithStockId(stockId);
// 			    $(this).closest('tr').find('.priceIncTax').val(parseFloat($(this).val())+($(this).val()*(vat/100)));
// 			});
			
		});
		
		$(document).on('blur', '.requisitionItem_number', function(event) {
			var quantity = $(this).parent().parent().parent().find('.requisitionItem_quantity');
			var requisitionItem_id = $(this).parent().parent().find('.requisitionItem_id');
			quantity = quantity.text();
			var number = $(this).val();
			requisitionItem_id = requisitionItem_id.val();
// 			console.log(quantity);
// 			console.log(number);
// 			console.log(requisitionItem_id);
// 			console.log(isNaN(number));
// 			console.log(parseInt(number) > parseInt(quantity));
			if(parseInt(number) > parseInt(quantity) || isNaN(number)){
				$(this).val(quantity);
			}else{
				if(number==""){
					$(this).val("0");
				}
			}
		});
		
		function autoUpdateRequisition(){
			var requisitionDocumentBean = {};
			
			//requisitionItemBean
			var requisitionItemBean = [];
			$('.table_requisitionItem > tbody  > tr').each(function() {
				var item = {};
				 $(this).find('input').each(function (i) {
					switch (i) {
						case 0:
							item.id = $(this).val();
						break;
						case 1:
							item.returnEquipmentProductItem = $(this).val();
						break;
					}	
				});
				requisitionItemBean.push(item);
			}); 
			requisitionDocumentBean.requisitionItemList = requisitionItemBean;
			console.log(requisitionDocumentBean);
			$('.preloader').modal('show');
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/productorderequipmentproduct/updaterequisition/",
				dataType : 'json',
				data : JSON.stringify(requisitionDocumentBean),
				//timeout : 100000,
				success : function(data) {
					 updateProduct();
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}
		
		function updateProduct(){
			//create bean
			//equipment bean
			var equipmentProductBean = {};
			equipmentProductBean.id = $('#new_masterEquipmentId').val();
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
									item.id = $(this).val();
									break;
								case 1:
									item.serialNo = $(this).val();
									break;
								case 2:
									item.referenceNo = $(this).val();
									break;
								case 3:
									item.numberImport = $(this).val();
									break;
								case 4:
									item.guaranteeDate = $(this).val();
									break;
								case 5:
									item.orderDate = $(this).val();
									break;
								case 6:
									item.cost = $(this).val();
									break;
								case 7:
									item.priceIncTax = $(this).val();
									break;
								case 8:
									item.salePrice = $(this).val();
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
							url : "${pageContext.request.contextPath}/productorderequipmentproduct/updateEquipmentProduct",
							dataType : 'json',
							async : false,
							data : JSON.stringify(equipmentProductBean),
							//timeout : 100000,
							success : function(data) {
								if (data["error"] == false) {
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
		
		function loadVatWithStockId(id){
			var vat = 0.0;
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/productadd/loadVatWithStockId/"+id,
				dataType : 'json',
				async: false,
				//timeout : 100000,
				success : function(data) {
					if(data["error"] == false){
					    vat = data["result"];
						//return vat;
					}else{
						vat = 0.0;
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
			return vat;
		}
		
		function formatNumber (num) {
			console.log("formatNumber : "+num);
			return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
		}
		
		function addCommas(x) {
		    var parts = x.toString().split(".");
		    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		    return parts.join(".");
		}
		
		function digits (number) { 
		    return number.toString().replace(/(\\d)(?=(\\d\\d\\d)+(?!\\d))/g, "$1,"); 
		}
		
		</script>
		
</body>
</html>
