<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card mt05" id="block_child_list_equipment">
	<div class="posts-list posts-list-1">
		<div class="pl-item">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-bordered mb-0 table-hover" id="tableEquipmentProductChild">
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
								<tbody id="tbody-child-item">
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
	
</script>