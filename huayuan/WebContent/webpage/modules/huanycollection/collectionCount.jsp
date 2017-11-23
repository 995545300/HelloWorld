<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>收藏品管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		laydate({
			elem : '#beginIncollectiondate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
		laydate({
			elem : '#endIncollectiondate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
		

	});
	
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>收藏品统计</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content">
				<sys:message content="${message}" />
				<!-- 表格 -->
				<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="location='${ctx}/huanycollection/huaYCollection/count'" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th class="sort-column code">项目</th>
							<th class="sort-column smallimg">数量</th>
							<!-- <th class="sort-column collectionname">在库藏品数量</th>
							<th class="sort-column author">出库藏品数量</th>
							 -->
						</tr>
					</thead>
					<tbody>
						
							<tr>
								<td>藏品总数量</td>
								<td>${list[0]}</td>
							</tr>
							<tr>
								<td>库内藏品数量</td>
								<td>${list[1]}</td>
							</tr>
							<tr>
								<td>出库藏品数量</td>
								<td>${list[2]}</td>
							</tr>
							</tbody>
							</table>
								
			</div>
		</div>
	</div>
</body>
</html>