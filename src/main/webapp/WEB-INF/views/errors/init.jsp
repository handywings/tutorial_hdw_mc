<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="theme-color" content="#1a3952">
<meta name="description" content="">
<meta name="author" content="HandyWing Co.,Ltd">
<title>ขออภัย !! พบข้อผิดพลาด</title>

<!-- Vendor CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/bootstrap4/css/bootstrap.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/themify-icons/themify-icons.css"/>">
<!-- <link rel="stylesheet" -->
<%-- 	href="<c:url value="/resources/assets/plugins/font-awesome/css/font-awesome.min.css"/>"> --%>

<!-- Neptune CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/core.css"/>">

<style type="text/css">
.bgc {
	background-image: linear-gradient(to top, #48c6ef 0%, #6f86d6 100%);
	color: #fff;
}
</style>
</head>
<body class="bgc">

	<div class="error-message text-xs-center">
		<h1 class="mb-3" style="font-size: 10rem">
			${errorcode}<span><i class="ti-na" style="font-size: inherit;"></i></span>
		</h1>
<!-- 		<h5 class="text-uppercase">Page not found!</h5> -->
		<center>
			<b><h3>${errorMsg}</h3></b>
		</center>
		<br> <a href="${pageContext.request.contextPath}/main"><button
				type="button" class="btn btn-lg btn-outline-white w-min-md"><span class="ti-home"></span> <b>กลับไปยังระบบบริหารงานเคเบิลทีวีและอินเตอร์เน็ต</b></button></a>
	</div>

	<!-- Vendor JS -->
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/tether/js/tether.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/bootstrap4/js/bootstrap.min.js"/>"></script>
</body>
</html>