<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<!-- Sidebar -->
<div class="site-overlay"></div>
<div class="site-sidebar">
	<div class="custom-scroll custom-scroll-light"
		style="padding-bottom: 40px">
		<ul class="sidebar-menu" style="margin-bottom: 4rem;">
			<li class="menu-title">เมนูหลัก</li>
			<perm:permission object="M1.check">
				<li
					<c:if test="${requestScope.mainMenu eq 'dashboard' }">class="active-menu"</c:if>><a
					href="${pageContext.request.contextPath}/main"
					class="waves-effect waves-light"> <span class="s-icon"
						style="background: #e97293;"><i class="ti-home"></i></span> <span
						class="s-text">ภาพรวมระบบ</span>
				</a></li>
			</perm:permission>

			<perm:permission object="M2.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'setting' }">active</c:if>">
					<a class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #c277d0;"><i
							class="ti-settings"></i></span> <span class="s-text">ตั้งค่าข้อมูลพื้นฐาน</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'setting'}">style="display: block;"</c:if>>
						<perm:permission object="M3.check">
							<li
								<c:if test="${requestScope.subMenu eq 'permissiongroup' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/permissiongroup">สิทธิ์การใช้งานระบบ</a></li>
						</perm:permission>

						<perm:permission object="M4.check">
							<li
								<c:if test="${requestScope.subMenu eq 'company' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/company">ข้อมูลบริษัท</a></li>
						</perm:permission>

						<perm:permission object="M5.check">
							<li
								<c:if test="${requestScope.subMenu eq 'position' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/position">ข้อมูลตำแหน่งงาน</a></li>
						</perm:permission>

						<perm:permission object="M6.check">
							<li
								<c:if test="${requestScope.subMenu eq 'zone' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/zone">ข้อมูลเขตชุมชน/โครงการ</a></li>
						</perm:permission>

						<perm:permission object="M7.check">
							<li
								<c:if test="${requestScope.subMenu eq 'unit' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/unit">รายการหน่วยนับ</a></li>
						</perm:permission>

						<perm:permission object="M8.check">
							<li
								<c:if test="${requestScope.subMenu eq 'servicepackagetype' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/servicepackagetype">ประเภทสินค้าและบริการ</a></li>
						</perm:permission>

						<li
							<c:if test="${requestScope.subMenu eq 'menuReport' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/menureport">รายการรูปแบบใบงาน</a></li>

