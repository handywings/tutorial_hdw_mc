<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_header_detail_equipment_olditem">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
			<div class="row mb05">
				<div class="col-md-12">
					<fieldset>
						<legend>&nbsp;&nbsp;&nbsp;ข้อมูลสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
						<div class="row">
							<div class="col-md-3">
								<input type="hidden" id="hiddenProductEquipmentId" />
								<b>รหัสสินค้า</b><br><span id="textMasterProductCode">-</span>
							</div>
							<div class="col-md-3">
								<b>ชื่อสินค้า</b><br><span id="textMasterProductName">-</span>
							</div>
							<div class="col-md-3">
								<b>หมวดหมู่วัสดุอุปกรณ์</b><br><span id="textMasterProductCategory">-</span>
							</div>
							<div class="col-md-3">
								<b>Supplier</b><br><span id="textMasterSupplier">-</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3">
								<input type="hidden" id="hiddenProductEquipmentId" />
								<b>คลังสินค้า</b><br><span id="textMasterProductStock">-</span>
							</div>
							<div class="col-md-3">
								<b>หน่วยนับ</b><br><span id="textMasterProductUnit">-</span>
							</div>
							<div class="col-md-3">
								<b>หมวดบัญชีสินค้า</b><br><span id="textFinancialType">-</span>
							</div>
							<div class="col-md-3">
								<b>จำนวนน้อยสุดในคลังสินค้า</b><br><span id="textMasterProductMinimum">-</span>
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