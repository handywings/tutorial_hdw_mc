<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="ShowModalChangeService" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายละเอียดการเปลี่ยนบริการ
					</h4>
				</div>

				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;<span class="ti-shopping-cart"></span> Package
												ที่เปลี่ยนใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">	
												<div class="col-md-2">
													<input type="hidden" id="perMonth_ismonthlyservice">
													<label for="exampleSelect1"><b>วันกำหนดชำระรอบบิล<span class="text-red"> *</span></b></label> 
												</div>
													<div class="col-md-5">
														<label style="padding-bottom: 5.5px;"
															class="custom-control custom-radio"> <input
															id="radio1" name="PaymentDate" type="radio" checked="checked"
															value="N" class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;กำหนดเอง</span>
														</label>&nbsp;&nbsp;&nbsp;
														
														<label class="custom-control custom-radio"> <input
															id="radio2" name="PaymentDate" type="radio" value="O"
															class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;วันครบกำหนดชำระเดิม</span>
														</label>
														<input type="text" class="form-control" name="billPaymentDate" path="billPaymentDate" 
													id="billPaymentDate" placeholder="วันครบรอบบิล" value="" style="width: 48%;">
														<div class="messages"></div>
														<div id="errorBillPaymentDate" style="display:none; margin-top:5px;">
															<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
																<button type="button" class="close" data-dismiss="alert" aria-label="Close">
<!-- 																<span aria-hidden="true">×</span> -->
																</button>
																<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือกวันครบรอบบิล
															</div>
														</div>
													</div>
											</div>
											<div class="row">
												<div class="col-md-4">
													<label for="exampleSelect1"><b>ประเภทบริการ<span class="text-red"> *</span></b></label> 
													<select class="form-control" id="select_service_type" onchange="getvalServiceType(this);">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกประเภทบริการ ---</option>
														<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
															<option value="${servicePackageTypeBean.id }">${servicePackageTypeBean.packageTypeName}</option>
														</c:forEach>
													</select>
													<div id="errorServicePackageType" style="display:none; margin-top:5px;">
														<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
															<button type="button" class="close" data-dismiss="alert" aria-label="Close">
<!-- 															<span aria-hidden="true">×</span> -->
															</button>
															<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือกประเภทบริการ
														</div>
													</div>
												</div>
												<div class="col-md-5">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ชื่อ Package<span class="text-red"> *</span></b></label> 
														<select class="form-control" id="service_packages">
														<option disabled="disabled" selected="selected">--- กรุณาเลือกประเภทบริการก่อน ---</option>
														</select>
														<div id="errorServicePackage" style="display:none; margin-top:5px;">
															<div class="alert alert-warning-outline alert-dismissible fade in" role="alert">
																<button type="button" class="close" data-dismiss="alert" aria-label="Close">
<!-- 																<span aria-hidden="true">×</span> -->
																</button>
																<strong><i class="fa fa-warning"></i> Warning !</strong> กรุณาเลือกประเภทบริการก่อน
															</div>
														</div>
													</div>
												</div>
												<div class="col-md-3">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>ประเภทใบสมัคร<span class="text-red"> *</span></b></label> 
														<select class="form-control" id="service_application_type">
														<c:forEach items="${serviceApplicationTypes}" var="serviceApplicationType"> 
															<c:if test="${serviceApplicationType.id != 4}">
															<option value="${serviceApplicationType.id }">${serviceApplicationType.serviceApplicationTypeName}</option>
															</c:if>
														</c:forEach>
														</select>
													</div>
												</div>
											</div>
											
											<div id="div-detail-package-ismonthlyservice" class="row">
												<div class="col-md-12">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>รายละเอียด Package</b></label>
														<div class="table-responsive">
															<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
<%-- 																		<th><center>ค่าติดตั้ง</center></th> --%>
<%-- 																		<th><center>ค่ามัดจำอุปกรณ์</center></th> --%>
<%-- 																		<th><center>รอบบิลแรกใช้ฟรี</center></th> --%>
																		<th><center>ส่วนลดรอบบิลแรก</center></th>
																		<th><center><font id="priceservice">ค่าบริการรายเดือน</font></center></th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
