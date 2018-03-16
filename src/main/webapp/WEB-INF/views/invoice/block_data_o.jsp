<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

					<div class="tab-pane card mb40 <c:if test="${invoiceSearchBean.tab eq 'O'}">active</c:if>" id="block_data_o" role="tabpanel"
						aria-expanded="true">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-5">
									<h4><i class="fa fa-exclamation-triangle" style="color: orange;"></i>&nbsp; เกินวันนัดชำระเงิน</h4>
								</div>
								<div class="col-md-7">
									<span class="text-grey pull-right">
										<i class="fa fa-hourglass-half" style="color: gray;"></i>&nbsp;<b style="color: gray;">รอลูกค้าชำระเงิน</b>
										&nbsp;&nbsp;<i class="fa fa-exclamation-triangle" style="color: orange;"></i>&nbsp;<b style="color: orange;">เกินวันนัดชำระเงิน</b>
										&nbsp;&nbsp;<i class="fa fa-check-circle" style="color: green;"></i>&nbsp;<b style="color: green;">ชำระเงินเรียบร้อย</b>
										&nbsp;&nbsp;<i class="fa fa-times-circle" style="color: red;"></i>&nbsp;<b style="color: red;">ยกเลิกบิล</b>
									</span>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="paging/paging-entries.jsp"></jsp:include>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th width="100px;"><center>เลขที่ใบแจ้งหนี้</center></th>
														<th width="160px;">ชื่อลูกค้า</th>
														<th><center>ประเภทบริการ</center></th>
														<th><center>เขตชุมชนส่งบิล</center></th>
														<th width="140px;"><center>ประเภทใบแจ้งหนี้</center></th>
														<th style="text-align: right;">จำนวนจุดรวม</th>
														<th style="width: 120px;text-align: right;">ยอดเงิน (บาท)</th>
														<th style="width: 111px;"><center>วันที่ออกเอกสาร</center></th>
														<th style="width: 101px;"><center>วันที่นัดชำระ</center></th>
														<th><center>สถานะ</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													
<c:choose>
														<c:when test="${pagination_o.dataListBean.size() > 0}">
															<c:forEach var="invoiceBean" items="${pagination_o.dataListBean}" varStatus="i">
														<tr>
															<td class="td-middle">
																<c:choose>
																	<c:when test="${invoiceBean.isBadDebt() == true && invoiceBean.status ne 'S'}">
																		<center>
																			<a href="${pageContext.request.contextPath}/invoice/view/${invoiceBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${invoiceBean.invoiceCode }</b></a>	
																			<br><small style="color: red;"><b>(หนี้สูญ)</b></small>
																		</center>
																	</c:when>
																	<c:otherwise>
																		<center>
																			<a href="${pageContext.request.contextPath}/invoice/view/${invoiceBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${invoiceBean.invoiceCode }</b></a>		 
																			<c:if test="${invoiceBean.billing == true }">
																						<br><small style="color: orange;"><b>(วางบิลแล้ว)</b></small>
																					</c:if>
																		</center>
																	</c:otherwise>
																</c:choose>
															</td>
															<td class="td-middle">
																${invoiceBean.serviceApplication.customer.prefix}${invoiceBean.serviceApplication.customer.firstName}&nbsp;
																${invoiceBean.serviceApplication.customer.lastName}<br>
																<small style="color: gray;">
																<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${invoiceBean.serviceApplication.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${invoiceBean.serviceApplication.customer.custCode }</a><br>
																<b>โทร&nbsp;:&nbsp;</b>${invoiceBean.serviceApplication.customer.contact.mobile}
																</small>
															</td>
															<td class="td-middle">
																${invoiceBean.serviceApplication.servicepackage.serviceType.packageTypeName}
															</td>
															<td class="td-middle">
																	<c:forEach var="addressBean" items="${invoiceBean.serviceApplication.addressList}" varStatus="j">
																	<c:if test="${addressBean.addressType == 4}">
																		<center>${addressBean.zoneBean.zoneDetail}<br><b>(${addressBean.zoneBean.zoneName })</b></center>
																	</c:if>
																	</c:forEach>
															</td>
															<td class="td-middle">
																<center>
																	<c:if test="${invoiceBean.invoiceType eq 'S'}">ติดตั้งใหม่</c:if>
																	<c:if test="${invoiceBean.invoiceType eq 'O'}">ค่าบริการตามรอบบิล</c:if>
																	<c:if test="${invoiceBean.invoiceType eq 'R'}">แจ้งซ่อม</c:if>
																</center>
															</td>
															<td class="td-middle" align="right">
																${invoiceBean.quantityTotalPoint}
															</td>
															<td class="td-middle" align="right">
																<fmt:formatNumber type="number" maxFractionDigits ="2"
															value="${invoiceBean.amount }"></fmt:formatNumber>
															</td>
															<td class="td-middle">
																<center>
																	${invoiceBean.issueDocDateTh }	 
																</center>
															</td>
															<td class="td-middle">
																<center>
																	${invoiceBean.createDateTh }	 
																</center>
															</td>
															<td class="td-middle"><center>
																	<c:choose>
																		<c:when test="${invoiceBean.status eq 'C'}">
																			<i class="fa fa-times-circle" style="color: red;"></i>
																		</c:when>
																		<c:when test="${invoiceBean.status eq 'W'}">
																			<i class="fa fa-hourglass-half" style="color: gray;"></i>
																		</c:when>
																		<c:when test="${invoiceBean.status eq 'O'}">
																			<i class="fa fa-exclamation-triangle" style="color: orange;"></i>
																		</c:when>
																		<c:when test="${invoiceBean.status eq 'S'}">
																			<i class="fa fa-check-circle" style="color: green;"></i>
																		</c:when>
																	</c:choose>	
																</center>
															</td>
															<td align="center" class="td-middle">
																<c:choose>
																	<c:when test="${invoiceBean.status != \"S\"}">
																		<a href="${pageContext.request.contextPath}/invoice/payment/${invoiceBean.id}"><span class="ti-receipt"
																			data-toggle="tooltip" data-placement="bottom"
																			title="ชำระเงิน"></span></a>&nbsp;
																		<a href="javascript:void(0);" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" onclick="setInvoiceId('${invoiceBean.id}');" >
												 							<span class="ti-printer" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="พิมพ์ใบแจ้งหนี้"> </span> 
												 						</a>
												 						<a href="javascript:void(0);" onclick="openCancelInvoice('${invoiceBean.id}');" >
															 				<span class="fa fa-times-circle" data-toggle="tooltip" data-placement="bottom" title="ยกเลิกบิล" data-original-title="ยกเลิกบิล"> </span> 
															 			</a>
																	</c:when>
																	<c:otherwise>
																			<a href="javascript:void(0);" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" onclick="setInvoiceId('${invoiceBean.id}');" >
												 								<span class="ti-printer" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="พิมพ์ใบเสร็จรับเงิน"> </span> 
												 							</a>
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="10"><center>ไม่พบรายการใบแจ้งหนี้ที่เกินวันชำระเงิน </center></td></tr>
														</c:otherwise>										</c:choose>
												
													

												</tbody>
											</table>
										</div>

									</div>
								</div>
								<c:if test="${pagination_o.dataListBean.size() > 0}"></c:if>
								<jsp:include page="paging/paging_o.jsp"></jsp:include>
							</div>
						</div>
					</div>
	
</body>
</html>
