<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

function downloadSdk(sdkId){
	$("#sdkId").val(sdkId);
	$("#type").val("sdk");
	$("#downloadForm").attr("action",'<s:url action="download" namespace="/function" />');
	$("#downloadForm").attr("target","_blank");
	$("#downloadForm").submit();
}

function downloadDoc(sdkId){
	$("#sdkId").val(sdkId);
	$("#type").val("doc");
	$("#downloadForm").attr("action",'<s:url action="download" namespace="/function" />');
	$("#downloadForm").attr("target","_blank");
	$("#downloadForm").submit();
}
</script>
