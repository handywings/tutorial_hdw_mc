<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

					<!-- Add Modal -->
					<div class="modal fade" id="addUnit" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" align="left">
						<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="myModalLabel"><i class="ti-plus"></i> เพิ่มหน่วยนับ</h4>
									</div>
									<form id="formAddUnit">
									<div class="modal-body">
										<div class="form-group">
											<label for="exampleInputEmail1"><b>ชื่อหน่วยหน่วยนับ<span class="text-red"> *</span>
											</b></label>
											<input type="text" class="form-control"
												id="add_unitName" name="add_unitName">
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
					<!-- End Add Modal -->

<script type="text/javascript">
	
	//save
	function saveUnit(){
		//generate bean
		var unitBean = generateAddUnitJson();
// 		console.log(unitBean);
		//validate basic
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/unit/save",
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
	
	function generateAddUnitJson(){
		var unitBean = {};
		
		unitBean.unitName = $('#add_unitName').val();

		return unitBean;
	}
</script>
<script>
	var constraints = {
			add_unitName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formAddUnit");
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
			saveUnit()();
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
