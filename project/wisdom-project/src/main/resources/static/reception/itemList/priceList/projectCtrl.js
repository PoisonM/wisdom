/**
 * Created by Administrator on 2018/3/29.
 */
PADWeb.controller("projectCtrl", function($scope, $state, $stateParams) {
    $scope.param = {
        selection:"0",
        projectAppear:false,
        parentSty:"",
        childrenFlag:""
    }
    // $scope.selectSingleData = [0,1,0,0,0,0,0,0,0,0];
    $scope.selectSingleData = [
        {
            name:"面部",
            status:"3",
        },
        {
            name:"脚部",
            status:"1",
        },
        {
            name:"背部",
            status:"1",
        },
        {
            name:"背部",
            status:"1",
        },
        {
            name:"背部",
            status:"1",
        },
        {
            name:"背部",
            status:"1",
        },
        {
            name:"背部",
            status:"1",
        },
        {
            name:"手部",
            status:"1",
        },
        {
            name:"臀部",
            status:"1",
        },
        {
            name:"足部",
            status:"1",
        },
        {
            name:"指甲",
            status:"1",
        }

    ]
    $scope.checkImg = function (index,status) {
        if($scope.param.childrenFlag == index){
            for(var i = 0; i < $scope.selectSingleData.length; i++ ){
                $scope.selectSingleData[i].status = 1
            }
            if(status == 1){
                $scope.selectSingleData[index].status = 4;
                $scope.param.projectAppear = true;
            }
            if(status == 4){
                $scope.selectSingleData[index].status = 3;
                $scope.param.projectAppear = false;
            }
            if(status == 3){
                $scope.selectSingleData[index].status = 4;
                $scope.param.projectAppear = true;
            }
        }
    }
    $scope.goProjectDetails=function () {
        $state.go("pad-web.projectDetails")
    }
    /*$scope.selectSingleDataPic =[];
    var single =[];
    for(var i=0;i<$scope.selectSingleData.length;i++){
        $scope.selectSingleDataPic[i] = 'images/check2.png';
        single[i] = true;
    }
    $scope.projectAppear=false;
    $scope.checkImg = function(index) {
        if (single[index] == true) {
            $scope.selectSingleDataPic[index] = 'images/check4.png';
            single[index] = false;
           $scope.projectAppear=true;
        } else {
            $scope.selectSingleDataPic[index] = 'images/check3.png';
            single[index] = true;
            $scope.projectAppear=false;
        }
    }*/
    $scope.selection  = function (index) {
        $scope.param.childrenFlag = index
        for(var i = 0; i < $scope.selectSingleData.length; i++ ){
            $scope.selectSingleData[i].status = 1
        }
        $scope.param.selection = index;
        $scope.selectSingleData[index].status = 3
    }
   
})