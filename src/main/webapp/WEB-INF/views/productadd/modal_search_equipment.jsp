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
							<label for="exampleInputEmail1"><b>ค้นหาจาก รหัสสินค้า หรือชื่อสินค้า</b></label> <input type="text" class="form-control"
								id="keySearchEquipmentProduct" placeholder="ระบุรหัสสินค้า หรือ ชื่อสินค้าเพื่อค้นหา...">
						</div>
					</div>
					<div class="col-md-3">
						<div class="">
							<label for="exampleInputEmail1"><b>คลังสินค้า</b></label> <select
								name="productCategory" class="form-control" id="stock">
								<option value="">--- แสดงทุกคลังสินค้า ---</option>
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
								<option value="">--- แสดงประเภทสินค้าทั้งหมด ---</option>
								<c:forEach items="${epcSearchBeans}" var="epcSearchBean"
									varStatus="i">
									<c:if test="${epcSearchBean.equipmentProductCategoryCode ne '00001'}">
									<option value="${epcSearchBean.id}">
										${epcSearchBean.equipmentProductCategoryName}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2 mt05">
						<br>
						<button type="button" class="btn btn-primary btn-block"
							onclick="searchEquipmentProduct();">ค้นหา</button>
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
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า
																/ Serial Number</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;"><center>สินค้าที่มีอยู่เดิม</center></th>

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

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$( document ).ready(function() {
		$('#table-1').dataTable({searching: false, paging: true});
	});
	
	function openModalSearchEquipment() {
		resetSearchEquipmentProduct();
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
		$('#table-1').DataTable().destroy();
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
						
						var columnProductItemCount = "<td><center>" + numberWithCommas(data["result"][i]["usable"]) 
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
					
					setTimeout(function() {
						$('.preloader').modal('hide');
				        $('#table-1').DataTable().draw();
				        $('#addEquipmentProduct').css('overflow-y',
				          'auto');
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
		}
	}
	function loadEuipmentProductItem(id){
		rowCount = 0;
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productadd/loadEquipmentProductWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
						var startRow = "<tr class='trOldItem'>";
						var endRow = "</tr>";
						var columnOrderCount = "<td><center>"+ (i+1) +"</center></td>";
						var columnProductCode = "<td>"+ data["result"].productCode +"</td>";
						var columnProductSerial = "<td><center><input type='hidden' id='masterEquipmentProductSerial' value='"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"' />"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
						var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
						var columnProductNumberImport = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].numberImport +"</center></td>";
						var columnGuaranteeDate = "<td>"+data["result"]["equipmentProductItemBeans"][i].guaranteeDate+"</td>";
						var columnOrderDateDate = "<td>"+data["result"]["equipmentProductItemBeans"][i].orderDate+"</td>";
						var columnAction = "<td></td>";
						var columnProductCost = "<td><center>"+ numberWithCommas(data["result"]["equipmentProductItemBeans"][i].cost) + " " + currentcyTh +"</center></td>";
						var columnProductPriceInTax = "<td><center>"+ numberWithCommas(data["result"]["equipmentProductItemBeans"][i].priceIncTax) + " " + currentcyTh +"</center></td>";
						var columnProductSalePrice = "<td><center>"+ numberWithCommas(data["result"]["equipmentProductItemBeans"][i].salePrice) +" "+ currentcyTh +"</center></td>";
						var columnAction = "<td></td>";
						
						rows = rows + startRow + columnOrderCount;
						rows = rows + columnProductSerial + columnProductRefDoc + columnProductNumberImport;
						rows = rows + columnGuaranteeDate + columnOrderDateDate;
						rows = rows + columnProductCost + columnProductPriceInTax + columnProductSalePrice + columnAction;
					}
					
					$('#tableEquipmentProductChild > tbody').html(rows);
					//append header master
					$('#textMasterProductCode').text(data["result"].productCode);
					$('#textMasterProductName').text(data["result"].productName);
					$('#textMasterProductCategory').text(data["result"]["productCategory"].equipmentProductCategoryName);
					$('#textMasterSupplier').text(data["result"].supplier);
					if(data["result"].isminimum == true){
						$('#textMasterProductMinimum').text(data["result"].minimumNumber);
					}else{
						$('#textMasterProductMinimum').text("-");
					}
					//stock
					$('#textMasterProductStock').text(data["result"]["stock"].stockName);
					//unit
					$('#textMasterProductUnit').text(data["result"]["unit"].unitName);
					
					//financial type
					if(data["result"].financialType == 'A'){
						$('#textFinancialType').text('ทรัพย์สิน');
					}else if(data["result"].financialType == 'C'){
						$('#textFinancialType').text('ต้นทุน');
					}
					
					
					//append master form
					//$('#masterEquipmentProductCode').val(data["result"].productCode);
					//$('#masterEquipmentProductCode').attr('readonly', true);
					//set count order equipment product number
					var countEquipmentProductOld = data["result"]["equipmentProductItemBeans"].length;
					if(countEquipmentProductOld > 0){
						$('#spanMasterEquipmentProductOrderCount').text(countEquipmentProductOld + 1);
					}else{

						$('#spanMasterEquipmentProductOrderCount').text("1");
					}
					//set productEquimentId
					$('#hiddenProductEquipmentId').val(data["result"].id);
					
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
</script>




