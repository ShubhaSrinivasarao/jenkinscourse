'use strict';

describe('Controller Tests', function() {

    describe('SalestypeBillDetl Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSalestypeBillDetl, MockSalestypeBill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSalestypeBillDetl = jasmine.createSpy('MockSalestypeBillDetl');
            MockSalestypeBill = jasmine.createSpy('MockSalestypeBill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SalestypeBillDetl': MockSalestypeBillDetl,
                'SalestypeBill': MockSalestypeBill
            };
            createController = function() {
                $injector.get('$controller')("SalestypeBillDetlDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'salesMaintenanceApp:salestypeBillDetlUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
