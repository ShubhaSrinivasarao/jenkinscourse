(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeAmountDetailController', SalestypeAmountDetailController);

    SalestypeAmountDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalestypeAmount', 'SalestypeBill'];

    function SalestypeAmountDetailController($scope, $rootScope, $stateParams, previousState, entity, SalestypeAmount, SalestypeBill) {
        var vm = this;

        vm.salestypeAmount = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('salesMaintenanceApp:salestypeAmountUpdate', function(event, result) {
            vm.salestypeAmount = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
