<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="oldcustomersdetail" style="display: none;">
<hr>
												<div class="row">
													<div class="col-md-3">
													<input type="hidden" class="form-control" id="customerId">
														<b>รหัสลูกค้า</b><br><span id="custCode_old"></span>
													</div>
													<div class="col-md-3">
														<b>ชื่อลูกค้า</b><br><span id="custName_old"></span>
													</div>
													<div class="col-md-3">
														<b>เลขที่บัตรประชาชน</b><br><span id="identityNumber_old"></span>
													</div>
													<div class="col-md-3">
														<b>ประเภทลูกค้า</b><br><span id="customerType_old"></span>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-3">
														<b>อาชีพ</b><br><span id="career_old"></span>
													</div>
													<div class="col-md-3">
														<b>เบอร์โทรติดต่อ</b><br><span id="mobile_old"></span>
													</div>
													<div class="col-md-3">
														<b>Fax</b><br><span id="fax_old"></span>
													</div>
													<div class="col-md-3">
														<b>E-mail</b><br><span id="email_old"></span>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-6">
														<b>ที่อยู่ตามทะเบียนบ้าน</b><br><span id="collectAddressDetail1_old"></span>
													</div>
													<div class="col-md-6">
														<b>สถานที่ใกล้เคียง</b><br><span id="nearbyPlaces1_old"></span>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-6">
														<b>ที่อยู่ปัจจุบัน</b><br><span id="collectAddressDetail2_old"></span>
													</div>
													<div class="col-md-6">
														<b>สถานที่ใกล้เคียง</b><br><span id="nearbyPlaces2_old"></span>
													</div>
												</div>
											</div>
	
</body>
</html>
