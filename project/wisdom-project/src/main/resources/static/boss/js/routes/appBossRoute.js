/**
 * 路由
 */
define(['appBoss'], function(app){
    return app
        .config(['$stateProvider','$urlRouterProvider',
            function($stateProvider,$urlRouterProvider) {
                var loadFunction = function($templateCache, $ocLazyLoad, $q, $http,name,files,htmlURL){
                    lazyDeferred = $q.defer();
                    return $ocLazyLoad.load ({
                        name: name,
                        files: files
                    }).then(function() {
                        return $http.get(htmlURL)
                            .success(function(data, status, headers, config) {
                                return lazyDeferred.resolve(data);
                            }).
                            error(function(data, status, headers, config) {
                                return lazyDeferred.resolve(data);
                            });
                    });
                };

                $stateProvider
                    .state('shopHome', {
                        url: '/shopHome',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'shopHomeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.shopHomeCtrl',
                                    ['js/controllers/shopHomeCtrl.js?ver='+ bossVersion],
                                    'views/shopHome.html?ver=' + bossVersion);
                            }
                        }
                    })
                .state('partialFiles', {
                    url: '/partialFiles',
                    templateProvider: function() { return lazyDeferred.promise; },
                    controller: 'partialFilesCtrl',
                    resolve: {
                        load: function($templateCache, $ocLazyLoad, $q, $http) {
                            loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.partialFilesCtrl',
                                ['js/controllers/archives/partialFilesCtrl.js?ver='+ bossVersion],
                                'views/archives/partialFiles.html?ver=' + bossVersion);
                        }
                    }
                })
                .state('warningFile', {
                    url: '/warningFile',
                    templateProvider: function() { return lazyDeferred.promise; },
                    controller: 'warningFileCtrl',
                    resolve: {
                        load: function($templateCache, $ocLazyLoad, $q, $http) {
                            loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.warningFileCtrl',
                                ['js/controllers/archives/warningFileCtrl.js?ver='+ bossVersion],
                                'views/archives/warningFile.html?ver=' + bossVersion);
                        }
                    }
                })
                .state('partialFilesSearch', {
                    url: '/partialFilesSearch',
                    templateProvider: function() { return lazyDeferred.promise; },
                    controller: 'partialFilesSearchCtrl',
                    resolve: {
                        load: function($templateCache, $ocLazyLoad, $q, $http) {
                            loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.partialFilesSearchCtrl',
                                ['js/controllers/archives/partialFilesSearchCtrl.js?ver='+ bossVersion],
                                'views/archives/partialFilesSearch.html?ver=' + bossVersion);
                        }
                    }
                })
                    .state('archives', {
                        url: '/archives',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'archivesCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.archivesCtrl',
                                    ['js/controllers/archives/archivesCtrl.js?ver='+ bossVersion],
                                    'views/archives/archives.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('modifyTheFile', {
                    url: '/modifyTheFile',
                    templateProvider: function() { return lazyDeferred.promise; },
                    controller: 'modifyTheFileCtrl',
                    resolve: {
                        load: function($templateCache, $ocLazyLoad, $q, $http) {
                            loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.modifyTheFileCtrl',
                                ['js/controllers/archives/modifyTheFileCtrl.js?ver='+ bossVersion],
                                'views/archives/modifyTheFile.html?ver=' + bossVersion);
                        }
                    }
                })
                    .state('userBasicMess', {
                    url: '/userBasicMess',
                    templateProvider: function() { return lazyDeferred.promise; },
                    controller: 'userBasicMessCtrl',
                    resolve: {
                        load: function($templateCache, $ocLazyLoad, $q, $http) {
                            loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.userBasicMessCtrl',
                                ['js/controllers/archives/userBasicMessCtrl.js?ver='+ bossVersion],
                                'views/archives/userBasicMess.html?ver=' + bossVersion);
                        }
                    }
                })
                    .state('menstrualPeriod', {
                        url: '/menstrualPeriod',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'menstrualPeriodCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.menstrualPeriodCtrl',
                                    ['js/controllers/archives/menstrualPeriodCtrl.js?ver='+ bossVersion],
                                    'views/archives/menstrualPeriod.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('label', {
                        url: '/label',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'labelCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.labelCtrl',
                                    ['js/controllers/archives/labelCtrl.js?ver='+ bossVersion],
                                    'views/archives/label.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('refillCard', {
                        url: '/refillCard',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'refillCardCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.refillCardCtrl',
                                    ['js/controllers/archives/refillCardCtrl.js?ver='+ bossVersion],
                                    'views/archives/refillCard.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('prepaidPhoneRecords', {
                        url: '/prepaidPhoneRecords',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'prepaidPhoneRecordsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.prepaidPhoneRecordsCtrl',
                                    ['js/controllers/archives/prepaidPhoneRecordsCtrl.js?ver='+ bossVersion],
                                    'views/archives/prepaidPhoneRecords.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('accountDetails', {
                        url: '/accountDetails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'accountDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.accountDetailsCtrl',
                                    ['js/controllers/archives/accountDetailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/accountDetails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('orderdDtails', {
                        url: '/orderdDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'orderdDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.orderdDtailsCtrl',
                                    ['js/controllers/archives/orderdDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/orderdDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('balanceRecord', {
                        url: '/balanceRecord',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'balanceRecordCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.balanceRecordCtrl',
                                    ['js/controllers/archives/balanceRecordCtrl.js?ver='+ bossVersion],
                                    'views/archives/balanceRecord.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('payDtails', {
                        url: '/payDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'payDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.payDtailsCtrl',
                                    ['js/controllers/archives/payDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/payDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('coupons', {
                        url: '/coupons',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'couponsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.couponsCtrl',
                                    ['js/controllers/archives/couponsCtrl.js?ver='+ bossVersion],
                                    'views/archives/coupons.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('couponsDtails', {
                        url: '/couponsDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'couponsDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.couponsDtailsCtrl',
                                    ['js/controllers/archives/couponsDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/couponsDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('treatmentCard', {
                        url: '/treatmentCard',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'treatmentCardCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.treatmentCardCtrl',
                                    ['js/controllers/archives/treatmentCardCtrl.js?ver='+ bossVersion],
                                    'views/archives/treatmentCard.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('treatmentCardDtails', {
                        url: '/treatmentCardDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'treatmentCardDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.treatmentCardDtailsCtrl',
                                    ['js/controllers/archives/treatmentCardDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/treatmentCardDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('treatmentCardDetail', {
                        url: '/treatmentCardDetail',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'treatmentCardDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.treatmentCardDetailCtrl',
                                    ['js/controllers/archives/treatmentCardDetailCtrl.js?ver='+ bossVersion],
                                    'views/archives/treatmentCardDetail.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('collectionCard', {
                        url: '/collectionCard',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'collectionCardCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.collectionCardCtrl',
                                    ['js/controllers/archives/collectionCardCtrl.js?ver='+ bossVersion],
                                    'views/archives/collectionCard.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('product', {
                        url: '/product',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'productCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.productCtrl',
                                    ['js/controllers/archives/productCtrl.js?ver='+ bossVersion],
                                    'views/archives/product.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('productDtails', {
                        url: '/productDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'productDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.productDtailsCtrl',
                                    ['js/controllers/archives/productDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/productDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('receiveDtails', {
                        url: '/receiveDtails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'receiveDtailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.receiveDtailsCtrl',
                                    ['js/controllers/archives/receiveDtailsCtrl.js?ver='+ bossVersion],
                                    'views/archives/receiveDtails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('accountRecords', {
                        url: '/accountRecords',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'accountRecordsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.accountRecordsCtrl',
                                    ['js/controllers/archives/accountRecordsCtrl.js?ver='+ bossVersion],
                                    'views/archives/accountRecords.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('recordCashier', {
                        url: '/recordCashier',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'recordCashierCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.recordCashierCtrl',
                                    ['js/controllers/archives/recordCashierCtrl.js?ver='+ bossVersion],
                                    'views/archives/recordCashier.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('detailsOfCashier', {
                        url: '/detailsOfCashier',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'detailsOfCashierCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.detailsOfCashierCtrl',
                                    ['js/controllers/archives/detailsOfCashierCtrl.js?ver='+ bossVersion],
                                    'views/archives/detailsOfCashier.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('drawCardRecords', {
                        url: '/drawCardRecords',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'drawCardRecordsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.drawCardRecordsCtrl',
                                    ['js/controllers/archives/drawCardRecordsCtrl.js?ver='+ bossVersion],
                                    'views/archives/drawCardRecords.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('drawCardRecordsDetail', {
                        url: '/drawCardRecordsDetail',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'drawCardRecordsDetailCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.drawCardRecordsDetailCtrl',
                                    ['js/controllers/archives/drawCardRecordsDetailCtrl.js?ver='+ bossVersion],
                                    'views/archives/drawCardRecordsDetail.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('newUser', {
                        url: '/newUser',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'newUserCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.newUserCtrl',
                                    ['js/controllers/archives/newUserCtrl.js?ver='+ bossVersion],
                                    'views/archives/newUser.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('importAddressBook', {
                        url: '/importAddressBook',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'importAddressBookCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.importAddressBookCtrl',
                                    ['js/controllers/archives/importAddressBookCtrl.js?ver='+ bossVersion],
                                    'views/archives/importAddressBook.html?ver=' + bossVersion);
                            }
                        }
                    })
                    /*specifications   规格*/
                    .state('specifications', {
                        url: '/specifications',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'specificationsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.specificationsCtrl',
                                    ['js/controllers/inventory/specificationsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/specifications.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('selShop', {
                        url: '/selShop',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selShopCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selShopCtrl',
                                    ['js/controllers/archives/selShopCtrl.js?ver='+ bossVersion],
                                    'views/archives/selShop.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('membersMess', {
                        url: '/membersMess',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'membersMessCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.membersMessCtrl',
                                    ['js/controllers/archives/membersMessCtrl.js?ver='+ bossVersion],
                                    'views/archives/membersMess.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('distributionFile', {
                        url: '/distributionFile',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'distributionFileCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.distributionFileCtrl',
                                    ['js/controllers/archives/distributionFileCtrl.js?ver='+ bossVersion],
                                    'views/archives/distributionFile.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('selFamily', {
                        url: '/selFamily',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selFamilyCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selFamilyCtrl',
                                    ['js/controllers/archives/selFamilyCtrl.js?ver='+ bossVersion],
                                    'views/archives/selFamily.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('login', {
                        url: '/login',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'loginCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.loginCtrl',
                                    ['js/controllers/archives/loginCtrl.js?ver='+ bossVersion],
                                    'views/archives/login.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('successfulInventory', {
                        url: '/successfulInventory',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'successfulInventoryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.successfulInventoryCtrl',
                                    ['js/controllers/inventory/successfulInventoryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/successfulInventory.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('outbound', {
                        url: '/outbound',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'outboundCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.outboundCtrl',
                                    ['js/controllers/inventory/outboundCtrl.js?ver='+ bossVersion],
                                    'views/inventory/outbound.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('outboundOrderDetails', {
                        url: '/outboundOrderDetails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'outboundOrderDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.outboundOrderDetailsCtrl',
                                    ['js/controllers/inventory/outboundOrderDetailsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/outboundOrderDetails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('outboundRecords', {
                        url: '/outboundRecords',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'outboundRecordsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.outboundRecordsCtrl',
                                    ['js/controllers/inventory/outboundRecordsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/outboundRecords.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('reservoirRunningWater', {
                        url: '/reservoirRunningWater',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'reservoirRunningWaterCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.reservoirRunningWaterCtrl',
                                    ['js/controllers/inventory/reservoirRunningWaterCtrl.js?ver='+ bossVersion],
                                    'views/inventory/reservoirRunningWater.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('unit', {
                        url: '/unit',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'unitCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.unitCtrl',
                                    ['js/controllers/inventory/unitCtrl.js?ver='+ bossVersion],
                                    'views/inventory/unit.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('efficacy', {
                        url: '/efficacy',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'efficacyCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.efficacyCtrl',
                                    ['js/controllers/inventory/efficacyCtrl.js?ver='+ bossVersion],
                                    'views/inventory/efficacy.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('funArea', {
                        url: '/funArea',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'funAreaCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.funAreaCtrl',
                                    ['js/controllers/inventory/funAreaCtrl.js?ver='+ bossVersion],
                                    'views/inventory/funArea.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('warehouseProducts', {
                        url: '/warehouseProducts',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'warehouseProductsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.warehouseProductsCtrl',
                                    ['js/controllers/inventory/warehouseProductsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/warehouseProducts.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('inventoryDetails', {
                        url: '/inventoryDetails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryDetailsCtrl',
                                    ['js/controllers/inventory/inventoryDetailsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventoryDetails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('selFamilys', {
                        url: '/selFamilys',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selFamilysCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selFamilysCtrl',
                                    ['js/controllers/inventory/selFamilysCtrl.js?ver='+ bossVersion],
                                    'views/inventory/selFamilys.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('libraryTubeSetting', {
                        url: '/libraryTubeSetting',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'libraryTubeSettingCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.libraryTubeSettingCtrl',
                                    ['js/controllers/inventory/libraryTubeSettingCtrl.js?ver='+ bossVersion],
                                    'views/inventory/libraryTubeSetting.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('historicalRecord', {
                        url: '/historicalRecord',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'historicalRecordCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.historicalRecordCtrl',
                                    ['js/controllers/inventory/historicalRecordCtrl.js?ver='+ bossVersion],
                                    'views/inventory/historicalRecord.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('addFamily', {
                        url: '/addFamily',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addFamilyCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addFamilyCtrl',
                                    ['js/controllers/inventory/addFamilyCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addFamily.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('inventory', {
                        url: '/inventory',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryCtrl',
                                    ['js/controllers/inventory/inventoryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventory.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('inventoryOver', {
                        url: '/inventoryOver',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryOverCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryOverCtrl',
                                    ['js/controllers/inventory/inventoryOverCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventoryOver.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('inventoryRecords', {
                        url: '/inventoryRecords',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryRecordsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryRecordsCtrl',
                                    ['js/controllers/inventory/inventoryRecordsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventoryRecords.html?ver=' + bossVersion);
                            }
                        }
                    })

                    .state('inventoryRecordsDetails', {
                        url: '/inventoryRecordsDetails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryRecordsDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryRecordsDetailsCtrl',
                                    ['js/controllers/inventory/inventoryRecordsDetailsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventoryRecordsDetails.html?ver=' + bossVersion);
                            }
                        }
                    })
                    /*unwind  平仓*/
                    .state('unwind', {
                        url: '/unwind',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'unwindCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.unwindCtrl',
                                    ['js/controllers/inventory/unwindCtrl.js?ver='+ bossVersion],
                                    'views/inventory/unwind.html?ver=' + bossVersion);
                            }
                        }
                    })
                    /*putInStorage 入库*/
                    .state('putInStorage', {
                        url: '/putInStorage',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'putInStorageCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.putInStorageCtrl',
                                    ['js/controllers/inventory/putInStorageCtrl.js?ver='+ bossVersion],
                                    'views/inventory/putInStorage.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*entryDetails 入库单详情*/
                    .state('entryDetails', {
                        url: '/entryDetails',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'entryDetailsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.entryDetailsCtrl',
                                    ['js/controllers/inventory/entryDetailsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/entryDetails.html?ver=' + bossVersion);
                            }
                        }
                    })
               /* modify 修改*/
                    .state('modify', {
                        url: '/modify',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'modifyCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.modifyCtrl',
                                    ['js/controllers/inventory/modifyLibraryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/modifyLibrary.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*inventoryRecords 入库记录*/
                    .state('inventoryRecordsPics', {
                        url: '/inventoryRecordsPics',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventoryRecordsPicsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventoryRecordsPicsCtrl',
                                    ['js/controllers/inventory/inventoryRecordsPicsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventoryRecordsPics.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*sweepTheCodeIntoTheTreasury 扫码入库*/
                    .state('sweepTheCodeIntoTheTreasury', {
                        url: '/sweepTheCodeIntoTheTreasury',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'sweepTheCodeIntoTheTreasuryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.sweepTheCodeIntoTheTreasuryCtrl',
                                    ['js/controllers/inventory/sweepTheCodeIntoTheTreasuryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/sweepTheCodeIntoTheTreasury.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*automaticallyStorage 扫码自动入库*/
                    .state('automaticallyStorage', {
                        url: '/automaticallyStorage',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'automaticallyStorageCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.automaticallyStorageCtrl',
                                    ['js/controllers/inventory/automaticallyStorageCtrl.js?ver='+ bossVersion],
                                    'views/inventory/automaticallyStorage.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*applicableParts 适用部位*/
                    .state('applicableParts', {
                        url: '/applicableParts',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'applicablePartsCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.applicablePartsCtrl',
                                    ['js/controllers/inventory/applicablePartsCtrl.js?ver='+ bossVersion],
                                    'views/inventory/applicableParts.html?ver=' + bossVersion);
                            }
                        }
                    })
               /* addEmployees 添加家人(员工)*/
                    .state('addEmployees', {
                        url: '/addEmployees',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addEmployeesCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addEmployeesCtrl',
                                    ['js/controllers/inventory/addEmployeesCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addEmployees.html?ver=' + bossVersion);
                            }
                        }
                    })
                /* addBrandOne  添加品牌*/
                    .state('addBrandOne', {
                        url: '/addBrandOne',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addBrandOneCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addBrandOneCtrl',
                                    ['js/controllers/inventory/addBrandOneCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addBrandOne.html?ver=' + bossVersion);
                            }
                        }
                    })
                    .state('addSeries', {
                        url: '/addSeries',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addSeriesCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addSeriesCtrl',
                                    ['js/controllers/inventory/addSeriesCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addSeries.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*addSeries 添加系列*/
                  /*  .state('addSeries', {
                        url: '/addSeries',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addSeriesCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addSeriesCtrl',
                                    ['js/controllers/inventory/addSeriesCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addSeries.html?ver=' + bossVersion);
                            }
                        }
                    })*/
                /*addProduct  添加产品*/
                    .state('addProduct', {
                        url: '/addProduct',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addProductCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addProductCtrl',
                                    ['js/controllers/inventory/addProductCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addProduct.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*modifyProduct 修改产品*/
                    .state('modifyProduct', {
                        url: '/modifyProduct',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'modifyProductCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.modifyProductCtrl',
                                    ['js/controllers/inventory/modifyProductCtrl.js?ver='+ bossVersion],
                                    'views/inventory/modifyProduct.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*inventorySetting  设置*/
                    .state('inventorySetting', {
                        url: '/inventorySetting',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'inventorySettingCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.inventorySettingCtrl',
                                    ['js/controllers/inventory/inventorySettingCtrl.js?ver='+ bossVersion],
                                    'views/inventory/inventorySetting.html?ver=' + bossVersion);
                            }
                        }
                    })
               /* addressBook 库存 通讯录导入*/
                    .state('addressBook', {
                        url: '/addressBook',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'addressBookCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.addressBookCtrl',
                                    ['js/controllers/inventory/addressBookCtrl.js?ver='+ bossVersion],
                                    'views/inventory/addressBook.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*AddOutbound 新增出库*/
                    .state('AddOutbound', {
                        url: '/AddOutbound',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'AddOutboundCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.AddOutboundCtrl',
                                    ['js/controllers/inventory/AddOutboundCtrl.js?ver='+ bossVersion],
                                    'views/inventory/AddOutbound.html?ver=' + bossVersion);
                            }
                        }
                    })
                /* newLibrary  新增入库*/
                    .state('newLibrary', {
                        url: '/newLibrary',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'newLibraryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.newLibraryCtrl',
                                    ['js/controllers/inventory/newLibraryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/newLibrary.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*modifyLibrary    修改(库)*/

                    .state('modifyLibrary', {
                        url: '/modifyLibrary',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'modifyLibraryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.modifyLibraryCtrl',
                                    ['js/controllers/inventory/modifyLibraryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/modifyLibrary.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*chooseWarehouse 选择仓库*/
                    .state('chooseWarehouse', {
                        url: '/chooseWarehouse',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'chooseWarehouseCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.chooseWarehouseCtrl',
                                    ['js/controllers/inventory/chooseWarehouseCtrl.js?ver='+ bossVersion],
                                    'views/inventory/chooseWarehouse.html?ver=' + bossVersion);
                            }
                        }
                    })
                    /*selBrand 选择品牌*/
                    .state('selBrand', {
                        url: '/selBrand',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selBrandCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selBrandCtrl',
                                    ['js/controllers/inventory/selBrandCtrl.js?ver='+ bossVersion],
                                    'views/inventory/selBrand.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*selectTheOutboundType 选择出库类型*/
                    .state('selectTheOutboundType', {
                        url: '/selectTheOutboundType',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selectTheOutboundTypeCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selectTheOutboundTypeCtrl',
                                    ['js/controllers/inventory/selectTheOutboundTypeCtrl.js?ver='+ bossVersion],
                                    'views/inventory/selectTheOutboundType.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*selSeries 选择系列*/
                    .state('selSeries', {
                        url: '/selSeries',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'selSeriesCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.selSeriesCtrl',
                                    ['js/controllers/inventory/selSeriesCtrl.js?ver='+ bossVersion],
                                    'views/inventory/selSeries.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*productInventory 产品盘点*/
                    .state('productInventory', {
                        url: '/productInventory',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'productInventoryCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.productInventoryCtrl',
                                    ['js/controllers/inventory/productInventoryCtrl.js?ver='+ bossVersion],
                                    'views/inventory/productInventory.html?ver=' + bossVersion);
                            }
                        }
                    })
                /*productPutInStorageMore  选择更多产品入库*/
                    .state('productPutInStorageMore', {
                        url: '/productPutInStorageMore',
                        templateProvider: function() { return lazyDeferred.promise; },
                        controller: 'productPutInStorageMoreCtrl',
                        resolve: {
                            load: function($templateCache, $ocLazyLoad, $q, $http) {
                                loadFunction($templateCache, $ocLazyLoad, $q, $http,'app.productPutInStorageMoreCtrl',
                                    ['js/controllers/inventory/productPutInStorageMoreCtrl.js?ver='+ bossVersion],
                                    'views/inventory/productPutInStorageMore.html?ver=' + bossVersion);
                            }
                        }
                    })
                $urlRouterProvider.otherwise('/shopHome')
            }])
})