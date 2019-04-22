(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salestype-bill', {
            parent: 'entity',
            url: '/salestype-bill',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeBill.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-bill/salestype-bills.html',
                    controller: 'SalestypeBillController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeBill');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('salestype-bill-detail', {
            parent: 'entity',
            url: '/salestype-bill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeBill.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-bill/salestype-bill-detail.html',
                    controller: 'SalestypeBillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeBill');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalestypeBill', function($stateParams, SalestypeBill) {
                    return SalestypeBill.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'salestype-bill',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('salestype-bill-detail.edit', {
            parent: 'salestype-bill-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill/salestype-bill-dialog.html',
                    controller: 'SalestypeBillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeBill', function(SalestypeBill) {
                            return SalestypeBill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-bill.new', {
            parent: 'salestype-bill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill/salestype-bill-dialog.html',
                    controller: 'SalestypeBillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                billNo: null,
                                allotedAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('salestype-bill', null, { reload: 'salestype-bill' });
                }, function() {
                    $state.go('salestype-bill');
                });
            }]
        })
        .state('salestype-bill.edit', {
            parent: 'salestype-bill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill/salestype-bill-dialog.html',
                    controller: 'SalestypeBillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeBill', function(SalestypeBill) {
                            return SalestypeBill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-bill', null, { reload: 'salestype-bill' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-bill.delete', {
            parent: 'salestype-bill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill/salestype-bill-delete-dialog.html',
                    controller: 'SalestypeBillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalestypeBill', function(SalestypeBill) {
                            return SalestypeBill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-bill', null, { reload: 'salestype-bill' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
