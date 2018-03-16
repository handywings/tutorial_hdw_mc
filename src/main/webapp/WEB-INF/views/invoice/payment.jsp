<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="invoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ชำระเงินค่าบริการ</title>
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
							<h3>ชำระเงินค่าบริการ</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item">ระบบการเงิน</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/invoice">รายการเอกสารใบแจ้งหนี้</a></li>
								<li class="breadcrumb-item active">ชำระเงินค่าบริการ</li>
							</ol>
						</div>
					</div>
					<div class="card mt05 mb40">
						<div class="card-header" style="font-size: 20px;">
							<div class="row">
								<div class="col-md-2">
									<img src='<c:url value="/resources/assets/img/logo-invoice.png" />'>
								</div>
								<div class="col-md-5">
									<input type="hidden" id="invoiceId" value="${invoiceBean.id }" />
									<b>เลขที่ใบแจ้งหนี้ : </b>${invoiceBean.invoiceCode }<br> <b>วันที่ออกเอกสาร
										: </b>${invoiceBean.issueDocDateTh }
								</div>
								<div class="col-md-5" align="right">
									<b>วันที่นัดชำระ : </b>
									<c:choose>
										<c:when test="${invoiceBean.paymentDateTh ne ''}">
											${invoiceBean.paymentDateTh }
										</c:when>
										<c:when test="${invoiceBean.paymentDateTh eq ''}">
											-
										</c:when>
									</c:choose>
									<br> 
									<b>สถานะ : </b>
									<c:choose>
										<c:when test="${invoiceBean.status eq 'W'}">
											<i class="ion-record text-orange"></i> รอลูกค้าชำระเงิน
										</c:when>
										<c:when test="${invoiceBean.status eq 'S'}">
											<i class="ion-record text-green"></i> ชำระเงินเรียบร้อย
										</c:when>
										<c:when test="${invoiceBean.status eq 'O'}">
											<i class="ion-record text-red"></i> เกินวันนัดชำระเงิน
										</c:when>
									</c:choose>
								</div>
							</div>

						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">

								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลลูกค้า&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-1">
														<b>รหัสลูกค้า :</b>
													</div>
													<div class="col-md-3"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" target="_blank" href="${pageContext.request.contextPath}/customerregistration/view/${invoiceBean.serviceApplication.customer.id }"><b>${invoiceBean.serviceApplication.customer.custCode }</b></a></div>
													<div class="col-md-1">
														<b>ชื่อลูกค้า :</b>
													</div>
													<div class="col-md-3">
														${invoiceBean.serviceApplication.customer.firstName }&nbsp;
														${invoiceBean.serviceApplication.customer.lastName }
													</div>
													<div class="col-md-2">
														<b>เลขที่ใบสมัคร :</b>
													</div>
													<div class="col-md-2"><a data-toggle="tooltip" data-placement="bottom" title="" data-original-title="ดูรายละเอียด" target="_blank" href="${pageContext.request.contextPath}/serviceapplicationlist/view/${invoiceBean.serviceApplication.id }"><b>${invoiceBean.serviceApplication.serviceApplicationNo }</b></a></div>
												</div>

												<div class="row mt05">
													<c:forEach var="addressBean" items="${invoiceBean.serviceApplication.addressList}" varStatus="i">
														<c:if test="${addressBean.addressType eq '4'}">
															<div class="col-md-1">
																<b>ที่อยู่ :</b>
															</div>
															<div class="col-md-7">${addressBean.collectAddressDetail }<br><b>(${addressBean.zoneBean.zoneDetail })</b></div>															
															<div class="col-md-2">
																<b>สถานที่ใกล้เคียง :</b>
															</div>
															<div class="col-md-2">${addressBean.nearbyPlaces }</div>
														</c:if>
													</c:forEach>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt05">
									<div class="col-md-8">
									
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รูปแบบใบเสร็จรับเงิน&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-12">
														<label class="custom-control custom-radio"> 
														<input id="pay_no_vat" name="pay_vat_type" type="radio" checked="checked"
															value="N" class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;ไม่คิดภาษี</span>
														</label>&nbsp;&nbsp;&nbsp;
														<label class="custom-control custom-radio"> 
														<input id="pay_add_vat" name="pay_vat_type" type="radio"
															value="A" class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;คิดภาษีมูลค่าพิ่ม ${companyBean.vat }%</span>
														</label> 
													</div>
												</div>
											</div>
										</fieldset>
									
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลการชำระเงิน&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-8">
														<label for="exampleInputEmail1"><b>วิธีการชำระเงิน</b></label><br>
														<label class="custom-control custom-radio"> <input
															id="radio1" name="paymentType" type="radio"
															checked="checked" class="custom-control-input" value="C"> 
															<span class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;ชำระที่บริษัท</span>
														</label>&nbsp;&nbsp;&nbsp;<label class="custom-control custom-radio"> <input
															id="radio2" name="paymentType" type="radio" value="T"
															class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;ชำระผ่านธนาคาร</span>
													</div>
													<div class="col-md-4">
													</div>
												</div>
												<div class="row bank" style="display: none;">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>ธนาคาร</b></label><br>
														<select id="bank" name="categoryID"
															class="form-control">
															<c:forEach var="bankBean" items="${bankBeanList}" varStatus="i">
																<option value="${bankBean.id }">${bankBean.bankShortName } - ${bankBean.bankNameTh }</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row mb05 mt05">
													<div class="col-md-6 datepayment" style="display: none;">
														<label for="exampleInputEmail1"><b>วันที่รับชำระเงิน</b></label><br>
														<input type="text" class="datepicker-autoclose form-control" id="paymentAcceptDate">
													</div>
													<div class="col-md-3 datepayment" style="display: none;">
														<label for="exampleInputEmail1"><b>ระบุชั่วโมง</b></label>
														<br> <select class="form-control" id="hr">
															<option value="00">00</option>
															<option value="01">01</option>
															<option value="02">02</option>
															<option value="03">03</option>
															<option value="04">04</option>
															<option value="05">05</option>
															<option value="06">06</option>
															<option value="07">07</option>
															<option value="08">08</option>
															<option value="09">09</option>
															<c:forEach begin="10" end="23" varStatus="i">
																<option value="${i.index }">${i.index }</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-md-3 datepayment" style="display: none;">
														<label for="exampleInputEmail1"><b>ระบุนาที</b></label>
														<br> <select class="form-control" id="mn">
															<option value="00">00</option>
															<option value="01">01</option>
															<option value="02">02</option>
															<option value="03">03</option>
															<option value="04">04</option>
															<option value="05">05</option>
															<option value="06">06</option>
															<option value="07">07</option>
															<option value="08">08</option>
															<option value="09">09</option>
															<c:forEach begin="10" end="59" varStatus="i">
																<option value="${i.index }">${i.index }</option>
															</c:forEach>
														</select>
													</div>													
												</div>
											</div>
										</fieldset>
									</div>
									
									<div class="col-md-4">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;วันที่ชำระเงิน&nbsp;&nbsp;&nbsp;</legend>
											
											<div class="row">
												<div class="col-md-12">
													<input type="text" class="form-control" name="paymentDate"
														id="paymentDate" placeholder="วันที่ชำระเงิน" value="${paymentDate}">
												</div>
											</div>
											
										</fieldset>
									</div>		
											
									<div class="col-md-4">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ค่าบริการ&nbsp;&nbsp;&nbsp;</legend>
											
											<div class="row">
												<div class="col-md-12">
													<h1 align="center">
														<span id="headerTextAmount">
															<fmt:formatNumber type="number" maxFractionDigits="2" pattern="#,##0.00" value="${invoiceBean.amount}" /></span> บาท
														
													</h1>
													<input type="hidden" id="hidden_amount" value="${invoiceBean.amount}">
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>ยอดที่ชำระ</b></label><br>
													<div class="input-group">
														<fmt:parseNumber var = "amountInt" integerOnly = "true" type = "number" value = "${invoiceBean.amount}" />
														<input type="number" id="amount" class="form-control" value="${amountInt}">
														<div class="input-group-addon">บาท</div>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<span class="text-red">หมายเหตุ : ลดหนี้ให้ 
													<input type="hidden" id="reductAmount" value='<fmt:formatNumber type="number" maxFractionDigits="2"
														value="${invoiceBean.amount -  amountInt}" />' />
													<span class="span-reductAmount">
													<fmt:formatNumber type="number" maxFractionDigits="2"
														value="${invoiceBean.amount -  amountInt}" /></span>
													 บาท</span>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15">
									<c:if test="${invoiceBean.status ne 'S'}">
									<div class="col-md-12 mt05" align="right">
										<a class="cursor-p">
											<button type="button" onclick="loadTypeVat()"
												class="btn btn-success label-left mr-0-5">
												<span class="btn-label"><i class="ti-check"></i></span>ยืนยันการชำระเงิน
											</button>
										</a> <a class="cursor-p" href="${pageContext.request.contextPath}/invoice"><button
												type="button" class="btn btn-danger	 label-left  mr-0-5">
												<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
											</button></a>
									</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
