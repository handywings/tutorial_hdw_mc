<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

					<div class="tab-pane card mb30" id="block_data_cancel" role="tabpanel"
						aria-expanded="false">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-7">
									<h4>รายการใบเบิกสถานะยกเลิก</h4>
								</div>
<!-- 								<div class="col-md-5"> -->
<!-- 									<span class="text-grey pull-right"> <i class="ion-record text-green"></i> ปกติ | <i class="ion-record text-red"></i> ยกเลิก -->
<!-- 									</span> -->
<!-- 								</div> -->
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th style="vertical-align: middle;">เลขที่ใบเบิก</th>
														<th style="vertical-align: middle;">วันที่ขอเบิก</th>
														<th style="vertical-align: middle;">เบิกเพื่อ</th>
														<th style="vertical-align: middle;">ผู้ขอเบิก</th>
														<th style="vertical-align: middle;">ผู้อนุมัติเบิก</th>
														<th style="vertical-align: middle;">เหตุผล</th>
														<th></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="requisitionDocumentBean" items="${paginationCancel.dataListBean}" varStatus="i">
														<tr role="row">						
															<td>${requisitionDocumentBean.requisitionDocumentCode }</td>
															<td>${requisitionDocumentBean.createDateTh }</td>
															<td>${requisitionDocumentBean.withdraw }</td>
															<td>
																<c:choose>
																	<c:when test="${requisitionDocumentBean.technicianGroup.personnel != null }">
																		<center>${requisitionDocumentBean.technicianGroup.personnel.firstName} 
																		${requisitionDocumentBean.technicianGroup.personnel.lastName}</center>
																	</c:when>
																	<c:otherwise>
																		<center>${requisitionDocumentBean.personnelBean.firstName} 
																		${requisitionDocumentBean.personnelBean.lastName}
																	</c:otherwise>
																</c:choose>															
															</td>
															<td>${requisitionDocumentBean.personnelBean.firstName} 
															${requisitionDocumentBean.personnelBean.lastName}
															</td>
															<td>
															${requisitionDocumentBean.remarkStatus}
															</td>
															<td align="center">
																<a href="${pageContext.request.contextPath}/requisitionlist/view/${requisitionDocumentBean.id}">
																	<span class="ti-search" data-toggle="tooltip" 
																	data-placement="bottom" title="" data-original-title="ดูรายละเอียด">
																	</span>
																</a>															
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
								<c:if test="${pagination.dataListBean.size() > 0}"></c:if>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
							</div>
						</div>
					</div>
	
</body>
</html>
