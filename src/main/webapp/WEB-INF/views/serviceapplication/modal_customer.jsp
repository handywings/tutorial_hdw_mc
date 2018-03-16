<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addCustomer" tabindex="-1"
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
					<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาลูกค้า
				</h4>
			</div>
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row">
					<div class="col-md-7">
					<label for="exampleInputEmail1">
					<i class="fa fa-exclamation-triangle" style="color: orange;"></i>&nbsp;<b style="color: orange;">ต้องระบุอย่างน้อย3ตัวในการค้นหา</b>
					</label>
						<input type="text" name="key" id="key" class="form-control" value=""
							placeholder="ระบุรหัส ชื่อสกุล บ้านเลขที่ เลขบัตรประชาชน อีเมล์ และเลขหมายโทรศัพท์ของลูกค้าที่ต้องการค้นหา...">
					</div>
					<div class="col-md-3">
						<label for="exampleInputEmail1"><b>ประเภทลูกค้า</b></label>
						<select id="epc" class="form-control">
							<option value="" selected="selected">--- ประเภทลูกค้าทั้งหมด ---</option>
							<option value="I">บุคคลธรรมดา</option>
							<option value="C">นิติบุคคล</option>
						</select>
					</div>					
					<div class="col-md-2 mt05" align="center"><br>
						<button id="searchCus" disabled type="button" class="btn btn-primary btn-block"
							onclick="searchCus();">ค้นหา</button>

