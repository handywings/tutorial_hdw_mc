<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addEquipmentProduct_setup" tabindex="-1"
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
								id="keySearchEquipmentProduct" placeholder="ระบุรหัส หรือชื่อสินค้า...">
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
							onclick="searchEquipmentProduct_setup();">ค้นหา</button>
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
											<table class="table table-bordered table-hover" id="table-1-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle;"><center>รหัสสินค้า</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;">ประเภทสินค้า</th>
														<th style="vertical-align: middle;"><center>จำนวนสินค้าในคลัง</center></th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${prodSearchs}" var="prodSearch"
														varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-radio"> <input
																		name="radioEquipmentProduct" type="radio"
																		value="${prodSearch.id }|${prodSearch.type}"
																		class="custom-control-input"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description">&nbsp;</span>
																	</label>
																</center></td>
															<td><center>${i.count}</center></td>
															<td><center>${prodSearch.productCode }</center></td>
															<td>${prodSearch.productName }</td>
															<td>${prodSearch.productCategory.equipmentProductCategoryName }</td>
															<td><center>
															<c:if test="${prodSearch.type eq 'E'}">
																	${prodSearch.usable + prodSearch.reserve}
																	${prodSearch.unit.unitName }
															</c:if>
																</center>
															</td>
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
				<button type="button" class="btn btn-success" onclick="appendToBlockChild_setup();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="addEquipmentProductItem_setup" tabindex="-1"
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
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row">
					<div class="col-md-10">
						<div class="">
							<input type="hidden" id="equipmentProductId" >
							<label for="exampleInputEmail1"><b>เลือกสินค้าจากกลุ่มทีมช่างที่เบิกไป(หัวหน้ากลุ่ม)</b></label> <select
								name="technicianGroup" class="form-control" id="technicianGroup">
								<option value="0">แสดงข้อมูลสินค้าทั้งหมด</option>
								<c:forEach items="${technicianGroupBeans}" var="technicianGroupBean" varStatus="i">
									<option value="${technicianGroupBean.personnel.id}">
										แสดงข้อมูลสินค้าที่เบิกโดย ${technicianGroupBean.personnel.firstName} ${technicianGroupBean.personnel.lastName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-2 mt05">
						<br>
						<button type="button" class="btn btn-primary btn-block"
							onclick="searchEquipmentProductItem_setup();">ค้นหา</button>
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
											<table class="table table-bordered mb-0 table-hover" id="tableEquipmentProductChild">
												<thead class="thead-bg">
													<tr>
														<th style="vertical-align: middle; width: 20px;">
														<center>
														<label class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input checkAll" >
														<span class="custom-control-indicator"></span>
														<span class="custom-control-description"></span>
														</label>
														</center>
														</th>
														<th style="vertical-align: middle;"><center>Serial Number</center></th>
														<th style="vertical-align: middle;"><center>ผู้ขอเบิกสินค้า</center></th>
														<th style="vertical-align: middle;"><center>จำนวนสินค้าในคลัง</center></th>
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
				<button type="button" class="btn btn-default pull-left" onclick="backToEquipmentProduct_setup();">กลับ</button>
				<button type="button" class="btn btn-success" onclick="appendToListProduct_setup();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="addInternetProductItem_setup" tabindex="-1"
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
											<table class="table table-bordered table-hover" id="tableInternetProductChild">
												<thead class="thead-bg">
													<tr>
														<th style="width: 20px;"></th>
														<th><center>Username</center></th>
														<th><center>Password</center></th>
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
				<button type="button" class="btn btn-default pull-left" onclick="backToInternetProduct_setup();">กลับ</button>
				<button type="button" class="btn btn-success" onclick="appendToListInternetProduct_setup();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var dataEquipmentProduct = '';
var dataInternetProduct = '';
var productItemId_array = [];
var productItemId_main_array = [];
var dataTable = '';
	$( document ).ready(function() {
		dataTable = $('#table-1-1').dataTable({searching: false, paging: true});
	});
	
	$(".checkAll").click(function () {
		var checkBox = $(".checkBoxItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});
	
	function backToEquipmentProduct_setup() {
		$('#addEquipmentProductItem_setup').modal('hide');
		$('#addEquipmentProduct_setup').modal('show');
	}
	
	function backToInternetProduct_setup() {
		$('#addInternetProductItem_setup').modal('hide');
		$('#addEquipmentProduct_setup').modal('show');
	}

	function openModalSearchEquipment_setup() {
		searchProcess_setup();
		$('#addEquipmentProduct_setup').modal('show');
		//$('#addEquipmentProduct_setup').css('z-index', 1040);
	}
	
	function searchEquipmentProduct_setup(){
		searchProcess_setup();
	}
	
	function resetSearchEquipmentProduct_setup(){
		$('#keySearchEquipmentProduct').val("");
		$("#stock").prop("selectedIndex", 0);
		$("#equipmentProductType").prop("selectedIndex", 0);
		searchProcess_setup();
	}
	
	function searchProcess_setup(){
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
			url : "${pageContext.request.contextPath}/worksheetlist/loadEquipmentProduct",
			dataType : 'json',
			async: false,
			data : JSON.stringify(equipmentProductBean),
			//timeout : 100000,
			success : function(data) {
				dataTable.fnClearTable();
				if(data["error"] == false){
					console.log(data);
					var table = $("#table-1-1").DataTable();
					//create data table search
					for(var i=0;i<data["result"].length;i++){
							var rows = "";
							var startRow = "<tr>";
							var endRow = "</tr>";
							
							var columnRadio = "<td><center>" +
								"<label class='custom-control custom-radio'>" + 
								"<input name='radioEquipmentProduct' type='radio'"+
								" value='"+ data["result"][i]["id"]+"|"+ data["result"][i]["type"] +"'" +
								"class='custom-control-input'>" +
								"<span class='custom-control-indicator'></span>" +
								"<span class='custom-control-description'>&nbsp;</span>" +
								"</label></center></td>";
								
							var columnOrderCount = "<td><center>"+(i+1)+"</center></td>";
							
							var columnProductCode = "<td><center>"+ data["result"][i]["productCode"] +"</center></td>";
							
							var columnProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
							
							var columnProductCategory = "<td>"+ data["result"][i]["productCategory"].equipmentProductCategoryName +"</td>";
							
							var usable = parseInt(data["result"][i]["usable"]);
							var reserve = parseInt(data["result"][i]["reserve"]);
							
							var columnProductItemCount = "<td></td>";
							if(data["result"][i]["type"]=="E"){
							columnProductItemCount = "<td><center>" + (usable+reserve) +" "+data["result"][i]["unit"].unitName + "</center></td>";
							}
							
							rows = rows + startRow+columnRadio + columnOrderCount + columnProductCode;
							rows = rows + columnProductName + columnProductCategory + columnProductItemCount + endRow;
							
							table.rows.add($(rows)).draw();
					}
					
					setTimeout(function() 
					{
						$('.preloader').modal('hide');
						$('#addEquipmentProduct_setup').css('overflow-y', 'auto');
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
	
	function searchEquipmentProductItem_setup(){
		loadEuipmentProductItemV2_setup();
	}
	
	function resetSearchEquipmentProductItem_setup(){
		$("#technicianGroup").prop("selectedIndex", 0);
		loadEuipmentProductItemV2_setup();
	}

	
	function appendToBlockChild_setup(){
		var radioEquipmentProduct = $("input:radio[name ='radioEquipmentProduct']:checked").val();
		if(typeof radioEquipmentProduct != 'undefined'){
			var seperate = radioEquipmentProduct.split("|");
			console.log('appendToBlockChild_setup');
			if(seperate[1] == "E"){
				loadEuipmentProductItem_setup(seperate[0]);
				$('#equipmentProductId').val(seperate[0]);
				$("#technicianGroup").prop("selectedIndex", 0);
				$('#addEquipmentProduct_setup').modal('hide');
				$('#addEquipmentProductItem_setup').modal('show');
				$(".checkAll").prop('checked', false);
				productItemId_array = [];
			}else if(seperate[1] == "S"){
				appendServiceToListProduct_setup(seperate[0]);
			}else if(seperate[1] == "I"){
				loadInternetProductItem_setup(seperate[0]);
				$('#addEquipmentProduct_setup').modal('hide');
				$('#addInternetProductItem_setup').modal('show');
			}
		}
	}
	
	function loadEuipmentProductItem_setup(id){
// 		$('.preloader').modal('show');
		$('#tableEquipmentProductChild').DataTable().destroy();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetlist/loadEquipmentProductWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				dataEquipmentProduct = data;
				if(data["error"] == false){
// 					console.log(data);
					var rows = "";
					for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
						if(jQuery.inArray(data["result"]["equipmentProductItemBeans"][i].id, productItemId_main_array) < 0){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var requisitionItemBeans = data["result"]["equipmentProductItemBeans"][i]["requisitionItemBeans"];
						var requester = "คลังสินค้าส่วนกลาง", requesterId = "0";
						var serialNo = data["result"]["equipmentProductItemBeans"][i].serialNo;
						
						if(requisitionItemBeans != null){
							if(requisitionItemBeans.length > 0){
								requester = requisitionItemBeans[0].personnelBean.firstName+" "+requisitionItemBeans[0].personnelBean.lastName;
								requesterId = requisitionItemBeans[0].id;
							}
						}
						
						var columnCheckBox = '<td><center><label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input checkBoxItem" value="'+data["result"]["equipmentProductItemBeans"][i].id+'|'+requesterId+'" >\
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
							</label></center></td>';
						
						var balance = parseInt(data["result"]["equipmentProductItemBeans"][i].balance);
						if(balance < 1){// ถ้าจำนวนอุปกรน้อยกว่า 1 ไม่ให้แสดง
							continue;
						}
// 						var spare = parseInt(data["result"]["equipmentProductItemBeans"][i].spare);
						
						var columnProductCode = "<td>"+ data["result"].productCode +"</td>";
						var columnPersonnel = "<td><center>"+ requester +"</center></td>";
						var columnProductSerial = "";
						if(data["result"]["equipmentProductItemBeans"][i].repair){
							columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +' <span style="color: #f59345;">(มีประวัติการซ่อม)</span></center></td>';
						}else{
							columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +'</center></td>';
						}
						var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
						var columnProductNumberImport = "<td><center>"+ balance  + "</center></td>";
						rows = rows + startRow + columnCheckBox;
						rows = rows + columnProductSerial + columnPersonnel + columnProductNumberImport + columnProductRefDoc;
						}
					}
					
					if(""==rows){
						$(".checkAll").attr("disabled", true);
					}else{
						$(".checkAll").removeAttr("disabled");
					}
					
					$('#tableEquipmentProductChild > tbody').html(rows);
					
					$('#tableEquipmentProductChild').DataTable().draw();
					$('#addEquipmentProductItem_setup').css('overflow-y', 'auto');
					
// 					setTimeout(
// 						function(){
// 						$('.preloader').modal('hide');
// 						}, 200);
					
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
	
	function loadInternetProductItem_setup(id){
// 		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/requisition/loadInternetWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
// 					console.log(data);
					dataInternetProduct = data;
					var rows = "";
					for(var i=0;i<data["result"]["internetProductBeanItems"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var columnRadio = "<td><center>" +
						"<label class='custom-control custom-radio'>" + 
						"<input name='radioInternetProduct' type='radio'"+
						" value='"+ data["result"]["internetProductBeanItems"][i].id +"'" +
						"class='custom-control-input'>" +
						"<span class='custom-control-indicator'></span>" +
						"<span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>";
						
						var columnUser = "<td><center>"+ data["result"]["internetProductBeanItems"][i].userName +"</center></td>";
						var columnPass = "<td><center>"+ data["result"]["internetProductBeanItems"][i].password +"</center></td>";
						
						rows = rows + startRow + columnRadio;
						rows = rows + columnUser + columnPass;
					}
					
					$('#tableInternetProductChild > tbody').html(rows);

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
	
	function loadEuipmentProductItemV2_setup(id){
		$('.preloader').modal('show');
		var technicianGroupId = $("#technicianGroup").val();
		var id = $('#equipmentProductId').val();
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetlist/loadEquipmentProductWithIdAndTechnicianGroupId/"+id+"/technicianGroupId/"+technicianGroupId,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				dataEquipmentProduct = data;
				if(data["error"] == false){
// 					console.log(data);
					var rows = "";
					for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
						if(jQuery.inArray(data["result"]["equipmentProductItemBeans"][i].id, productItemId_main_array) < 0){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var requisitionItemBeans = data["result"]["equipmentProductItemBeans"][i]["requisitionItemBeans"];
						var requester = "คลังสินค้าส่วนกลาง", requesterId = "0";
						var serialNo = data["result"]["equipmentProductItemBeans"][i].serialNo;
						
						if(requisitionItemBeans != null){
							if(requisitionItemBeans.length > 0){
								requester = requisitionItemBeans[0].personnelBean.firstName+" "+requisitionItemBeans[0].personnelBean.lastName;
								requesterId = requisitionItemBeans[0].id;
							}
						}
						
						var columnCheckBox = '<td><center><label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input checkBoxItem" value="'+data["result"]["equipmentProductItemBeans"][i].id+'|'+requesterId+'" >\
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
							</label></center></td>';
						
						var balance = parseInt(data["result"]["equipmentProductItemBeans"][i].balance);
						var spare = parseInt(data["result"]["equipmentProductItemBeans"][i].spare);
						
						if(balance < 1){// ถ้าจำนวนอุปกรน้อยกว่า 1 ไม่ให้แสดง
							continue;
						}
						
						var columnProductCode = "<td>"+ data["result"].productCode +"</td>";
						var columnPersonnel = "<td><center>"+ requester +"</center></td>";
						var columnProductSerial = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].serialNo +"</center></td>";
						var columnProductRefDoc = "<td><center>"+ data["result"]["equipmentProductItemBeans"][i].referenceNo +"</center></td>";
						var columnProductNumberImport = "<td><center>"+ balance  + "</center></td>";
						
						rows = rows + startRow + columnCheckBox;
						rows = rows + columnProductSerial + columnPersonnel + columnProductNumberImport + columnProductRefDoc;
						}
					}
					
					if(""==rows){
						$(".checkAll").attr("disabled", true);
					}else{
						$(".checkAll").removeAttr("disabled");
					}
					
					$('#tableEquipmentProductChild > tbody').html(rows);
					
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
	
	function appendToListProduct_setup(){
		console.log("appendToListProduct_setup");
// 		$('.checkBoxItem').each(function (i) {
// 			if($(this).is(":checked")){
// 		    	productItemId_array.push($(this).val());
// 			}
// 		});

		if('' != dataEquipmentProduct){
			var data = dataEquipmentProduct;
			var rows = "";
			var no = 1;
			console.log(data);
			$('.checkBoxItem').each(function (i) {
				if($(this).is(":checked")){
// 			for(var i=0;i<data["result"]["equipmentProductItemBeans"].length;i++){
// 				if(jQuery.inArray(""+data["result"]["equipmentProductItemBeans"][i].id,productItemId_array) >= 0){
// 				productItemId_main_array.push(data["result"].id);
				var startRow = "<tr class='trNewItem'>";
				var endRow = "</tr>";
				var disabled = "", disabledNum = "";
				
				var requester = "คลังสินค้าส่วนกลาง", requesterId = "0";
				var requisitionItemBeans = data["result"]["equipmentProductItemBeans"][i]["requisitionItemBeans"];
				if(requisitionItemBeans != null){					
					if(requisitionItemBeans.length > 0){
						requester = "<span class='ti-file'></span>&nbsp;เบิกจากใบเบิก&nbsp;<a target='_blank' href='${pageContext.request.contextPath}/requisitionlist/view/" + requisitionItemBeans[0].requisitionDocumentBean.id + "'>#" + requisitionItemBeans[0].requisitionDocumentBean.requisitionDocumentCode + "</a><br><span class='ti-user'></span>&nbsp;";
						requester += requisitionItemBeans[0].personnelBean.firstName+" "+requisitionItemBeans[0].personnelBean.lastName+"&nbsp;(ผู้เบิก)";
						requesterId = requisitionItemBeans[0].id;
					}
				}
				
				if(!data["result"]["equipmentProductItemBeans"][i].serialNo) disabled = "disabled";
				if(data["result"]["equipmentProductItemBeans"][i].serialNo) disabledNum = "disabled";

				var balance = parseInt(data["result"]["equipmentProductItemBeans"][i].balance);
				var spare = parseInt(data["result"]["equipmentProductItemBeans"][i].spare);
				
				var columnOrderCount = "<td><center><span class='no-item'>"+ (no++) +"</span><input type='hidden' value='"+ data["result"].type +"' /><center></td>";
				var columnProductCode = "<td>" + data["result"].productName + "<br><font style='color: gray;'><a href='${pageContext.request.contextPath}/productorderequipmentproduct/detail/" + data["result"].id + "' target='_blank'>" + data["result"].productCode + "</a></font><br><small class='text-gray'><b>"+requester+"</b></small></td>";
				var columnSerial = "<td><center> <a href='${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/" + data["result"]["equipmentProductItemBeans"][i].id + "' target='_blank'>" + data["result"]["equipmentProductItemBeans"][i].serialNo + "</a> </center></td>";
								
// 				var columnProductName = "<td>"+ data["result"].productName +"</td>";
				var columnProductNumberImport = "<td style='vertical-align: middle;'><input type='hidden' value='1' /><span class='quantity'>"+ data["result"]["equipmentProductItemBeans"][i].numberImport +"</span> "+data["result"]["unit"].unitName+"</td>";
								
				if(data["result"]["equipmentProductItemBeans"][i].serialNo){
					var columnProductNumberImport = '<td><div class="input-group">\
						<input type="number" class="form-control worksheetViewDisableDom quantity" '+disabledNum+' value="1">\
						<div class="input-group-addon">'+data["result"]["unit"].unitName+'</div>\
					</div><small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;' + balance + '&nbsp;' + data["result"]["unit"].unitName + '</b></small></td>';
				}else{
					var columnProductNumberImport = '<td><div class="input-group">\
						<input type="number" style="text-align: right;" class="form-control numberReqNotSN quantity" min="1" max="'+balance+'" '+disabledNum+' value="1">\
						<div class="input-group-addon">'+data["result"]["unit"].unitName+'</div>\
					</div><small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;' + balance + '&nbsp;' + data["result"]["unit"].unitName + '</b></small></td>';
				}
				
				var columnFree = '<td><center>\
					<label class="custom-control custom-checkbox">\
						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="'+ data["result"]["equipmentProductItemBeans"][i].salePrice +'" > \
						<span class="custom-control-indicator"></span>\
						<span class="custom-control-description"></span>\
					</label>\
					</center></td>';
					
				var columnLend = "";
					
				if(disabled != null && disabled != "") {
					columnLend = '<td><center style="display: none;">\
						<label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend" value="'+ data["result"]["equipmentProductItemBeans"][i].salePrice +'" '+disabled+'> \
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
						</label>\
						</center></td>';
				} else {
					columnLend = '<td><center>\
						<label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend" value="'+ data["result"]["equipmentProductItemBeans"][i].salePrice +'" '+disabled+'> \
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
						</label>\
						</center></td>';
				}
				
				var columnProductPrice = '<td><input class="form-control worksheetViewDisableDom salePrice calculate" type="number" min="1" value="'+ data["result"]["equipmentProductItemBeans"][i].salePrice +'"/></td>';
				
				var columnProductTotalPrice = '<td><input disabled class="form-control worksheetViewDisableDom totalPrice" type="number" min="1" value="'+ data["result"]["equipmentProductItemBeans"][i].salePrice +'"/></td>';
				
				var columnAction = "<td> <a class='run-swal cursor-p removeProductItemSetup'> <span class='ti-trash' data-toggle='tooltip' data-placement='bottom' title='' aria-describedby='tooltip128894'></span></a> <input type='hidden' value='" + data["result"]["equipmentProductItemBeans"][i].id + "' /> <input type='hidden' value='" + data["result"]["equipmentProductItemBeans"][i].equipmentProductBean.id + "' /> <input type='hidden' class='requesterId' value='"+requesterId+"'/> </td>";
					
				rows = rows + startRow + columnOrderCount;
				rows = rows + columnProductCode + columnSerial + columnProductNumberImport + columnFree + columnLend + columnProductPrice + columnProductTotalPrice + columnAction;
// 			}
// 			}
				}
			});
			
			$('#tableEquipmentProductSetup > tbody').append(rows);

			$('#addEquipmentProductItem_setup').modal('hide');
			
			// เรียงลำดับ
			$('#tableEquipmentProductSetup > tbody tr').each(function (i) {
			     $(this).find('.no-item').text((i + 1));
			});
			
			autoInsertAndDeleteDataSetup();
			
		}

	}
	
// 	function appendToListInternetProduct_setup(){
// 		console.log("appendToListInternetProduct_setup");
// 		var radioInternetProduct = $("input:radio[name ='radioInternetProduct']:checked").val();

// 		if('' != dataInternetProduct){
// 			var data = dataInternetProduct;
// 			var rows = "";
// 			var no = 1;

// 			for(var i=0;i<data["result"]["internetProductBeanItems"].length;i++){
				
// 				if(radioInternetProduct == data["result"]["internetProductBeanItems"][i].id){
// 				var startRow = "<tr class='trNewItem'>";
// 				var endRow = "</tr>";
// 				var disabled = "";
				
// 				var columnOrderCount = "<td><center><span class='no-item'>"+ (no++) +"</span><input type='hidden' value='"+ data["result"].type +"' /><center></td>";
				
// 				var columnProductName = "<td>"+ data["result"].productName +"<br><font style='color: gray;'><a href='${pageContext.request.contextPath}/productorderinternetproduct/detail/" + data["result"].id + "' target='_blank'>" + data["result"].productCode + "</a></font></td>";
// // 				var columnProductCode = "<td style='vertical-align: middle;'><center>"+ data["result"].productCode +"</center></td>";
				
// 				var columnSerial = '<td><center> </center></td>'
				
// 				var columnProductNumberImportTextBox = '<td><div class="input-group">\
// 								<input type="number" class="form-control worksheetViewDisableDom quantity calculate" value="1" disabled >\
// 								<div class="input-group-addon">user</div>\
// 							</div></td>';	
				
// 				var columnFree = '<td><center>\
// 					<label class="custom-control custom-checkbox">\
// 						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" disabled > \
// 						<span class="custom-control-indicator"></span>\
// 						<span class="custom-control-description"></span>\
// 					</label>\
// 					</center></td>';
					
// 				var columnLend = '<td><center>\
// 					<label class="custom-control custom-checkbox">\
// 						<input type="checkbox" class="custom-control-input check-box-lend" disabled > \
// 						<span class="custom-control-indicator"></span>\
// 						<span class="custom-control-description"></span>\
// 					</label>\
// 					</center></td>';
				
// 				var columnProductPrice = '<td><input disabled class="form-control worksheetViewDisableDom salePrice calculate" type="number" min="1" value="0"/></td>';
				
// 				var columnProductTotalPrice = '<td><input disabled  class="form-control worksheetViewDisableDom totalPrice" type="number" min="1" value="0"/></td>';
				
// 				var columnAction = '<td style="vertical-align: middle;">\
// 					<a class="run-swal cursor-p removeProductItemSetup"><span class="ti-trash"\
// 						data-toggle="tooltip" data-placement="bottom" title=""\
// 						data-original-title="ลบ"\
// 						aria-describedby="tooltip128894"></span></a>\
// 				<input class="hidden_item_id" type="hidden" value="'+data["result"]["internetProductBeanItems"][i].id+'">\
// 				<input  type="hidden" value="'+data["result"].id+'"></td>';
					
// 				rows = rows + startRow + columnOrderCount;
// 				rows = rows + columnProductName + columnSerial + columnProductNumberImportTextBox + columnFree + columnLend + columnProductPrice + columnProductTotalPrice + columnAction;
// 				}
// 			}
			
// 			$('#tableEquipmentProductSetup > tbody').append(rows);

// 			$('#addInternetProductItem_setup').modal('hide');
			
// 			// เรียงลำดับ
// 			$('#tableEquipmentProductSetup > tbody tr').each(function (i) {
// 			     $(this).find('.no-item').text((i + 1));
// 			});
			
// 			autoInsertAndDeleteDataSetup();
			
// 		}

// 	}
	
	
	function appendServiceToListProduct_setup(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetlist/loadServiceProductWithId/"+id,
			dataType : 'json',
			async: false,
			success : function(data) {
				if(data["error"] == false){
					console.log(data);
					
					var rows = "";
					var startRow = "<tr class='trNewItem'>";
					var endRow = "</tr>";
					
					var columnOrderCount = "<td><center><span class='no-item'></span><input type='hidden' value='"+ data["result"].type +"' /><center></td>";
// 					var columnProductCode = "<td style='vertical-align: middle;'><center>"+ data["result"].productCode +"</center></td>";
					var columnSerial = "<td></td>";
					var columnProductName = "<td>"+ data["result"].productName +"</td>";
					
					var columnProductNumberImportTextBox = '<td><div class="input-group">\
									<input type="number" class="form-control worksheetViewDisableDom numberReqNotSN numberService quantity" value="1"\
									min="1" >\
									<div class="input-group-addon">'+data["result"]["unit"].unitName+'</div>\
								</div></td>';	
					
					var columnFree = '<td><center>\
						<label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="'+ data["result"].price +'" > \
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
						</label>\
						</center></td>';
						
					var columnLend = '<td><center style="display: none;">\
						<label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input check-box-lend" value="'+ data["result"].price +'" disabled> \
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
						</label>\
						</center></td>';
							
					var columnProductPrice = '<td><input class="form-control worksheetViewDisableDom salePrice calculate" type="number" min="1" value="'+ data["result"].price +'"/></td>';
					
					var columnProductTotalPrice = '<td><input disabled class="form-control worksheetViewDisableDom totalPrice" type="number" min="1" value="'+ data["result"].price +'"/></td>';
					
					var columnAction = '<td style="vertical-align: middle;">\
						<a class="run-swal cursor-p removeProductItemSetup"><span class="ti-trash"\
							data-toggle="tooltip" data-placement="bottom" title=""\
							data-original-title="ลบ"\
							aria-describedby="tooltip128894"></span></a>\
							<input class="hidden_item_id" type="hidden" value="0">\
							<input  type="hidden" value="'+data["result"].id+'"></td>';
						
					rows = rows + startRow + columnOrderCount;
					rows = rows + columnProductName + columnSerial + columnProductNumberImportTextBox + columnFree + columnLend + columnProductPrice + columnProductTotalPrice + columnAction;
					
					$('#tableEquipmentProductSetup > tbody').append(rows);

					$('#addEquipmentProduct_setup').modal('hide');
					
					// เรียงลำดับ
					$('#tableEquipmentProductSetup > tbody tr').each(function (i) {
					     $(this).find('.no-item').text((i + 1));
					});
					autoInsertAndDeleteDataSetup();
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




