<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class="modal fade" id="addemployee" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabelAdd" align="left">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabelAdd">
					<i class="ti-plus"></i> เพิ่มพนักงาน
				</h4>
			</div>
			<form id="fromAddPersonnel">
				<div class="modal-body">
					<div class="row" style="display: none">
						<div class="col-md-2">
							<b>รูปแบบการออกรหัสพนักงาน </b>
						</div>
						<div class="form-group">
							<div class="col-md-10">
								<label class="custom-control custom-radio"> <input
									name="empIDType" type="radio" checked="checked" value="auto"
									class="custom-control-input"> <span
									class="custom-control-indicator"></span> <span
									class="custom-control-description">&nbsp;ระบบออกเลขรหัสพนักงานให้อัตโนมัติ</span>
								</label><br><label class="custom-control custom-radio"> <input
									name="empIDType" type="radio" value="custom"
									class="custom-control-input"> <span
									class="custom-control-indicator"></span> <span
									class="custom-control-description">&nbsp;กำหนดเลขรหัสพนักงานเอง</span>
								</label>
							</div>
						</div>
					</div>
					<div class="row" style="display: none">
						<div class="col-md-12">
							<hr>
						</div>
					</div>








					<div class="row mb15">
						<div class="col-md-12">
							<fieldset>
								<legend>&nbsp;&nbsp;&nbsp;ข้อมูลพนักงาน&nbsp;&nbsp;&nbsp;</legend>

								<div class="row">
									<div class="col-md-12">
									<div class="form-group">
										<div class="empid" style="display: none;">
											<div class="col-md-2 mt05">
												<b>รหัสพนักงาน<span class="text-red"> *</span>
												</b><br /> <span style="display: none"
													id="addCheckDupPersonnelCode">รหัสพนักงานซ้ำกับในระบบ</span>
												<div id="the-basics">
													<input class="form-control typeahead" type="text"
														id="addPersonnelCode" name="addPersonnelCode">
													<div class="messages"></div>
												</div>
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2 mt05">
											<b>คำนำหน้า<span class="text-red"> *</span>
											</b>
											<div id="the-basics">
												<input class="form-control typeahead" type="text"
													id="addPersonnelPrefix" name="addPersonnelPrefix">
												<div class="messages"></div>
											</div>

										</div>
									</div>
									<div class="form-group">
										<div class="col-md-3 mt05">
											<b>ชื่อ<span class="text-red"> *</span>
											</b> <input type="text" class="form-control"
												id="addPersonnelFirstName" name="addPersonnelFirstName">
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-3 mt05">
											<b>นามสกุล<span class="text-red"> *</span>
											</b> <input type="text" class="form-control"
												id="addPersonnelLastName" name="addPersonnelLastName">
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2 mt05">
											<b>เพศ
											</b><br> <label style="padding-bottom: 5.5px;"
												class="custom-control custom-radio"> <input
												id="radio1" name="addPersonnelSex" type="radio"
												checked="checked" value="male" class="custom-control-input">
												<span class="custom-control-indicator"></span> <span
												class="custom-control-description">&nbsp;ชาย</span>
											</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
												id="radio2" name="addPersonnelSex" type="radio"
												value="female" class="custom-control-input"> <span
												class="custom-control-indicator"></span> <span
												class="custom-control-description">&nbsp;หญิง</span>
											</label>
											<div class="messages"></div>
										</div>
									</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
									<div class="form-group">
										<div class="col-md-2 mt05">
											<b>ชื่อเล่น<span class="text-red"> *</span>
											</b> <input type="text" class="form-control"
												id="addPersonnelNickName" name="addPersonnelNickName">
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-3 mt05">
											<b>สังกัดบริษัท<span class="text-red"> *</span>
											</b> <select class="form-control" id="addCompanySelect"
												name="addCompanySelect">
											</select>
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-3 mt05">
											<b>ตำแหน่งงาน<span class="text-red"> *</span>
											</b> <select id="addPositionSelect" class="form-control"
												name="addPositionSelect">
											</select>
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2 mt05">
											<b>กลุ่มสิทธิ์การใช้งานในระบบ
											</b> <select id="addPermissionGroupSelect" name="categoryID"
												class="form-control">
											</select>
											<div class="messages"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="col-md-2 mt05">
											<b>เบอร์โทรติดต่อ<span class="text-red"> *</span>
											</b> <input type="text" class="form-control"
												id="addPersonnelMobile" name="addPersonnelMobile">
											<div class="messages"></div>
										</div>
									</div>
									</div>
								</div>
							</fieldset>
						</div>

					</div>










					<div class="row mt15 mb15">
						<div class="col-md-12">
						<fieldset style="background-color: rgba(158, 158, 158, 0.08);border-style: dashed;border-width: thin;border-color: rgba(158, 158, 158, 0.64);">
								<legend>&nbsp;&nbsp;&nbsp;Username และ Password ที่ใช้ในการเข้าสู่ระบบ&nbsp;&nbsp;&nbsp;</legend>
								<div class="form-group">
							<div class="col-md-6 mt05">
								<b>E-mail<span class="text-red"> *</span>
								</b> <input type="email" class="form-control" id="addPersonnelEamil"
									name="addPersonnelEamil">
								<div class="messages"></div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 mt05">
								<b>Password<span class="text-red"> *</span>
								</b> <input type="text" class="form-control" id="addPersonnelPassword"
									name="addPersonnelPassword">
								<div class="messages"></div>
							</div>
						</div>
								
							</fieldset>
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

