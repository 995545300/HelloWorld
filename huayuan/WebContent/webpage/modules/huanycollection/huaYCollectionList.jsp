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
	/*
	批量生成word
	*/
	function generateWords(){
		
		var size = $("#contentTable tbody tr td input.i-checks:checked").size();
		  if(size == 0 ){
				top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
				return;
			  }
		    var obj =  $("#contentTable tbody tr td input.i-checks:checkbox:checked");
		    var ids="";
		    for(var i=0; i<obj.length; i++){    
		    	ids+=obj[i].value+',';  //如果选中，将value添加到变量s中    
		      }  
		    window.location.href="${ctx}/huanycollection/huaYCollection/generateWords?ids="+ids;
		
		
	}
	
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>收藏品列表</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content">
				<sys:message content="${message}" />

				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="huaYCollection"
							action="${ctx}/huanycollection/huaYCollection/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<span>藏品名称：</span>
								<form:input path="collectionname" htmlEscape="false"
									maxlength="64" class=" form-control input-sm" />
								<span>藏品编码：</span>
								<form:input path="code" htmlEscape="false" maxlength="64"
									class=" form-control input-sm" />

								<span>搜索库房：</span>
								<sys:treeselect id="kufang" name="kufang.id"
									value="${huaYCollection.kufang.id}" labelName="kufang.name"
									labelValue="${huaYCollection.kufang.name }" title="库房"
									url="/huayuan_kufang/huaYuanKuFang/treeData"
									cssClass="form-control required" allowClear="true"
									notAllowSelectParent="true" />
								<br> <br> <span>入藏日期：</span> <input
									id="beginIncollectiondate" name="beginIncollectiondate"
									type="text" maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${huaYCollection.beginIncollectiondate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								- <input id="endIncollectiondate" name="endIncollectiondate"
									type="text" maxlength="20"
									class="laydate-icon form-control layer-date input-sm"
									value="<fmt:formatDate value="${huaYCollection.endIncollectiondate}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
								<%-- <span>一级类别：</span>
													<form:select path="classone" class="form-control m-b">
														<form:option value="" label="" />
														<form:options items="${fns:getDictList('')}" itemLabel="label"
															itemValue="value" htmlEscape="false" />
													</form:select>
													<span>二级类别：</span>
													<form:select path="classtwo" class="form-control m-b">
														<form:option value="" label="" />
														<form:options items="${fns:getDictList('')}" itemLabel="label"
															itemValue="value" htmlEscape="false" />
													</form:select>
													<span>三级类别：</span>
													<form:select path="classthree" class="form-control m-b">
														<form:option value="" label="" />
														<form:options items="${fns:getDictList('')}" itemLabel="label"
															itemValue="value" htmlEscape="false" />
													</form:select> --%>
								<span>是否已入库</span>

								<form:select path="isinkufang" class="form-control m-b">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('yes_no')}"
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
							<shiro:hasPermission name="huanycollection:huaYCollection:add">
							 	 <table:addRow url="${ctx}/huanycollection/huaYCollection/form"
									width="1150px" height="750px" title="收藏品"></table:addRow>
								<!-- 增加按钮 -->
							</shiro:hasPermission>
							<shiro:hasPermission name="huanycollection:huaYCollection:edit">
								<table:editRow url="${ctx}/huanycollection/huaYCollection/form"
									title="收藏品" width="1200px" height="750px" id="contentTable"></table:editRow>
								<!-- 编辑按钮 -->
							</shiro:hasPermission>
							<shiro:hasPermission name="huanycollection:huaYCollection:del">
								<table:delRow
									url="${ctx}/huanycollection/huaYCollection/deleteAll"
									id="contentTable"></table:delRow>
								<!-- 删除按钮 -->
							</shiro:hasPermission>
							<c:if test="${not empty huaYCollection.isinkufang}">
							<c:if test="${huaYCollection.isinkufang eq 0}">
								<shiro:hasPermission name="huanycollection:huaYCollection:del">
									<table:inKuFang
										url="${ctx}/collectioninout/collectionInOut/addAll" 
										id="contentTable"></table:inKuFang>
									<!-- 入库按钮 -->
								</shiro:hasPermission>
								</c:if>
							<c:if test="${huaYCollection.isinkufang eq 1}">
								<shiro:hasPermission name="huanycollection:huaYCollection:del">
									<table:outKuFang
										url="${ctx}/collectioninout/collectionInOut/addAll"
										id="contentTable"></table:outKuFang>
									<!-- 出库按钮 -->
								</shiro:hasPermission>
						</c:if>
						</c:if>
							<shiro:hasPermission name="huanycollection:huaYCollection:import">
								<table:importExcel
									url="${ctx}/huanycollection/huaYCollection/import"></table:importExcel>
								<!-- 导入按钮 -->
							</shiro:hasPermission>
							<shiro:hasPermission name="huanycollection:huaYCollection:export">
								<table:exportExcel
									url="${ctx}/huanycollection/huaYCollection/export"></table:exportExcel>
								<!-- 导出按钮 -->
							</shiro:hasPermission>
                            <a class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="generateWords()" title="批量生成word">
								<i class="glyphicon glyphicon-repeat"></i>批量生成word
							</a>
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>

						</div>
						<div class="pull-right">
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="reset()">
								<i class="fa fa-refresh"></i> 重置
							</button>
						</div>
					</div>
				</div>

				<!-- 表格 -->
				<table id="contentTable"
					class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
					<thead>
						<tr>
							<th><input type="checkbox" class="i-checks"></th>
							<th class="sort-column code">藏品编码</th>
							<th class="sort-column smallimg">藏品图片</th>
							<th class="sort-column collectionname">藏品名称</th>
							<th class="sort-column author">作者</th>
							<th class="sort-column num">实际数量</th>
							<th class="sort-column incollectiondate">入藏日期</th>
							<th class="sort-column isinkufang">是否在库房</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="huaYCollection">
							<tr>
								<td><input type="checkbox" value="${huaYCollection.id}" id="${huaYCollection.id}"
									class="i-checks"></td>
								<td>${huaYCollection.code}</td>
								<td>
								<c:choose>
								<c:when test="${empty huaYCollection.xinxi.smallimg}">
									<a href="${ctx}/yingxiangxinxi/huaYuanXinXi/list?cid=${huaYCollection.id}">无正面图，点击前往设置</a>
								</c:when>
								<c:when test="${not empty huaYCollection.xinxi.smallimg}">
								<a href="#"
									onclick="openDialogView('查看藏品影像信息', '${ctx}/yingxiangxinxi/huaYuanXinXi/imagelook?cid=${huaYCollection.xinxi.name}','1200px', '750px')">
										<img src="${huaYCollection.xinxi.smallimg}" alt="图片失效"
										height="100" width="100" />
								</a>
								</c:when>
								</c:choose>
								</td>
								<td>${huaYCollection.collectionname}</td>
								<td>${huaYCollection.author}</td>
								<td>${huaYCollection.num}</td>
								<td>
										${huaYCollection.incollectiondate}
									
								<%-- <td>${huaYCollection.copyrights}</td>
								<td>${huaYCollection.inputname}</td>
								<td><fmt:formatDate value="${huaYCollection.inputtime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${huaYCollection.checkname}</td>
								<td><fmt:formatDate value="${huaYCollection.checktime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${huaYCollection.collectorganization}</td>
								<td>${huaYCollection.kufang.name}</td> --%>
								<td>${fns:getDictLabel(huaYCollection.isinkufang, 'inout', '')}</td>
								<td><shiro:hasPermission
										name="huanycollection:huaYCollection:view">
										<a href="#"
											onclick="openDialogView('查看收藏品', '${ctx}/huanycollection/huaYCollection/form?id=${huaYCollection.id}','800px', '500px')"
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											查看</a>
									</shiro:hasPermission> <shiro:hasPermission
										name="huanycollection:huaYCollection:edit">
										<a href="#"
											onclick="openDialog('修改收藏品', '${ctx}/huanycollection/huaYCollection/form?id=${huaYCollection.id}','800px', '500px')"
											class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
											修改</a>
									</shiro:hasPermission> <shiro:hasPermission name="huanycollection:huaYCollection:del">
										<a
											href="${ctx}/huanycollection/huaYCollection/delete?id=${huaYCollection.id}"
											onclick="return confirmx('确认要删除该收藏品吗？', this.href)"
											class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>
											删除</a>
									</shiro:hasPermission> <shiro:hasPermission
										name="huanycollection:huaYCollection:view">
										<a
											href="${ctx}/yingxiangxinxi/huaYuanXinXi/list?cid=${huaYCollection.id} "
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											查看影像信息列表</a>
									</shiro:hasPermission><br> <br> <shiro:hasPermission
										name="huanycollection:huaYCollection:view">
										<a
											href="${ctx}/collectioninout/collectionInOut/list?cid=${huaYCollection.id} "
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											查看出入库列表</a>
									</shiro:hasPermission> <c:if
										test="${huaYCollection.isinkufang eq 0||huaYCollection.isinkufang eq ''}">
										<shiro:hasPermission
											name="huanycollection:huaYCollection:edit">
											<a href="#"
												onclick="openDialog('收藏品入库', '${ctx}/collectioninout/collectionInOut/add?cid=${huaYCollection.id}','400px', '250px')"
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
												入库</a>
											<%-- <a
											href="${ctx}/collectioninout/collectionInOut/add?cid=${huaYCollection.id}"
											onclick="return confirmx('确认该收藏品入库吗？', this.href)"
											class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
											入库</a> --%>
										</shiro:hasPermission>
									</c:if> <c:if test="${huaYCollection.isinkufang eq 1}">
										<shiro:hasPermission
											name="huanycollection:huaYCollection:edit">
											<a href="#"
												onclick="openDialog('收藏品出库', '${ctx}/collectioninout/collectionInOut/add?cid=${huaYCollection.id}','400px', '250px')"
												class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
												出库</a>
										</shiro:hasPermission>
									</c:if>
									 <shiro:hasPermission name="huanycollection:huaYCollection:view">
										<a href="${ctx}/huanycollection/huaYCollection/generateWords?ids=${huaYCollection.id} "
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											生成word</a>
											
										 <c:if test="${huaYCollection.collectionFile!=null&&huaYCollection.collectionFile!='' }">
										   <a href="${ctx}/huanycollection/huaYCollection/downCollectionFile?id=${huaYCollection.id} "
											class="btn btn-info btn-xs"><i class="fa fa-search-plus"></i>
											下载word</a>
										 </c:if>
									</shiro:hasPermission> 
										<a href="${ctx}/huanycollection/huaYCollection/print?id=${huaYCollection.id}"
											class="btn btn-info btn-xs" target="_blank"><i class="fa fa-search-plus"></i>
											打印</a>
									</td>
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