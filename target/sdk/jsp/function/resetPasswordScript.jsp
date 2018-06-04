<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
$(function(){
	$("#dataForm").validate({
		rules: {
	    	newPassword : {
	    		required : true,
	    		minlength : 8
	    	},
	    	confirmPassword : {
	    		required : true,
	    		equalTo : '#newPassword'
	    	}
	    },
	    messages: {
	    	newPassword : {
	    		required : '請輸入新密碼',
	    		minlength : $.validator.format("新密碼最短{0}個字元")
	    	},
	    	confirmPassword : {
	    		required : '請輸入確認密碼',
	    		equalTo : '與新密碼不一致'
	    	}
	    },
	    submitHandler: function(form){
	    	if(grecaptcha.getResponse().length == 0){
    			$.alert({
    				title: '注意',
    				content: "請勾選我不是機器人",
    				confirmButton: 'OK',
    				confirmButtonClass: 'btn-info',
    				animation: 'zoom'
    			});
    			return false;
    					
    		}	    	
	    	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    	var rememberMe = $("#rememberMe").prop("checked");
	    	$.ajax({
	    		type:'POST',
	    		url:'<s:url action="doResetPassword" namespace="/function"/>',
	    		data:{
	    			'user':<s:property value="user"/>,
	    			'token':'<s:property value="token"/>',
	    			'password' : $("#newPassword").val(),
	    			'confirmPassword' : $("#confirmPassword").val(),
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
		  						location.href = '<s:url action="login" namespace="/function"/>'
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
})

function doResetPassword(){
	$("#dataForm").submit();
}

</script>