<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row mt15" id="div_block_equipment_install">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;<span class="ti-hummer"></span> รายชื่อบริการและวัสดุอุปกรณ์ที่ติดตั้ง&nbsp;&nbsp;&nbsp;</legend>
				
<!-- 			<div class="row"> -->
<!-- 				<div class="col-md-12"> -->
<!-- 					<button type="button" onclick="openModalSearchProduct()" -->
<!-- 						class="btn btn-info label-left float-xs-right mr-0-5"> -->
<!-- 						<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า -->
<!-- 					</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
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
								<li class="nav-item"><a id="aEquiment" class="nav-link active"
									data-toggle="tab" href="#div_equiment" role="tab"
									aria-expanded="true"> <b>รายการสินค้าประเภทอุปกรณ์
											(<span id="count_div_equiment">${equimentSize}</span>)</b></a></li>
								<li class="nav-item"><a id="aInternetUser" class="nav-link" data-toggle="tab"
									href="#div_internet" role="tab" aria-expanded="false">
										<b>รายการสินค้าประเภท Internet User
											(<span id="count_div_internet">${internetSize}</span>)</b>
								</a></li>
								<li class="nav-item"><a class="nav-link" data-toggle="tab"
									href="#div_service" role="tab" aria-expanded="false">
										<b>รายการค่าแรง	/ ค่าบริการ
											(<span id="count_div_service">${serviceSize}</span>)</b>
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
										<div class="errorSN" style="display:none">
											<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
												<button type="button" class="close" data-dismiss="alert" aria-label="Close">
												<span aria-hidden="true">×</span>
												</button>
												<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือกอุปกรณ์ (Serial Number)
											</div>
										</div>
										<table id="table_template_equiment" class="table table-bordered mb-0 table-hover">
											<thead class="thead-bg">
												<tr>
