<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<br />
<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;อุปกรณ์ที่ใช้เพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
			<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' }">
			<div class="row">
					<div class="col-md-10" align="left">หากต้องการเพิ่มค่าอุปกรณ์ที่ใช้งานเพิ่มเติม กรุณาคลิกที่ปุ่ม <b>"เลือกสินค้า"</b></div>
					<div class="col-md-2" align="right">
						<button type="button" onclick="openModalSearchEquipment();" class="btn-block btn btn-info label-left float-xs-right mr-0-5 btn-xs">
							<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
						</button>
					</div>
			</div>
			</c:if>
			<div class="row mt15">
				<div class="col-md-12">
				<div class="table-responsive">
					<table id="tableEquipmentProduct" class="table table-bordered mb-0 table-hover">
						<thead class="thead-bg">
							<tr>
								<th style="vertical-align: middle; width: 20px;">ลำดับ</th>
								<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
								<th style="vertical-align: middle;"><center>S/N</center></th>
								<th style="vertical-align: middle; width: 150px;"><center>จำนวนที่เบิก</center>
								</th>
								<th style="vertical-align: middle;"><center>ฟรี</center></th>
								<th style="vertical-align: middle;"><center>ยืม</center></th>
								<th style="vertical-align: middle; width: 150px;"><center>ราคาขาย</center></th>
								<th style="vertical-align: middle; width: 150px;"><center>ราคารวม</center></th>
							<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' }">
								<th style="vertical-align: middle; width: 30px;"></th>
							</c:if>
							</tr>
						</thead>
						<tbody>
							<c:set var="countNo" value="1" scope="page" />
							<c:forEach var="productItemOld" items="${worksheetBean.productItemList}" varStatus="i">
							<c:if test="${productItemOld.productTypeMatch ne 'O' && productItemOld.productTypeMatch ne 'R' && productItemOld.productTypeMatch ne 'A' && productItemOld.productTypeMatch ne 'B'}">
								<c:choose>
									<c:when test="${productItemOld.type eq 'E'}">
																 			<c:forEach var="productWorkSheetItem" items="${productItemOld.productItemWorksheetBeanList}" varStatus="j">
																			<tr class="trNewItem">
																				<td>
																					<center>
																						<span class="no-item-setup"><c:out value="${countNo}" /></span>
																						<c:set var="countNo" value="${countNo + 1}" scope="page" />
																						<input type="hidden" value="${productItemOld.type }" />
																					</center>
																				</td>
																				<td>
																					${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productName }
																					<br><font style="color: gray;"><a href="${pageContext.request.contextPath}/productorderequipmentproduct/detail/${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.id }" target="_blank">${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.productCode }</a></font>
																					<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																					<br><small class="text-gray"><b><span class="ti-file"></span>&nbsp;เบิกจากใบเบิก&nbsp;<a target="_blank" href="${pageContext.request.contextPath}/requisitionlist/view/${productWorkSheetItem.requisitionItemBean.requisitionDocumentBean.id}">#${productWorkSheetItem.requisitionItemBean.requisitionDocumentBean.requisitionDocumentCode}</a><br><span class="ti-user"></span>&nbsp;${productWorkSheetItem.requisitionItemBean.personnelBean.firstName}&nbsp;${productWorkSheetItem.requisitionItemBean.personnelBean.lastName}&nbsp;(ผู้เบิก)</b></small>
																					</c:if>
																				</td>
																				<td>
																					 <center>
																						<a href="${pageContext.request.contextPath}/productorderequipmentproduct/item/detail/${productWorkSheetItem.equipmentProductItemBean.id }" target="_blank">${productWorkSheetItem.equipmentProductItemBean.serialNo }</a>
																					 </center>
																				</td>
																				<c:set var="quantity" value="${productWorkSheetItem.quantity }" scope="page" />
																				<c:if test="${not empty productWorkSheetItem.equipmentProductItemBean.serialNo}">
																					<c:set var="quantity" value="1" scope="page" />
																				</c:if>
																				
																				<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																					<c:set var="quantityMax" value="${productWorkSheetItem.requisitionItemBean.quantity}" scope="page" />
																				</c:if>
																				<c:if test="${empty productWorkSheetItem.requisitionItemBean}">
																					<c:set var="quantityMax" value="${productWorkSheetItem.equipmentProductItemBean.balance + productWorkSheetItem.quantity}" scope="page" />
																				</c:if>
																				
																				<td style="vertical-align: middle; width: 80px;">
																					<div class="input-group">
																					<input type="number" value="${quantity}" min="1" max="${quantityMax}" class="form-control worksheetViewDisableDom numberReqNotSN quantity" <c:if test="${not empty productWorkSheetItem.equipmentProductItemBean.serialNo}">disabled</c:if> />
																					<div class="input-group-addon">${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</div>
																					 </div>
																					<c:if test="${not empty productWorkSheetItem.requisitionItemBean}">
																						<small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;${quantityMax}&nbsp;${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</b></small>
																					</c:if>
																					<c:if test="${empty productWorkSheetItem.requisitionItemBean}">
																						<small class="text-gray"><span class="ti-alert"></span>&nbsp;<b>เบิกได้สูงสุด&nbsp;${quantityMax}&nbsp;${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.unit.unitName }</b></small>
																					</c:if>
																					 
																					 
																				</td>
																				<td><center>
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="${productWorkSheetItem.price }"
																						<c:if test="${productWorkSheetItem.free }">checked</c:if> >
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td><center <c:if test="${empty productWorkSheetItem.equipmentProductItemBean.serialNo}"> style="display: none;" </c:if>>
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend"
																						<c:if test="${productWorkSheetItem.lend }">checked</c:if> <c:if test="${empty productWorkSheetItem.equipmentProductItemBean.serialNo}">disabled</c:if> >
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																				</td>
																				<td>
																					<input type="number" value="${productWorkSheetItem.price }" class="form-control worksheetViewDisableDom salePrice"/>
																				</td>
																				<td>
																					<div class="input-group">
																					<input type="number" value="${quantity * productWorkSheetItem.price}" disabled class="form-control worksheetViewDisableDom totalPrice"/>
																					
																					 </div>
																				</td>
																				<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' }">
																					<td>
																						
																							<a class="run-swal cursor-p removeProductItem">
																							<span class="ti-trash" data-toggle="tooltip" 
																							data-placement="bottom" title="" 
																							aria-describedby="tooltip128894"></span></a>
																							<input type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.id }" />
																							<input type="hidden" value="${productWorkSheetItem.equipmentProductItemBean.equipmentProductBean.id }" />
																							<input type="hidden" class="requesterId" value="${productWorkSheetItem.requisitionItemBean.id}">
																					</td>
																				</c:if>
																			</tr>
																		</c:forEach>
																 		</c:when>
									<c:when test="${productItemOld.type eq 'S'}">
																 			<tr class="trNewItem">
																			<td>
																				<center>
																					<span class="no-item-setup"><c:out value="${countNo}" /></span>
																					<c:set var="countNo" value="${countNo + 1}" scope="page" />
																					<input type="hidden" value="${productItemOld.type }" />
																				</center>
																			</td>
																			<td>
																				${productItemOld.serviceProductBean.productName }
																			</td>
																			<td>
																				 <center>
																					
																				 </center>
																			</td>
																			<td style="vertical-align: middle; width: 80px;">
																				<div class="input-group">
																				<input type="number" value="${productItemOld.quantity }" class="form-control worksheetViewDisableDom numberReqNotSN quantity" />
																				<div class="input-group-addon">${productItemOld.serviceProductBean.unit.unitName }</div>
																				 </div>
																			</td>
																			<td><center>
																				<label class="custom-control custom-checkbox">
																					<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-free" value="${productItemOld.price }"
																					<c:if test="${productItemOld.free }">checked</c:if> >
																					<span class="custom-control-indicator"></span>
																					<span class="custom-control-description"></span>
																				</label>
																				</center>
																			</td>
																			<td><center>
																					<label class="custom-control custom-checkbox">
																						<input type="checkbox" class="custom-control-input worksheetViewDisableDom check-box-lend" 
																							disabled>
																						<span class="custom-control-indicator"></span>
																						<span class="custom-control-description"></span>
																					</label>
																					</center>
																			</td>
																			<td>
																				<input type="number" value="${productItemOld.price }" class="form-control worksheetViewDisableDom salePrice" />
																			</td>
																			<td>
																					<div class="input-group">
																					<input type="number" value="${productItemOld.quantity * productItemOld.price}" disabled class="form-control worksheetViewDisableDom totalPrice"/>
																					 </div>
																				</td>
																			<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' }">
																			<td>
																				 
																					<a class="run-swal cursor-p removeProductItem">
																					<span class="ti-trash" data-toggle="tooltip" 
																					data-placement="bottom" title="" 
																					aria-describedby="tooltip128894"></span></a>
																					<input type="hidden" value="0" />
																					<input type="hidden" value="${productItemOld.serviceProductBean.id  }" />
																				 
																			</td>
																			</c:if>
																		</tr>
																 		</c:when>
										<c:when test="${productItemOld.type eq 'I'}">
												<tr class="trNewItem">
													<td>
														<center>
															<span class="no-item-setup"><c:out
																	value="${countNo}" /></span>
															<c:set var="countNo" value="${countNo + 1}" scope="page" />
															<input type="hidden" value="${productItemOld.type }" />
														</center>
													</td>
													<td>
													${productItemOld.productItemWorksheetBeanList[0].internetProductBeanItem.internetProductBean.productName }
													<label for='exampleInputEmail1'><i class='fa fa-exclamation-triangle' style='color: orange;'></i>&nbsp;
													<b style='color: orange;'>( 
													 UserName : ${productItemOld.productItemWorksheetBeanList[0].internetProductBeanItem.userName}
													 Password : ${productItemOld.productItemWorksheetBeanList[0].internetProductBeanItem.password} )</b></label>
													</td>
													<td>
														
													</td>
													<td>
														<input type='hidden' value='' />
													</td>
													<td>
														<input type='hidden' value='' />
													</td>
													<td>
														<input type='hidden' value='' />
													</td>
													<td>
														<input type='hidden' value='' />
													</td>
													<td>
														<input type='hidden' value='' />
													</td>
													<c:if test="${worksheetBean.status.stringValue ne 'S' && worksheetBean.status.stringValue ne 'D' }">
														<td><a class="run-swal cursor-p removeProductItem">
																<span class="ti-trash" data-toggle="tooltip"
																data-placement="bottom" title=""
																aria-describedby="tooltip128894"></span>
														</a> 
														<input type="hidden" value="${productItemOld.productItemWorksheetBeanList[0].internetProductBeanItem.id}" /> 
														<input type="hidden" value="${productItemOld.productItemWorksheetBeanList[0].internetProductBeanItem.internetProductBean.id}" /></td>
													</c:if>
												</tr>
											</c:when>
								</c:choose>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
			</div>
		</fieldset>
	</div>
</div>