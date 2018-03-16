<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;ข้อมูลลูกค้า&nbsp;&nbsp;&nbsp;</legend>
			<div>
				<div class="row">
					<div class="col-md-1">
						<b>รหัสลูกค้า :</b>
					</div>
					<div class="col-md-3"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" target="_blank" href="${pageContext.request.contextPath}/customerregistration/view/${invoiceBean.serviceApplication.customer.id }"><b>${invoiceBean.serviceApplication.customer.custCode }</b></a></div>
					<div class="col-md-1">
						<b>ชื่อลูกค้า :</b>
					</div>
					<div class="col-md-3">
						${invoiceBean.serviceApplication.customer.firstName }&nbsp;
						${invoiceBean.serviceApplication.customer.lastName }</div>
					<div class="col-md-2">
						<b>เลขที่ใบสมัคร :</b>
					</div>
					<div class="col-md-2"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${invoiceBean.serviceApplication.id }"><b>${invoiceBean.serviceApplication.serviceApplicationNo }</b></a></div>
				</div>

				<div class="row mt05">
					<c:forEach var="addressBean"
						items="${invoiceBean.serviceApplication.addressList}"
						varStatus="i">
						<c:if test="${addressBean.addressType eq '4'}">
							<div class="col-md-1">
								<b>ที่อยู่ :</b>
							</div>
							<div class="col-md-7">${addressBean.collectAddressDetail }<br><b>(${addressBean.zoneBean.zoneDetail })</b></div>
							<div class="col-md-2">
								<b>สถานที่ใกล้เคียง :</b>
							</div>
							<div class="col-md-2">${addressBean.nearbyPlaces }</div>
						</c:if>
					</c:forEach>
				</div>
				<div class="row mt05">
					<div class="col-md-1">
						<b>แพ็กเกจ :</b>
					</div>
					<div class="col-md-7">${invoiceBean.serviceApplication.servicepackage.packageName } (${invoiceBean.serviceApplication.servicepackage.packageCode })</div>
					<div class="col-md-2">
						<b>วันที่ใช้บริการ :</b>
					</div>
					<div class="col-md-2">${dateSuccessInstall }</div>
				</div>
			</div>
		</fieldset>
	</div>
</div>