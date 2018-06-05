angular.module('controllers',[]).controller('sharePageCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetQRCodeURL','BusinessUtil',
        function ($scope,$rootScope,$stateParams,$state,GetQRCodeURL,BusinessUtil) {

            $rootScope.title = "分享赚钱";

            $scope.param = {
                imgs:{},
                weixinShareInfo:{},
                ctx : {},
                mainImg:{},
                canvas:{}
            };

            var calcHeight = function(obj, w)
            {
                return w / obj.width * obj.height;
            };

            //绘制$scope.param.canvas
            var drawImage = function(){
                //背景图
                $scope.param.ctx.drawImage($scope.param.imgs.bg, 0, 0, $scope.param.canvas.width, $scope.param.canvas.height);
                //头像
               /* $scope.param.ctx.drawImage($scope.param.imgs.via,60,50);*/
                //微信名字
                /*$scope.param.ctx.fillStyle = '#ffffff';
                $scope.param.ctx.font = '28px microsoft yahei';
                $scope.param.ctx.fillText($scope.param.weixinShareInfo.nickName,218,90);*/
                /*美享*/
                /*$scope.param.ctx.font = '36px microsoft  yahei';
                $scope.param.ctx.fillText('美享' ,218, 150);*/
                  /*九小主 大当家*/
               /* $scope.param.ctx.fillStyle = '#FFF100';
                $scope.param.ctx.font = '36px microsoft #FFF100';
                $scope.param.ctx.fillText($scope.param.weixinShareInfo.userType,328,150);*/
                //二维码
                $scope.param.ctx.drawImage($scope.param.imgs.qrCode, $scope.param.canvas.width*0.36, $scope.param.canvas.height*0.78 , $scope.param.canvas.width*0.23, calcHeight($scope.param.imgs.qrCode, $scope.param.canvas.width*0.23));

                //获取base64格式的src
                var imgSrc = $scope.param.canvas.toDataURL('image/jpg');
                $scope.param.mainImg.src = imgSrc;
                $("#canvas").hide();
            }

            var drawInto = function(){
                var imgNum = 0;
                for(var key in $scope.param.imgs){
                    var img = new Image();
                    img.src = $scope.param.imgs[key];
                    $scope.param.imgs[key] = img;
                    $scope.param.imgs[key].onload = function(){
                        imgNum++;
                        if(imgNum == Object.keys($scope.param.imgs).length) drawImage();
                    }
                }
                if($stateParams.reload){
                    $stateParams.reload=false;
                    $state.reload('app.toMenu');
                }
            }

            $scope.$on('$ionicView.enter', function(){
                GetQRCodeURL.get(function (data) {
                    BusinessUtil.checkResponseData(data,'sharePage');
                    $scope.param.weixinShareInfo = data.responseData;
                    if(data.responseData.userType.indexOf("B-1")>-1)
                    {
                        $scope.param.weixinShareInfo.userType = "9小主";
                    }
                    else if(data.responseData.userType.indexOf("A-1")>-1)
                    {
                        $scope.param.weixinShareInfo.userType = "大当家";
                    }

                    //获取$scope.param.canvas
                    $scope.param.canvas = document.getElementById('canvas');

                    //设置宽高
                    //想获取高清图请*2，一般的直接等于Width就行
                    var Height = 1102;
                    var Width = 750;

                    //$scope.param.canvas绘制需要的对象
                    $scope.param.ctx = $scope.param.canvas.getContext("2d");
                    $scope.param.canvas.width = Width;
                    $scope.param.canvas.height = Height;

                    //获取图片
                    $scope.param.mainImg = document.getElementById('mainImg');

                    //获取图片
                    $scope.param.imgs = {
                        bg: 'images/sharePage/bgs.png', //大背景
                        via:  $scope.param.weixinShareInfo.userImage, //'img/people.jpg', //头像
                        qrCode: $scope.param.weixinShareInfo.qrCodeURL //.shareCode //二维码
                    };

                    //载入图片
                    drawInto();
                })

            })
        }])