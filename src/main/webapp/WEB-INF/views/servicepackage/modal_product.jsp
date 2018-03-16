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
							<option value="" selected="selected">--- แสดงทุกประเภทสินค้า ---</option>
							<c:forEach items="${epcBeans}" var="epcBean" varStatus="i">
							<c:if test="${epcBean.equipmentProductCategoryCode eq '00001' || epcBean.equipmentProductCategoryCode eq '00002' || epcBean.equipmentProductCategoryCode eq '00003'}">
								<option value="${epcBean.equipmentProductCategoryCode}">
									${epcBean.equipmentProductCategoryName}</option>
							</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2 mt05" align="center">
						<button type="button" class="btn btn-primary btn-block"
							onclick="searchProduct();">ค้นหา</button>

<!-- 						<button type="button" class="btn btn-defalut" -->
<!-- 							onclick="resetSearchProduct();">ล้าง</button> -->
					</div>
				</div>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="responsive-fieldset">
										<div class="table-responsive">
											<table class="table table-bordered table-1 table-hover table-product" id="table-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 60px"><center>รหัสสินค้า</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;"><center>ประเภทบริการ</center></th>
														<th style="vertical-align: middle;">คลังสินค้า</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${productBeans}" var="productBean" varStatus="i">
													<tr>
														<td>
															<center>
																<label class="custom-control custom-checkbox"> <input
																	name="radioProduct" type="checkbox"
																	value="${productBean.id}|${productBean.type}" class="custom-control-input">
																	<span class="custom-control-indicator"></span> <span
																	class="custom-control-description">&nbsp;</span>
																</label>
															</center>
														</td>
														<td><center>${productBean.productCode }</center></td>
														<td>${productBean.productName }</td>
														<td><center>
															<c:choose>
																<c:when test="${productBean.productCategory.equipmentProductCategoryCode eq '00002'}">
																	บริการ Internet User
																</c:when>
																<c:when test="${productBean.productCategory.equipmentProductCategoryCode eq '00003'}">
																	ค่าบริการ
																</c:when>
																<c:otherwise>วัสดุอุปกรณ์สินค้า</c:otherwise>
															</c:choose></center>
														</td>
														<td>${productBean.stock.stockName }</td>
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
	var dataTableProductAdd;
	$( document ).ready(function() {
		$('#table-1').DataTable({searching: false, paging: true});
	});
	
	function openModalSearchProduct(){
		searchProcess();
		$('#addProduct').modal('show');
	}
	
	function appendToBlockChild(){
		var hideModel = false;
		$('#tableTemplate').show();
		$('.preloader').modal('show');
		$('.table-product > tbody  > tr').each(function() {
			 $(this).find('input').each(function (i) {
				 switch (i) {
					case 0:
						if($(this).is(":checked")){
							var radioProduct = $(this).val();
// 							console.log(radioProduct);
				 			loadProductItem(radioProduct);
				 			hideModel = true;
						};
					break;
				 }
			 });
		});
		if(hideModel){
			$('#addProduct').modal('hide');
		}else{
			$('.preloader').modal('hide');
		}
	}
	
	function loadProductItem(key){
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
					var rows = "";
					var startRow = "<tr class='trNewItem'>";
					var endRow = "</tr>";
					var rowCount = $('#tableTemplate > tbody tr').length;
					var columnOrderCount = "<td><center>"+ (rowCount + 1) +"</center></td>";
					var columnProductCode = "<td><center>"+ data["result"]["productCode"]+"</center>"+ "<input type='hidden' value='"+data["result"]["id"]+"' /><input type='hidden' value='"+data["result"]["type"]+"' />" +"</td>";

					var columnProductName = "<td>"+ data["result"]["productName"] +"</td>";
					
					var columnProductType = "<td><center>"+ data["result"]["productCategory"].equipmentProductCategoryName +"</center></td>";
					
// 					var unitName = "";
// 					if(data["result"]["unit"] == null){
// 						unitName = "user";
// 					}else{
// 						unitName = data["result"]["unit"].unitName;
// 					}
					
					var serialNo = "";
					if(seperate[1] == "E"){
						serialNo = data["result"]["equipmentProductItemBeans"][0]["serialNo"];
						var disabled = "";
						var disabled2 = "";
						if(""==serialNo){
							disabled = 'disabled="disabled"';
						}
					}
					
					if(seperate[1] == "S"){
						disabled = 'disabled="disabled"';
					}
					
					if(seperate[1] == "I"){
						disabled = 'disabled="disabled"';
						disabled2 = 'style="display: none"';
					}
					
					var unitName = data["result"]["unit"].unitName;
					var columnProductNumber = "<td><center><div class='input-group'>"+
					"<input type='number' min='1' value='1' class='form-control' />"+
					"<div class='input-group-addon'>"+ unitName +"</div></div></center></td>";
					
					var columnProductFree = "<td><center " + disabled2 + "><label class='custom-control custom-checkbox'>"+
						"<input type='checkbox' class='custom-control-input'>"+
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'></span>"+
						"</label></center></td>";
						
					var columnProductLend = "";
					
					if(disabled != null && disabled != "") {
						columnProductLend = "<td><center style='display: none'><label class='custom-control custom-checkbox'>"+
						"<input type='checkbox' class='custom-control-input' "+disabled+" >"+
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'></span>"+
						"</label></center></td>";
					} else {
						columnProductLend = "<td><center><label class='custom-control custom-checkbox'>"+
						"<input type='checkbox' class='custom-control-input' "+disabled+" >"+
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'></span>"+
						"</label></center></td>";
					}
					
					var columnAction = "<td><center><a class='removeRowsChildNew' style='cursor:pointer'>"+
					"<span class='ti-trash' data-toggle='tooltip'"+
					"data-placement='bottom' title=''"+
					" ></span></a></center></td>";
					
					rows = rows + startRow + columnOrderCount;
					rows = rows + columnProductCode + columnProductName + columnProductType;
					rows = rows + columnProductNumber + columnProductFree + columnProductLend;
					rows = rows + columnAction + endRow;
					$('#tableTemplate > tbody').append(rows);
					
					
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
		//dataTableProductAdd.fnClearTable();
		$('#table-1').DataTable().destroy();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/servicepackage/searchProductAjax",
			dataType : 'json',
			async: false,
			data : JSON.stringify(searchAllProductBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){					
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						if(checkDuplicate(data["result"][i]["id"],data["result"][i]["type"])){ // เช็คอุปกรณ์ที่ถูกเลือกไปแล้วจะต้องไม่แสดงใน dailog
							continue;
						}
						var startRow = "<tr>";
						var endRow = "</tr>";
						var tdRadio = "<td><center>"+
						"<label class='custom-control custom-checkbox'> <input name='radioProduct' type='checkbox' " +
						"value='"+ data["result"][i]["id"] +"|"+  data["result"][i]["type"] +"' class='custom-control-input'> " +
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>"; 
						
						var colOrderCount = "<td>"+ (i+1) +"</td>";
						var colProductCode = "<td>"+ data["result"][i]["productCode"] +"</td>";
						var colProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
						var colProductCategory = "<td>"+ data["result"][i]["productCategory"].equipmentProductCategoryName +"</td>";
						var colProductStock = "<td>"+ data["result"][i]["stock"].stockName +"</td>";
						rows = rows + startRow + tdRadio + colProductCode + colProductName + colProductCategory + colProductStock + endRow;
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
							$('#table-1').dataTable({searching: false, paging: true});
							$('#table-1').DataTable().draw();
							$('#addProduct').css('overflow-y', 'auto');
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
	
	function checkDuplicate(id,type){
		var returnDuplicate = false;
		$('#tableTemplate > tbody  > tr.trNewItem').each(function() {
			var idHidden = "", typeHidden = "";
			$(this).find('input').each(function (i) {
				switch (i) {
					case 0:
						idHidden = $(this).val();
						break;
					case 1:
						typeHidden = $(this).val();
						break;
				}
			});
// 			console.log(idHidden);
// 			console.log(typeHidden);
			if(id == idHidden && type == typeHidden){
				returnDuplicate = true;
			}
		});
		return returnDuplicate;
	}
	
</script>
 