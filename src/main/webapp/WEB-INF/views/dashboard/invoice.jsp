<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="dashboard" scope="request"/>
<c:set var="subMenu" value="dashboard" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ภาพรวมระบบ</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/chartist/chartist.min.css"/>">
<!-- JS -->
<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/chartjs/Chart.bundle.min.js"/>"></script>
<style type="text/css">
.card {
	box-shadow: none;
}
</style>
</head>

<body class="material-design fixed-header fixed-sidebar">
	<div class="wrapper">

		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>
		<div class="site-content">
			<div class="content-area bg-white" style="padding-top: 1rem !important;background-image: url(/cable/resources/assets/img/pattern.svg),linear-gradient(to right, #c3c3c3 0, rgba(158, 158, 158, 0.26) 100%);color: white;/* background-position: center,center center; */background-repeat: no-repeat,no-repeat;">
				<div class="container-fluid">
					<div class="row mt05">
						<div class="col-md-6">
							<h3>ภาพรวมระบบ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active" style=" color: #e6e6e6; "><b>ภาพรวมระบบ / ข้อมูลภาพรวม</b></li>
							</ol>
						</div>
						<div class="col-md-6">
							<ul class="nav nav-tabs nav-tabs-2 pull-right">
								<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/main"><i class="ti-layout-grid3 text-muted mr-0-25"></i> <b>เมนูทางลัด</b></a></li>
								<li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/dashboard" style=" border-bottom: 3px solid #F44336; "><i class="ti-bar-chart-alt text-muted mr-0-25"></i> <b>ข้อมูลภาพรวม</b></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row mb05">
						<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="box bg-white">
									<nav class="nav nav-1">
										<div class="row no-gutter" style=" background-color: white; ">
											<div class="col-xs-2">
												<a class="nav-link active" href="${pageContext.request.contextPath}/dashboard"><span><i class="ti-receipt"></i></span><br>บิล</a>
											</div><div class="col-xs-2">
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/stock"><span><i class="ti-package"></i></span><br>คลังสินค้า</a>
											</div><div class="col-xs-2">
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/worksheet"><span><i class="fa fa-file-text-o" style="font-size: 14px;"></i></span><br>ใบงาน</a>
											</div>
											<div class="col-xs-2">
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/customer"><span><i class="ti-user"></i></span><br>ลูกค้า</a>
											</div>
											<div class="col-xs-2">
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/service"><span><i class="ti-gift"></i></span><br>บริการ</a>
											</div>
											<div class="col-xs-2">
												<a class="nav-link b-r-0" href="${pageContext.request.contextPath}/dashboard/income"><span><i class="fa fa-money" style="font-size: 14px;"></i></span><br>รายได้</a>
											</div>
										</div>
									</nav>
								</div>
							</div>
					</div>					
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; เลือกช่วงเวลาที่ต้องการค้นข้อมูล
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
								<form:form class="form-material material-primary" id="wSeachInvoice" method="post" commandName="dashboardBean" action="${pageContext.request.contextPath}/dashboard/search">
									<div class="col-md-5">
										<select id="monthTabBill" name="monthTabBill" path="monthTabBill" class="form-control">
										<c:forEach items="${months}" var="month" varStatus="i" >
											<option value="${i.count}" <c:if test="${i.count == dashboardBean.monthTabBill}">selected="selected"</c:if>>เดือน${month}</option>
										</c:forEach>
										</select>
									</div>
									<div class="col-md-5">
										<select id="yearTabBill" name="yearTabBill" path="yearTabBill" class="form-control">
										<c:forEach items="${years}" var="year">
											<option value="${year}" <c:if test="${year == dashboardBean.yearTabBill}">selected="selected"</c:if>>ปี&nbsp;${year}</option>
										</c:forEach>
										</select>
									</div>
									<div class="col-md-2">
										<button type="submit" class="btn btn-lg bg-instagram b-a-0 waves-effect waves-light col-xs-12">
												<span class="ti-search"></span> ค้นหา
											</button>
									</div>
								</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="row mt05">
						<div class="col-md-6">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-bar-chart"></i>&nbsp;
										สรุปจำนวนใบบิลแบ่งตามประเภทต่างๆ
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<div class="row">
											<div class="col-md-12">
												<canvas id="bar" class="chart-container" height="200px;"></canvas>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-pie-chart"></i>&nbsp;
										สรุปจำนวนบิลตามประเภทใบสมัคร
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<canvas id="pie" class="chart-container" height="200px;"></canvas>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card mb40 mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายชื่อคนเก็บเงิน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="table-responsive">
									<table class="table table-bordered dataTable no-footer table-1"
										id="tb-cashier">
										<thead class="thead-default">
											<tr>
												<th width="60px;"><center>ลำดับ</center></th>
												<th><center>ชื่อ - นามสกุล</center></th>
												<th><center>จำนวนบิลที่เก็บได้</center></th>
												<th><div align="center">ยอดที่เก็บได้ (บาท)</div></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="cashierIsTrue" items="${cashierIsTrue}" varStatus="i">
												<tr>
													<td class="td-middle"><center>${i.count}</center></td>
													<td >${cashierIsTrue.fullName}</td>
													<td align="center">${cashierIsTrue.totalBill}</td>
													<td align="center">${cashierIsTrue.sumAmount}</td>
												</tr>
											</c:forEach>											
										</tbody>
									</table>
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

