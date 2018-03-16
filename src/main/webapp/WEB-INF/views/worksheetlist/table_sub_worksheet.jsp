<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<br />
<div class="row">
	<div class="col-md-12">
		<fieldset>
			<legend>&nbsp;&nbsp;&nbsp;ใบงานย่อย&nbsp;&nbsp;&nbsp;</legend>
			<div class="row">
				<c:if test="${statusCurrentHistory eq 'R' && worksheetBean.status.stringValue ne 'D'}">
					<div class="col-md-10">หากคุณต้องการเพิ่มใบงานย่อยในใบงานรายการนี้ คลิกที่ <b>"ค้นหาใบงานย่อย"</b> เพื่อเพิ่มรายการใบงาน</div>
					<div class="col-md-2" align="right">
						<button type="button" onclick="openModalSearchSubWorksheet();" class="btn-block btn btn-info label-left float-xs-right mr-0-5 btn-xs">
							<span class="btn-label"><i class="ti-plus"></i></span>ค้นหาใบงานย่อย
						</button>
					</div>
				</c:if>
			</div>
			<div class="row mt15 mb15">
				<div class="col-md-12">
					<div class="table-responsive">
					<table id="tableSubWorksheet" class="table table-bordered mb-0 table-hover">
						<thead class="thead-bg">
							<tr>
								<th style="vertical-align: middle; width: 20px;">ลำดับ</th>
								<th style="vertical-align: middle;">ประเภทใบงาน</th>
								<th style="vertical-align: middle;">หมายเหตุ / ข้อมูลเพิ่มเติม</th>
								<th style="vertical-align: middle;width: 50px;"><center>ราคา</center></th>
								<c:if test="${statusCurrentHistory eq 'R'}">
								<th style="vertical-align: middle; width: 30px;"></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="subWorksheetBean" items="${worksheetBean.subWorksheetBeanList}" varStatus="i">
							<tr class="trNewItemSubWorksheet">
								<td><center>
										<span class="no-item-subworksheet">${i.count }</span>
									</center>
									<input type="hidden" class="subWorksheetIdClass" value="${subWorksheetBean.workSheetType }"></td>
								<td>
									${subWorksheetBean.workSheetTypeText }							
								</td>
								<td>
									<textarea class="form-control worksheetViewDisableDom remark" rows="3">${subWorksheetBean.remark }</textarea>					
								</td>
								<td><input type="number" min="0" value="${subWorksheetBean.price }" class="worksheetViewDisableDom"></td>
								<c:if test="${statusCurrentHistory eq 'R'}">
								<td><center>
										<a class="run-swal cursor-p removeSubWorksheetItem"> <span
											class="ti-trash" data-toggle="tooltip"
											data-placement="bottom" title="" data-original-title=""></span></a>
									</center></td>
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


<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
