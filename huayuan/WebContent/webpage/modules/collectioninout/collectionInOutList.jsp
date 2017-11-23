<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>收藏品出入登记管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		laydate({
			elem : '#beginDatetime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});
		laydate({
			elem : '#endDatetime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			event : 'focus' //响应事件。如果没有传入event，则按照默认的click
		});

	});
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>收藏品出入登记列表</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i
						class="fa fa-wrench"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li><a href="#">选项1</a></li>
						<li><a href="#">选项2</a></li>
					</ul>
					<a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content">
				<sys:message content="${message}" />

				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="collectionInOut"
							action="${ctx}/collectioninout/collectionInOut/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<%-- <span>藏品名称：</span>
				<form:input path="collcetionname" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/> --%>
							<c:if test="${not empty collectionInOut.collection.id}">
								<form:hidden path="collection.id"/>
							</c:if>
								<span>操作人姓名：</span>
								<form:input path="name" htmlEscape="false" maxlength="64"
									class=" form-control input-sm" />
								<span>出入库时间：</span> <input id="beginDatetime"
									name="beginDatetime" type="text" maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${collectionInOut.beginDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								- <input id="endDatetime" name="endDatetime" type="text"
									maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${collectionInOut.endDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								<span>事务：</span>
								<form:select path="work" class="form-control m-b">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('inout')}"
										itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							
							<shiro:hasPermission name="collectioninout:collectionInOut:del">
								<table:delRow
									url="${ctx}/collectioninout/collectionInOut/deleteAll"
									id="contentTable"></table:delRow>
								<!-- 删除按钮 -->
							</shiro:hasPermission>
							
							<shiro:hasPermission
								name="collectioninout:collectionInOut:export">
								<table:exportExcel
									url="${ctx}/collectioninout/collectionInOut/export"></table:exportExcel>
								<!-- 导出按钮 -->
							</shiro:hasPermission>
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>

						</div>
						<div class="pull-right">
						<%-- 	<c:if test="${not empty collectionInOut.collection.id}">
								<button class="btn btn-primary btn-rounded btn-outline btn-sm "
									onclick="location='${ctx}/huanycollection/huaYCollection/list'">
									<i class="fa fa-mail-reply"></i> 返回收藏品
								</button>
								<button class="btn btn-primary btn-rounded btn-outline btn-sm "
									onclick="location='${ctx}/collectioninout/collectionInOut/list?cid=${collectionInOut.collection.id}'">
									<i class="fa fa-refresh"></i> 重置
								</button>
								<button class="btn btn-primary btn-rounded btn-outline btn-sm "
									onclick="search()">
									<i class="fa fa-search"></i> 查询
								</button>
							</c:if> --%>
							<c:choose>
								<c:when test="${not empty collectionInOut.collection.id}">
									<button class="btn btn-primary btn-rounded btn-outline btn-sm "
										onclick="location='${ctx}/huanycollection/huaYCollection/list'">
										<i class="fa fa-mail-reply"></i> 返回收藏品
									</button>
									<button class="btn btn-primary btn-rounded btn-outline btn-sm "
										onclick="location='${ctx}/collectioninout/collectionInOut/list?cid=${collectionInOut.collection.id}'">
										<i class="fa fa-refresh"></i> 重置
									</button>
									<button class="btn btn-primary btn-rounded btn-outline btn-sm "
										onclick="search()">
										<i class="fa fa-search"></i> 查询
									</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-primary btn-rounded btn-outline btn-sm "
										onclick="search()">
										<i class="fa fa-search"></i> 查询
									</button>
									<button class="btn btn-primary btn-rounded btn-outline btn-sm "
										onclick="reset()">
										<i class="fa fa-refresh"></i> 重置
									</button>
								</c:otherwise>
							</c:choose>
							<!-- <button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="reset()">
								<i class="fa fa-refresh"></i> 重置
							</button> -->

						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="i-checks"></th>
							<th class="sort-column collection.id">藏品名称</th>
							<th class="sort-column name">操作人姓名</th>
							<th class="sort-column datetime">出入库时间</th>
							<th class="sort-column work">事务</th>
							<th class="sort-column remarks">备注信息</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="collectionInOut">
							<tr>
								<td><input type="checkbox" id="${collectionInOut.id}"
									class="i-checks"></td>
								<td><a href="#"
									onclick="openDialogView('查看收藏品出入登记', '${ctx}/collectioninout/collectionInOut/form?id=${collectionInOut.id}','800px', '500px')">
										${collectionInOut.collection.collectionname} </a></td>
								<td>${collectionInOut.name}</td>
								<td><fmt:formatDate value="${collectionInOut.datetime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${fns:getDictLabel(collectionInOut.work, 'inout', '')}
								</td>
								<td>${collectionInOut.remarks}</td>
								<td><shiro:hasPermission
										name="collectioninout:collectionInOut:view">
										<a href="#"
											onclick="openDialogView('查看收藏品出入登记', '${ctx}/collectioninout/collectionInOut/form?id=${collectionInOut.id}','800px', '500px')"
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											查看</a>
									</shiro:hasPermission> <shiro:hasPermission
										name="collectioninout:collectionInOut:del">
										<a
											href="${ctx}/collectioninout/collectionInOut/delete?id=${collectionInOut.id}"
											onclick="return confirmx('确认要删除该收藏品出入登记吗？', this.href)"
											class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>
											删除</a>
									</shiro:hasPermission></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
			</div>
		</div>
	</div>
</body>
</html>