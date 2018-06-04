<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<s:property value="contextPath"/>/css/double-date.css" rel="stylesheet" />
<script src='<s:property value="contextPath"/>/js/double-date.js'></script>
<script type="text/javascript" src='<s:property value="contextPath"/>/js/fusioncharts/fusioncharts.js'></script>
<script type="text/javascript" src='<s:property value="contextPath"/>/js/fusioncharts/themes/fusioncharts.theme.zune.js?cacheBust=56'></script>
<script type="text/javascript" src='<s:property value="contextPath"/>/js/fusioncharts/fusioncharts.jqueryplugin.js'></script>
<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdmdrWygV7N0F4HQuItrnDndmYcrAE5nY&callback=initMap"></script>

<script>
var nowFlag = false;
var crossFlag = false;
var preferFlag = false;
var currentUser=<s:property value="appDataPlus.countNow"/>;
var totalUser=<s:property value="appDataPlus.countTotal"/>;
var appId='<s:property value="appDataPlus.appId" />';
var type = '<s:property value="type" />';
var markers = [];
var nowMap;
var diffMap;
var preferMap;
$(function(){
	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	<s:if test='"".equals(type)'>
			nowMap = new google.maps.Map(document.getElementById('nowMap'), {
	        zoom: 8,
	        center: {lat: 23.600, lng: 120.412}
	    });
	</s:if>
	<s:elseif test='"Cross".equals(type)'>
			diffMap = new google.maps.Map(document.getElementById('diffMap'), {
	        zoom: 8,
	        center: {lat: 23.600, lng: 120.412}
	    });
	</s:elseif>
	<s:elseif test='"Prefer".equals(type)'>
			preferMap = new google.maps.Map(document.getElementById('preferMap'), {
	        zoom: 8,
	        center: {lat: 23.600, lng: 120.412}
	    });
	</s:elseif>	
	
	
	
	$.ajax({
		type:'POST',
		<s:if test='"".equals(type)'>
			url:'<s:url action="getChartData" namespace="/function"/>',
		</s:if>
		<s:elseif test='"Cross".equals(type)'>
			url:'<s:url action="getCrossChartData" namespace="/function"/>',
		</s:elseif>
		<s:elseif test='"Prefer".equals(type)'>
			url:'<s:url action="getPreferChartData" namespace="/function"/>',
		</s:elseif>	
		data:{
			'app.appId' : '<s:property value="appDataPlus.appId"/>',
			'startDate' : '<s:property value="startDate"/>',
			'endDate' : '<s:property value="endDate"/>'
		},
		dataType:"json",
		success:function(chart){
			$("#modalDiv").modal('hide');  
			if(chart.isSuccess){
				
				<s:if test='"".equals(type)'>
					smallChart("monthUserChart",chart.data["monthUserChart"]);
					smallChart("weekUserChart",chart.data["weekUserChart"]);
					smallChart("dayUserChart",chart.data["dayUserChart"]);
					smallChart("nowUserChart",[{"label":"目前人數","value":currentUser}]);
					var dataset = [ {
						"seriesname" : "當前",
						"renderas" : "Line",
						"color" : "0075C2",
						"anchorRadius" : "3",
						"anchorBgColor" : "008ae6",
						"data" : chart.data["monthUserChart"]
					} ];
					
					curveChart("curveMonthChart",dataset,chart.data["monthUserChart"]);
					barChart("preDayBarChart",chart.data["preDayBarChart"]);
					barChart("monthBarChart",chart.data["monthBarChart"]);
					pieChart("locationMonthPieChart",chart.data["locationMonthPieChart"],"區域");
				</s:if>
				<s:elseif test='"Cross".equals(type)'>
					pieChart("thisLocationMonthPieChart",chart.data["thisLocationMonthPieChart"],"區域");
					pieChart("preLocationMonthPieChart",chart.data["preLocationMonthPieChart"],"區域");
					smallChart("diffMonthUserChart",chart.data["diffMonthUserChart"]);
					smallChart("diffWeekUserChart",chart.data["diffWeekUserChart"]);
					smallChart("diffDayUserChart",chart.data["diffDayUserChart"]);
					smallChart("totalUserChart",[{"label":"總人數","value":totalUser}]);
					$("#diffMonthCnt").html(chart.data["diffMonthCnt"]);
					$("#diffWeekCnt").html(chart.data["diffWeekCnt"]);
					$("#diffDayCnt").html(chart.data["diffDayCnt"]);
					
					
					dataset = [ {
						"seriesname" : chart.data["diffMonthSeries"][0],
						"renderas" : "Line",
						"color" : "7500C2",
						"data" : chart.data["diffCurveMonthChart"][0]
					},
					{
						"seriesname" : chart.data["diffMonthSeries"][1],
						"renderas" : "Line",
						"color" : "0075C2",
						"data" : chart.data["diffCurveMonthChart"][1]
					}];
					
					curveChart("diffCurveMonthChart",dataset,chart.data["diffCurveMonthChart"][0].length > chart.data["diffCurveMonthChart"][1].length ? chart.data["diffCurveMonthChart"][0] : chart.data["diffCurveMonthChart"][1]);
				
	
					var dataset = [ {
						"seriesname" : chart.data["diffMonthSeries"][0],
						"color" : "7500C2",
						"data" : chart.data["diffPreMonthBarChart"]
					},
					{
						"seriesname" : chart.data["diffMonthSeries"][1],
						"color" : "0075C2",
						"data" : chart.data["diffThisMonthBarChart"]
					}];
					
					diffBarChart("diffMonthBarChart",dataset,chart.data["diffPreMonthBarChart"]);
					
					dataset = [ {
						"seriesname" : chart.data["diffDaySeries"][0],
						"color" : "7500C2",
						"data" : chart.data["diffPreDayBarChart"]
					},
					{
						"seriesname" : chart.data["diffDaySeries"][1],
						"color" : "0075C2",
						"data" : chart.data["diffThisDayBarChart"]
					}];
					
					diffBarChart("diffPreDayBarChart",dataset,chart.data["diffPreDayBarChart"]);
					
					
				</s:elseif>
				<s:elseif test='"Prefer".equals(type)'>
					pieChart("categoryPieChart",chart.data["categoryPieChart"],"偏好");
					pieChart("categoryPieConditionChart",chart.data["categoryPieConditionChart"],"");
					pieChart("thisCategoryLocationCrossPieChart",chart.data["thisCategoryLocationCrossPieChart"],"區域");
					pieChart("preCategoryLocationCrossPieChart",chart.data["preCategoryLocationCrossPieChart"],"區域");
				</s:elseif>	
			}
		}
	});
	
	$.ajax({
		type:'POST',
		<s:if test='"".equals(type)'>
			url:'<s:url action="getMapData" namespace="/function"/>',
		</s:if>
		<s:elseif test='"Cross".equals(type)'>
			url:'<s:url action="getCrossMapData" namespace="/function"/>',
		</s:elseif>
		<s:elseif test='"Prefer".equals(type)'>
			url:'<s:url action="getPreferMapData" namespace="/function"/>',
		</s:elseif>	
		data:{
			'app.appId' : '<s:property value="appDataPlus.appId"/>',
			'startDate' : '<s:property value="startDate"/>',
			'endDate' : '<s:property value="endDate"/>',
			'category' : $("#mapCategory").length > 0 ? $("#mapCategory").val():'',
			'timeInterval' : $("#mapTimeInterval").length > 0 ? $("#mapTimeInterval").val():'',
			'weekMonth' : $("#mapWeekMonth").length > 0 ? $("#mapWeekMonth").val():''
		},
		dataType:"json",
		success:function(map){
			
			<s:if test='"".equals(type)'>
				var category = ["morning","noon","night","mid"]
				for (var i=0 ;i<category.length; i++){
					
					map.data[category[i]+"NowMap"].map(function(location, j) {
						return new google.maps.Marker({
				            position: location,
				            icon:'<s:property value="contextPath"/>/images/map/'+category[i]+'.png',
				            map :nowMap
		   				});
			        });
				}
			</s:if>
			<s:elseif test='"Cross".equals(type)'>
				var category = ["morning","noon","night","mid"]
				for (var i=0 ;i<category.length; i++){
					map.data[category[i]+"ThisMap"].map(function(location, j) {
						return new google.maps.Marker({
				            position: location,
				            icon:'<s:property value="contextPath"/>/images/map/'+category[i]+'.png',
				            map :diffMap
		   				});
			        });
					map.data[category[i]+"PreMap"].map(function(location, j) {
						return new google.maps.Marker({
				            position: location,
				            icon:'<s:property value="contextPath"/>/images/map/'+category[i]+'Pre.png',
				            map :diffMap
		   				});
			        });
				}
			</s:elseif>
			<s:elseif test='"Prefer".equals(type)'>
				map.data["preferMap"].map(function(location, j) {
					var marker = new google.maps.Marker({
			            position: location,
			            icon:'<s:property value="contextPath"/>/images/map/'+$("#mapTimeInterval").val()+'.png',
			            map :preferMap
	   				});
					markers.push(marker);
					return marker;
		        });
			</s:elseif>	
		}
	});
});

