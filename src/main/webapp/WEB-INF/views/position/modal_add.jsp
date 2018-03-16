<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

							<div class="modal fade" id="addOrg" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" align="left">
								<form id="formAddPosition">
									<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title" id="myModalLabel"><i class="ti-plus"></i> เพิ่มข้อมูลตำแหน่งงาน</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="form-group">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>ชื่อตำแหน่งงาน<span class="text-red"> *</span></b></label>
													<input type="text" class="form-control"
														id="add-positionName" name="add-positionName" aria-describedby="emailHelp">
													<div class="messages"></div>
												</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>สังกัดบริษัท<span class="text-red"> *</span></b></label>
													<select class="form-control" id="add-company">
													<c:forEach var="companys" items="${companys}" varStatus="i">
														<option value="${companys.id}">${companys.companyName}</option>
													</c:forEach>
													</select>
												</div>
											</div>
											<div class="row mt05">
											<div class="form-group">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>คำอธิบาย<span class="text-red"> *</span></b></label>
													<textarea class="form-control" id="add-desctiption" name="add-desctiption"
														rows="3"></textarea>
														<div class="messages"></div>
												</div>
												</div>
											</div>
										</div>
										<div class="modal-footer">
									<button type="submit" class="btn btn-success" >บันทึก</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
										</div>
									</div>
								</div>
								</form>
							</div>

<script type="text/javascript">

	function addCompany(parent){
		$('#parentId').val(parent);
		$('#addOrg').modal('show');
	}
	
	//update
	function savePosition(){
		//generate bean
		var positionBean = generateAddPositionJson();
// 		console.log(positionBean);
		//validate basic
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/position/save",
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
	
	function generateAddPositionJson(){
		var companyBean = {};
		companyBean.id = $('#add-company').val();
		
		var positionBean = {};
		positionBean.positionName = $('#add-positionName').val();
		positionBean.desctiption = $('#add-desctiption').val();
		
		positionBean.company = companyBean;
		
		return positionBean;
	}


</script>

<script>
	var constraints = {
		"add-positionName" : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		"add-desctiption" : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formAddPosition");
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
			savePosition();
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