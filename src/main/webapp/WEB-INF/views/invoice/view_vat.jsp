<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="table-responsive">
<table class="table table-bordered table-hover" id="tbVat" style="display: none" >
	<thead class="thead-default">
		<tr>
			<th style="vertical-align: middle;"><center>ลำดับ</center></th>
			<th style="vertical-align: middle;"><center>ประเภทรายการ</center></th>
			<th style="vertical-align: middle;">รายละเอียด</th>
			<th style="vertical-align: middle;width: 100px;"><center>จำนวน</center></th>
			<th style="text-align: right;width: 150px;">ราคา/หน่วย (บาท)</th>
			<th style="text-align: right;width: 140px;">ยอดหนี้รวม (บาท)</th>
			<th style="text-align: right;width: 140px;">ยอดรับ (บาท)</th>
			<th><center>หมายเหตุ</center></th>
		</tr>
	</thead>

	<c:choose>
		<c:when test="${invoiceBean.invoiceType eq 'O'}">

			<c:if test="${invoiceBean.additionMonthlyFee.type eq 'R' }">
				<c:set var="calAmountAdditionVat" value="${invoiceBean.additionMonthlyFee.amount}" />
				<c:set var="displayAmountAdditionVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
			</c:if>
			<c:if test="${invoiceBean.additionMonthlyFee.type ne 'R' }">
				<c:set var="calAmountAdditionVat" value="${invoiceBean.additionMonthlyFee.amount * -1}" />
				<c:set var="displayAmountAdditionVat" value="${invoiceBean.additionMonthlyFee.amount}" />
			</c:if>
			
			<tbody>
			<c:set var="countTbVatTypeO" value="${1 }" />
			<c:choose>
				<c:when test="${invoiceBean.cutting }">
					<c:set var="calSpencialDiscountVat" value="${invoiceBean.specialDiscount}" />
				</c:when>
				<c:otherwise>
					<c:set var="calSpencialDiscountVat" value="${0}" />	
				</c:otherwise>
			</c:choose>
				<tr>
					<td><center>
						${countTbVatTypeO }
						<c:set var="countTbVatTypeO" value="${countTbVatTypeO + 1}" />
					</center></td>
					<td><center>ค่าบริการ</center></td>
					<td>ค่าบริการรอบบิล ${roundServiceTypeO }</td>
					<td align="center">1</td>
					<td align="right"><fmt:formatNumber type="number"
							maxFractionDigits="2"
							value="${((invoiceBean.amount + calAmountAdditionVat + calSpencialDiscountVat) * 100)/(100+companyBean.vat)}" /></td>

					<td align="right"><fmt:formatNumber type="number"
							maxFractionDigits="2"
							value="${((invoiceBean.amount + calAmountAdditionVat + calSpencialDiscountVat) * 100)/(100+companyBean.vat)}" /></td>

					<td align="right"><fmt:formatNumber type="number"
							maxFractionDigits="2"
							value="${((invoiceBean.amount + calAmountAdditionVat + calSpencialDiscountVat) * 100)/(100+companyBean.vat)}" /></td>

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
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
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
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(displayAmountAdditionVat * 100)/(100+companyBean.vat)}" /></td>
			
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
							<td align="right"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${(invoiceBean.specialDiscount * 100)/(100+companyBean.vat)*(-1)}" /></td>

							<td align="right"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${(invoiceBean.specialDiscount * 100)/(100+companyBean.vat)*(-1)}" /></td>

							<td align="right"><fmt:formatNumber type="number"
									maxFractionDigits="2"
									value="${(invoiceBean.specialDiscount * 100)/(100+companyBean.vat)*(-1)}" /></td>

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
					<td align="right"><b>ยอดค่าบริการ</b></td>
					<td align="right"><fmt:formatNumber type="number"
							maxFractionDigits="2"
							value="${(invoiceBean.amount * 100)/(100+companyBean.vat)}" /></td>
				</tr>
				<tr>
					<td align="right"><b>ภาษีมูลค่าเพิ่ม ${companyBean.vat }%</b></td>
					<td align="right"><fmt:formatNumber type="number"
							maxFractionDigits="2"
							value="${invoiceBean.amount - (invoiceBean.amount * 100)/(100+companyBean.vat)}" />
					</td>
				</tr>
				<tr>
					<td colspan="5"
						style="border-left: 1px solid #fff; border-bottom: 1px solid #fff;"></td>
					<td style="text-align: right;background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
					<td style="text-align: right;background-color: #009688;color: #fff;"><h3>
							<fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceBean.amount}" />
						</h3></td>
				</tr>
			</tfoot>
		</c:when>
 
		<c:otherwise>
			<tbody>
				<c:set var="totalListVat" value="${0}" />
				<c:set var="countTbVat" value="${1 }" />
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
					<c:choose>
						<c:when test="${invoiceBean.serviceApplication.servicepackage.monthlyService != true}">
							<tr>
								<td>
									<center>
										${countTbVat }
										<c:set var="countTbVat" value="${countTbVat + 1}" />
									</center>
								</td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการรายครั้ง</td>
								<td align="center">1 ครั้ง</td>
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.oneServiceFee * 100)/(100+companyBean.vat)}" /></td>
		
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.oneServiceFee * 100)/(100+companyBean.vat)}" /></td>
		
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.oneServiceFee * 100)/(100+companyBean.vat)}" />
									<c:set var="totalListVat"
										value="${totalListVat + (invoiceBean.serviceApplication.oneServiceFee * 100)/(100+companyBean.vat)}" />
								</td>
		
								<td align="center">-</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>
									<center>
										${countTbVat }
										<c:set var="countTbVat" value="${countTbVat + 1}" />
									</center>
								</td>
								<td><center>ค่าบริการ</center></td>
								<td>ค่าบริการรอบบิล ${roundService }</td>
								<td align="center">${invoiceBean.serviceApplication.servicepackage.perMounth} เดือน</td>
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.monthlyServiceFee * 100)/(100+companyBean.vat)}" /></td>
		
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.monthlyServiceFee * 100)/(100+companyBean.vat)}" /></td>
		
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(invoiceBean.serviceApplication.monthlyServiceFee * 100)/(100+companyBean.vat)}" />
									<c:set var="totalListVat"
										value="${totalListVat + (invoiceBean.serviceApplication.monthlyServiceFee * 100)/(100+companyBean.vat)}" />
								</td>
		
								<td align="center">-</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>
							<center>
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าติดตั้ง</td>
						<td align="center">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.installationFee * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.installationFee * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.installationFee * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (invoiceBean.serviceApplication.installationFee * 100)/(100+companyBean.vat)}" />
						</td>

						<td align="center">-</td>
					</tr>
					<tr>
						<td>
							<center>
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่ามัดจำอุปกรณ์</td>
						<td align="center">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.deposit * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.deposit * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.deposit * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (invoiceBean.serviceApplication.deposit * 100)/(100+companyBean.vat)}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>
				
				<!-- 				add point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_AP'}">
					<tr>
						<td>
							<center>
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าเพิ่มจุด</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.addPointWorksheetBean.addPointPrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.addPointWorksheetBean.addPointPrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.addPointWorksheetBean.addPointPrice * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (invoiceBean.worksheet.addPointWorksheetBean.addPointPrice * 100)/(100+companyBean.vat)}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>