<!-- 						<button type="button" class="btn btn-defalut" -->
<!-- 							onclick="searchProcessCus();">ล้าง</button> -->
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
											<table class="table table-bordered table-hover" id="table-1-1">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle; width: 40px;"><center>ลำดับ</center></th>
														<th style="vertical-align: middle; width: 250px"><center>รหัสลูกค้า</center></th>
														<th style="vertical-align: middle;">ชื่อ-นามสกุลลูกค้า</th>
														<th style="vertical-align: middle;"><center>เลขที่บัตรประชาชน</center></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${customers}" var="customer" varStatus="i">
													<tr>
														<td>
															<center>
																<label class="custom-control custom-radio"> <input
																	name="radioProduct" type="radio"
																	value="${customer.id}" class="custom-control-input">
																	<span class="custom-control-indicator"></span> <span
																	class="custom-control-description">&nbsp;</span>
																</label>
															</center>
														</td>
														<td><center>${i.count}</center></td>
														<td><center><a href="${pageContext.request.contextPath}/customerregistration/view/${customer.id}" target="_blank"><b>${customer.custCode}</b></a></center></td>
														<td>${customer.prefix}${customer.firstName} ${customer.lastName}</td>
														<td><center>${customer.identityNumber}</center></td>
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
				<button type="button" class="btn btn-success" onclick="appendToBlockChildCustomer();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$( document ).ready(function() { 
		$('#table-1-1').dataTable({searching: false, paging: true});
		
		$( "#key" ).keyup(function() {
			var dInput = this.value;
			var size = dInput.length;
			if(size > 2){
				$( "#searchCus" ).prop('disabled', false);
			}else{
				$( "#searchCus" ).prop('disabled', true);
			}
		});
		
	});
	
	function openModalSearchCustomer(){
		$('#addCustomer').modal('show');
	}
	
	function appendToBlockChildCustomer(){
		var radio = $("input:radio[name ='radioProduct']:checked").val();
		if(typeof radio != 'undefined'){
			loadDataCustomer(radio);
			$('#addCustomer').modal('hide');
		}
	}
	
	function loadDataCustomer(key){
		$('.preloader').modal('show');
		var url = "${pageContext.request.contextPath}/serviceapplication/loadDataCustomer/"+key;
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : url,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){ 
					
// 					console.log(data);
					
					$('#customerId').val(data["result"]["id"]);
					$('#custCode_old').text(data["result"]["custCode"]);
					$('#custName_old').text(data["result"]["prefix"]+data["result"]["firstName"]+" "+data["result"]["lastName"]);
					$('#identityNumber_old').text(data["result"]["identityNumber"]);
					$('#customerType_old').text(data["result"]["customerFeatureBean"]["customerFeatureName"]);
					
					$('#career_old').text(data["result"]["careerBean"]["careerName"]);
					
					$('#mobile_old').text(data["result"]["contact"]["mobile"]);
					$('#fax_old').text(data["result"]["contact"]["fax"]);
					$('#email_old').text(data["result"]["contact"]["email"]);
					
					$('#collectAddressDetail1_old').text(data["result"]["addressList"][0]["collectAddressDetail"]);
					$('#nearbyPlaces1_old').text(data["result"]["addressList"][0]["nearbyPlaces"]);
					
					$('#collectAddressDetail2_old').text(data["result"]["addressList"][1]["collectAddressDetail"]);
					$('#nearbyPlaces2_old').text(data["result"]["addressList"][1]["nearbyPlaces"]);
					
					$('#newcustomers').hide();
					$('#oldcustomers').fadeIn();
					$('#oldcustomersdetail').fadeIn();
					
					$('#amphur1').html('<option value="'+data["result"]["addressList"][0]["amphurBean"]["id"]+'"></option>');
					$('#amphur2').html('<option value="'+data["result"]["addressList"][1]["amphurBean"]["id"]+'"></option>');
					
					$('#district1').html('<option value="'+data["result"]["addressList"][0]["districtBean"]["id"]+'"></option>');
					$('#district2').html('<option value="'+data["result"]["addressList"][0]["districtBean"]["id"]+'"></option>');
					
					
					$('#no1').val(data["result"]["addressList"][0]["no"]);
					$('#section1').val(data["result"]["addressList"][0]["section"]);
					$('#building1').val(data["result"]["addressList"][0]["building"]);
					$('#room1').val(data["result"]["addressList"][0]["room"]);
					$('#floor1').val(data["result"]["addressList"][0]["floor"]);
					$('#village1').val(data["result"]["addressList"][0]["village"]);
					$('#alley1').val(data["result"]["addressList"][0]["alley"]);
					$('#road1').val(data["result"]["addressList"][0]["road"]);
					$('#province1').val(data["result"]["addressList"][0]["provinceBean"]["id"]);
					$('#amphur1').val(data["result"]["addressList"][0]["amphurBean"]["id"]);
					$('#district1').val(data["result"]["addressList"][0]["districtBean"]["id"]);
					$('#postcode1').val(data["result"]["addressList"][0]["postcode"]);
					$('#nearbyPlaces1').val(data["result"]["addressList"][0]["nearbyPlaces"]);
					
					$('#no2').val(data["result"]["addressList"][1]["no"]);
					$('#section2').val(data["result"]["addressList"][1]["section"]);
					$('#building2').val(data["result"]["addressList"][1]["building"]);
					$('#room2').val(data["result"]["addressList"][1]["room"]);
					$('#floor2').val(data["result"]["addressList"][1]["floor"]);
					$('#village2').val(data["result"]["addressList"][1]["village"]);
					$('#alley2').val(data["result"]["addressList"][1]["alley"]);
					$('#road2').val(data["result"]["addressList"][1]["road"]);
					$('#province2').val(data["result"]["addressList"][1]["provinceBean"]["id"]);
					$('#amphur2').val(data["result"]["addressList"][1]["amphurBean"]["id"]);
					$('#district2').val(data["result"]["addressList"][1]["districtBean"]["id"]);
					$('#postcode2').val(data["result"]["addressList"][1]["postcode"]);
					$('#nearbyPlaces2').val(data["result"]["addressList"][1]["nearbyPlaces"]);
					
					setTimeout(
							function() 
								  {
								$('.preloader').modal('hide');
// 								$('#table-1-1').dataTable({searching: false, paging: true});
// 								$('#table-1-1').DataTable().draw();
								$('#addCustomer').css('overflow-y', 'auto');
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
	
	function searchCus(){
		searchProcessCus();
	}
	
	function searchProcessCus(){
		$('#key').val("");
		$("#epc").prop("selectedIndex", 0);
		searchProcessCus();
	}
	
	function searchProcessCus(){
		$('.preloader').modal('show');
		var searchAllProductBean = {};
		searchAllProductBean.key = $('#key').val();
		searchAllProductBean.customerType = $('#epc').val();
		$('#table-1-1').DataTable().destroy();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplication/searchCustomerAjax",
			dataType : 'json',
			async: false,
			data : JSON.stringify(searchAllProductBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
// 					console.log(data);
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						var tdRadio = "<td><center>"+
						"<label class='custom-control custom-radio'> <input name='radioProduct' type='radio' " +
						"value='"+ data["result"][i]["id"] +"' class='custom-control-input'> " +
						"<span class='custom-control-indicator'></span> <span class='custom-control-description'>&nbsp;</span>" +
						"</label></center></td>"; 
						
						var colOrderCount = "<td><center>"+ (i+1) +"</center></td>";
						var colCode = "<td><center><a href='${pageContext.request.contextPath}/customerregistration/view/" + data["result"][i]["id"] + "' target='_blank'><b>"+ data["result"][i]["custCode"] +"</b></a></center></td>";
						var colName = "<td>"+ data["result"][i]["firstName"] +" "+ data["result"][i]["lastName"] +"</td>";
						var colIdentityNumber = "<td><center>"+ data["result"][i]["identityNumber"] +"</center></td>";
						
						rows = rows + startRow + tdRadio + colOrderCount + colCode + colName + colIdentityNumber + endRow;

					}
					
					if(rows.length > 0){
						$('#table-1-1 > tbody').html(rows);
					}else{
						$('#table-1-1 > tbody').empty();
					}
					
					setTimeout(
							function() 
								  {
								$('.preloader').modal('hide');
								$('#table-1-1').dataTable({searching: false, paging: true});
								$('#table-1-1').DataTable().draw();
								$('#addCustomer').css('overflow-y', 'auto');
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
 