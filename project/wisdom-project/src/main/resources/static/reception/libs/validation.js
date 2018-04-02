/**
 * Created by T460P on 2017/6/5.
 */
/** 获取时间*/
function getTimes(n) {
    if (new Date(n) == 'Invalid Date') {
        return null
    } else {
        return new Date(n).getTime()
    }
}
function hideMidNum(str, frontLen, endLen) {
    if (str) {
        var len = str.length - frontLen - endLen;
        var xing = '';
        for (var i = 0; i < len; i++) {
            xing += '*';
        }
        return str.substring(0, frontLen) + xing + str.substring(str.length - endLen);
    } else {
        return ''
    }
}
/** 日期格式*/
function formatDate(time, mark, n) {
    if (time == null || time == '' || time == "undefined") {
        return "";
    } else {
        var date = new Date(time);
        var dYear = date.getFullYear();
        var dMonth = date.getMonth() + 1;
        var dDate = date.getDate();
        var dHours = date.getHours();
        var dMinutes = date.getMinutes();
        var dSeconds = date.getSeconds();
        if (dMonth < 10) {
            dMonth = '0' + dMonth
        }
        ;
        if (dDate < 10) {
            dDate = '0' + dDate
        }
        ;
        if (dHours < 10) {
            dHours = '0' + dHours
        }
        ;
        if (dMinutes < 10) {
            dMinutes = '0' + dMinutes
        }
        ;
        if (dSeconds < 10) {
            dSeconds = '0' + dSeconds
        }
        ;
        if (mark) {
            if (n == 1) {
                return dYear + mark + dMonth + mark + dDate;
            } else if (n == 2) {
                return dYear + mark + dMonth + mark + dDate + ' ' + dHours + ':' + dMinutes;
            } else {
                return dYear + mark + dMonth + mark + dDate + ' ' + dHours + ':' + dMinutes + ':' + dSeconds;
            }
        } else {
            if (n == 1) {
                return dYear + '/' + dMonth + '/' + dDate;
            } else if (n == 2) {
                return dYear + '/' + dMonth + '/' + dDate + ' ' + dHours + ':' + dMinutes;
            } else {
                return dYear + '/' + dMonth + '/' + dDate + ' ' + dHours + ':' + dMinutes + ':' + dSeconds;
            }
        }
    }
}
/** 验证手机号*/
function checkPhone(num) {
    if (!(/^1[34578]\d{9}$/.test(num))) {
        return false;
    } else {
        return true;
    }
}
/**验证邮箱*/
function checkEmail(email) {
    //对电子邮件的验证
    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (myreg.test(email)) {
        return true;
    } else {
        return false;
    }
}
/** 验证身份证*/
function checkID(ID) {
    //对身份证号
    var myreg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
    if (myreg.test(ID)) {
        return true;
    } else {
        return false;
    }
}
/** 验证运单号*/
function checkOrderCode(code) {
    //对身份证号
    /*var myreg = /^YD[0-9]{6}$/ ;
    if (myreg.test(code)) {
        return true;
    } else {
        return false;
    }*/
    return true;
}
/** 验证司机配送员*/
function checkDriver(code) {
    //对身份证号
    var myreg = /^SJ[0-9]{6}$/ ;
    var myreg1 = /^PS[0-9]{6}$/ ;
    if (myreg.test(code) || myreg1.test(code)) {
        return true;
    } else {
        return false;
    }
}
/** 验证密码*/
function checkPassword(code) {
    var regex =/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/ ;
    if (regex.test(code)) {
        return true;
    } else {
        return false;
    }
}