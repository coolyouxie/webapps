<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>抽奖</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<style>
.g-content {
	width: 100%;
	background: #fbe3cc;
	height: auto;
	font-family: "微软雅黑", "microsoft yahei";
}

.g-content .g-lottery-case {
	width: 500px;
	margin: 0 auto;
	overflow: hidden;
}

.g-content .g-lottery-case .g-left h2 {
	font-size: 20px;
	line-height: 32px;
	font-weight: normal;
	margin-left: 20px;
}

.g-content .g-lottery-case .g-left {
	width: 450px;
	float: left;
}

.g-lottery-box {
	width: 400px;
	height: 400px;
	margin-left: 30px;
	position: relative;
	background: url(ly-plate-c.gif) no-repeat;
}

.g-lottery-box .g-lottery-img {
	width: 340px;
	height: 340px;
	position: relative;
	background: url(bg-lottery.png) no-repeat;
	left: 30px;
	top: 30px;
}

.g-lottery-box .playbtn {
	width: 186px;
	height: 186px;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-left: -94px;
	margin-top: -94px;
	background: url(playbtn.png) no-repeat;
}
</style>
</head>
<body>
	<div class="g-content">
		<div class="g-lottery-case">
			<div class="g-left">
				<h2>
					您已拥有<span class="playnum"></span>次抽奖机会，点击立刻抽奖！~
				</h2>
				<div class="g-lottery-box">
					<div class="g-lottery-img"></div>
					<a class="playbtn" href="javascript:;" title="开始抽奖"></a>
				</div>
			</div>
		</div>
	</div>
	<script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="jsmin/jquery.rotate.min.js"></script>
	<script>
		$(function() {
			var $btn = $('.g-lottery-img');// 旋转的div
			var playnum = 5; //初始次数，由后台传入
			$('.playnum').html(playnum);//显示还剩下多少次抽奖机会
			var isture = 0;//是否正在抽奖
			var clickfunc = function() {
				var data = [ 1, 2, 3, 4, 5, 6 ];//抽奖
				//data为随机出来的结果，根据概率后的结果
				data = data[Math.floor(Math.random() * data.length)];//1~6的随机数
				switch (data) {
				case 1:
					rotateFunc(1, 0, '恭喜您获得2000元理财金');
					break;
				case 2:
					rotateFunc(2, 0, '恭喜您获得2000元理财金2');
					break;
				case 3:
					rotateFunc(3, 0, '恭喜您获得2000元理财金3');
					break;
				case 4:
					rotateFunc(4, -60, '谢谢参与4');
					break;
				case 5:
					rotateFunc(5, 120, '谢谢参与5');
					break;
				case 6:
					rotateFunc(6, 120, '谢谢参与6');
					break;
				}
			}
			$(".playbtn").click(function() {
				if (isture)
					return; // 如果在执行就退出
				isture = true; // 标志为 在执行
				if (playnum <= 0) { //当抽奖次数为0的时候执行
					alert("没有次数了");
					$('.playnum').html(0);//次数显示为0
					isture = false;
				} else { //还有次数就执行
					playnum = playnum - 1; //执行转盘了则次数减1
					if (playnum <= 0) {
						playnum = 0;
					}
					$('.playnum').html(playnum);
					clickfunc();
				}
			});
			var rotateFunc = function(awards, angle, text) {
				isture = true;
				$btn.stopRotate();
				$btn.rotate({
					angle : 0,//旋转的角度数
					duration : 4000, //旋转时间
					animateTo : angle + 1440, //给定的角度,让它根据得出来的结果加上1440度旋转
					callback : function() {
						isture = false; // 标志为 执行完毕
						alert(text);
					}
				});
			};
		});
	</script>
</body>