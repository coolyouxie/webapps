<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>

    <!--360 browser -->
    <meta name="renderer" content="webkit">
    <meta name="author" content="wos">
    <!-- Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="${ctx}/css/amazeUI/images/images/i/app.png">
    <!--Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="apple-touch-icon-precomposed" href="${ctx}/css/amazeUI/images/images/i/app.png">
    <!--Win8 or 10 -->
    <meta name="msapplication-TileImage" content="${ctx}/css/amazeUI/images/images/i/app.png">
    <meta name="msapplication-TileColor" content="#e1652f">

    <link rel="icon" type="image/png" href="${ctx}/css/amazeUI/images/i/favicon.png">
    <link rel="stylesheet" href="${ctx}/css/amazeUI/css/amazeui.min.css">
    <link rel="stylesheet" href="${ctx}/css/amazeUI/css/public.css">

    <!--[if (gte IE 9)|!(IE)]><!-->
    <script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
    <!--<![endif]-->
    <!--[if lte IE 8 ]>
    <script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
    <script src="assets/js/amazeui.ie8polyfill.min.js"></script>
    <![endif]-->
    <script src="${ctx}/js/common/amazeUI/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/js/common/amazeUI/public.js"></script>
</head>
<body>

<footer style="background-color:#ffffff">
    <div class="content" style="color:#000">
        <div class="btnlogo"><img src="${ctx}/img/timg-3.jpeg" style="max-width:100%;overflow:hidden;"/></div>
        <h1>好东西，要分享</h1>
        <div class="w1div">
            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-1 am-avg-md-1 am-avg-lg-1 am-gallery-overlay" >
                <li class="w2">
                    <div class="am-gallery-item">
                        <img src="${ctx}/img/1507555053.png" />
                    </div>
                </li>
            </ul>
        </div>
        <p>嘉聘网<br>© 2017 嘉聘信息科技股份有限公司.</p>
    </div>
</footer>
</body>
</html>