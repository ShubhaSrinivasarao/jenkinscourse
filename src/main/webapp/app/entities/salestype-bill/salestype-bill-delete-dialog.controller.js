(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDeleteController',SalestypeBillDeleteController);

    SalestypeBillDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalestypeBill'];

    function SalestypeBillDeleteController($uibModalInstance, entity, SalestypeBill) {
        var vm = this;

        vm.salestypeBill = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalestypeBill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
