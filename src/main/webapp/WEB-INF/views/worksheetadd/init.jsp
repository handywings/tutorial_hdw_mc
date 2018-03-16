<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="worksheet" scope="request"/>
<c:set var="subMenu" value="worksheetadd" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>เพิ่มใบงาน</title>
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
						<h3>เพิ่มใบงาน</h3>
						<ol class="breadcrumb no-bg mb-1">
							<li class="breadcrumb-item active">ระบบจัดการข้อมูลงาน</li>
							<li class="breadcrumb-item active">เพิ่มใบงาน</li>
						</ol>
					</div>
					<div class="col-md-4 mt05" align="right">
						<a data-toggle="modal" data-target="#searchServiceApp"><button
								type="button"
								class="btn btn-info label-left float-xs-right mr-0-5">
								<span class="btn-label"><i class="ti-search"></i></span>ค้นหาทะเบียนลูกค้า
							</button></a>
					</div>
					 <jsp:include page="modal_search_customer.jsp"></jsp:include>
				</div>

				<div class="card mt05">
					<div class="card-header text-uppercase">
						<div class="row">
							<div class="col-md-12">
								<h4><i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ทะเบียนประวัติสมาชิก</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div id="cusinfoNone">
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center class="text-grey">กรุณากดปุ่มค้นหาทะเบียนลูกค้า
										เพื่อทำการสร้างใบงาน</center>
									</div>
								</div>
							</div>
							<div id="cusinfo" style="display: none;">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลลูกค้า&nbsp;&nbsp;&nbsp;</legend>
											<div id="oldcustomers">
												<div class="row ">
													<div class="col-md-2">
														<input type="hidden" id="idServiceApplication" />
														<b>รหัสลูกค้า</b><br><b><span id="custCode"></span></b>
													</div>
													<div class="col-md-3">
														<b>ชื่อลูกค้า</b><br><span id="custName"></span>
													</div>
													<div class="col-md-2">
														<b>เลขที่บัตรประชาชน</b><br><span id="custIden"></span>
													</div>
													<div class="col-md-2">
														<b>ประเภทลูกค้า</b><br><span id="custType"></span>
													</div>
													<div class="col-md-3">
														<b>ลักษณะของลูกค้า</b><br><span id="custFeature"></span>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-2">
														<b>อาชีพ</b><br><span id="custCareer"></span>
													</div>
													<div class="col-md-3">
														<b>เบอร์โทรติดต่อ</b><br><span id="custMobile"></span>
													</div>
													<div class="col-md-2">
														<b>Fax</b><br><span id="custFax"></span>
													</div>
													<div class="col-md-5">
														<b>E-mail</b><br><span id="custEmail"></span>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-7">
														<b>ที่อยู่</b><br><span id="custAddress"></span>
													</div>
													<div class="col-md-5">
														<b>สถานที่ใกล้เคียง</b><br><span id="custNearby"></span>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt05">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;Package
												ที่สมัครใช้บริการ&nbsp;&nbsp;&nbsp;</legend>
											<div class="row ">
												<div class="col-md-3">
													<b>เลขที่ใบสมัคร</b><br><span id="custAppNo"></span>
												</div>
												<div class="col-md-3">
													<b>วันที่สมัคร</b><br><span id="custAppCreatedDate"></span>
												</div>
												<div class="col-md-3">
													<b>สถานะใบสมัคร</b><br> 
													<div id="custAppStatus"></div>
												</div>
												<div class="col-md-3">
													<b>ประเภทบริการ</b><br>
													<div id="custPackageType"></div>
												</div>

											</div>
											<div class="row">

												<div class="col-md-3">
													<b>รหัส Package</b><br><span id="custPackageCode"></span>
												</div>
												<div class="col-md-8">
													<div class="form-group">
														<b>ชื่อ Package</b><br><span id="custPackageName"></span>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12"></div>
											</div>
										</fieldset>
									</div>
								</div>
							</div>

						</div>
					</div>
				</div>
				<div class="card mt05" id="invoiceCard" style="display: none;">
					<div class="card-header text-uppercase">
						<div class="row">
							<div class="col-md-12">
								<h4><i class="ti-receipt"></i>&nbsp;&nbsp;การชำระเงิน</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-4">
										<b>รอบบิลคงค้าง</b>
									</div>
								</div>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="tbOverBill">
												<thead class="thead-bg">
													<tr>
														<th><center>เลขที่ใบแจ้งหนี้</center></th>
														<th><center>รายละเอียด</center></th>
														<th><center>กำหนดการชำระเงิน</center></th>
														<th><center>ยอดชำระ</center></th>
														<th><center>สถานะ</center></th>
													</tr>
												</thead>
												<tbody>
