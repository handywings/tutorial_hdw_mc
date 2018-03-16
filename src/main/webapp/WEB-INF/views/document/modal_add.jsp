<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="addOrg" tabindex="-1" role="dialog"
	align="left">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">
					<i class="ti-plus"></i> เพิ่มข้อมูลเอกสาร
				</h4>
			</div>
			<form id="formAddCompany">
				<div class="modal-body">
					<input type="hidden" id="parentId" value="" />
					
					<div class="row mt05">
						<div class="form-group" id="div-dropify" >
						<center>
							<b>เอกสาร</b>
						</center>
								<input type="file" id="add-input-file-now-custom-2"
							class="dropify"
							data-height="210" />
						<p></p>
					
						<div class="messages"></div>
						</div>
					</div>
					
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>เลขที่เอกสาร<span
										class="text-red"> *</span></b></label> <input id="add_document_code" type="text"
									name="add_document_code" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>ชื่อเอกสาร<span
										class="text-red"> *</span></b></label> <input id="add_document_name" type="text"
									name="add_document_name" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>สิทธิ์การเข้าใช้เอกสาร<span
										class="text-red"> *</span></b></label>
								<select class="form-control" id="add_permission">
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
								<select class="form-control" id="add_document_type">
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
	function addCompany(parent) {
		$('#addOrg').modal('show');
	}

	//update
	function saveDocument() {
		//generate bean
		
		var formData = new FormData();
		var fileInput = document.getElementById('add-input-file-now-custom-2');
		if(fileInput.files.length > 0){
			for (i = 0; i < fileInput.files.length; i++) {
				formData.append("file_document", fileInput.files[i]);
			}
		}else{
			formData.append("file_document", new File([""], "noPictureDocument"));
		}
		var documentBean = generateAddDocumentJson();
		documentBean = JSON.stringify(documentBean);
		formData.append("documentBean", documentBean);
		
// 		console.log(documentBean);
		//validate basic

		$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/document/save",
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

	function generateAddDocumentJson() {

		var documentBean = {};
		documentBean.document_code = $('#add_document_code').val();
		documentBean.document_name = $('#add_document_name').val();
		documentBean.permission = $('#add_permission').val();
		documentBean.document_type = $('#add_document_type').val();

		return documentBean;
	}
</script>

<script>
	var constraints = {
			add_document_code : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
			add_document_name : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formAddCompany");
	form.addEventListener("submit", function(ev) {
		ev.preventDefault();
		handleFormSubmit(form);
	});

	function handleFormSubmit(form, input) {
		// validate the form aainst the constraints
		var errors = validate(form, constraints);
		// then we update the form to reflect the results
		showErrors(form, errors || {});
		if (!errors) {
			saveDocument();
		}
	}

	// Hook up the inputs to validate on the fly
	var inputs = document.querySelectorAll("input, textarea, select");
	for (var i = 0; i < inputs.length; ++i) {
		inputs.item(i).addEventListener("change", function(ev) {
			var errors = validate(form, constraints) || {};
			showErrorsForInput(this, errors[this.name])
		});
	}
</script>


