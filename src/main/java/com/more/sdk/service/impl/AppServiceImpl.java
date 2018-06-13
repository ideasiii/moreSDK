package com.more.sdk.service.impl;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.more.sdk.entity.App;
import com.more.sdk.entity.AppPermission;
import com.more.sdk.entity.Sdk;
import com.more.sdk.entity.SdkAuthDuration;
import com.more.sdk.entity.api.AppTitle;
import com.more.sdk.entity.api.AppLatLonUsers;
import com.more.sdk.entity.api.AppPoiLatLonUsers;
import com.more.sdk.entity.api.AppUsers;
import com.more.sdk.entity.dao.AppMapper;
import com.more.sdk.entity.dao.AppPermissionMapper;
import com.more.sdk.entity.dao.SdkAuthDurationMapper;
import com.more.sdk.entity.dao.SdkMapper;
import com.more.sdk.entity.model.Chart;
import com.more.sdk.entity.model.ChartData;
import com.more.sdk.entity.model.Data;
import com.more.sdk.entity.model.LatLon;
import com.more.sdk.entity.model.MapData;
import com.more.sdk.entity.plus.AppDataPlus;
import com.more.sdk.entity.plus.SdkAuthDurationPlus;
import com.more.sdk.service.AppService;
import com.more.sdk.service.HttpService;

@Service("appService")
@Transactional("transactionManager")
public class AppServiceImpl implements AppService{
	@Resource
	private AppMapper appMapper;
	@Resource
	private AppPermissionMapper appPermissionMapper;
	@Resource
	private SdkMapper sdkMapper;
	@Resource
	private SdkAuthDurationMapper sdkAuthDurationMapper ;
	@Resource(name="httpService")
	private HttpService httpService;
	
	@Value("${app.agree.version}")
	private String appAgreeVersion;
	
	@Value("${storage.img.path}")
	private String storageImgPath;
	@Value("${storage.img.real.path}")
	private String storageImgRealPath;
	
	@Value("${app.api.url.users}")
	private String appApiUrlUsers;
	@Value("${app.api.url.period.users}")
	private String appApiUrlPeriodUsers;
	@Value("${app.api.url.month.daily}")
	private String appApiUrlMonthDaily;
	@Value("${app.api.url.daily.time}")
	private String appApiUrlDailyTime;
	@Value("${app.api.url.monthly.time}")
	private String appApiUrlMonthlyTime;
	@Value("${app.api.url.location.monthly.users}")
	private String appApiUrlLocationMonthlyUsers;
	@Value("${app.api.url.current.users}")
	private String appApiUrlCurrentUsers;
	@Value("${app.api.url.location.coordinate}")
	private String appApiUrlLocationCoordinate;
	@Value("${app.api.url.poi.category}")
	private String appApiUrlPoiCategory;
	@Value("${app.api.url.poi.category.detail}")
	private String appApiUrlPoiCategoryDetail;
	@Value("${app.api.url.poi.period}")
	private String appApiUrlPoiPeriod;
	@Value("${app.api.url.poi.period.detail}")
	private String appApiUrlPoiPeriodDetail;
	@Value("${app.api.url.poi.area}")
	private String appApiUrlPoiArea;
	@Value("${app.api.url.poi.area.detail}")
	private String appApiUrlPoiAreaDetail;
	@Value("${app.api.url.poi.area.poi}")
	private String appApiUrlPoiAreaPoi;
	
	@Value("${app.api.url.poi.coordinate}")
	private String appApiUrlPoiCoordinate;
	@Value("${app.api.url.poi.list.category}")
	private String appApiUrlPoiListCategory;
	
	@Value("${app.api.url.tracker.columns}")
	private String appApiUrlTrackerColumns;
	
