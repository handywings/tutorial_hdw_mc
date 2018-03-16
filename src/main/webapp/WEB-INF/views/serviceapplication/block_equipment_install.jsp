<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-hummer"></span> รายชื่อบริการและวัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-12">
													<button type="button" onclick="openModalSearchProduct()"
														class="btn btn-info label-left float-xs-right mr-0-5">
														<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
													</button>
												</div>
											</div>
											<hr>
											<div class="row">
												<div class="col-md-12">
													<div class="table-responsive">
													<table id="table_template" class="table table-bordered mb-0 table-hover">
														<thead class="thead-bg">
															<tr>
																<th style="width: 10px;"><center>ลำดับ</center></th>
																<th><center>รหัสสินค้า</center></th>
																<th>ชื่อเรียกสินค้า</th>
																<th><center>จำนวน</center></th>
<%-- 																<th style="width: 160px;"><center>ราคาขาย --%>
<%-- 																		</center></th> --%>
<%-- 																<th><center>ฟรี</center></th> --%>
<%-- 																<th><center>ยืม</center></th> --%>
<%-- 																<th style="width: 165px;"><center>ราคารวม --%>
<%-- 																		</center></th> --%>
																<th style="width: 10px;"></th>
															</tr>
														</thead>
														<tbody>
															<tr id="table_template_nodata"><td colspan="5"><center>ไม่พบรายชื่อบริการและวัสดุอุปกรณ์ที่ติดตั้ง</center></td></tr>
														</tbody>
													</table>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>

</body>
</html>
