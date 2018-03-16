<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="serviceapplicationlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบสมัครใช้บริการ</title>
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
							<h3>รายการใบสมัครใช้บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item active">รายการใบสมัครใช้บริการ</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a class="cursor-p" href="${pageContext.request.contextPath}/serviceapplication" ><button
									type="button"
									class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>สมัครใช้บริการ
								</button></a>
						</div>
					</div>
					<div class="card mt15">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบสมัครใช้บริการ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="applicationSearchBean" action="${pageContext.request.contextPath}/serviceapplicationlist/search">
										<div class="col-md-12 mb05">
											<input type="text" name="key" class="form-control" value="${applicationSearchBean.key }" path="key"
												placeholder="ระบุ เลขใบสมัคร ชื่อสกุล อีเมล์ บ้านเลขที่ติดตั้ง หรือหมายเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-4 mb05">
											<select id="servicePackageType" name="servicePackageType" class="form-control" path="servicePackageType" onchange="getvalServiceType(this);">
												<option value="0" selected="selected">ประเภทบริการทั้งหมด</option>
												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
													<option value="${servicePackageTypeBean.id }" <c:if test="${applicationSearchBean.servicePackageType eq servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName }</option>
												</c:forEach>
											</select>
										</div>
<!-- 										<div class="col-md-2"> -->
<!-- 											<select id="servicePackage" name="servicePackage" class="form-control" path="servicePackage"> -->
<!-- 												<option value="" selected="selected" disabled="disabled">---บริการ ---</option> -->
<!-- 												<option value="">ทั้งหมด</option> -->
<%-- 												<c:forEach items="${servicePackageBeans}" var="servicePackageBean">  --%>
<%-- 													<option value="${servicePackageBean.id }" <c:if test="${applicationSearchBean.servicePackage eq servicePackageBean.id}">selected="selected"</c:if> >${servicePackageBean.packageName }</option> --%>
<%-- 												</c:forEach> --%>
<!-- 											</select> -->
<!-- 										</div> -->
										<div class="col-md-3 mb05">
											<select id="zone" name="zone" path="zone" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">เขตชุมชน / โครงการทั้งหมด</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${applicationSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail} (${zoneBean.zoneName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-3 mb05">
											<select id="status" name="status" class="form-control" path="status">
												<option value="" selected="selected">สถานะใช้งานทั้งหมด</option>
												<option value="D" <c:if test="${applicationSearchBean.status eq 'D'}">selected="selected"</c:if> >แบบร่าง</option>
												<option value="H" <c:if test="${applicationSearchBean.status eq 'H'}">selected="selected"</c:if> >รอมอบหมายงาน</option>
												<option value="A" <c:if test="${applicationSearchBean.status eq 'A'}">selected="selected"</c:if> >ใช้งานปกติ</option>
