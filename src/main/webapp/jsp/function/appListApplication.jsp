<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading"><s:text name="appListApplication.yourApps"/></div>
	<div class="panel-body">
		<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">
			<thead>
				<tr>
					<th width="30%"><s:text name="appListApplication.appId"/></th>
					<th width="25%"><s:text name="appListApplication.appName"/></th>
					<th width="15%"><s:text name="appListApplication.osType"/></th>
					<th width="10%"><s:text name="appListApplication.appIcon"/></th>
					<th width=""><s:text name="option"/></th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="modalDiv"></div>
<form id="modifyForm">
	<input type="hidden" id="appId" name="app.appId">
</form>