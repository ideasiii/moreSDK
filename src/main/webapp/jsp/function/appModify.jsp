<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="panel panel-default">
	<div class="panel-heading">
		<a href="javascript:window.history.go(-1);"><button type="button" class="btn btn-primary"><s:text name="return"/></button></a>
	</div>
	<div class="panel-body">
		<form id="dataForm">
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.appName"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="appName" name="app.appName" value='<s:property value="app.appName" />'>
					<input class="form-control" type="hidden" id="appId" name="app.appId" value='<s:property value="app.appId" />'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.osType"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<label class="radio-inline"> 
						<input type="radio" name="app.appOs" value="ios" <s:if test='"ios".equals(app.appOs)'>checked</s:if>> 
						<img src='<s:property value="contextPath"/>/images/sdk/ios_small.jpg' width="32" height="32">
					</label> 
					<label class="radio-inline"> 
						<input type="radio" name="app.appOs" value="android" <s:if test='"android".equals(app.appOs)'>checked</s:if>> 
						<img src='<s:property value="contextPath"/>/images/sdk/android_small.jpg' width="32" height="32">
					</label>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.categories"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<select class="form-control" id="appCategory" name="app.appCategory">
						<option value="工具">工具</option>
						<option value="天氣">天氣</option>
						<option value="生活品味">生活品味</option>
						<option value="生產應用">生產應用</option>
						<option value="交通運輸">交通運輸</option>
						<option value="社交">社交</option>
						<option value="音樂與音效">音樂與音效</option>
						<option value="個人化">個人化</option>
						<option value="娛樂">娛樂</option>
						<option value="旅遊與地方資訊">旅遊與地方資訊</option>
						<option value="財經">財經</option>
						<option value="健康塑身">健康塑身</option>
						<option value="動態桌布">動態桌布</option>
						<option value="商業">商業</option>
						<option value="教育">教育</option>
						<option value="通訊">通訊</option>
						<option value="媒體與影片">媒體與影片</option>
						<option value="程式庫與試用程式">程式庫與試用程式</option>
						<option value="新聞與雜誌">新聞與雜誌</option>
						<option value="運動">運動</option>
						<option value="圖書與參考資源">圖書與參考資源</option>
						<option value="漫畫">漫畫</option>
						<option value="購物">購物</option>
						<option value="醫療">醫療</option>
						<option value="攝影">攝影</option>
						<option value="遊戲">遊戲</option>
						<option value="家庭">家庭</option>
					</select>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="col-md-12 table_line"></div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.organization"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="userName" name="app.userName" value='<s:property value="app.userName" />'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.supportEmail"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="userEmail" name="app.userEmail" value='<s:property value="app.userEmail" />'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.supportPhone"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<input class="form-control" placeholder="Enter text" id="userPhone" name="app.userPhone" value='<s:property value="app.userPhone" />'>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="down_line">
				<div class="col-md-2 col-sm-2">
					<div class="subject_txt"><s:text name="appRegistered.appIcon"/></div>
				</div>
				<div class="col-md-10  col-sm-10">
					<div style="float: left">
						<div class='circle'>
							<div class='inner' id="previewDiv" style='background-image:url(<s:property value="contextPath+app.appIcon" />)'></div>
						</div>
					</div>
					<div style="float: left;">
						<div style="margin-top: 10px; margin-left: 10px;">
							<input type="file" id="appIcon" name="appIcon" onchange="imagePreview(this);">
							<input type="hidden" id="defaultImage" name="defaultImage">
						</div>
					</div>
					<div style="float: left;">
						<button type="button" class="btn btn-primary" onclick="defalutImage();"><s:text name="appListApplication.removeIcon"/></button>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
	
			<div class="down_line">
				<div class="col-md-12" align="right">
					<button type="button" class="btn btn-primary" onclick="modifyDataForm();"><s:text name="saveChanges"/></button>
					<button type="button" class="btn btn-warning" onclick="resetDataForm();"><s:text name="resetForm"/></button>
				</div>
	
			</div>
		</form>
	</div>
</div>
<div id="modalDiv"></div>