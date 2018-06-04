<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function(){
		$("#dataForm").validate({
		    rules: {
		    	email: "required",
		    	password: "required",
		    },
		    messages: {
		    	email: "請輸入email",
		    	password: "請輸入密碼",
		    },
		    submitHandler: function(form){
		    	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
		    	var rememberMe = $("#rememberMe").prop("checked");
		    	$.ajax({
		    		type:'POST',
		    		url:'<s:url action="doLogin" namespace="/function"/>',
		    		data:{
		    			'email':$("#email").val(),
		    			'password':$("#password").val(),
		    			'rememberMe':rememberMe?"Y":"N"
		    		},
		    		dataType:"json",
		    		success:function(data){
		    			if(data.isSuccess){
		    				location.href = '<s:url action="trackerData" namespace="/function"/>'
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
	
	function doLogin(){
		$("#dataForm").submit();
	}

</script>