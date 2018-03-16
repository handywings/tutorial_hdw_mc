<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="cancelservice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>แจ้งยกเลิกใช้บริการ</title>
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
						<div class="col-md-12">
							<h3>แจ้งยกเลิกใช้บริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบลูกค้าสัมพันธ์</li>
								<li class="breadcrumb-item active">แจ้งยกเลิกใช้บริการ</li>
							</ol>
						</div>
					</div>
					<div class="card">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบสมัครใช้บริการ
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="applicationSearchBean" action="${pageContext.request.contextPath}/cancelservice/search">
										<div class="col-md-6 mb05">
											<input type="text" name="key" class="form-control" value="${applicationSearchBean.key }" path="key"
												placeholder="ระบุ เลขใบสมัคร ชื่อสกุล บ้านเลขที่ติดตั้ง อีเมล์ หรือหมายเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-4 mb05">
											<select id="servicePackageType" name="servicePackageType" class="form-control" path="servicePackageType" onchange="getvalServiceType(this);">
												<option value="0" selected="selected">ประเภทบริการทั้งหมด</option>
												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
													<option value="${servicePackageTypeBean.id }" <c:if test="${applicationSearchBean.servicePackageType eq servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName }</option>
												</c:forEach>
											</select>
										</div>
<!-- 										<div class="col-md-3"> -->
<!-- 											<select id="servicePackage" name="servicePackage" class="form-control" path="servicePackage"> -->
<!-- 												<option value="" selected="selected" disabled="disabled">---บริการ ---</option> -->
<!-- 												<option value="">ทั้งหมด</option> -->
<%-- 												<c:forEach items="${servicePackageBeans}" var="servicePackageBean">  --%>
<%-- 													<option value="${servicePackageBean.id }" <c:if test="${applicationSearchBean.servicePackage eq servicePackageBean.id}">selected="selected"</c:if> >${servicePackageBean.packageName }</option> --%>
<%-- 												</c:forEach> --%>
<!-- 											</select> -->
<!-- 										</div> -->
<!-- 										<div class="col-md-2"> -->
<!-- 											<select id="status" name="status" class="form-control" path="status"> -->
<!-- 												<option value="" disabled="disabled" selected="selected">---สถานะการใช้งาน ---</option> -->
<!-- 												<option value="">ทั้งหมด</option> -->
<%-- 												<option value="D" <c:if test="${applicationSearchBean.status eq 'D'}">selected="selected"</c:if> >แบบร่าง</option> --%>
<%-- 												<option value="H" <c:if test="${applicationSearchBean.status eq 'H'}">selected="selected"</c:if> >รอมอบหมายงาน</option> --%>
<%-- 												<option value="A" <c:if test="${applicationSearchBean.status eq 'A'}">selected="selected"</c:if> >ใช้งานปกติ</option> --%>
<%-- 												<option value="I" <c:if test="${applicationSearchBean.status eq 'I'}">selected="selected"</c:if> >ยกเลิกการใช้บริการ</option> --%>
<%-- 												<option value="W" <c:if test="${applicationSearchBean.status eq 'W'}">selected="selected"</c:if> >ระหว่างการติดตั้ง</option> --%>
<!-- 											</select> -->
<!-- 										</div> -->
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
					<div class="card">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการใบสมัครที่สามารถแจ้งยกเลิกใช้บริการได้</h4>
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
														<th width="20%">ข้อมูลลูกค้า</th>
														<th width="30%">ข้อมูลบริการที่สมัคร</th>
														<th width="30%"><center>เขตชุมชน / โครงการที่ติดตั้ง</center></th>
														<th width="10%"><center>แจ้งยกเลิก</center></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
															<c:forEach var="serviceApplicationBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td>
																	<a class="cursor-p" target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>${serviceApplicationBean.serviceApplicationNo}</b></a><br>
																	${serviceApplicationBean.customer.prefix}${serviceApplicationBean.customer.firstName}&nbsp;
																	${serviceApplicationBean.customer.lastName}<br>
																	<small style="color: gray;">
																	<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${serviceApplicationBean.customer.custCode }</a><br>
																	<b>Email&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.email}<br>
																	<b>โทร&nbsp;:&nbsp;</b>${serviceApplicationBean.customer.contact.mobile}
																	</small>
																	</td>
																	<td>${serviceApplicationBean.servicepackage.packageName}<br>
																	<font class="text-gray"><b>รหัส Package :</b> ${serviceApplicationBean.servicepackage.packageCode}<br><b>ประเภทบริการ :</b> ${serviceApplicationBean.servicepackage.serviceType.packageTypeName}</font></td>
																	<td class="td-middle">
																		<center>
																			<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																				<c:if test="${addressBean.addressType == 3}">																			
																					${addressBean.collectAddressDetail } <br><b>(${addressBean.zoneBean.zoneDetail })</b>
																					<br>
																					<font style="color: gray;">ใกล้เคียงกับ ${addressBean.nearbyPlaces }</font>
																				</c:if>
																			</c:forEach>
																		</center>
																	</td>
		<!-- 															<td class="td-middle"> -->
		<%-- 																<center> --%>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'A'}">ใช้งานปกติ</c:if> --%>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'I'}">ยกเลิกการใช้บริการ</c:if> --%>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'H'}">รอมอบหมายงาน</c:if> --%>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'D'}">แบบร่าง</c:if> --%>
		<%-- 																	<c:if test="${serviceApplicationBean.status.stringValue eq 'W'}">ระหว่างการติดตั้ง</c:if> --%>
		<%-- 																</center> --%>
		<!-- 															</td> -->
																	<td align="center" class="td-middle">
																	<perm:permission object="M27.add" >
																		<a href="${pageContext.request.contextPath}/cancelservice/view/${serviceApplicationBean.id}">
																			<button type="button" class="btn btn-danger label-left mr-0-5">
																				<span class="btn-label"><i class="ti-close"></i></span>แจ้งยกเลิกใช้บริการ
																			</button>
																		</a>
																	</perm:permission>
																	</td>
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="4"><center>ไม่พบข้อมูลใบสมัคร</center></td>
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
			url : "${pageContext.request.contextPath}/changeservice/loadServicePackage/"+id,
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
