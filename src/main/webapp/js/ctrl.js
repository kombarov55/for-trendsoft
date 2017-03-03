var app = angular.module("app", []);
app.controller("ctrl", function($scope, $http) {
	//view search create
	$scope.currentState = "view";
	$scope.newsFeed = [];
	$scope.categories = [];
	$scope.filteredNewsFeed = [];


	$http.get("http://localhost:8080/news/").then(function(resp) {
		$scope.newsFeed = resp.data;
	});

	$http.get("http://localhost:8080/categories/").then(function(resp) {
		$scope.categories = resp.data;
	});

	$scope.creatingObject = new News();
	$scope.searchingObject = new News();
	$scope.categorySearchingObject = [];

	
	function News(heading = "", content = "", category = new Category(), date = new Date()) {
		this.heading = heading;
		this.content = content;
		this.category = category;
		this.date = date;
	}

	function Category(name = "") {
		this.id = 1;
		this.name = name;
	}

	$scope.createButtonClicked = function() {
		if (isCreatingObjectEmpty()) {
			alert("Пожалуйста заполните все формы");
			return;
		}

		sendNewsToServer($scope.creatingObject);
		$scope.newsFeed.push($scope.creatingObject);
		$scope.creatingObject = new News();
		$scope.currentState = "view";
	}

	function isCreatingObjectEmpty() {
		return ($scope.creatingObject.heading == "") || ($scope.creatingObject.content == "") || ($scope.creatingObject.category == null);
	}

	function sendNewsToServer(news) {
		$http.post("http://localhost:8080/news/", news);
	}

	$scope.getStrDate = function(date) {
		return "" 
		+ date.getFullYear() 
		+ "." + (date.getMonth() + 1) 
		+ "." + date.getDate() 
		+ "  " 
		+ date.getHours() 
		+ ":" + date.getMinutes() 
		+ ":" + date.getSeconds();
	}

	$scope.saveButtonClicked = function() {
		$scope.currentState = "view";
		sendUpdateToServer($scope.updatingObject);
	}

	function sendUpdateToServer(news) {
		$http.put("http://localhost:8080/news/", news);
	}

	$scope.updateButtonClicked = function(news) {
		$scope.currentState = 'update';
		$scope.updatingObject = news;
	}

	$scope.deleteButtonClicked = function(news) {
		deleteNewsFromServer(news);
		deleteNews(news);
	}

	function deleteNewsFromServer(news) {
		$http.delete("http://localhost:8080/news/" + news.heading);

	}

	function deleteNews(news) {
		var match = _.find($scope.newsFeed, function(elem) {return elem.heading == news.heading});	
 			$scope.newsFeed = _.filter($scope.newsFeed, function(element) {
			return element.heading != news.heading;
		});
	}

	$scope.searchButtonClicked = function() {
		$scope.currentState = 'filtered';
		$scope.filteredNewsFeed = $scope.newsFeed;
		$scope.filteredNewsFeed = _.filter($scope.filteredNewsFeed, function(elem) {
			return (elem.heading == $scope.searchingObject.heading) 
			|| elem.content == $scope.searchingObject.content
			|| elem.category.name == $scope.searchingObject.category.name;
		});
		$scope.searchingObject = new News();
	}

});
