(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salestype-amount', {
            parent: 'entity',
            url: '/salestype-amount',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeAmount.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-amount/salestype-amounts.html',
                    controller: 'SalestypeAmountController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeAmount');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('salestype-amount-detail', {
            parent: 'entity',
            url: '/salestype-amount/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeAmount.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-amount/salestype-amount-detail.html',
                    controller: 'SalestypeAmountDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeAmount');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalestypeAmount', function($stateParams, SalestypeAmount) {
                    return SalestypeAmount.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'salestype-amount',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('salestype-amount-detail.edit', {
            parent: 'salestype-amount-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-amount/salestype-amount-dialog.html',
                    controller: 'SalestypeAmountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeAmount', function(SalestypeAmount) {
                            return SalestypeAmount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-amount.new', {
            parent: 'salestype-amount',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-amount/salestype-amount-dialog.html',
                    controller: 'SalestypeAmountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                salesTypeId: null,
                                totalAmount: null,
                                saleDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('salestype-amount', null, { reload: 'salestype-amount' });
                }, function() {
                    $state.go('salestype-amount');
                });
            }]
        })
        .state('salestype-amount.edit', {
            parent: 'salestype-amount',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-amount/salestype-amount-dialog.html',
                    controller: 'SalestypeAmountDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeAmount', function(SalestypeAmount) {
                            return SalestypeAmount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-amount', null, { reload: 'salestype-amount' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-amount.delete', {
            parent: 'salestype-amount',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-amount/salestype-amount-delete-dialog.html',
                    controller: 'SalestypeAmountDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalestypeAmount', function(SalestypeAmount) {
                            return SalestypeAmount.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-amount', null, { reload: 'salestype-amount' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
