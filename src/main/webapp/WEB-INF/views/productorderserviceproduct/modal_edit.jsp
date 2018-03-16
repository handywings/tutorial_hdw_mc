<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


	<div class="modal fade" id="editDailog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขข้อมูลค่าแรง / ค่าบริการ</h4>
			</div>
			<form id="formUpdateProductOrderService">
				<div class="modal-body">
					<div class="row">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ชื่อบริการ<span class="text-red"> *</span></b></label>
								<input type="hidden" id="serviceChargeId" value="" />
								<input type="text" class="form-control" id="serviceChargeName" name="serviceChargeName" aria-describedby="emailHelp" value="">
								<div class="messages"></div>
							</div>
						</div>	
					</div>
					<div class="row">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>คลังสินค้า<span class="text-red"> *</span></b></label>
								<select class="form-control" id="stockId" name="stockId">
									<c:forEach var="stocks" items="${stocks}" varStatus="i">
										<option value="${stocks.id}" >${stocks.stockName} (${stocks.company.companyName})</option>
									</c:forEach>
								</select>
								<div class="messages"></div>
							</div>
						</div>	
					</div>
					<div class="row">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ค่าบริการ<span class="text-red"> *</span></b></label>
								<input type="number" class="form-control" id="price" aria-describedby="emailHelp" value="">
							</div>
						</div>	
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">บันทึก</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">

	function editServiceProduct(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/productorderserviceproduct/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				console.log(data);
				if(data["error"] == false){
					$('#serviceChargeId').val(data["result"]["id"]);
					$('#serviceChargeName').val(data["result"]["productName"]);
					$('#price').val(data["result"]["price"]);
					
					var stockId = data["result"]["stock"]["id"];
					$('#stockId').val(stockId);
					
					$('#editDailog').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
							serviceChargeName : {
								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							stockId : {
								presence : {
									message : "^กรุณาเลือกข้อมูล"
								}
							}
						};

						// Hook up the form so we can prevent it from being posted
						var formEdit = document.querySelector("form#formUpdateProductOrderService");
						formEdit.addEventListener("submit", function(ev) {
							
							ev.preventDefault();
							var errors = validate(formEdit, constraintsEdit);
							// then we update the form to reflect the results
							showErrors(formEdit, errors || {});
							if (!errors) {
								updateInternetProduct();
							}
						});


						// Hook up the inputs to validate on the fly
						var inputsEdit = document.querySelectorAll("input, textarea, select");
						for (var i = 0; i < inputsEdit.length; ++i) {
							inputsEdit.item(i).addEventListener("change", function(ev) {
								var errors = validate(formEdit, constraintsEdit) || {};
								showErrorsForInput(this, errors[this.name])
							});
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
	}
	
	//update
	function updateInternetProduct(){
		//generate bean
		var serviceProductBean = generateEditServiceProductBeanJson();
		//validate basic
		console.log(serviceProductBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderserviceproduct/update",
			dataType : 'json',
			data : JSON.stringify(serviceProductBean),
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
	
	function generateEditServiceProductBeanJson(){

		var serviceProductBean = {};
		serviceProductBean.id = $('#serviceChargeId').val();
		serviceProductBean.productName = $('#serviceChargeName').val();
		serviceProductBean.price = $('#price').val();
		
		var stockBean = {};
		stockBean.id = $('#stockId').val();
		
		serviceProductBean.stock = stockBean;
		
		return serviceProductBean;
	}


</script>

