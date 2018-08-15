<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/icon.ico" type="image/x-icon"/>
<title>BTV律师号运营服务平台</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login/login.css" type="text/css"></link>
<script src="https://cdn.yunshicloud.com/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login/jQuery.easing.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login/script.js"></script>
<script>
	function doLogin(){
		var txtName = document.getElementById("txtName").value;
		var txtPwd = document.getElementById("password").value;
		var keyPwd = "";
		if (undefined == txtName || "" == txtName) {
			$("#txtName").focus();
			return;
		}else if(undefined == txtPwd || "" == txtPwd){
			$("#txtPwd").focus();
			return;
		}
		var uri = "<%=request.getContextPath()%>/lizhiyun/loginV2/?loginId="+txtName+"&password="+txtPwd+"&keyPwd="+keyPwd;
		$.ajax({
			type : 'POST',
			url : uri,// 请求的action路径
			success : function(data) {
				if("success" == data){
					$("#login").slideUp('slow',function(){window.location = "<%=request.getContextPath()%>/";});
					$("#loginBG").slideUp('slow');
				} else {
					var msg = "";
					if ("error" == data) {
						msg = "用户名或密码错误！";
					} else if ("unuse" == data) {
						msg = "该用户已被停止使用！";
					} else if ("nopermission" == data) {
						msg = "该用户无权限！";
					} else if ("keyerror" == data) {
						msg = "云key验证错误！";
					}
					document.getElementById("loginBG").style.height="300px";
					document.getElementById("login").style.height="300px";
					document.getElementById("errorMsg").innerHTML = msg;
					document.getElementById("errorMsg").style.display = "block";
				}
			}
		});
	}
	function hideMsg(id) {
		document.getElementById(id).select();
		document.getElementById("loginBG").style.height="270px";
		document.getElementById("login").style.height="270px";
		document.getElementById("errorMsg").style.display = "none";
	}
	function toLogin(va) {
		if (va == 13) {
			doLogin();
		}
	}
</script>
</head>
<body class="loginCloud" onkeydown="toLogin(event.keyCode)">
	<div class="body">
		<div class="main">
			<div class="slide-content" id="slide_content">
				<div class="logo_div">&nbsp;</div>
				<div id="loginLayout" class="loginLayout">
					<div id="loginBG" style="display: none;" class="loginBG">&nbsp;</div>
					<div id="login" style="display: none;" class="login">
						<h1>BTV律师号运营服务平台</h1>
						<input type="text" name="txtName" id="txtName" value="" class="login-input" placeholder="用户名" autofocus onfocus="hideMsg('txtName');"> 
						<input type="password" name="password" value="" id="password" class="login-input" placeholder="密码" onfocus="hideMsg('password');"> 
						<input type="button" value="登  录" class="login-submit" onclick="doLogin();">
						<p id="errorMsg" class="login-help" style="display: none; color: yellow;">用户名或密码错误！</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
$("#login").slideDown('slow');
$("#loginBG").slideDown('slow');
</script>