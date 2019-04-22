(function() {
    'use strict';
    angular
        .module('salesMaintenanceApp')
        .factory('SalestypeAmount', SalestypeAmount);

    SalestypeAmount.$inject = ['$resource', 'DateUtils'];

    function SalestypeAmount ($resource, DateUtils) {
        var resourceUrl =  'api/salestype-amounts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.saleDate = DateUtils.convertLocalDateFromServer(data.saleDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.saleDate = DateUtils.convertLocalDateToServer(copy.saleDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.saleDate = DateUtils.convertLocalDateToServer(copy.saleDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
