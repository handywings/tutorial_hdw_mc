<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="stock" scope="request" />
<c:set var="subMenu" value="productoverview" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
<title>ข้อมูลคลังสินค้า</title>
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
							<h3>ข้อมูลคลังสินค้า</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active">ข้อมูลคลังสินค้า</li>
							</ol>
						</div>
					</div>
					<div class="row row-md mt05">
						<div class="col-lg-3 col-md-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1 mb-2">
								<div class="t-icon right">
									<span class="bg-danger"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase">สินค้าทั้งหมด</h4>
									<h1 style="font-weight: 100">
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value="${textUtil.getFormatNumberInt(stock.allProducts) }" />
										รายการ
									</h1>

								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1">
								<div class="t-icon right">
									<span class="bg-warning"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase">สินค้าใกล้หมด</h4>
									<h1 style="font-weight: 100">
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value="${textUtil.getFormatNumberInt(stock.lowInStock)}" />
										รายการ
									</h1>

								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1">
								<div class="t-icon right">
									<span class="bg-success"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase">สินค้าหมด</h4>
									<h1 style="font-weight: 100">
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value="${textUtil.getFormatNumberInt(stock.outOfStock)}" />
										รายการ
									</h1>

								</div>
							</div>
						</div>
						<div class="col-lg-3 col-md-6 col-xs-12">
							<div class="box box-block bg-white tile tile-1">
								<div class="t-icon right">
									<span class="bg-primary"></span><i class="ti-package"></i>
								</div>
								<div class="t-content">
									<h4 class="text-uppercase">สินค้าหมดประกัน</h4>
									<h1 style="font-weight: 100">
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value="${textUtil.getFormatNumberInt(stock.insuranceExpire)}" />
										รายการ
									</h1>

								</div>
							</div>
						</div>

					</div>



					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการหมวดหมู่วัสดุอุปกรณ์
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<div class="col-md-2 mt05">
										<b>คลังสินค้าของ : </b>
									</div>
									<form action="admindashboard" method="post">
										<div class="col-md-8 mt05">
											<div class="form-group">
												<select class="form-control" id="select_company">
													<option value="0">--- แสดงคลังสินค้าของทุกบริษัท
														---</option>
													<c:forEach var="companys" items="${companys}" varStatus="i">
														<option value="${companys.id}">${companys.companyName}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="col-md-2 mt05" align="center">
											<perm:permission object="M16.add">
												<button type="button" data-toggle="modal"
													data-target="#addStock"
													class="btn btn-info label-left float-xs-right col-xs-12">
													<span class="btn-label"><i class="ti-plus"></i></span>สร้างคลังสินค้า
												</button>
											</perm:permission>
										</div>
									</form>
								</div>
								<div class="row">
									<div class="col-md-12">
										<hr>
									</div>
								</div>

								<div class="table-responsive">
									<table class="table table-bordered table-hover"
										id="table_stock">
										<thead class="thead-default">
											<tr>
												<th style="width: 125px;"><center>รหัสคลังสินค้า</center></th>
												<th style="width: 200px;"><center>คลังสินค้า</center></th>
												<th><center>บริษัท</center></th>
												<th><center>สินค้าทั้งหมด</center></th>
												<th><center>สินค้าใกล้หมด</center></th>
												<th><center>สินค้าหมด</center></th>
												<th><center>สินค้าหมดประกัน</center></th>
												<th style="width: 70px;"></th>
											</tr>
										</thead>
										<tbody>

											<c:choose>
												<c:when test="${stocks != null && stocks.size() > 0 }">
													<c:forEach var="stock" items="${stocks}" varStatus="i">
														<tr>
															<td align="center">${stock.stockCode}</td>
															<td><b class="text-bleu">${stock.stockName}<a><span
																		class="glyphicon glyphicon-info-sign"
																		aria-hidden="true"></span></a></b><br> <font
																style="color: gray;"><small>${stock.address.detail}</small></font>
																<br></td>
															<td style="vertical-align: middle;" align="center">${stock.company.companyName}</td>
															<td style="vertical-align: middle;" align="left"><center>${textUtil.getFormatNumberInt(stock.allProducts)}
																	รายการ</center></td>
															<td style="vertical-align: middle;">
																<center>${textUtil.getFormatNumberInt(stock.lowInStock)}
																	รายการ</center>
															</td>
															<td style="vertical-align: middle;">
																<center>${textUtil.getFormatNumberInt(stock.outOfStock)}
																	รายการ</center>
															</td>
															<td style="vertical-align: middle;">
																<center>${textUtil.getFormatNumberInt(stock.insuranceExpire)}
																	รายการ</center>
															</td>
															<td align="center" style="vertical-align: middle;">
																<perm:permission object="M16.edit">
																	<a class="cursor-p" data-toggle="modal"
																		onclick="editStock(${stock.id})"><span
																		class="ti-pencil-alt" data-toggle="tooltip"
																		data-placement="bottom" data-original-title="แก้ไข"></span></a>
																</perm:permission> <perm:permission object="M16.delete">
																	<a class="run-swal cursor-p"><span class="ti-trash"
																		onclick="openDialogDelete(${stock.id });"
																		data-toggle="tooltip" data-placement="bottom"
																		data-original-title="ลบ"></span></a>
																</perm:permission>
															</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="8"><center>ไม่พบข้อมูล</center></td>
													</tr>
												</c:otherwise>
											</c:choose>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- modal add -->
			<jsp:include page="modal_add.jsp"></jsp:include>
			<!-- end modal add -->

			<!-- modal edit -->
			<jsp:include page="modal_edit.jsp"></jsp:include>
			<!-- end modal edit -->

			<!-- modal edit -->
			<jsp:include page="modal_delete.jsp"></jsp:include>
			<!-- end modal edit -->

			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">

