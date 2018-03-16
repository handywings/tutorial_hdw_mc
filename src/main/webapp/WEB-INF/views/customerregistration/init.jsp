<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="customerregistration" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ทะเบียนลูกค้า</title>
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
							<h3>ทะเบียนลูกค้า</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item active">ทะเบียนลูกค้า</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาข้อมูลลูกค้า
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="customerSearchBean" action="${pageContext.request.contextPath}/customerregistration/search">
										<div class="col-md-12 mb05">
											<input type="text" path="key" name="key" class="form-control" value="${customerSearchBean.key }"
												placeholder="ระบุ รหัส ชื่อ นามสกุล บ้านเลขที่ หมายเลขบัตรประจำตัวประชาชน อีเมล์ หรือหมายเลขโทรศัพท์ ของลูกค้าที่ต้องการค้นหา...">
										</div>
										<div class="col-md-5 mb05">
											<select id="custType" name="custType" path="custType" class="form-control">
												<option value="" selected="selected">--- แสดงประเภทลูกค้าทั้งหมด ---</option>
												<option value="I" <c:if test="${customerSearchBean.custType eq 'I'}">selected="selected"</c:if>>บุคคลธรรมดา</option>
												<option value="C" <c:if test="${customerSearchBean.custType eq 'C'}">selected="selected"</c:if>>นิติบุคคล</option>
											</select>	
										</div>
										<div class="col-md-5 mb05">
											<select name="customerFeatures" path="customerFeatures" class="form-control">
												<option value="0" selected="selected">--- แสดงลักษณะของลูกค้าทั้งหมด ---</option>
												<c:forEach var="customerFeature" items="${customerFeatures}" varStatus="i">
													<option value="${customerFeature.id}" <c:if test="${customerSearchBean.customerFeatures == customerFeature.id}">selected="selected"</c:if>>${customerFeature.customerFeatureName}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05 mb30">
						<div class="card-header text-uppercase">
							<div class="col-md-6">
								<h4>
									<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายชื่อลูกค้า
								</h4>
							</div>
							<div class="col-md-6">
								<span class="text-grey pull-right"> สถานะการใช้งาน : <i class="fa fa-check text-green"></i>&nbsp;ใช้งานปกติ&nbsp;&nbsp;|&nbsp;&nbsp;<i class="fa fa-close text-red"></i>&nbsp;ไม่ได้ใช้งาน</span>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12">
										<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
									</div>									
								</div>
							
								<div class="row mb05">									
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th width="5%"><center>รหัสลูกค้า</center></th>
														<th width="20%">ชื่อ-นามสกุล</th>
														<th width="15%"><center>หมายเลขโทรศัพท์</center></th>
														<th width="20%"><center>ประเภท / ลักษณะลูกค้า</center></th>
														<th width="20%"><center>สถานที่ใกล้เคียง</center></th>
														<th width="15%"><center>สถานะการใช้งาน</center></th>
														<th width="10%"></th>
													</tr>
												</thead>
												<tbody>												
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
															<c:forEach var="customerBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td class="td-middle"><center><b>${customerBean.custCode }</b>
																		</center></td>
																	<td class="td-middle">
																		${customerBean.prefix}${customerBean.firstName}&nbsp;
																		${customerBean.lastName}<br>
																		<small style="color: gray;">
																		<b>Email&nbsp;:&nbsp;</b>${customerBean.contact.email}
																		</small>
																	</td>
																	<td class="td-middle"><center>${customerBean.contact.mobile }</center></td>
																	<td class="td-middle"><center>${customerBean.customerType.value }<br><font style="color: gray;">${customerBean.customerFeatureBean.customerFeatureName }</font></center></td>
																	<td class="td-middle"><center>
																	<c:forEach var="addressBean" items="${customerBean.addressList}" varStatus="i">
																	<c:if test="${addressBean.addressType eq '1' }">
																	${addressBean.nearbyPlaces }
																	</c:if>
																	</c:forEach> 
																	</center></td>
																	<td class="td-middle">
																		<center>
																			<c:choose>
																				<c:when test="${customerBean.active}">
																					<i class="fa fa-check text-green"></i>
																				</c:when>
																				<c:otherwise>
																					<i class="fa fa-close text-red"></i>
																				</c:otherwise>
																			</c:choose>
																		</center>
																	</td>
																	<td align="center" class="td-middle">
																	<perm:permission object="M24.view" >
																		<a class="cursor-p" href="${pageContext.request.contextPath}/customerregistration/view/${customerBean.id}">
																		<span class="ti-search" data-toggle="tooltip"
																				data-placement="bottom" title="ดูรายละเอียด">
																		</span></a> 
																	</perm:permission>
																	<perm:permission object="M24.edit" >
																	<a class="cursor-p" href="${pageContext.request.contextPath}/customerregistration/edit/${customerBean.id}"><span
																			class="ti-pencil-alt" data-toggle="tooltip"
																			data-placement="bottom" title=""
																			data-original-title="แก้ไข"></span></a>
																			<!-- check active -->
																	</perm:permission>
																	
																	<perm:permission object="M24.delete" >
																	<c:if test="${not customerBean.active}">
																		<a style="cursor:pointer" onclick="openDialogDelete(${customerBean.id});">
																		<span class="ti-trash" data-toggle="tooltip" data-placement="bottom" title=""
																		data-original-title="ลบ"></span>
																		 </a>
																	 </c:if>
																	 </perm:permission>
																	 
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="6"><center>ไม่พบข้อมูลลูกค้า</center></td>
															</tr>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- delete modal -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			<!-- end delete modal -->
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
		$( document ).ready(function() {
			$('#tableCustomer').dataTable({searching: true, paging: true});
		});
	</script>
	
</body>
</html>