function smallChart(id,data){
	$("#"+id).insertFusionCharts({
		type : 'column2d',
		width : '100%',
		height : '100',
		id : 'id_'+id,
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"paletteColors" : "#0075c2",
				"showvalues" : "0",
				"divlinealpha" : "30",
				"numdivlines" : "3",
				"showlabels" : "0",
				"showYAxisValues" : "0",
				//"yAxisMaxValue" : "9000",
				"palettecolors" : "008ae6",
				"plotspacepercent" : "0",
				"chartLeftMargin" : "0",
				"chartRightMargin" : "0",
				"plotToolText" : "<div><b>$label<br/>人數: $value</b></div>",
				"theme" : "zune"
			},
			"data" : data
		}
	});
}

function curveChart(id,dataset,category){
	
	var category = {
		"category" : category
	};
	$("#" + id).insertFusionCharts({
		type : 'zoomline',
		id : 'id_' + id,
		width : '100%',
		height : '200',
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"palette" : "2",
				"showvalues" : "0",
				//"yAxisMaxValue": "10000",
				"numDivLines" : "2",
				"plotfillalpha" : "20",
				"lineThickness" : "4",
				"divlinealpha" : "20",
				"formatnumberscale" : "0",
				"labelStep" : "6",
				"palettecolors" : "008ae6",
				"labelDisplay" : "NONE",
				"chartLeftMargin" : "10",
				"chartRightMargin" : "10",
				"chartBottomMargin" : "10",
				"yAxisValuesPadding" : "10",
				"plotToolText" : "<div><b>$label<br/>人數: $value</b></div>",
				"theme" : "zune"
			},
			"categories" : category,
			"dataset" : dataset
		}
	});
}