<!-- 	<script type="text/javascript" -->
<%-- 		src="<c:url value="/resources/assets/js/dashboard/bill.js"/>"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/js/dashboard/forms-pickers.js"/>"></script>
	<!--     <script type="text/javascript" src="/resources/assets/plugins/js/index.js"></script> -->
	<script>
	/* =================================================================
	Bar chart
	================================================================= */
	var ctxBar = document.getElementById("bar");
		var data = {
		    labels: ["เก็บได้", "เก็บไม่ได้", "หนี้สูญ"],
		    datasets: [{
		        label: 'จำนวน',
		        data: [${billDashboard.countKeep},${billDashboard.countNotKeep},${billDashboard.badDebt}],
		        backgroundColor: [
		            "#2ecbbe",
		            "#f49755",
		            "#9faec4"
		        ],
		        borderColor: [
		            'rgba(255,99,132,1)',
		            'rgba(54, 162, 235, 1)',
		            'rgba(75, 192, 192, 1)'
		        ],
		        borderWidth: 0,
		    }]
		};	

		var myChartBar = new Chart(ctxBar, {
		    type: 'bar',
		    data: data,
		    options: {
		        legend: {
		            display: false
		        },
		        scales: {
		            yAxes: [{
		                ticks: {
		                    beginAtZero: true
		                },
		                gridLines: {
		                    color: "rgba(0, 0, 0, 0)",
		                },
		                fontSize: 40
		            }],
		            xAxes: [{
		                gridLines: {
		                    color: "rgba(0, 0, 0, 0)",
		                },
		                fontSize: 40
		            }]
		        }
		    }
		});
		
		/* =================================================================
	    Pie chart
	================================================================= */
		var ctx = document.getElementById("pie");
		var data = {
		    labels: [
		        "รายเดือน",
		        "รายครึ่งปี",
		        "รายปี",
// 		        "ถูกตัดสาย",
		        "รายครั้ง",
		        "อื่นๆ"
		    ],
		    datasets: [{
		        data: [${billDashboard.countMonth},${billDashboard.countHalfYear},${billDashboard.countYear},
		        	${billDashboard.countTime},${billDashboard.other}],
		        backgroundColor: [
		            "#e97293",
		            "#c277d0",
		            "#8384e3",
		            "#2ecbbe",
		            "#f49755"
		        ]
		    }]
		};

		var myChart = new Chart(ctx, {
		    type: 'doughnut',
		    data: data
		});
	</script>
</body>

</html>