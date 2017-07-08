<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>404 - 页面不存在</title>
    <%@ include file="../head.jsp"%>
</head>
<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
        <h1>404</h1>
        <h3 class="font-bold">Page Not Found</h3>
        <div class="error-desc">
            Sorry, but the page you are looking for has note been found. Try checking the URL for error, then hit the refresh button on your browser or try found something else in our app.
            <form class="form-inline m-t" role="form">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search for page">
                </div>
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
    </div>
    <!-- Mainly scripts -->
    <%@ include file="../scripts.jsp"%>
</body>
</html>