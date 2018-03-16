<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="customer" scope="request"/>
<c:set var="subMenu" value="changeservice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>เปลี่ยนบริการ</title>
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
								<li class="breadcrumb-item active">เปลี่ยนบริการ</li>
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
									<form:form method="post" commandName="applicationSearchBean" action="${pageContext.request.contextPath}/changeservice/search">
										<div class="col-md-5 mb05">
											<input type="text" name="key" class="form-control" value="${applicationSearchBean.key }" path="key"
												placeholder="ระบุ เลขใบสมัคร ชื่อสกุล บ้านเลขที่ติดตั้ง อีเมล์ หรือหมายเลขโทรศัพท์ ของลูกค้า...">
										</div>
<!-- 										<div class="col-md-3 mb05"> -->
<!-- 											<select id="servicePackageType" name="servicePackageType" class="form-control" path="servicePackageType" onchange="getvalServiceType(this);"> -->
<!-- 												<option value="0" selected="selected">ประเภทบริการทั้งหมด</option> -->
<%-- 												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean">  --%>
<%-- 													<option value="${servicePackageTypeBean.id }" <c:if test="${applicationSearchBean.servicePackageType eq servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName }</option> --%>
<%-- 												</c:forEach> --%>
<!-- 											</select> -->
<!-- 										</div> -->
										<div class="col-md-3 mb05">
											<select id="servicePackage" name="servicePackage" class="form-control" path="servicePackage">
												<option value="0">-- รายการแพคเกจบริการทั้งหมด --</option>
												<c:forEach items="${servicePackageBeans}" var="servicePackageBean">
 													<option value="${servicePackageBean.id }" <c:if test="${applicationSearchBean.servicePackage eq servicePackageBean.id}">selected="selected"</c:if> >${servicePackageBean.packageName }</option>
 												</c:forEach>
 											</select>
										</div>
										<div class="col-md-2 mb05">
										<input type="text" class="form-control" name="billPaymentDateSearch" path="billPaymentDateSearch" 
													id="billPaymentDateSearch" placeholder="วันครบรอบบิล" value="${applicationSearchBean.billPaymentDateSearch }" >
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
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการใบสมัครที่สามารถเปลี่ยนบริการได้</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<div class="col-md-12">
										<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
									</div>
									
<!-- 									<div class="col-md-10"> -->
<!-- 									<span class="pull-right">  -->
<!-- 										<i class="fa fa-times-circle-o text-red"></i>&nbsp;<font style="color: gray;">แจ้งยกเลิกใช้บริการ</font> -->
<!-- 										&nbsp;|&nbsp;<i class="ti-reload" style="color: #0275d8;"></i>&nbsp;<font style="color: gray;">เปลี่ยนบริการ</font> -->
<!-- 									</span> -->
<!-- 									</div> -->
								</div>
								<div class="errorSN" style="display:none">
									<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
										<button type="button" class="close" data-dismiss="alert" aria-label="Close">
