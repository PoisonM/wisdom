angular.module('controllers',[]).controller('applicablePartsCtrl',
    ['$scope','$rootScope','$stateParams','$state','$ionicLoading',
        function ($scope,$rootScope,$stateParams,$state,$ionicLoading) {
            $rootScope.title = "适用部位";
            $scope.param = {
                applicableParts:[
                    "头部",'面部','眼部','鼻部','唇部','耳部','颈部','胸部','手部','背部','腹部','肾部','臀部','腿部','脚部','身体'
                ]
            }
            $scope.selParts = function(parts){
                if($stateParams.type == 'add'){
                    $state.go("addProduct",{id:$stateParams.id,parts:parts})
                }else{
                    $state.go("modifyProduct",{id:$stateParams.id,parts:parts})
                }

            }

        }])