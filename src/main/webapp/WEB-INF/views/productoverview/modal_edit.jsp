<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="editStock" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<i class="ti-pencil-alt"></i> แก้ไขสต๊อก
				</h4>
			</div>
			<form id="formUpdateProductoverview">
			<div class="modal-body">
				<div class="row">
				<div class="form-group">
					<div class="col-md-12">
						<label for=""><b>ชื่อสต๊อกสินค้า<span class="text-red">
									*</span></b></label> <input type="hidden" id="stockId" value="" /> <input
							type="text" id="stockName" class="form-control" name="stockName">
							<div class="messages"></div>
					</div>
					</div>
				</div>
				<div class="row">
				<div class="form-group">
					<div class="col-md-12">
						<label for=""><b>สังกัดบริษัท<span class="text-red">
									*</span></b></label> <select class="form-control" id="company" name="">
							<c:forEach var="companys" items="${companys}" varStatus="i">
								<option value="${companys.id}">${companys.companyName}</option>
							</c:forEach>
						</select>
						<div class="messages"></div>
					</div>
					</div>
				</div>
				<div class="row mt05">
				<div class="form-group">
					<div class="col-md-12">
						<label><b>รายละเอียดสต๊อกสินค้า<span class="text-red">
									*</span></b></label>
						<div class="input-field">
							<textarea class="form-control" id="stockDetail" rows="3" name="stockDetail"></textarea>
							<div class="messages"></div>
						</div>
					</div>
				</div>
				</div>
				<div class="row mt05">
				<div class="form-group">
					<div class="col-md-12">
						<label><b>ที่อยู่สต๊อกสินค้า<span class="text-red">
									*</span></b></label>
						<div class="input-field">
							<textarea class="form-control" id="stockAddress" rows="3" name="stockAddress"></textarea>
							<div class="messages"></div>
						</div>
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
	function editStock(id) {
		$
				.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/productoverview/get/json/"
							+ id,
					dataType : 'json',
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							$('#stockId').val(data["result"]["id"]);
							$('#stockName').val(data["result"]["stockName"]);
							$('#stockDetail')
									.val(data["result"]["stockDetail"]);
							$('#stockAddress').val(
									data["result"]["address"]["detail"]);

							var companyId = data["result"]["company"]["id"];
							$('#company').val(companyId);
							$('#editStock').modal('show');
							
							$(".error").remove();					
							var constraintsEdit = {
								"stockName" : {
									presence : {
										message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
									}
								},
								"stockDetail" : {
									presence : {
										message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
									}
								},
								"stockAddress" : {
									presence : {
										message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
									}
								}
// 								"company" : {
// 									presence : {
// 										message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
// 									}
// 								}
							};

							// Hook up the form so we can prevent it from being posted
							var formEdit = document.querySelector("form#formUpdateProductoverview");
							formEdit.addEventListener("submit", function(ev) {
								console.log(formEdit);
								ev.preventDefault();
								var errors = validate(formEdit, constraintsEdit);
								// then we update the form to reflect the results
								showErrors(formEdit, errors || {});
								if (!errors) {
									updateStock();
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
	function updateStock() {
		//generate bean
		var stockBean = generateEditStockJson();
		// 		console.log(stockBean);
		//validate basic
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productoverview/update",
			dataType : 'json',
			data : JSON.stringify(stockBean),
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

	function generateEditStockJson() {
		var stockBean = {};

		var companyBean = {};
		companyBean.id = $('#company').val();
		stockBean.company = companyBean;

		var addressBean = {};
		addressBean.detail = $('#stockAddress').val();
		stockBean.address = addressBean;

		stockBean.id = $('#stockId').val();
		stockBean.stockName = $('#stockName').val();
		stockBean.stockDetail = $('#stockDetail').val();

		return stockBean;
	}
</script>

