<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">Tracker Data</div>
	<!--search-->
	<div class="panel-body">
		<div class="date date1 fl" style="margin-right: 10px;">
			<s:select id="appId" class="form-control" style="height:30px;" list="appList" listKey='appId' listValue='appId+"_"+appName+"_"+appOs' headerKey="" headerValue="請選擇"></s:select>
		</div>
		<div>
			<div class="date date1 fl" id="from">
				<input type="text" class="date-check form-control" placeholder="Start Date" id="startDate" readonly="readonly">
			</div>
			<div class="date fr" id="to">
				<input type="text" class="date-check form-control" placeholder="End Date" id="endDate" readonly="readonly">
			</div>
			<div class="date date1 fl">
				<button class="btn btn-primary" type="button" onclick="search();">
					<i class="fa fa-search"></i>
				</button>
			</div>
			
			<s:if test="startDate != null && endDate != null && app != null && columns.size() > 0">
				<div class="date date1 fl" style="float:right;">
					<button class="btn btn-primary" type="button" onclick="exportCsv();">
						匯出
					</button>
				</div>
			</s:if>
			
			<div class="clearfix"></div>
		</div>

		<div class="clearfix"></div>
	</div>
	<!--search-->
	<div class="panel-body" style="margin-top: -20px;">
		<div>
			<div class="panel-body">
				<s:if test="columns != null">
					<table width="100%" class="table table-striped table-bordered table-hover" id="dataTable">
						<thead>
							<tr>
								<s:iterator value="columns" var="column" >
									<th><s:property value="column"/></th>
								</s:iterator>
							</tr>
						</thead>
					</table>
				</s:if>
			</div>
		</div>
	</div>
</div>

<form id="queryForm" method="post">
	<input type="hidden" name="app.appId" id="queryAppId">
	<input type="hidden" name="startDate" id="queryStartDate">
	<input type="hidden" name="endDate" id="queryEndDate">
</form>

<form id="downloadForm" method="post">
	<input type="hidden" name="app.appId" id="exAppId" value='<s:property value="app.appId"/>'>
	<input type="hidden" name="startDate" id="exStartDate" value='<s:property value="startDate"/>'>
	<input type="hidden" name="endDate" id="exEndDate" value='<s:property value="endDate"/>'>
</form>