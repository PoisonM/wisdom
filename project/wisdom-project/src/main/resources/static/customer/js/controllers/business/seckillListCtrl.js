angular.module('controllers',[]).controller('seckillListCtrl',
    ['$scope','$rootScope','$stateParams','$state','SeckillList',
        function ($scope,$rootScope,$stateParams,$state,SeckillList) {
            $scope.$on('$ionicView.enter', function(){
                SeckillList.save(function (data) {
                    console.log(data)
                })
            });
        }]);