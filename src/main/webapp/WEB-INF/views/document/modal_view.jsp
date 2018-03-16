<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="viewOrg" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabelView">ข้อมูลเอกสาร</h4>
			</div>
			<div class="modal-body">
				<div class="row" id="div_pdf">
				
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-culture"></i>&nbsp;&nbsp;เลขที่เอกสาร :</b>
					</div>
					<div class="col-md-7 col-xs-6">
						 <span id="spn-view-code"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-map-marker"></i>&nbsp;&nbsp;ชื่อเอกสาร :</b>
					</div>
					<div class="col-md-7 col-xs-6">
					<span id="spn-view-name"></span>
					</div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-call"></i>&nbsp;&nbsp;ประเภทเอกสาร :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-type"></span></div>
				</div>
				<div class="row">
					<div class="col-md-5 col-xs-6">
						<b><i class="pe-7s-mail"></i>&nbsp;&nbsp;สิทธิ์การเข้าใช้เอกสาร :</b>
					</div>
					<div class="col-md-7 col-xs-6"><span id="spn-view-permission"></span></div>
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
		url : "${pageContext.request.contextPath}/document/get/json/"+id,
		dataType : 'json',
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){
				$('#spn-view-code').text(data["result"]["document_code"]);
				$('#spn-view-name').text(data["result"]["document_name"]);
				
				var type = data["result"]["document_type"];
				if(1 == type){
					type = "Material approve";
				}else if(2 == type){
					type = "RFI";
				}else if(3 == type){
					type = "LTR";
				}else if(4 == type){
					type = "VO";
				}else if(5 == type){
					type = "Payment";
				}else if(6 == type){
					type = "Shop drawing";
				}else if(7 == type){
					type = "SI";
				}
				$('#spn-view-type').text(type);
				
				var permission = data["result"]["permission"];
				if('A' == permission){
					permission = "ทำได้หมด";
				}else if('R' == permission){
					permission = "อ่านเท่านั้น";
				}
				$('#spn-view-permission').text(permission);
				
				var iframe = '<iframe width="100%" height="500px" src="http://docs.google.com/gview?url=https://www.synergymicrotech.com/demo/images/'+data["result"]["document_path"]+'&embedded=true" onload="javascript:this.contentWindow.location.hash=\':0.page.20\';">';
				
				$('#div_pdf').html(iframe);
				
				
				
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
