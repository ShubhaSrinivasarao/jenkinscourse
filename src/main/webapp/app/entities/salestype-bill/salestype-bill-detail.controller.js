(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDetailController', SalestypeBillDetailController);

    SalestypeBillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalestypeBill', 'SalestypeAmount', 'SalestypeBillDetl'];

    function SalestypeBillDetailController($scope, $rootScope, $stateParams, previousState, entity, SalestypeBill, SalestypeAmount, SalestypeBillDetl) {
        var vm = this;

        vm.salestypeBill = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('salesMaintenanceApp:salestypeBillUpdate', function(event, result) {
            vm.salestypeBill = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
