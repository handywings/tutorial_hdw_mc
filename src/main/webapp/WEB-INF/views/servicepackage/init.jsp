<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="package" scope="request"/>
<c:set var="subMenu" value="servicepackage" scope="request"/>


<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการ Package บริการ</title>
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
						<div class="col-md-7">
							<h3>รายการ Package บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">Package บริการ</li>
								<li class="breadcrumb-item active">รายการ Package บริการ</li>
							</ol>
						</div>
						<div class="col-md-5 mt05" align="right">
						<perm:permission object="M46.add" >
							<a href="${pageContext.request.contextPath}/servicepackage/add"><button type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เพิ่ม
									Package บริการ
								</button></a>
						</perm:permission>		
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="card mt05">
								<div class="card-header text-uppercase">
									<h4>
										<i class="zmdi zmdi-search"></i>&nbsp;ค้นหารายการ Package บริการ
									</h4>
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item">
											<form:form method="post" commandName="servicePackageSearchBean" action="servicepackage/search">
												<div class="row mb15">
													<div class="col-md-12 mb05">
														<div class="form-group">
															<input type="text" id="key" path="key" name="key" class="form-control" value="${servicePackageSearchBean.key}" placeholder="ระบุรหัส หรือ ชื่อบริการที่ต้องการค้นหา">
														</div>
													</div>
													<div class="col-md-3 mb05">
														<div class="form-group">
															<select id="companyId" path="companyId" name="companyId" class="form-control">
																<option value="0">--- บริษัททั้งหมด ---</option>
																<c:forEach items="${companyBeans}" var="companyBean"> 
																	<c:choose>
																		<c:when test="${companyBean.id eq servicePackageSearchBean.companyId}">
																			<option value="${companyBean.id }" selected="selected">${companyBean.companyName }</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${companyBean.id }">${companyBean.companyName }</option>
																	    </c:otherwise> 
																	</c:choose>
																</c:forEach> 
															</select>
														</div>
													</div>
													<div class="col-md-4 mb05">
														<div class="form-group">
															<select id="servicePackageTypeCode" path="servicePackageTypeCode" name="servicePackageTypeCode" class="form-control">
																<option value="">--- ประเภทบริการทั้งหมด ---</option>
																<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
																	<c:choose>
																		<c:when test="${servicePackageTypeBean.packageTypeCode eq servicePackageSearchBean.servicePackageTypeCode}">
																			<option value="${servicePackageTypeBean.packageTypeCode }" selected="selected">${servicePackageTypeBean.packageTypeName }</option>
																		</c:when>
																		<c:otherwise>
																			<option value="${servicePackageTypeBean.packageTypeCode }">${servicePackageTypeBean.packageTypeName }</option>
																	    </c:otherwise> 
																	</c:choose>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="col-md-3 mb05">
														<div class="form-group">
															<select id="status" path="status" name="status" class="form-control">
																<option value="">--- สถานะการใช้งานทั้งหมด ---</option>
																<option value="1" <c:if test="${servicePackageSearchBean.status eq '1'}" >selected="selected"</c:if>>ให้บริการอยู่ </option>
																<option value="0" <c:if test="${servicePackageSearchBean.status eq '0'}" >selected="selected"</c:if>>ยกเลิกการให้บริการ</option>
															</select>
														</div>
													</div>
													<div class="col-md-2">
														<center>
															<button type="submit" id="exportPdf" class="btn btn-lg bg-instagram b-a-0 waves-effect waves-light col-xs-12">
																<span class="ti-search"></span>
																ค้นหา
															</button>
														</center>
														</div>
												</div>
											</form:form>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="card  mt05 mb30">
								<div class="card-header text-uppercase">
									<div class="col-md-6">
									<h4>
										<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;รายการ Package
										บริการ
									</h4>
									</div>
									<div class="col-md-6">
										<span class="text-grey pull-right"> สถานะการใช้งาน : <i class="fa fa-check text-green"></i>&nbsp;เปิดใช้งาน&nbsp;&nbsp;|&nbsp;&nbsp;<i class="fa fa-close text-red"></i>&nbsp;ปิดการใช้งาน
											</span>
									</div>									
								</div>
								<div class="posts-list posts-list-1">
									<div class="pl-item"> 
										<div class="row mb05">
											<div class="col-md-12 mb05 mt05">
												<div class="table-responsive">
													<table class="table table-bordered mb-0 table-hover" id="table-package">
														<thead class="thead-default">
															<tr>
																<th class="td-middle" width="10%"><center>รหัส</center></th>
																<th class="td-middle" width="40%">ชื่อ Package บริการ</th>
																<th class="td-middle" width="30%">ประเภทบริการ</th>
																<th class="td-middle" width="10%"><center>สถานะ</center></th>
																<th class="td-middle" width="10%"></th>
															</tr>
														</thead>
														<tbody>
														
															<c:choose>
																<c:when test="${servicePackageBeans != null && servicePackageBeans.size() > 0 }">
																	<c:forEach items="${servicePackageBeans}" var="servicePackageBean" varStatus="i">
																		<tr>
																			<td class="td-middle"><center>
																			<a class="cursor-p" href="${pageContext.request.contextPath}/servicepackage/view/${servicePackageBean.id}" data-toggle="tooltip"
																					data-placement="bottom" title=""
																					data-original-title="ดูรายละเอียด Package บริการ">
																					<b>
																					<perm:permission object="M46.view" >
																					${servicePackageBean.packageCode }
																					</perm:permission>
																					</b>
																			</a>
																			</center></td>
																			<td class="td-middle">${servicePackageBean.packageName }<br> <small class="text-gray">บันทึกเมื่อ
																					: ${servicePackageBean.createDateTh }</small>
																			</td>
																			<td class="td-middle">${servicePackageBean.serviceType.packageTypeName }<br><small style="color: gray;">( ${servicePackageBean.company.companyName } )</small></td>
																			<td class="td-middle"><center>
																				<c:choose>
																					<c:when test="${servicePackageBean.active}">
																						<i class="fa fa-check text-green"></i>
																					</c:when>
																					<c:otherwise>
																						<i class="fa fa-close text-red"></i>
																					</c:otherwise>
																				</c:choose>
																				</center>
																			</td>
																			<td align="center" class="td-middle">
																			<c:choose>
																				<c:when test="${fn:length(servicePackageBean.serviceApplicationBeans) >  0}">
																				<perm:permission object="M46.edit" >
																					<a  class="cursor-p" href="${pageContext.request.contextPath}/servicepackage/edit/${servicePackageBean.id}"><span class="ti-pencil-alt"
																							data-toggle="tooltip" data-placement="bottom" title=""
																							data-original-title="แก้ไข"></span>
																					</a>
																				</perm:permission>
																				</c:when>
																				<c:otherwise>
																				<perm:permission object="M46.edit" >
																					<a class="cursor-p" href="${pageContext.request.contextPath}/servicepackage/edit/${servicePackageBean.id}"><span class="ti-pencil-alt"
																							data-toggle="tooltip" data-placement="bottom" title=""
																							data-original-title="แก้ไข"></span>
																					</a>
																				</perm:permission>
																				</c:otherwise>
																			</c:choose>
																			<c:if test="${fn:length(servicePackageBean.serviceApplicationBeans) <= 0}">
																				 <perm:permission object="M46.delete" >
																					 <a style="cursor:pointer" onclick="openDialogDelete(${servicePackageBean.id});"><span class="ti-trash"
																							data-toggle="tooltip" data-placement="bottom" title=""
																							data-original-title="ลบ"></span>
																					 </a>
																				 </perm:permission>
																			 </c:if>
																			</td>
																		</tr>
					 												</c:forEach>
																</c:when>
																<c:otherwise>
																	<tr><td colspan="5"><center>ไม่พบข้อมูล</center></td></tr>
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

				</div>
			</div>

			<!-- delete modal -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			<!-- end delete modal -->
			
			<!-- edit modal -->
			<jsp:include page="modal_edit.jsp"></jsp:include>
			<!-- end edit modal -->
			
			<!-- Footer -->
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
		$( document ).ready(function() {
			$('#table-package').dataTable({searching: true, paging: true, "pageLength": 25});
		});
	</script>
</body>
</html>
