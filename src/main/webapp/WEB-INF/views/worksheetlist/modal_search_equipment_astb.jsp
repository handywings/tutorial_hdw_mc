<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="astb_addEquipmentProduct" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left"
	style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาสินค้า
				</h4>
			</div>
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row">
					<div class="col-md-4">
						<div class="">
							<label for="exampleInputEmail1"><b>รหัสสินค้า
									ชื่อสินค้า</b></label> <input type="text" class="form-control"
								id="astb_keySearchEquipmentProduct" placeholder="ระบุรหัส หรือชื่อสินค้า...">
						</div>
					</div>
					<div class="col-md-3">
						<div class="">
							<label for="exampleInputEmail1"><b>คลังสินค้า</b></label> <select
								name="astb_productCategory" class="form-control" id="astb_stock">
								<option value="">ทั้งหมด</option>
								<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
									<option value="${stockBean.id}">
										${stockBean.stockName} (${stockBean.company.companyName})</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-3">
						<div class="">
							<label for="exampleInputEmail1"><b>ประเภทสินค้า</b></label> <select
								name="productCategory" class="form-control"
								id="astb_equipmentProductType">
								<option value="">ทั้งหมด</option>
								<c:forEach items="${epcSearchBeans}" var="epcSearchBean"
									varStatus="i">
									<c:if test="${epcSearchBean.id != 1 }">
										<option value="${epcSearchBean.id}">${epcSearchBean.equipmentProductCategoryName}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2 mt05">
						<br>
						<button type="button" class="btn btn-primary btn-block"
							onclick="astb_searchEquipmentProduct();">ค้นหา</button>
					</div>
				</div>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">

							<div class="row mb05">
								<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="astb_table-search-equipment">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า
																/ Serial Number</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;"><center>จำนวนสินค้าในคลัง</center></th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${epbSearchs}" var="epbSearch"
														varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-radio"> <input
																		name="astb_radioEquipmentProduct" type="radio"
																		value="${epbSearch.id }"
																		class="custom-control-input"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description">&nbsp;</span>
																	</label>
																</center></td>
															<td><center>${i.count}</center></td>
															<td><center>${epbSearch.productCode }</center></td>
															<td>${epbSearch.productName }</td>
															<td><center>
																	${epbSearch.equipmentProductItemBeans.size() }
																	${epbSearch.unit.unitName }</center></td>
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
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="astb_appendToBlockChild();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="astb_addEquipmentProductItem" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left"
	style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<i class="zmdi zmdi-search"></i>&nbsp;เลือกสินค้ารายชิ้น
				</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">

							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="astb_tableEquipmentProductChild">
												<thead class="thead-bg">
													<tr>
														<th style="vertical-align: middle; width: 20px;">
														<center>
														<label class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input astb_checkAll" >
														<span class="custom-control-indicator"></span>
														<span class="custom-control-description"></span>
														</label>
														</center>
														</th>
														<th style="vertical-align: middle;"><center>Serial Number</center></th>
														<th style="vertical-align: middle;"><center>จำนวนรับเข้า</center></th>
														<th style="vertical-align: middle;"><center>เอกสารอ้างอิง</center></th>
													</tr>
												</thead>
												<tbody id="astb_tbody-child-item">
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
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left" onclick="astb_backToEquipmentProduct();">กลับ</button>
				<button type="button" class="btn btn-success" onclick="astb_appendToListProduct();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var astb_dataEquipmentProduct = '';
