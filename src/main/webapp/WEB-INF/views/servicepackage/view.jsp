<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="package" scope="request"/>
<c:set var="subMenu" value="servicepackage" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียด Package :${servicePackageBean.packageName }</title>
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
						<div class="col-md-7">
							<h3>รายละเอียด Package : ${servicePackageBean.packageName }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">Package บริการ</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/servicepackage">รายการ
										Package</a></li>
								<li class="breadcrumb-item active">รายละเอียด Package</li>
							</ol>
						</div>
						<div class="col-md-5" align="right">
<%-- 							<c:if test="${servicePackageBean.active }"> --%>
								<perm:permission object="M46.edit" >
									<a href="${pageContext.request.contextPath}/servicepackage/edit/${servicePackageBean.id}"><button type="button"
										class="btn btn-warning label-left float-xs-right mr-0-5 mb05">
										<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูล
										Package บริการ
									</button></a>
									
									<c:choose>
										<c:when test="${servicePackageBean.active }">
											<button type="button" onclick="openDialogCancel(${servicePackageBean.id });"
												class="btn btn-danger label-left float-xs-right mr-0-5 mb05">
												<span class="btn-label"><i class="fa fa-ban"></i></span>ยกเลิกบริการ
											</button>
										</c:when>
										<c:otherwise>
											<button type="button" onclick="openDialogEnable(${servicePackageBean.id });"
												class="btn btn-success label-left float-xs-right mr-0-5 mb05">
												<span class="btn-label"><i class="fa fa-check"></i></span>เปิดใช้งานบริการ
											</button>
										</c:otherwise>
									</c:choose>									
									
								</perm:permission>
<%-- 							</c:if> --%>
						</div>
					</div>
					<nav
						class="navbar navbar-light bg-white navbar-bottom-line  mb-1 mt05">
						<button class="navbar-toggler hidden-md-up collapsed"
							type="button" data-toggle="collapse"
							data-target="#exCollapsingNavbar4"
							aria-controls="exCollapsingNavbar4" aria-expanded="false"
							aria-label="Toggle navigation"></button>
						<div class="navbar-toggleable-sm collapse"
							id="exCollapsingNavbar4" aria-expanded="false">

							<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
								<li class="nav-item"><a class="nav-link active"
									data-toggle="tab" href="#packageTab" role="tab"><i class="zmdi zmdi-card-giftcard"></i> ข้อมูล Package บริการ</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#customerTab" role="tab"><i class="zmdi zmdi-format-list-bulleted"></i> รายชื่อผู้ใช้งาน Package</a></li>
							</ul>
						</div>
					</nav>
					<div class="tab-content">
					<div class="tab-pane active" id="packageTab" role="tabpanel">
					<div class="card mt05 mb30">
						<div class="card-header clearfix">
							<h4 class="float-xs-left mb-0">
								<i class="zmdi zmdi-card-giftcard"></i>&nbsp;&nbsp;ข้อมูล
								PACKAGE บริการ
							</h4>
							<div class="float-xs-right">วันที่สร้าง ${servicePackageBean.createDateTh }</div>
						</div>
						<div class="card-block">
							<fieldset>
								<legend>&nbsp;&nbsp;&nbsp;Package&nbsp;&nbsp;&nbsp;</legend>
								<div class="row">
									<div class="col-md-3">
										<label for="exampleSelect1"><b>ประเภทบริการ</b></label><br>
										${servicePackageBean.serviceType.packageTypeName }
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="exampleInputEmail1"><b>ชื่อ Package</b></label><br>
											[ Package ID : ${servicePackageBean.packageCode } ] ${servicePackageBean.packageName }
										</div>
									</div>
									<div class="col-md-3">
										<div class="form-group">
											<label for="exampleInputEmail1"><b>บริษัท</b></label><br>
											${servicePackageBean.company.companyName }
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="exampleInputEmail1"><b>รายละเอียด
													Package</b></label>
											<div class="table-responsive">
												<c:choose>
													<c:when test="${servicePackageBean.monthlyService}">
														<table class="table table-bordered mb-0 table-hover">
														<thead class="thead-bg">
															<tr>
																<th><center>ค่าติดตั้ง</center></th>
																<th><center>ค่ามัดจำอุปกรณ์</center></th>
<%-- 																<th><center>รอบบิลแรกใช้ฟรี</center></th> --%>
																<th><center>ส่วนลดรอบบิลแรก</center></th>
																<th><center>ค่าบริการ</center></th>
																<th><center>ระยะเวลารอบบิล</center></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.installationFee}" /> บาท</center>
																	</div></td>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.deposit}" />  บาท</center>
																	</div></td>
