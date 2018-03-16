<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<script type="text/javascript">
	function openDialogDelete(technicianGroupId, id){
		swal({
			title : 'คุณต้องการลบพนักงานรายการนี้ออกจากกลุ่ม',
			// 										text : "ถ้าคุณต้องการลบหน่วยนับรายการนี้กรุณากดปุ่ม \"ยืนยัน\" ด้านล่าง",
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยันการลบ',
			confirmButtonClass : 'btn btn-primary btn-lg mr-1',
			cancelButtonClass : 'btn btn-danger btn-lg',
			cancelButtonText : 'ยกเลิก',
			buttonsStyling : false
		}).then(function(isConfirm) {
			if (isConfirm) {
				/*swal({
					title : 'การลบเสร็จสมบูรณ์!',
					text : 'ระบบทำการลบบริษัทย่อยเรียบร้อยแล้ว',
					type : 'success',
					confirmButtonClass : 'btn btn-primary btn-lg',
					buttonsStyling : false
				});*/
				
				$.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/techniciangroup/deleteMember/"+technicianGroupId+"/"+id,
					dataType : 'json',
					async: false,
					//timeout : 100000,
					success : function(data) {
						if(data["error"] != true){ window.location.reload(); }
						else{
							alert(data["message"]);
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