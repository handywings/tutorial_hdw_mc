<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="modal_serviceapplication" tabindex="-1"
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
					<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาใบสมัครใช้บริการ
				</h4>
			</div>
			<div class="modal-body"
				style="background: rgba(232, 235, 240, 0.48);">
				<div class="row">
					<div class="col-md-2">
						<label for="exampleInputEmail1"><b>ค้นหาด้วย รหัส หรือ ชื่อสกุล ลูกค้า</b></label>
						<input type="text" name="key" id="key" class="form-control" value=""
							placeholder="ระบุรหัส หรือ ชื่อสกุล ลูกค้า">
					</div>
					<div class="col-md-2">
						<label for="exampleInputEmail1"><b>ประเภทบริการ</b></label>
						<select id="servicePackageType" name="servicePackageType" class="form-control" >
								<option value="0" selected="selected">ประเภทบริการทั้งหมด</option>
							<c:forEach items="${servicePackageTypeBeans}" var="servicePackageTypeBean"> 
								<option value="${servicePackageTypeBean.id }">${servicePackageTypeBean.packageTypeName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label for="exampleInputEmail1"><b>Package</b></label> 
								<select class="form-control" id="service_packages">
								<option value="0" selected="selected">---
									กรุณาเลือกประเภทบริการก่อน ---</option>
								</select>
						</div>
					</div>
					<div class="col-md-3">
						<label for="exampleInputEmail1"><b>เขตชุมชน / โครงการ</b></label>
						<select id="zone" name="zone" class="form-control">
							<c:forEach items="${zoneBeanList}" var="zoneBean">
								<option value="${zoneBean.id }">${zoneBean.zoneDetail} (${zoneBean.zoneName })</option>
							</c:forEach>
						</select>
					</div>			
					<div class="col-md-2" align="center">
						<button id="searchCus" type="button" class="btn btn-primary btn-block"
						style="margin-top: 32px;"
							onclick="search();">ค้นหา</button>
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
														<th width="10%">
														<center>
														<label class="custom-control custom-checkbox"> 
															<input value="1" class="custom-control-input" id="example-select-all" type="checkbox"> 							
														<span class="custom-control-indicator"></span> 							
														<span class="custom-control-description">&nbsp;</span> 						
														</label>
														</center></th>
														<th width="10%"><center>เลขที่ใบสมัคร</center></th>
														<th width="20%">ข้อมูลลูกค้า</th>
														<th width="30%">ข้อมูลบริการที่สมัคร</th>
														<th width="30%"><center>เขตชุมชน / โครงการ</center></th>
													</tr>
												</thead>
												<tbody>

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
	var mainData = "";
	var table = "";
	$( document ).ready(function() { 
		table = $('#table-1-1').dataTable({searching: false, paging: true});	
	});
	
	// Handle click on "Select all" control
	   $('#example-select-all').on('click', function(){		   
	      var all = $(this);
	      table.$('input[type="checkbox"]').each(function(){
	    	  $(this).prop('checked', all.is(':checked'));
		    });
	   });
	
	$('#servicePackageType').change(function () {
		var service_type_id = $(this).val();
		if(service_type_id != 0){
			$('.preloader').modal('show');
			$.ajax({
				type : "GET",
				contentType : "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/serviceapplication/getServicePackage/"+service_type_id,
				dataType : 'json',
				//timeout : 100000,
				success : function(data) {
					if(data["error"] == false){
						var option = '<option value="0" selected="selected" >--- Packageทั้งหมด ---</option>';
						$.each(data.result, function( index, value ) {
							option += '<option value="'+value.id+'">'+value.packageName+'</option>';
						});
						
						$('#service_packages').html(option);
						$('.div-detail-package').hide();
						$('.div-detail-package-ismonthlyservice').hide();
						
						$('#table_template tbody').html('');
						setTimeout(function() {$('.preloader').modal('hide');}, 200);
					}
				},
				error : function(e) {
					console.log("ERROR: ", e);
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		}else{
			var option = '<option value="0" selected="selected">--- กรุณาเลือกประเภทบริการก่อน ---</option></select>';
			$('#service_packages').html(option);
		}
	});
	
	function openModalServiceapplication(){
		$('#table-1-1').DataTable().destroy();
		$('#table-1-1 > tbody').empty();
		$('#table-1-1').DataTable().draw();
		
		$('#modal_serviceapplication').modal('show');
	}
	
	function appendToBlockChild(){
		var hideModel = false;
		var arrayId = [];
		$('.preloader').modal('show');
		
		table.$('input[type="checkbox"]').each(function(){
			if(this.checked){
	           	arrayId.push(this.value);
		 		hideModel = true;
	        }
	    });
		
		if(hideModel){
			loadServiceapplication(arrayId);
			$('#modal_serviceapplication').modal('hide');
		}else{
			$('.preloader').modal('hide');
		}
	}
	
	function loadServiceapplication(arrayId){
		var rows = "";
		var data = mainData;
		var personnelId = $('#cashier').val();
		
		var serviceApplicationBeanList = [];

		var serviceApplicationBean = {};
		serviceApplicationBean.personnelId = personnelId;
		serviceApplicationBeanList.push(serviceApplicationBean);
		
		for(var i=0;i<data["result"].length;i++){
			var id = data["result"][i]["id"];
			if(jQuery.inArray(""+id,arrayId) >= 0){
				var serviceApplicationBean = {};
				serviceApplicationBean.id = id;
				serviceApplicationBeanList.push(serviceApplicationBean);
			}
		}
		
		console.log(serviceApplicationBeanList);
		
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/printinvoice/setassigncashier",
			dataType : 'json',
			data : JSON.stringify(serviceApplicationBeanList),
				//timeout : 100000,
				success : function(data) {
					window.location.reload();
				},
				error : function(e) {
					console.log("ERROR: ",e);
					$('.preloader').modal('hide');
				},
				done : function(e) {
					console.log("DONE");
					$('.preloader').modal('hide');
				}
			});
		
	}
	
	function deleteServiceapplication(id){
		var personnelId = $('#cashier').val();
		var serviceApplicationBean = {};
		serviceApplicationBean.id = id;
		serviceApplicationBean.personnelId = personnelId;
		
		$('.preloader').modal('show');
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/printinvoice/deleteAssignCashier",
			dataType : 'json',
			data : JSON.stringify(serviceApplicationBean),
				//timeout : 100000,
				success : function(data) {
					$('#tr_'+id+'').remove();
					setTimeout(function() {$('.preloader').modal('hide');}, 200);
				},
				error : function(e) {
					console.log("ERROR: ",e);
					$('.preloader').modal('hide');
				},
				done : function(e) {
					console.log("DONE");
					$('.preloader').modal('hide');
				}
			});
		
	}
	
	function search(){
		searchProcess();
	}
	
	function searchProcess(){
		searchProcess();
	}
	
	function searchProcess(){
		$('.preloader').modal('show');
		var personnelId = $('#cashier').val();
		var applicationSearchBean = {};
		applicationSearchBean.key = $('#key').val();
		applicationSearchBean.servicePackageType = $('#servicePackageType').val();
		applicationSearchBean.zone = $('#zone').val();
		applicationSearchBean.servicePackage = $('#service_packages').val();
		applicationSearchBean.personnelId = 0;
		$('#table-1-1').DataTable().destroy();
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/serviceapplicationlistzone/searchServiceapplicationAjax",
			dataType : 'json',
			async: false,
			data : JSON.stringify(applicationSearchBean),
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var arr = [];
					$('#table_serviceapplication > tbody  > tr').each(function() {
						 $(this).find('input').each(function (i) {
							 switch (i) {
								case 0:
									var id = $(this).val();
									arr.push(id);
								break;
							 }
						 });
					});
					
					mainData = data;
					
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var id = data["result"][i]["id"];
// 						console.log(jQuery.inArray(""+id,arr));
						if(jQuery.inArray(""+id,arr) < 0){
							
						var startRow = "<tr>";
						var endRow = "</tr>";
						
						var tdCheckbox = '<td><center> \
							<label class="custom-control custom-checkbox"> <input type="checkbox" \
							value="'+ data["result"][i]["id"] +'" class="custom-control-input"> \
							<span class="custom-control-indicator"></span> \
							<span class="custom-control-description">&nbsp;</span> \
						</label></center></td>';
						
						var colServiceApplicationNo = '<td class="td-middle"><center><a class="cursor-p" target="_blank" \
						href="${pageContext.request.contextPath}/serviceapplicationlist/view/'+data["result"][i]["id"]+'" \
						data-toggle="tooltip" data-placement="bottom" title="ดูรายละเอียด"><b>'+data["result"][i]["serviceApplicationNo"]+'</b></center></a>';
						
						var colCus = '<td class="td-middle">'+ data["result"][i]["customer"]["prefix"]+data["result"][i]["customer"]["firstName"] +'&nbsp'+ data["result"][i]["customer"]["lastName"] +'<br> \
							<small style="color: gray;"> \
							<b>รหัสลูกค้า&nbsp;:&nbsp;</b><a href="${pageContext.request.contextPath}/customerregistration/view/'+ data["result"][i]["customer"]["id"] +'" target="_blank" data-toggle="tooltip" data-placement="bottom" \
							title="ดูรายละเอียด">'+ data["result"][i]["customer"]["custCode"] +'</a><br> \
							<b>Email&nbsp;:&nbsp;</b>'+ data["result"][i]["customer"]["contact"]["email"] +'<br> \
							<b>โทร&nbsp;:&nbsp;</b>'+ data["result"][i]["customer"]["contact"]["mobile"] +' \
							</small></td>';
							
						var colServeice = '<td class="td-middle">'+ data["result"][i]["servicepackage"]["packageName"] +'<br> \
							<small style="color: gray;"><b>รหัส Package :</b> '+ data["result"][i]["servicepackage"]["packageCode"] +'<br> \
							<b>ประเภทบริการ '+ data["result"][i]["servicepackage"]["serviceType"]["packageTypeName"] +'</b></small></td>';
							
						var addressList = data["result"][i]["addressList"];
						var collectAddressDetail = '-';
						var zoneDetail = '-';
						var nearbyPlaces = '-';

						for(var j=0;j<addressList.length;j++){
								var addressBean = addressList[j];
								if(addressBean.addressType == 3){
									collectAddressDetail = addressBean.collectAddressDetail;
									zoneDetail = addressBean.zoneBean.zoneDetail;
									nearbyPlaces = addressBean.nearbyPlaces;
								}
						};
							
						var colZone = '<td class="td-middle"><center>'+collectAddressDetail+'<br><b>( '+zoneDetail+')</b><br> \
								<font style="color: gray;">ใกล้เคียงกับ '+nearbyPlaces+'</font></center></td>';
						
						rows = rows + startRow + tdCheckbox + colServiceApplicationNo + colCus + colServeice + colZone + endRow;
						
						}
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
								table = $('#table-1-1').dataTable({searching: false, paging: true});
								$('#table-1-1').DataTable().draw();
								$('#modal_serviceapplication').css('overflow-y', 'auto');
								  }, 200);
					
				}else{
					console.log("ERROR: ");
					$('.preloader').modal('hide');
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
 