<%-- 			<jsp:include page="modal_confirm_pay.jsp"></jsp:include> --%>
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
		$( document ).ready(function() {
			
			//set date picker
			$("#paymentDate").datepicker({
				autoclose: true,
				format: 'dd-mm-yyyy',
				todayHighlight:'TRUE',
				todayBtn: true,
	            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
	            thaiyear: true              //Set เป็นปี พ.ศ.
			});	
			
			var oldAmount = '${invoiceBean.amount}';
			var vat = '${companyBean.vat}';
			var amount = parseInt(oldAmount);	
			$('#pay_add_vat').prop("checked", true);
			
			if(amount && vat){
				var amountA = ((vat/100) * amount)+amount;
				addVat(amountA);
			}
			
			$('input[type=radio][name=pay_vat_type]').change(function() {
		        if($(this).val() == 'V'){
		        	$('#amount').val(amount);
		        	$('#hidden_amount').val(amount);
		        	$('#headerTextAmount').text(numberWithCommas(parseFloat(amount).toFixed(2)));
		        	
		        }else if($(this).val() == 'N'){
		        	$('#amount').val(amount);
		        	$('#hidden_amount').val(amount);
		        	$('#headerTextAmount').text(numberWithCommas(parseFloat(amount).toFixed(2)));
		        	
		        }else if($(this).val() == 'A'){
		        	var amountA = ((vat/100) * amount)+amount;
		        	addVat(amountA);
		        }
		        $('.span-reductAmount').text("0");
		    });
		});
		
		function addVat(amountA){
        	$('#amount').val(amountA);
        	$('#hidden_amount').val(amountA);
        	$('#headerTextAmount').text(numberWithCommas(parseFloat(amountA).toFixed(2)));
	 	}
		
		$('.datepicker-autoclose').datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
            language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
		});
		var currentDate = new Date();
		$(".datepicker-autoclose").datepicker("setDate", currentDate);
		
		$('.bank').hide();
		$('input[name=paymentType]').change(function() {
			if (this.value == 'T' || this.value == 'cheque') {
				$('#pay_add_vat').prop("checked", true);
				$('.bank').fadeIn();
				$('.datepayment').fadeIn();

				var oldAmount = '${invoiceBean.amount}';
				var vat = '${companyBean.vat}';
				var amount = parseInt(oldAmount);
				
				var amountA = ((vat/100) * amount)+amount;
	        	$('#amount').val(amountA);
	        	$('#hidden_amount').val(amountA);
	        	$('#headerTextAmount').text(numberWithCommas(parseFloat(amountA).toFixed(2)));
	        	$('.span-reductAmount').text("0");
			} else {
				$('.bank').hide();
				$('.datepayment').hide();
			}
		});
		
		function loadTypeVat(){
	 		var radioTypeVat = $('input[name = "pay_vat_type"]:checked').val();
	 		savePayment(radioTypeVat);
	 	}
		
		//save payment
		function savePayment(vat){
			$('.preloader').modal('show');
			var billDocumentBean = {};
			var paymentType = $('input[name = "paymentType"]:checked').val();
			billDocumentBean.paymentType = paymentType;
			//bank transfer
			BankBean = {};
			if(paymentType == 'T'){
				BankBean.id = $('#bank').val();
				billDocumentBean.hour = $('#hr').val();
				billDocumentBean.minute = $('#mn').val();
				billDocumentBean.paymentDateTh = $('#paymentAcceptDate').val();
			}
			billDocumentBean.bankBean = BankBean;
			//invoice 
			InvoiceDocumentBean = {};
			InvoiceDocumentBean.id = $('#invoiceId').val();
			billDocumentBean.invoiceDocumentBean = InvoiceDocumentBean;
			//amount
			billDocumentBean.amount = $('#amount').val();
			billDocumentBean.reductAmount = $('.span-reductAmount').text();
			billDocumentBean.paymentDateTh = $('#paymentDate').val();
			
			//vat
			billDocumentBean.vat = vat;
			//send save
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/invoice/savePayment",
				dataType : 'json',
				async : false,
				data : JSON.stringify(billDocumentBean),
				//timeout : 100000,
				success : function(data) {
					if (data["error"] == false) {
						window.location.href = "${pageContext.request.contextPath}/invoice/view/"+'${invoiceBean.id }';
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
		
// 		$('#amount').keyup(function() {		
// 			var amount = $(this).val();
// 			var hidden_amount = $('#hidden_amount').val();
// 			if(parseFloat(amount) < parseFloat(hidden_amount)){
// 				var reductAmount = parseFloat(hidden_amount) - parseFloat(amount);
// 				$('#reductAmount').val(reductAmount.toFixed(2));
// 				$('.span-reductAmount').text(reductAmount.toFixed(2));
// 			}else{
// 				$('#reductAmount').val("0");
// 				$('.span-reductAmount').text("0");
// 			}
			
// 		});
		
		$('#amount').blur(function() {		
			var amount = $(this).val();
			var hidden_amount = $('#hidden_amount').val();
			if(amount < 0 || amount==""){
				$('#amount').val(hidden_amount);
			}
			if(isNaN(amount)){
				$('#amount').val(hidden_amount);
			}
			
			amount = $('#amount').val();
			if(parseFloat(amount) < parseFloat(hidden_amount)){
				var reductAmount = parseFloat(hidden_amount) - parseFloat(amount);
				$('#reductAmount').val(parseFloat(reductAmount).toFixed(2));
				$('.span-reductAmount').text(parseFloat(reductAmount).toFixed(2));
			}else{
				$('#reductAmount').val("0");
				$('.span-reductAmount').text("0");
			}
			
			$('#headerTextAmount').text(numberWithCommas(parseFloat(amount).toFixed(2)));
		});
		
	</script>
	
</body>
</html>
