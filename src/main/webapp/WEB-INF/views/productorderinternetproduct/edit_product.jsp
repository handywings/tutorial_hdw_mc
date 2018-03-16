<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="productorderequipmentproduct" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>แก้ไขสินค้า</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<link rel="stylesheet" href="<c:url value="/resources/assets/plugins/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/assets/plugins/bootstrap-daterangepicker/daterangepicker.css" />">
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
							<h3>${internetProductBean.productName }</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderinternetproduct">รายการสินค้าและบริการ</a></li>
								<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/productorderinternetproduct/detail/${internetProductBean.id}">ข้อมูลสินค้า</a></li>
								<li class="breadcrumb-item active">แก้ไขสินค้า</li>
							</ol>
						</div>
					</div>
					<div class="card mt05 mb30" id="block_header_detail_equipment_newitem">
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;ข้อมูลสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div class="row">
												<div class="col-md-6">
													<b>ชื่อ package<span class="text-red"> *</span></b><br>
													<input type="hidden" id="internetProductId" class="form-control" value="${internetProductBean.id}">
													<input type="text" id="internet_packageName" class="form-control" value="${internetProductBean.productName}">
												</div>
												<div class="col-md-3">
													<b>รหัสสินค้า<span class="text-red"> *</span></b><br>
													<input type="text" id="internet_code" class="form-control" value="${internetProductBean.productCode}">
												</div>
												<div class="col-md-3">
													<b>คลังสินค้า<span class="text-red"> *</span></b><br>
													<select class="form-control" id="stockId">
													<c:forEach var="stocks" items="${stocks}" varStatus="i">
														<c:choose>
															<c:when test="${stocks.id == internetProductBean.stock.id}">
																<option value="${stocks.id}" selected="selected">
																	${stocks.stockName} (${stocks.company.companyName})
																</option>
															</c:when> 
															<c:otherwise>
																<option value="${stocks.id}">
																	${stocks.stockName} (${stocks.company.companyName})
																</option>
															</c:otherwise>
														</c:choose>
													
													</c:forEach>
													</select>
												</div>
											</div>
											<div class="row mt15">
											<div class="col-md-12">
												<div class="table-responsive">
												<table class="table table-bordered mb-0 table-hover" id="tableEquipmentProductChild">
													<thead class="thead-bg">
														<tr>
															<th class="td-middle" style="width: 20px;"><center>ลำดับ</center></th>
															<th class="td-middle"><center>UserName<span class="text-red"> *</span></center></th>
															<th class="td-middle"><center>Password<span class="text-red"> *</span></center></th>
															<th class="td-middle"><center>Reference<span class="text-red"> *</span></center></th>
															<th class="td-middle" style="width: 60px;"><center>Status</center></th>
														</tr>
													</thead>
													<tbody id="tbodyNewItem">
													<c:forEach items="${internetProductBean.internetProductBeanItems}" var="productItem" varStatus="i">
														<c:choose>
															<c:when test="${productItem.status == '0'}">
																<tr class="trNewItem">
																	<td class="td-middle"><center>${i.count }</center></td>
																	<td>
																		<input type="hidden" class="form-control internetProductItemId" value="${productItem.id}">
																		<input type="text" class="form-control masterNetUsername" value="${productItem.userName}">
																		<div class="messages"></div>
																	</td>
																	<td>
																		<input type="text" class="form-control masterNetPassword" value="${productItem.password}">
																	</td>
																	<td>
																		<input type="text" class="form-control masterNetRef" value="${productItem.reference}">
																	</td>
																	<td>
																		<center><span class="text-green"><b>ปรกติ</b></span></center>
																	</td>
																</tr>
															</c:when>
															<c:when test="${productItem.status == '5'}">
																<tr class="trNewItem">
																	<td class="td-middle"><center>${i.count }</center></td>
																	<td>
																		<input type="hidden" class="form-control internetProductItemId" value="${productItem.id}">
																		<input type="text" disabled="disabled" class="form-control masterNetUsername" value="${productItem.userName}">
																		<div class="messages"></div>
																	</td>
																	<td>
																		<input type="text" disabled="disabled" class="form-control masterNetPassword" value="${productItem.password}">
																	</td>
																	<td>
																		<input type="text" disabled="disabled" class="form-control masterNetRef" value="${productItem.reference}">
																	</td>
																	<td>
																		<center><span class="text-red"><b>ใช้งาน</b></span></center>
																	</td>
																</tr>
															</c:when>
														</c:choose>
														
														
														
														
														
														
														
														
														
														
														
														
														
														
														
														
														
														</c:forEach>
													</tbody>
												</table>
												</div>
											</div>
										</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15 mb15" align="center">
							<a>
									<button type="button"  onclick="updateProduct();" 
										class="btn btn-info label-left mr-0-5">
										<span class="btn-label"><i class="ti-check"></i></span>บันทึก
									</button>
								</a>
							<a href="${pageContext.request.contextPath}/productorderinternetproduct">
								<button type="button"  
											class="btn btn-danger label-left mr-0-5">
											<span class="btn-label"><i class="ti-close"></i></span>ยกเลิก
								</button> 
							</a>
							
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
		$(document).ready(function() {
			//added 16-05-2017 blind textbox username for check dup
			$( ".masterNetUsername" ).keyup(function() {
				var idInternetItem = $(this).prev('.internetProductItemId').val();
				if(checkDuplicateUsername($(this).val(),idInternetItem)){
					$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
				}else{
					$(this).next('.messages').html("");
				}
			});
			
		});
		
		function updateProduct(){
			var countDup = 0;
			//$('.preloader').modal('show');
			//create bean
			//equipment bean
			var internetProductBean = {};
			internetProductBean.id = $('#internetProductId').val();
			internetProductBean.productName = $('#internet_packageName').val();
			internetProductBean.productCode = $('#internet_code').val();

			//stock
			StockBean = {};
			StockBean.id = $('#stockId').val();
			internetProductBean.stock = StockBean;
			
			//product item
			var productItem = [];
			$('#tableEquipmentProductChild > tbody  > tr.trNewItem').each(function() {
				var item = {};
				 $(this).find('input').each(function (i) {
						switch (i) {
								case 0:
									item.id = $(this).val();
									break;
								case 1:
									item.userName = $(this).val();
									var idInternetItem = $(this).prev('.internetProductItemId').val();
									
									if(checkDuplicateUsername($(this).val(),idInternetItem)){
										countDup = countDup+1;
										$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
									}
									break;
								case 2:
									item.password = $(this).val();
									break;
								case 3:
									item.reference = $(this).val();
									break;
								}
						
							}); //end find textbox
				 		//add to arrayjson
				 			productItem.push(item);
						}); //end find tr
						//add to EquipmentProductBean
						internetProductBean.internetProductBeanItems = productItem;
						console.log(internetProductBean);
						
					if(countDup == 0){
					//send new save
					$('.preloader').modal('show');
						$.ajax({
							type : "POST",
							contentType : "application/json; charset=utf-8",
							url : "${pageContext.request.contextPath}/productorderinternetproduct/updateInternetProduct",
							dataType : 'json',
							async : false,
							data : JSON.stringify(internetProductBean),
							//timeout : 100000,
							success : function(data) {
								if (data["error"] == false) {
									window.location.reload();
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
				}
		
		function checkDuplicateUsername(username,id){
			//send check dup
			var checkDup = false;
			var internetProductBeanItem = {};
			internetProductBeanItem.id = id;
			internetProductBeanItem.userName = username;
			
			$.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/productorderinternetproduct/checkDuplicateUsernameForUpdate",
				//dataType : 'json',
				async : false,
				data : JSON.stringify(internetProductBeanItem),
				//timeout : 100000,
				success : function(data) {
					if (data["error"] == false) {
						if(data["result"] == true){
							checkDup = true;
						}else{
							checkDup = false;
						}
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
			
			return checkDup;
		}
		
		</script>
		
</body>
</html>
