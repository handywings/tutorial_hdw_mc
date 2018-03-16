<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


<div class="modal fade" id="viewOrg" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabelView">ข้อมูลบริษัทย่อย</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-culture"></i>&nbsp;&nbsp;ชื่อบริษัท
							:</b>
					</div>
					<div class="col-md-7 col-xs-6">
						 <span id="spn-view-companyName"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-map-marker"></i>&nbsp;&nbsp;ที่อยู่ :</b>
					</div>
					<div class="col-md-7 col-xs-6">
					<span id="spn-view-address"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-call"></i>&nbsp;&nbsp;โทร :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-mobile"></span></div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-mail"></i>&nbsp;&nbsp;Email :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-email"></span></div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-print"></i>&nbsp;&nbsp;Fax :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-fax"></span></div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-id"></i>&nbsp;&nbsp;เลขที่ผู้เสียภาษี
							:</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-taxId"></span></div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-ticket"></i>&nbsp;&nbsp;ภาษีมูลค่าเพิ่ม :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-vat"></span> %</div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-date"></i>&nbsp;&nbsp;Inv เครดิต :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-invCredit"></span> วัน</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">ปิดหน้าต่าง</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

function viewCompany(id){
	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "${pageContext.request.contextPath}/company/get/json/"+id,
		dataType : 'json',
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				$('#spn-view-companyName').text(data["result"]["companyName"]);
				$('#spn-view-address').text(data["result"]["address"]["detail"]);
				$('#spn-view-mobile').text(data["result"]["contact"]["mobile"]);
				$('#spn-view-email').text(data["result"]["contact"]["email"]);
				$('#spn-view-fax').text(data["result"]["contact"]["fax"]);
				$('#spn-view-taxId').text(data["result"]["taxId"]);
				$('#spn-view-vat').text(data["result"]["vat"].toString());
				$('#spn-view-invCredit').text(data["result"]["invCredit"]);
				$('#viewOrg').modal('show');
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
