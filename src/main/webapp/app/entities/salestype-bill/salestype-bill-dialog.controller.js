(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDialogController', SalestypeBillDialogController);

    SalestypeBillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalestypeBill', 'SalestypeAmount', 'SalestypeBillDetl'];

    function SalestypeBillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalestypeBill, SalestypeAmount, SalestypeBillDetl) {
        var vm = this;

        vm.salestypeBill = entity;
        vm.clear = clear;
        vm.save = save;
        vm.salestypeamounts = SalestypeAmount.query();
        vm.salestypebilldetls = SalestypeBillDetl.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salestypeBill.id !== null) {
                SalestypeBill.update(vm.salestypeBill, onSaveSuccess, onSaveError);
            } else {
                SalestypeBill.save(vm.salestypeBill, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('salesMaintenanceApp:salestypeBillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
