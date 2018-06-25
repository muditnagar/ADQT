webApp.controller('profilingController', function($scope,service, $location,$rootScope) {
	
$rootScope.selectedLink ="profiling";

	$scope.selectedTable="Sample";
	$scope.selectedTab = "advance";
	
	$scope.changetab = function(value){
		$scope.selectedTab = value;
	}
	
var renderHighChartCompletnessFinal = function(data){
		
		Highcharts.chart('completness', {

			  title: {
			            text: 'Data Completness',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: data.total
			        },{
			            name: 'Mishmatch Count',
			            data: data.qualified
			        }]
			});
	}
var renderHighChartConsistencyFinal = function(data){
		
		Highcharts.chart('consistency', {

			  title: {
			            text: 'Data Consistency',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: data.total
			        },{
			            name: 'Mismatch Count',
			            data: data.qualified
			        }]
			});
	}
	
var renderHighChartConformityFinal = function(data){
		
		Highcharts.chart('conformity', {

			  title: {
			            text: 'Data Conformity',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: data.total
			        },{
			            name: 'Mismatch Count',
			            data: data.qualified
			        }]
			});
	}
var renderHighChartAccuracyFinal = function(data){
		
		Highcharts.chart('container', {

			  title: {
			            text: 'Data Accuracy',
			            x: -20 //center
			        },
			       
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: data.total
			        },{
			            name: 'Mismatch Count',
			            data: data.qualified
			        }]
			});
	}


	var renderHighChartAccuracy = function(){
		
		Highcharts.chart('container', {

			  title: {
			            text: 'Data Accuracy',
			            x: -20 //center
			        },
			       
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: []
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			        	
			            name: 'Total Count',
			            color: '#6ae1e4',
			            data: [0,0,0,0]
			        },{
			        	
			            name: 'Mismatch Count',
			            color: '#e4736a',
			            data: [0,0,0,0]
			        }]
			});
	}
	
	
	
	var renderHighChartConformity = function(){
		
		Highcharts.chart('conformity', {

			  title: {
			            text: 'Data Conformity',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: [0,0,0,0]
			        },{
			            name: 'Mismatch Count',
			            data: [0,0,0,0]
			        }]
			});
	}

	
	var renderHighChartConsistency = function(){
		
		Highcharts.chart('consistency', {

			  title: {
			            text: 'Data Consistency',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: [0,0,0,0]
			        },{
			            name: 'Final Count',
			            data: [0,0,0,0]
			        }]
			});
	}

	var renderHighChartCompletness = function(){
		
		Highcharts.chart('completness', {

			  title: {
			            text: 'Data Completness',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Count'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Total Count',
			            data: [0,0,0,0]
			        },{
			            name: 'Mismatch Count',
			            data: [0,0,0,0]
			        }]
			});
	}
	
	
	
	
	
	
