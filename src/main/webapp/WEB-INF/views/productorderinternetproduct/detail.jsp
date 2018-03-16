<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productorderequipmentproduct" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>รายการสินค้า ${internetProductBean.productName}</title>
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
					<div class="row mt05 mb05" >
						<div class="col-md-8">
							<h3>${internetProductBean.productName}</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderinternetproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item active">ข้อมูลสินค้า</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M19.edit" >
						<a href="${pageContext.request.contextPath}/productorderinternetproduct/edit/${internetProductBean.id}">
						<button type="button" class="btn btn-warning label-left float-xs-right mr-0-5 mb05">
									<span class="btn-label"><i class="ti-pencil-alt"></i></span>แก้ไขข้อมูล
								</button>
						</a>
						</perm:permission>
						</div>
					</div>

					<div class="card mb30">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายละเอียด INTERNET USER
										${internetProductBean.productName}
									</h4>
								</div>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูล INTERNET USER&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-3">
													<b>รหัสสินค้า</b><br>${internetProductBean.productCode}
												</div>
												<div class="col-md-5">
													<b>ชื่อสินค้า</b><br>${internetProductBean.productName}
												</div>
												<div class="col-md-4">
													<b>คลังสินค้า</b><br>${internetProductBean.stock.stockName} (${internetProductBean.stock.company.companyName})
												</div>
											</div>

										</fieldset>
									</div>
								</div>
								 <div class="row mt15">

								</div> 
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr style="background: #e8e8e8;">
														<th class="td-middle" style="width: 20px;"><center>ลำดับ</center></th>
														<th class="td-middle">UserName</th>
														<th class="td-middle">Password</th>
														<th class="td-middle"><center>Reference</center></th>
														<th class="td-middle" style="width: 40px;"><center>Status</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${internetProductBean.internetProductBeanItems}" var="productItem" varStatus="i">
														<tr>
															<td class="td-middle"><center>${i.count }</center></td>
															<td class="td-middle">${productItem.userName}</td>
															<td class="td-middle">${productItem.password}</td>
															<td class="td-middle"><center>${productItem.reference}</center></td>
															<td>
																<c:choose>
																	<c:when test="${productItem.status == '0'}">
																		<center><span class="text-green"><b>ปรกติ</b></span></center>
																	</c:when>
																	<c:when test="${productItem.status == '5'}">
																		<center><span class="text-red"><b>ใช้งาน</b></span></center>
																	</c:when>
																</c:choose>
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
			
			<!-- modal edit -->
			<jsp:include page="modal_edit_item.jsp"></jsp:include>
			<!-- end modal edit -->
			
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

	function openDialogDeleteItem(id){
		swal({
			title : 'คุณต้องการลบข้อมูลรายการนี้',
			// 										text : "ถ้าคุณต้องการลบหน่วยนับรายการนี้กรุณากดปุ่ม \"ยืนยัน\" ด้านล่าง",
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการลบ',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				/*swal({
					title : 'การลบเสร็จสมบูรณ์!',
					text : 'ระบบทำการลบบริษัทย่อยเรียบร้อยแล้ว',
					type : 'success',
					confirmButtonClass : 'btn btn-primary btn-lg',
					buttonsStyling : false
				});*/
				$('.preloader').modal('show');
				$.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/productorderinternetproduct/item/delete/"+id,
					dataType : 'json',
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
