<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:property value="contextPath"/>/vendor/assets/css/material-bootstrap-wizard.css" rel="stylesheet" />
<style>
	label.error, label.error {
		color: red;
	}
	input.error {
		border: 1px solid #f44336;
	}
</style>
<script src='https://www.google.com/recaptcha/api.js'></script>
<script type="text/javascript">

function registeredMember(){
	if($("#agreeCheckBox").prop("checked")){
		$.confirm({
    		title: '注意!',
    		content: '確認會員資料無誤!',
    		confirmButton: '確認',
    		confirmButtonClass: 'btn-info',
    		cancelButton: '取消',
    		cancelButtonClass: 'btn-danger',
    		confirm: function(){
    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
    	    	$.ajax({
    	    		type:'POST',
    	    		url:'<s:url action="doRegistered" namespace="/function"/>',
    	    		data:{
    	    			'user.email' : $("#email").val(),
    	    			'user.displayName' : $("#displayName").val(),
    	    			'user.company' : $("#company").val(),
       					'user.phone' : $("#phone").val(),
    					'user.purpose' : $("#purpose").val(),
    					'password' : $("#newPassword").val(),
    	    			'confirmPassword' : $("#confirmPassword").val()
    	    		},
    	    		dataType:"json",
    	    		success:function(data){
    	    			if(data.isSuccess){
    	    				$.alert({
    		  					title: '成功訊息',
    		  					content: data.message,
    		  					confirmButton: 'OK',
    		  					confirmButtonClass: 'btn-info',
    		  					animation: 'zoom',
    		  					close : function(){
    		  						location.href = '<s:url action="dashboard" namespace="/function"/>'
    		  					}
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
	}else{
		$.alert({
			title: '注意',
			content: "請先閱讀同意書並勾選",
			confirmButton: 'OK',
			confirmButtonClass: 'btn-info',
			animation: 'zoom'
								
		});
	}
	
	
}

</script>