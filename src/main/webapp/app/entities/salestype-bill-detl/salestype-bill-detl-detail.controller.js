(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDetlDetailController', SalestypeBillDetlDetailController);

    SalestypeBillDetlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SalestypeBillDetl', 'SalestypeBill'];

    function SalestypeBillDetlDetailController($scope, $rootScope, $stateParams, previousState, entity, SalestypeBillDetl, SalestypeBill) {
        var vm = this;

        vm.salestypeBillDetl = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('salesMaintenanceApp:salestypeBillDetlUpdate', function(event, result) {
            vm.salestypeBillDetl = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
