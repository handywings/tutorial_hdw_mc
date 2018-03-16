<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productorderequipmentproduct" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ข้อมูลสินค้า ${equipmentProductBean.productName}</title>
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
					<div class="row mt05 mb05" >
						<div class="col-md-8">
							<h3>${equipmentProductBean.productName}</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderequipmentproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item active">ข้อมูลสินค้า</li>

							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M18.edit" >
							<a href="${pageContext.request.contextPath}/productorderequipmentproduct/edit/${equipmentProductBean.id}">
							<button type="button" class="btn btn-warning label-left float-xs-right mr-0-5 mb05">
										<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูล
									</button>
							</a>
						</perm:permission>
						</div>
					</div>

					<div class="card">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ข้อมูลสินค้า
										${equipmentProductBean.productName}

									</h4>
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
													<b>รหัสสินค้า</b><br>${equipmentProductBean.productCode}
												</div>
												<div class="col-md-3">
													<b>ชื่อสินค้า</b><br>${equipmentProductBean.productName}
												</div>
												<div class="col-md-3">
													<b>ประเภทสินค้าและบริการ</b><br>${equipmentProductBean.productCategory.equipmentProductCategoryName}
												</div>
												<div class="col-md-3">
													<b>Supplier</b><br>${equipmentProductBean.supplier}
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<b>หน่วยนับ</b><br>
													${equipmentProductBean.unit.unitName }
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
								 <div class="row mt15">
								</div> 
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-1 table-hover">
												<thead class="thead-default">
													<tr style="background: #e8e8e8;">
														<th width="50px;"><center>ลำดับ</center></th>
														<th><center>SN</center></th>
														<th><center>ราคาต้นทุน</center></th>
														<th><center>จำนวนนำเข้า</center></th>
														<th><center>วันที่บันทึก</center></th>
														<th><center>สถานะใช้งาน</center></th>
														<th><center>อ้างอิง</center></th>
														<th width="50px;"></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${equipmentProductBean.equipmentProductItemBeans}" var="productItem" varStatus="i">
														<tr>
															<td class="td-middle" style="width: 20px;"><center>${i.count }</center></td>
															<td>
															<c:choose>
																<c:when test="${productItem.serialNo != ''}"><center><b>${productItem.serialNo}</b></center></c:when>
																<c:otherwise><center>-</center></c:otherwise>
															</c:choose>
															</td>
															<td class="td-middle"><center>${textUtil.getFormatNumber(productItem.cost) }</center></td>
															<td class="td-middle"><center>${textUtil.getFormatNumberInt(productItem.numberImport) }</center></td>
															<td class="td-middle"><center><small>${productItem.importSystemDate}</small></center></td>
															<td class="td-middle"><center>${productItem.status}</center></td>
															<td class="td-middle"><center>${productItem.referenceNo}</center></td>
															<td class="td-middle">
															<center>
															<a href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${productItem.id}">
																<span class="ti-search" data-toggle="tooltip"
																	data-placement="bottom" title=""
																	data-original-title="ดูรายละเอียด"></span>
															</a>	
															</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
								
							</div>
						</div>
					</div>
					<div class="card mb30">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการการขอเบิกสินค้าเพื่อสำรอง

									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">								
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-1 table-hover">
												<thead class="thead-default">
													<tr style="background: #e8e8e8;">
														<th><center>ลำดับ</center></th>														
														<th><center>หมายเลขการเบิก</center></th>
														<th><center>Serial Number</center></th>
														<th><center>ผู้ขอเบิก</center></th>
														<th><center>วันที่เบิก</center></th>
														<th><center>จำนวนที่เบิก</center></th>
														<th><center>เบิกเพื่อ</center></th>
													</tr>
												</thead>
												<tbody>
													<c:set var="count" value="0" scope="page" />
													<c:forEach items="${equipmentProductBean.equipmentProductItemBeans}" var="productItem" varStatus="i">
														<c:forEach items="${productItem.requisitionItemBeans}" var="requisitionItem" varStatus="j">
														<tr>
														    <c:set var="count" value="${count + 1}" scope="page"/>
															<td class="td-middle" style="width: 20px;"><center><c:out value="${count}" /></center></td>	
															<td class="td-middle"><center><a href="${pageContext.request.contextPath}/requisitionlist/view/${requisitionItem.requisitionDocumentBean.id}" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${requisitionItem.requisitionDocumentBean.requisitionDocumentCode}</b></a></center></td>
															<td>
															<c:choose>
																<c:when test="${productItem.serialNo != ''}"><center>${productItem.serialNo}</center></c:when>
																<c:otherwise><center>-</center></c:otherwise>
															</c:choose>
															</td>
															<td class="td-middle">
															<c:choose>
																<c:when test="${requisitionItem.requisitionDocumentBean.withdraw == 'R'}"><center>${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.firstName}&nbsp;${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.lastName} (${requisitionItem.requisitionDocumentBean.technicianGroup.personnel.nickName})</center></c:when>
																<c:otherwise><center>-</center></c:otherwise>
															</c:choose>
															</td>
															<td class="td-middle"><center>${requisitionItem.requisitionDocumentBean.createDateTh}</center></td>
															<td class="td-middle">
																<center>
																	${textUtil.getFormatNumberInt(requisitionItem.quantity)} 
																	<c:if test="${requisitionItem.returnEquipmentProductItem > 0 }">
																		<br><small style="color: gray;"><b>(&nbsp;คืนแล้ว ${requisitionItem.returnEquipmentProductItem } ${requisitionItem.equipment.unit.unitName}&nbsp;)</b></small>
																	</c:if>
																	
																</center>
															</td>
															<td class="td-middle"><center>${requisitionItem.requisitionDocumentBean.withdrawText}</center></td>															
														</tr>
														</c:forEach>
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
			</div>
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
