<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;เจ้าหน้าที่ปฏิบัติงาน&nbsp;&nbsp;&nbsp;</legend>
			<c:if test="${statusCurrentHistory eq 'R' && worksheetBean.status.stringValue ne 'D'}">
				<div class="row">
					<div class="col-md-10">หากคุณต้องการเพิ่มเจ้าหน้าที่ในใบงานรายการนี้ คลิกที่ <b>"เพิ่มเจ้าหน้าที่"</b> เพื่อเพิ่มเจ้าหน้าที่</div>
					<div class="col-md-2" align="right">
						<button type="button" onclick="openAddTechnicianTeam(${currentTechnicainGroupId});" class="btn-block btn btn-info label-left float-xs-right mr-0-5 btn-xs">
							<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มเจ้าหน้าที่
						</button>
					</div>
				</div>
			</c:if>
			<div class="row mt15 mb15">
				<div class="col-md-12">
				<div class="table-responsive">
					<table class="table table-bordered mb-0 table-hover" id="tableViewTeamCurrent">
					<thead class="thead-bg">
						<tr>
							<th style="vertical-align: middle; width: 20px;">
								<center>ลำดับ</center>
							</th>
							<th style="vertical-align: middle;"><center>ทีม</center></th>
							<th style="vertical-align: middle;"><center>รหัสพนักงาน</center></th>
							<th style="vertical-align: middle;">ข้อมูลพนักงาน</th>
							<c:if test="${statusCurrentHistory eq 'R'}">		
							<th style="vertical-align: middle;"></th>
							</c:if>
						</tr>
					</thead>
					<tbody id="tbody-child-item-team-current">
						<c:forEach var="personnelBean" items="${personnelBeans}"
							varStatus="i">
							<tr>
								<td>
								<center><span class="no-item-member">${i.count}</span></center>
								<input type="hidden" class="personnelIdClass" value="${personnelBean.id }" />
								</td>
								<td><center><b>${personnelBean.technicianGroupBean.technicianGroupName}</b></center></td>
								<td><center><a href="${pageContext.request.contextPath}/personnel/view/${personnelBean.id }" target="_blank">${personnelBean.personnelCode }</a></center></td>
								<td>${personnelBean.firstName }&nbsp;${personnelBean.lastName }
								</td>
								<c:if test="${statusCurrentHistory eq 'R'}">
								<td>
									<center>
										<a class="run-swal cursor-p removePersonnelItem"> <span
											class="ti-trash" data-toggle="tooltip"
											data-placement="bottom" title=""  
											aria-describedby="tooltip128894"></span></a>
									</center>
								</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				</div>
			</div>
		</fieldset>
	</div>
</div>
									<c:if test="${worksheetBean.workSheetType ne 'C_S'}">
										<div class="row">
											<div class="col-md-4">
												<label for="exampleInputEmail1"><b>วันเวลาที่ลูกค้าสะดวก<span class="text-red"> *</span></b></label>
												<div class="input-group">
													<input type="text" class="form-control worksheetViewDisableDom"id="daterange1" name="daterange1">
													<div class="input-group-addon"><i class="fa fa-calendar-o" style="margin: 0;"></i></div>
												</div>
											</div>
										</div>
									</c:if>

 