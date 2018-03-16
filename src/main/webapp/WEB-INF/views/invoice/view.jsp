<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="invoice" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายละเอียดใบแจ้งหนี้ : ${invoiceBean.invoiceCode }</title>
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
							<h3>รายละเอียดใบแจ้งหนี้ : ${invoiceBean.invoiceCode }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/invoice">รายการเอกสารใบแจ้งหนี้</a></li>
								<li class="breadcrumb-item active">รายละเอียดใบแจ้งหนี้</li>
							</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
							<c:if test="${invoiceBean.status ne 'S'}">
							<a href="javascript:void(0);" >
								<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
								 class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบแจ้งหนี้
 								</button>
 							</a>
 							</c:if>
 							<c:if test="${invoiceBean.status eq 'S'}">
							<a href="javascript:void(0);" >
								<button type="button" data-toggle="modal" data-target="#modalPrintInvoiceReceipt" data-whatever="@getbootstrap"
								 class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="pe-7s-print"></i></span>พิมพ์ใบเสร็จรับเงิน
 								</button>
 							</a>
 							</c:if>
							
							<div class="modal fade" id="modalPrintInvoiceReceipt" tabindex="-1" role="dialog"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="exampleModalLabel">เลือกบริษัท</h4>
										</div>
										<div class="modal-body">
											<form>
												<div class="form-group">
													<select id="company_for_print" name="company" class="form-control" path="company">
														<c:forEach items="${companyList}" var="company">
														<option value="${company.id}" >${company.companyName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="row">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>ส่วนลด</b></label> 
														<input type="number" min=0 class="form-control" 
														placeholder="ส่วนลด" id="discount" value="0">
													</div>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-info" data-dismiss="modal" onclick="printInvoiceReceipt();">ตกลง</button>
										</div>
									</div>
								</div>
							</div>
							
							<c:if test="${invoiceBean.status ne 'S'}">
							<a href="${pageContext.request.contextPath}/invoice/payment/${invoiceBean.id }" target="_blank"><button
									type="button"
									class="btn btn-primary label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="pe-7s-cash"></i></span>ชำระเงิน
								</button></a>
							</c:if>
						</div>
					</div>
					<div class="card mt05 mb30">
						 <jsp:include page="view_header_invoice.jsp"></jsp:include>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<jsp:include page="view_header_personnel.jsp"></jsp:include>	
								<c:if test="${isCashier}"> <!-- จะแสดงเฉพาะพนักงานเก็บเงิน -->
								<jsp:include page="view_billing.jsp"></jsp:include>				
								</c:if> 
								<c:if test="${invoiceBean.status ne 'S'}">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รูปแบบใบเสร็จรับเงิน&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-12">
														<label class="custom-control custom-radio"> 
														<input id="calType2" name="calType" type="radio"
															value="N" class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;ไม่คิดภาษี</span>
														</label>&nbsp;&nbsp;&nbsp;
														<label class="custom-control custom-radio"> 
														<input id="calType3" name="calType" type="radio"
															value="A" class="custom-control-input"> <span
															class="custom-control-indicator"></span> <span
															class="custom-control-description">&nbsp;คิดภาษีมูลค่าเพิ่ม ${companyBean.vat }%</span>
														</label> 
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								</c:if>
								<div class="row mt15">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<jsp:include page="view_vat.jsp"></jsp:include> 
											<jsp:include page="view_no_vat.jsp"></jsp:include>
											<jsp:include page="view_add_vat.jsp"></jsp:include>
										</div>
									</div>
								</div>
								<c:if test="${invoiceBean.invoiceType ne 'O'}">
								<jsp:include page="view_internet_product.jsp"></jsp:include> 
								</c:if>
								<jsp:include page="view_history_print.jsp"></jsp:include>
								
								
								<c:if test="${invoiceBean.billing == true && invoiceBean.status ne 'S' }">
									<div class="row">
										<div class="col-md-12">
											<fieldset>
												<legend>&nbsp;&nbsp;&nbsp;<span class="ti-bookmark-alt"></span> บันทึกเพิ่มเติม&nbsp;&nbsp;&nbsp;</legend>
												<div>
													<div class="row">
														<div class="col-md-12">
															<font style="color: gray;">ใบแจ้งหนี้นี้ คุณ${invoiceBean.personnelBean.firstName }&nbsp;${invoiceBean.personnelBean.lastName } (${invoiceBean.personnelBean.nickName }) ได้ทำการวางบิลกับลูกค้าไว้แล้ว</font>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</div>
								</c:if>
								
								
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
	<script type="text/javascript">
	
	$( document ).ready(function() {
		var billingStatusOld = $('input[type=radio][name=payed]:checked').val();
		$('#calType2').prop("checked", true);
		//radio type cal
		$('input[type=radio][name=calType]').on('change', function() {
		     if($(this).val() == 'V'){
		    	 $('#tbNoVat').hide();
		    	 $('#tbAddVat').hide();
		    	 $('#tbVat').fadeIn();
		    	 convertNumberToThbaht('${invoiceBean.amount}');
		     }else if($(this).val() == 'N'){
		    	 $('#tbNoVat').fadeIn();
		    	 $('#tbVat').hide();
		    	 $('#tbAddVat').hide();
		    	 convertNumberToThbaht('${invoiceBean.amount}');
		     }else if($(this).val() == 'A'){
		    	 $('#tbAddVat').fadeIn();
		    	 $('#tbNoVat').hide();
		    	 $('#tbVat').hide();
		    	 convertNumberToThbaht('${invoiceBean.amount + (invoiceBean.amount * (companyBean.vat/100))}');
		     }
		});
		//radio billing
		$('input[type=radio][name=payed]').on('change', function() {
		     openDialogMigrateInternet($(this).val());
		});
		
		function openDialogMigrateInternet(billingStatus){
			swal({
				title : 'คุณต้องการปรับสถานะของใบบิล',
				type : 'warning',
				showCancelButton : true,
				confirmButtonColor : '#3085d6',
				cancelButtonColor : '#d33',
				confirmButtonText : 'ยืนยันการปรับสถานะของใบบิล',
				confirmButtonClass : 'btn btn-primary btn-lg mr-1',
				cancelButtonClass : 'btn btn-danger btn-lg',
				cancelButtonText : 'ยกเลิก',
				buttonsStyling : false
			}).then(function(isConfirm) {
				if (isConfirm) {
					$('.preloader').modal('show');
					var invoiceDocumentBean = {};
					invoiceDocumentBean.id = '${invoiceBean.id}';
					invoiceDocumentBean.billingStatus = billingStatus;
					
					$.ajax({
						type : "POST",
						contentType : "application/json; charset=utf-8",
						url : "${pageContext.request.contextPath}/invoice/updateBillingStatus",
						dataType : 'json',
						async: false,
						data : JSON.stringify(invoiceDocumentBean),
						//timeout : 100000,
						success : function(data) {
							setTimeout(
									function(){
									$('.preloader').modal('hide');
									}, 200);
						},
						error : function(e) {
							console.log("ERROR: ", e);
						},
						done : function(e) {
							console.log("DONE");
						}
					});
				}else{
					$("input[name=payed][value='"+billingStatusOld+"']").prop("checked",true);
				}
			})
		}
		
	    //convertNumberToThbaht('${invoiceBean.amount}');
	    
	    //check for hide block
	    var statusInvoice = '${invoiceBean.status}';
	    if(statusInvoice == 'S'){
	    	var typeVat = '${invoiceBean.vat}'
	    	if(typeVat == 'V'){
	    		$('#tbNoVat').hide();
		    	$('#tbAddVat').hide();
		    	$('#tbVat').fadeIn();
		    	convertNumberToThbaht('${invoiceBean.amount}');
	    	}else if(typeVat == 'N'){
	    		$('#tbNoVat').fadeIn();
		    	$('#tbVat').hide();
		    	$('#tbAddVat').hide();
		    	convertNumberToThbaht('${invoiceBean.amount}');
		    	$('#calType2').prop("checked", true);
	    	}else if(typeVat == 'A'){
	    		$('#tbAddVat').fadeIn();
		    	$('#tbNoVat').hide();
		    	$('#tbVat').hide();
// 		    	convertNumberToThbaht('${invoiceBean.amount + (invoiceBean.amount * (companyBean.vat/100))}');
		    	convertNumberToThbaht('${invoiceBean.amount}');
		    	$('#calType3').prop("checked", true);
	    	}else{
	    		convertNumberToThbaht('${invoiceBean.amount}');
	    	}
	    }else{
	    	convertNumberToThbaht('${invoiceBean.amount}');
	    }
	  //End check for hide block
	});
	
	
	function convertNumberToThbaht(amount){
		//send convert
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/invoice/convertNumbertoText",
			dataType : 'json',
			async : false,
			data : JSON.stringify(amount),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					$('.textNumber').text(data["result"]);
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
// 		$('#oldcustomersdetail').hide();
// 		$('input[name=memberType]').change(function() {
// 			if (this.value == 'old') {
// 				$('#oldcustomers').fadeIn();
// 				$('#newcustomers').hide();
// 			} else {
// 				$('#oldcustomers').hide();
// 				$('#newcustomers').fadeIn();
// 			}
// 		});

function printInvoiceReceipt(){
	var companyId = $('#company_for_print').val();
	var discount = $('#discount').val();
	window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/${invoiceBean.id }/companyId/"+companyId+"/discount/"+discount,'_blank');
}

	</script>
</body>
</html>
