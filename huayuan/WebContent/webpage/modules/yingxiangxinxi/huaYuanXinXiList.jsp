<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>藏品影像信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>藏品影像信息列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="huaYuanXinXi" action="${ctx}/yingxiangxinxi/huaYuanXinXi/?cid=${huaYuanXinXi.collectionid}" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="collectionid" name="collectionid" type="hidden" value="${huaYuanXinXi.collectionid}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>拍摄角度：</span>
				<form:select path="paishejiaodu"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('jiaodu')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>制作人：</span>
				<form:input path="zhizuoren" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:add">
				<table:addRow url="${ctx}/yingxiangxinxi/huaYuanXinXi/form?cid=${huaYuanXinXi.collectionid}" title="藏品影像信息"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:edit">
			    <table:editRow url="${ctx}/yingxiangxinxi/huaYuanXinXi/form" title="藏品影像信息" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:del">
				<table:delRow url="${ctx}/yingxiangxinxi/huaYuanXinXi/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:import">
				<table:importExcel url="${ctx}/yingxiangxinxi/huaYuanXinXi/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:export">
	       		<table:exportExcel url="${ctx}/yingxiangxinxi/huaYuanXinXi/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="location='${ctx}/yingxiangxinxi/huaYuanXinXi/list?cid=${huaYuanXinXi.collectionid}'" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="location='${ctx}/huanycollection/huaYCollection/list'" ><i class="fa fa-mail-reply"></i> 返回收藏品</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="location='${ctx}/yingxiangxinxi/huaYuanXinXi/list?cid=${huaYuanXinXi.collectionid}'" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column name">藏品影像</th>
				<th  class="sort-column paishejiaodu">拍摄角度</th>
				<th  class="sort-column guige">规格</th>
				<th  class="sort-column zhizuoren">制作人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="huaYuanXinXi">
			<tr>
				<td> <input type="checkbox" id="${huaYuanXinXi.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看藏品影像信息', '${ctx}/yingxiangxinxi/huaYuanXinXi/imagelook?cid=${huaYuanXinXi.name}','1200px', '750px')">
					<img src="${huaYuanXinXi.smallimg}" alt="图片失效，请重新上传" height="100" width="100"/>
				</a></td>
				<td>
					${fns:getDictLabel(huaYuanXinXi.paishejiaodu, 'jiaodu', '')}
				</td>
				<td>
					${huaYuanXinXi.guige}
				</td>
				<td>
					${huaYuanXinXi.zhizuoren}
				</td>
				<td>
					<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:view">
						<a href="#" onclick="openDialogView('查看藏品影像信息', '${ctx}/yingxiangxinxi/huaYuanXinXi/form?id=${huaYuanXinXi.id}&cid=${huaYuanXinXi.collectionid}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:edit">
    					<a href="#" onclick="openDialog('修改藏品影像信息', '${ctx}/yingxiangxinxi/huaYuanXinXi/form?id=${huaYuanXinXi.id}&cid=${huaYuanXinXi.collectionid}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="yingxiangxinxi:huaYuanXinXi:del">
						<a href="${ctx}/yingxiangxinxi/huaYuanXinXi/delete?id=${huaYuanXinXi.id}&cid=${huaYuanXinXi.collectionid}" onclick="return confirmx('确认要删除该藏品影像信息吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>