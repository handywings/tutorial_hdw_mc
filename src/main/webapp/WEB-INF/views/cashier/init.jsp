<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:useBean id="textUtil" class="com.hdw.mccable.utils.TextUtil" />
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="personnel" scope="request"/>
<c:set var="subMenu" value="cashier" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ข้อมูลพนักงานเก็บเงิน</title>
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
						<div class="col-md-8">
							<h3>ข้อมูลพนักงานเก็บเงิน</h3>
							<ol class="breadcrumb no-bg mb-1">
								<li class="breadcrumb-item active">ข้อมูลพนักงาน</li>
								<li class="breadcrumb-item active">ข้อมูลพนักงานเก็บเงิน</li>
							</ol>
						</div>
						<div class="col-md-4 mt05">
							<perm:permission object="M13.add">
								<a onclick="openDialogMember(1);"><button type="button" data-toggle="modal" data-target="#addOrg" class="btn btn-info label-left float-xs-right mr-0-5">
										<span class="btn-label"><i class="ti-plus"></i></span>เพิ่มพนักงานเก็บเงิน
									</button></a>
							</perm:permission>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="fa fa-calculator"></i>&nbsp; เงื่อนไขคำนวณค่า Commission
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row mb05">
									<form id="cashierSearchBean" action="${pageContext.request.contextPath}/cashier/search" method="post">
										<div class="col-md-10">
											<b>เลือกช่วงเวลาที่ต้องการค้นหา<span class="text-red"> *
											</span>:
											</b><br> <input type="text" class="form-control daterange"
												id="daterange" name="daterange" value="${daterange}">
										</div>
