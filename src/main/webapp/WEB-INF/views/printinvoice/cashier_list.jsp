<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
						<div class="col-md-8">
							<h3>พิมพ์ใบแจ้งหนี้</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">มอบหมายพนักงานเก็บเงิน</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a href="${pageContext.request.contextPath}/printinvoice/exportExcel" target="_blank" >
								<button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="fa fa-file-excel-o"></i></span>Export Excel
 								</button>
 							</a>
 						</div>
					</div>

					<div class="row">
						<div class="col-md-12">
							<ul class="card nav nav-tabs nav-tabs-2">
								<li class="nav-item"><a class="nav-link active"
									href="${pageContext.request.contextPath}/printinvoice/cashier"><b><i
											class="fa fa-print"></i> มอบหมายพนักงานเก็บเงิน</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/printinvoice"><b><i
											class="fa fa-print"></i> รายละเอียดการพิมพ์</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/printinvoice/cashierconfig"><b><i
											class="ti-settings"></i> ตั้งค่ามอบหมายพนักงานเก็บเงิน</b></a></li>
							</ul>
						</div>
					</div>

					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหารายการใบแจ้งชำระหนี้
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
									<form:form id="wSeachInvoice" method="post"
										commandName="searchBillScanBean"
										action="${pageContext.request.contextPath}/printinvoice/cashier/search">
										<div class="row">
											<div class="col-md-2">
												<b>วันที่นัดชำระ</b>
											</div>
											<div class="col-md-2">
												<input class="form-control" type="text" id="exportDate" name="exportDate" value="${searchBillScanBean.exportDate}" placeholder="วันที่นัดชำระ" >
											</div>
											<div class="col-md-2">
												<b>พื้นที่เก็บเงิน / เขตชุมชน</b>
											</div>
											<div class="col-md-4 mb05">
												<select name="zoneId" id="zoneId" class="form-control" data-plugin="select2">
													<option value="" selected>--- แสดงพื้นที่เก็บเงิน /
														เขตชุมชนทั้งหมด ---</option>
													<c:forEach var="zoneBean" items="${zoneBeanList}"
														varStatus="i">
														<option value="${zoneBean.id }"
															<c:if test="${searchBillScanBean.zoneId == zoneBean.id }">selected</c:if>>
															${zoneBean.zoneDetail}&nbsp;(${zoneBean.zoneName})</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-2 mb05">
												<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
													<span class="ti-search"></span>
													ค้นหา
												</button>
											</div>
										</div>
										<div class="row">
											<div class="col-md-3">
												<b>มอบหมายพนักงานเก็บเงินผู้รับผิดชอบ</b>
											</div>
											<div class="col-md-9 mb05">
												<select id="personnelCashierId" name="personnelCashierId" class="form-control">
													<c:forEach items="${personnelBeans}" var="personnelBean">
														<option value="${personnelBean.id }"
															<c:if test="${personnelBean.id == searchBillScanBean.personnelCashierId}">selected="selected"</c:if>>${personnelBean.firstName }
															${personnelBean.lastName } (${personnelBean.nickName })</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</form:form>
							</div>
						</div>
					</div>

					<div class="card mt05 ">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-list"></i>&nbsp;&nbsp;รายการใบแจ้งหนี้
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05 mt15">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1-2">
												<thead class="thead-default">
													<tr>
														<th><center><label class="custom-control custom-checkbox">
																<input type="checkbox" id="select_all" checked
																class="custom-control-input"> <span
																class="custom-control-indicator"></span> <span
																class="custom-control-description"></span>
														</label></center></th>
														<th><center>เลขที่ใบแจ้งหนี้</center></th>
														<th>ข้อมูลลูกค้า</th>
														<th><center>เขตชุมชน</center></th>
														<th><center>สถานที่ใกล้เคียง</center></th>
														<th><center>ยอดเงิน</center></th>
														<th><center>วันนัดชำระ</center></th>
														<th><center>พนักงานเก็บปัจจุบัน</center></th>
