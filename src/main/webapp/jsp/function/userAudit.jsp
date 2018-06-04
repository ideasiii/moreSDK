<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="panel panel-default">
	<div class="panel-heading">待審核使用者</div>

	<div class="panel-body">
		<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">
			<thead>
				<tr>
					<th width="15%">使用者</th>
					<th width="20%">信箱</th>
					<th width="5%">單位</th>
					<th width="10%">目的</th>
					<th>建立時間</th>
					<th>管理</th>
				</tr>
			</thead>
		</table>

	</div>
</div>
<div id="modalDiv"></div>
<form id="modifyForm">
	<input type="hidden" id="userId" name="userPlus.userId">
</form>
