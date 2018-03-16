<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="table-responsive">
<table class="table table-bordered table-hover" id="tbNoVat" >
	<thead class="thead-default">
		<tr>
			<th style="vertical-align: middle;"><center>ลำดับ</center></th>
			<th style="vertical-align: middle;"><center>ประเภทรายการ</center></th>
			<th style="vertical-align: middle;">รายละเอียด</th>
			<th style="vertical-align: middle;">จำนวน</th>
			<th  style="text-align: right;">ราคา/หน่วย (บาท)</th>
			<th  style="text-align: right;">ยอดหนี้รวม (บาท)</th>
			<th  style="text-align: right;">ยอดรับ (บาท)</th>
			<th align="center">หมายเหตุ</th>
		</tr>
	</thead>
	<c:choose>
		<c:when test="${invoiceBean.invoiceType eq 'O'}">
			<c:if test="${invoiceBean.additionMonthlyFee.type eq 'R' }">
				<c:set var="calAmountAdditionNoVat" value="${invoiceBean.additionMonthlyFee.amount}" />
				<c:set var="displayAmountAdditionNoVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
			</c:if>
			<c:if test="${invoiceBean.additionMonthlyFee.type ne 'R' }">
				<c:set var="calAmountAdditionNoVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
				<c:set var="displayAmountAdditionNoVat" value="${invoiceBean.additionMonthlyFee.amount}" />
			</c:if>
			<tbody>
					<c:set var="countTbVatTypeO" value="${1 }" />
					<c:choose>
						<c:when test="${invoiceBean.cutting }">
							<c:set var="calSpencialDiscountNoVat" value="${invoiceBean.specialDiscount}" />
						</c:when>
						<c:otherwise>
							<c:set var="calSpencialDiscountNoVat" value="${0}" />
						</c:otherwise>
					</c:choose>
					<tr>
					<td><center>
						${countTbVatTypeO }
						<c:set var="countTbVatTypeO" value="${countTbVatTypeO + 1}" />
					</center></td>
					<td><center>ค่าบริการ</center></td>
					<td>ค่าบริการรอบบิล  ${roundServiceTypeO }</td>
					<td align="center">1</td>
					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${invoiceBean.amount + calAmountAdditionNoVat + calSpencialDiscountNoVat}" /></td>

					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${invoiceBean.amount + calAmountAdditionNoVat + calSpencialDiscountNoVat}" /></td>

					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${invoiceBean.amount + calAmountAdditionNoVat + calSpencialDiscountNoVat}" /></td>
					<td align="center">-</td>
				</tr>
				<c:if test="${invoiceBean.additionMonthlyFee.type ne 'N' }">
					<c:choose>
						<c:when test="${invoiceBean.additionMonthlyFee.type  eq 'A'}">
							<tr>
								<td><center>
									${countTbVatTypeO }
									<c:set var="countTbVatTypeO" value="${countTbVatTypeO + 1}" />
								</center></td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการเพิ่มจุดติดตั้ง</td>
								<td align="center">1</td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td align="center">-</td>
							</tr>
						</c:when>
						<c:when test="${invoiceBean.additionMonthlyFee.type  eq 'R'}">
							<tr>
								<td><center>
									${countTbVatTypeO }
									<c:set var="countTbVatTypeO" value="${countTbVatTypeO + 1}" />
								</center></td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการลดจุดติดตั้ง</td>
								<td align="center">1</td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionNoVat}" /></td>
			
								<td align="center">-</td>
							</tr>
						</c:when>
					</c:choose>
				</c:if>
					<c:if test="${invoiceBean.cutting}">
						<tr>
							<td><center>
									${countTbVatTypeO }
									<c:set var="countTbVatTypeO" value="${countTbVatTypeO + 1}" />
								</center></td>
							<td><center>ค่าบริการ</center></td>
							<td>ส่วนลดพิเศษตัดสาย</td>
							<td align="center">1</td>
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.specialDiscount*(-1)}" /></td>

							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.specialDiscount*(-1)}" /></td>

							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.specialDiscount*(-1)}" /></td>

							<td align="center">-</td>
						</tsr>
					</c:if>
				</tbody>
			<tfoot>
				<tr>
					<td colspan="5"
						style="vertical-align: middle; border-left: 1px solid #fff; border-bottom: 1px solid #fff;">
						<h3>
							<center>
								( <span class="textNumber"></span> )
							</center>
						</h3>
					</td>
					<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
					<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>
							<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${invoiceBean.amount}" />
						</h3></td>
				</tr>
			</tfoot>
		</c:when>
		<c:otherwise>
			<tbody>
				<c:set var="totalListNoVat" value="${0}" />
				<c:set var="countTbNoVat" value="${1 }" />
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
				<c:choose>
					<c:when test="${invoiceBean.serviceApplication.servicepackage.monthlyService != true}">
						<tr>
							<td>
								<center>
									${countTbNoVat }
									<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
								</center>
							</td>
							<td><center>ค่าบริการ</center></td>
							<td>ค่าบริการรายครั้ง</td>
							<td align="left">1 ครั้ง</td>
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.serviceApplication.oneServiceFee}" /></td>
	
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.serviceApplication.oneServiceFee}" /></td>
	
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.serviceApplication.oneServiceFee}" />
								<c:set var="totalListVat"
									value="${totalListNoVat + invoiceBean.serviceApplication.oneServiceFee}" />
							</td>
	
							<td align="center">-</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
								<center>
									${countTbNoVat }
									<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
								</center>
							</td>
							<td><center>ค่าบริการ</center></td>
							<td>ค่าบริการรอบบิลแรกเดือน ${roundService }</td>
							<td align="left">${dateTotal } วัน</td>
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${pricePerDay}" /></td>
	
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${totalPrice}" /></td>
	
							<td  style="text-align: right;"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${totalPrice}" />
								<c:set var="totalListVat"
									value="${totalListNoVat + totalPrice}" />
							</td>
	
							<td align="center">-</td>
						</tr>
					</c:otherwise>
				</c:choose>
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าติดตั้ง</td>
						<td align="left">1 หน่วย</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.installationFee}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.installationFee}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.installationFee}" />
							<c:set var="totalListVat"
								value="${totalListNoVat + invoiceBean.serviceApplication.installationFee}" />
						</td>

						<td align="center">-</td>
					</tr>
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่ามัดจำอุปกรณ์</td>
						<td align="left">1 หน่วย</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.deposit}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.deposit}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.deposit}" />
							<c:set var="totalListVat"
								value="${totalListNoVat + invoiceBean.serviceApplication.deposit}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
				<!-- 				add point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_AP'}">
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าเพิ่มจุด</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" />
							<c:set var="totalListVat"
								value="${totalListVat + invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>

