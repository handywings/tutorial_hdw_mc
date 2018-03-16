<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="equipmentproductcategory" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>หมวดหมู่วัสดุอุปกรณ์</title>
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
							<h3>หมวดหมู่วัสดุอุปกรณ์</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active">หมวดหมู่วัสดุอุปกรณ์</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a class="cursor-p" data-toggle="modal"
								data-target="#addServicePackageType">
								<perm:permission object="M17.add" >
								<button type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มหมวดหมู่วัสดุอุปกรณ์
								</button>
								</perm:permission>
							</a>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการหมวดหมู่วัสดุอุปกรณ์
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
														<th><center>รหัสประเภท</center></th>
														<th>ชื่อหมวดหมู่วัสดุอุปกรณ์</th>
														<th>รายละเอียด</th>
														<th width="200px;"><center>จำนวนวัสดุอุปกรณ์ทั้งหมด</center></th>
														<th></th>
													</tr>
												</thead>
												<tbody>

													<c:choose>
														<c:when
															test="${equipmentProductCategories != null && equipmentProductCategories.size() > 0 }">
															<c:forEach var="equipmentProductCategory"
																items="${equipmentProductCategories}" varStatus="i">
																<tr>
																	<td><center>
																			<b>${equipmentProductCategory.equipmentProductCategoryCode}</b>
																		</center></td>
																	<td>${equipmentProductCategory.equipmentProductCategoryName}</td>
																	<td>${equipmentProductCategory.description}</td>
																	<td><center>${textUtil.getFormatNumberInt(equipmentProductCategory.countEquipmentProduct)}</center></td>
																	<td align="center">
																	
																	<perm:permission object="M17.edit" >
																	<a class="cursor-p"
																		data-toggle="modal"
																		onclick="editEquipmentproductcategory(${equipmentProductCategory.id})"><span
																			class="ti-pencil-alt" data-toggle="tooltip"
																			data-placement="bottom" title=""
																			data-original-title="แก้ไข"></span></a> 
																		</perm:permission>
																		
																		<perm:permission object="M17.delete" >	
																			<c:if
																			test="${equipmentProductCategory.equipmentProductCategoryCode ne '00001' 
															&& equipmentProductCategory.equipmentProductCategoryCode ne '00002'
															&& equipmentProductCategory.equipmentProductCategoryCode ne '00003'}">
																			<a class="run-swal cursor-p"><span
																				class="ti-trash"
																				onclick="openDialogDelete(${equipmentProductCategory.id });"
																				data-toggle="tooltip" data-placement="bottom"
																				title="" data-original-title="ลบ"></span></a>
																		</c:if>
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

			<!-- modal add -->
			<jsp:include page="modal_add.jsp"></jsp:include>
			<!-- end modal add -->

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
</body>
</html>
