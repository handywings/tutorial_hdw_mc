<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="dashboard" scope="request" />
<c:set var="subMenu" value="dashboard" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<title>ระบบบริหารงานเคเบิลทีวีและอินเตอร์เน็ต</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style type="text/css">
.box-menu {
	margin: 14px !important;
	font-size: 22px;
	top: -1px;
	position: absolute;
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
			<div class="content-area bg-white" style="padding-top: 1rem !important;background-image: url(/cable/resources/assets/img/pattern.svg),linear-gradient(to right, #c3c3c3 0, rgba(158, 158, 158, 0.26) 100%);color: white;/* background-position: center,center center; */background-repeat: no-repeat,no-repeat;">
				<div class="container-fluid">
					<div class="row mt05">
						<div class="col-md-6">
							<h3>ภาพรวมระบบ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active" style=" color: #e6e6e6; "><b>ภาพรวมระบบ / เมนูทางลัด</b></li>
							</ol>
						</div>
						<div class="col-md-6">
							<ul class="nav nav-tabs nav-tabs-2 pull-right">
								<li class="nav-item"><a class="nav-link active"
									href="${pageContext.request.contextPath}/main" style=" border-bottom: 3px solid #F44336; "><i
										class="ti-layout-grid3 text-muted mr-0-25"></i> <b>เมนูทางลัด</b></a></li>
								<perm:permission object="M1.check" >
									<li class="nav-item"><a class="nav-link"
										href="${pageContext.request.contextPath}/dashboard"><i
											class="ti-bar-chart-alt text-muted mr-0-25"></i> <b>ข้อมูลภาพรวม</b></a></li>
								</perm:permission>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="content-area py-1">
				<div class="container-fluid">

					<div class="row">
						<div class="col-md-6 col-xs-12">
							<div class="card">
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<center>
											<div class="row">
												<div class="col-md-12 mb05">
													<h3>ค้นหาข้อมูลลูกค้า</h3>
												</div>
												<div class="col-md-10 offset-md-1 mb05">
													<div class="inbox-head">
														<form:form method="post" commandName="customerSearchBean"
															action="customerregistration/search">
															<div class="input-append">
																<input type="text" name="key" class="sr-input col-md-11"
																	value="" placeholder="ข้อมูลลูกค้าต้องการค้นหา ...">
																<button class="btn sr-btn" type="submit">
																	<i class="ti-search"></i>
																</button>
															</div>
														</form:form>
													</div>
												</div>
											</div>
											<hr class="mt15">
										</center>
										<div class="row mt15 mb05">
											<div class="col-md-6 mb05">
											<perm:permission object="M23.add" >
												<a href="${pageContext.request.contextPath}/serviceapplication">
											</perm:permission>
													<div class="media">
														<div class="media-left">
															<div class="avatar box-48 b-a-radius-circle bgc-green">
																<i class="ti-user box-menu"></i>
															</div>
														</div>
														<div class="media-body">
															<h4 class="media-heading">สมัครใช้บริการใหม่</h4>
															<span class="text-muted">บันทึกข้อมูลลูกค้าสมัครใช้บริการ</span>
														</div>
													</div>
												<perm:permission object="M23.add" >
												</a>
												</perm:permission>
											</div>
											<div class="col-md-6">
											<perm:permission object="M27.check" >
												<a href="${pageContext.request.contextPath}/changeservice">
											</perm:permission>
													<div class="media">
														<div class="media-left">
															<div class="avatar box-48 b-a-radius-circle bgc-teal">
																<i class="ti-close box-menu"></i>
															</div>
														</div>
														<div class="media-body">
															<h4 class="media-heading">แจ้งยกเลิกบริการ</h4>
															<span class="text-muted">ยกเลิกการใช้บริการของลูกค้า</span>
														</div>
													</div>
												<perm:permission object="M27.check" >
												</a>
												</perm:permission>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-6 col-xs-12">
							<div class="card">
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<center>
											<div class="row">
												<div class="col-md-12 mb05">
													<h3>ค้นหารายการใบงาน</h3>
												</div>
												<div class="col-md-10 offset-md-1 mb05">
													<div class="inbox-head">
														<form:form id="wSeachWorksheet" method="post"
															commandName="worksheetSearch"
															action="${pageContext.request.contextPath}/worksheetlist/search">
															<input type="hidden" id="tab" name="tab" path="tab"
																value="All" />
															<div class="input-append">
																<input type="text" class="sr-input col-md-11" name="key"
																	value=""
																	placeholder="ระบุเลขที่ใบงาน หรือข้อมูลของลูกค้า......">
																<button class="btn sr-btn" type="submit">
																	<i class="ti-search"></i>
																</button>
															</div>
														</form:form>
													</div>
												</div>
											</div>
											<hr class="mt15">
										</center>
										<div class="row mt15 mb05">
											<div class="col-md-6 mb05 mb05">
												<div class="row">
													<div class="col-md-12">
													<perm:permission object="M29.check" >
														<a href="${pageContext.request.contextPath}/worksheetadd" charset="p-cursor">
													</perm:permission>
															<div class="media">
																<div class="media-left">
																	<div
																		class="avatar box-48 b-a-radius-circle bgc-warning">
																		<i class="ti-clipboard box-menu"></i>
																	</div>
																</div>
																<div class="media-body">
																	<h4 class="media-heading">เพิ่มใบงานใหม่</h4>
																	<span class="text-muted">สร้างใบงานใหม่ให้ช่างดำเนินการ</span>
																</div>
															</div>
														<perm:permission object="M29.check" >
														</a>
														</perm:permission>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="row">
													<div class="col-md-12">
													<perm:permission object="M30.check" >
														<a href="${pageContext.request.contextPath}/assignworksheet" charset="p-cursor">
													</perm:permission>
															<div class="media">
																<div class="media-left">
																	<div class="avatar box-48 b-a-radius-circle bgc-red">
																		<i class="ti-briefcase box-menu"></i>
																	</div>
																</div>
																<div class="media-body">
																	<h4 class="media-heading">มอบหมายงานให้ช่าง</h4>
																	<span class="text-muted">มอบหมายงานให้ช่างที่รับผิดชอบ</span>
																</div>
															</div>
														<perm:permission object="M30.check" >
														</a>
														</perm:permission>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05 mb15">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-view-list-alt"></i>&nbsp;&nbsp;เมนูที่ใช้งานบ่อย
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mt05 mb05">									
									<div class="col-md-4 mb05 mt05">
										<perm:permission object="M34.check" >
										<a href="${pageContext.request.contextPath}/invoice">
										</perm:permission>
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-receipt box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">ชำระบริการ</h4>
													<span class="text-muted">แสดงรายการใบแจ้งหนี้เพื่อชำระบริการ</span>
												</div>
											</div>
										<perm:permission object="M34.check" >
										</a>
										</perm:permission>
									</div>
									<div class="col-md-4 mb05 mt05">
									<perm:permission object="M37.check" >
										<a href="${pageContext.request.contextPath}/suspenstiontempo">
									</perm:permission>
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-notepad box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">ข้อมูลลูกค้าค้างชำระ</h4>
													<span class="text-muted">แสดงข้อมูลรายชื่อลูกค้าที่ค้างชำระ</span>
												</div>
											</div>
										<perm:permission object="M37.check" >
										</a>
										</perm:permission>
									</div>
									<div class="col-md-4 mb05 mt05">
										<perm:permission object="M38.check" >
										<a href="${pageContext.request.contextPath}/validateinvoice">
										</perm:permission>
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-search box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">ตรวจสอบใบเสร็จรับเงิน</h4>
													<span class="text-muted">ตรวจสอบบิลค้างส่ง / นำบิลออก / รับบิลเข้า</span>
												</div>
											</div>
										<perm:permission object="M38.check" >
										</a>
										</perm:permission>
									</div>
									<div class="col-md-4 mb05 mt05">
									<perm:permission object="M40.check" >
										<a href="${pageContext.request.contextPath}/printinvoice">
									</perm:permission>	
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-printer box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">พิมพ์ใบแจ้งชำระเงิน</h4>
													<span class="text-muted">พิมพ์ใบแจ้งหนี้ / ใบเสร็จรับเงินชั่วคราว</span>
												</div>
											</div>
										<perm:permission object="M40.check" >
										</a>
										</perm:permission>
									</div>
									<div class="col-md-4 mb05 mt05">
									<perm:permission object="M45.check" >
										<a href="${pageContext.request.contextPath}/worksheetreport/bydailywork">
									</perm:permission>
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-printer box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">พิมพ์ใบงานประจำวัน</h4>
													<span class="text-muted">พิพม์สรุปใบงานประจำวันของช่าง</span>
												</div>
											</div>
										<perm:permission object="M40.check" >
										</a>
										</perm:permission>
									</div>
									<div class="col-md-4 mb05 mt05">
									<perm:permission object="M45.check" >
										<a href="${pageContext.request.contextPath}/worksheetreport/bytechnician">
									</perm:permission>
											<div class="media">
												<div class="media-left">
													<div class="avatar box-48 b-a-radius-circle bgc-purple">
														<i class="ti-printer box-menu"></i>
													</div>
												</div>
												<div class="media-body">
													<h4 class="media-heading" style="color: gray;">พิมพ์ภาระงานช่าง</h4>
													<span class="text-muted">พิมพ์ใบภาระงานช่างที่รับผิดชอบ</span>
												</div>
											</div>
										<perm:permission object="M40.check" >
										</a>
										</perm:permission>
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

</body>
</html>
