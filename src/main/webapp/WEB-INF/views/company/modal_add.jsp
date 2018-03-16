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
					<i class="ti-plus"></i> เพิ่มข้อมูลบริษัทย่อย
				</h4>
			</div>
			<form id="formAddCompany">
				<div class="modal-body">
					<input type="hidden" id="parentId" value="" />
					
<!-- 					<div class="row mt05"> -->
<!-- 						<div class="form-group" id="div-dropify" > -->
<!-- 								<input type="file" id="add-input-file-now-custom-2" -->
<%-- 							class="dropify" data-default-file="${defaultImage}"  --%>
<!-- 							data-height="210" /> -->
<!-- 						<p></p> -->
<%-- 						<center> --%>
<!-- 							<b>โลโก้บริษัท</b> -->
<%-- 						</center> --%>
<!-- 						<div class="messages"></div> -->
<!-- 						</div> -->
<!-- 					</div> -->
					
					<div class="row">
						<div class="form-group">
							<div class="col-md-12 control-label">
								<label for="add-companyName"><b>ชื่อบริษัท<span
										class="text-red"> *</span></b></label> <input type="text"
									class="form-control" id="add-companyName"
									name="add_companyName" aria-describedby="emailHelp" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ที่อยู่<span
										class="text-red"> *</span></b></label>
								<textarea class="form-control" id="add-address" rows="3"
									name="add_address"></textarea>
								<div class="messages"></div>
							</div>

						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>โทร<span
										class="text-red"> *</span></b></label> <input id="add-mobile" type="text"
									name="add_mobile" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>Email<span
										class="text-red"> *</span></b></label> <input id="add-email" type="email"
									name="add_email" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>Fax<span
										class="text-red"> *</span></b></label> <input id="add-fax" type="text"
									name="add_fax" class="form-control" value="">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6">
								<label for="exampleInputEmail1"><b>เลขที่ผู้เสียภาษี<span
										class="text-red"> *</span></b></label> <input type="text" type=text
									name="add_taxId" id="add-taxId"
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
									<input type="number" class="form-control" value="" id="add-vat" name="add_vat">
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
										id="add-invCredit" name="add_invCredit">
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
	function addCompany(parent) {
		$('#parentId').val(parent);
		$('#addOrg').modal('show');
	}

	//update
	function saveCompany() {
		//generate bean
		
		var formData = new FormData();
// 		var fileInput = document.getElementById('add-input-file-now-custom-2');
// 		if(fileInput.files.length > 0){
// 			for (i = 0; i < fileInput.files.length; i++) {
// 				formData.append("file_company", fileInput.files[i]);
// 			}
// 		}else{
// 			formData.append("file_company", new File([""], "noPictureCompany"));
// 		}

		formData.append("file_company", new File([""], "noPictureCompany"));
		
		var companyBean = generateAddCompanyJson();
		companyBean = JSON.stringify(companyBean);
		formData.append("companyBean", companyBean);
		
		//console.log(companyBean);
		//validate basic

		$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/company/save",
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

	function generateAddCompanyJson() {

		var companyBean = {};
		companyBean.parent = $('#parentId').val();
		companyBean.companyName = $('#add-companyName').val();
		companyBean.taxId = $('#add-taxId').val();
		companyBean.invCredit = $('#add-invCredit').val();
		companyBean.vat = $('#add-vat').val();
		//address bean
		var addressBean = {};
		addressBean.detail = $('#add-address').val();
		//contact bean
		var contactBean = {};
		contactBean.mobile = $('#add-mobile').val();
		contactBean.email = $('#add-email').val();
		contactBean.fax = $('#add-fax').val();
		//company bean
		companyBean.address = addressBean;
		companyBean.contact = contactBean;

		return companyBean;
	}
</script>

<script>
	var constraints = {
		add_companyName : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_address : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_mobile : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_email : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			},
			email : {
				message : "^กรุณากรอก E-mail ให้ถูกต้อง"
			}
		},
		add_fax : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_taxId : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_vat : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_taxId : {

			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		add_invCredit : {

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
			saveCompany();
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


