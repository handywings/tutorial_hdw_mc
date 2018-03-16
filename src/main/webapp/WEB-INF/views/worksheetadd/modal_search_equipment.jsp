<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addEquipmentProduct" tabindex="-1"
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
								id="keySearchEquipmentProduct">
						</div>
					</div>
					<div class="col-md-3">
						<div class="">
							<label for="exampleInputEmail1"><b>คลังสินค้า</b></label> <select
								name="productCategory" class="form-control" id="stock">
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
								id="equipmentProductType">
								<option value="">ทั้งหมด</option>
								<c:forEach items="${epcSearchBeans}" var="epcSearchBean"
									varStatus="i">
									<option value="${epcSearchBean.id}">
										${epcSearchBean.equipmentProductCategoryName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2 mt05" align="center">
						<br>
						<button type="button" class="btn btn-primary"
							onclick="searchEquipmentProduct();">ค้นหา</button>

						<button type="button" class="btn btn-defalut"
							onclick="resetSearchEquipmentProduct();">ล้าง</button>
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
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-search-equipment">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า
																/ Serial Number</center></th>
														<th style="vertical-align: middle;"><center>ชื่อเรียกสินค้า</center></th>
														<th style="vertical-align: middle;"><center>จำนวนสินค้าในคลัง</center></th>

													</tr>
												</thead>
												<tbody>
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
				<button type="button" class="btn btn-success" onclick="appendToBlockChild();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addEquipmentProductItem" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left"
	style="display: none;" aria-hidden="true">
	<div class="modal-dialog" role="document">
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
											<table class="table table-bordered mb-0 table-hover" id="tableEquipmentProductChild">
												<thead class="thead-bg">
													<tr>
														<th style="vertical-align: middle; width: 20px;">
														<label class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input checkAll" >
														<span class="custom-control-indicator"></span>
														<span class="custom-control-description"></span>
														</label>
														</th>
														<th style="vertical-align: middle;"><center>Serial Number</center></th>
														<th style="vertical-align: middle;"><center>จำนวนรับเข้า</center></th>
														<th style="vertical-align: middle;"><center>เอกสารอ้างอิง</center></th>
													</tr>
												</thead>
												<tbody id="tbody-child-item">
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
				<button type="button" class="btn btn-default pull-left" onclick="backToEquipmentProduct();">กลับ</button>
				<button type="button" class="btn btn-success" onclick="appendToListProduct();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var dataEquipmentProduct = '';
var productItemId_array = [];
var productItemId_main_array = [];

	$( document ).ready(function() {
		$('#table-search-equipment').dataTable({searching: false, paging: true});
		
		$('#tableEquipmentProductChild').dataTable({searching: true, paging: true});
	});
	
	
	
	$(".checkAll").click(function () {
		var checkBox = $(".checkBoxItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});
	
	function backToEquipmentProduct() {
		$('#addEquipmentProductItem').modal('hide');
		$('#addEquipmentProduct').modal('show');
	}
	
	function openModalSearchEquipment() {
		$('#addEquipmentProduct').modal('show');
	}
	
	function searchEquipmentProduct(){
		searchProcessEq();
	}
	
	function resetSearchEquipmentProduct(){
		$('#keySearchEquipmentProduct').val("");
		$("#stock").prop("selectedIndex", 0);
		$("#equipmentProductType").prop("selectedIndex", 0);
		searchProcessEq();
	}
	
	function searchProcessEq(){
		$('.preloader').modal('show');
		
		var equipmentProductBean = {};
		equipmentProductBean.productName = $('#keySearchEquipmentProduct').val();
		//EquipmentProductCategoryBean
		var EquipmentProductCategoryBean = {};
		EquipmentProductCategoryBean.id = $("#equipmentProductType").val();
		equipmentProductBean.productCategory = EquipmentProductCategoryBean;
		//Stock
		var StockBean = {};
		StockBean.id = $("#stock").val();
		equipmentProductBean.stock = StockBean;
		$('#table-search-equipment').DataTable().destroy();
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
								"<input name='radioEquipmentProduct' type='radio'"+
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
						$('#table-search-equipment > tbody').html(rows);
					}else{
						$('#table-search-equipment > tbody').empty();
					}
					
					setTimeout(
						function() 
							  {
							$('.preloader').modal('hide');
							$('#table-search-equipment').dataTable({searching: false, paging: true});
							$('#table-search-equipment').DataTable().draw();
							$('#addEquipmentProduct').css('overflow-y', 'auto');
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
	
	function appendToBlockChild(){
		var radioEquipmentProduct = $("input:radio[name ='radioEquipmentProduct']:checked").val();
		if(typeof radioEquipmentProduct != 'undefined'){
			loadEuipmentProductItem(radioEquipmentProduct);
			$('#addEquipmentProduct').modal('hide');
			$('#addEquipmentProductItem').modal('show');
			$(".checkAll").prop('checked', false);
			productItemId_array = []
		}
	}
	
	function loadEuipmentProductItem(id){
		$('.preloader').modal('show');
		$('#tableEquipmentProductChild').DataTable().destroy();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/requisition/loadEquipmentProductWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				dataEquipmentProduct = data;
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
						if(jQuery.inArray(data["result"]["equipmentProductItemBeans"][i].id, productItemId_main_array) < 0){
						var startRow = "<tr>";
						var endRow = "</tr>";
						var columnCheckBox = '<td><center><label class="custom-control custom-checkbox">\
						<input type="checkbox" class="custom-control-input checkBoxItem" value="'+data["result"]["equipmentProductItemBeans"][i].id+'" >\
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
						$(".checkAll").attr("disabled", true);
					}else{
						$(".checkAll").removeAttr("disabled");
					}
					
					if(rows.length > 0){
						$('#tableEquipmentProductChild > tbody').html(rows);
					}else{
						$('#tableEquipmentProductChild > tbody').empty();
					}
					
					setTimeout(
							function(){
								$('.preloader').modal('hide');
								$('#tableEquipmentProductChild').dataTable({searching: true, paging: true});
								$('#tableEquipmentProductChild').DataTable().draw();
								$('#addEquipmentProductItem').css('overflow-y', 'auto');
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
	
	function appendToListProduct(){
		
		$('.checkBoxItem').each(function (i) {
			if($(this).is(":checked")){
		    	productItemId_array.push($(this).val());
			}
		});

		if('' != dataEquipmentProduct){
			var data = dataEquipmentProduct;
			var rows = "";
			var no = 1;

			for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
				
				if(jQuery.inArray( ""+data["result"]["equipmentProductItemBeans"][i].id, productItemId_array) >= 0){
				productItemId_main_array.push(data["result"]["equipmentProductItemBeans"][i].id);
				var startRow = "<tr class='trNewItem'>";
				var endRow = "</tr>";
				
				var columnOrderCount = "<td class='td-middle'><center><span class='no-item'>"+ (no++) +"</center></td>";
				
				var columnProductCodeAndSerial = "<td class='td-middle'><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
				
				var columnProductName = "<td class='td-middle'>"+ data["result"].productName +"</td>";
				
// 				var columnStatus = "<td class='td-middle' align='center'>ขายขาด</td>";
				
				var columnCost = "<td class='td-middle'><center><input type='hidden' value='"+ data["result"]["equipmentProductItemBeans"][i].cost +"'/>" + data["result"]["equipmentProductItemBeans"][i].cost + " บาท</center></td>";
				
				var columnSalePrice = "<td class='td-middle'><div class='input-group'>"+
									  "<input type='number' class='form-control' min='0' value='" + data["result"]["equipmentProductItemBeans"][i].salePrice + "' id='exampleInputAmount'>"+
									  "<div class='input-group-addon'>บาท</div></div></td>";
			
				var columnAction = '<td align="center" class="td-middle">\
					<a class="run-swal cursor-p removeProductItem"><span class="ti-trash"\
						data-toggle="tooltip" data-placement="bottom" title=""\
						data-original-title="ลบ"\
						aria-describedby="tooltip128894"></span></a>\
				<input class="c_C_ASTB_hidden_equpment_product_item_id" type="hidden" value="'+data["result"]["equipmentProductItemBeans"][i].id+'">\
				<input class="c_C_ASTB_hidden_equpment_product_id" type="hidden" value="'+data["result"].id+'"></td>';
					
				rows = rows + startRow + columnOrderCount;
				rows = rows + columnProductCodeAndSerial + columnProductName + columnCost + columnSalePrice + columnAction;
				}
			}
			
			$('#tableEquipmentProduct > tbody').append(rows);

			$('#addEquipmentProductItem').modal('hide');
			
			// เรียงลำดับ
			$('#tableEquipmentProduct > tbody tr').each(function (i) {
			     $(this).find('.no-item').text((i + 1));
			});
			
			// ลบ row
			$(".removeProductItem").click(function () {
				$(this).closest('tr').remove();

				// เรียงลำดับ
				$('#tableEquipmentProduct > tbody tr').each(function (i) {
				     $(this).find('.no-item').text((i + 1));
				});
				
				var removeItem = $(this).parent().parent().find('.c_C_ASTB_hidden_equpment_product_item_id').val();
				productItemId_main_array = jQuery.grep(productItemId_main_array, function(value) {
					  return value != removeItem;
				});
				
			});
			
		}
	}
	
	function openModalAddsettopBox(){
		resetSearchEquipmentProduct();
		$('#addEquipmentProduct').modal('show');
	}
</script>




