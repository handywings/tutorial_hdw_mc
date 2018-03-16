<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_master_add_equipment">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-bordered mb-0 table-hover">
								<thead class="thead-bg">
									<tr>
										<th style="width: 20px;"><center>ลำดับ</center></th>
										<th><center>SN</center></th>
										<th><center>อ้างอิง</center></th>
										<th><center>จำนวนรับเข้า</center></th>
										<th><center>สิ้นสุดประกัน</center></th>
										<th><center>วันสั่งซื้อ</center></th>
										<th style="width: 140px;"><center>ราคาต้นทุน</center></th>
										<th style="width: 140px;"><center>ราคารวมภาษี</center></th>
										<th style="width: 140px;"><center>ราคาขาย</center></th>
										<th width="70px;"></th>
									</tr>
								</thead>
								<tbody id="tbodyNewItem">

									<tr class="trNewItem">
										<td class="td-middle" align="center"><center>
												<span id="spanMasterEquipmentProductOrderCount"></span>
											</center></td>
										<td class="td-middle"><input type="text"
											id="masterEquipmentProductSerial"
											name="masterEquipmentProductSerial" class="form-control"></td>
										<td class="td-middle"><input type="text"
											id="masterEquipmentProductReference"
											name="masterEquipmentProductReference" class="form-control"></td>
										<td class="td-middle"><div>
												<input type="number" class="form-control" value="1" min="1"
													style="text-align: center;"
													id="masterEquipmentProductCount">

											</div></td>
										<td class="td-middle">
											<div class="input-group">
												<input type="text"
													class="datepickerEquimentProduct form-control"
													style="text-align: center;" id="datepickerGuarantee"
													placeholder="วัน-เดือน-ปี">
												<!--<div class="input-group-addon"><i class="fa fa-calendar-o"></i></div>-->
											</div>
										</td>
										<td class="td-middle">
											<div class="input-group">
												<input type="text"
													class="datepickerOrderDate form-control"
													style="text-align: center;" id="datepickerOrderDate"
													placeholder="วัน-เดือน-ปี">
												<!-- <div class="input-group-addon"><i class="fa fa-calendar-o"></i></div> -->
											</div>
										</td>
										<td class="td-middle"><div class="input-group">
												<input type="text" class="form-control productCost" value="0"
													style="text-align: right;" id="productCost">
												<div class="input-group-addon">บาท</div>
											</div></td>
										<td class="td-middle"><div class="input-group">
												<input type="text" class="form-control priceIncTax" value="0"
													style="text-align: right;" id="priceIncTax">
												<div class="input-group-addon">บาท</div>
											</div></td>
										<td class="td-middle"><div class="input-group">
												<input type="text" class="form-control productSalePrice" value="0"
													style="text-align: right;" id="productSalePrice">
												<div class="input-group-addon">บาท</div>
											</div></td>
										<td align="center" class="td-middle">
											<button type="button"
												class="btn btn-success waves-effect waves-light btn-circle"
												onclick="appendBlockMasterToChild()">
												<i class="ti-plus"></i>
											</button>
										</td>
									</tr>
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

