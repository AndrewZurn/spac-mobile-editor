class ScheduleController

  constructor: (@$log, @$location, @ScheduleService) ->
    @$log.debug "constructing ScheduleController"
    @schedule = []
    @getClassSchedules()

  getClassSchedules: () ->
    classType = @$location.path().substring(1)
    @$log.debug "getClassSchedule(#{classType})"
    @ScheduleService.getSchedule(classType)
      .then(
        (data) =>
          @$log.debug "Promise returned getSchedule"
          @schedule = data
        ,
        (error) =>
          @$log.error "Unable to get Schedule: ${error}"
      )

  updateClassSchedule: () ->
    classType = @$location.path().substring(1)
    @$log.debug "updateClassSchedule(#{classType})"
    @ScheduleService.updateSchedule(classType, @schedule._id.$oid, @schedule)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data} Schedule"
        @schedule = data
        @$location.path("/schedule/#{classType}")
      ,
      (error) =>
        @$log.error "Unable to update Schedule: #{error}"
    )

controllersModule.controller('ScheduleController', ScheduleController)