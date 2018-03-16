<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;<span class="ti-map-alt"></span> ข้อมูลรายละเอียดการติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
			<div class="row mt05">
				<div class="col-md-12">
					<label for="exampleInputEmail1"><b>ที่อยู่ติดตั้ง</b></label><br>
					<select class="form-control" id="select_address1">
						<option value="1" selected="selected">ใช้ที่อยู่เดียวกับ ที่อยู่ในทะเบียนบ้าน</option>
						<option value="2">ใช้ที่อยู่เดียวกับ ที่อยู่ปัจจุบัน</option>
						<option value="">ที่อยู่อื่นๆ</option>
					</select>
				</div>
			</div>
			<!-- ที่อยู่ -->
			<div class="row detail-install-no-current-address mt05">
				<div class="col-md-2">
					<label for="exampleInputEmail1"><b>บ้านเลขที่<span class="text-red"> *</span></b></label> 
					<input type="text" class="form-control" id="no3">
				</div>
				<div class="col-md-1">
					<label for="exampleInputEmail1"><b>หมู่ที่</b></label> 
					<input type="text" class="form-control" id="section3">
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>อาคาร</b></label> 
					<input type="text" class="form-control" id="building3">
				</div>
				<div class="col-md-1">
					<label for="exampleInputEmail1"><b>เลขที่ห้อง</b></label> 
					<input type="text" class="form-control" id="room3">
				</div>
				<div class="col-md-1">
					<label for="exampleInputEmail1"><b>ชั้นที่</b></label> 
					<input type="text" class="form-control" id="floor3">
				</div>
				<div class="col-md-4">
					<label for="exampleInputEmail1"><b>หมู่บ้าน</b></label> 
					<input type="text" class="form-control" id="village3">
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>ตรอกซอย</b></label> 
					<input type="text" class="form-control" id="alley3">
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>ถนน</b></label> 
					<input type="text" class="form-control" id="road3">
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>จังหวัด<span class="text-red"> *</span></b></label> 
					<select class="form-control" id="province3" name="province3" data-plugin="select2" style="width: 100%">
						<option disabled="disabled" selected="selected">--- กรุณาเลือกจังหวัด ---</option>
						<c:forEach var="province" items="${provinces}" varStatus="i">
						<option value="${province.id}">${province.PROVINCE_NAME}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>อำเภอ / เขต<span class="text-red"> *</span></b></label>
					<select class="form-control" id="amphur3" name="amphur3" data-plugin="select2" style="width: 100%">
						<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกอำเภอ ---</option>
					</select>
				</div>
			</div>
			<div class="row detail-install-no-current-address">
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>ตำบล / แขวง<span class="text-red"> *</span></b></label>
					<select class="form-control" id="district3" name="district3" data-plugin="select2" style="width: 100%">
						<option value="" selected="selected" disabled="disabled">--- กรุณาเลือกตำบล ---</option>
					</select>
				</div>
				<div class="col-md-3">
					<label for="exampleInputEmail1"><b>รหัสไปรณีย์<span class="text-red"> *</span></b></label>
					<input class="form-control bg-form" type="text" id="postcode3" name="postcode3">
				</div>
				<div class="col-md-6">
					<label for="exampleInputEmail1"><b>สถานที่ใกล้เคียง</b></label>
					<input type="text" class="form-control" id="nearbyPlaces3">
				</div>
			</div>
							
			<!-- ที่อยู่ -->
			
			<div class="row mt05">
				<div class="col-md-4">
					<label for="exampleInputEmail1"><b>วันเวลาที่สะดวกในการติดตั้ง<span class="text-red"> *</span></b></label>
					<div class="input-group">
						<input type="text" class="form-control"id="daterange" name="daterange">
						<div class="input-group-addon"><i class="fa fa-calendar-o" style="margin: 0;"></i></div>
					</div>
				</div>
				<div class="col-md-4">
					<label for="exampleSelect1"><b>เขตชุมชน / โครงการที่ติดตั้ง<span class="text-red"> *</span></b></label> 
					<select class="form-control" id="select_zone" name="select_zone" data-plugin="select2">				
						<c:forEach var="zone" items="${zones}" varStatus="i">
						<option value="${zone.id}">${zone.zoneDetail} (${zone.zoneName})</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-4">
					<label for="exampleInputEmail1"><b>Plate Number</b></label> 
					<input type="text" class="form-control" id="plate_number">
				</div>
<!-- 				<div class="col-md-4"> -->
<!-- 					<label for="exampleInputEmail1"><b>แผนที่ในการติดตั้ง</b></label> -->
<!-- 					<label class="custom-file"> -->
<!-- 						<input type="file" id="install_map" class="custom-file-input"> -->
<!-- 						<span id="install_map_span" class="custom-file-control"></span> -->
<!-- 					</label> -->
<!-- 				</div> -->
			</div>

			<div class="row mt05">
				<div class="col-md-12">
					<ul class="nav nav-tabs nav-tabs-2" role="tablist">
						<li class="nav-item"><a class="nav-link active"
							data-toggle="tab" href="#home" role="tab" aria-expanded="true"><span class="ti-location-pin"></span>&nbsp;&nbsp;<b>ลากจุด B เพื่อกำหนดพื้นที่ติดตั้ง</b></a>
						</li>
						<li class="nav-item"><a class="nav-link" data-toggle="tab"
							href="#profile" role="tab" aria-expanded="false"><span class="ti-direction-alt"></span>&nbsp;&nbsp;<b>เส้นทาง</b></a></li>
					</ul>
					<div class="tab-content mt15">
						<div class="tab-pane active" id="home" role="tabpanel"
							aria-expanded="true">
							<div class="card modal static-modal custom-modal-4">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="cm-img img-cover"
											style="background-image: url(resources/assets/img/point_map.png);">
											<div class="gradient gradient-primary"></div>
											<div class="cm-content">
												<h1 class=" text-uppercase">พื้นที่ติดตั้ง</h1>
												<h4>
													ระยะทางโดยประมาณ <strong id="km"></strong>
												</h4>
												<hr>
												<font style="color: #e3d5ff;"><b>กรุณาระบุตำแหน่งพื้นที่ติดตั้ง
														ด้วยการลากจุดไปยังตำแหน่งใกล้เคียง</b></font>
											</div>
										</div>
										<div class="modal-body" style="padding: 0px;">
											<div id="gmap-route" style="with: 300px; height: 400px;"></div>
										</div>
										<!-- 						<div id="route"></div> -->
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="profile" role="tabpanel"
							aria-expanded="false">
							<div id="route"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<label for="exampleInputEmail1"><b>LATITUDE</b></label>
					<input class="form-control bg-form" type="text" id="latitude" name="latitude" value="13.556062165266543">
				</div>
				<div class="col-md-6">
					<label for="exampleInputEmail1"><b>LONGITUDE</b></label>
					<input type="text" class="form-control" id="longitude" name="longitude" value="100.27661451230313">
				</div>
			</div>

		</fieldset>
	</div>
</div>
	
</body>
</html>
