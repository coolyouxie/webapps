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
    <meta name="viewport" content="width=device-width,initial-scale=1" />
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
    <link rel="stylesheet" href="${ctx}/css/jquery/jquery.mobile-1.4.5.min.css">


    <!--[if (gte IE 9)|!(IE)]><!-->
    <script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
    <script src="${ctx}/js/jquery/jquery.mobile-1.4.5.min.js"></script>
    <!--<![endif]-->
    <!--[if lte IE 8 ]>
    <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
    <script src="assets/js/amazeui.ie8polyfill.min.js"></script>
    <![endif]-->
    <script src="${ctx}/js/common/amazeUI/assets/js/amazeui.min.js"></script>
    <script src="${ctx}/js/common/amazeUI/public.js"></script>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/css/bootstrap.min.css" type="text/css" />
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="${ctx}/js/common/bootstrap/bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>


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
            window.location.href = '${downloadUrl}/android${androidVersion}.apk';
	        alert("请使用移动设备打开此链接");  // 公司主页
	        //window.location.href = 'http://106.14.221.46:18080/webapps/download/android1.0.0.apk';
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


    var timeOutEvent = 0;
    $(function () {
        $("#touchDiv").on({
            touchstart:function (e) {
                timeOutEvent = setTimeout('open()',500);
//                e.preventDefault();
            },
            touchmove:function () {
                clearTimeout(timeOutEvent);
                timeOutEvent = 0;
            },
            touchend:function () {
                clearTimeout(timeOutEvent);
                return false;
            }
        })
    });
    function open() {
        $("#myPopup").popup('open');
    }
    function cancel() {
        $("#myPopup").popup('close');
    }
</script>
<style>

</style>
<body>
<div data-role="popup" id="myPopup" class="ui-content" data-transition="slideup">
    <a href="" class="ui-btn" onclick="goDownload()">下载应用</a>
    <a href="" class="ui-btn" onclick="cancel()">取消</a>
</div>

<div class="container">
    <div class="row">
        <div id="touchDiv" class="col-sm-12" style="padding-left: 0px;padding-right: 0px;">
            <img src="${ctx}/img/shareImg.jpg" style="width:100%"/>
        </div>
    </div>
</div>

</body>
</html>