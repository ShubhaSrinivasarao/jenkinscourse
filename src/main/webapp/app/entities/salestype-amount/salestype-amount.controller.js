(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeAmountController', SalestypeAmountController);

    SalestypeAmountController.$inject = ['$scope', '$state', 'SalestypeAmount'];

    function SalestypeAmountController ($scope, $state, SalestypeAmount) {
        var vm = this;

        vm.salestypeAmounts = [];

        loadAll();

        function loadAll() {
            SalestypeAmount.query(function(result) {
                vm.salestypeAmounts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
