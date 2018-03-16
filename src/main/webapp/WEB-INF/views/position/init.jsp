<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="position" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลตำแหน่งงาน</title>
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
							<h3>ข้อมูลตำแหน่งงาน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item active">ข้อมูลตำแหน่งงาน</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<perm:permission object="M5.add" >
								<button type="button" type="button" data-toggle="modal"
									data-target="#addOrg"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มตำแหน่งงาน
								</button>
							</perm:permission>
						</div>
					</div>

					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการตำแหน่งงาน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
<!-- 								<div class="row mb15"> -->
<!-- 									<div class="col-md-3 mb05"> -->
<!-- 										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่ -->
<!-- 											1 - 6 / 6</span> -->
<!-- 									</div> -->
<!-- 									<div class="col-md-9"> -->
<!-- 										<nav class="pull-right"> -->
<!-- 											<ul class="pagination m-0"> -->
<!-- 												<li class="page-item active"><a class="page-link" -->
<!-- 													href="#">1 <span class="sr-only">(current)</span></a></li> -->
<!-- 											</ul> -->
<!-- 										</nav> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="30px;"><center>ลำดับ</center></th>
														<th>ชื่อตำแหน่งงาน</th>
														<th>คำอธิบาย</th>
														<th>สังกัดบริษัท</th>
														<th width="30px"></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${positions != null && positions.size() > 0 }">
															<c:forEach var="positions" items="${positions}" varStatus="i">
																<tr>
																	<td><center>${i.count }</center></td>
																	<td>${positions.positionName}</td>
																	<td>${positions.desctiption}</td>
																	<td>${positions.company.companyName}</td>
																	<td align="center">
																	<perm:permission object="M5.edit" >
																		<a class="cursor-p"
																			data-toggle="modal"
																			onclick="editPosition(${positions.id})"><span
																				class="ti-pencil-alt" data-toggle="tooltip"
																				data-placement="bottom" title=""
																				data-original-title="แก้ไข"></span></a>
																	</perm:permission>
																	
																	<perm:permission object="M5.delete" >		
																		<a class="run-swal cursor-p"><span class="ti-trash"  onclick="openDialogDelete(${positions.id });"
																				data-toggle="tooltip" data-placement="bottom" title=""
																				data-original-title="ลบ"></span></a>
																	</perm:permission>
																			
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="5"><center>ไม่พบข้อมูล</center></td></tr>
														</c:otherwise>
													</c:choose>
													
												</tbody>
											</table>
										</div>
									</div>
								</div>
<!-- 								<div class="row mt15"> -->
<!-- 									<div class="col-md-3 mb05"> -->
<!-- 										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่ -->
<!-- 											1 - 10 / 10</span> -->
<!-- 									</div> -->
<!-- 									<div class="col-md-9"> -->
<!-- 										<nav class="pull-right"> -->
<!-- 											<ul class="pagination m-0"> -->
<!-- 												<li class="page-item active"><a class="page-link" -->
<!-- 													href="#">1 <span class="sr-only">(current)</span></a></li> -->
<!-- 											</ul> -->
<!-- 										</nav> -->
<!-- 									</div> -->
<!-- 								</div> -->
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
