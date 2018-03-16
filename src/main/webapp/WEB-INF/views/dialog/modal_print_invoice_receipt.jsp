<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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
<!-- 						<label for="recipient-name" class="form-control-label">บริษัท</label> -->
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
<script type="text/javascript">
function printInvoiceReceipt(){
	var companyId = $('#company_for_print').val();
	window.open("${pageContext.request.contextPath}/financialreport/invoiceOrReceipt/exportPdf/invoiceId/${invoiceBean.id }/companyId/"+companyId,'_blank');
}
</script>

