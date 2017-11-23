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
<title></title>
<link rel="stylesheet" href="${ctxStatic}/css/common.css" />
<link rel="stylesheet" href="${ctxStatic}/css/style.css" />
<!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
<script src="${ctxStatic}/js/jquery-1.12.3.js"></script>
<script src="${ctxStatic}/js/myjs.js"></script>
<script src="${ctxStatic}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
		<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
		
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
	$(document)
			.ready(
					function() {
						$("#inputForm")
								.validate(
										{
											rules : {
												loginName : {
													remote : "${ctx}/sys/user/validateLoginName"
												},
												mobile : {
													remote : "${ctx}/sys/user/validateMobile"
												},
												randomCode : {

													remote : {

														url : "${ctx}/sys/register/validateMobileCode",

														data : {
															mobile : function() {
																return $("#tel")
																		.val();
															}
														}

													}

												}
											},
											messages : {
												confirmNewPassword : {
													equalTo : "输入与上面相同的密码"
												},
												ck1 : {
													required : "必须接受用户协议."
												},
												loginName : {
													remote : "此用户名已经被注册!",
													required : "用户名不能为空."
												},
												mobile : {
													remote : "此手机号已经被注册!",
													required : "手机号不能为空."
												},
												randomCode : {
													remote : "验证码不正确!",
													required : "验证码不能为空."
												}
											},
											submitHandler : function(form) {
												loading('正在提交，请稍等...');
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}
										});

						$("#resetForm")
								.validate(
										{
											rules : {
												mobile : {
													remote : "${ctx}/sys/user/validateMobileExist"
												}
											},
											messages : {
												mobile : {
													remote : "此手机号未注册!",
													required : "手机号不能为空."
												}
											},
											submitHandler : function(form) {
												loading('正在提交，请稍等...');
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}
										});
						// 手机号码验证
						jQuery.validator
								.addMethod(
										"isMobile",
										function(value, element) {
											var length = value.length;
											var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
											return (length == 11 && mobile
													.test(value));
										}, "请正确填写您的手机号码");

						$('#sendPassBtn')
								.click(
										function() {
											if ($("#tel_resetpass").val() == ""
													|| $("#tel_resetpass-error")
															.text() != "") {
												top.layer.alert(
														"请输入有效的注册手机号码！", {
															icon : 0
														});//讨厌的白色字体问题
												return;

											}
											$("#sendPassBtn").attr("disabled",
													true);
											$
													.get(
															"${ctx}/sys/user/resetPassword?mobile="
																	+ $(
																			"#tel_resetpass")
																			.val(),
															function(data) {
																if (data.success == false) {
																	top.layer
																			.alert(
																					data.msg,
																					{
																						icon : 0
																					});//讨厌的白色字体问题
																	//alert(data.msg);
																	$(
																			"#sendPassBtn")
																			.html(
																					"重新发送")
																			.removeAttr(
																					"disabled");
																	clearInterval(countdown);

																}

															});
											var count = 60;
											var countdown = setInterval(
													CountDown, 1000);
											function CountDown() {
												$("#sendPassBtn").attr(
														"disabled", true);
												$("#sendPassBtn").html(
														"等待 " + count + "秒!");
												if (count == 0) {
													$("#sendPassBtn").html(
															"重新发送").removeAttr(
															"disabled");
													clearInterval(countdown);
												}
												count--;
											}

										});

						$('#sendCodeBtn')
								.click(
										function() {
											if ($("#tel").val() == ""
													|| $("#tel-error").text() != "") {
												top.layer.alert(
														"请输入有效的注册手机号码！", {
															icon : 0
														});//讨厌的白色字体问题
												return;

											}
											$("#sendCodeBtn").attr("disabled",
													true);
											$
													.get(
															"${ctx}/sys/register/getRegisterCode?mobile="
																	+ $("#tel")
																			.val(),
															function(data) {
																if (data.success == false) {
																	//top.layer.alert(data.msg, {icon: 0});讨厌的白色字体问题
																	alert(data.msg);
																	$(
																			"#sendCodeBtn")
																			.html(
																					"重新发送")
																			.removeAttr(
																					"disabled");
																	clearInterval(countdown);

																}

															});
											var count = 60;
											var countdown = setInterval(
													CountDown, 1000);
											function CountDown() {
												$("#sendCodeBtn").attr(
														"disabled", true);
												$("#sendCodeBtn").html(
														"等待 " + count + "秒!");
												if (count == 0) {
													$("#sendCodeBtn").html(
															"重新发送").removeAttr(
															"disabled");
													clearInterval(countdown);
												}
												count--;
											}

										});

					});
</script>
</head>
<body>
	<header class="header w1024 auto pt25 pb15">
		<a href="#" class="ml40 db"><img
			src="${ctxStatic }/images/logo.png" alt="" class="db"></a>
	</header>
	<section class="main w1024 auto">
		<section class="div_bottom">
			<div class="div_top">
				<ul class="ul1">
					<form id="loginForm" class="form-signin" action="${ctx}/login"
						method="post">
						<li>
							<div class="div_xian fix">
								<img src="${ctxStatic }/images/tip_01.png" alt="" class="fl">
								<input type="text" id="username" name="username"
									class="put_public put_01 fl" value="" placeholder="请输入用户名/手机号" />
							</div>
						</li>
						<li>
							<div class="div_xian fix">
								<img src="${ctxStatic }/images/tip_02.png" alt="" class="fl">
								<input type="password" id="password" name="password" value=""
									class="put_public put_01 fl" placeholder="请输入6-16位密码" />
							</div>
							<div class="tr">
								<a href="#" class="a1">忘记密码</a>
							</div>
						</li>
						<li class="fix ling2">
							<div class="div_xian ling fl fix">
								<img src="${ctxStatic }/images/tip_03.png" alt="" class="fl">
								<input type="text" class="put_public put_01 put_02 fl">
							</div> <img src="${ctxStatic }/images/yzm.jpg" alt=""
							class="fl yzm rel"> <span class="span1 fl">换一张</span>
						</li>
						<li class="fix ling">
							<button type="submit" class="put_public but_01">

								<span class="bigger-110">登&nbsp;&nbsp;&nbsp;&nbsp;录</span>
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
</body>
</html>