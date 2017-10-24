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
<script>
	function goDownload() {
	    var u = navigator.userAgent, app = navigator.appVersion;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1;
	    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
	    // 是安卓浏览器
	    if (isAndroid) {
	        window.location.href = '${downloadUrl}/android${androidVersion}.apk'; // 跳安卓端下载地址
	    }
	    // 是iOS浏览器
	    if (isIOS) {
	        window.location.href = '${iosUrl}'; // 跳AppStore下载地址
	    }
	
	    // 是微信内部webView
	    if (is_weixn()) {
	        alert("请点击右上角按钮, 点击使用浏览器打开");
	    }
	
	    // 是PC端
	    if (IsPC()) {
	        alert("请使用移动设备打开此链接");  // 公司主页
	        window.location.href = 'http://106.14.221.46:18080/webapps/download/android1.0.1.apk';
	    }
	}
	
	// 是微信浏览器
	function is_weixn(){
	    var ua = navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	
	function IsPC() {
	    var userAgentInfo = navigator.userAgent;
	    var Agents = ["Android", "iPhone",
	        "SymbianOS", "Windows Phone",
	        "iPad", "iPod"];
	    var flag = true;
	    for (var v = 0; v < Agents.length; v++) {
	        if (userAgentInfo.indexOf(Agents[v]) > 0) {
	            flag = false;
	            break;
	        }
	    }
	    return flag;
	}
</script>
<body>

<footer style="background-color:#ffffff">
    <div class="content" style="color:#000">
        <div class="btnlogo"><img src="${ctx}/img/timg-3.jpeg" style="max-width:100%;overflow:hidden;"/></div>
        <h3>嘉聘网，找工作</h3><h3>挣工资，赚补贴！</h3>
        <p>&nbsp;</p>
        <div class="w1div">
            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-1 am-avg-md-1 am-avg-lg-1 am-gallery-overlay" >
                <li class="w2">
                    <div class="am-gallery-item">
                        <img src="${ctx}/img/download.png" />
                    </div>
                </li>
            </ul>
        </div>
        <a class="am-btn am-btn-secondary am-round" onclick="goDownload()">点击下载</a>
        <p>&nbsp;</p>
        <span>嘉聘网络用科技助您找到好工作</span>
        <p>嘉聘网<br>© 2017 嘉聘信息科技股份有限公司.</p>
    </div>
</footer>
</body>
</html>