<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<img src="${imgurl}" width="800px"/><br/><br/><br/>
<table border="1" cellpadding="0" cellspacing="0" style="border-collapse:collapse;" width="800px">
<tr>
	<td>藏品编码</td>
	<td colspan="3">${huaYCollection.code }</td>
	<td>藏品登记</td>
	<td>${huaYCollection.registercode }</td>
</tr>
<tr>
	<td>所在位置(库房)</td>
	<td colspan="5">${kufangname}</td>
</tr>
<tr>
	<td>藏品名称</td>
	<td colspan="3">${huaYCollection.collectionname }</td>
	<td>收藏单位</td>
	<td>${huaYCollection.collectorganization }</td>
</tr>
<tr>
	<td>原名</td>
	<td colspan="3">${huaYCollection.name }</td>
	<td>入藏日期</td>
	<td>${huaYCollection.incollectiondate }</td>
</tr>
<tr>
	<td>类别</td>
	<td colspan="3">${huaYCollection.classone }</td>
	<td>来源</td>
	<td>${huaYCollection.source }</td>
</tr>
<tr>
	<td>创作年代</td>
	<td>${huaYCollection.createyear}</td>
	<td>作者</td>
	<td>${huaYCollection.author }</td>
	<td>质地</td>
	<td>${huaYCollection.characterway }</td>
</tr>
<tr>
	<td>工艺技法</td>
	<td>${huaYCollection.workway}</td>
	<td>形制</td>
	<td>${huaYCollection.morphologicalshape}</td>
	<td>实际数量</td>
	<td>${huaYCollection.num }</td>
</tr>
<tr>
	<td>主题</td>
	<td colspan="3">${huaYCollection.method}</td>
	<td>质量</td>
	<td>${huaYCollection.mass }</td>
</tr>
<tr>
	<td>题识和印鉴</td>
	<td colspan="5">${huaYCollection.autographContent}</td>
</tr>
<tr>
	<td>尺寸</td>
	<td colspan="5">${huaYCollection.measure}</td>
</tr>

<tr>
	<td>完残状况</td>
	<td colspan="3">${huaYCollection.degreeofresidualnote }</td>
	<td>完残程度</td>
	<td>${huaYCollection.degreeofresidual }</td>
</tr>

<tr>
	<td>保存状态</td>
	<td colspan="3">${huaYCollection.savestatus }</td>
	<td>影像文件数量</td>
	<td>${yingxiangnum }</td>
</tr>
<tr>
	<td>藏品著作权归属</td>
	<td colspan="5">${huaYCollection.copyright.name}</td>
</tr>
<!-- <tr>
	<td>描述</td>
	<td colspan="5">内容</td>
</tr> -->
<c:forEach items="${huaYCollection.huanyCollectionAdditiveList }" var="obj">
<tr>
	<td>${obj.name }</td>
	<td colspan="5">${obj.text }</td>
</tr>
</c:forEach>

<c:forEach items="${listinout}" var="obj">
<tr>
	<td>出入库</td>
	<td >${fns:getDictLabel(obj.work, 'inout', '')}</td>
	<td>出入库时间</td>
	<td><fmt:formatDate value="${obj.datetime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	<td>操作人</td>
	<td>${obj.name }</td>
</tr>
</c:forEach>

</table>
<br/>
<a onclick="dayin()" id="dayin" style="margin-left:400px;margin-bottom50px;idth: 100px;height: 36px;color: #fff;font-size: 15px;letter-spacing: 1px;background: #3385ff;border-bottom: 1px solid #2d78f4;">打印</a>
<script>
	function dayin(){
		document.getElementById("dayin").style.display="none"; 
		print();
	}



</script>