<%-- 						<perm:permission object="M9.check"> --%>
<!-- 							<li -->
<%-- 								<c:if test="${requestScope.subMenu eq 'backup' }">class="active-menu"</c:if>><a --%>
<%-- 								href="${pageContext.request.contextPath}/backup">สำรองข้อมูล</a></li> --%>
<%-- 						</perm:permission> --%>
						
						<perm:permission object="M9.check">
							<li
								<c:if test="${requestScope.subMenu eq 'migrate' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/migrate">นำเข้าข้อมูลเก่า</a></li>
						</perm:permission>
					</ul>
				</li>
			</perm:permission>

			<perm:permission object="M10.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'personnel' }">active</c:if>"><a
					class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #8384e3;"><i
							class="ti-id-badge"></i></span> <span class="s-text">ข้อมูลพนักงาน</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'personnel'}">style="display: block;"</c:if>>
						<perm:permission object="M11.check">
							<li
								<c:if test="${requestScope.subMenu eq 'personnel' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/personnel">ระเบียนพนักงาน</a></li>
						</perm:permission>

						<perm:permission object="M12.check">
							<li
								<c:if test="${requestScope.subMenu eq 'techniciangroup' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/techniciangroup">ข้อมูลกลุ่มช่างเทคนิค</a></li>
						</perm:permission>

						<perm:permission object="M13.check">
							<li
								<c:if test="${requestScope.subMenu eq 'cashier' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/cashier">ข้อมูลพนักงานเก็บเงิน</a></li>
						</perm:permission>
					</ul></li>
			</perm:permission>

			<perm:permission object="M14.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'package' }">active</c:if>">
					<a class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #73beff;"> <i
							class="ti-gift"></i></span> <span class="s-text">Package บริการ</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'package'}">style="display: block;"</c:if>>
						<perm:permission object="M46.check">
						<li
							<c:if test="${requestScope.subMenu eq 'servicepackage' }">class="active-menu"</c:if>><a
							href="${pageContext.request.contextPath}/servicepackage">รายการ
								Package บริการ</a></li>
						</perm:permission>
					</ul>
				</li>
			</perm:permission>

			<perm:permission object="M15.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'stock' }">active</c:if>"><a
					class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #2ecbbe;"><i
							class="ti-package"></i></span> <span class="s-text">ระบบคลังสินค้า
							/ บริการ</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'stock'}">style="display: block;"</c:if>>
						<perm:permission object="M16.check">
							<li
								<c:if test="${requestScope.subMenu eq 'productoverview' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/productoverview">ข้อมูลคลังสินค้า</a></li>
						</perm:permission>

						<perm:permission object="M17.check">
							<li
								<c:if test="${requestScope.subMenu eq 'equipmentproductcategory' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/equipmentproductcategory">หมวดหมู่วัสดุอุปกรณ์</a></li>
						</perm:permission>

						<perm:permission object="M18.add">
							<li
								<c:if test="${requestScope.subMenu eq 'productadd' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/productadd">นำเข้าสินค้า
									/ เพิ่มบริการ</a></li>
						</perm:permission>

						<perm:permission object="M19.check">
							<li
								<c:if test="${requestScope.subMenu eq 'productorderequipmentproduct' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/productorderequipmentproduct">รายการสินค้าและบริการ</a></li>
						</perm:permission>

						<perm:permission object="M20.check">
							<li
								<c:if test="${requestScope.subMenu eq 'requisition' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/requisition">เบิกสินค้าและอุปกรณ์</a></li>
						</perm:permission>

						<perm:permission object="M21.check">
							<li
								<c:if test="${requestScope.subMenu eq 'requisitionlist' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/requisitionlist">รายการใบเบิกสินค้า</a></li>
						</perm:permission>
					</ul></li>
			</perm:permission>

			<perm:permission object="M22.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'customer' }">active</c:if>"><a
					class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #56b786;"><i
							class="ti-user"></i></span> <span class="s-text">ระบบลูกค้าสัมพันธ์</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'customer'}">style="display: block;"</c:if>>
						<perm:permission object="M23.add">
							<li
								<c:if test="${requestScope.subMenu eq 'serviceapplication' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/serviceapplication">สมัครใช้บริการ</a></li>
						</perm:permission>
						<perm:permission object="M24.check">
							<li
								<c:if test="${requestScope.subMenu eq 'customerregistration' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/customerregistration">ทะเบียนลูกค้า</a></li>
						</perm:permission>
						<perm:permission object="M25.check">
							<li
								<c:if test="${requestScope.subMenu eq 'serviceapplicationlist' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/serviceapplicationlist">รายการใบสมัครใช้บริการ</a></li>
						</perm:permission>
						<perm:permission object="M26.check">
							<li
								<c:if test="${requestScope.subMenu eq 'serviceapplicationlistzone' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/serviceapplicationlistzone">ใบสมัครแยกตามเขตพื้นที่</a></li>
						</perm:permission>
						<perm:permission object="M27.check">
							<li
								<c:if test="${requestScope.subMenu eq 'cancelservice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/cancelservice">แจ้งยกเลิกใช้บริการ</a></li>
						</perm:permission>
						<perm:permission object="M27.check">
							<li
								<c:if test="${requestScope.subMenu eq 'changeservice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/changeservice">แจ้งเปลี่ยนบริการ</a></li>
						</perm:permission>
					</ul></li>
			</perm:permission>

			<perm:permission object="M28.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'worksheet' }">active</c:if>"><a
					class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #f49755;"><i
							class="ti-agenda"></i></span> <span class="s-text">ระบบจัดการข้อมูลงาน</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'worksheet'}">style="display: block;"</c:if>>
						<perm:permission object="M29.check">
							<li
								<c:if test="${requestScope.subMenu eq 'worksheetadd' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/worksheetadd">เพิ่มใบงาน</a></li>
						</perm:permission>

						<perm:permission object="M30.check">
							<li
								<c:if test="${requestScope.subMenu eq 'assignworksheet' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/assignworksheet">มอบหมายงาน</a></li>
						</perm:permission>

						<perm:permission object="M31.check">
							<li
								<c:if test="${requestScope.subMenu eq 'worksheetlist' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/worksheetlist">รายการใบงานทั้งหมด</a></li>
						</perm:permission>

						<perm:permission object="M32.check">
							<li
								<c:if test="${requestScope.subMenu eq 'calist' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/calist">รายการใบสมัครรอ
									CA</a></li>
						</perm:permission>
					</ul></li>
			</perm:permission>

			<perm:permission object="M33.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'finance' }">active</c:if>"><a
					class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #e87474;"><i
							class="ti-server"></i></span> <span class="s-text">ระบบการเงิน</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'finance'}">style="display: block;"</c:if>>
						<perm:permission object="M34.check">
							<li
								<c:if test="${requestScope.subMenu eq 'invoice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/invoice">รายการใบแจ้งหนี้
									/ ชำระเงิน</a></li>
						</perm:permission>

						<perm:permission object="M35.check">
							<li
								<c:if test="${requestScope.subMenu eq 'receipt' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/receipt">ใบเสร็จรับเงิน
									/ ใบกำกับภาษี</a></li>
						</perm:permission>

						<perm:permission object="M36.check">
							<li
								<c:if test="${requestScope.subMenu eq 'generatemanualinvoice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/generatemanualinvoice">สร้างใบแจ้งหนี้ตามรอบบิล</a></li>
						</perm:permission>

						<perm:permission object="M37.check">
							<li
								<c:if test="${requestScope.subMenu eq 'suspenstiontempo' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/suspenstiontempo">ข้อมูลลูกค้าค้างชำระ</a></li>
						</perm:permission>

						<perm:permission object="M38.check">
							<li
								<c:if test="${requestScope.subMenu eq 'validateinvoice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/validateinvoice">ตรวจสอบใบเสร็จรับเงิน</a></li>
						</perm:permission>

						<perm:permission object="M39.check">
							<li
								<c:if test="${requestScope.subMenu eq 'refund' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/refund">รายการคืนเงินมัดจำ</a></li>
						</perm:permission>

						<perm:permission object="M40.check">
							<li
								<c:if test="${requestScope.subMenu eq 'printinvoice' }">class="active-menu"</c:if>><a
								href="${pageContext.request.contextPath}/printinvoice/cashier">มอบหมายพนักงานเก็บเงิน</a></li>
						</perm:permission>
					</ul></li>
			</perm:permission>

			<perm:permission object="M41.check">
				<li
					class="with-sub <c:if test="${requestScope.mainMenu eq 'report' }">active</c:if>">
					<a class="waves-effect  waves-light cursor-p"> <span
						class="s-caret"><i class="fa fa-angle-down"></i></span> <span
						class="s-icon" style="background: #9faec4;"><i
							class="ti-clipboard"></i></span> <span class="s-text">รายงาน</span>
				</a>
					<ul
						<c:if test="${requestScope.mainMenu eq 'report'}">style="display: block;"</c:if>>
						<perm:permission object="M42.check">
							<li
								class="with-sub <c:if test="${requestScope.mainSubMenu eq 'reportcustomer' }">active</c:if>"><a
								href="javascript: void(0);"> <span class="s-caret"></span> <span
									class="s-text"><i class="fa fa-file-text-o"
										style="font-size: 12px; color: #afafaf;"></i>
										รายงานทะเบียนลูกค้า</span>
							</a>
								<ul
									<c:if test="${requestScope.mainSubMenu eq 'reportcustomer'}">style="display: block;"</c:if>>
									<li
										<c:if test="${requestScope.subMenu eq 'bycondition' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/customerreport/bycondition">ค้นหาตามเงื่อนไข</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'byservicetype' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/customerreport/byservicetype">แยกตามประเภทบริการ</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'byserviceapptype' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/customerreport/byserviceapptype">แยกตามประเภทใบสมัคร</a></li>
									<%-- 							<li><a href="${pageContext.request.contextPath}/customerreport/byworksheetstatus">แยกตามสถานะการติดตั้ง</a></li> --%>
									<li
										<c:if test="${requestScope.subMenu eq 'byworksheetcancel' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/customerreport/byworksheetcancel">ลูกค้าตัดสายหรือยกเลิกใช้บริการ</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'byworksheetmove' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/customerreport/byworksheetmove">ลูกค้าที่แจ้งย้ายสาย</a></li>
									<!-- 							<li class="with-sub"><a href="javascript: void(0);"> <span -->
									<!-- 									class="s-caret"><i class="fa fa-angle-down"></i></span> <span -->
									<!-- 									class="s-text">ทะเบียนลูกค้าทั้งหมด</span> -->
									<!-- 							</a></li> -->
								</ul></li>
						</perm:permission>

						<perm:permission object="M43.check">
							<li
								class="with-sub <c:if test="${requestScope.mainSubMenu eq 'reportfinance' }">active</c:if>"><a
								href="javascript: void(0);"> <span class="s-caret"></span> <span
									class="s-text"><i class="fa fa-file-text-o"
										style="font-size: 12px; color: #afafaf;"></i> รายงานการเงิน</span>
							</a>
								<ul
									<c:if test="${requestScope.mainSubMenu eq 'reportfinance'}">style="display: block;"</c:if>>
									<%-- 							<li><a href="${pageContext.request.contextPath}/financialreport/byserviceapplication">รายได้แยกตามบริการ</a></li> --%>
									<li
										<c:if test="${requestScope.subMenu eq 'byinvoicetype' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/financialreport/byinvoicetype">รายได้แยกตามใบแจ้งหนี้</a></li>
									<%-- 							<li><a href="${pageContext.request.contextPath}/financialreport/bycustomertype">รายได้แยกตามลักษณะสมาชิก</a></li>							 --%>
									<!-- 							<li><a href="javascript: void(0);">รายการค้างชำระ</a></li> -->
									<li
										<c:if test="${requestScope.subMenu eq 'bybaddebt' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/financialreport/bybaddebt">รายการหนี้สูญ</a></li>
									<!-- 							<li><a href="javascript: void(0);">รายงานภาษี</a></li> -->
									<!-- 							<li><a href="javascript: void(0);">รายงานการชำระเงิน</a></li> -->
									<!-- 							<li><a href="javascript: void(0);">รายงานตรวจสอบบิล</a></li> -->
								</ul></li>
						</perm:permission>

						<perm:permission object="M44.check">
							<li
								class="with-sub <c:if test="${requestScope.mainSubMenu eq 'reportstock' }">active</c:if>"><a
								href="javascript: void(0);"> <span class="s-caret"></span> <span
									class="s-text"><i class="fa fa-file-text-o"
										style="font-size: 12px; color: #afafaf;"></i> รายงานคลังสินค้า</span>
							</a>
								<ul
									<c:if test="${requestScope.mainSubMenu eq 'reportstock'}">style="display: block;"</c:if>>
									<li
										<c:if test="${requestScope.subMenu eq 'productimport' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/stockreport/productimport">รายการสินค้านำเข้า</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'requisitionproduct' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/stockreport/requisitionproduct">รายการเบิกสินค้า</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'stocksummary' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/stockreport/stocksummary">รายงานสินค้าคงคลัง</a></li>
									<%-- 							<li><a href="${pageContext.request.contextPath}/stockreport/polist">สรุปรายการซื้อสินค้า</a></li> --%>
								</ul></li>
						</perm:permission>

						<perm:permission object="M45.check">
							<li
								class="with-sub <c:if test="${requestScope.mainSubMenu eq 'reportworksheet' }">active</c:if>"><a
								href="javascript: void(0);"> <span class="s-caret"></span> <span
									class="s-text"><i class="fa fa-file-text-o"
										style="font-size: 12px; color: #afafaf;"></i> รายงานใบงาน</span>
							</a>
								<ul
									<c:if test="${requestScope.mainSubMenu eq 'reportworksheet'}">style="display: block;"</c:if>>
									<li
										<c:if test="${requestScope.subMenu eq 'worksheetlist' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/worksheetreport/worksheetlist">ข้อมูลใบงาน</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'bytechnician' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/worksheetreport/bytechnician">ภาระงานช่าง</a></li>
									<li
										<c:if test="${requestScope.subMenu eq 'bydailywork' }">class="active-menu"</c:if>
										style="padding-left: 2rem;"><a
										href="${pageContext.request.contextPath}/worksheetreport/bydailywork">ใบงานประจำวัน</a></li>
									<%-- 							<li><a href="${pageContext.request.contextPath}/stockreport/polist">สรุปรายการซื้อสินค้า</a></li> --%>
								</ul></li>
						</perm:permission>
					</ul>
				</li>
			</perm:permission>
		</ul>
	</div>
</div>
