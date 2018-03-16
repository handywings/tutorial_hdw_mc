<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card-header clearfix">
	<div class="row mt05">
		<div class="col-md-12">
			<h2 align="center">ใบงาน ${worksheetBean.workSheetTypeText }</h2>
			<hr>
		</div>

	</div>
	<div class="row mt05 mb05">
		<div class="col-md-3" align="left">
			<input type="hidden" id="hiddenCurrentWorksheetId" value="${worksheetBean.idWorksheetParent }" />
			<b>เลขที่ใบงาน : </b>${worksheetBean.workSheetCode }<br>
		</div>

		<div class="col-md-3" align="left">
			<b>ผู้รับเรื่อง : </b>${worksheetBean.createByTh }
		</div>
		<div class="col-md-3" align="left">
			<b>วันที่บันทึก : </b>${worksheetBean.createDateTh }
		</div>
		<div class="col-md-3" align="right">
			<b>สถานะใบงาน : </b>
<!-- 			<img src="img/status-icon/task/wrench.png" style="width: 20px;"> <b style="color: #3d8ff3;">อยู่ระหว่างดำเนินงาน</b> -->
			<c:choose>
				<c:when test="${worksheetBean.status.stringValue eq 'R' }">
					<img src="<c:url value="/resources/assets/img/status-icon/task/wrench.png" />" style="width: 20px;">
					<small><b style="color: #3d8ff3;">อยู่ระหว่างดำเนินงาน</b></small>
				</c:when>
				<c:when test="${worksheetBean.status.stringValue eq 'H' }">
					<span data-toggle="tooltip" data-placement="bottom"
						title="ลูกค้าแจ้งยกเลิกการเชื่อมสาย"><img
						src="<c:url value="/resources/assets/img/status-icon/task/time-left.png" />" style="width: 20px;">
						<small><b style="color: #96723a;">งานคงค้าง</b></small></span>
				</c:when>
				<c:when test="${worksheetBean.status.stringValue eq 'S' }">
					<img src="<c:url value="/resources/assets/img/status-icon/task/checked.png" />" style="width: 20px;">
					<small><b style="color: #6ed029;">เสร็จสมบูรณ์</b></small>
				</c:when>
				<c:when test="${worksheetBean.status.stringValue eq 'W' }">
					<img src="<c:url value="/resources/assets/img/status-icon/task/hourglass.png" />" style="width: 20px;">
					<small><b style="color: #f8c056;">รอมอบหมายงาน</b></small>
				</c:when>
				<c:when test="${worksheetBean.status.stringValue eq 'C' }">
					<span data-toggle="tooltip" data-placement="bottom"
						title="ลูกค้าแจ้งยกเลิกการเชื่อมสาย"><img
						src="<c:url value="/resources/assets/img/status-icon/task/cancel.png" />" style="width: 20px;">
						<small><b style="color: #e2574c;">ยกเลิก</b></small></span>
				</c:when>
				<c:when test="${worksheetBean.status.stringValue eq 'D' }">
					<span data-toggle="tooltip" data-placement="bottom"
						title="ยกเลิกใบงาน"><img
						src="<c:url value="/resources/assets/img/status-icon/task/cancel.png" />" style="width: 20px;">
						<small><b style="color: #e2574c;">ยกเลิก</b></small></span>
				</c:when>
			</c:choose>
		</div>
	</div>
	
	<c:if test="${worksheetBean.updateByTh != null }">
		<div class="row mt05 mb05" style="color: gray;">
			<div class="col-md-6" align="left">
				<b>แก้ไขข้อมูลล่าสุดโดย : </b>${worksheetBean.updateByTh }
			</div>
			<div class="col-md-6" align="left">
				<b>แก้ไขข้อมูลล่าสุดเมื่อ : </b>${worksheetBean.updateDateTh }
			</div>
		</div>
	</c:if>
	
