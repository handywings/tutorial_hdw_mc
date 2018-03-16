<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="modal fade" id="addEquipmentProductItemRepair" tabindex="-1"
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
					<i class="zmdi zmdi-search"></i>&nbsp;เลือกสินค้ารายชิ้น
				</h4>
			</div>

			<div class="modal-body">
				<div class="row">
					<div class="col-md-12">
						<div id="">
							<div class="row mb05">
								<div class="col-md-12 mb05">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered table-hover" id="tableEquipmentProductChildRepair">
												<thead class="thead-default">
													<tr>
														<th style="width: 40px;"><center>เลือก</center></th>
														<th style="vertical-align: middle;"><center>Serial Number</center></th>
														<th style="vertical-align: middle;"><center>จำนวนรับเข้า</center></th>
														<th style="vertical-align: middle;"><center>เอกสารอ้างอิง</center></th>
													</tr>
												</thead>
												<tbody id="tbody-child-itemRepair">
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
				<button type="button" class="btn btn-success" onclick="appendToBlockChildRepair();">ตกลง</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">ยกเลิก</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
var idRefAppend;

$( document ).ready(function() {
	$('#tableEquipmentProductChildRepair').DataTable({searching: true, paging: true});
});

function openViewModalRepair(equipmentProductId,idRef){
	idRefAppend = idRef;
	$('#addEquipmentProductItemRepair').modal('show');
	$('#tableEquipmentProductChildRepair').DataTable().destroy();
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : "${pageContext.request.contextPath}/worksheetadd/loadEquipmentProductItemWithEparentId/"+equipmentProductId,
		//dataType : 'json',
		async: false,
		//timeout : 100000,
		success : function(data) {
			if(data["error"] == false){		
				$(".c_repair_hidden_eItem_id").each( function(index){
				    
				});
				var rows = "";
				var startRow = "<tr>";
				var endRow = "</tr>";
				for(var i=0;i<data["result"].length;i++){
					var columnRadio = "<td><center><label class='custom-control custom-radio'>"+
					"<input name='c_repair_radioEquipmentProductItem' type='radio' value='" + data["result"][i]["id"] + "'"+
					"class='custom-control-input'> " + 
					"<span class='custom-control-indicator'></span>"+
					"<span class='custom-control-description'>&nbsp;</span></label>"+
					"</center></td>";
					var columnSerial = "<td><center>"+ data["result"][i]["serialNo"] +"</center></td>";
					var columnImport = "<td><center>"+ data["result"][i]["numberImport"] +"</center></td>";
					var columnDocRef = "<td><center>"+ data["result"][i]["referenceNo"] +"</center></td>";
					
					var chkDup = false;
					//check duplicate id
					$(".c_repair_hidden_eItem_id").each( function(index){
						var idOnly = $(this).val().split("_");
					    if(data["result"][i]["id"] == idOnly[0]) chkDup = true;
					});
					
					if(!chkDup){
						rows = rows + startRow + columnRadio + columnSerial;
						rows = rows + columnImport + columnDocRef  + endRow;
					}
				}
				
				if(rows.length > 0){
					$('#tableEquipmentProductChildRepair > tbody').html(rows);
				}else{
					$('#tableEquipmentProductChildRepair > tbody').empty();
				}
				
				$('#tableEquipmentProductChildRepair').dataTable({searching: true, paging: true});
				$('#tableEquipmentProductChildRepair').DataTable().draw();
			}else{
				
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
	function appendToBlockChildRepair(){
		var radioItemRepair = $("input:radio[name ='c_repair_radioEquipmentProductItem']:checked").val();
		if(typeof radioItemRepair != 'undefined'){
			loadEquipentProductItemRepair(radioItemRepair);
			$('#addEquipmentProductItemRepair').modal('hide');
		}
	}
	
	function loadEquipentProductItemRepair(id){
		$('.preloader').modal('show');
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/worksheetadd/loadEquipmentProductItem/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
					var idEItemHidden = "<input type='hidden' class='c_repair_hidden_eItem_id' value='"+data["result"].id+"_"+idRefAppend+"' />";
					$(".tdSn"+idRefAppend).html("<span>"+ idEItemHidden + data["result"].serialNo +"</span>");
					$(".tdEn"+idRefAppend).html("<span>"+ data["result"]["equipmentProductBean"].productName +"</span>");
					$(".tdETn"+idRefAppend).html("<span>"+ data["result"]["equipmentProductBean"].equipmentTypeName +"</span>");
					$(".tdESt"+idRefAppend).html("<span>"+ data["result"].status +"</span>");
					
					var typeGuarantee = "";
					if(data["result"].expireGuarantee == true){
						typeGuarantee = worksheetTitleTypeNotGuarantee; //properties.js
					}else{
						typeGuarantee = worksheetTitleTypeGuarantee; //properties.js
					}
					
					$(".tdStn"+idRefAppend).html("<span>"+ typeGuarantee +"</span>");
				}
				$('.preloader').modal('hide');
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
