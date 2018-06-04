<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:property value="contextPath"/>/css/double-date.css" rel="stylesheet" />
<script src='<s:property value="contextPath"/>/js/double-date.js'></script>
<script>
var dataTable;

$(function() {
	$("#appId").val('<s:property value="app.appId"/>');
	
	
	
	var now = new Date;
	$("#startDate").val(now.getFullYear()+"-"+(""+(now.getMonth()+1)).replace(/^(\d)$/, '0$1')+"-"+(""+now.getDate()).replace(/^(\d)$/, '0$1'));
	$("#endDate").val(now.getFullYear()+"-"+(""+(now.getMonth()+1)).replace(/^(\d)$/, '0$1')+"-"+(""+now.getDate()).replace(/^(\d)$/, '0$1'));
	
	
	<s:if test="startDate != null">
		$("#startDate").val('<s:property value="startDate"/>');
	</s:if>
	
	<s:if test="endDate != null">
		$("#endDate").val('<s:property value="endDate"/>');
	</s:if>
	
	<s:if test="startDate != null && endDate != null && app != null">
		dataTable = $('#dataTable').DataTable(getOption());
	</s:if>
	
});

function search() {
	if($("#appId").val() == ''){
		$.alert({
			title: '注意',
			content: "請先選擇App",
			confirmButton: 'OK',
			confirmButtonClass: 'btn-info',
			animation: 'zoom'
								
		});
		return false;
	}
	if($("#from,#to").hasClass("date-error")){
		return false;
	}
	$("#queryAppId").val($("#appId").val());
	$("#queryStartDate").val($("#startDate").val());
	$("#queryEndDate").val($("#endDate").val());
	$("#queryForm").attr("action",'<s:url action="trackerData" namespace="/function"/>')
	$("#queryForm").submit();
	
}

function exportCsv(){
	$("#downloadForm").attr("action",'<s:url action="exportTrackerData" namespace="/function" />');
	$("#downloadForm").attr("target","_blank");
	$("#downloadForm").submit();
}

function getOption() {
	var option = {};
	option.scrollX = true;
	option.dom = '<"top"l>rt<"bottom"ip>',
	option.columns = [
		<s:iterator value="columns" var="column" status="i">
		{ 'data':'<s:property value="column"/>', 'name':'<s:property value="column"/>'}
		<s:if test="#i.index < columns.size()-1" >
			,
		</s:if>
		</s:iterator>
    ];
	var url = '<s:url action="getTrackerData" namespace="/function" />?userPlus.memberId=<s:property value="loginUser.memberId"/>';
	url += '&startDate=<s:property value="startDate"/>&endDate=<s:property value="endDate"/>';
	url += '&app.appId=<s:property value="app.appId"/>';
	option.ajax = {
		"url" : url,
		"type" : "POST",
		"contentType" : "application/json",
		"dataType" : "json"
	};
	return option;
}

</script>