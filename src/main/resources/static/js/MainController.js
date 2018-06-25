webApp.controller('MainController', function($scope, $location,$rootScope) {
    
	 if(null != localStorage.getItem("userId") && null != localStorage.getItem("userEmail")) {
		 $rootScope.isLoggedIn = true;
	 }
	 
	 $scope.logout = function() {
		 $rootScope.removeLocalStorageVariables(true);
	 }
	 
	 $scope.login =function() {
		 $rootScope.selectedLink = "login";
		 $location.path('/login').replace();
	 }
	 
	 $scope.about =function() {
		 $rootScope.selectedLink = "about";
		 $location.path('/about').replace(); 
	 }

	$scope.home = function() {
		$rootScope.selectedLink = "home";
		$location.path('/profile').replace();
	}
	
	$scope.configure = function() {
		$rootScope.selectedLink = "configure";
		$location.path('/configure').replace();
	}
	
	$scope.profile = function() {
		$rootScope.selectedLink = "profiling";
		$location.path('/profiling').replace();
	}
	
	
});