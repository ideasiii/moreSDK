<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="panel panel-default">
	<div class="panel-heading">
		<a href="<s:url action="appDashboard" namespace="/function"/>">
			<button type="button" class="btn btn-primary"><s:text name="return"/></button>
		</a>
	</div>
	<div class="panel-body">
		<form id="searchForm" >
			<div>
				<div class="date date1 fl" id="from">
					<input type="text" class="date-check form-control" placeholder="start Date" name="startDate" id="startDate" value='<s:property value="startDate"/>' readonly="readonly">
					
				</div>
				<div class="date date1 fl" id="to">
					<input type="text" class="date-check form-control" placeholder="end Date" name="endDate" id="endDate" value='<s:property value="endDate"/>' readonly="readonly">
					
				</div>
				<div class="date date1 fl">
					<input type="hidden" name="app.appId" id="appId">
					<a href="javascript:;" onclick='searchDate("<s:url action="appDashboardDetailCross" namespace="/function"/>");'>
					<button class="btn btn-primary" type="button" >
						<i class="fa fa-search"></i>
					</button>
					</a>
				</div>
				<div class="clearfix"></div>
			</div>
		</form>
		

		<div class="col-md-12 d_shadow" style="margin-top: 10px;">
			<div class="d_margin "
				style="padding-top: 10px; padding-bottom: 10px;">
				<div class='circle' style="float: left">
					<div class='inner'
						style="background-image: url(<s:property value="contextPath+appDataPlus.appIcon" />)"></div>
				</div>
				<div style="float: left; padding-top: 4px;">
					<s:if test='"ios".equals(appDataPlus.appOs)'>
						<img
							src="<s:property value="contextPath"/>/images/sdk/ios_small.png">
					</s:if>
					<s:else>
						<img
							src="<s:property value="contextPath"/>/images/sdk/android_small.png">
					</s:else>
					<s:property value="appDataPlus.appName" />
				</div>
				<div class="clearfix"></div>

			</div>


			<div
				style="padding-top: 10px; padding-bottom: 10px; border-top: 2px solid #CCC">
				<div class="col-md-3 col-sm-6">
					<div>
						<span class="c_num_title"><s:property value="appDataPlus.countNow" /></span> 
					</div>
					<div><s:text name="appDashboard.currentActiveUsers"/></div>
				</div>
				<div class="col-md-3 col-sm-6">
					<div>
						<span class="c_num_title"><s:property value="appDataPlus.countMonth" /></span> 
						<span class='<s:if test="appDataPlus.countMonthSign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
							<img src='<s:property value="contextPath"/><s:if test="appDataPlus.countMonthSign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'>
							<s:property value="appDataPlus.countMonthGrowthRate" />%
						</span>
					</div>
					<div>月使用人數</div>
				</div>
				<div class="col-md-3 col-sm-6">
					<div>
						<span class="c_num_title"><s:property value="appDataPlus.countWeek" /></span> 
						<span class='<s:if test="appDataPlus.countWeekSign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
							<img src='<s:property value="contextPath"/><s:if test="appDataPlus.countWeekSign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'>
							<s:property value="appDataPlus.countWeekGrowthRate" />%
						</span>
					</div>
					<div>週使用人數</div>
				</div>
				<div class="col-md-3 col-sm-6">
					<div>
						<span class="c_num_title"><s:property value="appDataPlus.countDay" /></span> 
						<span class='<s:if test="appDataPlus.countDaySign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
							<img src='<s:property value="contextPath"/><s:if test="appDataPlus.countDaySign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'>
							<s:property value="appDataPlus.countDayGrowthRate" />%
						</span>
					</div>
					<div>日使用人數</div>
				</div>
				<div class="clearfix"></div>

			</div>
			<div class="panel-body">
				<!-- Nav tabs -->
				<ul class="nav nav-tabs">
					<li><a href="javascript:;" onclick='changeTab("<s:url action="appDashboardDetail" namespace="/function"/>")' >APP當前分析</a></li>
					<li class="active"><a href='javascript:;' data-toggle="tab">APP交叉分析</a></li>
					<li><a href="javascript:;" onclick='changeTab("<s:url action="appDashboardDetailPrefer" namespace="/function"/>")'>APP偏好分析</a></li>

				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div class="tab-pane fade" id="home">
						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App目前線上使用人數</h5>
											<h3><s:property value="appDataPlus.countNow" /></h3>
	
										</div>
										<div class="col-md-12 col-lg-6">
											<span id="nowUserChart"></span>
										</div>
									</div>
								</div>
	
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App月使用人數</h5>
											<h3><s:property value="appDataPlus.countMonth" /></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="monthUserChart"></span>
										</div>
									</div>
	
								</div>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App週使用人數</h5>
											<h3><s:property value="appDataPlus.countWeek" /></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="weekUserChart"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App日使用人數</h5>
											<h3><s:property value="appDataPlus.countDay" /></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="dayUserChart"></span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="well" id="curveMonthChartWell">
									<h4>App 使用人數曲線</h4>
									<p>
										<span id="curveMonthChart"></span>
									</p>
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-sm-6">
								<div class="well">
									<h4>App 前一日 使用時段人數統計</h4>
									<p>
										<span id="preDayBarChart"></span>
									</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="well">
									<h4>App 月使用時段平均人數統計</h4>
									<p>
										<span id="monthBarChart"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="well">
									<h4>App 月使用者出沒位置</h4>
									<p>
										<span id="locationMonthPieChart"></span>
									</p>
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-sm-12">
								<div class="well">
									<h4>APP 使用者出沒位置</h4>
									<div style="width: 100%;height: 500px;" id="nowMap">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade in active" id="profile">
						<div class="row">
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App 總使用人數</h5>
											<h3><s:property value="appDataPlus.countTotal"/></h3>
	
										</div>
										<div class="col-md-12 col-lg-6">
											<span id="totalUserChart"></span>
										</div>
									</div>
								</div>
	
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App月使用人數</h5>
											<h3 id="diffMonthCnt"></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="diffMonthUserChart"></span>
										</div>
									</div>
	
								</div>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App週使用人數</h5>
											<h3 id="diffWeekCnt"></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="diffWeekUserChart"></span>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-md-6 col-lg-3">
								<div class="well">
									<div class="row">
										<div class="col-md-12 col-lg-6">
	
											<h5>App日使用人數</h5>
											<h3 id="diffDayCnt"></h3>
										</div>
	
										<div class="col-md-12 col-lg-6">
											<span id="diffDayUserChart"></span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="well" id="diffCurveMonthChartWell">
									<h4>App 使用人數曲線</h4>
									<p>
										<span id="diffCurveMonthChart"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="well">
									<h4>App 日使用時段人數交叉分析</h4>
									<p>
										<span id="diffPreDayBarChart"></span>
									</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="well">
									<h4>App 月使用時段日均人數交叉分析</h4>
									<p>
										<span id="diffMonthBarChart"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="well">
									<h4>APP <s:property value='startDate.split("-")[1]'/>月使用者出沒位置</h4>
									<p>
										<span id="preLocationMonthPieChart"></span>
									</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="well">
									<h4>APP <s:property value='endDate.split("-")[1]'/>月使用者出沒位置</h4>
									<p>
										<span id="thisLocationMonthPieChart"></span>
									</p>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<div class="well">
									<h4>App 月使用者出沒位置</h4>
									<div style="width: 100%;height: 500px;" id="diffMap">
									</div>
								</div>
								<div style="text-align: center;">
									<div style="display: inline;">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/morning.png);"><s:property value='endDate.split("-")[1]'/>月 早</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/noon.png);"><s:property value='endDate.split("-")[1]'/>月 中</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/night.png);"><s:property value='endDate.split("-")[1]'/>月 晚</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/mid.png); "><s:property value='endDate.split("-")[1]'/>月 半夜</li>
										</ul>
									</div>
									<div style="display: inline;">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/morningPre.png);"><s:property value='startDate.split("-")[1]'/>月 早</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/noonPre.png);"><s:property value='startDate.split("-")[1]'/>月 中</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/nightPre.png);"><s:property value='startDate.split("-")[1]'/>月 晚</li>
										</ul>
									</div>
									<div style="display: inline">
										<ul style="display: inline-block;">
											<li style="list-style: url(<s:property value="contextPath"/>/images/map/midPre.png); "><s:property value='startDate.split("-")[1]'/>月 半夜</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="messages">
						<div class="row">
							<div class="col-sm-6">
								<div class="well">
									<h4>App 整體使用者出沒位置偏好類別比例</h4>
									<p>
										<span id="categoryPieChart"></span>
									</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="well">
									<h4>App 週使用者出沒位置偏好比例</h4>
									<p>
										<span id="categoryWeekPieChart"></span>
									</p>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="well">
									<h4>App 月使用者出沒位置偏好比例</h4>
									<p>
										<span id="categoryMonthPieChart"></span>
									</p>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="well">
									<h4>App 月使用者出沒位置偏好比例交叉分析</h4>
									<p>
										<span id="categoryDiffMonthPieChart"></span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="modalDiv"></div>
<form method="post" id="pageForm">
	<input type="hidden" id="appId" name="app.appId" value='<s:property value="app.appId"/>' >
</form>