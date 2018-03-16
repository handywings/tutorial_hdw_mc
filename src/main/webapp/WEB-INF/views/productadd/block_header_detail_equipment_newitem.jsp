<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_header_detail_equipment_newitem">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
		 <form id="formProductOrderEquipment">
			<div class="row mb05">
				<div class="col-md-12">
					<fieldset>
						<legend>&nbsp;&nbsp;&nbsp;ข้อมูลสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
						<div class="row">
							<div class="col-md-3">
									<b>รหัสสินค้า</b><br> 
									<input type="text" id="new_masterEquipmentProductCode"
									class="form-control">
							</div>
							<div class="col-md-3">
								<div class="form-group">
								<b>ชื่อสินค้า<span class="text-red"> *</span></b><br> 
									<input type="text" id="new_masterEquipmentProductName" name="new_masterEquipmentProductName" 
									class="form-control">
									<div class="messages"></div>
								</div>
							</div>
							<div class="col-md-3">
								<b>หมวดหมู่วัสดุอุปกรณ์</b><br>
								<div class="">
									<div class="form-group">
									<select class="form-control" id="new_equipmentProductType" name="new_equipmentProductType">
										<c:forEach items="${epcSearchBeans}" var="epcSearchBean"
											varStatus="i">
											<c:if test="${epcSearchBean.equipmentProductCategoryCode ne '00001'}">
											<option value="${epcSearchBean.id}">
												${epcSearchBean.equipmentProductCategoryName}</option>
											</c:if>
										</c:forEach>
									</select>
									<div class="messages"></div>
									</div>
								</div>
							</div>
								<div class="col-md-3">
									<div id="the-basics">
										<b>Supplier</b><br> <input type="text" id="new_supplier"
											class="form-control typeahead">
									</div>
								</div>
							</div>
						<div class="row">
							<div class="col-md-3">
								<b>คลังสินค้า</b><br>
								<div class="">
									<div class="form-group">
										<select class="form-control" id="new_equipmentStock" name="new_equipmentStock">
											<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
												<option value="${stockBean.id}">
													${stockBean.stockName} (${stockBean.company.companyName})</option>
											</c:forEach>
										</select>
										<div class="messages"></div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<b>หน่วยนับ</b><br>
								<div class="">
									<div class="form-group">
										<select class="form-control" id="new_equipmentUnit" name="new_equipmentUnit">
											<c:forEach items="${unitBeans}" var="unitBean" varStatus="i">
												<option value="${unitBean.id}">${unitBean.unitName}</option>
											</c:forEach>
										</select>
										<div class="messages"></div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<b>หมวดบัญชีสินค้า</b><br>
								<div class="">
									<div class="form-group">
										<select class="form-control" id="financial_type" name="financial_type">
											<option value="C" selected="selected">ต้นทุน</option>
											<option value="A">ทรัพย์สิน</option>											
										</select>
										<div class="messages"></div>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<b>จำนวนน้อยสุดในคลังสินค้า</b><br>
								<div class="controls form-inline">
									<label class="custom-control custom-checkbox"> 
									<input type="checkbox" id="checkboxIsMinimum" class="custom-control-input" value=""> 
										<span class="custom-control-indicator"></span> 
										<span class="custom-control-description"></span>
									</label>
									<input type="number" id="new_masterEquipmentProductMinumun" value="1" min="1" 
									class="form-control">
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
	$(document).ready(function() {
		
		$('#checkboxIsMinimum').click(function(){
		    if($(this).is(':checked')){
		    	$('#new_masterEquipmentProductMinumun').removeAttr('disabled');
		    } else {
		    	$('#new_masterEquipmentProductMinumun').attr('disabled','disabled');
		    }
		});
		
	});
</script>