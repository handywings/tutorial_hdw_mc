<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="requisitionlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดเลขที่ใบเบิกสินค้า ${requisitionDocumentBean.requisitionDocumentCode}</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
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
					<div class="row mb05 mt05 mb05" >
						<div class="col-md-8">
							<h3>รายละเอียดเลขที่ใบเบิกสินค้า ${requisitionDocumentBean.requisitionDocumentCode}</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/requisitionlist">รายการเบิกสินค้าทั้งหมด</a></li>
								<li class="breadcrumb-item active">รายละเอียดเลขที่ใบเบิกสินค้า
									${requisitionDocumentBean.requisitionDocumentCode}</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a target="_blank" href="${pageContext.request.contextPath}/requisitionlist/exportPdf/requisitionDocumentId/${requisitionDocumentBean.id}"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์รายละเอียดใบเบิกสินค้า
								</button></a>
						</div>
						
<!-- 						<div class="col-md-4 mt05" align="right"> -->
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${requisitionDocumentBean.status.numberValue == 1 }"> --%>
<!-- 									<button type="button" data-toggle="modal" data-target="#changeStatus" class="btn btn-info label-left float-xs-right mr-0-5"> -->
<!-- 										<span class="btn-label"><i class="ti-marker-alt"></i></span>ปรับสถานะอุปกรณ์ -->
<!-- 									</button> -->
<%-- 								</c:when> --%>
<%-- 							</c:choose>	 --%>
<%-- 							<jsp:include page="modal_adjust_status.jsp"></jsp:include> --%>
							
<!-- 						</div> -->
					</div>
					<div class="card mt05 mb30">
						<div class="card-header" style="font-size: 20px;">
							<div class="row">
								<div class="col-md-2">
									<img src="${pageContext.request.contextPath}/resources/assets/img/logo-invoice.png">
								</div>
								<div class="col-md-5">
									<b>เลขที่ใบเบิกสินค้า&nbsp;${requisitionDocumentBean.requisitionDocumentCode}</b>
								</div>
								<div class="col-md-5" align="right">
									<span class="ti-calendar"></span>&nbsp;วันที่ขอเบิก&nbsp;${requisitionDocumentBean.createDateTh}
								</div>
<!-- 								<div class="col-md-5" align="right">			 -->
<%-- 									<c:choose> --%>
<%-- 									<c:when test="${requisitionDocumentBean.status.numberValue == 1 }"> --%>
<%-- 										<b>สถานะ : </b><i class="ion-record text-green"></i> ${requisitionDocumentBean.status.stringValue} --%>
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<%-- 										<b>สถานะ : </b><i class="ion-record text-red"></i> ${requisitionDocumentBean.status.stringValue} --%>
<%-- 									</c:otherwise> --%>
<%-- 									</c:choose>	 --%>
<!-- 								</div> -->
							</div>

						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">

								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายละเอียดการขอเบิกสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-2">
														<b>เบิกเพื่อ :</b>
													</div>
													<div class="col-md-10">
														${requisitionDocumentBean.withdraw}
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-2">
														<b>รายละเอียดเพิ่มเติม :</b>
													</div>
													<div class="col-md-10">
														${requisitionDocumentBean.detail}
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-2">
														<b>รหัสผู้ขอเบิก :</b>
													</div>
													<div class="col-md-2">
														<c:choose>
															<c:when test="${requisitionDocumentBean.technicianGroup.personnel != null }">
																${requisitionDocumentBean.technicianGroup.personnel.personnelCode}
															</c:when>
															<c:otherwise>
																${requisitionDocumentBean.personnelBean.personnelCode}
															</c:otherwise>
														</c:choose>
													</div>
													<div class="col-md-2">
														<b>ชื่อผู้ขอเบิก :</b>
													</div>
													<div class="col-md-3">
														<c:choose>
															<c:when test="${requisitionDocumentBean.technicianGroup.personnel != null }">
																${requisitionDocumentBean.technicianGroup.personnel.firstName} 
																${requisitionDocumentBean.technicianGroup.personnel.lastName}
																&nbsp;(${requisitionDocumentBean.technicianGroup.personnel.nickName })
															</c:when>
															<c:otherwise>
																${requisitionDocumentBean.personnelBean.firstName} 
																${requisitionDocumentBean.personnelBean.lastName}
																&nbsp;(${requisitionDocumentBean.personnelBean.nickName })
															</c:otherwise>
														</c:choose>
													
													
														
													</div>
												</div>


											</div>
										</fieldset>
									</div>
								</div>								
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายการสินค้าและอุปกรณ์ที่ขอเบิก&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="">
													<div class="">
														<div class="table-responsive">
															<table class="table table-bordered table-hover">
																<thead class="thead-default">
																	<tr>
																		<th><center>ลำดับ</center></th>
																		<th><center>รหัสสินค้า</center></th>
																		<th><center>Serial Number</center></th>
																		<th>ชื่อเรียกสินค้า</th>
																		<th><center>จำนวนที่เบิก</center></th>
																		<th><center>หน่วย</center></th>
																		<th><center>หมายเหตุ</center></th>

																	</tr>
																</thead>
																<tbody>
																	<c:forEach var="requisitionItem" items="${requisitionDocumentBean.requisitionItemList}" varStatus="i">
																	<tr>
																		<td align="center">${i.count}</td>
																		<td align="center"><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${requisitionItem.equipment.id}" target="_blank"><b>${requisitionItem.equipment.productCode}</b></a></td>
																		<td align="center"><a href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${requisitionItem.equipmentItem.id}" target="_blank"><b>${requisitionItem.equipmentItem.serialNo}</b></a></td>
																		<td>${requisitionItem.equipment.productName}</td>
																		<td align="center">${textUtil.getFormatNumberInt(requisitionItem.quantity)}</td>
																		<td align="center">${requisitionItem.equipment.unit.unitName}</td>
																		<td align="center">
																			<c:if test="${requisitionItem.equipmentItem != null }">
																				<c:choose>
																					<c:when test="${requisitionItem.equipmentItem.referenceNo != null }">
																						<a target="_blank" href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${requisitionItem.equipmentItem.id }">เบิกจาก ${requisitionItem.equipmentItem.referenceNo }</a>
																					</c:when>
																					<c:otherwise>
																						<a target="_blank" href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${requisitionItem.equipmentItem.id }">เบิกจาก #${requisitionItem.equipmentItem.id }</a>
																					</c:otherwise>
																				</c:choose>
																			</c:if>
																			<c:if test="${requisitionItem.returnEquipmentProductItem > 0 }">
																				<br><small style="color: gray;"><b>(&nbsp;คืนแล้ว ${requisitionItem.returnEquipmentProductItem } ${requisitionItem.equipment.unit.unitName}&nbsp;)</b></small>
																			</c:if>
																		</td>
																	</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>


											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายละเอียดการอนุมัติใบเบิกสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row mt05">
													<div class="col-md-4">
														<b>รหัสผู้พิจารณา :</b> ${requisitionDocumentBean.personnelBean.personnelCode}
													</div>
													<div class="col-md-4">
														<b>ชื่อผู้พิจารณาใบเบิก :</b> 
														${requisitionDocumentBean.personnelBean.firstName} 
														${requisitionDocumentBean.personnelBean.lastName}
														&nbsp;(${requisitionDocumentBean.personnelBean.nickName })
													</div>
													<div class="col-md-4">
														<b>วันเวลาที่บันทึก :</b> ${requisitionDocumentBean.createDateTh}
													</div>		
												</div>


											</div>
										</fieldset>
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
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	
<script type="text/javascript">


</script>
	
</body>
</html>
