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
				<h4 class="modal-title" id="myModalLabelEdit"></h4>
			</div>
			<form id="formUpdateCompany">
			<div class="modal-body">			
				<input type="hidden" id="companyId" value="" />
				
<!-- 					<div class="row mt05"> -->
<!-- 						<div class="form-group" id="div-dropify" > -->
							
<!-- 						</div> -->
<!-- 					</div> -->
				
					<div class="row">
						<div class="form-group">
							<div class="col-md-12 control-label">
								<label for="edit-companyName"><b>ชื่อบริษัท<span
										class="text-red"> *</span></b></label> <input type="text"
									class="form-control" id="edit-companyName"
									name="edit_companyName" aria-describedby="emailHelp" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ที่อยู่<span
										class="text-red"> *</span></b></label>
								<textarea class="form-control" id="edit-address" rows="3"
									name="edit_address"></textarea>
								<div class="messages"></div>
							</div>

						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>โทร<span
										class="text-red"> *</span></b></label> <input id="edit-mobile" type="text"
									name="edit_mobile" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>Email<span
										class="text-red"> *</span></b></label> <input id="edit-email" type="email"
									name="edit_email" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>Fax<span
										class="text-red"> *</span></b></label> <input id="edit-fax" type="text"
									name="edit_fax" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>เลขที่ผู้เสียภาษี<span
										class="text-red"> *</span></b></label> <input type="text" type=text
									name="edit_taxId" id="edit-taxId"
									onkeypress="return isNumberKey(event);" class="form-control"
									value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>ภาษีมูลค่าเพิ่ม<span
										class="text-red"> *</span></b></label>
								<div class="input-group">
									<input type="number" class="form-control" value="" id="edit-vat" name="edit_vat">
									<div class="input-group-addon">%</div>
								</div>
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>Inv เครดิต<span
										class="text-red"> *</span></b></label>
								<div class="input-group">
									<input type="number" class="form-control" value=""
										id="edit-invCredit" name="edit_invCredit">
									<div class="input-group-addon">วัน</div>
								</div>
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
			url : "${pageContext.request.contextPath}/company/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#companyId').val(data["result"]["id"]);
					$('#edit-companyName').val(data["result"]["companyName"]);
					$('#edit-address').val(data["result"]["address"]["detail"]);
					$('#edit-mobile').val(data["result"]["contact"]["mobile"]);
					$('#edit-email').val(data["result"]["contact"]["email"]);
					$('#edit-fax').val(data["result"]["contact"]["fax"]);
					$('#edit-taxId').val(data["result"]["taxId"]);
					$('#edit-vat').val(data["result"]["vat"].toString());
					$('#edit-invCredit').val(data["result"]["invCredit"]);
					
					// ref : https://github.com/JeremyFagis/dropify

					
					var input = '<input type="file" id="edit-input-file-now-custom-2" \
						class="dropify" data-default-file="'+data["result"]["logo"]+'" \
						data-height="210" />\
					<p></p>\
					<center>\
						<b>โลโก้บริษัท</b>\
					</center>\
					<div class="messages"></div>';
					
					$('#div-dropify').html(input);
	                
					var drEvent = $('.dropify').dropify();
// 					drEvent.on('dropify.beforeClear', function(event, element){
// 					    return confirm("Do you really want to delete \"" + element.filename + "\" ?");
// 					});

					
					if(!parent){
						$('#myModalLabelEdit').html("<i class='ti-pencil-alt'></i> "+titleModalEditSubCompany);
					}else{
						$('#myModalLabelEdit').html("<i class='ti-pencil-alt'></i> "+titleModalEditMaterCompany);
					}
					$('#editOrg').modal('show');
					
					$(".error").remove();					
					var constraintsEdit = {
							edit_companyName : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_address : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_mobile : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_email : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								},
								email : {
									message : "^กรุณากรอก E-mail ให้ถูกต้อง"
								}
							},
							edit_fax : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_taxId : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_vat : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_taxId : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								}
							},
							edit_invCredit : {

								presence : {
									message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
								},
								numericality : {
									onlyInteger : true,
									greaterThanOrEqualTo : 0
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
// 		var fileInput = document.getElementById('edit-input-file-now-custom-2');
// 		if(fileInput.files.length > 0){
// 			for (i = 0; i < fileInput.files.length; i++) {
// 				formData.append("file_company", fileInput.files[i]);
// 			}
// 		}else{
// 			formData.append("file_company", new File([""], "noPictureCompany"));
// 		}

		formData.append("file_company", new File([""], "noPictureCompany"));

		var companyBean = generateEditCompanyJson();
		companyBean = JSON.stringify(companyBean);
		formData.append("companyBean", companyBean);

		//console.log(companyBean);
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/company/update",
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
		
		var companyBean = {};
		companyBean.id = $('#companyId').val();
		companyBean.companyName = $('#edit-companyName').val();
		companyBean.taxId = $('#edit-taxId').val();
		companyBean.invCredit = $('#edit-invCredit').val();
		companyBean.vat = $('#edit-vat').val();
		//address bean
		var addressBean = {};
		addressBean.detail = $('#edit-address').val();
		//contact bean
		var contactBean = {};
		contactBean.mobile = $('#edit-mobile').val();
		contactBean.email = $('#edit-email').val();
		contactBean.fax = $('#edit-fax').val();
		//company bean
		companyBean.address = addressBean;
		companyBean.contact = contactBean;	
		
		return companyBean;
	}
</script>


