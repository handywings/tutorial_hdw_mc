<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="table-responsive">
<table class="table table-bordered table-hover" id="tbAddVat" style="display: none">
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
				<c:set var="calAmountAdditionAddVat" value="${invoiceBean.additionMonthlyFee.amount}" />
				<c:set var="displayAmountAdditionAddVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
			</c:if>
			<c:if test="${invoiceBean.additionMonthlyFee.type ne 'R' }">
				<c:set var="calAmountAdditionAddVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
				<c:set var="displayAmountAdditionAddVat" value="${invoiceBean.additionMonthlyFee.amount}" />
			</c:if>
			<tbody>
					<c:set var="countTbAddVatTypeO" value="${1 }" />
					<c:choose>
						<c:when test="${invoiceBean.cutting }">
							<c:set var="calSpencialDiscountAddVat" value="${invoiceBean.specialDiscount}" />
						</c:when>
						<c:otherwise>
							<c:set var="calSpencialDiscountAddVat" value="${0}" />
						</c:otherwise>
					</c:choose>
					
						<c:choose>
								<c:when test="${invoiceBean.status ne 'S' }">
									<c:set var="vatTemp" value="${0}" />
								</c:when>
								<c:when test="${invoiceBean.status eq 'S' }">
									<c:set var="vatTemp" value="${((invoiceBean.amount * 100)/(100+companyBean.vat))*(companyBean.vat/100)}" />
								</c:when>
							</c:choose>
							
					<tr>
					<td><center>
						${countTbAddVatTypeO }
						<c:set var="countTbAddVatTypeO" value="${countTbAddVatTypeO + 1}" />
					</center></td>
					<td><center>ค่าบริการ</center></td>
					<td>ค่าบริการรอบบิล ${roundServiceTypeO }</td>
					<td align="center">1</td>
					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${(invoiceBean.amount + calAmountAdditionAddVat + calSpencialDiscountAddVat)}" /></td>

					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${(invoiceBean.amount + calAmountAdditionAddVat + calSpencialDiscountAddVat)}" /></td>

					<td  style="text-align: right;"><fmt:formatNumber type="number"
							maxFractionDigits="2" value="${(invoiceBean.amount + calAmountAdditionAddVat + calSpencialDiscountAddVat)}" /></td>
					<td align="center">-</td>
				</tr>
				<c:if test="${invoiceBean.additionMonthlyFee.type ne 'N' }">
					<c:choose>
						<c:when test="${invoiceBean.additionMonthlyFee.type  eq 'A'}">
							<tr>
								<td><center>
									${countTbAddVatTypeO }
									<c:set var="countTbAddVatTypeO" value="${countTbAddVatTypeO + 1}" />
								</center></td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการเพิ่มจุดติดตั้ง</td>
								<td align="center">1</td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td align="center">-</td>
							</tr>
						</c:when>
						<c:when test="${invoiceBean.additionMonthlyFee.type  eq 'R'}">
							<tr>
								<td><center>
									${countTbAddVatTypeO }
									<c:set var="countTbAddVatTypeO" value="${countTbAddVatTypeO + 1}" />
								</center></td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการลดจุดติดตั้ง</td>
								<td align="center">1</td>
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td  style="text-align: right;"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${displayAmountAdditionAddVat}" /></td>
			
								<td align="center">-</td>
							</tr>
						</c:when>
					</c:choose>
				</c:if>
					<c:if test="${invoiceBean.cutting}">
						<tr>
							<td><center>
									${countTbAddVatTypeO }
									<c:set var="countTbAddVatTypeO" value="${countTbAddVatTypeO + 1}" />
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
						</tr>
					</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5" rowspan="2" style="vertical-align: middle;"><h3>
								<center>
									( <span class="textNumber"></span> )
								</center>
							</h3></td>
						<td  style="text-align: right;"><b>ยอดค่าบริการ</b></td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.amount}" /></td>
					</tr>
					<tr>
						<td  style="text-align: right;"><b>ภาษีมูลค่าเพิ่ม ${companyBean.vat }%</b></td>
						<td  style="text-align: right;">
							<c:choose>
								<c:when test="${invoiceBean.status ne 'S' }">
									<fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.amount * (companyBean.vat/100) }" />
								</c:when>
								<c:when test="${invoiceBean.status eq 'S' }">
									<fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${invoiceBean.amount * (companyBean.vat/100) }" />
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td colspan="5"
							style="border-left: 1px solid #fff; border-bottom: 1px solid #fff;"></td>
						<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
						<c:choose>
							<c:when test="${invoiceBean.status ne 'S' }">
								<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>
									<fmt:formatNumber type="number" maxFractionDigits="2"
										value="${invoiceBean.amount + (invoiceBean.amount * (companyBean.vat/100))}" />
								</h3></td>
							</c:when>
							<c:when test="${invoiceBean.status eq 'S' }">
								<td  style="text-align: right;background-color: #009688;color: #fff;"><h3>
									<fmt:formatNumber type="number" maxFractionDigits="2"
										value="${invoiceBean.amount + (invoiceBean.amount * (companyBean.vat/100))}" />
								</h3></td>
							</c:when>
						</c:choose>
					</tr>
				</tfoot>
		</c:when>
		<c:otherwise>
			<tbody>
				<c:set var="totalListAddVat" value="${0}" />
				<c:set var="countTbAddVat" value="${1 }" />
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
					<c:choose>
						<c:when test="${invoiceBean.serviceApplication.servicepackage.monthlyService != true}">
							<tr>
								<td>
									<center>
										${countTbAddVat }
										<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
									<c:set var="totalListAddVat"
										value="${totalListAddVat + invoiceBean.serviceApplication.oneServiceFee}" />
								</td>
		
								<td align="center">-</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>
									<center>
										${countTbAddVat }
										<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
									<c:set var="totalListAddVat"
										value="${totalListAddVat + totalPrice}" />
								</td>
		
								<td align="center">-</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<center>
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
							<c:set var="totalListAddVat"
								value="${totalListAddVat + invoiceBean.serviceApplication.installationFee}" />
						</td>

						<td align="center">-</td>
					</tr>
					<tr>
						<td>
							<center>
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
							<c:set var="totalListAddVat"
								value="${totalListAddVat + invoiceBean.serviceApplication.deposit}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
