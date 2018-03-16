<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="permissiongroup" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>เพิ่มกลุ่มสิทธิ์ผู้ใช้งาน</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="material-design fixed-sidebar fixed-header">

	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">

					<div class="row mt05 mb05" >

						<div class="col-md-12">
							<h3>เพิ่มกลุ่มสิทธิ์ผู้ใช้งาน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/permissiongroup">สิทธิ์การใช้งานระบบ</a></li>
								<li class="breadcrumb-item active">เพิ่มกลุ่มสิทธิ์ผู้ใช้งาน</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">
								<span class="ti-receipt"
									style="position: absolute; padding-top: 2px;"></span>&nbsp;&nbsp;&nbsp;&nbsp; ข้อมูลสิทธิ์การใช้งาน
							</h4>
						</div>
					<form id="formAddPermissionGroup">	
						<div class="card-block">
							<div class="row">
										<div class="col-md-12">
											<b>ชื่อกลุ่มสิทธิ์ใช้งาน<span class="text-red"> *</span></b>
										</div>
										<div class="form-group">
										<div class="col-md-12">
											<input type="text" class="form-control"
												id="permissionGroupName" name="permissionGroupName" placeholder="ระบุชื่อกลุ่มสิทธิ์การใช้งาน">
											<div class="messages"></div>
										</div>					
										</div>					
									</div>
									<div class="row mt05">
										<div class="col-md-12">
											<b>คำอธิบายกลุ่มสิทธิ์ใช้งาน </b>
										</div>
										<div class="col-md-12">
											<textarea rows="2" id="permissionGroupDesc" class="form-control" placeholder="ระบุรายละเอียดหรือคำอธิบายกลุ่มสิทธิ์"></textarea>
										</div>
									</div>
						</div>
					</div>
					<div class="card mt05 mb30">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">
								<span class="ti-layout-list-thumb" style="position: absolute; padding-top: 4px;"></span>&nbsp;&nbsp;&nbsp;&nbsp; สิทธิ์การใช้งาน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mt05">
									<div class="col-md-12">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table_permission" >
												<thead class="thead-default">
													<tr>
														<th>เมนู</th>
														<th>ฟังก์ชั่น</th>
														<th width="100px;"><center>ดูข้อมูล</center></th>
														<th width="100px;"><center>เพิ่มข้อมูล</center></th>
														<th width="100px;"><center>แก้ไขข้อมูล</center></th>
														<th width="100px;"><center>ลบข้อมูล</center></th>
													</tr>
												</thead>
												<tbody>
												<c:forEach var="functions" items="${permissionGroups.functions}" varStatus="i">
													<c:choose>
													    <c:when test="${functions.parent==0}">
													    <c:set var="permissionMenuId" scope="session" value="permissionMenu_${functions.id}"/>
															<tr style=" background-color: rgba(158, 158, 158, 0.11); ">
																<td colspan="6"><label class="custom-control custom-checkbox"> 
																<input type="checkbox" id="permissionMenu_${functions.id}" class="custom-control-input permissionMenu_${functions.id}" value="permissionMenu_${functions.id}" > 
																<span class="custom-control-indicator"></span> 
																<span id="spanMenu_permissionMenu_${functions.id}" class="custom-control-description"><b>${functions.functionName}</b></span>
																</label></td>
															</tr>
													    </c:when>
													    <c:otherwise>
													    <c:set var="functionNameId" scope="session" value="functionName_${functions.id}"/>
													    <c:set var="checksize" scope="session" value="checksize${permissionMenuId}"/>
															<tr>
																<td></td>
																<td><label class="custom-control custom-checkbox">
																		<input type="checkbox" id="${functionNameId}" class="custom-control-input ${checksize} ${permissionMenuId}" value="${functionNameId}">
																		<span class="custom-control-indicator"></span> 
																		<span id="spanFunc_${functionNameId}" class="custom-control-description">${functions.functionName}</span>
																</label></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																			<input id="permission_view_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" > 
																			<span class="custom-control-indicator"></span> 
																			<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																			<input id="permission_add_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" > 
																			<span class="custom-control-indicator"></span> 
																			<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																		<input id="permission_edit_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" > 
																		<span class="custom-control-indicator"></span> 
																		<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																		<input id="permission_delete_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" > 
																		<span class="custom-control-indicator"></span> 
																		<span class="custom-control-description"></span>
																		</label>
																	</center></td>
															</tr>
													    </c:otherwise>
													</c:choose>
												</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>					
							</div>
						</div>
					</div>
					
					<div class="row mt05 mb30" align="center">
	<button type="submit"
		class="btn btn-success label-left mr-0-5">
		<span class="btn-label"><i class="ti-check"></i></span>บันทึก</button> <a href="${pageContext.request.contextPath}/permissiongroup"><button type="button"
		class="btn btn-danger label-left mr-0-5">
		<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก</button></a> 
	
					</div>
					
					</form>
				</div>
				<!-- Footer -->
				<jsp:include page="../layout/footer.jsp"></jsp:include>
			</div>

		</div>
	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
	    <script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	
