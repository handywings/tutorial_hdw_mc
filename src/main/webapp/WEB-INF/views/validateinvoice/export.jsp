<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="validateinvoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>นำบิลออก</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style type="text/css">
.nav-tabs .nav-link {
    padding: 0.5em .5em;
}
</style>
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
						<div class="col-md-12">
							<h3>ตรวจสอบใบเสร็จรับเงิน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบการเงิน</li>
								<li class="breadcrumb-item active">ตรวจสอบใบเสร็จรับเงิน</li>
								<li class="breadcrumb-item active">นำบิลออก</li>
							</ol>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<ul class="card nav nav-tabs nav-tabs-2">
								<li class="nav-item"><a class="nav-link "
									href="${pageContext.request.contextPath}/validateinvoice"><b><i class="zmdi zmdi-search"></i> บิลค้างส่งคืน</b></a></li>
								<li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/validateinvoice/export"><b><i class="ti-export"></i> นำบิลออก</b></a></li>
								<li class="nav-item"><a class="nav-link"
									href="${pageContext.request.contextPath}/validateinvoice/import"><b><i class="ti-import"></i> รับบิลเข้า</b></a></li>
							</ul>
						</div>
					</div>

					<div class="card mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="ti-export"></i>&nbsp;&nbsp;นำบิลออก
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;มอบหมายผู้รับผิดชอบ&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">													
													<div class="col-md-2">
														<b>วันที่นำบิลออก<span class="text-red"> * </span>
														</b>
													</div>
													<div class="col-md-3">
														<input type="text" class="form-control daterange" id="exportDate">
													</div>
													<div class="col-md-3">
														<b>ผู้รับผิดชอบ / พนักงานเก็บเงิน<span class="text-red"> * </span>
														</b>
													</div>
													<div class="col-md-4">
														<select name="personnelCashier" class="form-control" id="personnelCashier">
															<c:forEach var="personnelCashierBean" items="${personnelCashierList}"
																varStatus="i">
																<option value="${personnelCashierBean.id }">
																	${personnelCashierBean.prefix }${personnelCashierBean.firstName }&nbsp;
																	${personnelCashierBean.lastName } (${personnelCashierBean.nickName })
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-4 offset-md-4" align="center">
										<label for="exampleInputEmail1"><b>กรอกหมายเลขใบแจ้งหนี้
												/ ใบเสร็จรับเงินชั่วคราว<span class="text-red"> *</span>
										</b></label> <input type="text" class="form-control" id="invoiceCodeLoad" style="background-color: rgba(33, 150, 243, 0.08);font-size: 25px;font-weight: bold;color: #2196F3;">
									</div>
								</div>
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="tableDisplayInvoice">
												<thead class="thead-default">
													<tr>
														<th><center>หมายเลข</center></th>
														<th>ข้อมูลลูกค้า</th>
														<th><center>เขตชุมชน</center></th>
														<th><center>วันครบกำหนดชำระ</center></th>
														<th align="right">ยอดชำระ</th>
														<th><center>ผู้รับผิดชอบ</center></th>
														<th><center>วันที่นำออกบิล</center></th>
														<th><center>ลบ</center></th>
													</tr>
												</thead>
												<tbody>

												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="row mt15 mb15">
									<div class="col-md-12">
										<center>
											<button type="button" onclick="saveExport();" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
												<span class="btn-label"><i class="ti-export"></i></span>
												<b>ยืนยันการนำบิลออก</b>
											</button>
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

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>
	
    <script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
	<script type="text/javascript">
		$('.daterange').datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
		});		
		
		//enter press
		$(document).ready(function() {
			//$('#tableDisplayInvoice').DataTable({searching: true, paging: true});
			
			$('#invoiceCodeLoad').focus();
		    $('#invoiceCodeLoad').keypress(function(e) {
		        if ( e.keyCode == 13 ) {  // detect the enter key
		            loadInvoiceByCode($(this).val());
		        }
		    });
		    
		    //set current date
		    var currentDate = new Date();
		 	$(".daterange").datepicker("setDate", currentDate);
		});
		
		//load invoice 
		function loadInvoiceByCode(invoiceCode){
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/validateinvoice/loadInvoice",
				dataType : 'json',
				async: false,
				data : JSON.stringify(invoiceCode),
				//timeout : 100000,
				success : function(data) {
					if(data["error"] == false){
						var row = "";
						var startRow = "<tr class='trNewItem'>";
						var endRow = "</tr>";
						var colBilling = "";
						if(data["result"].billing == true){
							var colBilling = "<br/>(วางบิลแล้ว)";
						}
						
						var colInvoiceCode = "<td class='td-middle'><center><input type='hidden' value='"+ data["result"].id +"' /><b>"+ data["result"].invoiceCode + colBilling +"</b></center></td>";
						var colInvoiceCustomer = "<td class='td-middle'>"+ 
						data["result"]["serviceApplication"]["customer"].prefix +
						data["result"]["serviceApplication"]["customer"].firstName +" "+
						data["result"]["serviceApplication"]["customer"].lastName +
						"</td>";
						var zoneName = "";
						var zoneDetail = "";
						for(var i=0;i<data["result"]["serviceApplication"]["addressList"].length;i++){
							if(data["result"]["serviceApplication"]["addressList"][i].addressType == '4'){
								zoneName = data["result"]["serviceApplication"]["addressList"][i]["zoneBean"].zoneName;
								zoneDetail = data["result"]["serviceApplication"]["addressList"][i]["zoneBean"].zoneDetail;
								if (typeof zoneName != 'undefined'){
									zoneName = zoneName;
									zoneDetail = zoneDetail;
								}else{
									zoneName = "";
									zoneDetail = "";
								}
							}
						}
						
						var colInvoiceZone = "<td class='td-middle'><center>"+ zoneDetail + " (" + zoneName +")</center></td>";
						var colInvoiceOrderDatePay = "<td class='td-middle'><center>"+ data["result"].createDateTh +"</center></td>";
						var colInvoiceAmount = "<td class='td-middle' align='right'>"+ numberWithCommas(data["result"].amount) +"</td>";
						
						var personnelDropdown = $("#personnelCashier option:selected").text();
						var colInvoicePersonnel = "<td class='td-middle'><center>"+personnelDropdown+"</center></td>";
						
						var exportBillDate = $(".daterange").val();
						var colInvoiceExportBillDate = "<td class='td-middle'><center>"+convertTodateString(exportBillDate)+"</center></td>";
						var colInvoiceOption = "<td class='td-middle'><center><a class='cursor-p' target='_blank' href='"+
						"${pageContext.request.contextPath}/invoice/view/"+ data["result"].id +"'>"+
						"<span class='ti-search' data-toggle='tooltip' data-placement='bottom' title='' "+
						"data-original-title='ดูรายละเอียด'></span></a>  <a class='run-swal cursor-p removeInvoceItem'><span class='ti-trash' "+
						" data-toggle='tooltip' data-placement='bottom' title='' "+
						"data-original-title='ลบ'></span></a></center></td>";
						
						row = row + startRow + colInvoiceCode + colInvoiceCustomer + colInvoiceZone + colInvoiceOrderDatePay;
						row = row + colInvoiceAmount + colInvoicePersonnel + colInvoiceExportBillDate + colInvoiceOption + endRow;
						
						if(row.length > 0){
							var checkDup = false;
							$('#tableDisplayInvoice > tbody  > tr.trNewItem').each(function() {
								$(this).find('input').each(function (i) {
									switch (i) {
									case 0:
										if($(this).val() == data["result"].id){
											checkDup = true;
										}
									}
								});
							});
							if(!checkDup){
								$('#tableDisplayInvoice').append(row);
							}
							$('#invoiceCodeLoad').val("");
							//$('#tableDisplayInvoice').DataTable({searching: true, paging: true});
							//$('#tableDisplayInvoice').DataTable().draw();
						}
					}else{
						$('#invoiceCodeLoad').val("");
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
			
			// ลบ row
			$(".removeInvoceItem").click(function () {
				$(this).closest('tr').remove();
			});
		}
		
			function saveExport() {
				$('.preloader').modal('show');
					var ValidateInvoiceBean = {};
					ValidateInvoiceBean.exportDate = $('#exportDate').val();
					ValidateInvoiceBean.idPersonnelCashier = $('#personnelCashier').val();
					var worksheetList = [];
					
					$('#tableDisplayInvoice > tbody  > tr.trNewItem').each(function() {
						var InvoiceDocumentBean = {};
						$(this).find('input').each(function (i) {
							switch (i) {
							case 0:
								InvoiceDocumentBean.id = $(this).val();
								break;
							}
						});
						worksheetList.push(InvoiceDocumentBean);
					});
					ValidateInvoiceBean.invoiceDocumentBeanList = worksheetList;
					
					$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/validateinvoice/saveExport",
						dataType : 'json',
						async : false,
						data : JSON.stringify(ValidateInvoiceBean),
						//timeout : 100000,
						success : function(data) {
							if (data["error"] == false) {
								$('.preloader').modal('hide');
								window.location.reload();
							} else {

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
			
			$("#exportDate").on("change",function(){
				$("#invoiceCodeLoad").focus();
		    });

			$("#importDate").on("change",function(){
				$("#invoiceCodeLoad").focus();
		    });
			
	</script>
</body>
</html>
