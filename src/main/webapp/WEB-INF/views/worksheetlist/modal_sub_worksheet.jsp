<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="modalAddSubWorksheet" tabindex="-1"
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
					<i class="zmdi zmdi-search"></i>&nbsp;ค้นหาใบงานย่อย
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div id="">
								<div class="row mb05">
									<div class="col-md-12 mb05">
										<div class="table-responsive">
												<table class="table table-bordered mb-0 table-hover"
													id="tableAddSubWorksheet">
													<thead class="thead-bg">
														<tr>
															<th style="vertical-align: middle; width: 20px;">
															<center>
															<label
																class="custom-control custom-checkbox"> <input
																	type="checkbox"
																	class="custom-control-input checkAllSubWorksheet">
																	<span class="custom-control-indicator"></span> <span
																	class="custom-control-description"></span>
															</label>
															</center>
															</th>
															<th style="vertical-align: middle;">ประเภทใบงาน</th>
															<th style="vertical-align: middle;"><center>ประเภท</center></th>
														</tr>
													</thead>
													<tbody id="tbody-child-item-add-subworksheet">
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_S"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>ติดตั้งใหม่</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_AP"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>เสริมจุดบริการ</td>
															<td><center>Cable</center></td>
														</tr>
<!-- 														<tr> -->
<%-- 															<td><center> --%>
<!-- 																	<label class="custom-control custom-checkbox"> -->
<!-- 																		<input type="checkbox" -->
<!-- 																		class="custom-control-input checkBoxSubWorksheetItem" -->
<!-- 																		value="C_C"> <span -->
<!-- 																		class="custom-control-indicator"></span> <span -->
<!-- 																		class="custom-control-description"></span> -->
<!-- 																	</label> -->
<%-- 																</center></td> --%>
<!-- 															<td>การจั้มสาย</td> -->
<%-- 															<td><center>Cable</center></td> --%>
<!-- 														</tr> -->
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_TTV"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การจูนสัญญาณโทรทัศน์</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_RC"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การซ่อมสัญญาณ</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_ASTB"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>ขอเพิ่มอุปกรณ์รับสัญญาณเคเบิลทีวี</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_MP"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การย้ายจุด</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_RP"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การลดจุด</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_CU"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การตัดสาย</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_M"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>การย้ายสาย</td>
															<td><center>Cable</center></td>
														</tr>
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="C_B"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td>แจ้งยืมอุปกรณ์รับสัญญาณเคเบิลทีวี</td>
															<td><center>Cable</center></td>
														</tr>
														
														<c:forEach var="menuReportBean" items="${menuReportBeanList}" varStatus="i">
														<tr>
															<td><center>
																	<label class="custom-control custom-checkbox">
																		<input type="checkbox"
																		class="custom-control-input checkBoxSubWorksheetItem"
																		value="${menuReportBean.id}"> <span
																		class="custom-control-indicator"></span> <span
																		class="custom-control-description"></span>
																	</label>
																</center></td>
															<td><span class="span_text">${menuReportBean.menuReportName}</span></td>
															<td><center>วิเคราะห์ปัญหา</center></td>
														</tr>
														</c:forEach>
													</tbody>
												</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>				
			</div>
			<div class="modal-footer">
					<button type="button" class="btn btn-success"
						onclick="appendToTableSubWorksheet();">ตกลง</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
				</div>
		</div>
	</div>
	</div>
	<script type="text/javascript"
		src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript">
	$(".checkAllSubWorksheet").click(function () {
		var checkBox = $(".checkBoxSubWorksheetItem");
		checkBox.prop('checked', $(this).prop("checked"));
	});
	
		function openModalSearchSubWorksheet() {
			$('.checkBoxSubWorksheetItem').prop('checked', false); // Unchecks it
			$('.checkAllSubWorksheet').prop('checked', false); // Unchecks it
			
			$('#modalAddSubWorksheet').modal('show');
			//$('#modalAddSubWorksheet').css('z-index', 1010);
		}
		//append to table
		function appendToTableSubWorksheet(){
			$('.checkBoxSubWorksheetItem').each(function (i) {
				if($(this).is(":checked")){
					
							var text = convertWorksheetTypeToText($(this).val());
							console.log("AAAAAAAA");
							if(!text){
								var span_text = $(this).parent().parent().parent().parent();
								text = span_text.find('.span_text').text();
							}
							
							var rows = "";
			    			var startRow = "<tr class='trNewItemSubWorksheet'>";
							var endRow = "</tr>";
							var columnOrderNo = "<td><center>"+
							'<span class="no-item-subworksheet"></span></center>'+
							"<input type='hidden' class='subWorksheetIdClass' value='"+ $(this).val() +"' /></td>";
							var columnSubWorkSheetName = "<td>"+ text +"</td>";
							var columnSubWorkSheetRemark = '<td><textarea class="form-control remark" rows="3"></textarea></td>';
							var columnSubWorkSheetPrice = "<td><input type='number' min='0' value='0' /></td>";
							var columnAction = '<td><center>'+
							'<a class="run-swal cursor-p removeSubWorksheetItem"> <span class="ti-trash" '+
							'data-toggle="tooltip" data-placement="bottom" title="" data-original-title=""></span></a>'+
							'</center></td>';
							
							rows = rows + startRow + columnOrderNo + columnSubWorkSheetName + columnSubWorkSheetRemark;
							rows = rows + columnSubWorkSheetPrice + columnAction + endRow;
							$('#tableSubWorksheet > tbody').append(rows);
			    		
				}
				
			});
			
			// เรียงลำดับ
			$('#tableSubWorksheet > tbody tr').each(function (i) {
			     $(this).find('.no-item-subworksheet').text((i + 1));
			});
			
			$('#modalAddSubWorksheet').modal('hide');
			
			// ลบ row
			$(".removeSubWorksheetItem").click(function () {
				$(this).closest('tr').remove();
				// เรียงลำดับ
				$('#tableSubWorksheet > tbody tr').each(function (i) {
				     $(this).find('.no-item-subworksheet').text((i + 1));
				});
			});
		}
		
		// ลบ row
		$(".removeSubWorksheetItem").click(function () {
			$(this).closest('tr').remove();
			// เรียงลำดับ
			$('#tableSubWorksheet > tbody tr').each(function (i) {
			     $(this).find('.no-item-subworksheet').text((i + 1));
			});
		});
	</script>