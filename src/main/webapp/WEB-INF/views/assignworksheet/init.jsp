<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="worksheet" scope="request"/>
<c:set var="subMenu" value="assignworksheet" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>มอบหมายงาน</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="material-design fixed-sidebar fixed-header">

	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row" >
						<div class="col-md-12">
							<h3>มอบหมายงาน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
								<li class="breadcrumb-item active">มอบหมายงาน</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาใบงาน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="frmSeachWorksheet" method="post" commandName="assignWorksheetSearch" action="${pageContext.request.contextPath}/assignworksheet/search">
										<div class="col-md-12 mb05">
											<input type="hidden" id="orderByType" name="orderByType" path="orderByType" value="${assignWorksheetSearchBean.orderByType  }" />
											<input type="text" id="key" name="key" path="key" class="form-control" value="${assignWorksheetSearchBean.key }"
												placeholder="ระบุ เลขใบสมัคร ชื่อสกุล อีเมล์ หรือเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-4 mb05">
											<select id="jobType" name="jobType" path="jobType" class="form-control">
												<option value="" selected="selected">ประเภทใบงานทั้งหมด</option>
												<option value="C_S" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_S'}">selected="selected"</c:if> >ติดตั้งใหม่</option>
												<option value="C_AP" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_AP'}">selected="selected"</c:if>>เสริมจุดบริการ</option>
