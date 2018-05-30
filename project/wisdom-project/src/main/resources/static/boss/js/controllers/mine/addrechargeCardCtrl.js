/**
 * Created by Administrator on 2018/5/5.
 */
angular.module('controllers',[]).controller('addrechargeCardCtrl',
    ['$scope','$rootScope','$stateParams','$state','SaveRechargeCardInfo','Global',
        function ($scope,$rootScope,$stateParams,$state,SaveRechargeCardInfo,Global) {

            $rootScope.title = "添加充值卡";
            $scope.param={
                appearArr:[false,false,false],
                status:true
            };
            $rootScope.settingAddsome.editedRecharge={
                name:'',
                amount:"",
                imageUrls:[],
                introduce:'',
                status:'0',
                timesList:[],/*次卡数组id*/
                periodList:[],/*疗程卡数组id*/
                productList:[]/*产品数组id*/,
                timeDiscount:'',/*单次折扣*/
                periodDiscount:'',/*疗程卡折扣*/
                productDiscount:''/*产品折扣*/


            }

            $scope. appear=function (index) {
               $scope.param.appearArr[index ] =!$scope.param.appearArr[index ]
            }
            $scope.discount = function(style){
                $rootScope.settingAddsome.editedRecharge[style] = $rootScope.settingAddsome.editedRecharge[style].replace(/[^\d]/g,'')

            }
            $scope.discounts = function (style) {
                if($rootScope.settingAddsome.editedRecharge[style]>10||$rootScope.settingAddsome.editedRecharge[style]<0.1){
                    $rootScope.settingAddsome.editedRecharge[style] =0.1
                }
            }
            $scope.save = function () {
                if($scope.param.status == true){
                    $rootScope.settingAddsome.editedRecharge.status = '0'
                }else{
                    $rootScope.settingAddsome.editedRecharge.status = '1'
                }


                if($rootScope.settingAddsome.editedRecharge.name==""||$rootScope.settingAddsome.editedRecharge.amount==""||($rootScope.settingAddsome.editedRecharge.timesList.length>0&&$rootScope.settingAddsome.editedRecharge.timeDiscount=='')||($rootScope.settingAddsome.editedRecharge.timesList.length<=0&&$rootScope.settingAddsome.editedRecharge.timeDiscount!=''||( $rootScope.settingAddsome.editedRecharge.timesList.periodList>0&&$rootScope.settingAddsome.editedRecharge.periodDiscount=='')||($rootScope.settingAddsome.editedRecharge.timesList.periodList<=0&&$rootScope.settingAddsome.editedRecharge.periodDiscount!='')||($rootScope.settingAddsome.editedRecharge.productList.length>0&&$rootScope.settingAddsome.editedRecharge.productDiscount=='')||($rootScope.settingAddsome.editedRecharge.productList.length<=0&&$rootScope.settingAddsome.editedRecharge.productDiscount!=''))||($rootScope.settingAddsome.editedRecharge.productDiscount==''&&$rootScope.settingAddsome.editedRecharge.periodDiscount==''&&$rootScope.settingAddsome.editedRecharge.timeDiscount=='')){
                    alert("信息不完全")
                    return
                }
                SaveRechargeCardInfo.save($rootScope.settingAddsome.editedRecharge, function (data) {
                    if(data.result==Global.SUCCESS){
                        $state.go("basicSetting")
                    }

                })
            }

        }]);