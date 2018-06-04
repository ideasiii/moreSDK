<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">已註冊使用者</div>
	<!-- /.panel-heading -->
	<div class="panel-body">
		<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">
			<thead>
				<tr>
					<th width="10%">使用者</th>
					<th width="10%">信箱</th>
					<th width="10%">單位</th>
					<th width="5%">權限</th>
					<th width="">建立時間</th>
					<th width="">最近上線</th>
					<th width="">最近IP</th>
					<th width="">管理</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="modalDiv"></div>
<form id="modifyForm">
	<input type="hidden" id="userId" name="userPlus.userId">
	<input type="hidden" id="memberId" name="userPlus.memberId">
</form>