<!-- 										<span aria-hidden="true">×</span> -->
										</button>
										<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือกใบสมัคร
									</div>
								</div>
								<c:if test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
									<perm:permission object="M27.add" >
										<div class="row mt05 mb15">
											<div class="col-md-12">
													<button type="button" onclick="ShowModalChangeService();" class="btn btn-info label-left mr-0-5">
														<span class="btn-label"><span class="ti-reload"></span></span>
														<b>เปลี่ยนบริการ</b>
													</button>
											</div>								
										</div>
									</perm:permission>
								</c:if>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table id="tableServiceApplication" class="table table-bordered mb-0 table-hover">
												<thead class="thead-default">
													<tr>
														<th width="5%"><center><label class="custom-control custom-checkbox">
																<input type="checkbox" id="select_all"
																class="custom-control-input"> <span
																class="custom-control-indicator"></span> <span
																class="custom-control-description"></span>
														</label></center>
														</th>
														<th width="25%">ข้อมูลลูกค้า</th>
														<th width="20%">ข้อมูลบริการที่สมัคร</th>
														<th width="20%">วันกำหนดชำระรอบบิล</th>
														<th width="30%"><center>เขตชุมชน / โครงการที่ติดตั้ง</center></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
															<c:forEach var="serviceApplicationBean" items="${pagination.dataListBean}" varStatus="i">
																<tr>
																	<td><center><label class="custom-control custom-checkbox">
																			<input type="checkbox"
																			class="custom-control-input checkbox_ser"
																			value="${serviceApplicationBean.id}"> <span
																			class="custom-control-indicator"></span> <span
																			class="custom-control-description"></span>
																	</label></center></td>
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
																	<td>${serviceApplicationBean.worksheetSetup.dateOrderBillTh}</td>
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
<!-- 																	<td align="center" class="td-middle"> -->
<%-- 																	<perm:permission object="M27.add" > --%>
<%-- 																		<a href="${pageContext.request.contextPath}/changeservice/view/${serviceApplicationBean.id}"><span --%>
<!-- 																				class="fa fa-times-circle-o text-red" data-toggle="tooltip" -->
<!-- 																				data-placement="bottom" title="" -->
<!-- 																				data-original-title="แจ้งยกเลิกใช้บริการ"></span></a>&nbsp;&nbsp; -->
<%-- 																		<a href="${pageContext.request.contextPath}/changeservice/edit/${serviceApplicationBean.id}"><span --%>
<!-- 																				class="ti-reload" data-toggle="tooltip" -->
<!-- 																				data-placement="bottom" title="" -->
<!-- 																				data-original-title="เปลี่ยนบริการ"></span></a> -->
<%-- 																	</perm:permission> --%>
<!-- 																	</td> -->
																</tr>
															</c:forEach>
														</c:when>
														<c:otherwise>
															<tr>
																<td colspan="5"><center>ไม่พบข้อมูลใบสมัคร</center></td>
															</tr>
														</c:otherwise>
													</c:choose>
													
												</tbody>
											</table>
										</div>

									</div>
								</div>
								<c:if test="${pagination.dataListBean != null && pagination.dataListBean.size() > 0 }">
									<perm:permission object="M27.add" >
										<div class="row mt05 mb15">
											<div class="col-md-12">
													<button type="button" onclick="ShowModalChangeService();" class="btn btn-info label-left mr-0-5">
														<span class="btn-label"><span class="ti-reload"></span></span>
														<b>เปลี่ยนบริการ</b>
													</button>
											</div>
										</div>
									</perm:permission>
								</c:if>
								<jsp:include page="../layout/paging.jsp"></jsp:include>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- modal_changeservice -->
			<jsp:include page="modal_changeservice.jsp"></jsp:include>
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
	
	$("#select_all").click(function() {
		$(".checkbox_ser").prop('checked', $(this).prop("checked"));
	});
	
	function ShowModalChangeService(){
		if(validate()){
			$('#ShowModalChangeService').modal('show');
			$('.errorSN').hide();
		}else{
			$('html, body').animate({ scrollTop: $('#table-1_length').offset().top }, 'slow');
			$('.errorSN').fadeIn();
		}
	}
	
	function validate(){
		var serviceApplicationBeanList = [];
		var isValidate = false;
		$('#tableServiceApplication > tbody  > tr').each(function() {
			var serviceApplicationBean = {};
			$(this).find('input').each(function (i) {
				switch (i) {
						case 0:
							var checked = $(this).is(':checked');
							if(checked){
								isValidate = true;
							}
							break;
						}
				});
		});
		return isValidate;
	}
	
		function getvalServiceType(type){
			$('#errorServicePackageType').hide();
			if(type.value == "" || type.value == null){
				$('#service_packages')
			    .empty()
			    .append("<option value='' selected='selected' disabled='disabled'>--- กรุณาเลือกประเภทบริการก่อน ---</option>");
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
// 					console.log(data);
					$('#service_packages')
				    .empty()
				    .append("<option value='' selected='selected' disabled='disabled'>---กรุณาเลือก Package ---</option>");
					
					for(var i=0;i<data["result"].length;i++){
						if(data["result"][i]["monthlyService"]===true){
							$('#service_packages')
					    	.append("<option value='"+ data["result"][i]["id"] +"'>"+ data["result"][i]["packageName"] +"</option>");
						}
					}
					
					$("#div-detail-package-ismonthlyservice").hide();
					$("#div-detail-package").hide();
					
				}else{
					$('#service_packages')
				    .empty()
				    .append("<option value='' selected='selected' disabled='disabled'>--- กรุณาเลือกประเภทบริการก่อน ---</option>");
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
