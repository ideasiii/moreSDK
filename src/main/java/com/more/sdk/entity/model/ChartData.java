package com.more.sdk.entity.model;

import java.io.Serializable;
import java.util.List;

public class ChartData implements Serializable{

	private static final long serialVersionUID = -6114117394260497813L;
	private List<Chart> nowUserChart;
	private List<Chart> monthUserChart;
	private List<Chart> weekUserChart;
	private List<Chart> dayUserChart;
	private List<Chart> preDayBarChart;
	private List<Chart> monthBarChart;
	private List<Chart> locationMonthPieChart;
	
	private List<Chart> diffMonthUserChart;
	private List<Chart> diffWeekUserChart;
	private List<Chart> diffDayUserChart;
	private List<List<Chart>> diffCurveMonthChart;
	private int diffMonthCnt;
	private int diffWeekCnt;
	private int diffDayCnt;
	
	private List<Chart> diffThisDayBarChart;
	private List<Chart> diffPreDayBarChart;
	private List<String> diffDaySeries;
	
	private List<Chart> diffThisMonthBarChart;
	private List<Chart> diffPreMonthBarChart;
	private List<String> diffMonthSeries;
	private List<Chart> thisLocationMonthPieChart;
	private List<Chart> preLocationMonthPieChart;
	private List<Chart> categoryPieChart;
	private List<Chart> categoryPieConditionChart;
	private List<Chart> preCategoryLocationCrossPieChart;
	private List<Chart> thisCategoryLocationCrossPieChart;
	public List<Chart> getNowUserChart() {
		return nowUserChart;
	}
	public void setNowUserChart(List<Chart> nowUserChart) {
		this.nowUserChart = nowUserChart;
	}
	public List<Chart> getMonthUserChart() {
		return monthUserChart;
	}
	public void setMonthUserChart(List<Chart> monthUserChart) {
		this.monthUserChart = monthUserChart;
	}
	public List<Chart> getWeekUserChart() {
		return weekUserChart;
	}
	public void setWeekUserChart(List<Chart> weekUserChart) {
		this.weekUserChart = weekUserChart;
	}
	public List<Chart> getDayUserChart() {
		return dayUserChart;
	}
	public void setDayUserChart(List<Chart> dayUserChart) {
		this.dayUserChart = dayUserChart;
	}
	public List<Chart> getPreDayBarChart() {
		return preDayBarChart;
	}
	public void setPreDayBarChart(List<Chart> preDayBarChart) {
		this.preDayBarChart = preDayBarChart;
	}
	public List<Chart> getMonthBarChart() {
		return monthBarChart;
	}
	public void setMonthBarChart(List<Chart> monthBarChart) {
		this.monthBarChart = monthBarChart;
	}
	public List<Chart> getLocationMonthPieChart() {
		return locationMonthPieChart;
	}
	public void setLocationMonthPieChart(List<Chart> locationMonthPieChart) {
		this.locationMonthPieChart = locationMonthPieChart;
	}
	public List<Chart> getDiffMonthUserChart() {
		return diffMonthUserChart;
	}
	public void setDiffMonthUserChart(List<Chart> diffMonthUserChart) {
		this.diffMonthUserChart = diffMonthUserChart;
	}
	public List<Chart> getDiffWeekUserChart() {
		return diffWeekUserChart;
	}
	public void setDiffWeekUserChart(List<Chart> diffWeekUserChart) {
		this.diffWeekUserChart = diffWeekUserChart;
	}
	public List<Chart> getDiffDayUserChart() {
		return diffDayUserChart;
	}
	public void setDiffDayUserChart(List<Chart> diffDayUserChart) {
		this.diffDayUserChart = diffDayUserChart;
	}
	public int getDiffMonthCnt() {
		return diffMonthCnt;
	}
	public void setDiffMonthCnt(int diffMonthCnt) {
		this.diffMonthCnt = diffMonthCnt;
	}
	public int getDiffWeekCnt() {
		return diffWeekCnt;
	}
	public void setDiffWeekCnt(int diffWeekCnt) {
		this.diffWeekCnt = diffWeekCnt;
	}
	public int getDiffDayCnt() {
		return diffDayCnt;
	}
	public void setDiffDayCnt(int diffDayCnt) {
		this.diffDayCnt = diffDayCnt;
	}
	public List<List<Chart>> getDiffCurveMonthChart() {
		return diffCurveMonthChart;
	}
	public void setDiffCurveMonthChart(List<List<Chart>> diffCurveMonthChart) {
		this.diffCurveMonthChart = diffCurveMonthChart;
	}
	public List<Chart> getDiffPreDayBarChart() {
		return diffPreDayBarChart;
	}
	public void setDiffPreDayBarChart(List<Chart> diffPreDayBarChart) {
		this.diffPreDayBarChart = diffPreDayBarChart;
	}
	public List<Chart> getDiffThisDayBarChart() {
		return diffThisDayBarChart;
	}
	public void setDiffThisDayBarChart(List<Chart> diffThisDayBarChart) {
		this.diffThisDayBarChart = diffThisDayBarChart;
	}
	public List<Chart> getDiffThisMonthBarChart() {
		return diffThisMonthBarChart;
	}
	public void setDiffThisMonthBarChart(List<Chart> diffThisMonthBarChart) {
		this.diffThisMonthBarChart = diffThisMonthBarChart;
	}
	public List<Chart> getDiffPreMonthBarChart() {
		return diffPreMonthBarChart;
	}
	public void setDiffPreMonthBarChart(List<Chart> diffPreMonthBarChart) {
		this.diffPreMonthBarChart = diffPreMonthBarChart;
	}
	public List<String> getDiffDaySeries() {
		return diffDaySeries;
	}
	public void setDiffDaySeries(List<String> diffDaySeries) {
		this.diffDaySeries = diffDaySeries;
	}
	public List<String> getDiffMonthSeries() {
		return diffMonthSeries;
	}
	public void setDiffMonthSeries(List<String> diffMonthSeries) {
		this.diffMonthSeries = diffMonthSeries;
	}
	public List<Chart> getThisLocationMonthPieChart() {
		return thisLocationMonthPieChart;
	}
	public void setThisLocationMonthPieChart(List<Chart> thisLocationMonthPieChart) {
		this.thisLocationMonthPieChart = thisLocationMonthPieChart;
	}
	public List<Chart> getPreLocationMonthPieChart() {
		return preLocationMonthPieChart;
	}
	public void setPreLocationMonthPieChart(List<Chart> preLocationMonthPieChart) {
		this.preLocationMonthPieChart = preLocationMonthPieChart;
	}
	public List<Chart> getCategoryPieChart() {
		return categoryPieChart;
	}
	public void setCategoryPieChart(List<Chart> categoryPieChart) {
		this.categoryPieChart = categoryPieChart;
	}
	public List<Chart> getCategoryPieConditionChart() {
		return categoryPieConditionChart;
	}
	public void setCategoryPieConditionChart(List<Chart> categoryPieConditionChart) {
		this.categoryPieConditionChart = categoryPieConditionChart;
	}
	public List<Chart> getPreCategoryLocationCrossPieChart() {
		return preCategoryLocationCrossPieChart;
	}
	public void setPreCategoryLocationCrossPieChart(List<Chart> preCategoryLocationCrossPieChart) {
		this.preCategoryLocationCrossPieChart = preCategoryLocationCrossPieChart;
	}
	public List<Chart> getThisCategoryLocationCrossPieChart() {
		return thisCategoryLocationCrossPieChart;
	}
	public void setThisCategoryLocationCrossPieChart(List<Chart> thisCategoryLocationCrossPieChart) {
		this.thisCategoryLocationCrossPieChart = thisCategoryLocationCrossPieChart;
	}
}
