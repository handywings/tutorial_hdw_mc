<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>


	<div class="modal fade" id="editDailog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" align="left">
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
						<label for="exampleInputEmail1"><b>ชื่อ package</b></label>
						<input type="hidden" id="internetProductId" value="" />
						<input type="text" class="form-control" id="internetProductName" aria-describedby="emailHelp" value="">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>รหัสสินค้า</b></label>
						<input type="text" class="form-control" id="internetProductCode" aria-describedby="emailHelp" value="">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
						<label for="exampleInputEmail1"><b>คลังสินค้า</b></label>
						<select class="form-control" id="stockId">
						<c:forEach var="stocks" items="${stocks}" varStatus="i">
							<option value="${stocks.id}" >${stocks.stockName} (${stocks.company.companyName})</option>
						</c:forEach>
						</select>
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

	function editInternetProduct(id){
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/get/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				console.log(data);
				if(data["error"] == false){
					$('#internetProductId').val(data["result"]["id"]);
					$('#internetProductName').val(data["result"]["productName"]);
					$('#internetProductCode').val(data["result"]["productCode"]);
					
					var stockId = data["result"]["stock"]["id"];
					$('#stockId').val(stockId);
					
					$('#editDailog').modal('show');
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
		var internetProductBean = generateEditInternetProductJson();
		//validate basic
		console.log(internetProductBean);
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/update",
			dataType : 'json',
			data : JSON.stringify(internetProductBean),
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
	
	function generateEditInternetProductJson(){

		var internetProductBean = {};
		internetProductBean.id = $('#internetProductId').val();
		internetProductBean.productName = $('#internetProductName').val();
		internetProductBean.productCode = $('#internetProductCode').val();
		
		var stockBean = {};
		stockBean.id = $('#stockId').val();
		
		internetProductBean.stock = stockBean;
		
		return internetProductBean;
	}


</script>

