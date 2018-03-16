<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addProduct" tabindex="-1"
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
					<div class="col-md-7 mt05">
						<input type="text" name="key" id="key" class="form-control" value=""
							placeholder="ระบุรหัส หรือ ชื่อสินค้าบริการที่ต้องการค้นหา">
					</div>
					<div class="col-md-3 mt05">
						<select id="epc" name="categoryID" class="form-control">
							<option value="" selected="selected">--- หมวดหมู่อุปกรณ์ทั้งหมด ---</option>
							<c:forEach items="${epcBeans}" var="epcBean" varStatus="i">
								<option value="${epcBean.equipmentProductCategoryCode}">
									${epcBean.equipmentProductCategoryName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2 mt05">
						<button type="button" class="btn btn-primary"
							onclick="searchProduct();">ค้นหา</button>

						<button type="button" class="btn btn-defalut"
							onclick="resetSearchProduct();">ล้าง</button>
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
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;">ประเภทบริการ</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${productBeans}" var="productBean" varStatus="i">
													<tr>
														<td>
															<center>
																<label class="custom-control custom-radio"> <input
																	name="radioProduct" type="radio"
																	value="${productBean.id}|${productBean.type}" class="custom-control-input">
																	<span class="custom-control-indicator"></span> <span
																	class="custom-control-description">&nbsp;</span>
																</label>
															</center>
														</td>
														<td><center>${i.count }</center></td>
														<td><center><b>${productBean.productCode }</b></center></td>
														<td>${productBean.productName }</td>
														<td>${productBean.productCategory.equipmentProductCategoryName}</td>
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
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="appendToBlockChild();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var dataTable = '';
$( document ).ready(function() {
	dataTable = $('#table-1').dataTable({searching: false, paging: true});
});
	
	function openModalSearchProduct(){
		$('#addProduct').modal('show');
	}
	
	function appendToBlockChild(){
		var radioProduct = $("input:radio[name ='radioProduct']:checked").val();
		if(typeof radioProduct != 'undefined'){
			loadProductItem(radioProduct);
			$('#addProduct').modal('hide');
		}
	}
	
	function loadProductItem(key){
		$('.preloader').modal('show');
		var url = "";
		var seperate = key.split("|");
		
		//check search choose same old choose
		var countSame = 0;
		$('#tableTemplate > tbody  > tr.trNewItem').each(function() {
			 var idType = "";
				 $(this).find('input').each(function (i) {
					 //alert($(this).val());
						switch (i) {
								case 0:
									idType = idType + $(this).val() + "|";
									break;
								case 1:
									idType = idType + $(this).val();
									break;
								}
							});  
				 			if(idType == key){
				 				countSame = countSame + 1;
				 			}
						}); //end find tr
		
						if(countSame > 0){
							$('.preloader').modal('hide');
							return false;
						}
						
		if(seperate[1] == "E"){
			url = "${pageContext.request.contextPath}/servicepackage/loadEquipmentProductWithId/"+seperate[0];
		}else if(seperate[1] == "I"){
			url = "${pageContext.request.contextPath}/servicepackage/loadInternetProductWithId/"+seperate[0];
		}else if(seperate[1] == "S"){
			url = "${pageContext.request.contextPath}/servicepackage/loadServiceProductWithId/"+seperate[0];
		}
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : url,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){ 
// 					console.log(data);		
					if(checkAppendTable(data)){
					var rows = "";
					var startRow = "<tr>";
					var endRow = "</tr>";
// 					var rowCount = $('#tableTemplate > tbody tr').length;
					var columnOrderCount = "<td><center><span class='template-no'></span></center></td>";
					
					columnOrderCount = columnOrderCount + "<input class='productId' type='hidden' value='"+data["result"]["id"]+"'>"+
					"<input class='productType' type='hidden' value='"+data["result"]["type"]+"'></td>";
					
					var columnProductCode = "<td><center>"+ data["result"]["productCode"]+"</center></td>";

					var columnProductName = "<td>"+ data["result"]["productName"] +"</td>";
					
					var unitName = data["result"]["unit"].unitName;
					
					var columnProductNumber = "<td><center><div class='input-group'>"+
					"<input type='number' min='1' value='1' class='form-control quantity calculate' />"+
					"<div class='input-group-addon'>"+ unitName +"</div></div></center></td>";
					
					var salePrice = '<td scope="row"><div class="input-group">\
						<input type="number" class="form-control salePrice calculate" value="0" >\
						<div class="input-group-addon">บาท</div>\
					</div></td>';
					
					var columnProductFree = "<td><center><label class='custom-control custom-checkbox'>"+
						"<input type='checkbox' class='custom-control-input check-box-free'>"+
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'></span>"+
						"</label></center></td>";
						
					var columnProductFree = '<td align="center" style="vertical-align: middle;">\
						<label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input check-box-free"> <span\
							class="custom-control-indicator"></span> <span\
							class="custom-control-description"></span>\
						</label>\
						</td>';
						
					var columnProductLend = '<td align="center" style="vertical-align: middle;">\
						<label class="custom-control custom-checkbox">\
						<input type="checkbox" class="custom-control-input check-box-lend"> <span\
						class="custom-control-indicator"></span> <span\
						class="custom-control-description"></span>\
						</label>\
						</td>';
					
					var totalPrice = '<td scope="row"><div class="input-group">\
						<input type="number" class="form-control totalPrice" value="0" disabled>\
						<div class="input-group-addon">บาท</div>\
					</div></td>';
					
					var columnAction  = '<td align="center" style="vertical-align: middle;">\
						<a class="run-swal cursor-p"><span\
						class="ti-trash remove-template" data-toggle="tooltip"\
						data-placement="bottom" title=""\
						data-original-title="ลบ"></span></a>\
						</td>';
					
					rows = rows + startRow + columnOrderCount;
					rows = rows + columnProductCode + columnProductName + columnProductNumber;
// 					rows = rows + salePrice + columnProductFree + columnProductLend;
// 					rows = rows + totalPrice;
					rows = rows + columnAction + endRow;
					$('#table_template_equiment > tbody').append(rows);
					calculate();
					}
					setTimeout(
						function(){
						$('.preloader').modal('hide');
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
	
	function checkAppendTable(data){
		console.log(data);
		var flag = true;
		var id = data["result"]["id"];
		var type = data["result"]["type"];
		$.each($('#table_template_equiment tbody tr'), function( index, value ) {
			var productId = $(this).find('.productId').val();
			var productType = $(this).find('.productType').val();
			if(productId==id && productType==type){
				flag = false;
			}
		});
		return flag;
	}
	
	function searchProduct(){
		searchProcess();
	}
	
	function resetSearchProduct(){
		$('#key').val("");
		$("#epc").prop("selectedIndex", 0);
		searchProcess();
	}
	
	function searchProcess(){
		$('.preloader').modal('show');
		var searchAllProductBean = {};
		searchAllProductBean.key = $('#key').val();
		searchAllProductBean.productTypeCode = $('#epc').val();
		dataTable.fnClearTable();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/servicepackage/searchProductAjax",
			dataType : 'json',
			async: false,
			data : JSON.stringify(searchAllProductBean),
			//timeout : 100000,
			success : function(data) {
				var table = $("#table-1").DataTable();
				if(data["error"] == false){					
					for(var i=0;i<data["result"].length;i++){
						var rows = "";
						var startRow = "<tr>";
						var endRow = "</tr>";
						var tdRadio = "<td><center>"+
						"<label class='custom-control custom-radio'> <input name='radioProduct' type='radio' " +
						"value='"+ data["result"][i]["id"] +"|"+  data["result"][i]["type"] +"' class='custom-control-input'> " +
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>"; 
						
						var colOrderCount = "<td><center>"+ (i+1) +"</center></td>";
						var colProductCode = "<td><center><b>"+ data["result"][i]["productCode"] +"</b></center></td>";
						var colProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
						var colProductCategory = "<td>"+ data["result"][i]["productCategory"].equipmentProductCategoryName +"</td>";
						
						rows = rows + startRow + tdRadio + colOrderCount + colProductCode + colProductName + colProductCategory + endRow;
						table.rows.add($(rows)).draw();
					}
					
					setTimeout(function() 
							{
								$('.preloader').modal('hide');
								$('#addProduct').css('overflow-y', 'auto');
							}, 200);
					
				}else{
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
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
 