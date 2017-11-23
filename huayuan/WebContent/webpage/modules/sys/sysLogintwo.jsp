<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>

<script type="text/javascript">
	if ('ontouchstart' in document.documentElement)
		document.write("<script src='../assets/js/jquery.mobile.custom.js'>"
				+ "<"+"/script>");
</script>
<style>
/* Validation */
label.error {
	color: #cc5965;
	display: inline-block;
	margin-left: 5px;
}

.form-control.error {
	border: 1px dotted #cc5965;
}
</style>
<!-- inline scripts related to this page -->
<script type="text/javascript">
	$(document).ready(function() {
		$(document).on('click', '.form-options a[data-target]', function(e) {
			e.preventDefault();
			var target = $(this).data('target');
			$('.widget-box.visible').removeClass('visible');//hide others
			$(target).addClass('visible');//show target
		});
	});
</script>
<head lang="en">
<meta charset="UTF-8">
		<link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic}/css/common.css" />
<link rel="stylesheet" href="${ctxStatic}/css/style.css" />
<link rel="stylesheet" href="${ctxStatic }/common/login/ace-rtl.css" />
<script src="${ctxStatic}/js/jquery-1.12.3.js"></script>
<script src="${ctxStatic}/js/myjs.js"></script>
<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入layer插件 -->
<script src="${ctxStatic}/layer-v2.3/layer/layer.js"></script>
<script src="${ctxStatic}/layer-v2.3/layer/laydate/laydate.js"></script>



<!--[if lte IE 9]>
			<link rel="stylesheet" href="../assets/css/ace-part2.css" />
		<![endif]-->
<link rel="stylesheet" href="${ctxStatic }/common/login/ace-rtl.css" />

<style type="text/css">
.bound {
	
}
</style>
<title>太原画院&nbsp;&nbsp;&nbsp;&nbsp; 登录</title>
<script>
	if (window.top !== window.self) {
		window.top.location = window.location;
	}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#loginForm")
								.validate(
										{
											rules : {
												validateCode : {
													remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
												}
											},
											messages : {
												username : {
													required : "请填写用户名."
												},
												password : {
													required : "请填写密码."
												},
												validateCode : {
													remote : "验证码不正确.",
													required : "请填写验证码."
												}
											},
											errorLabelContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												error.appendTo($("#loginError")
														.parent());
											}
										});
					});
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
			|| $('#left').length > 0 || $('.jbox').length > 0) {
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
</script>
<script type="text/javascript">
// 验证输入不为空的脚本代码
function checkForm(form) {
if(form.username.value == "") {
alert("用户名不能为空!");
form.username.focus();
return false;
}
if(form.password.value == "") {
alert("密码不能为空!");
form.password.focus();
return false;
}

return true;
}
</script>
</head>
<body class="login-layout light-login">
<header class="header w1024 auto pt25 pb15">
	<a href="#" class="ml40 db"><img
		src="${ctxStatic }/images/logo.png" alt="" class="db"></a>
</header>
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<section class="main w1024 auto">
							<section class="div_bottom" style="margin-top:-20px;">
								<div class="div_top">
									<ul class="ul1">
						<div class="center">
							<sys:message content="${message}" />
						</div>

										<form id="loginForm" class="form-signin" action="${ctx}/login01"
											method="post" onsubmit="return checkForm(this);>

											<li>
												<div class="div_xian fix">
													<img src="${ctxStatic }/images/tip_01.png" alt=""
														class="fl"> <input type="text" id="username"
														name="username" class="put_public put_01 fl" value=""
														placeholder="请输入用户名/手机号" />
												</div>

											</li>
											<li>
												<div class="div_xian fix">
													<img src="${ctxStatic }/images/tip_02.png" alt=""
														class="fl"> <input type="password" id="password"
														name="password" value="" class="put_public put_01 fl"
														placeholder="请输入6-16位密码" />
												</div>

												<div class="tr">
													<a href="#" class="a1">忘记密码</a>
												</div>
											</li>

											<li>
												<%-- <div class="div_xian ling fl fix">
													<img src="${ctxStatic }/images/tip_03.png" alt=""
														class="fl"> <input type="text"
														class="put_public put_01 put_02 fl">
												</div> <img src="${ctxStatic }/images/yzm.jpg" alt="验证码获取失败"
												class="fl yzm rel"> <span class="span1 fl">换一张</span> --%>
												<%-- <div class="input-group m-b text-muted validateCode">
											<input type="text"
														class="put_public put_01 put_02 fl">
														<label class="input-label mid" for="validateCode">验证码:</label>
														<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;"/>
														</div> --%>
											</li>

											<li>
												<button type="submit" class="put_public but_01">
													<span class="bigger-110">一&nbsp;&nbsp;&nbsp;&nbsp;级&nbsp;&nbsp;&nbsp;&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录</span>
												</button>
											</li>
										</form>
									</ul>
								</div>
							</section>
						</section>
						<footer class="footer tc pt20 pb15 w1024 auto">
							太原画院藏品管理系统<br> 地址:山西省太原市老军营小区太原画院 电话: 0351-7241176
						</footer>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
</body>
</html>