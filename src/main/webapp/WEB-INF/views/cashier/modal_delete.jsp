<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<script type="text/javascript">
	function openDialogDelete(id){
		swal({
			title : 'คุณต้องการลบพนักงานเก็บเงินรายการนี้',
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
				$.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/cashier/delete/"+id,
					dataType : 'json',
					async: false,
					//timeout : 100000,
					success : function(data) {
						window.location.reload();
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