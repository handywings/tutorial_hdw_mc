<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_header_detail_internet_old">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
			<div class="row">
				<div class="col-md-12">
					<fieldset>
						<legend>&nbsp;&nbsp;&nbsp;เพิ่มสินค้า (Internet User)&nbsp;&nbsp;&nbsp;</legend>
						<div class="row">
							<div class="col-md-6">
								<input type="hidden" id="hiddenProductInternetId" />
								<b>ชื่อ package</b><br><span id="textMasterInternetPackageName">-</span>
							</div>
							<div class="col-md-3">
								<b>รหัสสินค้า</b><br><span id="textMasterInternetPackageCode">-</span>
							</div>
							<div class="col-md-3">
								<b>คลังสินค้า</b><br><span id="textMasterInternetPackageStock">-</span>
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