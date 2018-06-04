<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<a href='<s:url action="user" namespace="/function" />'><button type="button" class="btn btn-primary">返回上一頁</button></a>
	</div>
	<!-- /.panel-heading -->
	<div class="panel-body">
		<div class="user_title">使用者:<s:property value="userPlus.displayName"/></div>
		<div class="down_line">
			<div class="col-md-1 col-sm-1" style="margin-bottom: 10px;">
            	<div >App</div>
            </div>
			<div class="col-md-10  col-sm-10" style="margin-bottom: 10px;">
				<s:select id="appId" class="form-control" style="height:30px;" list="appList" listKey='appId' listValue='appId+"_"+appName+"_"+appOs' headerKey="" headerValue="請選擇" onchange="appIdChange(this);"></s:select>
			</div>
		</div>
		<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">
			<thead>
				<tr>
					<th>APP ID</th>
					<th>APP名稱</th>
					<th>SDK</th>
					<th>TYPE</th>
					<th>權限</th>
					<th>授權起始時間</th>
					<th>授權到期日</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="modalDiv"></div>