<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="printinvoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>มอบหมายพนักงานเก็บเงิน</title>
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
							<h3>พิมพ์ใบแจ้งหนี้</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">มอบหมายพนักงานเก็บเงิน</li>
							</ol>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12">
							<ul class="card nav nav-tabs nav-tabs-2">
								<li class="nav-item"><a class="nav-link" 
									href="${pageContext.request.contextPath}/printinvoice/cashier"><b><i class="fa fa-print"></i> มอบหมายพนักงานเก็บเงิน</b></a></li>
								<li class="nav-item"><a class="nav-link active"
									href="${pageContext.request.contextPath}/printinvoice"><b><i class="fa fa-print"></i> รายละเอียดการพิมพ์</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/printinvoice/cashierconfig"><b><i
											class="ti-settings"></i> ตั้งค่ามอบหมายพนักงานเก็บเงิน</b></a></li>
							</ul>
						</div>
					</div>
					
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาข้อมูลรายละเอียดการพิมพ์
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="wSeachInvoice" method="post" commandName="searchBillScanBean" action="${pageContext.request.contextPath}/printinvoice/search">
										<div class="col-md-2 mb05">
											<select id="month" name="month" path="month" class="form-control">
											<c:forEach items="${months}" var="month" varStatus="i" >
												<option value="${i.count}" <c:if test="${i.count == searchBillScanBean.month}">selected="selected"</c:if> >เดือน${month}</option>
											</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<select id="year" name="year" path="year" class="form-control">
											<c:forEach items="${years}" var="year">
												<option value="${year}" <c:if test="${year == searchBillScanBean.year}">selected="selected"</c:if> >ปี ${year}</option>
											</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<b>พนักงานเก็บเงิน</b>
										</div>
										<div class="col-md-4 mb05">
											<select id="personnelCashierId" name="personnelCashierId" path="personnelCashierId" class="form-control">
												<option value="0" selected="selected">--- แสดงข้อมูลพนักงานเก็บเงินทั้งหมด ---</option>
												<c:forEach items="${personnelBeans}" var="personnelBean">
													<option value="${personnelBean.id }" <c:if test="${personnelBean.id == searchBillScanBean.personnelCashierId}">selected="selected"</c:if> >${personnelBean.firstName } ${personnelBean.lastName } (${personnelBean.nickName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2">
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
					
					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-list"></i>&nbsp;&nbsp;รายละเอียดการพิมพ์
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">								
								<div class="row mb05 mt15">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-1 table-hover" id="table_print_detail" style="width: 4000px !important;">
												<thead class="thead-default">
													<tr>
														<th rowspan="2" width="70px;"><center>ลำดับที่</center></th>
														<th rowspan="2" style="width: 300px;"><center>ข้อมูลพนักงานเก็บเงิน</center></th>
														<th colspan="31"><center>แสดงจำนวนบิลและมูลค่าบิล</center></th>
													</tr>
													<tr>			
														<c:forEach var = "i" begin = "1" end = "${lastDayOfMonth}">	
															<th><center>${i}</center></th>
														</c:forEach>
													</tr>
												</thead>
												<tbody>
												<c:forEach items="${invoiceHistoryPrintBeanList}" var="invoiceHistoryPrintBean" varStatus="i">
													<tr>
														<td><center>${i.count}</center></td>
														<td>
															${invoiceHistoryPrintBean.personnelBean.firstName}&nbsp;${invoiceHistoryPrintBean.personnelBean.lastName}
															<c:if test="${invoiceHistoryPrintBean.personnelBean.nickName != name }">
																(${invoiceHistoryPrintBean.personnelBean.nickName })
															</c:if>
														</td>
														<c:forEach items="${invoiceHistoryPrintBean.invoiceDocumentBeanList}" var="invoiceDocumentBean">
															<td><center>${invoiceDocumentBean.quantityBill} ( <fmt:formatNumber type="number" maxFractionDigits="3" pattern="#,##0.00" value="${invoiceDocumentBean.amount}" /> บาท )</center></td>
														</c:forEach>
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
