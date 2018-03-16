<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>pagination example</title>
<jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body style="background-color: #FFF" class="material-design fixed-sidebar fixed-header">
	<div style="padding:50px">
	<div id="table-1_wrapper"
		class="dataTables_wrapper form-inline dt-bootstrap4 no-footer">
		<jsp:include page="../layout/paging-entries.jsp"></jsp:include>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-bordered dataTable no-footer"
					role="grid" aria-describedby="table-1_info">
					<thead class="thead-default">
						<tr role="row">
							<th>column-1</th>
							<th>column-2</th>
							<th>column-3</th>
							<th>column-4</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="unitBean" items="${pagination.dataListBean}" varStatus="i">
							<tr role="row">
								<c:choose>
									<c:when test="${pagination.currentPage == 1 }">
										<td>${i.count}</td>
									</c:when>
									<c:otherwise>
										<td>${i.count + ((pagination.currentPage * pagination.itemPerPage)-1) - (pagination.itemPerPage - 1)}</td>
									</c:otherwise>
								</c:choose>
								<td>${unitBean.unitName }</td>
								<td>${unitBean.createBy }</td>
								<td>${unitBean.createDate }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${pagination.dataListBean.size() > 0}"></c:if>
		<jsp:include page="../layout/paging.jsp"></jsp:include> 
	</div>
	</div>
	<jsp:include page="../layout/script.jsp"></jsp:include>
</body>
</html>
