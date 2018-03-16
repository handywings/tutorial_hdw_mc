<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="changeStatus" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" align="left" style="display: none;"
	aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel"><span
					class="ti-search" data-toggle="tooltip" data-placement="bottom"
					title="" data-original-title="ดูรายละเอียด"></span> ปรับสถานะอุปกรณ์</h4>
			</div>
			<form id="formAddProductOrderRequiment">
			<div class="modal-body">
				<div class="row">
					<div class="form-group">
						<div class="col-md-12">
							<input type="hidden" id="hiddenItemId"
								value="${equipmentProductItemBean.id}" />
						</div>
					</div>
				</div>		
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="exampleInputEmail1"><b>สถานะอุปกรณ์<span class="text-red"> *</span></b></label> <select
								id="dropdownStatus" name="dropdownStatus" class="form-control">
								<option value="" selected="selected" disabled="disabled">---
									เลือกสถานะอุปกรณ์ ---</option>
								<option value="1">ใช้งานได้ปกติ</option>
								<option value="2">จอง</option>
								<option value="3">ยืม</option>
								<option value="0">เสีย</option>
								<option value="6">อยู่ระหว่างซ่อม</option>
								<option value="7">CA</option>
							</select>
							<div class="messages"></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">	
							<label for="exampleInputEmail1"><b>วันที่แจ้งซ่อม<span class="text-red"> *</span></b></label>
							<div class="input-group">
								<input type="text" class="datepickerEquimentProduct form-control"
									id="datepickerRepair" name="datepickerRepair" placeholder="วัน-เดือน-ปี">
								<div class="input-group-addon"><i class="fa fa-calendar-o"></i></div>
							</div>
							<div class="messages"></div>
						</div>
					</div>
				</div>
				<div class="row mt05">
					<div class="col-md-12">
						<label for="exampleInputEmail1"><b>เหตุผล</b></label>
						<textarea class="form-control" id="textRemark" rows="3"></textarea>
					</div>
				</div>

			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success">บันทึก</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$( document ).ready(function() {
		//set date picker
		$(".datepickerEquimentProduct").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE'
		});
	});
	
	function openDialogUpdateStatus() {
		$('#changeStatus').modal('show');
		var constraintsEdit = {
				datepickerRepair : {
					presence : {
						message : "^กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง"
					}
				},
				dropdownStatus : {
					presence : {
						message : "^กรุณาเลือกข้อมูล"
					}
				}
			};

			// Hook up the form so we can prevent it from being posted
			var formEdit = document.querySelector("form#formAddProductOrderRequiment");
			formEdit.addEventListener("submit", function(ev) {
				
				ev.preventDefault();
				var errors = validate(formEdit, constraintsEdit);
				// then we update the form to reflect the results
				showErrors(formEdit, errors || {});
				if (!errors) {
					updateStatus();
				}
			});


			// Hook up the inputs to validate on the fly
			var inputsEdit = document.querySelectorAll("input, textarea, select");
			for (var i = 0; i < inputsEdit.length; ++i) {
				inputsEdit.item(i).addEventListener("change", function(ev) {
					var errors = validate(formEdit, constraintsEdit) || {};
					showErrorsForInput(this, errors[this.name])
				});
			}
	}
	function updateStatus() {
		$('.preloader').modal('show');
		//create bean
		var jsonRequestUpdateStatus = {};
		jsonRequestUpdateStatus.equipmentProductItemId = $('#hiddenItemId')
				.val();
		jsonRequestUpdateStatus.status = $('#dropdownStatus').val();
		jsonRequestUpdateStatus.remark = $('#textRemark').val();
		jsonRequestUpdateStatus.dateRepair = $('#datepickerRepair').val();
		
		//send update
				$.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/productorderequipmentproduct/updateStatusEquipmentProductItem",
					dataType : 'json',
					async : false,
					data : JSON.stringify(jsonRequestUpdateStatus),
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
// 							window.location.reload();
							window.location.href = "${pageContext.request.contextPath}/productorderequipmentproduct";
						} else {

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

