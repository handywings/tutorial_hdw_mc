<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="job4" class="job" style="display: none;">
	<div class="row">
		<div class="col-md-8">
			<b>เพิ่มเติมอุปกรณ์ซ่อมสัญญาณเคเบิลทีวี และอุปกรณ์เสริมอื่นๆ</b>
		</div>
	</div>
	<div class="row mt05">
		<div class="col-md-12" id="c_repair_main_block">
			
		</div>
	</div>
	
	
	<div class="row mt05">
		<div class="col-md-12">
			<b>ปัญหาที่พบ</b>
			<textarea class="form-control" id="c_repair_remark" rows="3"></textarea>
		</div>
	</div>
</div>

	<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
	<script type="text/javascript">
	$( document ).ready(function() {

	});
	
	$(".btn_repair").hide();
	$(".div_repair").hide();
	$(".reason_repair").hide();
	
		function checkBoxRepair(id) {
			if ($("#checkbox_repair_" + id).is(':checked')) {
				$("#btn_repair_" + id).fadeIn();
				$("#div_repair_" + id).fadeIn();
				$("#reason_repair_" + id).fadeIn();
			} else {
				clearAppend(id);
				$("#btn_repair_" + id).hide();
				$("#div_repair_" + id).hide();
				$("#reason_repair_" + id).hide();
			}
		}
		
		function clearAppend(id){
			$(".tdSn"+id).html("");
			$(".tdEn"+id).html("");
			$(".tdETn"+id).html("");
			$(".tdStn"+id).html("");
			$("#c_repair_remark_" + id).val("");
		}
		
	</script>

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
function saveRepair(){
	$('.preloader').modal('show');
	var repairConnectionWorksheetBean = {};
	repairConnectionWorksheetBean.remark = $('#c_repair_remark').val();
	
	repairConnectionWorksheetBean.availableDateTime = $('#daterange1').val();
	
	//service application
	ServiceApplicationBean = {};
	ServiceApplicationBean.id = $('#idServiceApplication').val();
	repairConnectionWorksheetBean.serviceApplication = ServiceApplicationBean;
	
	//loop create match save bean
	var repairMatchItemBeanList = [];
	$(".c_repair_hidden_eItem_id").each(function(index) {
		var splitId = $(this).val().split("_");
		var idProductItem = splitId[0];
		var idRef = splitId[1];
		var RepairMatchItemBean = {};
		var productItemWorksheetId = $('#hiddenOldItem'+idRef).val();
		RepairMatchItemBean.oldItemId = productItemWorksheetId;
		RepairMatchItemBean.newItemId = idProductItem;
		RepairMatchItemBean.comment = $('#c_repair_remark_'+idRef).val();
		repairMatchItemBeanList.push(RepairMatchItemBean);
	});
	repairConnectionWorksheetBean.repairMatchItemBeanList =  repairMatchItemBeanList;
	console.log(repairConnectionWorksheetBean);
		//send save worksheet repair
		$.ajax({
			type : "POST",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/saveRepair",
			dataType : 'json',
			async : false,
			data : JSON.stringify(repairConnectionWorksheetBean),
			//timeout : 100000,
			success : function(data) {
				if (data["error"] == false) {
					window.location.reload();
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

	function loadRepair() {
		var serviceApplicationBean = {};
		serviceApplicationBean.id = $('#idServiceApplication').val();
		$
				.ajax({
					type : "POST",
					contentType : "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/worksheetadd/loadRepair",
					dataType : 'json',
					async : false,
					data : JSON.stringify(serviceApplicationBean),
					//timeout : 100000,
					success : function(data) {
						if (data["error"] == false) {
							var allBolck = "";
							var block1 = "<div class='row mt05'><div class='col-md-12'><fieldset>";
							var index = 1;
							for (var i = 0; i < data["result"].length; i++) {
								console.log(data["result"][i]);
// 								if((data["result"][i]["productTypeMatch"] == "R" || 
// 										data["result"][i]["productTypeMatch"] == "A" || 
// 										data["result"][i]["productTypeMatch"] == "B" ||
// 										data["result"][i]["productTypeMatch"] == "N" || 
// 										data["result"][i]["productTypeMatch"] == "O" || 
// 										!data["result"][i]["productTypeMatch"]) && !data["result"][i]["parent"] ){
								if(!data["result"][i]["parent"]){
								//console.log(data["result"][i]["equipmentProductItemBean"].expireGuarantee);
								var block2 = "<legend>&nbsp;&nbsp;&nbsp;อุปกรณ์ ลำดับที่ "
										+ (index++)
										+ "&nbsp;&nbsp;&nbsp;</legend>"
										+ "<div class='row mt05 mb05'><div class='col-md-12'><b>อุปกรณ์ที่ใช้งานในปัจจุบัน</b>"
										+ "<div class='table-responsive'><table class='table table-bordered mb-0 table-hover'>"
										+ "<thead class='thead-bg'>"
										+ "<tr><th><center>หมายเลข S/N</center></th>"
										+ "<th><center>ชื่อเรียกอุปกรณ์</center></th>"
										+ "<th><center>ประเภทอุปกรณ์</center></th>"
										+ "<th><center>สถานะอุปกรณ์</center></th>"
										+ "<th><center>สถานะการรับประกัน</center></th>"
										+ "</tr>"
										+ "</thead>"
										+ "<tbody>"
										+ "<tr role='row' class='odd'>";

								var typeGuarantee = "";
								if (data["result"][i]["equipmentProductItemBean"].expireGuarantee == true) {
									typeGuarantee = worksheetTitleTypeNotGuarantee; //properties.js
								} else {
									typeGuarantee = worksheetTitleTypeGuarantee; //properties.js
								}
								var hiddenOldItem = "<input type='hidden' id='hiddenOldItem"+i+"' value='"+ data["result"][i]["id"] +"' />";
								var block3 = "<td align='center'><span>"
										+ hiddenOldItem
										+ data["result"][i]["equipmentProductItemBean"]["serialNo"]
										+ "</span></td>"
										+ "<td align='center'><span>"
										+ data["result"][i]["equipmentProductItemBean"]["equipmentProductBean"].productName
										+ "</span></td>"
										+ "<td align='center'><span>"
										+ data["result"][i]["equipmentProductItemBean"]["equipmentProductBean"].equipmentTypeName
										+ "</span></td>"
										+ "<td align='center'><span>" + data["result"][i]["equipmentProductItemBean"]["status"] + "</span></td>"
										+ "<td align='center'><span>"
										+ typeGuarantee + "</span></td>"
										+ "</tr></tbody></table></div></div></div>";

								var block4 = "<div class='row mt05 pt05'>"
										+ "<div class='col-md-12'>"
										+ "<label class='custom-control custom-checkbox checkbox_repair'>"
										+ "<input type='checkbox' id='checkbox_repair_"
										+ i
										+ "'"
										+ "onclick='checkBoxRepair("
										+ i
										+ ")'; class='custom-control-input'>"
										+ "<span class='custom-control-indicator'></span> <span"+
								"class='custom-control-description'>แจ้งอุปกรณ์เสียต้องการแจ้งซ่อม</span>"
										+ "</label>"
										+ "<button type='button' onclick='openViewModalRepair("
										+ data["result"][i]["equipmentProductItemBean"]["equipmentProductBean"].id
										+ ","
										+ i
										+ ")'"
										+ "class='btn btn-info label-left float-xs-right btn_repair'"
										+ "id='btn_repair_"
										+ i
										+ "'>"
										+ "<span class='btn-label'><i class='ti-plus'></i></span>เลือกสินค้า</button></div></div>";

								var block5 = "<div class='row mt05 mb05 div_repair' id='div_repair_"+i+"'>"
										+ "<div class='col-md-12'><b>อุปกรณ์ที่ใช้งานทดแทน</b>"
										+ "<div class='table-responsive'><table class='table table-bordered mb-0 table-hover'>"
										+ "<thead class='thead-bg'>"
										+ "<tr>"
										+ "<th><center>หมายเลข S/N</center></th>"
										+ "<th><center>ชื่อเรียกอุปกรณ์</center></th>"
										+ "<th><center>ประเภทอุปกรณ์</center></th>"
										+ "<th><center>สถานะอุปกรณ์</center></th>"
										+ "<th><center>สถานะการรับประกัน</center></th>"
										+ "</tr>"
										+ "</thead>"
										+ "<tbody>"
										+ "<tr role='row' class='odd'>"
										+ "<td align='center' class='tdSn"+i+"'></td>"
										+ "<td align='center' class='tdEn"+i+"'></td>"
										+ "<td align='center' class='tdETn"+i+"'></td>"
										+ "<td align='center' class='tdESt"+i+"'></td>"
										+ "<td align='center' class='tdStn"+i+"'></td>"
										+ "</tr>"
										+ "</tbody></table></div></div>";

								var block6 = "<div class='reason_repair' id='reason_repair_"+i+"'>"
										+ "<div class='col-md-12'><b>ปัญหาที่พบ</b>"
										+ "<textarea class='form-control' id='c_repair_remark_"+i+"' rows='3'></textarea>"
										+ "</div></div></fieldset></div></div>";

								allBolck = allBolck + block1 + block2 + block3
										+ block4 + block5 + block6;
								}
							}

							//append to block
							$('#c_repair_main_block').html(allBolck);

							$(".btn_repair").hide();
							$(".div_repair").hide();
							$(".reason_repair").hide();
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