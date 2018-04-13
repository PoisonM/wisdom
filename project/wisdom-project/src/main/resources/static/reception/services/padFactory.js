var IP = '';
var appointmentInfo ="http://localhost:9051/appointmentInfo/";
PADWeb.factory('httpInterceptor', ["$q", "$injector", function ($q) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                if (localStorage.getItem("token") != undefined) {
                    config.headers['user_login_token'] = localStorage.getItem("token");
                }
                return config || $q.when(config);
            },
            requestError: function (err) {
            },
            response: function (res) {
                return res;
            },
            responseError: function (err) {
            }
        };
    }])
    /**
     * 
     */
    //
    .factory('DriverReservationList', ['$resource', function ($resource) {
        return $resource(IP + 'list')
    }])
    .factory('ShopDayAppointmentInfoByDate', ['$resource', function ($resource) {
        return $resource(appointmentInfo + 'shopDayAppointmentInfoByDate')
    }]);


    

