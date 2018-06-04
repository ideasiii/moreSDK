<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script>

$(function(){
	/*  會員資料驗證 */
	$("#dataForm").validate({
	    rules: {
	    	displayName: "required",
	    	company: "required",
	    	phone: "required",
	    	purpose: "required"
	    },
	    messages: {
	    	displayName : "請輸入姓名",
	    	company : "請輸入單位",
	    	phone : "請輸入電話",
	    	purpose : "請輸入目的"
	    },
	    submitHandler: function(form){
	    	$.confirm({
	    		title: '<s:text name="editConfirm" />!',
	    		content: '確認修改會員資料!',
	    		confirmButton: '<s:text name="confirm"/>',
	    		confirmButtonClass: 'btn-info',
	    		cancelButton: '<s:text name="cancel"/>',
	    		cancelButtonClass: 'btn-danger',
	    		confirm: function(){
	    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    	    	$.ajax({
	    	    		type:'POST',
	    	    		url:'<s:url action="modifyMember" namespace="/function"/>',
	    	    		data:{
	    	    			'user.displayName' : $("#displayName").val(),
	    	    			'user.company' : $("#company").val(),
	       					'user.phone' : $("#phone").val(),
	    					'user.purpose' : $("#purpose").val()
	    	    		},
	    	    		dataType:"json",
	    	    		success:function(data){
	    	    			if(data.isSuccess){
	    	    				$.alert({
	    		  					title: '<s:text name="success"/>',
	    		  					content: data.message,
	    		  					confirmButton: 'OK',
	    		  					confirmButtonClass: 'btn-info',
	    		  					animation: 'zoom'
	    		  										
	    	  					});
	    	    			}else{
	    	    				$.alert({
	    	  					  	title: '<s:text name="error"/>',
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
    });
	
	$("#passwordForm").validate({
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
	    	$.confirm({
	    		title: '<s:text name="editConfirm" />!',
	    		content: '確認修改密碼!',
	    		confirmButton: '<s:text name="confirm"/>',
	    		confirmButtonClass: 'btn-info',
	    		cancelButton: '<s:text name="cancel"/>',
	    		cancelButtonClass: 'btn-danger',
	    		confirm: function(){
	    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    	    	$.ajax({
	    	    		type:'POST',
	    	    		url:'<s:url action="modifyMemberPassword" namespace="/function"/>',
	    	    		data:{
	    	    			'password' : $("#newPassword").val(),
	    	    			'confirmPassword' : $("#confirmPassword").val(),
	    	    		},
	    	    		dataType:"json",
	    	    		success:function(data){
	    	    			if(data.isSuccess){
	    	    				$.alert({
	    		  					title: '<s:text name="success"/>',
	    		  					content: data.message,
	    		  					confirmButton: 'OK',
	    		  					confirmButtonClass: 'btn-info',
	    		  					confirm:function(){
	    		  						//location.href = '<s:url action="login" namespace="/function"/>';
	    		  					},
	    		  					animation: 'zoom',
	    	  					});
	    	    				
	    	    			}else{
	    	    				$.alert({
	    	  					  	title: '<s:text name="error"/>',
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
    });
	
});

/* 重設會員資料 */
function resetDataForm(){
	$.confirm({
		title: '<s:text name="editConfirm" />!',
		content: '確認重設會員資料!',
		confirmButton: '<s:text name="confirm"/>',
		confirmButtonClass: 'btn-info',
		cancelButton: '<s:text name="cancel"/>',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#dataForm").trigger('reset');
		}
	});
	
}

/* 重設密碼 */
function resetPasswordForm(){
	$.confirm({
		title: '<s:text name="editConfirm" />!',
		content: '確認重設密碼!',
		confirmButton: '<s:text name="confirm"/>',
		confirmButtonClass: 'btn-info',
		cancelButton: '<s:text name="cancel"/>',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#passwordForm").trigger('reset');
		}
	});
}

/* 送出會員資料(會觸發驗證) */
function modifyDataForm(){
	$("#dataForm").submit();
}

/* 送出密碼(會觸發驗證) */
function modifyPasswordForm(){
	$("#passwordForm").submit();
	
}
</script>