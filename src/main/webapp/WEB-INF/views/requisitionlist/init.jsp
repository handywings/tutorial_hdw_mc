<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tld/permission.tld" prefix="perm"%>

<c:set var="mainMenu" value="stock" scope="request"/>
<c:set var="subMenu" value="requisitionlist" scope="request"/>

<!DOCTYPE html>
<html lang="en">
<head>
<title>รายการใบเบิกสินค้าทั้งหมด</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body class="fixed-header material-design fixed-sidebar">
	<div class="wrapper">

		<!-- Preloader -->
		<div class="preloader"></div>
		<jsp:include page="../layout/menu-left.jsp"></jsp:include>
		<jsp:include page="../layout/menu-right.jsp"></jsp:include>

		<jsp:include page="../layout/menu-top.jsp"></jsp:include>

		<div class="site-content">
			<!-- Content -->
			<div class="content-area py-1">
				<div class="container-fluid">
					<div class="row mt05 mb05" >
						<div class="col-md-8">
							<h3>รายการใบเบิกสินค้าทั้งหมด</h3>
					<ol class="breadcrumb no-bg mb-1">
						<li class="breadcrumb-item active">ระบบคลังสินค้า / บริการ</li>
						<li class="breadcrumb-item active">รายการใบเบิกสินค้าทั้งหมด</li>
					</ol>
						</div>
						<div class="col-md-4 mt05" align="right">
						<perm:permission object="M20.add" >
							<a href="${pageContext.request.contextPath}/requisition"><button type="button" class="btn btn-info label-left float-xs-right mr-0-5">
									<span class="btn-label"><i class="ti-plus"></i></span>เบิกสินค้าและอุปกรณ์
								</button></a>
						</perm:permission>
						</div>
					</div>
					<div class="card mt05">
						<div class="card-header text-uppercase">
							<h4>
								<i class="zmdi zmdi-search"></i>&nbsp; ค้นหาใบเบิกสินค้า
							</h4>
						</div>
						<div class="posts-list posts-list-1">
							<div class="pl-item">
								<div class="row">
									<form:form method="post" commandName="requisitionDocumentBean" action="requisitionlist/search">
										<div class="col-md-4 mb05">
											<form:input type="text" id="key" path="key" class="form-control" value="${requisitionDocumentBean.key}"
												placeholder="ระบุเลขที่ใบเบิกสินค้า ชื่อหรือรหัสพนักงานที่ต้องการค้นหา..." />
										</div>
										<div class="col-md-3 mb05">
											<form:input  type="text" id="daterange" path="daterange" 
											class="form-control" name="daterange" value="${requisitionDocumentBean.daterange}" />
										</div>
										<div class="col-md-3 mb05">
											<form:select path="withdraw" class="form-control" id="searchWithdrawSelect" >
												<option value="" selected="selected">--- แสดงการเบิกทุกรูปแบบ ---</option>
												<c:choose>
														<c:when test="${requisitionDocumentBean.withdraw eq 'R'}">
															<option value="R" selected="selected">เบิกเพื่อไปสำรอง (ยังไม่ตัดสต็อก)</option>
													    </c:when>
														 <c:otherwise>
													        <option value="R">เบิกเพื่อไปสำรอง (ยังไม่ตัดสต็อก)</option>
													    </c:otherwise> 
												</c:choose>
												<c:choose>
														<c:when test="${requisitionDocumentBean.withdraw eq 'O'}">
															<option value="O" selected="selected">เบิกเพื่องานสำนักงาน (ตัดสต็อกทันที)</option>
													    </c:when>
														 <c:otherwise>
													        <option value="O">เบิกเพื่องานสำนักงาน (ตัดสต็อกทันที)</option>
													    </c:otherwise> 
												</c:choose>
												<c:choose>
														<c:when test="${requisitionDocumentBean.withdraw eq 'U'}">
															<option value="U" selected="selected">เบิกเพื่อปรับปรุงลดสต็อก (ตัดสต็อกทันที)</option>
													    </c:when>
														 <c:otherwise>
													        <option value="U">เบิกเพื่อปรับปรุงลดสต็อก (ตัดสต็อกทันที)</option>
													    </c:otherwise> 
												</c:choose>
											</form:select>
										</div>										
										<div class="col-md-2 mb05">
											<button type="submit" class="btn btn-lg bg-instagram col-xs-12 b-a-0 waves-effect waves-light">
												<span class="ti-search"></span> ค้นหา
											</button>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					
					<div class="tab-content">
					<jsp:include page="block_data_all.jsp"></jsp:include>
					<jsp:include page="block_data_normal.jsp"></jsp:include>
					<jsp:include page="block_data_cancel.jsp"></jsp:include>
					</div>
				</div>
			</div>
			
			<!-- Footer -->
			<jsp:include page="../layout/footer.jsp"></jsp:include>
		</div>

	</div>

	<jsp:include page="../layout/script.jsp"></jsp:include>
	<c:if test="${not empty alertBean}">
	    <script type="text/javascript">
	    	openAlert('${alertBean.type}','${alertBean.title}','${alertBean.detail}');
		</script>
	</c:if>
	
<script type="text/javascript">
	
</script>
	
</body>
</html>
