webApp.service('service', function($http,$q,$log,$rootScope){
	
	this.signup = function(data){
        var deferred = $q.defer();
        $http({method:"PUT", url:"/app/unsecured/signup", data:data}).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        return deferred.promise
    };
    
    this.login = function(obj){
        var deferred = $q.defer();
        $http({
            method:"POST", 
            url:"/app/unsecured/login",
            data : obj
            /*headers: {
                Authorization: "Basic " + btoa(email + ":" + password)
            }*/
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
    
    this.getUserData = function(userId){
        var deferred = $q.defer();
        $http({
            method:"GET", 
            url:"/api/user/"+userId+"/profile"
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
    
    
    /*Table Schema Config
     * 
     * */
    
    this.saveSchema = function(obj){
        var deferred = $q.defer();
        $http({
            method:"POST", 
            url:"/api/table",
            data : obj
            /*headers: {
                Authorization: "Basic " + btoa(email + ":" + password)
            }*/
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
    
    this.getAllTable = function(){
        var deferred = $q.defer();
        $http({
            method:"GET", 
            url:"/api/table"
            /*headers: {
                Authorization: "Basic " + btoa(email + ":" + password)
            }*/
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
    
    
    this.saveRuleMapping = function(obj){
        var deferred = $q.defer();
        $http({
            method:"POST", 
            url:"/api/ruleValue",
            data : obj
            /*headers: {
                Authorization: "Basic " + btoa(email + ":" + password)
            }*/
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
    
    this.startProfiling = function(tableName){
        var deferred = $q.defer();
        $http({
            method:"GET", 
            url:"/api/profiling/"+tableName,
            /*headers: {
                Authorization: "Basic " + btoa(email + ":" + password)
            }*/
        }).
        then(function(data,status,header,config){
            deferred.resolve(data);
        },function(error){
        	 deferred.reject(error);
        });
        
        return deferred.promise
    };
});
