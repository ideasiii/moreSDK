<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var dataTable;
$(function() {
	var option = {};
	option.columns = [
		 { 'targets':1, 'render':function(data, type, full, meta) {
				return getUserNameHtml(full.userId, full.displayName);
	        }
     	},
        { 'data':'email', 'name':'email'},
        { 'data':'company', 'name':'company'},
        { 'data':'accessLevel', 'name':'accessLevel'},
        { 'data':'createTime', 'name':'createTime'},
        { 'data':'lastAccessTime', 'name':'lastAccessTime'},
        { 'data':'lastAccessIp', 'name':'lastAccessIp'},
        { 'targets':8, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
				return getFunctionHtml(full.userId, full.email);
	        }
        }
    ];
	
	option.ajax = {
		"url" : '<s:url action="getUserList" namespace="/function"/>',
		"type" : "POST",
		"contentType" : "application/json",
		"dataType" : "json",
		"data" : {}
	};
	
	option.order = [[ 4, "desc" ]];
	
	dataTable = $('#dataTable').DataTable(option);
});

function getUserNameHtml(userId, displayName){
	var html = "";
	html+='<a href=javascript:; onclick="userSdk('+userId+');">';
	html+=displayName;
	html+='</a>';
	return html;
}

function getFunctionHtml(userId, email){
	var html = "";
	html+='<button type="button" title="編輯用戶" class="btn btn-primary btn-circle" onclick="userModify('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html+='<i class="fa fa-gears"></i>';
	html+='</button>';

	html+='<button type="button" title="刪除用戶" class="btn btn-danger btn-circle" onClick="deleteUser('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html+='<i class="fa fa-times"></i>';
	html+='</button>';
	
	html+='<button type="button" title="用戶使用資訊" class="btn btn-success btn-circle" onClick="userDashboard('+userId+')" style="margin-right:3px;">';
	html+='<i class="fa fa-tasks"></i>';
	html+='</button>';
	
	return html;
}

function userModify(userId, email){
	$("#modifyForm").find("#userId").val(userId);
	$("#modifyForm").attr("action",'<s:url action="userModify" namespace="/function"/>');
	$("#modifyForm").attr("method","POST");
	$("#modifyForm").submit();
}

function userDashboard(userId){
	$("#modifyForm").find("#userId").val(userId);
	$("#modifyForm").attr("action",'<s:url action="adminAppDashboard" namespace="/function"/>');
	$("#modifyForm").attr("method","POST");
	$("#modifyForm").submit();
}

function deleteUser(userId, email){
	$.confirm({
		title: '注意!',
		content: '確認拒絕該用戶!',
		confirmButton: '確認',
		confirmButtonClass: 'btn-info',
		cancelButton: '取消',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
			$.ajax({
	    		type:'POST',
	    		url:'<s:url action="deleteUser" namespace="/function"/>',
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

function userSdk(userId){
	$("#modifyForm").find("#userId").val(userId);
	$("#modifyForm").attr("action",'<s:url action="userSdk" namespace="/function"/>');
	$("#modifyForm").attr("method","POST");
	$("#modifyForm").submit();
	
}
</script>