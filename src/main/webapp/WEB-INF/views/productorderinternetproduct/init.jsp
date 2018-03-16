<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productorderequipmentproduct" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการสินค้าและบริการ</title>
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
							<h3>รายการสินค้าและบริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active">รายการสินค้าและบริการ</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M18.add" >
							<a
								href="${pageContext.request.contextPath}/productorderinternetproduct/add"><button
									type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>นำเข้าสินค้าประเภท
									Internet User
								</button></a>
						 </perm:permission>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<nav
								class="navbar navbar-light bg-white navbar-bottom-line b-a mb-2">
								<button class="navbar-toggler hidden-md-up" type="button"
									data-toggle="collapse" data-target="#exCollapsingNavbar4"
									aria-controls="exCollapsingNavbar4" aria-expanded="false"
									aria-label="Toggle navigation"></button>
								<div class="collapse navbar-toggleable-sm"
									id="exCollapsingNavbar4">
									<ul class="nav navbar-nav float-md-left">
										<li class="nav-item"><a class="nav-link"
											href="${pageContext.request.contextPath}/productorderequipmentproduct"><b>รายการสินค้าประเภทอุปกรณ์</b></a></li>
										<li class="nav-item active"><a class="nav-link"
											href="${pageContext.request.contextPath}/productorderinternetproduct"><b>รายการสินค้าประเภท
													Internet User</b></a></li>
										<li class="nav-item"><a class="nav-link"
											href="${pageContext.request.contextPath}/productorderserviceproduct"><b>รายการค่าแรง
													/ ค่าบริการ</b></a></li>
									</ul>
								</div>
							</nav>
						</div>
					</div>
					<div class="card mb30">
						<div class="card-header text-uppercase">
							<div class="row mt05">
								<div class="col-md-12">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการสินค้าประเภท
										Internet User
									</h4>
								</div>
							</div>
							<hr>
							<div class="row">
								<form
									action="${pageContext.request.contextPath}/productorderinternetproduct/searchinternetproduct"
									method="post">
									<div class="col-md-6 mb05">
										<input type="text" name="criteria" class="form-control"
											value="${criteria}"
											placeholder="ระบุชื่อหรือรหัสสินค้าที่ต้องการค้นหา...">
									</div>
									<div class="col-md-4 mb05">
										<select name="stock.id" class="form-control">
											<option value="0" selected="selected">ทุกคลังสินค้า</option>
											<c:forEach var="stocks" items="${stocks}" varStatus="i">
												<c:set var="selected" scope="session" value="" />
												<c:if test="${stocks.id==stockId}">
													<c:set var="selected" scope="session"
														value="selected=\"selected\"" />
												</c:if>
												<option value="${stocks.id}" ${selected}>${stocks.stockName}
													(${stocks.company.companyName})</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-2 mb05">
										<button type="submit" class="btn btn-lg bg-instagram col-xs-12 b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
									</div>
								</form>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">

								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr style="background: #e8e8e8;">
														<th class="td-middle"><center>ลำดับ</center></th>
														<th class="td-middle"><center>รหัสสินค้า</center></th>
														<th class="td-middle">ชื่อ package บริการ</th>
														<th class="td-middle">คลังสินค้า</th>
														<th width="50px"></th>
													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${internetProduct != null && internetProduct.size() > 0 }">
															<c:forEach var="internetProduct"
																items="${internetProduct}" varStatus="i">
																<tr>
																	<td class="td-middle" style="width: 20px;"><center>${i.count }</center></td>
																	<td class="td-middle" align="center"><b>${internetProduct.productCode}</b></td>
																	<td class="td-middle">${internetProduct.productName}</td>
																	<td class="td-middle">${internetProduct.stock.stockName}
																		(${internetProduct.stock.company.companyName})</td>
																	<td align="center"><a class="cursor-p"
																		href="${pageContext.request.contextPath}/productorderinternetproduct/detail/${internetProduct.id}">
																			<span class="ti-search" data-toggle="tooltip"
																			data-placement="bottom" title=""
																			data-original-title="ดูรายละเอียด"></span>
																	</a> 
																	
																	<perm:permission object="M19.edit" >
																		<a class="cursor-p"
																			href="${pageContext.request.contextPath}/productorderinternetproduct/edit/${internetProduct.id}">
																				<span class="ti-pencil-alt" data-toggle="tooltip"
																				data-placement="bottom" title=""
																				data-original-title="แก้ไข"></span>
																		</a> 
																	</perm:permission>
																	
																	<perm:permission object="M19.delete" >
																		<a class="run-swal cursor-p"><span
																				class="ti-trash"
																				onclick="openDialogDelete(${internetProduct.id });"
																				data-toggle="tooltip" data-placement="bottom"
																				title="" data-original-title="ลบ"></span></a>
																	</perm:permission>
																			
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="5"><center>ไม่พบข้อมูล</center></td>
															</tr>
														</c:otherwise>
													</c:choose>

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

			<!-- modal edit -->
			<jsp:include page="modal_edit.jsp"></jsp:include>
			<!-- end modal edit -->

			<!-- modal delete -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			<!-- end modal delete -->

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
		$( document ).ready(function() {
			$('#table-1_filter').remove();
		});
	</script>
</body>
</html>
