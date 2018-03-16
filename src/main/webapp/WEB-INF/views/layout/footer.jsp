<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatDate var="year" value="${now}" pattern="yyyy" />
<footer class="footer">
					<div class="container-fluid">
						<div class="row text-xs-center">
							<div class="col-sm-12 text-sm-left mb05">								
							<center>© MC Digital TV ${year} VERSION 1.5</center>
							</div>
							<div class="col-sm-8 text-sm-right">
<!-- 								<ul class="nav nav-inline l-h-2"> -->
<!-- 									<li class="nav-item"><a class="nav-link text-black" href="#">Privacy</a></li> -->
<!-- 									<li class="nav-item"><a class="nav-link text-black" href="#">Terms</a></li> -->
<!-- 									<li class="nav-item"><a class="nav-link text-black" href="#">Help</a></li> -->
<!-- 								</ul> -->
							</div>
						</div>
					</div>
				</footer>