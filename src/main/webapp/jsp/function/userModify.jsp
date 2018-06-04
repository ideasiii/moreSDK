<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<a href="javascript:window.history.go(-1);"><button type="button" class="btn btn-primary">返回上一頁</button></a>
	</div>
	<div class="panel-body">
		<form id="dataForm">
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">姓名</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="displayName" name="displayName" value='<s:property value="userPlus.displayName"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">信箱</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="email" name="email" value='<s:property value="userPlus.email"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">單位</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="company" name="company" value='<s:property value="userPlus.company"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">電話</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="phone" name="phone" value='<s:property value="userPlus.phone"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">目的</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="purpose" name="purpose" value='<s:property value="userPlus.purpose"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
               	<div class="subject_txt">權限</div>
               	</div>
               	<div class="col-md-10  col-sm-10">
               		<select class="form-control" id="memberLevel" name="memberLevel">
               			<s:iterator value="permissionDefinitionList" status="i" var="permissionDefinition">
               				<s:if test='("Y".equals(userPlus.verified) && memberLevel > 0) || !"Y".equals(userPlus.verified)'>
               					<option value='<s:property value="memberLevel"/>' <s:if test="userPlus.memberLevel == memberLevel">selected</s:if>><s:property value="permission"/></option>
               				</s:if>
               				
               			</s:iterator>
               		</select>
              	</div>
               	<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">建立時間</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" value='<s:date name="userPlus.createTime" format="yyyy/MM/dd HH:mm:ss"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">最近上線</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" value='<s:date name="userPlus.lastAccessTime" format="yyyy/MM/dd HH:mm:ss"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-12" align="right">
					<button type="button" class="btn btn-primary" onclick="modifyDataForm();">確定</button>
					<button type="button" class="btn btn-warning" onclick="resetDataForm();">重設</button>
				</div>
			</div>
		</form>
		<!-- /.row (nested) -->
	</div>

	<!-- /.panel-body -->
</div>
<div id="modalDiv"></div>