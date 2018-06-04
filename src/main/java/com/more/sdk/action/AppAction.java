package com.more.sdk.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.more.sdk.entity.App;
import com.more.sdk.entity.plus.AppDataPlus;
import com.more.sdk.service.AppService;

@Component
@Scope("prototype")
@Namespace("/function")
@ParentPackage(value = "super-default")
@InterceptorRef(value = "authStack")
public class AppAction extends RootAction {

	private static final long serialVersionUID = -2368389567220756001L;

	@Resource(name = "appService")
	private AppService appService;

	private App app;
	private File appIcon;
	private String defaultImage;
	private List<AppDataPlus> appDataPlusList;
	private AppDataPlus appDataPlus;
	private String startDate;
	private String endDate;
	private String type="";
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String timeInterval;
	private String weekMonth;
	private String area;
	private String category;
	private List<String> categoryList;

	/**
	 * App註冊頁面
	 * 
	 * @return
	 */
	@Action(value = "appRegistered", results = { @Result(name = "success", type = "tiles", location = "function.app.registered") })
	public String appRegistered() {
		setFunctionName(getText("sidebar.appIdRegistration"));
		return SUCCESS;
	}

	/**
	 * App註冊
	 * 
	 * @return
	 */

	@Action(value = "registeredApp", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String registeredApp() {
		try {
			if (StringUtils.isEmpty(app.getAppName()) || StringUtils.isEmpty(app.getAppCategory()) || StringUtils.isEmpty(app.getAppOs()) || StringUtils.isEmpty(app.getUserEmail()) || StringUtils.isEmpty(app.getUserName())
					|| StringUtils.isEmpty(app.getUserPhone())) {
				returnData.setMessage("輸入參數有誤");
			} else {
				app.setMemberId(loginUser.getMemberId());
				appService.registeredApp(app, appIcon, loginUser.getUserId());
				returnData.setIsSuccess(true);
				returnData.setMessage("App註冊成功");
			}

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * App列表頁面
	 * 
	 * @return
	 */
	@Action(value = "appListApplication", results = { @Result(name = "success", type = "tiles", location = "function.app.list.application") })
	public String appListApplication() {
		setFunctionName(getText("sidebar.appList"));
		return SUCCESS;
	}

	/**
	 * 取得App列表
	 * 
	 * @return
	 */
	@Action(value = "getAppList", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getAppList() {
		try {
			List<App> appList = appService.getAppList(loginUser.getMemberId());
			returnData.setData(appList);
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 編輯App頁面
	 * 
	 * @return
	 */
	@Action(value = "appModify", results = { @Result(name = "success", type = "tiles", location = "function.app.modify") })
	public String appModify() {
		setFunctionName(getText("sidebar.appList"));
		try {
			app = appService.getApp(app.getAppId(), loginUser.getMemberId());
		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * App編輯
	 * 
	 * @return
	 */

	@Action(value = "modifyApp", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String modifyApp() {
		try {
			if (StringUtils.isEmpty(app.getAppName()) || StringUtils.isEmpty(app.getAppCategory()) || StringUtils.isEmpty(app.getAppOs()) || StringUtils.isEmpty(app.getUserEmail()) || StringUtils.isEmpty(app.getUserName())
					|| StringUtils.isEmpty(app.getUserPhone())) {
				returnData.setMessage("輸入參數有誤");
			} else {
				appService.modifyApp(app, appIcon, loginUser.getUserId(), "Y".equals(defaultImage));
				returnData.setIsSuccess(true);
				returnData.setMessage("App已修改成功");
			}

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * App刪除
	 * 
	 * @return
	 */

	@Action(value = "deleteApp", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String deleteApp() {
		try {
			appService.deleteApp(app.getAppId(), loginUser.getMemberId());
			returnData.setIsSuccess(true);
			returnData.setMessage("App已刪除成功");

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * App Dashboard
	 * 
	 * @return
	 */
	@Action(value = "appDashboard", results = { @Result(name = "success", type = "tiles", location = "function.app.dashboard") })
	public String appDashboard() {
		setFunctionName(getText("sidebar.appDashboard"));
		appDataPlusList = appService.appDashboard(loginUser.getMemberId());
		return SUCCESS;
	}
	
	
	/**
	 * App Dashboard Detail
	 * 
	 * @return
	 */
	@Action(value = "appDashboardDetail", results = { @Result(name = "success", type = "tiles", location = "function.app.dashboard.detail") })
	public String appDashboardDetail() {
		Calendar calendar = Calendar.getInstance();
		if(StringUtils.isBlank(startDate)){
			startDate = dateFormat.format(calendar.getTime());
		}
		appDataPlus = appService.appDashboardDetail(null, app.getAppId(), calendar.getTime(), false);
		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getChartData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getChartData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getChartData(app.getAppId(), thisDate, preDate));

		} catch (RuntimeException e) {
			e.printStackTrace();
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getMapData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getMapData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getMapData(app.getAppId(), thisDate, preDate));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * App Dashboard Detail
	 * 
	 * @return
	 */
	@Action(value = "appDashboardDetailCross", results = { @Result(name = "success", type = "tiles", location = "function.app.dashboard.detail.cross") })
	public String appDashboardDetailCross() {
		Calendar calendar = Calendar.getInstance();
		appDataPlus = appService.appDashboardDetail(null, app.getAppId(), new Date(), false);
		
		if(StringUtils.isBlank(startDate)){
			endDate  = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			startDate = dateFormat.format(calendar.getTime());
		}
		
		
		
		type = "Cross";
		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getCrossChartData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getCrossChartData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getCrossChartData(app.getAppId(), thisDate, preDate));

		} catch (RuntimeException e) {
			e.printStackTrace();
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getCrossMapData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getCrossMapData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getCrossMapData(app.getAppId(), thisDate, preDate));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}
	
	
	/**
	 * App Dashboard Detail
	 * 
	 * @return
	 */
	@Action(value = "appDashboardDetailPrefer", results = { @Result(name = "success", type = "tiles", location = "function.app.dashboard.detail.prefer") })
	public String appDashboardDetailPrefer() {
		categoryList = appService.getCategoryList();
		
		Calendar calendar = Calendar.getInstance();
		appDataPlus = appService.appDashboardDetail(null, app.getAppId(), new Date(), false);
		
		if(StringUtils.isBlank(startDate)){
			endDate  = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			startDate = dateFormat.format(calendar.getTime());
		}
		
		type="Prefer";
		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getPreferChartData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getPreferChartData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getPreferChartData(app.getAppId(), thisDate, preDate));

		} catch (RuntimeException e) {
			e.printStackTrace();
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * AppDetail Chart
	 * 
	 * @return
	 */

	@Action(value = "getPreferMapData", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getPreferMapData() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getPreferMapData(app.getAppId(),category,timeInterval,weekMonth));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}
	
	
	@Action(value = "getCategoryPieChartByCondition", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getCategoryPieChartByCondition() {
		try {
//			Calendar calendar = Calendar.getInstance();
//			Date thisDate = calendar.getTime();
//			calendar.add(Calendar.MONTH, -1);
//			Date preDate = calendar.getTime();
//			
//			if (StringUtils.isNotBlank(startDate)) {
//				preDate = dateFormat.parse(startDate);
//			}
//			if (StringUtils.isNotBlank(endDate)) {
//				thisDate = dateFormat.parse(endDate);
//			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getCategoryPieChartByCondition(app.getAppId(), timeInterval, weekMonth));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}
	
	@Action(value = "getLocationCrossPieChart", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getLocationCrossPieChart() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date thisDate = calendar.getTime();
			calendar.add(Calendar.MONTH, -1);
			Date preDate = calendar.getTime();
			
			if (StringUtils.isNotBlank(startDate)) {
				preDate = dateFormat.parse(startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				thisDate = dateFormat.parse(endDate);
			}
			returnData.setIsSuccess(true);
			returnData.setData(appService.getLocationCrossPieChart(app.getAppId(), thisDate, preDate, area));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}
	@Action(value = "getSdkAuthDuration", results = { @Result(name = "success", type = "json", params = { "root", "returnData" }) })
	public String getSdkAuthDuration() {
		try {
			returnData.setIsSuccess(true);
			returnData.setData(appService.getSdkAuthDuration(app.getAppId()));

		} catch (RuntimeException e) {
			returnData.setMessage(e.getMessage());
		} catch (Exception e) {
			returnData.setMessage("系統發生異常，請洽管理者:" + e.getMessage());
		}

		return SUCCESS;
	}
		
	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public File getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(File appIcon) {
		this.appIcon = appIcon;
	}

	public String getDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImage = defaultImage;
	}

	public List<AppDataPlus> getAppDataPlusList() {
		return appDataPlusList;
	}

	public AppDataPlus getAppDataPlus() {
		return appDataPlus;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getWeekMonth() {
		return weekMonth;
	}

	public void setWeekMonth(String weekMonth) {
		this.weekMonth = weekMonth;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}


}
