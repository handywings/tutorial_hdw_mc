<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript"> 
	function openModalNotSuccess(workSheetType) {
		swal(
				{
					//title : 'คุณต้องการมอบหมายงานที่เลือกให้ '+name,
					title : 'ยืนยันการบันทึกใบงานไม่สำเร็จ',
					text : "ถ้าคุณต้องบันทึกใบงานกรุณากด \"ยืนยัน\" ด้านล่าง ระบบจะทำการบันทึกเป็นใบงานคงค้าง",
					type : 'warning',
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
									updateWorksheetSettup('N');
									
								}else if(workSheetType == 'C_TTV'){
									updateWorksheetTune('N')
									
								}else if(workSheetType == 'C_C'){
									updateWorksheetConnect('N')
									
								}else if(workSheetType == 'C_CU'){
									updateWorksheetCut('N')
									
								}else if(workSheetType == 'C_AP'){
									updateWorksheetAddPoint('N')
									
								}else if(workSheetType == 'C_ASTB'){
									updateWorksheetAddSetTopbox('N')
									
								}else if(workSheetType == 'C_B'){
									updateWorksheetBorrow('N')
									
								}else if(workSheetType == 'C_M'){
									updateWorksheetMove('N')
									
								}else if(workSheetType == 'C_MP'){
									updateWorksheetMovePoint('N')
									
								}else if(workSheetType == 'C_RP'){
									updateWorksheetReduce('N')
									
								}else if(workSheetType == 'C_RC'){
									updateWorksheetRepairConnection('N')
								}else if(workSheetType == 'I_AP'){
									updateWorksheetanalyzeProblems('N')
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