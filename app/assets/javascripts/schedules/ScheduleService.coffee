class ScheduleService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing ScheduleService"

  listSchedule: (classType) ->
    @$log.debug "listSchedule(" + classType + ")"
    deferred = @$q.defer()

    @$http.get("/schedule/#{ classType }")
    .success((data, status, headers) =>
      @$log.info("Successfully listed #{ classType } schedule - status #{ status }")
      deferred.resolve(data)
    )
    .error((data, status, heaaders) =>
      @$log.error("Failed to list #{ classType } schedule - status #{ status }")
      deferred.reject(data)
    )
    deferred.promise

  updateSchedule: (classType, id, schedule) ->
    @$log.debug "updateUser #{angular.toJson(schedule, true)}"
    deferred = @$q.defer()

    @$http.post('/schedule/#{classType}/#{id}', schedule)
    .success((data, status, headers) =>
      @$log.info("Successfully updated #{classType} Schedule - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to update #{classType} Schedule - status #{status}")
      deferred.reject(data)
    )
    deferred.promise


servicesModule.service('ScheduleService', ScheduleService)