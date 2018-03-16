<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="modal fade" id="editOrg" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabelEdit" align="left">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabelEdit">แก้ไข Package บริการ</h4>
			</div>
			<div class="modal-body">
				<input type="hidden" id="servicePackageId" value="" />
				<div class="row">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>ชื่อ Package บริการ</b></label>
						<input type="text" class="form-control" id="edit-servicepackage" 
							aria-describedby="emailHelp" value="">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-success" onclick="updateServicePackage();">บันทึก</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function loadServicePackage(id){
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/servicepackage/loadServicePackage/"+id,
			dataType : 'json',
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					$('#servicePackageId').val(data["result"]["id"]);
					$('#edit-servicepackage').val(data["result"]["packageName"]);
					$('#editOrg').modal('show');
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
	
	//update
	function updateServicePackage(){
		//generate bean
		var servicePackageBean = {};
		servicePackageBean.id = $('#servicePackageId').val();
		servicePackageBean.packageName = $('#edit-servicepackage').val();
			
		//validate basic
		//console.log(companyBean);
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${pageContext.request.contextPath}/servicepackage/update",
			dataType : 'json',
			data : JSON.stringify(servicePackageBean),
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

