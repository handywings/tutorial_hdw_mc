<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


	<div class="modal fade" id="editDailogItem" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
		<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">แก้ไขข้อมูล INTERNET USER</h4>
			</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>UserName</b></label>
						<input type="hidden" id="internetProductItemId" value="" />
						<input type="text" class="form-control" id="internetProductUserName" value="">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>Password</b></label>
						<input type="text" class="form-control" id="internetProductPassword" value="">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>Reference</b></label>
						<input type="text" class="form-control" id="internetProductReference" value="">
						</div>
					</div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="updateInternetProduct();">บันทึก</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	function editInternetProductItem(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/get/detail/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				console.log(data);
				if(data["error"] == false){
					$('#internetProductItemId').val(data["result"]["id"]);
					$('#internetProductUserName').val(data["result"]["userName"]);
					$('#internetProductPassword').val(data["result"]["password"]);
					$('#internetProductReference').val(data["result"]["reference"]);
					
					$('#editDailogItem').modal('show');
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
	
	//update
	function updateInternetProduct(){
		//generate bean
		var internetProductItemBean = generateEditInternetProductItemJson();
		//validate basic
// 		console.log(internetProductItemBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/item/update",
			dataType : 'json',
			data : JSON.stringify(internetProductItemBean),
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
	
	function generateEditInternetProductItemJson(){

		var internetProductItemBean = {};
		internetProductItemBean.id = $('#internetProductItemId').val();
		internetProductItemBean.userName = $('#internetProductUserName').val();
		internetProductItemBean.password = $('#internetProductPassword').val();
		internetProductItemBean.reference = $('#internetProductReference').val();
		
		return internetProductItemBean;
	}


</script>

