(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeAmountDialogController', SalestypeAmountDialogController);

    SalestypeAmountDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalestypeAmount', 'SalestypeBill'];

    function SalestypeAmountDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalestypeAmount, SalestypeBill) {
        var vm = this;

        vm.salestypeAmount = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.salestypebills = SalestypeBill.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.salestypeAmount.id !== null) {
                SalestypeAmount.update(vm.salestypeAmount, onSaveSuccess, onSaveError);
            } else {
                SalestypeAmount.save(vm.salestypeAmount, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('salesMaintenanceApp:salestypeAmountUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.saleDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
