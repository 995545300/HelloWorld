<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>收藏品管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function() {
		var str = "";
		//获取同名复选框元素
		var name = document.getElementsByName("copyright");
		//接收后台传来的list
		var list = "${copyright}";
		if (list.length != 0) {
			for (var i = 0; i < name.length; i++) {
				<c:forEach items="${copyright}" var="item">
				if (str + name[i].value == "${copyright}") {
					$("#" + str + name[i].id).parent().attr('class',
							'icheckbox_square-green checked');

				}
				</c:forEach>
			}
		}

	});
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(
			function() {
				validateForm = $("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

				/*laydate({
					elem : '#incollectiondate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
					event : 'focus' //响应事件。如果没有传入event，则按照默认的click
				});
			 	laydate({
					elem : '#inputtime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
					event : 'focus' //响应事件。如果没有传入event，则按照默认的click
				}); 
				laydate({
					elem : '#checktime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
					event : 'focus' //响应事件。如果没有传入event，则按照默认的click
				});*/
			});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
			idx : idx,
			delBtn : true,
			row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(
				function() {
					var ss = $(this).attr("data-value").split(',');
					for (var i = 0; i < ss.length; i++) {
						if ($(this).val() == ss[i]) {
							$(this).attr("checked", "checked");
						}
					}
				});
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}
</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="huaYCollection"
		action="${ctx}/huanycollection/huaYCollection/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table
			class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<c:if test="${not empty huaYCollection.code}">
					<tr>
						<%-- <td class="width-15 active"><label class="pull-right">数据状态：</label></td>
					<td class="width-35">
						<form:input path="datastatus" htmlEscape="false"    class="form-control "/>
					</td> --%>
						<td class="width-15 active"><label class="pull-right">藏品编码：</label></td>
						<td class="width-35"><form:input path="code"
								htmlEscape="false" class="form-control " /></td>
					</tr>
				</c:if>
				<tr>
					<td class="width-15 active"><label class="pull-right">藏品登记号：</label></td>
					<td class="width-35"><form:input path="registercode"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>藏品名称：</label></td>
					<td class="width-35"><form:input path="collectionname"
							htmlEscape="false" class="form-control required" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">原名：</label></td>
					<td class="width-35"><form:input path="name"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>一级类别：</label></td>
					<td class="width-35"><form:select path="classone"
							class="form-control" onchange="gettwo(this.value)" id="classone"
							value="${huaYCollection.classone}">
							<form:options items="${classone}" itemLabel="name"
								itemValue="name" htmlEscape="false" />
						</form:select></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>二级类别：</label></td>
					<td class="width-35"><form:select path="classtwo"
							class="form-control " onchange="getthree(this.value)"
							id="classtwo" value="${huaYCollection.classtwo}">
							<form:option value="" label="" />
						</form:select></td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>三级类别：</label></td>
					<td class="width-35"><form:select path="classthree"
							class="form-control " id="classthree"
							value="${huaYCollection.classthree}">
							<form:option value="" label="" />
						</form:select></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">创作年代：</label></td>
					<td class="width-35"><form:input path="createyear"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">质地：</label></td>
					<td class="width-35"><form:input path="characterway"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">尺寸：</label></td>
					<td class="width-35"><form:input path="measure"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">作者：</label></td>
					<td class="width-35"><form:input path="author"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">工艺技法：</label></td>
					<td class="width-35"><form:input path="workway"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">形态形制：</label></td>
					<td class="width-35"><form:input path="morphologicalshape"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">主题：</label></td>
					<td class="width-35"><form:input path="method"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">题名：</label></td>
					<td class="width-35"><form:input path="autograph"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">款识：</label></td>
					<td class="width-35"><form:input path="inscriptions"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">题跋：</label></td>
					<td class="width-35"><form:input path="preface"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">铭文：</label></td>
					<td class="width-35"><form:input path="epigraph"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">题签：</label></td>
					<td class="width-35"><form:input path="titlepiece"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">印鉴：</label></td>
					<td class="width-35"><form:input path="publicseal"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">质量：</label></td>
					<td class="width-35"><form:input path="mass"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">质量填写：</label></td>
					<td class="width-35"><form:input path="massnote"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">完残程度：</label></td>
					<td class="width-35"><form:select path="degreeofresidual"
							class="form-control ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('chengdu')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">完残状况：</label></td>
					<td class="width-35"><form:input path="degreeofresidualnote"
							htmlEscape="false" class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">保存状态：</label></td>
					<td class="width-35"><form:select path="savestatus"
							class="form-control ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('saved_state')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">实际数量：</label></td>
					<td class="width-35"><form:input path="num" htmlEscape="false"
							class="form-control " /></td>
					<td class="width-15 active"><label class="pull-right">来源：</label></td>
					<td class="width-35"><form:select path="source"
							class="form-control ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('source')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>入藏日期：</label></td>
					<td class="width-35"><%-- <input id="incollectiondate"
						name="incollectiondate" type="text" maxlength="20"
						class="laydate-icon form-control layer-date required"
						value="<fmt:formatDate value="${huaYCollection.incollectiondate}" pattern="yyyy-MM-dd HH:mm:ss"/>" /> --%>
						<form:input path="incollectiondate"
							htmlEscape="false" class="form-control " />
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>藏品著作权归属：</label></td>
					<td class="width-35">
						<%-- <sys:gridselect url="${ctx}/huanycollection/huaYCollection/selectcopyright" id="copyright" name="copyright.id"  value="${huaYCollection.copyright.id}"  title="选择藏品著作权归属" labelName="copyright.name" 
						 labelValue="${huaYCollection.copyright.name}" cssClass="form-control required" fieldLabels="著作权" fieldKeys="name" searchLabel="著作权" searchKey="name" ></sys:gridselect> --%>
						<form:radiobuttons path="copyrightlist" items="${copyright}"
							itemLabel="name" itemValue="name" htmlEscape="false"
							cssClass="i-checks required" /> <%--  							<form:radiobuttons path="copyrightlist" items="${copyright}" itemLabel="name" itemValue="name" htmlEscape="false" class="i-checks "/>
 --%>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否在库房：</label></td>
					<td class="width-35"><form:select path="isinkufang"
							class="form-control m-b required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<%-- <td class="width-15 active"><label class="pull-right">藏品著作权归属第二子项：</label></td>
					<td class="width-35"><form:input path="copyrightsecond"
							htmlEscape="false" class="form-control " /></td> --%>
					<td class="width-15 active"><label class="pull-right">备注：</label></td>
					<td class="width-35"><form:input path="mark"
							htmlEscape="false" class="form-control " /></td>
				</tr>
				<%-- <tr>
					<td class="width-15 active"><label class="pull-right">单位代码：</label></td>
					<td class="width-35">
						<form:input path="unitcode" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">录入人：</label></td>
					<td class="width-35">
						<form:input path="inputname" htmlEscape="false"    class="form-control "/>
					</td>
				</tr> --%>
				<%-- <tr>
					<td class="width-15 active"><label class="pull-right">录入时间：</label></td>
					<td class="width-35">
						<input id="inputtime" name="inputtime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${huaYCollection.inputtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">审核人：</label></td>
					<td class="width-35">
						<form:input path="checkname" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">审核时间：</label></td>
					<td class="width-35">
						<input id="checktime" name="checktime" type="text" maxlength="20" class="laydate-icon form-control layer-date "
							value="<fmt:formatDate value="${huaYCollection.checktime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
					<td class="width-15 active"><label class="pull-right">退回原因：</label></td>
					<td class="width-35">
						<form:input path="backreason" htmlEscape="false"    class="form-control "/>
					</td>
				</tr> --%>
				<tr>
					<td class="width-15 active"><label class="pull-right">收藏单位：</label></td>
					<td class="width-35"><form:input path="collectorganization"
							htmlEscape="false" class="form-control " /></td>
					<%-- <td class="width-15 active"><label class="pull-right">搜索库房：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/huanycollection/huaYCollection/selectkufang" id="kufang" name="kufang.id"  value="${huaYCollection.kufang.id}"  title="选择搜索库房" labelName="kufang.name" 
						 labelValue="${huaYCollection.kufang.name}" cssClass="form-control required" fieldLabels="库房" fieldKeys="name" searchLabel="库房" searchKey="name" ></sys:gridselect>
					</td> --%>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>搜索库房：</label></td>
					<td class="width-35"><sys:treeselect id="kufang"
							name="kufang.id" value="${huaYCollection.kufang.id}"
							labelName="kufang.name"
							labelValue="${huaYCollection.kufang.name }" title="库房"
							url="/huayuan_kufang/huaYuanKuFang/treeData"
							cssClass="form-control required" allowClear="true"
							notAllowSelectParent="true" /></td>
				</tr>
				
			</tbody>
		</table>
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1"
					aria-expanded="true">附加项目：</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<a class="btn btn-white btn-sm"
						onclick="addRow('#huanyCollectionAdditiveList', huanyCollectionAdditiveRowIdx, huanyCollectionAdditiveTpl);huanyCollectionAdditiveRowIdx = huanyCollectionAdditiveRowIdx + 1;"
						title="新增"><i class="fa fa-plus"></i> 新增</a>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th width="20%">项目</th>
								<th width="75%">内容</th>
								<th width="5%">&nbsp;</th>
							</tr>
						</thead>
						<tbody id="huanyCollectionAdditiveList">
						</tbody>
					</table>
					<script type="text/template" id="huanyCollectionAdditiveTpl">//<!--
				<tr id="huanyCollectionAdditiveList{{idx}}">
					<td class="hide">
						<input id="huanyCollectionAdditiveList{{idx}}_id" name="huanyCollectionAdditiveList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="huanyCollectionAdditiveList{{idx}}_delFlag" name="huanyCollectionAdditiveList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="huanyCollectionAdditiveList{{idx}}_name" name="huanyCollectionAdditiveList[{{idx}}].name" type="text" value="{{row.name}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="huanyCollectionAdditiveList{{idx}}_text" name="huanyCollectionAdditiveList[{{idx}}].text" type="text" value="{{row.text}}"    class="form-control "/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#huanyCollectionAdditiveList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
					<script type="text/javascript">
						var huanyCollectionAdditiveRowIdx = 0, huanyCollectionAdditiveTpl = $("#huanyCollectionAdditiveTpl").html().replace(
								/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");
						
						$(document).ready(function() {
							var data = ${fns:toJson(huaYCollection.huanyCollectionAdditiveList)};
							for (var i = 0; i < data.length; i++) {
								addRow(
										'#huanyCollectionAdditiveList',
										huanyCollectionAdditiveRowIdx,
										huanyCollectionAdditiveTpl,
										data[i]);
								huanyCollectionAdditiveRowIdx = huanyCollectionAdditiveRowIdx + 1;
							}
						});
						
					</script>
				</div>
	</form:form>
	<script>
		function gettwo(name) {
			document.getElementById('classtwo').options.length = 0;
			var url = "${ctx}/huanycollection/huaYCollection/gettwo";
			$
					.ajax({
						type : "get",
						async : false, //同步请求
						url : url,
						timeout : 1000,
						data : {
							name : name,
						},
						success : function(dates) {
							document.getElementById('classtwo').options.length = 0;
							var objJson = JSON.stringify(dates);
							var list = eval("(" + objJson + ")");
							for (var i = 0; i < list.length; i++) {
								if (null != list[i] && list[i] != "") {
									var op = document.createElement("option"); // 新建OPTION (op) 
									op.setAttribute("value", list[i].name); // 设置OPTION的 VALUE 
									op.appendChild(document
											.createTextNode(list[i].name));// 设置OPTION的 TEXT
									document.getElementById('classtwo')
											.appendChild(op);// 为SELECT 新建一 OPTION(op)
									if ('${huaYCollection.classtwo}' == list[i].name) {
										op.setAttribute("selected", "true");
									}
								}
							}
						},
						error : function() {
							/*  alert("error"); */
						}
					});
			getthree($("#classtwo").val());
		}

		function getthree(name) {
			document.getElementById('classthree').options.length = 0;
			var url = "${ctx}/huanycollection/huaYCollection/gettwo";
			$
					.ajax({
						type : "get",
						async : false, //同步请求
						url : url,
						timeout : 1000,
						data : {
							name : name,
						},
						success : function(dates) {
							document.getElementById('classthree').options.length = 0;
							var objJson = JSON.stringify(dates);
							var list = eval("(" + objJson + ")");
							for (var i = 0; i < list.length; i++) {
								if (null != list[i] && list[i] != "") {
									var op = document.createElement("option"); // 新建OPTION (op) 
									op.setAttribute("value", list[i].name); // 设置OPTION的 VALUE 
									op.appendChild(document
											.createTextNode(list[i].name));// 设置OPTION的 TEXT
									document.getElementById('classthree')
											.appendChild(op);// 为SELECT 新建一 OPTION(op)
									if ('${huaYCollection.classthree}' == list[i].name) {
										op.setAttribute("selected", "true");
									}
								}
							}
						},
						error : function() {
							/*  alert("error"); */
						}
					});
		}

		gettwo($("#classone").val());
	</script>



</body>
</html>