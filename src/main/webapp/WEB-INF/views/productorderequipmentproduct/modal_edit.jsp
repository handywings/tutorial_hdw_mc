<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="editDailog" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">แก้ไขข้อมูลสินค้าประเภทอุปกรณ์</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-6">
						<input type="hidden" id="hiddenEquipmentProductId" />
						<b>รหัสสินค้า</b><br> <input type="text"
							id="new_masterEquipmentProductCode" class="form-control">
					</div>
					<div class="col-md-6">
						<b>ชื่อสินค้า</b><br> <input type="text"
							id="new_masterEquipmentProductName" class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<b>ประเภทสินค้าและบริการ</b><br>
						<div class="">
							<select class="form-control" id="new_equipmentProductType">
								<option value="">--- เลือก ---</option>
								<c:forEach items="${epcSearchBeans}" var="epcSearchBean"
									varStatus="i">
									<option value="${epcSearchBean.id}">
										${epcSearchBean.equipmentProductCategoryName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-6">
						<b>Supplier</b><br> <input type="text" id="new_supplier"
							class="form-control">
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<b>คลังสินค้า</b><br>
						<div class="">
							<select class="form-control" id="new_equipmentStock">
								<option value="">--- เลือก ---</option>
								<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
									<option value="${stockBean.id}">
										${stockBean.stockName} (${stockBean.company.companyName})</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-6">
						<b>หน่วยนับ</b><br>
						<div class="">
							<select class="form-control" id="new_equipmentUnit">
								<option value="">--- เลือก ---</option>
								<c:forEach items="${unitBeans}" var="unitBean" varStatus="i">
									<option value="${unitBean.id}">${unitBean.unitName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<b>จำนวนน้อยสุดในคลังสินค้า</b><br>
						<div class="controls form-inline">
							<label class="custom-control custom-checkbox"> <input
								type="checkbox" id="checkboxIsMinimum"
								class="custom-control-input" value=""> <span
								class="custom-control-indicator"></span> <span
								class="custom-control-description"></span>
							</label> <input type="number" id="new_masterEquipmentProductMinumun"
								  min="1" class="form-control">
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success"
					onclick="updateEquipmentProduct();">บันทึก</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		
		$('#checkboxIsMinimum').click(function(){
		    if($(this).is(':checked')){
		    	$('#new_masterEquipmentProductMinumun').removeAttr('disabled');
		    } else {
		    	//$('#new_masterEquipmentProductMinumun').val("");
		    	$('#new_masterEquipmentProductMinumun').attr('disabled','disabled');
		    }
		});
		
	});

	function openDialogEdit(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderequipmentproduct/loadEquipmentProduct/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#hiddenEquipmentProductId').val(data["result"]["id"]);
					$('#new_masterEquipmentProductCode').val(data["result"]["productName"]);
					$('#new_masterEquipmentProductName').val(data["result"]["productCode"]);
					$('#new_supplier').val(data["result"]["supplier"]);
					$("#new_equipmentProductType").val(data["result"]["productCategory"].id);
					$("#new_equipmentStock").val(data["result"]["stock"].id);
					$("#new_equipmentUnit").val(data["result"]["unit"].id);
					
					if(data["result"]["isminimum"] == true){
						$("#checkboxIsMinimum").prop( "checked", true );
						$('#new_masterEquipmentProductMinumun').removeAttr('disabled');
						$('#new_masterEquipmentProductMinumun').val(data["result"]["minimumNumber"]);
					}else{
						$("#checkboxIsMinimum").prop( "checked", false );
						$('#new_masterEquipmentProductMinumun').attr('disabled','disabled');
					}
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
		$('#editDailog').modal('show');
	}
	
	function updateEquipmentProduct(){
		$('.preloader').modal('show');
		//create bean
		//equipment bean
		var equipmentProductBean = {};
		equipmentProductBean.id = $('#hiddenEquipmentProductId').val();
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
		
		//send update
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
					window.location.reload();
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