<!-- 				move point -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_MP'}">
					<tr>
						<td>
							<center>
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายจุด</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.movePointWorksheetBean.movePointPrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.movePointWorksheetBean.movePointPrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.movePointWorksheetBean.movePointPrice * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (invoiceBean.worksheet.movePointWorksheetBean.movePointPrice * 100)/(100+companyBean.vat)}" />
						</td>

						<td align="center">-</td>
					</tr>
				</c:if>

<!-- 				move cable -->
				<c:if test="${invoiceBean.worksheet.workSheetType eq 'C_M'}">
					<tr>
						<td>
							<center>
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ค่าบริการ</center></td>
						<td>ค่าย้ายสาย</td>
						<td align="left">1 หน่วย</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.moveWorksheetBean.moveCablePrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.moveWorksheetBean.moveCablePrice * 100)/(100+companyBean.vat)}" /></td>

						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.worksheet.moveWorksheetBean.moveCablePrice * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (invoiceBean.worksheet.moveWorksheetBean.moveCablePrice * 100)/(100+companyBean.vat)}" />
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
										${countTbVat }
										<c:set var="countTbVat" value="${countTbVat + 1}" />
									</center>
								</td>
								<td><center>${productItem.productCategoryName }</center></td>
								<td>${productItem.serviceProductBean.productName }</td>
								<td><center>${productItem.quantity }
									${productItem.serviceProductBean.unit.unitName }</center></td>
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(productItem.price * 100)/(100+companyBean.vat)}" />
								</td>
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(productItem.amount * 100)/(100+companyBean.vat)}" />
								</td>
								<td align="right"><fmt:formatNumber type="number"
										maxFractionDigits="2"
										value="${(productItem.amount * 100)/(100+companyBean.vat)}" />
									<c:set var="totalListVat"
										value="${totalListVat + (productItem.amount * 100)/(100+companyBean.vat)}" />
								</td>
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
											${countTbVat }
											<c:set var="countTbVat" value="${countTbVat + 1}" />
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
									<td align="center">${productWorkSheetItem.quantity }
										${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.deposit * 100)/(100+companyBean.vat)}" />
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.deposit * 100)/(100+companyBean.vat)}" />
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.deposit * 100)/(100+companyBean.vat)}" />
										<c:set var="totalListVat"
											value="${totalListVat + (productWorkSheetItem.deposit * 100)/(100+companyBean.vat)}" />
									</td>
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
								${countTbVat }
								<c:set var="countTbVat" value="${countTbVat + 1}" />
							</center>
						</td>
						<td><center>ใบงานย่อย</center></td>
						<td>${subworksheet.workSheetTypeText }</td>
						<td style="text-align: left;"><center>1 งาน</center></td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(subworksheet.price * 100)/(100+companyBean.vat)}" />
						</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(subworksheet.price * 100)/(100+companyBean.vat)}" />
						</td>
						<td align="right"><fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(subworksheet.price * 100)/(100+companyBean.vat)}" />
							<c:set var="totalListVat"
								value="${totalListVat + (subworksheet.price * 100)/(100+companyBean.vat)}" />
						</td>
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
											${countTbVat }
											<c:set var="countTbVat" value="${countTbVat + 1}" />
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
									<td align="center">${productWorkSheetItem.quantity }
										${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.price * 100)/(100+companyBean.vat)}" />
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.amount * 100)/(100+companyBean.vat)}" />
									</td>
									<td align="right"><fmt:formatNumber type="number"
											maxFractionDigits="2"
											value="${(productWorkSheetItem.amount * 100)/(100+companyBean.vat)}" />
										<c:set var="totalListVat"
											value="${totalListVat + (productWorkSheetItem.amount * 100)/(100+companyBean.vat)}" />
									</td>
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
					<td align="right"><b>ยอดค่าบริการ</b></td>
					<td align="right">
							<fmt:formatNumber type="number"
							maxFractionDigits="2" value="${totalListVat}" />
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
						<td align="right" style=" background-color: rgba(244, 67, 54, 0.12);"><b>ส่วนลด</b></td>
						<td align="right" style=" background-color: rgba(244, 67, 54, 0.12); color: red">
						<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${(invoiceBean.serviceApplication.firstBillFreeDisCount * 100)/(100+companyBean.vat)}" /></td>
					</tr>
				</c:if>
				<tr>
					<td align="right"><b>ภาษีมูลค่าเพิ่ม ${companyBean.vat }%</b></td>
					<td align="right">
						<c:choose>
							<c:when test="${invoiceBean.worksheet.workSheetType eq 'C_S'}">
								<c:set var="amountTemp" value="${totalListVat - (invoiceBean.serviceApplication.firstBillFreeDisCount * 100)/(100+companyBean.vat)}" />
								<fmt:formatNumber type="number"
								maxFractionDigits="2" value="${amountTemp * (companyBean.vat / 100)}" />
							</c:when>
							<c:otherwise>
								<fmt:formatNumber type="number"
								maxFractionDigits="2"
								value="${totalListVat * (companyBean.vat / 100)}" />
							</c:otherwise>
						</c:choose> 
					</td>
				</tr>
				<tr>
					<td colspan="5"
						style="border-left: 1px solid #fff; border-bottom: 1px solid #fff;"></td>
					<td align="right" style="background-color: #009688;color: #fff;"><h3>ยอดรวมสุทธิ</h3></td>
					<td align="right" style="background-color: #009688;color: #fff;"><h3><fmt:formatNumber type="number" maxFractionDigits="2" value="${invoiceBean.amount }" /></h3></td>
				</tr>
			</tfoot>
		</c:otherwise>
 
	</c:choose>
</table>
</div>