<!-- 										<div class="col-md-5">	 -->
<!-- 										<label for="exampleInputEmail1"><b>รูปแบบการคำนวณค่า Commission<span class="text-red"> *</span></b></label>	<br>											 -->
<%-- 													<label class="custom-control custom-radio"> <input <c:if test="${type == 'percent'}">checked="checked"</c:if> id="radio2" name="typeCalCommission" type="radio" value="percent" class="custom-control-input"> <span class="custom-control-indicator"></span> <span class="custom-control-description">คิดจาก % ของยอดรวมเงินที่เก็บได้</span> --%>
<%-- 													</label> <label class="custom-control custom-radio"> <input <c:if test="${type == 'bill'}">checked="checked"</c:if> id="radio1" name="typeCalCommission" type="radio" value="bill" class="custom-control-input"> --%>
<!-- 														<span class="custom-control-indicator"></span> <span class="custom-control-description">คิดจากจำนวนใบบิลที่เก็บได้</span> -->
<!-- 													</label> -->
<!-- 												</div> -->
<!-- 										<div class="col-md-2"> -->
<%-- 											<div id="calPercent" <c:if test="${type == 'bill'}">style="display: none;"</c:if>> --%>
<!-- 												<b>จำนวนเปอร์เซ็นต์<span class="text-red"> *</span></b> -->
<!-- 											<div class="input-group"> -->
<!-- 												<input type="text" class="form-control" onkeypress="return isNumberKey(event);" -->
<%-- 													id="calCommissionPercent" name="calCommissionPercent" value="${calCommissionPercent}"> --%>
<!-- 												<div class="input-group-addon">%</div> -->
<!-- 											</div> -->
<!-- 											<span class="text-red" id="calCommissionPercentValid" style="display: none;">กรุณากรอกข้อมูล</span> -->
<!-- 											</div>											 -->
<%-- 											<div id="calBill" <c:if test="${type == 'percent'}">style="display: none;"</c:if>> --%>
<!-- 												<b>ค่า Commission / ใบบิล<span class="text-red"> *</span></b> -->
<!-- 											<div class="input-group"> -->
<!-- 												<input type="text" class="form-control" onkeypress="return isNumberKey(event);" -->
<%-- 													id="calCommissionBill" name="calCommissionBill" value="${calCommissionBill}"> --%>
<!-- 												<div class="input-group-addon">บาท</div> -->
<!-- 											</div> -->
<!-- 											<span class="text-red" id="calCommissionBillValid" style="display: none;">กรุณากรอกข้อมูล</span> -->
<!-- 											</div> -->
<!-- 										</div> -->
										<div class="col-md-2 col-xs-12">
										&nbsp;<br>										
											<button type="button" onclick="calculateCommission()" class="btn btn-lg bg-instagram col-xs-12 b-a-0 waves-effect waves-light">
												<span class="ti-search"></span> ค้นหา
											</button>
											<button type="submit" id="btnCalCommission" hidden></button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="card mt05 mb40">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-format-list-bulleted"></i>&nbsp;&nbsp;ระเบียนพนักงานเก็บเงิน
							</h4>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="posts-list posts-list-1">
									<div class="pl-item">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="60px;"><center>ลำดับ</center></th>
														<th><center>ชื่อ - นามสกุล</center></th>
														<th><center>จำนวนบิลที่เก็บได้</center></th>
														<th><div align="center">ยอดที่เก็บได้ (บาท)</div></th>
														<th><div align="center">ค่า Commission / ใบบิล (บาท)</div></th>
														<th><div align="center">ค่า Commission (บาท)</div></th>
														<th width="30px;"></th>
													</tr>
												</thead>
												<tbody>
												
													<c:choose>
														<c:when test="${cashierIsTrue != null && cashierIsTrue.size() > 0 }">
															<c:forEach var="cashierIsTrue" items="${cashierIsTrue}" varStatus="i">
																<tr role="row" class="odd">
																	<td class="td-middle sorting_1" align="center">${i.count}</td>
																	<td class="bill-staff">${cashierIsTrue.fullName}</td>
																	<td align="center" class="total-bill">${cashierIsTrue.totalBill}</td>
																	<td align="center">${textUtil.getFormatNumberInt(cashierIsTrue.sumAmount)}</td>
																	<td align="center"><input type="text" class="form-control bill" 
																	style="text-align: right;" onkeypress="return isNumberKey(event);"></td>
																	<td align="center"><b class="result">${textUtil.getFormatNumberInt(cashierIsTrue.commission)}</b></td>
																	<td align="center">
																		<perm:permission object="M13.delete">
																			<a class="cursor-p" onclick="openDialogDelete(${cashierIsTrue.id});"> <span
																					class="ti-trash" data-toggle="tooltip"
																					data-placement="bottom" title=""
																					data-original-title="ลบ"></span></a>
																		</perm:permission>
																	</td>
																</tr>
															</c:forEach>	
														</c:when>
														<c:otherwise>
															<tr><td colspan="7"><center>ไม่พบข้อมูล</center></td></tr>
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
				</div>
			</div>
			<button type="button" onclick="calTest()" id="calTest" hidden> </button>
			<jsp:include page="modal_delete.jsp" />
			<jsp:include page="modal_member.jsp" />

			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<script src="<c:url value="/resources/assets/plugins/datepicker-th/js/bootstrap-datepicker-custom.js" />"></script>
    <script src="<c:url value="/resources/assets/plugins/datepicker-th/locales/bootstrap-datepicker.th.min.js" />" charset="UTF-8"></script>
	

	<c:if test="${not empty alertBean}">
		<script type="text/javascript">
			openAlert('${alertBean.type}', '${alertBean.title}',
					'${alertBean.detail}');
		</script>
	</c:if>

	<script type="text/javascript">
	$('input[name=typeCalCommission]').change(function() {
		if (this.value == 'bill') {
			$('#calBill').fadeIn();
			$('#calPercent').hide();
			$('#calCommissionPercent').val("");
		} else {
			$('#calBill').hide();
			$('#calPercent').fadeIn();
			$('#calCommissionBill').val("");
		}
	});
	
		$('.daterange').daterangepicker({
			buttonClasses : [ 'btn', 'btn-sm' ],
			applyClass : 'btn-success',
			cancelClass : 'btn-inverse',
			 language: 'th',             //เปลี่ยน label ต่างของ ปฏิทิน ให้เป็น ภาษาไทย   (ต้องใช้ไฟล์ bootstrap-datepicker.th.min.js นี้ด้วย)
             thaiyear: true              //Set เป็นปี พ.ศ.
		});	
		
		function calculateCommission() {
			$('#btnCalCommission').click();			
		}
				
		$('.bill').keyup(function() {		
			var totalBill = $(this).parent().parent().find('.total-bill').html();
			var commissionPerBill = $(this).val();			
			if(commissionPerBill == ""){
				commissionPerBill = 0;
			}
			var commission = totalBill * commissionPerBill;
			$(this).parent().parent().find('.result').html(addComma(commission));
		});
		
		function addComma(val) {
			while (/(\d+)(\d{3})/.test(val.toString())){
			      val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
			    }
			return val;
		}
	</script>

</body>
</html>