$('#select_company').change(function(){
	var id = $(this).val();
	$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/productoverview/getByCompany/json/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var html = '<thead class="thead-default">\
						<tr>\
					<th style="width: 125px;"><center>รหัสคลังสินค้า</center></th>\
					<th style="width: 200px;"><center>คลังสินค้า</center></th>\
					<th><center>บริษัท</center></th>\
					<th><center>สินค้าทั้งหมด</center></th>\
					<th><center>สินค้าใกล้หมด</center></th>\
					<th><center>สินค้าหมด</center></th>\
					<th><center>สินค้าหมดประกัน</center></th>\
					<th style="width: 70px;"></th>\
				</tr>\
			</thead>';
					$.each( data["result"], function( key, value ) {
						html += '<tr><td>'+value.stockCode+'</td>\
							<td><b class="text-bleu">'+value.stockName+'<a><span\
										class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a></b><br>\
								<font style="color: gray;"><small>'+value.address.detail+'</small></font> <br>\
							</td>\
							<td style="vertical-align: middle;" align="center">'+value.company.companyName+'</td>\
							<td style="vertical-align: middle;" align="left">\
							<center>'+value.allProducts+' รายการ</center></td>\
							<td style="vertical-align: middle;">\
							<center>'+value.lowInStock+' รายการ</center></td>\
							<td style="vertical-align: middle;">\
							<center>'+value.outOfStock+' รายการ</center></td>\
							<td style="vertical-align: middle;">\
							<center>'+value.insuranceExpire+' รายการ</center></td>\
							<td align="center" style="vertical-align: middle;">\
							<a class="cursor-p" data-toggle="modal"\
							onclick="editStock('+value.id+')"><span\
							class="ti-pencil-alt" data-toggle="tooltip"\
							data-placement="bottom"\
							data-original-title="แก้ไข"></span></a>\
							<a class="run-swal cursor-p"><span class="ti-trash" \
							 onclick="openDialogDelete('+value.id+');"\
							data-toggle="tooltip" data-placement="bottom"\
							data-original-title="ลบ"></span></a>\
							</td>\
						</tr>';
					});
					$('#table_stock').html(html);
				}
				setTimeout(function() {$('.preloader').modal('hide');}, 500);
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
});

</script>

</body>
</html>