<%-- 												<option value="C_C" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_C'}">selected="selected"</c:if>>การจั้มสาย</option> --%>
												<option value="C_TTV" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_TTV'}">selected="selected"</c:if>>การจูนสัญญาณโทรทัศน์</option>
												<option value="C_RC" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_RC'}">selected="selected"</c:if>>การซ่อมสัญญาณ</option>
												<option value="C_ASTB" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_ASTB'}">selected="selected"</c:if>>ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
												<option value="C_MP" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_MP'}">selected="selected"</c:if>>การย้ายจุด</option>
												<option value="C_RP" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_RP'}">selected="selected"</c:if>>การลดจุด</option>
												<option value="C_CU" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_CU'}">selected="selected"</c:if>>การตัดสาย</option>
												<option value="C_M" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_M'}">selected="selected"</c:if>>การย้ายสาย</option>
												<option value="C_B" <c:if test="${assignWorksheetSearchBean.jobType eq 'C_B'}">selected="selected"</c:if>>แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
											</select>
										</div>
										<div class="col-md-3 mb05">
											<select id="jobStatus" name="jobStatus" path="jobStatus" class="form-control">
												<option value="" selected="selected">สถานะงานทั้งหมด</option>
												<option value="W" <c:if test="${assignWorksheetSearchBean.jobStatus eq 'W'}">selected="selected"</c:if>>ใบงานใหม่</option>
												<option value="H" <c:if test="${assignWorksheetSearchBean.jobStatus eq 'H'}">selected="selected"</c:if>>ใบงานคงค้าง</option>
											</select>
										</div>
										<div class="col-md-3 mb05">
											<select id="zone" name="zone" path="zone" class="form-control"  data-plugin="select2">
												<option value="0" selected="selected">เขตชุมชนทั้งหมด</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${assignWorksheetSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail } (${zoneBean.zoneName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
												<span class="ti-search"></span> ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>

					<div class="card mb40">
						<div class="card-header text-uppercase">
							<div class="row">
								<div class="col-md-6">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการใบงาน
									</h4>
								</div>
								<div class="col-md-2"></div>
								<div class="col-md-4">
									<select id="orderByDropdown" name="orderByDropdown" class="form-control">
										<option value="" selected="selected">--- เลือกรูปแบบการจัดเรียงข้อมูล ---</option>
										<option value="W" <c:if test="${assignWorksheetSearchBean.orderByType eq 'W'}">selected="selected"</c:if>>เลขที่ใบงาน</option>
										<option value="S" <c:if test="${assignWorksheetSearchBean.orderByType eq 'S'}">selected="selected"</c:if>>สถานะงาน</option>
										<option value="T" <c:if test="${assignWorksheetSearchBean.orderByType eq 'T'}">selected="selected"</c:if>>ประเภทงาน</option>
										<option value="Z" <c:if test="${assignWorksheetSearchBean.orderByType eq 'Z'}">selected="selected"</c:if>>เขตชุมชน / โครงการที่ติดตั้ง</option>
									</select>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item"> 
								<div class="row mb05">
									<div class="col-md-2">
										<perm:permission object="M30.add" >
											<c:if test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0  }">											
												<button aria-expanded="false" type="button" onclick="assignTaskBefore();" class="btn btn-lg btn-info label-left b-a-0 waves-effect waves-light">
													<span class="btn-label"><span class="ti-bookmark-alt"></span></span>
													<b>มอบหมายงาน</b>
												</button>
											</c:if>
										</perm:permission>	
									</div>
									<div class="col-md-10">
									<span class="text-grey pull-right">
										<i class="fa fa-history text-orange"></i> ใบงานคงค้าง&nbsp;&nbsp;&nbsp;&nbsp;
										<i class="fa fa-file-text-o text-green"></i> ใบงานใหม่
									</span>
								</div>
								</div>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th width="30px;"><center>เลือก</center></th>
														<th width="15%"><center>เลขที่ใบงาน</center></th>
														<th width="20%">ข้อมูลลูกค้า</th>
														<th width="10%"><center>ประเภทใบงาน</center></th>
														<th width="40%">สถานที่ดำเนินงาน</th>
														<th width="40%">วันเวลาที่ลูกค้าสะดวก</th>
														<th width="50px"><center>สถานะ</center></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0  }">
															<c:forEach var="workSheetBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td>
																		<center>
																			<label class="custom-control custom-checkbox">
																				<input type="checkbox" value="${workSheetBean.idWorksheetParent }" onclick="sendValueToJson(${workSheetBean.idWorksheetParent})" class="custom-control-input">
																				<span class="custom-control-indicator"></span> <span
																				class="custom-control-description"></span>
																			</label>
																		</center>
																	</td>
																	<td>
																		<center>
																			<a href="${pageContext.request.contextPath}/worksheetlist/detail/${workSheetBean.idWorksheetParent}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${workSheetBean.workSheetCode }</b></a>
																		</center>
																	</td>
																	<td>
																		${workSheetBean.serviceApplication.customer.prefix}${workSheetBean.serviceApplication.customer.firstName}&nbsp;
																		${workSheetBean.serviceApplication.customer.lastName}<br>
																		<small style="color: gray;">
																		<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${workSheetBean.serviceApplication.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${workSheetBean.serviceApplication.customer.custCode }</a><br>
																		<b>Email&nbsp;:&nbsp;</b>${workSheetBean.serviceApplication.customer.contact.email}<br>
																		<b>โทร&nbsp;:&nbsp;</b>${workSheetBean.serviceApplication.customer.contact.mobile}
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
																	<td>
																		<c:choose>
																			<c:when test="${workSheetBean.workSheetType eq 'C_S'}">
																				${workSheetBean.serviceApplication.easyInstallationDateTime }
																			</c:when>
																			<c:when test="${workSheetBean.workSheetType ne 'C_S'}">
																				${workSheetBean.availableDateTime}
																			</c:when>
																		</c:choose>
																	</td>
																	<td><center>
																			<c:choose>
																				<c:when test="${workSheetBean.status.stringValue eq 'W'}">
																					<i class="fa fa-file-text-o text-green"></i>
																				</c:when>
																				<c:when test="${workSheetBean.status.stringValue eq 'H'}">
																					<i class="fa fa-history text-orange"></i>
																				</c:when>
																			</c:choose>
																		</center></td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="7"><center>ไม่พบข้อมูลใบงานที่รอการมอบหมาย</center></td>
															</tr>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>

									</div>
								</div> 
								<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
								<jsp:include page="../layout/paging.jsp"></jsp:include> 
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Footer -->
		<jsp:include page="modal_display_technicain.jsp"></jsp:include>
		<jsp:include page="../layout/footer.jsp"></jsp:include>
	</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>
	<script type="text/javascript">
	jsonChecked = [];
	
	$( document ).ready(function() {
	    $('#orderByDropdown').change(function() {
		    $('#orderByType').val($(this).val());
		    $('#frmSeachWorksheet').submit();
		});
	});
	
	function assignTask(id,name) {
		swal(
				{
					//title : 'คุณต้องการมอบหมายงานที่เลือกให้ '+name,
					title : 'ยืนยันการมอบหมายงาน',
					text : "ถ้าคุณต้องกามอบหมายงานให้ทีมช่างที่คุณเลือก กรุณากด \"ยืนยัน\" ด้านล่าง",
					type : 'warning',
					showCancelButton : true,
					confirmButtonColor : '#3085d6',
					cancelButtonColor : '#d33',
					confirmButtonText : 'ยืนยัน',
					confirmButtonClass : 'btn btn-primary btn-lg mr-1',
					cancelButtonClass : 'btn btn-danger btn-lg',
					cancelButtonText : 'ยกเลิก',
					buttonsStyling : false
				})
				.then(
						function(isConfirm) {
							if (isConfirm) {
								saveAssignTask(id);
// 								swal({
// 									title : 'มอบหมายงานเสร็จสมบูรณ์!',
// 									text : 'ระบบทำการมมอบหมายงานให้ทีมช่างเรียบร้อยแล้ว',
// 									type : 'success',
// 									confirmButtonClass : 'btn btn-primary btn-lg',
// 									buttonsStyling : false
// 								});
							}
						})
	
	}
	
	function sendValueToJson(idWorksheet){
		var flagHas = false;
		if(jsonChecked.length > 0){
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] == idWorksheet){
					flagHas = true;
				}
			}
		}else{
			var flagHas = false;
		}
		if(flagHas){
			newJsonChecked = [];
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] != idWorksheet){
					newJsonChecked.push(jsonChecked[i]);
				}
			}
			//update json
			jsonChecked = newJsonChecked;
		}else{
			jsonChecked.push(idWorksheet);
		}
	}
	
	function saveAssignTask(technicianGroupId){
		$('.preloader').modal('show');
		var jsonRequestAssignWorksheets = [];
		for(var i=0;i<jsonChecked.length;i++){
			var worksheetMap = {};
			worksheetMap.technicianGroupId = technicianGroupId;
			worksheetMap.worksheetId = jsonChecked[i];
			worksheetMap.assignDate = $('#dateTableOrderTechnicain').val();
			jsonRequestAssignWorksheets.push(worksheetMap);
		}
		
		//send assign worksheet
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/assignworksheet/saveAssignWorksheet",
			dataType : 'json',
			async: false,
			data : JSON.stringify(jsonRequestAssignWorksheets),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					window.location.reload();
					//$('#frmSeachWorksheet').submit();
				}else{
					
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
	</script>
</body>
</html>
