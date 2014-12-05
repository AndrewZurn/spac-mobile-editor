class EventsService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = { headers: @headers }

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing EventsService"

  getEvents: () ->
    @$log.debug "getEvents()"
    deferred = @$q.defer()

    @$http.get("/events")
      .success((data, status, headers) =>
        @$log.info("Successfully retrieved events - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.error("Failure to retrieve events - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

  updateEvents: (events) ->
    @$log.debug "updateEvents #{angular.toJson(events, true)}"

    id = events._id.$oid
    deferred = @$q.defer()

    @$http.put("/events/${id}", events)
      .success((data, status, headers) =>
        @$log.info("Successfully updated events - status #{status}")
        deferred.resolve(data)
      )
      .error((data, status, headers) =>
        @$log.info("Failure to update events - status #{status}")
        deferred.reject(data)
      )
    deferred.promise

servicesModule.service('EventsService', EventsService)