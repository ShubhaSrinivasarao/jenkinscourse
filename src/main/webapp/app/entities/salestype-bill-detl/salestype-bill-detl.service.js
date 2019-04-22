(function() {
    'use strict';
    angular
        .module('salesMaintenanceApp')
        .factory('SalestypeBillDetl', SalestypeBillDetl);

    SalestypeBillDetl.$inject = ['$resource'];

    function SalestypeBillDetl ($resource) {
        var resourceUrl =  'api/salestype-bill-detls/:id';

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
