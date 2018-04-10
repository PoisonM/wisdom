angular.module('controllers',[]).controller('shareHomeCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetUserInfoByOpenId','Global','$ionicPopup',
        function ($scope,$rootScope,$stateParams,$state,GetUserInfoByOpenId,Global,$ionicPopup) {

            $rootScope.title = "分享赚钱";

            $scope.goSharePage = function()
            {
                GetUserInfoByOpenId.get(function(data){

                    if(data.result==Global.SUCCESS)
                    {
                        if(data.responseData.userType=="business-A-1"||data.responseData.userType=="business-B-1"){
                            $state.go("sharePage");
                        }else
                        {
                            var alertPopup = $ionicPopup.alert({
                                template: '<span style="font-size: 0.3rem;color: #333333;"> 立做美享店主，坐拥75%返利</span>',
                                buttons: [
                                    {
                                        text: '不用啦'
                                    },
                                    {
                                        onTap: function() {
                                            $state.go("shopHome")
                                        },
                                        text: '成为店主',
                                        type: 'button-calm'
                                    }
                                ]

                            });
                            // alert("亲，请先升级为我们的铂金店主或者钻石店主");
                        }
                    }
                    else{
                        var alertPopup = $ionicPopup.alert({
                            template: '<span style="font-size: 0.3rem;color: #333333;">立做美享店主，坐拥75%返利</span>',
                            buttons: [
                                {
                                    text: '不用啦'
                                },
                                {
                                    onTap: function() {
                                        $state.go("shopHome")
                                    },
                                    text: '成为店主',
                                    type: 'button-calm'
                                }
                            ]

                        });
                        // alert("亲，请先升级为我们的铂金店主或者钻石店主");
                    }
                })

            }
}])