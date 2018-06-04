<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta   HTTP-EQUIV="Pragma"   CONTENT="no-cache">   
<meta   HTTP-EQUIV="Cache-Control"   CONTENT="no-cache">   
<meta   HTTP-EQUIV="Expires"   CONTENT="0">   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <tiles:insertAttribute name="headerScript"/>
 <tiles:insertAttribute name="bodyScript"/>
 <script type="text/javascript">
 $(function () {
	if (!!window.performance && window.performance.navigation.type === 2) {
		window.location.reload();
	}
 });
 </script>
</head>
<body>
	<div id="wrapper">
		<tiles:insertAttribute name="menu"/>
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><s:property value="functionName"/></h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                	<tiles:insertAttribute name="body"/>
                	<tiles:insertAttribute name="copyRight"/>
               </div>
             </div>
        </div>
    </div>
</body>
</html>