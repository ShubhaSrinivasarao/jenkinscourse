(function() {
    'use strict';

    angular
        .module('salesMaintenanceApp')
        .controller('SalestypeBillDetlDialogController', SalestypeBillDetlDialogController);

    SalestypeBillDetlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SalestypeBillDetl', 'SalestypeBill'];

    function SalestypeBillDetlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SalestypeBillDetl, SalestypeBill) {
        var vm = this;

        vm.salestypeBillDetl = entity;
        vm.clear = clear;
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
            if (vm.salestypeBillDetl.id !== null) {
                SalestypeBillDetl.update(vm.salestypeBillDetl, onSaveSuccess, onSaveError);
            } else {
                SalestypeBillDetl.save(vm.salestypeBillDetl, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('salesMaintenanceApp:salestypeBillDetlUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
