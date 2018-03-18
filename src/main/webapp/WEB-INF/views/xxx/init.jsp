<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="xxx" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการหน่วยนับ</title>
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
						<div class="col-md-8">
							<h3>รายการXXX</h3>
							<ol class="breadcrumb no-bg mb-1">
							<li class="breadcrumb-item active">รายการXXX</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						
								<a class="cursor-p" data-toggle="modal" data-target="#addXxx"><button type="button"
										class="btn btn-info label-left float-xs-right mr-0-5">
										<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มXXX
									</button></a>
							
						</div>
					</div>

					<div class="card mt05 mb40">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการXXX</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="30px;"><center>ลำดับ</center></th>
														<th><center>xxxName</center></th>
														<th width="150px;"><center>ผู้บันทึก</center></th>
														<th width="250px;"><center>วันเวลาที่บันทึก</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>

			<!-- modal add -->
			<jsp:include page="modal_add.jsp"></jsp:include>
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>

</body>
</html>
