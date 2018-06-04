<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading"><s:text name="member.information"/></div>
	<div class="panel-body">
		<form id="dataForm">
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.name"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="displayName" name="displayName" value='<s:property value="user.displayName"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.emailAccount"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="email" name="email" value='<s:property value="user.email"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.organization"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="company" name="company" value='<s:property value="user.company"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.phone"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="phone" name="phone" value='<s:property value="user.phone"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.purposeOfRegister"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="purpose" name="purpose" value='<s:property value="user.purpose"/>'>
				</div>
				<div class="clearfix"></div>
			</div>
			<%-- <div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt">權限</div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="accessLevel" name="accessLevel" value='<s:property value="user.accessLevel"/>'>
				</div>
				<div class="clearfix"></div>
			</div> --%>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.RegistrationTime"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" value='<s:date name="user.createTime" format="yyyy/MM/dd HH:mm:ss"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.lastLogon"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" value='<s:date name="user.lastAccessTime" format="yyyy/MM/dd HH:mm:ss"/>' disabled>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-12" align="right">
					<button type="button" class="btn btn-primary" onclick="modifyDataForm();"><s:text name="saveChanges"/></button>
					<button type="button" class="btn btn-warning" onclick="resetDataForm();"><s:text name="cancel"/></button>
				</div>
			</div>
		</form>
		<!-- /.row (nested) -->
	</div>

	<!-- /.panel-body -->
</div>


<div class="panel panel-default">
	<div class="panel-heading"><s:text name="member.changePassword"/></div>
	<div class="panel-body">
		<form id="passwordForm">
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.newPassword"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" type="password" placeholder="Password (Minimum 8 charcters allowed)" id="newPassword" name="newPassword">
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="member.retypeNewPassword"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" type="password" placeholder="Password again" id="confirmPassword" name="confirmPassword">
				</div>
				<div class="clearfix"></div>
			</div>
	
			<div class="down_line">
				<div class="col-md-12" align="right">
					<button type="button" class="btn btn-primary" onclick="modifyPasswordForm();"><s:text name="confirm"/></button>
					<button type="button" class="btn btn-warning" onclick="resetPasswordForm();"><s:text name="cancel"/></button>
				</div>
			</div>
		</form>
		<!-- /.row (nested) -->
	</div>

	<!-- /.panel-body -->
</div>
<div id="modalDiv"></div>