var astb_productItemId_array = [];
var astb_productItemId_main_array = [];

	$( document ).ready(function() {
		$('#astb_table-search-equipment').dataTable({searching: false, paging: true});
	});
	
	$(".astb_checkAll").click(function () {
		var checkBox = $(".astb_checkBoxItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});
	
	function astb_backToEquipmentProduct() {
		$('#astb_addEquipmentProductItem').modal('hide');
		$('#astb_addEquipmentProduct').modal('show');
	}
	
	function astb_openModalSearchEquipment() {
		$('#astb_addEquipmentProduct').modal('show');
	}
	
	function astb_searchEquipmentProduct(){
		astb_searchProcess();
	}
	
	function astb_resetSearchEquipmentProduct(){
		$('#astb_keySearchEquipmentProduct').val("");
		$("#astb_stock").prop("selectedIndex", 0);
		$("#astb_equipmentProductType").prop("selectedIndex", 0);
		astb_searchProcess();
	}
	
	function astb_searchProcess(){
		$('.preloader').modal('show');
		
		var equipmentProductBean = {};
		equipmentProductBean.productName = $('#astb_keySearchEquipmentProduct').val();
		//EquipmentProductCategoryBean
		var EquipmentProductCategoryBean = {};
		EquipmentProductCategoryBean.id = $("#astb_equipmentProductType").val();
		equipmentProductBean.productCategory = EquipmentProductCategoryBean;
		//Stock
		var StockBean = {};
		StockBean.id = $("#astb_stock").val();
		equipmentProductBean.stock = StockBean;
		$('#astb_table-search-equipment').DataTable().destroy();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productadd/loadEquipmentProductNotSn",
			dataType : 'json',
			async: false,
			data : JSON.stringify(equipmentProductBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					//create data table search
					for(var i=0;i<data["result"].length;i++){
							var startRow = "<tr>";
							var endRow = "</tr>";
							
							var columnRadio = "<td><center>" +
								"<label class='custom-control custom-radio'>" + 
								"<input name='astb_radioEquipmentProduct' type='radio'"+
								" value='"+ data["result"][i]["id"] +"'" +
								"class='custom-control-input'>" +
								"<span class='custom-control-indicator'></span>" +
								"<span class='custom-control-description'>&nbsp;</span>" +
								"</label></center></td>";
								
							var columnOrderCount = "<td><center>"+(i+1)+"</center></td>";
							
							var columnProductCode = "<td><center>"+ data["result"][i]["productCode"] +"</center></td>";
							
							var columnProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
							
							var columnProductItemCount = "<td><center>" + data["result"][i]["usable"] 
							+" "+data["result"][i]["unit"].unitName + "</center></td>";
							
							rows = rows + startRow+columnRadio+columnOrderCount+columnProductCode;
							rows = rows + columnProductName+columnProductItemCount+endRow;
					}
					
					//var emptyData = "";
					if(rows.length > 0){
						$('#astb_table-search-equipment > tbody').html(rows);
					}else{
						$('#astb_table-search-equipment > tbody').empty();
					}
					
					setTimeout(
						function() 
							  {
							$('.preloader').modal('hide');
							$('#astb_table-search-equipment').DataTable().draw();
							$('#astb_addEquipmentProduct').css('overflow-y', 'auto');
							  }, 200);
					
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
	
	function astb_appendToBlockChild(){
		var radioEquipmentProduct = $("input:radio[name ='astb_radioEquipmentProduct']:checked").val();
		if(typeof radioEquipmentProduct != 'undefined'){
			astb_loadEuipmentProductItem(radioEquipmentProduct);
			$('#astb_addEquipmentProduct').modal('hide');
			$('#astb_addEquipmentProductItem').modal('show');
			$(".astb_checkAll").prop('checked', false);
			astb_productItemId_array = []
		}
	}
	
	function astb_loadEuipmentProductItem(id){
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/requisition/loadEquipmentProductWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				astb_dataEquipmentProduct = data;
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
						if(jQuery.inArray(data["result"]["equipmentProductItemBeans"][i].id, astb_productItemId_main_array) < 0){
						var startRow = "<tr>";
						var endRow = "</tr>";
						var columnCheckBox = '<td><center><label class="custom-control custom-checkbox">\
						<input type="checkbox" class="custom-control-input astb_checkBoxItem" value="'+data["result"]["equipmentProductItemBeans"][i].id+'" >\
						<span class="custom-control-indicator"></span>\
						<span class="custom-control-description"></span>\
						</label></center></td>';
						
						var columnProductCode = "<td>"+ data["result"].productCode +"</td>";
						var columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
						var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
						var columnProductNumberImport = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].numberImport +"</center></td>";
						
						rows = rows + startRow + columnCheckBox;
						rows = rows + columnProductSerial + columnProductNumberImport + columnProductRefDoc;
						}
					}
					
					if(""==rows){
						$(".astb_checkAll").attr("disabled", true);
					}else{
						$(".astb_checkAll").removeAttr("disabled");
					}
					
					$('#astb_tableEquipmentProductChild > tbody').html(rows);
					
					setTimeout(
						function(){
						$('.preloader').modal('hide');
						}, 200);
					
				}else{
					dataEquipmentProduct = '';
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
	
	function astb_appendToListProduct(){
		
		$('.astb_checkBoxItem').each(function (i) {
			if($(this).is(":checked")){
				astb_productItemId_array.push($(this).val());
			}
		});

		if('' != astb_dataEquipmentProduct){
			var data = astb_dataEquipmentProduct;
			var rows = "";
			var no = 1;

			for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
				
				if(jQuery.inArray( ""+data["result"]["equipmentProductItemBeans"][i].id, astb_productItemId_array) >= 0){
					astb_productItemId_main_array.push(data["result"]["equipmentProductItemBeans"][i].id);
				var startRow = "<tr class='astb_trNewItem'>";
				var endRow = "</tr>";
				
				var columnOrderCount = "<td class='td-middle'><center><span class='astb_no-item'>"+ (no++) +"</center></td>";
				
				var columnProductCodeAndSerial = "<td class='td-middle'><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
				
				var columnProductName = "<td class='td-middle'>"+ data["result"].productName +"</td>";
				
// 				var columnStatus = "<td class='td-middle' align='center'>ขายขาด</td>";
				
				var columnCost = "<td class='td-middle'><center><input type='hidden' value='"+ data["result"]["equipmentProductItemBeans"][i].cost +"'/>" + data["result"]["equipmentProductItemBeans"][i].cost + " บาท</center></td>";
				
				var columnSalePrice = "<td class='td-middle'><div class='input-group'>"+
									  "<input type='number' class='form-control astbAmount' min='0' value='" + data["result"]["equipmentProductItemBeans"][i].salePrice + "' id='exampleInputAmount'>"+
									  "<div class='input-group-addon'>บาท</div></div></td>";
			
				var columnAction = '<td align="center" class="td-middle">\
					<a class="run-swal cursor-p astb_removeProductItem"><span class="ti-trash"\
						data-toggle="tooltip" data-placement="bottom" title=""\
						data-original-title="ลบ"\
						aria-describedby="tooltip128894"></span></a>\
				<input class="c_C_ASTB_hidden_equpment_product_item_id" type="hidden" value="'+data["result"]["equipmentProductItemBeans"][i].id+'">\
				<input class="c_C_ASTB_hidden_equpment_product_id" type="hidden" value="'+data["result"].id+'"></td>';
					
				rows = rows + startRow + columnOrderCount;
				rows = rows + columnProductCodeAndSerial + columnProductName + columnCost + columnSalePrice + columnAction;
				}
			}
			
			$('#astb_tableEquipmentProduct > tbody').append(rows);

			$('#astb_addEquipmentProductItem').modal('hide');
			
			// เรียงลำดับ
			$('#astb_tableEquipmentProduct > tbody tr').each(function (i) {
			     $(this).find('.astb_no-item').text((i + 1));
			});
			
			// ลบ row
			$(".astb_removeProductItem").click(function () {
				$(this).closest('tr').remove();

				// เรียงลำดับ
				$('#astb_tableEquipmentProduct > tbody tr').each(function (i) {
				     $(this).find('.astb_no-item').text((i + 1));
				});
				
				var removeItem = $(this).parent().parent().find('.c_C_ASTB_hidden_equpment_product_item_id').val();
				astb_productItemId_main_array = jQuery.grep(astb_productItemId_main_array, function(value) {
					  return value != removeItem;
				});
				
				AstbautoInsertAndDeleteData();
			});
			
			$(document).on('blur', '.astbAmount', function(event) {
				AstbautoInsertAndDeleteData();
			});
		}
		
		AstbautoInsertAndDeleteData();
	}
	
	function AstbautoInsertAndDeleteData(){
		worksheetUpdateSnapShotBean = {};
		
		worksheetUpdateSnapShotBean.idWorksheetParent =  $('#hiddenCurrentWorksheetId').val();
		
		//product
		var productItemBeanList = [];
		$('#astb_tableEquipmentProduct > tbody  > tr.astb_trNewItem').each(function() {
			var productWorksheetItem = [];
			var worksheetItem = {};
			var productItem = {};
			 $(this).find('input').each(function (i) {
					switch (i) {
							case 0:
								worksheetItem.price = $(this).val();
								break;
							case 1:
								worksheetItem.amount = $(this).val();
								break;
							case 2:
								var EquipmentProductItemBean = {};
								EquipmentProductItemBean.id = $(this).val();
								worksheetItem.equipmentProductItemBean = EquipmentProductItemBean;
								break;
						    case 3:
						    	productItem.id =  $(this).val();
								break;
							}
					
						});
			 			productWorksheetItem.push(worksheetItem);
			 			productItem.productItemWorksheetBeanList = productWorksheetItem; //productItem ตัวนี้ให้มองว่าเป็น equimentProduct ที่เป็นตัวแม่ของ equipmentProductItem ยนน
			 			productItemBeanList.push(productItem);
					});
		
		worksheetUpdateSnapShotBean.productItemBeanList = productItemBeanList;
			
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetlist/astbInsertAndUpdateEquipmentProductItem/",
			dataType : 'json',
			data : JSON.stringify(worksheetUpdateSnapShotBean),
			//timeout : 100000,
			success : function(data) {

			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
	
	// ลบ row
	$(".astb_removeProductItem").click(function () {
		$(this).closest('tr').remove();

		// เรียงลำดับ
		$('#astb_tableEquipmentProduct > tbody tr').each(function (i) {
		     $(this).find('.astb_no-item').text((i + 1));
		});
		
		var removeItem = $(this).parent().parent().find('.c_C_ASTB_hidden_equpment_product_item_id').val();
		astb_productItemId_main_array = jQuery.grep(astb_productItemId_main_array, function(value) {
			  return value != removeItem;
		});
		
		AstbautoInsertAndDeleteData();
	});
	
	$(document).on('blur', '.astbAmount', function(event) {
		AstbautoInsertAndDeleteData();
	});
	
	function openModalAddsettopBox(){
		astb_resetSearchEquipmentProduct();
		$('#astb_addEquipmentProduct').modal('show');
	}
</script>