<%-- 													<th><center>ลำดับ</center></th> --%>
<%-- 													<th><center>รหัสสินค้า</center></th> --%>
													<th><center>รหัสสินค้า / SN</center></th>
													<th>ชื่อเรียกสินค้า</th>
													<th style="width: 150px;"><center>จำนวน</center></th>
													<th style="width: 160px;"><center>ราคาขาย</center></th>
													<th><center>ฟรี</center></th>
													<th><center>ยืม</center></th>
													<th style="width: 165px;"><center>ราคารวม</center></th>
													<th></th>
												</tr>
											</thead>
											<tbody>											
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${productItemBeans}" var="productItemBean" varStatus="i">	
								<c:if test="${productItemBean.type eq 'E'}">	
								<c:forEach items="${productItemBean.productItemWorksheetBeanList}" var="productItemWorksheetBeanList" varStatus="j">
								<tr>
									<c:set var="count" value="${count + 1}" scope="page"/>	
	<%-- 								<td><center><span class="template-no"><c:out value="${count}" /></span></center> --%>
	<%-- 									<input class="productId" type="hidden" value="${productItemBean.id}"> --%>
	<!-- 								</td> -->
									<input class="productId" type="hidden" value="${productItemBean.id}">
									<input class="productType" type="hidden" value="${productItemBean.type}">
									<input id="productItemWorksheetId_${count}" class="productItemWorksheetId" type="hidden" value="${productItemWorksheetBeanList.id}">
									<input id="productItemId_${count}" class="productItemId" type="hidden" value="${productItemWorksheetBeanList.equipmentProductItemBean.id}">
	<%-- 								<td><center><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productItemBean.product.id}" target="_blank"><b>${productItemBean.product.productCode}</b></a></center></td> --%>
									<c:if test="${productItemBean.serialNo}">
									<td><center><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productItemBean.product.id}" target="_blank"><b>${productItemBean.product.productCode}</b></a><br><span class="serial-no" id="serial_${count}" style="color: gray;">${productItemWorksheetBeanList.equipmentProductItemBean.serialNo}</span></center></td>
									</c:if>
									<c:if test="${!productItemBean.serialNo}">
									<td><center><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productItemBean.product.id}" target="_blank"><b>${productItemBean.product.productCode}</b></a><br></center></td>
									</c:if>
									<td>${productItemBean.product.productName}</td>
									<td scope="row">
										<div class="input-group">
										<input type="number" class="form-control quantity" 
										value="<c:if test="${productItemWorksheetBeanList.quantity == 0}">1</c:if><c:if test="${productItemWorksheetBeanList.quantity != 0}">${productItemWorksheetBeanList.quantity}</c:if>" <c:if test="${productItemBean.serialNo}">disabled</c:if> >
										<div class="input-group-addon">${productItemBean.product.unit.unitName}</div>
										</div>
									</td>
									<td scope="row">
										<div class="input-group">
										<input type="number" class="form-control salePrice calculate" value="${productItemWorksheetBeanList.price}" >
										<div class="input-group-addon">บาท</div>
										</div>
									</td>
									<td align="center" style="vertical-align: middle;">
										<label class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input check-box-free"
											<c:if test="${productItemWorksheetBeanList.free }">checked</c:if> > <span
											class="custom-control-indicator"></span> <span
											class="custom-control-description"></span>
										</label>
									</td>
									<td align="center" style="vertical-align: middle;">
										<label class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input check-box-lend" <c:if test="${!productItemBean.serialNo}">disabled="disabled"</c:if>
											<c:if test="${productItemWorksheetBeanList.lend }">checked</c:if> > <span
											class="custom-control-indicator"></span> <span
											class="custom-control-description"></span>
										</label>
									</td>
									<td scope="row">
										<div class="input-group">
										<input type="number" class="form-control totalPrice" 
										value="<c:if test="${productItemWorksheetBeanList.free || productItemWorksheetBeanList.lend }">0.0</c:if><c:if test="${!productItemWorksheetBeanList.free && !productItemWorksheetBeanList.lend}">${productItemWorksheetBeanList.amount * productItemWorksheetBeanList.quantity }</c:if>" >
										<div class="input-group-addon">บาท</div>
										</div>
									</td>
									<td align="center" style="vertical-align: middle;">
							<c:if test="${productItemBean.serialNo}">
							<a class="run-swal cursor-p btn btn-success waves-effect waves-light btn-circle btn-sm font-size-12"
							onclick="loadProductItemEdit(${productItemBean.product.id },'${productItemBean.type}','${count}')" ><span
							class="ti-plus" data-toggle="tooltip"
							data-placement="bottom" title=""
							data-original-title="เพิ่มรายชิ้น"></span></a>
							</c:if>
									<a class="run-swal cursor-p btn btn-danger waves-effect waves-light btn-circle btn-sm font-size-12"><span
										class="ti-trash remove-template" data-toggle="tooltip"
										data-placement="bottom" title=""
										data-original-title="ลบ"></span></a>						
									</td>
								</tr>
								</c:forEach>
								</c:if>
						</c:forEach>
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
										<div class="errorInternet" style="display:none">
											<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
												<button type="button" class="close" data-dismiss="alert" aria-label="Close">
												<span aria-hidden="true">×</span>
												</button>
												<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือก (UserName Password)
											</div>
										</div>
										<table id="table_template_internet" class="table table-bordered mb-0 table-hover">
											<thead class="thead-bg">
												<tr>
<%-- 													<th><center>ลำดับ</center></th> --%>
													<th><center>รหัสสินค้า</center></th>
													<th>ชื่อ package</th>
													<th><center>UserName</center></th>
													<th><center>Password</center></th>
													<th></th>
												</tr>
											</thead>
											<tbody>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${productItemBeans}" var="productItemBean" varStatus="i">	
							<c:if test="${productItemBean.type eq 'I'}">	
							<c:forEach items="${productItemBean.productItemWorksheetBeanList}" var="productItemWorksheetBeanList" varStatus="j">
								<tr>
									<c:set var="count" value="${count + 1}" scope="page"/>	
