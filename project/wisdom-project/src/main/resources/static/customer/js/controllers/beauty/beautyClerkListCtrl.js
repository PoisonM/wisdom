/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('beautyClerkListCtrl',
    ['$scope','$rootScope','$stateParams','$state',
        function ($scope,$rootScope,$stateParams,$state) {

            $scope.param = {

                clerkList : [
                    {
                        clerkName:"苗向阳",
                        clerkImg : "http://thirdwx.qlogo.cn/mmopen/HhRfwkaJkMuUzMJUhZOhicVYGMQkRGtfMo1ZotlLP6wgQaTBibYGviclPticuqrJZae8hyCVCmLibZc06dz9vLUmWmnWjqqDRk5X9/132",
                        clerkProgressStyle : {
                            width: "30%",
                            background: "red",
                            height:"10px"
                        }
                    },
                    {
                        clerkName:"苗向阳",
                        clerkImg : "http://thirdwx.qlogo.cn/mmopen/HhRfwkaJkMuUzMJUhZOhicVYGMQkRGtfMo1ZotlLP6wgQaTBibYGviclPticuqrJZae8hyCVCmLibZc06dz9vLUmWmnWjqqDRk5X9/132",
                        clerkProgressStyle : {
                            width: "60%",
                            background: "red",
                            height:"10px"
                        }
                    },
                    {
                        clerkName:"苗向阳",
                        clerkImg : "http://thirdwx.qlogo.cn/mmopen/HhRfwkaJkMuUzMJUhZOhicVYGMQkRGtfMo1ZotlLP6wgQaTBibYGviclPticuqrJZae8hyCVCmLibZc06dz9vLUmWmnWjqqDRk5X9/132",
                        clerkProgressStyle : {
                            width: "80%",
                            background: "red",
                            height:"10px"
                        }
                    }
                ]
            }

}])