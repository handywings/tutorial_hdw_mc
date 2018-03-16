<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_header_detail_internet">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
		 <form id="formProductOrderInternet">
			<div class="row">
				<div class="col-md-12">
					<fieldset>
						<legend>&nbsp;&nbsp;&nbsp;เพิ่มสินค้า (Internet User)&nbsp;&nbsp;&nbsp;</legend>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
								<b>ชื่อ package<span class="text-red"> *</span></b><br> <input type="text"
									id="internet_packageName" name="internet_packageName" class="form-control">
									<div class="messages"></div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
								<b>รหัสสินค้า<span class="text-red"> *</span></b><br> <input type="text"
									id="internet_code" name="internet_code" class="form-control">
									<div class="messages"></div>
								</div>
							</div>
							<div class="col-md-3">
								<b>คลังสินค้า<span class="text-red"> *</span></b><br>
								<div class="">
									<div class="form-group">
										<select class="form-control" id="internet_equipmentStock" name="internet_equipmentStock">
											<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
												<option value="${stockBean.id}">
												${stockBean.stockName} (${stockBean.company.companyName})</option>
											</c:forEach>
										</select>
										<div class="messages"></div>
									</div>
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