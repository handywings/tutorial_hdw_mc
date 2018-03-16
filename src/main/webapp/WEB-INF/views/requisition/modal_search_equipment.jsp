<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />

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
					<div class="col-md-2 mt05 mb05" align="center">
						<br>
						<button type="button" class="btn btn-primary btn-block"
							onclick="searchEquipmentProduct();">ค้นหา</button>

<!-- 						<button type="button" class="btn btn-defalut" -->
<!-- 							onclick="resetSearchEquipmentProduct();">ล้าง</button> -->
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
											<table class="table table-bordered table-hover" id="table-1">
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
													<c:forEach items="${epbSearchs}" var="epbSearch"
														varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-radio"> <input
																		name="radioEquipmentProduct" type="radio"
																		value="${epbSearch.id }"
																		class="custom-control-input"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description">&nbsp;</span>
																	</label>
																</center></td>
															<td><center>${i.count}</center></td>
															<td><center><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${epbSearch.id }" target="_blank"><b>${epbSearch.productCode }</b></a></center></td>
															<td>${epbSearch.productName }</td>
															<td><center>
																	${textUtil.getFormatNumberInt(epbSearch.usable) }
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
				<button type="button" class="btn btn-success" onclick="appendToBlockChild();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addEquipmentProductItem" tabindex="-1"
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
														<th style="vertical-align: middle;" width="150px"><center>Serial Number</center></th>
														<th style="vertical-align: middle;" width="150px;"><center>จำนวนสินค้าในคลัง</center></th>
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
		$('#table-1').dataTable({searching: false, paging: true});
	});
	
	$(".checkAll").click(function () {
		var checkBox = $(".checkBoxItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});
	
	function backToEquipmentProduct() {
		$('#addEquipmentProductItem').modal('hide');
		$('#addEquipmentProduct').modal('show');
		$('#addEquipmentProduct').css('overflow-y', 'auto');
	}
	
	function openModalSearchEquipment() {
		$('#addEquipmentProduct').modal('show');
	}
	
	function searchEquipmentProduct(){
		searchProcess();
	}
	
	function resetSearchEquipmentProduct(){
		$('#keySearchEquipmentProduct').val("");
		$("#stock").prop("selectedIndex", 0);
		$("#equipmentProductType").prop("selectedIndex", 0);
		searchProcess();
	}
	
	function searchProcess(){
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
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productadd/loadEquipmentProduct",
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
							
							var columnProductCode = "<td><center><a href='${pageContext.request.contextPath}/productorderequipmentproduct/detail/" + data["result"][i]["id"] + "' target='_blank'><b>"+ data["result"][i]["productCode"] +"</b></a></center></td>";
							
							var columnProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
							
							var columnProductItemCount = "<td><center>" + data["result"][i]["usable"] 
							+" "+data["result"][i]["unit"].unitName + "</center></td>";
							
							rows = rows + startRow+columnRadio+columnOrderCount+columnProductCode;
							rows = rows + columnProductName+columnProductItemCount+endRow;
					}
					
					//var emptyData = "";
					if(rows.length > 0){
						$('#table-1 > tbody').html(rows);
					}else{
						$('#table-1 > tbody').empty();
					}
					
					setTimeout(
							function() 
								  {
								$('.preloader').modal('hide');
								$('.table-1').dataTable({searching: false, paging: true});
								$('.table-1').DataTable().draw();
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
						
						var columnProductCode = "<td><center><a href='${pageContext.request.contextPath}/productorderequipmentproduct/detail/" + data["result"].id + "' target='_blank'><b>"+ data["result"].productCode +"</b></a></center></td>";
						var columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
						var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
						var columnProductNumberImport = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].balance +"</center></td>";
						
						rows = rows + startRow + columnCheckBox;
						rows = rows + columnProductSerial + columnProductNumberImport + columnProductRefDoc;
						}
					}
					
					if(""==rows){
						$(".checkAll").attr("disabled", true);
					}else{
						$(".checkAll").removeAttr("disabled");
					}
					
					$('#tableEquipmentProductChild > tbody').html(rows);
					setTimeout(
							function() 
								  {
								$('.preloader').modal('hide');
								$('.table-1').dataTable({searching: false, paging: true});
								$('.table-1').DataTable().draw();
								$('#addEquipmentProductItem').css('overflow-y', 'auto');
								  }, 200);
					$("#tableEquipmentProduct").fadeIn();
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
				var startRow = "<tr class='trOldItem'>";
				var endRow = "</tr>";
				
				var columnOrderCount = "<td style='vertical-align: middle;'><span class='no-item'>"+ (no++) +"</span></td>";
				var columnProductCode = "<td style='vertical-align: middle;'><center><a href='${pageContext.request.contextPath}/productorderequipmentproduct/detail/" + data["result"].id + "' target='_blank'><b>"+ data["result"].productCode +"</b></a></center></td>";
				var columnSerial = "<td style='vertical-align: middle;'>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</td>";
				var columnProductName = "<td>"+ data["result"].productName +"</td>";
				var columnProductNumberImport = "<td style='vertical-align: middle;'><span class='quantity'>1</span> "+data["result"]["unit"].unitName+"</td>";
				
				var columnProductNumberImportTextBox = '<td><div class="input-group">\
									<input type="number" class="form-control numberImport" value="1"\
									min="1" max="'+data["result"]["equipmentProductItemBeans"][i].balance+'">\
									<div class="input-group-addon">'+data["result"]["unit"].unitName+'</div>\
								</div></td>';
								
				if(!data["result"]["equipmentProductItemBeans"][i].serialNo){
					columnProductNumberImport = columnProductNumberImportTextBox;
				}
				
				var columnAction = '<td style="vertical-align: middle;">\
					<a class="run-swal cursor-p removeProductItem"><span class="ti-trash"\
						data-toggle="tooltip" data-placement="bottom" title=""\
						data-original-title="ลบ"\
						aria-describedby="tooltip128894"></span></a>\
				<input class="hidden_item_id" type="hidden" value="'+data["result"]["equipmentProductItemBeans"][i].id+'"></td>'
					
				rows = rows + startRow + columnOrderCount;
				rows = rows + columnProductName + columnProductCode + columnSerial + columnProductNumberImport + columnAction;
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
				$(this).parent().parent().remove();

				// เรียงลำดับ
				$('#tableEquipmentProduct > tbody tr').each(function (i) {
				     $(this).find('.no-item').text((i + 1));
				});
				
				var removeItem = $(this).parent().parent().find('.hidden_item_id').val();
				productItemId_main_array = jQuery.grep(productItemId_main_array, function(value) {
					  return value != removeItem;
				});
				
			});
			
			$(".numberImport").blur(function( event ) {
				var max = $(this).attr("max");
				var min = $(this).attr("min");
				var val = $(this).val();
// 				console.log(isNaN(parseInt(val)));
// 				console.log(parseInt(val) > parseInt(max));
				if(parseInt(val) > parseInt(max)){
					$(this).val(max);
				}else if(isNaN(parseInt(val)) || parseInt(val) < parseInt(min)){
					$(this).val(min);
				}
				
			});
		}
	}
	
</script>




