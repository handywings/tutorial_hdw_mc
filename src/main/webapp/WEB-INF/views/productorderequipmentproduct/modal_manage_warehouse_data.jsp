<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<style>

</style>

	<div id="manageStock" class="modal fade" tabindex="-1" role="dialog"
			aria-labelledby="myLargeModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">จัดการข้อมูลคลังสินค้า</h4>
					</div>
					<div class="modal-body">
						<div class="row mt05">
							<div class="col-md-12">
								<select class="form-control" id="stockManage"
									name="managetype">
									<option disabled="disabled" selected="selected">---
										เลือกการจัดการสินค้า ---</option>
									<option value="export">นำสินค้าออก</option>
								</select>
							</div>
						</div>

						<div id="div_stockType" class="div_stockType" style="display: none;">
							<div class="row mt15">
								<div class="col-md-12">
									<div class="table-responsive">
										<table class="table table-bordered mb-0 table-fixed table-hover" id="table-1">
											<thead class="thead-default">
												<tr>
													<th style="width: 80px;"><center>เลือก</center></th>
													<th><center>รหัสสินค้า </center></th>
													<th><center>Serial Number</center></th>
												</tr>
											</thead>
											<tbody id="table_serial_number_tbody">
											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="row mt15">
								<div class="col-md-4">
									<h5>
									<input type="hidden" id="productCode" >
										<label for="email"><b>ย้ายไปยังคลังสินค้า</b> <span
											class="text-red">*</span> </label>
									</h5>
								</div>
								<div class="col-md-8">
									<h5></h5>
									<select class="form-control bg-form" id="toStock"
										name="toStock">
										<option value="0" selected="selected">นำสินค้าออกโดยไม่ระบุคลังสินค้า</option>
										<c:forEach var="stocks" items="${stocks}" varStatus="i">
											<option value="${stocks.id}" ${selected}>${stocks.stockName}
													(${stocks.company.companyName})</option>
										</c:forEach>
									</select>
								</div>
							</div>							
						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
						<button type="button" class="btn btn-primary" onclick="exportProduct();">บันทึก</button>
					</div>
				</div>
			</div>
		</div>
		
<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">

	$("#stockManage").change(function() {
		$("#div_stockType").show();
	});

	function openDialogManage(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderequipmentproduct/loadEquipmentProduct/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				console.log(data);
				if(data["error"] == false){
					var html = '';
					$.each( data.result.equipmentProductItemBeans , function( key, value ) {
						html += '<tr>\
								<td><center>\
								<label class="custom-control custom-checkbox">\
								<input type="checkbox" class="custom-control-input check_product" value="'+value.id+'" > <span\
									class="custom-control-indicator"></span> <span\
									class="custom-control-description"></span>\
								</label>\
										</center></td>\
									<td><center>'+data.result.productCode+'</center></td>\
									<td><center>'+value.serialNo+'</center></td>\
								</tr>';
					});		
					
					$('#table_serial_number_tbody').html(html);
					$('#productCode').val(data.result.productCode);
					
					$('#manageStock').modal('show');
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
	
	//update
	function exportProduct(){
		//generate bean
		var equipmentProductBean = generateProductJson();
		//validate basic
		console.log(equipmentProductBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderequipmentproduct/export",
			dataType : 'json',
			data : JSON.stringify(equipmentProductBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					window.location.reload();
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
	
	function generateProductJson(){

		var equipmentProductBean = {};
		var equipmentProductItemMap = [];
		var equipmentProductItemBeans = {};
		var stockBeans = {};
		
		$('.check_product').each(function () {
			var sThisVal = (this.checked ? $(this) : "");
			if(sThisVal){
// 				console.log(sThisVal.val());
				equipmentProductItemBeans.id = sThisVal.val();
				equipmentProductItemMap.push(equipmentProductItemBeans);
				equipmentProductItemBeans = {};
			}
		});
		equipmentProductBean.equipmentProductItemBeans = equipmentProductItemMap;
		
		
		stockBeans.id = $('#toStock').val();
		equipmentProductBean.stock = stockBeans;
		equipmentProductBean.productCode = $('#productCode').val();
		
		
		return equipmentProductBean;
	}


</script>

