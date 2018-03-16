<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!-- check session login -->
    <%
	    if ((session.getAttribute("authen") != null)) {
	    	response.sendRedirect("main");
	    }else{
	    	response.sendRedirect("login");
	    }
	%>
	