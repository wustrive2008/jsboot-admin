<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - 系统内部错误</title>
    <%@ include file="../head.jsp"%>
</head>
<body>
	<div class="middle-box text-center animated fadeInDown">
        <h1>500</h1>
        <h3 class="font-bold">Internal Server Error</h3>

        <div class="error-desc">
        	<% Exception ex = (Exception) request.getAttribute("ex");%>
			<%-- <H2>Exception:<%=ex.getMessage()%> --%>
           <br/><a href="index.html" class="btn btn-primary m-t">Dashboard</a>
        </div>
    </div>
     <!-- Mainly scripts -->
   	 <%@ include file="../scripts.jsp"%>
</body>
</html>