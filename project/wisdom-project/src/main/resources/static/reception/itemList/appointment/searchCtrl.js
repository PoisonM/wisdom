function searchConsumption($scope,SearchShopProjectList,SearchShopProductList,GetShopProjectGroups,ThreeLevelProject,productInfoThreeLevelProject){
    var index = [];


    $scope.searchConsumption = function(){/*搜索三级*/
      if( $scope.param.consumptionObj.consumptionType == "selectSingle"){
          $scope.selectSingleDataPic =[];
          $scope.single =[];
          SearchShopProjectList.get({
              filterStr:$scope.param.consumptionObj.singleFilterStr,
              useStyle:"1"
          },function(data){
              $scope.param.consumptionObj.singleByshopId.detailProject = data.responseData.detailProject;/*三级数据*/
              for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                  $scope.selectSingleDataPic[i] = 'images/bt_Single%20election_nor_.png';
                  $scope.single[i] = true;
              }
          })

      }else if($scope.param.consumptionObj.consumptionType == "selectTreatmentCard"){
          $scope.selectTreatmentDataPic =[];
          $scope.treatment =[];
          SearchShopProjectList.get({
              filterStr:$scope.param.consumptionObj.singleFilterStr,
              useStyle:"0"
          },function(data){
              $scope.param.consumptionObj.singleByshopId.detailProject = data.responseData.detailProject;/*三级数据*/
              for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                  $scope.selectTreatmentDataPic[i] = 'images/bt_Single%20election_nor_.png';
                  $scope.treatment[i] = true;
              }
          })
      }else if($scope.param.consumptionObj.consumptionType=="selectProduct"){
          console.log(1)
          $scope.selectProductDataPic = []
          $scope.product = [];
          SearchShopProductList.get({
              pageSize:"100",
              filterStr:$scope.param.consumptionObj.singleFilterStr
          },function(data){
              $scope.param.consumptionObj.singleByshopId.detailProject = data.responseData.detailProduct;/*三级数据*/
              for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                  $scope.selectProductDataPic[i] = 'images/bt_Single%20election_nor_.png';
                  $scope.product[i] = true;
              }
          })
          }


        /*for(var i=0;i<$scope.param.data.length;i++){
            for(var j=0;j<$scope.param.data[i].content.length;j++){
                if($scope.param.data[i].content[j].indexOf($scope.param.serachContent) !=-1){
                    index.push(i)
                }
            }
        }
        if(index.length>0){
            var arr =[]
            for(var i=0;i<index.length;i++){
                arr.push($scope.param.data[index[i]])
            }
            $scope.param.data = arr;
            console.log($scope.param.data);
            index= [];
        }*/
    }


    $scope.searchActive = function(projectTypeOneId,projectTypeTwoId){  /*点击查询三级*/
        /*$scope.param.index = index;
        $scope.param.index2 = index2;
        $scope.active = "bgff6666";*/

        if($scope.param.consumptionObj.consumptionType == "selectTreatmentCard"||$scope.param.consumptionObj.consumptionType == "selectSingle"){
            $scope.selectSingleDataPic = [];
            $scope.selectTreatmentDataPic=[]
            $scope.single = [];
            $scope.treatment=[]
            ThreeLevelProject.get({
                pageSize:100,
                projectName:"",
                projectTypeOneId:"1",
                projectTypeTwoId:"4",
            },function(data){
                $scope.param.consumptionObj.singleByshopId.detailProject =  data.responseData;
                if($scope.param.consumptionObj.consumptionType == "selectTreatmentCard"){
                    for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                        $scope.selectTreatmentDataPic[i] = 'images/bt_Single%20election_nor_.png';
                        $scope.treatment[i] = true;
                    }
                }
                if($scope.param.consumptionObj.consumptionType == "selectSingle"){
                    for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                        $scope.selectSingleDataPic[i] = 'images/bt_Single%20election_nor_.png';
                        $scope.single[i] = true;
                    }


                }

            });

        }else if($scope.param.consumptionObj.consumptionType == "selectProduct"){
            $scope.selectProductDataPic = [];
            $scope.product = [];
            productInfoThreeLevelProject.get({
                pageSize:100,
                productName:"",
                productTypeOneId:"2",
                productTypeTwoId:"3",
            },function(data){
                $scope.param.consumptionObj.singleByshopId.detailProject =  data.responseData;
                for(var i=0;i<$scope.param.consumptionObj.singleByshopId.detailProject.length;i++){
                    $scope.selectProductDataPic[i] = 'images/bt_Single%20election_nor_.png';
                    $scope.product[i] = true;
                }
            });

        }
        selectSingleCtrl && selectSingleCtrl($scope);
        selectTreatmentCardCtrl && selectTreatmentCardCtrl($scope);
        selectProductCtrl && selectProductCtrl($scope)

    }

}