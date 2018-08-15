<%@page import="com.cdvcloud.rochecloud.common.Constants"%>
<%@page import="com.cdvcloud.rochecloud.util.UserUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String baseCtx = request.getScheme() + "://" + request.getServerName();
	String serPort = "" + request.getServerPort();
	String loginId = UserUtil.getUserByRequest(request, "loginId");
	String userName = UserUtil.getUserByRequest(request, "userName");
	String userId = UserUtil.getUserByRequest(request, "userId");
	String role_code = UserUtil.getUserByRequest(request, "role_code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>BTV律师号运营服务平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
	<meta http-equiv="X-UA-Compatible" content="IE=10">
		<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/demo.css" type="text/css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/zTreeStyle.css" type="text/css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-responsive.min.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/matrix-style.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/matrix-media.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sco.message.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui-1.10.4.dialog.min.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font-awesome/css/font-awesome.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/js/catalog/css/lightbox.css" type="text/css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/js/catalog/css/screen.css" type="text/css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-switch.css" type="text/css"></link>
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/tipso.min.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/viewJs/order/orderList.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/viewJs/common/common.css" />
			<link rel="stylesheet" href="<%=request.getContextPath()%>/css/viewJs/comment/comment.css" />
			<script>
		        var ctx = "<%=request.getContextPath()%>";
		        var baseCtx = "<%=baseCtx%>";
		        var serPort = "<%=serPort%>";
		        var serBasePath = "<%=basePath%>";
		    </script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/matrix.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sco.modal.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sco.confirm.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/sco.message.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.10.4.dialog.min.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/Validform_v5.3.2_min.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/tipso.min.js"></script>
			<script type="text/javascript" src="<%=request.getContextPath()%>/js/vms-main.js"></script>
		</meta>
	</meta>
</meta>
<style type="text/css">
.left_nav.active {background:#53cef2; color:#FFF;}
</style>
</head>
<body style="min-width:1230px" onkeydown="banBackspace(event)">
	<!--Header-part-->
	<div id="header" >
		<h1>
			<a href="<%=basePath%>" style="width: 280px; height: 66px;">
				<img src="<%=request.getContextPath()%>/images/logo/abc.png" onerror="this.src='<%=request.getContextPath()%>/images/logo/vms-logo.png'" style="margin-left: 10px; margin-top: 10px;" onclick="javascript:" />
			</a>
		</h1>
	</div>
	<!--close-Header-part-->

	<!--top-Header-menu-->
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class=""><a title="当前用户" href="javascript:" style="color: #ffffff;"><i> <img src="<%=request.getContextPath()%>/images/user.png" /></i>&nbsp;<span class="text" id="userName"><%=userName %></span></a>
			</li>
			<li class=""><a id="msgTipLoginOut" title="退出" href="javascript:" onclick="doLogout()"  style="color: #ffffff;"><i
					style="line-height: 15px; font-size: 25px;"><img src="<%=request.getContextPath()%>/images/tuichu.png" /></i>
			</a>
			</li>
		</ul>
	</div>
	<!--close-top-Header-menu-->

	<!--sidebar-menu-->
	<div id="sidebar">
	 <ul>
     
    <li class="submenu active open">
      <ul style="display:block" class="left_nav">
		  <%
		  	if("2".equals(role_code)){

		  %>
		  <li class="first_nav"><a id="firstOnLoad" href="javascript:void(0)" shref="<%=request.getContextPath()%>/department/findall/" onclick="openurl(this)">律所管理</a></li>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/findall/" onclick="openurl(this)">律师管理</a></li>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/serviceManageList/" onclick="openurl(this)">服务管理</a></li>
		  <%--<li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/comment/queryCommentPage/" onclick="openurl(this)">在线客服</a></li>--%>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/api/xy/wechatMessage/v1/queryWechatMessage4page/" onclick="openurl(this)">在线客服</a></li>
		  <%
		  	}
		  %>

		  <%
			  if("1".equals(role_code)){

		  %>
		  <li class="first_nav"><a id="firstOnLoad" href="javascript:void(0)" shref="<%=request.getContextPath()%>/department/findall/" onclick="openurl(this)">律所管理</a></li>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/findall/" onclick="openurl(this)">律师管理</a></li>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/serviceManageList/" onclick="openurl(this)">服务管理</a></li>
		  <%
			  }
		  %>

		  <%
			  if("0".equals(role_code)){

		  %>
		  <li class="first_nav"><a id="firstOnLoad" href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/findall/" onclick="openurl(this)">律师管理</a></li>
		  <li><a href="javascript:void(0)" shref="<%=request.getContextPath()%>/lawyer/serviceManageList/" onclick="openurl(this)">服务管理</a></li>
		  <%
			  }
		  %>
      </ul>
    </li> 
    </ul>
	
	</div>
	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content" style="overflow:hidden;"></div>
	<!--end-main-container-part-->
	<!--Footer-part-->
	<div style="display: none" class="row-fluid">
		<div id="footer" class="span34" style="color: #666666;">版权所有&copy;北京新奥特云视科技有限公司</div>
	</div>
	<!--end-Footer-part-->
	<div class="ajax_background" id="ajax_mask"></div>
	<div class="ajax_background_inner" id="ajax_mask_inner"></div>
</body>
<script>
	$(function() {
		btnTip();
		$(".first_nav").addClass("active");
		$(".left_nav li").click(function(){
			$(".left_nav li").removeClass("active");
			$(this).addClass("active");
		})
	});
	$("#firstOnLoad").click();
	function banBackspace(e) {
		var ev = e || window.event;//获取event对象
		if(ev.keyCode == 13){
			$("#btnSearch").click();
			return;
		}
		var obj = ev.target || ev.srcElement;//获取事件源
		var t = obj.type || obj.getAttribute('type');//获取事件源类型
		//获取作为判断条件的事件类型
		var vReadOnly = obj.getAttribute('readonly');
		var vEnabled = obj.getAttribute('enabled');
		//处理null值情况
		vReadOnly = (vReadOnly == null) ? false : vReadOnly;
		vEnabled = (vEnabled == null) ? true : vEnabled;
		//当敲Backspace键时，事件源类型为密码或单行、多行文本的，
		//并且readonly属性为true或enabled属性为false的，则退格键失效
		var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")
		&& (vReadOnly==true || vEnabled!=true))?true:false;
		//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
		var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")
		?true:false;
		//判断
		if(flag2){
			$('#content').css('display','block');
			$('#contenttwo').remove();
			return false;
		}
		if(flag1){
			return false;
		}
	}
	//禁止后退键 作用于Firefox、Opera
	document.onkeypress=banBackspace;
	//禁止后退键 作用于IE、Chrome
	document.onkeydown=banBackspace;
	
	var tempURL = window.location.href;
	if(tempURL.indexOf("jsessionid") > 0 || tempURL.indexOf("ticket") > 0){
		window.location.href = "<%=basePath%>";
	}
	
	setInterval(function(){
		$.ajax({
			type : 'POST',
			url : ctx+"/api/checkLogin/",
			cache : false,
			success:function(data){
				if("error" == data){
					window.location.href = window.location.href
				}
			},
			error : function(a, b, c) {
				window.location.href = window.location.href
			}
		});
	},9000);

    function doLogout() {
        var logouturi = ctx + "/lizhiyun/doLogout/";
        $.ajax({
            type : 'POST',
            url : logouturi,
            success : function(data) {
                if ("success" == data) {
                    window.location.href=ctx;
                } else {
                    document.getElementById("errorMsg").style.display = "block";
                }
            }
        });
    }

</script>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.ztree.exedit-3.5.js"></script>