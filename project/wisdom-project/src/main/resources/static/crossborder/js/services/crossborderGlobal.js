angular.module('crossborderGlobal',[])
    .constant("Global",
        {
            SUCCESS : '0x00001',
            FAILURE : '0x00002',
            ONLINE : '0x00003',
            OFFLINE : '0x00004',
            LOGIN_OUT: '0x00005',
            TOKEN_ERROR: '0x00006',
            PARAM_ERROR: '0x00007',
            LOGIN_SUCCESS_SECOND : '0x00008',
        })
    .factory('CrossborderUtil', ['Global','$state',
        function(Global,$state) {
            return {
                checkResponseData: function(data) {
                    if(data.result==Global.FAILURE)
                    {
                        if(data.errorInfo==Global.TOKEN_ERROR){
                            $state.go("register");
                        }
                    }
                },
                getAddDate:function(date,days){
                    var d=new Date(date);
                    d.setDate(d.getDate()+days);
                    var month=d.getMonth()+1;
                    var day = d.getDate();
                    if(month<10){
                        month = "0"+month;
                    }
                    if(day<10){
                        day = "0"+day;
                    }
                    var val = d.getFullYear()+"-"+month+"-"+day;
                    return val;
                },
                exportLis:function(name,tableName,aName){
                var tableNames="."+tableName;
                var aNames ="."+aName;
                var html = "<html><head><meta charset='utf-8' /></head><body>" + document.querySelector(tableNames).outerHTML + "</body></html>";
                var blob = new Blob([html], { type: "application/vnd.ms-excel" });
                var a = document.querySelector(aNames);
                a.href = URL.createObjectURL(blob);
                if(name==undefined)
                    a.download = "产品列表.xls";
                else
                    a.download = name+".xls";
            }
            };
        }]);
