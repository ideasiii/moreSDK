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
	    		title: '注意!',
	    		content: '確認修改用戶資料!',
	    		confirmButton: '確認',
	    		confirmButtonClass: 'btn-info',
	    		cancelButton: '取消',
	    		cancelButtonClass: 'btn-danger',
	    		confirm: function(){
	    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    	    	$.ajax({
	    	    		type:'POST',
	    	    		url:'<s:url action="modifyUser" namespace="/function"/>',
	    	    		data:{
	    	    			'userPlus.displayName' : $("#displayName").val(),
	    	    			'userPlus.company' : $("#company").val(),
	       					'userPlus.phone' : $("#phone").val(),
	    					'userPlus.purpose' : $("#purpose").val(),
	    					'userPlus.userId' : '<s:property value="userPlus.userId"/>',
	    					'userPlus.memberLevel' : $("#memberLevel").val()
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
    });
	
	
});

/* 重設會員資料 */
function resetDataForm(){
	$.confirm({
		title: '注意!',
		content: '確認重設會員資料!',
		confirmButton: '確認',
		confirmButtonClass: 'btn-info',
		cancelButton: '取消',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#dataForm").trigger('reset');
		}
	});
	
}

/* 送出會員資料(會觸發驗證) */
function modifyDataForm(){
	$("#dataForm").submit();
}

</script>