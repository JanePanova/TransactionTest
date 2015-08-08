app = angular.module('app', ['ngAnimate', 'NavigationBar', 'ui.router', 'Requests', 'toaster'])

app.config(($stateProvider, $urlRouterProvider) ->
  $urlRouterProvider.otherwise('/')
  $stateProvider.state('users', {
    url: '/'
    templateUrl: 'templates/users.html'
    controller: 'UsersListController'
  }).state('transactions', {
    url: '/transactions'
    templateUrl: 'templates/transactions.html'
    controller: 'TransactionsListController'
  }).state('user', {
    url: '/user/{id:int}'
    templateUrl: 'templates/user.html'
    controller: 'UserController'
  }).state('addUser', {
    url: '/user/add'
    templateUrl: 'templates/addUser.html'
    controller: 'AddUserController'
  }).state('makeTransaction', {
    url: '/transaction'
    templateUrl: 'templates/makeTransaction.html'
    controller: 'MakeTransactionController'
  })
)

app.controller('UsersListController', ($scope, RequestFactory) ->
  $scope.users = {}
  $scope.hasMore = true
  $scope.minId = null

  RequestFactory.getUsers().success((data) ->
    $scope.users = data
    for user in data
      do (user) ->
        if $scope.minId is null
          $scope.minId = user.id
        $scope.minId = Math.min $scope.minId, user.id
        return
  )

  $scope.loadMore = () ->
    RequestFactory.getUsers($scope.minId).success((users) ->
      for user in users
        do (user) ->
          if $scope.minId is null
            $scope.minId = user.id
          $scope.minId = Math.min $scope.minId, user.id
          return
      $scope.users = $scope.users.concat(users)
      if users.length is 0
        $scope.hasMore = false
    )
)

app.controller('UserController', ($stateParams, $scope, RequestFactory, $state, toaster) ->
  if $stateParams.id <= 0
    $state.go('users')
  RequestFactory.getUser($stateParams.id).success((data) ->
    $scope.user = data
    if data.blocked
      toaster.error('This user is blocked')
      $state.go('users')
  ).error((data) ->
    toaster.error(data.message)
    $state.go('users')
  )
  $scope.deleteUser = () ->
    RequestFactory.deleteUser($scope.user.id).success(() ->
      $state.go('users', {reload: true})
    ).error((data)->
      toaster.error(data.message)
    )
    return
  $scope.updateUser = () ->
    console.log $scope.user
    RequestFactory.updateUser($scope.user).success(() ->
      $state.go('users', {reload: true})
    ).error((data) ->
      console.log data
      toaster.error(data.message)
    )
    return
  $scope.addMoney = () ->
    money = fixMoney($scope.moneyToAdd)
    RequestFactory.addMoney($scope.user.id, $scope.moneyToAdd).success(() ->
      $scope.user.wallet.money += money
      $scope.moneyToAdd = null
    ).error((data) ->
      toaster.error(data.message)
    )
    return
  $scope.withdrawMoney = () ->
    money = fixMoney($scope.moneyToWithdraw)
    RequestFactory.withdrawMoney($scope.user.id, $scope.moneyToWithdraw)
    .success(() ->
      $scope.user.wallet.money -= money
      return
    ).error((data) ->
      toaster.error(data.message)
    )
    $scope.moneyToWithdraw = null
    return
  fixMoney = (money) ->
    Math.floor(money * 100) / 100
  return
)

app.controller('TransactionsListController', ($scope, RequestFactory) ->
  $scope.transactions = {}
  $scope.hasMore = true
  $scope.minId = null

  RequestFactory.getTransactions().success((transactions) ->
    $scope.transactions = transactions
    for transaction in transactions
      do (transaction) ->
        if $scope.minId is null
          $scope.minId = transaction.id
        $scope.minId = Math.min $scope.minId, transaction.id
        return
  )

  $scope.loadMore = () ->
    RequestFactory.getTransactions($scope.minId).success((transactions) ->
      for transaction in transactions
        do (transaction) ->
          if $scope.minId is null
            $scope.minId = user.id
          $scope.minId = Math.min $scope.minId, transaction.id
          return
      $scope.transactions = $scope.transactions.concat(transactions)
      if transactions.length is 0
        $scope.hasMore = false
    )

  return
)

app.controller('AddUserController', ($scope, RequestFactory, $state, toaster) ->
  $scope.createUser = () ->
    RequestFactory.createUser($scope.name)
    .success(() ->
      $state.go('users')
    ).error((data) ->
      toaster.error(data.message)
    )
    return
  return
)

app.controller('MakeTransactionController', ($scope, RequestFactory, $state, toaster) ->
  $scope.$watch('from', () ->
    if $scope.from == undefined or $scope.from == null or $scope.from is ""
      $scope.userFrom = null
    else
      RequestFactory.getUser($scope.from).success((data) ->
        if data is null
          $scope.userFrom = null
        else
          $scope.userFrom = data
        return
      ).error( ->
        $scope.userFrom = null
      )
    return
  )

  $scope.$watch('to', () ->
    if $scope.to == undefined or $scope.to == null or $scope.to is ""
      $scope.userTo = null
    else
      RequestFactory.getUser($scope.to).success((data) ->
        if data is null
          $scope.userTo = null
        else
          $scope.userTo = data
        return
      ).error( ->
        $scope.userTo = null
      )
    return
  )

  $scope.makeTransaction = () ->
    RequestFactory.makeTransaction($scope.from, $scope.to, $scope.money)
    .success(() ->
      $state.go('transactions')
    )
    return

  return
)