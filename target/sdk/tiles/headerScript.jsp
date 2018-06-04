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

<link href="<s:property value="contextPath"/>/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/css/sb-admin-2.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/vendor/morrisjs/morris.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<s:property value="contextPath"/>/css/jquery-confirm.css" rel="stylesheet" type="text/css"/>
<link href="<s:property value="contextPath"/>/vendor/demo/demo.min.css" rel="stylesheet" />
<link href="<s:property value="contextPath"/>/css/jquery-confirm.css" rel="stylesheet">
<%-- <link href="<s:property value="contextPath"/>/vendor/datatables/css/jquery.dataTables.min.css" rel="stylesheet"> --%>
<link href="<s:property value="contextPath"/>/vendor/datatables-plugins/dataTables.bootstrap.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/vendor/datatables-responsive/dataTables.responsive.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/css/lanrenzhijia.css" rel="stylesheet">
<link href="<s:property value="contextPath"/>/vendor/ddsmoothmenu/ddsmoothmenu.css" rel="stylesheet"/>
<link href="<s:property value="contextPath"/>/vendor/ddsmoothmenu/ddsmoothmenu-v.css"rel="stylesheet" />

<script src="<s:property value="contextPath"/>/vendor/assets/js/jquery-2.2.4.min.js" type="text/javascript"></script>
<script src="<s:property value="contextPath"/>/vendor/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="<s:property value="contextPath"/>/vendor/metisMenu/metisMenu.min.js"></script>
<script src="<s:property value="contextPath"/>/vendor/raphael/raphael.min.js"></script>
<%-- <script src="<s:property value="contextPath"/>/vendor/morrisjs/morris.min.js"></script>
<script src="<s:property value="contextPath"/>/js/morris-data.js"></script> --%>
<script src="<s:property value="contextPath"/>/js/sb-admin-2.js"></script>
<script src="<s:property value="contextPath"/>/vendor/demo/libs/pretty.js"></script>
<script src="<s:property value="contextPath"/>/vendor/assets/js/jquery.validate.min.js"></script>
<script src="<s:property value="contextPath"/>/js/jquery-confirm.js"></script>
<script src="<s:property value="contextPath"/>/vendor/datatables/js/jquery.dataTables.min.js"></script>
<script src="<s:property value="contextPath"/>/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="<s:property value="contextPath"/>/vendor/datatables-responsive/dataTables.responsive.js"></script>
<script src="<s:property value="contextPath"/>/js/jquery.form.min.js"></script>
<script src="<s:property value="contextPath"/>/js/lanrenzhijia.js"></script>
<script src="<s:property value="contextPath"/>/vendor/ddsmoothmenu/ddsmoothmenu.js"></script>
<script type="text/javascript">
$(function(){
	$.fn.dataTable.ext.errMode = 'none';
	jQuery.validator.addMethod("matches", function(phone_number, element, params) {
	    phone_number = phone_number.replace(/\s+/g, "");
	    return  eval(params).test(phone_number);
	}, "Please specify a valid phone number");
});
</script>
