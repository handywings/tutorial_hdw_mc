<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="personnel" scope="request"/>
<c:set var="subMenu" value="techniciangroup" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายละเอียดกลุ่มพนักงาน</title>
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
							<h3>รายละเอียดกลุ่มช่างเทคนิค</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ข้อมูลพนักงาน</li>
								<li class="breadcrumb-item active"><a
									href="${pageContext.request.contextPath}/techniciangroup">ข้อมูลกลุ่มช่างเทคนิค</a></li>
								<li class="breadcrumb-item active">รายละเอียดกลุ่มช่างเทคนิค</li>
							</ol>
						</div>
						<div class="col-md-4 mt05">
							<perm:permission object="M12.add" >
								<a onclick="openDialogMember(${technicianGroupBean.id });"><button
										type="button" data-toggle="modal" data-target="#addOrg"
										class="btn btn-info label-left float-xs-right mr-0-5">
										<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มพนักงานช่างเทคนิค
									</button></a>
							</perm:permission>
						
						</div>
					</div>
					<div class="card mt05 mb30">
						<div class="card-header text-uppercase">
							<h3>รายชื่อพนักงานช่างเทคนิคในกลุ่ม ${technicianGroupBean.technicianGroupName }</h3>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th><center>รหัสพนักงาน</center></th>
														<th>ชื่อ - นามสกุล</th>
														<th><center>เบอร์โทร</center></th>
														<th><center>E-mail</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${technicianGroupBean.personnelList}" var="personnelMember" varStatus="i">
														<tr>
															<td><center>
																	 <b>${personnelMember.personnelCode }</b>
																</center>
															</td>
															<td>
																${personnelMember.firstName } ${personnelMember.lastName }&nbsp;(${personnelMember.nickName })&nbsp;
																<c:if test="${personnelMember.id == technicianGroupBean.personnel.id}"> (หัวหน้ากลุ่ม)</c:if>
															</td>
															<td>
																<center>
																	${personnelMember.contact.mobile }
																</center>
															</td>
															<td>
																<center>${personnelMember.contact.email }</center>
															</td>
															<td align="center">
															<perm:permission object="M12.view" >
															<a class="cursor-p" href="${pageContext.request.contextPath}/personnel/view/${personnelMember.id }">
																<span class="ti-search"
																		data-toggle="tooltip" data-placement="bottom" title=""
																		data-original-title="ดูรายละเอียด">
																</span>
															</a> 
															</perm:permission>
															
															<perm:permission object="M12.delete" >
															<a class="cursor-p" 
															onclick="openDialogDelete(${technicianGroupBean.id },${personnelMember.id})">
																	<span class="ti-trash" data-toggle="tooltip"
																	data-placement="bottom" title=""
																	data-original-title="ลบ"></span></a>
															</perm:permission>		
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
								<!--<div class="row mt15">
									<div class="col-md-3 mb05">
										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่
											1 - 5 / 5</span>
									</div>
									<div class="col-md-9">
										<nav class="pull-right">
											<ul class="pagination m-0">
												<li class="page-item active"><a class="page-link"
													href="#">1 <span class="sr-only">(current)</span></a></li>
											</ul>
										</nav>
									</div>
								</div> -->
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- modal delete member -->
			<jsp:include page="modal_delete_member.jsp"></jsp:include>
			<!-- end modal delete member -->
			<!-- modal delete member -->
			<jsp:include page="modal_member.jsp"></jsp:include>
			<!-- end modal delete member -->
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
		
	</script>

</body>
</html>
