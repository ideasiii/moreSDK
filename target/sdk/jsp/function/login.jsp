<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="container">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Login</h3>
				</div>
				<div class="panel-body">
					<form id="dataForm">
						<div class="form-group">
							<input class="form-control" placeholder="E-mail" id="email" name="email">
						</div>
						<div class="form-group">
							<input class="form-control" type="password" placeholder="Password" id="password" name="password">
						</div>
						<div class="checkbox">
							<div style="float: left">
								<label>
									<input type="checkbox" id="rememberMe" name="rememberMe" value="Y">Remember Me
								</label>
							</div>
							<div style="float: right">
								<a href='<s:url action="forget" namespace="/function"/>'>Forget Passsword</a>
							</div>
							<div class="clearfix"></div>
						</div>
						<!-- Change this to a button or input when using this as a form -->
						<div style="padding-top: 10px; padding-bottom: 10px;">
							<a href="javascript:;" class="btn btn-lg btn-success btn-block" onclick="doLogin();">Login</a>
						</div>
						
						<div class="col-md-6" style="float: left; padding-left: 0px; padding-right: 5px;">
							<a href='<s:url action="dashboard" namespace="/function" />' class="btn btn-lg btn-success btn-block" style="background-color:#09C">Home</a>
						</div>
						<div class="col-md-6"  style="padding-left: 5px; padding-right: 0px;">
							<a href='<s:url action="registered" namespace="/function" />' class="btn btn-lg btn-success btn-block" style="background-color:#09C">Register</a>
						</div>
					</form>
				</div>
			</div>
			<div class="copy_txt">Powered By iii MORE SDK © 2018</div>
		</div>
	</div>
</div>
<div id="modalDiv"></div>