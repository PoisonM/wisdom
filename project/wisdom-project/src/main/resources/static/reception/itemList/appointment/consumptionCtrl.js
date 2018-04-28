function consumption($scope,ngDialog,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,GetRechargeCardList,ThreeLevelProject,productInfoThreeLevelProject,GetUserShopProjectList,ConsumeCourseCard,GetShopClerkList){

    $scope.ngDialog = ngDialog;
    $scope.selsectConsumption = function(type){
        $scope.param.consumptionObj.singleByshopId.detailProject=[];
        $scope.param.consumptionObj.consumptionType = type;
        if(type == "selectSingle"){/*单次*/
            ngDialog.open({
                template: 'selectSingle',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    SearchShopProjectList.get({
                        filterStr:"",
                        useStyle:"1"
                    },function(data){
                        $scope.param.consumptionObj.singleByshopId.detailLevel = data.responseData.detailLevel;/*一二级数据*/
                    });
                    $scope.close = function(type) {

                        if(type== 1){

                        }
                        $scope.closeThisDialog();
                    };
                }],
                className: 'ngdialog-theme-default ngdialog-theme-custom'
            });
        }else if (type=="selectTreatmentCard"){/*疗程卡*/
            ngDialog.open({
                template: 'selectTreatmentCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    SearchShopProjectList.get({
                        filterStr:"",
                        useStyle:"0"
                    },function(data){
                        $scope.param.consumptionObj.singleByshopId.detailLevel = data.responseData.detailLevel;/*一二级数据*/
                    });
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="selectProduct"){/*产品*/
            ngDialog.open({
                template: 'selectProduct',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.param.consumptionObj.singleByshopId.detailProjec = [];
                    SearchShopProductList.get({
                        filterStr:""
                    },function(data){
                        $scope.param.consumptionObj.singleByshopId.detailLevel = data.responseData.detailLevel;
                    })
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }else if (type=="collectionCard"){/*套卡*/
            ngDialog.open({
                template: 'collectionCard',
                scope: $scope, //这样就可以传递参数
                controller: ['$scope', '$interval', function($scope, $interval) {
                    $scope.selectCollectionCardDataPic =[];
                    $scope.collectionCard =[];
                    $scope.param.consumptionObj.singleFilterStr = "";
                    GetShopProjectGroups.get({
                        projectGroupName:"",
                        pageSize:100
                    },function(data){
                        $scope.param.consumptionObj.collectionCardByShowId= data.responseData;
                        for(var i=0;i<$scope.param.consumptionObj.collectionCardByShowId.length;i++){
                            $scope.selectCollectionCardDataPic[i] = 'images/bt_Single%20election_nor_.png';
                            $scope.collectionCard[i] = true;
                        }
                    });
                    collectionCardCtrl && collectionCardCtrl($scope,GetShopProjectGroups);
                    $scope.close = function() {
                        $scope.closeThisDialog();
                    };
                }],
                className: 'selectContent ngdialog-theme-custom'
            });
        }
    }
    $scope.subtractOrAdd = function(type){
        if(type == 0){
            if($scope.param.num == 1)return
            $scope.param.num--
        }else{
            $scope.param.num++
        }
    };

    $scope.candel = function(type,index){
        if(type=="singleByUserId"){

            console.log(index);
            $scope.param.consumptionObj.singleByUserId.splice(index,1);
            if($scope.param.consumptionObj.singleByUserId.length<=0){
                $scope.param.consumptionObj.singleByUserIdFlag = false;
            }
        } else if(type=="treatmentCardByUserId"){
            $scope.param.consumptionObj.treatmentCardByUserId.splice(index,1)
            if($scope.param.consumptionObj.treatmentCardByUserId.length<=0){
                $scope.param.consumptionObj.treatmentCardByUserIdFlag = false;
            }
        }else if(type=="productByUserId"){
            $scope.param.consumptionObj.productByUserId.splice(index,1)
            if($scope.param.consumptionObj.productByUserId.length<=0){
                $scope.param.consumptionObj.productByUserIdFlag = false;
            }
        }else if(type=="collectionCardByUserId"){
            $scope.param.consumptionObj.collectionCardByUserId.splice(index,1);
            if($scope.param.consumptionObj.collectionCardByUserId.length<=0){
                $scope.param.consumptionObj.collectionCardByUserIdFlag = false;
            }
        }

    };
    balancePrepaid && balancePrepaid ($scope,ngDialog,GetRechargeCardList)/*余额充值*/
    givingChange && givingChange($scope,ngDialog);/*赠送*/
    relatedStaffCtrl && relatedStaffCtrl($scope,ngDialog);/*关联员工*/

    payTypeCtrl && payTypeCtrl($scope,ngDialog,GetUserShopProjectList);/*消费-消费-下一步*/
    scratchCard && scratchCard($scope,ngDialog,ConsumeCourseCard,GetShopClerkList);
    selectSingleCtrl && selectSingleCtrl($scope);/*单次*/
    searchConsumption && searchConsumption($scope,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,ThreeLevelProject,productInfoThreeLevelProject);/*搜索单次 疗程卡 套卡*/
    selectTreatmentCardCtrl && selectTreatmentCardCtrl($scope);/*套卡*/

}
