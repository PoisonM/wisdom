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
        pageSize:"10",
        chooseProjectItem : ''
    };

    $scope.loading = true;
    /*一级项目列表接口*/
    OneLevelProject.get(function (data) {
        $scope.selectSingleList=data.responseData;
        $scope.selectSingleList[0].status=3;//给一个值用来点击切换图片的时候图片的样式
        $scope.selection(0,data.responseData[0].id) //获取二级为了调去3级默认选择
        $scope.loading = false;
    });

    //点击二级列表调取三级项目列表产品数据方法
    $scope.refreshGoods=function (id) {
        $scope.param.chooseProjectItem = id;
        $scope.loading = true;
        $scope.threeList = [];
        ThreeLevelProject.get({
            ProjectTypeTwoId:id,
            projectTypeOneId: $scope.param.projectTypeOneId,
            projectName:$scope.param.projectName,
            pageSize:$scope.param.pageSize
        },function (data) {
            $scope.threeList=data.responseData;
            $scope.loading = false;
        })
    };

    $scope.goProjectDetails=function (id) {
        $state.go("pad-web.projectDetails",{id:id})
    };

    $scope.selection  = function (index,oneId) {
        $scope.loading = true;
        $scope.param.selection = index;
        $scope.threeList = [];
        TwoLevelProject.get({id:oneId},function (data) {
            $scope.project2List = data.responseData;
            if($scope.project2List.length>0)
            {
                $scope.param.projectAppear = true;
                $scope.param.chooseProjectItem = $scope.project2List[0].id;
                ThreeLevelProject.get({
                    ProjectTypeTwoId:$scope.project2List[0].id,
                    projectTypeOneId:oneId,
                    projectName:$scope.param.projectName,
                    pageSize:$scope.param.pageSize
                },function (data) {
                    $scope.threeList=data.responseData;
                    $scope.loading = false;
                });
            }
            else
            {
                $scope.loading = false;
            }
        });
    };
});