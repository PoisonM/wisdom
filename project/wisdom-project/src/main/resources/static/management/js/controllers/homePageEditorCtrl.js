angular.module('controllers',[]).controller('homePageEditorCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout) {

             $scope.show = false;

            $scope.createImage = function(){
                $state.go("homeImageUpload",{id:0});

             };

             $scope.loadPageList  = function(){

             };
            $scope.detailPageList = function () {
                    var page = {
                        pageNo:$scope.pageNum,
                        pageSize:$scope.pageSize,
                        requestData:{
                        },
                        isExportExcel:"N",
                    };

                };
            $scope.updateImageInfo = function(id){
                $state.go("homeImageUpload",{id:id})

            }

            $scope.remove = function(id){

            }
        }]);
