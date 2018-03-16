<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="serviceapplicationlistzone" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบสมัครตามเขตพื้นที่</title>
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
							<h3>รายการใบสมัครตามเขตพื้นที่</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item active">รายการใบสมัครตามเขตพื้นที่</li>
							</ol>
						</div>
					</div>
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบสมัครใช้บริการ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="applicationSearchBean" action="${pageContext.request.contextPath}/serviceapplicationlistzone/search">
										<div class="col-md-12 mb05">
											<input type="text" name="key" class="form-control" value="${applicationSearchBean.key }" path="key"
												placeholder="ระบุ เลขใบสมัคร ชื่อสกุล บ้านเลขที่ติดตั้ง อีเมล์ หรือหมายเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-5 mb05">
											<select id="servicePackageType" name="servicePackageType" class="form-control" path="servicePackageType" onchange="getvalServiceType(this);">
												<option value="0" selected="selected">ประเภทบริการทั้งหมด</option>
												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
													<option value="${servicePackageTypeBean.id }" <c:if test="${applicationSearchBean.servicePackageType eq servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName }</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-5 mb05">
											<select id="zone" name="zone" path="zone" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">เขตชุมชน / โครงการทั้งหมด</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${applicationSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail} (${zoneBean.zoneName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light col-xs-12">
												<span class="ti-search"></span> ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05 mb40">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการใบสมัครตามเขตพื้นที่</h4>
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
														<th width="10%"><center>เลขที่ใบสมัคร</center></th>
														<th width="30%">ข้อมูลลูกค้า</th>
														<th width="30%">ข้อมูลบริการที่สมัคร</th>
														<th width="30%"><center>เขตชุมชน / โครงการ</center></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0}">
															<c:forEach var="serviceApplicationBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td class="td-middle">
																		<center>
																		<perm:permission object="M25.view">
																			<a class="cursor-p" target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">
																		</perm:permission>
																				<b>${serviceApplicationBean.serviceApplicationNo}</b>
																			<perm:permission object="M25.view">
																			</a>
																			</perm:permission>
																			</
																		</center>
																	</td>
																	<td class="td-middle">
																	${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;
																	${serviceApplicationBean.customer.lastName} ${serviceApplicationBean.plateNumber }<br>
																	<small style="color: gray;">
																	<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${serviceApplicationBean.customer.custCode }</a><br>
																	
																	<c:choose>
																		<c:when test="${serviceApplicationBean.plateNumber != null && serviceApplicationBean.plateNumber != \"\" }">
																			<b>Plate Number&nbsp;:&nbsp;</b>${serviceApplicationBean.plateNumber }<br>
																		</c:when>
																		<c:otherwise>
																			<c:if test="${serviceApplicationBean.customer.contact.email != null && serviceApplicationBean.customer.contact.email != \"\" }">
																				<b>Email&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.email}<br>
																			</c:if>
																		</c:otherwise>
																	</c:choose>
																	
																	<b>โทร&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.mobile}
																	</small>
																	</td>
																	<td class="td-middle">${serviceApplicationBean.servicepackage.packageName}<br>
																	<small style="color: gray;"><b>รหัส Package :</b> ${serviceApplicationBean.servicepackage.packageCode}<br><b>ประเภทบริการ ${serviceApplicationBean.servicepackage.serviceType.packageTypeName}</b></small></td>
																	<td class="td-middle">
																		<center>
																			<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																				<c:if test="${addressBean.addressType == 3}">																			
																					${addressBean.collectAddressDetail } <br><b>(${addressBean.zoneBean.zoneDetail })</b>
																					<c:if test="${addressBean.nearbyPlaces != null }">
																					<br>
																					<font style="color: gray;">ใกล้เคียงกับ ${addressBean.nearbyPlaces }</font>
																					</c:if>
																				</c:if>
																			</c:forEach>																	
																		</center>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="4"><center>ไม่พบข้อมูลใบสมัคร</center></td>
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
	<script type="text/javascript">

	</script>
</body>
</html>