<%-- 									<td><center><span class="template-no"><c:out value="${count}" /></span></center> --%>
<!-- 									</td> -->
									<input class="productId" type="hidden" value="${productItemBean.product.id }">
									<input class="productType" type="hidden" value="${productItemBean.type}">
									<input id="productItemWorksheetId_${count}" class="productItemWorksheetId" type="hidden" value="${productItemWorksheetBeanList.id}">
									<input id="internetId_${count}" class="productItemId" type="hidden" value="${productItemWorksheetBeanList.equipmentProductItemBean.id}">
									<td><center><a href="${pageContext.request.contextPath}/productorderinternetproduct/detail/${productItemBean.product.id}" target="_blank"><b>${productItemBean.product.productCode}</b></a></center></td>
									<td>${productItemBean.product.productName}</td>
									<td><span class="internet-username" id="span_username_${count}">${productItemWorksheetBeanList.internetProductBeanItem.userName}</span></td>
									<td><span class="internet-password" id="span_password_${count}">${productItemWorksheetBeanList.internetProductBeanItem.password}</span></td>
									<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p btn btn-success waves-effect waves-light btn-circle btn-sm"
									onclick="loadInternetItemEdit(${productItemBean.product.id },'${productItemBean.type}','${count}')" ><span
									class="ti-plus" data-toggle="tooltip"
									data-placement="bottom" title=""
									data-original-title="เพิ่มรายชิ้น"></span></a>&nbsp;<a class="run-swal cursor-p btn btn-danger waves-effect waves-light btn-circle btn-sm font-size-12"><span
											class="ti-trash remove-template" data-toggle="tooltip"
											data-placement="bottom" title=""
											data-original-title="ลบ"></span></a>
									</td>
								</tr>
							</c:forEach>
							</c:if>
							</c:forEach>
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
<%-- 													<th><center>ลำดับ</center></th> --%>
													<th><center>ชื่อค่าแรง	/ ค่าบริการ</center></th>
													<th style="width: 130px;"><center>จำนวน</center></th>
													<th style="width: 160px;"><center>ราคา (ไม่รวม Vat)</center></th>
													<th><center>ฟรี</center></th>
													<th style="width: 165px;"><center>ราคารวม (ไม่รวม Vat)</center></th>
													<th style="width: 10px;"></th>
												</tr>
											</thead>
											<tbody>
							<c:set var="count" value="0" scope="page" />
							<c:forEach items="${productItemBeans}" var="productItemBean" varStatus="i">	
							<c:if test="${productItemBean.type eq 'S'}">	
							<tr>
								<c:set var="count" value="${count + 1}" scope="page"/>
<%-- 								<td><center><span class="template-no"><c:out value="${count}" /></span></center> --%>
								<input id="productItemId_${i.count}" class="productItemWorksheetId" type="hidden" value="${productItemBean.id}">
								<input class="productType" type="hidden" value="${productItemBean.type}">			
								<td>${productItemBean.product.productName}</td>
								<td scope="row"><div class="input-group">
										<input type="number" class="form-control quantity calculate" value="${productItemBean.quantity}" >
										<div class="input-group-addon">${productItemBean.product.unit.unitName}</div>
									</div></td>
								<td scope="row"><div class="input-group">
										<input type="number" class="form-control salePrice calculate" value="${productItemBean.price}" >
										<div class="input-group-addon">บาท</div>
									</div></td>
								<td align="center" style="vertical-align: middle;">
										<label class="custom-control custom-checkbox">
											<input type="checkbox" class="custom-control-input check-box-free"
											<c:if test="${productItemBean.free }">checked</c:if> > <span
											class="custom-control-indicator"></span> <span
											class="custom-control-description"></span>
										</label>
								</td>
								<td scope="row"><div class="input-group">
										<input type="number" class="form-control totalPrice" value="<c:if test="${productItemBean.free}">0.0</c:if><c:if test="${!productItemBean.free}">${productItemBean.amount}</c:if>" >
										<div class="input-group-addon">บาท</div>
									</div></td>
								<td align="center" style="vertical-align: middle;"><a class="run-swal cursor-p btn btn-danger waves-effect waves-light btn-circle btn-sm font-size-12"><span
										class="ti-trash remove-template" data-toggle="tooltip"
										data-placement="bottom" title=""
										data-original-title="ลบ"></span></a></td></tr>
							</c:if>
							</c:forEach>
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
