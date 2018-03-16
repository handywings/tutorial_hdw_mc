<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!--  bill ค้างชำระ -->
<div class="row">
	<div class="col-md-4">
		<b>รอบบิลค้างชำระ</b>
	</div>
</div>
<div class="row mb05">
	<div class="col-md-12 mb05">
		<div class="table-responsive">
			<table class="table table-bordered mb-0 table-hover">
				<thead class="thead-bg">
					<tr>
						<th><center>เลขที่ใบแจ้งหนี้</center></th>
						<th><center>รายละเอียด</center></th>
						<th><center>ยอดชำระ</center></th>
						<th><center>สถานะ</center></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${worksheetBean.serviceApplication.invoiceOverBill != null && worksheetBean.serviceApplication.invoiceOverBill.size() > 0 }">						
							<c:forEach var="bill" items="${worksheetBean.serviceApplication.invoiceOverBill}" varStatus="i">
								<tr>
									<td class="td-middle"><center><a href="${pageContext.request.contextPath}/invoice/view/${bill.id }" target="_blank"><b>${bill.invoiceCode }</b></a></center></td>
									<td><center>
									<c:choose>
										<c:when test="${bill.invoiceType eq 'S'}">
											ติดตั้งอุปกรณ์ใหม่
										</c:when>
										<c:when test="${bill.invoiceType eq 'R'}">
											${bill.worksheet.workSheetTypeText}
										</c:when>
										<c:when test="${bill.invoiceType eq 'O'}">
											ค่าสมาชิกรายเดือน (${bill.serviceRoundDate })
										</c:when>
									</c:choose>
									</center></td>
									<td><center>
									<fmt:formatNumber type="number"
												maxFractionDigits="2"
												value="${bill.amount}" /> บาท</center></td>
									<td><center>ค้างชำระ</center></td>
								</tr>
							</c:forEach>						
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4"><center>ไม่พบรายการค้างชำระ</center></td>
							</tr>
						</c:otherwise>
					</c:choose>
					

				</tbody>
			</table>
		</div>

	</div>
</div>

<!--  bill รอบปัจจุบัน -->
<div class="row">
	<div class="col-md-4">
		<b>รอบบิลปัจจุบัน</b>
	</div>
</div>
<div class="row mb05">
	<div class="col-md-12 mb05">
		<div class="table-responsive">
			<table class="table table-bordered mb-0 table-hover">
				<thead class="thead-bg">
					<tr>
						<th><center>เลขที่ใบแจ้งหนี้</center></th>
						<th><center>รายละเอียด</center></th>
						<th><center>ยอดชำระ</center></th>
						<th><center>สถานะ</center></th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${worksheetBean.serviceApplication.invoiceCurrentBill != null && worksheetBean.serviceApplication.invoiceCurrentBill.size() > 0 }">
							<c:forEach var="bill" items="${worksheetBean.serviceApplication.invoiceCurrentBill}" varStatus="i">
								<tr>
									<td class="td-middle"><center><a href="${pageContext.request.contextPath}/invoice/view/${bill.id }" target="_blank"><b>${bill.invoiceCode }</b></a></center></td>
									<td><center>
									<c:choose>
										<c:when test="${bill.invoiceType eq 'S'}">
											ติดตั้งอุปกรณ์ใหม่
										</c:when>
										<c:when test="${bill.invoiceType eq 'R'}">
											${bill.worksheet.workSheetTypeText}
										</c:when>
										<c:when test="${bill.invoiceType eq 'O'}">
											ค่าสมาชิกรายเดือน (${bill.serviceRoundDate })
										</c:when>
									</c:choose>
									</center></td>
									<td><center>
									<fmt:formatNumber type="number"
												maxFractionDigits="2"
												value="${bill.amount}" /> บาท</center></td>
									<td><center>รอจ่าย</center></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4"><center>ไม่พบรายการค้างชำระ</center></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
				</tbody>
			</table>
		</div>

	</div>
</div>
<!--  bill ค้างชำระ -->

<!--  bill ล่วงหน้า -->
<!-- <div class="row"> -->
<!-- 	<div class="col-md-4"> -->
<!-- 		<b>ค่าบิลล่วงหน้า</b> -->
<!-- 	</div> -->
<!-- </div> -->
<!-- <div class="row mb05"> -->
<!-- 	<div class="col-md-12 mb05"> -->
<!-- 		<div class="table-responsive"> -->
<!-- 			<table class="table table-bordered mb-0"> -->
<!-- 				<thead class="thead-bg"> -->
<!-- 					<tr> -->
<%-- 						<th><center>เลขที่ใบแจ้งหนี้</center></th> --%>
<%-- 						<th><center>รายละเอียด</center></th> --%>
<%-- 						<th><center>ยอดชำระ</center></th> --%>
<%-- 						<th><center>สถานะ</center></th> --%>
<!-- 					</tr> -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
<!-- 					<tr> -->
<%-- 						<td class="td-middle"><center>00000291</center></td> --%>
<%-- 						<td><center>ค่าบิลล่วงหน้า (24 ธันวาคม 2559 - 24 มกราคม 2560)</center></td> --%>
<%-- 						<td><center>400.00 บาท</center></td> --%>
<%-- 						<td><center>ค้างชำระ</center></td> --%>
<!-- 					</tr> -->

<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</div> -->

<!-- 	</div> -->
<!-- </div> -->