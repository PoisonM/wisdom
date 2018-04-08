/**
 * Created by Administrator on 2018/4/8.
 */
PADWeb.controller("customerSignatureCtrl", function($scope, $state, $stateParams) {
    console.log("customerSignatureCtrl");
    $scope.customerSignature = [
        {
            name:"面部补水",
            type:"年卡",
            frequency:"1次"
        },
        {
            name:"胸部保养",
            type:"赠",
            frequency:"1次"
        },
    ];

})