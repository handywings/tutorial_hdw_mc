<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_header_detail_charge">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
			<div class="row">
				<div class="col-md-12">
					<fieldset>
						<legend>&nbsp;&nbsp;&nbsp;เพิ่มสินค้า (ค่าแรง/ค่าบริการ)&nbsp;&nbsp;&nbsp;</legend>
						<div class="row">
							<div class="col-md-4">
								<b>ชื่อบริการ</b><br> <input type="text"
									id="service_productName" class="form-control">
							</div>
							<div class="col-md-3">
								<b>ค่าบริการ</b><br>
								<div class="input-group">
									<input type="number" class="form-control" value="0.00" min="0"
										id="service_productPrice">
									<div class="input-group-addon">บาท</div>
								</div>
							</div>
							<div class="col-md-2">
								<b>หน่วยนับ</b><br>
								<div class="">
									<select class="form-control" id="serviceProduct_Unit">
										<c:forEach items="${unitBeans}" var="unitBean" varStatus="i">
											<option value="${unitBean.id}">${unitBean.unitName}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-3">
								<b>คลังสินค้า</b><br>
								<div class="">
									<select class="form-control" id="service_productStock">
										<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
											<option value="${stockBean.id}">
												${stockBean.stockName} (${stockBean.company.companyName})</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	
</script>