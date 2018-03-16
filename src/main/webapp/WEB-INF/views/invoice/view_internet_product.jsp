<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="countTbInternetCheckDisplay" value="0" />
<c:forEach var="productItem" items="${invoiceBean.worksheet.productItemList}" varStatus="i">
	<c:choose>
		<c:when test="${productItem.type eq 'I' }">
			<c:set var="countTbInternetCheckDisplay" value="${countTbInternetCheckDisplay + 1}" />
		</c:when>
	</c:choose>
</c:forEach>
<c:if test="${countTbInternetCheckDisplay > 0 }">
<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;หมายเหตุ มีการใช้งานบริการอินเตอร์เน็ต ตามรายละเอียดดังนี้&nbsp;&nbsp;&nbsp;</legend>
			<div>
				<div class="row mt15">
					<div class="col-md-12 mb05">
						<div class="table-responsive">
							<table class="table table-bordered table-hover" id="tbInvoiceInternetProductList">
								<thead class="thead-default">
									<tr>
										<th style="vertical-align: middle;"><center>ลำดับ</center></th>
										<th style="vertical-align: middle;">ชื่อแพกเกจ</th>
										<th style="vertical-align: middle;"><center>USERNAME</center></th>
										<th style="vertical-align: middle;"><center>PASSWORD</center></th>
									</tr>
								</thead>
								<tbody>
								<c:set var="countTbInternet" value="1" />
									<c:forEach var="productItem" items="${invoiceBean.worksheet.productItemList}" varStatus="i">
										<c:choose>
											<c:when test="${productItem.type eq 'I' }">
												<c:forEach var="productWorkSheetItem"
													items="${productItem.productItemWorksheetBeanList}"
													varStatus="j">
													<tr>
														<td>
															<center>
																${countTbInternet }
																<c:set var="countTbInternet" value="${countTbInternet + 1}" />
															</center>
														</td>
														<td>${productWorkSheetItem.internetProductBeanItem.internetProductBean.productName }</td>
														<td><center>${productWorkSheetItem.internetProductBeanItem.userName }</center></td>
														<td><center>${productWorkSheetItem.internetProductBeanItem.password }</center></td>
													</tr>
												</c:forEach>
											</c:when>
										</c:choose>
									</c:forEach>
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