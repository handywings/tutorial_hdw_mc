<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="generatemanualinvoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>สร้างใบแจ้งหนี้ตามรอบบิล</title>
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
							<h3>สร้างใบแจ้งหนี้ตามรอบบิล</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">สร้างใบแจ้งหนี้ตามรอบบิล</li>
							</ol>
						</div>
					</div>
					<div class="card mb05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหารอบบิล
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="frmSeachOrderBill" method="post" commandName="orderBillSearchBean" action="${pageContext.request.contextPath}/generatemanualinvoice/search">
										<div class="col-md-8 mb05">
											<input type="text" id="key" name="key" path="key" class="form-control" value="${orderBillSearchBean.key }"
												placeholder="ระบุ ชื่อ สกุล เลขประชาชน อีเมล์ หรือเลขโทรศัพท์ ของลูกค้า...">
										</div>
										<div class="col-md-4 mb05">
											<select id="servicePackageTypeId" name="servicePackageTypeId" path="servicePackageTypeId" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">--- ประเภทบริการทั้งหมด ---</option>
												<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean">
													<option value="${servicePackageTypeBean.id }" <c:if test="${orderBillSearchBean.servicePackageTypeId == servicePackageTypeBean.id}">selected="selected"</c:if> >${servicePackageTypeBean.packageTypeName}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-5 mb05">
											<input type="text" class="form-control" name="orderBillDate" path="orderBillDate" 
												id="orderBillDate" placeholder="วันครบรอบบิล" value="${orderBillSearchBean.orderBillDate }">
										</div>
										<div class="col-md-5 mb05">
											<select id="zone" name="zone" path="zone" class="form-control" data-plugin="select2">
												<option value="0" selected="selected">--- เขตชุมชน / โครงการทั้งหมด ---</option>
												<c:forEach items="${zoneBeans}" var="zoneBean">
													<option value="${zoneBean.id }" <c:if test="${orderBillSearchBean.zone == zoneBean.id}">selected="selected"</c:if> >${zoneBean.zoneDetail } (${zoneBean.zoneName })</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-block btn-lg bg-instagram col-xs-12 b-a-0 waves-effect waves-light">
												<span class="ti-search"></span>
												ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mb40">
						<div class="card-header text-uppercase">
							<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp; รายการรอบบิล</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
								<div class="row">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover">
												<thead class="thead-bg">
													<tr>
														<th><center>
																<label class="custom-control custom-checkbox"> <input
																	type="checkbox" value="" id="orderBillCheckAll"
																	class="custom-control-input"> <span
																	class="custom-control-indicator"></span> <span
																	class="custom-control-description"></span>
																</label>
															</center></th>
														<th>ชื่อลูกค้า</th>
														<th><center>ประเภทบริการ</center></th>
														<th>เขตชุมชน / โครงการ</th>
														<th><center>ประเภทใบแจ้งหนี้</center></th>
														<th><center>ยอดเงิน (บาท)</center></th>
														<th><center>วันที่นัดชำระ</center></th>
													</tr>
												</thead>
												<tbody>
													<c:choose>
														<c:when test="${pagination.dataListBean != null && pagination.dataListBean.size()>0}">
															<c:forEach var="serviceApplicationBean" items="${pagination.dataListBean}" varStatus="i">
														<tr class="trOrderBill">
															<td class="td-middle">
																<center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox" value="${serviceApplicationBean.id }"
																		onclick="sendValueToJson(${serviceApplicationBean.id })"
																		class="custom-control-input OrderBillCheckbox"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center>
															</td>
															<td class="td-middle">
															<a class="cursor-p" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id}" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด" target="_blank"><b>${serviceApplicationBean.serviceApplicationNo }</b></a><br>
															<b>${serviceApplicationBean.customer.firstName } ${serviceApplicationBean.customer.lastName }</b>
															<br> <font style="color: gray;"> รหัสลูกค้า <a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank" data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด">${serviceApplicationBean.customer.custCode }</a>
															</font></td>
															<td class="td-middle">
																${serviceApplicationBean.servicepackage.serviceType.packageTypeName}
															</td>
															<td class="td-middle">
																<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																	${addressBean.collectAddressDetail }<br>
																	<b>(${addressBean.zoneBean.zoneDetail })</b><small style="color: gray;">&nbsp;${serviceApplicationBean.customer.customerFeatureBean.customerFeatureName}</small>																	
																</c:forEach>
															</td>
															<td class="td-middle"><center>
																	ใบแจ้งหนี้ค่าบริการตามรอบบิล
																</center></td>
															<td class="td-middle"><center>
																	${serviceApplicationBean.servicepackage.monthlyServiceFee } บาท
																</center></td>
															<td class="td-middle"><center>
															${serviceApplicationBean.worksheetSetup.dateOrderBillTh }
															</center></td>
														</tr>
													</c:forEach>
														</c:when>
														<c:otherwise>
															<tr><td colspan="7"><center>ไม่พบข้อมูล</center></td></tr>
														</c:otherwise>
													</c:choose>
													
												</tbody>
											</table>
										</div>
										<jsp:include page="../layout/paging.jsp"></jsp:include>
										<center class="text-grey mt15">
												<perm:permission object="M36.add" >
													<button type="button" onclick="createOrderBill()" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
														<span class="btn-label"><span class="ti-files"></span></span>
														<b>สร้างใบบิลแจ้งชำระเงิน</b>
													</button>
												</perm:permission>
											</center>
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
	
	<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<jsp:include page="../layout/script.jsp"></jsp:include>
	    <script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
	
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	<script type="text/javascript">
	jsonChecked = [];
	function sendValueToJson(idApplication){
		var flagHas = false;
		if(jsonChecked.length > 0){
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] == idApplication){
					flagHas = true;
				}
			}
		}else{
			var flagHas = false;
		}
		if(flagHas){
			newJsonChecked = [];
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] != idApplication){
					newJsonChecked.push(jsonChecked[i]);
				}
			}
			//update json
			jsonChecked = newJsonChecked;
		}else{
			jsonChecked.push(idApplication);
		}
		
