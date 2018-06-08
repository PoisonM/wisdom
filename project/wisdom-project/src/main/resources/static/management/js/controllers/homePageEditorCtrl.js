angular.module('controllers',[]).controller('homePageEditorCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$timeout','DelHomeBannerById','GetHomeBannerList','UpdateHomeBannerRank',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$timeout,DelHomeBannerById,GetHomeBannerList,UpdateHomeBannerRank) {

             $scope.show = false;
             $scope.bannerList=[];

            //新增
            $scope.createImage = function(){
                $state.go("homeImageUpload",{bannerId:0});
             };

             $scope.loadPageList  = function(){
                GetHomeBannerList.get(function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.bannerList = data.responseData;
                        }else{
                            alert("无查询数据！");
                        }

                    })
             };
            $scope.detailPageList = function () {
                GetHomeBannerList.get(function(data){
                    if(data.result == Global.SUCCESS){
                        $scope.bannerList = data.responseData;
                    }else{
                        alert("无查询数据！");
                    }

                })
             };

            //更新banner
            $scope.updateImageInfo = function(bannerId){
                $state.go("homeImageUpload",{bannerId:bannerId})
            }


            //删除banner图
            $scope.remove = function(bannerId){
                DelHomeBannerById.get({bannerId:bannerId},function(data){

                    if (data.result == Global.SUCCESS) {
                        /*$scope.detailPageList();*/
                        alert("删除成功");
                        $state.reload('app.toMenu');
                    }else{
                        alert("删除失败");
                        $scope.detailPageList();
                    }

                })
            };

            //上移下移
            $scope.upAndDownBanner = function(bannerId,status){
                UpdateHomeBannerRank.get({bannerId:bannerId,status:status},function(data){
                    if (data.result == Global.SUCCESS) {
                        alert("移动成功");
                        $scope.detailPageList();
                    }else{
                        alert("移动失败");
                        $scope.detailPageList();
                    }

                })
            }


        }]);
