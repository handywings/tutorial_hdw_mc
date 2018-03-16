<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

					<div class="tab-pane card active mb40" id="block_data" role="tabpanel"
						aria-expanded="true">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-7">
									<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการใบเสร็จรับเงิน / ใบกำกับภาษี</h4>
								</div>

							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th><center>เลขที่ใบเสร็จรับเงิน</center></th>
														<th><center>ชื่อลูกค้า</center></th>
														<th><center>ประเภทใบเสร็จรับเงิน</center></th>
														<th><center>ยอดเงินรับจริง (บาท)</center></th>
														<th><center>รูปแบบการจ่ายเงิน</center></th>
														<th><center>จำนวนครั้งที่พิมพ์</center></th>
														<th><center>วันที่ชำระเงิน</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${pagination.dataListBean.size() > 0}">
															<c:forEach var="receiptBean" items="${pagination.dataListBean}" varStatus="i">
														<tr>
															<td class="td-middle">
																<center>
																	<a href="${pageContext.request.contextPath}/receipt/view/${receiptBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${receiptBean.receiptCode }</b></a>	 
																</center>
															</td>
															<td class="td-middle">
																${receiptBean.invoiceDocumentBean.serviceApplication.customer.prefix}${receiptBean.invoiceDocumentBean.serviceApplication.customer.firstName}&nbsp;
																${receiptBean.invoiceDocumentBean.serviceApplication.customer.lastName}<br>
																<small style="color: gray;">
																<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${receiptBean.invoiceDocumentBean.serviceApplication.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${receiptBean.invoiceDocumentBean.serviceApplication.customer.custCode }</a><br>
																<b>โทร&nbsp;:&nbsp;</b>${receiptBean.invoiceDocumentBean.serviceApplication.customer.contact.mobile}
																</small>
															</td>
															<td class="td-middle">
																<center>
																	<c:if test="${receiptBean.invoiceDocumentBean.invoiceType eq 'S'}">ลูกค้าติดตั้งใหม่</c:if>
																	<c:if test="${receiptBean.invoiceDocumentBean.invoiceType eq 'O'}">ค่าบริการตามรอบบิล</c:if>
																	<c:if test="${receiptBean.invoiceDocumentBean.invoiceType eq 'R'}">แจ้งซ่อม</c:if>
																</center>
															</td>
															<td class="td-middle">
																<center>
																	<fmt:formatNumber type="number" maxFractionDigits ="2" value="${receiptBean.amount }"></fmt:formatNumber>
																	<c:if test="${receiptBean.reductAmount > 0 }">
																		<br><small style="color: orange;"><b>( ลดหนี้ <fmt:formatNumber type="number" maxFractionDigits ="2" value="${receiptBean.reductAmount }"></fmt:formatNumber> บาท )</b></small>
																	</c:if>																		 
																</center>
															</td>
															<td class="td-middle">
																<center>
																	<c:choose>
																		<c:when test="${receiptBean.invoiceDocumentBean.vat eq 'V'}">
																			คิดภาษี
																		</c:when>
																		<c:when test="${receiptBean.invoiceDocumentBean.vat eq 'N'}">
																			ไม่คิดภาษี
																		</c:when>
																		<c:when test="${receiptBean.invoiceDocumentBean.vat eq 'A'}">
																			คิดภาษีมูลค่าพิ่ม 
																			${receiptBean.invoiceDocumentBean.serviceApplication.servicepackage.company.vat} %
<%-- 																			<c:if test="${receiptBean.amount - receiptBean.invoiceDocumentBean.amount > 0 }"> --%>
																				<br>
																				<c:set var="netAmount" value="${(receiptBean.invoiceDocumentBean.amount * 100)/(100+receiptBean.invoiceDocumentBean.serviceApplication.servicepackage.company.vat)}" />
																				<small style="color: green;"><b>( ภาษี <fmt:formatNumber type="number" maxFractionDigits ="2" value="${netAmount * (receiptBean.invoiceDocumentBean.serviceApplication.servicepackage.company.vat/100)}"></fmt:formatNumber> บาท )</b></small>
<%-- 																			</c:if> --%>
																		</c:when>
																	</c:choose>																	 
																</center>
															</td>
															<td class="td-middle">
																<center>
																	${receiptBean.lastPrint }
																</center>
															</td>
															<td class="td-middle">
																<center>
																	${receiptBean.paymentDateTh }	 
																</center>
															</td>
															<td align="center" class="td-middle">
<%-- 																<a href="${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/${receiptBean.invoiceDocumentBean.id}" target="_blank"> --%>
<!-- 																	<span class="ti-printer" data-toggle="tooltip" -->
<!-- 																	data-placement="bottom" title="พิมพ์ใบเสร็จรับเงิน"> -->
<!-- 																	</span> -->
<!-- 																</a> -->
																
																<a href="javascript:void(0);" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" onclick="setInvoiceId('${receiptBean.invoiceDocumentBean.id}');" >
												 					<span class="ti-printer" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="พิมพ์ใบเสร็จรับเงิน"> </span> 
												 				</a>
															</td>
														</tr>
													</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="8"><center>ไม่พบรายการใบเสร็จรับเงิน / ใบกำกับภาษี</center></td></tr>
														</c:otherwise>
													</c:choose>
													

												</tbody>
											</table>
										</div>

									</div>
								</div>
								<c:if test="${pagination.dataListBean.size() > 0}"></c:if>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
							</div>
						</div>
					</div>
	
</body>
</html>