// 		for(i=0;i<jsonChecked.length;i++){
// 		      console.log(jsonChecked[i]);
// 		}
	}
	
	function checkAll(typecheck){
		jsonChecked = [];
		$('.OrderBillCheckbox').each(function (index, value) {
			if(typecheck){
				$(this).prop( "checked", true );
				sendValueToJson($(this).val());
			}else{
				$(this).prop( "checked", false );
			}
		});
	}
	
	$( document ).ready(function() {
		//set date picker
		$("#orderBillDate").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
		});	
		
		$('#orderBillCheckAll').change(function() {
	        if($(this).is(':checked')){
	        	checkAll(true);
	        }else{
	        	checkAll(false);
	        }
	        
	    });
	});
	
	function resetSearch(){
		$('#key').val("");
		$('#orderBillDate').val("");
		$('#zone').prop("selectedIndex", 0);
		$('#frmSeachOrderBill').submit();
	}
	
	function createOrderBill(){
		//$('.preloader').modal('show');
		var jsonRequestOrderBillList = [];
		for(var i=0;i<jsonChecked.length;i++){
			var serviceApplicationBean = {};
			var listOrder = {};
			serviceApplicationBean.id = jsonChecked[i];
			listOrder.serviceApplicationBean = serviceApplicationBean;
			listOrder.orderBillDate = $('#orderBillDate').val();
			jsonRequestOrderBillList.push(listOrder);
		}
// 		for(i=0;i<JsonRequestOrderBillList.length;i++){
// 		      console.log('val : ' + JsonRequestOrderBillList[i]["id"]);
// 		}
		
		//send assign worksheet
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/generatemanualinvoice/createManualInvoice",
			dataType : 'json',
			async: false,
			data : JSON.stringify(jsonRequestOrderBillList),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					window.location.reload();
					//$('#frmSeachWorksheet').submit();
				}else{
					
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
