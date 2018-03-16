<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;รายชื่อบริการและวัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
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
					<nav
						class="navbar navbar-light bg-white navbar-bottom-line b-a mb-1">
						<button class="navbar-toggler hidden-md-up" type="button"
							data-toggle="collapse" data-target="#exCollapsingNavbar4"
							aria-controls="exCollapsingNavbar4" aria-expanded="false"
							aria-label="Toggle navigation"></button>
						<div class="collapse navbar-toggleable-sm"
							id="exCollapsingNavbar4">
							<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
								<li class="nav-item"><a class="nav-link active"
									data-toggle="tab" href="#div_equiment" role="tab"
									aria-expanded="true"> <b>รายการสินค้าประเภทอุปกรณ์
											(<span id="count_div_equiment">0</span>)</b></a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#div_internet" role="tab" aria-expanded="false">
										<b>รายการสินค้าประเภท Internet User
											(<span id="count_div_internet">0</span>)</b>
								</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#div_service" role="tab" aria-expanded="false">
										<b>รายการค่าแรง	/ ค่าบริการ
											(<span id="count_div_service">0</span>)</b>
								</a></li>
							</ul>
						</div>
					</nav>
				</div>
			</div>

			<div class="tab-content">
				<!-- TYPE_EQUIMENT -->
				<div class="tab-pane active" id="div_equiment"
					role="tabpanel" aria-expanded="true">
					<div class="card-header text-uppercase">
						<div class="row mt05">
							<div class="col-md-7">
								<h4>รายการสินค้าประเภทอุปกรณ์</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<table id="table_template_equiment" class="table table-bordered mb-0 table-hover">
											<thead class="thead-bg">
												<tr>
													<th><center>ลำดับ</center></th>
													<th><center>รหัสสินค้า</center></th>
													<th><center>Serial Number</center></th>
													<th><center>ชื่อเรียกสินค้า</center></th>
													<th style="width: 160px;"><center>ราคาขาย
															</center></th>
													<th><center>ฟรี</center></th>
													<th><center>ยืม</center></th>
													<th style="width: 165px;"><center>ราคารวม
															</center></th>
													<th style="width: 10px;"></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>

								</div>
							</div>

						</div>
					</div>
				</div>
				<!-- TYPE_INTERNET_USER -->
				<div class="tab-pane" id="div_internet"
					role="tabpanel" aria-expanded="true">
					<div class="card-header text-uppercase">
						<div class="row mt05">
							<div class="col-md-7">
								<h4>รายการสินค้าประเภท Internet User</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<table id="table_template_internet" class="table table-bordered mb-0 table-hover">
											<thead class="thead-bg">
												<tr>
													<th><center>ลำดับ</center></th>
													<th><center>รหัสสินค้า</center></th>
													<th><center>ชื่อ package</center></th>
													<th><center>UserName</center></th>
													<th><center>Password</center></th>
													<th style="width: 10px;"></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>

								</div>
							</div>

						</div>
					</div>
				</div>
				<!-- TYPE_SERVICE -->
				<div class="tab-pane" id="div_service"
					role="tabpanel" aria-expanded="true">
					<div class="card-header text-uppercase">
						<div class="row mt05">
							<div class="col-md-7">
								<h4>รายการค่าแรง	/ ค่าบริการ</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<table id="table_template_service" class="table table-bordered mb-0 table-hover">
											<thead class="thead-bg">
												<tr>
													<th><center>ลำดับ</center></th>
													<th><center>ชื่อค่าแรง	/ ค่าบริการ</center></th>
													<th style="width: 130px;"><center>จำนวน</center></th>
													<th style="width: 160px;"><center>ราคา (ไม่รวม Vat)</center></th>
													<th><center>ฟรี</center></th>
													<th style="width: 165px;"><center>ราคารวม (ไม่รวม Vat)</center></th>
													<th style="width: 10px;"></th>
												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>

								</div>
							</div>

						</div>
					</div>
				</div>
				
			</div>

		</fieldset>
	</div>
</div>

</body>
</html>
