<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

							<div class="modal fade" id="editServicePackageType" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" align="left">
								<div class="modal-dialog" role="document">

										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขประเภทสินค้าและบริการ</h4>
											</div>
											<form id="formUpdateServicepackagetype">
											<div class="modal-body">
												<div class="row mt05">
													<div class="form-group">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>ชื่อประเภทสินค้าและบริการ<span class="text-red"> *</span></b></label>
														<input type="text" class="form-control"
															id="packageTypeName" name="packageTypeName">
														<input type="hidden" id="packageTypeId" value="" />
														<div class="messages"></div>
													</div>
													</div>
												</div>
												<div class="row mt05">
													<div class="form-group">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>รายละเอียด<span class="text-red"> *</span></b></label>
														<textarea class="form-control" id="description" name="description"
															rows="3"></textarea>
															<div class="messages"></div>
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

	function editServicePackageType(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/servicepackagetype/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#packageTypeId').val(data["result"]["id"]);
					$('#packageTypeName').val(data["result"]["packageTypeName"]);
					$('#description').val(data["result"]["description"]);

					$('#editServicePackageType').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
						"packageTypeName" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						},
						"description" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						}
					};

					// Hook up the form so we can prevent it from being posted
					var formEdit = document.querySelector("form#formUpdateServicepackagetype");
					formEdit.addEventListener("submit", function(ev) {
						
						ev.preventDefault();
						var errors = validate(formEdit, constraintsEdit);
						// then we update the form to reflect the results
						showErrors(formEdit, errors || {});
						if (!errors) {
							updateServicePackageType();
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
	function updateServicePackageType(){
		//generate bean
		var servicePackageTypeBeans = generateEditServicePackageTypeJson();
		//validate basic
// 		console.log(servicePackageTypeBeans);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/servicepackagetype/update",
			dataType : 'json',
			data : JSON.stringify(servicePackageTypeBeans),
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
	
	function generateEditServicePackageTypeJson(){
		var servicePackageTypeBeans = {};
		
		servicePackageTypeBeans.id = $('#packageTypeId').val();
		servicePackageTypeBeans.packageTypeName = $('#packageTypeName').val();
		servicePackageTypeBeans.description = $('#description').val();

		return servicePackageTypeBeans;
	}



</script>

