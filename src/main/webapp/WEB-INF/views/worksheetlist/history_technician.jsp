<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="dateUtils" class="com.hdw.mccable.utils.DateUtil" />

<c:if test="${fn:length(worksheetBean.historyTechnicianGroupWorkBeans) >  0}">
<c:if test="${worksheetBean.status.stringValue != 'W'}">
	<div class="row mt05">
		<div class="col-md-12">
			<fieldset>
				<legend>&nbsp;&nbsp;&nbsp;ทีมช่างผู้ดำเนินงาน&nbsp;&nbsp;&nbsp;</legend>
				<div id="job1" class="job">

					<div class="row mb05">
						<div class="col-md-12 mb15 mb15">
							<div class="table-responsive">
								<table class="table table-bordered mb-0 table-hover">
									<thead class="thead-bg">
										<tr>
											<th><center>ลำดับ</center></th>
											<th><center>กลุ่มทีมช่าง</center></th>
											<th><center>วันส่งมอบงาน</center></th>
											<th><center>สถานะ</center></th>
											<th><center>ช่างที่ปฏิบัติงาน</center></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="hisTechnicain" items="${worksheetBean.historyTechnicianGroupWorkBeans}"
											varStatus="i">
											<tr>
												<td class="td-middle"><center>${i.count}</center></td>
												<td><center>${hisTechnicain.technicainGroup.technicianGroupName }</center></td>
												<td><center>${dateUtils.getDateThaiFormat(hisTechnicain.assingCurrentDate) }</center></td>
												<td>
													<center>
														<c:choose>
															<c:when test="${hisTechnicain.statusHistory eq 'R'}">
																<b style="color: orange;">อยู่ระหว่างดำเนินการ</b>
															</c:when>
															<c:when test="${hisTechnicain.statusHistory eq 'S'}">
																<b style="color: green;">งานสำเร็จ</b>
															</c:when>
															<c:when test="${hisTechnicain.statusHistory eq 'N'}">
																<b style="color: red;">งานไม่สำเร็จ</b>
															</c:when>
															<c:when test="${hisTechnicain.statusHistory eq 'C'}">
																<b style="color: gray;">ยกเลิกใบงาน</b>
															</c:when>
														</c:choose>
													</center>
												</td>
												<td>
													<center>
														<a class="cursor-p" data-toggle="modal"
															onclick="openViewTeam(${hisTechnicain.id})"><span
															class="ti-search" data-toggle="tooltip"
															data-placement="bottom" title=""
															data-original-title="ดูสมาชิก"></span></a>
													</center>
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
</c:if>
</c:if>


					<div class="row mb05">
						<div class="col-md-12 mb15 mb15">
						<div class="table-responsive">
							<b>รายละเอียดงาน</b>
							<textarea class="form-control worksheetViewDisableDom" onblur="addJobDetails()"
								id="jobDetails" rows="3">${worksheetBean.jobDetails}</textarea>
						</div>
						</div>
					</div>
