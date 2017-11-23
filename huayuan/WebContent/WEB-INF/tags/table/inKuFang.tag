<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<button class="btn btn-white btn-sm" onclick="inKuFangAll()" data-toggle="tooltip" data-placement="top"><i class="fa fa-level-up"> ${label==null?'入库':label}</i>
                        </button>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入table的id和controller的url --%>
<script type="text/javascript">
$(document).ready(function() {
    $('#${id} thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('check');
    	});

    $('#${id} thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('uncheck');
    	});
    
});

	function inKuFangAll(){

		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  $("#${id} tbody tr td input.i-checks:checkbox").each(function(){
		    if(true == $(this).is(':checked')){
		      str+=$(this).attr("id")+",";
		    }
		  });
		  if(str.substr(str.length-1)== ','){
		    ids = str.substr(0,str.length-1);
		  }
		  if(ids == ""){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
		  }
			top.layer.confirm('确认要批量入库吗?', {icon: 3, title:'系统提示'}, function(index){
			/* window.location = "${url}?cid="+ids; */
			openDialog("添加备注"+'${title}',"${url}?cid="+ids,"${width==null?'400px':width}", "${height==null?'250px':height}","${target}");
		    top.layer.close(index);
		});
		 

	}
</script>