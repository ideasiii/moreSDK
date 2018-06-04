<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
var dataTable;


function appIdChange(obj){
	if(dataTable != null){
		dataTable.rows().remove().draw();
		dataTable.destroy();
		dataTable = null;
	}
	if($(obj).val().length > 0){
		var option = {};
		option.destroy = true;
		option.lengthMenu=[
	        [ 20, 50, 100],
	        [ '20', '50', '100']
	    ]
		option.columns = [
			{ 'data':'appId', 'name':'appId'},
	        { 'data':'appName', 'name':'appName'},
	        { 'data':'sdkName', 'name':'sdkName'},
	        { 'targets':4, 'searchable':false, 'render':function(data, type, full, meta) {
					return getOsImage(full.sdkOs);
		        }
		    },
	        { 'data':'permissionDescription', 'name':'permissionDescription'},
	        { 'targets':6, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
	        	return getStartDateFunctionHtml(full.startDate,full.appId,full.sdkId);
		        }
	        },
	        { 'targets':7, 'searchable':false, 'orderable':false, 'render':function(data, type, full, meta) {
					return getFunctionHtml(full.startDate,full.endDate,full.appId,full.sdkId);
		        }
	        }
	    ];
		
		option.ajax = {
			"url" : '<s:url action="getUserSdkList" namespace="/function" ><s:param name="userPlus.memberId" value="userPlus.memberId"></s:param></s:url>&sdkAuthDuration.appId='+$(obj).val(),
			"type" : "POST",
			"contentType" : "application/json",
			"dataType" : "json"
		};
		option.drawCallback = function(settings) {
			f_tcalAddOnload(f_tcalInit());
		};
		dataTable = $('#dataTable').DataTable(option);
	}
	
}

function getOsImage(sdkOs){
	var html = "";
	if("ios" == sdkOs){
		html+='<img src=\'<s:property value="contextPath"/>/images/sdk/ios_small.png\'/>'
	}else{
		html+='<img src=\'<s:property value="contextPath"/>/images/sdk/android_small.png\'/>'
	}
	return html;
}

function getFunctionHtml(startDate, endDate, appId, sdkId){
	var html = "";
	var tmpDate = ""; 
	if(endDate != null){
		tmpDate =  endDate.split("T")[0];
	}
	
	html +=' <input type="text" id="endDate" name="endDate" class="tcal" value="'+tmpDate+'" onchange="dateConfirm(this,\''+appId+'\',\''+sdkId+'\')" readonly="readonly"/>';
	html +=' <input type="hidden" id="appId" name="appId" value="'+appId+'" />';
	html +=' <input type="hidden" id="sdkId" name="sdkId" value="'+sdkId+'" />';
	//html +=' <input type="hidden" id="startDate" name="startDate" value="'+startDate.split("T")[0]+'" />';
	return html;
}

function getStartDateFunctionHtml(startDate, appId, sdkId){
	var html = "";
	var tmpDate = ""; 
	if(startDate != null){
		tmpDate =  startDate.split("T")[0];
	}
	html +=' <input type="text" id="startDate" name="startDate" class="tcal" value="'+tmpDate+'" onchange="dateConfirm(this,\''+appId+'\',\''+sdkId+'\')" readonly="readonly"/>';
	//html +=' <input type="hidden" id="startDate" name="startDate" value="'+startDate.split("T")[0]+'" />';
	return html;
}

function dateConfirm(obj,appId,sdkId){
	var parentTr = $(obj).parents("tr");
	var parentTd = parentTr.find("#endDate").parents("td");
	var endDate = parentTr.find("#endDate").val();
	var startDate = parentTr.find("#startDate").val();
	$(parentTd).find("button").remove();
	$(parentTd).append('<button type="button" class="btn btn-success btn-circle" onClick="confirmDate(this,\''+appId+'\',\''+sdkId+'\',\''+endDate+'\',\''+startDate+'\')"><i class="fa fa-check"></i>');
}

function confirmDate(obj, appId, sdkId, endDate, startDate){
	if(startDate >= endDate){
		$.alert({
		  	title: '錯誤訊息',
		  	content: '授權時間有誤',
		  	confirmButton: 'OK',
		  	confirmButtonClass: 'btn-info',
		  	animation: 'zoom'
								
		});
	}else{
		$.confirm({
			title: '注意!',
			content: '確認修改授權時間!',
			confirmButton: 'OK',
			confirmButtonClass: 'btn-info',
			cancelButtonClass: 'btn-danger',
			confirm: function(){
				$("#modalDiv").modal({backdrop:'static',keyboard:false});  
				$.ajax({
		    		type:'POST',
		    		url:'<s:url action="modifyUserSdkDate" namespace="/function"/>',
		    		data:{
		    			'sdkAuthDuration.sdkId' : sdkId,
		    			'sdkAuthDuration.appId' : appId,
		    			'endDate' : endDate,
		    			'startDate' : startDate
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
		    				$(obj).remove();
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
</script>
