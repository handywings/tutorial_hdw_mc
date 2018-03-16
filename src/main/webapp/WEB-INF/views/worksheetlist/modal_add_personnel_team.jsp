<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="AddPersonnelTeam" tabindex="-1"
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
					<i class="zmdi zmdi-user"></i>&nbsp;สมาชิกที่ปฏิบัติหน้าที่
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
											<table class="table table-bordered mb-0 table-hover" id="tableAddTeam">
												<thead class="thead-bg">
													<tr>
														<th style="vertical-align: middle; width: 20px;">
														<center>
														<label class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input checkAllMember" >
														<span class="custom-control-indicator"></span>
														<span class="custom-control-description"></span>
														</label>
														</center>
														</th>
														<th style="vertical-align: middle;"><center>ทีม</center></th>
														<th style="vertical-align: middle;"><center>รหัสพนักงาน</center></th>
														<th style="vertical-align: middle;">ชื่อ - สกุล</th>
													</tr>
												</thead>
												<tbody id="tbody-child-item-add-team">
													<tr><td colspan="4"><center>ไม่พบรายชื่อพนักงานปฏิบัติหน้าที่</center></td></tr>
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
				<button type="button" class="btn btn-success" onclick="appendToTableMember()">เพิ่ม</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$(".checkAllMember").click(function () {
		var checkBox = $(".checkBoxMemberItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});

	var personnelListAll;

	function openAddTechnicianTeam(technicianGroupId){
		$('#AddPersonnelTeam').modal('show');
		//$('#AddPersonnelTeam').css('z-index', 1020);
		
		var memberItem_Array = [];
		$('.personnelIdClass').each(function (i) {
				memberItem_Array.push($(this).val());
		});
		
		//$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/techniciangroup/loadAllTechnician",
			//dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					personnelListAll = data["result"];
					var rows = "";
					for(var i=0;i<data["result"].length;i++){
						var startRow = "<tr>";
						var endRow = "</tr>";
						var columnCheckBox = '<td><center><label class="custom-control custom-checkbox">\
							<input type="checkbox" class="custom-control-input checkBoxMemberItem" value="'+data["result"][i].id+'" >\
							<span class="custom-control-indicator"></span>\
							<span class="custom-control-description"></span>\
							</label></center></td>';
						var team = "<td><center>"+ data["result"][i]["technicianGroupBean"]["technicianGroupName"] +"</center></td>";
						var personnelCode = "<td><center>"+ data["result"][i].personnelCode +"</center></td>";
						var personnelName = "<td>"+ data["result"][i].firstName +" "+ data["result"][i].lastName +"</td>";
						
						if(jQuery.inArray( ""+data["result"][i].id, memberItem_Array) == -1){
							rows = rows + startRow + columnCheckBox + team;
							rows = rows + personnelCode + personnelName + endRow;
						}
					}
					
					if(rows.length > 0){
						$('#tableAddTeam > tbody').html(rows);
					}else{
						$('#tableAddTeam > tbody').empty();
					}
					
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
	
	//append to table
	function appendToTableMember(){
		$('.checkBoxMemberItem').each(function (i) {
			if($(this).is(":checked")){
				var rows = "";
		    	//productItemId_array.push($(this).val());
		    	for(var i=0;i<personnelListAll.length;i++){
		    		if($(this).val() == personnelListAll[i].id){
		    			var startRow = "<tr>";
						var endRow = "</tr>";
						var columnOrderNo = "<td><center>"+
						'<span class="no-item-member"></span></center>'+
						"<input type='hidden' class='personnelIdClass' value='"+ personnelListAll[i].id +"' /></td>";
						var team = "<td><center><b>"+ personnelListAll[i]["technicianGroupBean"]["technicianGroupName"] +"</b></center></td>";
						var personnelCode = "<td><center><a href='${pageContext.request.contextPath}/personnel/view/" + personnelListAll[i].id + "' target='_blank'>"+ personnelListAll[i].personnelCode +"</a></center></td>";
						var personnelName = "<td>"+ personnelListAll[i].firstName +" "+ personnelListAll[i].lastName +"</td>";
						var columnAction = '<td><center>'+
						'<a class="run-swal cursor-p removePersonnelItem"> <span class="ti-trash" '+
						'data-toggle="tooltip" data-placement="bottom" title="" data-original-title=""></span></a>'+
						'</center></td>';
						
						rows = rows + startRow + columnOrderNo + team;
						rows = rows + personnelCode + personnelName + columnAction + endRow;
						$('#tableViewTeamCurrent > tbody').append(rows);
		    		}
		    	}
			}
			
		});
		
		// เรียงลำดับ
		$('#tableViewTeamCurrent > tbody tr').each(function (i) {
		     $(this).find('.no-item-member').text((i + 1));
		});
		
		$('#AddPersonnelTeam').modal('hide');
		
		// ลบ row
		$(".removePersonnelItem").click(function () {
			$(this).closest('tr').remove();
			// เรียงลำดับ
			$('#tableViewTeamCurrent > tbody tr').each(function (i) {
			     $(this).find('.no-item-member').text((i + 1));
			});
		});
	}
	
	// ลบ row
	$(".removePersonnelItem").click(function () {
		$(this).closest('tr').remove();
		// เรียงลำดับ
		$('#tableViewTeamCurrent > tbody tr').each(function (i) {
		     $(this).find('.no-item-member').text((i + 1));
		});
	});
	
</script>
