<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var dataTable;
$(function() {
	var option = {};
	option.columns = [
		{ 'data':'appId', 'name':'appId', 'render':function(data, type, full, meta) {
			return getAppIdHtml(full.appId);
	    	}
		},
        { 'data':'appName', 'name':'appName'},
        { 'targets':3, 'searchable':false, 'render':function(data, type, full, meta) {
				return getOsImage(full.appOs);
        	}
    	},
    	 { 'targets':4, 'searchable':false, 'render':function(data, type, full, meta) {
				return getIcon(full.appIcon);
     	}
 		},
        { 'targets':5, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
				return getFunctionHtml(full.appId);
	        }
        }
    ];
	
	option.ajax = {
		"url" : '<s:url action="getAppList" namespace="/function" />',
		"type" : "POST",
		"contentType" : "application/json",
		"dataType" : "json"
	};
	option.drawCallback = function(settings) {
		f_tcalAddOnload(f_tcalInit());
	};
	dataTable = $('#dataTable').DataTable(option);
});

function showSdkAuthDuration(appId){
	$("#modalDiv").modal({backdrop:'static',keyboard:false}); 
	$.ajax({
		type:'POST',
		url:'<s:url action="getSdkAuthDuration" namespace="/function"/>',
		data:{
			'app.appId' : appId
		},
		dataType:"json",
		success:function(json){
			if(json.isSuccess){
				var content = "";
				if(json.data != null && json.data.length > 0){
					content = "<table width='100%'>";
					content += "<tr>";
					content += "<td width='15%'>類型</td>";
					content += "<td width='45%'>名稱</td>";
					content += "<td width='20%'>授權起始時間</td>";
					content += "<td width='20%'>授權到期日</td>";
					content += "</tr>";
					for(var i=0 ; i < json.data.length ; i++){
						content += "<tr>";
						var type 
						content += "<td>"+getOsImage(json.data[i].sdkOs)+"</td>";
						content += "<td>"+json.data[i].sdkName+"</td>";
						content += "<td>"+json.data[i].startDate.split("T")[0]+"</td>";
						content += "<td>"+json.data[i].endDate.split("T")[0]+"</td>";
						content += "</tr>";
					}
					content += "</table>";
				}else{
					content = "目前尚無Sdk授權"
				}
				$.alert({
  					title: 'Sdk授權列表',
  					content: content,
  					confirmButton: 'OK',
  					confirmButtonClass: 'btn-info',
  					animation: 'zoom'
  										
					});
				dataTable.ajax.reload();
			}else{
				$.alert({
					  	title: '<s:text name="error"/>',
					  	content: json.message,
					  	confirmButton: 'OK',
					  	confirmButtonClass: 'btn-info',
					  	animation: 'zoom'
										
					});
			}
			$("#modalDiv").modal('hide');  
		}
	});
}

function getAppIdHtml(appId){
	var html = "";
	html+='<a href="javascript:;" onclick="showSdkAuthDuration(\''+appId+'\');">';
	html+=appId;
	html+='</a>';
	return html;
}

function getIcon(path){
	var html = "";
	html+='<div class="circle">';
	html+='<div class="inner" id="previewDiv" style=\'background-image:url(<s:property value="contextPath" />'+path+')\'></div>';
	html+='</div>';
	return html;
}

function getOsImage(appOs){
	var html = "";
	if("ios" == appOs){
		html+='<img src=\'<s:property value="contextPath"/>/images/sdk/ios_small.png\'/>'
	}else{
		html+='<img src=\'<s:property value="contextPath"/>/images/sdk/android_small.png\'/>'
	}
	return html;
}

function getFunctionHtml(appId){
	var html = "";
	html +='<button type="button" class="btn btn-primary btn-circle" title="<s:text name="edit"/>" onclick="appModify(\''+appId+'\')" style="margin-right:3px;">';
	html +='<i class="fa fa-gears"></i>';
	html +='</button>';
	html +='<button type="button" class="btn btn-danger btn-circle" title="<s:text name="delete"/>" onClick="deleteApp(\''+appId+'\')" style="margin-right:3px;">';
	html +='<i class="fa fa-times"></i>';
	html +='</button>';
	return html;
}


function appModify(userId, email){
	$("#modifyForm").find("#appId").val(userId);
	$("#modifyForm").attr("action",'<s:url action="appModify" namespace="/function"/>');
	$("#modifyForm").attr("method","POST");
	$("#modifyForm").submit();
}

function deleteApp(appId){
	$.confirm({
		title: '<s:text name="deleteConfirm"/>!',
		content: '<s:text name="deleteConfirm"/> App?刪除後無法復原!',
		confirmButton: '<s:text name="confirm"/>',
		confirmButtonClass: 'btn-info',
		cancelButton: '<s:text name="cancel"/>',
		cancelButtonClass: 'btn-danger',
		confirm: function(){
			$("#modalDiv").modal({backdrop:'static',keyboard:false});  
			$.ajax({
	    		type:'POST',
	    		url:'<s:url action="deleteApp" namespace="/function"/>',
	    		data:{
	    			'app.appId' : appId
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
	    				dataTable.ajax.reload();
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
</script>
