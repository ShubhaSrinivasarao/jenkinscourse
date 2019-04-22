(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillController', SalestypeBillController);

    SalestypeBillController.$inject = ['$scope', '$state', 'SalestypeBill'];

    function SalestypeBillController ($scope, $state, SalestypeBill) {
        var vm = this;

        vm.salestypeBills = [];

        loadAll();

        function loadAll() {
            SalestypeBill.query(function(result) {
                vm.salestypeBills = result;
                vm.searchQuery = null;
            });
        }
    }
})();
