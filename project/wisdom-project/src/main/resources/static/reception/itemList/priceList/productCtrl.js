PADWeb.controller("productCtrl", function($scope, $state, $stateParams,OneLevelProduct,TwoLevelProduct,ThreeLevelProduct) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.param.priceType = "cp"
    $scope.$parent.$parent.param.headerPrice.blackTitle = "产品";
    $scope.flagFn = function (bool) {
        //左
        $scope.$parent.mainLeftSwitch.peopleListFlag = !bool;
        $scope.$parent.mainLeftSwitch.priceListFlag = bool;
        //头
        $scope.$parent.$parent.mainSwitch.headerReservationAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerCashAllFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListAllFlag = bool;
        $scope.$parent.$parent.mainSwitch.headerLoginFlag = !bool;
        $scope.$parent.$parent.mainSwitch.headerPriceListBlackFlag = bool
    };
    /*打开收银头部/档案头部/我的头部*/
    $scope.flagFn(true);

    $scope.param = {
        selection:"0",
        productAppear:false,
        parentSty:"",
        childrenFlag:"",
        selectSingleData:{},//产品的一级商品列表
        product2List:{},//点击一级产品列表出现二级列表
        product3List:{},//最终展示的商品列表
        pageSize:"10",//页码大小
        productName:"",//产品名称
        productTypeOneId:"",//一级产品id
        productTypeTwoId:"",//二级产品id
    };


    //一级商品列表接口
    OneLevelProduct.get(function (data) {
       $scope.selectSingleData=data.responseData;
        $scope.selectSingleData[0].status=3;
        console.log(data);
        $scope.selection(0,data.responseData[0].id)
        // $scope.selection(0,"2")
    });
    $scope.checkImg = function (index,status,id) {
        $scope.param.productTypeOneId=id;
        //点击一级列表图标调取二级列表接口
        TwoLevelProduct.get({id:id},function (data) {
            $scope.product2List=data.responseData;
            console.log(data)
        });
        if($scope.param.childrenFlag == index){
            for(var i = 0; i < $scope.selectSingleData.length; i++ ){
                $scope.selectSingleData[i].status = 1
            }
            if(status == 1){
                $scope.selectSingleData[index].status = 4;
                $scope.param.productAppear = true;
            }
            if(status == 4){
                $scope.selectSingleData[index].status = 3;
                $scope.param.productAppear = false;
            }
            if(status == 3){
                $scope.selectSingleData[index].status = 4;
                $scope.param.productAppear = true;
            }
        }
    };
    //点击二级列表调取三级商品接口
    $scope.goThreeList=function (id) {
        ThreeLevelProduct.get({
            pageSize:$scope.param.pageSize,
            productTypeOneId:$scope.param.productTypeOneId,
            productTypeTwoId:id,
            productName:$scope.param.productName
        },function (data) {
            $scope.product3List = data.responseData;
         $scope.param.productAppear=false;
        })
    };
    $scope.goProductDetails=function (id) {
        $state.go("pad-web.productDetails",{id:id})
    };

    $scope.selection  = function (index,id) {
        TwoLevelProduct.get({id:id},function (data) {
            $scope.product2List=data.responseData;
            console.log(data);
            ThreeLevelProduct.get({
                pageSize:$scope.param.pageSize,
                productTypeOneId:"2",productTypeTwoId:"3",
                productName:$scope.param.productName
            },function (data) {
                $scope.product3List=data.responseData;
                $scope.param.productAppear=false;
            })
        });
        $scope.param.childrenFlag = index;
        for(var i = 0; i < $scope.selectSingleData.length; i++ ){
            $scope.selectSingleData[i].status = 1
        }
        $scope.param.selection = index;
        $scope.selectSingleData[index].status = 3
    }
});