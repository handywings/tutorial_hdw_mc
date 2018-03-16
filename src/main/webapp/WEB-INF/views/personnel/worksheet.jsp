<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="mainMenu" value="personnel" scope="request"/>
<c:set var="subMenu" value="personnel" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายละเอียดพนักงาน</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style type="text/css">
.form-group {
	margin-bottom: 0rem !important;
}

.list-group-item {
	border: 0px !important;
	border-top: 1px solid #ddd !important;
}
</style>
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
					<div class="row mt05 mb05" >
						<div class="col-md-12">
							<h3>รายละเอียดพนักงาน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item "><a
									href="${pageContext.request.contextPath}/personnel">รายละเอียดพนักงาน</a></li>
								<li class="breadcrumb-item active">รายละเอียดพนักงาน</li>
							</ol>
						</div>
					</div>
				</div>
				<div class="container-fluid mb30 mt05">
					<div class="row">
						<div class="col-md-3">
							<div class="card modal static-modal custom-modal-1">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="cm-img img-cover"
											style="background-image: url(../../../resources/assets/img/5.jpg);">
											<div class="gradient gradient-success"></div>
											<div class="cm-content">
												<div class="cm-title">${personnelBean.prefix }${personnelBean.firstName } ${personnelBean.lastName }</div>
												<b>รหัสพนักงาน ${personnelBean.personnelCode }</b><br>
												( ${personnelBean.nickName } )
											</div>
										</div>
										<div class="modal-body" style="color: gray;">
											<b>ตำแหน่งงาน</b>
												: ${personnelBean.position.positionName }<br> <b>สังกัดบริษัท</b>
												: ${personnelBean.company.companyName }<br> <b>สิทธิ์การใช้งาน</b>
												: ${personnelBean.permissionGroup.permissionGroupName }<br><br>
											<ul class="list-group">
												<p class="list-group-item">
													<i class="ti-mobile mr-0-5"></i>
													${personnelBean.contact.mobile }
												</p>
												<p class="list-group-item">
													<i class="ti-email mr-0-5"></i> ${personnelBean.contact.email }
												</p>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-9">
							<div class="card">
								<div class="card-header text-uppercase">
									<h4>
										<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาช่วงเวลาในการทำงาน
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<form:form id="searchCountWorksheet" method="post" commandName="seachCountWorksheetRequest" action="${pageContext.request.contextPath}/personnel/searchWorksheet">
										<div class="row">
											<div class="col-md-10 mb05">
												<input type="text" class="form-control daterange"
													id="daterange" name="dateRange" value="${seachCountWorksheetRequest.dateRange }">
												<input type="hidden" id="personnelId" name="personnelId" value="${personnelBean.id }" />
											</div>
											<div class="col-md-2 mb05">
												<center>
													<button type="submit" class="btn btn-lg btn-info label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><span class="ti-search"></span></span>
														ค้นหา
													</button>
												</center>
											</div>
										</div>
										</form:form>
									</div>
								</div>
							</div>
							<div class="card">
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<div class="row">
											<div class="col-md-12">
												<ul class="nav nav-tabs nav-tabs-2">
													<li class="nav-item"><a class="nav-link"
														href="${pageContext.request.contextPath}/personnel/view/${personnelBean.id }"><b>ภาพรวมการทำงาน</b></a></li>
													<li class="nav-item"><a class="nav-link active"
														href="${pageContext.request.contextPath}/personnel/view/${personnelBean.id }/worksheet"><b>ประวัติการทำงาน</b></a></li>
												</ul>
											</div>
										</div>
										<div class="row mb05 mt05">
											<div class="col-md-12 mb05" align="right">
												<img
													src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png"
													style="width: 14px;"> อยู่ระหว่างดำเนินงาน | <img
													src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png"
													style="width: 14px;"> งานคงค้าง | <img
													src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png"
													style="width: 14px;"> เสร็จสมบูรณ์ | <img
													src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png"
													style="width: 14px;"> งานยกเลิก
											</div>
											<div class="col-md-12 mb05">											
												<div class="table-responsive">
													<table class="table table-bordered table-hover" id="table-1">
														<thead class="thead-default">
															<tr>
																<th style="vertical-align: middle; width: 70px;"><center>เลขที่ใบงาน</center></th>
																<th style="vertical-align: middle;"><center>ข้อมูลลูกค้า</center></th>
																<th style="vertical-align: middle;"><center>ประเภทใบงาน</center></th>
																<th style="vertical-align: middle;"><center>เขตชุมชน
																	</center></th>
																<th style="vertical-align: middle; width: 40px;"><center>สถานะ</center></th>
															</tr>
														</thead>
														<tbody>

															<c:choose>
																<c:when test="${worksheetBeanList.size() > 0}">
																	<c:forEach var="workSheetBean" items="${worksheetBeanList}" varStatus="i">
																		<tr>
																			<td class="td-middle">
																				<center><a
																				href="${pageContext.request.contextPath}/worksheetlist/detail/${workSheetBean.idWorksheetParent}"><b>${workSheetBean.workSheetCode }</b></a></center>
																			</td>
																			<td class="td-middle">
																				${workSheetBean.serviceApplication.customer.firstName }&nbsp;
																				${workSheetBean.serviceApplication.customer.lastName }<br>
																				<font class="text-gray">รหัสลูกค้า  <a href="${pageContext.request.contextPath}/customerregistration/view/${workSheetBean.serviceApplication.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${workSheetBean.serviceApplication.customer.custCode }</a></font>
																			</td>
																			<td class="td-middle">
																				<center>${workSheetBean.workSheetTypeText }
																				</center>
																			</td>
																			<td class="td-middle"><c:forEach
																					var="addressBean"
																					items="${workSheetBean.serviceApplication.addressList}"
																					varStatus="j">
																					<c:if test="${addressBean.addressType == 3}">
																						<center>${addressBean.zoneBean.zoneDetail}&nbsp;<b>( ${addressBean.zoneBean.zoneName } )</b></center>
																					</c:if>
																				</c:forEach></td>
																			<td class="td-middle"><center>
																					<c:choose>
																						<c:when
																							test="${workSheetBean.status.stringValue eq 'W'}">
																							<img
																								src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png"
																								style="width: 20px;" data-toggle="tooltip"
																								data-placement="bottom" title="ใบงานรอมอบหมาย">
																						</c:when>
																						<c:when
																							test="${workSheetBean.status.stringValue eq 'R'}">
																							<img
																								src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png"
																								style="width: 20px;" data-toggle="tooltip"
																								data-placement="bottom"
																								title="ใบงานอยู่ระหว่างดำเนินงาน">
																						</c:when>
																						<c:when
																							test="${workSheetBean.status.stringValue eq 'H'}">
																							<img
																								src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png"
																								style="width: 20px;" data-toggle="tooltip"
																								data-placement="bottom" title="ใบงานคงค้าง">
																						</c:when>
																						<c:when
																							test="${workSheetBean.status.stringValue eq 'D'}">
																							<img
																								src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png"
																								style="width: 20px;" data-toggle="tooltip"
																								data-placement="bottom" title="ใบงานยกเลิก">
																						</c:when>
																						<c:when
																							test="${workSheetBean.status.stringValue eq 'S'}">
																							<img
																								src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png"
																								style="width: 20px;" data-toggle="tooltip"
																								data-placement="bottom"
																								title="ใบงานที่เสร็จเรียบร้อย">
																						</c:when>
																					</c:choose>
																				</center>					
																			</td>
																		</tr>
																	</c:forEach>
																</c:when>
																<c:otherwise>
																	<tr>
																		<td colspan="7"><center>ไม่พบรายการใบงาน</center></td>
																	</tr>
																</c:otherwise>
															</c:choose>


														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Footer -->
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
		$('input[name=empIDType]').change(function() {
			if (this.value == 'custom') {
				$('.empid').show();
			} else {
				$('.empid').hide();
			}
		});

		//generate session search personnel
		function generatePersonnelSearchSession() {
			var personnelSearchBean = {};
			personnelSearchBean.key = $('#tempKey').val();
			personnelSearchBean.position = $('#tempSearchPositionSelect').val();
			personnelSearchBean.permissionGroup = $(
					'#tempSearchPermissionGroupSelect').val();

			//send generate session personnel search
			$
					.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/personnel/generatePersonnelSearchSession",
						dataType : 'json',
						async : false,
						data : JSON.stringify(personnelSearchBean),
						//timeout : 100000,
						success : function(data) {

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
	<script type="text/javascript">
		$('.daterange').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
		});
	</script>

</body>
</html>