</div>
<div class="card-block">
	<div class="">
		<div class="row">
			<div class="col-md-12">
				<fieldset>
					<legend>&nbsp;&nbsp;&nbsp;ข้อมูลลูกค้า&nbsp;&nbsp;&nbsp;</legend>
					<div id="oldcustomers">
						<div class="row ">
							<div class="col-md-3">
								<b>รหัสลูกค้า</b><br><a href="${pageContext.request.contextPath}/customerregistration/view/${worksheetBean.serviceApplication.customer.id }" target="_blank"><b>${worksheetBean.serviceApplication.customer.custCode }</b></a>
							</div>
							<div class="col-md-3">
								<b>ชื่อลูกค้า</b><br>${worksheetBean.serviceApplication.customer.firstName } ${worksheetBean.serviceApplication.customer.lastName }
							</div>
							<div class="col-md-3">
								<b>เลขที่บัตรประชาชน</b><br>${worksheetBean.serviceApplication.customer.identityNumber }
							</div>
							<div class="col-md-3">
								<b>ประเภทลูกค้า</b><br>${worksheetBean.serviceApplication.customer.customerType.value }
							</div>
						</div>
						<div class="row mt05">
							<div class="col-md-3">
								<b>อาชีพ</b><br>${worksheetBean.serviceApplication.customer.careerBean.careerName }
							</div>
							<div class="col-md-3">
								<b>เบอร์โทรติดต่อ</b><br>${worksheetBean.serviceApplication.customer.contact.mobile }
							</div>
							<div class="col-md-3">
								<b>Fax</b><br>${worksheetBean.serviceApplication.customer.contact.fax }
							</div>
							<div class="col-md-3">
								<b>E-mail</b><br>${worksheetBean.serviceApplication.customer.contact.email }
							</div>
						</div>
						<div class="row mt05">
							<c:forEach var="addressBean" items="${worksheetBean.serviceApplication.customer.addressList}"
								varStatus="i">
								<c:if test="${addressBean.addressType eq '2' }">
									<c:if test="${worksheetBean.serviceApplication.id == addressBean.serviceApplicationBean.id}">
									<div class="col-md-6">
										<b>ที่อยู่ปัจจุบัน</b><br>${addressBean.collectAddressDetail }
									</div>
									<div class="col-md-6">
										<b>สถานที่ใกล้เคียง</b><br>${addressBean.nearbyPlaces }
									</div>
									</c:if>
								</c:if>
							</c:forEach>
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
							<b>เลขที่ใบสมัคร</b><br><a href="${pageContext.request.contextPath}/serviceapplicationlist/view/${worksheetBean.serviceApplication.id }" target="_blank"><b>${worksheetBean.serviceApplication.serviceApplicationNo }</b></a>
						</div>
						<div class="col-md-3">
							<b>วันที่สมัคร</b><br>${worksheetBean.serviceApplication.createDateTh }
						</div>
						<div class="col-md-3">
							<b>สถานะใบสมัคร</b><br>
							<c:choose>
								<c:when test="${worksheetBean.serviceApplication.status.stringValue eq 'D'}">
									
									แบบร่าง
								</c:when>
								<c:when test="${worksheetBean.serviceApplication.status.stringValue eq 'H'}">
									
									รอมอบหมายงาน
								</c:when>
								<c:when test="${worksheetBean.serviceApplication.status.stringValue eq 'W'}">
									
									ระหว่างการติดตั้ง
								</c:when>
								<c:when test="${worksheetBean.serviceApplication.status.stringValue eq 'A'}">
									
									ใช้งานปกติ
								</c:when>
								<c:when test="${worksheetBean.serviceApplication.status.stringValue eq 'I'}">
									
									ยกเลิกใช้งาน
								</c:when>
							</c:choose> 
						</div>
						<div class="col-md-3">
							<label for="exampleSelect1"><b>ประเภทบริการ</b></label><br>
							${worksheetBean.serviceApplication.servicepackage.serviceType.packageTypeName }
						</div>

					</div>
					<div class="row">

						<div class="col-md-3">
							<b>รหัส Package</b><br>${worksheetBean.serviceApplication.servicepackage.packageCode }
						</div>
						<div class="col-md-8">
							<div class="form-group">
								<b>ชื่อ Package</b><br>${worksheetBean.serviceApplication.servicepackage.packageName }
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12"></div>
					</div>
				</fieldset>
			</div>
		</div>
		<jsp:include page="modal_view_personnel_team.jsp"></jsp:include>
		<jsp:include page="modal_close_worksheet.jsp"></jsp:include>
		<!-- modal search equipment -->
		
		<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
		<script type="text/javascript">
		$( document ).ready(function() {
			//disalble when status not equal R
			var statusCurrentHistoryCheckDisable = '${statusCurrentHistory}';
			var statuWorksheet = '${worksheetBean.status.stringValue}';
			//alert(statusCurrentHistory);
			if(statuWorksheet == 'S' || statuWorksheet == 'D'){
				$(".worksheetViewDisableDom").attr('disabled','disabled');
				$(".worksheetViewDisableDomNotAuto").attr('disabled','disabled');
			}
			
		});
			
			function setDataHeader(worksheetUpdateSnapShotBean,typeSave){
				var workSheetType = '${worksheetBean.workSheetType}';
				worksheetUpdateSnapShotBean.idWorksheetParent =  $('#hiddenCurrentWorksheetId').val();
				//var radioSnapType = $('input[name = "snapType"]:checked').val();
				worksheetUpdateSnapShotBean.type = typeSave;
				if(typeSave == 'N'){
					worksheetUpdateSnapShotBean.remarkFail = $('#remark').val();
				}else{
					worksheetUpdateSnapShotBean.remarkFail = '';
					worksheetUpdateSnapShotBean.remark = $('#remark').val();
					worksheetUpdateSnapShotBean.remarkSuccess = $('#remarkSuccess').val();
				}
				worksheetUpdateSnapShotBean.jobDetails = $('#jobDetails').val();
				//personnel
				var personnelBeanList = [];
				$('.personnelIdClass').each(function (i) {
					var personnelBean = {};
					personnelBean.id = $(this).val();
					personnelBeanList.push(personnelBean);
				});
				worksheetUpdateSnapShotBean.personnelBeanList = personnelBeanList;
				//product
				var productItemBeanList = [];
				if(workSheetType == 'C_S'){
					$('#tableEquipmentProductSetup > tbody  > tr.trNewItem').each(function() {
						var productWorksheetItem = [];
						var worksheetItem = {};
						var productItem = {};
						var typeHidden = '';
						 $(this).find('input').each(function (i) {
								switch (i) {
										case 0:
											typeHidden = $(this).val();
											productItem.type = typeHidden;
											break;
										case 1:
											if(typeHidden == 'S'){
												productItem.quantity = $(this).val();
											}else if(typeHidden == 'E'){
												worksheetItem.quantity = $(this).val();
											}else if(typeHidden == 'I'){
												worksheetItem.quantity = $(this).val();
											}
											break;
										case 2:
											var checkIsFree = $(this).is(':checked');
											if(typeHidden == 'S'){
												productItem.free = checkIsFree;
											}else if(typeHidden == 'E'){
												worksheetItem.free = checkIsFree;
											}else if(typeHidden == 'I'){
												worksheetItem.free = checkIsFree;
											}
											break;
										case 3:
											var checkIsLend = $(this).is(':checked');
											if(typeHidden == 'S'){
												productItem.lend = checkIsLend;
											}else if(typeHidden == 'E'){
												worksheetItem.lend = checkIsLend;
											}else if(typeHidden == 'I'){
												worksheetItem.lend = checkIsLend;
											}
											break;
										case 4:
											if(typeHidden == 'S'){
												productItem.price = $(this).val();
											}else if(typeHidden == 'E'){
												worksheetItem.price = $(this).val();
											}else if(typeHidden == 'I'){
												worksheetItem.price = $(this).val();
											}
											break;
										case 5:
											
											break;
										case 6:
											if(typeHidden == 'S'){
												
											}else if(typeHidden == 'E'){
												var EquipmentProductItemBean = {};
												EquipmentProductItemBean.id = $(this).val();
												worksheetItem.equipmentProductItemBean = EquipmentProductItemBean;
											}else if(typeHidden == 'I'){
												var InternetProductBean = {};
												InternetProductBean.id = $(this).val();
												worksheetItem.internetProductBeanItem = InternetProductBean;
											}
											break;
										case 7:
											if(typeHidden == 'S'){
												productItem.id = $(this).val();
											}else if(typeHidden == 'E'){
												productItem.id = $(this).val();
											}else if(typeHidden == 'I'){
												productItem.id = $(this).val();
											}
											break;
										case 8:
											if(typeHidden == 'S'){
												
											}else if(typeHidden == 'E'){
												var requisitionItemBean = {};
												requisitionItemBean.id = $(this).val();
												worksheetItem.requisitionItemBean = requisitionItemBean;
											}else if(typeHidden == 'I'){
												
											}
											break;
										}
								
									});
						 			productWorksheetItem.push(worksheetItem);
						 			productItem.productItemWorksheetBeanList = productWorksheetItem; //productItem ตัวนี้ให้มองว่าเป็น equimentProduct ที่เป็นตัวแม่ของ equipmentProductItem ยนน
						 			productItemBeanList.push(productItem);
								});
				}else{
					$('#tableEquipmentProduct > tbody  > tr.trNewItem').each(function() {
						var productWorksheetItem = [];
						var worksheetItem = {};
						var productItem = {};
						var typeHidden = '';
						 $(this).find('input').each(function (i) {
							 switch (i) {
								case 0:
									typeHidden = $(this).val();
									productItem.type = typeHidden;
									break;
								case 1:
									if(typeHidden == 'S'){
										productItem.quantity = $(this).val();
									}else if(typeHidden == 'E'){
										worksheetItem.quantity = $(this).val();
									}else if(typeHidden == 'I'){
										worksheetItem.quantity = $(this).val();
									}
									break;
								case 2:
									var checkIsFree = $(this).is(':checked');
									if(typeHidden == 'S'){
										productItem.free = checkIsFree;
									}else if(typeHidden == 'E'){
										worksheetItem.free = checkIsFree;
									}else if(typeHidden == 'I'){
										worksheetItem.free = checkIsFree;
									}
									break;
								case 3:
									var checkIsLend = $(this).is(':checked');
									if(typeHidden == 'S'){
										productItem.lend = checkIsLend;
									}else if(typeHidden == 'E'){
										worksheetItem.lend = checkIsLend;
									}else if(typeHidden == 'I'){
										worksheetItem.lend = checkIsLend;
									}
									break;
								case 4:
									if(typeHidden == 'S'){
										productItem.price = $(this).val();
									}else if(typeHidden == 'E'){
										worksheetItem.price = $(this).val();
									}else if(typeHidden == 'I'){
										worksheetItem.price = $(this).val();
									}
									break;
								case 5:
									
									break;
								case 6:
									if(typeHidden == 'S'){
										
									}else if(typeHidden == 'E'){
										var EquipmentProductItemBean = {};
										EquipmentProductItemBean.id = $(this).val();
										worksheetItem.equipmentProductItemBean = EquipmentProductItemBean;
									}else if(typeHidden == 'I'){
										var InternetProductBean = {};
										InternetProductBean.id = $(this).val();
										worksheetItem.internetProductBeanItem = InternetProductBean;
									}
									break;
								case 7:
									if(typeHidden == 'S'){
										productItem.id = $(this).val();
									}else if(typeHidden == 'E'){
										productItem.id = $(this).val();
									}else if(typeHidden == 'I'){
										productItem.id = $(this).val();
									}
									break;
								case 8:
									if(typeHidden == 'S'){
										
									}else if(typeHidden == 'E'){
										var requisitionItemBean = {};
										requisitionItemBean.id = $(this).val();
										worksheetItem.requisitionItemBean = requisitionItemBean;
									}else if(typeHidden == 'I'){
										
									}
									break;
								}
								
									});
						 			productWorksheetItem.push(worksheetItem);
						 			productItem.productItemWorksheetBeanList = productWorksheetItem; //productItem ตัวนี้ให้มองว่าเป็น equimentProduct ที่เป็นตัวแม่ของ equipmentProductItem ยนน
						 			productItemBeanList.push(productItem);
								});
				}
				worksheetUpdateSnapShotBean.productItemBeanList = productItemBeanList;
				
				//sub worksheet
				var subWorkSheetBeanList = [];
				$('#tableSubWorksheet > tbody  > tr.trNewItemSubWorksheet').each(function() {
					var subWorksheetBean = {};
					 $(this).find('input').each(function (i) {
							switch (i) {
									case 0:
										subWorksheetBean.workSheetType = $(this).val();
										break;
									case 1:
										subWorksheetBean.price = $(this).val();
										break;
									}
							
								});
					 		var remark = $('.remark').val();
					 		subWorksheetBean.remark = remark;
					 		subWorkSheetBeanList.push(subWorksheetBean);
							});
				
				worksheetUpdateSnapShotBean.subWorkSheetBeanList = subWorkSheetBeanList;
				//End sub worksheet
				
				return worksheetUpdateSnapShotBean;
			}
			
			function convertWorksheetTypeToText(workSheetType){
				var worksheetText = '';
				
				if(workSheetType == 'C_S'){
					worksheetText = 'ติดตั้ง';
				}else if(workSheetType == 'C_AP'){
					worksheetText = 'เสริมจุดบริการ';
				}else if(workSheetType == 'C_C'){
					worksheetText = 'การจั้มสาย';
				}else if(workSheetType == 'C_TTV'){
					worksheetText = 'การจูน TV';
				}else if(workSheetType == 'C_RC'){
					worksheetText = 'การซ่อมสัญญาณ';
				}else if(workSheetType == 'C_ASTB'){
					worksheetText = 'ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี';
				}else if(workSheetType == 'C_MP'){
					worksheetText = 'การย้ายจุด';
				}else if(workSheetType == 'C_RP'){
					worksheetText = 'การลดจุด';
				}else if(workSheetType == 'C_CU'){
					worksheetText = 'การตัดสาย';
				}else if(workSheetType == 'C_M'){
					worksheetText = 'การย้ายสาย';
				}else if(workSheetType == 'C_B'){
					worksheetText = 'แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี';
				}
				
				return worksheetText;
			}
			
			//close worksheet
			function processCloseWorksheet(worksheetId){
				var worksheetUpdateSnapShotBean = {};
				worksheetUpdateSnapShotBean.idWorksheetParent =  $('#hiddenCurrentWorksheetId').val();
				worksheetUpdateSnapShotBean.remark = $('#remark').val();
				worksheetUpdateSnapShotBean.remarkFail = $('#remark').val();
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/worksheetlist/closeWorksheet",
					dataType : 'json',
					async : false,
					data : JSON.stringify(worksheetUpdateSnapShotBean),
					//timeout : 100000,
					success : function(data) {
						if(data["error"] == false){
							window.location.reload();
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
		
		
		