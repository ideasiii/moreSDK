<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:property value="contextPath"/>/css/sb-admin-2.css" rel="stylesheet">
<script>
function appDashboardDetail(appId){
	$("#pageForm").attr("action", '<s:url action="appDashboardDetail" namespace="/function" />');
	$("#pageForm").attr("method", 'post');
	$("#appId").val(appId);
	$("#pageForm").submit();
}
<s:if test='"Y".equals(isAdmin)'>
function modifyAppPermissionType(){
	$.confirm({
		title: '注意!',
		content: '確認修改App權限!',
		confirmButton: '確認',
		confirmButtonClass: 'btn-info',
		cancelButton: '取消',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#modalDiv").modal({backdrop:'static',keyboard:false}); 
			var appIds = [];
			$("input[name='appId']").each(function(){
				appIds.push($(this).val());
			});
			var permissionTypes = [];
			$("select[name='permissionType']").each(function(){
				permissionTypes.push($(this).val());
			});
			$.ajax({
	    		type:'POST',
	    		url:'<s:url action="modifyAppPermissionType" namespace="/function"/>',
	    		data:{
	    			'appIds' : appIds.join(","),
	    			'permissionTypes' : permissionTypes.join(",")
	    		},
	    		dataType:"json",
	    		success:function(data){
	    			if(data.isSuccess){
	    				$.alert({
		  					title: '成功訊息',
		  					content: data.message,
		  					confirmButton: 'OK',
		  					confirmButtonClass: 'btn-info',
		  					animation: 'zoom'
		  										
	  					});
	    			}else{
	    				$.alert({
	  					  	title: '錯誤訊息',
	  					  	content: data.message,
	  					  	confirmButton: 'OK',
	  					  	confirmButtonClass: 'btn-info',
	  					  	animation: 'zoom'
	  										
	  					});
	    			}
	    			$("#modalDiv").modal('hide');  
	    		}
	    	});
		}
	});
	
}
</s:if>

</script>