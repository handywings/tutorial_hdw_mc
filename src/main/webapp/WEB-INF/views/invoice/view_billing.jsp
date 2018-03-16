<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;สถานะของใบบิล&nbsp;&nbsp;&nbsp;</legend>
			<div>
				<div class="row">
					<div class="col-md-5">
						<label style="padding-bottom: 5.5px;"
							class="custom-control custom-radio"> <input id="radio1"
							name="payed" type="radio" value="N" <c:if test="${invoiceBean.status ne 'S'}">checked="checked"</c:if>
							class="custom-control-input"> <span
							class="custom-control-indicator"></span> <span
							class="custom-control-description">&nbsp;บิลค้างชำระ</span>
						</label>&nbsp;&nbsp;&nbsp; <label class="custom-control custom-radio">
							<input id="radio2" name="payed" type="radio" value="Y"
							<c:if test="${invoiceBean.status eq 'S'}">checked="checked"</c:if> 
							class="custom-control-input"> <span
							class="custom-control-indicator"></span> <span
							class="custom-control-description">&nbsp;บิลชำระแล้ว</span>
						</label>&nbsp;&nbsp;&nbsp; <label class="custom-control custom-radio">
							<input id="radio3" name="payed" type="radio" value="A" <c:if test="${invoiceBean.billing && invoiceBean.status ne 'S'}">checked="checked"</c:if>
							class="custom-control-input"> <span
							class="custom-control-indicator"></span> <span
							class="custom-control-description">&nbsp;บิลค้างชำระพร้อมวางบิล</span>
						</label>
						<div class="messages"></div>
					</div>
				</div>
			</div>
		</fieldset>
	</div>
</div>