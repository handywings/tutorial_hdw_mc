<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
								<li class="breadcrumb-item active">ข้อมูลพนักงาน</li>
								<li class="breadcrumb-item "><a
									href="${pageContext.request.contextPath}/personnel">ระเบียนพนักงาน</a></li>
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
											style="background-image: url(../../resources/assets/img/5.jpg);">
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
								<form:form id="searchCountWorksheet" method="post" commandName="seachCountWorksheetRequest" action="${pageContext.request.contextPath}/personnel/searchCount">
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
													<li class="nav-item"><a class="nav-link active"
														href="${pageContext.request.contextPath}/personnel/view/${personnelBean.id }"><b>ภาพรวมการทำงาน</b></a></li>
													<li class="nav-item"><a class="nav-link"
														href="${pageContext.request.contextPath}/personnel/view/${personnelBean.id }/worksheet"><b>ประวัติการทำงาน</b></a></li>
												</ul>
											</div>
										</div>
										<div class="row mt15">
											<div class="col-md-12">
												<div class="table-responsive">
													<table
														class="table table-bordered table-hover table-hover">
														<thead class="thead-bg">
															<tr>
																<th align="center" width="10%"><center>ลำดับ</center></th>
																<th align="center" width="31%">ประเภทใบงาน</th>
																<th align="center" width="20%"><center>งานได้รับมอบหมาย</center></th>
																<th align="center" width="13%"><center>งานสำเร็จ</center></th>
																<th align="center" width="13%"><center>งานไม่สำเร็จ</center></th>
																<th align="center" width="13%"><center>งานยกเลิก</center></th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<td align="center" style="vertical-align: middle;">1</td>
																<td style="vertical-align: middle;">เสริมจุดบริการ</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_AP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_AP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_AP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_AP }</td>
															</tr>
<!-- 															<tr> -->
<!-- 																<td align="center" style="vertical-align: middle;">2</td> -->
<!-- 																<td style="vertical-align: middle;">การจั้มสาย</td> -->
<%-- 																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_C }</td> --%>
<!-- 															</tr> -->
															<tr>
																<td align="center" style="vertical-align: middle;">2</td>
																<td style="vertical-align: middle;">การจูน TV</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_TTV }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_TTV }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_TTV }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_TTV }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">3</td>
																<td style="vertical-align: middle;">การซ่อมสัญญาณ</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_RC }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_RC }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_RC }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_RC }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">4</td>
																<td style="vertical-align: middle;">ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_ASTB }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_ASTB }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_ASTB }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_ASTB }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">5</td>
																<td style="vertical-align: middle;">การย้ายจุด</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_MP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_MP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_MP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_MP }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">6</td>
																<td style="vertical-align: middle;">การลดจุด</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_RP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_RP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_RP }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_RP }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">7</td>
																<td style="vertical-align: middle;">การตัดสาย</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_CU }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_CU }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_CU }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_CU }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">8</td>
																<td style="vertical-align: middle;">การย้ายสาย</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_M }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_M }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_M }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_M }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">9</td>
																<td style="vertical-align: middle;">แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_B }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_B }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_B }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_B }</td>
															</tr>
															<tr>
																<td align="center" style="vertical-align: middle;">10</td>
																<td style="vertical-align: middle;">ติดตั้งใหม่</td>
																<td align="center" style="vertical-align: middle;">${countWorksheetBean.countC_S }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(76, 175, 80, 0.13);">${countWorksheetBean.countSC_S }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(255, 152, 0, 0.16);">${countWorksheetBean.countNC_S }</td>
																<td align="center" style="vertical-align: middle;background-color: rgba(244, 67, 54, 0.11);">${countWorksheetBean.countFC_S }</td>
															</tr>
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
