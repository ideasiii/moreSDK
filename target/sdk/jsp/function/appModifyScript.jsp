<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var defaultPhotoPath = '<s:property value="contextPath+app.appIcon" />';
var cancelFlag = "N";
$(function(){
	$("#appCategory").val('<s:property value="app.appCategory" />')
	/*  App資料驗證 */
	$("#dataForm").validate({
	    rules: {
	    	'app.appName': "required",
	    	'app.userName': "required",
	    	'app.userEmail': {
	    		required: true,
    	      	email: true
	    	},
	    	'app.userPhone': "required"
	    },
	    messages: {
	    	'app.appName' : "請輸入名稱",
	    	'app.userName' : "請輸入連絡者名稱",
	    	'app.userEmail' :  {
	    		required: "請輸入Support Email",
    	      	email: "Email格式錯誤"
	    	},
	    	'app.userPhone' : "請輸入Support Phone"
	    },
	    submitHandler: function(form){
	    	$.confirm({
	    		title: '<s:text name="editConfirm"/>!',
	    		content: '<s:text name="editConfirm"/> App!',
	    		confirmButton: '<s:text name="confirm"/>',
	    		confirmButtonClass: 'btn-info',
	    		cancelButton: '<s:text name="cancel"/>',
	    		cancelButtonClass: 'btn-danger',
	    		confirm: function(){
	    			$("#defaultImage").val(cancelFlag);
	    			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	    			$("#dataForm").ajaxSubmit({
   	    				url:'<s:url action="modifyApp" namespace="/function"/>',
   	    				type:'post',
   	    				dataType:"json",
   	    				success:function(data){
   	    	    			if(data.isSuccess){
   	    	    				$.alert({
   	    		  					title: '<s:text name="success"/>',
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

/* 圖片預覽 */
function imagePreview(obj){
	if (obj.files && obj.files[0]) {
		cancelFlag = "N";
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
		title: '<s:text name="resetForm"/>!',
		content: '<s:text name="resetConfirm"/>!',
		confirmButton: '<s:text name="confirm"/>',
		confirmButtonClass: 'btn-info',
		cancelButton: '<s:text name="cancel"/>',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#dataForm").trigger('reset');
			$("#previewDiv").attr("style","background-image:url("+defaultPhotoPath+")");
		}
	});
}

/* 送出App資料(會觸發驗證) */
function modifyDataForm(){
	$("#dataForm").submit();
}

function defalutImage(){
	cancelFlag = "Y";
	$("#appIcon").val("");
	$("#previewDiv").removeAttr("style");
}
</script>