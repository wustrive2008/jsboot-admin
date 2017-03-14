<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="../common/head.jsp"%>
<title>登录</title>
</head>
<body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">Z+</h1>
            </div>
            <form class="m-t" role="form" action="${pageContext.request.contextPath}/manage/login" method="post">
                <div class="form-group">
                     <input name="username" class="form-control" type="text" placeholder="用户名"  required="" />
                     <input type="hidden" name="rememberMe" value="false" />
                </div>
                <div class="form-group">
                    <input name="password" type="password" class="form-control" placeholder="密码" required="">
                </div>
                <button type="submit" class="btn btn-primary block full-width m-b">登录</button>
                <a href="#" style="display: none;"><small>Forgot password?</small></a>
                <p class="text-muted text-center">
                <small>
                	<c:choose>
		      		 	<c:when test="${shiroLoginFailure eq 'org.apache.shiro.authc.IncorrectCredentialsException'}">
		      		 		密码错误
		      		 	</c:when>
		      		 	<c:when test="${shiroLoginFailure eq 'org.apache.shiro.authc.UnknownAccountException'}">
		      		 		帐号不存在
		      		 	</c:when>
		      		 	<c:when test="${shiroLoginFailure eq 'org.apache.shiro.authc.ConcurrentAccessException'}">
		      		 		当前登录账号角色已被禁用
		      		 	</c:when>
		      		 	<c:when test="${shiroLoginFailure eq 'org.apache.shiro.authc.DisabledAccountException'}">
		      		 		当前登录账号已被禁用
		      		 	</c:when>
		      		  	<c:otherwise>
		    			</c:otherwise>
		      		</c:choose>
                </small>
               </p>
            </form>
            <p class="m-t"> <small>© 2016 <a href="http://www.zicai8.com/" target="_blank">zicai8.com</a> 郑州小刀计算机科技有限公司</small> </p>
        </div>
    </div>

    <!-- Mainly scripts -->
    <%@ include file="../common/scripts.jsp"%>
</body>
</html>