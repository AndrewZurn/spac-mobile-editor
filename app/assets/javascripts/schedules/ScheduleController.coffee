class ScheduleController

  constructor: (@$log, @$location, @ScheduleService) ->
    @$log.debug "constructing ScheduleController"
    @schedule = {}

  getClassSchedule: (classType) ->
    @$log.debug "getClassSchedule(#{classType})"

    @ScheduleService.getSchedule(classType)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} getSchedule"
        @schedule = data
      ,
      (error) =>
        @$log.error "Unable to get Schedule: ${error}"
    )

  updateClassSchedule: (classType) ->
    @$log.debug "updateClassSchedule(#{classType})"
    @ScheduleService.updateSchedule(classType, @schedule._id, @schedule)
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