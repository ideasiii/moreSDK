<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
ddsmoothmenu.init({
	mainmenuid: "smoothmenu2", //Menu DIV id
	orientation: 'v', //Horizontal or vertical menu: Set to "h" or "v"
	classname: 'ddsmoothmenu-v', //class added to menu's outer DIV
	//customtheme: ["#804000", "#482400"],
	contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
});

function setLanguage (language){
	$.ajax({
		type:'POST',
		url:'<s:url action="setLanguage" namespace="/function"/>',
		data:{
			'language' : language,
		},
		dataType:"json",
		success:function(json){
			if(json.isSuccess){
				location.reload();
			}
		}
	})
}
</script>
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="<s:url action="index" namespace="/" />" style="padding: 0px;">
			<img src="<s:property value="contextPath"/>/images/sdk/more_logo1.png">
		</a>
	</div>
	<!-- /.navbar-header -->

	<ul class="nav navbar-top-links navbar-right">
		<!-- /.dropdown -->
		<li class="dropdown"><a class="dropdown-toggle"
			data-toggle="dropdown" href="#"><s:text name="navbar.language"/><i class="fa fa-caret-down"></i>
		</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="javascript:undefined" onclick="setLanguage('TW')">
						<div>繁中</div>
				</a></li>
				<li class="divider"></li>
				<li><a href="javascript:undefined" onclick="setLanguage('US')">
						<div>English</div>
				</a></li>

			</ul> <!-- /.dropdown-alerts --></li>
		<s:if test="loginUser == null">
			<li class="dropdown">
				<a class="dropdown-toggle" href='<s:url action="login" namespace="/function"/>'><s:text name="navbar.login"/> </a>
			</li>
			<li class="dropdown">
				<a class="dropdown-toggle" href='<s:url action="registered" namespace="/function"/>'><s:text name="navbar.register"/></a>
			</li>
		</s:if>
		
		<!-- /.dropdown -->
		<s:if test="loginUser != null">
			<li class="dropdown">
				<a class="dropdown-toggle" data-toggle="dropdown" href="#"> 
					<i class="fa fa-user fa-fw"></i>
					<i class="fa fa-caret-down"></i>
				</a>
				<ul class="dropdown-menu dropdown-user">
					<li>
						<a href='<s:url action="member" namespace="/function"/>'>
							<i class="fa fa-user fa-fw"></i>
							<s:text name="navbar.memberInfo"/>
						</a>
					</li>
					<li class="divider"></li>
					<li><a href='<s:url action="doLogout" namespace="/function"/>'><i class="fa fa-sign-out fa-fw"></i> <s:text name="navbar.logout"/></a></li>
				</ul> <!-- /.dropdown-user -->
			</li>
		</s:if>
		<!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="menu_pc">
				 <div id="smoothmenu2" class="ddsmoothmenu-v">
				<ul>
					<li class="sidebar-search"><s:text name="sidebar.menu" /></li>
					<li><a href="#"><i class="fa fa-globe fa-fw"></i>
							<s:text name="sidebar.apiSdk" /><span class="fa "></span></a>
						<ul class="nav nav-second-level">
							<li><a href='<s:property value="moreApiUrl"/>'>More API</a></li>
							<li><a href="#">More SDK<span class="fa "></span></a>
								<s:property value="menuHtml" escapeHtml="false" /> 
							</li>
						</ul> <!-- /.nav-second-level --></li>
					<li>
						<a href="<s:property value="moreTcUrl"/>">
							<i class="fa fa-list-alt fa-fw"></i>
							<s:text name="sidebar.tadkComposer" />
						</a>
					</li>
					<s:if test="loginUser.memberLevel == 1 || loginUser.memberLevel == 2 || loginUser.memberLevel == 8 || loginUser.memberLevel == 9">
						<li>
							<a href="#">
								<i class="fa fa-bar-chart-o fa-fw"></i>
								<s:text name="sidebar.dashboard" />
								<span class="fa "></span>
							</a>
							<ul class="nav nav-second-level">
								<li><a href="<s:url action="trackerData" namespace="/function"/>"><s:text name="sidebar.trackerData" /></a></li>
								<li><a href='<s:url action="analysis" namespace="/function"/>'><s:text name="sidebar.analysis" /></a></li>
							</ul>
						</li>
					</s:if>
					
	
					<s:if test="loginUser.memberLevel == 1 || loginUser.memberLevel == 2 || loginUser.memberLevel == 8 || loginUser.memberLevel == 9">
						<li>
							<a href="#">
								<i class="fa fa-user fa-fw"></i> 
								<s:text name="sidebar.membersArea" />
								<span class="fa "></span>
							</a>
							<ul class="nav nav-second-level">
								<li><a href='<s:url action="member" namespace="/function"/>'><s:text name="sidebar.memberInfo" /></a></li>
								<li><a href='<s:url action="appRegistered" namespace="/function"/>'><s:text name="sidebar.appIdRegistration" /></a></li>
								<li><a href='<s:url action="appListApplication" namespace="/function"/>'><s:text name="sidebar.appList" /></a></li>
								<li><a href='<s:url action="appDashboard" namespace="/function"/>'><s:text name="sidebar.appDashboard" /></a></li>
							</ul>
						</li>
					</s:if>
					<s:if test="loginUser.memberLevel == 9">
						<li>
							<a href="#">
								<i class="fa fa-users fa-fw"></i> 
								<s:text name="sidebar.management" />
								<span class="fa "></span>
							</a>
							<ul class="nav nav-second-level">
								<li><a href='<s:url action="user" namespace="/function"/>'><s:text name="sidebar.registeredUsers" /></a></li>
								<li><a href='<s:url action="userAudit" namespace="/function"/>'><s:text name="sidebar.unanthorizedUsers" /></a></li>
								<li><a href='<s:url action="userDel" namespace="/function"/>'><s:text name="sidebar.blackList" /></a></li>
							</ul>
						</li>
					</s:if>
					<s:if test="loginUser.memberLevel == 9">
						<li><a href='<s:url action="log" namespace="/function"/>'><s:text name="sidebar.userLogs" /></a></li>
					</s:if>
					<li>
					</li>
				</ul>
			</div>
		</div>
		<!-- /.sidebar-collapse -->
	</div>
	<!-- /.navbar-static-side -->
</nav>