webApp.controller('configureSchema', function($scope,service, $location,$rootScope,service) {
	
	$scope.profilingCompleted = false;
	$rootScope.selectedLink ="configure";
	  $scope.pageNumber = 1;
	  $scope.visitNext = function(){
		  $scope.pageNumber += 1;
	  }
	  
	  $scope.visitPrevious = function(){
		  $scope.pageNumber -= 1;
	  }
	  
	  $scope.setpage= function(number){
		  $scope.pageNumber = number;
	  }
	  
	  $scope.getAllTable = function(){
		  service.getAllTable().then(function(data){
			  $scope.allTables = data.data;
		  })
	  }
	  $scope.tableSchema ={
			  tableName:"",
			  columnInfoList:[{columnName:"",dataType:"STRING"}]
	  }
	  
	  $scope.addNewColumns = function(columns){
		  columns.push({columnName:"",dataType:"STRING"});
	  }
	  
	  $scope.supportedDataType =["INTEGER","FLOAT","STRING","RECORD","BYTES","TIME","TIMESTAMP","DATETIME","BOOLEAN","DATE"];
	
	  $scope.supportedRule = ["ACCURACY","COMPLETNESS","CONFORMITY","CONSISTENCY"];
	  
	  $scope.savetableSchema =function(tableSchema){

	  }
	  
	  $scope.addNewMapping = function(ruleList){
		  ruleList.push({columnId:-1,ruleType:"ACCURACY",ruleExpression:"",tableId:$scope.recievedSchema.tableId})
	  }
	  
	  $scope.recievedSchema = {};
	  $scope.ruleMappingList = []
	
	  $scope.saveNewSchema =function(schema){
		  service.saveSchema(schema).then(function(data){
			  $scope.recievedSchema= data.data;
			  $scope.visitNext();
		  },function(error){
			  console.log(error);
		  })
	  }
	  
	  $scope.getColumnName=function(columnList,columnId){
		  
		  var name="";
		  angular.forEach(columnList,function(column){
			  if(column.columnId == columnId){
				  name = column.columnName;
				  return name;
			  }
		  })
		  return name;
	  }
	  
	  $scope.saveMapping=function(ruleMappingList){
		  
		  angular.forEach(ruleMappingList,function(rule){
			  rule.columnId = rule.columnId.columnId;
		  })
		  service.saveRuleMapping(ruleMappingList).then(function(data){
			  $scope.visitNext();
		  },function(error){
			  
		  });
	  }

	  $scope.startProfiling=function(tableName){
		  $scope.profilingCompleted = true;
		  service.startProfiling(tableName.tableName).then(function(data){
			  $rootScope.profilingTableName = tableName.tableName;
			  $location.path('/profiling').replace();
			  $scope.profilingCompleted = false;
			  
			  
		  },function(error){
			  $location.path('/profiling').replace();
			  $rootScope.profilingTableName = tableName.tableName;
			  $scope.profilingCompleted = false;
		  })
	  }
});