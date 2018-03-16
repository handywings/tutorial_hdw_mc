<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!-- Header -->
<div class="site-header">
	<nav class="navbar navbar-light">
		<div class="navbar-left">
			<a class="navbar-brand">
				<div class=""></div>
			</a>
			<div
				class="toggle-button dark sidebar-toggle-first float-xs-left hidden-md-up">
				<span class="hamburger"></span>
			</div>
			<div class="toggle-button-second dark float-xs-right hidden-md-up">
				<a href="#" data-toggle="dropdown" aria-expanded="false"
					style="color: #fff"> <span class="avatar box-32"> <img
						src="<c:url value="/resources/assets/img/user-128.png" />" alt="">
				</span>
				</a>
				<div class="dropdown-menu dropdown-menu-right animated fadeInUp"  style=" position: absolute !important; ">
					<a class="dropdown-item" href="${pageContext.request.contextPath}/changepassword"> <i
						class="ti-settings mr-0-5"></i> เปลี่ยนรหัสผ่าน
					</a>
					<div class="dropdown-divider"></div>
					<a class="dropdown-item"
						href="${pageContext.request.contextPath}/login/removeSession"><i
						class="ti-power-off mr-0-5"></i> ออกจากระบบ</a>
				</div>
			</div>
		</div>
		<div class="navbar-right navbar-toggleable-sm collapse"
			id="collapse-1">
			<div
				class="toggle-button light sidebar-toggle-second float-xs-left hidden-sm-down">
				<span class="hamburger"></span>
			</div>
			<!-- 						<div class="toggle-button-second light float-xs-right hidden-sm-down"> -->
			<!-- 							<i class="ti-arrow-left"></i> -->
			<!-- 						</div> -->
			<ul class="nav navbar-nav float-md-right">
				<li class="nav-item dropdown hidden-sm-down"><a href="#"
					data-toggle="dropdown" aria-expanded="false" style="color: #fff">
						<span class="avatar box-32"> <img
							src="<c:url value="/resources/assets/img/user-128.png" />" alt="">
					</span>&nbsp;&nbsp;<b>${sessionScope.authen.personnel.firstName}
						${sessionScope.authen.personnel.lastName}</b>
				</a>
					<div class="dropdown-menu dropdown-menu-right animated fadeInUp">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/changepassword"> <i
							class="ti-settings mr-0-5"></i> เปลี่ยนรหัสผ่าน
						</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item"
							href="${pageContext.request.contextPath}/login/removeSession"><i
							class="ti-power-off mr-0-5"></i> ออกจากระบบ</a>
					</div></li>
			</ul>
			<ul class="nav navbar-nav">
				<li class="nav-item dropdown hidden-sm-down">
					<!-- 								<a class="nav-link" href="#" data-toggle="dropdown" aria-expanded="false"> -->
					<!-- 									<i class="ti-layout-grid3"></i> --> <!-- 								</a> -->
					<div class="dropdown-apps dropdown-menu animated fadeInUp">
						<div class="a-grid">
							<div class="row row-sm">
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/dropbox.png" />"
													alt="">
											</div>
											<div class="ai-title">Dropbox</div>
										</a>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/github.png" />"
													alt="">
											</div>
											<div class="ai-title">Github</div>
										</a>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/wordpress.png" />"
													alt="">
											</div>
											<div class="ai-title">Wordpress</div>
										</a>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/gmail.png" />"
													alt="">
											</div>
											<div class="ai-title">Gmail</div>
										</a>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/drive.png" />"
													alt="">
											</div>
											<div class="ai-title">Drive</div>
										</a>
									</div>
								</div>
								<div class="col-xs-4">
									<div class="a-item">
										<a href="#">
											<div class="ai-icon">
												<img class="img-fluid"
													src="<c:url value="/resources/assets/img/brands/dribbble.png" />"
													alt="">
											</div>
											<div class="ai-title">Dribbble</div>
										</a>
									</div>
								</div>
							</div>
						</div>
						<a class="dropdown-more" href="#"> <strong>View all
								apps</strong>
						</a>
					</div>
				</li>
			</ul>

		</div>
	</nav>
</div>

