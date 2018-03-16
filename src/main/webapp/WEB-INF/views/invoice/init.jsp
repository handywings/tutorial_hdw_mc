<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="invoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบแจ้งหนี้</title>
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
						<div class="col-md-8">
							<h3>รายการใบแจ้งหนี้</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">รายการใบแจ้งหนี้</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a href="${pageContext.request.contextPath}/invoice/exportExcel" target="_blank" >
								<button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="fa fa-file-excel-o"></i></span>Export Excel
 								</button>
 							</a>
 						</div>
					</div>
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบแจ้งหนี้
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="wSeachInvoice" method="post" commandName="invoiceSearchBean" action="${pageContext.request.contextPath}/invoice/search">
										<div class="col-md-4 mb05">
											<input type="hidden" id="itemPerPage" name="itemPerPage" value="${invoiceSearchBean.itemPerPage}" />
											<input type="hidden" id="tab" name="tab" value="${invoiceSearchBean.tab}" />
											<input type="text" name="key" class="form-control" value="${invoiceSearchBean.key}"
												placeholder="เลขที่ใบสมัคร ใบแจ้งหนี้ รหัส ชื่อสกุล อีเมล์ หรือเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-3 mb05">
											<input class="form-control" type="text" id="daterange1" name="daterange1" value="${invoiceSearchBean.daterange1}" placeholder="วันที่ชำระ" >
										</div>
										<div class="col-md-3 mb05">
											<select id="servicePackageTypeId" name="servicePackageTypeId" path="servicePackageTypeId" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">ประเภทบริการทั้งหมด</option>
												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean">
													<option value="${servicePackageTypeBean.id }" <c:if test="${invoiceSearchBean.servicePackageTypeId == servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<label class="custom-control custom-checkbox">
											<input <c:if test="${invoiceSearchBean.searchbill != null }"> checked="checked" </c:if> type="checkbox" class="custom-control-input" name="searchbill" value="searchbill"> 
											<span class="custom-control-indicator"></span>
											<span class="custom-control-description">&nbsp;<b style="color: gray;">เฉพาะที่วางบิล</b></span></label>
											
											<label class="custom-control custom-checkbox">
											<input <c:if test="${invoiceSearchBean.searchbadDebt != null }"> checked="checked" </c:if> type="checkbox" class="custom-control-input" name="searchbadDebt" value="searchbadDebt"> 
											<span class="custom-control-indicator"></span>
											<span class="custom-control-description">&nbsp;<b style="color: gray;">เฉพาะหนี้สูญ</b></span></label>
										</div>

										<div class="col-md-4 mb05">
											<select id="fullname" name="invoiceType" class="form-control">
												<option value="" selected="selected">ประเภทใบแจ้งหนี้ทั้งหมด</option>
												<option value="S" <c:if test="${invoiceSearchBean.invoiceType eq 'S'}">selected="selected"</c:if> >ใบแจ้งหนี้ลูกค้าติดตั้งใหม่</option>
												<option value="O" <c:if test="${invoiceSearchBean.invoiceType eq 'O'}">selected="selected"</c:if> >ใบแจ้งหนี้ค่าบริการตามรอบบิล</option>
												<option value="R" <c:if test="${invoiceSearchBean.invoiceType eq 'R'}">selected="selected"</c:if> >ใบแจ้งหนี้ตามใบแจ้งซ่อม</option>
											</select>
										</div>
										<div class="col-md-3 mb05">
											<input class="form-control" type="text" id="daterange" name="daterange" value="${invoiceSearchBean.daterange}" placeholder="วันที่นัดชำระ" >
										</div>
										<div class="col-md-3 mb05">
											<select id="zone" name="zone" path="zone" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">เขตชุมชนทั้งหมด</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${invoiceSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail } (${zoneBean.zoneName })</option>
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
					<div class="row">
						<div class="col-md-12">
							<nav class="navbar navbar-light bg-white navbar-bottom-line b-a mb-1">
								<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar4" aria-controls="exCollapsingNavbar4" aria-expanded="false" aria-label="Toggle navigation"></button>
								<div class="collapse navbar-toggleable-sm" id="exCollapsingNavbar4">
									<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
										<li class="nav-item">
										<a class="nav-link <c:if test="${invoiceSearchBean.tab eq 'All'}">active</c:if>" data-toggle="tab" onclick="setTab('All');"
										href="#block_data_all" role="tab" aria-expanded="true">
										<b>ใบแจ้งหนี้ทั้งหมด ( ${pagination.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${invoiceSearchBean.tab eq 'W'}">active</c:if>" data-toggle="tab" onclick="setTab('W');"
										href="#block_data_w" role="tab" aria-expanded="false">
										<b>รอลูกค้าชำระเงิน ( ${pagination_w.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${invoiceSearchBean.tab eq 'O'}">active</c:if>" data-toggle="tab" onclick="setTab('O');"
										href="#block_data_o" role="tab" aria-expanded="false">
										<b>เกินวันชำระเงิน ( ${pagination_o.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${invoiceSearchBean.tab eq 'S'}">active</c:if>" data-toggle="tab" onclick="setTab('S');"
										href="#block_data_s" role="tab" aria-expanded="false">
										<b>ชำระเงินเรียบร้อยแล้ว ( ${pagination_s.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${invoiceSearchBean.tab eq 'C'}">active</c:if>" data-toggle="tab" onclick="setTab('C');"
										href="#block_data_c" role="tab" aria-expanded="false">
										<b>ยกเลิกบิล ( ${pagination_c.totalItem} )</b></a></li>
									</ul>
								</div>
							</nav>
						</div>
					</div>
					
							<div class="modal fade" id="modalPrintInvoiceReceipt" tabindex="-1" role="dialog"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="exampleModalLabel">เลือกบริษัท</h4>
										</div>
										<div class="modal-body">
											<form>
												<div class="form-group">
													<select id="company_for_print" name="company" class="form-control" path="company">
														<c:forEach items="${companyList}" var="company">
														<option value="${company.id}" >${company.companyName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="row">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>ส่วนลด</b></label> 
														<input type="number" min=0 class="form-control" 
														placeholder="ส่วนลด" id="discount" value="0">
													</div>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-info" data-dismiss="modal" onclick="printInvoiceReceipt();">ตกลง</button>
										</div>
									</div>
								</div>
							</div>
							
							
					
					<div class="tab-content">
						<jsp:include page="block_data_all.jsp"></jsp:include>
						<jsp:include page="block_data_w.jsp"></jsp:include>
						<jsp:include page="block_data_o.jsp"></jsp:include>
						<jsp:include page="block_data_s.jsp"></jsp:include>
						<jsp:include page="block_data_c.jsp"></jsp:include>
					</div>
			</div>
			<!-- Footer -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			
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
	var invoiceId = 0;
	$(document).ready(function() {
		<c:if test="${empty invoiceSearchBean.daterange}">
		$('#daterange').val('');
		</c:if>
		
		<c:if test="${empty invoiceSearchBean.daterange1}">
		$('#daterange1').val('');
		</c:if>
	});
	
		$('input[name="daterange"]').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
		});
		
		$('input[name="daterange1"]').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
		});
		
		function setTab(tab){
		    $('#tab').val(tab);
		};
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			var discount = $('#discount').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/"+invoiceId+"/companyId/"+companyId+"/discount/"+discount,'_blank');
		}
		
		function setInvoiceId(id){
			invoiceId = id;
		}
		
	</script>
	
</body>
</html>
