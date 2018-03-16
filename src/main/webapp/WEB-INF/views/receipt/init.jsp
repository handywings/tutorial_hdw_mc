<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="finance" scope="request"/>
<c:set var="subMenu" value="receipt" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบเสร็จรับเงิน / ใบกำกับภาษี</title>
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
							<h3>รายการใบเสร็จรับเงิน / ใบกำกับภาษี</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบการเงิน</li>
								<li class="breadcrumb-item active">รายการใบเสร็จรับเงิน / ใบกำกับภาษี</li>
							</ol>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบเสร็จรับเงิน
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form id="wSeachReceipt" method="post" commandName="receiptSearchBean" action="${pageContext.request.contextPath}/receipt/search">
										<div class="col-md-12 mb05">
											<input type="hidden" id="itemPerPage" name="itemPerPage" value="${receiptSearchBean.itemPerPage}" />
											<input type="text" name="key" class="form-control" value="${receiptSearchBean.key}"
												placeholder="เลขที่ใบเสร็จรับเงิน ชื่อสกุล อีเมล์ หรือเลขโทรศัพท์ ของลูกค้า...">
										</div>

										<div class="col-md-5 mb05">
											<select name="invoiceType" class="form-control">
												<option value="" selected="selected">ประเภทใบเสร็จรับเงินทั้งหมด</option>
												<option value="S" <c:if test="${receiptSearchBean.paymentType eq 'S'}">selected="selected"</c:if> >ลูกค้าติดตั้งใหม่</option>
												<option value="O" <c:if test="${receiptSearchBean.paymentType eq 'O'}">selected="selected"</c:if> >ค่าบริการตามรอบบิล</option>
												<option value="R" <c:if test="${receiptSearchBean.paymentType eq 'R'}">selected="selected"</c:if> >แจ้งซ่อม</option>
											</select>
										</div>
										<div class="col-md-5 mb05">
											<input class="form-control" type="text" id="daterange" name="daterange" value="${receiptSearchBean.daterange}" placeholder="วันที่ชำระเงิน">
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
					<div class="tab-content">
						<jsp:include page="block_data.jsp"></jsp:include>
					</div>
				</div>
			</div>
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
			
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
											</form>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-info" data-dismiss="modal" onclick="printInvoiceReceipt();">ตกลง</button>
										</div>
									</div>
								</div>
							</div>
							
		</div>
	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	
	<script src="<c:url value="/resources/assets/plugins/datepicker-th/js/bootstrap-datepicker-custom.js" />"></script>
    <script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
	
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
		
	<script type="text/javascript">
	var invoiceId = 0;
		$(document).ready(function() {
			<c:if test="${empty invoiceSearchBean.daterange}">
			$('#daterange').val('');
			</c:if>
		});
	
		$('#daterange').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
			language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
            thaiyear: true              //Set เป็นปี พ.ศ.
			
		});
		
		function setTab(tab){
		    $('#tab').val(tab);
		};
		
		function printInvoiceReceipt(){
			var companyId = $('#company_for_print').val();
			window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/"+invoiceId+"/companyId/"+companyId,'_blank');
		}
		
		function setInvoiceId(id){
			invoiceId = id;
		}
		
	</script>

</body>
</html>
