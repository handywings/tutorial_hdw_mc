<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
			<div class="row">
				<div class="col-md-3">
					<div class="dataTables_length" id="table-1_length">
						<label>Show <select id="show_entries_for_data_table_w" name="table-1_length" onchange="selectEntries(this);"
							aria-controls="table-1" class="form-control input-sm">
								<option value="10">10</option>
								<option value="25">25</option>
								<option value="50">50</option>
								<option value="100">100</option></select> entries
						</label>
					</div>
				</div>
				<div class="col-md-9 mt15" align="right">
				<img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/hourglass.png" style="width: 14px;"> รอมอบหมายงาน | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/wrench.png" style="width: 14px;"> อยู่ระหว่างดำเนินงาน | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/time-left.png" style="width: 14px;"> งานคงค้าง | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/checked.png" style="width: 14px;"> เสร็จสมบูรณ์ | <img src="${pageContext.request.contextPath}/resources/assets/img/status-icon/task/cancel.png" style="width: 14px;"> งานยกเลิก 
				</div>
			</div>
<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">

// $("#show_entries_for_data_table_w").val("${pagination.itemPerPage}");

$('select[name=table-1_length]').val("${pagination.itemPerPage}");

function selectEntries(ele){
	window.location.href = "${pageContext.request.contextPath}/${pagination.url }/page/${pagination.currentPage}/itemPerPage/"+ele.value+"/tab/"+$('#tab').val();
};

</script>