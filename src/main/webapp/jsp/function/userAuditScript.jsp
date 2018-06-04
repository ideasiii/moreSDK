<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var dataTable;
$(function() {
	/* 設定DATATABLE選項 */
	var option = {};
	option.columns = [
        { 'data':'displayName', 'name':'displayName'},
        { 'data':'email', 'name':'email'},
        { 'data':'company', 'name':'company'},
        { 'data':'purpose', 'name':'purpose'},
        { 'data':'createTime', 'name':'createTime'},
        { 'targets':6, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
				return getfunctionHtml(full.userId, full.email);
	        }
        }
    ];
	
	option.ajax = {
		"url" : '<s:url action="getUserAuditList" namespace="/function"/>',
		"type" : "POST",
		"contentType" : "application/json",
		"dataType" : "json",
		"data" : {}
	};
	option.order = [[ 4, "desc" ]];
	dataTable = $('#dataTable').DataTable(option);
});

/* 功能項按鈕的HTML */
function getfunctionHtml(userId, email){
	var html = "";
	html +='<button type="button" title="編輯用戶" class="btn btn-primary btn-circle" onclick="userModify('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html +='<i class="fa fa-gears"></i>';
	html +='</button>';
	
	html +='<button type="button" title="審核通過" class="btn btn-success btn-circle" onclick="verifiedUser('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html +='<i class="fa fa-check"></i>';
	html +='</button>';
	
	html +='<button type="button" title="拒絕用戶" class="btn btn-danger btn-circle" onclick="rejectUser('+userId+',\''+email+'\')" style="margin-right:3px;">';
	html +='<i class="fa fa-times"></i>';
	html +='</button>';
	return html;
}

function userModify(userId, email){
	$("#userId").val(userId);
	$("#modifyForm").attr("action",'<s:url action="userModify" namespace="/function"/>');
	$("#modifyForm").attr("method","POST");
	$("#modifyForm").submit();
}

function verifiedUser(userId, email){
	$.confirm({
		title: '注意!',
		content: '確認審核通過該用戶!',
		confirmButton: '確認',
		confirmButtonClass: 'btn-info',
		cancelButton: '取消',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#modalDiv").modal({backdrop:'static',keyboard:false}); 
			$.ajax({
	    		type:'POST',
	    		url:'<s:url action="verifiedUser" namespace="/function"/>',
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

function rejectUser(userId, email){
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
	    		url:'<s:url action="rejectUser" namespace="/function"/>',
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