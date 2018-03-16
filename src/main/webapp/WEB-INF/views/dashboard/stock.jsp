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
.box{
	border: none;
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
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard"><span><i class="ti-receipt"></i></span><br>บิล</a>
											</div><div class="col-xs-2">
												<a class="nav-link active" href="${pageContext.request.contextPath}/dashboard/stock"><span><i class="ti-package"></i></span><br>คลังสินค้า</a>
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
					<div class="row">
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1 mb-2">
								<div class="t-icon right">
									<span class="bg-primary"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase mb-1">สินค้าทั้งหมด</h4>
									<h1 class="mb-1">${stock.allProducts} รายการ</h1>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1 mb-2">
								<div class="t-icon right">
									<span class="bg-warning"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase mb-1">สินค้าใกล้หมด</h4>
									<h1 class="mb-1">${stock.lowInStock} รายการ</h1>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1 mb-2">
								<div class="t-icon right">
									<span class="bg-danger"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase mb-1">สินค้าหมด</h4>
									<h1 class="mb-1">${stock.outOfStock} รายการ</h1>
								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1 mb-2">
								<div class="t-icon right">
									<span class="bg-success"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase mb-1 text-gray">สินค้าหมดประกัน</h4>
									<h1 class="mb-1">${stock.insuranceExpire} รายการ</h1>
								</div>
							</div>
						</div>
					</div>
					<div class="row mt05 mb30">
						<c:forEach var = "stocks" items="${stocks}" varStatus="i">
<%-- 							<c:if test="${stocks.allProducts != '0'} && ${stocks.lowInStock != '0'} && ${stocks.outOfStock != '0'} && ${stocks.insuranceExpire != '0'}"> --%>
								<div class="col-md-6">	
									<div class="card pb15">
										<div class="card-header text-uppercase">
											<h4>
												<i class="ti-pie-chart"></i>&nbsp; คลังสินค้า ${stocks.stockName}
											</h4>
										</div>
										<div class="posts-list posts-list-1">
											<div class="pl-item">
												<canvas id="pie_${i.count}" class="chart-container"></canvas>
											</div>
										</div>
									</div>
								</div>
<%-- 							</c:if>						 --%>
						</c:forEach>
					</div>
				</div>
			</div>

			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>
	</div>
	<jsp:include page="../layout/script.jsp"></jsp:include>

<!-- 	<script type="text/javascript" -->
<%-- 		src="<c:url value="/resources/assets/js/dashboard/stock.js"/>"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/js/dashboard/forms-pickers.js"/>"></script>
	<!--     <script type="text/javascript" src="/resources/assets/plugins/js/index.js"></script> -->
	
<script type="text/javascript">
<c:forEach var = "stocks" items="${stocks}" varStatus="i">
var ctx = document.getElementById("pie_"+${i.count});
var data = {
    labels: [
        "สินค้าทั้งหมด",
        "สินค้าใกล้หมด",
        "สินค้าหมด",
        "สินค้าหมดประกัน"
    ],
    datasets: [{
        data: [${stocks.allProducts}, ${stocks.lowInStock}, ${stocks.outOfStock}, ${stocks.insuranceExpire}],
        backgroundColor: [
            "#73beff",
            "#f49755",
            "#e97293",
            "#9faec4"
        ]
    }]
};

var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: data
});
</c:forEach>
</script>

</body>

</html>