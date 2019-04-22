(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('salestype-bill-detl', {
            parent: 'entity',
            url: '/salestype-bill-detl?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeBillDetl.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detls.html',
                    controller: 'SalestypeBillDetlController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeBillDetl');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('salestype-bill-detl-detail', {
            parent: 'entity',
            url: '/salestype-bill-detl/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'salesMaintenanceApp.salestypeBillDetl.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detl-detail.html',
                    controller: 'SalestypeBillDetlDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('salestypeBillDetl');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SalestypeBillDetl', function($stateParams, SalestypeBillDetl) {
                    return SalestypeBillDetl.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'salestype-bill-detl',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('salestype-bill-detl-detail.edit', {
            parent: 'salestype-bill-detl-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detl-dialog.html',
                    controller: 'SalestypeBillDetlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeBillDetl', function(SalestypeBillDetl) {
                            return SalestypeBillDetl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-bill-detl.new', {
            parent: 'salestype-bill-detl',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detl-dialog.html',
                    controller: 'SalestypeBillDetlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                returnDesc: null,
                                returnAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('salestype-bill-detl', null, { reload: 'salestype-bill-detl' });
                }, function() {
                    $state.go('salestype-bill-detl');
                });
            }]
        })
        .state('salestype-bill-detl.edit', {
            parent: 'salestype-bill-detl',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detl-dialog.html',
                    controller: 'SalestypeBillDetlDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SalestypeBillDetl', function(SalestypeBillDetl) {
                            return SalestypeBillDetl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-bill-detl', null, { reload: 'salestype-bill-detl' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('salestype-bill-detl.delete', {
            parent: 'salestype-bill-detl',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/salestype-bill-detl/salestype-bill-detl-delete-dialog.html',
                    controller: 'SalestypeBillDetlDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SalestypeBillDetl', function(SalestypeBillDetl) {
                            return SalestypeBillDetl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('salestype-bill-detl', null, { reload: 'salestype-bill-detl' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
