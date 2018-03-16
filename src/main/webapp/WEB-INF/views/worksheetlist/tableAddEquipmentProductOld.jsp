<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<br />
<div class="row">
	<div class="col-md-12">
		<fieldset>
		<legend>&nbsp;&nbsp;&nbsp;อุปกรณ์ที่ใช้&nbsp;&nbsp;&nbsp;</legend>
			<div class="row">
				<div class="col-md-12">
				<div class="table-responsive">
					<table id="tableEquipmentProductOld" class="table table-bordered mb-0 table-hover">
						<thead class="thead-bg">
							<tr>
								<th style="vertical-align: middle; width: 20px;">ลำดับ</th>
								<th style="vertical-align: middle;">รหัสสินค้า </th>
								<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
								<th style="vertical-align: middle;">รหัสสินค้า / Serial
									Number</th>
								<th style="vertical-align: middle; width: 150px;">จำนวนที่เบิก
								</th>
<!-- 								<th style="vertical-align: middle; width: 30px;"></th> -->
							</tr>
						</thead>
						<tbody>
							<c:set var="countNo" value="1" scope="page" />
							<c:forEach var="productItemOld" items="${worksheetBean.productItemList}" varStatus="i">
								<c:choose>
									<c:when test="${productItemOld.type eq 'E'}">
										<c:forEach var="productWorkSheetItem" items="${productItemOld.productItemWorksheetBeanList}" varStatus="j">
											<tr>
												<td>
													<center>
														<c:out value="${countNo}" />
														<c:set var="countNo" value="${countNo + 1}" scope="page"/>
													</center>
												</td>
												<td>
													<center>
														${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productCode }
													</center>
												</td>
												<td>
													${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }
												</td>
												<td>
													<center>
														${productWorkSheetItem.equipmentProductItemBean.serialNo }
													</center>
												</td>
												<td>
													<center>
														${productWorkSheetItem.quantity }
													</center>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:when test="${productItemOld.type eq 'I'}">
										<c:forEach var="productWorkSheetItem" items="${productItemOld.productItemWorksheetBeanList}" varStatus="j">
											<tr>
												<td>
													<center>
														<c:out value="${countNo}" />
														<c:set var="countNo" value="${countNo + 1}" scope="page"/>
													</center>
												</td>
												<td>
													<center>
														${productWorkSheetItem.internetProductBeanItem.internetProductBean.productCode }
													</center>
												</td>
												<td>
													${productWorkSheetItem.internetProductBeanItem.internetProductBean.productName }
												</td>
												<td>
													<center>
														-
													</center>
												</td>
												<td>
													<center>
														${productWorkSheetItem.quantity }
													</center>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:when test="${productItemOld.type eq 'S'}">
											<tr>
												<td>
													<center>
														<c:out value="${countNo}" />
														<c:set var="countNo" value="${countNo + 1}" scope="page"/>
													</center>
												</td>
												<td>
													<center>
														${productItemOld.serviceProductBean.productCode }
													</center>
												</td>
												<td>
													${productItemOld.serviceProductBean.productName }
												</td>
												<td>
													<center>
														-
													</center>
												</td>
												<td>
													<center>
														-
													</center>
												</td>
											</tr>
									</c:when>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
			</div>
		</fieldset>
	</div>
</div>