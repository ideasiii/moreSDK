<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">RECENTLY VIEWED APPS</div>
	<s:if test="appDataPlusList != null && appDataPlusList.size() > 0">
		<div class="panel-body">
			<div class="table-responsive" style="border: 1px solid #CCC">
				<table class="table">
					<tbody>
						<s:iterator value="appDataPlusList" var="appDataPlus">
							<tr>
								<td class="table_w1">
									<div style="border-right: 1px solid #e3e3e3;">
										<div class="col-md-12 d_margin">
											<div class='circle' style="float: left">
												<div class='inner' style="background-image: url(<s:property value="contextPath+appIcon" />)"></div>
											</div>
											<div style="float: left; padding-top: 4px;">
												<s:if test='"ios".equals(appOs)'>
													<img src="<s:property value="contextPath"/>/images/sdk/ios_small.png">
												</s:if>
												<s:else>
													<img src="<s:property value="contextPath"/>/images/sdk/android_small.png">
												</s:else>
												<s:property value="appName"/>
											</div>
											<div class="clearfix"></div>
										</div>
										<s:if test='"Y".equals(isAdmin)'>
											<div class="col-md-12 d_margin">
												<div class="col-md-3 d_t" style="padding: 2px">權限</div>
												<div class="col-md-9" style="padding: 0px;">
													<input type="hidden" name="appId" value='<s:property value="appId"/>'>
													<select style="width: 100%;" name="permissionType">
														<option value="0" <s:if test="permissionType == 0">selected</s:if>>可使用sdk</option>
														<option value="1" <s:if test="permissionType == 1">selected</s:if>>可查詢tracker data</option>
														<option value="2" <s:if test="permissionType == 2">selected</s:if>>可使用tableau dashboard</option>
													</select>
												</div>
												<div class="clearfix"></div>
											</div>
										</s:if>
										<div class="col-md-12 d_margin">
											<div class="use_t">ACTIVE USERS RIGHT NOW</div>
											<div>
												<span class="btn-danger c_btn"><s:property value="countNow"/></span>
		
											</div>
		
										</div>
										<div class="clearfix"></div>
									</div>
								</td>
								<td class="table_w2">
									<div class="col-md-12 c_desc_div" style="margin-top: 4%;" >
										<div class="col-md-3 c_desc">
											<div>
												<span class="c_num_title"><s:property value="countNow"/></span>
											</div>
											<div><s:text name="appDashboard.currentActiveUsers"/></div>
										</div>
										<div class="col-md-3 c_desc">
											<div>
												<span class="c_num_title"><s:property value="countMonth"/></span>
												<span class='<s:if test="countMonthSign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
													<img src='<s:property value="contextPath"/><s:if test="countMonthSign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'><s:property value="countMonthGrowthRate"/>%
												</span>
											</div>
											<div><s:text name="appDashboard.monthlyUser"/></div>
										</div>
										<div class="col-md-3 c_desc">
											<div>
												<span class="c_num_title"><s:property value="countWeek"/></span>
												<span class='<s:if test="countWeekSign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
													<img src='<s:property value="contextPath"/><s:if test="countWeekSign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'><s:property value="countWeekGrowthRate"/>%
												</span>
											</div>
											<div><s:text name="appDashboard.weeklyUser"/></div>
										</div>
										<div class="col-md-3 c_desc">
											<div>
												<span class="c_num_title"><s:property value="countDay"/></span>
												<span class='<s:if test="countDaySign">c_num_red</s:if><s:else>c_num_grey</s:else>'>
													<img src='<s:property value="contextPath"/><s:if test="countDaySign">/images/top.jpg</s:if><s:else>/images/down.png</s:else>'><s:property value="countDayGrowthRate"/>%
												</span>
											</div>
											<div><s:text name="appDashboard.daylyUser"/></div>
										</div>
										<div class="clearfix"></div>
		
									</div>
								</td>
								<td class="table_w3">
									<div class="col-md-12 c_desc_div" style="margin-top: 40%;">
										<button type="button" class="btn btn-primary" onclick='appDashboardDetail("<s:property value="appId"/>")'><s:text name="appDashboard.detail"/></button>
									</div>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
		<s:if test='"Y".equals(isAdmin)'>
			<div class="panel-footer">
				<div align="right">
					<button type="button" class="btn btn-primary" onclick="modifyAppPermissionType();">確定</button>
				</div>
			</div>
		</s:if>
	</s:if>
</div>
<div id="modalDiv"></div>
<form method="post" id="pageForm">
	<input type="hidden" id="appId" name="app.appId">
</form>