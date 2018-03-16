<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productadd" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายละเอียดสินค้า</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="material-design fixed-sidebar fixed-header">

	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row" >
						<div class="col-md-5">
							<h3>รายละเอียดสินค้า</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderequipmentproduct">รายการสินค้า/บริการ</a></li>
								<li class="breadcrumb-item active">ความเคลื่อนไหวของสินค้า</li>
							</ol>
						</div>
						<div class="col-md-7 mt05">
							<!--<button type="button"
								class="btn btn-warning label-left float-xs-right mr-0-5">
								<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูลสินค้า
							</button>  -->
							<c:if test="${!empty equipmentProductItemBean.serialNo}">
							<button type="button" onclick="openDialogUpdateStatus();" 
								class="btn btn-info label-left float-xs-right mr-0-5">
								<span class="btn-label"><i class="ti-wand"></i></span>ปรับสถานะอุปกรณ์
							</button>
							</c:if>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">

							<div class="row">
								<div class="col-md-7">
									<h4>${equipmentProductBean.productName }</h4>
								</div>
								<div class="col-md-5" align="right">
									<b>สถานะอุปกรณ์ : </b>
									<c:choose>
										<c:when test="${equipmentProductItemBean.statusReal == 0}">
									       <i class="ion-record text-red"></i> ${equipmentProductItemBean.status }
									    </c:when>
										<c:when test="${equipmentProductItemBean.statusReal == 1}">
									        <i class="ion-record text-green"></i> ${equipmentProductItemBean.status }
									    </c:when>
									    <c:when test="${equipmentProductItemBean.statusReal == 2}">
									        <i class="ion-record text-warning"></i> ${equipmentProductItemBean.status }
									    </c:when>
									    <c:when test="${equipmentProductItemBean.statusReal == 3}">
									        <i class="ion-record text-primary"></i> ${equipmentProductItemBean.status }
									    </c:when>
									    <c:when test="${equipmentProductItemBean.statusReal == 4}">
									        <i class="ion-record text-info"></i> ${equipmentProductItemBean.status }
									    </c:when>
									    <c:when test="${equipmentProductItemBean.statusReal == 6}">
									        <i class="ion-record text-warning"></i> ${equipmentProductItemBean.status }
									    </c:when>
									    <c:when test="${equipmentProductItemBean.statusReal == 7}">
									        <i class="ion-record text-gray"></i> ${equipmentProductItemBean.status }
									    </c:when>
									</c:choose>
								</div>
							</div>


						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-3">
													<b>รหัสสินค้า</b><br>${equipmentProductBean.productCode }
												</div>
												<div class="col-md-3">
													<b>ชื่อสินค้า</b><br>${equipmentProductBean.productName }
												</div>
												<div class="col-md-3">
													<b>ประเภทสินค้าและบริการ</b><br>${equipmentProductBean.productCategory.equipmentProductCategoryName }
												</div>
												<div class="col-md-3">
													<b>เอกสารอ้างอิง</b><br>${equipmentProductItemBean.referenceNo }
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<b>Serial Number</b><br>${equipmentProductItemBean.serialNo }
												</div>
												<div class="col-md-3">
													<b>Supplier</b><br>${equipmentProductBean.supplier }
												</div>
												<div class="col-md-3">
													<b>ราคาต้นทุน</b><br>${textUtil.getFormatNumber(equipmentProductItemBean.cost)} บาท
												</div>
												<div class="col-md-3">
													<b>ราคารวมภาษี</b><br>${textUtil.getFormatNumber(equipmentProductItemBean.priceIncTax)} บาท
												</div>
											</div>
											<div class="row mt05">
												<div class="col-md-3">
													<b>ราคาขาย</b><br>${textUtil.getFormatNumber(equipmentProductItemBean.salePrice)} บาท
												</div>
												<div class="col-md-3">
													<b>วันที่สั่งซื้อ</b><br>${equipmentProductItemBean.orderDate }
												</div>
												<div class="col-md-3">
													<b>วันที่นำเข้าในระบบ</b><br>${equipmentProductItemBean.importSystemDate }
												</div>
												<div class="col-md-3">
													<b>ผู้บันทึกลงในระบบ</b><br>
													${equipmentProductItemBean.personnelBean.firstName } &nbsp;
													${equipmentProductItemBean.personnelBean.lastName }
												</div>
											</div>
											<div class="row mt05">
												<div class="col-md-3">
													<b>รับประกันถึงวันที่</b><br>${equipmentProductItemBean.guaranteeDate }
												</div>
												<div class="col-md-3">
													<b>หมวดบัญชีสินค้า</b><br>
													<c:choose>
														<c:when test="${equipmentProductBean.financialType eq 'A' }">
															ทรัพย์สิน
														</c:when>
														<c:when test="${equipmentProductBean.financialType eq 'C' }">
															ต้นทุน
														</c:when>
													</c:choose>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								
								<c:if test="${equipmentProductItemBean.historyUseEquipmentBeans != null && equipmentProductItemBean.historyUseEquipmentBeans.size() > 0}">
								
									<div class="row mt15">
										<div class="col-md-12">
											<fieldset>
												<legend>&nbsp;&nbsp;&nbsp;ประวัติการใช้งานสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
												<div class="row">
													<div class="col-md-12 mb05">
														<div class="table-responsive">
															<table class="table table-bordered table-hover">
																<thead class="thead-default">
																	<tr>
																		<th><center>เอกสารอ้างอิง</center></th>
																		<th>ชื่อลูกค้า</th>
																		<th><center>สถานะ</center></th>
																		<th><center>วันที่ใช้งาน</center></th>
																		<th><center>วันที่คืน</center></th>
	
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${equipmentProductItemBean.historyUseEquipmentBeans}" var="historyUseBean" varStatus="i">
																	<tr>
																		<td><center>
																		<a target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${historyUseBean.serviceApplicationBean.id}">
																		<b>${historyUseBean.serviceApplicationBean.serviceApplicationNo }</b>
																		</a>
																		</center>
																		</td>
																		<td>
																			${historyUseBean.customerBean.firstName } &nbsp;
																			${historyUseBean.customerBean.lastName }
																		</td>
																		<td><center>${historyUseBean.status }</center></td>
																		<td><center>${historyUseBean.activeDate }</center></td>
																		<td><center>${historyUseBean.returnDate }</center></td>
																	</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</div>
								
								</c:if>
								
								<c:if test="${!empty equipmentProductItemBean.serialNo}">
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ประวัติการปรับสถานะ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mb05">
												<div class="col-md-12 mb05">
													<div class="table-responsive">
														<table class="table table-bordered mb-0 table-hover">
															<thead class="thead-default">
																<tr>
																	<th style="width: 80px;"><center>ลำดับ</center></th>
																	<th><center>วันที่แจ้งซ่อม</center></th>
																	<th><center>ผู้แจ้ง</center></th>
																	<th><center>วันที่บันทึก</center></th>
																	<th>หมายเหตุ</th>
																	<th><center>สถานะอุปกรณ์</center></th>

																</tr>
															</thead>
															<tbody>
																<c:forEach items="${equipmentProductItemBean.historyUpdateStatusBeans }" var="historyStatusBean" varStatus="i">
																	<tr>
																		<td><center>${i.count }</center></td>
																		<td class="td-middle">
																			<center>
																				${historyStatusBean.dateRepair }
																			</center>
																		</td>
																		<td>
																			<c:choose>
																				<c:when test="${historyStatusBean.personnelBean != null && historyStatusBean.personnelBean.firstName != null }">
																					<center>
																						${historyStatusBean.personnelBean.firstName } &nbsp;
																						${historyStatusBean.personnelBean.lastName }
																					</center>
																				</c:when>
																				<c:otherwise>
																					<center>
																						${historyStatusBean.informer }
																					</center>
																				</c:otherwise>
																			</c:choose>
																			
																		</td>
																		<td><center>${historyStatusBean.recordDate }</center></td>
																		<td>${historyStatusBean.remark }</td>
																		<td><center>${historyStatusBean.status }</center></td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- modal delete -->
			<jsp:include page="modal_status.jsp"></jsp:include>
			<!-- end modal delete -->
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
		
	</script>

</body>
</html>
