<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="permissiongroup" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดกลุ่มสิทธิ์</title>
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
						<div class="col-md-4">
							<h3>รายละเอียดกลุ่มสิทธิ์</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item"><a
									href="${pageContext.request.contextPath}/permissiongroup">สิทธิ์การใช้งานระบบ</a></li>
								<li class="breadcrumb-item active">รายละเอียดกลุ่มสิทธิ์</li>
							</ol>
						</div>
						<div class="col-md-8">
							<c:if test="${permissionGroups.permissionType == null}">
								<perm:permission object="M3.edit" >
									<a href="${pageContext.request.contextPath}/permissiongroup/edit/${permissionGroups.id}"><button type="button" class="btn btn-warning label-left float-xs-right mr-0-5">
											<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูล</button></a>
								</perm:permission>
							</c:if>							
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">
								<span class="ti-receipt"
									style="position: absolute; padding-top: 2px;"></span>&nbsp;&nbsp;&nbsp;&nbsp;ข้อมูลสิทธิ์การใช้งาน
							</h4>
						</div>
						<div class="card-block">
							<div class="row">
								<div class="col-md-8">
									<div class="row">
										<div class="col-md-6 col-xs-6">
											<b>ชื่อกลุ่มสิทธิ์ใช้งาน</b>
										</div>
										<div class="col-md-6 col-xs-6">${permissionGroups.permissionGroupName}</div>
									</div>
									<div class="row">
										<div class="col-md-6 col-xs-6">
											<b>คำอธิบายกลุ่มสิทธิ์ใช้งาน</b>
										</div>
										<div class="col-md-6 col-xs-6">${permissionGroups.permissionGroupDesc}</div>
									</div>
									<div class="row mb15">
										<div class="col-md-6 col-xs-6">
											<b>สถานะกลุ่มสิทธิ์ใช้งาน</b>
										</div>
										<div class="col-md-6 col-xs-6"> 
											<c:choose>
											<c:when test="${permissionGroups.status}">
												<i class="fa fa-circle text-green" ></i> เปิดใช้งานปกติ
											</c:when>
											<c:otherwise>
												<i class="fa fa-circle text-red" ></i> ปิดการใช้งาน
											</c:otherwise>
											</c:choose>	
										</div>
									</div>
								</div>
								<div class="col-md-4">
									<fieldset>
										<legend style=" margin: 0 auto; ">&nbsp;&nbsp;&nbsp;จำนวนผู้ใช้งานสิทธิ์  ${permissionGroups.permissionGroupName}&nbsp;&nbsp;&nbsp;</legend>
										<div class="row">
											<div class="col-md-12">
												<center><span class="ti-user"></span> ${fn:length(permissionGroups.personnels)} คน</center>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>
					</div>
					
					<div class="card mt05">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">
								<span class="ti-receipt" style="position: absolute; padding-top: 4px;"></span>&nbsp;&nbsp;&nbsp;&nbsp;สิทธิ์การใช้งาน
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
													    <c:set var="checkedMenu" scope="session" value=""/>
													    <c:set var="permissionMenuId" scope="session" value="permissionMenu_${functions.id}"/>
													    <c:if test="${functions.check}">
													    	<c:set var="checkedMenu" scope="session" value="checked='checked'"/>
													    </c:if>
															<tr style=" background-color: rgba(158, 158, 158, 0.11); ">
																<td colspan="6"><label class="custom-control custom-checkbox"> 
																<input type="checkbox" id="permissionMenu_${functions.id}" class="custom-control-input permissionMenu_${functions.id}" value="permissionMenu_${functions.id}" ${checkedMenu} disabled> 
																<span class="custom-control-indicator"></span> 
																<span id="spanMenu_permissionMenu_${functions.id}" class="custom-control-description"><b>${functions.functionName}</b></span>
																</label></td>
															</tr>
													    </c:when>
													    <c:otherwise>
													     
													    <c:set var="checkedFunc" scope="session" value=""/>
													    <c:set var="checkedView" scope="session" value=""/>
													    <c:set var="checkedAdd" scope="session" value=""/>
													    <c:set var="checkedEdit" scope="session" value=""/>
													    <c:set var="checkedDelete" scope="session" value=""/>
														
														<c:if test="${functions.check}">
													    	<c:set var="checkedFunc" scope="session" value="checked='checked'"/>
													    </c:if>
													    <c:if test="${functions.view}">
													    	<c:set var="checkedView" scope="session" value="checked='checked'"/>
													    </c:if>
													    <c:if test="${functions.add}">
													    	<c:set var="checkedAdd" scope="session" value="checked='checked'"/>
													    </c:if>
													    <c:if test="${functions.edit}">
													    	<c:set var="checkedEdit" scope="session" value="checked='checked'"/>
													    </c:if>
													    <c:if test="${functions.delete}">
													    	<c:set var="checkedDelete" scope="session" value="checked='checked'"/>
													    </c:if>
													    
													    <c:set var="functionNameId" scope="session" value="functionName_${functions.id}"/>
													    <c:set var="checksize" scope="session" value="checksize${permissionMenuId}"/>
	
															<tr>
																<td></td>
																<td><label class="custom-control custom-checkbox">
																		<input type="checkbox" id="${functionNameId}" class="custom-control-input ${checksize} ${permissionMenuId}" value="${functionNameId}" ${checkedFunc} disabled>
																		<span class="custom-control-indicator"></span> 
																		<span id="spanFunc_${functionNameId}" class="custom-control-description">${functions.functionName}</span>
																</label></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																			<input id="permission_view_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" ${checkedView} disabled> 
																			<span class="custom-control-indicator"></span> 
																			<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																			<input id="permission_add_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" ${checkedAdd} disabled> 
																			<span class="custom-control-indicator"></span> 
																			<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																		<input id="permission_edit_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" ${checkedEdit} disabled> 
																		<span class="custom-control-indicator"></span> 
																		<span class="custom-control-description"></span>
																		</label>
																	</center></td>
																<td><center>
																		<label class="custom-control custom-checkbox"> 
																		<input id="permission_delete_${functionNameId}" type="checkbox" class="custom-control-input ${checksize} ${permissionMenuId} ${functionNameId}" ${checkedDelete} disabled> 
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
					
					<div class="card mb40">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายชื่อผู้ใช้งานกลุ่มสิทธิ์
								ฝ่ายขาย / รับสมัคร
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="50px;"><center>ลำดับ</center></th>
														<th><center>รหัสพนักงาน</center></th>
														<th>ชื่อ -นามสกุล</th>
														<th><center>กลุ่มสิทธิ์</center></th>
														<th><center>เบอร์โทร</center></th>
														<th><center>E-mail</center></th>
													</tr>
												</thead>
												<tbody>
												<c:forEach var="personnels" items="${permissionGroups.personnels}" varStatus="i">
												<tr>
													<td><center>${i.count }</center></td>
													<td><center>${personnels.personnelCode}</center></td>
													<td>${personnels.firstName} ${personnels.lastName}</td>
													<td><center>${personnels.position.positionName}</center></td>
													<td><center>${personnels.contact.mobile}</center></td>
													<td><center>${personnels.contact.email}</center></td>
												</tr>
												</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	
<script type="text/javascript">



</script>

</body>
</html>
