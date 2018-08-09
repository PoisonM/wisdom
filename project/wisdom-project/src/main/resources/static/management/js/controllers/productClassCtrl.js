angular.module('controllers', []).controller('productClassCtrl',
    ['$scope', '$interval', '$rootScope', '$stateParams', '$state', 'Global', '$timeout', 'QueryUserIncomeByParameters'
        , 'ManagementUtil', 'GetIncomeRecordByPageParam', "CheckIncomeRecordManagement", 'QueryIncomeInfoByIncomeId'
        , 'ExportExcelIncomeRecord', 'MonthlyIncomeSignalMT', 'GetKey', 'GetUserInfo', 'GetIncomeShareActivityInfoByIncomeId'
        , 'getOneProductClassList', 'getTwoProductClassList', 'addOneProductClass', 'addTwoProductClass', 'imageBase64UploadToOSS', 'upOrDownProductClass', 'delProductClassById', 'updateProductClass',
        function ($scope, $interval, $rootScope, $stateParams, $state, Global, $timeout, QueryUserIncomeByParameters
            , ManagementUtil, GetIncomeRecordByPageParam, CheckIncomeRecordManagement, QueryIncomeInfoByIncomeId
            , ExportExcelIncomeRecord, MonthlyIncomeSignalMT, GetKey, GetUserInfo, GetIncomeShareActivityInfoByIncomeId,
                  getOneProductClassList, getTwoProductClassList, addOneProductClass, addTwoProductClass, imageBase64UploadToOSS, upOrDownProductClass, delProductClassById, updateProductClass) {
            var startTime = document.querySelector(".MStart");
            var endTime = document.querySelector(".MEnd");
            var pattern = /^1[34578]\d{9}$/;
            var pattern1 = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            $scope.MAccount = "";
            $scope.agencyIndex = -1;
            $scope.checkStatus = "";
            $scope.key = "";
            $scope.value = "false";
            $scope.mum = true;
            /*var pageTrue = true;*/
            $scope.auditFlag = false;
            $scope.shareActivityFlag = false


            $scope.showAddTwoName = false;
            $scope.showAddOneName = false;

            /*点击添加二级类目按钮,所需传父级id和父级name*/
            $scope.parentId = "";
            $scope.parentName = "";
            /*点击修改一级类目按钮*/
            $scope.oneClassId = "";
            $scope.oneClassName = "";
            /**/
            $scope.productClassId = "";


            var a = [];

            //获取一级类目列表
            getOneProductClassList.save({}, function (data) {
                $scope.mum = false;
                if (data.result == Global.SUCCESS) {
                    $scope.oneClassLiat = data.responseData
                }else {
                    alert(data.errorInfo);
                    $scope.oneClassLiat = data.responseData
                }

            })
            $scope.getOneProductClassListMethod = function () {
                getOneProductClassList.save({}, function (data) {
                    $scope.mum = false;
                    if (data.result == Global.SUCCESS) {
                        $scope.oneClassLiat = data.responseData
                    }else {
                        alert(data.errorInfo);
                        $scope.oneClassLiat = data.responseData
                    }

                })
            }
            /*$scope.getOneProductClassList.save({}, function (data) {
                if (data.result == Global.SUCCESS) {
                    $scope.mum = false;
                    $scope.oneClassLiat = data.responseData
                }

            })*/
            /*获取二级类目*/
            $scope.getTwoProductClassListMethod = function () {
                getTwoProductClassList.get({productClassId: $scope.productClassId}, function (data) {
                    if(null == data .responseData){
                        alert("无下级");
                    }
                    $scope.twoList = data.responseData;
                })
            }
            /*$scope.getTwoProductClassList.get({productClassId: productClassId}, function (data) {
                $scope.mum = false;
                if(null == data .responseData){
                    alert("无下级");
                }
                $scope.twoList = data.responseData;
            })*/
            /*点击空白,隐藏*/
            $scope.bgAll = function () {
                $scope.showFlag = false;
            }
            /*点击添加一级类目按钮*/
            $scope.showAddOneProductClass = function () {
                $scope.showAddOneName = true;
                $scope.showAddTwoName = false;
                $scope.showUpdateOneName = false;
            }

            /*点击添加二级类目按钮*/
            $scope.showAddTwoProductClass = function (id, className) {
                $scope.showAddTwoName = true;
                $scope.showAddOneName = false;
                $scope.showUpdateOneName = false;
                $scope.parentId = id;
                $scope.parentName = className;
            }

            /*点击修改一级类目按钮*/
            $scope.showUpdateClass = function (oneClassId, oneClassName) {
                $scope.showUpdateOneName = true;
                $scope.showAddOneName = false;
                $scope.showAddTwoName = false;
                $scope.oneClassId = oneClassId;
                $scope.oneClassName = oneClassName;

            }

            /*添加一级分类*/
            $scope.addOneProductClass = function (className) {
                addOneProductClass.get({
                    className: className
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("成功");
                        $scope.showAddOneName = false;
                        // $scope.getOneProductClassList();
                        $scope.getOneProductClassListMethod();
                    }
                })
            }
            /*添加二级分类*/
            $scope.addTwoProductClass = function (id, twoClassName, url) {
                addTwoProductClass.get({
                    parentId: id,
                    className: twoClassName,
                    url: url
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("成功");
                        $scope.showAddTwoName = false;
                        // $scope.getTwoProductClassList($scope.productClassId);
                        $scope.getTwoProductClassListMethod();
                    }
                })
            }

            /*上移*/
            $scope.upClass = function (id,oneAndTwo) {
                upOrDownProductClass.get({
                    id: id,
                    upAndDown: "up"
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("上移成功");
                        if('one'== oneAndTwo){
                            // $scope.getOneProductClassList();
                            $scope.getOneProductClassListMethod();
                        }else {
                            // $scope.getTwoProductClassList($scope.productClassId);
                            $scope.getTwoProductClassListMethod();
                        }
                    }
                })
            }
            /*下移*/
            $scope.downClass = function (id,oneAndTwo) {
                upOrDownProductClass.get({
                    id: id,
                    upAndDown: "down"
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("下移成功");
                        if('one'== oneAndTwo){
                            // $scope.getOneProductClassList();
                            $scope.getOneProductClassListMethod();
                        }else {
                            // $scope.getTwoProductClassList($scope.productClassId);
                            $scope.getTwoProductClassListMethod();
                        }
                    }
                })
            }
            /*编辑*/
            $scope.updateClass = function (oneClassName) {
                updateProductClass.get({
                    id: $scope.oneClassId,
                    className: oneClassName
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("成功");
                        // $scope.getOneProductClassList();
                        $scope.getOneProductClassListMethod();
                    }
                })
            }
            /*删除*/
            $scope.delClass = function (id,oneAndTwo) {
                delProductClassById.get({
                    id: id
                }, function (data) {
                    if (data.result == Global.FAILURE) {
                        alert(data.errorInfo);
                    } else {
                        alert("成功");
                        if('one'== oneAndTwo){
                            // $scope.getOneProductClassList();
                            $scope.getOneProductClassListMethod();
                        }else {
                            // $scope.getTwoProductClassList($scope.productClassId);
                            $scope.getTwoProductClassListMethod();
                        }
                    }
                })
            }

            /*点击查看按钮*/
            $scope.showFlag = ""
            $scope.details = function (productClassId) {
                $scope.showFlag = productClassId;
                $scope.productClassId = productClassId;
                $scope.mum = true;
                getTwoProductClassList.get({productClassId: productClassId}, function (data) {
                    $scope.mum = false;
                    if(null == data .responseData){
                        alert("无下级");
                    }
                    $scope.twoList = data.responseData;
                })
            }
            $scope.reader = new FileReader();   //创建一个FileReader接口
            $scope.thumb = "";      //用于存放图片的base64
            $scope.img_upload = function (files) {
                var file = files[0];
                if (window.FileReader) {
                    var fr = new FileReader();
                    fr.onloadend = function (e) {
                        $scope.thumb = e.target.result
                        imageBase64UploadToOSS.save($scope.thumb, function (data) {
                            if (data.result == "0x00001") {

                            }
                            $scope.url = data.responseData//图片地址
                        })
                    };
                    fr.readAsDataURL(file);
                } else {
                    alert("浏览器不支持")
                }

            };
        }]);


