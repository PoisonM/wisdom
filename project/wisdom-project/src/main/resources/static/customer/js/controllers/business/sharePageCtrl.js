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
                $scope.param.ctx.beginPath();
                $scope.param.ctx.save(); // 保存当前canvas的状态
                $scope.param.ctx.arc($scope.param.canvas.width*0.5, $scope.param.canvas.width*0.46, $scope.param.canvas.width*0.16, 0, 2*Math.PI);
                $scope.param.ctx.fill();
                $scope.param.ctx.clip(); // 裁剪圆形
                $scope.param.ctx.drawImage($scope.param.imgs.via, $scope.param.canvas.width*0.34, $scope.param.canvas.width*0.29, $scope.param.canvas.width*0.35, calcHeight($scope.param.imgs.via, $scope.param.canvas.width*0.35));
                $scope.param.ctx.closePath();
                $scope.param.ctx.restore(); //还原状态
                //昵称
                //颜色
                $scope.param.ctx.fillStyle = '#ffffff';
                // //字体设置 三个参数，font-weight font-size font-family
                $scope.param.ctx.font = '30px microsoft yahei';
                $scope.param.ctx.fillText($scope.param.weixinShareInfo.nickName, $scope.param.canvas.width/2-$scope.param.weixinShareInfo.nickName.length*30/2, $scope.param.canvas.width*0.68);
                // // //说明
                $scope.param.ctx.font = '90px microsoft  yahei';
                $scope.param.ctx.fillText($scope.param.weixinShareInfo.balance, $scope.param.canvas.width/2-$scope.param.weixinShareInfo.balance.length*90/4,$scope.param.canvas.width*1);
                $scope.param.ctx.font = '30px microsoft  yahei';
                $scope.param.ctx.fillText('推荐店主:'+$scope.param.weixinShareInfo.peoperCount + '人', 72, 835);
                $scope.param.ctx.fillText('推荐奖励:'+$scope.param.weixinShareInfo.istanceMoney+'元', 428, 835);

                $scope.param.ctx.fillStyle = '#FFF100';
                $scope.param.ctx.font = '60px microsoft #FFF100';
                $scope.param.ctx.fillText($scope.param.weixinShareInfo.userType,285,562);
                //二维码
                $scope.param.ctx.drawImage($scope.param.imgs.qrCode, $scope.param.canvas.width*0.08, $scope.param.canvas.height*0.8 , $scope.param.canvas.width*0.22, calcHeight($scope.param.imgs.qrCode, $scope.param.canvas.width*0.22));

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
                    var Height = 1196;
                    var Width = 750;

                    //$scope.param.canvas绘制需要的对象
                    $scope.param.ctx = $scope.param.canvas.getContext("2d");
                    $scope.param.canvas.width = Width;
                    $scope.param.canvas.height = Height;

                    //获取图片
                    $scope.param.mainImg = document.getElementById('mainImg');

                    //获取图片
                    $scope.param.imgs = {
                        bg: 'images/sharePage/bgs.jpg', //大背景
                        via:  $scope.param.weixinShareInfo.userImage, //'img/people.jpg', //头像
                        qrCode: $scope.param.weixinShareInfo.qrCodeURL //.shareCode //二维码
                    };

                    //载入图片
                    drawInto();
                })

            })
        }])