<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

	<div class="modal fade" id="addArea" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="ti-plus"></i> เพิ่มเขตชุมชน / โครงการ
					</h4>
				</div>
				<form id="addZone">
				<div class="modal-body">					
						<div class="row">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>ชื่อเขตชุมชน /
										โครงการ<span class="text-red"> *</span>
								</b></label> <input type="text" class="form-control" id="add_zoneName"
									name="add_zoneName" aria-describedby="emailHelp" value="">
								<div class="messages"></div>
							</div>
						</div>
					</div>
					
						<div class="row mt05">
						<div class="form-group">
							<div class="col-md-12">
								<label for="exampleInputEmail1"><b>เขตพื้นที่<span
										class="text-red"> *</span></b></label>
								<textarea class="form-control" id="add_zoneDetail"
									name="add_zoneDetail" rows="3"></textarea>
								<div class="messages"></div>
							</div>
						</div>
					</div>
					<input type="hidden" value="ทั่วไป" id="add_zoneType">
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
	
	//save
	function saveZone(){
		//generate bean
		var zoneBean = generateAddZoneJson();
// 		console.log(zoneBean);
		//validate basic
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/zone/save",
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
	
	function generateAddZoneJson(){
		var zoneBean = {};
		
		zoneBean.zoneName = $('#add_zoneName').val();
		zoneBean.zoneDetail = $('#add_zoneDetail').val();
		zoneBean.zoneType = $('#add_zoneType').val();
		
		return zoneBean;
	}
</script>
<script>
	var constraints = {
			add_zoneName : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			add_zoneDetail : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#addZone");
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
			saveZone();
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

