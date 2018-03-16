<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="receipt" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบเสร็จรับเงิน / ใบกำกับภาษี</title>
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
							<h3>รายละเอียดใบเสร็จรับเงิน / ใบกำกับภาษี</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบการเงิน</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/receipt">รายการใบเสร็จรับเงิน / ใบกำกับภาษี</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบเสร็จรับเงิน</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a href="javascript:void(0);" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" onclick="setInvoiceId('${receiptBean.invoiceDocumentBean.id}');"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบเสร็จรับเงิน
								</button></a>
						</div>
					</div>
					<div class="card mt05 mb40">
						<div class="card-header" style="font-size: 20px;">
							<div class="row">
								<div class="col-md-2">
									<img src='<c:url value="/resources/assets/img/logo-invoice.png" />'>
								</div>
								<div class="col-md-5">
									<b>เลขที่ใบเสร็จรับเงิน : </b>${receiptBean.receiptCode}<br>
									<c:if test="${receiptBean.issueDocDateTh != null && receiptBean.issueDocDateTh != \"\" }">
										<b>วันที่ออกเอกสาร : </b>${receiptBean.issueDocDateTh}
									</c:if>									
								</div>
								<div class="col-md-5" align="right">
									<b>วันที่บันทึกลงในระบบ: </b>${receiptBean.createDateTh}<br>
									<b>ใบแจ้งหนี้อ้างอิง : </b><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" href="${pageContext.request.contextPath}/invoice/view/${receiptBean.invoiceDocumentBean.id}"><b>${receiptBean.invoiceDocumentBean.invoiceCode}</b></a>
								</div>
							</div>

						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">

								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลลูกค้า&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-1">
														<b>รหัสลูกค้า :</b>
													</div>
													<div class="col-md-3"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" href="${pageContext.request.contextPath}/customerregistration/view/${receiptBean.invoiceDocumentBean.serviceApplication.customer.id}"><b>${receiptBean.invoiceDocumentBean.serviceApplication.customer.custCode }</b></a></div>
													<div class="col-md-1">
														<b>ชื่อลูกค้า :</b>
													</div>
													<div class="col-md-3">${receiptBean.invoiceDocumentBean.serviceApplication.customer.firstName }&nbsp;
																${receiptBean.invoiceDocumentBean.serviceApplication.customer.lastName }</div>
													<div class="col-md-2">
														<b>เลขที่ใบสมัคร :</b>
													</div>
													<div class="col-md-2"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${receiptBean.invoiceDocumentBean.serviceApplication.id }"><b>${receiptBean.invoiceDocumentBean.serviceApplication.serviceApplicationNo}</b></a></div>
												</div>

												<div class="row mt05">
													<div class="col-md-1">
														<b>ที่อยู่ :</b>
													</div>
													<div class="col-md-7">
														<c:forEach var="addressBean" items="${receiptBean.invoiceDocumentBean.serviceApplication.customer.addressList }" varStatus="j">
															<c:if test="${addressBean.addressType == 2}">
																</b>${addressBean.collectAddressDetail }
															</c:if>
														</c:forEach>		
													</div>
													<div class="col-md-2">
														<b>สถานที่ใกล้เคียง :</b>
													</div>
													<div class="col-md-2">
														<c:forEach var="addressBean" items="${receiptBean.invoiceDocumentBean.serviceApplication.customer.addressList }" varStatus="j">
															<c:if test="${addressBean.addressType == 2}">
																</b>${addressBean.nearbyPlaces }
															</c:if>
														</c:forEach>	
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt05">
									<div class="col-md-8">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลการชำระเงิน&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row mb05">
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>วิธีการชำระเงิน</b></label><br>
														<c:if test="${receiptBean.paymentType eq 'T'}">
															ชำระผ่านธนาคาร (${receiptBean.bankBean.bankNameTh})
														</c:if>
														<c:if test="${receiptBean.paymentType eq 'C'}">
															ชำระที่บริษัท
														</c:if>
													</div>
													<div class="col-md-4">
														<label for="exampleInputEmail1"><b>วันที่รับชำระเงิน</b></label><br>
														${receiptBean.paymentDateTh}
													</div>
													<div class="col-md-4 blank">
														<label for="exampleInputEmail1"><b>ลงชื่อพนักงานแจ้งชำระเงิน</b></label><br>
														${receiptBean.createByTh}
													</div>
												</div>	
											</div>
										</fieldset>
									</div>
									<div class="col-md-4">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ยอดที่ชำระ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-12">
													<h1 align="center"><fmt:formatNumber type="number" maxFractionDigits ="2" value="${receiptBean.amount }"></fmt:formatNumber> บาท</h1>
												</div>
											</div>
											<c:if test="${receiptBean.reductAmount > 0 }">
												<div class="row">
													<div class="col-md-12" align="center">
														<span class="text-red">( ลดหนี้ให้ <fmt:formatNumber type="number" maxFractionDigits ="2" value="${receiptBean.reductAmount }"></fmt:formatNumber> บาท )</span>
													</div>
												</div>
											</c:if>											
										</fieldset>
									</div>
								</div>		
								
								<c:if test="${fn:length(receiptBean.billHistoryPrintBeanList) >  0}">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ประวัติการพิมพ์&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row mt15">
													<div class="col-md-12 mb05">
														<div class="table-responsive">
															<table class="table table-bordered table-hover" id="tbInvoiceHistory">
																<thead class="thead-default">
																	<tr>
																		<th style="vertical-align: middle;"><center>ลำดับ</center></th>
																		<th style="vertical-align: middle;"><center>วันที่พิมพ์</center></th>
																		<th style="vertical-align: middle;"><center>ผู้พิมพ์</center></th>
																		<th style="vertical-align: middle;"><center>ครั้งที่พิมพ์</center></th>
																	</tr>
																</thead>
																	<c:forEach var="history" items="${receiptBean.billHistoryPrintBeanList}" varStatus="i">
																		<tr>
																			<td><center>${i.count }</center></td>
																			<td><center>${history.createDateTh }</center></td>
																			<td><center>
																				<c:if test="${not empty history.personnelBean }">
																					${history.personnelBean.firstName }&nbsp;
																					${history.personnelBean.lastName } (${history.personnelBean.nickName })
																				</c:if>
																				</center>
																			</td>
																			<td><center>${history.printTime }</center></td>
																		</tr>
																	</c:forEach>
																<tbody>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								</c:if>
														
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
			
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
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-info" data-dismiss="modal" onclick="printInvoiceReceipt();">ตกลง</button>
										</div>
									</div>
								</div>
							</div>
			
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
	var invoiceId = 0;
		$('.datepicker-autoclose').datepicker({
			autoclose : true,
			todayHighlight : true,
			format : "dd/mm/yyyy"
		});
		
		$('.bank').hide();
		$('input[name=paymentType]').change(function() {
			if (this.value == 'bank' || this.value == 'cheque') {
				$('.bank').fadeIn();
			} else {
				$('.bank').hide();
			}
		});
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/"+invoiceId+"/companyId/"+companyId,'_blank');
		}
		
		function setInvoiceId(id){
			invoiceId = id;
		}
		
	</script>
</body>
</html>
