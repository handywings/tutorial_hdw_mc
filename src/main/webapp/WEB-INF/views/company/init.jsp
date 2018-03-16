<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="company" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลบริษัท</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/dropify/css/dropify.min.css" />">
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
							<h3>ข้อมูลบริษัท</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item active">ข้อมูลบริษัท</li>
							</ol>
						</div>
						
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ข้อมูล  ${company.companyName}</h4>
						</div>
						<perm:permission object="M4.edit" >
							<button type="button" data-toggle="modal" onclick="editCompany(${company.id },true)"
								class="btn btn-warning btn-circle waves-effect waves-light pull-right"
								style="position: absolute; top: -16px; right: 11px;"
								data-toggle="tooltip" data-placement="bottom" title=""
								data-original-title="แก้ไขข้อมูลบริษัท">
								<i class="ti-pencil-alt"></i>
							</button>
						</perm:permission>
						
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-culture"></i>&nbsp;&nbsp;ชื่อบริษัท
											:</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.companyName}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-map-marker"></i>&nbsp;&nbsp;ที่อยู่ :</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.address.detail}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-call"></i>&nbsp;&nbsp;โทร :</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.contact.mobile}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-mail"></i>&nbsp;&nbsp;Email :</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.contact.email}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-print"></i>&nbsp;&nbsp;Fax :</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.contact.fax}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-id"></i>&nbsp;&nbsp;เลขที่ผู้เสียภาษี
											:</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.taxId}</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-ticket"></i>&nbsp;&nbsp;ภาษีมูลค่าเพิ่ม
											:</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.vat} %</div>
								</div>
								<div class="row">
									<div class="col-md-5 col-xs-6">
										<b><i class="pe-7s-date"></i>&nbsp;&nbsp;Inv เครดิต :</b>
									</div>
									<div class="col-md-7 col-xs-6">${company.invCredit} วัน</div>
								</div>
							</div>
						</div>
					</div>

					<!-- End Add Modal -->
					<div class="card mb40">
						<div class="card-header text-uppercase">
							<div class="row">
								<div class="col-md-4"><h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายชื่อบริษัทย่อย
							</h4></div>
								<div class="col-md-8">
									<perm:permission object="M4.add" >
										<button type="button" data-toggle="modal"
											onclick="addCompany(${company.id});" 
											class="btn btn-info label-left float-xs-right mr-0-5 btn-xs">
											<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มบริษัทย่อย
										</button>
								</perm:permission>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="40px;"><center>ลำดับ</center></th>
														<th class="td-middle" width="150px;">ชื่อบริษัท</th>
<%-- 														<th class="td-middle" width="20px;"><center>โลโก้บริษัท</center></th> --%>
														<th class="td-middle"><center>ที่อยู่</center></th>
														<th class="td-middle"><center>เบอร์ติดต่อ</center></th>
														<th width="60px;" class="td-middle"></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${company.subcompanyList != null && company.subcompanyList.size() > 0 }">
															<c:forEach var="subCompany" items="${company.subcompanyList}" varStatus="i">
																<tr>
																	<td><center>${i.count }</center></td>
																	<td>${subCompany.companyName }</td>
<!-- 																	<td> -->
<!-- 																	<span class="avatar box-32"> -->
<%-- 																	 	<img src="${subCompany.logo }" style="border-radius: 0%" alt="โลโก้บริษัท"> --%>
<!-- 																	</span> -->
<!-- 																	</td> -->
																	<td>${subCompany.address.detail }</td>
																	<td align="center">${subCompany.contact.mobile }</td>
																	<td align="center"> 
																		<!-- view -->
																		<perm:permission object="M4.view" >
																			<a class="cursor-p" onclick="viewCompany(${subCompany.id })"><span
																				class="ti-search" data-toggle="tooltip"
																				data-placement="bottom" title="ดูรายละเอียด"></span></a>
																		</perm:permission>
																		<!-- end view -->
																		
																		<!-- edit -->
																		<perm:permission object="M4.edit" >
																			<a class="cursor-p" data-toggle="modal" onclick="editCompany(${subCompany.id },false)"><span
																				class="ti-pencil-alt" data-toggle="tooltip"
																				data-placement="bottom" title=""
																				data-original-title="แก้ไข"></span></a>
																		</perm:permission>
																		<!-- end edit -->
																		
																		<!-- delete -->
																		<perm:permission object="M4.delete" >
																			<a class="cursor-p"><span class="ti-trash" onclick="openDialogDelete(${subCompany.id });"
																				data-toggle="tooltip" data-placement="bottom" title=""
																				data-original-title="ลบ"></span></a>
																		</perm:permission>
																		<!-- end delete -->
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
								<!-- 
								<div class="row mt15">
									<div class="col-md-3 mb05">
										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่
											1 - 4 / 4</span>
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
			
			<!-- modal view -->
			<jsp:include page="modal_view.jsp"></jsp:include>
			<!-- end modal view -->
			
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
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/dropify/js/dropify.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/js/forms-upload.js" />"></script>
	<c:if test="${not empty alertBean}">
	    <script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>

</body>
</html>