<%-- 														<th><center>จำนวนครั้งที่พิมพ์</center></th> --%>
													</tr>
												</thead>
												<tbody>
																
													<c:choose>
														<c:when
															test="${invoiceBeanLists != null && invoiceBeanLists.size() > 0 }">
															<c:forEach var="invoiceBeanList"
																items="${invoiceBeanLists}" varStatus="i">
																<tr
																	<c:if test="${invoiceBeanList.printCount > 0}"> style=" background-color: rgba(250, 235, 215, 0.57); " </c:if>>
																	<td><center><label class="custom-control custom-checkbox">
																			<input type="checkbox" checked
																			class="custom-control-input checkbox_inv"
																			value="${invoiceBeanList.id}"> <span
																			class="custom-control-indicator"></span> <span
																			class="custom-control-description"></span>
																			<input type="hidden" value="${invoiceBeanList.serviceApplication.id}" >
																	</label></center></td>
																	<td>
																		<center>
																			<a
																				href="${pageContext.request.contextPath}/invoice/view/${invoiceBeanList.id}"
																				data-toggle="tooltip" data-placement="bottom"
																				title="ดูรายละเอียด" target="_blank"><b>${invoiceBeanList.invoiceCode}</b></a>
																		</center>
																	</td>
																	<td>${invoiceBeanList.serviceApplication.customer.firstName}
																		${invoiceBeanList.serviceApplication.customer.lastName}</td>
																	<td><center>
																			<c:forEach var="addressApplicationBean"
																				items="${invoiceBeanList.serviceApplication.addressList}"
																				varStatus="i">
																				<c:if
																					test="${addressApplicationBean.addressType eq '4' }">	
																${addressApplicationBean.zoneBean.zoneDetail } (${addressApplicationBean.zoneBean.zoneName})
															</c:if>
																			</c:forEach>
																		</center></td>
																	<td><center>
																	<c:forEach var="addressApplicationBean"
																				items="${invoiceBeanList.serviceApplication.customer.addressList}"
																				varStatus="i">
																				<c:if
																					test="${addressApplicationBean.addressType eq '1' }">	
																${addressApplicationBean.nearbyPlaces}
															</c:if></c:forEach>
																	</center></td>
																	<td align="right"><fmt:formatNumber type="number"
																			maxFractionDigits="2"
																			value="${invoiceBeanList.amount}" /></td>
																	<td>${invoiceBeanList.createDateTh }</td>
																	<td><center>${invoiceBeanList.personnelBean.firstName}
																				${invoiceBeanList.personnelBean.lastName}
																	</center></td>
<%-- 																	<td><center>${invoiceBeanList.printCount}</center></td> --%>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="8"><center>ไม่พบข้อมูล</center></td>
															</tr>
														</c:otherwise>
													</c:choose>

												</tbody>
											</table>
										</div>
									</div>
									
									<div class="row mt15 mb15">
										<div class="col-md-12">
											<center>
												<button type="button" id="submit_print" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
													<span class="btn-label"><span class="ti-check"></span></span>
													<b>มอบหมายพนักงานเก็บเงิน</b>
												</button>
											</center>
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
		
			var table = "";
			$( document ).ready(function() { 
				table = $('#table-1-2').dataTable({searching: false, paging: true});	
			});
		
			$('input[name="exportDate"]').daterangepicker({
				buttonClasses : [ 'btn', 'btn-sm' ],
				applyClass : 'btn-success',
				cancelClass : 'btn-inverse',
			});
			
			$("#select_all").click(function() {
				var all = $(this);
			      table.$('input[type="checkbox"]').each(function(){
						$(this).prop('checked', all.is(':checked'));
				    });
			});

			$('.daterange').datepicker({
				autoclose : true,
				format : 'dd-mm-yyyy',
				todayHighlight : 'TRUE'
			});

			$('#personnelCashierId').change(function() {
				$("#wSeachInvoice").submit();
				
			});
			
			$("#submit_print")
					.click(
							function() {
								var cashier = $('#personnelCashierId').val();
								// 		console.log(cashier);
								var isSubmit = false;
								if ("0" == cashier) {
									openAlert('warning', 'เลือกผู้รับผิดชอบ');
								} else {
									var invoiceDocumentBeanList = [];
									table.$('input[type="checkbox"]').each(function(){
										var invoiceDocumentBean = {};
										if(this.checked){
											invoiceDocumentBean.id = this.value;
											var personnelBean = {};
											personnelBean.id = $('#personnelCashierId').val();
											invoiceDocumentBean.personnelBean = personnelBean;
											invoiceDocumentBeanList
													.push(invoiceDocumentBean);
											isSubmit = true;
										}
									});
													
									console.log(invoiceDocumentBeanList);
									if (!isSubmit) {
										openAlert('warning', 'เลือกใบแจ้งหนี้');
									} else {
										$('.preloader').modal('show');
										$
												.ajax({
													type : "POST",
													contentType : "application/json; charset=utf-8",
													url : "${pageContext.request.contextPath}/printinvoice/assigninvoice",
													dataType : 'json',
													data : JSON
															.stringify(invoiceDocumentBeanList),
													//timeout : 100000,
													success : function(data) {
														window.open('${pageContext.request.contextPath}/financialreport/invoiceOrReceiptList/exportPdf','_blank');
														window.location.reload();
													},
													error : function(e) {
														console.log("ERROR: ",
																e);
													},
													done : function(e) {
														console.log("DONE");
													}
												});
									}
								}

							});
		</script>
</body>
</html>
