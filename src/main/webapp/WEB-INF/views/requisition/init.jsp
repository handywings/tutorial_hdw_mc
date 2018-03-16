<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="requisition" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>เบิกสินค้าและอุปกรณ์</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
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
							<h3>เบิกสินค้าและอุปกรณ์</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
								<li class="breadcrumb-item active">เบิกสินค้าและอุปกรณ์</li>
							</ol>
						</div>
					</div>
					<div class="card mt05 mb15">
						<div class="posts-list posts-list-1">
							<div class="pl-item">

								<div class="row">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายละเอียดการขอเบิกสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div>
												<div class="row">
													<div class="col-md-6 mb05">
														<b>เบิกเพื่อ :</b><br> <select id="withdraw"
															name="categoryID" class="form-control">
															<option value="R">เบิกเพื่อไปสำรอง
																(ยังไม่ตัดสต็อก)</option>
															<option value="O">เบิกเพื่องานสำนักงาน
																(ตัดสต็อกทันที)</option>
															<option value="U">เบิกเพื่อปรับปรุงลดสต็อก
																(ตัดสต็อกทันที)</option>
														</select>
													</div>
													<div class="col-md-3 mb05" id="div_technicianGroup">
														<b>ทีมช่างผู้ขอเบิก :</b><br> 
														<select id="technicianGroup" name="technicianGroup" class="form-control">
															<c:forEach var="technicianGroupBean" items="${technicianGroupBeans}" varStatus="i">
															<option value="${technicianGroupBean.id}">${technicianGroupBean.personnel.firstName}&nbsp
															${technicianGroupBean.personnel.lastName}&nbsp;
															( ${technicianGroupBean.technicianGroupName} )</option>
															</c:forEach>
														</select>
													</div>
													<div class="col-md-3 mb05">
														<b>ผู้บันทึกใบเบิกสินค้าและอุปกรณ์ :</b><br>
														คุณ ${authen.personnel.firstName }&nbsp;${authen.personnel.lastName }&nbsp;
														<c:if test="${authen.personnel.nickName != null }">
															( ${authen.personnel.nickName } )
														</c:if>
													</div>
												</div>
												<div class="row mt05">
													<div class="col-md-12">
														<label for="exampleInputEmail1"><b>รายละเอียดเพิ่มเติม</b></label>
														<textarea class="form-control" id="detail"
															rows="1"></textarea>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt15">
									<div class="col-md-12">
										<fieldset>
											<legend>&nbsp;&nbsp;&nbsp;รายการสินค้าและอุปกรณ์&nbsp;&nbsp;&nbsp;</legend>
											<div class="row mb05 mt05">
												<div class="col-md-6">เลือกสินค้าและอุปกรณ์ที่ต้องการเบิก
													มีดังต่อไปนี้</div>
												<div class="col-md-6">
													<button type="button" data-toggle="modal"
														data-target="#addOrg" onclick="openModalSearchEquipment()"
														class="btn btn-info label-left float-xs-right mr-0-5">
														<span class="btn-label"><i class="ti-plus"></i></span>เลือกสินค้า
													</button>
												</div>
											</div>
											<div class="table-responsive">
												<table id="tableEquipmentProduct" class="table table-bordered table-hover">
													<thead class="thead-default">
														<tr>
															<th style="vertical-align: middle; width: 20px;">ลำดับ</th>
															<th style="vertical-align: middle;">ชื่อเรียกสินค้า</th>
															<th style="vertical-align: middle;">รหัสสินค้า </th>
															<th style="vertical-align: middle;">Serial Number</th>
															<th style="vertical-align: middle; width: 150px;">จำนวนที่เบิก	</th>
															<th style="vertical-align: middle; width: 30px;"></th>
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="row mt05 mb30">
									<div class="col-md-12" align="center">
									<perm:permission object="M20.add" >
										<button type="button" onclick="submitRequisition();" class="btn btn-lg btn-lg btn-success btn-rounded label-left b-a-0 waves-effect waves-light">
												<span class="btn-label"><i class="ti-check"></i></span>
												<b>บันทึกและสร้างใบเบิกสินค้า</b>
										</button>
									</perm:permission>
										
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			
			<!-- modal search equipment -->
			<jsp:include page="modal_search_equipment.jsp"></jsp:include>
			<!-- end modal search equipment -->
		
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

function submitRequisition() {
	//generate bean
	var requisitionDocumentBean = generateBeanJson();
	console.log(requisitionDocumentBean);
	//validate basic
	$('.preloader').modal('show');
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/requisition/save",
		dataType : 'json',
		data : JSON.stringify(requisitionDocumentBean),
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

$('#withdraw').change(function () {
	if("R"==$(this).val()){
		$('#div_technicianGroup').fadeIn();
	}else{
		$('#div_technicianGroup').hide();
	}
});

function generateBeanJson(){
	var requisitionDocumentBean = {};
	
	requisitionDocumentBean.withdraw = $('#withdraw').val();
	
	if("R"==$('#withdraw').val()){
		var technicianGroupBean = {};
		technicianGroupBean.id = $('#technicianGroup').val();
		requisitionDocumentBean.technicianGroup = technicianGroupBean;
	}
	
	requisitionDocumentBean.detail = $('#detail').val();
	
	var requisitionItemList = [];
	$('#tableEquipmentProduct > tbody tr').each(function (i) {
		 var requisitionItem = {};
	     var item_id = $(this).find('.hidden_item_id').val();
	     var numberImport = $(this).find('.numberImport').val();
	     var quantity = $(this).find('.quantity').text();

	     if (typeof numberImport !== "undefined") {
	    	 quantity = numberImport;
	     }

	     requisitionItem.id = item_id;
	     requisitionItem.quantity = quantity;
	     requisitionItemList.push(requisitionItem);
	});
	
    requisitionDocumentBean.requisitionItemList = requisitionItemList;

	return requisitionDocumentBean;
}


</script>
	
</body>
</html>
