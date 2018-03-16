<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="document" scope="request"/>
<c:set var="subMenu" value="document" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลเอกสาร</title>
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
							<h3>ข้อมูลเอกสาร</h3>
						</div>
						
					</div>
			

					<!-- End Add Modal -->
					<div class="card mb40">
						<div class="card-header text-uppercase">
							<div class="row">
								<div class="col-md-4"><h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการเอกสาร
							</h4></div>
								<div class="col-md-8">
									<perm:permission object="M4.add" >
										<button type="button" data-toggle="modal"
											onclick="addCompany();" 
											class="btn btn-info label-left float-xs-right mr-0-5 btn-xs">
											<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มเอกสาร
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
														<th class="td-middle">ชื่อเอกสาร</th>
														<th class="td-middle"><center>รหัสเอกสาร</center></th>
														<th class="td-middle"><center>ประเภทเอกสาร</center></th>
														<th class="td-middle"><center>สิทธิ์การเข้าใช้เอกสาร</center></th>
														<th width="60px;" class="td-middle"></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${documentList != null && documentList.size() > 0 }">
															<c:forEach var="document" items="${documentList}" varStatus="i">
																<tr>
																	<td><center>${i.count }</center></td>
																	<td>${document.document_name }</td>
																	<td>${document.document_code }</td>
																	<td>
																	<c:if test="${document.document_type eq '1'}">
																		Material approve
																	</c:if>
																	<c:if test="${document.document_type eq '2'}">
																		RFI
																	</c:if>
																	<c:if test="${document.document_type eq '3'}">
																		LTR
																	</c:if>
																	<c:if test="${document.document_type eq '4'}">
																		VO
																	</c:if>
																	<c:if test="${document.document_type eq '5'}">
																		Payment
																	</c:if>
																	<c:if test="${document.document_type eq '6'}">
																		Shop drawing
																	</c:if>
																	<c:if test="${document.document_type eq '7'}">
																		SI
																	</c:if>
																	</td>
																	<td>
																	<c:if test="${document.permission eq 'A'}">
																		ทำได้หมด
																	</c:if>
																	<c:if test="${document.permission eq 'R'}">
																		อ่านเท่านั้น
																	</c:if>
																	</td>
																	<td align="center"> 
																			<a class="cursor-p" onclick="viewCompany(${document.id })"><span
																				class="ti-search" data-toggle="tooltip"
																				data-placement="bottom" title="ดูรายละเอียด"></span></a>

																			<a class="cursor-p" data-toggle="modal" onclick="editCompany(${document.id },false)"><span
																				class="ti-pencil-alt" data-toggle="tooltip"
																				data-placement="bottom" title=""
																				data-original-title="แก้ไข"></span></a>

																			<a class="cursor-p"><span class="ti-trash" onclick="openDialogDelete(${document.id });"
																				data-toggle="tooltip" data-placement="bottom" title=""
																				data-original-title="ลบ"></span></a>
																				
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
