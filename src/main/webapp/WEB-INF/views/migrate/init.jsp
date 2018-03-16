<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="mainMenu" value="setting" scope="request"/>
<c:set var="subMenu" value="migrate" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>นำเข้าข้อมูลเดิม</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
<style type="text/css">
.custom-file-control:after{content: "เลือกไฟล์ข้อมูลลูกค้า..." !important;}

.choose-custom-file-control:after{content: "" !important;}

.inv-file-control:after{content: "เลือกไฟล์ข้อมูลใบแจ้งหนี้..." !important;}

.product-file-control:after{content: "เลือกไฟล์ข้อมูลอุปกรณ์..." !important;}

.internet-file-control:after{content: "เลือกไฟล์ข้อมูลInternet..." !important;}

</style>
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
					<div class="row mb05 mt05" >
						<div class="col-md-8">
							<h3>นำเข้าข้อมูลเก่า</h3>
						</div>
						<div class="col-md-4 mt05" align="right">
							<a href="#" onclick="openDialogMergeCustomer();" ><button type="button" class="btn btn-warning label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="fa fa-file-excel-o"></i></span>MergeCustomer
							</button></a>
						</div>
					</div>

					<div class="card mt05  mb30">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;รายการนำเข้าข้อมูลเก่า
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
								<div class="col-md-12 mb05">
								<fieldset class="mt15">
									<legend>&nbsp;&nbsp;&nbsp;<span class="ti-id-badge"></span> นำเข้าข้อมูลเก่า&nbsp;&nbsp;&nbsp;</legend>
									<div class="row">
										<div class="col-md-4">
											<label class="custom-file" style="width: 100%;">
												<input type="file" id="cus_file" class="custom-file-input">
												<span id="cus_span" class="custom-file-control"></span>
											</label>
										</div>
										<div class="col-md-2" >
											<a href="#" onclick="openDialogMigrateCustomer();" style="float: left;"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="ion-ios-cloud-upload-outline"></i></span>นำเข้าข้อมูลลูกค้า
											</button></a>
										</div>
										
										<div class="col-md-4">
											<label class="custom-file" style="width: 100%;">
												<input type="file" id="inv_file" class="custom-file-input">
												<span id="inv_span" class="custom-file-control inv-file-control"></span>
											</label>
										</div>
										<div class="col-md-2" >
											<a href="#" onclick="openDialogMigrateInvoce();" style="float: left;"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="ion-ios-cloud-upload-outline"></i></span>นำเข้าข้อมูลใบแจ้งหนี้
											</button></a>
										</div>
									</div>
									<div class="row">
										<div class="col-md-4">
											<label class="custom-file" style="width: 100%;">
												<input type="file" id="product_file" class="custom-file-input">
												<span id="product_span" class="custom-file-control product-file-control"></span>
											</label>
										</div>
										<div class="col-md-2" >
											<a href="#" onclick="openDialogMigrateProduct();" style="float: left;"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="ion-ios-cloud-upload-outline"></i></span>นำเข้าข้อมูลอุปกรณ์
											</button></a>
										</div>
										
										<div class="col-md-4">
											<label class="custom-file" style="width: 100%;">
												<input type="file" id="internet_file" class="custom-file-input">
												<span id="internet_span" class="custom-file-control internet-file-control"></span>
											</label>
										</div>
										<div class="col-md-2" >
											<a href="#" onclick="openDialogMigrateInternet();" style="float: left;"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
												<span class="btn-label"><i class="ion-ios-cloud-upload-outline"></i></span>นำเข้าข้อมูลInternet
											</button></a>
										</div>
										
									</div>
								</fieldset>		
								</div>		
								</div>
							</div>
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
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	
<script type="text/javascript">
var file_customer, file_inv, file_product;
$( document ).ready(function() {
	document.getElementById('cus_file').addEventListener('change', handleFileSelectCus, false);
	
	function handleFileSelectCus(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_customer = evt.target.files[0];
	      if(typeof file_customer !== "undefined"){
			  var cus_span = $('#cus_span');
			  cus_span.addClass('choose-custom-file-control');
	    	  cus_span.text(file_customer.name);
	      }
	}
	
	document.getElementById('inv_file').addEventListener('change', handleFileSelectInv, false);
	
	function handleFileSelectInv(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_inv = evt.target.files[0];
	      if(typeof file_inv !== "undefined"){
			  var inv_span = $('#inv_span');
			  inv_span.removeClass('inv-file-control');
			  inv_span.addClass('choose-custom-file-control');
			  inv_span.text(file_inv.name);
	      }
	}
	
	document.getElementById('product_file').addEventListener('change', handleFileSelectProduct, false);
	
	function handleFileSelectProduct(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_product = evt.target.files[0];
	      if(typeof file_product !== "undefined"){
			  var product_span = $('#product_span');
			  product_span.removeClass('product-file-control');
			  product_span.addClass('choose-custom-file-control');
			  product_span.text(file_product.name);
	      }
	}
	
	document.getElementById('internet_file').addEventListener('change', handleFileSelectInternet, false);
	
	function handleFileSelectInternet(evt) {
	      evt.stopPropagation();
	      evt.preventDefault();
	      file_internet = evt.target.files[0];
	      if(typeof file_internet !== "undefined"){
			  var internet_span = $('#internet_span');
			  internet_span.removeClass('internet-file-control');
			  internet_span.addClass('choose-custom-file-control');
			  internet_span.text(file_internet.name);
	      }
	}
	
});
	
	function openDialogMigrateInternet(){
		swal({
			title : 'คุณต้องการนำเข้าข้อมูล',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการนำเข้าข้อมูล',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				var file_default = new File([""], "filename");
				
				var formData = new FormData();
				if(typeof file_product === "undefined"){
					formData.append("file_internet", file_default);
				}else{
					formData.append("file_internet", file_internet);
				}
				
			 	$('.preloader').modal('show');
				$.ajax({dataType : 'json',
					contentType : "application/json; charset=utf-8",
			    	url : "${pageContext.request.contextPath}/migrate/migrateInternet",
			        data : formData,
			        type : "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        async : true,
			        cache: false,
			        success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/migrate";
						} else {
							$('.preloader').modal('hide');
							openAlert('warning',data);
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
		})
	}

	function openDialogMigrateProduct(){
		swal({
			title : 'คุณต้องการนำเข้าข้อมูล',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการนำเข้าข้อมูล',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				var file_default = new File([""], "filename");
				
				var formData = new FormData();
				if(typeof file_product === "undefined"){
					formData.append("file_product", file_default);
				}else{
					formData.append("file_product", file_product);
				}
				
			 	$('.preloader').modal('show');
				$.ajax({dataType : 'json',
					contentType : "application/json; charset=utf-8",
			    	url : "${pageContext.request.contextPath}/migrate/migrateProduct",
			        data : formData,
			        type : "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        async : true,
			        cache: false,
			        success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/migrate";
						} else {
							$('.preloader').modal('hide');
							openAlert('warning',data);
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
		})
	}	

	function openDialogMigrateCustomer(){
		swal({
			title : 'คุณต้องการนำเข้าข้อมูลลูกค้า',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการนำเข้าข้อมูล',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				var file_default = new File([""], "filename");
				
				var formData = new FormData();
				if(typeof file_customer === "undefined"){
					formData.append("file_customer", file_default);
				}else{
					formData.append("file_customer", file_customer);
				}
				
			 	$('.preloader').modal('show');
				$.ajax({dataType : 'json',
					contentType : "application/json; charset=utf-8",
			    	url : "${pageContext.request.contextPath}/migrate/migrateCustomer",
			        data : formData,
			        type : "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        async : true,
			        cache: false,
			        success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/migrate";
						} else {
							$('.preloader').modal('hide');
							openAlert('warning',data);
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
		})
	}	

	function openDialogMigrateInvoce(){
		swal({
			title : 'คุณต้องการนำเข้าข้อมูลใบแจ้งหนี้',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการนำเข้าข้อมูล',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				var file_default = new File([""], "filename");
				
				var formData = new FormData();
				if(typeof file_inv === "undefined"){
					formData.append("file_inv", file_default);
				}else{
					formData.append("file_inv", file_inv);
				}
				
			 	$('.preloader').modal('show');
				$.ajax({dataType : 'json',
					contentType : "application/json; charset=utf-8",
			    	url : "${pageContext.request.contextPath}/migrate/migrateInvoice",
			        data : formData,
			        type : "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        async : true,
			        cache: false,
			        success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/migrate";
						} else {
							$('.preloader').modal('hide');
							openAlert('warning',data);
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
		})
	}
	
	function openDialogMergeCustomer(){
		swal({
			title : 'คุณต้องการ MergeCustomer',
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการ MergeCustomer',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				var file_default = new File([""], "filename");	
				var formData = new FormData();
				formData.append("file_MergeCustomer", file_default);
				
			 	$('.preloader').modal('show');
				$.ajax({dataType : 'json',
					contentType : "application/json; charset=utf-8",
			    	url : "${pageContext.request.contextPath}/migrate/mergeCustomer",
			        data : formData,
			        type : "POST",
			        enctype: 'multipart/form-data',
			        processData: false,
			        contentType:false,
			        async : true,
			        cache: false,
			        success : function(data) {
						if (data["error"] == false) {
							window.location.href = "${pageContext.request.contextPath}/migrate";
						} else {
							$('.preloader').modal('hide');
							openAlert('warning',data);
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
		})
	}
	
</script>
</body>
</html>
