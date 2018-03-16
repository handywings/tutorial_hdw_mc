<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="newcustomers">
	<div class="row">
		<div class="col-md-3">
			<b>เพศ<span class="text-red"> *</span>
			</b><br> <label style="padding-bottom: 5.5px;"
				class="custom-control custom-radio"> <input id="radio1"
				name="sex" type="radio" <c:if test="${serviceApplicationBean.customer.sex eq 'male' }">checked="checked"</c:if> value="male"
				class="custom-control-input" disabled> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;ชาย</span>
			</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input id="radio2" 
			    name="sex" type="radio" <c:if test="${serviceApplicationBean.customer.sex eq 'female' }">checked="checked"</c:if> value="female"
				class="custom-control-input" disabled> <span
				class="custom-control-indicator"></span> <span
				class="custom-control-description">&nbsp;หญิง</span>
			</label>
			<div class="messages"></div>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>คำนำหน้า<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="prefix" value="${serviceApplicationBean.customer.prefix}" disabled>
		</div>
	</div>
	<div class="row">
<!-- 		<div class="col-md-2"> -->
<!-- 			<label for="exampleInputEmail1"><b>รหัสลูกค้า</b></label> -->
<!-- 				<input type="text" class="form-control" id="exampleInputEmail1"> -->
<!-- 		</div> -->
		<div class="col-md-3">
			<input type="hidden" class="form-control" id="customerId" value="${serviceApplicationBean.customer.id }" disabled>
			<label for="exampleInputEmail1"><b>ชื่อ<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="firstName" value="${serviceApplicationBean.customer.firstName }" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>นามสกุล<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="lastName" value="${serviceApplicationBean.customer.lastName }" disabled>
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>ประเภทลูกค้า<span class="text-red"> *</span></b></label>
				<select id="customerType" class="form-control" disabled>
					<option value="I">บุคคลธรรมดา</option>
					<option value="C">นิติบุคคล</option>
				</select>
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>ประเภทลูกค้า<span class="text-red"> *</span></b></label>
				<select id="customerFeatures" class="form-control" disabled>
					<option disabled="disabled" selected="selected">--- เลือกประเภทลูกค้า ---</option>
					<c:forEach var="customerFeature" items="${customerFeatures}" varStatus="i">
					<option value="${customerFeature.id}">${customerFeature.customerFeatureName}</option>
					</c:forEach>
				</select>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>เลขที่บัตรประชาชน<span class="text-red"> *</span></b></label>
			<input type="text" class="form-control" id="identityNumber" value="${serviceApplicationBean.customer.identityNumber }" disabled>
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>อาชีพ<span class="text-red"> *</span></b></label> 
			<select id="career" class="form-control" disabled>
				<option disabled="disabled" selected="selected">--- เลือกอาชีพ ---</option>
				<c:forEach var="career" items="${careers}" varStatus="i">
					<option value="${career.id}">${career.careerName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>เบอร์ติดต่อ<span class="text-red"> *</span></b></label>
			<input type="text" class="form-control" id="mobile" value="${serviceApplicationBean.customer.contact.mobile }" disabled>
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>Fax</b></label> 
			<input type="text" class="form-control" id="fax" value="${serviceApplicationBean.customer.contact.fax }" disabled>
		</div>
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>E-mail<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="email" value="${serviceApplicationBean.customer.contact.email }" disabled>
		</div>
	</div>
	
	<!-- ที่อยู่ -->
	<fieldset>
		<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ตามทะเบียนบ้าน&nbsp;&nbsp;&nbsp;</legend>
	<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '1' }">
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
			<input type="text" class="form-control" id="no1" value="${addressBean.no}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
			<input type="text" class="form-control" id="section1" value="${addressBean.section}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อาคาร</b></label> 
			<input type="text" class="form-control" id="building1" value="${addressBean.building}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
			<input type="text" class="form-control" id="room1" value="${addressBean.room}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
			<input type="text" class="form-control" id="floor1" value="${addressBean.floor}" disabled>
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
			<input type="text" class="form-control" id="village1" value="${addressBean.village}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
			<input type="text" class="form-control" id="alley1" value="${addressBean.alley}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ถนน</b></label> 
			<input type="text" class="form-control" id="road1" value="${addressBean.road}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label> 
			<select class="form-control" id="province1" name="province1" disabled>
				<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
				<c:forEach var="province" items="${provinces}" varStatus="i">
				<option value="${province.id}">${province.PROVINCE_NAME}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
			<select class="form-control" id="amphur1" name="amphur1" disabled>
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
			</select>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
			<select class="form-control" id="district1" name="district1" disabled>
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>รหัสไปรณีย์<span class="text-red"> *</span></b></label>
			<input class="form-control bg-form" type="text" id="postcode1" name="postcode1" value="${addressBean.postcode}" disabled>
		</div>
		<div class="col-md-6">
			<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
			<input type="text" class="form-control" id="nearbyPlaces1" value="${addressBean.nearbyPlaces}" disabled>
		</div>
	</div>
	</c:if>
	</c:forEach>						
	<!-- ที่อยู่ -->
	</fieldset>
	<c:forEach var="addressBean" items="${serviceApplicationBean.customer.addressList}" varStatus="i">
	<c:if test="${addressBean.addressType eq '2' }">
	<div class="row mt05">
		<div class="col-md-12">
			<label class="custom-control custom-checkbox"> 
			<input type="checkbox" id="checkbox_no_current_address" class="custom-control-input" disabled> 
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
			<input type="text" class="form-control" id="no2" value="${addressBean.no}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
			<input type="text" class="form-control" id="section2" value="${addressBean.section}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อาคาร</b></label> 
			<input type="text" class="form-control" id="building2" value="${addressBean.building}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
			<input type="text" class="form-control" id="room2" value="${addressBean.room}" disabled>
		</div>
		<div class="col-md-1">
			<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
			<input type="text" class="form-control" id="floor2" value="${addressBean.floor}" disabled>
		</div>
		<div class="col-md-4">
			<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
			<input type="text" class="form-control" id="village2" value="${addressBean.village}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
			<input type="text" class="form-control" id="alley2" value="${addressBean.alley}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ถนน</b></label> 
			<input type="text" class="form-control" id="road2" value="${addressBean.road}" disabled>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label> 
			<select class="form-control" id="province2" name="province2" disabled>
				<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
				<c:forEach var="province" items="${provinces}" varStatus="i">
				<option value="${province.id}">${province.PROVINCE_NAME}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
			<select class="form-control" id="amphur2" name="amphur2" disabled>
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
			</select>
		</div>
	</div>
	<div class="row no-current-address">
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
			<select class="form-control" id="district2" name="district2" disabled>
				<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
			</select>
		</div>
		<div class="col-md-3">
			<label for="exampleInputEmail1"><b>รหัสไปรณีย์<span class="text-red"> *</span></b></label>
			<input class="form-control bg-form" type="text" id="postcode2" name="postcode2" value="${addressBean.postcode}" disabled>
		</div>
		<div class="col-md-6">
			<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
			<input type="text" class="form-control" id="nearbyPlaces2" value="${addressBean.nearbyPlaces}" disabled>
		</div>
	</div>								
	<!-- ที่อยู่ -->
	</fieldset>
	</c:if>
	</c:forEach>
</div>
	
</body>
</html>
