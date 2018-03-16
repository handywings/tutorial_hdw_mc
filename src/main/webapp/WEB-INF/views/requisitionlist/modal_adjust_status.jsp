<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

							<div class="modal fade" id="changeStatus" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left" style="display: none;" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">×</span>
											</button>
											<h4 class="modal-title" id="myModalLabel">ปรับสถานะใบเบิกสินค้า</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>สถานะใบเบิกสินค้า<span class="text-red"> *</span></b></label>
													<select id="status" class="form-control">
														<option value="N">ปกติ</option>
														<option value="C">ยกเลิก</option>
													</select>
												</div>
											</div>
											<div class="row mt05">
												<div class="col-md-12">
													<label for="exampleInputEmail1"><b>เหตุผล<span class="text-red"> *</span></b></label>
													<textarea class="form-control" id="remarkStatus" rows="3"></textarea>
												</div>
											</div>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-success" onclick="adjustStatus();">บันทึก</button>
											<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
										</div>
									</div>
								</div>
							</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
function adjustStatus(){
	//generate bean
	var bean = generateJson();
//		console.log(bean);
	//validate basic
	$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/requisitionlist/updatestatus",
		dataType : 'json',
		data : JSON.stringify(bean),
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

function generateJson(){
	var requisitionDocumentBean = {};
	
	requisitionDocumentBean.id = "${requisitionDocumentBean.id}";
	
	var status = {}
	status.stringValue = $('#status').val();
	requisitionDocumentBean.status = status;
	requisitionDocumentBean.remarkStatus = $('#remarkStatus').val();

	return requisitionDocumentBean;
}
</script>




