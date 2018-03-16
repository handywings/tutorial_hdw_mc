<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript"> 
	function openModalSuccess(workSheetType) {
		swal(
				{
					//title : 'คุณต้องการมอบหมายงานที่เลือกให้ '+name,
					title : 'ยืนยันการบันทึกใบงานสำเร็จ',
					text : "ถ้าคุณต้องบันทึกใบงานกรุณากด \"ยืนยัน\" ด้านล่าง ระบบจะทำการตัดสต็อกทันที",
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
							if (isConfirm) {
								if(workSheetType == 'C_S'){
									updateWorksheetSettup('S');
									
								}else if(workSheetType == 'C_TTV'){
									updateWorksheetTune('S')
									
								}else if(workSheetType == 'C_C'){
									updateWorksheetConnect('S')
									
								}else if(workSheetType == 'C_CU'){
									updateWorksheetCut('S')
									
								}else if(workSheetType == 'C_AP'){
									updateWorksheetAddPoint('S')
									
								}else if(workSheetType == 'C_ASTB'){
									updateWorksheetAddSetTopbox('S')
									
								}else if(workSheetType == 'C_B'){
									updateWorksheetBorrow('S')
									
								}else if(workSheetType == 'C_M'){
									updateWorksheetMove('S')
									
								}else if(workSheetType == 'C_MP'){
									updateWorksheetMovePoint('S')
									
								}else if(workSheetType == 'C_RP'){
									updateWorksheetReduce('S')
									
								}else if(workSheetType == 'C_RC'){
									updateWorksheetRepairConnection('S')
								}else if(workSheetType == 'I_AP'){
									updateWorksheetanalyzeProblems('S')
								}
// 								swal({
// 									title : 'มอบหมายงานเสร็จสมบูรณ์!',
// 									text : 'ระบบทำการมมอบหมายงานให้ทีมช่างเรียบร้อยแล้ว',
// 									type : 'success',
// 									confirmButtonClass : 'btn btn-primary btn-lg',
// 									buttonsStyling : false
// 								});
							}
						})
	
	}
	</script>