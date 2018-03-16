<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="modalConfirmPay" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left"
	style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">
					<i class="zmdi zmdi-search"></i>&nbsp;รูปแบบการชำระเงิน
				</h4>
			</div>
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row"></div>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-12">
							 <label for="exampleInputEmail1"><b>รูปแบบการชำระเงิน</b></label><br>
								<label class="custom-control custom-radio"> 
								<input id="pay_vat" name="pay_vat_type" type="radio" checked="checked" 
									value="V" class="custom-control-input"> <span
									class="custom-control-indicator"></span> <span
									class="custom-control-description"> ถอด Vat</span>
								</label> &nbsp;
								<label class="custom-control custom-radio"> 
								<input id="pay_no_vat" name="pay_vat_type" type="radio"
									value="N" class="custom-control-input"> <span
									class="custom-control-indicator"></span> <span
									class="custom-control-description"> ไม่คิด Vat</span>
								</label> &nbsp;
								<label class="custom-control custom-radio"> 
								<input id="pay_add_vat" name="pay_vat_type" type="radio"
									value="A" class="custom-control-input"> <span
									class="custom-control-indicator"></span> <span
									class="custom-control-description"> เพิ่ม Vat</span>
								</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div style="float:right">
									<button type="button" class="btn btn-success" onclick="loadTypeVat();">ตกลง</button>
								</div>
							</div>
							<div class="col-md-6">
								<div style="float:left">
									<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
								</div>
							</div>
						</div>
					</div>
				</div>
<!-- 				<div class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-success" onclick="loadTypeVat();">ตกลง</button> -->
<!-- 					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button> -->
<!-- 				</div> -->
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript">
	function openModalConfirmPay(){
		$('#modalConfirmPay').modal('show');
	}
	 	function loadTypeVat(){
	 		var radioTypeVat = $('input[name = "pay_vat_type"]:checked').val();
	 		savePayment(radioTypeVat);
	 	}
	</script>