<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-shopping-cart"></span> Package
												ที่สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-4">
													<label for="exampleSelect1"><b>ประเภทบริการ<span class="text-red"> *</span></b></label> 
													<select class="form-control" id="select_service_type">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกประเภทบริการ ---</option>
														<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
															<option value="${servicePackageTypeBean.id }">${servicePackageTypeBean.packageTypeName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-5">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ชื่อ Package<span class="text-red"> *</span></b></label> 
														<select class="form-control" id="service_packages">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกประเภทบริการก่อน ---</option>
														</select>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ประเภทใบสมัคร<span class="text-red"> *</span></b></label> 
														<select class="form-control" id="service_application_type">
														<c:forEach items="${serviceApplicationTypes}" var="serviceApplicationType"> 
															<option value="${serviceApplicationType.id }">${serviceApplicationType.serviceApplicationTypeName}</option>
														</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="row div-detail-package-ismonthlyservice">
												<div class="col-md-12">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>รายละเอียด Package</b></label>
														<div class="table-responsive">
															<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
																		<th><center>ค่าติดตั้ง</center></th>
																		<th><center>ค่ามัดจำอุปกรณ์</center></th>
<%-- 																		<th><center>รอบบิลแรกใช้ฟรี</center></th> --%>
																		<th><center>ส่วนลดรอบบิลแรก</center></th>
																		<th><center><font id="priceservice">ค่าบริการรายเดือน</font></center></th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td scope="row">
																			<input type="hidden" id="perMonth_ismonthlyservice">
																			<div class="input-group">
																				<input type="text" class="form-control" style="text-align: center;"
																					value="" id="installationFee_ismonthlyservice">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control" style="text-align: center;"
																					value="" id="deposit_ismonthlyservice">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
<!-- 																		<td scope="row"><div class="input-group"> -->
<!-- 																				<input type="text" class="form-control"  style="text-align: center;" -->
<!-- 																					id="firstBillFree_ismonthlyservice"> -->
<!-- 																				<div class="input-group-addon">เดือน</div> -->
<!-- 																			</div></td> -->
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control" style="text-align: center;"
																					value="" id="firstBillFreeDisCount_ismonthlyservice">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control" style="text-align: center;"
																					value="" id="monthlyServiceFee_ismonthlyservice">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											
											<div class="row div-detail-package">
												<div class="col-md-12">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>รายละเอียด Package</b></label>
														<div class="table-responsive">
															<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
																		<th><center>ค่าติดตั้ง</center></th>
																		<th><center>ค่ามัดจำอุปกรณ์</center></th>
																		<th><center>ค่าบริการ</center></th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="installationFee">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="deposit">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="oneServiceFee">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											
										</fieldset>
									</div>
								</div>
	
</body>
</html>
