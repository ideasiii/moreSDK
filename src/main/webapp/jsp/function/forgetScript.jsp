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
<script>
$(function(){
	$("#dataForm").validate({
	    rules: {
	    	email : {
 	    		required : true,
 	    		email : true
 	    	}
	    },
	    messages: {
	    	email : {
	    		required: "請輸入Email",
    	      	email: "Email 格式錯誤"
	    	}
	    },
	    submitHandler: function(form){
	    	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    	var rememberMe = $("#rememberMe").prop("checked");
	    	$.ajax({
	    		type:'POST',
	    		url:'<s:url action="doForget" namespace="/function"/>',
	    		data:{
	    			'email':$("#email").val()
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

function doForget(){
	$("#dataForm").submit();
}

</script>