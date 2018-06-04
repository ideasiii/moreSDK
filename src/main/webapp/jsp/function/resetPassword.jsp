<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Reset Passsword</h3>
				</div>
				<div class="panel-body">
					<form role="form" id="dataForm">
						<fieldset>
							<div class="form-group">
								<input class="form-control" type="password" placeholder="New Password (Minimum 8 charcters allowed)" id="newPassword" name="newPassword">
							</div>
							<div class="form-group">
								<input class="form-control" type="password" placeholder="Confirm Password" id="confirmPassword" name="confirmPassword">
							</div>
							<div class="form-group" align="center">
								<div class="g-recaptcha" data-sitekey="6Le380YUAAAAALhDbgYnN4dQk3DgN2mBM6J-n03F"></div>
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<div style="padding-top: 10px; padding-bottom: 10px;">
								<a href="#" class="btn btn-lg btn-success btn-block" onClick="doResetPassword()">Submit</a>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
			<div class="copy_txt">Powered By iii MORE SDK © 2018</div>
		</div>
	</div>
</div>
<div id="modalDiv"></div>