<!-- 																<td scope="row"><div class="input-group"> -->
<%-- 																		<center>${servicePackageBean.firstBillFree} เดือน</center> --%>
<!-- 																	</div></td> -->
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.firstBillFreeDisCount}" />  บาท</center>
																	</div></td>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.monthlyServiceFee}" />  บาท</center>
																	</div></td>
																<td scope="row"><div class="input-group">
																		<center>${servicePackageBean.perMounth} เดือน</center>
																	</div></td>
															</tr>
														</tbody>
													</table>
													</c:when>
													<c:otherwise>
														<div class="table-responsive">
														<table class="table table-bordered mb-0 table-hover">
														<thead class="thead-bg">
															<tr>
																<th><center>ค่าติดตั้ง</center></th>
																<th><center>ค่ามัดจำอุปกรณ์</center></th>
																<th><center>ค่าบริการ</center></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.installationFee}" /> บาท</center>
																	</div></td>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.deposit}" /> บาท</center>
																	</div></td>
																<td scope="row"><div class="input-group">
																		<center><fmt:formatNumber type="number" maxFractionDigits="3" value="${servicePackageBean.oneServiceFee}" /> บาท</center>
																	</div></td>
															</tr>
														</tbody>
													</table>
													</div>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<div class="row mt15">
								<div class="col-md-12">
									<fieldset>
										<legend>&nbsp;&nbsp;&nbsp;รายชื่อบริการและ วัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
										<div class="row">
											<div class="col-md-12">
											<div class="table-responsive">
												<table class="table table-bordered mb-0 table-hover">
													<thead class="thead-bg">
														<tr>
															<th><center>ลำดับ</center></th>
															<th><center>รหัสสินค้า</center></th>
															<th>ชื่อเรียกสินค้า</th>
															<th>ประเภทสินค้า</th>
															<th style="width: 130px;"><center>จำนวน</center></th>
															<th><center>ฟรี</center></th>
															<th><center>ยืม</center></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${servicePackageBean.template.listTemplateServiceItemBean}" var="templateItem" varStatus="i">
															<tr>
																<td><center>${i.count }</center></td>
																<td><center><a class="cursor-p" target="_blank" href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${templateItem.productBean.id }">${templateItem.productBean.productCode }</a></center></td>
																<td>${templateItem.productBean.productName }</td>
																<td scope="row">${templateItem.productBean.productCategory.equipmentProductCategoryName }</td>
																<td class="td-middle"><center>${templateItem.quantity } &nbsp; ${templateItem.productBean.unit.unitName }</center></td>
																<td align="center" style="vertical-align: middle;">
																<center>
																	<c:if test="${templateItem.free }">
																		<span class="ti-check-box"></span>
																	</c:if>
																</center></td>
																<td align="center" style="vertical-align: middle;">
																	<center>
																		<c:if test="${templateItem.lend }">
																		<span class="ti-check-box"></span>
																	</c:if>
																	</center>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>
										</div>
									</fieldset>
								</div>
							</div>
						</div>

					</div>
					</div>
					<!-- customer -->
						<div class="tab-pane" id="customerTab" role="tabpanel">
							<div class="card mt05 mb30">
								<div class="card-header clearfix">
									<h4 class="float-xs-left mb-0">
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายชื่อลูกค้าที่ใช้งาน PACKAGE
									</h4>
								</div>
								<div class="card-block">
									 <div class="posts-list posts-list-1">
										<div class="pl-item">
											<div class="row mb15">
													<div class="col-md-11 mb05">
														<input type="text" name="inputSearchCustomer" id="inputSearchCustomer" class="form-control" value="" placeholder="ระบุชื่อหรือรหัสพนักงานที่ต้องการค้นหา...">
													</div>
													<div class="col-md-1 mb05">
														<button type="button" class="btn btn-warning col-xs-12" id="btnClearSearchCustomer">ล้าง</button>
													</div>
											</div>
											 
											<div class="row mb05">
												<div class="col-md-12 mb05">
													<div class="table-responsive">
														<table class="table table-bordered mb-0 table-hover" id="tableCustomer">
															<thead class="thead-bg">
																<tr>
																	<th><center>รหัสลูกค้า</center></th>
																	<th>ชื่อ -นามสกุล</th>
																	<th><center>ประเภทลูกค้า</center></th>
																	<th><center>วันที่เริ่มต้นใช้ Package</center></th>
																	<th><center>ครั้งล่าสุดที่ชำระค่าบริการ</center></th>
																	<th></th>
																</tr>
															</thead>
															<tbody>
																<c:forEach items="${servicePackageBean.serviceApplicationBeans}" var="serviceApplicationBean" varStatus="i">
																	<tr>
																		<td><center>
																				${serviceApplicationBean.customer.custCode }
																			</center></td>
																		<td>
																				${serviceApplicationBean.customer.firstName } &nbsp;
																				${serviceApplicationBean.customer.lastName }
																		</td>
																		<td><center>
																			<c:choose>
																				<c:when test="${serviceApplicationBean.customer.customerType.value eq 'C' }">นิติบุคคล</c:when>
																				<c:when test="${serviceApplicationBean.customer.customerType.value eq 'I' }">บุคคลธรรมดา</c:when>
																			</c:choose>
																		</center></td>
																		<td>
																			<center>${serviceApplicationBean.startDate }</center>
																		</td>
																		<td>
																		<!-- แก้ไขเป็นวันที่ชำระล่าสุด -->
																			<center>${serviceApplicationBean.startDate }</center>
																		</td>
																		<td align="center"><a target="_blank" href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id}"><span
																				class="ti-search" data-toggle="tooltip"
																				data-placement="bottom" title="ดูข้อมูลลูกค้า"></span></a></td>
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
					<!-- end customer -->
					</div>
				</div>
			</div>
			<jsp:include page="cancel_service.jsp"></jsp:include>
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
		$(document).ready(function() {
			$( document ).ready(function() {
				var oTable = $('#tableCustomer').dataTable();
				$('#tableCustomer_filter').css('display','none');

				$("#inputSearchCustomer").keyup( function (e) {
				     oTable.fnFilter( this.value );
				} );
				
				$('#btnClearSearchCustomer').on('click',function(){
					$("#inputSearchCustomer").val("");
					oTable.fnFilter("");
				});
				
			});
		});
	</script>
</body>
</html>
