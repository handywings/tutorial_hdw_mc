<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="refund" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการคืนเงินมัดจำ</title>
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
							<h3>รายการคืนเงินมัดจำ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบการเงิน</li>
								<li class="breadcrumb-item active">รายการคืนเงินมัดจำ</li>
							</ol>
						</div>
					</div>
					<div class="card mt15">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหารายการคืนเงินมัดจำ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
								<form:form method="post" commandName="applicationSearchBean" action="refund/search">
									<div class="col-md-6 mb05">
										<b>ค้นหาจากเลขใบสมัคร ชื่อสกุล เลขประชาชน อีเมล์
											หรือหมายเลขโทรศัพท์ของลูกค้า</b><br> <input type="text"
											class="form-control" id="key" path="key" name="key"
											placeholder="ระบุเลขใบสมัคร  ชื่อสกุล เลขประชาชน อีเมล์  หรือหมายเลขโทรศัพท์ของลูกค้า..." value="${applicationSearchBean.key }">
									</div>
									<div class="col-md-4 mb05">
										<b>ค้นหาจากวันที่ยกเลิกบริการ<span class="text-red">
												* </span>:
										</b><br> <input type="text" id="dateRangeRefund" path="dateRangeRefund" name="dateRangeRefund"
										class="form-control daterange" value="${applicationSearchBean.dateRangeRefund }">
									</div>
									<div class="col-md-2">
										&nbsp;<br>
										<center>
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</center>
									</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-list"></i>&nbsp;&nbsp;รายการคืนเงินมัดจำ
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
														<th class="td-middle" width="80px;"><center>เลขใบสมัคร</center></th>
														<th class="td-middle"><center>ชื่อ Package
																บริการ</center></th>
														<th class="td-middle" width="150px;">ข้อมูลลูกค้า</th>
														<th class="td-middle" width="100px;"><center>วันที่ยกเลิกบริการ</center></th>
														<th class="td-middle" align="right" width="100px;">จำนวนเงินมัดจำ</th>
														<th></th>
													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${serviceApplicationBeans != null && serviceApplicationBeans.size() > 0 }">
															<c:forEach var="serviceApplicationBean"
																items="${serviceApplicationBeans}" varStatus="i">
																<tr>
																	<td class="td-middle"><center>
																			<b>${serviceApplicationBean.serviceApplicationNo}</b>
																		</center></td>
																	<td class="td-middle">${serviceApplicationBean.servicepackage.packageName}</td>
																	<td class="td-middle">${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;${serviceApplicationBean.customer.lastName}</td>
																	<td class="td-middle"><center>${serviceApplicationBean.cancelServiceDate}</center></td>
																	<td align="right" class="td-middle">${serviceApplicationBean.refund}</td>
																	<c:if test="${empty serviceApplicationBean.refundDate}">
																		<td align="center" class="td-middle">
																		<perm:permission object="M39.add" >
																			<a class="cursor-p"
																				onclick="openDialogConfirm(${serviceApplicationBean.id});">
																					<button type="button"
																						class="btn btn-warning w-min-sm mb-0-25 waves-effect waves-light">คืนเงินมัดจำ</button>
																			</a>
																		</perm:permission>
																		
																		</td>
																	</c:if>
																	<c:if
																		test="${not empty serviceApplicationBean.refundDate}">
																		<td align="center" class="td-middle"
																			style="color: green;"><b>คืนเงินเรียบร้อย</b><br>
																		<small style="color: gray;">${serviceApplicationBean.refundDate}</small></td>
																	</c:if>
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

			<!-- modal confirm -->
			<jsp:include page="modal_confirm.jsp"></jsp:include>
			<!-- end modal confirm -->

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
