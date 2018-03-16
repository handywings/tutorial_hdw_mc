<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

							<div class="modal fade" id="editArea" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" align="left">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขเขตชุมชน
												/ โครงการ</h4>
										</div>
										<form id="formUpdateZone">
										<div class="modal-body">
											<div class="row">
												<div class="col-md-12">
												<div class="form-group">
													<label for="exampleInputEmail1"><b>ชื่อเขตชุมชน
															/ โครงการ<span class="text-red"> *</span></b></label> <input type="text" class="form-control"
														id="zoneName" aria-describedby="emailHelp" name="zoneName"
														value="	">
												<input type="hidden" id="zoneId" value="" />
												<div class="messages"></div>
												</div>
												</div>
											</div>
											<div class="row mt05">
											<div class="form-group">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>เขตพื้นที่<span class="text-red"> *</span></b></label>
													<textarea class="form-control" id="zoneDetail" name="zoneDetail"
														rows="3"></textarea>
													<div class="messages"></div>
												</div>
												</div>
											</div>
											<input type="hidden" value="ทั่วไป" id="zoneType">
											
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

	function editZone(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/zone/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#zoneId').val(data["result"]["id"]);
					$('#zoneName').val(data["result"]["zoneName"]);
					$('#zoneDetail').val(data["result"]["zoneDetail"]);
					$('#zoneType').val(data["result"]["zoneType"]);

					$('#editArea').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
						"zoneName" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						},
						"zoneDetail" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						}
					};

					// Hook up the form so we can prevent it from being posted
					var formEdit = document.querySelector("form#formUpdateZone");
					formEdit.addEventListener("submit", function(ev) {
						
						ev.preventDefault();
						var errors = validate(formEdit, constraintsEdit);
						// then we update the form to reflect the results
						showErrors(formEdit, errors || {});
						if (!errors) {
							updateZone();
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
	function updateZone(){
		//generate bean
		var zoneBean = generateEditZoneJson();
		//validate basic
		console.log(zoneBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/zone/update",
			dataType : 'json',
			data : JSON.stringify(zoneBean),
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
	
	function generateEditZoneJson(){
		var zoneBean = {};

		zoneBean.id = $('#zoneId').val();
		zoneBean.zoneName = $('#zoneName').val();
		zoneBean.zoneDetail = $('#zoneDetail').val();
		zoneBean.zoneType = $('#zoneType').val();
		
		return zoneBean;
	}



</script>

