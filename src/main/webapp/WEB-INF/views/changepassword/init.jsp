<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="theme-color" content="#2682cb">
<meta name="description" content="">
<meta name="author" content="HandyWing Co.,Ltd">
<link rel="shortcut icon" href="<c:url value="/resources/assets/img/logo-login.png" />">
<!-- Vendor CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/bootstrap4/css/bootstrap.min.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/themify-icons/themify-icons.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/plugins/font-awesome/css/font-awesome.min.css"/>">

<!-- Neptune CSS -->
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/core.css"/>">

<!-- HTML5 Shiv and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>
<body class="material-design img-cover"
	style="background-image: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);">

	<div class="container-fluid">
		<div class="sign-form">
			<div class="row">
				<div class="col-md-4 offset-md-4 px-3">
					<div class="card b-a-0 bg-white">
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="p-2 text-xs-center">
									<h2>
										<i class="ti-key mr-0-5" style="font-size: 20px;"></i>
										เปลี่ยนแปลงรหัสผ่านเข้าระบบ
									</h2>
								</div>
								<div class="row">
									<div class="col-md-12">
										<form class="form-material">
											<div class="form-group mb05">
												<input type="email" class="form-control"
													id="exampleInputEmail" placeholder="Email"
													readonly="readonly" value="${sessionScope.authen.username}">
											</div>
											<div class="form-group mb05">
												<input type="password" class="form-control"
													id="exampleInputPassword"
													placeholder="รหัสผ่านที่ใช้ปัจจุบัน">
											</div>
											<div class="form-group mb05">
												<input type="password" class="form-control"
													id="exampleInputNewPassword" placeholder="รหัสผ่านใหม่ ">
											</div>
											<div class="form-group mb-3 mb05">
												<input type="password" class="form-control"
													id="exampleInputResetPassword"
													placeholder="ยืนยันรหัสผ่านใหม่"> <br><center><span
													style="color: red" class="msgError"></span></center>
											</div>

											<div class="px-2 form-group mb-0">
												<button type="button" onclick="validateChangePassword()"
													class="btn btn-lg btn-warning btn-block text-uppercase"><b style="font-size: 20px;">ยืนยันข้อมูล</b></button>
											</div>
										</form>
									</div>
								</div>
								<div class="p-2 text-xs-center text-muted"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Vendor JS -->
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/tether/js/tether.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/bootstrap4/js/bootstrap.min.js"/>"></script>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>
	<script>
		function validateChangePassword() {
			var exampleInputPassword = $('#exampleInputPassword').val();
			var exampleInputNewPassword = $('#exampleInputNewPassword').val();
			var exampleInputResetPassword = $('#exampleInputResetPassword')
					.val();
			if (exampleInputPassword == "") {
				$('.msgError').text("คุณยังไม่ได้กรอกรหัสผ่านที่ใช้ปัจจุบัน");
			} else if (exampleInputNewPassword == "") {
				$('.msgError').text("คุณยังไม่ได้กรอกรหัสผ่านใหม่");
			} else if (exampleInputResetPassword == "") {
				$('.msgError').text("คุณยังไม่ได้กรอกยืนยันรหัสผ่านใหม่");
			} else if (exampleInputNewPassword != exampleInputResetPassword) {
				$('.msgError')
						.text(
								"คุณจะต้องใส่รหัสผ่านเดียวกันสองครั้งเพื่อที่จะยืนยันรหัสผ่านนี้");
			} else if (exampleInputPassword == exampleInputNewPassword) {
				$('.msgError').text("รหัสใหม่ของคุณซ้ำกับรหัสเดิม");
			} else {
				$
						.ajax({
							type : 'POST',
							url : "changepassword/json/validateOldPassword",
							data : {
								"old_pass" : exampleInputPassword
							}
						})
						.done(
								function(data) {
									if (data == "password not match") {
										$('.msgError')
												.text(
														"รหัสผ่านที่ใช้ปัจจุบันของคุณไม่ถูกต้อง");
									} else if (data == "success") {
										confirmChangePassword(
												exampleInputPassword,
												exampleInputNewPassword);
									} else {
										window.location.href = "login";
									}
								}).fail(function() {

						});
			}
		}

		function confirmChangePassword(old_pass, new_pass) {
			$.ajax({
				type : 'POST',
				url : "changepassword/confirmChangePassword",
				data : {
					"new_pass" : new_pass,
					"old_pass" : old_pass
				}
			}).done(function(data) {
				if (data == "success") {
					window.location.href = "login";
				}
			}).fail(function() {

			});
		}

		$('#exampleInputPassword').click(function() {
			$('.msgError').text("");
		});
		$('#exampleInputNewPassword').click(function() {
			$('.msgError').text("");
		});
		$('#exampleInputResetPassword').click(function() {
			$('.msgError').text("");
		});
	</script>

</body>
</html>