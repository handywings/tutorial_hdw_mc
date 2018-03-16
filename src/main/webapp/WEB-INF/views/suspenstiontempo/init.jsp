<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="suspenstiontempo" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลลูกค้าค้างชำระ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
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
							<h3>ข้อมูลลูกค้าค้างชำระ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">ข้อมูลลูกค้าค้างชำระ</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp;
								ค้นหาใบสมัครใช้บริการที่เกินวันนัดชำระเงิน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="applicationSearchBean"
										action="${pageContext.request.contextPath}/suspenstiontempo/search">
										<div class="col-md-10 mb05">
											<input type="text" name="key" class="form-control"
												value="${applicationSearchBean.key }" path="key"
												placeholder="ระบุจำนวนงวดที่เกินวันนัดชำระเงิน">
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram col-xs-12 b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;
								รายการใบสมัครที่ค้างชำระเงินเกินกำหนด
							</h4>
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
														<th class="td-middle" width="10%"><center>เลขที่ใบสมัคร</center></th>
														<th class="td-middle" width="20%">ข้อมูลลูกค้า</th>
														<th class="td-middle" width="25%">ข้อมูลบริการที่สมัคร</th>
														<th class="td-middle" width="30%">เขตชุมชน</th>
														<th class="td-middle" width="10%"><center>ค้างจ่าย</center></th>
														<th class="td-middle" width="5%"></th>
													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
															<c:forEach var="serviceApplicationBean"
																items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td>
																		<center>
																			<a class="cursor-p" target="_blank"
																				href="${pageContext.request.contextPath}/serviceapplicationlist/worksheetadd/${serviceApplicationBean.id}"
																				data-toggle="tooltip" data-placement="bottom"
																				title="ดูรายละเอียด"><b>${serviceApplicationBean.serviceApplicationNo}</b></a>
																		</center>
																	</td>
																	<td>
																		${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;
																		${serviceApplicationBean.customer.lastName}<br> <small
																		style="color: gray;"> <b>รหัสลูกค้า&nbsp;:&nbsp;</b><a
																			href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }"
																			target="_blank" data-toggle="tooltip"
																			data-placement="bottom" title="ดูรายละเอียด">${serviceApplicationBean.customer.custCode }</a><br>
																			<b>Email&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.email}<br>
																			<b>โทร&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.mobile}
																	</small>
																	</td>
																	<td>${serviceApplicationBean.servicepackage.packageName}<br>
																		<small class="text-gray">รหัส Package :
																			${serviceApplicationBean.servicepackage.packageCode}
																			| ประเภทบริการ :
																			${serviceApplicationBean.servicepackage.serviceType.packageTypeName}</small></td>
																	<td><c:forEach var="addressBean"
																			items="${serviceApplicationBean.addressList}"
																			varStatus="j">
																			<c:if test="${addressBean.addressType == 3}">																			
																			${addressBean.collectAddressDetail } <br>
																				<b>(${addressBean.zoneBean.zoneDetail })</b>
																			</c:if>
																		</c:forEach> <br> <small style="color: gray;">${serviceApplicationBean.customer.customerFeatureBean.customerFeatureName}</small>
																	</td>
																	<td><center>
																			<b><a
																				href="${pageContext.request.contextPath}/invoice/search/${serviceApplicationBean.serviceApplicationNo}"
																				target="_blank">${serviceApplicationBean.overpay}
																					รอบบิล</a></b>
																		</center></td>
																	<td align="center" class="td-middle"><a
																		class="cursor-p"
																		href="${pageContext.request.contextPath}/changeservice/view/${serviceApplicationBean.id}">
																			<span class="ti-bolt" data-toggle="tooltip"
																			data-placement="bottom" title="แจ้งยกเลิกใช้บริการ">
																		</span>
																	</a> <%-- 																<a class="cursor-p" onclick="confirm(${serviceApplicationBean.id});" > --%>
																		<!-- 																	<span class="ti-bolt" data-toggle="tooltip" -->
																		<!-- 																			data-placement="bottom" title="ระงับสายสัญญาณชั่วคราว"> -->
																		<!-- 																	</span> --> <!-- 																</a>  -->
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="6"><center>ไม่พบข้อมูล</center></td>
															</tr>
														</c:otherwise>
													</c:choose>

												</tbody>
											</table>
										</div>

									</div>
								</div>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
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
		function confirm(serviceApplicationId) {
			swal(
					{
						//title : 'คุณต้องการมอบหมายงานที่เลือกให้ '+name,
						title : 'ยืนยันการระงับสายสัญญาณชั่วคราว',
						text : "ถ้าคุณต้องการระงับสายสัญญาณชั่วคราว กรุณากด \"ยืนยัน\" ด้านล่าง",
						type : 'warning',
						showCancelButton : true,
						confirmButtonColor : '#3085d6',
						cancelButtonColor : '#d33',
						confirmButtonText : 'ยืนยัน',
						confirmButtonClass : 'btn btn-primary btn-lg mr-1',
						cancelButtonClass : 'btn btn-danger btn-lg',
						cancelButtonText : 'ยกเลิก',
						buttonsStyling : false
					}).then(function(isConfirm) {
				if (isConfirm) {
					updateServiceApplication(serviceApplicationId);
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

		function updateServiceApplication(serviceApplicationId) {
			var serviceApplicationBean = {};
			serviceApplicationBean.id = serviceApplicationId;
			$('.preloader').modal('show');
			$
					.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/suspenstiontempo/updateServiceApplication",
						dataType : 'json',
						data : JSON.stringify(serviceApplicationBean),
						async : false,
						//timeout : 100000,
						success : function(data) {
							if (data["error"] == false) {
								window.location.href = "${pageContext.request.contextPath}/suspenstiontempo";
							} else {

							}
						},
						error : function(e) {
							console.log("ERROR: ", e);
							var n = e.responseText
									.indexOf("MaxUploadSizeExceededException");
							if (n > 0) {
								$('.preloader').modal('hide');
								openAlert('warning',
										'ไฟล์ที่อัพโหลดมีขนาดใหญ่เกินที่ระบบกำหนดไว้');
							}
						},
						done : function(e) {
							console.log("DONE");
						}
					});
		}
	</script>
</body>
</html>
