<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="validateinvoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ตรวจสอบใบเสร็จรับเงิน</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style type="text/css">
.nav-tabs .nav-link {
    padding: 0.5em .5em;
}
</style>
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
						<div class="col-md-12">
							<h3>ตรวจสอบใบเสร็จรับเงิน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบการเงิน</li>
								<li class="breadcrumb-item active">ตรวจสอบใบเสร็จรับเงิน</li>
								<li class="breadcrumb-item active">บิลค้างส่งคืน</li>
							</ol>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<ul class="card nav nav-tabs nav-tabs-2">
								<li class="nav-item"><a class="nav-link active"
									href="${pageContext.request.contextPath}/validateinvoice"><b><i
											class="zmdi zmdi-search"></i> บิลค้างส่งคืน</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/validateinvoice/export"><b><i
											class="ti-export"></i> นำบิลออก</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/validateinvoice/import"><b><i
											class="ti-import"></i> รับบิลเข้า</b></a></li>
							</ul>
						</div>
					</div>
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบบิลคงค้าง
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<form:form id="searchBillScan" method="post"
									commandName="searchBillScanBean"
									action="${pageContext.request.contextPath}/validateinvoice/search">
									<div class="row">
										<div class="col-md-12 mb05">
											<b>ค้นหาจากหมายเลขเอกสาร / ชื่อ - สกุล
												หรือหมายเลขโทรศัพท์ลูกค้า : </b><br> <input type="text"
												class="form-control" id="key" name="key"
												value="${searchBillScanBean.key }">
										</div>
										<div class="col-md-3 mb05">
											<b>ค้นหาจากเขตชุมชน<span class="text-red"> * </span>:
											</b><br> <select name="zoneId" id="zoneId"
												class="form-control" data-plugin="select2">
												<option value="" selected>--- แสดงเขตชุมชนทั้งหมด ---</option>
												<c:forEach var="zoneBean" items="${zoneBeanList}"
													varStatus="i">
													<option value="${zoneBean.id }"
														<c:if test="${searchBillScanBean.zoneId == zoneBean.id }">selected</c:if>>
														${zoneBean.zoneDetail}&nbsp;(${zoneBean.zoneName})</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<b>ค้นหาจากวันที่นำบิลออก<span class="text-red"> *
											</span>:
											</b><br> <input type="text" class="form-control daterange"
												id="dateExportRange" name="dateExportRange"
												value="${searchBillScanBean.dateExportRange }">
										</div>
										<div class="col-md-4 mb05">
											<b>ค้นหาจากผู้รับผิดชอบ / พนักงานเก็บเงิน<span
												class="text-red"> * </span>:
											</b><br> <select name="personnelCashierId"
												class="form-control" id="personnelCashierId">
												<option value="" selected>--- แสดงผู้รับผิดชอบ /
													พนักงานเก็บเงินทั้งหมด ---</option>
												<c:forEach var="personnelCashierBean"
													items="${personnelCashierList}" varStatus="i">
													<option value="${personnelCashierBean.id }"
														<c:if test="${searchBillScanBean.personnelCashierId == personnelCashierBean.id }">selected</c:if>>
														${personnelCashierBean.firstName }&nbsp;
														${personnelCashierBean.lastName }
														<c:if test="${personnelCashierBean.nickName != null }">
															(${personnelCashierBean.nickName })
														</c:if>														
													</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-3">
											<center>
											<br>
												<button type="submit"
													class="col-xs-12 btn btn-info w-min-sm mb-0-25 waves-effect waves-light">
													<i class="ti-search"></i> ค้นหาใบบิล
												</button>
											</center>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-list"></i>&nbsp;&nbsp;รายการบิลค้างส่งคืน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05 mt15">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th><center>หมายเลข</center></th>
														<th>ข้อมูลลูกค้า</th>
														<th><center>เขตชุมชน</center></th>
														<th><center>วันครบกำหนดชำระ</center></th>
														<th align="right">ยอดชำระ</th>
														<th><center>ผู้รับผิดชอบ</center></th>
														<th><center>วันที่นำออกบิล</center></th>
													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${invoiceBeanList != null && invoiceBeanList.size() > 0 }">
															<c:forEach var="invoiceBean" items="${invoiceBeanList}"
																varStatus="i">
																<tr>
																	<td class="td-middle"><center>
																			<a class="cursor-p"
																				href="${pageContext.request.contextPath}/invoice/view/${invoiceBean.id }"
																				target="_blank" data-toggle="tooltip"
																				data-placement="bottom" title=""
																				data-original-title="ดูรายละเอียด"><b>${invoiceBean.invoiceCode }</b></a>
																		</center></td>
																	<td class="td-middle">
																		${invoiceBean.serviceApplication.customer.prefix }
																		${invoiceBean.serviceApplication.customer.firstName }&nbsp;
																		${invoiceBean.serviceApplication.customer.lastName }<br>
																		<small class="text-gray"> <b><i
																				class="pe-7s-call"></i>&nbsp;Tel&nbsp;:&nbsp;</b>${invoiceBean.serviceApplication.customer.contact.mobile}
																	</small>
																	</td>
																	<td class="td-middle"><c:forEach var="addressBean"
																			items="${invoiceBean.serviceApplication.addressList}"
																			varStatus="j">
																			<c:if test="${addressBean.addressType eq '4' }">
																		${addressBean.zoneBean.zoneDetail }&nbsp;<b>(${addressBean.zoneBean.zoneName })</b>
																			</c:if>
																		</c:forEach></td>
																	<td class="td-middle"><center>${invoiceBean.createDateTh }</center></td>
																	<td align="right" class="td-middle"><fmt:formatNumber
																			type="number" maxFractionDigits="2"
																			value="${invoiceBean.amount}" /></td>
																	<td class="td-middle"><center>
																			${invoiceBean.personnelBean.prefix }${invoiceBean.personnelBean.firstName }&nbsp;
																			${invoiceBean.personnelBean.lastName }(${invoiceBean.personnelBean.nickName })
																		</center></td>
																	<td class="td-middle"><center>
																			${invoiceBean.scanOutDateTh }</center></td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="7"><center>ไม่พบข้อมูล</center></td>
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
		$('.daterange').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
		});
	</script>
</body>
</html>