function barChart(id, data){

	$("#"+id).insertFusionCharts({
		type : 'column2d',
		id : 'id_'+id,
		width : '100%',
		height : '300',
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"chartLeftMargin" : "0",
				"chartRightMargin" : "0",
				"chartBottomMargin" : "0",
				"xAxisName" : "區間",
				"yAxisName" : "人數",
				//"yAxisMaxValue" : "100000",
				"placevaluesInside" : "0",
				"valueFontColor" : "000000",
				"palettecolors" : "008ae6",
				"rotateValues" : "0",
				"showValues" : "1",
				"showLegend" : "1",
				"divLineAlpha" : "30",
				"plotTooltext" : "<b>區間: $label<br/>人數: $value</b>",
				"theme" : "zune"
			},
			"data" : data
		}
	});
}

function diffBarChart(id, dataset, category){

	$("#"+id).insertFusionCharts({
		type : 'mscolumn2d',
		id : 'id_'+id,
		width : '100%',
		height : '300',
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"chartLeftMargin" : "0",
				"chartRightMargin" : "0",
				"chartBottomMargin" : "0",
				"xAxisName" : "區間",
				"yAxisName" : "人數",
				//"yAxisMaxValue" : "100000",
				"placevaluesInside" : "0",
				"valueFontColor" : "000000",
				"palettecolors" : "008ae6",
				"rotateValues" : "0",
				"showValues" : "1",
				"showLegend" : "1",
				"divLineAlpha" : "30",
				"plotTooltext" : "<b>區間: $label<br/>人數: $value</b>",
				"theme" : "zune"
			},
	        "categories": [{
	            "category": category
	        }],
	        "dataset":dataset
		}
	});
}

function pieChart(id, data, word){

	$("#"+id).insertFusionCharts({
		type : 'pie2d',
		id : 'id_'+id,
		width : '100%',
		height : '300',
		dataFormat : 'json',
		dataSource : {
			"chart" : {
				"chartLeftMargin": "0",
		        "chartRightMargin": "0",
		        "chartTopMargin": "0",
		        "chartBottomMargin": "0",
		        "startingAngle": "90",
		        "showValues": "1",
		        "showLegend": "1",
				"plotTooltext" : "<b>"+word+": $label 人數: $value</b><br/>$html",
				"theme" : "zune"
			},
			"data" : data
		}
	});
}

function searchDate(url){
	$("#searchForm").attr("action", url);
	$("#searchForm").find("#appId").val('<s:property value="app.appId"/>');
	$("#searchForm").attr("method", 'post');
	$("#searchForm").submit();
}

