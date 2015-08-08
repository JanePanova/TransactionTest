requests = angular.module('Requests', [])

requests.factory('RequestFactory', ($http) ->
  accountUrl = '/api/account/'
  transactionUrl = '/api/transaction/'
  class factory
    @getUsers: () ->
      $http.get accountUrl
    @getUsers: (start) ->
      if start == undefined
        return $http.get accountUrl
      else
        return $http.get accountUrl + '?start=' + start
    @getUser: (id) ->
      $http.get accountUrl + id
    @createUser: (name) ->
      $http.post accountUrl, {name: name}
    @deleteUser: (id) ->
      $http.delete accountUrl + id
    @updateUser: (user) ->
      $http.put accountUrl + user.id, user
    @addMoney: (id, money) ->
      $http.post transactionUrl + 'add/' + id, {money: money}
    @withdrawMoney: (id, money) ->
      $http.post transactionUrl + 'withdraw/' + id, {money: money}
    @getTransactions: () ->
      $http.get transactionUrl
    @getTransactions: (start) ->
      if start == undefined
        return $http.get transactionUrl
      else
        $http.get transactionUrl + '?start=' + start
    @makeTransaction: (from, to, money) ->
      $http.post transactionUrl, {from: from, to: to, money: money}

)