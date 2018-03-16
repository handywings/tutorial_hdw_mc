<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
	<div class="col-md-5">
		<div class="dataTables_info" id="table-1_info" role="status"
			aria-live="polite">Showing ${(pagination.currentPage - 1) * pagination.itemPerPage + 1} to 
			<c:if test="${(pagination.itemPerPage * pagination.currentPage) > pagination.totalItem}">${pagination.totalItem }</c:if>
			<c:if test="${(pagination.itemPerPage * pagination.currentPage) < pagination.totalItem}">${pagination.itemPerPage * pagination.currentPage}</c:if> of
			${pagination.totalItem } entries</div>
	</div>
	<div class="col-md-7">
		<c:if test="${pagination.dataListBean.size() > 0}">
		<div class="dataTables_paginate paging_simple_numbers" style="float: right;"
			id="table-1_paginate">
			<ul class="pagination">
				<!-- previos page -->
				<c:choose>
					<c:when test="${pagination.currentPage == 1}">
						<li class="paginate_button page-item previous disabled" id="table-1_previous">
							<a href="" aria-controls="table-1"
								data-dt-idx="0" tabindex="0" class="page-link">Previous
							</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="paginate_button page-item previous" id="table-1_previous">
							<a href="${pageContext.request.contextPath}/${pagination.url }/page/${pagination.currentPage - 1}/itemPerPage/${pagination.itemPerPage}/tab/All" aria-controls="table-1"
								data-dt-idx="0" tabindex="0" class="page-link">Previous
							</a>
						</li>
					</c:otherwise>
				</c:choose>
				
				<c:forEach var="i" begin="1" end="${pagination.pageSize }">
					<!-- กรณีที่มีขนาดน้อยกว่าหรือเท่ากับ 7 -->
					<c:if test="${(pagination.pageSize) <= 7}">
						<c:if test="${i == pagination.currentPage}">
							<li class="paginate_button page-item active"><a href=""
							aria-controls="table-1" data-dt-idx="1" tabindex="0"
							class="page-link">${i}</a></li>
						</c:if>
						<c:if test="${i != pagination.currentPage}">
							<li class="paginate_button page-item">
								<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
									aria-controls="table-1" data-dt-idx="1" tabindex="0"
									class="page-link">${i}
								</a>
							</li>
						</c:if>
					</c:if>	
					
					<!-- กรณีที่มีขนาดมากกว่า 7 -->
					<c:if test="${(pagination.pageSize) > 7}">
						<c:if test="${pagination.currentPage > 4 }">
							<!-- สร้าง pagina แรกและ... ไว้ -->
							<c:if test="${i == 1 }">
								<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
									<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
										aria-controls="table-1" data-dt-idx="1" tabindex="0"
										class="page-link">${i}
									</a>
								</li>
								<li class="paginate_button page-item disabled">
									<a href="" class="page-link">...</a>
								</li>
							</c:if>
							<c:if test="${(pagination.pageSize-4) >= pagination.currentPage }">
								<c:if test="${i == pagination.currentPage || i == pagination.currentPage-1 || i == pagination.currentPage+1 }">
									<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
										<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
											aria-controls="table-1" data-dt-idx="1" tabindex="0"
											class="page-link">${i}
										</a>
									</li>
								</c:if>
								<c:if test="${i == pagination.pageSize }">
								<li class="paginate_button page-item disabled">
									<a href="" class="page-link">...</a>
								</li>
								<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
									<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
										aria-controls="table-1" data-dt-idx="1" tabindex="0"
										class="page-link">${i}
									</a>
								</li>
								</c:if>
							</c:if>
							<c:if test="${(pagination.pageSize-5) < pagination.currentPage }">
								<c:if test="${(pagination.pageSize-5) < i && pagination.currentPage > (pagination.pageSize-4)}">
									<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
										<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
											aria-controls="table-1" data-dt-idx="1" tabindex="0"
											class="page-link">${i}
										</a>
									</li>
								</c:if>
							</c:if>
						</c:if>
						<c:if test="${pagination.currentPage <= 4 }">
							<c:if test="${i <= 5 }">
							<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
								<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
									aria-controls="table-1" data-dt-idx="1" tabindex="0"
									class="page-link">${i}
								</a>
							</li>
							</c:if>
							<c:if test="${i == 6 }">
							<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
								<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
									aria-controls="table-1" data-dt-idx="1" tabindex="0"
									class="page-link">...
								</a>
							</li>
							</c:if>
							<c:if test="${(pagination.pageSize) == i}">
							<li class="paginate_button page-item <c:if test="${i == pagination.currentPage}">active</c:if>">
								<a href="${pageContext.request.contextPath}/${pagination.url }/page/${i}/itemPerPage/${pagination.itemPerPage}/tab/All"
									aria-controls="table-1" data-dt-idx="1" tabindex="0"
									class="page-link">${i}
								</a>
							</li>
							</c:if>
						</c:if>

					</c:if>
					
				</c:forEach>
				
				<c:choose>
					<c:when test="${pagination.currentPage == pagination.pageSize}">
						<li class="paginate_button page-item next disabled" id="table-1_next">
							<a href="" aria-controls="table-1" data-dt-idx="8" tabindex="0"
								class="page-link">Next
							</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="paginate_button page-item next" id="table-1_next">
							<a href="${pageContext.request.contextPath}/${pagination.url }/page/${pagination.currentPage + 1}/itemPerPage/${pagination.itemPerPage}/tab/All" 
								aria-controls="table-1" data-dt-idx="8" tabindex="0"
								class="page-link">Next
							</a>
						</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		</c:if>
	</div>
</div>