<!-- 													<tr> -->
<%-- 														<td class="td-middle"><center>00000290</center></td> --%>
<%-- 														<td><center>ค่าสมาชิกรายเดือน (2 ธันวาคม --%>
<%-- 																2559 - 2 มกราคม 2560)</center></td> --%>
<%-- 														<td><center>10 มกราคม 2560</center></td> --%>
<%-- 														<td><center>400.00 บาท</center></td> --%>
<%-- 														<td><center>ค้างชำระ</center></td> --%>
<!-- 													</tr> -->

												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4">
										<b>รอบบิลปัจจุบัน</b>
									</div>
								</div>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="tbCurrentBill">
												<thead class="thead-bg">
													<tr>
														<th><center>เลขที่ใบแจ้งหนี้</center></th>
														<th><center>รายละเอียด</center></th>
														<th><center>กำหนดการชำระเงิน</center></th>
														<th><center>ยอดชำระ</center></th>
														<th><center>สถานะ</center></th>
													</tr>
												</thead>
												<tbody>
<!-- 													<tr> -->
<%-- 														<td class="td-middle"><center>00000290</center></td> --%>
<%-- 														<td><center>ค่าสมาชิกรายเดือน (2 ธันวาคม --%>
<%-- 																2559 - 2 มกราคม 2560)</center></td> --%>
<%-- 														<td><center>10 มกราคม 2560</center></td> --%>
<%-- 														<td><center>400.00 บาท</center></td> --%>
<%-- 														<td><center>ค้างชำระ</center></td> --%>
<!-- 													</tr> -->

												</tbody>
											</table>
										</div>

									</div>
								</div>
							</div>
						</div>
				</div>
				<div class="card mt05" id="jobform" style="display: none;">
					<div class="card-header text-uppercase">
						<div class="row">
							<div class="col-md-12">
								<h4><i class="ti-clipboard"></i>&nbsp;&nbsp;ใบงาน</h4>
							</div>
						</div>
					</div>
					<div class="posts-list posts-list-1">
						<div class="pl-item">
							<div class="row">
								<div class="col-md-4">
									<label for="exampleInputEmail1"><b>วันเวลาที่ลูกค้าสะดวก<span class="text-red"> *</span></b></label>
									<div class="input-group">
										<input type="text" class="form-control"id="daterange1" name="daterange1">
										<div class="input-group-addon"><i class="fa fa-calendar-o" style="margin: 0;"></i></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="mb-1">
										<label for="jobType"><b>รูปแบบใบงาน</b></label> <select
											id="jobType" class="form-control">
											<option value="" selected="selected" disabled="disabled">--- เลือกรูปแบบใบงาน ---</option>
											<option value="1">เสริมจุดบริการ</option>
