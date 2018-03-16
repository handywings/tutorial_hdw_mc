<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="newcustomers">
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-3"> -->
<!-- 			<b>รูปลูกค้า<span class="text-red"></span> -->
<!-- 				<div class="form-group" id="div-dropify" > -->
<!-- 						<input type="file" id="add-input-file-now-custom-2" -->
<%-- 					class="dropify" data-default-file="${defaultImage}"  --%>
<!-- 					data-height="210" /> -->
<!-- 				<div class="messages"></div> -->
<!-- 				</div> -->
<!-- 			<div class="messages"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<div class="row">
		<div class="col-md-3">
			<b>เพศ<span class="text-red"> *</span>
			</b><br> <label style="padding-bottom: 5.5px;"
				class="custom-control custom-radio"> <input id="radio1"
				name="sex" type="radio" checked="checked" value="male"
				class="custom-control-input"> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;ชาย</span>
			</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
				id="radio2" name="sex" type="radio" value="female"
				class="custom-control-input"> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;หญิง</span>
			</label>
			<div class="messages"></div>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>คำนำหน้า</b></label> 
			<input type="text" class="form-control" id="prefix">
		</div>
	</div>
	<div class="row">
<!-- 		<div class="col-md-2"> -->
<!-- 			<label for="exampleInputEmail1"><b>รหัสลูกค้า</b></label> -->
<!-- 				<input type="text" class="form-control" id="exampleInputEmail1"> -->
<!-- 		</div> -->
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ชื่อ<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="firstName">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>นามสกุล<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="lastName">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>ประเภทลูกค้า</b></label>
				<select id="customerType" class="form-control">
					<option value="I">บุคคลธรรมดา</option>
					<option value="C">นิติบุคคล</option>
				</select>
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>ลักษณะของลูกค้า</b></label>
				<select id="customerFeatures" class="form-control">
					<c:forEach var="customerFeature" items="${customerFeatures}" varStatus="i">
					<option value="${customerFeature.id}">${customerFeature.customerFeatureName}</option>
					</c:forEach>
				</select>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>เลขที่บัตรประชาชน<span class="text-red"> *</span></b></label>
			<input type="text" class="form-control" maxlength="13" id="identityNumber">
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>อาชีพ<span class="text-red"> *</span></b></label> 
			<select id="career" class="form-control">
<!-- 				<option disabled="disabled" selected="selected">--- เลือกอาชีพ ---</option> -->
				<c:forEach var="career" items="${careers}" varStatus="i">
					<option value="${career.id}">${career.careerName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>เบอร์ติดต่อ<span class="text-red"> *</span></b></label>
			<input type="text" class="form-control" id="mobile">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>Fax</b></label> 
			<input type="text" class="form-control" id="fax">
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>E-mail</b></label> 
			<input type="text" class="form-control" id="email">
		</div>
	</div>
	
	<!-- ที่อยู่ -->
	<fieldset class="mt15">
		<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ตามทะเบียนบ้าน&nbsp;&nbsp;&nbsp;</legend>
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="no1">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
			<input type="text" class="form-control" id="section1">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อาคาร</b></label> 
			<input type="text" class="form-control" id="building1">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
			<input type="text" class="form-control" id="room1">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
			<input type="text" class="form-control" id="floor1">
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
			<input type="text" class="form-control" id="village1">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
			<input type="text" class="form-control" id="alley1">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ถนน</b></label> 
			<input type="text" class="form-control" id="road1">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label> 
			<select class="form-control" id="province1" name="province1" data-plugin="select2" style="width: 100%">
				<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
				<c:forEach var="province" items="${provinces}" varStatus="i">
				<option value="${province.id}">${province.PROVINCE_NAME}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
			<select class="form-control" id="amphur1" name="amphur1" data-plugin="select2" style="width: 100%">
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
			</select>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
			<select class="form-control" id="district1" name="district1" data-plugin="select2" style="width: 100%">
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>รหัสไปรษณีย์<span class="text-red"> *</span></b></label>
			<input class="form-control bg-form" type="text" id="postcode1" name="postcode1">
		</div>
		<div class="col-md-6">
			<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
			<input type="text" class="form-control" id="nearbyPlaces1">
		</div>
	</div>
										
	<!-- ที่อยู่ -->
	</fieldset>
	
	<div class="row mt05">
		<div class="col-md-12">
			<label class="custom-control custom-checkbox"> 
			<input type="checkbox" id="checkbox_no_current_address" class="custom-control-input"> 
			<span class="custom-control-indicator"></span> 
			<span class="custom-control-description">ที่อยู่ปัจจุบัน ไม่ได้เป็นที่อยู่เดียวกันกับที่อยู่ในทะเบียนบ้าน</span>
			</label>
		</div>
	</div>

	<!-- ที่อยู่ -->
	<fieldset class="no-current-address">
		<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ปัจจุบัน&nbsp;&nbsp;&nbsp;</legend>
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="no2">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
			<input type="text" class="form-control" id="section2">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อาคาร</b></label> 
			<input type="text" class="form-control" id="building2">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
			<input type="text" class="form-control" id="room2">
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
			<input type="text" class="form-control" id="floor2">
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
			<input type="text" class="form-control" id="village2">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
			<input type="text" class="form-control" id="alley2">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ถนน</b></label> 
			<input type="text" class="form-control" id="road2">
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label> 
			<select class="form-control" id="province2" name="province2" data-plugin="select2" style="width: 100%">
				<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
				<c:forEach var="province" items="${provinces}" varStatus="i">
				<option value="${province.id}">${province.PROVINCE_NAME}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
			<select class="form-control" id="amphur2" name="amphur2" data-plugin="select2" style="width: 100%">
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
			</select>
		</div>
	</div>
	<div class="row no-current-address">
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
			<select class="form-control" id="district2" name="district2" data-plugin="select2" style="width: 100%">
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>รหัสไปรษณีย์<span class="text-red"> *</span></b></label>
			<input class="form-control bg-form" type="text" id="postcode2" name="postcode2">
		</div>
		<div class="col-md-6">
			<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
			<input type="text" class="form-control" id="nearbyPlaces2">
		</div>
	</div>								
	<!-- ที่อยู่ -->
	</fieldset>
	
</div>
	
</body>
</html>