<!-- 				move point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_MP'}">
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายจุด</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" />
							<c:set var="totalListVat"
								value="${totalListVat + invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
<!-- 				move cable -->		
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_M'}">
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายสาย</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" />
							<c:set var="totalListVat"
								value="${totalListVat + invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
				<c:forEach var="productItem"
					items="${invoiceBean.worksheet.productItemList}" varStatus="i">
					<c:choose>
						<c:when test="${productItem.type eq 'S' }">
							<tr>
								<td>
									<center>
										${countTbNoVat }
										<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
									</center>
								</td>
								<td><center>${productItem.productCategoryName }</center></td>
								<td>${productItem.serviceProductBean.productName }</td>
								<td align="left">${productItem.quantity }
									${productItem.serviceProductBean.unit.unitName }</td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${productItem.price}" /></td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${productItem.amount}" /></td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${productItem.amount}" /> <c:set
										var="totalListNoVat"
										value="${totalListNoVat + productItem.amount}" /></td>
								<td align="center"><c:choose>
										<c:when test="${productItem.free }">
																				ฟรี
																			</c:when>
										<c:when test="${productItem.lend}">
																				ยืม
																			</c:when>
									</c:choose></td>
							</tr>
						</c:when>
					</c:choose>
				</c:forEach>
				