<!-- 																		<td scope="row"> -->
<!-- 																			<input type="hidden" id="perMonth_ismonthlyservice"> -->
<!-- 																			<div class="input-group"> -->
<!-- 																				<input type="text" class="form-control" style="text-align: center;" -->
<!-- 																					value="" id="installationFee_ismonthlyservice"> -->
<!-- 																				<div class="input-group-addon">บาท</div> -->
<!-- 																			</div></td> -->
<!-- 																		<td scope="row"><div class="input-group"> -->
<!-- 																				<input type="text" class="form-control" style="text-align: center;" -->
<!-- 																					value="" id="deposit_ismonthlyservice"> -->
<!-- 																				<div class="input-group-addon">บาท</div> -->
<!-- 																			</div></td> -->
<!-- 																		<td scope="row"><div class="input-group"> -->
<!-- 																				<input type="text" class="form-control"  style="text-align: center;" -->
<!-- 																					id="firstBillFree_ismonthlyservice"> -->
<!-- 																				<div class="input-group-addon">เดือน</div> -->
<!-- 																			</div></td> -->
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control" style="text-align: right;"
																					value="" id="firstBillFreeDisCount_ismonthlyservice">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control" style="text-align: right;"
																					value="" id="monthlyServiceFee_ismonthlyservice" disabled>
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											
											<div id="div-detail-package" class="row">
												<div class="col-md-12">
													<div class="form-group">
														<label for="exampleInputEmail1"><b>รายละเอียด Package</b></label>
														<div class="table-responsive">
															<table class="table table-bordered mb-0 table-hover">
																<thead class="thead-bg">
																	<tr>
																		<th><center>ค่าติดตั้ง</center></th>
																		<th><center>ค่ามัดจำอุปกรณ์</center></th>
																		<th><center>ค่าบริการ</center></th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="installationFee">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="deposit">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																		<td scope="row"><div class="input-group">
																				<input type="text" class="form-control"
																					value="" id="oneServiceFee">
																				<div class="input-group-addon">บาท</div>
																			</div></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											
										</fieldset>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="ok" >ตกลง</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
			</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript"> 
	$( document ).ready(function() {
		
		$("#div-detail-package-ismonthlyservice").hide();
		$("#div-detail-package").hide();
		$("#billPaymentDate").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
		});	
		
		$("#billPaymentDateSearch").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
		});
		
		$('#billPaymentDate').datepicker().on("input change", function (e) {
			$('#errorBillPaymentDate').hide();
		});
		
		$('#table-1').dataTable({searching: false, paging: true});
	});
	
	$('#ok').click(function () {
		var validate = true;
		if(!$('#select_service_type').val()){
			$('#errorServicePackageType').fadeIn();
			validate = false;
		}
		if(!$('#service_packages').val()){
			$('#errorServicePackage').fadeIn();
			validate = false;
		}
		if(!$('#billPaymentDate').val()){
			var PaymentDate = $("input[name='PaymentDate']:checked").val();
			if (PaymentDate == 'N') {
				$('#errorBillPaymentDate').fadeIn();
				validate = false;
			}
		}
		if(validate){
			$('#errorBillPaymentDate').hide();
			generateDataToJson();
		}
	});
	
