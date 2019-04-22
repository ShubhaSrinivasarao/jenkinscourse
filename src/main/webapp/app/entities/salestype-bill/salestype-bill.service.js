(function() {
    'use strict';
    angular
        .module('salesMaintenanceApp')
        .factory('SalestypeBill', SalestypeBill);

    SalestypeBill.$inject = ['$resource'];

    function SalestypeBill ($resource) {
        var resourceUrl =  'api/salestype-bills/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