<!-- 											<option value="2">การจั้มสาย</option> -->
											<option value="3">การจูน TV</option>
											<option value="4">การซ่อมสัญญาณ</option>
											<option value="5">ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
											<option value="6">การย้ายจุด</option>
											<option value="7">การลดจุด</option>
											<option value="8">การตัดสาย</option>
											<option value="9">การย้ายสาย</option>
											<option value="10">แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</option>
											<option value="11">วิเคราะห์ปัญหา</option>
										</select>
									</div>
								</div>
							</div>
							
							<!-- include page worksheet type -->	
 							<jsp:include page="cable/addpoint.jsp"></jsp:include>
							<jsp:include page="cable/connect.jsp"></jsp:include>
							<jsp:include page="cable/tune.jsp"></jsp:include>
							<jsp:include page="cable/repair.jsp"></jsp:include>
							<jsp:include page="cable/addsettop.jsp"></jsp:include>
							<jsp:include page="cable/movepoint.jsp"></jsp:include>
							<jsp:include page="cable/reducepoint.jsp"></jsp:include>
							<jsp:include page="cable/cut.jsp"></jsp:include>
							<jsp:include page="cable/move.jsp"></jsp:include>
							<jsp:include page="cable/borrow.jsp"></jsp:include>
							<jsp:include page="cable/analyzeproblems.jsp"></jsp:include>
							 <!-- End include page worksheet type -->
							 <!-- modal -->
							 <jsp:include page="modal_search_equipment.jsp"></jsp:include>
							 <jsp:include page="modal_search_equipment_borrow.jsp"></jsp:include>
							 <jsp:include page="modal_search_equipment_repair.jsp"></jsp:include>
							 <!-- End modal -->
						</div>
					</div>
				</div>
				<div class="row mt15 mb15" id="btnSave" style="display: none;">
					<div class="col-md-4"></div>
					<div class="col-md-4">
						<center>
							<button type="button" onclick="saveWorksheet();" class="btn btn-success b-a-0 waves-effect waves-light btn-block btn-lg">
								<span class="ti-pencil-alt"></span>
								ยืนยันสร้างใบงาน
							</button>
						</center>
					</div>
				</div>
				<div class="row mb40"><div class="col-md-12"></div></div>
			</div>
		</div>
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
		var disabledDropdownWorksheetType = [];
		
		$('input[name="daterange1"]').daterangepicker({
			timePicker: true,
			timePicker24Hour: true,
//	 		timePickerIncrement: 30,
			locale: {
				format: 'DD/MM/YYYY HH:mm'
			}
		});
		
		// >>> กรณีออกใบงานตัดสายที่มาจากหน้า แจ้งยกเลิกใช้บริการ
		<c:if test="${not empty serviceapplicationId}">
			loadApplicationDetail("${serviceapplicationId}");
			generateDropdownTypeCable();
			<c:if test="${jobType eq 'cutWorksheet' }">
				loadCut();
				$(".job").hide();
				$("#cusinfoNone").hide();
				$('#cusinfo').fadeIn();
				$('#jobform').fadeIn();
				$('#invoiceCard').fadeIn();
				$('#btnSave').fadeIn();
				$('#job8').fadeIn();
				$('#jobType').val('8');
			</c:if>
		</c:if>
		// >>>
		
		$('#jobType').change(function() {
			
			var workSheetType = this.value;
			switch (parseInt(workSheetType)) {
			case 1:
				loadAddPoint();
				break;
			case 2:
				loadConnect();
				break;
			case 3:
				loadTune();
				break;
			case 4:
				loadRepair();
				break;
			case 5:
				loadAddSettop();
				break;
			case 6:
				loadMovePoint();
				break;
			case 7:
				loadReducePoint();
				break;
			case 8:
				loadCut();
				break;
			case 9:
				loadMove();
				break;
			case 10:
				loadBorrow();
				break;
			case 11:
				loadAnalyzeProblems();
				break;
			}
			
			$(".job").hide();
			$('#job' + this.value).fadeIn();
		});
		
		$('#ok').click(function() {
			$('.preloader').modal('show');
			//load data
			var radioApplication = $("input:radio[name ='radio_search_serviceApp']:checked").val();
			if(typeof radioApplication != 'undefined'){
				loadApplicationDetail(radioApplication);
				$(".job").hide();
				$("#cusinfoNone").hide();
				$('#cusinfo').fadeIn();
				$('#jobform').fadeIn();
				$('#invoiceCard').fadeIn();
				$('#btnSave').fadeIn();
				//ต้อง check package type
				generateDropdownTypeCable();
			}
		});
		
		//case service type cable
		function generateDropdownTypeCable(){
			$('#jobType')
		    .empty()
		    .append("<option value='' selected='selected' disabled='disabled'>--- เลือกรูปแบบใบงาน ---</option>")
		    .append("<option value='1'>เสริมจุดบริการ</option>")
// 		    .append("<option value='2'>การจั้มสาย</option>")
		    .append("<option value='3'>การจูน TV</option>")
		    .append("<option value='4'>การซ่อมสัญญาณ</option>")
		    .append("<option value='5'>ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</option>")
		    .append("<option value='6'>การย้ายจุด</option>")
		    .append("<option value='7'>การลดจุด</option>")
		    .append("<option value='8'>การตัดสาย</option>")
		    .append("<option value='9'>การย้ายสาย</option>")
		    .append("<option value='10'>แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</option>")
			.append("<option value='11'>วิเคราะห์ปัญหา</option>");
			
			for(var i in disabledDropdownWorksheetType)
			{
			  var key = disabledDropdownWorksheetType[i];
			  $("#jobType > option").each(function() {
				  if(key == this.value){
					  $(this).prop('disabled', true);
				  }
				});
			}
		}
		
		//case service type internet
		function genereateDropdownTypeInternet(){
			
		}
		
		//save worksheet
		function saveWorksheet(){
			var workSheetType = $('#jobType').val();
			switch (parseInt(workSheetType)) {
			case 1:
				saveAddPoint();
				break;
			case 2:
				saveConnect();
				break;
			case 3:
				saveTune();
				break;
			case 4:
				saveRepair();
				break;
			case 5:
				saveAddSettop();
				break;
			case 6:
				saveMovePoint();
				break;
			case 7:
				saveReducePoint();
				break;
			case 8:
				saveCut();
				break;
			case 9:
				saveMove();
				break;
			case 10:
				saveBorrow();
				break;
			case 11:
				saveAnalyzeProblems();
				break;
			}
		}
		
		function loadApplicationDetail(applicationId){
			$('.preloader').modal('show');
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/worksheetadd/loadServiceApplication/"+applicationId,
				dataType : 'json',
				async: true,
				//timeout : 100000,
				success : function(data) {
					if(data["error"] == false){
						// เซตข้อมูลลงในตาราง รายการอุปกรณ์ที่เช่ายืม
						setDataTableBorrowedEquipment(data);
						
						$('#idServiceApplication').val(data["result"].id);
						$('#custCode').html("<a href='${pageContext.request.contextPath}/customerregistration/view/" + data["result"]["customer"].id + "' target='_blank'>" + data["result"]["customer"].custCode + "</a>");
						$('#custName').text(data["result"]["customer"].firstName + " " + data["result"]["customer"].lastName);
						$('#custIden').text(data["result"]["customer"].identityNumber);
						$('#custType').text(data["result"]["customer"]["customerType"].value);
						$('#custFeature').text(data["result"]["customer"]["customerFeatureBean"].customerFeatureName);
						$('#custCareer').text(data["result"]["customer"]["careerBean"].careerName);
						$('#custMobile').text(data["result"]["customer"]["contact"].mobile);
						$('#custFax').text(data["result"]["customer"]["contact"].fax);
						$('#custEmail').text(data["result"]["customer"]["contact"].email);
						//address type1
						for(var i=0;i<data["result"]["customer"]["addressList"].length;i++){
							if(data["result"]["customer"]["addressList"][i].addressType == '1'){
								$('#custAddress').text(data["result"]["customer"]["addressList"][i].collectAddressDetail);
								$('#custNearby').text(data["result"]["customer"]["addressList"][i].nearbyPlaces);
							}
						}
						$('#custAppNo').html("<a href='${pageContext.request.contextPath}/serviceapplicationlist/view/" + data["result"]["id"] + "' target='_blank'><b>" + data["result"]["serviceApplicationNo"] + "</b></a>");
						$('#custAppCreatedDate').text(data["result"]["createDateTh"]);
						
						if(data["result"]["status"].stringValue == 'A'){
							$('#custAppStatus').html("<i class='ion-record text-green'></i> ใช้งานปกติ");
						}
						
						$('#custPackageType').text(data["result"]["servicepackage"]["serviceType"].packageTypeName);
						$('#custPackageCode').text(data["result"]["servicepackage"].packageCode);
						$('#custPackageName').text(data["result"]["servicepackage"].packageName);
						
						//set disabled id worksheet type
						disabledDropdownWorksheetType = [];
						for(var i=0;i<data["result"]["worksheetBeanList"].length;i++){
							//console.log("loop worksheet");
							if(data["result"]["worksheetBeanList"][i]["status"].stringValue != 'S' && data["result"]["worksheetBeanList"][i]["status"].stringValue != 'D'){
								//console.log("loop worksheet not type success");
								var workSheetType = data["result"]["worksheetBeanList"][i].workSheetType;
								
								if(workSheetType == 'C_AP'){
									disabledDropdownWorksheetType.push(1);
								}else if(workSheetType == 'C_C'){
									disabledDropdownWorksheetType.push(2);
								}else if(workSheetType == 'C_TTV'){
									disabledDropdownWorksheetType.push(3);
								}else if(workSheetType == 'C_RC'){
									disabledDropdownWorksheetType.push(4);
								}else if(workSheetType == 'C_ASTB'){
									disabledDropdownWorksheetType.push(5);
								}else if(workSheetType == 'C_MP'){
									disabledDropdownWorksheetType.push(6);
								}else if(workSheetType == 'C_RP'){
									disabledDropdownWorksheetType.push(7);
								}else if(workSheetType == 'C_CU'){
									disabledDropdownWorksheetType.push(8);
								}else if(workSheetType == 'C_M'){
									disabledDropdownWorksheetType.push(9);
								}else if(workSheetType == 'C_B'){
									disabledDropdownWorksheetType.push(10);
								}
							}
						}
						
						//bill over
// 						for(var i=0;i<data["result"]["invoiceOverBill"].length;i++){
// 							console.log(data["result"]["invoiceOverBill"][i].invoiceCode);
// 						}
						var RowOverBill = "";
						var StartRowOverBill = "<tr>";
						var EndRowOverBill = "</tr>";
						for(var i=0;i<data["result"]["invoiceOverBill"].length;i++){
							var colInvCode = "<td class='td-middle'><center><a target='_blank' href='${pageContext.request.contextPath}/invoice/view/" + data["result"]["invoiceOverBill"][i].id + "' data-toggle='tooltip' data-placement='bottom' title='' data-original-title='ดูรายละเอียด'><b>"+ data["result"]["invoiceOverBill"][i].invoiceCode +"</b></a></center></td>";
							
							var typeTextDisplay = "";
							if(data["result"]["invoiceOverBill"][i].invoiceType == 'S'){
								typeTextDisplay = "ติดตั้งอุปกรณ์ใหม่";
								
							}else if(data["result"]["invoiceOverBill"][i].invoiceType == 'R'){
								typeTextDisplay = data["result"]["invoiceOverBill"][i]["worksheet"].workSheetTypeText;
								
							}else if(data["result"]["invoiceOverBill"][i].invoiceType == 'O'){
								typeTextDisplay = "ค่าสมาชิกรายเดือน ("+ data["result"]["invoiceOverBill"][i].serviceRoundDate +")";
							}
							
							var colInvDetail = " <td><center>"+ typeTextDisplay +"</center></td>";
							var colInvDate = "<td><center>"+ data["result"]["invoiceOverBill"][i].createDateTh +"</center></td>";
							var colInvMoney = "<td><center>"+ numberWithCommas(data["result"]["invoiceOverBill"][i].amount) +" บาท</center></td>";
							var colInvStatus = "<td><center>ค้างชำระ</center></td>";
							
							if(data["result"]["invoiceOverBill"][i].createDateTh != null && data["result"]["invoiceOverBill"][i].createDateTh != "") {
								RowOverBill = RowOverBill + StartRowOverBill + colInvCode + colInvDetail + colInvDate;
								RowOverBill = RowOverBill + colInvMoney + colInvStatus + EndRowOverBill;
							}
						}
						$('#tbOverBill > tbody').html(RowOverBill);
						
						//bill current
						var RowCurrentBill = "";
						var StartRowCurrentBill = "<tr>";
						var EndRowCurrentBill = "</tr>";
						for(var i=0;i<data["result"]["invoiceCurrentBill"].length;i++){
							var colInvCode = "<td class='td-middle'><center><a target='_blank' href='${pageContext.request.contextPath}/invoice/view/" + data["result"]["invoiceCurrentBill"][i].id + "' data-toggle='tooltip' data-placement='bottom' title='' data-original-title='ดูรายละเอียด'><b>"+ data["result"]["invoiceCurrentBill"][i].invoiceCode +"</b></a></center></td>";
							
							var typeTextDisplay = "";
							if(data["result"]["invoiceCurrentBill"][i].invoiceType == 'S'){
								typeTextDisplay = "ติดตั้งอุปกรณ์ใหม่";
								
							}else if(data["result"]["invoiceCurrentBill"][i].invoiceType == 'R'){
								typeTextDisplay = data["result"]["invoiceCurrentBill"][i]["worksheet"].workSheetTypeText;
								
							}else if(data["result"]["invoiceCurrentBill"][i].invoiceType == 'O'){
								typeTextDisplay = "ค่าสมาชิกรายเดือน ("+ data["result"]["invoiceCurrentBill"][i].serviceRoundDate +")";
							}
							
							var colInvDetail = " <td><center>"+ typeTextDisplay +"</center></td>";
							var colInvDate = "<td><center>"+ data["result"]["invoiceCurrentBill"][i].createDateTh +"</center></td>";
							var colInvMoney = "<td><center>"+ numberWithCommas(data["result"]["invoiceCurrentBill"][i].amount) +" บาท</center></td>";
							var colInvStatus = "<td><center>รอจ่าย</center></td>";
							
							if(data["result"]["invoiceCurrentBill"][i].createDateTh != null && data["result"]["invoiceCurrentBill"][i].createDateTh != "") {
								RowCurrentBill = RowCurrentBill + StartRowCurrentBill + colInvCode + colInvDetail + colInvDate;
								RowCurrentBill = RowCurrentBill + colInvMoney + colInvStatus + EndRowCurrentBill;
							}
						}
						$('#tbCurrentBill > tbody').html(RowCurrentBill);
						setTimeout(function() {$('.preloader').modal('hide'); }, 200);
					}else{
						setTimeout(function() {$('.preloader').modal('hide'); }, 200);
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
		
		function setDataTableBorrowedEquipment(data){
// 			console.log(data);
			var count = 1;
			var rows = "";
			var deposit = 0;
			var amount = 0;
			var depositAll = data["result"].deposit;
			
			for(var i=0;i<data["result"]["productitemList"].length;i++){
				var productitem = data["result"]["productitemList"][i];
				var type = productitem.type;
				if(type != null && "E"==type){
					for(var j=0;j<productitem.productItemWorksheetBeanList.length;j++){
						var lend = productitem.productItemWorksheetBeanList[j].lend;
						var productItemWorksheet = productitem.productItemWorksheetBeanList[j];
						if(productItemWorksheet.equipmentProductItemBean!=null){
						if(lend){
							console.log(productitem.productItemWorksheetBeanList[j]);
							count++;
							rows += '<tr>\
								<td><center><label class="custom-control custom-checkbox "> <input type="checkbox" checked value="'+productItemWorksheet.id+'" class="custom-control-input checkBox_return_device"> <span class="custom-control-indicator"></span> <span class="custom-control-description"></span> </label></center>\
								<td>'+productitem.product.productName+' ('+productitem.product.productCode+') / '+productItemWorksheet.equipmentProductItemBean.serialNo+'</td>\
								<td><center>'+productItemWorksheet.quantity+' '+productitem.product.unit.unitName+'</center></td>\
								<td><select class="form-control"><option value="1">ปกติ</option><option value="0">ชำรุด</option><option value="5">หาย</option><option value="7">CA</option></select>\
								<td><center>'+productItemWorksheet.deposit+' บาท</center></td>\
								<td><center>'+productItemWorksheet.amount+' บาท</center></td>\
							</tr>';
							deposit += productItemWorksheet.deposit;
							amount += productItemWorksheet.amount;
						}
						}
					}
				}
			}
			
			//มัดจำรวมทั้งหมดภายใต้ใบสมัครนั้น
			depositAll = depositAll + deposit;
			$('#depositAll').val(depositAll);
			
			if(count > 1){
				rows += '<tr>\
					<td class="td-middle" colspan="4" align="right"><b>รวมเป็นเงิน</b></td>\
					<td><center>\
							<b>'+deposit+' บาท</b>\
						</center>\
					</td>\
					<td><center>\
							<b>'+amount+' บาท</b>\
						</center>\
					</td>\
				</tr>';
			}else{
				rows = '<tr><td colspan="6"><center>ไม่พบอุปรณ์ที่มีการเช่ายืม</center></td></tr>'
			}
			
			$('#table_borrowed_equipment > tbody').html(rows);
			
			
			$('.checkBox_return_device').click(function() {
				var idWorksheetItem = $(this).val();
				var typeCal = 0;
				var currentDeposit = $('#depositAll').val();
				
				if ($(this).is(':checked')) {
					typeCal = 1; //บวก
				} else {
					typeCal = 0; //ลบ
				}
				
				url = "${pageContext.request.contextPath}/worksheetadd/loadDepositItem/"+idWorksheetItem;
				$.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : url,
					dataType : 'json',
					async: false,
					//timeout : 100000,
					success : function(data) {
						console.log(data);
						if(data["error"] == false){
							if(typeCal == 1){
								$('#depositAll').val(parseFloat(currentDeposit) + data["result"]);
							}else if(typeCal == 0){
								$('#depositAll').val(parseFloat(currentDeposit) - data["result"]);
							}
						}
					}
				});
			});
			
		}
		
	</script>
</body>
</html>
