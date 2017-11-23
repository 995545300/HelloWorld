<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>藏品影像信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>

<body class="hideScroll">

		<form:form id="inputForm" modelAttribute="huaYuanXinXi" action="${ctx}/yingxiangxinxi/huaYuanXinXi/save?cid=${huaYuanXinXi.collectionid}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<form:hidden path="collectionid" value="${huaYuanXinXi.collectionid}"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>藏品影像文件名：</label></td>
					<td class="width-35">
						<form:hidden id="name" path="name" htmlEscape="false" class="form-control"/>
						<sys:ckfinder input="name" type="images" uploadPath="/yingxiangxinxi/huaYuanXinXi" selectMultiple="false"/>
					</td>
					<%-- <td class="width-15 active"><label class="pull-right"><font color="red">*</font>藏品影像缩略图：</label></td>
					<td class="width-35">
						<form:hidden id="smallimg" path="smallimg" htmlEscape="false" class="form-control"/>
						<sys:ckfinder input="smallimg" type="images" uploadPath="/yingxiangxinxi/huaYuanXinXi" selectMultiple="false"/>
					</td> --%>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">拍摄角度：</label></td>
					<td class="width-35">
						<form:select path="paishejiaodu" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('jiaodu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">规格：</label></td>
					<td class="width-35">
						<form:input path="guige" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">制作人：</label></td>
					<td class="width-35">
						<form:input path="zhizuoren" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>