<%-- 												<option value="T" <c:if test="${applicationSearchBean.status eq 'T'}">selected="selected"</c:if> >ระงับชั่วคราว</option> --%>
												<option value="I" <c:if test="${applicationSearchBean.status eq 'I'}">selected="selected"</c:if> >ยกเลิกการใช้บริการ</option>
												<option value="W" <c:if test="${applicationSearchBean.status eq 'W'}">selected="selected"</c:if> >ระหว่างการติดตั้ง</option>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05 mb40">
						<div class="card-header text-uppercase">
							<div class="col-md-5">
								<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการใบสมัครใช้บริการทั้งหมด</h4>
							</div>
							<div class="col-md-7">
									<span class="pull-right"> 
										<i class="fa fa-pencil-square-o text-brown"></i>&nbsp;<font style="color: gray;">แบบร่าง</font>
										&nbsp;|&nbsp;<i class="fa fa-hourglass-2 text-orange"></i>&nbsp;<font style="color: gray;">รอมอบหมายงาน</font>
										&nbsp;|&nbsp;<i class="fa fa-wrench text-blue"></i>&nbsp;<font style="color: gray;">ระหว่างการติดตั้ง</font>
										&nbsp;|&nbsp;<i class="fa fa-check-circle-o text-green"></i>&nbsp;<font style="color: gray;">ใช้งานปกติ</font>
	<!-- 									<i class="ion-record text-red"></i> ระงับชั่วคราว |  -->
										&nbsp;|&nbsp;<i class="fa fa-times-circle-o text-red"></i>&nbsp;<font style="color: gray;">ยกเลิกการใช้บริการ</font>
									</span>
							</div>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">								
								<div class="row mb05">
									<div class="col-md-12">
										<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
									</div>									
								</div>
								
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th width="13%"><center>เลขที่ใบสมัคร</center></th>
														<th width="20%">ข้อมูลลูกค้า</th>
														<th width="32%">ข้อมูลบริการที่สมัคร</th>
														<th width="15%"><center>เขตชุมชน</center></th>
														<th width="10%"><center>สถานะใช้งาน</center></th>
														<th width="10%"></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
															<c:forEach var="serviceApplicationBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td class="td-middle">
																		<center>
																			<b>${serviceApplicationBean.serviceApplicationNo}</b>
																		</center>
																	</td>
																	<td class="td-middle">
																		${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;
																		${serviceApplicationBean.customer.lastName}<br>
																		<small style="color: gray;">
																		<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${serviceApplicationBean.customer.custCode }</a><br>
																		
																		<c:choose>
																			<c:when test="${serviceApplicationBean.plateNumber != null && serviceApplicationBean.plateNumber != \"\" }">
																				<b>Plate Number&nbsp;:&nbsp;</b>${serviceApplicationBean.plateNumber }<br>
																			</c:when>
																			<c:otherwise>
																				<c:if test="${serviceApplicationBean.customer.contact.email != null && serviceApplicationBean.customer.contact.email != \"\" }">
																					<b>Email&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.email}<br>
																				</c:if>																				
																			</c:otherwise>
																		</c:choose>
																		
																		<b>โทร&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.mobile}
																		</small>									
																	</td>
																	<td class="td-middle">${serviceApplicationBean.servicepackage.packageName}<br>
																	<small style="color: gray;"><b>รหัส Package :</b> ${serviceApplicationBean.servicepackage.packageCode}<br><b>ประเภทบริการ ${serviceApplicationBean.servicepackage.serviceType.packageTypeName}</b></small></td>
																	<td class="td-middle">
																		<center>
																			<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																				<c:if test="${addressBean.addressType eq '3' }">
																					${addressBean.zoneBean.zoneDetail}<br><b>(${addressBean.zoneBean.zoneName })</b>
																				</c:if>
																			</c:forEach>
																		</center>
																	</td>
																	<td class="td-middle">
																		<center>																
																			<c:if test="${serviceApplicationBean.status.stringValue eq 'A'}"><i class="fa fa-check-circle-o text-green" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ใช้งานปกติ"></i></c:if>
																			<c:if test="${serviceApplicationBean.status.stringValue eq 'I'}"><i class="fa fa-times-circle-o text-red" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ยกเลิกการใช้บริการ"></i></c:if>
																			<c:if test="${serviceApplicationBean.status.stringValue eq 'H'}"><i class="fa fa-hourglass-2 text-orange" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="รอมอบหมายงาน"></i></c:if>
																			<c:if test="${serviceApplicationBean.status.stringValue eq 'D'}"><i class="fa fa-pencil-square-o text-brown" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="แบบร่าง"></i></c:if>
																			<c:if test="${serviceApplicationBean.status.stringValue eq 'W'}"><i class="fa fa-wrench text-blue" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ระหว่างการติดตั้ง"></i></c:if>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'T'}"><i class="ion-record text-red" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ระงับชั่วคราว"></i></c:if> --%>
																		</center>
																	</td>
																	<td align="center" class="td-middle">
																	<perm:permission object="M25.view">
																	<a class="cursor-p" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id}">
																	<span class="ti-search" data-toggle="tooltip"
																			data-placement="bottom" title="ดูรายละเอียด">
																	</span></a> 
																	</perm:permission>
																	<perm:permission object="M25.edit">
																	<a class="cursor-p" href="${pageContext.request.contextPath}/serviceapplicationlist/edit/${serviceApplicationBean.id}"><span
																			class="ti-pencil-alt" data-toggle="tooltip"
																			data-placement="bottom" title=""
																			data-original-title="แก้ไข"></span></a>
																	</perm:permission>
																	<c:if test="${serviceApplicationBean.status.stringValue eq 'D'}">
																	<perm:permission object="M25.edit">
																	<a class="cursor-p" href="${pageContext.request.contextPath}/serviceapplicationlist/worksheetadd/${serviceApplicationBean.id}"><span
																			class="zmdi zmdi-assignment-o" data-toggle="tooltip"
																			data-placement="bottom" title=""
																			data-original-title="ออกใบงาน"></span></a>
																	</perm:permission>
																	</c:if>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="6"><center>ไม่พบข้อมูลใบสมัคร</center></td>
															</tr>
														</c:otherwise>
													</c:choose>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
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
		function getvalServiceType(type){
			if(type.value == "" || type.value == null){
				$('#servicePackage')
			    .empty()
			    .append("<option value='' selected='selected' disabled='disabled'>---บริการ ---</option>"+
			    		"<option value=''>ทั้งหมด</option>");
			}else{
				loadServicePackage(type.value);
			}
		}
	
		function loadServicePackage(id){
			
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplicationlist/loadServicePackage/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){					
					$('#servicePackage')
				    .empty()
				    .append("<option value='' selected='selected' disabled='disabled'>---บริการ ---</option>"+
				    		"<option value=''>ทั้งหมด</option>");
					
					for(var i=0;i<data["result"].length;i++){
						$('#servicePackage')
					    .append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["packageName"] +"</option>");
					}
				}else{
					$('#servicePackage')
				    .empty()
				    .append("<option value='' selected='selected' disabled='disabled'>---บริการ ---</option>"+
				    		"<option value=''>ทั้งหมด</option>");
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
		
	}
	</script>
</body>
</html>
