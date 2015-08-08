navigation = angular.module('NavigationBar', [])

navigation.directive('navbar', () ->
  restrict: 'E'
  templateUrl: 'templates/navbar.html'
)