function changeTab(url){
	$("#pageForm").attr("action", url);
	$("#pageForm").attr("method", 'post');
	$("#pageForm").submit();
}

function getCategoryPieChartByCondition(){
	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	$.ajax({
		type:'POST',
		url:'<s:url action="getCategoryPieChartByCondition" namespace="/function"/>',
		data:{
			'app.appId' : '<s:property value="appDataPlus.appId"/>',
			'timeInterval' : $("#userTimeInterval").val(),
			'weekMonth' : $("#userWeekMonth").val()
		},
		dataType:"json",
		success:function(chart){
			$("#modalDiv").modal('hide');  
			if(chart.isSuccess){
				$("#categoryPieConditionChart").html("");
				pieChart("categoryPieConditionChart",chart.data,"");
				/* var pieChart = getChartFromId("id_categoryPieConditionChart");
				pieChart.setJSONData(
					{
						"chart" : {
						"chartLeftMargin": "0",
				        "chartRightMargin": "0",
				        "chartTopMargin": "0",
				        "chartBottomMargin": "0",
				        "startingAngle": "90",
				        "showValues": "1",
				        "showLegend": "1",
						"plotTooltext" : "<b>時段: $label 人數: $value</b><br/>$html",
						"theme" : "zune"
						},
						'data':chart.data
					}
				); */
			}
		}
	});
}

function getPreferMapData(){
	$("#modalDiv").modal({backdrop:'static',keyboard:false}); 
	$.ajax({
		type:'POST',
		url:'<s:url action="getPreferMapData" namespace="/function"/>',
		data:{
			'app.appId' : '<s:property value="appDataPlus.appId"/>',
			'category' : $("#mapCategory").val(),
			'timeInterval' : $("#mapTimeInterval").val(),
			'weekMonth' : $("#mapWeekMonth").val()
		},
		dataType:"json",
		success:function(map){
			$("#modalDiv").modal('hide');  
			if(map.isSuccess){
				for (var i = 0; i < markers.length; i++) {
		        	markers[i].setMap(null);
		        }
				markers = [];
				map.data["preferMap"].map(function(location, j) {
					var marker = new google.maps.Marker({
			            position: location,
			            icon:'<s:property value="contextPath"/>/images/map/'+$("#mapTimeInterval").val()+'.png',
			            map :preferMap
	   				});
					markers.push(marker);
					return marker;
		        });
			}
		}
	});
}

function getLocationCrossPieChart(){
	$("#modalDiv").modal({backdrop:'static',keyboard:false});  
	$.ajax({
		type:'POST',
		url:'<s:url action="getLocationCrossPieChart" namespace="/function"/>',
		data:{
			'app.appId' : '<s:property value="appDataPlus.appId"/>',
			'area' : $("#crossArea").val(),
			'startDate' : '<s:property value="startDate"/>',
			'endDate' : '<s:property value="endDate"/>'
		},
		dataType:"json",
		success:function(chart){
			$("#modalDiv").modal('hide');  
			if(chart.isSuccess){
				/* var thisChart = getChartFromId("id_thisCategoryLocationCrossPieChart");
				thisChart.setJSONData(
					{
						"chart" : {
						"chartLeftMargin": "0",
				        "chartRightMargin": "0",
				        "chartTopMargin": "0",
				        "chartBottomMargin": "0",
				        "startingAngle": "90",
				        "showValues": "1",
				        "showLegend": "1",
						"plotTooltext" : "<b>區域: $label 人數: $value</b><br/>$html",
						"theme" : "zune"
						},
						'data':chart.data['thisCategoryLocationCrossPieChart']
					}
				); */
				
				
				$("#thisCategoryLocationCrossPieChart").html("");
				pieChart("thisCategoryLocationCrossPieChart",chart.data,"");
				
				
				/* var preChart = getChartFromId("id_preCategoryLocationCrossPieChart");
				preChart.setJSONData(
					{
						"chart" : {
						"chartLeftMargin": "0",
				        "chartRightMargin": "0",
				        "chartTopMargin": "0",
				        "chartBottomMargin": "0",
				        "startingAngle": "90",
				        "showValues": "1",
				        "showLegend": "1",
						"plotTooltext" : "<b>區域: $label 人數: $value</b><br/>$html",
						"theme" : "zune"
						},
						'data':chart.data['preCategoryLocationCrossPieChart']
					}
				); */
				
				$("#preCategoryLocationCrossPieChart").html("");
				pieChart("preCategoryLocationCrossPieChart",chart.data,"");
			}
		}
	});
}
</script>