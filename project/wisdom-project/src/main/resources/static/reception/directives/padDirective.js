PADWeb.directive('ngTime', function () {
        return {
            restrict: 'A',
            require: '?ngModel',
            link: function ($scope, $element, $attrs, $ngModel) {
                if (!$ngModel) {
                    return;
                }
                $('.form_date').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    minView: 2,
                    forceParse: 0
                });

                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn:  1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 2,
                    forceParse: 0,
                    showMeridian: 1
                });
            }
        };
    })
    //分页
    .directive('tmPagination', [function () {
        return {
            restrict: 'EA',
            template: '<div class="page-list">' +
            '<ul class="pagination" ng-show="conf.totalItems > 0">' +
            '<li ng-class="{disabled: conf.currentPage == 1}" ng-click="prevPage()"><span>&laquo;</span></li>' +
            '<li ng-repeat="item in pageList track by $index" ng-class="{active: item == conf.currentPage, separate: item == \'...\'}" ' +
            'ng-click="changeCurrentPage(item)">' +
            '<span>{{ item }}</span>' +
            '</li>' +
            '<li ng-class="{disabled: conf.currentPage == conf.numberOfPages}" ng-click="nextPage()"><span>&raquo;</span></li>' +
            '</ul>' +
            '<span class="page-total" style="float: left; line-height: 52px; margin-right: 10px;" ng-show="conf.totalItems > 0">' +
            '共<strong>{{ conf.totalItems }}</strong>条 ' +
            '</span>' +
            '<div class="no-items" ng-show="conf.totalItems <= 0">暂无数据</div>' +
            '</div>',
            replace: true,
            scope: {
                conf: '='
            },
            link: function (scope, element, attrs) {

                var conf = scope.conf;

                // 默认分页长度
                var defaultPagesLength = 9;

                // 默认分页选项可调整每页显示的条数
                var defaultPerPageOptions = [10, 15, 20, 30, 50];

                // 默认每页的个数
                var defaultPerPage = 15;

                // 获取分页长度
                if (conf.pagesLength) {
                    // 判断一下分页长度
                    conf.pagesLength = parseInt(conf.pagesLength, 10);

                    if (!conf.pagesLength) {
                        conf.pagesLength = defaultPagesLength;
                    }

                    // 分页长度必须为奇数，如果传偶数时，自动处理
                    if (conf.pagesLength % 2 === 0) {
                        conf.pagesLength += 1;
                    }

                } else {
                    conf.pagesLength = defaultPagesLength
                }

                // 分页选项可调整每页显示的条数
                if (!conf.perPageOptions) {
                    conf.perPageOptions = defaultPagesLength;
                }

                // pageList数组
                function getPagination(newValue, oldValue) {

                    // conf.currentPage
                    if (conf.currentPage) {
                        conf.currentPage = parseInt(scope.conf.currentPage, 10);
                    }

                    if (!conf.currentPage) {
                        conf.currentPage = 1;
                    }

                    // conf.totalItems
                    if (conf.totalItems) {
                        conf.totalItems = parseInt(conf.totalItems, 10);
                    }

                    // conf.totalItems
                    if (!conf.totalItems) {
                        conf.totalItems = 0;
                        return;
                    }

                    // conf.itemsPerPage 
                    if (conf.itemsPerPage) {
                        conf.itemsPerPage = parseInt(conf.itemsPerPage, 10);
                    }
                    if (!conf.itemsPerPage) {
                        conf.itemsPerPage = defaultPerPage;
                    }

                    // numberOfPages
                    conf.numberOfPages = Math.ceil(conf.totalItems / conf.itemsPerPage);

                    // 如果分页总数>0，并且当前页大于分页总数
                    if (scope.conf.numberOfPages > 0 && scope.conf.currentPage > scope.conf.numberOfPages) {
                        scope.conf.currentPage = scope.conf.numberOfPages;
                    }

                    // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                    var perPageOptionsLength = scope.conf.perPageOptions.length;

                    // 定义状态
                    var perPageOptionsStatus;
                    for (var i = 0; i < perPageOptionsLength; i++) {
                        if (conf.perPageOptions[i] == conf.itemsPerPage) {
                            perPageOptionsStatus = true;
                        }
                    }
                    // 如果itemsPerPage在不在perPageOptions数组中，就把itemsPerPage加入这个数组中
                    if (!perPageOptionsStatus) {
                        conf.perPageOptions.push(conf.itemsPerPage);
                    }

                    // 对选项进行sort
                    conf.perPageOptions.sort(function (a, b) {
                        return a - b
                    });


                    // 页码相关
                    scope.pageList = [];
                    if (conf.numberOfPages <= conf.pagesLength) {
                        // 判断总页数如果小于等于分页的长度，若小于则直接显示
                        for (i = 1; i <= conf.numberOfPages; i++) {
                            scope.pageList.push(i);
                        }
                    } else {
                        // 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）
                        // 计算中心偏移量
                        var offset = (conf.pagesLength - 1) / 2;
                        if (conf.currentPage <= offset) {
                            // 左边没有...
                            for (i = 1; i <= offset + 1; i++) {
                                scope.pageList.push(i);
                            }
                            scope.pageList.push('...');
                            scope.pageList.push(conf.numberOfPages);
                        } else if (conf.currentPage > conf.numberOfPages - offset) {
                            scope.pageList.push(1);
                            scope.pageList.push('...');
                            for (i = offset + 1; i >= 1; i--) {
                                scope.pageList.push(conf.numberOfPages - i);
                            }
                            scope.pageList.push(conf.numberOfPages);
                        } else {
                            // 最后一种情况，两边都有...
                            scope.pageList.push(1);
                            scope.pageList.push('...');

                            for (i = Math.ceil(offset / 2); i >= 1; i--) {
                                scope.pageList.push(conf.currentPage - i);
                            }
                            scope.pageList.push(conf.currentPage);
                            for (i = 1; i <= offset / 2; i++) {
                                scope.pageList.push(conf.currentPage + i);
                            }

                            scope.pageList.push('...');
                            scope.pageList.push(conf.numberOfPages);
                        }
                    }

                    scope.$parent.conf = conf;
                }

                // prevPage
                scope.prevPage = function () {
                    if (conf.currentPage > 1) {
                        conf.currentPage -= 1;
                    }
                    getPagination();
                    if (conf.onChange) {
                        conf.onChange();
                    }
                };

                // nextPage
                scope.nextPage = function () {
                    if (conf.currentPage < conf.numberOfPages) {
                        conf.currentPage += 1;
                    }
                    getPagination();
                    if (conf.onChange) {
                        conf.onChange();
                    }
                };

                // 变更当前页
                scope.changeCurrentPage = function (item) {

                    if (item == '...') {
                        return;
                    } else {
                        conf.currentPage = item;
                        getPagination();
                        // conf.onChange()函数
                        if (conf.onChange) {
                            conf.onChange();
                        }
                    }
                };

                // 修改每页展示的条数
                scope.changeItemsPerPage = function () {

                    // 一发展示条数变更，当前页将重置为1
                    conf.currentPage = 1;

                    getPagination();
                    // conf.onChange()函数
                    if (conf.onChange) {
                        conf.onChange();
                    }
                };

                // 跳转页
                scope.jumpToPage = function () {
                    num = scope.jumpPageNum;
                    if (num.match(/\d+/)) {
                        num = parseInt(num, 10);

                        if (num && num != conf.currentPage) {
                            if (num > conf.numberOfPages) {
                                num = conf.numberOfPages;
                            }

                            // 跳转
                            conf.currentPage = num;
                            getPagination();
                            // conf.onChange()函数
                            if (conf.onChange) {
                                conf.onChange();
                            }
                            scope.jumpPageNum = '';
                        }
                    }

                };

                scope.jumpPageKeyUp = function (e) {
                    var keycode = window.event ? e.keyCode : e.which;

                    if (keycode == 13) {
                        scope.jumpToPage();
                    }
                }

                scope.$watch('conf.totalItems', function (value, oldValue) {

                    // 在无值或值相等的时候，去执行onChange事件
                    if (!value || value == oldValue) {

                        if (conf.onChange) {
                            conf.onChange();
                        }
                    }
                    getPagination();
                })

            }
        };
    }])
    //获取焦点
    .directive('focus', function() {
        return {
            restrict: 'AE',
            scope: {data: '='},
            template:"",
            link: function(scope, elem, attr, ctrl) {
                scope.$watch("data",function(newValue){
                    if(newValue){
                        elem[0].focus()
                    }else{
                        elem[0].blur()
                    }
                })
            }
        };
    })
    //下拉搜索
    .directive('selectSearch', function () {
        return {
            restrict: 'AE',
            scope: {data: '='},
            template: '<div class="outside">' +
            '<input class="form-control" type="text" ng-blur="blurFn()" ng-focus="focusFn()" ng-change="changeKeyValue()" ng-model="data.receiveData">' +
            '<ul ng-hide="hidden" class="select">' +
            '<li style="cursor:pointer" ng-repeat="item in data.data|filter:data.receiveData track by $index" ng-click="change(item)">{{item}}</li>' +
            '</ul>' +
            '</div>',
            link: function (scope, elem, attrs, ctrl) {
                scope.hidden = true;
                scope.focusFn = function () {
                    scope.hidden = false;
                }
                scope.blurFn = function () {
                    setTimeout(function () {
                        scope.$apply(function () {
                            scope.hidden = true;
                        })
                    },300)
                }
                scope.change = function (name) {
                    scope.data.receiveData = name;
                    scope.hidden = true;
                    if (scope.data.onChange) {
                        scope.data.onChange();
                    }
                }
                scope.changeKeyValue = function () {
                    if (scope.data.onChange) {
                        scope.data.onChange();
                    }
                }
            }
        };
    })
    //展示固定的列
    .directive('showCols', function () {
        return {
            restrict: 'AE',
            scope: {data: '='},
            template: '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-th icon-th"></i> <span class="caret"></span></button>'
            + '<ul class="dropdown-menu" role="menu">'
            + '<li ng-repeat="item in datas">'
            + '<label class="mt-checkbox mt-checkbox-outline">'
            + '<input ng-change="change($index,bool)" ng-model="bool" type="checkbox" name="tableTitle"/>{{item}}'
            + '</label></li></ul>'
            ,
            link: function (scope, elem, attr, ctrl) {
                scope.bool = true;

                scope.datas = scope.$parent[attr.data].colNames;

                scope.arr = new Array(scope.datas.length);
                for (var n = 0; n < scope.arr.length; n++) {
                    scope.arr[n] = true;
                }

                scope.change = function (i, bool) {
                    scope.arr[i] = bool;
                    scope.$parent[attr.data].receiveBoolean = scope.arr;
                    if (scope.$parent[attr.data].onchange) {
                        scope.$parent[attr.data].onchange()
                    }
                }
            }
        };
    })
    .directive("ngOnhold", ["$swipe", "$parse", function($swipe, $parse){
    //长按触发事件需要的时间
    var ON_HOLD_TIMEMS = 1000;
    return function(scope, element, attr) {
        var onholdHandler = $parse(attr["ngOnhold"]);
        var run;
        $swipe.bind(element, {
            'start': function(coords, event) {
                run = setTimeout(function(){
                    scope.$apply(function() {
                        element.triggerHandler("onhold");
                        onholdHandler(scope, {$event: event});
                        console.log(event);
                    });
                }, ON_HOLD_TIMEMS);
            },
            'cancel': function(event) {
                if(run)clearTimeout(run);
            },
            'move' : function(event){
                if(run)clearTimeout(run);
            },
            'end': function(coords, event) {
                if(run)clearTimeout(run);
            }
        }, ['touch', 'mouse']);
    }
}]);
;
