/**
 * Created by Administrator on 2017/12/15.
 */
angular.module('controllers',[]).controller('mxShopTestCtrl',
    ['$scope','$rootScope','$http','$stateParams','$state',"MxShopTest",
        function ($scope,$rootScope,$http,$stateParams,$state,MxShopTest) {

            $http.get('/weixin/customer/mxShopTest?shopId=1').success(function(data){
                location.href = data.responseData;
            });

        //     $http({
        //         url:'/weixin/customer/mxShopTest?shopId=1',
        //         method:"GET",
        //         headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        // })


        }])