<!-- 				add point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_AP'}">
					<tr>
						<td>
							<center>
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าเพิ่มจุด</td>
						<td align="left">1 หน่วย</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" />
							<c:set var="totalListAddVat"
								value="${totalListAddVat + invoiceBean.worksheet.addPointWorksheetBean.addPointPrice}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
<!-- 				move point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_MP'}">
					<tr>
						<td>
							<center>
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายจุด</td>
						<td align="left">1 หน่วย</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" />
							<c:set var="totalListAddVat"
								value="${totalListAddVat + invoiceBean.worksheet.movePointWorksheetBean.movePointPrice}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
<!-- 				move cable -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_M'}">
					<tr>
						<td>
							<center>
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายสาย</td>
						<td align="left">1 หน่วย</td>
						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" /></td>

						<td  style="text-align: right;"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" />
							<c:set var="totalListAddVat"
								value="${totalListAddVat + invoiceBean.worksheet.moveWorksheetBean.moveCablePrice}" />
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
										${countTbAddVat }
										<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
										var="totalListAddVat"
										value="${totalListAddVat + productItem.amount}" /></td>
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
				<c:forEach var="productItem"
					items="${invoiceBean.worksheet.productItemList}" varStatus="i">
					<c:choose>
						<c:when test="${productItem.type eq 'E' }">
							<c:forEach var="productWorkSheetItem" items="${productItem.productItemWorksheetBeanList}"
								varStatus="j">
								<c:if test="${productWorkSheetItem.lend}">
								<tr>
									<td>
										<center>
											${countTbAddVat }
											<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
										<c:set var="totalListAddVat"
											value="${totalListAddVat + productWorkSheetItem.deposit}" /></td>
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
								${countTbAddVat }
								<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
								var="totalListAddVat"
								value="${totalListAddVat + subworksheet.price}" /></td>
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
											${countTbAddVat }
											<c:set var="countTbAddVat" value="${countTbAddVat + 1}" />
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
										<c:set var="totalListAddVat"
											value="${totalListAddVat + productWorkSheetItem.amount}" /></td>
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
			<tfoot>
				<tr>
					<c:choose>
						<c:when test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
						<td colspan="5" rowspan="3" style="vertical-align: middle;">
					</c:when>
						<c:otherwise>
							<td colspan="5" rowspan="2" style="vertical-align: middle;">
						</c:otherwise>
					</c:choose>
						<h3>
							<center>
								( <span class="textNumber"></span> )
							</center>
						</h3>
					</td>
					<td  style="text-align: right;"><b>ยอดค่าบริการ</b></td>
					<td  style="text-align: right;">
							<fmt:formatNumber type="number"
							maxFractionDigits="2" value="${totalListAddVat}" />
					</td>
					<c:choose>
						<c:when test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
						<td rowspan="4" style="vertical-align: middle;"></td>
					</c:when>
						<c:otherwise>
							<td rowspan="3" style="vertical-align: middle;"></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
					<tr>
						<td  style="text-align: right;background-color: rgba(244, 67, 54, 0.12);"><b>ส่วนลด</b></td>
						<td  style="text-align: right;background-color: rgba(244, 67, 54, 0.12); color: red">
						<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.serviceApplication.firstBillFreeDisCount}" /></td>
					</tr>
				</c:if>
				<tr>
					<td  style="text-align: right;"><b>ภาษีมูลค่าเพิ่ม ${companyBean.vat }%</b></td>
					<td  style="text-align: right;">
						<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
								<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${totalListAddVat * (companyBean.vat / 100)}" />
						</c:if>
						<c:if test="${invoiceBean.worksheet.workSheetType ne 'C_S'}">
						<c:choose>
							<c:when test="${invoiceBean.status ne 'S' }">
								<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${invoiceBean.amount * (companyBean.vat / 100)}" />
							</c:when>
							<c:when test="${invoiceBean.status eq 'S' }">
								<c:set var="netAmount" value="${(invoiceBean.amount * 100)/(100+companyBean.vat)}" />
								<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${netAmount * (companyBean.vat/100)}" />
							</c:when>
						</c:choose>
						</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="5"
						style="border-left: 1px solid #fff; border-bottom: 1px solid #fff;"></td>
					<td  style="text-align: right;background-color: #009688;color: #fff;" ><h3>ยอดรวมสุทธิ</h3></td>
					<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
						<td  style="text-align: right;background-color: #009688;color: #fff;" ><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalListAddVat + (totalListAddVat * (companyBean.vat / 100))}" /></h3></td>
					</c:if>
					<c:if test="${invoiceBean.worksheet.workSheetType ne 'C_S'}">
					<c:choose>
						<c:when test="${invoiceBean.status ne 'S' }">
							<td  style="text-align: right;background-color: #009688;color: #fff;" ><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceBean.amount + (invoiceBean.amount * (companyBean.vat / 100))}" /></h3></td>
						</c:when>
						<c:when test="${invoiceBean.status eq 'S' }">
							<td  style="text-align: right;background-color: #009688;color: #fff;" ><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceBean.amount}" /></h3></td>
						</c:when>
					</c:choose>
					</c:if>
				</tr>
			</tfoot>
		</c:otherwise>
	
	</c:choose>
</table>
</div>