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
.card{box-shadow:none;}
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
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/stock"><span><i class="ti-package"></i></span><br>คลังสินค้า</a>
											</div><div class="col-xs-2">
												<a class="nav-link" href="${pageContext.request.contextPath}/dashboard/worksheet"><span><i class="fa fa-file-text-o" style="font-size: 14px;"></i></span><br>ใบงาน</a>
											</div>
											<div class="col-xs-2">
												<a class="nav-link active" href="${pageContext.request.contextPath}/dashboard/customer"><span><i class="ti-user"></i></span><br>ลูกค้า</a>
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
                    
                    <div class="row  mb30 ">	
                    	<div class="col-md-12">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-bar-chart"></i>&nbsp;
										จำนวนลูกค้าใหม่ประจำเดือนแต่ละเดือน ย้อนหลัง 12 เดือน 
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<div class="row">
											<div class="col-md-12">
												<canvas id="custAmount" class="chart-container" height="100px;"></canvas>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>					
						<div class="col-md-12">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-pie-chart"></i>&nbsp; ลูกค้าตามโซนเขตชุมชนติดตั้ง 
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">	
										 <canvas id="bar" height="120" class="chart-container"></canvas>									
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-6 mt05">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-pie-chart"></i>&nbsp; จำนวนลูกค้า (แยกตามประเภทลูกค้า)
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item"> 		
										 <canvas id="pie" class="chart-container"></canvas>								
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-6 mt05">
							<div class="card pb15">
								<div class="card-header text-uppercase">
									<h4>
										<i class="ti-pie-chart"></i>&nbsp; จำนวนลูกค้า (แยกตามลักษณะลูกค้า)
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">			
										 <canvas id="styleCustomer" class="chart-container"></canvas>							
									</div>
								</div>
							</div>
						</div>
					</div>	
                </div>
            </div>
           <jsp:include page="../layout/footer.jsp"></jsp:include>
        </div>
			
		<jsp:include page="../layout/script.jsp"></jsp:include>

<!-- 		<script type="text/javascript" -->
<%-- 			src="<c:url value="/resources/assets/js/dashboard/customer.js"/>"></script> --%>
		<script type="text/javascript"
			src="<c:url value="/resources/assets/js/dashboard/forms-pickers.js"/>"></script>
		<!--     <script type="text/javascript" src="/resources/assets/plugins/js/index.js"></script> -->
		<script>
		/* =================================================================
		Bar chart
		================================================================= */
		var ctxBarcustAmount = document.getElementById("custAmount");
			var data = {
			    labels: [
			    	<c:forEach items="${customerAmountBeanList}" var="customerAmountBeanList" varStatus="i">
			    	<c:choose>
					 	<c:when test="${i.count eq '12'}">
					 		"${customerAmountBeanList.title}"
					 	</c:when>
					 	<c:otherwise>
					 		"${customerAmountBeanList.title}",
					 	</c:otherwise>
					</c:choose>
		    	</c:forEach>
			    	],
			    datasets: [{
			        label: 'จำนวน',
			        data: [
	 			    	<c:forEach items="${customerAmountBeanList}" var="customerAmountBeanList" varStatus="i">
					    	<c:choose>
							 	<c:when test="${i.count eq '12'}">
							 		${customerAmountBeanList.amount}
							 	</c:when>
							 	<c:otherwise>
							 		${customerAmountBeanList.amount},
							 	</c:otherwise>
							</c:choose>
				    	</c:forEach>	
			    	],
			        backgroundColor: [
			            "#2ecbbe",
			            "#f49755",
			            "#9faec4",
			            "#e97293",
			            "#c277d0",
			            "#8384e3",
			            "#73beff",
			            "#2ecbbe",
			            "#56b786",
			            "#f49755",
			            "#e97293",
			            "#c277d0",
			            "#8384e3",
			            "#73beff",
			            "#2ecbbe",
			            "#56b786",
			        ],
			        borderColor: [
			            'rgba(255,99,132,1)',
			            'rgba(54, 162, 235, 1)',
			            'rgba(75, 192, 192, 1)'
			        ],
			        borderWidth: 0,
			    }]
			};	

			var myChartBarcustAmount = new Chart(ctxBarcustAmount, {
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
			
		var ctxBar = document.getElementById("bar");

		var data = {
		    labels: [
		    	<c:forEach items="${customerAmountBeanList2}" var="customerAmountBeanList2" varStatus="i">
			    	<c:if test="${i.count < 11}">
				    	<c:choose>
					    	<c:when test="${i.count eq sizeCusByZone}">
						 		"${customerAmountBeanList2.title2}"
						 	</c:when>
						 	<c:otherwise>
						 		"${customerAmountBeanList2.title2}",
						 	</c:otherwise>
						</c:choose>
				 	</c:if>		    	
		    	</c:forEach>	

		    ],
		    datasets: [{
		        label: 'จำนวน',
		        data: [
		        	<c:forEach items="${customerAmountBeanList2}" var="customerAmountBeanList2" varStatus="i">
				    	<c:if test="${i.count < 11}">
					    	<c:choose>
						    	<c:when test="${i.count eq sizeCusByZone}">
							 		${customerAmountBeanList2.amount2}
							 	</c:when>
							 	<c:otherwise>
							 		${customerAmountBeanList2.amount2},
							 	</c:otherwise>
							</c:choose>
					 	</c:if>		    	
			    	</c:forEach>	
		        	],
		        backgroundColor: [
		        	"#e97293",
		            "#c277d0",
		            "#8384e3",
		            "#73beff",
		            "#2ecbbe",
		            "#56b786",
		            "#f49755",
		            "#e97293",
		            "#c277d0",
		            "#8384e3"
		        ],
		        borderColor: [
		            'rgba(255,99,132,1)',
		            'rgba(54, 162, 235, 1)',
		            'rgba(255, 206, 86, 1)',
		            'rgba(75, 192, 192, 1)'
		        ],
		        borderWidth: 0,
		    }]
		};

		var myChartBar = new Chart(ctxBar, {
		    type: 'horizontalBar',
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
		                }
		            }],
		            xAxes: [{
		                gridLines: {
		                    color: "rgba(0, 0, 0, 0)",
		                }
		            }]
		        }
		    }
		});
		
		// กราฟวงกลม แสดงสัดส่วนตามประเภทลูกค้า 
		var ctx = document.getElementById("pie");

		var data = {
		    labels: [
		        "บุคคลธรรมดา",
		        "นิติบุคคล"
		    ],
		    datasets: [{
		        data: [${customerDashboard.individual}, ${customerDashboard.corporate}],
		        backgroundColor: [
		            "#e97293",
		            "#73beff"
		        ]
		    }]
		};

		var myChart = new Chart(ctx, {
		    type: 'pie',
		    data: data
		});
		
		// กราฟวงกลม แสดงสัดส่วนตามลักษณะลูกค้า
		var ctxStyle = document.getElementById("styleCustomer");

		var data = {
		    labels: [
		        "สมาชิกบ้าน (ที่พักอาศัยส่วนตัว )",
		        "สมาชิกโครงการ (ราคาเหมาจ่าย)",
		        "สมาชิก VIP (ไม่เสียค่าบริการ)"
		    ],
		    datasets: [{
		        data: [${customerDashboard.homeMember}, ${customerDashboard.projectMember}, ${customerDashboard.memberVIP}],
		        backgroundColor: [
		            "#20b2aa",
		            "#e97293",
		            "#8384e3"
		        ]
		    }]
		};

		var myChartStyle = new Chart(ctxStyle, {
		    type: 'pie',
		    data: data
		});
		</script>
</div>
</body>

</html>