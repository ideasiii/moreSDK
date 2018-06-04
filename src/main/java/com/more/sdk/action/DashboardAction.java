package com.more.sdk.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.App;
import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;
import com.more.sdk.service.AppService;
import com.more.sdk.util.CsvUtils;
import com.opensymphony.xwork2.ActionContext;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value="authStack")
public class DashboardAction extends RootAction{
	private static final long serialVersionUID = 6194273987564471064L;
	@Resource(name="appService")
	private AppService appService;
	private List<App> appList;
	private List<String> columns;
	private App app;
	private String startDate;
	private String endDate;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private InputStream inputStream;
	/**
	 * Dashboard頁面
	 * @return
	 */
	@Action(value = "dashboard", results = { @Result(name = "success", type = "tiles", location = "function.dashboard") })
	public String dashboard() {
		setFunctionName(getText("sidebar.dashboard"));
		return SUCCESS;
	}
	
	/**
	 * 分析頁面
	 * @return
	 */
	@Action(value = "trackerData", results = { @Result(name = "success", type = "tiles", location = "function.tracker.data") })
	public String trackerData() {
		setFunctionName(getText("sidebar.trackerData"));
		if(app != null){
			columns = appService.getTrackerColumn(app.getAppId());
		}
		appList = appService.getAppList(loginUser.getMemberId());
		return SUCCESS;
	}
	
	@Action(value = "getTrackerData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getTrackerData(){
		try {
			Date startDate = null;
			Date endDate = null;
			if(this.startDate != null){
				startDate = dateFormat.parse(this.startDate);
			}
			if(this.endDate != null){
				endDate = dateFormat.parse(this.endDate);
			}
			if(app == null){
				app = new App();
			}
			List<Object> trackerData = appService.getTrackerData(app.getAppId(), startDate, endDate, true);
			returnData.setData(trackerData);
		} catch (ParseException e) {
			throw new RuntimeException("參數輸入有誤");
		}
		
		return SUCCESS;
	}
	
	@Action(value = "exportTrackerData", results = { @Result(name = "success", type = "stream", params = {"contentType", "application/octet-stream", "inputName", "inputStream", "contentDisposition","attachment;filename=export.csv","bufferSize","1024"}  )})
	public String exportTrackerData() {
		try {
			Date startDate = null;
			Date endDate = null;
			if(this.startDate != null){
				startDate = dateFormat.parse(this.startDate);
			}
			if(this.endDate != null){
				endDate = dateFormat.parse(this.endDate);
			}
			if(app == null){
				app = new App();
			}
			List<Object> trackerData = appService.getTrackerData(app.getAppId(), startDate, endDate, false);
			
			inputStream = new ByteArrayInputStream(CsvUtils.exportCSV(appService.getTrackerColumn(app.getAppId()), trackerData));
		} catch (ParseException e) {
			throw new RuntimeException("參數輸入有誤");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 分析頁面
	 * @return
	 */
	@Action(value = "analysis", results = { @Result(name = "success", type = "tiles", location = "function.analysis") })
	public String analysis() {
		setFunctionName(getText("sidebar.analysis"));
		return SUCCESS;
	}
	
	@Action(value = "personal", results = { @Result(name = "success", location = "/jsp/function/personal.jsp") })
	public String personal() {
		return SUCCESS;
	}
	
	@Action(value = "privacy", results = { @Result(name = "success", type = "tiles", location = "function.privacy") })
	public String privacy() {
		setFunctionName(getText("footer.privacyPolicy"));
		return SUCCESS;
	}
	
	@Action(value="setLanguage", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String setLanguage(){
		
		if("US".equals(getLanguage())){
			ActionContext.getContext().setLocale(new Locale("en", "US"));
		}else if("TW".equals(getLanguage())){
			ActionContext.getContext().setLocale(new Locale("zh", "TW"));
		}else{
			ActionContext.getContext().setLocale(new Locale("en", "US"));
		}
		
		request.getSession().setAttribute("language", getLanguage());
		returnData.setIsSuccess(true);
		return SUCCESS;
	}

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
