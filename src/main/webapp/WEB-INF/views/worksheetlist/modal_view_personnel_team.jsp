<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="viewPersonnelTeam" tabindex="-1"
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
					<i class="zmdi zmdi-user"></i>&nbsp;พนักงานช่างที่ปฏิบัติหน้าที่
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div class="row mb05">
								<div class="col-md-12 mb05">
										<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="tableViewTeam">
												<thead class="thead-bg">
													<tr>
														<th style="vertical-align: middle; width: 20px;">
														<center>
														 ลำดับ
														 </center>
														</th>
														<th style="vertical-align: middle;"><center>ทีม</center></th>
														<th style="vertical-align: middle;"><center>รหัสพนักงาน</center></th>
														<th style="vertical-align: middle;">ชื่อ - สกุล</th>
													</tr>
												</thead>
												<tbody id="tbody-child-item-team">
												</tbody>
											</table>
											<hr>
											<div class="form-group">
												<label for="exampleTextarea" class="tag tag-pill tag-info"><b>บันทึกเพิ่มเติม</b></label>
												<textarea class="form-control" id="remarkNotSuccess" rows="2" disabled="disabled"></textarea>
											</div>
											
											
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">ปิด</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	function openViewTeam(historyId){
		$('#viewPersonnelTeam').modal('show');
		//$('#viewPersonnelTeam').css('z-index', 1000);
		//$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetlist/loadPersonnelTeam/"+historyId,
			//dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){					
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						var order = "<td><center>" + (i+1) + "</center></td>";
						var team = "<td><center>"+ data["result"][i]["technicianGroupBean"]["technicianGroupName"] +"</center></td>";
						var personnelCode = "<td><center><a href='${pageContext.request.contextPath}/personnel/view/" + data["result"][i].id + "' data-toggle='tooltip' data-placement='bottom' title='' data-original-title='ดูรายละเอียด'><b>"+ data["result"][i].personnelCode +"</b></a></center></td>";
						var personnelName = "<td>"+ data["result"][i].firstName +" "+ data["result"][i].lastName +"</td>";
						
						rows = rows + startRow + order + team + personnelCode + personnelName + endRow;
						$('#remarkNotSuccess').val(data["message"]);
					}
					
					$('#tableViewTeam > tbody').empty();
					$('#tableViewTeam > tbody').html(rows);
					
// 					setTimeout(
// 						function() 
// 							  {
// 							$('.preloader').modal('hide');
// 							  }, 200);
					
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
