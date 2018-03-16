<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
	
<div class="modal fade" id="editOrg" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabelEdit" align="left">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">
					<i class="ti-pencil-alt"></i> แก้ไขข้อมูลเอกสาร
				</h4>
			</div>
			<form id="formUpdateCompany">
			
			<div class="modal-body">
					<input type="hidden" id="edit_documentId" value="" />
					
					<div class="row mt05">
						<div class="form-group" id="div-dropify" >
						
						</div>
					</div>
					
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>เลขที่เอกสาร<span
										class="text-red"> *</span></b></label> <input id="edit_document_code" type="text"
									name="edit_document_code" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>ชื่อเอกสาร<span
										class="text-red"> *</span></b></label> <input id="edit_document_name" type="text"
									name="edit_document_name" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>สิทธิ์การเข้าใช้เอกสาร<span
										class="text-red"> *</span></b></label>
								<select class="form-control" id="edit_permission">
									<option value="A">ทำได้หมด</option>
									<option value="R">อ่านเท่านั้น</option>
								</select>
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>ประเภทเอกสาร<span
										class="text-red"> *</span></b></label>
								<select class="form-control" id="edit_document_type">
									<option value="1">Material approve</option>
									<option value="2">RFI</option>
									<option value="3">LTR</option>
									<option value="4">VO</option>
									<option value="5">Payment</option>
									<option value="6">Shop drawing</option>
									<option value="7">SI</option>
								</select>
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

	function editCompany(id, parent){
		
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/document/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#edit_documentId').val(data["result"]["id"]);
					$('#edit_document_code').val(data["result"]["document_code"]);
					$('#edit_document_name').val(data["result"]["document_name"]);
					
					$('#edit_permission').val(data["result"]["permission"]);
					$('#edit_document_type').val(data["result"]["document_type"]);
					
					// ref : https://github.com/JeremyFagis/dropify

					
					var input = '<center> \
							<b>เอกสาร</b> \
						</center> \
						<input type="file" id="edit-input-file-now-custom-2" \
						class="dropify" data-default-file="'+data["result"]["document_path"]+'" \
						data-height="210" />\
					<p></p>\
					<div class="messages"></div>';
					
					$('#div-dropify').html(input);
	                
					var drEvent = $('.dropify').dropify();
// 					drEvent.on('dropify.beforeClear', function(event, element){
// 					    return confirm("Do you really want to delete \"" + element.filename + "\" ?");
// 					});


					$('#editOrg').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
							edit_document_code : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_document_name : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							}
						};

						// Hook up the form so we can prevent it from being posted
						var formEdit = document.querySelector("form#formUpdateCompany");
						formEdit.addEventListener("submit", function(ev) {
							
							ev.preventDefault();
							var errors = validate(formEdit, constraintsEdit);
							// then we update the form to reflect the results
							showErrors(formEdit, errors || {});
							if (!errors) {
								updateCompany();
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
	function updateCompany(){
		var formData = new FormData();
		var fileInput = document.getElementById('edit-input-file-now-custom-2');
		if(fileInput.files.length > 0){
			for (i = 0; i < fileInput.files.length; i++) {
				formData.append("file_document", fileInput.files[i]);
			}
		}else{
			formData.append("file_document", new File([""], "noPictureDocument"));
		}
		var documentBean = generateEditCompanyJson();
		documentBean = JSON.stringify(documentBean);
		formData.append("documentBean", documentBean);

		//console.log(documentBean);
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/document/update",
				data : formData,
				type : "POST",
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false,
				async : false,
				cache : false,
				success : function(data) {
					window.location.reload();
				},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
	
	function generateEditCompanyJson(){
		var documentBean = {};
		
		documentBean.id = $('#edit_documentId').val();
		documentBean.document_code = $('#edit_document_code').val();
		documentBean.document_name = $('#edit_document_name').val();
		documentBean.permission = $('#edit_permission').val();
		documentBean.document_type = $('#edit_document_type').val();

		return documentBean;
	}
</script>