// 	$('#billPaymentDateSearch').change(function(){
// 		console.log('billPaymentDateSearch');
// 		$('#errorBillPaymentDate').hide();
// 	});
	
	function generateDataToJson(){
		$('.preloader').modal('show');
		var serviceApplicationBeanList = [];
		$('#tableServiceApplication > tbody  > tr').each(function() {
			var serviceApplicationBean = {};
			$(this).find('input').each(function (i) {
				switch (i) {
						case 0:
							var checked = $(this).is(':checked');
							if(checked){
								serviceApplicationBean.id = $(this).val();
								serviceApplicationBean.firstBillFreeDisCount = $('#firstBillFreeDisCount_ismonthlyservice').val();
								serviceApplicationBean.monthlyServiceFee = $('#monthlyServiceFee_ismonthlyservice').val();
								serviceApplicationBean.perMonth = $('#perMonth_ismonthlyservice').val();
								serviceApplicationBean.monthlyService = true;
								
								var servicepackage = {};
								servicepackage.id = $('#service_packages').val();
								serviceApplicationBean.servicepackage = servicepackage;
								
								var serviceApplicationTypeBean = {};
								serviceApplicationTypeBean.id = $('#service_application_type').val();
								serviceApplicationBean.serviceApplicationTypeBean = serviceApplicationTypeBean;
								
								var PaymentDate = $("input[name='PaymentDate']:checked").val();
								if (PaymentDate == 'N') {
									var billPaymentDate = $("#billPaymentDate").val();
									serviceApplicationBean.orderBillDate = billPaymentDate;
								}
								
								
								serviceApplicationBeanList.push(serviceApplicationBean);
							}
							break;
						}
				
					});
			
		});
		console.log(serviceApplicationBeanList);
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/changeservice/change",
			dataType : 'json',
			async : false,
			data : JSON.stringify(serviceApplicationBeanList),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					window.location.href = "${pageContext.request.contextPath}/serviceapplicationlist";
				} else {
					$('.preloader').modal('hide');
					openAlert('warning',data);
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
	
	$('#service_packages').change(function () {
		$('#errorServicePackage').hide();
		var id = $(this).val();
		selectServiceChange(id);
	});
	
	$("input[name='PaymentDate']").change(function() {
		var PaymentDate = $("input[name='PaymentDate']:checked").val();
		if (PaymentDate == 'O') {
			$("#billPaymentDate").hide();
		} else if(PaymentDate == 'N'){
			$("#billPaymentDate").fadeIn();
		}
	});
	
	function selectServiceChange(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/getServicePackageDetail/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
// 				console.log(data);
				if(data.result.monthlyService===true){
					$('#priceservice').html("ค่าบริการรอบบิล / " + data.result.perMounth + " เดือน");
					$('#perMonth_ismonthlyservice').val(data.result.perMounth);
					$('#firstBillFreeDisCount_ismonthlyservice').val(data.result.firstBillFreeDisCount);
					$('#monthlyServiceFee_ismonthlyservice').val(data.result.monthlyServiceFee);
					
					$("#div-detail-package-ismonthlyservice").fadeIn();
					$("#div-detail-package").hide();
				}else{
					$("#div-detail-package-ismonthlyservice").hide();
					$("#div-detail-package").fadeIn();
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
	
	function searchServiceApplication(){
		$('.preloader').modal('show');
		var applicationSearchBean = {};
		applicationSearchBean.key = $('#c_addpoint_key').val();
		applicationSearchBean.custType = $('#c_addpoint_custType').val();
		applicationSearchBean.customerFeatures = $('#customerFeatures').val();
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/searchServiceApplication",
			dataType : 'json',
			async: false,
			data : JSON.stringify(applicationSearchBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var columnRadio = "<td><center>" +
						"<label class='custom-control custom-radio'>" + 
						"<input name='radio_search_serviceApp' type='radio'"+
						" value='"+ data["result"][i]["id"] +"'" +
						"class='custom-control-input'>" +
						"<span class='custom-control-indicator'></span>" +
						"<span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>";
						
						var columnServiceNo = "<td><center><a href='${pageContext.request.contextPath}/serviceapplicationlist/view/" + data["result"][i]["id"] + "' target='_blank'><b>"+ data["result"][i]["serviceApplicationNo"] +"</b></a></center></td>";
						var columnCustomer = "<td>"+ data["result"][i]["customer"]["firstName"] +"&nbsp;"+ data["result"][i]["customer"]["lastName"] +
						"<br> <font class='text-gray'><a href='${pageContext.request.contextPath}/customerregistration/view/"+ data["result"][i]["customer"]["id"] +"' target='_blank'>"+ data["result"][i]["customer"]["custCode"] +"</a></font> </td>";
						
						var columnServicePackage = "<td>"+ data["result"][i]["servicepackage"]["packageName"] +"<br>" + 
						"<font class='text-gray'>Package : " + data["result"][i]["servicepackage"]["packageCode"] + 
						"| ประเภทบริการ : "+ data["result"][i]["servicepackage"]["serviceType"]["packageTypeName"] +"</font></td>";
						
						var columnZone = "<td><center></center></td>";
						for(var j=0;j<data["result"][i]["addressList"].length;j++){
							if(data["result"][i]["addressList"][j].addressType == '3'){
								columnZone = "<td>" + data["result"][i]["addressList"][j].collectAddressDetail + "&nbsp;<b>(" + data["result"][i]["addressList"][j]["zoneBean"].zoneDetail + ")</b>" + "<br><font style='color: gray;'>" + data["result"][i]["customer"]["customerFeatureBean"].customerFeatureName + "</font>" + "</td>";
							}
						} 
						
						rows = rows + startRow+columnRadio+columnServiceNo+columnCustomer;
						rows = rows + columnServicePackage+columnZone+endRow;
					}
					
					//var emptyData = "";
					if(rows.length > 0){
						$('#table-1 > tbody').html(rows);
					}else{
						$('#table-1 > tbody').empty();
					}
					
					setTimeout(
						function() 
							  {
							$('.preloader').modal('hide');
							$('#searchServiceApp').css('overflow-y', 'auto');
							  }, 200);
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
	
// 	function resetSearchServiceApplication(){
// 		$('#c_addpoint_key').val("");
// 		$("#c_addpoint_custType").prop("selectedIndex", 0);
// 		$("#customerFeatures").prop("selectedIndex", 0);
// 		searchProcess();
// 	}
</script>