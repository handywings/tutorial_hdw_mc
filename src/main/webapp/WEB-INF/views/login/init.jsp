<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 	<%
	    if ((session.getAttribute("authen") != null)) {
	    	response.sendRedirect("dashboard");
	    }
	%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="theme-color" content="#1a3952">
<meta name="description" content="">
<meta name="author" content="HandyWing Co.,Ltd">
<link rel="shortcut icon" href="<c:url value="/resources/assets/img/logo-login.png" />">

<!-- Title -->
<title>ระบบบริหารงานเคเบิลทีวีและอินเตอร์เน็ต</title>

<link rel="stylesheet" href="resources/assets/css/login.css">
</head>

<body class="material-design fixed-sidebar fixed-header">
	<div class="bg-img"></div>
	<div class="grid">
<!-- 		<img src="resources/assets/img/logo-login.png" class="logo" /> -->
		<div class="text">
			<div class="text-main"><b>ระบบบริหารงานเคเบิลทีวีและอินเตอร์เน็ต</b></div>
<!-- 			<div class="text-corp"><b>บริษัท มหาชัยเคเบิลทีวี จำกัด</b></div> -->
		</div>
		<form:form method="post" id="loginForm" commandName="loginForm" class="form login" action="${pageContext.request.contextPath}/login/authen">

			<div class="form__field">
				<label for="login__username"><svg class="icon">
              <use xmlns:xlink="http://www.w3.org/1999/xlink"
							xlink:href="#user"></use></svg> </label> 
							<input type="text" name="username"  class="form__input" placeholder="ชื่อผู้ใช้" required value="">
			</div>

			<div class="form__field">
				<label for="login__password"> <svg class="icon">
						<use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#lock"></use></svg>
				</label> <input id="login__password" type="password" name="password"  class="form__input" placeholder="รหัสผ่าน" required value="">
			</div>
			<span style="color: #ffcf00;font-size: 20px;font-weight: bold; <c:if test="${loginStatus == null || loginStatus != 'N' }">display: none;</c:if>" >ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง
    กรุณาตรวจสอบอีกครั้ง</span>
			<a>
			<div class="form__field"> 
				<input type="submit" value="เข้าสู่ระบบ">
			</div>
			</a>

		</form:form>
		

	</div>

	<svg xmlns="http://www.w3.org/2000/svg" class="icons">
        <symbol id="arrow-right" viewBox="0 0 1792 1792">
            <path
			d="M1600 960q0 54-37 91l-651 651q-39 37-91 37-51 0-90-37l-75-75q-38-38-38-91t38-91l293-293H245q-52 0-84.5-37.5T128 1024V896q0-53 32.5-90.5T245 768h704L656 474q-38-36-38-90t38-90l75-75q38-38 90-38 53 0 91 38l651 651q37 35 37 90z" />
        </symbol>
        <symbol id="lock" viewBox="0 0 1792 1792">
            <path
			d="M640 768h512V576q0-106-75-181t-181-75-181 75-75 181v192zm832 96v576q0 40-28 68t-68 28H416q-40 0-68-28t-28-68V864q0-40 28-68t68-28h32V576q0-184 132-316t316-132 316 132 132 316v192h32q40 0 68 28t28 68z" />
        </symbol>
        <symbol id="user" viewBox="0 0 1792 1792">
            <path
			d="M1600 1405q0 120-73 189.5t-194 69.5H459q-121 0-194-69.5T192 1405q0-53 3.5-103.5t14-109T236 1084t43-97.5 62-81 85.5-53.5T538 832q9 0 42 21.5t74.5 48 108 48T896 971t133.5-21.5 108-48 74.5-48 42-21.5q61 0 111.5 20t85.5 53.5 62 81 43 97.5 26.5 108.5 14 109 3.5 103.5zm-320-893q0 159-112.5 271.5T896 896 624.5 783.5 512 512t112.5-271.5T896 128t271.5 112.5T1280 512z" />
        </symbol>
    </svg>
    
    <script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js"/>"></script>
    <c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>	
	<c:remove var="loginStatus" scope="session" />
</body>
</html>