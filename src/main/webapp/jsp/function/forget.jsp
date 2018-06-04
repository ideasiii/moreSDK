<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Forget Passsword</h3>
				</div>
				<div class="panel-body">
					<form role="form" id="dataForm">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="E-mail" id="email" name="email" type="email" autofocus>
							</div>
							<div class="form-group">
								填寫註冊帳號，系統會發送密碼至您的申請信箱，請務必登入修改您的密碼，以確保登入資訊安全。</div>
							<div class="checkbox">
								<div style="float: right">
									<a href='<s:url action="login" namespace="/function"/>'>返回登入</a>
								</div>
								<div class="clearfix"></div>
							</div>
							<!-- Change this to a button or input when using this as a form -->
							<div style="padding-top: 10px; padding-bottom: 10px;">
								<a href="#" class="btn btn-lg btn-success btn-block" onClick="doForget()">Submit</a>
							</div>
						</fieldset>
					</form>
				</div>

			</div>
			<div class="copy_txt"><a href='<s:url action="privacy" namespace="/function"/>'><s:text name="footer.privacyPolicy"/></a>&nbsp;&nbsp;&nbsp;&nbsp;Powered By iii MORE SDK © 2018</div>
		</div>
	</div>
</div>
<div id="modalDiv"></div>