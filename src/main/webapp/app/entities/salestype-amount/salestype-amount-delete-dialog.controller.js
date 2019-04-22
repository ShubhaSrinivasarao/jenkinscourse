(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeAmountDeleteController',SalestypeAmountDeleteController);

    SalestypeAmountDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalestypeAmount'];

    function SalestypeAmountDeleteController($uibModalInstance, entity, SalestypeAmount) {
        var vm = this;

        vm.salestypeAmount = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalestypeAmount.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
