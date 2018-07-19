PADWeb.controller("projectCtrl", function($scope, $state, $stateParams,$rootScope,OneLevelProject,TwoLevelProject,ThreeLevelProject) {
    /*-------------------------------------------定义头部/左边信息--------------------------------*/
    $scope.$parent.$parent.param.top_bottomSelect = "jiamubiao";
    $scope.$parent.$parent.param.headerPrice.blackTitle = "项目";
    $scope.$parent.param.priceType = "xm"
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
        projectAppear:false,
        parentSty:"",
        childrenFlag:"",
        selectSingleList:{},//导航栏商品list
        project2List:{},//点击导航栏弹出div内的商品list
        threeList:{},//商品的list
        projectTypeOneId:"",//一级项目id
        projectTypeTwoId:"",//二级项目id
        projectName:"",
        pageSize:"100",
        chooseProjectItem:""
    };
    //查询已启用状态的项目
    $scope.status = '0';

    /*一级项目列表接口*/
    $rootScope.loadingFlag = true;
    OneLevelProject.get(({
        status:$scope.status
    }),function (data) {
        if(data.responseData.length>0){
            $scope.selectSingleList=data.responseData;
            $scope.selectSingleList[0].status=3;//给一个值用来点击切换图片的时候图片的样式
            $scope.selection(0,data.responseData[0].id) //获取二级为了调去3级默认选择
        }
        $rootScope.loadingFlag = false;
    });
    //点击二级列表调取三级项目列表产品数据方法
    $scope.refreshGoods=function (id) {
        $rootScope.loadingFlag = true;
        $scope.param.chooseProjectItem = id;
        ThreeLevelProject.get({
            projectTypeTwoId:id,
            projectTypeOneId: $scope.param.projectTypeOneId,
            projectName:$scope.param.projectName,
            pageSize:$scope.param.pageSize,
            status:$scope.status
        },function (data) {
            if(data.responseData.length>0){
                $scope.threeList=data.responseData;
            }
            $rootScope.loadingFlag = false;
        })
    };

    /*点击图片显示内容*/
    $scope.checkImg = function (index,status,id) {
        $scope.param.projectTypeOneId=id;
        $rootScope.loadingFlag = true;
        /*二级产品列表接口*/
        TwoLevelProject.get({id:id,status:$scope.status},function (data) {
            if(data.result="0x00001"){
                $rootScope.loadingFlag = false;
                if(data.responseData.length>0){
                    $scope.project2List=data.responseData;
                }
            }
        });
        if($scope.param.childrenFlag == index){
            for(var i = 0; i < $scope.selectSingleList.length; i++ ){
                $scope.selectSingleList[i].status = 1
            }
            if(status == 1){
                $scope.selectSingleList[index].status = 4;
                $scope.param.projectAppear = true;
            }
            if(status == 4){
                $scope.selectSingleList[index].status = 3;
                $scope.param.projectAppear = false;
            }
            if(status == 3){
                $scope.selectSingleList[index].status = 4;
                $scope.param.projectAppear = true;
            }
        }
    };

    $scope.goProjectDetails=function (id) {
        $state.go("pad-web.projectDetails",{id:id})
    };

    $scope.selection  = function (index,oneId) {
        TwoLevelProject.get({id:oneId,status:$scope.status},function (data) {
            $scope.project2List=data.responseData;
            // $scope.param.chooseProjectItem = $scope.project2List[0].id//默认选中第一个
            $scope.param.projectAppear=false;

            //默认调去三级展示
            ThreeLevelProject.get({
                // ProjectTypeTwoId:data.responseData[0].id,
                projectTypeTwoId:"",//默认查一级下面所有的三级 不需要二级id
                projectTypeOneId:oneId,
                projectName:$scope.param.projectName,
                pageSize:$scope.param.pageSize,
                status:$scope.status
            },function (data) {
                if(data.result="0x00001"){
                    $scope.threeList=data.responseData;
                }
                $rootScope.loadingFlag = false;
            });
        });
        $scope.param.childrenFlag = index;
        for(var i = 0; i < $scope.selectSingleList.length; i++ ){
            $scope.selectSingleList[i].status = 1
        }
        $scope.param.selection = index;
        $scope.selectSingleList[index].status = 3
    };
});