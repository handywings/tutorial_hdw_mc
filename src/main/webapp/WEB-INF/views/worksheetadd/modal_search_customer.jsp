<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="searchServiceApp" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="zmdi zmdi-search"></i>&nbsp;&nbsp;ค้นหาข้อมูลใบสมัครลูกค้าเพื่อสร้างใบงาน
					</h4>
				</div>
				<div class="modal-body"
					style="background: rgba(232, 235, 240, 0.48);">
					<div class="row">
						<div class="col-md-5">
							<div class="">
								<label for="exampleInputEmail1"><b>ข้อมูลการค้นหา </b>
								<i class="fa fa-exclamation-triangle" style="color: orange;"></i>&nbsp;<b style="color: orange;">ต้องระบุอย่างน้อย3ตัวในการค้นหา</b>
								</label> <input type="text" id="c_addpoint_key"
									class="form-control" placeholder="ระบุเลขที่ใบงาน ชื่อสกุล เลขบัตรประชาชน อีเมล์ หมายเลขโทรศัพท์ หรือที่อยู่ที่ติดตั้ง ของลูกค้า......">
							</div>
						</div>
						<div class="col-md-3">
							<div class="">
								<label for="exampleInputEmail1"><b>ประเภทลูกค้า</b></label> <select
									id="c_addpoint_custType" name="categoryID" class="form-control">
									<option value="" selected="selected">--- แสดงประเภทลูกค้าทั้งหมด ---</option>
									<option value="I">บุคคลธรรมดา</option>
									<option value="C">นิติบุคคล</option>
								</select>
							</div>
						</div>
						<div class="col-md-3">
							<div class="">
								<label for="exampleInputEmail1"><b>ลักษณะลูกค้า</b></label> 
								<select id="customerFeatures" name="customerFeatures" class="form-control">
									<option value="0" selected="selected">--- แสดงลักษณะของลูกค้าทั้งหมด ---</option>
									<c:forEach var="customerFeature" items="${customerFeatures}" varStatus="i">
										<option value="${customerFeature.id}" <c:if test="${customerSearchBean.customerFeatures == customerFeature.id}">selected="selected"</c:if>>${customerFeature.customerFeatureName}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="col-md-1 mt05">
							<br>
							<button id="searchServiceApplication" type="button" class="btn btn-primary col-xs-12" disabled onclick="searchServiceApplication();">ค้นหา</button>
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
											<table class="table table-bordered mb-0 table-hover" id="table-1">
												<thead class="thead-default">
													<tr>
														<th width="5%"><center>เลือก</center></th>
														<th width="20%"><center>เลขที่ใบสมัคร</center></th>
														<th style="width: 100px !important;">ข้อมูลลูกค้า</th>
														<th style="width: 150px !important;">ข้อมูลบริการที่สมัคร</th>
														<th width="30%">สถานที่ติดตั้ง (เขตชุมชน)</th>
													</tr>
												</thead>
												<tbody> 
													<c:forEach items="${serviceApplicationBeans}" var="serviceApplicationBean" varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-radio"> 
																	<input  name="radio_search_serviceApp" type="radio"
																		class="custom-control-input" value="${serviceApplicationBean.id }"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description">&nbsp;</span>
																	</label>
																</center></td>
															<td><center><a href="${pageContext.request.contextPath}/serviceapplicationlist/view/${serviceApplicationBean.id }" target="_blank"><b>${serviceApplicationBean.serviceApplicationNo }</b></a></center></td>
															<td>${serviceApplicationBean.customer.firstName }&nbsp;${serviceApplicationBean.customer.lastName }<br> <font
																class="text-gray"><a href="${pageContext.request.contextPath}/customerregistration/view/${serviceApplicationBean.customer.id }" target="_blank">${serviceApplicationBean.customer.custCode }</a></font> 
															</td>
															<td>${serviceApplicationBean.servicepackage.packageName }<br> <font class="text-gray">Package : ${serviceApplicationBean.servicepackage.packageCode } | ประเภทบริการ : ${serviceApplicationBean.servicepackage.serviceType.packageTypeName }</font>
															</td>
															<td>
																<c:forEach var="addressBean" items="${serviceApplicationBean.addressList}" varStatus="j">
																	<c:if test="${addressBean.addressType == 3}">																			
																		${addressBean.zoneBean.zoneDetail } <b>(${addressBean.zoneBean.zoneName })</b>
																	</c:if>
																</c:forEach><br>
																<font style="color: gray;">${serviceApplicationBean.customer.customerFeatureBean.customerFeatureName}</font>															
															</td> 
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
				<div class="modal-footer">
					<button type="button" class="btn btn-success" id="ok"
						data-dismiss="modal" >ตกลง</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
			</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript"> 
	$( document ).ready(function() {
		$('#table-1').dataTable({searching: false, paging: true});
		
		$( "#c_addpoint_key" ).keyup(function() {
			var dInput = this.value;
			var size = dInput.length;
			if(size > 2){
				$( "#searchServiceApplication" ).prop('disabled', false);
			}else{
				$( "#searchServiceApplication" ).prop('disabled', true);
			}
		});
		
	});
	
	function searchServiceApplication(){
		$('.preloader').modal('show');
		var applicationSearchBean = {};
		applicationSearchBean.key = $('#c_addpoint_key').val();
		applicationSearchBean.custType = $('#c_addpoint_custType').val();
		applicationSearchBean.customerFeatures = $('#customerFeatures').val();
		$('#table-1').DataTable().destroy();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/searchServiceApplication",
			dataType : 'json',
			async: false,
			data : JSON.stringify(applicationSearchBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var columnRadio = "<td><center>" +
						"<label class='custom-control custom-radio'>" + 
						"<input name='radio_search_serviceApp' type='radio'"+
						" value='"+ data["result"][i]["id"] +"'" +
						"class='custom-control-input'>" +
						"<span class='custom-control-indicator'></span>" +
						"<span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>";
						
						var columnServiceNo = "<td><center><a href='${pageContext.request.contextPath}/serviceapplicationlist/view/" + data["result"][i]["id"] + "' target='_blank'><b>"+ data["result"][i]["serviceApplicationNo"] +"</b></a></center></td>";
						var columnCustomer = "<td>"+ data["result"][i]["customer"]["firstName"] +"&nbsp;"+ data["result"][i]["customer"]["lastName"] +
						"<br> <font class='text-gray'><a href='${pageContext.request.contextPath}/customerregistration/view/"+ data["result"][i]["customer"]["id"] +"' target='_blank'>"+ data["result"][i]["customer"]["custCode"] +"</a></font> </td>";
						
						var columnServicePackage = "<td>"+ data["result"][i]["servicepackage"]["packageName"] +"<br>" + 
						"<font class='text-gray'>Package : " + data["result"][i]["servicepackage"]["packageCode"] + 
						"| ประเภทบริการ : "+ data["result"][i]["servicepackage"]["serviceType"]["packageTypeName"] +"</font></td>";
						
						var columnZone = "<td><center></center></td>";
						for(var j=0;j<data["result"][i]["addressList"].length;j++){
							if(data["result"][i]["addressList"][j].addressType == '3'){
								columnZone = "<td>" + data["result"][i]["addressList"][j].collectAddressDetail + "&nbsp;<b>(" + data["result"][i]["addressList"][j]["zoneBean"].zoneDetail + ")</b>" + "<br><font style='color: gray;'>" + data["result"][i]["customer"]["customerFeatureBean"].customerFeatureName + "</font>" + "</td>";
							}
						} 
						
						rows = rows + startRow+columnRadio+columnServiceNo+columnCustomer;
						rows = rows + columnServicePackage+columnZone+endRow;
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
							$('#table-1').dataTable({searching: false, paging: true});
							$('#table-1').DataTable().draw();
							$('#searchServiceApp').css('overflow-y', 'auto');
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
	
	function resetSearchServiceApplication(){
		$('#c_addpoint_key').val("");
		$("#c_addpoint_custType").prop("selectedIndex", 0);
		$("#customerFeatures").prop("selectedIndex", 0);
		searchProcess();
	}
</script>