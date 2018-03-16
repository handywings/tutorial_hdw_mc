<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_master_add_internet">
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
										<th><center>ลำดับ</center></th>
										<th><center>เอกสารอ้างอิง</center></th>
										<th><center>Username</center></th>
										<th><center>Password</center></th>
										<th width="70px;"></th>
									</tr>
								</thead>
								<tbody id="tbodyNewItem">

									<tr class="trNewItem">
										<td align="center"><center><span id="spanMasterInternetProductOrderCount"></span></center></td>
										<td><input type="text" id = "masterNetRef" name="masterNetRef" class="form-control netref-textbox">
										<div class="messagesRef"></div>
										</td>
										<td>
										<input type="text" id = "masterNetUsername" name="masterNetUsername" class="form-control username-textbox">
										<div class="messages"></div>
										</td>
										<td><input type="text" id = "masterNetPassword" name="masterNetPassword" class="form-control"></td>
										 
										<td align="center">
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

<script type="text/javascript"
	src="<c:url value="/resources/assets/plugins/jquery/jquery-1.12.3.min.js" />"></script>
<script type="text/javascript">
	$( document ).ready(function() {
		//set date picker
		  
		//click remove in child list
		$('#tableInternetProductChild > tbody').on('click', '.removeRowsChildNew', function () {
		    $(this).closest('tr').remove();
		    calculateOrderCount();
		});
		
	});
	
	function appendBlockMasterToChild(){
		var tr = $('#tbodyNewItem > .trNewItem');
		var cloneTr = tr.clone();
		cloneTr.each(function() {
			var td = $(this).children('td:last');
			var buttonDetachOntable = "<button type='button' class='btn btn-danger waves-effect waves-light btn-circle removeRowsChildNew'> <i class='ti-trash'></i> </button>";
			td.html(buttonDetachOntable);
		});
		
		$('#tableInternetProductChild > tbody').append(cloneTr);
		calculateOrderCount();
		
		//added 16-05-2017 blind textbox username for check dup
		$( ".username-textbox" ).keyup(function() {
			if(checkDuplicateUsername($(this).val())){
				$(this).next('.messages').html("<p class='help-block error'>ข้อมูลซ้ำกับในระบบ กรุณาเปลี่ยนใหม่</p>");
			}else{
				$(this).next('.messages').html("");
			}
		});
		
		$( ".netref-textbox" ).keyup(function() {
			if(isEmpty($(this).val())){
				$(this).next('.messagesRef').html("<p class='help-block error'>กรุณากรอกข้อมูล ห้ามเป็นค่าว่าง</p>");
			}else{
				$(this).next('.messagesRef').html("");
			}
		});
		 
	}
	
	function removeRowsChildNew(){
		
	}
	function calculateOrderCount(){
		var rowCount = $('#tbody-child-item tr').length;
		$('#spanMasterInternetProductOrderCount').text(rowCount + 1);
		//$('#tbody-child-item tr').find('td:not(:empty):first')
		var countInChild = 1;
		$('#tbody-child-item tr').each(function(){
			var td = $(this).children('td:first');
			td.text(countInChild++);
		});
	}
</script>