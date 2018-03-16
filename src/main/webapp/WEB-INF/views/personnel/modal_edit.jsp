<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<div class="modal fade" id="editemployee" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabelEdit" align="left">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabelEdit">
					<i class="ti-pencil-alt"></i> แก้ไขพนักงาน
				</h4>
			</div>
			<form id="formUpdatePersonal">
				<div class="modal-body">
					<div class="row">
						<input type="hidden" id="editPersonnelId" />
					</div>

					<!-- <div class="row">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>การออกหมายเลขรหัสพนักงาน
						</b></label><br> <label class="custom-control custom-radio"> 
						<input name="empIDTypeEdit" type="radio" checked="checked"
							value="auto" class="custom-control-input"> <span
							class="custom-control-indicator"></span> <span
							class="custom-control-description">ระบบออกเลขรหัสพนักงานให้อัตโนมัติ</span>
						</label><label class="custom-control custom-radio"> 
						<input name="empIDTypeEdit" type="radio" value="custom"
							class="custom-control-input"> <span
							class="custom-control-indicator"></span> <span
							class="custom-control-description">กำหนดเลขรหัสพนักงานเอง</span>
						</label>
					</div>
				</div>  -->








					<div class="row mb15">
						<div class="col-md-12">
							<fieldset>
								<legend>&nbsp;&nbsp;&nbsp;ข้อมูลพนักงาน&nbsp;&nbsp;&nbsp;</legend>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>รหัสพนักงาน<span
														class="text-red"> *</span>
												</b><br /></label>
												<div id="the-basics">
													<input type="hidden" id="editPersonnelCodeHidden" /> <input
														class="form-control typeahead" type="text"
														id="editPersonnelCode" name="editPersonnelCode">
													<div class="messages"></div>
													<span style="display: none" id="editCheckDupPersonnelCode">รหัสพนักงานซ้ำกับในระบบ</span>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>เพศ<span
														class="text-red"> *</span>
												</b></label><br> <label class="custom-control custom-radio">
													<input id="radio1" name="editPersonnelSex" type="radio"
													checked="checked" value="male" class="custom-control-input">
													<span class="custom-control-indicator"></span> <span
													class="custom-control-description">&nbsp;ชาย</span>
												</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio">
													<input id="radio2" name="editPersonnelSex" type="radio"
													value="female" class="custom-control-input"> <span
													class="custom-control-indicator"></span> <span
													class="custom-control-description">&nbsp;หญิง</span>
												</label>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>คำนำหน้า<span
														class="text-red"> *</span>
												</b></label>
												<div id="the-basics">
													<input class="form-control typeahead" type="text"
														id="editPersonnelPrefix" name="editPersonnelPrefix">
													<div class="messages"></div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>ชื่อ<span
														class="text-red"> *</span>
												</b></label> <input type="text" class="form-control"
													id="editPersonnelFirstName" name="editPersonnelFirstName">
												<div class="messages"></div>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>นามสกุล<span
														class="text-red"> *</span>
												</b></label> <input type="" text"" class="form-control"
													id="editPersonnelLastName" name="editPersonnelLastName">
												<div class="messages"></div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>ชื่อเล่น<span
														class="text-red"> *</span>
												</b></label>
												<div id="the-basics">
													<input class="form-control typeahead" type="text"
														id="editPersonnelNickName" name="editPersonnelNickName">
													<div class="messages"></div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>สังกัดบริษัท<span
														class="text-red"> *</span>
												</b></label> <select class="form-control" id="editCompanySelect"
													name="editCompanySelect">
												</select>
												<div class="messages"></div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>ตำแหน่งงาน<span
														class="text-red"> *</span>
												</b></label> <select id="editPositionSelect" name="editPositionSelect"
													class="form-control">
													<!-- 							<option value="" selected="selected">--- เลือก ---</option> -->
												</select>
												<div class="messages"></div>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>กลุ่มสิทธิ์การใช้งานในระบบ<span
														class="text-red"> *</span>
												</b></label> <select id="editPermissionGroupSelect"
													name="editPermissionGroupSelect" class="form-control">
													<option value="" selected="selected">--- เลือก ---</option>
												</select>
												<div class="messages"></div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-md-3">
												<label for="exampleInputEmail1"><b>เบอร์โทรติดต่อ<span
														class="text-red"> *</span>
												</b></label> <input type="text" class="form-control"
													id="editPersonnelMobile" name="editPersonnelMobile">
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
								<legend>&nbsp;&nbsp;&nbsp;Username และ Password
									ที่ใช้ในการเข้าสู่ระบบ&nbsp;&nbsp;&nbsp;</legend>
								<div class="form-group">
									<div class="col-md-6">
										<label for="exampleInputEmail1"><b>E-mail<span
												class="text-red"> *</span>
										</b></label> <input type="email" class="form-control"
											id="editPersonnelEamil" name="editPersonnelEamil">
										<div class="messages"></div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-md-6">
										<label for="exampleInputEmail1"><b>Password<span
												class="text-red"> *</span>
										</b></label> <input type="password" class="form-control"
											id="editPersonnelPassword" name="editPersonnelPassword">
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
	function editPersonnel() {
		var personnelBean = generateEditPersonnelJson();
		//check duplicate
		var personnelCode = $('#editPersonnelCode').val();
		if (personnelCode != ""
				&& personnelCode != $('#editPersonnelCodeHidden').val()) {
			if (checkDuplicatePersonnelCode(personnelCode) == true) {
				return false;
			}
		}
		//end check type custom

		//send update
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/personnel/update",
			dataType : 'json',
			async : false,
			data : JSON.stringify(personnelBean),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					generatePersonnelSearchSession();
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

	function generateEditPersonnelJson() {

		var personnelBean = {};
		personnelBean.personnelCode = $('#editPersonnelCode').val();
		personnelBean.id = $('#editPersonnelId').val();
		personnelBean.sex = $('input[name=editPersonnelSex]:checked').val();
		personnelBean.prefix = $('#editPersonnelPrefix').val();
		personnelBean.firstName = $('#editPersonnelFirstName').val();
		personnelBean.lastName = $('#editPersonnelLastName').val();
		personnelBean.nickName = $('#editPersonnelNickName').val();
		//company bean
		var CompanyBean = {};
		CompanyBean.id = $('#editCompanySelect').val();
		personnelBean.company = CompanyBean;
		//position bean
		var PositionBean = {};
		PositionBean.id = $('#editPositionSelect').val();
		personnelBean.position = PositionBean;
		//permission group
		var PermissionGroupBean = {};
		PermissionGroupBean.id = $('#editPermissionGroupSelect').val();
		personnelBean.permissionGroup = PermissionGroupBean;
		//contact bean
		var contactBean = {};
		contactBean.mobile = $('#editPersonnelMobile').val();
		contactBean.email = $('#editPersonnelEamil').val();
		personnelBean.contact = contactBean;

		//authen bean
		var authenticationBean = {};
		authenticationBean.username = $('#editPersonnelEamil').val();
		authenticationBean.password = $('#editPersonnelPassword').val();
		personnelBean.authenticationBean = authenticationBean;

		return personnelBean;
	}

	function openDialogEdit(personnelId) {
		initDataEdit(personnelId);
		$('#editemployee').modal('show');

		$(".error").remove();
		var constraintsEdit = {
			editPersonnelCode : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelPrefix : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelFirstName : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelLastName : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPositionSelect : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPermissionGroupSelect : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelMobile : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelEamil : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editCompanySelect : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelNickName : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			},
			editPersonnelPassword : {
				presence : {
					message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
				}
			}
		};

		// Hook up the form so we can prevent it from being posted
		var formEdit = document.querySelector("form#formUpdatePersonal");
		formEdit.addEventListener("submit", function(ev) {

			ev.preventDefault();
			var errors = validate(formEdit, constraintsEdit);
			// then we update the form to reflect the results
			showErrors(formEdit, errors || {});
			if (!errors) {
				editPersonnel();
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

	//init load ////////////////////////////////////////////////////////////////////////
	function initDataEdit(id) {
		$
				.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/personnel/openEdit/"
							+ id,
					dataType : 'json',
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							//Begin set init company dropdown//////////////////////////////////
							$('#editCompanySelect').find('option').remove();
							for (var i = 0; i < data["resultStore01"].length; i++) {
								if (data["resultStore01"][i].id == data["result"]["company"].id) {
									$('#editCompanySelect')
											.append(
													$(
															"<option selected='selected'></option>")
															.attr(
																	"value",
																	data["resultStore01"][i].id)
															.text(
																	data["resultStore01"][i].companyName));
								} else {
									$('#editCompanySelect')
											.append(
													$("<option></option>")
															.attr(
																	"value",
																	data["resultStore01"][i].id)
															.text(
																	data["resultStore01"][i].companyName));
								}
							}
							//End set init company dropdown//////////////////////////////////

							//Begin set init position dropdown//////////////////////////////////
							if (data["resultStore02"].length > 0) {
								$('#editPositionSelect').find('option')
										.remove();
								//set choose select text
								$('#editPositionSelect').append(
										$("<option></option>")
												.attr("value", "").text(
														textChooseDropdown));

								for (var i = 0; i < data["resultStore02"].length; i++) {
									if (data["resultStore02"][i].id == data["result"]["position"].id) {
										$('#editPositionSelect')
												.append(
														$(
																"<option selected='selected'></option>")
																.attr(
																		"value",
																		data["resultStore02"][i].id)
																.text(
																		data["resultStore02"][i].positionName));
									} else {
										$('#editPositionSelect')
												.append(
														$("<option></option>")
																.attr(
																		"value",
																		data["resultStore02"][i].id)
																.text(
																		data["resultStore02"][i].positionName));
									}
								}
							}
							//End set init position dropdown//////////////////////////////////

							//Begin set init position dropdown//////////////////////////////////
							if (data["resultStore03"].length > 0) {
								$('#editPermissionGroupSelect').find('option')
										.remove();
								//set choose select text
								// 						$('#editPermissionGroupSelect').append($("<option selected='selected'></option>").attr("value","").text(textChooseDropdown));

								for (var i = 0; i < data["resultStore03"].length; i++) {
									if (data["resultStore03"][i].id == data["result"]["permissionGroup"].id) {
										$('#editPermissionGroupSelect')
												.append(
														$(
																"<option selected='selected'></option>")
																.attr(
																		"value",
																		data["resultStore03"][i].id)
																.text(
																		data["resultStore03"][i].permissionGroupName));
									} else {
										$('#editPermissionGroupSelect')
												.append(
														$("<option></option>")
																.attr(
																		"value",
																		data["resultStore03"][i].id)
																.text(
																		data["resultStore03"][i].permissionGroupName));
									}
								}
							}
							//End set init position dropdown//////////////////////////////////

							//Begin set personnel basic data
							$('#editPersonnelId').val(data["result"]["id"]);
							$('#editPersonnelCodeHidden').val(
									data["result"]["personnelCode"]);
							$('#editPersonnelCode').val(
									data["result"]["personnelCode"]);
							var sex = data["result"]["sex"];
							$(
									'input:radio[name="editPersonnelSex"][value='
											+ sex + ']').prop('checked', true);
							$('#editPersonnelPrefix').val(
									data["result"]["prefix"]);
							$('#editPersonnelFirstName').val(
									data["result"]["firstName"]);
							$('#editPersonnelLastName').val(
									data["result"]["lastName"]);
							$('#editPersonnelNickName').val(
									data["result"]["nickName"]);
							//contact
							$('#editPersonnelMobile').val(
									data["result"]["contact"].mobile);
							$('#editPersonnelEamil').val(
									data["result"]["contact"].email);
							//authen
							$('#editPersonnelPassword')
									.val(
											data["result"]["authenticationBean"].password);
							//End set personnel basic data
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
						$('#editCompanySelect')
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
																			'#editPositionSelect')
																			.find(
																					'option')
																			.remove();
																	//set choose select text
																	$(
																			'#editPositionSelect')
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
																				'#editPositionSelect')
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
																			'#editPositionSelect')
																			.find(
																					'option')
																			.remove();
																	//set choose select text
																	$(
																			'#editPositionSelect')
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
						$('#editPersonnelCode')
								.change(
										function() {
											var personnelCode = $(this).val();
											if (personnelCode != ""
													&& personnelCode != $(
															'#editPersonnelCodeHidden')
															.val()) {
												if (checkDuplicatePersonnelCode(personnelCode) == true) {
													$(
															'#editCheckDupPersonnelCode')
															.css(
																	{
																		'display' : 'inline',
																		'color' : 'red'
																	});
												} else {
													$(
															'#editCheckDupPersonnelCode')
															.css(
																	{
																		'display' : 'none',
																		'color' : 'red'
																	});
												}
											} else {
												$('#editCheckDupPersonnelCode')
														.css({
															'display' : 'none',
															'color' : 'red'
														});
											}
										});
						//End event personnelCode
					});
</script>