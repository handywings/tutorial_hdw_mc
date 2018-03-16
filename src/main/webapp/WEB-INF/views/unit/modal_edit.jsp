<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

	<div class="modal fade" id="editUnit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><i class="ti-pencil-alt"></i> แก้ไขหน่วยนับ</h4>
				</div>
				<form id="formUpdateUnit">
				<div class="modal-body">
					<div class="form-group">
					<label for="exampleInputEmail1"><b>ชื่อหน่วยหน่วยนับ<span class="text-red"> *</span></b></label>
					<input type="text" class="form-control" id="unitName" aria-describedby="emailHelp" name="unitName">
					<input type="hidden" id="unitId" value="" />
					<div class="messages"></div>
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

	function editUnit(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/unit/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#unitId').val(data["result"]["id"]);
					$('#unitName').val(data["result"]["unitName"]);

					$('#editUnit').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
						"unitName" : {
							presence : {
								message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
							}
						}
					};

					// Hook up the form so we can prevent it from being posted
					var formEdit = document.querySelector("form#formUpdateUnit");
					formEdit.addEventListener("submit", function(ev) {
						
						ev.preventDefault();
						var errors = validate(formEdit, constraintsEdit);
						// then we update the form to reflect the results
						showErrors(formEdit, errors || {});
						if (!errors) {
							updateUnit();
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
	function updateUnit(){
		//generate bean
		var unitBean = generateEditUnitJson();
		//validate basic
// 		console.log(unitBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/unit/update",
			dataType : 'json',
			data : JSON.stringify(unitBean),
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
	
	function generateEditUnitJson(){
		var unitBean = {};

		unitBean.id = $('#unitId').val();
		unitBean.unitName = $('#unitName').val();
		
		return unitBean;
	}



</script>

