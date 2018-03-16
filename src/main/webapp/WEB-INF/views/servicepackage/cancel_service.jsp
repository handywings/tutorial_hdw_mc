<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<script type="text/javascript">
	function openDialogCancel(id){
		swal({
			title : 'คุณต้องการยกเลิกบริการนี้',
			// 										text : "ถ้าคุณต้องการลบหน่วยนับรายการนี้กรุณากดปุ่ม \"ยืนยัน\" ด้านล่าง",
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยัน',
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
					url : "${pageContext.request.contextPath}/servicepackage/cancel/"+id,
					dataType : 'json',
					//timeout : 100000,
					success : function(data) {
						window.location.href = "${pageContext.request.contextPath}/servicepackage/view/"+id;
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
	
	function openDialogEnable(id){
		swal({
			title : 'คุณต้องการเปิดใช้งานบริการนี้',
			// 										text : "ถ้าคุณต้องการลบหน่วยนับรายการนี้กรุณากดปุ่ม \"ยืนยัน\" ด้านล่าง",
			type : 'warning',
			showCancelButton : true,
			confirmButtonColor : '#3085d6',
			cancelButtonColor : '#d33',
			confirmButtonText : 'ยืนยัน',
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
					url : "${pageContext.request.contextPath}/servicepackage/enable/"+id,
					dataType : 'json',
					//timeout : 100000,
					success : function(data) {
						window.location.href = "${pageContext.request.contextPath}/servicepackage/view/"+id;
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