<script type="text/javascript">
	
	$("input:checkbox").click(function () {
		var classCheckBox = $(this).val();
	    $("."+classCheckBox+"").prop('checked', $(this).prop("checked"));
	    
	    // เช็คปิด checkBox เมนู
		var classes = $(this).attr('class').split(' ');
		var permissionMenu,functionName;

	    if(classes.length > 2){
	    	functionName = classes[3];
	    	permissionMenu = classes[2];
	    	classes = classes[1];
		    $("#"+permissionMenu+"").prop('checked', true);
	    }

	    var table_permission_tr = $('#table_permission tr');
	    var findClassPer = table_permission_tr.find('.'+functionName+'');
		var findClass = table_permission_tr.find('.'+classes+'');
		var findClassMenu = table_permission_tr.find('.'+permissionMenu+'');

		
		if(!findClassPer.is(":checked")){
			$("#"+functionName+"").prop('checked', false);
		}else{
			$("#"+functionName+"").prop('checked', true);
		}
		
		if(!findClass.is(":checked")){
			$("#"+permissionMenu+"").prop('checked', false);
		}else{
			$("#"+permissionMenu+"").prop('checked', true);
		}

		
	});


	function savePermissionGroup(){
		//generate bean
		var permissionGroup = generateAddPermissionGroupJson();
		console.log(permissionGroup);
// 		console.log(JSON.stringify(permissionGroup));
		//validate basic
		$('.preloader').modal('show');
		 $.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/permissiongroup/save",
			dataType : 'json',
			data : JSON.stringify(permissionGroup),
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
	
	function generateAddPermissionGroupJson(){
		
		var PermissionGroupBean = {};
		var functionmap = [];
		var FunctionBean = {};
		PermissionGroupBean.permissionGroupName = $('#permissionGroupName').val();
		PermissionGroupBean.permissionGroupDesc = $('#permissionGroupDesc').val();
		PermissionGroupBean.status = true;
		
		$('input:checkbox').each(function () {
// 		       var sThisVal = (this.checked ? $(this) : "");	
		       var sThisVal = $(this);
		       if(sThisVal){
// 		    	console.log(sThisVal.val());
		       	var dataMenu = $("#spanMenu_"+sThisVal.val()+"").text();
		       	var dataFunc = $("#spanFunc_"+sThisVal.val()+"").text();
		       	var view = $("#permission_view_"+sThisVal.val()+"").is(":checked");
		       	var add = $("#permission_add_"+sThisVal.val()+"").is(":checked");
		       	var edit = $("#permission_edit_"+sThisVal.val()+"").is(":checked");
		       	var deleted = $("#permission_delete_"+sThisVal.val()+"").is(":checked");

// 		       	console.log(view);
// 		       	console.log(dataMenu);
// 		       	console.log(dataFunc);
				FunctionBean.isView = false;
				FunctionBean.isAdd = false;
				FunctionBean.isEdit = false;
				FunctionBean.isDelete = false;
		       		if(view){
// 		       			console.log("view");
				       	FunctionBean.isView = true;
		       		}
		       		if(add){
// 		       			console.log("add");
				       	FunctionBean.isAdd = true;
		       		}
		       		if(edit){
// 		       			console.log("edit");
				       	FunctionBean.isEdit = true;
		       		}
		       		if(deleted){
// 		       			console.log("delete");
				       	FunctionBean.isDelete = true;
		       		}
		       		if(dataFunc){
// 		       			console.log(dataFunc);
						var isCheck = $("#"+sThisVal.val()+"").is(":checked");
						FunctionBean.isCheck = isCheck;
		       			FunctionBean.functionName = dataFunc;
		       			FunctionBean.parent = 1; 
		       			functionmap.push(FunctionBean);
		       			PermissionGroupBean.functions = functionmap;
		       			FunctionBean = {};
		       		}
		       		if(dataMenu){
// 		       			console.log(dataMenu);
		       			var isCheck = $("#"+sThisVal.val()+"").is(":checked");
						FunctionBean.functionName = dataMenu;
				       	FunctionBean.isView = false;
				       	FunctionBean.isAdd = false;
				       	FunctionBean.isEdit = false;
				       	FunctionBean.isDelete = false;
				       	FunctionBean.isCheck = isCheck;
		       			FunctionBean.parent = 0; 
		       			functionmap.push(FunctionBean);
		       			PermissionGroupBean.functions = functionmap;
		       			FunctionBean = {};
		       		}

				}
		  });
		
		return PermissionGroupBean;
	}
</script>

<script>
	var constraints = {
			permissionGroupName : {
			presence : {
				message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
			}
		}
	};

	// Hook up the form so we can prevent it from being posted
	var form = document.querySelector("form#formAddPermissionGroup");
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
			savePermissionGroup();
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


</body>
</html>
