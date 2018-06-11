PADWeb.controller("projectCtrl", function($scope, $state, $stateParams,OneLevelProject,TwoLevelProject,ThreeLevelProject) {
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
        ProjectTypeTwoId:"",//二级项目id
        projectName:"",
        pageSize:"10"
    };

    /*一级项目列表接口*/
    OneLevelProject.get(function (data) {
        $scope.selectSingleList=data.responseData;
        $scope.selectSingleList[0].status=3;//给一个值用来点击切换图片的时候图片的样式
        $scope.selection(0,data.responseData[0].id) //获取二级为了调去3级默认选择
    });

    //点击二级列表调取三级项目列表产品数据方法
    $scope.refreshGoods=function (id) {
        ThreeLevelProject.get({
            ProjectTypeTwoId:id,
            projectTypeOneId: $scope.param.projectTypeOneId,
            projectName:$scope.param.projectName,
            pageSize:$scope.param.pageSize
        },function (data) {
            $scope.threeList=data.responseData;
            $scope.param.projectAppear=false;
        })
    };

    /*点击图片显示内容*/
    $scope.checkImg = function (index,status,id) {
        $scope.param.projectTypeOneId=id;
        /*二级产品列表接口*/
        TwoLevelProject.get({id:id},function (data) {
            $scope.project2List=data.responseData;
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
        TwoLevelProject.get({id:oneId},function (data) {
            $scope.project2List=data.responseData;
            //默认调去三级展示
            ThreeLevelProject.get({
                ProjectTypeTwoId:data.responseData[0].id,
                projectTypeOneId:oneId,
                projectName:$scope.param.projectName,
                pageSize:$scope.param.pageSize
            },function (data) {
                $scope.threeList=data.responseData;
                $scope.param.projectAppear=false;
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