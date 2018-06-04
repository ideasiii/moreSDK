<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
$(function(){
	/*  App資料驗證 */
	$("#dataForm").validate({
		rules: {
	    	'app.appName': "required",
	    	'app.userName': "required",
	    	'app.userEmail': {
	    		required: true,
    	      	email: true
	    	},
	    	'app.userPhone': {
 	    		required : true,
 	    		matches : "/^(([0][1-9]{1,3}[-][0-9]{6,8})|([0-9]{9,10}))$/"
 	    	}
	    },
	    messages: {
	    	'app.appName' : "請輸入名稱",
	    	'app.userName' : "請輸入連絡者名稱",
	    	'app.userEmail' :  {
	    		required: "請輸入Support Email",
    	      	email: "Email格式錯誤"
	    	},
	    	'app.userPhone' : {
	    		required : "請輸入Support Phone",
	    		matches : "電話格式錯誤"
	    	}
	    },
	    submitHandler: function(form){
	    	if(!$("#agreement").prop("checked")){
	    		$.alert({
				  	title: '錯誤訊息',
				  	content: '請勾選同意書',
				  	confirmButton: 'OK',
				  	confirmButtonClass: 'btn-info',
				  	animation: 'zoom'
				});
	    	}else{
	    		$.confirm({
		    		title: '<s:text name="appRegistered.registrationConfirm"/>!',
		    		content: '<s:text name="appRegistered.registrationConfirm"/> App!',
		    		confirmButton: '<s:text name="confirm"/>',
		    		confirmButtonClass: 'btn-info',
		    		cancelButton: '<s:text name="cancel"/>',
		    		cancelButtonClass: 'btn-danger',
		    		confirm: function(){
		    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
		    			$("#dataForm").ajaxSubmit({
	   	    				url:'<s:url action="registeredApp" namespace="/function"/>',
	   	    				type:'post',
	   	    				dataType:"json",
	   	    				success:function(data){
	   	    	    			if(data.isSuccess){
	   	    	    				$.alert({
	   	    		  					title: '成功訊息',
	   	    		  					content: data.message,
	   	    		  					confirmButton: 'OK',
	   	    		  					confirmButtonClass: 'btn-info',
	   	    		  					animation: 'zoom',
	   	    		  					close:function(){
	   	    		  						location.href='<s:url action="appListApplication" namespace="/function"/>';
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
	    	}
	    	
	    }
    });
});

/* 圖片預覽 */
function imagePreview(obj){
	if (obj.files && obj.files[0]) {
		var reader = new FileReader();

        reader.onload = function (e) {
        	$("#previewDiv").attr("style", "background-image:url("+e.target.result+")");
        }
        reader.readAsDataURL(obj.files[0]);
	}else{
		$("#previewDiv").removeAttr("style");
	}
}

/* 重設App資料 */
function resetDataForm(){
	$.confirm({
		title: '<s:text name="resetConfirm"/>!',
		content: '<s:text name="confirm"/>重設App資料!',
		confirmButton: '<s:text name="confirm"/>',
		confirmButtonClass: 'btn-info',
		cancelButton: '<s:text name="cancel"/>',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#dataForm").trigger('reset');
		}
	});
}

/* 送出App資料(會觸發驗證) */
function addDataForm(){
	$("#dataForm").submit();
}

function showAgreement(){
	$.ajax({
		type:'POST',
		url:'<s:url action="personal" namespace="/function"/>',
		dataType:"html",
		success:function(data){
			$("#agreement").removeAttr("disabled");
			$.alert({
			  	title: '<s:text name="appRegistered.agree.a"/>',
			  	content: data,
			  	confirmButton: 'OK',
			  	confirmButtonClass: 'btn-info',
			  	animation: 'zoom',
			  	columnClass: 'col-md-12 '
								
			});
		}
	});
	
}
</script>