<!-- 				deposit -->
				<c:if test="${invoiceBean.worksheet.workSheetType ne 'C_S'}">
					<c:forEach var="productItem" items="${invoiceBean.worksheet.productItemList}" varStatus="i">
					<c:choose>
						<c:when test="${productItem.type eq 'E' }">
							<c:forEach var="productWorkSheetItem" items="${productItem.productItemWorksheetBeanList}"
								varStatus="j">
								
								<c:if test="${productWorkSheetItem.lend}">
									<tr>
										<td>
											<center>
												${countTbNoVat }
												<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
											</center>
										</td>
										<td><center>ค่าบริการ</center></td>
										<td>
											 ค่ามัดจำอุปกรณ์ 
											${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }
	
											<c:if
												test="${ not empty productWorkSheetItem.equipmentProductItemBean.serialNo }">
																			(${productWorkSheetItem.equipmentProductItemBean.serialNo })
																		</c:if>
	
										</td>
										<td align="left">${productWorkSheetItem.quantity }
											${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }
										</td>
										<td  style="text-align: right;"><fmt:formatNumber type="number"
												maxFractionDigits="2" value="${productWorkSheetItem.deposit}" />
										</td>
										<td  style="text-align: right;"><fmt:formatNumber type="number"
												maxFractionDigits="2" value="${productWorkSheetItem.deposit}" />
										</td>
										<td  style="text-align: right;"><fmt:formatNumber type="number"
												maxFractionDigits="2" value="${productWorkSheetItem.deposit}" />
											<c:set var="totalListNoVat"
												value="${totalListNoVat + productWorkSheetItem.deposit}" /></td>
										<td align="center"><c:choose>
												<c:when test="${productWorkSheetItem.lend}">
																					ค่ามัดจำ
																				</c:when>
											</c:choose></td>
									</tr>
								</c:if>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:forEach>
				</c:if>
				
				<c:forEach var="subworksheet"
					items="${invoiceBean.worksheet.subWorksheetBeanList}" varStatus="i">
					<tr>
						<td>
							<center>
								${countTbNoVat }
								<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
							</center>
						</td>
						<td><center>ใบงานย่อย</center></td>
						<td>${subworksheet.workSheetTypeText }</td>
						<td style="text-align: left;">1 งาน</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${subworksheet.price}" /></td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${subworksheet.price}" /></td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2" value="${subworksheet.price}" /> <c:set
								var="totalListVat"
								value="${totalListNoVat + subworksheet.price}" /></td>
						<td align="center"></td>
					</tr>
				</c:forEach>
				<c:forEach var="productItem"
					items="${invoiceBean.worksheet.productItemList}" varStatus="i">
					<c:choose>
						<c:when test="${productItem.type eq 'E' }">
							<c:forEach var="productWorkSheetItem"
								items="${productItem.productItemWorksheetBeanList}"
								varStatus="j">
								<tr>
									<td>
										<center>
											${countTbNoVat }
											<c:set var="countTbNoVat" value="${countTbNoVat + 1}" />
										</center>
									</td>
									<td><center>${productItem.productCategoryName }</center></td>
									<td>
										${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }

										<c:if
											test="${ not empty productWorkSheetItem.equipmentProductItemBean.serialNo }">
																		(${productWorkSheetItem.equipmentProductItemBean.serialNo })
																	</c:if>

									</td>
									<td align="left">${productWorkSheetItem.quantity }
										${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }
									</td>
									<td  style="text-align: right;"><fmt:formatNumber type="number"
											maxFractionDigits="2" value="${productWorkSheetItem.price}" />
									</td>
									<td  style="text-align: right;"><fmt:formatNumber type="number"
											maxFractionDigits="2" value="${productWorkSheetItem.amount}" />
									</td>
									<td  style="text-align: right;"><fmt:formatNumber type="number"
											maxFractionDigits="2" value="${productWorkSheetItem.amount}" />
										<c:set var="totalListNoVat"
											value="${totalListNoVat + productWorkSheetItem.amount}" /></td>
									<td align="center"><c:choose>
											<c:when test="${productWorkSheetItem.free }">
																				ฟรี
																			</c:when>
											<c:when test="${productWorkSheetItem.lend}">
																				ยืม
																			</c:when>
										</c:choose></td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</c:forEach>
			</tbody>
			<c:choose>
			<c:when test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
				<tfoot>
					<tr>
						<td colspan="5" rowspan="2"
							style="vertical-align: middle; border-left: 1px solid #fff; border-bottom: 1px solid #fff;">
							<h3>
								<center>
									( <span class="textNumber"></span> )
								</center>
							</h3>
						</td>
						<td  style=" text-align: right; background-color: rgba(244, 67, 54, 0.12);"><b>ส่วนลด</b></td>
						<td  style=" text-align: right; background-color: rgba(244, 67, 54, 0.12); color: red">
							<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.firstBillFreeDisCount }" /></td>
					</tr>
					<tr>
						<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
						<td  style="text-align: right;background-color: #009688;color: #fff;"><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalPrice}" /></h3></td>
					</tr>
				</tfoot>
			</c:when>
			<c:otherwise>
					<tfoot>
						<tr>
							<td colspan="5"
								style="vertical-align: middle; border-left: 1px solid #fff; border-bottom: 1px solid #fff;">
								<h3>
									<center>
										( <span class="textNumber"></span> )
									</center>
								</h3>
							</td>
							<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
							<td  style="text-align: right;background-color: #009688;color: #fff;"><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceBean.amount }" /></h3></td>
						</tr>
					</tfoot>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</table>
</div>