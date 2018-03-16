<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="card-header" style="font-size: 20px;">
	<div class="row">
		<div class="col-md-2">
			<img src='<c:url value="/resources/assets/img/logo-invoice.png" />'>
		</div>
		<div class="col-md-5">
			<input type="hidden" id="invoiceId" value="${invoiceBean.id }" /> <b>เลขที่ใบแจ้งหนี้
				: </b><b>${invoiceBean.invoiceCode }</b><br> <b>วันที่ออกเอกสาร : </b>${invoiceBean.issueDocDateTh }
		</div>
		<div class="col-md-5" align="right">
			<b>วันที่นัดชำระ : </b>
			<c:choose>
				<c:when test="${invoiceBean.createDateTh ne '' && invoiceBean.createDateTh ne null}">
					${invoiceBean.createDateTh }
				</c:when>
				<c:otherwise>
					-
				</c:otherwise>
			</c:choose>
			<br> <b>สถานะ : </b>
			<c:choose>
				<c:when test="${invoiceBean.status eq 'W'}">
					<i class="ion-record text-orange"></i> รอลูกค้าชำระเงิน
										</c:when>
				<c:when test="${invoiceBean.status eq 'S'}">
					<i class="ion-record text-green"></i> ชำระเงินเรียบร้อย
										</c:when>
				<c:when test="${invoiceBean.status eq 'O'}">
					<i class="ion-record text-red"></i> เกินวันนัดชำระเงิน
										</c:when>
			</c:choose>
		</div>
	</div>

</div>

