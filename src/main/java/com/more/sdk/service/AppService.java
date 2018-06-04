package com.more.sdk.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.more.sdk.entity.App;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.model.Chart;
import com.more.sdk.entity.model.ChartData;
import com.more.sdk.entity.model.MapData;
import com.more.sdk.entity.plus.AppDataPlus;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;

public interface AppService {

	/**
	 * App 註冊
	 * @param app
	 * @param appIcon 
	 * @param updater
	 */
	public void registeredApp(App app, File appIcon, int updater);

	/**
	 * 取得App 列表
	 * @param memberId
	 * @return
	 */
	public List<App> getAppList(Integer memberId);

	/**
	 * 取得 該會員App
	 * @param appId
	 * @param memberId
	 * @return
	 */
	public App getApp(String appId, Integer memberId);

	/**
	 * 修改App
	 * @param app
	 * @param appIcon
	 * @param updater
	 * @param cancelImage 
	 */
	public void modifyApp(App app, File appIcon, int updater, boolean cancelImage);

	/**
	 * 刪除App
	 * @param appId
	 * @param memberId
	 */
	public void deleteApp(String appId, Integer memberId);

	/**
	 * 取得APP DASHBOARD資料
	 * @param memberId
	 * @return
	 */
	public List<AppDataPlus> appDashboard(Integer memberId);

	/**
	 * 取得app detail資料
	 * @param app
	 * @param appId
	 * @param now
	 * @param isAdmin
	 * @return
	 */
	public AppDataPlus appDashboardDetail(App app, String appId, Date now, boolean isAdmin);

	/**
	 * 取得圖表資料
	 * @param appId
	 * @param thisDate
	 * @param preDate 
	 * @return
	 */
	public ChartData getChartData(String appId, Date thisDate, Date preDate);

	public MapData getMapData(String appId, Date thisDate, Date preDate);

	public ChartData getCrossChartData(String appId, Date thisDate, Date preDate);

	public MapData getCrossMapData(String appId, Date thisDate, Date preDate);

	public ChartData getPreferChartData(String appId, Date thisDate, Date preDate);

	public MapData getPreferMapData(String appId,String category, String timePeriod, String weekMonth);

	public List<Chart> getCategoryPieChartByCondition(String appId, String timeInterval, String weekMonth);

	public ChartData getLocationCrossPieChart(String appId, Date startDate, Date endDate, String area);

	public List<String> getCategoryList();

	public List<String> getTrackerColumn(String appId);

	public List<Object> getTrackerData(String appId, Date startDate, Date endDate, boolean isLimit);

	public List<SdkAuthDurationPlus> getSdkAuthDuration(String appId);


}
