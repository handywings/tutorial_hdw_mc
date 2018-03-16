<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


	<div class="modal fade" id="editOrg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขข้อมูลตำแหน่งงาน</h4>
			</div>
				<form id="formEditPosition">
				
				<div class="modal-body">
					<div class="row">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ชื่อตำแหน่งงาน<span
										class="text-red"> *</span></b></label> <input type="hidden"
									id="positionId" value="" /> <input type="text"
									class="form-control" id="edit-positionName"
									name="edit-positionName" aria-describedby="emailHelp" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>สังกัดบริษัท<span class="text-red"> *</span></b></label>
						<select class="form-control" id="edit-company">
						<c:forEach var="companys" items="${companys}" varStatus="i">
							<option value="${companys.id}" >${companys.companyName}</option>
						</c:forEach>
						</select>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>คำอธิบาย<span
										class="text-red"> *</span>
								</b></label>
								<textarea class="form-control" id="edit-desctiption"
									name="edit-desctiption" rows="3">ช่าง</textarea>
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

	function editPosition(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/position/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#positionId').val(data["result"]["id"]);
					$('#edit-positionName').val(data["result"]["positionName"]);
					$('#edit-desctiption').val(data["result"]["desctiption"]);
					
					var companyId = data["result"]["company"]["id"];
					$('#edit-company').val(companyId);
					$('#editOrg').modal('show');
					
					$(".error").remove();					
						var constraintsEdit = {
							"edit-positionName" : {
								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							"edit-desctiption" : {
								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							}
						};

						// Hook up the form so we can prevent it from being posted
						var formEdit = document.querySelector("form#formEditPosition");
						formEdit.addEventListener("submit", function(ev) {
							
							ev.preventDefault();
							var errors = validate(formEdit, constraintsEdit);
							// then we update the form to reflect the results
							showErrors(formEdit, errors || {});
							if (!errors) {
								updatePosition();
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
	function updatePosition(){
		//generate bean
		var positionBean = generateEditPositionJson();
		//validate basic
		console.log(positionBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/position/update",
			dataType : 'json',
			data : JSON.stringify(positionBean),
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
	
	function generateEditPositionJson(){
		var companyBean = {};
		companyBean.id = $('#edit-company').val();
		
		var positionBean = {};
		positionBean.id = $('#positionId').val();
		positionBean.positionName = $('#edit-positionName').val();
		positionBean.desctiption = $('#edit-desctiption').val();
		
		positionBean.company = companyBean;
		
		return positionBean;
	}


</script>