<script type="text/javascript"
	src="resources/assets/plugins/jquery/jquery-1.12.3.min.js"></script>
<script type="text/javascript">
	function addPersonnel() {
		var personnelBean = generateAddPersonnelJson();
		if ($('input[name=empIDType]:checked').val() == 'custom') {
			var personnelCode = $('#addPersonnelCode').val();
			if (personnelCode != "") {
				if (checkDuplicatePersonnelCode(personnelCode) == true) {
					return false;
				}
			}
		} //end check type custom

		//send save
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/personnel/save",
			dataType : 'json',
			async : false,
			data : JSON.stringify(personnelBean),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					//generatePersonnelSearchSession();
					window.location.reload();
				} else {

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
	function generateAddPersonnelJson() {

		var personnelBean = {};
		if ($('input[name=empIDType]:checked').val() == 'custom') {
			personnelBean.personnelCode = $('#addPersonnelCode').val();
		} else {
			personnelBean.personnelCode = "";
		}

		personnelBean.sex = $('input[name=addPersonnelSex]:checked').val();
		personnelBean.prefix = $('#addPersonnelPrefix').val();
		personnelBean.firstName = $('#addPersonnelFirstName').val();
		personnelBean.lastName = $('#addPersonnelLastName').val();
		personnelBean.nickName = $('#addPersonnelNickName').val();
		//company bean
		var CompanyBean = {};
		CompanyBean.id = $('#addCompanySelect').val();
		personnelBean.company = CompanyBean;
		//position bean
		var PositionBean = {};
		PositionBean.id = $('#addPositionSelect').val();
		personnelBean.position = PositionBean;
		//permission group
		var PermissionGroupBean = {};
		PermissionGroupBean.id = $('#addPermissionGroupSelect').val();
		personnelBean.permissionGroup = PermissionGroupBean;
		//contact bean
		var contactBean = {};
		contactBean.mobile = $('#addPersonnelMobile').val();
		contactBean.email = $('#addPersonnelEamil').val();
		personnelBean.contact = contactBean;
		
		//authen bean
		var authenticationBean = {};
		authenticationBean.username = $('#addPersonnelEamil').val();
		authenticationBean.password = $('#addPersonnelPassword').val();
		personnelBean.authenticationBean = authenticationBean;
		
		return personnelBean;
	}
	function openDialogAdd() {
		initData();
		$('#addemployee').modal('show');
	}
	//init load ////////////////////////////////////////////////////////////////////////
	function initData() {
		$
				.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/personnel/openAdd",
					dataType : 'json',
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							//Begin set init company dropdown//////////////////////////////////
							$('#addCompanySelect').find('option').remove();
							for (var i = 0; i < data["result"].length; i++) {
								$('#addCompanySelect').append(
										$("<option></option>").attr("value",
												data["result"][i].id).text(
												data["result"][i].companyName));
							}
							//End set init company dropdown//////////////////////////////////

							//Begin set init position dropdown//////////////////////////////////
							if (data["resultStore01"].length > 0) {
								$('#addPositionSelect').find('option').remove();
								//set choose select text
								$('#addPositionSelect')
										.append(
												$(
														"<option selected='selected'></option>")
														.attr("value", "")
														.text(
																textChooseDropdown));

								for (var i = 0; i < data["resultStore01"].length; i++) {
									$('#addPositionSelect')
											.append(
													$("<option></option>")
															.attr(
																	"value",
																	data["resultStore01"][i].id)
															.text(
																	data["resultStore01"][i].positionName));
								}
							}
							//End set init position dropdown//////////////////////////////////

							//Begin set init position dropdown//////////////////////////////////
							if (data["resultStore02"].length > 0) {
								$('#addPermissionGroupSelect').find('option')
										.remove();
								//set choose select text
// 								$('#addPermissionGroupSelect')
// 										.append(
// 												$(
// 														"<option selected='selected'></option>")
// 														.attr("value", "")
// 														.text(
// 																textChooseDropdown));

								for (var i = 0; i < data["resultStore02"].length; i++) {
									$('#addPermissionGroupSelect')
											.append(
													$("<option></option>")
															.attr(
																	"value",
																	data["resultStore02"][i].id)
															.text(
																	data["resultStore02"][i].permissionGroupName));
								}
							}
							//End set init position dropdown//////////////////////////////////

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

	////End init load data ////////////////////////////////////////////////////////////////////////

	$(document)
			.ready(
					function() {
						///event 
						//Begin event company dropdown
						$('#addCompanySelect')
								.change(
										function() {
											var id = $(this).val();
											$
													.ajax({
														type : "GET",
														contentType : "application/json; charset=utf-8",
														url : "${pageContext.request.contextPath}/personnel/loadDataWithCompany/"
																+ id,
														dataType : 'json',
														//timeout : 100000,
														success : function(data) {
															if (data["error"] == false) {

																//Begin set init position dropdown//////////////////////////////////
																if (data["resultStore01"].length > 0) {
																	$(
																			'#addPositionSelect')
																			.find(
																					'option')
																			.remove();
																	//set choose select text
																	$(
																			'#addPositionSelect')
																			.append(
																					$(
																							"<option selected='selected'></option>")
																							.attr(
																									"value",
																									"")
																							.text(
																									textChooseDropdown));

																	for (var i = 0; i < data["resultStore01"].length; i++) {
																		$(
																				'#addPositionSelect')
																				.append(
																						$(
																								"<option></option>")
																								.attr(
																										"value",
																										data["resultStore01"][i].id)
																								.text(
																										data["resultStore01"][i].positionName));
																	}
																} else {
																	$(
																			'#addPositionSelect')
																			.find(
																					'option')
																			.remove();
																	//set choose select text
																	$(
																			'#addPositionSelect')
																			.append(
																					$(
																							"<option selected='selected'></option>")
																							.attr(
																									"value",
																									"")
																							.text(
																									textChooseDropdown));
																}
																//End set init position dropdown//////////////////////////////////
															}
														},
														error : function(e) {
															console.log(
																	"ERROR: ",
																	e);
														},
														done : function(e) {
															console.log("DONE");
														}
													});
										});
						//End event company dropdown

						//Bigin event personnelCode
						$('#addPersonnelCode')
								.change(
										function() {
											var personnelCode = $(this).val();
											if (personnelCode != "") {
												if (checkDuplicatePersonnelCode(personnelCode) == true) {
													$(
															'#addCheckDupPersonnelCode')
															.css(
																	{
																		'display' : 'inline',
																		'color' : 'red'
																	});
												} else {
													$(
															'#addCheckDupPersonnelCode')
															.css(
																	{
																		'display' : 'none',
																		'color' : 'red'
																	});
												}
											} else {
												$('#addCheckDupPersonnelCode')
														.css({
															'display' : 'none',
															'color' : 'red'
														});
											}
										});
						//End event personnelCode

					});

	/////////////////////////////////////function check dupplicate personnel code ////////////////
	function checkDuplicatePersonnelCode(personnelCode) {
		var checkdup;
		$
				.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/personnel/checkDuplicatePersonnelCode/"
							+ personnelCode,
					dataType : 'json',
					async : false,
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							checkdup = data["result"];
						} else {
							checkdup = true;
						}
					},
					error : function(e) {
						console.log("ERROR: ", e);
						checkdup = true;
					},
					done : function(e) {
						console.log("DONE");
					}
				});

		return checkdup;
	}
</script>

<script>
	var constraints = {
		addPersonnelPrefix : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelFirstName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelLastName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addCompanySelect : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPositionSelect : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		categoryID : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelMobile : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelEamil : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelNickName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		},
		addPersonnelPassword : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#fromAddPersonnel");
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
			addPersonnel();
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


