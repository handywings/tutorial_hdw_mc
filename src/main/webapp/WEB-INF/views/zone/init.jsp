<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="zone" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลเขตชุมชน/โครงการ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
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
							<h3>ข้อมูลเขตชุมชน/โครงการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item active">ข้อมูลเขตชุมชน/โครงการ</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M6.add" >
							<a class="cursor-p" data-toggle="modal" data-target="#addArea"><button
									type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มเขตชุมชน
									/ โครงการ
								</button></a>
						</perm:permission>
						</div>
					</div>

					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการเขตชุมชน
							</h4>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
											<thead class="thead-default">
												<tr>
													<th width="30px;"><center>ลำดับ</center></th>
													<th><center>ชื่อเขตชุมชน / โครงการ</center></th>
													<th>เขตพื้นที่</th>
													<th></th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
														<c:when test="${zones != null && zones.size() > 0 }">
															<c:forEach var="zone" items="${zones}" varStatus="i">
																<tr>
																	<td><center>${i.count }</center></td>
																	<td><center><b>${zone.zoneName}</b></center></td>
																	<td>${zone.zoneDetail}</td>
																	<td align="center">
																		<perm:permission object="M6.edit" >
																			<a class="cursor-p"
																				data-toggle="modal"
																				onclick="editZone(${zone.id})"><span
																					class="ti-pencil-alt" data-toggle="tooltip"
																					data-placement="bottom" title=""
																					data-original-title="แก้ไข"></span></a>
																			</perm:permission>
																			
																		<perm:permission object="M6.delete" >	
																			<a class="run-swal cursor-p"><span class="ti-trash"  onclick="openDialogDelete(${zone.id });"
																					data-toggle="tooltip" data-placement="bottom" title=""
																					data-original-title="ลบ"></span></a>
																		</perm:permission>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="4"><center>ไม่พบข้อมูล</center></td></tr>
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
						<!-- modal edit -->
			<jsp:include page="modal_edit.jsp"></jsp:include>
			<!-- end modal edit -->
			
			<!-- modal add -->
			<jsp:include page="modal_add.jsp"></jsp:include>
			<!-- end modal add -->
			
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
