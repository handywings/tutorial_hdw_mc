<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="worksheet" scope="request"/>
<c:set var="subMenu" value="worksheetlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายการใบงานทั้งหมด</title>
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
							<h3>รายการใบงานทั้งหมด</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
								<li class="breadcrumb-item active">รายการใบงานทั้งหมด</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบงาน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="wSeachWorksheet" method="post" commandName="worksheetSearch" action="${pageContext.request.contextPath}/worksheetlist/search">
										<div class="col-md-12 mb05">
											<input type="hidden" id="tab" name="tab" path="tab" value="${worksheetSearchBean.tab  }" />
											<input type="text" id="key" name="key" path="key" class="form-control" value="${worksheetSearchBean.key }"
												placeholder="ระบุเลขที่ใบงาน ชื่อสกุล เลขบัตรประชาชน อีเมล์ หรือหมายเลขโทรศัพท์ ของลูกค้า......">
										</div>

										<div class="col-md-5 mb05">
											<select id="workSheetType" name="workSheetType" path="workSheetType" class="form-control">
												<option value="" selected="selected">--- แสดงประเภทใบงานทั้งหมด ---</option>
												<option value="C_S" <c:if test="${worksheetSearchBean.workSheetType eq 'C_S'}">selected="selected"</c:if> >ติดตั้งใหม่</option>
												<option value="C_AP" <c:if test="${worksheetSearchBean.workSheetType eq 'C_AP'}">selected="selected"</c:if>>เสริมจุดบริการ</option>
<%-- 												<option value="C_C" <c:if test="${worksheetSearchBean.workSheetType eq 'C_C'}">selected="selected"</c:if>>การจั้มสาย</option> --%>
												<option value="C_TTV" <c:if test="${worksheetSearchBean.workSheetType eq 'C_TTV'}">selected="selected"</c:if>>การจูนสัญญาณโทรทัศน์</option>
												<option value="C_RC" <c:if test="${worksheetSearchBean.workSheetType eq 'C_RC'}">selected="selected"</c:if>>การซ่อมสัญญาณ</option>
												<option value="C_ASTB" <c:if test="${worksheetSearchBean.workSheetType eq 'C_ASTB'}">selected="selected"</c:if>>ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
												<option value="C_MP" <c:if test="${worksheetSearchBean.workSheetType eq 'C_MP'}">selected="selected"</c:if>>การย้ายจุด</option>
												<option value="C_RP" <c:if test="${worksheetSearchBean.workSheetType eq 'C_RP'}">selected="selected"</c:if>>การลดจุด</option>
												<option value="C_CU" <c:if test="${worksheetSearchBean.workSheetType eq 'C_CU'}">selected="selected"</c:if>>การตัดสาย</option>
												<option value="C_M" <c:if test="${worksheetSearchBean.workSheetType eq 'C_M'}">selected="selected"</c:if>>การย้ายสาย</option>
												<option value="C_B" <c:if test="${worksheetSearchBean.workSheetType eq 'C_B'}">selected="selected"</c:if>>แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
											</select>
										</div>
										<div class="col-md-5 mb05">
											<select id="zone" name="zone" path="zone" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">--- แสดงเขตชุมชน / โครงการทั้งหมด ---</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${worksheetSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail } (${zoneBean.zoneName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<nav class="navbar navbar-light bg-white navbar-bottom-line b-a mb-1">
								<button class="navbar-toggler hidden-md-up" type="button" data-toggle="collapse" data-target="#exCollapsingNavbar4" aria-controls="exCollapsingNavbar4" aria-expanded="false" aria-label="Toggle navigation"></button>
								<div class="collapse navbar-toggleable-sm" id="exCollapsingNavbar4">
									<ul class="nav nav-tabs nav-tabs-2 profile-tabs" role="tablist">
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'All'}">active</c:if>" data-toggle="tab" onclick="setTab('All');"
										href="#block_data_all" role="tab" aria-expanded="true">
										<b>ใบงานทั้งหมด ( ${pagination.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'W'}">active</c:if>" data-toggle="tab" onclick="setTab('W');"
										href="#block_data_w" role="tab" aria-expanded="false">
										<b>ใบงานรอมอบหมายงาน ( ${pagination_w.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'R'}">active</c:if>" data-toggle="tab" onclick="setTab('R');"
										href="#block_data_r" role="tab" aria-expanded="false">
										<b>ใบงานอยู่ระหว่างดำเนินงาน ( ${pagination_r.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'H'}">active</c:if>" data-toggle="tab" onclick="setTab('H');"
										href="#block_data_h" role="tab" aria-expanded="false">
										<b>ใบงานคงค้าง ( ${pagination_h.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'C'}">active</c:if>" data-toggle="tab" onclick="setTab('C');"
										href="#block_data_c" role="tab" aria-expanded="false">
										<b>ใบงานที่ยกเลิก ( ${pagination_c.totalItem} )</b></a></li>
										<li class="nav-item">
										<a class="nav-link <c:if test="${worksheetSearchBean.tab eq 'S'}">active</c:if>" data-toggle="tab" onclick="setTab('S');"
										href="#block_data_s" role="tab" aria-expanded="false">
										<b>ใบงานที่เสร็จเรียบร้อย ( ${pagination_s.totalItem} )</b></a></li>
									</ul>
								</div>
							</nav>
						</div>
					</div>
					<div class="tab-content">
					<jsp:include page="block_data_all.jsp"></jsp:include>
					<jsp:include page="block_data_w.jsp"></jsp:include>
					<jsp:include page="block_data_r.jsp"></jsp:include>
					<jsp:include page="block_data_h.jsp"></jsp:include>
					<jsp:include page="block_data_c.jsp"></jsp:include>
					<jsp:include page="block_data_s.jsp"></jsp:include>
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
		function setTab(tab){
		    $('#tab').val(tab);
		};
	</script>
</body>
</html>