var renderHighChartAccuracyPer = function(perdata){
		console.log(perdata);
		Highcharts.chart('container_per', {

			  title: {
			            text: 'Data Accuracy',
			            x: -20 //center
			        },
			       
			        yAxis: {
			            title: {
			                text: 'Percentage'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Percentage',
			            data: perdata
			        }]
			});
	}
	
	var renderHighChartConformityPer = function(perdata){
		
		Highcharts.chart('conformity_per', {

			  title: {
			            text: 'Data Conformity',
			            x: -20 //center
			        },
			       
			        yAxis: {
			            title: {
			                text: 'Percentage'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Percentage',
			            data: perdata
			        }]
			});
	}

	
	var renderHighChartConsistencyPer = function(perdata){
		
		Highcharts.chart('consistency_per', {

			  title: {
			            text: 'Data Consistency',
			            x: -20 //center
			        },
			       
			        yAxis: {
			            title: {
			                text: 'Percentage'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Percentage',
			            data: perdata
			        }]
			});
	}

	var renderHighChartCompletnessPer = function(perdata){
		
		Highcharts.chart('completness_per', {

			  title: {
			            text: 'Data Completness',
			            x: -20 //center
			        },
			        
			        yAxis: {
			            title: {
			                text: 'Percentage'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: 'Percentage',
			            data: perdata
			        }]
			});
	}
	
	$scope.rerenderPer =function(){
		renderHighChartCompletnessPer([0,0,0,0]);
		renderHighChartConformityPer([0,0,0,0]);
		renderHighChartAccuracyPer([0,0,0,0]);
		renderHighChartConsistencyPer([0,0,0,0]);
	}
	
	$scope.rerender = function(){
		setTimeout($scope.rerenderPer(),1000);
		setTimeout($scope.rerenderPer(),5000);
	}
	
	$scope.rerenderHighchart =function(){
		renderHighChartCompletness();
		renderHighChartConformity();
		renderHighChartAccuracy();
		renderHighChartConsistency();
		
	}
	$scope.getRandomArray = function(){
		var arr = []
		for(var i = 0; i<10;i++){
			arr.push($scope.generateRandom());
		}
		return arr;
	}
	
		setTimeout($scope.rerenderHighchart(),1000);
		setTimeout($scope.rerenderHighchart(),5000);
	
	
	
	
	
	/*Stomp for socket code
	 * 
	 * */
	
	
	$scope.accuracyData = [];
	$scope.accuracyDataAxis = {
			total:[],
			qualified:[],
			percentage:[],
			belowThreshold:false
	}
	$scope.consistencyData = [];
	$scope.consistencyDataAxis = {
			total:[],
			qualified:[],
			percentage:[]
	}
	$scope.conformityData = [];
	$scope.conformityDataAxis = {
			total:[],
			qualified:[],
			percentage:[]
	}
	$scope.completnessData = [];
	$scope.completnessDataAxis = {
			total:[],
			qualified:[],
			percentage:[]
	}
	$scope.socketConnected = false;
	function connect() {
	    var socket = new SockJS('/dataquality');
	    stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	        /*setConnected(true);*/
	    	
	    	$scope.socketConnected = true;
	    	$scope.$apply();
	        console.log('Connected: ' + frame);
	        stompClient.subscribe('/dataquality/accuracy', function (greeting) {
	            if($scope.accuracyData.length > 10){
	            	$scope.accuracyData.shift();
	            	$scope.accuracyData.push(greeting.body);
	            	
	            }
	            else{
	            	$scope.accuracyData.push(greeting.body);
	            }
	            $scope.accuracyDataAxis = {
	        			total:[],
	        			qualified:[],
	        			percentage:[]
	        	}
	            angular.forEach($scope.accuracyData,function(data){
	            	var parsedJson = JSON.parse(data);
	            	 $scope.accuracyDataAxis.total.push(parsedJson.totalRowCount);
	            	 $scope.accuracyDataAxis.qualified.push(parsedJson.qualifiedRowCount);
	            	 $scope.accuracyDataAxis.percentage.push(parseInt(parsedJson.percentage));
	            })
	            
	            renderHighChartAccuracyFinal($scope.accuracyDataAxis);
	            
	            renderHighChartAccuracyPer($scope.accuracyDataAxis.percentage)
	            
	        });
	        
	        stompClient.subscribe('/dataquality/consistency', function (greeting) {
	        	console.log(greeting);
	        	
	        	if($scope.consistencyData.length > 10){
	            	$scope.consistencyData.shift();
	            	$scope.consistencyData.push(greeting.body);
	            	
	            }
	            else{
	            	$scope.consistencyData.push(greeting.body);
	            }
	            $scope.accuracyDataAxis = {
	        			total:[],
	        			qualified:[],
	        			percentage:[]
	        	}
	            angular.forEach($scope.consistencyData,function(data){
	            	var parsedJson = JSON.parse(data);
	            	 $scope.consistencyDataAxis.total.push(parsedJson.totalRowCount);
	            	 $scope.consistencyDataAxis.qualified.push(parsedJson.qualifiedRowCount);
	            	 $scope.consistencyDataAxis.percentage.push(parseInt(parsedJson.percentage));
	            })
	            
	            renderHighChartConsistencyFinal($scope.consistencyDataAxis);
	            renderHighChartConsistencyPer($scope.consistencyDataAxis.percentage)
	            
	        });
	        
	        stompClient.subscribe('/dataquality/conformity', function (greeting) {
	        	console.log(greeting);
	        	
	        	if($scope.conformityData.length > 10){
	            	$scope.conformityData.shift();
	            	$scope.conformityData.push(greeting.body);
	            	
	            }
	            else{
	            	$scope.conformityData.push(greeting.body);
	            }
	            $scope.accuracyDataAxis = {
	        			total:[],
	        			qualified:[],
	        			percentage:[]
	        	}
	            angular.forEach($scope.conformityData,function(data){
	            	var parsedJson = JSON.parse(data);
	            	 $scope.conformityDataAxis.total.push(parsedJson.totalRowCount);
	            	 $scope.conformityDataAxis.qualified.push(parsedJson.qualifiedRowCount);
	            	 $scope.conformityDataAxis.percentage.push(parseInt(parsedJson.percentage));
	            })
	            
	            renderHighChartConformityFinal($scope.conformityDataAxis);
	            renderHighChartConformityPer($scope.conformityDataAxis.percentage);
	        });
	        
	        stompClient.subscribe('/dataquality/completness', function (greeting) {
	        	console.log(greeting);
	        	if($scope.completnessData.length > 10){
	            	$scope.completnessData.shift();
	            	$scope.completnessData.push(greeting.body);
	            	
	            }
	            else{
	            	$scope.completnessData.push(greeting.body);
	            }
	            $scope.completnessDataAxis = {
	        			total:[],
	        			qualified:[],
	        			percentage:[]
	        	}
	            angular.forEach($scope.completnessData,function(data){
	            	var parsedJson = JSON.parse(data);
	            	$scope.completnessDataAxis.total.push(parsedJson.totalRowCount);
	            	$scope.completnessDataAxis.qualified.push(parsedJson.qualifiedRowCount);
	            	$scope.completnessDataAxis.percentage.push(parseInt(parsedJson.percentage));
	            })
	            
	            renderHighChartCompletnessFinal($scope.completnessDataAxis);
	            renderHighChartCompletnessPer($scope.completnessDataAxis.percentage)
	        });
	    });
	}

	
	function disconnect() {
	    if (stompClient !== null) {
	        stompClient.disconnect();
	        $scope.socketConnected = false;
	    }
	    /*setConnected(false);*/
	    console.log("Disconnected");
	}
	
	connect();
	
	
	$scope.connectSocket = function(){
		connect();
	}
});