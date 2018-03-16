<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${fn:length(invoiceBean.invoiceHistoryPrintBeanList) >  0}">
	<div class="row">
		<div class="col-md-12">
			<fieldset>
				<legend>&nbsp;&nbsp;&nbsp;ประวัติการพิมพ์&nbsp;&nbsp;&nbsp;</legend>
				<div>
					<div class="row mt15">
						<div class="col-md-12 mb05">
							<div class="table-responsive">
								<table class="table table-bordered table-hover" id="tbInvoiceHistory">
									<thead class="thead-default">
										<tr>
											<th style="vertical-align: middle;"><p align="center">ลำดับ</p></th>
											<th style="vertical-align: middle;"><p align="center">วันที่พิมพ์</p></th>
											<th style="vertical-align: middle;"><p align="center">ผู้พิมพ์</p></th>
											<th style="vertical-align: middle;"><p align="center">ครั้งที่พิมพ์</p></th>
										</tr>
									</thead>
									<c:forEach var="history"
										items="${invoiceBean.invoiceHistoryPrintBeanList}"
										varStatus="i">
										<tr>
											<td><center>${i.count }</center></td>
											<td><center>${history.createDateTh }</center></td>
											<td><center>
													<c:if test="${not empty history.personnelBean }">
																					${history.personnelBean.firstName }&nbsp;
																					${history.personnelBean.lastName } (${history.personnelBean.nickName })
																				</c:if>
												</center></td>
											<td><center>${history.printTime }</center></td>
										</tr>
									</c:forEach>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</fieldset>
		</div>
	</div>
</c:if>