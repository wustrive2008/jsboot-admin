<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <%@ include file="../common/head.jsp"%>
<title>紫菜管理平台 | 锁屏</title>
</head>
<body class="gray-bg">

<div class="lock-word animated fadeInDown">
    <span class="first-word">LOCKED</span><span>SCREEN</span>
</div>
    <div class="middle-box text-center lockscreen animated fadeInDown">
        <div>
            <div class="m-b-md">
            <img alt="image" class="img-circle circle-border" src="https://s3.amazonaws.com/uifaces/faces/twitter/ok/128.jpg">
            </div>
            <h3>John Smith</h3>
            <p>进入锁屏，主要的应用程序被关闭，你需要输入你的登录密码进行解锁。</p>
            <form class="m-t" role="form" action="${pageContext.request.contextPath}/manage/login" method="post">
                <div class="form-group">
                	 <input name="username" type="hidden" value="${username}"/>
                    <input  name="password" type="password" class="form-control" placeholder="******" required="">
                </div>
                <button type="submit" class="btn btn-primary block full-width">Unlock</button>
            </form>
        </div>
    </div>

    <!-- Mainly scripts -->
    <%@ include file="../common/scripts.jsp"%>
</body>
</html>