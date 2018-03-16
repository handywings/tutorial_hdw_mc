<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="worksheet" scope="request"/>
<c:set var="subMenu" value="calist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายการใบงานทั้งหมด</title>
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
							<h3>รายการใบงานรอ CA</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
								<li class="breadcrumb-item active">รายการใบงานรอ CA</li>
							</ol>
						</div>
					</div>

					<div class="card mb40">
						<div class="card-header text-uppercase">
							<div class="row">
								<div class="col-md-7">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการใบงานที่ยกเลิก
									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th style="vertical-align: middle;" width="15%"><center>เลขที่ใบงาน</center></th>
														<th style="vertical-align: middle;" width="25%">ข้อมูลลูกค้า</th>
														<th style="vertical-align: middle;" width="15%"><center>ประเภทใบงาน</center></th>
														<th style="vertical-align: middle;" width="30%"><center>เขตชุมชน
																/ โครงการ</center></th>
<%-- 														<th style="vertical-align: middle;" align="center" width="20%"><center>ผู้รับผิดชอบ</center></th> --%>
														<th style="vertical-align: middle;" width="15%"><center>ยืนยันการทำ
																CA</center></th>

													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${worksheets != null && worksheets.size() > 0 }">
															<c:forEach var="workSheetBean" items="${worksheets}"
																varStatus="i">
																<tr>
																	<td>
																		<center>
																			<a
																				href="${pageContext.request.contextPath}/worksheetlist/detail/${workSheetBean.idWorksheetParent}"
																				data-toggle="tooltip" data-placement="bottom"
																				title="ดูรายละเอียด"><b>${workSheetBean.workSheetCode }</b></a>
																		</center>
																	</td>
																	<td>
																		${workSheetBean.serviceApplication.customer.firstName }&nbsp;
																		${workSheetBean.serviceApplication.customer.lastName }<br>
																		<small class="text-gray"> <b>รหัสสมาชิก :
																		</b><a
																			href="${pageContext.request.contextPath}/customerregistration/view/${workSheetBean.serviceApplication.customer.id }"
																			target="_blank" data-toggle="tooltip"
																			data-placement="bottom" title="ดูรายละเอียด">${workSheetBean.serviceApplication.customer.custCode }</a><br>
																			<b>โทร:&nbsp;</b>${workSheetBean.serviceApplication.customer.contact.mobile}
																	</small>
																	</td>
																	<td>
																		<center>${workSheetBean.workSheetTypeText }</center>
																	</td>
																	<td><c:forEach var="addressBean"
																			items="${workSheetBean.serviceApplication.addressList}"
																			varStatus="j">
																			<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.collectAddressDetail } <br>
																				<b>( ${addressBean.zoneBean.zoneDetail } )</b>
																			</c:if>
																		</c:forEach> <small style="color: gray;">&nbsp;${workSheetBean.serviceApplication.customer.customerFeatureBean.customerFeatureName}</small>
																	</td>
<!-- 																	<td> -->
<%-- 																		<center> --%>
<%-- 																			<c:forEach var="historyTechnicianGroupWork" --%>
<%-- 																				items="${workSheetBean.historyTechnicianGroupWorkBeans}" --%>
<%-- 																				varStatus="j"> --%>
<%-- 																				<c:if --%>
<%-- 																					test="${(fn:length(workSheetBean.historyTechnicianGroupWorkBeans)-1) == j.index}"> --%>
<%-- 																	${historyTechnicianGroupWork.technicainGroup.personnel.firstName}&nbsp; --%>
<%-- 																	${historyTechnicianGroupWork.technicainGroup.personnel.lastName} --%>
<%-- 																	</c:if> --%>
<%-- 																			</c:forEach> --%>
<%-- 																		</center> --%>
<!-- 																	</td> -->
																	<td align="center">
																		<perm:permission object="M32.add" >
																			<button type="button"
																			class="btn btn-warning w-min-sm mb-0-25 waves-effect waves-light"
																			onclick="openDialogConfirm(${workSheetBean.idWorksheetParent });">ยืนยัน
																			CA</button>
																		</perm:permission>
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

			<jsp:include page="modal_confirm.jsp"></jsp:include>

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

</body>
</html>
