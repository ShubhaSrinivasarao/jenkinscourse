'use strict';

describe('Controller Tests', function() {

    describe('SalestypeAmount Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSalestypeAmount, MockSalestypeBill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSalestypeAmount = jasmine.createSpy('MockSalestypeAmount');
            MockSalestypeBill = jasmine.createSpy('MockSalestypeBill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SalestypeAmount': MockSalestypeAmount,
                'SalestypeBill': MockSalestypeBill
            };
            createController = function() {
                $injector.get('$controller')("SalestypeAmountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'salesMaintenanceApp:salestypeAmountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
