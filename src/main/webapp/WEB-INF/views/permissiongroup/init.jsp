<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="permissiongroup" scope="request"/>


<!DOCTYPE html>
<html lang="en">
<head>
<title>สิทธิ์การใช้งานระบบ</title>
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
						<div class="col-md-8">
							<h3>สิทธิ์การใช้งานระบบ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item active">สิทธิ์การใช้งานระบบ</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M3.add" >
							<a
								href="${pageContext.request.contextPath}/permissiongroup/formAddPermissionGroup"><button
									type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มกลุ่มสิทธิ์ผู้ใช้งาน
								</button></a>
							</perm:permission>
						</div>
					</div>
					<!-- End Add Modal -->
					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการกลุ่มสิทธิ์ในระบบ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="30px;"><center>ลำดับ</center></th>
														<th>ชื่อกลุ่ม</th>
														<th>คำอธิบาย</th>
														<th><center>สถานะการใช้งาน</center></th>
														<th><center>จำนวนผู้ใช้งาน</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when
															test="${permissionGroups != null && permissionGroups.size() > 0 }">
															<c:forEach var="permissionGroups"
																items="${permissionGroups}" varStatus="i">
																<tr>
																	<td><center>${i.count }</center></td>
																	<td><b>${permissionGroups.permissionGroupName}</b></td>
																	<td>${permissionGroups.permissionGroupDesc}</td>
																	<c:choose>
																		<c:when test="${permissionGroups.status}">
																			<td><center>
																					<i class="fa fa-circle text-green"></i>
																					เปิดใช้งานปกติ
																				</center></td>
																		</c:when>
																		<c:otherwise>
																			<td><center>
																					<i class="fa fa-circle text-red"></i> ปิดการใช้งาน
																				</center></td>
																		</c:otherwise>
																	</c:choose>
																	<td>
																		<center>${fn:length(permissionGroups.personnels)}
																			คน</center>
																	</td>
																	<td align="center">
																	<perm:permission object="M3.view" >
																		<a class="cursor-p"
																			href="${pageContext.request.contextPath}/permissiongroup/view/${permissionGroups.id}">
																				<span class="ti-search" data-toggle="tooltip"
																				data-placement="bottom" title="ดูรายละเอียด"></span>
																		</a> 
																	</perm:permission>
																	<c:if test="${permissionGroups.permissionType ne 'init' }">
																		<perm:permission object="M3.edit" >
																		<a class="cursor-p"
																			href="${pageContext.request.contextPath}/permissiongroup/edit/${permissionGroups.id}">
																				<span class="ti-pencil-alt" data-toggle="tooltip"
																				data-placement="bottom" title="แก้ไข"></span>
																		</a> 
																		</perm:permission>
																		
																		<perm:permission object="M3.delete" >
																			<a class="run-swal cursor-p"
																				onclick="openDialogDelete(${permissionGroups.id});">
																					<span class="ti-trash" data-toggle="tooltip"
																					data-placement="bottom" title="ลบ"></span>
																			</a>
																		</perm:permission>
																	</c:if>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="6"><center>ไม่พบข้อมูล</center></td>
															</tr>
														</c:otherwise>
													</c:choose>

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

			<!-- modal delete -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			<!-- end modal delete -->

			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
</body>
</html>
