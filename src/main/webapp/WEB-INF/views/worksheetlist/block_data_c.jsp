<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

					<div class="tab-pane card mb40 <c:if test="${worksheetSearchBean.tab eq 'C'}">active</c:if>" id="block_data_c" role="tabpanel"
						aria-expanded="true">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-5">
									<h4><img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png" style="width: 20px;">&nbsp;รายการใบงานที่ยกเลิก</h4>
								</div>
								<div class="col-md-7" align="right">
									<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png" style="width: 20px;"> รอมอบหมายงาน | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png" style="width: 20px;"> อยู่ระหว่างดำเนินงาน | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png" style="width: 20px;"> งานคงค้าง | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png" style="width: 20px;"> เสร็จสมบูรณ์ | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png" style="width: 20px;"> งานยกเลิก 
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="paging/paging-entries.jsp"></jsp:include>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th style="vertical-align: middle;" width="15%"><center>เลขที่ใบงาน</center></th>
														<th style="vertical-align: middle;" width="20%">ข้อมูลลูกค้า</th>
														<th style="vertical-align: middle;" width="10%"><center>ประเภทใบงาน</center></th>														
														<th style="vertical-align: middle;" width="30%">สถานที่ดำเนินงาน</th>														
														<th style="vertical-align: middle;" width="10%"><center>สถานะ</center></th>
														<th style="vertical-align: middle;" align="center" width="15%"><center>ผู้รับผิดชอบ</center></th>
													</tr>
												</thead>
												<tbody>
													
														<c:choose>
														<c:when test="${pagination_c.dataListBean.size() > 0}">
															<c:forEach var="workSheetBean" items="${pagination_c.dataListBean}" varStatus="i">
														<tr>
															<td>
																<center>
																	<a href="${pageContext.request.contextPath}/worksheetlist/detail/${workSheetBean.idWorksheetParent}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${workSheetBean.workSheetCode }</b></a>
																</center>
															</td>
															<td>
																${workSheetBean.serviceApplication.customer.firstName }&nbsp;
																${workSheetBean.serviceApplication.customer.lastName }<br>
																<small class="text-gray">
																	<b>รหัสสมาชิก : </b><a href="${pageContext.request.contextPath}/customerregistration/view/${workSheetBean.serviceApplication.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${workSheetBean.serviceApplication.customer.custCode }</a><br>
																	<b>โทร:&nbsp;</b>${workSheetBean.serviceApplication.customer.contact.mobile}
																</small>
															</td>
															<td>
																<center>
																	${workSheetBean.workSheetTypeText }	 
																</center>
															</td>
															<td>
																	<c:forEach var="addressBean" items="${workSheetBean.serviceApplication.addressList}" varStatus="j">
																		<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.collectAddressDetail } <br><b>( ${addressBean.zoneBean.zoneDetail } )</b>
																		</c:if>
																	</c:forEach>
																<font style="color: gray;">&nbsp;${workSheetBean.serviceApplication.customer.customerFeatureBean.customerFeatureName}</font>
															</td>
															<td><center>
																	<c:choose>
																		<c:when test="${workSheetBean.status.stringValue eq 'W'}">
																			<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png" style="width: 20px;" data-toggle="tooltip"
																	data-placement="bottom" title="ใบงานรอมอบหมาย">
																		</c:when>
																		<c:when test="${workSheetBean.status.stringValue eq 'R'}">
																			<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png" style="width: 20px;" data-toggle="tooltip"
																	data-placement="bottom" title="ใบงานอยู่ระหว่างดำเนินงาน">
																		</c:when>
																		<c:when test="${workSheetBean.status.stringValue eq 'H'}">
																			<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png" style="width: 20px;" data-toggle="tooltip"
																	data-placement="bottom" title="ใบงานคงค้าง">
																		</c:when>
																		<c:when test="${workSheetBean.status.stringValue eq 'D'}">
																			<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png" style="width: 20px;" data-toggle="tooltip"
																	data-placement="bottom" title="ใบงานยกเลิก">
																		</c:when>
																		<c:when test="${workSheetBean.status.stringValue eq 'S'}">
																			<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png" style="width: 20px;" data-toggle="tooltip"
																	data-placement="bottom" title="ใบงานที่เสร็จเรียบร้อย">
																		</c:when>
																	</c:choose>	
																</center>
															</td>
															<td>
															<center>
																<c:forEach var="historyTechnicianGroupWork" items="${workSheetBean.historyTechnicianGroupWorkBeans}" varStatus="j">
																	<c:if test="${(fn:length(workSheetBean.historyTechnicianGroupWorkBeans)-1) == j.index}">
																	${historyTechnicianGroupWork.technicainGroup.personnel.firstName}&nbsp;
																	${historyTechnicianGroupWork.technicainGroup.personnel.lastName}
																	</c:if>
																</c:forEach>
															</center>
															</td>
														</tr>
													</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="7"><center>ไม่พบรายการใบงานที่ยกเลิก</center></td></tr>
														</c:otherwise>										</c:choose>
												
													

												</tbody>
											</table>
										</div>

									</div>
								</div>
								<c:if test="${pagination.dataListBean.size() > 0}"></c:if>
								<jsp:include page="paging/paging_c.jsp"></jsp:include>
							</div>
						</div>
					</div>
	
</body>
</html>
