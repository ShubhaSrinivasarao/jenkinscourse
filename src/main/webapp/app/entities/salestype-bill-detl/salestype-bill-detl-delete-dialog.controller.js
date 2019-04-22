(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDetlDeleteController',SalestypeBillDetlDeleteController);

    SalestypeBillDetlDeleteController.$inject = ['$uibModalInstance', 'entity', 'SalestypeBillDetl'];

    function SalestypeBillDetlDeleteController($uibModalInstance, entity, SalestypeBillDetl) {
        var vm = this;

        vm.salestypeBillDetl = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SalestypeBillDetl.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
