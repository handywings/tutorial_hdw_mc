<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ที่อยู่ในการจัดส่งเอกสารการเงิน&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mt05">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>ที่อยู่สำหรับการจัดส่งใบแจ้งหนี้และใบเสร็จรับเงิน</b></label><br>
													<select class="form-control" id="select_address2">
														<option value="3" selected="selected">ใช้ที่อยู่เดียวกับ ที่อยู่ที่ติดตั้ง</option>
<!-- 														<option value="1">ใช้ที่อยู่เดียวกับ ที่อยู่ในทะเบียนบ้าน</option> -->
<!-- 														<option value="2">ใช้ที่อยู่เดียวกับ ที่อยู่ปัจจุบัน</option>														 -->
														<option value="">ที่อยู่อื่นๆ</option>
													</select>
												</div>
											</div>
										<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="i">
										<c:if test="${addressBean.addressType eq '4' }">
											<div class="row mt05 shipping-address-no-current-address">
												<div class="col-md-2">
													<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
													<input type="text" class="form-control" id="no4" value="${addressBean.no}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
													<input type="text" class="form-control" id="section4" value="${addressBean.section}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>อาคาร</b></label> 
													<input type="text" class="form-control" id="building4" value="${addressBean.building}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
													<input type="text" class="form-control" id="room4" value="${addressBean.room}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
													<input type="text" class="form-control" id="floor4" value="${addressBean.floor}">
												</div>
												<div class="col-md-4">
													<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
													<input type="text" class="form-control" id="village4" value="${addressBean.village}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
													<input type="text" class="form-control" id="alley4" value="${addressBean.alley}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ถนน</b></label> 
													<input type="text" class="form-control" id="road4" value="${addressBean.road}">
												</div>	
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label>
													<select class="form-control" id="province4" name="province4">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
														<c:forEach var="province" items="${provinces}" varStatus="i">
														<option value="${province.id}">${province.PROVINCE_NAME}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
													<select class="form-control" id="amphur4" name="amphur4">
														<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
													</select>
												</div>
											</div>
											<div class="row shipping-address-no-current-address">
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
													<select class="form-control" id="district4" name="district4" >
														<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
													</select>
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>รหัสไปรณีย์<span class="text-red"> *</span></b></label>
													<input class="form-control bg-form" type="text"
														id="postcode4" name="postcode4" value="${addressBean.postcode}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
													<input type="text" class="form-control"
														id="nearbyPlaces4" value="${addressBean.nearbyPlaces}">
												</div>												
												<div class="col-md-3">
													<label for="exampleSelect1"><b>เขตชุมชน / โครงการที่ติดตั้ง<span class="text-red"> *</span></b></label> 
													<select class="form-control" id="select_zone4" name="select_zone4" data-plugin="select2" style="width: 100%">				
														<c:forEach var="zone" items="${zones}" varStatus="i">
														<option value="${zone.id}">${zone.zoneDetail} (${zone.zoneName})</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</c:if>
										</c:forEach>
											<!--  -->										
											<hr>
											<!--  -->
											<div class="row mt05">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>ที่อยู่สำหรับการจัดส่งใบกำกับภาษี</b></label><br>
													<select class="form-control" id="select_address3">
														<option value="4" selected="selected">ใช้ที่อยู่เดียวกับ ที่อยู่สำหรับการจัดส่งใบแจ้งหนี้และใบเสร็จรับเงิน</option>
														<option value="1">ใช้ที่อยู่เดียวกับ ที่อยู่ในทะเบียนบ้าน</option>
														<option value="2">ใช้ที่อยู่เดียวกับ ที่อยู่ปัจจุบัน</option>
														<option value="3">ใช้ที่อยู่เดียวกับ ที่อยู่ที่ติดตั้ง</option>														
														<option value="">ที่อยู่อื่นๆ</option>
													</select>
												</div>
											</div>
										<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="i">
										<c:if test="${addressBean.addressType eq '5' }">
											<div class="row mt05 invoice-address-no-current-address">
												<div class="col-md-2">
													<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
													<input type="text" class="form-control" id="no5" value="${addressBean.no}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
													<input type="text" class="form-control" id="section5" value="${addressBean.section}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>อาคาร</b></label> 
													<input type="text" class="form-control" id="building5" value="${addressBean.building}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
													<input type="text" class="form-control" id="room5" value="${addressBean.room}">
												</div>
												<div class="col-md-1">
													<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
													<input type="text" class="form-control" id="floor5" value="${addressBean.floor}">
												</div>
												<div class="col-md-4">
													<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
													<input type="text" class="form-control" id="village5" value="${addressBean.village}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
													<input type="text" class="form-control" id="alley5" value="${addressBean.alley}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ถนน</b></label> 
													<input type="text" class="form-control" id="road5" value="${addressBean.road}">
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label>
													<select class="form-control" id="province5" name="province5">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
														<c:forEach var="province" items="${provinces}" varStatus="i">
														<option value="${province.id}">${province.PROVINCE_NAME}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
													<select class="form-control" id="amphur5" name="amphur5">
														<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
													</select>
												</div>
											</div>
											<div class="row invoice-address-no-current-address">
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
													<select class="form-control" id="district5" name="district5" >
														<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
													</select>
												</div>
												<div class="col-md-3">
													<label for="exampleInputEmail1"><b>รหัสไปรณีย์<span class="text-red"> *</span></b></label>
													<input class="form-control bg-form" type="text"
														id="postcode5" name="postcode5" value="${addressBean.postcode}">
												</div>
												<div class="col-md-6">
													<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
													<input type="text" class="form-control"
														id="nearbyPlaces5" value="${addressBean.nearbyPlaces}">
												</div>
											</div>
										</c:if>
										</c:forEach>
										</fieldset>
									</div>
								</div>
	
</body>
</html>
