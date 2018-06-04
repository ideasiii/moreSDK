<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading"><s:property value="sdkGroupDefinition.category"/> SDK</div>
	<div class="panel-body">
		<div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<div class="panel panel-green">
					<div class="panel-heading">Agent SDK</div>
					<div class="panel-body">
						<p>IOS SDK 文字說明IOS SDK 文字說明IOS SDK 文字說明IOS SDK 文字說明IOS SDK
							文字說明IOS SDK 文字說明IOS SDK 文字說明IOS SDK 文字說明IOS SDK 文字說明IOS SDK
							文字說明IOS SDK 文字說明</p>
					</div>
					<div class="panel-footer">須先下載此SDK Agent</div>
				</div>
			</div>
			<!-- /.col--->
			<div class="clearfix"></div>
		</div>
		<!--start detail-->
		<s:iterator value="sdkList" status="i" var="sdk">
			<s:if test="i.index == 0 ||i.index % 4 == 0">
				<div>
			</s:if>
			<div class="col-lg-3 col-md-6">
				<div class='panel panel-<s:if test="sdkOs.equals(\"ios\")">green</s:if><s:else>primary</s:else>'>
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3">
								<s:if test="sdkOs.equals(\"ios\")">
									<img src="<s:property value="contextPath"/>/images/sdk/ios_m.png">
								</s:if>
								<s:else>
									<img src="<s:property value="contextPath"/>/images/sdk/android_m.png">
								</s:else>
							</div>
							<div class="col-xs-9 text-right">
								<div class="sdk_txt"><s:property value="sdkName"/></div>

							</div>
						</div>
					</div>
					
					<div class="panel-footer">
						<a href="javascript:;" onclick='downloadSdk("<s:property value="sdkId"/>");'>
							<span class="pull-left">download</span> 
						</a>
						<a href="javascript:;" onclick='downloadDoc("<s:property value="sdkId"/>");'>
							<span class="pull-right">document</span>
						</a>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<s:if test="i.index == sdkList.size() ||(i.index+1) % 4 == 0">
				</div>
			</s:if>
		</s:iterator>
	</div>
	<!-- /.panel-body -->
</div>

<form id="downloadForm" method="post">
	<input type="hidden" id="sdkId" name="sdkId">
	<input type="hidden" id="type" name="type">
</form>