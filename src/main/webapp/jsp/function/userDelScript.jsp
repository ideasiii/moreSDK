<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var dataTable;
$(function() {
	var option = {};
	option.columns = [
        { 'data':'displayName', 'name':'displayName'},
        { 'data':'email', 'name':'email'},
        { 'data':'company', 'name':'company'},
        { 'data':'accessLevel', 'name':'accessLevel'},
        { 'data':'createTime', 'name':'createTime'},
        { 'data':'lastAccessTime', 'name':'lastAccessTime'},
        { 'targets':7, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
				return getfunctionHtml(full.userId, full.email);
	        }
        }
    ];
	
	option.ajax = {
		"url" : '<s:url action="getUserDelList" namespace="/function"/>',
		"type" : "POST",
		"contentType" : "application/json",
		"dataType" : "json",
		"data" : {}
	};
	option.order = [[ 4, "desc" ]];
	dataTable = $('#dataTable').DataTable(option);
});

function getfunctionHtml(userId, email){
	var html = "";

	html+='<button type="button" title="還原用戶" class="btn btn-danger btn-circle" onClick="recoverUser('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html+='<i class="fa fa-times"></i>';
	html+='</button>';
	
	return html;
	
}

function recoverUser(userId, email){
	$.confirm({
		title: '注意!',
		content: '確認恢復該用戶!',
		confirmButton: '確認',
		confirmButtonClass: 'btn-info',
		cancelButton: '取消',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
			$.ajax({
	    		type:'POST',
	    		url:'<s:url action="recoverUser" namespace="/function"/>',
	    		data:{
	    			'userPlus.userId' : userId,
	    			'userPlus.email' : email
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
	    				dataTable.ajax.reload();
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
</script>