<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript"> 
	function openModalConfirm(status) {
		swal(
				{
					//title : 'คุณต้องการมอบหมายงานที่เลือกให้ '+name,
					title : 'ยืนยันการเปลี่ยนบริการ',
					text : "ถ้าคุณต้องการบันทึกการเปลี่ยนบริการกรุณากด \"ยืนยัน\" ด้านล่าง",
					type : 'success',
					showCancelButton : true,
					confirmButtonColor : '#3085d6',
					cancelButtonColor : '#d33',
					confirmButtonText : 'ยืนยัน',
					confirmButtonClass : 'btn btn-primary btn-lg mr-1',
					cancelButtonClass : 'btn btn-danger btn-lg',
					cancelButtonText : 'ยกเลิก',
					buttonsStyling : false
				})
				.then(
						function(isConfirm) {
							submitData(status);
						})
	
	}
	</script>