	@Value("${app.api.url.tracker.data}")
	private String appApiUrlTrackerData;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat monthFormat = new SimpleDateFormat("MM月");
	
	
	public void registeredApp(App app, File appIcon, int updater) {
		Date now = new Date();
		
		if(appIcon != null){
			String fileName = "/"+app.getMemberId()+"_"+now.getTime()+".jpg";
			
			String tmpAppPath = this.getClass().getClassLoader().getResource("").getPath();
			String[] tmpPathArray = tmpAppPath.split("/WEB-INF/classes/");
			try{
				FileUtils.copyFile(appIcon, new File(tmpPathArray[0]+storageImgPath+fileName));
				FileUtils.copyFile(appIcon, new File(storageImgRealPath+fileName));
			}catch (IOException e) {
				throw new RuntimeException("儲存圖片發生問題，請洽管理者");
			}
			app.setAppIcon(storageImgPath+fileName);
		}else{
			if("ios".equals(app.getAppOs())){
				app.setAppIcon("/images/sdk/ios_small.jpg");
			}else{
				app.setAppIcon("/images/sdk/android_small.jpg");
			}
		}
		
		app.setAppId(now.getTime()+"");
		app.setAgreeNo(appAgreeVersion);
		app.setCreateDate(now);
		app.setUpdateDate(now);
		app.setUpdater(updater);
		appMapper.insert(app);
		
		AppPermission appPermission = new AppPermission();
		appPermission.setAppId(app.getAppId());
		appPermission.setPermissionType(0);
		appPermission.setCreateDate(now);
		appPermission.setUpdateDate(now);
		appPermission.setUpdater(updater);
		appPermissionMapper.insertSelective(appPermission);
		List<Sdk> sdkList = sdkMapper.selectAll();
		if(sdkList != null && sdkList.size() > 0){
			for(Sdk sdk : sdkList){
				SdkAuthDuration sdkAuthDuration = new SdkAuthDuration();
				sdkAuthDuration.setAppId(app.getAppId());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				sdkAuthDuration.setStartDate(calendar.getTime());
				
				calendar.add(Calendar.MONTH, 1);
				calendar.set(Calendar.HOUR, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
				sdkAuthDuration.setEndDate(calendar.getTime());
				sdkAuthDuration.setSdkId(sdk.getSdkId());
				sdkAuthDuration.setCreateDate(now);
				sdkAuthDuration.setUpdateDate(now);
				sdkAuthDuration.setUpdater(updater);
				sdkAuthDurationMapper.insertSelective(sdkAuthDuration);
			}
		}
		
		
	}


	public List<App> getAppList(Integer memberId) {
		List<App> appList = appMapper.selectByMemberId(memberId);
		return appList;
	}


	public App getApp(String appId, Integer memberId) {
		try{
			App app = appMapper.selectByPrimaryKey(appId);
			if(app == null){
				throw new RuntimeException("查無App資料");
			}else{
				if (app.getMemberId().intValue() != memberId.intValue()){
					throw new RuntimeException("無權限查詢該App資料");
				}else{
					return app;
				}
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}
	}


	public void modifyApp(App app, File appIcon, int updater, boolean cancelImage) {
		Date now = new Date();
		App olderApp = appMapper.selectByPrimaryKey(app.getAppId());
		BeanUtils.copyProperties(app, olderApp, new String[]{"appId","appIcon","memberId","agreeNo","createDate","updateDate","updater"});
		if(appIcon != null){
			String fileName = "/"+olderApp.getMemberId()+"_"+now.getTime()+".jpg";
			
			String tmpAppPath = this.getClass().getClassLoader().getResource("").getPath();
			String[] tmpPathArray = tmpAppPath.split("/WEB-INF/classes/");
			try{
				FileUtils.copyFile(appIcon, new File(tmpPathArray[0]+storageImgPath+fileName));
				FileUtils.copyFile(appIcon, new File(storageImgRealPath+fileName));
			}catch (IOException e) {
				throw new RuntimeException("儲存圖片發生問題，請洽管理者");
			}
			olderApp.setAppIcon(storageImgPath+fileName);
		}else{
			if(cancelImage){
				if("ios".equals(app.getAppOs())){
					olderApp.setAppIcon("/images/sdk/ios_small.jpg");
				}else{
					olderApp.setAppIcon("/images/sdk/android_small.jpg");
				}
			}
		}
		
		
		olderApp.setUpdateDate(now);
		olderApp.setUpdater(updater);
		appMapper.updateByPrimaryKeySelective(olderApp);
		
		
	}


	public void deleteApp(String appId, Integer memberId) {
		try{
			App app = appMapper.selectByPrimaryKey(appId);
			if(app == null){
				throw new RuntimeException("查無App資料");
			}else{
				if (app.getMemberId().intValue() != memberId.intValue()){
					throw new RuntimeException("無權限刪除該App資料");
				}else{
					sdkAuthDurationMapper.deleteByAppId(appId);
					appPermissionMapper.deleteByAppId(appId);
					appMapper.deleteByPrimaryKey(appId);
				}
			}
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("系統發生問題，請洽管理者:" + e.getMessage());
		}
		
	}


	public List<AppDataPlus> appDashboard(Integer memberId) {
		List<App> appList = getAppList(memberId);
		List<AppDataPlus> appDataPlusList = new ArrayList<AppDataPlus>();
		Date now = new Date();
		if(appList != null && appList.size() > 0){
			
			for (int i = 0; i < appList.size(); i++) {
				appDataPlusList.add(appDashboardDetail(appList.get(i), null, now, false));
			}
		}
		
		return appDataPlusList;
	}


	public AppDataPlus appDashboardDetail(App app, String appId, Date now, boolean isAdmin) {
		String urlPeriodUsers = appApiUrlPeriodUsers+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String urlCurrentUsers = appApiUrlCurrentUsers+"?app_id={appId}";
		String urlUsers = appApiUrlUsers+"?app_id={appId}&date={date}";
		
		if(app == null){
			app = appMapper.selectByPrimaryKey(appId);
		}
		
		AppDataPlus appDataPlus = new AppDataPlus();
		BeanUtils.copyProperties(app, appDataPlus);
		
		//判斷App有無權限
		AppPermission appPermission = appPermissionMapper.selectByAppId(app.getAppId());
		appDataPlus.setPermissionType(appPermission.getPermissionType());
		if(appPermission== null || appPermission.getPermissionType() < 1){
			return appDataPlus;
		}
		
		AppUsers currentUsers = (AppUsers) httpService.sendGet(true, urlCurrentUsers.replace("{appId}", app.getAppId()), AppUsers.class, false);
		if(currentUsers.getSuccess()){
			appDataPlus.setCountNow(currentUsers.getCount());
			
			
			AppUsers totalUser = (AppUsers) httpService.sendGet(true, urlUsers.replace("{appId}", app.getAppId()).replace("{date}", dateFormat.format(now)), AppUsers.class, false);
			appDataPlus.setCountTotal(totalUser.getCount());
			Calendar calendar = Calendar.getInstance();
//			查月數據
			calendar.set(Calendar.DATE, 1);
			Date startDate = calendar.getTime();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endDate = calendar.getTime();
			
			AppUsers thisMonth = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
			
			calendar.add(Calendar.MONTH, -1);
			
			calendar.set(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate = calendar.getTime();
			
			AppUsers preMonth = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
			
			appDataPlus.setCountMonth(thisMonth.getCount());
			appDataPlus.setCountMonthSign(thisMonth.getCount() >= preMonth.getCount());
			BigDecimal countData = null;
			if(appDataPlus.getCountMonthSign()){
				countData = new BigDecimal(thisMonth.getCount()).subtract(new BigDecimal(preMonth.getCount()));
			}else{
				countData = new BigDecimal(preMonth.getCount()).subtract(new BigDecimal(thisMonth.getCount()));
			}
			appDataPlus.setCountMonthGrowthRate(countData.divide(new BigDecimal(preMonth.getCount() == 0 ? 1:preMonth.getCount()), 4, RoundingMode.HALF_UP).movePointRight(2).setScale(2).doubleValue());
			
//			查週數據
			calendar.setTime(now);
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			calendar.add(Calendar.DATE, 1);
			endDate = calendar.getTime();
			
			AppUsers thisWeek = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

			calendar.add(Calendar.WEEK_OF_YEAR, -1);
			
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			calendar.add(Calendar.DATE, 1);
			endDate = calendar.getTime();
			
			AppUsers preWeek = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
			
			appDataPlus.setCountWeek(thisWeek.getCount());
			appDataPlus.setCountWeekSign(thisWeek.getCount() >= preWeek.getCount());
			countData = null;
			if(appDataPlus.getCountWeekSign()){
				countData = new BigDecimal(thisWeek.getCount()).subtract(new BigDecimal(preWeek.getCount()));
			}else{
				countData = new BigDecimal(preWeek.getCount()).subtract(new BigDecimal(thisWeek.getCount()));
			}
			appDataPlus.setCountWeekGrowthRate(countData.divide(new BigDecimal(preWeek.getCount() == 0 ? 1:preWeek.getCount()), 4, RoundingMode.HALF_UP).movePointRight(2).setScale(2).doubleValue());
			
//			查日數據
			
			calendar.setTime(now);
			startDate = calendar.getTime();
			endDate = calendar.getTime();
			
			AppUsers thisDay = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

			calendar.add(Calendar.DATE, -1);
			
			startDate = calendar.getTime();
			endDate = calendar.getTime();
			
			AppUsers preDay = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", app.getAppId()).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
			
			appDataPlus.setCountDay(thisDay.getCount());
			appDataPlus.setCountDaySign(thisDay.getCount() >= preDay.getCount());
			countData = null;
			if(appDataPlus.getCountDaySign()){
				countData = new BigDecimal(thisDay.getCount()).subtract(new BigDecimal(preDay.getCount()));
			}else{
				countData = new BigDecimal(preDay.getCount()).subtract(new BigDecimal(thisDay.getCount()));
			}
			appDataPlus.setCountDayGrowthRate(countData.divide(new BigDecimal(preDay.getCount() == 0?1:preDay.getCount()), 4, RoundingMode.HALF_UP).movePointRight(2).setScale(2).doubleValue());
			
		}
		
		return appDataPlus;
	}


	public ChartData getChartData(String appId, Date thisDate, Date preDate) {
		String monthDailyUrl = appApiUrlMonthDaily+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String dailyTimeUrl = appApiUrlDailyTime+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String monthTimeUrl = appApiUrlMonthlyTime+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String locationMonthUsersUrl = appApiUrlLocationMonthlyUsers+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		Calendar calendar = Calendar.getInstance();
//		查當前月數據
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		Date startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = calendar.getTime();
		
		AppUsers nowMonthDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
		ChartData chartData = new ChartData();
		List<Chart> monthUserChart = new ArrayList<Chart>();
		if(nowMonthDaily.getSuccess()){
			for (int i = 0; i < nowMonthDaily.getResult().size(); i++) {
				Chart chart = new Chart();
				chart.setLabel(nowMonthDaily.getResult().get(i).getDate());
				chart.setValue(nowMonthDaily.getResult().get(i).getCount()+"");
				monthUserChart.add(chart);
			}
		}
		chartData.setMonthUserChart(monthUserChart);
//		當前週、日數據
		
		calendar.setTime(preDate);
		String nowDay = dateFormat.format(preDate);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.add(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		calendar.add(Calendar.DATE, 1);
		endDate = calendar.getTime();
		
		AppUsers nowWeekDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		int nowIndex = 0;
		calendar.setTime(startDate);
		List<Chart> weekUserChart = new ArrayList<Chart>();
		List<Chart> dayUserChart = new ArrayList<Chart>();
		dayUserChart.add(new Chart());
		dayUserChart.get(0).setLabel(nowDay);
		dayUserChart.get(0).setValue("0");
		for (int i = 0; i < 7; i++) {
			String tmpDate = dateFormat.format(calendar.getTime());
			Chart chart = new Chart();
			Data data = new Data();
			chart.setLabel(tmpDate);
			chart.setValue("0");
			if(nowWeekDaily.getSuccess() && nowWeekDaily.getResult().size() > nowIndex){
				data = nowWeekDaily.getResult().get(nowIndex);
				nowIndex++;
			}
			chart.setValue(data.getCount()+"");
			weekUserChart.add(chart);
			if(nowDay.equals(data.getDate())){
				dayUserChart.get(0).setValue(data.getCount()+"");
			}
			calendar.add(Calendar.DATE, 1);
		}
		chartData.setWeekUserChart(weekUserChart);
		chartData.setDayUserChart(dayUserChart);
		
//		當前日時間分布	
		calendar.setTime(preDate);
		calendar.add(Calendar.DATE, -1);
		startDate = calendar.getTime();
		endDate = calendar.getTime();
		AppUsers dailyTime = (AppUsers) httpService.sendGet(true, dailyTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		List<Chart> preDayBarChart = new ArrayList<Chart>();
		String[] names = new String[]{"morningCount","noonCount","nightCount","midCount"};
		String[] labels = new String[]{"早（06~12）","中（12~18）","晚（18~24）","半夜（00~06）"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart chart = new Chart();
			chart.setLabel(labels[i]);
			try{
				Object tmpCnt = readMethod.invoke(dailyTime);
				if(tmpCnt != null){
					chart.setValue(""+tmpCnt);
				}else{
					chart.setValue("0");
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			preDayBarChart.add(chart);
			
		}
		chartData.setPreDayBarChart(preDayBarChart);
		
//		當前月 時間分布
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		
		AppUsers monthlyTime = (AppUsers) httpService.sendGet(true, monthTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		List<Chart> monthBarChart = new ArrayList<Chart>();
		names = new String[]{"morningCount","noonCount","nightCount","midCount"};
		labels = new String[]{"早（06~12）","中（12~18）","晚（18~24）","半夜（00~06）"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart chart = new Chart();
			chart.setLabel(labels[i]);
			try{
				Object tmpCnt = readMethod.invoke(monthlyTime);
				if(tmpCnt != null){
					chart.setValue(""+tmpCnt);
				}else{
					chart.setValue("0");
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			monthBarChart.add(chart);
			
		}
		chartData.setMonthBarChart(monthBarChart);
		
//		當前區域分布
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		AppUsers locationMonth = (AppUsers) httpService.sendGet(true, locationMonthUsersUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		List<Chart> locationMonthPieChart = new ArrayList<Chart>();
		names = new String[]{"northCount","midCount","southCount","eastCount"};
		labels = new String[]{"北","西","南","東"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart chart = new Chart();
			chart.setLabel(labels[i]);
			try{
				Object tmpCnt = readMethod.invoke(locationMonth);
				if(tmpCnt != null){
					chart.setValue(""+tmpCnt);
				}else{
					chart.setValue("0");
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			locationMonthPieChart.add(chart);
			
		}
		chartData.setLocationMonthPieChart(locationMonthPieChart);
		
		return chartData;
	}
	
	public MapData getMapData(String appId, Date thisDate, Date preDate){
		String locationCoordinateUrl = appApiUrlLocationCoordinate+"?app_id={appId}&time_period={timePeriod}&start_date={startDate}&end_date={endDate}&limit=2500";
		
		MapData mapData = new MapData();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		Date startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = calendar.getTime();
		String[] names = new String[]{"morning","noon","night","mid"};
		for (int i = 0; i < names.length; i++) {
			AppLatLonUsers monthMap = (AppLatLonUsers) httpService.sendGet(true, locationCoordinateUrl.replace("{appId}", appId).replace("{timePeriod}", names[i]).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppLatLonUsers.class, false);

			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppLatLonUsers.class, "result");
			PropertyDescriptor mapDesc = BeanUtils.getPropertyDescriptor(MapData.class, names[i]+"NowMap");
			Method readMethod = descriptor.getReadMethod();
			Method setMapMethod = mapDesc.getWriteMethod();
			try{
				List<LatLon> latLonList = (List<LatLon>) readMethod.invoke(monthMap);
				if(latLonList == null){
					latLonList = new ArrayList<LatLon>();
				}
				setMapMethod.invoke(mapData, latLonList);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mapData;
	}


	public ChartData getCrossChartData(String appId, Date thisDate, Date preDate) {
		String urlPeriodUsers = appApiUrlPeriodUsers+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String monthDailyUrl = appApiUrlMonthDaily+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String dailyTimeUrl = appApiUrlDailyTime+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String monthTimeUrl = appApiUrlMonthlyTime+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		String locationMonthUsersUrl = appApiUrlLocationMonthlyUsers+"?app_id={appId}&start_date={startDate}&end_date={endDate}";
		Calendar calendar = Calendar.getInstance();
		ChartData chartData = new ChartData();

		List<Chart> thisMonthCurveChart = new ArrayList<Chart>();
		List<Chart> preMonthCurveChart = new ArrayList<Chart>();
		
		calendar.setTime(thisDate);
		calendar.set(Calendar.DATE, 1);
		Date startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = calendar.getTime();
		AppUsers thisMonthDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		AppUsers thisMonthCount = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		AppUsers preMonthDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		AppUsers preMonthCount = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

		
		chartData.setDiffMonthCnt(thisMonthCount.getCount()-preMonthCount.getCount());
		
		int thisIndex = 0;
		int preIndex = 0;	
		List<Chart> diffMonthUserChart = new ArrayList<Chart>();
		for (int i = 0; i < 31; i++) {
			Chart thisCurve = new Chart();
			Chart preCurve = new Chart();
			thisCurve.setLabel((i+1)+"日");
			thisCurve.setValue("0");
			preCurve.setLabel((i+1)+"日");
			preCurve.setValue("0");
			
			Data thisData = new Data();
			Data preData = new Data();
			if(thisMonthDaily.getSuccess() && thisMonthDaily.getResult().size() > thisIndex){
				if((i+1)==Integer.valueOf((thisMonthDaily.getResult().get(thisIndex).getDate().split("-")[2])).intValue()){
					thisData = thisMonthDaily.getResult().get(thisIndex);
					thisIndex++;
				}
			}
			
			if(preMonthDaily.getSuccess() && preMonthDaily.getResult().size() > preIndex){
				if((i+1)==Integer.valueOf((preMonthDaily.getResult().get(preIndex).getDate().split("-")[2])).intValue()){
					preData = preMonthDaily.getResult().get(preIndex);
					preIndex++;
				}
			}
			
			thisCurve.setValue(thisData.getCount()+"");
			preCurve.setValue(preData.getCount()+"");
			
			thisMonthCurveChart.add(thisCurve);
			preMonthCurveChart.add(preCurve);
			
			Chart chart = new Chart();
			chart.setLabel((i+1)+"日");
			chart.setValue((thisData.getCount()-preData.getCount())+"");
			diffMonthUserChart.add(chart);
			//chartData.setDiffMonthCnt(chartData.getDiffMonthCnt()+thisData.getCount()-preData.getCount());
			
		}
		chartData.setDiffMonthUserChart(diffMonthUserChart);
		
		
		calendar.setTime(thisDate);
		String thisDay = dateFormat.format(thisDate);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.add(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		calendar.add(Calendar.DATE, 1);
		endDate = calendar.getTime();
		
		AppUsers thisWeekDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		AppUsers thisWeekCount = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
		String[] thisWeekName = new String[7];
		calendar.setTime(startDate);
		for (int i = 0; i < thisWeekName.length; i++) {
			thisWeekName[i] = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}
		calendar.setTime(preDate);
		String preDay = dateFormat.format(preDate);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.add(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		calendar.add(Calendar.DATE, 1);
		endDate = calendar.getTime();
		
		AppUsers preWeekDaily = (AppUsers) httpService.sendGet(true, monthDailyUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		AppUsers preWeekCount = (AppUsers) httpService.sendGet(true, urlPeriodUsers.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

		String[] preWeekName = new String[7];
		calendar.setTime(startDate);
		for (int i = 0; i < preWeekName.length; i++) {
			preWeekName[i] = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
		}

		
		chartData.setDiffWeekCnt(thisWeekCount.getCount()-preWeekCount.getCount());
		thisIndex = 0;
		preIndex = 0;
		List<Chart> diffWeekUserChart = new ArrayList<Chart>();
		List<Chart> diffDayUserChart = new ArrayList<Chart>();
		diffDayUserChart.add(new Chart());
		diffDayUserChart.get(0).setValue("0");
		for (int i = 0; i < 7; i++) {
			Data thisData = new Data();
			Data preData = new Data();
			if(thisWeekDaily.getSuccess() && thisWeekDaily.getResult().size() > thisIndex){
				if(thisWeekName[i].equals(thisWeekDaily.getResult().get(thisIndex).getDate())){
					thisData = thisWeekDaily.getResult().get(thisIndex);
					thisIndex++;
				}
			}
			
			if(preWeekDaily.getSuccess() && preWeekDaily.getResult().size() > preIndex){
				if(preWeekName[i].equals(preWeekDaily.getResult().get(preIndex).getDate())){
					preData = preWeekDaily.getResult().get(preIndex);
					preIndex++;
				}
			}
			
			Chart chart = new Chart();
			chart.setLabel("週"+(i+1));
			chart.setValue((thisData.getCount()-preData.getCount())+"");
			diffWeekUserChart.add(chart);
//			chartData.setDiffWeekCnt(chartData.getDiffWeekCnt()+thisData.getCount()-preData.getCount());
			if(thisDay.equals(thisData.getDate())){
				diffDayUserChart.get(0).setValue((Integer.valueOf(diffDayUserChart.get(0).getValue())+thisData.getCount())+"");
				chartData.setDiffDayCnt(chartData.getDiffDayCnt()+thisData.getCount());
			}
			if(preDay.equals(preData.getDate())){
				diffDayUserChart.get(0).setValue((Integer.valueOf(diffDayUserChart.get(0).getValue())-preData.getCount())+"");
				chartData.setDiffDayCnt(chartData.getDiffDayCnt()-preData.getCount());
			}
		}
		
		
		chartData.setDiffDayUserChart(diffDayUserChart);
		chartData.setDiffWeekUserChart(diffWeekUserChart);
		
		List<List<Chart>> diffCurveMonthChart = new ArrayList<List<Chart>>();
		diffCurveMonthChart.add(preMonthCurveChart);
		diffCurveMonthChart.add(thisMonthCurveChart);
		chartData.setDiffCurveMonthChart(diffCurveMonthChart);
		
		
		calendar.setTime(thisDate);
		startDate = calendar.getTime();
		endDate = calendar.getTime();
		AppUsers thisMonthDailyTime = (AppUsers) httpService.sendGet(true, dailyTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
		calendar.setTime(preDate);
		startDate = calendar.getTime();
		endDate = calendar.getTime();
		AppUsers preMonthDailyTime = (AppUsers) httpService.sendGet(true, dailyTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		List<Chart> diffThisDayBarChart = new ArrayList<Chart>();
		List<Chart> diffPreDayBarChart = new ArrayList<Chart>();
		List<String> diffDaySeries = new ArrayList<String>();
		diffDaySeries.add(dateFormat.format(preDate));
		diffDaySeries.add(dateFormat.format(thisDate));
		chartData.setDiffDaySeries(diffDaySeries);
		String[] names = new String[]{"morningCount","noonCount","nightCount","midCount"};
		String[] labels = new String[]{"早（06~12）","中（12~18）","晚（18~24）","半夜（00~06）"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart thisChart = new Chart();
			Chart preChart = new Chart();
			thisChart.setLabel(labels[i]);
			thisChart.setValue("0");
			preChart.setLabel(labels[i]);
			preChart.setValue("0");
			try{
				Object tmpThisCnt = readMethod.invoke(thisMonthDailyTime);
				
				if(tmpThisCnt != null){
					thisChart.setValue(""+tmpThisCnt);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			diffThisDayBarChart.add(thisChart);
			
			try{
				Object tmpPreCnt = readMethod.invoke(preMonthDailyTime);
				if(tmpPreCnt != null){
					preChart.setValue(""+tmpPreCnt);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			diffPreDayBarChart.add(preChart);
		}
		chartData.setDiffThisDayBarChart(diffThisDayBarChart);
		chartData.setDiffPreDayBarChart(diffPreDayBarChart);
		
		
		calendar.setTime(thisDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		
		AppUsers diffThisMonthlyTime = (AppUsers) httpService.sendGet(true, monthTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

		
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		AppUsers diffPreMonthlyTime = (AppUsers) httpService.sendGet(true, monthTimeUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		List<Chart> diffThisMonthBarChart = new ArrayList<Chart>();
		List<Chart> diffPreMonthBarChart = new ArrayList<Chart>();
		List<String> diffMonthSeries = new ArrayList<String>();
		diffMonthSeries.add(monthFormat.format(preDate));
		diffMonthSeries.add(monthFormat.format(thisDate));
		chartData.setDiffMonthSeries(diffMonthSeries);
		names = new String[]{"morningCount","noonCount","nightCount","midCount"};
		labels = new String[]{"早（06~12）","中（12~18）","晚（18~24）","半夜（00~06）"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart thisChart = new Chart();
			Chart preChart = new Chart();
			thisChart.setLabel(labels[i]);
			thisChart.setValue("0");
			preChart.setLabel(labels[i]);
			preChart.setValue("0");
			try{
				Object tmpThisCnt = readMethod.invoke(diffThisMonthlyTime);
				
				if(tmpThisCnt != null){
					thisChart.setValue(""+tmpThisCnt);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			diffThisMonthBarChart.add(thisChart);
			
			try{
				Object tmpPreCnt = readMethod.invoke(diffPreMonthlyTime);
				if(tmpPreCnt != null){
					preChart.setValue(""+tmpPreCnt);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			diffPreMonthBarChart.add(preChart);
			
			
		}
		chartData.setDiffThisMonthBarChart(diffThisMonthBarChart);
		chartData.setDiffPreMonthBarChart(diffPreMonthBarChart);
		
		
		calendar.setTime(thisDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		AppUsers thisLocationMonth = (AppUsers) httpService.sendGet(true, locationMonthUsersUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);
		
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		AppUsers preLocationMonth = (AppUsers) httpService.sendGet(true, locationMonthUsersUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppUsers.class, false);

		
		
		List<Chart> thisLocationMonthPieChart = new ArrayList<Chart>();
		List<Chart> preLocationMonthPieChart = new ArrayList<Chart>();
		names = new String[]{"northCount","midCount","southCount","eastCount"};
		labels = new String[]{"北","西","南","東"};
		for (int i = 0; i < names.length; i++) {
			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppUsers.class, names[i]);
			Method readMethod = descriptor.getReadMethod();
			Chart thisChart = new Chart();
			Chart preChart = new Chart();
			thisChart.setLabel(labels[i]);
			preChart.setLabel(labels[i]);
			thisChart.setValue("0");
			preChart.setValue("0");
			try{
				Object tmpThisCnt = readMethod.invoke(thisLocationMonth);
				if(tmpThisCnt != null){
					thisChart.setValue(""+tmpThisCnt);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			thisLocationMonthPieChart.add(thisChart);
			
			try{
				Object tmpPreCnt = readMethod.invoke(preLocationMonth);
				if(tmpPreCnt != null){
					preChart.setValue(""+tmpPreCnt);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			preLocationMonthPieChart.add(preChart);
			
		}
		chartData.setThisLocationMonthPieChart(thisLocationMonthPieChart);
		chartData.setPreLocationMonthPieChart(preLocationMonthPieChart);
		return chartData;
	}


	public MapData getCrossMapData(String appId, Date thisDate, Date preDate) {
		String locationCoordinateUrl = appApiUrlLocationCoordinate+"?app_id={appId}&time_period={timePeriod}&start_date={startDate}&end_date={endDate}&limit=2500";
		
		MapData mapData = new MapData();
		
		String[] names = new String[]{"morning","noon","night","mid"};
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(thisDate);
		calendar.set(Calendar.DATE, 1);
		Date startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = calendar.getTime();
		for (int i = 0; i < names.length; i++) {
			AppLatLonUsers monthMap = (AppLatLonUsers) httpService.sendGet(true, locationCoordinateUrl.replace("{appId}", appId).replace("{timePeriod}", names[i]).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppLatLonUsers.class, false);

			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppLatLonUsers.class, "result");
			PropertyDescriptor mapDesc = BeanUtils.getPropertyDescriptor(MapData.class, names[i]+"ThisMap");
			Method readMethod = descriptor.getReadMethod();
			Method setMapMethod = mapDesc.getWriteMethod();
			try{
				List<LatLon> latLonList = (List<LatLon>) readMethod.invoke(monthMap);
				if(latLonList == null){
					latLonList = new ArrayList<LatLon>();
				}
				setMapMethod.invoke(mapData, latLonList);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		calendar.setTime(preDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		for (int i = 0; i < names.length; i++) {
			AppLatLonUsers monthMap = (AppLatLonUsers) httpService.sendGet(true, locationCoordinateUrl.replace("{appId}", appId).replace("{timePeriod}", names[i]).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), AppLatLonUsers.class, false);

			PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(AppLatLonUsers.class, "result");
			PropertyDescriptor mapDesc = BeanUtils.getPropertyDescriptor(MapData.class, names[i]+"PreMap");
			Method readMethod = descriptor.getReadMethod();
			Method setMapMethod = mapDesc.getWriteMethod();
			try{
				List<LatLon> latLonList = (List<LatLon>) readMethod.invoke(monthMap);
				if(latLonList == null){
					latLonList = new ArrayList<LatLon>();
				}
				setMapMethod.invoke(mapData, latLonList);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mapData;
	}


	public ChartData getPreferChartData(String appId, Date thisDate, Date preDate) {
		String urlPoiCategory = appApiUrlPoiCategory+"?app_id={appId}";
		String urlPoiCategoryDetail = appApiUrlPoiCategoryDetail+"?app_id={appId}&category={category}";
		ChartData chartData = new ChartData();
		
		Comparator<Data> comparator = new Comparator<Data>() {
			public int compare(Data o1, Data o2) {
				return o1.getCount() < o2.getCount()? 1 : o1.getCount() > o2.getCount()? -1: 0;
			}
		};
		List<Chart> categoryPieChart = new ArrayList<Chart>(); 
		AppUsers category = (AppUsers) httpService.sendGet(true, urlPoiCategory.replace("{appId}", appId), AppUsers.class, false);
		if(category.getSuccess()){
			
			for (int i = 0; i <category.getResult().size() && i < 10; i++) {
				Chart chart = new Chart();
				chart.setValue(category.getResult().get(i).getCount()+"");
				chart.setLabel(category.getResult().get(i).getCategory());
				AppUsers detail = null;
				try {
					detail = (AppUsers) httpService.sendGet(true, urlPoiCategoryDetail.replace("{appId}", appId).replace("{category}", URLEncoder.encode(category.getResult().get(i).getCategory(), "UTF-8")), AppUsers.class, false);
				} catch (UnsupportedEncodingException e) {
					detail = new AppUsers();
					detail.setSuccess(false);
				} 
				if(detail.getSuccess()){
					List<Data> detailData =  detail.getResult();
					detailData.sort(comparator);
					StringBuffer tableHtml = new StringBuffer();
					tableHtml.append("<table border='1' style='height:"+(((detailData.size()>10?10:detailData.size())+1)*25)+"px;'>");
					tableHtml.append("<tr>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>人數</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>偏好</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>POI資訊</font></td>");
					tableHtml.append("</tr>");
					for (int j = 0; j < detailData.size() && j < 10; j++) {
						tableHtml.append("<tr>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getCount()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getCategory()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getPoi()+"</font></td>");
						tableHtml.append("</tr>");
					}
					tableHtml.append("</table>");
					chart.setHtml(tableHtml.toString());
					categoryPieChart.add(chart);
				}
			}
		}
		
		chartData.setCategoryPieChart(categoryPieChart);
		chartData.setCategoryPieConditionChart(getCategoryPieChartByCondition(appId,"morning","week"));
		ChartData preferData = getLocationCrossPieChart(appId, thisDate, preDate, "north");
		chartData.setPreCategoryLocationCrossPieChart(preferData.getPreCategoryLocationCrossPieChart());
		chartData.setThisCategoryLocationCrossPieChart(preferData.getThisCategoryLocationCrossPieChart());
		return chartData;
	}

	public ChartData getLocationCrossPieChart(String appId, Date thisDate, Date preDate, String area){
		ChartData chartData = new ChartData();
		String urlPoiArea = appApiUrlPoiArea+"?app_id={appId}&start_date={startDate}&end_date={endDate}&area={area}";
		String urlPoiAreaDetail = appApiUrlPoiAreaDetail+"?app_id={appId}&start_date={startDate}&end_date={endDate}&category={category}&area={area}";
		String urlPoiAreaPoi = appApiUrlPoiAreaPoi+"?app_id={appId}&start_date={startDate}&end_date={endDate}&category={category}&area={area}&poi={poi}";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(preDate);
		Date startDate = preDate;
		Date endDate = preDate;
		
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		Map<String, Integer> areaMap = new HashMap<String, Integer>();
		List<Chart> preCategoryLocationCrossPieChart = new ArrayList<Chart>(); 
		AppUsers preArea = (AppUsers) httpService.sendGet(true, urlPoiArea.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{area}", area), AppUsers.class, false);
		if(preArea.getSuccess()){
			for (int i = 0; i <preArea.getResult().size() && i < 10; i++) {
				Chart chart = new Chart();
				chart.setValue(preArea.getResult().get(i).getCount()+"");
				chart.setLabel(preArea.getResult().get(i).getCategory());
				AppUsers detail = null;
				try {
					detail = (AppUsers) httpService.sendGet(true, urlPoiAreaDetail.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{area}", area).replace("{category}", URLEncoder.encode(preArea.getResult().get(i).getCategory(), "UTF-8")), AppUsers.class, false);
				} catch (UnsupportedEncodingException e) {
					detail = new AppUsers();
					detail.setSuccess(false);
				} 
				/*if(detail.getSuccess()){
					for (int j = 0; j < detail.getResult().size(); j++) {
						areaMap.put(preArea.getResult().get(i).getCategory()+":"+detail.getResult().get(j).getPoi(), detail.getResult().get(j).getCount());
					}
				}*/
				preCategoryLocationCrossPieChart.add(chart);
			}
		}
		
		chartData.setPreCategoryLocationCrossPieChart(preCategoryLocationCrossPieChart);
		
		
		calendar.setTime(thisDate);
		calendar.set(Calendar.DATE, 1);
		startDate = calendar.getTime();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endDate = calendar.getTime();
		List<Chart> thisCategoryLocationCrossPieChart = new ArrayList<Chart>();
		
		AppUsers thisArea = (AppUsers) httpService.sendGet(true, urlPoiArea.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{area}", area), AppUsers.class, false);
		if(thisArea.getSuccess()){
			Comparator<Data> comparator = new Comparator<Data>() {
				public int compare(Data o1, Data o2) {
					return o1.getCount() < o2.getCount()? 1 : o1.getCount() > o2.getCount()? -1: 0;
				}
			};
			
			Calendar preCal = Calendar.getInstance();
			preCal.setTime(preDate);
			preCal.set(Calendar.DATE, 1);
			Date preStartDate = preCal.getTime();
			preCal.set(Calendar.DATE, preCal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date preEndDate = preCal.getTime();
			for (int i = 0; i <thisArea.getResult().size() && i < 10; i++) {
				Chart chart = new Chart();
				chart.setValue(thisArea.getResult().get(i).getCount()+"");
				chart.setLabel(thisArea.getResult().get(i).getCategory());
				AppUsers detail = null;
				try {
					detail = (AppUsers) httpService.sendGet(true, urlPoiAreaDetail.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{area}", area).replace("{category}", URLEncoder.encode(thisArea.getResult().get(i).getCategory(), "UTF-8")), AppUsers.class, false);
				} catch (UnsupportedEncodingException e) {
					detail = new AppUsers();
					detail.setSuccess(false);
				} 
				
				if(detail.getSuccess()){
					List<Data> detailData =  detail.getResult();
					detailData.sort(comparator);
					StringBuffer tableHtml = new StringBuffer();
					tableHtml.append("<table border='1' style='height:"+(((detailData.size()>10?10:detailData.size())+1)*25)+"px;'>");
					tableHtml.append("<tr>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>當月人數</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>上月人數</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>變動幅度</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>偏好</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>POI 資訊</font></td>");
					tableHtml.append("</tr>");
					for (int j = 0; j < detailData.size() && j < 10; j++) {
						Data data = detailData.get(j);
						AppUsers poiUser = null;
						
						try {
							poiUser = (AppUsers) httpService.sendGet(true, urlPoiAreaPoi.replace("{appId}", appId).replace("{startDate}", dateFormat.format(preStartDate)).replace("{endDate}", dateFormat.format(preEndDate)).replace("{area}", area).replace("{category}", URLEncoder.encode(data.getCategory(), "UTF-8")).replace("{poi}", URLEncoder.encode(data.getPoi(), "UTF-8")), AppUsers.class, false);
						} catch (UnsupportedEncodingException e) {
							poiUser = new AppUsers();
							poiUser.setSuccess(false);
						}
						
						Integer preCount = 0;
						
						if(poiUser.getSuccess()){
							preCount = poiUser.getResult().get(0).getCount();
						}
						BigDecimal countData = null;
						if(data.getCount() > preCount.intValue()){
							countData = new BigDecimal(data.getCount()).subtract(new BigDecimal(preCount));
						}else{
							countData = new BigDecimal(preCount).subtract(new BigDecimal(data.getCount()));
						}
						double rate = countData.divide(new BigDecimal(preCount == 0 ? 1:preCount), 4, RoundingMode.HALF_UP).movePointRight(2).setScale(2).doubleValue();
						
						
						tableHtml.append("<tr>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+data.getCount()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+preCount.intValue()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+(data.getCount() >= preCount.intValue()?"up ":"down ")+rate+"%</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+data.getCategory()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+data.getPoi()+"</font></td>");
						tableHtml.append("</tr>");
					}
					tableHtml.append("</table>");
					chart.setHtml(tableHtml.toString());
				}else{
					chart.setHtml("");
				}
				thisCategoryLocationCrossPieChart.add(chart);	
			}
		}
		chartData.setThisCategoryLocationCrossPieChart(thisCategoryLocationCrossPieChart);
		return chartData;
	}

	public List<Chart> getCategoryPieChartByCondition(String appId, String timeInterval, String weekMonth){
		String urlPoiPeriod = appApiUrlPoiPeriod+"?app_id={appId}&start_date={startDate}&end_date={endDate}&time_interval={timeInterval}";
		String urlPoiPeriodDetail = appApiUrlPoiPeriodDetail+"?app_id={appId}&start_date={startDate}&end_date={endDate}&category={category}&time_interval={timeInterval}";
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		Date startDate = now;
		Date endDate = now;
		if("week".equals(weekMonth)){
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			calendar.add(Calendar.DATE, 1);
			endDate = calendar.getTime();
		}else if("month".equals(weekMonth)){
			calendar.set(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate = calendar.getTime();
		}
		
		
		List<Chart> categoryPieConditionChart = new ArrayList<Chart>(); 
		AppUsers period = (AppUsers) httpService.sendGet(true, urlPoiPeriod.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{timeInterval}", timeInterval), AppUsers.class, false);
		if(period.getSuccess()){
			Comparator<Data> comparator = new Comparator<Data>() {
				public int compare(Data o1, Data o2) {
					return o1.getCount() < o2.getCount()? 1 : o1.getCount() > o2.getCount()? -1: 0;
				}
			};
			for (int i = 0; i <period.getResult().size() && i < 10; i++) {
				Chart chart = new Chart();
				chart.setValue(period.getResult().get(i).getCount()+"");
				chart.setLabel(period.getResult().get(i).getCategory());
				AppUsers detail = null;
				try {
					detail = (AppUsers) httpService.sendGet(true, urlPoiPeriodDetail.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{timeInterval}", timeInterval).replace("{category}", URLEncoder.encode(period.getResult().get(i).getCategory(), "UTF-8")), AppUsers.class, false);
				} catch (UnsupportedEncodingException e) {
					detail = new AppUsers();
					detail.setSuccess(false);
				} 
				if(detail.getSuccess()){
					List<Data> detailData =  detail.getResult();
					detailData.sort(comparator);
					StringBuffer tableHtml = new StringBuffer();
					tableHtml.append("<table border='1' style='height:"+(((detailData.size()>10?10:detailData.size())+1)*25)+"px;'>");
					tableHtml.append("<tr>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>人數</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>偏好</font></td>");
					tableHtml.append("<td style='margin:5px;'><font size='3'>POI資訊</font></td>");
					tableHtml.append("</tr>");
					for (int j = 0; j < detailData.size() && j < 10; j++) {
						tableHtml.append("<tr>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getCount()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getCategory()+"</font></td>");
						tableHtml.append("<td style='margin:5px;'><font size='3'>"+detailData.get(j).getPoi()+"</font></td>");
						tableHtml.append("</tr>");
					}
					tableHtml.append("</table>");
					chart.setHtml(tableHtml.toString());
				}else{
					chart.setHtml("");
				}
				categoryPieConditionChart.add(chart);
			}
		}
		return categoryPieConditionChart;
	}
	

	
	public MapData getPreferMapData(String appId,String category, String timePeriod, String weekMonth) {
		String locationPoiCoordinateUrl = appApiUrlPoiCoordinate+"?app_id={appId}&time_period={timePeriod}&start_date={startDate}&end_date={endDate}&category={category}";
		Date now = new Date();
		
		MapData mapData = new MapData();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		Date startDate = now;
		Date endDate = now;
		if("week".equals(weekMonth)){
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			calendar.add(Calendar.DATE, 1);
			endDate = calendar.getTime();
		}else if("month".equals(weekMonth)){
			calendar.set(Calendar.DATE, 1);
			startDate = calendar.getTime();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate = calendar.getTime();
		}
		
		
		try{
			AppPoiLatLonUsers poiMap = (AppPoiLatLonUsers) httpService.sendGet(true, locationPoiCoordinateUrl.replace("{appId}", appId).replace("{timePeriod}", timePeriod).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)).replace("{category}", URLEncoder.encode(category, "UTF-8")), AppPoiLatLonUsers.class, false);
			List<LatLon> latLonList = poiMap.getResult().get(0).getCoordinate();
			if(latLonList == null){
				latLonList = new ArrayList<LatLon>();
			}
			mapData.setPreferMap(latLonList);
		}catch (Exception e) {
			mapData.setPreferMap(new ArrayList<LatLon>());
		}
		
		return mapData;
	}
	
	public List<String> getCategoryList(){
		AppTitle appCategory = (AppTitle) httpService.sendGet(true, appApiUrlPoiListCategory, AppTitle.class, false);
		if(appCategory.getSuccess()){
			return appCategory.getResult();
		}else{
			return new ArrayList<String>();
		}

	}


	public List<String> getTrackerColumn(String appId) {
		AppTitle appColumns = (AppTitle) httpService.sendGet(true, appApiUrlTrackerColumns+"?app_id="+appId, AppTitle.class, false);
		if(appColumns.getSuccess()){
			return appColumns.getResult();
		}else{
			return new ArrayList<String>();
		}
	}


	public List<Object> getTrackerData(String appId, Date startDate, Date endDate, boolean isLimit) {
		String trackerDataUrl = appApiUrlTrackerData+"?app_id={appId}&start_date={startDate}&end_date={endDate}"+(isLimit?"&limit=1000":"");
		JsonObject jsonObject = (JsonObject) httpService.sendGet(true, trackerDataUrl.replace("{appId}", appId).replace("{startDate}", dateFormat.format(startDate)).replace("{endDate}", dateFormat.format(endDate)), JsonObject.class, false);
		if(jsonObject.get("success").getAsBoolean()){
			return new Gson().fromJson(jsonObject.get("result").getAsJsonArray(), new TypeToken<List<Object>>() {}.getType());
			
		}
		return new ArrayList<Object>();
	}


	public List<SdkAuthDurationPlus> getSdkAuthDuration(String appId) {
		Date now = new Date();
		List<SdkAuthDurationPlus> returnData = new ArrayList<SdkAuthDurationPlus>();
		List<SdkAuthDuration> data = sdkAuthDurationMapper.selectByEffective(appId, now);
		if(data != null && data.size() > 0){
			for(SdkAuthDuration sdkAuthDuration : data){
				SdkAuthDurationPlus plus = new SdkAuthDurationPlus();
				BeanUtils.copyProperties(sdkAuthDuration, plus);
				Sdk sdk = sdkMapper.selectByPrimaryKey(sdkAuthDuration.getSdkId());
				BeanUtils.copyProperties(sdk, plus);
				returnData.add(plus);
			}
		}
		return returnData;
	}
}
