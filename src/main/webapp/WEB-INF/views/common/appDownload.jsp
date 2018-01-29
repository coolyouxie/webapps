<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html class="no-js">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title>微信弹出遮罩</title>
	<script src="${ctx}/js/jquery/jQuery-1.12.4.0.js"></script>
</head>
<body>
<style type="text/css">
	#weixin-tip {
		display: none;
		position: fixed;
		left: 0;
		top: 0;
		background: rgba(0, 0, 0, 0.8);
		filter: alpha(opacity=80);
		width: 100%;
		height: 100%;
		z-index: 100;
	}

	#weixin-tip p {
		text-align: center;
		margin-top: 10%;
		padding: 0 5%;
		position: relative;
	}

	#weixin-tip .close {
		color: #fff;
		padding: 5px;
		font: bold 20px/24px simsun;
		text-shadow: 0 1px 0 #ddd;
		position: absolute;
		top: 0;
		left: 5%;
	}
</style>
<div class="test">
	<a id="J_weixin" class="android-btn" href="${downloadUrl}/android${androidVersion}.apk">
		<img src="${ctx}/img/android-btn.png" alt="安卓版下载"/>
	</a>
	<a id="I_weixin" class="android-btn" href="${iosUrl}">
		<img src="${ctx}/img/ios-btn.png" alt="苹果版下载" />
	</a>
	<div id="weixin-tip">
		<p>
			<img src="${ctx}/img/live_weixin.png" alt="微信打开" style="width:100%;"/>
			<span id="close" title="关闭"class="close">×</span>
		</p>
	</div>
</div>
<script type="text/javascript">
    var is_weixin = (function () {
        return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1
    })();
    window.onload = function () {
        var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight; //兼容IOS，不需要的可以去掉
        var btn = document.getElementById('J_weixin');
        var tip = document.getElementById('weixin-tip');
        var close = document.getElementById('close');
        if (is_weixin) {
            btn.onclick = function (e) {
                tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
                tip.style.display = 'block';
                return false;
            }
            close.onclick = function () {
                tip.style.display = 'none';
            }
        }
    }
</script>
</body>
</html>
