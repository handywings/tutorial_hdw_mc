<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="backup" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>สำรองข้อมูล</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
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
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row mb05 mt05" >
						<div class="col-md-8">
							<h3>สำรองข้อมูล</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ตั้งค่าข้อมูลพื้นฐาน</li>
								<li class="breadcrumb-item active">สำรองข้อมูล</li>
							</ol>
						</div>
					</div>

					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการสำรองข้อมูล
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="50px"><center>ลำดับ</center></th>
														<th><center>วันที่บันทึก</center></th>
														<th><center>ขนาดไฟล์</center></th>
														<th width="50px">Download</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="backupBean" items="${backupFileBeanList}" varStatus="i">
														<tr>
															<td class="td-middle"><center>${i.count }</center></td>
															<td class="td-middle"><center>${backupBean.createDateTh }</center></td>
															<td class="td-middle"><center>${backupBean.fileSizeConv }</center></td>
															<td align="center">
															<a href="${pageContext.request.contextPath}/backup/download/${backupBean.id}"><span
																	class="ti-download" data-toggle="tooltip"
																	data-placement="bottom" title=""
																	data-original-title="ดาวน์โหลด"></span></a> 
																	
<!-- 															<a class="run-swal cursor-p"><span class="ti-loop" -->
<%-- 																	onclick="openDialogRestore(${backupBean.id });" data-toggle="tooltip" --%>
<!-- 																	data-placement="bottom" title="" -->
<!-- 																	data-original-title="Restore"></span></a> -->
															</td>
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

				</div>
			</div>
			
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
	    <script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	
<script type="text/javascript">
	function openDialogRestore(id){
		swal({
			title : 'คุณต้องการกู้ข้อมูลชุดข้อมูลนี้',
			// 										text : "ถ้าคุณต้องการลบหน่วยนับรายการนี้กรุณากดปุ่ม \"ยืนยัน\" ด้านล่าง",
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการกู้ข้อมูล',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				$('.preloader').modal('show');
				$.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/backup/restoredb/"+id,
					dataType : 'json',
					async: false,
					//timeout : 100000,
					success : function(data) {
						window.location.reload();
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
			}
		})
	}	

</script>
</body>
</html>
