class ScheduleService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing ScheduleService"

  getSchedule: (classType) ->
    @$log.debug "listSchedule(#{classType})"
    deferred = @$q.defer()

    @$http.get("/schedule/#{classType}")
    .success((data, status, headers) =>
      @$log
        .info("Listed #{classType} schedule - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list #{classType} schedule - status #{status}")
      deferred.reject(data)
    )
    deferred.promise

  updateSchedule: (classType, schedule) ->
    @$log.debug "updateUser"

    id = schedule._id.$oid
    deferred = @$q.defer()

    @$http.put("/schedule/#{classType}/#{id}", angular.toJson(schedule, false))
    .success((data, status, headers) =>
      @$log.info("Updated #{classType} Schedule - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to update #{classType} Schedule - status #{status}")
      deferred.reject(data)
    )
    deferred.promise


servicesModule.service('ScheduleService', ScheduleService)