<script type="text/javascript">
	var rowCount = 0, vat = 1;
	$( document ).ready(function() {
		//set date picker
		$(".datepickerEquimentProduct").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
			language : 'th',
			thaiyear : true
		});
		
		$(".datepickerOrderDate").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
			language : 'th',
			thaiyear : true
		}).datepicker("setDate", "0");
	
		//click remove in child list
		$('#tableEquipmentProductChild > tbody').on('click', '.removeRowsChildNew', function () {
			rowCount -= 1;
		    $(this).closest('tr').remove();
		    calculateOrderCount();
		    $('#masterEquipmentProductSerial').val('');
			$('#masterEquipmentProductSerial').focus();
		});
		
		$('.productCost').blur(function(){
			var stockId = $('#new_equipmentStock').val();
			var vat = loadVatWithStockId(stockId);
			console.log(vat);
			if( !isNaN(parseFloat($(this).val())+($(this).val()*(vat/100))) ){
				console.log($(this).val()*(vat/100));
				var valuePrice = parseFloat($(this).val())+($(this).val()*(vat/100));
				console.log(valuePrice);
				valuePrice = valuePrice.toFixed(2);
			    $(this).closest('tr').find('.priceIncTax').val(valuePrice);
			    $(this).closest('tr').find('.productSalePrice').val(valuePrice);
			    
// 			    var x = $(this).closest('tr').find('.productSalePrice').val();
// 			    $(this).closest('tr').find('.productSalePrice').val(formatNumber(x));
			    
// 			    var x = $(this).closest('tr').find('.priceIncTax').val();
// 			    $(this).closest('tr').find('.priceIncTax').val(formatNumber(x));
			    
// 			    $(this).closest('tr').find('.productCost').val(formatNumber($(this).val()));
			}else{
				$(this).closest('tr').find('.priceIncTax').val('0');
			    $(this).closest('tr').find('.productSalePrice').val('0');
			}
		    
		});

	});
	
	function loadVatWithStockId(id){
		var vat = 0.0;
		$.ajax({
			type : "GET",
			contentType : "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/productadd/loadVatWithStockId/"+id,
			dataType : 'json',
			async: false,
			//timeout : 100000,
			success : function(data) {
				if(data["error"] == false){
				    vat = data["result"];
					//return vat;
				}else{
					vat = 1;
				}
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
		return vat;
	}
	
	function appendBlockMasterToChild(){
		var tr = $('#tbodyNewItem > .trNewItem');
		var cloneTr = tr.clone();
		var isAppend = true;
		cloneTr.each(function() {
			var masterSN = $(this).find('#masterEquipmentProductSerial');
			var masterCount = $(this).find('#masterEquipmentProductCount');
			if(masterSN.val()){
				masterCount.val('1');	
				masterCount.prop( "disabled", true );
				$('#tbody-child-item tr').each(function(){
					var sn = $(this).find('#masterEquipmentProductSerial');
					if(masterSN.val()==sn.val()){
						isAppend = false;			
					}
				});
			}
			var td = $(this).children('td:last');
			var buttonDetachOntable = "<button type='button' class='btn btn-danger waves-effect waves-light btn-circle removeRowsChildNew'> <i class='ti-trash'></i> </button>";
			td.html(buttonDetachOntable);
		});
		if(isAppend){
			$('#tableEquipmentProductChild > tbody').append(cloneTr);
			rowCount += 1;
			calculateOrderCount();
		}
		//set date picker
		$(".datepickerEquimentProduct , .datepickerOrderDate").datepicker({
			autoclose: true,
			format: 'dd-mm-yyyy',
			todayHighlight:'TRUE',
			todayBtn: true,
			language : 'th',
			thaiyear : true
		});
		
		$('.productCost').blur(function(){
			var stockId = $('#new_equipmentStock').val();
			var vat = loadVatWithStockId(stockId);
			console.log(vat);
		    if( !isNaN(parseFloat($(this).val())+($(this).val()*(vat/100))) ){
				console.log($(this).val()*(vat/100));
				var valuePrice = parseFloat($(this).val())+($(this).val()*(vat/100));
				console.log(valuePrice);
				valuePrice = valuePrice.toFixed(2);
			    $(this).closest('tr').find('.priceIncTax').val(valuePrice);
			    $(this).closest('tr').find('.productSalePrice').val(valuePrice);
			    
// 			    var x = $(this).closest('tr').find('.productSalePrice').val();
// 			    $(this).closest('tr').find('.productSalePrice').val(formatNumber(x));
			    
// 			    var x = $(this).closest('tr').find('.priceIncTax').val();
// 			    $(this).closest('tr').find('.priceIncTax').val(formatNumber(x));
			    
// 			    $(this).closest('tr').find('.productCost').val(formatNumber($(this).val()));
			}else{
				$(this).closest('tr').find('.priceIncTax').val('0');
			    $(this).closest('tr').find('.productSalePrice').val('0');
			}

		});
		
		$('#masterEquipmentProductSerial').val('');
		$('#masterEquipmentProductSerial').focus();
		
	}

	function calculateOrderCount(){
// 		var rowCount = $('#tbody-child-item tr').length;
		$('#spanMasterEquipmentProductOrderCount').text(rowCount);
		//$('#tbody-child-item tr').find('td:not(:empty):first')
		var countInChild = 1;
		$('#tbody-child-item tr').each(function(){
			var td = $(this).children('td:first');
			td.text(countInChild++);
		});
	}
	
	function formatNumber (num) {
		console.log("formatNumber : "+num);
	    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,")
	}
	
	function addCommas(x) {
	    var parts = x.toString().split(".");
	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    return parts.join(".");
	}
	
	function digits (number) { 
	    return number.toString().replace(/(\\d)(?=(\\d\\d\\d)+(?!\\d))/g, "$1,"); 
	}
	
</script>