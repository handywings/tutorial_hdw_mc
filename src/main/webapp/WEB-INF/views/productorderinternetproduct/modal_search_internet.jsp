<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addInternetProduct" tabindex="-1"
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
					<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาสินค้า
				</h4>
			</div>
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row">
					<div class="col-md-6">
						<div class="">
							<label for="exampleInputEmail1"><b>รหัสสินค้า
									ชื่อสินค้า</b></label> <input type="text" class="form-control"
								id="keySearchInternetProduct" placeholder="ระบุรหัสสินค้า หรือชื่อสินค้า">
						</div>
					</div>
					<div class="col-md-4">
						<div class="">
							<label for="exampleInputEmail1"><b>คลังสินค้า</b></label> <select
								name="productCategory" class="form-control" id="stock">
								<option value="">ทั้งหมด</option>
								<c:forEach items="${stockBeans}" var="stockBean" varStatus="i">
									<option value="${stockBean.id}">
										${stockBean.stockName} (${stockBean.company.companyName})</option>
								</c:forEach>
							</select>
						</div>
					</div> 
					<div class="col-md-2 mt05">
						<br>
						<button type="button" class="btn btn-primary btn-block"
							onclick="searchInternetProduct();">ค้นหา</button>
					</div>
				</div>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id=""> 
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสสินค้า
																/ Serial Number</center></th>
														<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
														<th style="vertical-align: middle;"><center>จำนวนสินค้าในคลัง</center></th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${internetProductBeans}" var="internetProductBean"
														varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-radio"> <input
																		name="radioInternetProduct" type="radio"
																		value="${internetProductBean.id }"
																		class="custom-control-input"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description">&nbsp;</span>
																	</label>
																</center></td>
															<td><center>${i.count}</center></td>
															<td><center><a href='${pageContext.request.contextPath}/productorderinternetproduct/detail/${internetProductBean.id }' target='_blank'><b>${internetProductBean.productCode }</b></a></center></td>
															<td>${internetProductBean.productName }</td>
															<td><center>
																${internetProductBean.internetProductBeanItems.size() }</center></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>

								</div>
							</div> 
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="appendToBlockChild();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$( document ).ready(function() {
		$('#table-1').dataTable({searching: false, paging: true});
	});
	
	function openModalSearchInternet(){
		$('#addInternetProduct').modal('show');
	} 
	
	function appendToBlockChild(){
		var radioInternetProduct = $("input:radio[name ='radioInternetProduct']:checked").val();
		if(typeof radioInternetProduct != 'undefined'){
			loadInternetProductItem(radioInternetProduct);
			$('#addInternetProduct').modal('hide');
		}
	}
	
	function loadInternetProductItem(id){
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/loadInternetProductWithId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"]["internetProductBeanItems"].length;i++){
						var startRow = "<tr class='trOldItem'>";
						var endRow = "</tr>";
						var columnOrderCount = "<td><center>"+ (i+1) +"</center></td>";
						var columnInterRef = "<td><center>"+ data["result"]["internetProductBeanItems"][i].reference +"</center></td>";
						var columnInterUsername = "<td><center>"+ data["result"]["internetProductBeanItems"][i].userName +"</center></td>";
						var columnInterPassword = "<td><center>"+ data["result"]["internetProductBeanItems"][i].password +"</center></td>";
						var columnAction = "<td></td>";
						
						rows = rows + startRow + columnOrderCount;
						rows = rows + columnInterRef + columnInterUsername + columnInterPassword;
						rows = rows + columnAction + endRow;
					}
					$('#tableInternetProductChild > tbody').html(rows);
					//append header master
					$('#textMasterInternetPackageName').text(data["result"].productName);
					$('#textMasterInternetPackageCode').text(data["result"].productCode);
					$('#textMasterInternetPackageStock').text(data["result"]["stock"].stockName);
					
					var countInternetProductOld = data["result"]["internetProductBeanItems"].length;
					if(countInternetProductOld > 0){
						$('#spanMasterInternetProductOrderCount').text(countInternetProductOld + 1);
					}else{

						$('#spanMasterInternetProductOrderCount').text("1");
					}
					
					//set ProductInternetId
					$('#hiddenProductInternetId').val(data["result"].id);
					$('#aBtnSubmit').hide();
					$('#aBtn').show();
					
					setTimeout(
						function(){
						$('.preloader').modal('hide');
						}, 200);
					
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
	
	function searchInternetProduct(){
		searchProcess();
	}
	
	function resetSearchEquipmentProduct(){
		$('#keySearchInternetProduct').val("");
		$("#stock").prop("selectedIndex", 0);
		searchProcess();
	}
	
	function searchProcess(){
		$('.preloader').modal('show');
		
		var internetProductBean = {};
		internetProductBean.productName = $('#keySearchInternetProduct').val();
		 
		//Stock
		var StockBean = {};
		StockBean.id = $("#stock").val();
		internetProductBean.stock = StockBean;
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productorderinternetproduct/loadInternetProduct",
			dataType : 'json',
			async: false,
			data : JSON.stringify(internetProductBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					//create data table search
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var columnRadio = "<td><center>" +
							"<label class='custom-control custom-radio'>" + 
							"<input name='radioInternetProduct' type='radio'"+
							" value='"+ data["result"][i]["id"] +"'" +
							"class='custom-control-input'>" +
							"<span class='custom-control-indicator'></span>" +
							"<span class='custom-control-description'>&nbsp;</span>" +
							"</label></center></td>";
							
						var columnOrderCount = "<td><center>"+(i+1)+"</center></td>";
						
						var columnProductCode = "<td><center><a href='${pageContext.request.contextPath}/productorderinternetproduct/detail/" + data["result"][i]["id"] + "' target='_blank'><b>"+ data["result"][i]["productCode"] +"</b></a></center></td>";
						
						var columnProductName = "<td>"+ data["result"][i]["productName"] +"</td>";
						
						var columnProductItemCount = "<td><center>" + data["result"][i]["internetProductBeanItems"].length 
						+"</center></td>";
						
						rows = rows + startRow+columnRadio+columnOrderCount+columnProductCode;
						rows = rows + columnProductName+columnProductItemCount+endRow;
					}
					
					//var emptyData = "";
					if(rows.length > 0){
						$('#table-1 > tbody').html(rows);
					}else{
						$('#table-1 > tbody').empty();
					}
					
					setTimeout(
						function() 
							  {
							$('.preloader').modal('hide');
							  }, 200);
					
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
	
</script>




