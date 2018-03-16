<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<script type="text/javascript">
	function openDialogDelete(id){
		swal({
			title : 'คุณต้องการลบข้อมูลตำแหน่งงานรายการนี้',
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
				$('.preloader').modal('show');
				$.ajax({
					type : "GET",
					contentType : "application/json",
					url : "${pageContext.request.contextPath}/position/delete/"+id,
					dataType : 'json',
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