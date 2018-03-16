<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="dialogMember" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left">
	<div class="modal-dialog modal-lg" role="document">
		<form action="" method="post">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<i class="zmdi zmdi-search"></i>&nbsp;&nbsp;ค้นหาข้อมูลพนักงาน
					</h4>
				</div>
				<div class="modal-body"
					style="background: rgba(232, 235, 240, 0.48);">
					<div class="row">
						<div class="col-md-11 mb05">
							<div class="">
								<input type="text" placeholder="รหัสพนักงาน ชื่อ หรือนามสกุล ที่ต้องการค้นหา"
									class="form-control" id="inputSearchPersonnel">
							</div>
						</div>
						 <div class="col-md-1 mb05">
							<!-- <button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#newMemberModal">ค้นหา</button> -->

							<center><button type="button" class="btn btn-warning col-xs-12" id="btnclearPersonnelSearch">ล้าง</button></center>
						</div>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12">
							<div id="">
								<!-- <div class="row mb05">
									<div class="col-md-3 mb05">
										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่
											1 - 1 / 1</span>
									</div>
									<div class="col-md-9">
										<nav class="pull-right">
											<ul class="pagination m-0">
												<li class="page-item active"><a class="page-link"
													href="#">1 <span class="sr-only">(current)</span></a></li>
											</ul>
										</nav>
									</div>
								</div> -->
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id=tableTcgPersonnelSearch>
												<thead class="thead-default">
													<tr>
														<th><center>เลือก</center></th>
														<th><center>รหัสพนักงาน</center></th>
														<th>ชื่อ - นามสกุล</th>
														<th><center>ตำแหน่งงาน</center></th>
														<th><center>สังกัดบริษัท</center></th>
													</tr>
												</thead>
												<tbody>
												<c:forEach items="${personnelBeans}" var="personnelBean" varStatus="i">
													<tr>
														<td><center>
																<input type="hidden" id="personneIdHidden${personnelBean.id}" value="${personnelBean.id}" />
																<label class="custom-control custom-checkbox">
																	<input type="checkbox" onclick="sendValueToJson(${personnelBean.id})" id="${personnelBean.id}" class="custom-control-input" value="${personnelBean.id}">
																 <span class="custom-control-indicator"></span> <span
																	class="custom-control-description"></span>
																</label>
															</center></td>
														<td><center>${personnelBean.personnelCode }</center></td>
														<td>${personnelBean.firstName } ${personnelBean.lastName }&nbsp;(${personnelBean.nickName })</td>
														<td><center>${personnelBean.position.positionName }</center></td>
														<td><center>${personnelBean.company.companyName }</center></td>
													</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>

									</div>
								</div>
								<!-- <div class="row">
									<div class="col-md-3 mb05">
										<b>หน้าที่ 1 </b> <br> <span class="bg-paging">รายการที่
											1 - 1 / 1</span>
									</div>
									<div class="col-md-9">
										<nav class="pull-right">
											<ul class="pagination m-0">
												<li class="page-item active"><a class="page-link"
													href="#">1 <span class="sr-only">(current)</span></a></li>

											</ul>
										</nav>
									</div>
								</div> -->
								
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" onclick="addMember()">ตกลง</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	jsonChecked = [];
	var technicianGroupId;
	
	$( document ).ready(function() {
		var oTable = $('#tableTcgPersonnelSearch').dataTable();
		$('#tableTcgPersonnelSearch_filter').css('display','none');

		$("#inputSearchPersonnel").keyup( function (e) {
		     oTable.fnFilter( this.value );
		} );
		
		$('#btnclearPersonnelSearch').on('click',function(){
			$("#inputSearchPersonnel").val("");
			oTable.fnFilter("");
		});
		
	});

	function openDialogMember(id){
		technicianGroupId = id;
		$('#dialogMember').modal('show');
	}
	function sendValueToJson(idMember){
		var flagHas = false;
		if(jsonChecked.length > 0){
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] == idMember){
					flagHas = true;
				}
			}
		}else{
			var flagHas = false;
		}
		if(flagHas){
			newJsonChecked = [];
			for(var i=0;i<jsonChecked.length;i++){
				if(jsonChecked[i] != idMember){
					newJsonChecked.push(jsonChecked[i]);
				}
			}
			//update json
			jsonChecked = newJsonChecked;
		}else{
			member = {};
			member.id = idMember;
			member.technicianGroupId = technicianGroupId;
			jsonChecked.push(member);
		}
	}
	
	function addMember(){
		for(var i=0;i<jsonChecked.length;i++){
			console.log(jsonChecked[i]);
		}
		//send save member
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/saveMember",
			dataType : 'json',
			async: false,
			data : JSON.stringify(jsonChecked),
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
</script>



